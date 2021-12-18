package DFA_minimisation;

import RE2DFA.Beans.DFA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class generateDiagram {
    public static void main(String[] args) {
        generateDiagram g=new generateDiagram();
        List<Integer> K=new ArrayList<>();
        K.add(0);
        K.add(1);
        K.add(2);
        K.add(3);
        K.add(4);
        K.add(5);
        K.add(6);
        char l[]=new char[2];
        l[0]='a';
        l[1]='b';
        int f[][]=new int[][]{{1,2},{3,2},{1,5},{3,4},{6,5},{6,5},{3,4}};
        List<Integer> z=new ArrayList<>();
        z.add(3);
        z.add(4);
        z.add(5);
        z.add(6);
        DFA dfa=new DFA(K,l,f,0,z);
        minDFA minDFA= new minDFA();
        minDFA.setup(l);
        DFA nDFA=minDFA.minDFA(dfa);
        for(int i=0;i<nDFA.getK().size();i++)
        {
            for(int j=0;j<nDFA.getLetters().length;j++)
            {
                System.out.println(nDFA.getF()[i][j]);
            }
        }
        g.generateDiagram(nDFA);
    }
    public void generateDiagram(DFA dfa)
    {
        //    a  b            5  1  4  2
        //5   2  4                     0
        //1   1  1
        //4   2  1
        //2   1  4
        //得到转换函数
        int[][] f= dfa.getF();
        //得到状态集
        List<Integer> states=dfa.getK();
        HashMap<Integer,Integer> index_state=new HashMap<>();
        HashMap<Integer,Integer> state_index=new HashMap<>();
        int iterator=0;
        for(Integer i:states)
        {
            index_state.put(iterator,i);
            state_index.put(i,iterator);
            iterator++;
        }
        System.out.println("index:"+index_state);

        int[][] diagram=new int[states.size()][states.size()];
        for(int i=0;i<diagram.length;i++)
        {
            for(int j=0;j<diagram[0].length;j++)
            {
                diagram[i][j]=-1;
            }
        }
        for(int i=0;i<f.length;i++)
        {
            for(int j=0;j<f[0].length;j++)
            {
                if(f[i][j]!=-1)
                {
                    if(diagram[i][state_index.get(f[i][j])]!=-1)
                    {
                        diagram[i][state_index.get(f[i][j])]=diagram[i][state_index.get(f[i][j])]+j+1;
                    }
                    else
                    {
                        diagram[i][state_index.get(f[i][j])] =j+1;
                    }

                }
            }
        }
        for(int i=0;i<diagram.length;i++)
        {
            for(int j=0;j<diagram[0].length;j++)
            {
                System.out.print(diagram[i][j]+"  ");
            }
            System.out.println();
        }

    }

}
