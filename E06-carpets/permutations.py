import numpy as np
import itertools
print(len(list(itertools.permutations(np.array([1, 2, 3, 4, 5, 6, 7, 8, 9, 10])))))
print(len(list(itertools.product([1, 2], repeat=10))))
