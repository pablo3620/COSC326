### COSC326 Etude 06 Carpets
### Make Carpet
### Due: N/A
### Group: PAWNS
### Memebers: Andy, William, Pablo, Nicolas

#test data
strip_1 = ["R", "G", "B"]
strip_2 = ["R", "G", "B"]
strip_3 = ["R", "G", "R"]
strip_4 = ["R", "G", "G"]
carpet = [strip_1, strip_2, strip_3, strip_4]
#print(carpet);

# Count the Number of Matching Blocks in a Carpet
# @param carpet A carpet represented as a 2D list.
# @param inverse A logical. If True, total number of non-matches is returned.
# @return n_match Total number of matches or non-matches in a carpet.
def count_match(carpet, inverse = False):              #count the number of matches in a carpet
    n_match = 0;                      #number of matches
    strip_length = len(carpet[1]);    #assume equal strip length
    carpet_length = len(carpet);      #number of strips in a carpet
    for i in range(carpet_length-1):  #iterate over carpet
        strip_left = carpet[i];
        strip_right = carpet[i+1];
        for j in range(strip_length): #iterate over strip
            if (strip_left[j] == strip_right[j]):
                n_match = n_match + 1;
    if (inverse):
        return (carpet_length-1) * strip_length - n_match; 
    return n_match;                   #total number of matches in a carpet

# Reverse a Carpet Strip
# @param strip A single carpet strip.
# @return A reversed carpet strip.
def flip_it(strip):
    strip.reverse();
    return strip;

def no_match(carpet, size = 5):
    first_strip = carpet[1];
    new_carpet = first_strip;
    new_carpet = new_carpet.append(carpet[2]);
    return new_carpet;
    

#test output
#print(count_match(carpet));
#print(count_match(carpet, inverse = True));
#print(flip_it(carpet[1]));
print(no_match(carpet));