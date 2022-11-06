package graph;

import java.util.ArrayList;
import java.util.List;

public class Couplage {
    String className;
//    List<Couplage> couples = new ArrayList<>();
    List<String> couplesNames = new ArrayList<>();
    Couplage couplageA;
    Couplage couplageB;
    double weight;
    List<Double> listWeights = new ArrayList<>();
    
   

    public Couplage(String className) {
        super();
        this.className = className;
        this.couplesNames.add(className);
    }
    
    public Couplage(Couplage couplageA, Couplage couplageB, double weight, List<Double> listWeights) {
        super();
        this.couplageA = couplageA;
        this.couplageB = couplageB;
        this.weight = weight;
        updateListWeights(weight);
        this.listWeights.addAll(listWeights);
        this.couplesNames.addAll(couplageA.getCouplesNames());
        this.couplesNames.addAll(couplageB.getCouplesNames());
    }

//    public Couplage(List<Couplage> couples, double weight) {
//        super();
//        this.couples = couples;
//        this.weight = weight;
//        for (Couplage c : couples) {
//            this.couplesNames.addAll(c.getCouplesNames());
//        }
//        
//    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

//    public List<Couplage> getCouples() {
//        return couples;
//    }
//
//    public void setCouples(List<Couplage> couples) {
//        this.couples = couples;
//    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<String> getCouplesNames() {
        return couplesNames;
    }

    public void setCouplesNames(List<String> couplesNames) {
        this.couplesNames = couplesNames;
    }

    public Couplage getCouplageA() {
        return couplageA;
    }

    public void setCouplageA(Couplage couplageA) {
        this.couplageA = couplageA;
    }

    public Couplage getCouplageB() {
        return couplageB;
    }

    public void setCouplageB(Couplage couplageB) {
        this.couplageB = couplageB;
    }
    
    public List<Double> getListWeights() {
        return listWeights;
    }

    public void setListWeights(List<Double> listWeights) {
        this.listWeights = listWeights;
    }
    
    public void updateListWeights(double weight) {
        this.listWeights.add(weight);
    }
    
    public double averageWeight() {
        double total = 0;
        for (Double w : listWeights) {
            total = total + w;
        }
        return total / listWeights.size();
    }
    
}
