package com.WhaleDB.spatiotemp.gpsdata;

import org.apache.commons.lang.NotImplementedException;
import com.WhaleDB.spatiotemp.datamodel.datatype.TemporalLineString;

public class GPSLingStringStream {

	private GPSStream inter_stream;

	public GPSLingStringStream(GPSStream inner)
	{
		this.inter_stream = inner;
	}
	public TemporalLineString  getNextLineString() throws Exception
	{
		throw new NotImplementedException("not implemented now");
	}
}
