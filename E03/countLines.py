#python countlines.py < good.txt
import re
from sys import stdin


def process_tokens(tokens):
    # current_token = re.split('(\d.*)',tokens[0])
    # print(current_token)
    token_count = 0
    lat = 0
    long = 0

    if len(tokens) >= 2:
        #first token is only number 
        if re.match(r'[+-]?[0-9]+[.]?[0-9]*', tokens[0]) and abs(float(tokens[0])) <= 180:
            #if second token is also number (Stardard form)
            if re.match(r'[+-]?[0-9]+[.]?[0-9]*', tokens[1]) and abs(float(tokens[1])) <= 180 and abs(float(tokens[0])) <= 90:
                lat = float(tokens.pop(0))
                long = float(tokens.pop(0))
            if re.match(r'[NSEW]', tokens[1]):
                if tokens[1] == "N": 
                    lat = float(tokens.pop(0))
                    tokens.pop(0)
                if tokens[1] == "S": 
                    lat = -float(tokens.pop(0))
                    tokens.pop(0)
                if tokens[1] == "E": 
                    long = float(tokens.pop(0))
                    tokens.pop(0)
                if tokens[1] == "W": 
                    long = -float(tokens.pop(0))
                    tokens.pop(0)
    print("cords: [" + str(lat) + "," + str(long) + "]")
    print("comment: " + ' '.join(tokens))

    raise ValueError()


for line in stdin:
    line = line.rstrip()
    tokens = line.replace(",", " ").split()
    print(line)
    print(tokens)
    try:
        process_tokens(tokens)
    except ValueError as e:
        print("Unable to process: " + line)
