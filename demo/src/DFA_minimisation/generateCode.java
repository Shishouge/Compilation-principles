package DFA_minimisation;

import RE2DFA.Beans.DFA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class generateCode {
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


    //public static void main(String[] args) {
    public void generate_code(String token_id,String[] code,String[] type,DFA dfa){
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
        for(num=0;num<code.length;num++)
        {
            curState=dfa.getS();
            flag=false;
            iterator=0;
            for(int j=0;j< type.length;j++)
            {
                if(code[num].equals(type[j]))
                {
                    System.out.println("<"+code[num]+">");
                    flag=true;
                    break;
                }
            }
            if(!flag)
            {
                while(iterator<code[num].length())
                {
                    if(t.isContained(letters,code[num].charAt(iterator)))
                    {
                        curState=newF[newstate_index.get(curState)][mapLetter.get(code[num].charAt(iterator))];
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
                        System.out.println("<"+token_id+","+code[num]+">");
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
