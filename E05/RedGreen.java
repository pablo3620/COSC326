import java.util.Scanner;

public class RedGreen {

    public static char[] colMemoize;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            Scanner tokens = new Scanner(line);
            try {
                String colors = getRedGreen(tokens.nextInt(), tokens.nextInt());
                if (tokens.hasNext()) {
                    tokens.close();
                    throw new Exception();
                }
                System.out.println(line + " " + colors);
            } catch (Exception e) {
                System.out.println("Bad line: " + line);
            }
        }
        in.close();
    }

    static String getRedGreen(int start, int end) throws Exception {
        if (end < start || start < 1) {
            throw new Exception();
        }
        String colors = "";
        colMemoize = new char[end + 1];
        colMemoize[0] = 'G';
        for (int i = start; i <= end; i++) {
            colors = colors + calculateRedGreen(i);
        }
        return colors;
    }

    static char calculateRedGreen(int num) {
        if (colMemoize[num - 1] == 'G')
            return 'G';
        if (colMemoize[num - 1] == 'R')
            return 'R';
        int gCount = 0;
        int rCount = 0;

        int preFactor = num;
        for (int i = 2; i <= num/2+1; i++) {
            if (num / i != preFactor) {
                preFactor = num / i;
                if (calculateRedGreen(preFactor) == 'G')
                    gCount++;
                else
                    rCount++;
            }
        }
        if (gCount > rCount) {
            colMemoize[num - 1] = 'R';
            return 'R';
        } else {
            colMemoize[num - 1] = 'G';
            return 'G';
        }
    }
}