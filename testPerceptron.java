
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
        String docName = args[0];
        String docType = args[1];
        Input input = new Input(docName, docType);
        int[][] in = input.getInputs();
        int inputSize = in[0].length;
        int[] target = input.getTargets();
        /*
        for(int i = 0; i < 3823; i++){
            for(int j = 0; j < 1024; j++){
                
                System.out.print(in[i][j]);
                if((j+1) % 32 == 0){
                    System.out.println("");
                }
                
            }
            System.out.println("This is input : " + i);
            System.out.println("Next Example");
        }
        System.out.println("Here's the targets");
        for(int i : target){
            System.out.println(i);
        }
        */
        Perceptron percy = new Perceptron(inputSize);
        percy.trainPerceptron(in, target, 0.001);
    }
    
    
}
