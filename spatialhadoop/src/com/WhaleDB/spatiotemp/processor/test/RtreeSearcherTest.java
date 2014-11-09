package com.WhaleDB.spatiotemp.processor.test;

import org.junit.Before;
import org.junit.Test;

import com.WhaleDB.spatiotemp.datamodel.result.SpatioObjectResultSet;
import com.WhaleDB.spatiotemp.processor.RtreeSearcher;

import edu.umn.cs.spatialHadoop.core.Point;
import edu.umn.cs.spatialHadoop.core.Rectangle;

public class RtreeSearcherTest {
	String r_tree_path;
	RtreeSearcher<Rectangle> r_searcher;
	
	@Before
	public void setUp() throws Exception {
		r_tree_path = "hdfs://9.186.88.253:8010/user/hadoop/test.rtree/data_00001";
		
		if(r_searcher == null)
			r_searcher = new RtreeSearcher<Rectangle>(r_tree_path, new Rectangle());
	}
	
	@Test
	public void Range_Query_MemBased()
	{
		try {
			int cnt = 0;
			r_searcher.LoadtoMemory(true);
			
			SpatioObjectResultSet rset = r_searcher.Range_Query(new Rectangle(10,200,100,400));
			
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
			
			SpatioObjectResultSet rset = r_searcher.Range_Query(new Rectangle(10,200,100,400));
			
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
			
			SpatioObjectResultSet rset = r_searcher.KNN_Query(new Point(10,20),100);
			
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
			
			SpatioObjectResultSet rset = r_searcher.KNN_Query(new Point(10,20),100);
			
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
