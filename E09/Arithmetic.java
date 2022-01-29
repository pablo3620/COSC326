import java.util.Arrays;
import java.util.Scanner;

public class Arithmetic {

    public static int targetValue;
    private static int[] nums;
    private static char[] ops;
    private static int order;

    // private int num;
    // private Arithmetic nextArithmetic;
    // private char op;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            try {
                if (line.substring(0, 2).equals("N "))
                    order = 1;
                else if (line.substring(0, 2).equals("L "))
                    order = 0;
                else
                    throw new Exception();

                nums = Arrays.stream(line.substring(2).trim().split(" ")).filter(x -> !x.equals(""))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                if (nums.length < 2) {
                    throw new Exception();
                }
                targetValue = nums[0];
                ops = new char[nums.length - 1];
                ops[0] = '=';
                // Arithmetic arith = new Arithmetic();
                if (findAnswer(2, nums[1], nums[1])) {
                    System.out.print(line.substring(0, 2));
                    for (int i = 0; i < ops.length; i++) {
                        System.out.print(nums[i] + " ");
                        System.out.print(ops[i] + " ");
                    }
                    System.out.print(nums[nums.length - 1] + "\n");
                } else {
                    System.out.println(line + " impossible");
                }

            } catch (Exception e) {
                System.out.println(line + " Invalid");
            }
        }
        in.close();

    }

    // static boolean findAnswer(int pos, int value) {
    // if (value > targetValue)
    // return false;
    // if (pos == nums.length) {
    // return value == targetValue;
    // }
    // char op = '*';
    // if (findAnswer(pos + 1, value * nums[pos])) {
    // ops[pos - 1] = op;
    // return true;
    // }
    // op = '+';
    // if (findAnswer(pos + 1, value + nums[pos])) {
    // ops[pos - 1] = op;
    // return true;
    // }
    // return false;
    // }
    // findAnswer(pos + 1, value + (int) Math.round(Math.pow(timesNum, order)) *
    // (nums[pos] - order), timesNum * nums[pos]))
    // (findAnswer(pos + 1, value + timesNum * (nums[pos] - 1), timesNum *
    // nums[pos]))

    static boolean findAnswer(int pos, int value, int timesNum) {
        if (value > targetValue)
            return false;
        if (pos == nums.length) {
            return value == targetValue;
        }
        char op = '*';
        if (findAnswer(pos + 1, value + (timesNum * order + value * (1 - order)) * (nums[pos] - 1),
                timesNum * nums[pos])) {
            ops[pos - 1] = op;
            return true;
        }
        op = '+';
        if (findAnswer(pos + 1, value + nums[pos], nums[pos])) {
            ops[pos - 1] = op;
            return true;
        }
        return false;
    }

}
