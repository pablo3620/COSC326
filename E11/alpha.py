import re
import sys

words = set()


def getWord(token):
    # regex looks confusing but it not really
    # just checks it is in <a><b><c> format as per specs
    if re.fullmatch(r'[\"]?([A-Z]|[a-z])[a-z]*[\']?[a-z]*'
                    r'[\"]?[\,\.\:\;\?\!]?', token):
        # print(token)
        m = re.search('([A-Z]|[a-z])[a-z]*[\']?[a-z]*', token)
        words.add(m.group().lower())


fileName = sys.argv[1]
# print(fileName)

file = open(fileName, "r")
for line in file:
    tokens = line.split()
    # print(tokens)
    for token in tokens:
        getWord(token)

file.close()

wordSorted = list(words)
wordSorted.sort()

for word in wordSorted:
    print(word)
