
/**
 * Write a description of class Perceptron here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.lang.*;
import java.util.Random;

public class EvolvingWeights
{
    // instance variables - replace the example below with your own
    private int inputSize;
    private double[][] inputToHiddenW;
    private double[][] hiddenToOutputW;
    private int print = 100;
    private Random rand = new Random();
    private static int NODES = 6;
    private double[] losses = new double[NODES];
    private double[][] hVec;

    /**
     * Constructor for objects of class Perceptron
     */
    public EvolvingWeights(int inputSize)
    {
        this.inputSize = inputSize;

        inputToHiddenW = new double[inputSize*2][inputSize];
        hiddenToOutputW = new double[NODES][inputSize*2];

    }

    public void printArray(WeightSet[] set){

      for(int i = 0; i < set.length; i++){
        System.out.println(set[i].getFitness());
      }

    }
    // this method does feed forward and backprop for all training inputs for a
    // given number of epochs
    public WeightSet evolvingNN(double[][] inputs, int[] target, int popSize, double crossProb, double mProb, double mAmt, int generations){
        int counter = 0;
        Random rand = new Random();
        double totalLoss= 0.0;
        WeightSet[] population = new  WeightSet[popSize];
        WeightSet[] parents = new WeightSet[2];
        WeightSet[] children = new WeightSet[2];

        for(int i = 0; i < population.length; i++){
          population[i] = new WeightSet(inputSize, NODES);
        }


        for(int i = 0; i < inputs.length; i++){


          for(int generation = 0; generation < generations; generation++){
              WeightSet[] newPop = new WeightSet[popSize];
              int count = 0;

              WeightSet[] sortedByRank = returnSortedPopByFitnesses(population, inputs[i], target[i]);
              printArray(sortedByRank);
              System.out.println(sortedByRank[sortedByRank.length - 1].getFitness() + " is the loss of best WS in generation " + generation);
              while(count < popSize){

                parents = rankSelection(sortedByRank, inputs[i], target[i]);

                if(rand.nextDouble() < crossProb){
                  children = crossover(parents);
                }
                else{
                  children[0] = parents[0];
                  children[1] = parents[1];
                }
                newPop[count] = children[0];
                count++;
                if(count < popSize){
                  newPop[count] = children[1];
                  count++;
                }

              }
              newPop = mutate(newPop, mProb, mAmt);


          }

        }

        return population[0];
    }


    public WeightSet[] mutate(WeightSet[] ws, double mProb, double amt){
      double[][] hid = ws[0].getHiddenWeights();
      double[][] out = ws[0].getOutputWeights();
      Random rand = new Random();
      double[][] hidWeights;
      double[][] outWeights;
      for(WeightSet w : ws){
        hidWeights = w.getHiddenWeights();
        outWeights = w.getOutputWeights();
        for(int i = 0; i < hid.length; i++){
          for(int j = 0; j < hid[0].length; j++){
            if(rand.nextDouble() < mProb){
              if(rand.nextDouble() < 0.5){
                hidWeights[i][j] += amt;
              }
              else{
                hidWeights[i][j] += (amt * -1);
              }
            }
          }
        }

        for(int i = 0; i < out.length; i++){
          for(int j = 0; j < out[0].length; j++){
            if(rand.nextDouble() < mProb){
              if(rand.nextDouble() < 0.5){
                outWeights[i][j] += amt;
              }
              else{
                outWeights[i][j] += (amt * -1);
              }
            }
          }
        }
        w.setWeights(hidWeights, outWeights);
      }
      return ws;
    }
    public WeightSet[] crossover(WeightSet[] weights){
      Random rand = new Random();
      double[][] fParentHidWeights = weights[0].getHiddenWeights();
      double[][] sParentHidWeights = weights[1].getHiddenWeights();
      double[][] fParentOutWeights = weights[0].getOutputWeights();
      double[][] sParentOutWeights = weights[1].getOutputWeights();
      double[][] buffer = fParentHidWeights;
      int crossoverPoint = rand.nextInt(fParentHidWeights.length);
      WeightSet[] children = new WeightSet[2];
      for(int i = 0; i < fParentHidWeights.length; i++){
        if(i < crossoverPoint){
          fParentHidWeights[i] = sParentHidWeights[i];
        }
        else{
          sParentHidWeights[i] = fParentHidWeights[i];
        }
      }

      for(int i = 0;i < fParentOutWeights.length; i++){
        int newPoint = (crossoverPoint % 5) + 1;
        if(i < newPoint){
          fParentOutWeights[i] = sParentOutWeights[i];
        }
        else{
          sParentOutWeights[i] = fParentOutWeights[i];
        }
      }
      children[0] = new WeightSet(fParentHidWeights, fParentOutWeights);
      children[1] = new WeightSet(sParentHidWeights, sParentOutWeights);
      return children;


    }
    public WeightSet[] returnSortedPopByFitnesses(WeightSet[] weights, double[] in, int target){
      double fitness = 0.0;
      for(WeightSet ws : weights){
        fitness = ws.calcFitness(in, target);
        ws.setFitness(fitness);
      }
      WeightSet temp;
      for (int j = 0; j < weights.length; ++j) {
          for (int k = j+1; k < weights.length; ++k) {
              if (weights[j].getFitness() < weights[k].getFitness()) {
                  temp = weights[k];
                  weights[k] = weights[j];
                  weights[j] = temp;
              }
          }
      }
      return weights;
    }
    //pop should be sorted by fitness in paramter
    public WeightSet[] rankSelection(WeightSet[] pop, double[] input, int target) {

        Random rand = new Random();

        int index, count = 0;
        double prob, sumRanks = 0.0;

        WeightSet[] selected = new WeightSet[2];

        for (int x = 0; x < pop.length; x++) {
            sumRanks += x;
        }

        while (count < 2) {
            index = rand.nextInt(pop.length);
            prob = rand.nextDouble();
            if (prob < (index/sumRanks)) {
                selected[count] = pop[index];
                ++count;
            }
        }
        return selected;
    }

}

class WeightSet{
    private double[][] hiddenWeightSet;
    private double[][] outputWeightSet;
    private double fitness;
    private Random rand = new Random();

    public WeightSet(int num, int classes){
      hiddenWeightSet = new double[num*2][num];
      outputWeightSet = new double[classes][num*2];
      initializeWeightSet();
    }


    public WeightSet(double[][] hid, double[][] out){
      hiddenWeightSet = hid;
      outputWeightSet = out;
    }

    public double[][] getHiddenWeights(){
      return hiddenWeightSet;
    }

    public double[][] getOutputWeights(){
      return outputWeightSet;
    }

    public double getFitness(){
      return fitness;
    }

    public void setWeights(double[][] hid, double[][] out){
      hiddenWeightSet = hid;
      outputWeightSet = out;
    }

    public void setHiddenWeights(double[][] hid){
      hiddenWeightSet = hid;
    }

    public void setOutputWeights(double[][] out){
      outputWeightSet = out;
    }

    public void setFitness(double fit){
      fitness = fit;
    }

    public void printWeights(){
      for(int i = 0; i < hiddenWeightSet.length; i++){
        System.out.println("Weights for hidden node " + i + ": ");
        for(int j = 0; j < hiddenWeightSet[0].length; j++){
          System.out.println(hiddenWeightSet[i][j]);
        }
      }

      for(int i = 0; i < outputWeightSet.length; i++){
        System.out.println("Weights for output node " + i + ": ");
        for(int j = 0; j < outputWeightSet[0].length; j++){
          System.out.println(outputWeightSet[i][j]);
        }
      }
    }
    public double sigmoid(double x){
      double rawClass = 0.0;
      rawClass = 1.0/(1+Math.exp((-1 * x)));
      return rawClass;
    }

    public double calcFitness(double[] input, int target){
      double accum = 0.0;
      double[] hVec = new double[hiddenWeightSet.length];
        for(int i = 0; i < hiddenWeightSet.length; i++){
          for(int j = 0; j < hiddenWeightSet[0].length; j++){
            accum += input[j] * hiddenWeightSet[i][j];
          }
          hVec[i] = sigmoid(accum);
          accum = 0;
        }

        accum = 0;
        double[] outputs = new double[outputWeightSet.length];
        for(int i = 0; i < outputWeightSet.length; i++){
          for(int j = 0; j < outputWeightSet[0].length; j++){
            accum += hVec[i] * outputWeightSet[i][j];
          }
          outputs[i] = sigmoid(accum);
          accum = 0;
          //System.out.println("For class " + (i + 1) + " the system gives a score of: " + sigmoid(accum));
        }

        double[] losses = new double[outputWeightSet.length];
        for(int i = 0; i < outputWeightSet.length; i++){
          if((i+1) == target){
            losses[i] = Math.pow(1 - outputs[i], 2) * .5;
          }
          else{
            losses[i] = Math.pow(0 - outputs[i], 2) * .5;
          }
        }
        double fitness = 0.0;
        for(double l : losses){
          fitness += l;
        }
        fitness = fitness/losses.length;
        return fitness;
    }

    public void initializeWeightSet(){
        double weight;
        // initializing the weights for the input to hidden layer
        for(int i = 0; i < hiddenWeightSet.length; i++){
            for(int j = 0; j < hiddenWeightSet[0].length; j++){
              weight = rand.nextDouble();
              if(weight > 0.5){
                weight = rand.nextDouble() * 0.15;
                //System.out.println(" Initialize!: " + weight);
                hiddenWeightSet[i][j] = weight;
              }
              else{
                weight = rand.nextDouble() * -0.15;
                //System.out.println(" Initialize!: " + weight);
                hiddenWeightSet[i][j] = weight;
              }
            }
        }
        // this initializes the weights from the hiddne layer to the output layer
        for(int i = 0; i < outputWeightSet.length; i++){
            for(int j = 0; j < outputWeightSet[0].length; j++){
              weight = rand.nextDouble();
              if(weight > 0.5){
                weight = rand.nextDouble() * 0.15;
                //System.out.println(" Initialize!: " + weight);
                outputWeightSet[i][j] = weight;
              }
              else{
                weight = rand.nextDouble() * -0.15;
                //System.out.println(" Initialize!: " + weight);
                outputWeightSet[i][j] = weight;
              }
            }
        }
    }

}
