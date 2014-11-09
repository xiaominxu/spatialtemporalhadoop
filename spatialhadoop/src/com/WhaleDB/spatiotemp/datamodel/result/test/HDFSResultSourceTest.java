package com.WhaleDB.spatiotemp.datamodel.result.test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.WhaleDB.spatiotemp.datamodel.result.HDFSFileResultSource;

public class HDFSResultSourceTest {
	
	public String path;
	private HDFSFileResultSource src;
	
	@Before
	public void setUp() throws Exception {
		path = "hdfs://9.186.88.253:8010/user/hadoop/rq_results_1";
		src = new HDFSFileResultSource(path);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void TestSimpleRead() throws Exception{
		int cnt = 0;
		src.Open();
		while(src.hasNext())
		{
			System.out.println(src.next());
			++cnt;
		}
		src.Close();
		System.out.println(String.format("cnt = %d", cnt));
	}
}
