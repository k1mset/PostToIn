package cmsc350project2;

/**
 * File: OperatorNode.java
 * Date: 9/15/2018
 * Author: Dillan Cobb
 * Purpose: Using the provided material this aids in building the tree, creates
 * and manages the trees as well as provides output with methods.
 */

public class OperatorNode implements Node {

    Node left, right;
    Operator operator;
   
    public OperatorNode(Operator operator, Node left,
                        Node right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public double evaluate() {
        double leftValue = left.evaluate();
        double rightValue = right.evaluate();
        return operator.evaluate(leftValue, rightValue);
    } 
    
    public String preOrderWalk() {
        String leftValue = left.preOrderWalk();
        String rightValue = right.preOrderWalk();
        return "" + operator + " " + leftValue + " "
                  + rightValue;
    }

    public String inOrderWalk() {
        String leftValue = left.inOrderWalk();
        String rightValue = right.inOrderWalk();
        return "(" + leftValue + " " + operator + " "
                   + rightValue + ")";
    }

    public String postOrderWalk() {
        String leftValue = left.postOrderWalk();
        String rightValue = right.postOrderWalk();
        return leftValue + " " + rightValue + " " + operator;
    }
    
    
}
