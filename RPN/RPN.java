package RPN;

import java.util.*;
import java.io.*;

public class RPN {
	public static void main(String[] args) throws FileNotFoundException {
		LinkedList<Token> queue = new LinkedList<>();
		HashMap<String, Double> ids = new HashMap<String, Double>();
		
		ids.put("x", 1.0);
		ids.put("y", 2.0);
		ids.put("z", 3.0);

		String line = "";
		Token token;
		double result;

		File file = new File("/home/mcdag/Compiladores/RPN/Calc1.stk");
		Scanner in = new Scanner(file);
		
		try {
			FileWriter resultFile = new FileWriter("/home/mcdag/Compiladores/RPN/Calc1Result.stk");	
			PrintWriter writeFile = new PrintWriter(resultFile);

			if(file.length() == 0){
				writeFile.printf("File is blank");
				return;
			}

			while (in.hasNext()) {
				line = in.nextLine();

				if(isNumber(line)) {
					token = new Token(TokenType.NUM, line);
					queue.add(token);
				}else {
					if(line.equals("+")) {
						token = new Token(TokenType.PLUS, line);
					} else if(line.equals("-")) {
						token = new Token(TokenType.MINUS, line);
					} else if(line.equals("/")) {
						token = new Token(TokenType.SLASH, line);
					}else if(line.equals("*")) {
						token = new Token(TokenType.STAR, line);
					} else if(line.equals("x") || line.equals("y") || line.equals("z")) {
						token = new Token(TokenType.ID, line);
					} else {
						throw new Exception();
					}
					queue.add(token);
				}
			}

			result = getResult(queue, ids);
			writeFile.printf("result: " + result);
			resultFile.close();
			
		} catch (Exception e) {
			System.out.println("Error: Unexpected character: " + line);

		} finally {
			in.close();
		}
	}

	public static boolean isNumber (String number) {
		try{
			Double.parseDouble(number);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	public static double getResult(LinkedList<Token> queue, HashMap<String, Double> ids) throws Exception {
		Stack<Double> stack = new Stack<Double>();
		double result, first, second;

		while(queue.size() != 0) {
			Token line;

			line = queue.removeFirst();

			if(line.type == TokenType.NUM) {
				stack.push(Double.parseDouble(line.lexeme));
			}else if(line.type == TokenType.ID){
				stack.push(ids.get(line.lexeme));
			}else {
				if (stack.size() < 2) {
					throw new Exception();
				}
				second = stack.pop();
				first = stack.pop();

				if (line.type == TokenType.PLUS) {
					result = first + second;
				} else if(line.type == TokenType.MINUS) {
					result = first - second;
				} else if(line.type == TokenType.SLASH) {
					result = first / second;
				} else {
					result = first * second;
				}
				stack.push(result);
			}
		}

		if (stack.size() != 1) {
			throw new Exception();
		}
		return stack.pop();
	}
}
