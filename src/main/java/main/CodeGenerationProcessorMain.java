package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.util.mxCellRenderer;

import processors.Processor;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoonProcessors.CodeGenerationProcessor;
import spoonVisitors.GetAllCalls;
import spoonVisitors.ToStringGenerator;
import graph.MyWeightedEdge;

public class CodeGenerationProcessorMain extends AbstractMain {

	public static List<MyWeightedEdge> classEdgeList = new ArrayList<MyWeightedEdge>();
	
	protected void menu() {
		StringBuilder builder = new StringBuilder();
		builder.append("1. toString() generator.");
		builder.append("\n2. relation number of two classes / relation numbers between all classes.");
		builder.append("\n3. Générez un graphe de couplage pondéré entre les classes.");
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
							}
							if (classNameA.equals(className) && methodInvocReceiver.equals(classNameB)) {
								callNb++;
							}
						}
					}
					System.out.println("Call nb between " + classNameA + " and " + classNameB + " : " + callNb);
		            System.out.println("All call nb of App's classes : " + callNbTotal);
		            System.out.println("Couplage (A,B) = " + callNb/callNbTotal + " (callNb/callNbTotal)");
			    } else {
		            System.err.println("Wrong class name !");
		        }
				break;
				
			case "3":
	            getAllCalls = new GetAllCalls();
				codeGenerationProcessor.apply(getAllCalls);
				classNames = ((GetAllCalls) getAllCalls).getClassNames();
				double invocAppNb = ((GetAllCalls) getAllCalls).getInvocAppNb();
				
		    	for (Map.Entry<CtClass, List<CtInvocation>> entry : ((GetAllCalls) getAllCalls).getMap().entrySet()) {
		    		double invocClasTotal = 0;
					String departNode = entry.getKey().getSimpleName();
					for (CtInvocation invoc : entry.getValue()) {
//						invocAppNb++;
						String methodInvocReceiver = invoc.getExecutable().getReferencedTypes().stream().findFirst().get().toString();
						if (classNames.contains(methodInvocReceiver) && !departNode.equals(methodInvocReceiver)) {
							invocClasTotal++;
							MyWeightedEdge weightEdge = findOrCreateMyWeightedEdge(departNode, methodInvocReceiver);
							weightEdge.setInvocNb(weightEdge.getInvocNb()+1);
						}
					}
					for (MyWeightedEdge weightEdge : classEdgeList) {
		                if (weightEdge.getDepartNode().equals(departNode)) {
		                    weightEdge.setWeight((weightEdge.getInvocNb()/invocAppNb) * weightEdge.getInvocNb()/invocClasTotal);
		                }
		            }
				}
		    	createGraphCouplagePondere(classNames);
		    	for (MyWeightedEdge e : classEdgeList) {
		            System.out.println(e.getDepartNode() + " : "+e.getArriveNode() + " : "+e.getWeight());
		        }
		    	System.out.println("invocAppNb: "+invocAppNb);
		    	
		    	break;
		    	
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
	public static MyWeightedEdge findOrCreateMyWeightedEdge(String departNode, String arriveNode) {
	    for (int i=0; i<classEdgeList.size(); i++) {
	        if (classEdgeList.get(i).getDepartNode().equals(departNode) 
	            && classEdgeList.get(i).getArriveNode().equals(arriveNode)) {
	            return classEdgeList.get(i);
	        }
	    }
	    MyWeightedEdge newMyWeightedEdge =  new MyWeightedEdge(departNode, arriveNode);
	    classEdgeList.add(newMyWeightedEdge);
	    return newMyWeightedEdge;
	}
	
	 public static void createGraphCouplagePondere(List<String> classNames) {
	        DirectedWeightedMultigraph<String, MyWeightedEdge> g = 
	                new DirectedWeightedMultigraph<String, MyWeightedEdge>(MyWeightedEdge.class);
	        for (String clas : classNames) {
	            g.addVertex(clas);
	        }
	        
	        for (MyWeightedEdge edge : classEdgeList) {
	            String d = edge.getDepartNode();
	            String a = edge.getArriveNode();
	            g.addEdge(d, a, edge);
//	            g.setEdgeWeight(edge, edge.getWeight());
	        }
	        JGraphXAdapter<String, MyWeightedEdge> graphAdapter = 
	                new JGraphXAdapter<String, MyWeightedEdge>(g);
//	              mxIGraphLayout layout = new mxParallelEdgeLayout(graphAdapter, 5);
//	              layout.execute(graphAdapter.getDefaultParent());
	          new mxCircleLayout(graphAdapter).execute(graphAdapter.getDefaultParent());
	          new mxParallelEdgeLayout(graphAdapter, 100).execute(graphAdapter.getDefaultParent());
	          BufferedImage image = 
	            mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
	          File imgFile = new File("./graphCouplagePondere.png");
	          try {
	              imgFile.createNewFile();
	          } catch (IOException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          }
	          try {
	              ImageIO.write(image, "PNG", imgFile);
	              System.out.println("Graphe couplage pondere generated.");
	          } catch (IOException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          }
	    }

}
