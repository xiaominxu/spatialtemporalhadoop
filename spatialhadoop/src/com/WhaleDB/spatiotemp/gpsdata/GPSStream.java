package com.WhaleDB.spatiotemp.gpsdata;

import com.WhaleDB.spatiotemp.index.GPSURI;
/**
 * 
 * @author xuxiaomin
 *
 */

public abstract class GPSStream {

	public static class TemporalPoint
	{
		public long timestamp;
		public double x;
		public double y;
		public TemporalPoint(long ts, double x, double y)
		{
			this.timestamp = ts;
			this.x = x;
			this.y = y;
		}
	}
	
	protected GPSURI targetURI;
	
	public GPSURI getURI()
	{
		return this.targetURI;
	}
	
	public abstract TemporalPoint getNextPoint() throws Exception;
	
}
