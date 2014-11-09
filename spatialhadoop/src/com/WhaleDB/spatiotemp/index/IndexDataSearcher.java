package com.WhaleDB.spatiotemp.index;
/**
 * read index data from secondary stroage
 * @author xuxiaomin
 *
 */
public abstract class IndexDataSearcher {

	public abstract SpatialIndex getSpatialIndexFromURI(IndexDataURI uri) throws Exception;
}
