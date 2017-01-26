package Parkeersimulator;

import java.awt.Color;
import java.util.Random;

public class ParkingCarPassElectric extends Car {
	private static final Color COLOR=Color.orange;
	
	public ParkingCarPassElectric() {
		Random random = new Random();
		int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
		this.setMinutesLeft(stayMinutes);
		this.setHasToPay(false);
        this.setType("PE");
	}
	
	public Color getColor(){
		return COLOR;
	}
}
