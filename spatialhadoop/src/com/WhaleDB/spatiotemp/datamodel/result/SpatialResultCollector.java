package com.WhaleDB.spatiotemp.datamodel.result;

import java.util.ArrayList;
import java.util.Iterator;

import edu.umn.cs.spatialHadoop.core.ResultCollector;
import edu.umn.cs.spatialHadoop.core.Shape;

public class SpatialResultCollector<T extends Shape> extends ResultSource<T> implements ResultCollector<T>{

	protected ArrayList<T> result_set = null;
	
	protected Iterator<T> iterator;
	
	protected boolean isopened,isclosed;
	
	public SpatialResultCollector() throws Exception
	{
		result_set = new ArrayList<T>();
		init();
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return iterator.hasNext();
	}

	@Override
	public T next() {
		// TODO Auto-generated method stub
		return iterator.next();
	}

	@Override
	protected boolean init() throws Exception {
		iterator = result_set.iterator();
		isopened = false;
		isclosed = false;
		return true;
	}

	@Override
	public void Open() throws Exception {
		isopened = true;
	}

	@Override
	public boolean isOpen() {
		return isopened;
	}

	@Override
	public void Close() throws Exception {
		isclosed = true;
	}

	@Override
	public boolean isClosed() throws Exception {
		return isclosed;
	}

	@Override
	public void collect(T r) {
		/*do not insert obj's reference but a copy of object*/
		result_set.add((T)r.clone());
	}

}
