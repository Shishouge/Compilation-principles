package RE2DFA.Service;

import RE2DFA.Beans.Edge;
import RE2DFA.Beans.Graph;
import RE2DFA.Beans.NFA;
import RE2DFA.Beans.Node;

import java.util.*;

public class RE2NFA {
    public NFA get(List<String> re){
        NFA nfa = new NFA();
        Graph nfaGraph = getNFAGraph(re);
        System.out.println(nfaGraph);
        List<Edge> edges = nfaGraph.getEdges();
        Node start = nfaGraph.getStart();
        Node end = nfaGraph.getEnd();

        List<Integer> S = new ArrayList<>();
        S.add(start.getId());
        List<Integer> Z = new ArrayList<>();
        Z.add(end.getId());
        nfa.setS(S);
        nfa.setZ(Z);
        Set<Integer> stateSet = new HashSet<>();
        Set<Character> letterSet = new HashSet<>();
        for (Edge edge : edges){
            stateSet.add(edge.getBegin().getId());
            stateSet.add(edge.getEnd().getId());
            if(edge.getLabel() != "epsilon"){
                System.out.println(edge.getLabel().charAt(0));
                letterSet.add(edge.getLabel().charAt(0));
            }
        }
        List<Integer> K = new ArrayList<>(stateSet);
        int len = K.size();
        String temp = letterSet.toString().replaceAll("[^a-zA-Z0-9]","");
        System.out.println(temp);
        nfa.setK(K);
        nfa.setLetters(temp.toCharArray());
        String[][] f = new String[len][len];
        for(String[] tmp : f){
            Arrays.fill(tmp, "");
        }
        for(int i = 0; i < f.length; i++){
            f[i][i] = "ε";
        }
        for (Edge edge : edges){
            String letter = edge.getLabel();
            if(letter == "epsilon"){
                letter = "ε";
            }
            f[edge.getBegin().getId()][edge.getEnd().getId()] += letter;
        }
        nfa.setF(f);
        return nfa;
    }

    private Graph getNFAGraph(List<String> re){
        Stack operators = new Stack<>();
        Stack nums = new Stack<>();
        operators.push("#");
        re.add("#");
        int i = 0;
        while(re.get(i) != "#" || (operators.peek()) != "#"){
            String s = re.get(i);
            if(s.matches("\\w")){ // 是数字
                nums.push(s);
                i++;
            } else {
                switch (s){
                    case "*":
                        Object num = nums.pop();
                        Graph graphStar = new Graph();
                        graphStar.Star(num);
                        nums.push(graphStar);
                        break;
                    case "^":
                        Object num2 = nums.pop();
                        Object num1 = nums.pop();
                        Graph graphConcat = new Graph();
                        graphConcat.Concat(num1,num2);
                        nums.push(graphConcat);
                        break;
                    case "|":
                        Object num4 = nums.pop();
                        Object num3 = nums.pop();
                        Graph graphUnion = new Graph();
                        graphUnion.Union(num3,num4);
                        nums.push(graphUnion);
                        break;
                    default:
                        break;
                }
                i++;
            }
        }
        return (Graph) nums.pop();
    }



}
