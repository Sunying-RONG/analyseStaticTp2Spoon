package spoonProcessors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

public class CodeGenerationProcessor extends SpoonProcessor {
	/* CONSTRUCTOR */
	public CodeGenerationProcessor(String projectPath) {
		super(projectPath);
	}
	
	/* METHODS */
	public void apply(AbstractProcessor<CtClass> codeGenerator) {
		parser.addProcessor(codeGenerator);
		parser.run();
	}
}
