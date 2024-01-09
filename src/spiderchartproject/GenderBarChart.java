package spiderchartproject;

import java.sql.Statement;
import java.sql.DriverManager;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenderBarChart {

    public void drawBar() {
        int m = 0, f = 0;
        String g;
        try {            
            Connection conn = DbConnection.getConnection();
            String query = "select * from users where access=\'student\'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                g = rs.getString(6);
                if (g.equals("male")) {
                    m++;
                } else {
                    f++;
                }
            }
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(m, "Male", "");
            dataset.addValue(f, "Female", "");

            JFreeChart barChart = ChartFactory.createBarChart("Sample Bar Chart", "Gender", "Value", dataset, PlotOrientation.VERTICAL, true, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 400));

            JFrame frame = new JFrame("Bar Chart Example");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(chartPanel);
            frame.pack();
            frame.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenderBarChart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GenderBarChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
