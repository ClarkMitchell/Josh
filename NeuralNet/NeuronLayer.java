
public class NeuronLayer {

	int numNeurons;													// number of neurons per layer
	Neuron[] vecNeurons;											// array/vector of neurons
	
	public NeuronLayer(int numNeurons, int numInputsPerNeuron){		// Constructor initilizes the array
		this.numNeurons = numNeurons;								// with neurons, which have the given
		vecNeurons = new Neuron[numNeurons];						// number of inputs.
		
		for(int i = 0; i < numNeurons; i++){
			vecNeurons[i] = new Neuron(numInputsPerNeuron);
		}
	}
}
