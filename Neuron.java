package recognition;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Neuron implements Serializable {

    private final double NU = 0.5;
    double[] weights;
    double[] input;
    double output;
    double derivOfSig;
    double error;

    public Neuron(int inputSize) {

        this.weights = new double[inputSize];
        this.input = new double[inputSize];
        Random random = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextGaussian();
        }
    }
    public Neuron(Neuron original) {

        this.weights = Arrays.copyOf(original.weights, original.weights.length);
        this.input = Arrays.copyOf(original.input, original.input.length);
        this.output = original.output;
        this.derivOfSig = original.derivOfSig;
        this.error = original.error;
    }

    public void output(double[] input) {

        double output = 0;
        for (int i = 0; i < weights.length; i++) {
            output += input[i] * weights[i];
        }

        output = sigmoid(output);

        this.output = output;
        this.input = input;
    }

    public void reSigmoid() {

        double output = 0;
        for (int i = 0; i < weights.length; i++) {
            output += input[i] * weights[i];
        }
        derivOfSig = derivOfSigmoid(output);
        output = sigmoid(output);

        this.error = output - this.output;
        this.output = output;
    }
    public void learning(double error) {

        for (int i = 0; i < weights.length; i++) {
            double deltaW = NU * error * input[i] * output * (1 - output);
            weights[i] += deltaW;
        }
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    private double derivOfSigmoid(double x) {
        return Math.pow(Math.E, x) / Math.pow(1 + Math.pow(Math.E, x), 2);
    }


}
