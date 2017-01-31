package Parkeersimulator;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Test extends JFrame {

	private JPanel contentPane;

	
	/**
    DefaultPieDataset piedataset = new DefaultPieDataset();  
    piedataset.setValue("Normal spots", new Integer(70));  
    piedataset.setValue("Pass spots", new Integer(15));  
    piedataset.setValue("Electric spots", new Integer(15));  
    JFreeChart piechart = ChartFactory.createPieChart(  
      null,   // Title  
      piedataset,             // Dataset  
      true,                   // Show legend  
      false,                   // Use tooltips  
      false                   // Generate URLs  
    );  
    piechart.removeLegend();
    
    
    ChartPanel chPanel = new ChartPanel(piechart); //creating the chart panel, which extends JPanel
    chPanel.setPreferredSize(new Dimension(785, 440)); //size according to my window
    **/
    /**
    XYSeries series = new XYSeries("Real time graph");
    XYDataset data = new XYSeriesCollection(series);
    JFreeChart chart = ChartFactory.createXYLineChart("Real time graph", "x-series",
      "y-series", data, PlotOrientation.VERTICAL, true, true, true);
    chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.blue);
    //ChartFrame chartFrame = new ChartFrame("RealTimeChart", chart);
    //chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //chartFrame.pack();
    //chartFrame.setVisible(true);
    for (int i = 0; i < 1000;) {
     try {
		Thread.sleep(5000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
     series.add(i++, Math.random() * 1000);
     series.add(i++, Math.random() * 1000);
     series.add(i++, Math.random() * 1000);
     series.add(i++, Math.random() * 1000);
     series.add(i++, Math.random() * 1000);
     chart = ChartFactory.createXYLineChart("Real time graph", "x-series", "y-series",
       data, PlotOrientation.VERTICAL, true, true, true);
     //chartFrame = new ChartFrame("Real time graph", chart);
     ChartPanel chPanel = new ChartPanel(chart); //creating the chart panel, which extends JPanel
     chPanel.setPreferredSize(new Dimension(785, 440)); //size according to my window
    **/
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
