/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc350project2;

/**
 * File: OperandNode.java
 * Date: 9/15/2018
 * Author: Dillan Cobb
 * Purpose: Using the provided material this aids in building the tree by
 * providing a string output of the value of the operand.
 */

public class OperandNode implements Node {
    private double value;
    
    public OperandNode(double value) {
        this.value = value;
    }
    
    public double evaluate() {
        return value;
    }
    
    public String preOrderWalk() {
        return String.valueOf(value);
    }

    public String inOrderWalk() {
        return String.valueOf(value);
    }

    public String postOrderWalk() {
        return String.valueOf(value);
    }
}
