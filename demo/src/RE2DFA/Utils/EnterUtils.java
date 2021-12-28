package RE2DFA.Utils;

import RE2DFA.Beans.DFA;
import RE2DFA.Beans.NFA;
import RE2DFA.Beans.Operator;
import RE2DFA.Beans.RegexExpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * EnterUtils工具类————用于从外部获取输入，然后set给传入的nfa对象
 */
public class EnterUtils {

    /**
     * 状态集的个数（转换函数f也要用到）
     * 这个n被所有nfa类共享————为了防止出错，enterF一定要写在enterK的后面，才能保证初始化f时使用正确的n
     */
    private static int n;

    // 输入状态集
    public static void enterK(NFA nfa) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> list = new ArrayList<>();
        String[] strings = br.readLine().split("");
        for(String s : strings){
            list.add(Integer.parseInt(s));
        }
        n = list.size();
        nfa.setK(list);
    }

    // 输入字母表
    public static void enterLetters(NFA nfa) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] letters = br.readLine().toCharArray();
        nfa.setLetters(letters);
    }

    // 输入转换函数
    public  static void enterF(NFA nfa) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[][] f = new String[n][n];
        for(String[] arr : f){
            Arrays.fill(arr, "");
        }
        for(int i = 0; i < f.length; i++){
            f[i][i] = "ε";
        }
        String line = "";
        while(!(line = br.readLine().trim()).equals("end")){
            String[] arr = line.split("");
            f[Integer.parseInt(arr[0])][Integer.parseInt(arr[2])] += arr[1];
        }
        nfa.setF(f);
    }

    // 输入初态集
    public static void enterS(NFA nfa) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> list = new ArrayList<>();
        String[] strings = br.readLine().split("");
        for(String s : strings){
            list.add(Integer.parseInt(s));
        }
        nfa.setS(list);
    }

    // 输入终态集
    public static void enterZ(NFA nfa) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> list = new ArrayList<>();
        String[] strings = br.readLine().split("");
        for (String s : strings){
            list.add(Integer.parseInt(s));
        }
        nfa.setZ(list);
    }



    // 输入状态集
    public static void enterK(DFA dfa) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> list = new ArrayList<>();
        String[] strings = br.readLine().split("");
        for(String s : strings){
            list.add(Integer.parseInt(s));
        }
        n = list.size();
        dfa.setK(list);
    }

    // 输入字母表
    public static void enterLetters(DFA dfa) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] letters = br.readLine().toCharArray();
        dfa.setLetters(letters);
    }

    // 输入转换函数
    public  static void enterF(DFA dfa) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[][] f = new String[n][n];
        for(String[] arr : f){
            Arrays.fill(arr, "");
        }
        String line = "";
        while(!(line = br.readLine().trim()).equals("end")){
            String[] arr = line.split("");
            f[Integer.parseInt(arr[0])][Integer.parseInt(arr[2])] += arr[1];
        }
        dfa.setFv(f);
    }

    // 输入初态集
    public static void enterS(DFA dfa) throws IOException{
        Scanner scanner = new Scanner(System.in);
        int S = scanner.nextInt();
        dfa.setS(S);
    }

    // 输入终态集
    public static void enterZ(DFA dfa) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> list = new ArrayList<>();
        String[] strings = br.readLine().split("");
        for (String s : strings){
            list.add(Integer.parseInt(s));
        }
        dfa.setZ(list);
    }

    // 输入正则表达式
    public static void enterRE(RegexExpression re) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> list = new ArrayList<>();//表达式中缀
        String input = br.readLine();
        String[] strings = input.split("");
        for(String s : strings){
            // “&”的有六种情况，分别为<a a>  ,  <* a>  ,  <） a>  ,  <a （>  ,  <* （>  ,  <） （>            <a,*,|,(,)>
            if(!list.isEmpty()){
                String last = list.get(list.size()-1);
                if(s.matches("\\w")){
                    if(last.matches("\\w") || last.equals("*") || last.equals(")")){
                        list.add("^");
                    }
                } else if(s.equals("(")){
                    if(last.equals(")") || last.equals("*") || last.matches("\\w")){
                        list.add("^");
                    }
                }
            }
            if(!list.isEmpty() && s.matches("\\w") && list.get(list.size()-1).matches("\\w")){
                list.add("^");
            }
            list.add(s);
        }
        System.out.println(list);
        re.setRe(infix2postfix(list));
    }

    // 中缀表达式转后缀
    public static List<String> infix2postfix(List<String> infix){
        Stack<String> opr = new Stack<>(); // 符号栈
        List<String> postfix = new ArrayList<>();
        for(String s : infix){
            if(s.matches("\\w")){ // 匹配字母或数字
                postfix.add(s);
            } else if (s.equals("(")) {
                opr.push(s);
            } else if (s.equals(")")) {
                while (true) {
                    if (opr.peek().equals("(")){
                        break;
                    }
                    postfix.add(opr.pop());
                }
                opr.pop();
            } else {
                while (opr.size() != 0 && (Operator.getValue(opr.peek()) >= Operator.getValue(s))){
                    postfix.add(opr.pop());
                }
                opr.push(s);
            }
        }
        while (opr.size()!=0){
            postfix.add(opr.pop());
        }
        return postfix;
    }
}
