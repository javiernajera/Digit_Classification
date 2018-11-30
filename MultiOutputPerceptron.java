
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
    private double[][] weights;
    private int epochPrint = 100;
    private Random rand = new Random();
    private static int NODES = 10;
    private double[] losses = new double[NODES];

    /**
     * Constructor for objects of class Perceptron
     */
    public MultiOutputPerceptron(int inputSize)
    {
        this.inputSize = inputSize;
        weights = new double[NODES][inputSize];
        initializeWeights();
    }

    public void trainPerceptron(int[][] inputs, int[] target, double lr){
        int epochs = inputs.length;

        int counter = 0;
        double loss = 0.0;
        for(int i = 0; i < epochs; i++){
          for(int node = 0; node < NODES; node++){
              loss = trainWeights(node, target[i], inputs[i], lr);
              //printWeights();
              losses[node] += loss;

            }
            counter++;
            if(counter % epochPrint == 0){
                calcAverages(epochPrint, i);
                counter = 0;
              }
        }

    }
    public double trainWeights(int node, int target, int[] input, double lr){
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

    public double getRawClassification(int node, int[] input){
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



    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void initializeWeights(){
        double weight;
        for(int i = 0; i < NODES; i++){
            for(int j = 0; j < weights[0].length; j++){
              if(rand.nextDouble() > 0.5){
                weight = rand.nextDouble() * 0.15;
                //System.out.println(" Initialize!: " + weight);
                weights[i][j] = weight;
              }
              else{
                weight = rand.nextDouble() * -0.15;
                //System.out.println(" Initialize!: " + weight);
                weights[i][j] = weight;
              }
            }
        }
    }

    public void evaluatePerceptron(int[][] test, int[] target){
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
