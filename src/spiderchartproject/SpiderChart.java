package spiderchartproject;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

public class SpiderChart {

    String semester;

    public SpiderChart() {
    }

    public SpiderChart(String sem) {
        semester = sem;
    }

    public void drawSpider() {
        DefaultCategoryDataset dataset = createScaledDataset();

        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        plot.setStartAngle(90);
        JFreeChart chart = new JFreeChart(semester + " Spider Chart", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        TextTitle title = new TextTitle("Performance  Metrics  of  " + semester + " of " + User.getName());
        title.setFont(new Font("Arial", Font.BOLD, 18));
        chart.setTitle(title);

        plot.setBackgroundPaint(Color.white);
        plot.setWebFilled(true);
        plot.setLabelFont(new Font("Consolas", Font.PLAIN, 12));
        plot.setMaxValue(100);

        ChartPanel chartPanel = new ChartPanel(chart);

        // Create a panel to display the marks
        JPanel marksPanel = createMarksPanel();

        JFrame frame = new JFrame("Spider Chart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Add the chart panel to the top
        frame.getContentPane().add(chartPanel, BorderLayout.NORTH);

        // Add the marks panel below the chart
        frame.getContentPane().add(marksPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(723, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public DefaultCategoryDataset createScaledDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            Connection conn = DbConnection.getConnection();
            String query = "select * from " + semester + " where username=\'" + User.getUname() + "\'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            List<String> subjects = new ArrayList<>();
            int columnCount = rsmd.getColumnCount();
            for (int i = 2; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                subjects.add(columnName);
            }
            rs.next();
            int c = 2;
            for (String subject : subjects) {
                dataset.addValue(rs.getInt(c), "Semester-1", subject);
                c++;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dataset;
    }

    private JPanel createMarksPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add labels and marks for each subject
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spider", "root", "root");
            String query = "select * from " + semester + " where username='" + User.getUname() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            List<String> subjects = new ArrayList<>();
            int columnCount = rsmd.getColumnCount();
            for (int i = 2; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                subjects.add(columnName);
            }

            rs.next();
            for (String subject : subjects) {
                JLabel label = new JLabel(subject + ":");
                int marks = rs.getInt(subject);
                JLabel marksLabel = new JLabel(String.valueOf(marks)+"      ");
                panel.add(label);
                panel.add(marksLabel);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return panel;
    }
}
