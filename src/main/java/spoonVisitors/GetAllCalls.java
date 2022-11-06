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

public class GetAllCalls extends AbstractProcessor<CtClass> {
	private Map<CtClass, List<CtInvocation>> map = new HashMap<>();
	private List<String> classNames = new ArrayList<>();
	private double invocAppNb;

	@Override
	public void process(CtClass clazz) {
		if (!classNames.contains(clazz.getSimpleName())) {
			classNames.add(clazz.getSimpleName());
		}
		List<CtInvocation> listInvocations = new ArrayList<>();
		
		Set<CtMethod> methods = clazz.getMethods();
		for (CtMethod method : methods) {
			listInvocations.addAll(method.getBody().getElements(new TypeFilter(CtInvocation.class)));
		}
		invocAppNb += listInvocations.size();
		map.put(clazz, listInvocations);
	}

	public Map<CtClass, List<CtInvocation>> getMap() {
		return map;
	}

	public void setMap(Map<CtClass, List<CtInvocation>> map) {
		this.map = map;
	}

	public List<String> getClassNames() {
		return classNames;
	}

	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}

	public double getInvocAppNb() {
		return invocAppNb;
	}

	public void setInvocAppNb(double invocAppNb) {
		this.invocAppNb = invocAppNb;
	}


}
