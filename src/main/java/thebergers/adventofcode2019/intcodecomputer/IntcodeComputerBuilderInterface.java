package thebergers.adventofcode2019.intcodecomputer;

public interface IntcodeComputerBuilderInterface<T> {

	T setSequenceNumber(Integer sequenceNumber);

	T setName(String name);

	String getName();

	T setProgram(String program);

	String getProgram();

	T addInput(Long input);

	T setConnectsTo(String connectsTo);

	String getConnectsTo();

	IntcodeComputerInterface build();
}
