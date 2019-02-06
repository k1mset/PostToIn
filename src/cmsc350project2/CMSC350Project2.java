package cmsc350project2;

/**
 * File: CMSC350Project2.java
 * Date: 9/15/2018
 * Author: Dillan Cobb
 * Purpose: Creates the gui for the application and displays it to the user.
 * Takes an Postfix expression and returns it to an Infix expression with the
 * use of binary trees, stacks and nodes.
 */

// Imports
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class CMSC350Project2 extends JPanel {
    
    // Frame setup for the gui constants
    static final int WIDTH = 400, HEIGHT = 200;
    static final String FRAMETITLE = "CMSC 350 - Project 2";
    
    // Panel gui labels
    private JLabel expressionLbl = new JLabel("Enter Postfix Expression:");
    private JLabel infixLbl = new JLabel("Infix Expression:");
    
    // Panel gui textfields
    private JTextField expressionTxt = new JTextField();
    private JTextField infixTxt = new JTextField();
    
    // Panel gui button
    private JButton constructBtn = new JButton("Construct Tree");
    
    // Option pane
    private JOptionPane msgPane = new JOptionPane();
    
    private BufferedWriter registryOutput;
    private PrintWriter out;
        
    public static void main(String[] args) {
        // Sets up and displays the gui to the user
        JFrame appFrame = new JFrame();
        CMSC350Project2 appPanel = new CMSC350Project2();
        appFrame.setTitle(FRAMETITLE);
        appFrame.setSize(WIDTH, HEIGHT);
        appFrame.setLocationRelativeTo(null);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.add(appPanel);
        appFrame.setVisible(true);
    }
    
    public CMSC350Project2() {
        // Creates the panels for the frame
        JPanel mainPanel = new JPanel();
        JPanel entryPanel = new JPanel();
        JPanel submitPanel = new JPanel();
        JPanel resultPanel = new JPanel();
        
        // Setup the entryPanel
        entryPanel.setLayout(new GridLayout(1,2));
        entryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        entryPanel.add(expressionLbl);
        entryPanel.add(expressionTxt);
        
        // Setup the submitPanel
        submitPanel.setLayout(new FlowLayout());
        
        submitPanel.add(constructBtn);
        
        // Setup the resultPanel
        resultPanel.setLayout(new GridLayout(1,2));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        resultPanel.add(infixLbl);
        resultPanel.add(infixTxt);
        infixTxt.setEnabled(false);
        
        // Setup the mainPanel
        mainPanel.setLayout(new GridLayout(3,1));
        
        mainPanel.add(entryPanel);
        mainPanel.add(submitPanel);
        mainPanel.add(resultPanel);
        
        add(mainPanel);
        
        // Actionlistener for the constructBtn, heart of the application
        constructBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               
               char[] expChars = expressionTxt.getText().toCharArray();
               //operand number, operator +-
               
               // Variables for the program
               // Stacks for the operands and the nodes
               Stack operandStack = new Stack();
               Stack nodeStack = new Stack();
               // Nodes for setting the trees
               Node tree = null;
               Node leftNode = null;
               Node rightNode = null;
               // Operands for the nodes
               double left;
               double right;
               // String for the final text
               String infixExp = "";
               // char for the selected text
               char currChar = '0';
               // Use for registries
               int rNum = 0;
               Stack rStack = new Stack();
               String rPop;
               String rPop2;
               
               try {
                    // For loop scans thru the inputted text
                    for (int i=0; i<expChars.length; i++) {
                        // String for the registries
                        String rStr;
                        // Select current text
                        currChar = expChars[i];

                        // if the current item is a number, push it to the operandStack
                        if (Character.isDigit(currChar)) {
                            operandStack.push(currChar);
                        }
                        // if the current item is a +
                        else if (currChar == '+') {
                            // if is a + and the operandStack is empty we need to add
                            // the remaining nodes from the nodeStack into one tree
                            if (operandStack.isEmpty()) {
                                // Check if the nodeStack is empty, it SHOULD NOT be
                                if (!nodeStack.isEmpty()) {
                                    // pop left and right nodes from the nodeStack
                                    rightNode = (Node) nodeStack.pop();
                                    leftNode = (Node) nodeStack.pop();
                                    // create the entire tree from the two remaining nodeStack
                                    tree = new OperatorNode(new AddOperator(), leftNode, rightNode);
                                    
                                     // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rPop = rStack.pop().toString();
                                    rPop2 = rStack.pop().toString();
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Add " + rStr+ " " + rPop2+ " " + rPop);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                                else {
                                    System.out.print("Error occured 1");
                                }
                            }
                            // if the operandStack is not empty
                            else {
                                // pop first number from the operandStack
                                left = Double.parseDouble(String.valueOf(operandStack.pop()));

                                // if the operandStack is now empty
                                if (operandStack.isEmpty()) {
                                    // pop next operand and add into node, push new node
                                    // to the nodeStack
                                     if (!nodeStack.isEmpty()) {
                                         rightNode = (Node) nodeStack.pop();
                                         tree = new OperatorNode(new AddOperator(), new OperandNode(left), rightNode);
                                         nodeStack.push(tree);
                                         
                                         // Add the new rnumber to the string
                                        rStr = "R" + rNum;
                                        rNum++; // increase the rnumber
                                        rPop = rStack.pop().toString();
                                        rStack.push(rStr); // push rnumber torstack
                                        // write to registry.txt
                                        try {
                                            // open file and write
                                            registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                            out = new PrintWriter(registryOutput);
                                            out.println("Add " + rStr+ " " + left + " " + rPop);
                                        } catch (IOException io) {
                                            // catch any issues
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        } finally {
                                            // use exceptions to close file after writing line
                                            try {
                                                if (registryOutput != null) {
                                                    registryOutput.close();
                                                }
                                                if (out != null) {
                                                    out.close();
                                                }
                                            } catch (IOException io) {
                                                System.out.println("File IO Exception: " + io.getMessage());
                                            }
                                        }
                                     }
                                     else {
                                         System.out.print("Error occured 2");
                                     }
                                }
                                // if the operandStack is not empty, pop next operand
                                // and create a new node. push the new new to nodeStack
                                else {
                                    right = Double.parseDouble(String.valueOf(operandStack.pop()));
                                    tree = new OperatorNode(new AddOperator(), new OperandNode(right), new OperandNode(left));
                                    nodeStack.push(tree);
                                    
                                    // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Add " + rStr+ " " + left+ " " + right);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                            }
                        }

                        // if the current item is a -
                        else if (currChar == '-') {
                            // if is a - and the operandStack is empty we need to sub
                            // the remaining nodes from the nodeStack into one tree
                            if (operandStack.isEmpty()) {
                                // Check if the nodeStack is empty, it SHOULD NOT be
                                if (!nodeStack.isEmpty()) {
                                    // pop left and right nodes from the nodeStack
                                    rightNode = (Node) nodeStack.pop();
                                    leftNode = (Node) nodeStack.pop();
                                    // create the entire tree from the two remaining nodeStack
                                    tree = new OperatorNode(new SubOperator(), leftNode, rightNode);
                                    
                                     // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rPop = rStack.pop().toString();
                                    rPop2 = rStack.pop().toString();
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Sun " + rStr+ " " + rPop2+ " " + rPop);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                                else {
                                    System.out.print("Error occured 1");
                                }
                            }
                            // if the operandStack is not empty
                            else {
                                // pop first number from the operandStack
                                left = Double.parseDouble(String.valueOf(operandStack.pop()));

                                // if the operandStack is now empty
                                if (operandStack.isEmpty()) {
                                    // pop next operand and sub into node, push new node
                                    // to the nodeStack
                                     if (!nodeStack.isEmpty()) {
                                         rightNode = (Node) nodeStack.pop();
                                         tree = new OperatorNode(new SubOperator(), new OperandNode(left), rightNode);
                                         nodeStack.push(tree);
                                         
                                          // Add the new rnumber to the string
                                        rStr = "R" + rNum;
                                        rNum++; // increase the rnumber
                                        rPop = rStack.pop().toString();
                                        rStack.push(rStr); // push rnumber torstack
                                        // write to registry.txt
                                        try {
                                            // open file and write
                                            registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                            out = new PrintWriter(registryOutput);
                                            out.println("Sub " + rStr+ " " + left+ " " + rPop);
                                        } catch (IOException io) {
                                            // catch any issues
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        } finally {
                                            // use exceptions to close file after writing line
                                            try {
                                                if (registryOutput != null) {
                                                    registryOutput.close();
                                                }
                                                if (out != null) {
                                                    out.close();
                                                }
                                            } catch (IOException io) {
                                                System.out.println("File IO Exception: " + io.getMessage());
                                            }
                                        }
                                     }
                                     else {
                                         System.out.print("Error occured 2");
                                     }
                                }
                                // if the operandStack is not empty, pop next operand
                                // and create a new node. push the new new to nodeStack
                                else {
                                    right = Double.parseDouble(String.valueOf(operandStack.pop()));
                                    tree = new OperatorNode(new SubOperator(), new OperandNode(right), new OperandNode(left));
                                    nodeStack.push(tree);
                                    
                                    // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Sub " + rStr+ " " + left+ " " + right);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                            }
                        }

                        // if the current item is a /
                        else if (currChar == '/') {
                            // if is a + and the operandStack is empty we need to div
                            // the remaining nodes from the nodeStack into one tree
                            if (operandStack.isEmpty()) {
                                // Check if the nodeStack is empty, it SHOULD NOT be
                                if (!nodeStack.isEmpty()) {
                                    // pop left and right nodes from the nodeStack
                                    rightNode = (Node) nodeStack.pop();
                                    leftNode = (Node) nodeStack.pop();
                                    // create the entire tree from the two remaining nodeStack
                                    tree = new OperatorNode(new DivOperator(), leftNode, rightNode);
                                    
                                     // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rPop = rStack.pop().toString();
                                    rPop2 = rStack.pop().toString();
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Div " + rStr+ " " + rPop2+ " " + rPop);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                                else {
                                    System.out.print("Error occured 1");
                                }
                            }
                            // if the operandStack is not empty
                            else {
                                // pop first number from the operandStack
                                left = Double.parseDouble(String.valueOf(operandStack.pop()));

                                // if the operandStack is now empty
                                if (operandStack.isEmpty()) {
                                    // pop next operand and div into node, push new node
                                    // to the nodeStack
                                     if (!nodeStack.isEmpty()) {
                                         rightNode = (Node) nodeStack.pop();
                                         tree = new OperatorNode(new DivOperator(), new OperandNode(left), rightNode);
                                         nodeStack.push(tree);
                                         
                                          // Add the new rnumber to the string
                                        rStr = "R" + rNum;
                                        rNum++; // increase the rnumber
                                        rPop = rStack.pop().toString();
                                        rStack.push(rStr); // push rnumber torstack
                                        // write to registry.txt
                                        try {
                                            // open file and write
                                            registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                            out = new PrintWriter(registryOutput);
                                            out.println("Div " + rStr+ " " + left+ " " + rPop);
                                        } catch (IOException io) {
                                            // catch any issues
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        } finally {
                                            // use exceptions to close file after writing line
                                            try {
                                                if (registryOutput != null) {
                                                    registryOutput.close();
                                                }
                                                if (out != null) {
                                                    out.close();
                                                }
                                            } catch (IOException io) {
                                                System.out.println("File IO Exception: " + io.getMessage());
                                            }
                                        }
                                     }
                                     else {
                                         System.out.print("Error occured 2");
                                     }
                                }
                                // if the operandStack is not empty, pop next operand
                                // and create a new node. push the new new to nodeStack
                                else {
                                    right = Double.parseDouble(String.valueOf(operandStack.pop()));
                                    tree = new OperatorNode(new DivOperator(), new OperandNode(right), new OperandNode(left));
                                    nodeStack.push(tree);
                                    
                                    // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Div " + rStr+ " " + left+ " " + right);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                            }
                        }

                        // if the current item is a *
                        else if (currChar == '*') {
                            // if is a + and the operandStack is empty we need to mult
                            // the remaining nodes from the nodeStack into one tree
                            if (operandStack.isEmpty()) {
                                // Check if the nodeStack is empty, it SHOULD NOT be
                                if (!nodeStack.isEmpty()) {
                                    // pop left and right nodes from the nodeStack
                                    rightNode = (Node) nodeStack.pop();
                                    leftNode = (Node) nodeStack.pop();
                                    // create the entire tree from the two remaining nodeStack
                                    tree = new OperatorNode(new MulOperator(), leftNode, rightNode);
                                    
                                     // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rPop = rStack.pop().toString();
                                    rPop2 = rStack.pop().toString();
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Mul " + rStr+ " " + rPop2+ " " + rPop);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                                else {
                                    System.out.print("Error occured 1");
                                }
                            }
                            // if the operandStack is not empty
                            else {
                                // pop first number from the operandStack
                                left = Double.parseDouble(String.valueOf(operandStack.pop()));

                                // if the operandStack is now empty
                                if (operandStack.isEmpty()) {
                                    // pop next operand and mull into node, push new node
                                    // to the nodeStack
                                     if (!nodeStack.isEmpty()) {
                                         rightNode = (Node) nodeStack.pop();
                                         tree = new OperatorNode(new MulOperator(), new OperandNode(left), rightNode);
                                         nodeStack.push(tree);
                                         
                                          // Add the new rnumber to the string
                                        rStr = "R" + rNum;
                                        rNum++; // increase the rnumber
                                        rPop = rStack.pop().toString();
                                        rStack.push(rStr); // push rnumber torstack
                                        // write to registry.txt
                                        try {
                                            // open file and write
                                            registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                            out = new PrintWriter(registryOutput);
                                            out.println("Mul " + rStr+ " " + left+ " " + rPop);
                                        } catch (IOException io) {
                                            // catch any issues
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        } finally {
                                            // use exceptions to close file after writing line
                                            try {
                                                if (registryOutput != null) {
                                                    registryOutput.close();
                                                }
                                                if (out != null) {
                                                    out.close();
                                                }
                                            } catch (IOException io) {
                                                System.out.println("File IO Exception: " + io.getMessage());
                                            }
                                        }
                                     }
                                     else {
                                         System.out.print("Error occured 2");
                                     }
                                }
                                // if the operandStack is not empty, pop next operand
                                // and create a new node. push the new new to nodeStack
                                else {
                                    right = Double.parseDouble(String.valueOf(operandStack.pop()));
                                    tree = new OperatorNode(new MulOperator(), new OperandNode(right), new OperandNode(left));
                                    nodeStack.push(tree);
                                    
                                    // Add the new rnumber to the string
                                    rStr = "R" + rNum;
                                    rNum++; // increase the rnumber
                                    rStack.push(rStr); // push rnumber torstack
                                    // write to registry.txt
                                    try {
                                        // open file and write
                                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                                        out = new PrintWriter(registryOutput);
                                        out.println("Mul " + rStr+ " " + left+ " " + right);
                                    } catch (IOException io) {
                                        // catch any issues
                                        System.out.println("File IO Exception: " + io.getMessage());
                                    } finally {
                                        // use exceptions to close file after writing line
                                        try {
                                            if (registryOutput != null) {
                                                registryOutput.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException io) {
                                            System.out.println("File IO Exception: " + io.getMessage());
                                        }
                                    }
                                }
                            }
                        }

                        // Display an error message for having an unallowed character
                        else if (currChar != ' ') {
                            msgPane.showMessageDialog(null, "You have entered a character that is not allowed: " + currChar);
                        }
                    }
                    
                    // Adds a line at the end of the file so you can seperate runs
                    try {
                        // open file and write
                        registryOutput = new BufferedWriter(new FileWriter("Registry.txt", true));
                        out = new PrintWriter(registryOutput);
                        out.println("-------------------");
                    } catch (IOException io) {
                        // catch any issues
                        System.out.println("File IO Exception: " + io.getMessage());
                    } finally {
                        // use exceptions to close file after writing line
                        try {
                            if (registryOutput != null) {
                                registryOutput.close();
                            }
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException io) {
                            System.out.println("File IO Exception: " + io.getMessage());
                        }
                    }
                    
                    
               } catch (RuntimeException ex) {
                   msgPane.showMessageDialog(null, "You have entered a character that is not allowed: " + currChar);
                   throw new RuntimeException("Incorrect character inputted for expression: " + currChar); 
               }
               
               // Ensures there is a tree before trying to call the inOrderWalk method
               if (tree != null) {
                    infixExp = tree.inOrderWalk();

                    infixTxt.setText(infixExp);
                    System.out.print(infixExp);
               }
           } 
        });
    }
}
