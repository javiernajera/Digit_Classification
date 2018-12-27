
/**
 * Write a description of class Input here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;


public class Input
{
    // instance variables
    private double[][] inputs = new double[7352][561];
    private int[] targetClass = new int[7352];
    private double[][] testInputs = new double[2947][561];
    private int[] testTargets = new int [2947];
    /**
     * Constructor for objects of class Input
     */
    public Input(String docName, String inputType, boolean isTest)
    {
        // initialise instance variables
        readArguments(docName, inputType, isTest);
    }

    public void readArguments(String docName, String inputType, boolean isTest) {

        List<String> lines = new ArrayList<>();

        // Try reading in the file, if it fails, print out error message and exit program.
        try{
            lines = Files.readAllLines(Paths.get(docName));
            System.out.println(lines.size());
        }
        catch(IOException e) {
            System.out.println(e.toString());
            System.out.println("Could not find file " + docName);
            System.exit(-1);
        }
        // Assert that the file has information in it
        assert(lines.size() != 0);
        int inputIndex = 0;
        int localIndex = 0;
        int targetIndex = 0;
        // Boolean which dictates when we are in the node coordinate section
        int i = 0;
        for(String line: lines) {
            // Checks to see when we hit the node coordinate section

            String[] lineArr = line.split("\\t");



            // Once in the coordinate section, read in the lines

              if(!isTest){
                int lnSize = lineArr.length;



                if(inputType.equals("ip")){
                    for(int j = 0; j < lineArr.length - 1; j++){
                      inputs[i][j] = Double.parseDouble(lineArr[j]);
                    }
                    targetClass[i] = Integer.parseInt(lineArr[lineArr.length - 1]);
                    i++;
                }
              }

        }
    }

    public double[][] getTestInputs(){
      return testInputs;
    }

    public int[] getTestTargets(){
      return testTargets;
    }

    public double[][] getInputs(){
        return inputs;
    }

    public int[] getTargets(){
        return targetClass;
    }


}
