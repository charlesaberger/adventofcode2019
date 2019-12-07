package thebergers.adventofcode2019.day04;

import java.util.HashMap;
import java.util.Map;
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

	Predicate<Integer> checkGroupSizes = password -> {
		char[] numbers = password.toString().toCharArray();
		Map<Character, Integer> groupSizes = new HashMap<>();
		groupSizes.put(Character.valueOf(numbers[0]), 1);
		for (int i = 1; i < numbers.length; i++) {
			Character currNumberChar = Character.valueOf(numbers[i]);
			groupSizes.putIfAbsent(currNumberChar, 1);
			Character prevNumberChar = Character.valueOf(numbers[i - 1]);
			if (currNumberChar.equals(prevNumberChar)) {
				Integer groupSize = groupSizes.get(currNumberChar);
				groupSize++;
				groupSizes.put(currNumberChar, groupSize);
			}
		}
		boolean result = true;
		for (Map.Entry<Character, Integer> entry : groupSizes.entrySet()) {
			Integer groupSize = groupSizes.get(entry.getKey());
			if (groupSize == 3 || groupSize == 5) {
				result = false;
				break;
			}
		}
		System.out.format("password: %s, groupSizes: %s, result: %s\n",
				password, groupSizes, result);
		return result;
	};
	
	Predicate<Integer> validations = 
			checkLength
			.and(withinRange)
			.and(containsDoubleDigit)
			.and(doesNotContainADecrease)
			.and(checkGroupSizes);

}
