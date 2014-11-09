package com.WhaleDB.spatiotemp.datamodel.result;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import edu.umn.cs.spatialHadoop.core.Rectangle;
import edu.umn.cs.spatialHadoop.core.Shape;

public class HDFSFileResultSource extends ResultSource
{
	public static final String CodingMode_GBK = "GBK";
	public static final String CodingMode_Default = "GBK";
	
	  String path;  
//      String confFile;  
      Configuration conf;
      FileInputStream fis;
      FileSystem fileSystem;
      FSDataInputStream fs;
      BufferedReader bis;
//      FileWriter fos;
//      BufferedWriter bw;
      
      Shape curObjtoReturn;
      boolean isOpened;
      boolean isClosed;
      
      
    public HDFSFileResultSource(String HDFSPath) throws Exception
    {
    	this.path = HDFSPath;
//    	this.confFile = Conffile;
    	init();
    }
    
	@Override
	public boolean hasNext() {
		curObjtoReturn = null;
		String cur_text = null;
		try {
			if ((cur_text = bis.readLine()) != null) 
			{
				curObjtoReturn = new Rectangle();
				curObjtoReturn.fromText(new Text(cur_text));
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Shape next() {
		return curObjtoReturn != null ? curObjtoReturn : null;
	}
	
	@Override
	protected boolean init() throws Exception {
		  isOpened = false;
		  isClosed = false;
	      conf = new Configuration();   
//	      fis = new FileInputStream(confFile);  
//	      conf.addResource(fis);  
	      fileSystem = FileSystem.get(URI.create(path),conf);  
	      fs = fileSystem.open(new Path(path));  
	      bis = new BufferedReader(new InputStreamReader(fs,CodingMode_Default));     
//	      fos = new FileWriter(confFile);  
//	      bw = new BufferedWriter(fos);  
		return true;
	}
	
	@Override
	public void Open() throws Exception {
		isOpened = true;
	}

	@Override
	public boolean isOpen() {
		return isOpened;
	}

	@Override
	public void Close() throws Exception {
		// TODO Auto-generated method stub
		if(isOpened)
		{
//			bw.close();  
		    bis.close();  
		    fileSystem.close();
		    isClosed = true;
		}
	}

	@Override
	public boolean isClosed() throws Exception {
		// TODO Auto-generated method stub
		return isClosed;
	}

}