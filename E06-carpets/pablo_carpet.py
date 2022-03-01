import numpy as np
import itertools
import copy

strips = []


strips.append(["R", "G", "B"])
strips.append(["G", "R", "B"])
strips.append(["R", "G", "R"])
strips.append(["R", "G", "G"])





print(itertools.permutations(strips))

carpetPerm = itertools.permutations(strips)

print(itertools.product([False, True], repeat=len(strips)))

# 0 for unflipped 1 for fliped 
flipComb = list(itertools.product([False, True], repeat=len(strips)))


match_count = []
carpetComb = []

# for each combination of carpet
for carpet in carpetPerm:
    # print(carpet)
    #for each flip
    for flip in flipComb:
        print(flip)
        carpet_copy = copy.deepcopy(list(carpet))
        # flip carpet if needs to be flipped 
        for idx, o in enumerate(flip):
            if o:
                carpet_copy[idx].reverse()
        print(carpet_copy)
        # compare the elements of combination
        match_count.append(0)
        carpetComb.append(carpet_copy)
        for i in range(len(carpet_copy)-1):
            for j in range(len(carpet_copy[i])):
                if carpet_copy[i][j] == carpet_copy[i+1][j]:
                    match_count[-1] += 1
        print(match_count[-1])


# can make ouput here
print(max(match_count))
print(carpetComb[match_count.index(max(match_count))])