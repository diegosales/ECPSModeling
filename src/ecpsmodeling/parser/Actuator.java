package ecpsmodeling.parser;

import java.util.ArrayList;

public class Actuator {
	protected ArrayList<String> inputs;
	protected ArrayList<String> outputs;
	
	protected boolean periodic;
	protected int sampling;
	protected int priority;
	protected int period;
	protected int index;
	
	protected String protocol;
	protected String name;
	
	Actuator(){
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
	}
	
	public ArrayList<String> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList<String> inputs) {
		this.inputs = inputs;
	}

	public ArrayList<String> getOutputs() {
		return outputs;
	}

	public void setOutputs(ArrayList<String> outputs) {
		this.outputs = outputs;
	}
	
	public int getSampling() {
		return sampling;
	}

	public void setSampling(int auxSampling) {
		sampling = auxSampling;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean getPeriodic() {
		return periodic;
	}

	public void setPeriodic(boolean periodic) {
		this.periodic = periodic;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	
	
	/*public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}*/
}
