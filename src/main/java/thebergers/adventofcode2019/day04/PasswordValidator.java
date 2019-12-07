package thebergers.adventofcode2019.day04;

import java.util.function.Predicate;

public class PasswordValidator {

	private Integer rangeStart;
	
	private Integer rangeEnd;
	
	public PasswordValidator(Integer rangeStart, Integer rangeEnd) {
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;
	}

	public boolean validatePassword(Integer password) {
		return validations.test(password);
	}
	
	Predicate<Integer> checkLength = password ->  {
		String passwordStr = password.toString();
		return passwordStr.length() == 6; 
	};
	
	Predicate<Integer> withinRange = password -> {
		return password >= rangeStart && password <= rangeEnd;
	};
	
	Predicate<Integer> containsDoubleDigit = password -> {
		char[] numbers = password.toString().toCharArray();
		for (int i = 1; i < numbers.length; i++) {
			if (numbers[i] == numbers[i - 1]) {
				return true;
			}
		}
		return false;
	};
	
	Predicate<Integer> doesNotContainADecrease = password -> {
		char[] numbers = password.toString().toCharArray();
		for (int i = 1; i < numbers.length; i++) {
			if (numbers[i] < numbers[i - 1]) {
				return false;
			}
		}
		return true;
	};
	
	Predicate<Integer> validations = 
			checkLength
			.and(withinRange)
			.and(containsDoubleDigit)
			.and(doesNotContainADecrease);

}
