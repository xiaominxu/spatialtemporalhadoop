package com.WhaleDB.spatiotemp.datamodel.datatype;

import edu.umn.cs.spatialHadoop.core.Point;

public abstract class DistanceCalculator {

	public static final byte dist_unit_meter = 0x01; 
	public static final byte dist_unit_kilometer = 0x02; 
	
	public static final byte time_unit_second = 0x01; 
	public static final byte time_unit_hour = 0x02; 
	
	public static double cptKmPerhour(double sec_meter_speed)
	{
		return sec_meter_speed * 3.6;
	}
	
	public double cptDistance(Point p1, Point p2)
	{
		return cptDistance(p1.x,p1.y, p2.x, p2.y);
	}
	
	public abstract double cptDistance(double p1_x, double p1_y, double p2_x, double p2_y);
	
}
