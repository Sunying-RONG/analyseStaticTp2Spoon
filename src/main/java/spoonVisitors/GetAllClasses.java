package spoonVisitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.filter.TypeFilter;

public class GetAllClasses extends AbstractProcessor<CtClass> {
	private List<String> classNames = new ArrayList<>();

	@Override
	public void process(CtClass clazz) {
		if (!classNames.contains(clazz.getSimpleName())) {
			classNames.add(clazz.getSimpleName());
		}

	}

	public List<String> getClassNames() {
		return classNames;
	}

	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}
	
	public int getClassNb() {
		return classNames.size();
	}

}
