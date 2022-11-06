package Spoon;

import java.util.Set;

import spoon.Launcher;
import spoon.processing.Processor;
import spoon.reflect.declaration.CtClass;

public class SpoonParser extends Parser<Launcher> {
    public static final String jrePath = "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/";

	public SpoonParser(String projectPath) {
		super(projectPath);
	}
	
	public void setLauncher(String sourceOutputPath, String binaryOutputPath, 
			boolean autoImports, boolean commentsEnabled) {
		parser = new Launcher(); // create launcher
		parser.addInputResource(getProjectSrcPath()); // set project source path
		parser.getEnvironment().setSourceClasspath(new String[] {getProjectBinPath()}); // set project classpath
//		parser.getEnvironment().setSourceClasspath(new String[] {jrePath});
		parser.setSourceOutputDirectory(sourceOutputPath); // set generated source code directory path
		parser.setBinaryOutputDirectory(binaryOutputPath); // set generated binary code directory path
		parser.getEnvironment().setAutoImports(autoImports); // set auto imports
		parser.getEnvironment().setCommentEnabled(commentsEnabled); // set comments enabled
		System.out.println("SpoonParser setLauncher");
//		CtClass l = parser.parseClass("class A { void m() { System.out.println(\"Hello, World!\"); }}");
//        Set methods = l.getAllMethods();
//        for (Object o : methods.toArray()) {
//            System.out.println(o.toString());
//        }
	}

	
	public void configure() {
		setLauncher(projectPath+"/spooned/src/", projectPath+"/spooned/bin/", true, true);
	}
	
	public void addProcessor(Processor<CtClass> processor) {
		parser.addProcessor(processor);
		System.out.println("SpoonParser addProcessor");
	}
	
	public void run() {
	    System.out.println("SpoonParser run");
		parser.run();
	    
	}
}
