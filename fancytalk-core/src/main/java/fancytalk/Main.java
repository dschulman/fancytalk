package fancytalk;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
	String input;
	BufferedReader reader = 
	    new BufferedReader(
		new InputStreamReader(System.in));

	Fancytalker talker = new Fancytalker();
	while ((input = reader.readLine()) != null)
	    System.out.println(talker.apply(input));
    }
}