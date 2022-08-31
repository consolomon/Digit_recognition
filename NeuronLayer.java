package recognition;

import java.io.Serializable;

public class NeuronLayer implements Serializable {

    Neuron[] neurons;


    public NeuronLayer(int neuronsNumber, int neuronSize) {

        neurons = new Neuron[neuronsNumber];
        for (int i = 0; i < neuronsNumber; i++) {
            neurons[i] = new Neuron(neuronSize);
        }
    }

    public NeuronLayer(Neuron[] neurons, boolean isLast) {
        int n = isLast ? neurons.length - 1 : neurons.length;
        this.neurons = new Neuron[n];
        for (int i = 0; i < n; i++) {
            this.neurons[i] = new Neuron(neurons[i]);
        }
    }
    public double[] getOutputArray() {

        double[] outputArray = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputArray[i] = neurons[i].output;
        }

        return outputArray;
    }
}
