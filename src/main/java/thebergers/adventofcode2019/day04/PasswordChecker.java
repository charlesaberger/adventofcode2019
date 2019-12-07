package thebergers.adventofcode2019.day04;

import java.util.stream.IntStream;

public class PasswordChecker {

	public static void main(String[] args) {
		Integer rangeStart = 206938;
		Integer rangeEnd = 679128;
		PasswordChecker checker = new PasswordChecker(rangeStart, rangeEnd);
		long matches = checker.countMatches();
		System.out.format("Found %d matches", matches);
	}

	private final Integer rangeStart;
	
	private final Integer rangeEnd;
	
	private final PasswordValidator passwordValidator;
	
	public PasswordChecker(Integer rangeStart, Integer rangeEnd) {
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;
		this.passwordValidator = new PasswordValidator(rangeStart, rangeEnd);
	}
	
	private long countMatches() {
		return 
			IntStream.range(rangeStart, rangeEnd + 1)
			.filter(password -> passwordValidator.validatePassword(password))
			.count();
	}
}
