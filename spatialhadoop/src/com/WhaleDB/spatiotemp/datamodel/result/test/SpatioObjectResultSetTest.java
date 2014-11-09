package com.WhaleDB.spatiotemp.datamodel.result.test;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.WhaleDB.spatiotemp.datamodel.result.HDFSFileResultSource;
import com.WhaleDB.spatiotemp.datamodel.result.SpatioObjectResultSet;

public class SpatioObjectResultSetTest {
	public String path;
	private HDFSFileResultSource src;
	private SpatioObjectResultSet rset;
	@Before
	public void setUp() throws Exception {
		path = "hdfs://9.186.88.253:8010/user/hadoop/rq_results_1";
		src = new HDFSFileResultSource(path);
		rset = new SpatioObjectResultSet(src);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void TestSimpleReadResultSet() throws Exception{
		int cnt = 0;
		
		while(rset.next())
		{
			Object obj = rset.getObject(0);
			System.out.println(obj);
			++cnt;
		}
		
		rset.close();
		
		System.out.println(String.format("cnt = %d", cnt));
		Assert.assertEquals(cnt, rset.getRow());
	}
}
