package com.WhaleDB.spatiotemp.datamodel.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.umn.cs.spatialHadoop.core.ResultCollector2;
import edu.umn.cs.spatialHadoop.core.Shape;

public class SpatialResultCollector2<T extends Shape, S extends Entry<T ,Double> > extends ResultSource<T> implements ResultCollector2<T,Double> {

	public static class KNNEntry<K, V> implements Map.Entry<K, V>
	{
		private K key;
		private V value;
		
		public KNNEntry(K k, V v)
		{
			this.key = k;
			this.value = v;
		}
		
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}
	}
	
	protected ArrayList<Entry<T,Double>> result_set;

	protected Iterator<Entry<T,Double>> iterator;
	
	protected boolean isopened,isclosed;

	public SpatialResultCollector2() throws Exception
	{
		result_set = new ArrayList<Entry<T,Double>>();
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
		return iterator.next().getKey();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void collect(T r, Double s) {
		result_set.add(new KNNEntry<T,Double>((T)r.clone(),s.doubleValue()));
	}


	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
