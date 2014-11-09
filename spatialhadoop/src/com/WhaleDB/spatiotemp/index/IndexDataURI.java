package com.WhaleDB.spatiotemp.index;

import org.apache.commons.lang.NotImplementedException;

public class IndexDataURI {
	public static class URIPathFormatter{
		public static String container_format_single = "%ld-%ld-%d";
		public static String container_format_multiple = "%ld-%d";
		public static String format_index = "%s";
		public static String format_targetdata = "linestrings";
		
		public static void URIPathFormat(IndexDataURI target)
		{
			switch(target.Idxmode)
			{
			case IndexMode.Mode_Multi:{
				target.setContainer_path(String.format(container_format_multiple, target.start_timestamp, target.timescale));
				target.setIndex_path(String.format(format_index, IndexType.getIndexString(target.Idxmode)));
				target.setRaw_data_path(String.format(format_targetdata));
				break;
			}
			case IndexMode.Mode_Single:{
				target.setContainer_path(String.format(container_format_single, target.tsid, target.start_timestamp, target.timescale));
				target.setIndex_path(String.format(format_index, IndexType.getIndexString(target.Idxmode)));
				target.setRaw_data_path(String.format(format_targetdata));
				break;
			}
			default:
			{
				throw new NotImplementedException("not implemented");
			}
			}
		}
	}
	
	public long tsid;//not used when indexmode = multi
	public long start_timestamp;
	public byte timescale;
	public byte Idxmode;
	
	public IndexDataURI(long tsid, long start_timestamp, byte timescale,
			byte indexMode) {
		this.tsid = tsid;
		this.start_timestamp = start_timestamp;
		this.timescale = timescale;
		this.Idxmode = indexMode;
		URIPathFormatter.URIPathFormat(this);
	}
	
	public String container_path;

	public String index_path;
	
	public String raw_data_path;

	public byte getIndexMode() {
		return Idxmode;
	}

	public void setIndexMode(byte indexMode) {
		Idxmode = indexMode;
	}

	
	public String getContainer_path() {
		return container_path;
	}

	public void setContainer_path(String container_path) {
		this.container_path = container_path;
	}

	public String getIndex_path() {
		return index_path;
	}

	public void setIndex_path(String index_path) {
		this.index_path = index_path;
	}

	public String getRaw_data_path() {
		return raw_data_path;
	}

	public void setRaw_data_path(String raw_data_path) {
		this.raw_data_path = raw_data_path;
	}
	
}
