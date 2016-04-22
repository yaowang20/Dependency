package com.example;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.apache.log4j.Logger;

public class Algorithm {
    private static final Logger logger = Logger.getLogger(Algorithm.class);

    Node addNodeList(String name, Map<String, Node> nodeList) {
        Node new_node = null;
        if (nodeList != null) {
            if (!nodeList.containsKey(name)) {
                new_node = new Node(name);
                nodeList.put(name, new_node);
            }else{
                new_node = nodeList.get(name);
            }
        }
        return new_node;
    }

    void updatePredecessors(String nodeName, Set<String> set, Map<String, Node> nodeList) {
        if (nodeList.containsKey(nodeName)) {
            Node node = nodeList.get(nodeName);
            Set<String> predeceesorList = node.getPredecessors();
            if (predeceesorList != null && set != null) {

                for (String predecessorName : predeceesorList) {
                    Node predecessor = nodeList.get(predecessorName);
                    Set<String> predecessor_successorSet = predecessor.getSuccessors();
                    predecessor_successorSet.addAll(set);
                }
            }

        }
    }

    void updateSuccessor(String firstNodeName, Set<String> list, Map<String, Node> nodeList) {
        Node firstNode = nodeList.get(firstNodeName);
        Set<String> firstNode_Predecessors = firstNode.getPredecessors();
        if (list != null) {
            for (String name : list) {
                Node node = nodeList.get(name);
                Set<String> predecessorsSet = node.getPredecessors();
                predecessorsSet.add(firstNodeName);
                predecessorsSet.addAll(firstNode_Predecessors);
            }
        }
    }

    void processALine(String line, Map<String, Node> nodeList) {
        String[] words = line.trim().split("[-\\.,;|\\s]+");

        if (words.length > 0) {
            String firstWord = words[0];
            Node firstNode = addNodeList(firstWord, nodeList);

            if (words.length > 1) {
                Set<String> firstNode_successorSet = firstNode.getSuccessors();
                Set<String> newSuccessorSet = new HashSet<>();
                Queue<String> successorQueue = new LinkedList<>();

                for (int i = 1; i < words.length; i++) {
                    String next = words[i];
                    addNodeList(next, nodeList);
                    if (!firstNode_successorSet.contains(next)) {
                        firstNode_successorSet.add(next);
                        newSuccessorSet.add(next);
                        successorQueue.add(next);
                    }
                }
                while (successorQueue.size() > 0) {
                    String next = successorQueue.remove();
                    Node nextNode = nodeList.get(next);
                    Set<String> nextNode_successorSet = nextNode.getSuccessors();
                    for (String nextNode_successorName : nextNode_successorSet) {
                        if (!firstNode_successorSet.contains(nextNode_successorName)) {
                            firstNode_successorSet.add(nextNode_successorName);
                            newSuccessorSet.add(nextNode_successorName);
                            successorQueue.add(nextNode_successorName);
                        }
                    }

                }
                updatePredecessors(firstWord, newSuccessorSet, nodeList);
                updateSuccessor(firstWord, newSuccessorSet, nodeList);
            }

        }
    }

    void saveResults(String filePath, Map<String, Node> nodeList) {
        try {
            logger.info(" The original file is " + filePath);

            Path p = Paths.get(filePath);
            String originalFileName = p.getFileName().toString();
            String fileName = "result_" + originalFileName;
            String resultFilePath = filePath.replace(originalFileName, fileName);
            File file = new File(resultFilePath);

            if (!file.createNewFile()) {
                if (file.delete()) {
                    if(!file.createNewFile()){
                        logger.warn("Creating " + resultFilePath + " failed!");
                    }else {
                        logger.info(" The result file is " + resultFilePath);
                    }
                }
            }else logger.info(" The result file is " + resultFilePath);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            System.out.println(" The result is as follows!");
            for (String word : nodeList.keySet()) {
                Node node = nodeList.get(word);
                String ss = node.getNodeName() + "    ";
                Set<String> successorList = node.getSuccessors();
                if (successorList != null) {
                    for (String next : successorList) {
                        ss = ss + next + " ";
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

    // the main algorithm
    public void runAlgorithm(String fileName) {
        logger.info(" Running the algorithm");

        String line;
        Map<String, Node> nodeList = new HashMap<>();

        try {

            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                processALine(line, nodeList);
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            ex.printStackTrace();
        }

        saveResults(fileName, nodeList);

    }
}
