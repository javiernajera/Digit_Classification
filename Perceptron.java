
/**
 * Write a description of class Perceptron here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.lang.*;
import java.util.Random;
 
public class Perceptron
{
    // instance variables - replace the example below with your own
    private int inputSize;
    private double[] weights;
    private int epochPrint = 100;
    private Random rand = new Random();
    
    /**
     * Constructor for objects of class Perceptron
     */
    public Perceptron(int inputSize)
    {
        this.inputSize = inputSize;
        weights = new double[inputSize];
       
        initializeWeights();
    }
    
    public void trainPerceptron(int[][] inputs, int[] target, double lr){
        int epochs = inputs.length;
        double averageLoss = 0.0;
        int counter = 0;
        double loss = 0.0;
        for(int i = 0; i < epochs; i++){
            
            loss = trainWeights(target[i], inputs[i], lr);
            
            averageLoss += loss;
            counter++;
            if(counter % epochPrint == 0){
                averageLoss = averageLoss / 100.0;
                System.out.println("Here is the average loss after "+ i +" epochs: " + averageLoss);
                averageLoss = 0.0;
                counter = 0;
            }
        }
        
    }

    public double getRawClassification(int[] input){
        double rawClass = 0.0;
        double x = 0.0;
        for(int i = 0; i < input.length; i++){
            //System.out.println(input[i] + " x " + weights[i] + " = " + input[i]*weights[i]); 
            x += input[i] * weights[i]; 
        }
        double denominator = (1+ Math.exp((-1*x)+0.5));
        double euler = Math.exp((-1*x)+0.5);
        
        System.out.println("the x is: " + x);
        System.out.println("the argument is: " + ((-1*x)+0.5));
        System.out.println("the denominator is: " + denominator);
        System.out.println("the euler is: " + euler);
        
        rawClass = 1.0/(1+Math.exp((-1*x)+0.5));
        
        return rawClass;
    }
    
    public double trainWeights(int target, int[] input, double lr){
        
        double rawClass = getRawClassification(input);
        double loss = target - rawClass;
        System.out.println(target + " - " + rawClass + " = " + loss);
        double sigmoidDeriv = rawClass * (1-rawClass);
        for(int i = 0; i < weights.length; i++){
            weights[i] = weights[i] + (lr * input[i] * loss * sigmoidDeriv);
        }
        
        return loss;
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void initializeWeights(){
        for(int i = 0; i < weights.length; i++){
            weights[i] = rand.nextDouble() * 0.15;
        }
    }
}
