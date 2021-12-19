package DFA_minimisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import RE2DFA.Beans.DFA;

public class test {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        String str=input.nextLine();
        String[] code=str.split(" ");
        System.out.println(str);
        String[] type_1=new String[1];
        type_1[0]="VAR";
        generateCode t=new generateCode();
        List<Integer> K=new ArrayList<>();
        K.add(0);
        K.add(1);
        K.add(2);
//        K.add(3);
//        K.add(4);
//        K.add(5);
//        K.add(6);
        char l[]=new char[2];
        l[0]='d';
        l[1]='l';
        int f[][]=new int[][]{{1,-1},{2,2},{2,2}};
        List<Integer> z=new ArrayList<>();
        z.add(1);
        z.add(2);
        DFA dfa=new DFA(K,l,f,0,z);
        minDFA mDFA= new minDFA();
        //设置字母对应的索引
        mDFA.setup(l);
        //最小化DFA
        DFA nDFA=mDFA.minDFA(dfa);
        //生成状态转移图矩阵
        generateDiagram g=new generateDiagram();
        g.generateDiagram(nDFA);
        //输入代码测试，输出二元式
        t.generate_code("V",code,type_1,nDFA);
    }


}
