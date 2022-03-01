import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Carpet {

    /**
     * list of every carpet inputed into the program.
     * each carpet is stored as a seperate string on the ArrayList with each
     * character representing color tile
     */
    static ArrayList<String> carpets;
    /**
     * same length as total carpets. a true value at index i indicates that
     * carpets[i] has already been used
     */
    static boolean[] usedCarpets;
    /**
     * stores the ArrayList carpets indexs of the carpet that can be used in the
     * final carpet
     */
    static int[] finalCarpet;
    /**
     * same size as finalCarpet. true indicates that the carpet has been flipped
     * when used
     */
    static boolean[] flippedCarpet;

    /**
     * array contains the number of matches of carpets. i.e. number at index 5
     * contains the number 5 matches.
     */
    static int[] carpetMatch;
    /** Desired length of the final carpet */
    static int carpetLength;
    /** widith of the carpet */
    static int carpetWidth;

    static Integer[][] matchCountMemo;

    public static void main(String[] args) {

        carpetLength = Integer.parseInt(args[0]);
        String option = args[1];

        // initializing array
        carpets = new ArrayList<String>(1);
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            try {
                // for each line save the String to the array
                carpets.add(line);
            } catch (Exception e) {
                System.out.println(line + " Invalid");
            }
        }
        in.close();

        // initializing data structure. could probably do objects instead but i found
        // arrays easier
        setupCarpets();

        // checking the carpets
        try {
            for (String carpet : carpets) {
                if (carpet.length() != carpetWidth)
                    throw new Exception("Invalid carpet, width not consistent");
            }
            if (carpets.size() < carpetLength) {
                throw new Exception("Desired carpet is longer than number of carpets available");
            }
            if (carpets.size() == 0) {
                throw new Exception("No carpets available");
            }

            switch (option) {
                // no matching is allowed
                case "-n":
                    // if a carpet combination is possible with no matching then print that carpet
                    // combination
                    if (noMatch(-1, false, 0)) {
                        printCarpet();
                    } else {
                        System.out.println("not possible");
                    }
                    break;

                case "-m":
                    // test print of a greedy carpet combination
                    // System.out.println("no. greedy matches " + greedyMaxMatch(0, false, 1, 0));
                    // printCarpet();
                    // setupCarpets();

                    // uses all carpets as a starting point for the greedy algorithm to get a lower
                    // baseline
                    int maxMatch = 0;
                    for (int i = 0; i < carpets.size(); i++) {
                        int match = greedyMaxMatch(i, false, 1, 0);
                        setupCarpets();
                        if (match > maxMatch)
                            maxMatch = match;
                    }
                    //System.out.println("max no. greedy matches " + maxMatch);
                    // System.out.println(Arrays.toString(carpetMatch));
                    // System.out.println("no. matches " + maxMatch(-1, false, 0, 0, maxMatch - 1));
                    int match = maxMatch(-1, false, 0, 0, maxMatch - 1);
                    printCarpet();
                    System.out.println(match);
                    break;
                case "-b":
                    int noMatch = greedyBalMatch(0, false, 1, 0);
                    // System.out.println("Number of matches " + greedyBalMatch(0, false, 1, 0));
                    printCarpet();
                    System.out.println(noMatch);
                    break;
                default:
                    throw new Exception("no option selected");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * uses a greedy algorithm to find equal number of matches and non-matching
     * carpets. at each step will attempt to find the number matches so that the
     * total number of matches is closest as possible equal to the number of
     * non-matches. as using greedy algorithm will only ever start as the specified
     * previousCarpet
     * 
     * @param previousCarpet  index of the previous carpet
     * @param previousFlipped true indicates that the previous carpet was used
     *                        flipped
     * @param count           current count of carpets used.
     * @param matchCount      count of current number of matches
     * @return
     */
    static int greedyBalMatch(int previousCarpet, boolean previousFlipped, int count, int matchesCount) {
        // adds carpets found as best in the previous recursion to the final carpets
        // is done before as greedyBalMatch takes in the input of what carpet to use to
        // start
        usedCarpets[previousCarpet] = true;
        finalCarpet[count - 1] = previousCarpet;
        flippedCarpet[count - 1] = previousFlipped;
        // if carpet is full length finish
        if (count == carpetLength)
            return Math.abs((count - 1) * carpetWidth - 2 * matchesCount);

        int nonMatches = (count - 1) * carpetWidth - matchesCount;
        // number of desiredMatches. rounds up if is odd number under the logic that for
        // most carpets it will be easier to have a non-match than a match later on. 0
        // is baseline as can not have less than 0 matches
        int desiredMatches = Math.max(0, (carpetWidth + 1) / 2 + nonMatches - matchesCount);
        desiredMatches = Math.min(desiredMatches, carpetWidth);
        // System.out.println("");
        // System.out.println("mathes: " + matchesCount + " non-matches: " + nonMatches
        // + " desired matches: "+ desiredMatches);
        for (int matchDeviation = 1; matchDeviation <= 2 * carpetWidth; matchDeviation++) {
            // System.out.print(" desired matches: "+ desiredMatches);
            if (desiredMatches >= 0 && desiredMatches <= carpetWidth) {
                // for each carpet in the option
                for (int i = 0; i < carpets.size(); i++) {
                    // if it hasnt already been used
                    if (!usedCarpets[i]) {
                        // check number of matches without flipping
                        int matches = matchCount(previousCarpet, i, previousFlipped);
                        if (matches == desiredMatches) {
                            // usedCarpets[i] = true;
                            // finalCarpet[count] = i;
                            // flippedCarpet[count] = previousFlipped;
                            return greedyBalMatch(i, false, count + 1, matchesCount + matches);
                        }
                        matches = matchCount(previousCarpet, i, !previousFlipped);
                        if (matches == desiredMatches) {
                            // usedCarpets[i] = true;
                            // finalCarpet[count] = i;
                            // flippedCarpet[count] = !previousFlipped;
                            return greedyBalMatch(i, true, count + 1, matchesCount + matches);
                        }
                    }
                }

            }
            if (matchDeviation % 2 == 1) {
                desiredMatches -= matchDeviation;
            } else {
                desiredMatches += matchDeviation;
            }

        }
        return 0;
    }

    /**
     * find the maxmum number of possible matches using a purned tree method
     * 
     * @param previousCarpet  index of the previous carpet
     * @param previousFlipped true indicates that the previous carpet was used
     *                        flipped
     * @param count           current count of carpets used.
     * @param matchCount      count of current number of matches
     * @param baseline        base line number to beat. program will avoid
     *                        calculating any solution that will not lead to such a
     *                        solution
     * @return the maximum number of matches possible.
     */
    // not 100% sure this works. was debugging for ages and wasnt working and then
    // not really sure what i changed and it works
    static int maxMatch(int previousCarpet, boolean previousFlipped, int count, int matchCount, int baseline) {
        if (count == carpetLength)
            return matchCount;
        // making this number as small as possible without eliminating any possible best
        // solution would be the best way to improve efficiency
        int maxPossibleMatchLeft = maxPossibleMatchLeft(count, previousCarpet);
        if (matchCount + maxPossibleMatchLeft < baseline) {
            return 0;
        }

        // int[] finalCarpetMaxCopy;
        // boolean[] flippedCarpetMaxCopy;

        int furtherMatch = 0;
        // for each carpet
        for (int i = 0; i < carpets.size(); i++) {
            // if havent already been used
            if (!usedCarpets[i]) {
                // check is carpet is better reversed or not reversed
                // if there is more matches in one oriantiation do not need to check other as
                // later on will be equivalent but reversed bur with less matches at current
                // carpet
                int match = matchCount(previousCarpet, i, previousFlipped);
                int reverseMatch = matchCount(previousCarpet, i, !previousFlipped);
                int mostMatch = Math.max(match, reverseMatch);
                boolean flipped = match < reverseMatch;

                // check recursivly and update the number of possible total matches left
                // carpetMatch[mostMatch]--;
                usedCarpets[i] = true;
                furtherMatch = maxMatch(i, flipped, count + 1, matchCount + mostMatch, baseline);
                usedCarpets[i] = false;
                // carpetMatch[mostMatch]++;

                // if recursive maxMatch found a better solution than baseline (current best
                // solution) save to the carpet array
                if (furtherMatch > baseline) {
                    baseline = furtherMatch;
                    finalCarpet[count] = i;
                    flippedCarpet[count] = flipped;
                    // finalCarpetMaxCopy = Arrays.copyOf(finalCarpet, finalCarpet.length);
                    // flippedCarpetMaxCopy = Arrays.copyOf(flippedCarpet, finalCarpet.length);
                }
            }
        }
        return baseline;

    }

    /**
     * Greedy algorithm to determine a baseline performance for the maximum match
     * carpet problem.
     * 
     * @param previousCarpet  index of the previous carpet
     * @param previousFlipped true indicates that the previous carpet was used
     *                        flipped
     * @param count           current count of carpets used.
     * @param matchCount      count of current number of matches
     * @return number of matches found
     */
    static int greedyMaxMatch(int previousCarpet, boolean previousFlipped, int count, int matchCount) {
        // adds carpets found as best in the previous recursion to the final carpets
        // is done before as greedyMaxMatch takes in the input of what carpet to use to
        // start
        usedCarpets[previousCarpet] = true;
        finalCarpet[count - 1] = previousCarpet;
        flippedCarpet[count - 1] = previousFlipped;
        // if carpet is full length finish
        if (count == carpetLength)
            return matchCount;
        // setting the null values of the variables
        int maxMatch = -1;
        int maxMatchCarpetIdx = -1;
        boolean maxMatchFlipped = false;

        // for each carpet in the option
        for (int i = 0; i < carpets.size(); i++) {
            // if it hasnt already been used
            if (!usedCarpets[i]) {
                // check number of matches without flipping
                int matches = matchCount(previousCarpet, i, previousFlipped);
                // if is more matches than anything seen before save index for adding later
                if (matches > maxMatch) {
                    maxMatch = matches;
                    maxMatchCarpetIdx = i;
                    maxMatchFlipped = false;
                }
                // check number of matches with flipping
                matches = matchCount(previousCarpet, i, !previousFlipped);
                if (matches > maxMatch) {
                    maxMatch = matches;
                    maxMatchCarpetIdx = i;
                    maxMatchFlipped = true;
                }
            }
        }

        return greedyMaxMatch(maxMatchCarpetIdx, maxMatchFlipped, count + 1, matchCount + maxMatch);

    }

    /**
     * goes through a pruned tree to see if there is a combination of carpets so
     * that there is no matches.
     * 
     * @param previousCarpet  the index of the previous carpet. -1 corresponds no
     *                        previous carpet
     * @param previousFlipped boolean indicates weather the previous carpet was
     *                        flipped
     * @param count           current length of the carpet created
     * @return true if there is a combination of carpets so that there is no matches
     *         false otherwise
     */
    static boolean noMatch(int previousCarpet, boolean previousFlipped, int count) {
        // if carpet is full length finish
        if (count == carpetLength)
            return true;

        // trys combining every carpet to the previousCarpet
        // will not check all as as soon as solution is found it is exited
        for (int i = 0; i < carpets.size(); i++) {
            // ignore if carpet is already being used
            if (!usedCarpets[i]) {
                // check if the i'th carpet matchs without flipping
                if (matchCount(previousCarpet, i, previousFlipped) == 0) {
                    // set the current carpet to check as used so that in the inner recursive loops
                    // program knows not to use number again
                    usedCarpets[i] = true;
                    // if it found a final solution
                    if (noMatch(i, false, count + 1)) {
                        // adds the index of the current carpet to finalCarpet
                        finalCarpet[count] = i;
                        flippedCarpet[count] = false;
                        return true;
                    } else
                        usedCarpets[i] = false;
                    // check if the i'th carpet matchs with flipping
                    // if matched without flipping but failed later on there is no need to run again
                    // so am using the else if
                } else if (matchCount(previousCarpet, i, !previousFlipped) == 0) {
                    usedCarpets[i] = true;
                    if (noMatch(i, true, count + 1)) {
                        finalCarpet[count] = i;
                        flippedCarpet[count] = true;
                        return true;
                    } else
                        usedCarpets[i] = false;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param count current number of carpets added to the finalCarpet array.
     * @return a maximum number of further matches possible.
     */
    static int maxPossibleMatchLeft(int count, int previousCarpet) {
        carpetMatch = new int[carpetWidth + 1];
        // sets up array containing the number of carpet matches are in the set
        for (int i = 0; i < carpets.size(); i++) {
            for (int j = i + 1; j < carpets.size(); j++) {
                if ((!usedCarpets[i] || i == previousCarpet) && (!usedCarpets[j] || j == previousCarpet)) {
                    carpetMatch[Math.max(matchCount(i, j, false), matchCount(i, j, true))]++;
                }
            }
        }

        // gets number of moves left
        int movesLeft = carpetLength - count;
        int carpetMatchIdx = carpetMatch.length - 1;
        int maxPossibleMatchLeft = 0;
        // for each index in carpetMatch starting from the end (largest number of
        // matches) add that number of matches to maxPossibleMatchLeft
        while (movesLeft > 0) {
            maxPossibleMatchLeft += Math.min(carpetMatch[carpetMatchIdx], movesLeft) * carpetMatchIdx;
            movesLeft -= carpetMatch[carpetMatchIdx];
            carpetMatchIdx--;

        }
        return maxPossibleMatchLeft;
    }

    /**
     * uses the indexs of 2 items in the carpets Arraylist and determines the number
     * of matches between the 2 carpets
     * 
     * @param carpetIdx1 index of one carpet to compare. returns 0 if is -1 to
     *                   indicate start of the carpet.
     * @param carpetIdx2 index of second carpet to compare
     * @param flipped    wheather or not to flip the carpets when comparing
     * @return the number of matching tiles
     */
    static int matchCount(int carpetIdx1, int carpetIdx2, boolean flipped) {
        if (carpetIdx1 == -1)
            return 0;
        // keeps carpetIdx1 less than carpetIdx2 so that below diagonal of the memo is
        // storing the flipped matches
        if (carpetIdx2 < carpetIdx1) {
            int temp = carpetIdx2;
            carpetIdx2 = carpetIdx1;
            carpetIdx2 = temp;
        }
        if (flipped && matchCountMemo[carpetIdx2][carpetIdx1] != null) {
            return matchCountMemo[carpetIdx2][carpetIdx1];
        } else if (!flipped && matchCountMemo[carpetIdx1][carpetIdx2] != null) {
            return matchCountMemo[carpetIdx1][carpetIdx2];
        }

        int matchCount = 0;
        char[] carpet1 = carpets.get(carpetIdx1).toCharArray();
        char[] carpet2 = carpets.get(carpetIdx2).toCharArray();
        if (flipped)
            reverse(carpet2);

        for (int i = 0; i < carpetWidth; i++) {
            if (carpet1[i] == carpet2[i]) {
                matchCount++;
            }
        }
        if (flipped) {
            matchCountMemo[carpetIdx2][carpetIdx1] = matchCount;
        } else {
            matchCountMemo[carpetIdx1][carpetIdx2] = matchCount;
        }
        return matchCount;
    }

    /**
     * reverses a char array
     * 
     * @param array char array to reverse
     */
    static void reverse(char[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            char temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

    static void printCarpet() {
        // System.out.println(Arrays.toString(finalCarpet));
        // System.out.println(Arrays.toString(flippedCarpet));
        // for each carpet combination print out the carpet on each line
        for (int i = 0; i < carpetLength; i++) {
            String carpet = carpets.get(finalCarpet[i]);
            // flip the carpet if needed
            if (flippedCarpet[i]) {
                char[] temp = carpet.toCharArray();
                reverse(temp);
                carpet = String.valueOf(temp);
            }
            System.out.println(carpet);
        }
    }

    static void setupCarpets() {
        usedCarpets = new boolean[carpets.size()];
        finalCarpet = new int[carpetLength];
        flippedCarpet = new boolean[carpetLength];
        carpetWidth = carpets.get(0).length();
        carpetMatch = new int[carpetWidth + 1];
        matchCountMemo = new Integer[carpets.size()][carpets.size()];
    }

}
