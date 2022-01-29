# python countlines.py < good.txt
import re
from sys import stdin




for line in stdin:
    line=line.rstrip()


    try:
        for i in line: 
            print(i)
    except UserWarning as e:
        print("Unable to process: " + line)
