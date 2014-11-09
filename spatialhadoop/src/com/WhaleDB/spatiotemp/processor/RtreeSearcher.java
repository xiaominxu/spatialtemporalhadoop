package com.WhaleDB.spatiotemp.processor;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.WhaleDB.spatiotemp.datamodel.result.SpatialResultCollector;
import com.WhaleDB.spatiotemp.datamodel.result.SpatialResultCollector2;
import com.WhaleDB.spatiotemp.datamodel.result.SpatialResultCollector2.KNNEntry;
import com.WhaleDB.spatiotemp.datamodel.result.SpatioObjectResultSet;

import edu.umn.cs.spatialHadoop.core.Point;
import edu.umn.cs.spatialHadoop.core.RTree;
import edu.umn.cs.spatialHadoop.core.Shape;

public class RtreeSearcher<T extends Shape> implements SpatialSearcher {

	protected RTree<T>  RtreeObject;
	FSDataInputStream fs_instream;
	FileSystem fs;
	Path rtreepath;
	String rtreepath_uri_str;
	Configuration conf;
	
	T object_reflector;
	
	SpatialResultCollector<T> result_collector;
	SpatialResultCollector2<T, KNNEntry<T,Double>> result_collector2;
	protected boolean ld_tomem = false;
	
//	RTreeRecordReader<T> rtreeloader;
	
	public RtreeSearcher(String rtree_path, T ref) throws Exception
	{
		this.rtreepath_uri_str = rtree_path;
		this.object_reflector = ref;
		
		result_collector = new SpatialResultCollector<T>();
		result_collector2 = new SpatialResultCollector2<T, KNNEntry<T,Double>>();
	}
	
	
	
	public void LoadtoMemory(boolean ld2mem) throws Exception
	{
		 ld_tomem = ld2mem;
		 LoadSpatialObjects();
	}
	
	protected FSDataInputStream getRtreeDataInputStream() throws IOException
	{
		if(fs_instream == null)
		{
			fs_instream = fs.open(rtreepath);
			/*xuxiaomin: SpatialIndex Header 8bytes skipped, see RTree.java*/
			fs_instream.seek(8);
		}
		else
		{
			/*xuxiaomin: SpatialIndex Header 8bytes skipped, see RTree.java*/
			fs_instream.seek(8);
		}
		return fs_instream;

	}
	
	@Override
	public void LoadSpatialObjects() throws Exception {
		if(conf == null)
			conf = new Configuration(); 
		if(fs == null)
			fs = FileSystem.get(URI.create(rtreepath_uri_str),conf);
		if(rtreepath == null)
			rtreepath = new Path(rtreepath_uri_str);
		
//		long length = fs.getFileStatus(rtreepath).getLen();  
	
//		if(rtreeloader == null)
//			rtreeloader = new RTreeRecordReader<T>(fs_instream, 0, length);
		if(RtreeObject == null)
			RtreeObject = new RTree<T>(object_reflector,ld_tomem);
		
		RtreeObject.setRtreeStorage(getRtreeDataInputStream(), ld_tomem);
        }
	
	@Override
	public SpatioObjectResultSet Range_Query(Shape queryshape) throws Exception {
		RtreeObject.search(queryshape, result_collector);
		return new SpatioObjectResultSet(result_collector);
	}

	@Override
	public SpatioObjectResultSet KNN_Query(Point querypoint, int k) throws Exception {
		// TODO Auto-generated method stub
		RtreeObject.knn(querypoint.getMBR().x1, querypoint.getMBR().y1, k, result_collector2);
		return new SpatioObjectResultSet(result_collector2);
	}

	@Override
	public SpatioObjectResultSet spatialJoin(Collection<Shape> collection1,
			Collection<Shape> collition2) {
		// TODO Auto-generated method stub
		return null;
	}

}
