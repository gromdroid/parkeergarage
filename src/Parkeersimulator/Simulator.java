package Parkeersimulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class Simulator {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String Electric = "3";
	private static final String PassElectric = "4";
	
	static int randomFloor1, randomRow1, randomPlace1, 
	randomFloor2, randomRow2, randomPlace2, 
	randomFloor3, randomRow3, randomPlace3, 
	randomFloor4, randomRow4, randomPlace4,
	randomFloor5, randomRow5, randomPlace5;

	private static CarQueue entranceCarQueue;
    private static CarQueue entrancePassQueue;
    private static CarQueue entranceElectricQueue;
    private static CarQueue entrancePassElectricQueue;

    private static CarQueue paymentCarQueue;
    private static CarQueue exitCarQueue;
    static SimulatorView simulatorView;
    
    static int week = 0;
    static int day = 0;
    static int hour = 0;
    static int minute = 0;
    static String time;
    static int[][] randomList;
    
    String timeHour, timeMinute;
    
    static int totalCarsParkingE;
    static int totalCarsParkingN;
    static int totalCarsParkingP;
    static int totalCarsParkingPE;
    static int totalCarsQueue;
    static int totalCarsPaying;

    static long tickPause = 100;
    static boolean pauseState;

    static int weekDayArrivals= 175; // average number of arriving cars per hour
    int weekendArrivals = 275; // average number of arriving cars per hour
    static int weekDayPassArrivals= 30; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    static int weekDayElectricArrivals= 20; //average number of electric cars per hour
    int weekendElectricArrivals= 5; //average number of electric cars per hour
    int weekDayPassElectricArrivals= 20; //average number of electric cars per hour
    int weekendPassElectricArrivals= 5; //average number of electric cars per hour
    
    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    
    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        entranceElectricQueue = new CarQueue();
        entrancePassElectricQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);
    }

    
    public void run() {
    	generatePreserved();
        while (0 == 0 ) {
        	tick();
        }
    }

    public static void generatePreserved() {
    	Random rand = new Random();

    	int random1 = rand.nextInt(2-0) + 0;
    	if(random1 == 0){
    		randomRow1 = rand.nextInt(5-4) + 4;
        	randomPlace1 = rand.nextInt(29-0) + 0;
    	} else if(random1 == 1){
    		randomRow1 = rand.nextInt(5-0) + 0;
        	randomPlace1 = rand.nextInt(29-0) + 0;
    	} else if(random1 == 2){
    		randomRow1 = rand.nextInt(4-0) + 0;
        	randomPlace1 = rand.nextInt(29-0) + 0;
    	}
    	randomFloor1 = random1;
    	
    	int random2 = rand.nextInt(2) + 0;
    	if(random2 == 0){
    		randomRow2 = rand.nextInt(5-4) + 4;
        	randomPlace2 = rand.nextInt(29-0) + 0;
    	} else if(random2 == 1){
    		randomRow2 = rand.nextInt(5-0) + 0;
        	randomPlace2 = rand.nextInt(29-0) + 0;
    	} else if(random2 == 2){
    		randomRow2 = rand.nextInt(4-0) + 0;
        	randomPlace2 = rand.nextInt(29-0) + 0;
    	}
    	randomFloor2 = random2;
    	
    	int random3 = rand.nextInt(2) + 0;
    	if(random3 == 0){
    		randomRow3 = rand.nextInt(5-4) + 4;
        	randomPlace3 = rand.nextInt(29-0) + 0;
    	} else if(random3 == 1){
    		randomRow3 = rand.nextInt(5-0) + 0;
        	randomPlace3 = rand.nextInt(29-0) + 0;
    	} else if(random3 == 2){
    		randomRow3 = rand.nextInt(4-0) + 0;
        	randomPlace3 = rand.nextInt(29-0) + 0;
    	}
    	randomFloor3 = random3;
    	
    	int random4 = rand.nextInt(2) + 0;
    	if(random4 == 0){
    		randomRow4 = rand.nextInt(5-4) + 4;
        	randomPlace4 = rand.nextInt(29-0) + 0;
    	} else if(random4 == 1){
    		randomRow4 = rand.nextInt(5-0) + 0;
        	randomPlace4 = rand.nextInt(29-0) + 0;
    	} else if(random4 == 2){
    		randomRow4 = rand.nextInt(4-0) + 0;
        	randomPlace4 = rand.nextInt(29-0) + 0;
    	}
    	randomFloor4 = random4;
    	
    	int random5 = rand.nextInt(2) + 0;
    	if(random5 == 0){
    		randomRow5 = rand.nextInt(5-4) + 4;
        	randomPlace5 = rand.nextInt(29-0) + 0;
    	} else if(random5 == 1){
    		randomRow5 = rand.nextInt(5-0) + 0;
        	randomPlace5 = rand.nextInt(29-0) + 0;
    	} else if(random5 == 2){
    		randomRow5 = rand.nextInt(4-0) + 0;
        	randomPlace5 = rand.nextInt(29-0) + 0;
    	}
    	randomFloor5 = random5;
	}


	public void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private void advanceTime(){
    	if(!pauseState){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
            week++;
        }
    	} else {
    		//do nothing
    	}

    }

    private void handleEntrance(){
    	if(!pauseState){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    	carsEntering(entranceElectricQueue);
    	carsEntering(entrancePassElectricQueue);
    	} else {
    		//do nothing electric car toegevoegd
    	}
    }
    
    private void handleExit(){
    	if(!pauseState){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    	} else {
    		//do nothing
    	}
    }
    
    private void updateViews(){
    	if(!pauseState){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
        
        int totalFreeSpots = 540 - (totalCarsParkingN + totalCarsParkingP + totalCarsParkingE + totalCarsParkingPE);
        SimulatorView.piedataset.setValue("Normal cars", totalCarsParkingN);  
        SimulatorView.piedataset.setValue("Passholders", totalCarsParkingP);  
        SimulatorView.piedataset.setValue("Electric cars", totalCarsParkingE);  
        SimulatorView.piedataset.setValue("Electric cars with pass", totalCarsParkingPE);
        SimulatorView.piedataset.setValue("Free spots", totalFreeSpots);
        
        if(hour < 10){
        	timeHour = "0" + hour;
        } else {
        	timeHour = String.valueOf(hour);
        }
        
        if(minute < 10){
        	timeMinute = "0" + minute;
        } else {
        	timeMinute = String.valueOf(minute);
        }
        time = timeHour + ":" + timeMinute;
        SimulatorView.totalLabel.setText("<html>Total amount of cars parked: " + String.valueOf(totalCarsParkingE + totalCarsParkingN + totalCarsParkingP) + "<br> "
        		+ "Total amount of cars in queue: " + String.valueOf(totalCarsQueue) + "<br> "
        		+ "Total amount of people paying: " + String.valueOf(totalCarsPaying) + "<br>"
        		+ "Time passed: " + time + "<br> "
        		+ "Days passed: " + String.valueOf(day) + "<br> "
        		+ "Weeks passed: " + String.valueOf(week) + "<br>"
        		+ randomFloor1 + "" + randomRow1 + "" + randomPlace1 + "<br>"
        		+ randomFloor2 + "" + randomRow2 + "" + randomPlace2 + "<br>"
        		+ randomFloor3 + "" + randomRow3 + "" + randomPlace3 + "<br>"
        		+ "</html>");
    	} else {
    		//do nothing
    	}
    }
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayElectricArrivals, weekendElectricArrivals);
        addArrivingCars(numberOfCars, Electric);
        numberOfCars=getNumberOfCars(weekDayPassElectricArrivals, weekendPassElectricArrivals);
        addArrivingCars(numberOfCars, PassElectric);
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			simulatorView.getNumberOfOpenSpots()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = simulatorView.getFirstFreeLocation(car.getType());
            simulatorView.setCarAt(freeLocation, car);
            i++;
            //totalCarsParking++;
        }
    	totalCarsQueue = queue.carsInQueue();
    }
    
    public static void deleteCars(){
    	generatePreserved();
    	entrancePassQueue.clearQueue();
    	entranceCarQueue.clearQueue();
    	entranceElectricQueue.clearQueue();
    	entrancePassElectricQueue.clearQueue();
    	paymentCarQueue.clearQueue();
    	exitCarQueue.clearQueue();
    	totalCarsParkingE = 0;
    	totalCarsParkingN = 0;
    	totalCarsParkingP = 0;
    	simulatorView.updateView();
    	
    	SimulatorView.piedataset.setValue("Normal cars", 0);  
        SimulatorView.piedataset.setValue("Passholders", 0);  
        SimulatorView.piedataset.setValue("Electric cars", 0);  
        SimulatorView.piedataset.setValue("Electric cars with pass", 0); 
    	
    	for(int i = 0; i < 539; i++){
    		Location usedLocation = simulatorView.getFirstUsedLocation();

    	}
    	
    	SimulatorView.totalLabel.setText("<html>Total amount of cars parked: 0<br> "
        		+ "Total amount of cars in queue: 0<br> "
        		+ "Total amount of people paying: 0<br>"
        		+ "Time passed: 00:00<br> "
        		+ "Days passed: 0<br> "
        		+ "Weeks passed: 0</html>");
    	
    	
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            totalCarsPaying = paymentCarQueue.carsInQueue();
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            	totalCarsParkingN++;
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            	totalCarsParkingP++;
            }
            break;
    	case Electric:
    		for (int i = 0; i < numberOfCars; i++) {
    			entranceElectricQueue.addCar(new ParkingCarElectric());
    			totalCarsParkingE++;
    		}
    		break;
    	case PassElectric:
    		for (int i = 0; i < numberOfCars; i++) {
    			entrancePassElectricQueue.addCar(new ParkingCarPassElectric());
    			totalCarsParkingPE++;
    		}
    		break;
    	}
    }
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    	if(car.getType() == "N"){
    		totalCarsParkingN--;
    	} else if (car.getType() == "E"){
    		totalCarsParkingE--;
    	} else if(car.getType() == "P"){
    		totalCarsParkingP--;
    	} else if(car.getType() == "PE"){
    		totalCarsParkingPE--;
    	}
    }

}
