package com.labexam.task2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a TestLombok entity with a name property.
 */
@Getter
@Setter
@NoArgsConstructor
public class TestLombok {
	// The name of the TestLombok instance
	private String name = "Abdulazez";
	
	/**
	 * The main method to execute the TestLombok class.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		TestLombok testLombok = new TestLombok();
		System.out.println(testLombok.getName());
	}
}
