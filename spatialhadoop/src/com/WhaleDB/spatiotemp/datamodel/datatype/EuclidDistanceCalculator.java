package com.WhaleDB.spatiotemp.datamodel.datatype;

public class EuclidDistanceCalculator extends DistanceCalculator {

	@Override
	public double cptDistance(double p1_x, double p1_y, double p2_x, double p2_y) {
		return Math.sqrt( (p2_x- p1_x)*(p2_x- p1_x) + (p2_y - p1_y)*(p2_y- p1_y));
	}


}
