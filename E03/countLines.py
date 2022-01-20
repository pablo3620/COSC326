#python countlines.py < good.txt
import fileinput
from sys import stdin


for line in stdin:
    tokens = line.replace(",", " ").split()
    print(line)
    print(tokens)