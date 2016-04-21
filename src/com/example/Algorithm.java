package com.example;

import java.io.*;
import java.util.*;

/**
 * Created by yao on 4/20/2016.
 */
public class Algorithm {

    Node addNodeList( String name, Map<String, Node> nodeList){
        Node new_node = null;
        if(nodeList != null & !nodeList.containsKey(name)){
            new_node = new Node(name);
            nodeList.put(name, new_node);
        }else{
            new_node = nodeList.get(name);
        }
        return new_node;
    }

    void updatePredecessors(String nodeName, Set<String> set, Map<String, Node> nodeList){
        if(nodeList.containsKey(nodeName)){
            Node node = nodeList.get(nodeName);
            Set<String> predeceesorList = node.getPredecessors();
            if(predeceesorList != null){
                if(set != null){
                    for(String predecessorName :predeceesorList ){
                        Node predecessor = nodeList.get(predecessorName);
                        Set<String> predecessor_successorSet = predecessor.getSuccessors();
                        predecessor_successorSet.addAll(set);
                    }
                }

            }

        }
    }

    void updateSuccessor(String firstNodeName, Set<String> list, Map<String, Node> nodeList){
        Node firstNode = nodeList.get(firstNodeName);
        Set<String> firstNode_Predecessors = firstNode.getPredecessors();
       if(list != null){
           for(String name : list){
               Node node = nodeList.get(name);
               Set<String> predecessorsSet = node.getPredecessors();
               predecessorsSet.add(firstNodeName);
               predecessorsSet.addAll(firstNode_Predecessors);
           }
       }
    }

    void processALine(String line, Map<String, Node> nodeList){
        System.out.println(line);
        String[] words = line.trim().split("\\s+");

        if(words.length > 0) {
            String firstWord = words[0];
            Node firstNode = addNodeList(firstWord, nodeList);
            //System.out.println(" first word : " + firstWord);

            if (words.length > 1) {
                Set<String> firstNode_successorSet = firstNode.getSuccessors();
                Set<String> newSuccessorSet = new HashSet<String>();
                Queue<String> successorQueue = new LinkedList<String>();

                for (int i = 1; i < words.length; i++) {
                    String next = words[i];
                    addNodeList(next, nodeList);
                    //System.out.println(" " + i + " :  " + next);
                    if(!firstNode_successorSet.contains(next)){
                        firstNode_successorSet.add(next);
                        newSuccessorSet.add(next);
                        successorQueue.add(next);
                    }
                }
                while(successorQueue.size() > 0){
                    String next = successorQueue.remove();
                    Node nextNode = nodeList.get(next);
                    Set<String> nextNode_successorSet =nextNode.getSuccessors();
                    for(String nextNode_successorName : nextNode_successorSet){
                        if(!firstNode_successorSet.contains(nextNode_successorName)){
                            firstNode_successorSet.add(nextNode_successorName);
                            newSuccessorSet.add(nextNode_successorName);
                            successorQueue.add(nextNode_successorName);
                        }
                    }

                }
                updatePredecessors(firstWord, newSuccessorSet, nodeList);
                updateSuccessor(firstWord,newSuccessorSet, nodeList);
            }

        }
    }


    void saveResults(String originalFileName,Map<String, Node> nodeList)
    {
        try {

            String fileName = "result_" + originalFileName;
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (String word : nodeList.keySet()) {
                Node node = nodeList.get(word);
                String ss = node.getNodeName() + " : ";
                Set<String> successoreList = node.getSuccessors();
                if (successoreList != null) {
                    for (String next : successoreList) {
                        ss = ss + next + "   ";
                    }
                }
                System.out.println(ss);
                bw.write("" + ss);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    public void runAlgorithm(String fileName){
        // The name of the file to open.
       // String fileName = "SampleData.txt";

        String line = null;
        Map<String, Node> nodeList = new HashMap();


        try {

            FileReader fileReader =  new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                processALine(line, nodeList);
            }

            // close files.
            bufferedReader.close();
        }  catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            ex.printStackTrace();
        }  catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            ex.printStackTrace();
        }

        saveResults(fileName, nodeList);

    }
}
