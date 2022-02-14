# python countlines.py < good.txt
import re
from sys import stdin
from geojson import Point, Feature, FeatureCollection, dump


def TrueXor(*bools):
    return sum(bools) == 1


def isNumber(s):
    try:
        float(s)
        return True
    except ValueError:
        return False


def skipIfBlank(idx):
    if (str.isspace(tokens[idx])):
        return 1
    else:
        return 0


def addFeature(lat, long, comment):
    if abs(lat) > 90 or abs(long) > 180:
        raise UserWarning()
    # why is lat and long switched?
    coords = Point((long, lat))
    print("{:.6f}, {:.6f} ".format(lat, long))
    features.append(Feature(geometry=coords, properties={"Comment": comment}))


def standardForm():
    nextNumNegative = False  # if set true will make the next number negative
    cords = []

    idx = 0  # number of tokens Used
    idx += skipIfBlank(idx)  # remove starting spaces if there is any

    while idx < len(tokens):
        if isNumber(tokens[idx]):
            if nextNumNegative:
                cords.append(-float(tokens[idx]))
            else:
                cords.append(float(tokens[idx]))
            nextNumNegative = False
            idx += 2  # skip the next space and then to the next token
            if len(cords) >= 2:
                break
        elif tokens[idx] == '-' or tokens[idx] == '+':
            if tokens[idx] == '-':
                nextNumNegative = True
            idx += 1
        else:
            raise UserWarning()

    comment = "".join(tokens[idx:])

    # print(*cords)
    # print("comment: ", comment)
    addFeature(cords[0], cords[1], comment)


def hemisphereForm():
    cords = []
    hemi = []

    idx = 0  # number of tokens Used
    idx += skipIfBlank(idx)  # remove starting spaces if there is any

    while idx < len(tokens) and len(cords) < 2:
        if isNumber(tokens[idx]):
            cords.append(tokens[idx])
            idx += 1
        else:
            raise UserWarning()

        idx += skipIfBlank(idx)  # remove spaces if there is any

        if re.fullmatch(r'[NSEW]', tokens[idx]):
            hemi.append(tokens[idx])
            idx += 2  # skip the next space and then to the next token
        else:
            raise UserWarning()

    comment = "".join(tokens[idx:])

    checkHem(float(cords[0]), hemi[0], float(cords[1]), hemi[1], comment)


def checkHem(num1, hem1, num2, hem2, comment):
    if (((hem1 == "N" or hem1 == "S") and (hem2 == "N" or hem2 == "S"))
            or (hem1 == "W" or hem1 == "E") and (hem2 == "W" or hem2 == "E")):
        raise UserWarning()
    if hem1 == "S" or hem1 == "W":
        num1 = -num1
    if hem2 == "S" or hem2 == "W":
        num2 = -num2
    if hem1 == "N" or hem1 == "S":
        addFeature(num1, num2, comment)
    if hem1 == "W" or hem1 == "E":
        addFeature(num2, num1, comment)


def degreeCords():
    cords = []
    hemi = []

    idx = 0  # number of tokens Used

    idx += skipIfBlank(idx)  # remove starting spaces if there is any

    # loop until we have all the cords or there is no more tokens
    while idx < len(tokens) and len(cords) < 2:
        dms = []
        # first token must be a number
        if isNumber(tokens[idx]):
            dms.append(tokens[idx])
            idx += 1
        else:
            raise UserWarning()

        idx += skipIfBlank(idx)  # remove spaces if there is any

        # next token should be a degree indicator
        if tokens[idx] == 'd' or tokens[idx] == '°' or tokens[idx] == 'Â°':
            idx += 1
        else:
            raise UserWarning()

        idx += skipIfBlank(idx)  # remove spaces if there is any

        # next must be a minutes and it must be less than 60 to be valid
        if isNumber(tokens[idx]) and float(tokens[idx]) < 60:
            dms.append(tokens[idx])
            idx += 1
        else:
            raise UserWarning()

        idx += skipIfBlank(idx)  # remove spaces if there is any

        # next token should be minutes indicator
        if tokens[idx] == 'm' or tokens[idx] == "'":
            idx += 1
        else:
            raise UserWarning()

        idx += skipIfBlank(idx)  # remove spaces if there is any

        # if next token is a direction then it's degree and decimal minute form
        if re.fullmatch(r'[NSEW]', tokens[idx]):
            hemi.append(tokens[idx])
            idx += 2  # skip the next space and then to the next token
        # else it is a dms form
        else:
            # next must be a seconds and it must be less than 60 to be valid
            if isNumber(tokens[idx]) and float(tokens[idx]) < 60:
                dms.append(tokens[idx])
                idx += 1
            else:
                raise UserWarning()

            idx += skipIfBlank(idx)  # remove spaces if there is any

            # next token should be seconds indicator
            if tokens[idx] == 's' or tokens[idx] == '"':
                idx += 1
            else:
                raise UserWarning()

            idx += skipIfBlank(idx)  # remove spaces if there is any

            # next should be a direction
            if re.fullmatch(r'[NSEW]', tokens[idx]):
                hemi.append(tokens[idx])
                idx += 2  # skip the next space and then to the next token
            else:
                raise UserWarning()

        c = 0

        # convert dm or dms to standard form
        for i in range(len(dms)):
            # if is last one it can be float
            if i == len(dms)-1:
                c += float(dms[i])/(60**i)
            # if it is not a float it must be a Integer
            else:
                if re.fullmatch(r'[0-9]+', dms[i]):
                    c += float(dms[i])/(60**i)
                else:
                    raise UserWarning()
        cords.append(c)

    comment = "".join(tokens[idx:])
    checkHem(float(cords[0]), hemi[0], float(cords[1]), hemi[1], comment)


def checkCorType():
    numForm = False  # set true is cord does not have direction or degrees
    directionForm = False  # set true is cord does have direction
    degreeForm = False  # set true is cord is in degrees

    idx = 0  # number of tokens Used

    idx += skipIfBlank(idx)  # remove starting spaces if there is any

    # if there is a plus or minus it must be number only coordinate
    if (tokens[idx] == '-' or tokens[idx] == '+'):
        numForm = True
        idx += 1

    # the next token must be a number or it is an invaild input
    if (isNumber(tokens[idx])):
        idx += 1
    else:
        raise UserWarning()

    idx += skipIfBlank(idx)  # remove spaces if there is any

    # if next token is a d or degress symbol it must be
    if (tokens[idx] == 'd' or tokens[idx] == '°'):
        degreeForm = True
    # if next token is a NSEW it must be direction coordinate
    elif (re.fullmatch(r'[NSEW]', tokens[idx])):
        directionForm = True
    # if next token is a plus or minus or a number it must be number only cord
    elif (tokens[idx] == '-' or tokens[idx] == '+'
            or isNumber(tokens[idx])):
        numForm = True

    if(not TrueXor(numForm, directionForm, degreeForm)):
        raise UserWarning()
    elif (numForm):
        standardForm()
    elif (directionForm):
        # print('is directionForm')
        hemisphereForm()
    elif (degreeForm):
        # print('is degreeForm')
        degreeCords()


features = []

for line in stdin:
    line = line.rstrip()
    # find tokens where is all digits
    # or all not (whitespace or digits)
    # or all whitespace
    tokens = re.findall(r'[0-9]+\.?[0-9]*|[^\s\d,\'"]+|[\s,]+|[\'"]',  line)

    try:
        # print(*tokens, sep="")
        # print(tokens)
        if len(tokens) == 0:
            continue
        if len(tokens) < 2:
            raise UserWarning()
        checkCorType()
    except UserWarning:
        print("Unable to process: " + line)
    except IndexError:
        print("Unable to process: " + line)


feature_collection = FeatureCollection(features)

with open('myfile.geojson', 'w') as file:
    dump(feature_collection, file)
