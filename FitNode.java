
public class FitNode {

	int popIndex;
	double fitness;
	
	// No argument constructor that sets popIndex and fitness to 0;
	public FitNode() {
		popIndex = 0;
		fitness = 0;
	}
	
	// Constructor that that makes a fitnode obj with the given popIndex  
	// and fitness.
	public FitNode(int popIndex, double fitness) {
		this.popIndex = popIndex;
		this.fitness = fitness;
	}
	
	// Getter that returns the popIndex.
	public int GetPopIndex() {
		return popIndex;
	}
	
	// Getter that returns the fitness.
	public double GetFitness() {
		return fitness;
	}
	
	// Setter that sets the popIndex
	public void SetPopIndex(int popIndex) {
		this.popIndex = popIndex;
	}
	
	// Setter that sets the fitness
	public void SetFitness(int fitness) {
		this.fitness = fitness;
	}
	
}
