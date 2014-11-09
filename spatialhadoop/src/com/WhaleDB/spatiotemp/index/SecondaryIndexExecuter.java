package com.WhaleDB.spatiotemp.index;

import java.util.Collection;

import edu.umn.cs.spatialHadoop.core.Rectangle;

/**
 * 
 * @author xuxiaomin
 *	organize index in temporal order
 */

public abstract class SecondaryIndexExecuter {

	IndexDataWriter idx_writer;
	IndexDataSearcher idx_reader;

	public abstract boolean initSpatialIndexMetaStorage() throws Exception;
	
	public abstract boolean BuildSpatialIndex(IndexDataURI targeturi, boolean overwrite)
			throws Exception;
	
	public abstract Collection<GPSURI> getURIsBySpatialTemporalRange(long gps_id, long startts,
			long endts) throws Exception;
	
	public abstract Collection<GPSURI> getURIsBySpatialTemporalRange(long gps_id, long startts,
			long endts, Rectangle query_mbr) throws Exception;
	
}
