package DFA_minimisation;

import java.util.Set;

public class Group {
    public int groupID;                 // 该组的唯一ID
    public Set<Integer> stateSet;       // 该组所包含的状态集

    public Group(int groupID, Set<Integer> stateSet) {
            this.groupID = groupID;
        this.stateSet = stateSet;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public Set<Integer> getStateSet() {
        return stateSet;
    }

    public void setStateSet(Set<Integer> stateSet) {
        this.stateSet = stateSet;
    }
}
