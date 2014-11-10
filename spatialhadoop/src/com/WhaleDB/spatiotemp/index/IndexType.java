package com.WhaleDB.spatiotemp.index;

import java.util.HashMap;

public class IndexType {

	public static final byte R_Tree_Idx = 0x01;
	public static final String R_Tree_Idx_String = "rtree";
	
	public static HashMap<Byte, String> index_map = new HashMap<Byte, String>();
	
	static{
		index_map.put(R_Tree_Idx, R_Tree_Idx_String);
	}
	
	public static String getIndexString(byte mode)
	{
		return index_map.get(mode);
	}
}
