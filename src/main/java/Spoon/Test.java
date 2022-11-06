package Spoon;

import java.io.IOException;
import java.util.Set;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

public class Test {
	public static void main(String[] args) throws IOException {
	    CtClass l = Launcher.parseClass("class A { void m() { System.out.println(\"yeah\");} }");
	    Set methods = l.getAllMethods();
          for (Object o : methods.toArray()) {
              System.out.println(o.toString());
          }
	}
}
