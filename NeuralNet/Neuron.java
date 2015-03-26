import java.util.*;

public class Neuron {

	int numInputs;										// number of inputs to each neuron
	double[] vecWeights;								// the weight of each connection
	
	public Neuron(int numInputs){
		
		vecWeights = new double[numInputs + 1];
		Random random = new Random();
		
		this.numInputs = numInputs + 1;					/**
														 *  Additional weight is the bias (the threshold of neuron firing). 
														 *  Considering it a weight
														 *  allows us to evolve it just like a normal weight.
														 */
		
		
		for(int i = 0; i < numInputs; i++){				// Initialize the weights to random numbers.
			vecWeights[i] = random.nextDouble();
		}
	}
}
