package com.WhaleDB.spatiotemp.datamodel.result;
import java.util.Iterator;

import edu.umn.cs.spatialHadoop.core.Shape;


public abstract class ResultSource<T extends Shape> implements Iterator<T>{
	protected abstract boolean init() throws Exception;
	public abstract void Open() throws Exception;
	public abstract boolean isOpen();
	public abstract void Close() throws Exception;
	public abstract boolean isClosed() throws Exception;
}




