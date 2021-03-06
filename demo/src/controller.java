import DFA_minimisation.generateCode;
import DFA_minimisation.generateDiagram;
import DFA_minimisation.minDFA;
import RE2DFA.Beans.DFA;
import RE2DFA.Beans.NFA;
import RE2DFA.Beans.RegexExpression;
import RE2DFA.Service.NFA2DFA;
import RE2DFA.Service.RE2NFA;
import RE2DFA.Utils.DisplayUtils;
import regularE.Rule;
import regularE.impelementRules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class controller {
    public List<String> divide(String[] code, String[] Key) {
        List<String> c = new ArrayList<>();
        boolean isContainPunc = false;
        for (int i = 0; i < code.length; i++) {
            int first = 0;
            int last = 0;
            for (int j = 0; j < Key.length; j++) {
                if (code[i].contains(Key[j])) {
                    isContainPunc = true;
                    break;
                }
            }
            //System.out.println("code"+i+":"+isContainPunc);
            if(isContainPunc)
            {
                for(int m=0;m<code[i].length();m++)
                {
                    if((code[i].charAt(m)>='a'&&code[i].charAt(m)<='z')||(code[i].charAt(m)>='A'&&code[i].charAt(m)<='Z')||(code[i].charAt(m)>='0'&&code[i].charAt(m)<='9')||code[i].charAt(m)=='_')
                    {
//=======
//            System.out.println("code" + i + ":" + isContainPunc);
//            if (isContainPunc) {
//                for (int m = 0; m < code[i].length(); m++) {
//                    if ((code[i].charAt(m) >= 'a' && code[i].charAt(m) <= 'z') || (code[i].charAt(m) >= 'A' && code[i].charAt(m) <= 'Z') || (code[i].charAt(m) >= '0' && code[i].charAt(m) <= '9') || code[i].charAt(m) == '_') {
//>>>>>>> fb52ece8b5cc846f9dcffdbc4d5ccef934d36dd5
                        last++;
                        if (m == code[i].length() - 1) {
                            c.add(code[i].substring(first, last));
                        }
                    } else {
                        String temp = code[i].substring(first, last);
                        c.add(temp);
                        first = m + 1;
                        if ((m + 1 < code[i].length()) && ((code[i].charAt(m) == ':' && code[i].charAt(m + 1) == '=') || (code[i].charAt(m) == '>' && code[i].charAt(m + 1) == '=') || (code[i].charAt(m) == '<' && code[i].charAt(m + 1) == '=') || (code[i].charAt(m) == '<' && code[i].charAt(m + 1) == '>'))) {
                            c.add(code[i].substring(m, m + 2));
                            m++;
                            first++;
                            last += 2;
                        } else {
                            for (int l = 0; l < Key.length; l++) {
                                if (Key[l].contains(String.valueOf(code[i].charAt(m)))) {
                                    c.add(code[i].substring(m, m + 1));
                                    last++;
                                    break;
                                }
                            }
                        }

                    }
                }
            } else {
                c.add(code[i]);
            }
        }
        return c;
    }

    public static void main(String[] args) throws IOException {
//        //?????????
//        String Key[]=new String[13];
//        //??????
//        String Boundary[]=new String[10];
//        //DFA??????????????????
//        int n=1;
//        //?????????
//        String[] res=new String[n];
//        //??????????????????token-id
//        String[] token_ids=new String[n];

        impelementRules session = new impelementRules();
        session.Init();
        session.mix1();
        int n = session.resNum;
        String[] Key = (String[]) session.Key.toArray(new String[0]); //?????????
        String[] Boundary = (String[]) session.Boundary.toArray(new String[0]); //??????
        String[] res = (String[]) session.res.toArray(new String[0]); //?????????
//        System.out.println(res[0]);
        String[] token_ids = (String[]) session.tokenids.toArray(new String[0]); //?????????token
        for (int i = 0; i < token_ids.length; i++) {
            System.out.print(token_ids[i]);
            System.out.print(':');
            System.out.println(res[i]);
        }

        //??????????????????nfa,dfa,????????????dfa
        NFA[] nfas = new NFA[n];
        DFA[] dfas = new DFA[n];
        DFA[] ndfas = new DFA[n];

//        System.out.println("??????????????????");//?????????????????????
        System.out.println("???????????????????????????");

//<<<<<<< HEAD
//        for(int i=0;i<n;i++)
//        {
//            int it=i+1;
//            System.out.println("???"+it+"??????");
//=======
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        String str = "";
        while (!(line = br.readLine().trim()).equals("#")) {
//            String in = br.readLine().trim();
            str += line;
            str+=" ";
        }
//        System.out.println(str);
        String[] code = str.split(" ");

        for (int i = 0; i < n; i++) {
            System.out.println("?????????"+token_ids[i]+"?????????????????????????????????");
            RegexExpression re = new RegexExpression(res[i]);
            List<String> t = re.getRe();
            nfas[i] = new RE2NFA().get(re.getRe());
            System.out.println("??????????????????NFA???");
            System.out.print("NFA????????????");            DisplayUtils.displayK(nfas[i]);
            System.out.print("NFA????????????");            DisplayUtils.displayLetters(nfas[i]);
            System.out.print("NFA?????????????????????");       DisplayUtils.displayF(nfas[i]);
            System.out.print("NFA???????????????");          DisplayUtils.displayS(nfas[i]);
            System.out.print("NFA????????????");            DisplayUtils.displayZ(nfas[i]);
            System.out.println();
            dfas[i] = new NFA2DFA().definite(nfas[i]);
            System.out.println("NFA?????????DFA???");
            System.out.print("DFA????????????");            DisplayUtils.displayK(dfas[i]);
            System.out.print("DFA????????????");            DisplayUtils.displayLetters(dfas[i]);
            System.out.print("DFA?????????????????????");       DisplayUtils.displayF(dfas[i]);
            System.out.print("DFA???????????????");          DisplayUtils.displayS(dfas[i]);
            System.out.print("DFA????????????");            DisplayUtils.displayZ(dfas[i]);
            System.out.println();
            dfas[i].transF();
            minDFA mDFA = new minDFA();
            //???????????????????????????
            mDFA.setup(dfas[i].getLetters());
            //?????????DFA
            ndfas[i] = mDFA.minDFA(dfas[i]);
            generateDiagram g = new generateDiagram();
            System.out.println("????????????DFA???????????????????????????");
            g.generateDiagram(ndfas[i]);
            System.out.println();
        }
        generateCode t = new generateCode();
        controller con = new controller();
        //????????????
        List<String> c=con.divide(code,Boundary);
        for(int i=0;i<c.size();i++) {
            if (c.get(i).length() == 0) {
                c.remove(i);
                i--;
            }
        }
//        for (int i = 0; i < c.size(); i++) {
//            System.out.println(c.get(i));
//        }
//        for(int i=0;i<c.size();i++)
//        {
//            System.out.println(c.get(i));
//        }
        //??????
        for (int i = 0; i < c.size(); i++) {
            for (int j = 0; j < n; j++) {
                String info = t.gCode(token_ids[j], c.get(i), Key, Boundary, ndfas[j]);
                if (info == "error" && j != n - 1) {
                    continue;
                } else if (info == "error" && j == n - 1) {
                    System.out.println(info);
                } else {
                    System.out.println(info);
                    break;
                }
            }
        }

    }
}
