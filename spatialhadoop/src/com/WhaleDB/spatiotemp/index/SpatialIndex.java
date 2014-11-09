package com.WhaleDB.spatiotemp.index;

import java.util.Collection;

import edu.umn.cs.spatialHadoop.core.Rectangle;
import edu.umn.cs.spatialHadoop.core.Shape;

public abstract class SpatialIndex {

	public abstract Collection<Shape> Range_Query(Rectangle qmbr) throws Exception;
	
	public abstract Collection<Shape> KNN_Query(Rectangle qmbr, int k) throws Exception;
}
