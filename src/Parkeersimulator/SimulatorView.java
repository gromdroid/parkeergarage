package Parkeersimulator;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    
    static JLabel  totalLabel;
    static String  state;
    JButton reset, pauseButton;
    static DefaultPieDataset piedataset;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView();
        
        //create frame with menubar
        JFrame frame = new JFrame("Parkeergarage");
        
        //main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2,3,3));
        panel.setBackground(Color.black);
        
        carParkView.setBackground(Color.black);
        
        //pieChart
        piedataset = new DefaultPieDataset();  
        piedataset.setValue("Normal cars", 0);  
        piedataset.setValue("Passholders", 0);  
        piedataset.setValue("Electric cars", 0); 
        piedataset.setValue("Electric cars with pass", 0);  
        piedataset.setValue("Free spots", 0);
        
        JFreeChart piechart = ChartFactory.createPieChart(  
          null,   // Title  
          piedataset,             // Dataset  
          true,                   // Show legend  
          false,                   // Use tooltips  
          false                   // Generate URLs  
        );  
        
        PiePlot plot = (PiePlot) piechart.getPlot();

        plot.setSectionPaint("Normal cars", Color.red);
        plot.setSectionPaint("Passholders", Color.blue);
        plot.setSectionPaint("Electric cars", Color.green);
        plot.setSectionPaint("Electric cars with pass", Color.orange);
        plot.setSectionPaint("Free spots", Color.white);
        
        piechart.removeLegend();
        piechart.getPlot().setOutlineVisible(false);
        piechart.getPlot().setBackgroundPaint(Color.white);
        
        //piechart panel
        ChartPanel chPanel = new ChartPanel(piechart); //creating the chart panel, which extends JPanel
        chPanel.setBackground(Color.white);
        //Add panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        
        JButton fastButton = new JButton("Faster");   
        
        fastButton.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  if(Simulator.tickPause < 7){
          			  
          		  } else {
          			Simulator.tickPause *= 0.5;
          		  }
        	  } 
        		  
        	} );
        
        
        pauseButton = new JButton("Pause");
        
        pauseButton.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  if(pauseButton.getText() == "Pause"){
        			  Simulator.pauseState = !Simulator.pauseState;
        			  pauseButton.setText("Start");
        		  } else {
        			  Simulator.pauseState = !Simulator.pauseState;
        			  pauseButton.setText("Pause");
        		  }
        	  } 
        		  
        	} );
        
        
        JButton slowButton = new JButton("Slower");
        
        slowButton.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  if(Simulator.tickPause > 1600){
      			  
      		  } else {
      			Simulator.tickPause *= 2;
      		  }
      	  } 
      		  
      	} );
        
        reset = new JButton("Reset");
        
        reset.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		Simulator.minute = 0;
      		Simulator.hour = 0;
      		Simulator.day = 0;
      		Simulator.week = 0;
      		
      		Simulator.deleteCars();
      		Simulator.simulatorView.updateView();
      		
      			if(pauseButton.getText() == "Pause"){
        			  Simulator.pauseState = !Simulator.pauseState;
        			  pauseButton.setText("Start");
            		} else {
        			  Simulator.pauseState = !Simulator.pauseState;
        			  pauseButton.setText("Pause");
            		}	
      		}
      	  
      		  
      	} );
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3,2,2,2));
        
        JTextField inputNormal = new JTextField();
        
        JButton buttonInputNormal = new JButton("Set normal arrivals per hour");
        buttonInputNormal.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  Simulator.weekDayArrivals = Integer.parseInt(inputNormal.getText());
        		}
        	  
        		  
        	} );
        
        JTextField inputPass = new JTextField();
        
        JButton buttonInputPass = new JButton("Set pass arrivals per hour");
        buttonInputPass.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  Simulator.weekDayPassArrivals = Integer.parseInt(inputPass.getText());
        		}
        	  
        		  
        	} );
        
        JTextField inputElectric = new JTextField();
        
        JButton buttonInputElectric = new JButton("Set electric arrivals per hour");
        buttonInputElectric.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  Simulator.weekDayElectricArrivals = Integer.parseInt(inputElectric.getText());
        		}
        	  
        		  
        	} );
        
        inputPanel.add(buttonInputNormal);
        inputPanel.add(inputNormal);
        inputPanel.add(buttonInputPass);
        inputPanel.add(inputPass);
        inputPanel.add(buttonInputElectric);
        inputPanel.add(inputElectric);
        
        buttonPanel.add(inputPanel, BorderLayout.NORTH);
        
        buttonPanel.add(fastButton, BorderLayout.WEST);
        buttonPanel.add(pauseButton, BorderLayout.CENTER);
        buttonPanel.add(slowButton, BorderLayout.EAST);
        buttonPanel.add(reset, BorderLayout.SOUTH);
        
        
        //Add panel for information text
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.white);
        totalLabel = new JLabel("Totaal: ");
        
        textPanel.add(totalLabel);
        

        panel.add(carParkView);
        panel.add(buttonPanel);
        panel.add(chPanel);
        panel.add(textPanel);
        frame.add(panel);
        
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        frame.setSize(1000, 400);
		frame.setResizable(true);
        
        //create the file menu
        JMenu fileMenu = new JMenu("Export");
        menubar.add(fileMenu);
        
        JMenuItem expSim = new JMenuItem("Save to image");
        expSim.addActionListener((ActionEvent event) -> {
        	BufferedImage bi = new BufferedImage(carParkView.getSize().width, carParkView.getSize().height, BufferedImage.TYPE_INT_ARGB); 
			Graphics g = bi.createGraphics();
			carParkView.paint(g);  //this == JComponent
			g.dispose();
			try{ImageIO.write(bi,"png",new File(System.getProperty("user.home") + "/SIM - simulator - " + Simulator.time + ".png"));}catch (Exception e1) {}
        
			BufferedImage bi1 = new BufferedImage(chPanel.getSize().width, chPanel.getSize().height, BufferedImage.TYPE_INT_ARGB); 
			Graphics g1 = bi1.createGraphics();
			chPanel.paint(g1);  //this == JComponent
			g1.dispose();
			try{ImageIO.write(bi1,"png",new File(System.getProperty("user.home") + "/SIM - pie - " + Simulator.time + ".png"));}catch (Exception e1) {}
        });
        //openItem.addActionListener(this);
        fileMenu.add(expSim);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        updateView();
    }
    
    public Location getFirstUsedLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) != null) {
                    	removeCarAt(location);
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public void updateView() {
        carParkView.updateView();
    }
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    public Location getFirstFreeLocation(String type) {
    	if(type == "N"){
    		for (int floor = 0; floor < getNumberOfFloors(); floor++) {
    			if(floor == 0){
    				for (int row = 4; row < getNumberOfRows(); row++) {
                        for (int place = 0; place < getNumberOfPlaces(); place++) {
                            Location location = new Location(floor, row, place);
                            if (getCarAt(location) == null) {
                                return location;
                            }
                        }
                    }
    			} else if(floor == 1) {
    				for (int row = 0; row < getNumberOfRows(); row++) {
                        for (int place = 0; place < getNumberOfPlaces(); place++) {
                            Location location = new Location(floor, row, place);
                            if (getCarAt(location) == null) {
                                return location;
                            }
                        }
                    }
    			} else {
    				for (int row = 0; row < 5; row++) {
                        for (int place = 0; place < getNumberOfPlaces(); place++) {
                            Location location = new Location(floor, row, place);
                            if (getCarAt(location) == null) {
                                return location;
                            }
                        }
                    }
    			}
            }
    	} else if(type == "P"){
    		for (int floor = 0; floor < 1; floor++) {
                for (int row = 0; row < 4; row++) {
                	if(row >= 1){
                		for (int place = 0; place < getNumberOfPlaces(); place++) {
                            Location location = new Location(floor, row, place);
                            if (getCarAt(location) == null) {
                                return location;
                            }
                        }
                	} else {
                		for (int place = 15; place < getNumberOfPlaces(); place++) {
                            Location location = new Location(floor, row, place);
                            if (getCarAt(location) == null) {
                                return location;
                            }
                        }
                	}
                }
            }
    	} else if(type == "E"){
    		for (int floor = 2; floor < getNumberOfFloors(); floor++) {
                for (int row = 5; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        if (getCarAt(location) == null) {
                            return location;
                        }
                    }
                }
            }
    	} else {
    		for (int floor = 0; floor < 1; floor++) {
                for (int row = 0; row < 1; row++) {
                    for (int place = 0; place < 15; place++) {
                        Location location = new Location(floor, row, place);
                        if (getCarAt(location) == null) {
                            return location;
                        }
                    }
                }
            }
    	}
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(850, 400);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
        	setBackground(Color.white);
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        Color color = car == null ? Color.white : car.getColor();
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
