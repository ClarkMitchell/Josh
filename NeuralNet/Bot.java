import java.util.*
import java.lang.Math;

public class Bot implements Comparable<Bot>{
    private Genome genome;
    private neuralNet brain;
    private static ArrayList<Resource> vecResources;
    ArrayList<Double> neuralInput;
    private double x;
    private double y;
    private double direction;
    private int fitness;
    public int thisFitness;
    private int rateRatio;
    public boolean isElite;

    public Bot(int x, int y Genome genome){
        this.x = x;
        this.y = y;
        direction = 0;
        this.genome = genome;
        brain = new neuralNet();
        neuralInput = new ArrayList<Double>();
        fitness = 0;
        thisFitness = 0;
        isElite = false;
    }

    public boolean update(ArrayList<Resource> resources, int ratio){
        vecResources = resources;
        rateRatio = ratio;
        Resource closestResource = getClosestResource();

        neuralInput.add((double)(closestResource.x - x));
        neuralInput.add((double)(closestResource.y - y));
        neuralInput.add((double)(Math.cos(direction)));
        neuralInput.add((double)(Math.sin(direction)));

        ArrayList<Double> outputs = brain.update(neuralInput);

        direction += (rightTread - leftTread) * Params.MAX_TURN_RATE * rateRatio;

        neuralInput.clear();

        if(x < 0)
            x = (double).Params.FIELD_WIDTH;
        if(y < 0)
            y = (double).Params.FIELD_HEIGHT;
        if(x > Params.FIELD_WIDTH)
            x = 0;
        if(y > Params.FIELD_HEIGHT)
            y = 0;

        return true;
    }

    public void reset(){
        fitness = 0;
    }

    public void putWeights(Genome genome){
        genome.vecWeights = genome.vecWeights;
        fitness = genome.fitness;
        thisFitness = 0;
    }

    public void moveForward(double units){
        x += Math.cos(direction) * units;
        y += Math.sin(direction) * units;
    }

    public Resource getClosestResource(){
        int best = 0;
        double distance = vecResources.get(0).getDistance(x, y);
        for(int i = 1; i < vecResources.size(); i++){
            if(vecResources.get(i).getDistance(x, y) < distance){
                distance = vecResources.get(i).getDistance(x, y);
                best = i;
            }
        }
        return vecResources.get(best);
    }

    public int checkForResource(ArrayList<Resource> vecResources, int resourceScale){
        for(int i = 0; i < vecResources.size(); i++){
            if(Math.abs(vecResources.get(i).x - x) < resourceScale + Params.BOT_SCALE / 2 && Math.abs(vecResources.get(i).y - y) < resouceScale + Params.BOT_SCALE / 2)
                return i;
        return -1;
        }
    }

    public int compareTo(Bot other){
        return fitness - other.fitness;
    }

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }

    public int getAngle(){
        return (int)direction;
    }

    public int getFitness(){
        return fitness;
    }

    public Genome getGenome(){
        return genome;
    }

    public void incrementFitness(){
        fitness++;
        thisFitness++;
    }
}