package RE2DFA.Service;

import RE2DFA.Beans.DFA;
import RE2DFA.Beans.NFA;

import java.util.*;

public class NFA2DFA {

    public DFA definite(NFA nfa){

        // 获取NFA初态
        List<Integer> S0 = nfa.getS();
        // 获取NFA字母表
        char[] letters = nfa.getLetters();
        // 获取NFA状态转移函数
        String[][] f = nfa.getF();
        // 获取NFA终态
        List<Integer> Z = nfa.getZ();

        // 构造DFA
        DFA dfa = new DFA();
        // 暂存DFA状态集
        List<Integer> K = new ArrayList<>();
        // 状态计数
        int k = 0;
        // 暂存DFA的转换函数
        List<String[]> listF = new ArrayList<>();
        // 暂存DFA的终态
        List<Integer> listZ = new ArrayList<>();
        // 输出子集法过程
        StringBuilder sb = new StringBuilder();

        // 状态集临时集合
        Set<List<Integer>> set = new HashSet<>();               // 状态集临时集合
        // 状态集临时队列
        Queue<List<Integer>> queue = new LinkedList<>();        // 状态集临时队列
        // 状态集和命名的映射
        Map<List<Integer>, Integer> map = new HashMap<>();      //「状态集」向「命名」的映射

        // 获得NFA初态的闭包
        List<Integer> closure_K = closure(S0, f);
        // 将状态0加入DFA状态集
        K.add(k);
        // 将初始状态集内部状态升序排列
        closure_K.sort(Comparator.comparing(Integer::intValue));
        // 将初始状态集加入状态集临时集合
        set.add(closure_K);
        // 初态闭包和状态0构成映射
        map.put(closure_K, k++);
        // 将初态闭包加入状态集临时队列
        queue.add(closure_K);
        // 当状态集临时队列不为空，作以下处理
        while(!queue.isEmpty()){
            // 获取队头并移出队列
            List<Integer> I = queue.poll();
            // 对于NFA字母表各个字母，做以下处理
            for(char letter : letters){
                // 获取当前闭包在输入letter转移后的状态集的闭包
                List<Integer> nextI = closure(move(I, letter, f), f);
                // 结果为空则直接跳过
                if(nextI.isEmpty()) continue;
                // 不为空，则判断是否在状态集临时集合中
                if(!containsI(set, nextI)){
                    // 不在，将状态集按升序排序
                    nextI.sort(Comparator.comparing(Integer::intValue));
                    // 将状态集和k映射
                    map.put(nextI, k);
                    // 状态k加入DFA状态集
                    K.add(k);
                    // 判断nextI和NFA终态是否有交集
                    if(!Collections.disjoint(nextI, Z)){
                        // 有交集，则将DFA状态k加入DFA终态
                        listZ.add(k);
                    }
                    // 将状态集加入状态集临时集合
                    set.add(nextI);
                    // 将状态集加入状态集临时队列
                    queue.add(nextI);
                    k++;
                }
                System.out.print(I);
                System.out.println(nextI);
                // 将DFA状态I到iNext的转移加入DFA状态转移函数
                listF.add(new String[]{String.valueOf(map.get(I)), String.valueOf(letter), String.valueOf(map.get(nextI))});
                sb.append(map.get(I)).append(letter).append(map.get(nextI)).append('\n');
            }
        }
        System.out.println("重命名后：");
        System.out.println(sb.toString());

        // 下面是为了构造出DFA对象
        int len = K.size();
        String[][] f2 = new String[len][len];
        for(String[] tmp : f2){
            Arrays.fill(tmp, "");
        }
        for(String[] arr : listF){
            f2[Integer.parseInt(arr[0])][Integer.parseInt(arr[2])] += arr[1];
        }
        dfa.setK(K);
        dfa.setLetters(letters);
        dfa.setFv(f2);
        dfa.setS(0);
        dfa.setZ(listZ);
        return dfa;
    }


    private List<Integer> closure(List<Integer> I, String[][] f){
        // 经过任意多个ε，因此BFS
        List<Integer> closureI  = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for(int i : I){
            queue.add(i);
            while (!queue.isEmpty()){
                int n = queue.poll();
                for(int iNext = 0; iNext < f.length; iNext++){
                    for(char c : f[n][iNext].toCharArray()){
                        if(c == 'ε' && !closureI.contains(iNext)){
                            closureI.add(iNext);
                            if(n != iNext){
                                queue.add(iNext);
                            }
                        }
                    }
                }
            }
        }
        return closureI;
    }


    private List<Integer> move(List<Integer> I, char letter, String[][] f){
        List<Integer> nextI = new ArrayList<>();
        for(int i : I){
            for(int iNext = 0; iNext < f.length; iNext++){
                for(char c : f[i][iNext].toCharArray()){
                    if(c == letter && !nextI.contains(iNext)) {
                        nextI.add(iNext);
                    }
                }
            }
        }
        return nextI;
    }



    private boolean containsI(Set<List<Integer>> set, List<Integer> list){
        for(List<Integer> l : set){
            if(listEquals(l, list)){
                return true;
            }
        }
        return false;
    }


    private boolean listEquals(List<Integer> list1, List<Integer> list2){
        list1.sort(Comparator.comparing(Integer::intValue));
        list2.sort(Comparator.comparing(Integer::intValue));
        return list1.toString().equals(list2.toString());
    }

}
