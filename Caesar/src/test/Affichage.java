
package test;

import 
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;

public class Affichage {

    public static void main(String[] args) {
        //create a dataset...
        int[][] a2 = new int[10][5];

        //print array in rectangular form
        for (int r = 0; r < a2.length; r++) {
            for (int c = 0; c < a2[r].length; c++) {
                System.out.print(" " + a2[r][c]);
            }
            System.out.println("");
        }

        //create a chart...
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Plot", //chart title
                "X", //x axis label
                "Y", //y axis label
                a2, //data  ***-----PROBLEM------***
                PlotOrientation.VERTICAL,
                true, //include legend
                true, //tooltips
                false //urls
        );

        //create and display a frame...
        ChartFrame frame = new ChartFrame("First", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
