package RPN;

import java.util.*;
import java.io.*;

public class RPN {
	public static void main(String[] args) throws FileNotFoundException {
		Stack<Double> stack = new Stack<Double>();
		double finalResult, result, first, second;

		File file = new File("/home/mcdag/Compiladores/RPN/Calc1.stk");
		Scanner in = new Scanner(file);
		
		try {
			while (in.hasNext()) {
				String line = in.nextLine();

				if(Character.isDigit(line.charAt(0))) {
					stack.push(Double.parseDouble(line));
				}else {
					if (stack.size() < 2) {
						throw new Exception();
					}
					second = stack.pop();
					first = stack.pop();
					
					if(line.equals("+")) {
						result = first + second;
					} else if(line.equals("-")) {
						result = first - second;
					} else if(line.equals("/")) {
						result = first / second;
					}else if(line.equals("*")) {
						result = first * second;
					}else if(line.equals("^") || line.equals("**")) {
						result = Math.pow(first, second);
					} else {
						throw new Exception();
					}
					stack.push(result);
				}
			}
			if (stack.size() != 1) {
				throw new Exception();
			}
			finalResult = stack.pop();
			
			FileWriter resultFile = new FileWriter("/home/mcdag/Compiladores/RPN/Calc1Result.stk");	
			PrintWriter writeFile = new PrintWriter(resultFile);
			writeFile.printf("result: " + finalResult);
			resultFile.close();
			
		} catch (Exception e) {
			System.out.println("Erro no arquivo");

		} finally {
			in.close();
		}
	}
}
