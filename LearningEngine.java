package recognition;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class LearningEngine {

    NeuronLayer[] neuronLayers;
    IdealSet[] idealSet = new IdealSet[10];


    public LearningEngine(int[] neuronsNumber) {

        idealSet[0] = new IdealSet(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        idealSet[1] = new IdealSet(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0});
        idealSet[2] = new IdealSet(new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0});
        idealSet[3] = new IdealSet(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0});
        idealSet[4] = new IdealSet(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0});
        idealSet[5] = new IdealSet(new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0});
        idealSet[6] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0});
        idealSet[7] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0});
        idealSet[8] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0});
        idealSet[9] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1});

        this.neuronLayers = new NeuronLayer[neuronsNumber.length];

        neuronLayers[0] = new NeuronLayer(neuronsNumber[0], 784);
        for (int i = 1; i < neuronsNumber.length; i++) {
            neuronLayers[i] = new NeuronLayer(neuronsNumber[i], neuronsNumber[i - 1]);
        }
    }

    public LearningEngine(NeuronLayer[] learnedLayers) {

        idealSet[0] = new IdealSet(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        idealSet[1] = new IdealSet(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0});
        idealSet[2] = new IdealSet(new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0});
        idealSet[3] = new IdealSet(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0});
        idealSet[4] = new IdealSet(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0});
        idealSet[5] = new IdealSet(new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0});
        idealSet[6] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0});
        idealSet[7] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0});
        idealSet[8] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0});
        idealSet[9] = new IdealSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1});

        this.neuronLayers = learnedLayers;
    }


    public void learn() throws IOException {

        final double target = 0.12;
        double globalError = 10.0;

        int iter = 0;

        while (globalError > target && iter < 5) {

            iter++;
            Random random = new Random();

            double totalError = 0;
            int g = 0;
            while (g < (120000 - 20000 * iter)) {
                g++;

                if (g % (6000 - (1000 * iter)) == 0) {
                    System.out.println("Learning... iteration completed for " + g / (1200 - (200 * iter)) + "%");
                    System.out.println("Current global error value is :" + totalError / g);
                    System.out.println("\n * * * * * * * * * * * * * * * * \n");
                }

                int number = 0;
                if (g % 100 < 60) {
                    number = (g % 10) * 6000 + random.nextInt(6000) + 1;
                }
                 else if ((g % 100 >= 60) && (g % 100 < 80)) {
                    number = 60000 + (g % 10) * 1000 + random.nextInt(1000) + 1;
                }
                else if ((g % 100) >= 80) {
                    number = 70001 + (g % 10);
                }

                int copyOfNumber = number;
                String fileName = "00000";
                while (copyOfNumber != 0) {
                    copyOfNumber = copyOfNumber / 10;
                    fileName = fileName.replaceFirst("0", "");
                }
                fileName = fileName.concat(Integer.toString(number));

                double[] pix = new double[785];

                String pathname = "C:\\Users\\Consolomon\\IdeaProjects\\Digit Recognition\\data\\data\\" + fileName + ".txt";
                Scanner in = SerializationUtils.read(pathname);

                for (int i = 0; i < 784; i++) {
                    pix[i] = in.nextInt() != 0 ? 1 : 0;
                }
                pix[784] = 1;
                number = in.nextInt();

                int predictedNumber = recognition(pix);

                double error = findGlobalError(idealSet[number].ideal);
                totalError += error;

                backDrawing(idealSet[number].ideal, 1.8 / (iter + 0.8));

                if (g % (12000 - (2000 * iter)) < 10) {

                    System.out.println("Generation number is " + g);
                    System.out.println("Number source is file: " + fileName + ".txt");
                    System.out.println("Current number number is " + number);
                    System.out.println("Recognized number is " + predictedNumber);
                    System.out.println("The error value is :" + error + "\n **************** \n ");

                }
            }

            globalError = 1 - accuracy();
            System.out.println(" Prediction accuracy is: " + Math.round((1 - globalError) * 100) + "%");
            System.out.println("\n * * * * * * * * END OF ITERATION " + iter + " * * * * * * * * \n");
        }
        System.out.println("Total iterations number is : " + iter);

        String pathname = "C:\\Users\\Consolomon\\IdeaProjects\\Digit Recognition\\Digit Recognition\\task\\src\\recognition\\weights.data";
        SerializationUtils.serialize(neuronLayers, pathname);

    }

    public double accuracy() throws IOException {

        int domainSize = 10000;
        int g = 60000;

        int correct = 0;
        double globalError = 0;

        while (g < 70000) {

            if (g % 500 == 0) {
                System.out.println("Checking... completed for " + (g % 60000) / 100 + "%");
            }
            g++;
            int number = g;
            String fileName = "00000";
            while (number != 0) {
                number = number / 10;
                fileName = fileName.replaceFirst("0", "");
            }
            fileName = fileName.concat(Integer.toString(g));

            double[] pix = new double[785];

            String pathname = "C:\\Users\\Consolomon\\IdeaProjects\\Digit Recognition\\data\\data\\" + fileName + ".txt";
            Scanner in = SerializationUtils.read(pathname);

            for (int i = 0; i < 784; i++) {
                pix[i] = in.nextInt() != 0 ? 1 : 0;
            }
            pix[784] = 1;
            number = in.nextInt();

            int predictedNumber = recognition(pix);
            globalError += findGlobalError(idealSet[number].ideal);

            correct += number == predictedNumber ? 1: 0;
        }

        globalError = globalError / domainSize ;

        System.out.println("Correct answers :" + correct + " / " + domainSize);
        System.out.println("Average error is: " + globalError);

        return correct * 1.0 / domainSize;
    }

    public void guessing(String testFileName) throws IOException {

        Scanner in = SerializationUtils.read(testFileName);

        double[] pix = new double[785];
        for (int i = 0; i < 784; i++) {
            pix[i] = in.nextInt() != 0 ? 1 : 0;
        }
        pix[784] = 1;

        int predictedNumber = recognition(pix);
        System.out.println("This number is: " + predictedNumber);
        in.close();
    }

    public int recognition(double[] firstInput) {

        double[] input = Arrays.copyOf(firstInput, firstInput.length);
        int l = neuronLayers.length;

        for (int k = 0; k < l; k++) {

            int b = neuronLayers[k].neurons.length;
            b = k < l - 1 ? b - 1 : b;

            for (int i = 0; i < b; i++) {
                neuronLayers[k].neurons[i].output(input);
            }

            if (k  < l - 1) {
                neuronLayers[k].neurons[b].output = 1;
            }

            input = neuronLayers[k].getOutputArray();
        }

        double max = 0;
        int number = 0;

        for (int i = 0; i < input.length; i++) {

            number = input[i] > max ? i : number;
            max = Math.max(max, input[i]);
        }
        return number;
    }

    private double findGlobalError(double[] ideal) {

        double[] output = neuronLayers[neuronLayers.length - 1].getOutputArray();
        double globalError = 0;

        for (int i = 0; i < ideal.length; i++) {
            globalError += Math.pow(ideal[i] - output[i], 2);
        }
        globalError = Math.sqrt(globalError / ideal.length);
        return globalError;
    }

    private void backDrawing(double[] firstIdeal, double stepSize) {

        double[]ideal = Arrays.copyOf(firstIdeal, firstIdeal.length);
        int l = neuronLayers.length - 1;

        for (int k = l ; k >= 0; k--) {
            for (int i = 0; i < neuronLayers[k].neurons.length; i++) {
                double error = ideal[i] - neuronLayers[k].neurons[i].output;
                neuronLayers[k].neurons[i].learning(error);
            }

            ideal = findNextIdeal(neuronLayers[k].neurons, k == l - 1, stepSize);
        }
    }

    private double[] findNextIdeal(Neuron[] currentLayer, boolean isLast, double stepSize) {

        NeuronLayer outLayer = new NeuronLayer(currentLayer, isLast);

        int inLayerSize = outLayer.neurons[0].weights.length;
        double[] ideal = new double[inLayerSize];

        for (Neuron o : outLayer.neurons) {
            o.reSigmoid();
        }

        double nextIdeal = 0;
        for(int i = 0; i < inLayerSize; i++) {
            for (Neuron o : outLayer.neurons) {
                nextIdeal += stepSize * o.weights[i] * o.derivOfSig * o.error;
            }
            ideal[i] = nextIdeal + outLayer.neurons[0].input[i];
        }

        return ideal;
    }

}
