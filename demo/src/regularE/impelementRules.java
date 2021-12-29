package regularE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//import com.company.AcMachine;
//import com.company.Rule;

import java.util.List;
import java.util.Scanner;


//定义Rule类
public class impelementRules extends Rule {

    //全局变量，表示输入的正规文法行数
    int n;

    String[] s1 = {"+", "-", "*", "/", "=", ">", "<", "(", ")", ";", ","};
    String[] s2 = {":=", "<>", ">=", "<="};
    String[] ss = {"program", "begin", "end", "const"
            , "var", "while", "do", "if", "then", "else", "goto"};
    String[] sss = {"PROGRAM", "BEGIN", "END", "CONST",
            "VAR", "WHILE", "DO", "IF", "THEN", "ELSE", "GOTO"};
    List<Rule> rules = new ArrayList<>();
    //将关键字转换为小写形式
    Map<String, String> mpToLow = new HashMap<>();

    public ArrayList<String> Key = new ArrayList<>();
    public ArrayList<String> Boundary = new ArrayList<>();
    public ArrayList<Character> token_ids = new ArrayList<>();
    public ArrayList<String> res = new ArrayList<>();
    public ArrayList<String> tokenids = new ArrayList<>();
    public int resNum = 0;

//    void initMap() {
//        mpToLow.put("PROGRAM", "program");
//        mpToLow.put("BEGIN", "begin");
//        mpToLow.put("END", "end");
//        mpToLow.put("CONST", "const");
//        mpToLow.put("VAR", "var");
//        mpToLow.put("WHILE", "while");
//        mpToLow.put("DO", "do");
//        mpToLow.put("IF", "if");
//        mpToLow.put("THEN", "then");
//    }

    public boolean Init() throws IOException {
//        System.out.println("Init");
//        initMap();//完成Map的初始化
        System.out.println("输入正规文法：");
        int i, j = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        n = 0;
        while (!(line = br.readLine().trim()).equals("#")) {
//            String in = br.readLine().trim();
            Rule add = new Rule();
            add.str = line;
            rules.add(add);
            n++;
        }
        for (Rule rule : rules) {
            rule.left = rule.str.charAt(0);
            int keynum = 0;
            rule.right = rule.str.substring(3);

            for (int index = 1; index <= 11; ++index) {
                if (rule.right.contains(sss[index - 1])) {
                    rule.right = rule.right.replace(sss[index - 1], ss[index - 1]);
                    keynum++;
                }
            }
            if (keynum > 0) {
                for (int si = 0; si < rule.right.length(); si++) {
                    if (rule.right.charAt(si) >= 'A' && rule.right.charAt(si) <= 'Z') {
                        System.out.println("Error:出现了不在关键词列表里的关键词");
                        return false;
                    }
                }
            }
        }
        for (Rule rule : rules) {
            for (int si = 0; si < rule.right.length(); si++) {
                if (rule.right.charAt(si) >= 'A' && rule.right.charAt(si) <= 'Z') {
                    char problem = rule.right.charAt(si);
                    boolean isproblem = true;
                    for (Rule arule : rules) {
                        if (arule.left == problem)
                            isproblem = false;
                    }
                    if (isproblem = true) {
                        System.out.println("Error:出现了未定义的非终结符");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    //正规文法到正规式的转换1
    public void mix1() {
        //合并文法中形如S->aS S->bS为S->aS|bS;S->aB S->bB为S->aB|bB;S->a S->b为S->a|b ;即合并左部相同、右部相同的部分，以及右部单个字符
//        System.out.println("mix1");
        int i, j;
        for (i = 0; i < n - 1; i++)
            for (j = i + 1; j < n; j++) {
                if (rules.get(i).right.length() > 1 && rules.get(j).right.length() > 1) {
                    if ((rules.get(i).left == rules.get(j).left && rules.get(i).right.charAt(1) == rules.get(j).right.charAt(1)
                            && rules.get(i).left != '\0') ||
                            (rules.get(i).left == rules.get(j).left && rules.get(i).right.length() == 1 && rules.get(j).right.length() == 1
                                    && rules.get(i).left != '\0')) {
                        //并左部相同、右部相同的部分和单个字符
                        rules.get(i).right = rules.get(i).right.concat("|").concat(rules.get(j).right);
                        rules.get(j).left = '\0';
                        rules.get(j).right = "";

                    }
                }
            }
        mix2();
    }

    @Override
    public void mix2() {
        //合并Mix1中S->aS|bS为S->(a|b)S ;S->aB|bB为S->(a|b)B
//        System.out.println("mix2");
        int i, j, k;
        for (i = 0; i < n; i++) {
            if (rules.get(i).right.length() > 2 && rules.get(i).right.charAt(1) >= 'A' && rules.get(i).right.charAt(1) <= 'Z') {
                char[] tem = rules.get(i).right.toCharArray();
                //使用中间数组，将需保留内容存入其中，最后再存入rules中
                k = 0;
                tem[k++] = '(';
                String a = new String("(");
                for (j = 0; j < rules.get(i).right.length(); j += 3) {
                    //存储需保留的值
                    tem[k] = rules.get(i).right.charAt(j);
                    tem[k + 1] = '|';
                    k = k + 2;
                }
                tem[k - 1] = ')';
                tem[k + 1] = rules.get(i).right.charAt(1);
                rules.get(i).right = "";
                rules.get(i).right = Arrays.toString(tem);
            }
        }
        mix3();
    }


    @Override
    public void mix3() {
        //合并类似S->a|b S->(a|b)S 为S->(a|b)*(a|b) ,采用右递归
//        System.out.println("mix3");
        int i, j, temp1;
        for (i = 0; i < n; i++) {
            temp1 = 0;
            for (j = 0; j < rules.get(i).right.length(); j++) {
                if (Character.isUpperCase(rules.get(i).right.charAt(j))) {
                    //先寻找S->a|b,即右边无非终结符
                    temp1 = 1;
                }
            }
            if (temp1 == 0) {
                //然后再寻找与其左部有相同非终结符且右部也相同非终结符的文法
                for (j = 0; j < n; j++) {
                    int length = rules.get(j).right.length();
                    if (length > 0) {
                        if (rules.get(j).right.charAt(length - 1) == rules.get(j).left
                                && rules.get(j).right.charAt(length - 1) == rules.get(i).left && rules.get(j).left != '\0') {
                            StringBuilder tj = new StringBuilder(rules.get(j).right);
                            StringBuilder ti = new StringBuilder(rules.get(i).right);
                            //将两个文法进行合并
                            tj.setCharAt(length - 1, '\0');
                            if (ti.length() == 1 | ti.length() == 2) {
                                tj.append("*");
                                tj.append(ti);

                            }
                            if (ti.length() > 2) {
                                tj.append("*(");
                                tj.append(ti);
                                tj.append(")");
                            }
                            rules.get(i).left = '\0';
                            rules.get(j).right = tj.toString();
                            rules.get(i).right = "";
                        }
                    }
                }
            }
        }
        mix4();
    }

    @Override
    public void mix4() {
        //合并类型S->aA A->(b|a) 为S->a(b|a)
//        System.out.println("mix4");
        int i, j, temp1, k;
        for (i = 0; i < n; i++) {
            temp1 = 0;
            for (j = 0; j < rules.get(i).right.length(); j++) { //先寻找S->a|b,即右边无非终结符
                if (Character.isUpperCase(rules.get(i).right.charAt(j)))
                    temp1 = 1;
            }
            if (temp1 == 0) { //然后再寻找右部与其左部有相同非终结符但左部不相同非终结符的文法
                for (j = 0; j < n; j++) {
                    int length = rules.get(j).right.length();
                    if (length > 0) {
                        if (rules.get(j).right.charAt(length - 1) == rules.get(i).left && rules.get(j).left != '\0') { //进行合并
                            StringBuilder tj = new StringBuilder(rules.get(j).right);
                            StringBuilder ti = new StringBuilder(rules.get(i).right);
                            tj.setCharAt(length - 1, '\0');
                            k = 0;
                            for (int m = 0; m < ti.length(); m++) //判断操作符是否都为乘法
                                if (ti.charAt(m) == '*' | ti.charAt(m) == '|')
                                    k = 1;
                            if (ti.length() > 2 && k == 1) { //若都为乘法，则在合并时可以不用加括号
                                tj.append("(");
                                tj.append(ti);
                                tj.append(")");
                            } else
                                tj.append(ti);
                            rules.get(i).left = '\0';
                            rules.get(j).right = tj.toString();
                            rules.get(i).right = "";
                        }
                    }
                }
            }
        }
        mix();
    }

    @Override
    public void mix() {
//        System.out.println("mix");
        //进行最后的判断和循环
        int i, j, temp1 = 0;
        for (i = 0; i < n; i++) { //判断是否所有文法右边均不含非终结符
            int length = rules.get(i).right.length();
//            System.out.println(rules.get(i).right);
            if (length > 0) {
                for (j = 0; j < length; j++) {
                    if (Character.isUpperCase(rules.get(i).right.charAt(j)))
                        temp1 = 1;
                }
            }
        }
        if (temp1 == 0) { //若都不含非终结符，进行合并输出
            for (i = 0; i < n - 1; i++) {
                for (j = i + 1; j < n; j++) {
                    if (rules.get(i).left == rules.get(j).left && rules.get(i).left != '\0') {
                        rules.get(i).right = rules.get(i).right.concat("|").concat(rules.get(j).right);
                        rules.get(j).left = '\0';
                        rules.get(j).right = "";
                    }
                }
            }
        }
        if (temp1 != 0) //若含非终结符，调至Mix1循环执行
            mix1();
        else {
            for (i = 0; i < n; i++) { //输出正规式
                if (rules.get(i).right != "") {
                    //change

                    for (int index = 1; index <= 11; ++index) {
                        if (rules.get(i).right.contains(ss[index - 1])) {
                            rules.get(i).right = rules.get(i).right.replace(ss[index - 1], sss[index - 1]);
                            Key.add(sss[index - 1]);
                        }
                    }
                    if (!rules.get(i).right.contains("d") && !rules.get(i).right.contains("l")) {
                        String tempBR = rules.get(i).right;
                        for (int index = 1; index <= 4; ++index) {
                            if (tempBR.contains(s2[index - 1])) {
                                Boundary.add(s2[index - 1]);
                                tempBR = tempBR.replace(s2[index - 1], "0");
                            }
                        }
                        for (int index = 1; index <= 11; ++index) {
                            if (tempBR.contains(s1[index - 1])) {
                                Boundary.add(s1[index - 1]);
                            }
                        }

                    }
                    if (rules.get(i).right.contains("d") || rules.get(i).right.contains("l")) {
                        if ((rules.get(i).right.contains("d"))) {
                            rules.get(i).right = rules.get(i).right.replace("d", "(0|1|2|3|4|5|6|7|8|9)");
                        }
                        if ((rules.get(i).right.contains("l"))) {
                            rules.get(i).right = rules.get(i).right.replace("l", "(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|_)");
                        }
//                        System.out.println(rules.get(i).left + "->" + rules.get(i).right);
                        res.add(rules.get(i).right);
                        token_ids.add(rules.get(i).left);
                        resNum++;
                    }
                }

            }
        }
        for (Character str : token_ids) {
            String xx = Character.toString(str);
            tokenids.add(xx);
        }
    }
}
