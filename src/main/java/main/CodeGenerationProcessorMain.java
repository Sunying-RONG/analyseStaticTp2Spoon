package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import processors.Processor;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoonProcessors.CodeGenerationProcessor;
import spoonVisitors.GetAllCalls;
import spoonVisitors.GetAllClasses;
import spoonVisitors.ToStringGenerator;

public class CodeGenerationProcessorMain extends AbstractMain {

	
	protected void menu() {
		StringBuilder builder = new StringBuilder();
		builder.append("1. toString() generator.");
		builder.append("\n2. relation number of two classes / relation numbers between all classes.");
//		builder.append("\n2. Help menu.");
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
	
//	/Users/rongsunying/eclipse-workspace/promotion
	protected void processUserInput(String userInput, Processor<?> processor) {
		CodeGenerationProcessor codeGenerationProcessor = (CodeGenerationProcessor) processor;
		
		switch(userInput) {
			case "1": 
				codeGenerationProcessor.apply(new ToStringGenerator());
				break;
				
			case "2":
//				Map<String, Integer> classInvocPair = new HashMap<>();
				double callNbTotal = 0;
				double callNb = 0;
				AbstractProcessor<CtClass> getAllCalls = new GetAllCalls();
				codeGenerationProcessor.apply(getAllCalls);
				List<String> classNames = ((GetAllCalls) getAllCalls).getClassNames();
				
//				for (String n : classNames) {
//					System.out.println(n);
//				}
				Scanner sc = new Scanner(System.in);  // Create a Scanner object
		        System.out.println("Enter first class name: ");
		        String classNameA = sc.nextLine();  // Read user input
		        System.out.println("Enter second class name: ");
		        String classNameB = sc.nextLine();
			    if (classNames.contains(classNameA) && classNames.contains(classNameB) && !classNameA.equals(classNameB)) {
			    	for (Map.Entry<CtClass, List<CtInvocation>> entry : ((GetAllCalls) getAllCalls).getMap().entrySet()) {
						String className = entry.getKey().getSimpleName();
//						int classInvocNb = 0;
						for (CtInvocation invoc : entry.getValue()) {
							String methodInvocReceiver = invoc.getExecutable().getReferencedTypes().stream().findFirst().get().toString();
							if (!methodInvocReceiver.equals(className)
									&& classNames.contains(methodInvocReceiver)) {
								callNbTotal++;
//								classInvocNb++;
							}
							if (classNameA.equals(className) && methodInvocReceiver.equals(classNameB)) {
								callNb++;
							}
						}
//						totalInvoc += classInvocNb;
//						classInvocPair.put(className, classInvocNb);
					}
//					classInvocPair.forEach((k, v) -> System.out.println(k + " : " + v));
					System.out.println("Call nb between " + classNameA + " and " + classNameB + " : " + callNb);
		            System.out.println("All call nb of App's classes : " + callNbTotal);
		            System.out.println("Couplage (A,B) = " + callNb/callNbTotal + " (callNb/callNbTotal)");
			    } else {
		            System.err.println("Wrong class name !");
		        }
				
				
				
				
			
//			case "2":
//				return;
			
			case QUIT:
				System.out.println("Bye...");
				return;
			
			default:
				System.err.println("Sorry, wrong input. Please try again.");
				return;
		}
	}

}
