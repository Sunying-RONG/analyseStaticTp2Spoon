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

	@Override
	public void process(CtClass clazz) {
		if (!classNames.contains(clazz.getSimpleName())) {
			classNames.add(clazz.getSimpleName());
		}
		List<CtInvocation> listInvocations = new ArrayList<>();
		
		Set<CtMethod> methods = clazz.getMethods();
		for (CtMethod method : methods) {
			listInvocations.addAll(method.getBody().getElements(new TypeFilter(CtInvocation.class)));
//			for (CtInvocation invoc : listInvocations) {
//				String methodInvocReceiver = invoc.getExecutable().getReferencedTypes().stream().findFirst().get().toString();
//				System.out.println("--"+methodInvocReceiver);
//				if (!methodInvocReceiver.equals(clazz.getSimpleName())
//						&& classNames.contains(methodInvocReceiver)) {
//					classInvocNb++;
//					System.out.println("=="+methodInvocReceiver);
//				}
////				System.out.println(invoc.getExecutable().getReferencedTypes().stream().findFirst().get());
//				
//			}

		}
		map.put(clazz, listInvocations);
//		totalCalls += classInvocNb;
//		System.out.println(clazz.getSimpleName());
//		System.out.println(classInvocNb);
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


}
