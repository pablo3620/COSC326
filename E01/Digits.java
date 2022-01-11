import java.util.Scanner;

public class Digits {
    public static void main(String[] args) {
        System.out.println("enter question, base, and number to use");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        scan.close();
        char a = ' ';
        int b = 0, c = 0;
        Scanner tokens = new Scanner(input);
        try {
            a = tokens.next().toLowerCase().charAt(0);
            b = tokens.nextInt();
            c = tokens.nextInt();
            tokens.close();
            if (a != 'a' && a != 'b')
                throw new Exception();
        } catch (Exception e) {
            System.out.println("Bad line: " + input);
        }
        switch (a) {
            case 'a':
                int repeatedCount = 0;
                int maxRepeat = 0;
                int maxRepLocation = 0;
                for (int i = c - 1; i > b; i--) {
                    if (checkRepeated(i, b))
                        repeatedCount++;
                    else {
                        repeatedCount = 0;
                    }
                    if (repeatedCount > maxRepeat) {
                        maxRepeat = repeatedCount;
                        maxRepLocation = i;
                    }
                }
                System.out.println(maxRepLocation + " " + maxRepeat);
                break;
            case 'b':
                break;
        }

    }

    private static boolean checkRepeated(int num, int base) {
        boolean[] usedDigits = new boolean[base];
        int digit;
        while (num > 0) {
            digit = num % base;
            num = num / base;
            if (usedDigits[digit]) {
                return true;
            }
            usedDigits[digit] = true;
        }
        return false;
    }
}