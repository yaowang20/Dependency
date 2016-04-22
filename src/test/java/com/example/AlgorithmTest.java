package test;

import static org.junit.Assert.assertTrue;

import com.example.Algorithm;
import org.junit.Test;
import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AlgorithmTest {
    @Test
    public void testData1(){
        //the sample date
        String workingDirectory = System.getProperty("user.dir");
        String filePath = workingDirectory + "//testData//SampleData.txt";
        assertTrue(test(filePath));

    }

    @Test
    public void testData2(){
        //the loop data set
        String workingDirectory = System.getProperty("user.dir");
        String filePath = workingDirectory + "//testData//SampleData 2.txt";
        assertTrue(test(filePath));

    }

    @Test
    public void testData3(){
        //the loop data set
        String workingDirectory = System.getProperty("user.dir");
        String filePath = workingDirectory + "//testData//SampleData 3.txt";
        assertTrue(test(filePath));

    }


    private static boolean test(String filePath){

        Algorithm alg = new Algorithm();
        alg.runAlgorithm(filePath);

        Path p = Paths.get(filePath);
        String originalFileName = p.getFileName().toString();

        //System.out.println(originalFileName);

        String resultFileName = "result_" + originalFileName;
        String resultFilePath = filePath.replace(originalFileName,resultFileName);
        //System.out.println(resultFilePath);

        String expectedFileName = "expected_" + resultFileName;
        String expectedFilePath = filePath.replace(originalFileName,expectedFileName);
        return equalFiles(expectedFilePath,resultFilePath);
    }


    private static boolean equalFiles(String expectedFileName,
                                      String resultFileName) {
        boolean equal;
        BufferedReader bExp;
        BufferedReader bRes;
        String expLine ;
        String resLine ;

        equal = false;
        bExp = null ;
        bRes = null ;

        try {
            bExp = new BufferedReader(new FileReader(expectedFileName));
            bRes = new BufferedReader(new FileReader(resultFileName));

            do{
                expLine = bExp.readLine() ;
                resLine = bRes.readLine() ;
                equal = ((expLine == null) && (resLine == null)) || ((expLine != null) && expLine.equals(resLine)) ;
            }while(equal && expLine != null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bExp != null) {
                    bExp.close();
                }
                if (bRes != null) {
                    bRes.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return equal;

    }

}
