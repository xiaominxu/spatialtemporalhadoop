package com.WhaleDB.spatiotemp.index;

import java.util.Collection;

import edu.umn.cs.spatialHadoop.core.Rectangle;

public abstract class PrimaryIndexExecuter{
	IndexDataWriter idx_writer;
	IndexDataSearcher idx_searcher;
	
	public abstract boolean initSpatialIndexMetaStorage() throws Exception;
	
	public abstract boolean BuildSpatialIndex(IndexDataURI targeturi, boolean overwrite)
			throws Exception;

	public abstract Collection<GPSURI> Range_Query(long startts, long endts, Rectangle qmbr) throws Exception;
	
	public abstract Collection<GPSURI> KNN_Query(long startts, long endts, Rectangle qmbr, int k) throws Exception;

}
