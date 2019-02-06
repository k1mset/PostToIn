package cmsc350project2;

/**
 * File: Node.java
 * Date: 9/15/2018
 * Author: Dillan Cobb
 * Purpose: Using the provided material this aids in building the tree with 
 * predefined methods.
 */

public interface Node {
    public double evaluate();
    public String preOrderWalk();
    public String inOrderWalk();
    public String postOrderWalk();
}
