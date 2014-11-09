package com.WhaleDB.spatiotemp.processor;

import java.util.Collection;

import com.WhaleDB.spatiotemp.datamodel.result.SpatioObjectResultSet;

import edu.umn.cs.spatialHadoop.core.Point;
import edu.umn.cs.spatialHadoop.core.Shape;

public interface SpatialSearcher {
	 
	public void LoadSpatialObjects() throws Exception;
	
	/**
	   * Performs a range query over this tree using the given query range.
	   * @param query - The query rectangle to use (TODO make it any shape not just rectangle)
	   * @param output - Shapes found are reported to this output. If null, results are not reported
	   * @return - Total number of records found
	   */
	  public SpatioObjectResultSet Range_Query(Shape queryshape) throws Exception;
	
	  /**
	   * Perorm a knn neighbors over spatialobjects soruce to the given query
	   * @param queryshape
	   * @param k
	   * @return
	   */
	  public SpatioObjectResultSet KNN_Query(Point queryshape, int k) throws Exception;
	  
	  /**
	   * 
	   * @param collection1
	   * @param collition2
	   * @return
	   * @throws Exception
	   */
	  public SpatioObjectResultSet spatialJoin(Collection<Shape> collection1, Collection<Shape> collition2) throws Exception;
	  
	  
	 
}
