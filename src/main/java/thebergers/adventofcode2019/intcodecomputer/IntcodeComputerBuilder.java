package thebergers.adventofcode2019.intcodecomputer;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class IntcodeComputerBuilder extends AbstractIntcodeComputerBuilder<IntcodeComputerBuilder> {

	public static IntcodeComputerBuilder newInstance() {
		return new IntcodeComputerBuilder();
	}
	
	@Override
	public IntcodeComputerBuilder setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
		return me();
	}
	
	@Override
	public IntcodeComputerBuilder setName(String name)  {
		this.name = name;
		return me();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IntcodeComputerBuilder setProgram(String program) {
		this.program = program;
		return me();
	}
	
	@Override
	public String getProgram() {
		return program;
	}
	
	@Override
	public IntcodeComputerBuilder addInput(Long input) {
		if (CollectionUtils.isEmpty(this.initialInput)) {
			this.initialInput = new LinkedList<>();
		}
		this.initialInput.add(input);
		return me();
	}
	
	@Override
	public IntcodeComputerBuilder setConnectsTo(String connectsTo) {
		this.connectsTo = connectsTo;
		return me();
	}
	@Override
	public String getConnectsTo() {
		return connectsTo;
	}

	@Override
	protected IntcodeComputerBuilder me() {
		return this;
	}
}
