package com.WhaleDB.spatiotemp.processor.test;

import org.junit.Before;
import org.junit.Test;

import com.WhaleDB.spatiotemp.datamodel.datatype.TemporalLineString;
import com.WhaleDB.spatiotemp.datamodel.result.SpatioObjectResultSet;
import com.WhaleDB.spatiotemp.processor.RtreeSearcher;

import edu.umn.cs.spatialHadoop.core.Point;
import edu.umn.cs.spatialHadoop.core.Rectangle;

public class RtreeSearcherTest2 {
	String r_tree_path;
	RtreeSearcher<TemporalLineString> r_searcher;
	Rectangle query_rec;
	Point query_point;
	
	@Before
	public void setUp() throws Exception {
		
		//linestring R-tree index path
		r_tree_path	 = "hdfs://9.186.88.253:8010/user/hadoop/test_linestr.grid/data_00001";
		
		query_rec = new Rectangle(5,5,10,10);
		query_point = new Point(10,20);
		 
		if(r_searcher == null)
			r_searcher = new RtreeSearcher<TemporalLineString>(r_tree_path, new TemporalLineString());
	}
	
	@Test
	public void Range_Query_MemBased()
	{
		try {
			int cnt = 0;
			r_searcher.LoadtoMemory(true);
			
			SpatioObjectResultSet rset = r_searcher.Range_Query(query_rec);
			
			while(rset.next())
			{
				Object obj = rset.getObject(0);
				System.out.println(obj);
				++cnt;
			}
			
			rset.close();
			System.out.println("total result size: " + cnt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	@Test
	public void Range_Query_DiskBased()
	{
		try {
			int cnt = 0;
			r_searcher.LoadtoMemory(false);
			
			SpatioObjectResultSet rset = r_searcher.Range_Query(query_rec);
			
			while(rset.next())
			{
				Object obj = rset.getObject(0);
				System.out.println(obj);
				++cnt;
			}
			
			rset.close();
			System.out.println("total result size: " + cnt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void KNN_Query_MemBased()
	{
		try {
			r_searcher.LoadtoMemory(true);

			int cnt = 0;
			
			SpatioObjectResultSet rset = r_searcher.KNN_Query(query_point,100);
			
			while(rset.next())
			{
				Object obj = rset.getObject(0);
				System.out.println(obj);
				++cnt;
			}
			
			rset.close();
			System.out.println("total result size: " + cnt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void KNN_Query_DiskBased()
	{
		try {
			r_searcher.LoadtoMemory(false);

			int cnt = 0;
			
			SpatioObjectResultSet rset = r_searcher.KNN_Query(query_point,100);
			
			while(rset.next())
			{
				Object obj = rset.getObject(0);
				System.out.println(obj);
				++cnt;
			}
			
			rset.close();
			System.out.println("total result size: " + cnt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
