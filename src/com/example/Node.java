package com.example;

import java.util.HashSet;
import java.util.Set;

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