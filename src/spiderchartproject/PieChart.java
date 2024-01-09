package spiderchartproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart {

    String semester;
    String subject;

    public PieChart() {

    }

    public PieChart(String sem, String sub) {
        semester = sem;
        subject = sub;
    }

    public void drawPie() {
        try {
            int ap = 0, a = 0, am = 0, bp = 0, b = 0, bm = 0, cp = 0, c = 0, cm = 0, f = 0;

            DefaultPieDataset dataset = new DefaultPieDataset();

            Connection conn = DbConnection.getConnection();
            String query = "select " + subject + " from " + semester;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int n;
            while (rs.next()) {
                n = rs.getInt(1);

                if (n >= 80 && n <= 100) {
                    ap++;
                } else if (n >= 75 && n < 80) {
                    a++;
                } else if (n >= 70 && n < 75) {
                    am++;
                } else if (n >= 65 && n < 70) {
                    bp++;
                } else if (n >= 60 && n < 65) {
                    b++;
                } else if (n >= 55 && n < 60) {
                    bm++;
                } else if (n >= 50 && n < 55) {
                    cp++;
                } else if (n >= 45 && n < 50) {
                    c++;
                } else if (n >= 40 && n < 45) {
                    cm++;
                } else if (n >= 0 && n < 40) {
                    f++;
                }
            }

            dataset.setValue("A+   ->" + ap, ap);
            dataset.setValue("A   ->" + a, a);
            dataset.setValue("A-   ->" + am, am);
            dataset.setValue("B+   ->" + bp, bp);
            dataset.setValue("B   ->" + b, b);
            dataset.setValue("B-   ->" + bm, bm);
            dataset.setValue("C+   ->" + cp, cp);
            dataset.setValue("C   ->" + c, c);
            dataset.setValue("C-   ->" + cm, cm);
            dataset.setValue("F   ->" + f, f);

            JFreeChart chart = ChartFactory.createPieChart("Grade Distribution of " + subject, dataset, true, true, false);
            PiePlot plot = (PiePlot) chart.getPlot();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 400));
            JFrame frame = new JFrame("Grade Distribution Chart");
            //frame.setBounds(500,600,500,400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.getContentPane().add(chartPanel);
            frame.pack();
            frame.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PieChart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PieChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
