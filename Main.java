package recognition;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // write your code here

        Scanner in = new Scanner(System.in);
        int s = Integer.parseInt(in.nextLine());

        if (s == 1) {

            System.out.println("Your choose: " + s);
            System.out.println("Learning... (one eternity later...)");
            int[] neuronsNumber = new int[]{64, 16, 10};
            LearningEngine neuroGear = new LearningEngine(neuronsNumber);
            neuroGear.learn();

        } else if (s == 2) {

            System.out.println("Your choose: " + s);
            String pathname = "C:\\Users\\Consolomon\\IdeaProjects\\Digit Recognition\\Digit Recognition\\task\\src\\recognition\\weights.data";
            NeuronLayer[] neuronLayers = (NeuronLayer[]) SerializationUtils.deserialize(pathname);
            LearningEngine learnedGear = new LearningEngine(neuronLayers);

            System.out.println("The network prediction accuracy: " + learnedGear.accuracy());

        } else if (s == 3) {

            System.out.println("Your choose: " + s);
            String pathname = "C:\\Users\\Consolomon\\IdeaProjects\\Digit Recognition\\Digit Recognition\\task\\src\\recognition\\weights.data";
            NeuronLayer[] neuronLayers = (NeuronLayer[]) SerializationUtils.deserialize(pathname);
            LearningEngine learnedGear = new LearningEngine(neuronLayers);

            System.out.print("Enter filename: ");
            String testFileName = in.nextLine();
            learnedGear.guessing(testFileName);
            in.close();

        }
    }

}
