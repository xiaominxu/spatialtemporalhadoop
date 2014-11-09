package com.WhaleDB.spatiotemp.gpsdata;

import org.apache.commons.lang.NotImplementedException;

import com.WhaleDB.spatiotemp.index.GPSURI;
/**
 * 
 * @author xuxiaomin
 *
 */

public class GPSStream {

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
	
	private GPSURI targetURI;
	
	public GPSStream(GPSURI uri)
	{
		this.targetURI = uri;
	}
	
	public GPSURI getURI()
	{
		return this.targetURI;
	}
	
	public TemporalPoint getNextPoint() throws Exception
	{
		throw new NotImplementedException("not implemented now");
	}
	
}
