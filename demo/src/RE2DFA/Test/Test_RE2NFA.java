package RE2DFA.Test;

import RE2DFA.Beans.NFA;
import RE2DFA.Beans.RegexExpression;
import RE2DFA.Service.RE2NFA;
import RE2DFA.Utils.DisplayUtils;
import RE2DFA.Utils.EnterUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test_RE2NFA {
    public static void main(String[] args) throws IOException {
        RegexExpression re = new RegexExpression();

        System.out.print("请输入正规式");            EnterUtils.enterRE(re);
        System.out.println(re.getRe());

        NFA nfa = new RE2NFA().get(re.getRe());

        System.out.print("NFA状态集：");            DisplayUtils.displayK(nfa);
        System.out.print("NFA字母表：");            DisplayUtils.displayLetters(nfa);
        System.out.print("NFA状态转换函数：");       DisplayUtils.displayF(nfa);
        System.out.print("NFA唯一初态：");          DisplayUtils.displayS(nfa);
        System.out.print("NFA终态集：");            DisplayUtils.displayZ(nfa);
    }
}
