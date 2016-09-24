import java.io.*;
import java.util.*;

public class Suudoku {
    int[][] finalAns;

    Suudoku(int[][] data) {
        slove(data, 0, 0);
        finalAns = data;
    }

    /**
     * 引数の回答が数独の条件を満たしているかちぇっくする
     *
     * @param ans
     * @return
     */
    boolean isCheck(int x, int y, int[][] ans) {
        int ansVal = ans[x][y];


        for (int j = 0; j < 9; j++) {
            //横
            if (y != j && ansVal == ans[x][j])
                return false;
            //縦
            if (x != j && ansVal == ans[j][y])
                return false;
        }


        //各グループ
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int groupX = i + x / 3 * 3;
                int groupY = j + y / 3 * 3;
                if ((x != groupX || y != groupY) && ansVal == ans[groupX][groupY]) {
                    return false;
                }
            }
        }

        return true;
    }

    boolean slove(int[][] ans, int x, int y) {

        if (y == 9)
            return true;

        if (x == 9)
            return slove(ans, 0, y + 1);

        if (ans[x][y] != 0)
            return slove(ans, x + 1, y);

        for (int i = 1; i < 10; i++) {
            ans[x][y] = i;

            if (isCheck(x, y, ans) && slove(ans, x + 1, y))
                    return true;
        }

        ans[x][y] = 0;
        return false;
    }


    static void printData(int[][] data) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.print(" -----------------------------");
                System.out.println("");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("|");
                }
                if (data[i][j] == 0) {
                    System.out.print("( )");
                } else {
                    System.out.print(" " + data[i][j] + " ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(" -----------------------------");
    }

    public static void main(String[] args) {
        // データの読み込み
        int[][] data = new int[9][9];
        if (args.length != 1) {
            System.err.println("need 1 argument");
            System.exit(1);    // 異常終了
        }

        BufferedReader br;
        try {

            br = new BufferedReader(new FileReader(args[0]));

            for (int i = 0; i < 9; i++) {
                String tmp = br.readLine();
                StringTokenizer stn = new StringTokenizer(tmp, " ", false);
                for (int j = 0; j < 9; j++) {
                    data[i][j] = Integer.parseInt(stn.nextToken());
                }
            }
        } catch (Exception e) {
            System.err.println("" + e);
            System.exit(1);    // 異常終了
        }

        // 問題表示
        System.out.println("Start");
        printData(data);

        System.out.println("Ans");
        int[][] result = new Suudoku(data).finalAns;
        printData(result);
    }
}