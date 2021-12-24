package RE2DFA.Beans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RegexExpression {

    private String s;

    private List<String> re;

    private boolean debug = false;

    public List<String> getRe() {
        return re;
    }

    public void setRe(List<String> re) {
        this.re = re;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setDebug(Boolean debug){
        this.debug = debug;
    }

    public RegexExpression(){}

    public RegexExpression(String input){
        s = input;
        List<String> list = new ArrayList<>();//表达式中缀
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
        re = infix2postfix(list);
        if(debug){
            System.out.println(re);
        }
    }

    // 中缀表达式转后缀
    private static List<String> infix2postfix(List<String> infix){
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
