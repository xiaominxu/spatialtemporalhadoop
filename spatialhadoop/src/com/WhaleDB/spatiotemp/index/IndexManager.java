package com.WhaleDB.spatiotemp.index;


/**
 * 
 * @author xuxiaomin
 *
 */
/*Shift+Alt+S 会弹出一个对话框 选择Generate Getters and Setters ...*/
public class IndexManager {
//	public HashMap< IndexDataURI, SpatialIndex > index_cache;
	PrimaryIndexExecuter primary_index;
	SecondaryIndexExecuter secondary_index;
	
	
	public PrimaryIndexExecuter getPrimary_index() {
		return primary_index;
	}
	public void setPrimary_index(PrimaryIndexExecuter primary_index) {
		this.primary_index = primary_index;
	}
	public SecondaryIndexExecuter getSecondary_index() {
		return secondary_index;
	}
	public void setSecondary_index(SecondaryIndexExecuter secondary_index) {
		this.secondary_index = secondary_index;
	}
	
}
