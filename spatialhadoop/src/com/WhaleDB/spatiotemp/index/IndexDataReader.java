package com.WhaleDB.spatiotemp.index;
/**
 * read index data from secondary stroage
 * @author xuxiaomin
 *
 */
public abstract class IndexDataReader {

	public abstract SpatialIndex getSpatialIndexFromURI(IndexDataURI uri) throws Exception;
}
