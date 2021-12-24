package DFA_minimisation;

import RE2DFA.Beans.DFA;
import RE2DFA.Beans.NFA;
import RE2DFA.Beans.RegexExpression;
import RE2DFA.Service.NFA2DFA;
import RE2DFA.Service.RE2NFA;
import RE2DFA.Utils.EnterUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class generateCode {
    public static void main(String[] args) throws IOException {
        String token_id="V";String code="dl";
        String[] type2=new String[2];
        type2[0]="(";
        type2[1]=")";
        String[] type1=new String[1];
        type1[0]="IF";
        RegexExpression re = new RegexExpression();

        System.out.print("请输入正规式：");
        EnterUtils.enterRE(re);
        System.out.println(re.getRe());

        NFA nfa = new RE2NFA().get(re.getRe());
        DFA dfa = new NFA2DFA().definite(nfa);
        dfa.transF();
        minDFA mDFA = new minDFA();
        //设置字母对应的索引
        mDFA.setup(dfa.getLetters());
        //最小化DFA
        DFA nDFA=mDFA.minDFA(dfa);
        generateDiagram g=new generateDiagram();
        System.out.println("化简后的DFA的状态转移图矩阵：");
        g.generateDiagram(nDFA);
        generateCode ge=new generateCode();
        System.out.println(ge.gCode(token_id,code,type2,type1,nDFA));
    }
    public boolean isContained(char[] letters,char l)
    {
        boolean flag=false;
        for(int i=0;i<letters.length;i++)
        {
            if(l==letters[i])
            {
                flag=true;
                break;
            }
        }
        return flag;
    }

    public boolean isPunc(char a,String[] type)
    {
        for(int i=0;i<type.length;i++)
        {
            if(type[i].equals(a))
            {
                return true;
            }
        }
        return false;
    }

    //public static void main(String[] args) {
    public String gCode(String token_id,String code,String[] type2,String[] type1,DFA dfa){
        int curState=dfa.getS();
        boolean flag=false;
        int iterator=0;
        generateCode t=new generateCode();
        char[] letters= dfa.getLetters();
        int[][] newF=dfa.getF();
        List<Integer> states=dfa.getK();
        HashMap<Integer,Integer> index_newstate=new HashMap<>();
        HashMap<Integer,Integer> newstate_index=new HashMap<>();
        HashMap<Character,Integer> mapLetter=new HashMap<>();
        for(int i=0;i< dfa.getLetters().length;i++)
        {
            mapLetter.put(dfa.getLetters()[i],i );
        }
        int k=0;
        for(Integer i:states)
        {
            index_newstate.put(k,i);
            newstate_index.put(i,k);
            k++;
        }
        for(int j=0;j< type2.length;j++)
        {
            if(code.equals(type2[j]))
            {
                //System.out.println("<"+code+">");
                flag=true;
                return "<"+code+">";
            }
        }
        for(int j=0;j< type1.length;j++)
        {
            if(code.equals(type1[j]))
            {
                //System.out.println("<"+code+">");
                flag=true;
                return "<"+code+">";
            }
        }
        if(!flag)
        {
            while(iterator<code.length())
            {
                if(t.isContained(letters,code.charAt(iterator)))
                {
                    curState=newF[newstate_index.get(curState)][mapLetter.get(code.charAt(iterator))];
                    if(curState==-1)
                    {
                        //System.out.println("error");
                        return "error";
                    }
                    iterator++;
                }
                else
                {
                    //System.out.println("error");
                    return "error";
                }

            }
            List<Integer> newZ=dfa.getZ();
            boolean b=false;
            for(int i=0;i<newZ.size();i++)
            {
                if(curState==newZ.get(i))
                {
                    //System.out.println("<"+token_id+","+code+">");
                    // b=true;
                    return "<"+token_id+","+code+">";
                }
            }
            if(!b)
            {
                //System.out.println("error");
                return "error";
            }

        }
        return null;

    }
    public void generate_code(String token_id,String[] code,String[] type2,String[] type1,DFA dfa){
//        List<Integer> K=new ArrayList<>();
//        K.add(0);
//        K.add(1);
//        K.add(2);
//        K.add(3);
//        K.add(4);
//        K.add(5);
//        K.add(6);
//        char l[]=new char[2];
//        l[0]='a';
//        l[1]='b';
//        int f[][]=new int[][]{{1,2},{3,2},{1,5},{3,4},{6,5},{6,5},{3,4}};
//        List<Integer> z=new ArrayList<>();
//        z.add(3);
//        z.add(4);
//        z.add(5);
//        z.add(6);
//        DFA dfa=new DFA(K,l,f,0,z);
//        char[] l=dfa.getLetters();
//        minDFA minDFA= new minDFA();
//        minDFA.setup(l);
//        DFA nDFA=minDFA.minDFA(dfa);

//        generateDiagram g=new generateDiagram();
//        g.generateDiagram(dfa);

        //System.out.println(dfa.getF());
        int w[][]=dfa.getF();
//        for(int i=0;i<w.length;i++)
//        {
//            for(int j=0;j<w[0].length;j++)
//            {
//                System.out.print(w[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println("state"+dfa.getK());        //dui
//        System.out.println("letters"+dfa.getLetters());  //dui
//        System.out.println("start:"+dfa.getS());        //dui
//        System.out.println("end:"+dfa.getZ());        //dui

//        String code[]=new String[2];
//        code[0]="VAR";
//        code[1]="abb";
//        String[] type=new String[1];
//        type[0]="VAR";

        HashMap<Character,Integer> mapLetter=new HashMap<>();
        for(int i=0;i< dfa.getLetters().length;i++)
        {
            mapLetter.put(dfa.getLetters()[i],i );
        }


        int iterator=0;
        int num=0;
        boolean flag=false;
        int curState= dfa.getS();
        char[] letters= dfa.getLetters();
        generateCode t=new generateCode();
        int[][] newF=dfa.getF();
        List<Integer> states=dfa.getK();
        HashMap<Integer,Integer> index_newstate=new HashMap<>();
        HashMap<Integer,Integer> newstate_index=new HashMap<>();
        int k=0;
        for(Integer i:states)
        {
            index_newstate.put(k,i);
            newstate_index.put(i,k);
            k++;
        }
//        System.out.println("index-state"+index_newstate);
        List<String> c=new ArrayList<>();
        boolean isContainPunc=false;
        for(int i=0;i<code.length;i++)
        {
            int first=0;
            int last=0;
            for(int j=0;j<type1.length;j++)
            {
                if(code[i].contains(type1[j]))
                {
                    isContainPunc=true;
                    break;
                }
            }
            System.out.println("code"+i+":"+isContainPunc);
            if(isContainPunc)
            {
                for(int m=0;m<code[i].length();m++)
                {
                    if((code[i].charAt(m)>='a'&&code[i].charAt(m)<='z')||(code[i].charAt(m)>='A'&&code[i].charAt(m)<='Z')||(code[i].charAt(m)>='0'&&code[i].charAt(m)<='9')||code[i].charAt(m)=='_')
                    {
                        last++;
                    }
                    else
                    {
                        String temp=code[i].substring(first,last);
                        c.add(temp);
                        first=m+1;
                        if((m+1<code[i].length())&&((code[i].charAt(m)==':'&&code[i].charAt(m+1)=='=')||(code[i].charAt(m)=='>'&&code[i].charAt(m+1)=='=')||(code[i].charAt(m)=='<'&&code[i].charAt(m+1)=='=')||(code[i].charAt(m)=='<'&&code[i].charAt(m+1)=='>')))
                        {
                            c.add(code[i].substring(m,m+2));
                            last++;
                        }
                        else
                        {
                            for(int l=0;l<type1.length;l++)
                            {
                                if(type1[l].contains(String.valueOf(code[i].charAt(m))))
                                {
                                    c.add(code[i].substring(m,m+1));
                                    last++;
                                    break;
                                }
                            }
                        }

                    }
                }
            }
            else
            {
                c.add(code[i]);
            }
        }
        System.out.println("分割好的字符串：");
        for(int i=0;i<c.size();i++)
        {
            System.out.println(c.get(i));
        }
        String[] code1 = new String[c.size()];
        for(int i=0;i<c.size();i++)
        {
            code1[i]=c.get(i);
        }

//        for(int i=0;i< code1.length;i++)
//        {
//            System.out.println(code1[i]);
//        }
        for(num=0;num<code1.length;num++)
        {
            curState=dfa.getS();
            flag=false;
            iterator=0;
            for(int j=0;j< type2.length;j++)
            {
                if(code1[num].equals(type2[j]))
                {
                    System.out.println("<"+code1[num]+">");
                    flag=true;
                    break;
                }
            }
            for(int j=0;j< type1.length;j++)
            {
                if(code1[num].equals(type1[j]))
                {
                    System.out.println("<"+code1[num]+">");
                    flag=true;
                    break;
                }
            }
            if(!flag)
            {
                while(iterator<code1[num].length())
                {
                    if(t.isContained(letters,code1[num].charAt(iterator)))
                    {
                        curState=newF[newstate_index.get(curState)][mapLetter.get(code1[num].charAt(iterator))];
                        if(curState==-1)
                        {
                            System.out.println("error");
                            return;
                        }
                        iterator++;
                    }
                    else
                    {
                        System.out.println("error");
                        return;
                    }

                }
                List<Integer> newZ=dfa.getZ();
                boolean b=false;
                for(int i=0;i<newZ.size();i++)
                {
                    if(curState==newZ.get(i))
                    {
                        System.out.println("<"+token_id+","+code1[num]+">");
                        b=true;
                        break;
                    }
                }
                if(!b)
                {
                    System.out.println("error");
                }

            }

        }


    }
}
