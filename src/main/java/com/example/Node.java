package com.example;

import java.util.HashSet;
import java.util.Set;

// A -> B -> C
// For B, A is predecessor, C is successor
// For C, A and B both are  predecessors
// For A, B and C both are successors

public class Node {
   final String nodeName;
    Set<String> successorSet = null;
    Set<String> predecessorSet = null;

    Node(String nodeName){
        this.nodeName = nodeName;

    }

    public Set<String> getSuccessors(){
        if(successorSet == null) {
            successorSet = new HashSet<String>();
        }
        return successorSet;
    }

    public Set<String> getPredecessors(){
        if(predecessorSet == null) {
            predecessorSet = new HashSet<String>();
        }
        return predecessorSet;
    }

    public String getNodeName(){
        return nodeName;
    }


}