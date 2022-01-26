# python countlines.py < good.txt
import re
from sys import stdin




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
