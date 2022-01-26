# python countlines.py < good.txt
import re
from sys import stdin


def standard_form(lat, long):
    if (abs(lat) <= 90 and abs(long) <= 180):
        print("cords: [" + str(lat) + "," + str(long) + "]")
    else:
        raise UserWarning()


def hemi_form(num1, hem1, num2, hem2):
    if ((hem1 == "N" or hem1 == "S") and (hem2 == "N" or hem2 == "S")) or ((hem1 == "W" or hem1 == "E") and (hem2 == "W" or hem2 == "E")):
        raise UserWarning()
    if hem1 == "S" or hem1 == "W":
        num1 = -num1
    if hem2 == "S" or hem2 == "W":
        num2 = -num2
    if hem1 == "N" or hem1 == "S":
        standard_form(num1, num2)
    if hem1 == "W" or hem1 == "E":
        standard_form(num2, num1)


def process_tokens():
    # first is only number and second is only number (standard form)
    if re.fullmatch(r'[+-]?[0-9]+\.?[0-9]*', tokens[0]) and re.fullmatch(r'[+-]?[0-9]+\.?[0-9]*', tokens[1]):
        standard_form(float(tokens.pop(0)), float(tokens.pop(0)))
        return
     # format is num hem num hem (standard form with hemisphere with spaces)
    if re.fullmatch(r'[0-9]+\.?[0-9]*', tokens[0]) and re.fullmatch(r'[0-9]+\.?[0-9]*', tokens[2]) and re.fullmatch(r'[NSEW]', tokens[1]) and re.fullmatch(r'[NSEW]', tokens[3]) :
        hemi_form(float(tokens.pop(0)), tokens.pop(0),
                    float(tokens.pop(0)), tokens.pop(0))
        return
    # format is numhem numhem (standard form with hemisphere no spaces)
    if re.fullmatch(r'[0-9]+\.?[0-9]*?[NSEW]', tokens[0]) and re.fullmatch(r'[0-9]+\.?[0-9]*?[NSEW]', tokens[1]):
        n1 = re.findall(r'[0-9]+\.?[0-9]*|[NSEW]', tokens.pop(0))
        n2 = re.findall(r'[0-9]+\.?[0-9]*|[NSEW]', tokens.pop(0))
        print(n1, n2)
        hemi_form(float(n1[0]), n1[1], float(n2[0]), n2[1])
        return
    # degress and decimal minutes form with spaces
    if re.fullmatch(r'[0-9]+', tokens[0]) and re.fullmatch(r'[\°d]', tokens[1]) and re.fullmatch(r'[0-9]+\.?[0-9]*',tokens[2])


    print("end of process_tokens")


for line in stdin:
    line=line.rstrip()
    tokens=line.replace(",", " ").split()
    print(line)
    print(tokens)
    try:
        if len(tokens) >= 2:
            process_tokens()
            print("comment: " + ' '.join(tokens))
        else:
            raise UserWarning()
    except UserWarning as e:
        print("Unable to process: " + line)
