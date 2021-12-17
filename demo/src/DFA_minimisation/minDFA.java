package DFA_minimisation;

import java.util.*;

//BUG:状态表示用字母还是数字（string or int）
public class minDFA {

    private int cnt = 0;                      //维护Group的唯一ID

    private Map<Character, Integer> m = new HashMap<Character, Integer>();
    public void setup(char[] letters)
    {
        for(int i=0;i<letters.length;i++)
        {
            m.put(letters[i],i);
        }
    }

    public DFA minDFA(DFA dfa){
        List<Integer> K = dfa.getK();          //状态集
        List<Integer> Z = dfa.getZ();          //终态集
        int[][] f = dfa.getF();             //转换函数
        char[] letters = dfa.getLetters();     //字母表

        K.removeAll(Z);                        // 全部状态集K - 终态集Z = 非终态集
        Group groupx = new Group(cnt++, new HashSet<>(K));
        Group groupy = new Group(cnt++, new HashSet<>(Z));
        Set<Group> finalGroupSet = new HashSet<>();             // 最终分组
        Set<Group> curGroupSet = new HashSet<>();               // 此时的分组
        finalGroupSet.add(groupx);                     //加入非终态集
        finalGroupSet.add(groupy);                     //加入终态集

        for(char letter : letters){                               // 对于每个字母
            curGroupSet = finalGroupSet;                            // 【最终分组】不断沦为【此时分组】
            finalGroupSet = separate(curGroupSet, letter, f);       // 【此时分组】又分裂成新的【最终分组】
        }                                                           // 所有字母都用了一次后，成为名副其实的【最终分组】

        // 打印最终分组(每个组中的状态等价)
        int i=1;
        HashMap<Integer,Group> new_old=new HashMap<Integer,Group>();
        HashMap<Group,Integer> old_new=new HashMap<Group,Integer>();
        for(Group group : finalGroupSet){
                System.out.print(group.groupID);
            System.out.print(group.stateSet);
            System.out.println();
        }
        //构建新状态集
        List<Integer> newK=new ArrayList<>();
        //通过新状态集ID得到状态集
        HashMap<Integer,Set<Integer>> ID_group=new HashMap<Integer,Set<Integer>>();
        HashMap<Set,Integer> group_ID=new HashMap<>();
        //通过数组索引得到新状态集ID
        HashMap<Integer,Integer> index_newstate=new HashMap<>();
        HashMap<Integer,Integer> newstate_index=new HashMap<>();
        int iterator=0;
        for(Group group:finalGroupSet)
        {
            ID_group.put(group.groupID,group.stateSet);
            group_ID.put(group.stateSet,group.groupID);
            newK.add(group.groupID);
            index_newstate.put(iterator,group.groupID);
            iterator++;
        }
        System.out.println("1"+ID_group.get(1));

        //构建新字母表，不变
        char[] newLetters= dfa.getLetters();

        //构建新转移函数
        int[][] newF=new int[finalGroupSet.size()][newLetters.length];
        for(int m=0;m< finalGroupSet.size();m++)
        {
            for(int n=0;n<newLetters.length;n++)
            {
                //m索引对应的ID（新状态）对应的旧状态集
//                List<Integer> ls=new ArrayList<Integer>(ID_group.get(index_newstate.get(m)));
                List<Integer> ls = new ArrayList<> ();
                System.out.println("ssg"+ID_group.get(index_newstate.get(m)));
                for(Integer inte:ID_group.get(index_newstate.get(m)))
                {
                    ls.add(inte);
                }
                for(int k=0;k<ls.size();k++)
                {
                    //如果当前状态集的状态转移函数不为空
                    if(f[ls.get(k)][n]!=-1)                   //对应旧状态转移函数不为空
                    {
                        //内容也需要转化为新状态
                        int toState=f[ls.get(k)][n];
                        //遍历查看
                        for(int o=0;o< finalGroupSet.size();o++)
                        {
                            //如果o索引对应的ID对应的旧状态集包含要得到的状态
                            System.out.println("index"+index_newstate.get(2));
                            if(ID_group.get(index_newstate.get(o)).contains(toState))
                            {
                                //新的转移函数内容应该是o索引对应的ID
                                newF[m][n]=index_newstate.get(o);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }

        //构建初始状态
        int oldstart=dfa.getS();
        int newstart=-1;
        for(int m=0;m<finalGroupSet.size();m++)
        {
            //如果索引m对应的ID的状态集包含旧的初始状态，则该m对应的新状态为初始状态
            if(ID_group.get(index_newstate.get(m)).contains(oldstart))
            {
                newstart=index_newstate.get(m);
                break;
            }
        }

        //构建终态集
        List<Integer> oldend=dfa.getZ();
        List<Integer> newend=new ArrayList<>();
        for(int m=0;m< finalGroupSet.size();m++)
        {
            for(int n=0;n<oldend.size();n++)
            {
                if(ID_group.get(index_newstate.get(m)).contains(oldend.get(n)))
                {
                    newend.add(index_newstate.get(m));
                }
            }
        }

        DFA newdfa=new DFA(newK,newLetters,newF,newstart,newend);
        return newdfa;

//        for (Map.Entry<Integer,Group > entry : new_old.entrySet()) {
//            Integer mapKey = entry.getKey();
//            Group mapValue = entry.getValue();
//            System.out.println(mapKey + "：" + mapValue);
//        }
//        DFA newDfa=new DFA();
//        //List<Integer> k 状态集, char[] letters 字母表, int[][] f, int s,初始状态 List<Integer> z终态集
//        List<Integer> k=new ArrayList<>();
//        HashMap<Integer,Integer> state_index=new HashMap<Integer,Integer>();
//        HashMap<Integer,Set> ID_oldState=new HashMap<>();
//        //构造新状态集 建立索引
//        int index=0;
//        for(Group group:finalGroupSet)
//        {
//            k.add(group.groupID);
//            state_index.put(index,group.groupID);
//            ID_oldState.put(group.groupID, group.stateSet);
//            index++;
//        }
//        //构造新字母表
//        char[] newL=letters;
//        //构造新转换函数
//        int newF[][]=new int[i][letters.length];
//        for(int m=0;m< finalGroupSet.size();m++)
//        {
//            for(int n=0;n<letters.length;n++)
//            {
//                boolean flag=false;
//                Set<Integer> set =ID_oldState.get(state_index.get(m));
//                for(Integer state:set)
//                {
//                    if(f[state][n]!=-1)
//                    {
//                        for(Group group:finalGroupSet)
//                        {
//                            if(group.stateSet.contains(f[state][n]))
//                            {
//                                newF[m][n]=group.groupID;
//                                flag=true;
//                                break;
//                            }
//                        }
//                    }
//                    if(flag)
//                        break;
//                }
//                System.out.println(newF[m][n]);
//            }
//        }
//        //构造新初始状态
//        int newS=0;
//        //构造新终态集

    }

    private Set<Group> separate(Set<Group> groupSet, char letter, int[][] f){
        Set<Group> finalGroupSet = new HashSet<>();
        Set<Group> curGroupSet = groupSet;
        Queue<Group> queue = new LinkedList<>();
        for(Group group : groupSet){
                queue.add(group);
        }

        while (!queue.isEmpty()){
            Group oldGroup = queue.poll();
            Map<Group, List<Integer>> map = new HashMap<>();  //根据指向的组，对状态Integer进行分类
            for(Integer state : oldGroup.stateSet){
                    Group stateNextBelong = beLong(state, letter, f, curGroupSet);
                if(!map.containsKey(stateNextBelong)){
                        map.put(stateNextBelong, new ArrayList<>());
                }
                map.get(stateNextBelong).add(state);
            }
            if (map.size() == 1){   // 如果这些状态映射到了一个状态集(Group)中，则为最终分组
                    finalGroupSet.add(oldGroup);
            }else{                 // 如果这些状态映射到了多个状态集(Group)中，则删除原先分组，创建多个新分组，并将新分组入队
                curGroupSet.remove(oldGroup);
                for(List<Integer> list : map.values()){
                    Group newGroup = new Group(cnt++, new HashSet<>(list));
                    curGroupSet.add(newGroup);
                    queue.add(newGroup);
                }
            }
        }
        return finalGroupSet;
    }

    /**
     * move方法: 返回唯一后继状态（-1表示没有后继状态）
     */
    private int move(int state, char letter, int[][] f){
//        for(int nextState = 0; nextState < f.length; nextState++){
//            for(int c : f[state][nextState].toCharArray()){
//                if(c == letter){
//                    return nextState;
//                }
//            }
//        }
//        return -1;
        return f[state][m.get(letter)];
    }

    /**
     * beLong方法: 某状态(state)经过字母(letter)一次转化(move)后，所属于的当前分组(group)
     */
    private Group beLong(int state, char letter, int[][] f, Set<Group> groupSet){
        int newState = move(state, letter, f);
        for(Group group : groupSet){
            if(group.stateSet.contains(newState)){
                return group;
            }
        }
        return null;
    }


}
