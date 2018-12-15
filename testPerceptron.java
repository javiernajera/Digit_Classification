
/**
 * Write a description of class testPerceptron here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class testPerceptron
{
    // instance variables - replace the example below with your own


    /**
     * Constructor for objects of class testPerceptron
     */
    public static void main(String[] args) {
        String trainDoc= args[0];

        String docType = args[1];
        Input input = new Input(trainDoc, docType, false);
        double[][] in = input.getInputs();
        int inputSize = in[0].length;
        int[] target = input.getTargets();

        //Input testInput = new Input(testDoc, docType, true);

      //  double[][] testIn = testInput.getTestInputs();
      //  int[] testTargets = testInput.getTestTargets();

        for(int i = 0; i < in.length; i++){
            for(int j = 0; j < in[0].length; j++){

                System.out.print(in[i][j] + " ");


            }
            System.out.println("This is input : " + i);
            System.out.println("And this is the class: " + target[i]);
            System.out.println("Next Example");
        }




        //MultiOutputPerceptron percy = new MultiOutputPerceptron(inputSize);
        //Perceptron percy = new Perceptron(inputSize);
        //System.out.println("perceptron created!");

        //percy.trainPerceptron(in, target, 0.01, 200);
        //percy.evaluatePerceptron(testIn, testTargets);

    }


}
