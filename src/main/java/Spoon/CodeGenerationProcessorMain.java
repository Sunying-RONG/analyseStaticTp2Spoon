package Spoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeGenerationProcessorMain extends AbstractMain {

	
	protected void menu() {
		StringBuilder builder = new StringBuilder();
		builder.append("1. toString() generator.");
		builder.append("\n2. Help menu.");
		builder.append("\n"+QUIT+". To quit.");
		
		System.out.println(builder);
	}
	
	public static void main(String[] args) {	
		CodeGenerationProcessorMain main = new CodeGenerationProcessorMain();
		BufferedReader inputReader;
		try {
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			if (args.length < 1)
				setTestProjectPath(inputReader);
			else
				verifyTestProjectPath(inputReader, args[0]);
			CodeGenerationProcessor processor = new CodeGenerationProcessor(TEST_PROJECT_PATH);
			String userInput = "";
			
			do {	
				main.menu();			
				userInput = inputReader.readLine();
				main.processUserInput(userInput, processor);
				Thread.sleep(3000);
				
			} while(!userInput.equals(QUIT));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	
	protected void processUserInput(String userInput, Processor<?> processor) {
		CodeGenerationProcessor codeGenerationProcessor = (CodeGenerationProcessor) processor;
		switch(userInput) {
			case "1": 
				codeGenerationProcessor.apply(new ToStringGenerator());
				break;
			
			case "2":
				return;
			
			case QUIT:
				System.out.println("Bye...");
				return;
			
			default:
				System.err.println("Sorry, wrong input. Please try again.");
				return;
		}
	}

}
