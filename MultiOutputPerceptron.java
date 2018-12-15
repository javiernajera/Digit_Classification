
/**
 * Write a description of class Perceptron here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.lang.*;
import java.util.Random;

public class MultiOutputPerceptron
{
    // instance variables - replace the example below with your own
    private int inputSize;
    private double[][] inputToHiddenW;
    private double[][] hiddenToOutputW;
    private int epochPrint = 50;
    private Random rand = new Random();
    private static int NODES = 6;
    private double[] losses = new double[NODES];
    private double[][] hVec;

    /**
     * Constructor for objects of class Perceptron
     */
    public MultiOutputPerceptron(int inputSize)
    {
        this.inputSize = inputSize;

        inputToHiddenW = new double[inputSize*2][inputSize];
        hiddenToOutputW = new double[NODES][inputSize*2];
        initializeWeights();
    }

    public void trainNN(double[][] inputs, int[] target, double lr, int epochs){


        for(int i = 0; i < epochs; i++){
          int input_length = inputs.length;

          //printWeights();
          double loss = 0.0;
          for(int j = 0; i < input_length; j++){


              if(counter % epochPrint == 0){
                  //calcAverages(epochPrint, counter);

              }
          }

        }

    }

    public double[][] feedForward(double[] input, int[] target){
      double accum = 0.0;
      double[][] hVec = new double[2][input.length*2];
        for(int i = 0; i < inputToHiddenW.length; i++){
          for(int j = 0; j < inputToHiddenW[0].length; j++){
            accum += inputs[j] * inputToHiddenW[i][j];
          }
          hVec[0][i] = sigmoid(accum);
        }

        accum = 0;
        for(int i = 0; i < hiddenToOutputW.length; i++){
          for(int j = 0; j < hiddenToOutputW[0].length; j++){
            accum += hVec[0][j]*hiddenToOutputW[i][j];
          }
          hVec[1][i] = sigmoid(accum);
        }
    }

    public void backpropagate(double[][] hVec, int target, double lr){
        double[] inputHidSum = new double[];
        for(int i = 0; i < hiddenToOutputW.length; i++){
          if (i + 1 == target) {
              losses[i] = 1 - hVec[1][i];
          } else {
              losses[i] = 0 - hVec[1][i];
          }
          double derivLoss = losses[i] * (losses[i] - 1);
          for(int j = 0; j < hiddenToOutputW[0].length; j++){
              hiddenToOutputW[i][j] = hiddenToOutputW[i][j] * lr * losses[i] * derivLoss;
              inputHidSum += hiddenToOutputW[i][j] * ;
          }
        }
        
        for (int i = 0; i < NODES; ){
        }
        for(int i = 0; i < inputToHiddenW.length; i++){
          double loss = 0;
            for(int j = 0; j < inputToHiddenW[0].length; j++){
            accum += inputs[j] * hiddenToOutput[i][j];
            
          }
          
        }

    }
    /*
    public void trainPerceptron(double[][] inputs, int[] target, double lr, int epochs){
        int counter = 0;

        for (int j = 0;j < epochs; j++) {
            int input_length = inputs.length;

            //printWeights();
            double loss = 0.0;
            for(int i = 0; i < input_length; i++){
                for(int node = 0; node < NODES; node++){
                  loss = trainWeights(node, target[i], inputs[i], lr);

                  losses[node] += loss;

                }
                counter++;
                if(counter % epochPrint == 0){
                    //calcAverages(epochPrint, counter);

                  }
            }
        }
    }
    */

    public double trainWeights(int node, int target, double[] input, double lr,double weights){
        double rawClass = getRawClassification(node, input);
        double loss;
        if(target == node){
          loss = 1 - rawClass;
        }
        else{
          loss = 0 - rawClass;
        }
        //System.out.println(target + " - " + classified + " = " + loss);
        double sigmoidDeriv = rawClass * (1-rawClass);
        for(int i = 0; i < weights[0].length; i++){
            weights[node][i] = weights[node][i] + (lr * input[i] * loss * sigmoidDeriv);
        }

        return loss;
    }

    public void calcAverages(int num, int iter){
      System.out.println("Here are the averages for iteration " + iter + ": ");
      double average;
      for(int i = 0; i < NODES; i++){
        average = losses[i]/num;
        System.out.println(i + ": " + average);
        losses[i] = 0.0;
      }

    }

    public double getRawClassification(int node, double[] input){
        double rawClass = 0.0;
        double x = 0.0;
        for(int i = 0; i < input.length; i++){
            //System.out.println(input[i] + " x " + weights[i] + " = " + input[i]*weights[i]);
            x += input[i] * weights[node][i];
        }
        double denominator = (1+ Math.exp((-1*x)+0.5));
        double euler = Math.exp((-1*x)+0.5);
        /*
        System.out.println("the x is: " + x);
        System.out.println("the argument is: " + ((-1*x)+0.5));
        System.out.println("the denominator is: " + denominator);
        System.out.println("the euler is: " + euler);
        */
        rawClass = 1.0/(1+Math.exp((-1*x)+0.5));

        return rawClass;
    }

    public double sigmoid(double x){
      double rawClass = 0.0;
      rawClass = 1.0/(1+Math.exp((-1*x)));
      return rawClass;
    }

    public double getRawClassificationTrain(int node, double[] input){
        double rawClass = 0.0;
        double x = 0.0;
        for(int i = 0; i < input.length; i++){
            //System.out.println(input[i] + " x " + weights[i] + " = " + input[i]*weights[i]);
            x += input[i] * weights[node][i];
        }
        double denominator = (1+ Math.exp((-1*x)+0.5));
        double euler = Math.exp((-1*x)+0.5);
        /*
        System.out.println("the x is: " + x);
        System.out.println("the argument is: " + ((-1*x)+0.5));
        System.out.println("the denominator is: " + denominator);
        System.out.println("the euler is: " + euler);
        */
        rawClass = 1.0/(1+Math.exp((-1*x)));

        return rawClass;
    }

   /*
    * This method simply is a helper method for debugging purposes
    * it prints out the weights for the NN
    */
    public void printWeights(){
        for(int i = 0; i < weights.length; i++){
            System.out.println("Here's weights for node: "  + i);
            for(int j = 0; j < weights[0].length; j++){
                System.out.println("input " + j + " is: " + weights[i][j]);
            }
        }
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void initializeWeights(){
        double weight;
        // initializing the weights for the input to hidden layer
        for(int i = 0; i < inputToHiddenW.length; i++){
            for(int j = 0; j < inputToHiddenW[0].length; j++){
              weight = rand.nextDouble();
              if(weight > 0.5){
                weight = rand.nextDouble() * 0.15;
                //System.out.println(" Initialize!: " + weight);
                inputToHiddenW[i][j] = weight;
              }
              else if (weight < -0.5){
                weight = rand.nextDouble() * -0.15;
                //System.out.println(" Initialize!: " + weight);
                inputToHiddenW[i][j] = weight;
              }
            }
        }
        // this initializes the weights from the hiddne layer to the output layer
        for(int i = 0; i < hiddenToOutputW.length; i++){
            for(int j = 0; j < hiddenToOutputW[0].length; j++){
              weight = rand.nextDouble();
              if(weight > 0.5){
                weight = rand.nextDouble() * 0.15;
                //System.out.println(" Initialize!: " + weight);
                hiddenToOutputW[i][j] = weight;
              }
              else if (weight < -0.5){
                weight = rand.nextDouble() * -0.15;
                //System.out.println(" Initialize!: " + weight);
                hiddenToOutputW[i][j] = weight;
              }
            }
        }
    }

    public void evaluatePerceptron(double[][] test, int[] target){
      double max = 0.0;
      double classification;
      int theClass = -1;
      int numCorrect = 0;
      int numIncorrect = 0;
      for(int i = 0; i < test.length; i++){
        for(int node = 0; node < NODES; node++){
          classification = getRawClassification(node, test[i]);
          if(classification > max){
            max = classification;
            theClass = node;
          }
        }

        if(theClass == target[i]){
          numCorrect++;
        }
        else{
          numIncorrect++;
        }

      }

      System.out.println("Here's how many it got right: " + numCorrect);
      System.out.println("Here's how many it got wrong: " + numIncorrect);
    }
}
