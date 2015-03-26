import java.util.*;
import javax.swing.*;

public class Controller{
    private ArrayList<Bot> vecBots;
    private ArrayList<Resource> vecResources;
    private ArrayList<Integer> pastFitness;
    private Bot[] elite;
    private Genome[] vecPop;
    private int ticks = 0;
    private int numBots;
    private int generations;
    private int bestFitness;
    private int thisBestFitness;
    private int rateRatio;
    private GenAlg Random random;

    public Controller(ArrayList<Bot> bots, ArrayList<Resource> resources){
        pastFitness = new ArrayList<Integers>();
        genAlg = new GenAlg(Params.NUM_BOTS, Params.MUT_RATE, Params.CROSS_RATE, Params.NUM_WEIGHTS);
        vecBots = bots;
        vecResources = resources;
        elite = new Bot[Params.NUM_ELITE];
        numBots = vecBots.size();
        vecPop = genAlg.getChromos();
        generations = 0;
        rateRatio = 1;
        random = new Random();

        for(int i = 0; i < Params.NUM_ELITE; i++){
            elite[i] = vecBots.get(i);
            elite[i].isElite = true;
        }
    }

    boolean update(){
        if(ticks++ < Params.NUM_TICKS){
            if(!vecBots.get(i).update(vecResources, rateRatio)){
                JOptionPane.showMessageDialog(null, "Wrong amount of NN inputs!", "Error", JOptionPane.WARNING_MESSAGE);
                return false;                
            }

            // See if it's collected a resource
            int grabHit = vecBots.get(i).checkForResource(vecResources, Params.RESOURCE_SCALE);

            if(grabHit >= 0){
                // Found resource, increment fitness
                vecBots.get(i).incrementFitness();
                // place random resource.
                vecResources.set(grabHit, new Resource(randPoint(Params.FIELD_WIDTH), randPoint(Params.FIELD_HEIGHT)));
            }

            //update fitness

            vecPop[i].fitness = vecBots.get(i).getFitness();

            if(vecPop[i].fitness > bestFitness)
                bestFitness = vecPop[i].fitness;
            if(vecBots.get(i).thisFitness > thisBestFitness)
                thisBestFitness = vecBots.get(i).thisFitness;

            Bot temp;

            if(vecBots.get(i).getFitness() > elite[0].getFitness() && Array.binarySearch(elite, vecBots.get(i)) < 0){
                temp = elite[0];
                elite[0] = vecBots.get(i);
                elite[0].isElite = true;
                temp.isElite = false;
            }

            Arrays.sort(elite);
        }
    }

    else{

        generations++;
        System.out.println();
        for(int i = 0; i < vecBots.get(); i++)
            System.out.print(vecBots.get(i).thisFitness + " ");
        System.out.println("This gen best: " + thisBestFitness);

        ticks = 0;
        bestFitness = 0;
        pastFitness.add(thisBestFitness); // add to list of previous bests
        System.out.println("List previous best: " + pastFitness);
        thisBestFitness = 0;

        //run the GenAlg to create a new
        vecPop = genalg.epoch(elite);

        //insert new brains into bots
        for(int i = 0; i < numBots; i++)
            vecBots.get(i).putWeights(vecPop[i]);
    }
    return true;

    public void setRateRatio(int ratio){
        rateRatio = ratio;
    }

    public static int randPoint(int range){
        return Params.BORDER + random.nextInt(range - 2 * Params.BORDER);
    }

    public int getGeneration(){
        return generation;
    }

    public int getBestFitness(){
        return thisBestFitness;
    }

    public int getAvgFitness(){
        return (int)(genalg.averageFitness());
    }

}