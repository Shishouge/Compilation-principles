package RE2DFA.Test;

import DFA_minimisation.generateCode;
import DFA_minimisation.generateDiagram;
import DFA_minimisation.minDFA;
import RE2DFA.Beans.DFA;
import RE2DFA.Beans.NFA;
import RE2DFA.Beans.RegexExpression;
import RE2DFA.Service.NFA2DFA;
import RE2DFA.Service.RE2NFA;
import RE2DFA.Utils.DisplayUtils;
import RE2DFA.Utils.EnterUtils;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        RegexExpression re = new RegexExpression();

        System.out.print("请输入正规式：");
        EnterUtils.enterRE(re);
        System.out.println(re.getRe());

        NFA nfa = new RE2NFA().get(re.getRe());

        System.out.print("NFA状态集：");            DisplayUtils.displayK(nfa);
        System.out.print("NFA字母表：");            DisplayUtils.displayLetters(nfa);
        System.out.print("NFA状态转换函数：");       DisplayUtils.displayF(nfa);
        System.out.print("NFA唯一初态：");          DisplayUtils.displayS(nfa);
        System.out.print("NFA终态集：");            DisplayUtils.displayZ(nfa);

        DFA dfa = new NFA2DFA().definite(nfa);
        dfa.transF();

        System.out.print("DFA状态集：");            DisplayUtils.displayK(dfa);
        System.out.print("DFA字母表：");            DisplayUtils.displayLetters(dfa);
        System.out.print("DFA状态转换函数：");       DisplayUtils.displayF(dfa);
        System.out.print("DFA唯一初态：");          DisplayUtils.displayS(dfa);
        System.out.print("DFA终态集：");            DisplayUtils.displayZ(dfa);

        int[][] nf=dfa.getF();
        System.out.println("未化简的dfa状态转移函数");

        for(int i=0;i<nf.length;i++)
        {
            for(int j=0;j<nf[0].length;j++)
            {
                System.out.print(nf[i][j]+" ");
            }
            System.out.println();
        }

        minDFA mDFA = new minDFA();
        //设置字母对应的索引
        mDFA.setup(dfa.getLetters());
        //最小化DFA
        DFA nDFA=mDFA.minDFA(dfa);
        generateDiagram g=new generateDiagram();
        System.out.println("化简后的DFA的状态转移图矩阵：");
        g.generateDiagram(nDFA);
        System.out.println("请输入要识别的代码：");
        Scanner input=new Scanner(System.in);
        String str=input.nextLine();
        String[] type_1=new String[3];
        type_1[0]="VAR";
        type_1[1]=",";
        type_1[2]=";";
        String[] code=str.split(" ");
        generateCode t=new generateCode();
        for(int i=0;i< code.length;i++)
        {
            System.out.println(code[i]+" ");
        }
        t.generate_code("V",code,type_1,nDFA);

//        new minDFA().minDFA(dfa);
    }
}
