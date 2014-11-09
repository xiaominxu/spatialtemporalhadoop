package com.WhaleDB.spatiotemp.index;

import org.apache.commons.lang.NotImplementedException;
import org.apache.zookeeper.KeeperException.UnimplementedException;

/**
 * 
 * @author xuxiaomin
 * containing time range information
 */

/**P1 to do: add json support for uri*/
public class GPSURI {
	
	public long tsid;
	public long start_timestamp;
	public byte timescale;
	
	public GPSURI(long tsid, long starts, byte t_scale)
	{
		this.tsid = tsid;
		this.start_timestamp = starts;
		this.timescale = t_scale;
	}
	
	public long getStart_timestamp() {
		return start_timestamp;
	}

	public void setStart_timestamp(long start_timestamp) {
		this.start_timestamp = start_timestamp;
	}

	public byte getTimescale() {
		return timescale;
	}

	public void setTimescale(byte timescale) {
		this.timescale = timescale;
	}

	public void setGPSid(long id)
	{
		this.tsid = id;
	}
	
	public long getGPSid()
	{
		return tsid;
	}
	
	/**
	 * get related spatialindex according to tsid startts, byte scale
	 * @return
	 */
	public SpatialIndex getRelatedSpatialIndex() throws Exception
	{
		throw new NotImplementedException("not implemented now");
	}
	
	@Override
	public int hashCode() {
		return (int)tsid | (int) start_timestamp | (int)timescale;
	}
	
	
}
