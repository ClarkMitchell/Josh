import java.util.*;

public class Genome {

	double[] vecWeights;
	int fitness;
	
	public Genome(){
		vecWeights = new double[Params.NUM_WEIGHTS];
		Random random = new Random();
		for(int i = 0; i < vecWeights.length; i++)
			vecWeights[i] = random.nextDouble();
		fitness = 0;
	}
	
	public Genome(double[] weight, int fitness){
		vecWeights = weight;
		this.fitness = fitness;
	}
	
	public Genome(Genome other){
		vecWeights = other.vecWeights;
		fitness = other.fitness;
	}
	
}
