package RE2DFA.Beans;

import java.util.List;

public class DFA {

    private List<Integer> K;    // 状态集

    private char[] letters;     // 字母表

    private String[][] fv;       // 转换函数

    private int[][] f;      //  转换函数

    private int S;              // 唯一初态

    private List<Integer> Z;    // 终态集

    public DFA(List<Integer> k, char[] letters, int[][] f, int s, List<Integer> z) {
        K = k;
        this.letters = letters;
        this.f = f;
        S = s;
        Z = z;
    }

    public DFA(){}

    public List<Integer> getK() {
        return K;
    }

    public void setK(List<Integer> k) {
        K = k;
    }

    public char[] getLetters() {
        return letters;
    }

    public void setLetters(char[] letters) {
        this.letters = letters;
    }

    public String[][] getFv() {
        return fv;
    }

    public void setFv(String[][] fv) {
        this.fv = fv;
    }

    public int[][] getF() {
        return f;
    }

    public void setF(int[][] f) {
        this.f = f;
    }

    public int getS() {
        return S;
    }

    public void setS(int s) {
        S = s;
    }

    public List<Integer> getZ() {
        return Z;
    }

    public void setZ(List<Integer> z) {
        Z = z;
    }

    public void transF(){
        f = new int[K.size()][letters.length];
        for(int i = 0; i < K.size(); i++) {
            for(int j = 0; j < K.size(); j++ ) {
                char[] trans = fv[i][j].toCharArray();
                for (char c : trans) {
                    f[i][getLetterIndex(c)] = j;
                }
            }
        }
    }

    private Integer getLetterIndex(char c){
        for(int i = 0; i < letters.length; i++){
            if(c == letters[i]){
                return i;
            }
        }
        return -1;
    }
}
