package com.WhaleDB.spatiotemp.index;
/**
 * write index into secondary storage
 * @author xuxiaomin
 *
 */
public abstract class IndexDataWriter {

	public abstract void upsertSpatialIndex(IndexDataURI targeturi, SpatialIndex idxobj, boolean overwrite)
			throws Exception;
}
