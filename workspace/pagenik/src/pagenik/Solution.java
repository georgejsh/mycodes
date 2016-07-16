package pagenik;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import org.la4j.*;
import org.la4j.matrix.sparse.CCSMatrix;

class Node1
{
    Double prob;
    Integer ID;
    
    Node1(int id,Double indivProb)
    {
        ID = id;
        prob = indivProb;
    }
    public static Node1 createNode(int ID,Double indivProb)
    {
        return new Node1(ID,indivProb);
    }
    
}

class NodeCompare implements Comparator<Node1>
{

    @Override
    public int compare(Node1 o1, Node1 o2) {
        // TODO Auto-generated method stub
        return -o1.prob.compareTo(o2.prob);
    }
    
}


public class Solution {
    int noOfNodes;
    int noOfEdges;
    //int[][] adjacency;
    double[][] trans;
    int[] degree;
    static int max_ID = 10000;
    HashMap<Integer, Node1> idVsNode;
    public static void main(String[] args) {
    
    Solution s1 = new Solution();
    s1.readData();
    s1.convergence();
}

private void convergence() {
    // TODO Auto-generated method stub
	Matrix trans1 =CCSMatrix.from2DArray(trans);
	trans1=trans1.transpose();
    double[] initialMatrix = new double[max_ID];
    double[] prevMatrix = new double[max_ID];
    for(int i =0;i< max_ID;i++)
    {
    
        {
            initialMatrix[i] = (1.0/max_ID);
            prevMatrix[i] = 1.0;

        }
       
    }
    double precision = 0.01;//1.0/(10*max_ID);
    //.from2DArray(trans);
    org.la4j.Vector v = org.la4j.Vector.fromArray(initialMatrix);
    org.la4j.Vector prev = org.la4j.Vector.fromArray(prevMatrix);


    while(!whetherConverged(v,prev,precision))
    {
        prev =v;
        v = trans1.multiply(v);
   //     System.out.println("hhh");
    }
  //  System.out.println("Hi2");
    Node1 n1 = null;
    ArrayList<Node1> nodeArray = new ArrayList<Node1>();
    for(int i=0;i< max_ID;i++)
    {
        if(idVsNode.containsKey(i))
        {
            n1 = idVsNode.get(i);
            n1.prob = v.get(i);
            nodeArray.add(n1);
          //  System.out.println(v.get(i));;
        }
    }
    Collections.sort(nodeArray, new NodeCompare());
    int top = 10;
    int[] ar = new int[top];
    for(int i =0; i < top;i++)
    {
        ar[i] = nodeArray.get(i).ID;
    }
    //Arrays.sort(ar);
   // System.out.println("Hi");
    for(int i =0; i < top;i++)
    {
        System.out.println(ar[i]);
    }

    
}



private boolean whetherConverged(org.la4j.Vector initialMatrix, org.la4j.Vector prev,double prec) {

    
    for(int i = 0;i < max_ID;i++)
    {
        if(Math.abs(initialMatrix.get(i) - prev.get(i)) > prec)
            return false;
    }
    return true;
}

    private void readData() {
        // TODO Auto-generated method stub
        String fileName = "/home/nikhil/Wiki-Vote.txt";
        File file = new File(fileName);

        Scanner inputFromUser = null;
        try {
            inputFromUser = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } finally {
            // inputFromUser.close();
        }
        noOfNodes = inputFromUser.nextInt();
        noOfEdges = inputFromUser.nextInt();

      //  adjacency = new int[max_ID][max_ID];
        trans = new double[max_ID][max_ID];
        degree = new int[max_ID];
        Double indivProb = 1.0/(max_ID);
        int start;
        int end;
        Node1 n1 = null;
        idVsNode = new HashMap<Integer, Node1>();
        while (inputFromUser.hasNextInt()) {
            start = inputFromUser.nextInt();
            end = inputFromUser.nextInt();
            if (!idVsNode.containsKey(start)) {
                n1 = Node1.createNode(start,indivProb);
                idVsNode.put(start, n1);
            }
            if (!idVsNode.containsKey(end)) {
                n1 = Node1.createNode(end,indivProb);
                idVsNode.put(end, n1);
            }
            trans[start][end]=1.0;
            degree[start]++;

        }

        for (int nRow = 0; nRow < max_ID; nRow++) {
            if (idVsNode.containsKey(nRow)) {
                for (int nCol = 0; nCol < max_ID; nCol++) {

                    if (trans[nRow][nCol]!=0.0) {
                        
                    	trans[nRow][nCol] = (double) trans[nRow][nCol]/ degree[nRow];
                    	//System.out.println("a"+trans[nRow][nCol]);
                    } else {
                        trans[nRow][nCol] = 0.0;
                    }

                }
            } else {
                for (int nCol = 0; nCol < noOfNodes; nCol++) {

                    trans[nRow][nCol] = 0.0;
                }

            }
        }
    }
}