package com.WhaleDB.spatiotemp.index;

import com.WhaleDB.spatiotemp.gpsdata.GPSStream;

/**
 * connected to whaledb.timeseries part
 * @author xuxiaomin
 *
 */
public abstract class GPSDataManager {

	public abstract GPSStream getGPSStreamByURI(GPSURI uri) throws Exception;
}
