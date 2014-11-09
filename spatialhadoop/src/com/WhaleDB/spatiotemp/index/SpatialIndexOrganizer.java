package com.WhaleDB.spatiotemp.index;

import java.util.Collection;

import edu.umn.cs.spatialHadoop.core.Rectangle;

/**
 * 
 * @author xuxiaomin
 *	organize index in temporal order
 */

public abstract class SpatialIndexOrganizer {
	
	public static final byte TimeUnit_Day = (byte) 0x1;
	public static final byte TimeUnit_Month = (byte) 0x2;
	public static final byte TimeUnit_Year = (byte) 0x3;
	
	public abstract Collection<GPSURI> getURIsBySpatialTemporalRange(long startts,
			long endts, Rectangle query_mbr) throws Exception;
	
	public abstract Collection<GPSURI> getURIsBySpatialTemporalRange(long gps_id, long startts,
			long endts) throws Exception;
	
	public abstract Collection<GPSURI> getURIsBySpatialTemporalRange(long gps_id, long startts,
			long endts, Rectangle query_mbr) throws Exception;
	
}
