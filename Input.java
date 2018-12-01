
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
    private int[][] inputs = new int[3823][1024];
    private int[] targetClass = new int[3823];
    private int[][] testInputs = new int[1797][1024];
    private int[] testTargets = new int [1797];
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
        boolean bitMapSection = false;
        for(String line: lines) {
            // Checks to see when we hit the node coordinate section
            if(!bitMapSection && line.contains("0") && line.contains("1")){
                bitMapSection = true;
            }


            // Once in the coordinate section, read in the lines
            if(bitMapSection) {
              if(!isTest){
                int lnSize = line.length();
                int lineIndex = 0;


                if(inputType.equals("bm")){
                    if(localIndex >= 1024){
                        localIndex = 0;
                        //System.out.println("The target for this bm is : " + line + "with size " + lnSize);
                        targetClass[targetIndex] = Character.getNumericValue(line.charAt(1));
                        targetIndex++;
                        inputIndex++;
                    }
                    else{
                        while(lineIndex < lnSize){
                            inputs[inputIndex][localIndex] = Character.getNumericValue(line.charAt(lineIndex));
                            //System.out.print(line.charAt(lineIndex));
                            lineIndex++;
                            localIndex++;
                        }

                    }
                } 
                // For the 8x8 downsampled image
                else {
                    if(localIndex >= 64){
                        localIndex = 0;
                        targetClass[targetIndex] = Character.getNumericValue(line.charAt(1));
                        targetIndex++;
                        inputIndex++;
                    }
                    else{
                        while(lineIndex < lnSize){
                            inputs[inputIndex][localIndex] = Character.getNumericValue(line.charAt(lineIndex));
                            lineIndex++;
                            localIndex++;
                        }
                    }
                }
              }
              else{
                int lnSize = line.length();
                int lineIndex = 0;


                if(inputType.equals("bm")){
                    if(localIndex >= 1024){
                        localIndex = 0;
                        //System.out.println("The target for this bm is : " + line + "with size " + lnSize);
                        testTargets[targetIndex] = Character.getNumericValue(line.charAt(1));
                        targetIndex++;
                        inputIndex++;
                    }
                    else{
                        while(lineIndex < lnSize){
                            testInputs[inputIndex][localIndex] = Character.getNumericValue(line.charAt(lineIndex));
                            //System.out.print(line.charAt(lineIndex));
                            lineIndex++;
                            localIndex++;
                        }
                    }
                }
                // For the 8x8 downsampled image
                else {
                    if(localIndex >= 64){
                        localIndex = 0;
                        testTargets[targetIndex] = Character.getNumericValue(line.charAt(1));
                        targetIndex++;
                        inputIndex++;
                    }
                    else{
                        while(lineIndex < lnSize){
                            testInputs[inputIndex][localIndex] = Character.getNumericValue(line.charAt(lineIndex));
                            lineIndex++;
                            localIndex++;
                        }
                    }
                }
              }
             }

        }
    }
    
    public int[][] getTestInputs(){
      return testInputs;
    }

    public int[] getTestTargets(){
      return testTargets;
    }
    
    public int[][] getInputs(){
        return inputs;
    }

    public int[] getTargets(){
        return targetClass;
    }


}
