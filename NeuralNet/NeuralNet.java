import java.util.*;
import java.lang.Math;

public class NeuralNet {

	private int numInputs;
	private int numOutputs;
	private int numHiddenLayers;
	private int neuronsPerHiddenLayer;
	private NeuronLayer[] vecLayers;														// vector of vectors, 
																							// this is our net.
	public NeuralNet(){
		numInputs = Params.NUM_INPUTS;
		numOutputs = Params.NUM_OUTPUTS;
		numHiddenLayers = Params.NUM_HIDDEN;
		neuronsPerHiddenLayer = Params.NEURONS_PER_HIDDEN_LAYER;
		vecLayers = new NeuronLayer[numHiddenLayers + 1];									// +1 is out input layer
		
		vecLayers[0] = new NeuronLayer(neuronsPerHiddenLayer, numInputs);					// This is our input layer
		for(int i = 1; i < numHiddenLayers; i++){
			vecLayers[i] = new NeuronLayer(neuronsPerHiddenLayer, neuronsPerHiddenLayer);	// num of neurons == num of inputs
		}
		vecLayers[numHiddenLayers] = new NeuronLayer(numOutputsm, neuronsPerHiddenLayer);	// output layer
	}
	
	double getWeights(){
		
	}
	
	int getNumberofWeights(){
		
	}
	
	void setWeights(ArrayList<Double> weights){
		
	}
	
	/**
	 *  This function does everything basically, nested for loops go from net -> layer -> neuron -> inputs
	 *  Sums all the inputs multiplied  by the corresponding weights. Last weight it the bias.
	 *  All of this is then passed through the sigmoid function to produce our output,
	 *  which is then returned via ArrayList as either the output proper or the next layers input.
	 */
	
	ArrayList<Double> update(ArrayList<Double> inputs){					// calculate outputs based on inputs #allTheMagic
		ArrayList<Double> outputs = new ArrayList<Double>();			// God thats ugly syntax
		int weight;
		
		if(inputs.size() != numInputs)
			return outputs;
		
		for(int i = 0; i < numHiddenLayers + 1; i++){					// do this for each layer of neurons
			if(i > 0)
				inputs = new ArrayList<Double>(outputs);				// Last layers outputs are this layers inputs
			
			outputs.clear();
			weight = 0;
			
			for (int j = 0; j < vecLayers[i].numNeurons; j++){			// loop for the # of neurons in THIS (ith) layer.
				double netInput = 0;
				int numInputs = vecLayers[i].vecNeurons[j].numInputs;	// get the # of inputs for this neuron.
				
				for (int k = 0; k < numInputs - 1; k++){				// sum the weights
					netInput += vecLayers[i].vecNeurons[j].vecWeights[k] * inputs.get(weight++);
				}
				
				netInput += vecLayers[i].vecNeurons[j].vecWeights[numInputs - 1] * Params.BIAS;
				outputs.add(sigmoid(netInput, Params.ACTIVATION_RESPONSE));
				weight = 0;
			}
		}
		return outputs;													// the outputs of this layer
	}

	static double sigmoid(double activation, double response){			// sigmoid is the thresholds filter, looks like -> º
		return 1/(1 + Math.exp(-activation/response));
	}
	
}
