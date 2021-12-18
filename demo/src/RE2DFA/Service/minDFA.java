package RE2DFA.Service;

import RE2DFA.Beans.DFA;
import RE2DFA.Beans.Group;

import java.util.*;

public class minDFA {

   private int cnt = 0;

   public void minDFA(DFA dfa){
       List<Integer> K = dfa.getK();
       List<Integer> Z = dfa.getZ();
       String[][] f = dfa.getFv();
       char[] letters = dfa.getLetters();

       K.removeAll(Z);         // 全部状态集K - 终态集Z = 非终态集
       Group groupx = new Group(cnt++, new HashSet<>(K));
       Group groupy = new Group(cnt++, new HashSet<>(Z));
       Set<Group> finalGroupSet = new HashSet<>();             // 最终分组
       Set<Group> curGroupSet = new HashSet<>();               // 此时的分组
       finalGroupSet.add(groupx);
       finalGroupSet.add(groupy);

       for(char letter : letters){                                 // 对于每个字母
           curGroupSet = finalGroupSet;                            // 【最终分组】不断沦为【此时分组】
           finalGroupSet = separate(curGroupSet, letter, f);       // 【此时分组】又分裂成新的【最终分组】
       }                                                           // 所有字母都用了一次后，成为名副其实的【最终分组】

       // 打印最终分组(一组中的状态等价)
       for(Group group : finalGroupSet){
           System.out.print(group.groupID);
           System.out.print(group.stateSet);
           System.out.println();
       }
   }

   private Set<Group> separate(Set<Group> groupSet, char letter, String[][] f){
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
           }else{                  // 如果这些状态映射到了多个状态集(Group)中，则删除原先分组，创建多个新分组，并将新分组入队
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


   private int move(int state, char letter, String[][] f){
       for(int nextState = 0; nextState < f.length; nextState++){
           for(char c : f[state][nextState].toCharArray()){
               if(c == letter){
                   return nextState;
               }
           }
       }
       return -1;
   }


   private Group beLong(int state, char letter, String[][] f, Set<Group> groupSet){
       int newState = move(state, letter, f);
       for(Group group : groupSet){
           if(group.stateSet.contains(newState)){
               return group;
           }
       }
       return null;
   }
}







