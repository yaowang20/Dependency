package test;

import static org.junit.Assert.assertTrue;

import com.example.Algorithm;
import org.junit.Test;
import java.io.*;

import java.io.File;

/**
 * Created by yao on 4/21/2016.
 */
public class AlgorithmTest {
    @Test
    public void testData1(){
        String fileName = "SampleData.txt";
        assertTrue(test(fileName));
    }


    private static boolean test(String fileName){

        Algorithm alg = new Algorithm();
        alg.runAlgorithm(fileName);

        String resultFileName = "result_"+ fileName;
        String expectedFileName = "expected_" + resultFileName;
        return equalFiles(expectedFileName, resultFileName);
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

            if ((bExp != null) && (bRes != null)) {
                expLine = bExp.readLine() ;
                resLine = bRes.readLine() ;

                equal = ((expLine == null) && (resLine == null)) || ((expLine != null) && expLine.equals(resLine)) ;

                while(equal && expLine != null)
                {
                    expLine = bExp.readLine() ;
                    resLine = bRes.readLine() ;
                    equal = expLine.equals(resLine) ;
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                if (bExp != null) {
                    bExp.close();
                }
                if (bRes != null) {
                    bRes.close();
                }
            } catch (Exception e) {
            }

        }

        return equal;

    }

}
