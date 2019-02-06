
package cmsc350project2;

/**
 * File: Operator.java
 * Date: 9/15/2018
 * Author: Dillan Cobb
 * Purpose: Using the provided material this aids in building the tree by 
 * providing the correct outputs
 */

public abstract class Operator {
    abstract public double evaluate(double x, double y);
}

class AddOperator extends Operator {
    public double evaluate(double d1, double d2) {
        return d1 + d2;
    }
    
    public String toString() {
        return "+";
    }
}

class MulOperator extends Operator {
    public double evaluate(double d1, double d2) {
        return d1 * d2;
    }
    
    public String toString() {
        return "*";
    }
}
   
class SubOperator extends Operator {
    public double evaluate(double d1, double d2) {
        return d1 - d2;
    }
    
    public String toString() {
        return "-";
    }
}
   
class DivOperator extends Operator {
    public double evaluate(double d1, double d2) {
        return d1 / d2;
    }
    
    public String toString() {
        return "/";
    }
}
