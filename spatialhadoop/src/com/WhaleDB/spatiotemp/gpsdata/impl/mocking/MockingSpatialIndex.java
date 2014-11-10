package com.WhaleDB.spatiotemp.gpsdata.impl.mocking;

import java.util.Collection;

import com.WhaleDB.spatiotemp.index.SpatialIndex;

import edu.umn.cs.spatialHadoop.core.Rectangle;
import edu.umn.cs.spatialHadoop.core.Shape;

public class MockingSpatialIndex extends SpatialIndex{

	@Override
	public Collection<Shape> Range_Query(Rectangle qmbr) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Shape> KNN_Query(Rectangle qmbr, int k) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getRectangle(double cover_ratio) {
		// TODO Auto-generated method stub
		return null;
	}

}
