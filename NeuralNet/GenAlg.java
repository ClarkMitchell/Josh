import java.util.*;
import java.lang.Math;

public class GenAlg{
    private int generation;
    private int popSize;
    private int chromoLength;
    private int fittestGenome;
    private int totalFitness;
    private int totalFitnessSquared;
    private int bestFitness;
    private int worstFitness;
    private double averageFitness;
    private double mutationRate;
    private double crossoverRate;
    private Genome[] vecPopulation;
    private Bot[] elite;
    private int[] fits;
    private static Random random;

    public GenAlg(int popSize, double mutationRate, double crossoverRate, int numWeights){
        this.popSize = popSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        chromoLength = numWeights;
        random = new Random();
        fits = new int[Params.NUM_ELITE];

        vecPopulation = new Genome[popSize];

        for(int i = 0; i < popSize; i++){
            vecPopulation[i] = new Genome();
        }

    Genome crossover(Genome mother, Genome father){
        int splitLocation = random.nextInt(chromoLength);
        Genome result = new Genome();

        for(int i = 0; i < chromoLength; i++){
            if(i < splitLocation)
                result.vecWeights[i] = mother.vecWeights[i];
            else
                result.vecWeights[i] = father.vecWeights[i];
        }

        result.fitness = (int)averageFitness;
        return result;
    }

    Genome mutate(Genome chromo){
        Genome result = chromo;
        result.vecWeights[random.nextInt(chromoLength)] = random.nextDouble();
        result.fitness = (int)averageFitness;
        return result;
    }

    Genome getChromoRoulette(){
        int index = random.nextInt(totalFitnessSquared + 1);

        for(int i = 0; i < Params.NUM_ELITE; i++){
            if(index <= fits[i])
                return new Genome(elite[i].getGenome());
        }

        System.out.println("Something went wrong in getChromoRoulette");
        return new Genome();
    }

    void calcBestWorstAverageTotal(){
        bestFitness = 0;
        worstFitness = 0;
        totalFitness = 0;
        totalFitnessSquared = 0;

        for(int i = 0; i < Params.NUM_ELITE; i++){
            int currentFit = elite[i].getFitness();

            totalFitness += currentFit;
            totalFitnessSquared += Math.pow(currentFit, 3);
            fits[i] = totalFitnessSquared;

            if(currentFit > bestFitness){
                bestFitness = currentFit;
                fittestGenome = i;
            }

            if(currentFit < worstFitness)
                worstFitness = currentFit;

        }

        averageFitness = totalFitnessSquared / Params.NUM_ELITE;
    }

    public Genome[] epoch(Bot[] elite){
        this.elite = elite;
        calcBestWorstAverageTotal();
        System.out.println("Total: " + totalFitness);
        System.out.println("New Epoch: ");
        Genome[] newPop = new Genome[popSize];
        Genome selected;

        for(int i = 0; i < popSize; i++){
            selected = getChromoRoulette();

            if(random.nextDouble() < crossoverRate){
                selected = crossover(selected, getChromoRoulette());
                System.out.print("-cross-");
            }

            if(random.nextDouble() < mutationRate){
                mutate(selected);
                System.out.print("-mutate-");
            }

            newPop[i] = selected;
            System.out.print(" " + newPop[i].fitness);
        }

        vecPopulation = newPop;
        return vecPopulation;
    }

    public Genome[] getChromos(){
        return vecPopulation;
    }

    public double getAverageFitness(){
        return averageFitness;
    }

    public double getBestFitness(){
        return bestFitness;
    }
}