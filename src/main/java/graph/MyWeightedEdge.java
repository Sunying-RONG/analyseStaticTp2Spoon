package graph;

import java.text.DecimalFormat;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

public class MyWeightedEdge extends DefaultWeightedEdge implements Comparable<MyWeightedEdge>{
    private String departNode;
    private String arriveNode;
    private double weight;
    private double invocNb;
    private static final DecimalFormat df = new DecimalFormat("0.0000");
    
    public MyWeightedEdge(String departNode, String arriveNode) {
        super();
        this.departNode = departNode;
        this.arriveNode = arriveNode;
    }

    public double getInvocNb() {
        return invocNb;
    }

    public void setInvocNb(double invocNb) {
        this.invocNb = invocNb;
    }

    public String getDepartNode() {
        return departNode;
    }

    public void setDepartNode(String departNode) {
        this.departNode = departNode;
    }

    public String getArriveNode() {
        return arriveNode;
    }

    public void setArriveNode(String arriveNode) {
        this.arriveNode = arriveNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "(" + df.format(weight) + ")";
    }

    @Override
    public int compareTo(MyWeightedEdge o) {
        // TODO Auto-generated method stub
        return Double.compare(o.getWeight(), getWeight());
    }


}
