package regularE;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        System.out.println("hello world");
        impelementRules session = new impelementRules();
        session.Init();
        session.mix1();
        int num=session.resNum; //正规文法条数
        String[] Key = (String[]) session.Key.toArray(new String[0]); //关键字
        String[] Boundary = (String[]) session.Boundary.toArray(new String[0]); //算符
        String[] res = (String[]) session.res.toArray(new String[0]); //正规式
        String[] token_ids = (String[]) session.tokenids.toArray(new String[0]); //正规式token
    }
}
