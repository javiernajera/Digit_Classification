
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
        String testDoc = args[1];
        String docType = args[2];
        Input input = new Input(trainDoc, docType, false);
        int[][] in = input.getInputs();
        int inputSize = in[0].length;
        int[] target = input.getTargets();

        Input testInput = new Input(testDoc, docType, true);

        int[][] testIn = testInput.getTestInputs();
        int[] testTargets = testInput.getTestTargets();
        /*
        for(int i = 0; i < testIn.length; i++){
            for(int j = 0; j < 1024; j++){

                System.out.print(testIn[i][j]);
                if((j+1) % 32 == 0){
                    System.out.println("");
                }

            }
            System.out.println("This is input : " + i);
            System.out.println("Next Example");
        }

        System.out.println("Here's the targets");
        for(int i : testTargets){
            System.out.println(i);
        }
        */

        MultiOutputPerceptron percy = new MultiOutputPerceptron(inputSize);
        //Perceptron percy = new Perceptron(inputSize);
        System.out.println("perceptron created!");

        percy.trainPerceptron(in, target, 0.1);
        percy.evaluatePerceptron(testIn, testTargets);

    }


}
