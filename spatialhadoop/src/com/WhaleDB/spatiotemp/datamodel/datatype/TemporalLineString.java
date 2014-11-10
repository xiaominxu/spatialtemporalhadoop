package com.WhaleDB.spatiotemp.datamodel.datatype;

import java.awt.Graphics;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import edu.umn.cs.spatialHadoop.core.Point;
import edu.umn.cs.spatialHadoop.core.Rectangle;
import edu.umn.cs.spatialHadoop.core.Shape;
import edu.umn.cs.spatialHadoop.io.TextSerializerHelper;
import edu.umn.cs.spatialHadoop.mapred.SpatialRecordReader;

/**
 * 
 * @author xuxiaomin
 *
 */

public class TemporalLineString implements Shape, WritableComparable<TemporalLineString>{
	private static final Log LOG = LogFactory.getLog(TemporalLineString.class);
	/*raw data to be stored*/
	public long ts_id;
	public long base_timestamp;
	public int[] timestamp_vector;
	public double[] x_vector;
	public double[] y_vector;
	
	/*bounding box, Rectangle info*/
	public double x_min;
	public double x_max;
	public double y_min;
	public double y_max;
	public Rectangle boundingbox;
	
	public int point_cnt;
	
	public int point_capability;
	
	public void clear()
	{
		point_cnt = 0;
		base_timestamp = 0;
		x_min = Double.MAX_VALUE;
		x_max = Double.MIN_VALUE;
		y_min = Double.MAX_VALUE;
		y_max = Double.MIN_VALUE;
	}
	
	public TemporalLineString(long tsid)
	{
		ts_id = tsid;
		timestamp_vector = new int[Constraints.Default_LineString_Points];
		x_vector = new double[Constraints.Default_LineString_Points];
		y_vector = new double[Constraints.Default_LineString_Points];
		x_min = Double.MAX_VALUE;
		x_max = Double.MIN_VALUE;
		y_min = Double.MAX_VALUE;
		y_max = Double.MIN_VALUE;
		point_cnt = 0;
		base_timestamp = 0;
		point_capability = Constraints.Default_LineString_Points;
	}
	
	public TemporalLineString()
	{
		ts_id = 1L;//default tsid
		timestamp_vector = new int[Constraints.Default_LineString_Points];
		x_vector = new double[Constraints.Default_LineString_Points];
		y_vector = new double[Constraints.Default_LineString_Points];
		x_min = Double.MAX_VALUE;
		x_max = Double.MIN_VALUE;
		y_min = Double.MAX_VALUE;
		y_max = Double.MIN_VALUE;
		point_cnt = 0;
		base_timestamp = 0;
		point_capability = Constraints.Default_LineString_Points;
	}
	
	public TemporalLineString(TemporalLineString target)
	{
		this.ts_id = target.ts_id;
		this.point_cnt = target.point_cnt;
		this.base_timestamp = target.base_timestamp;
		this.x_min = target.x_min;
		this.x_max = target.x_max;
		this.y_min = target.y_min;
		this.y_max = target.y_max;
		
		this.x_vector = Arrays.copyOf(target.x_vector, point_cnt);
		this.y_vector = Arrays.copyOf(target.y_vector, point_cnt);
		this.timestamp_vector = Arrays.copyOf(target.timestamp_vector, point_cnt);
	}
	
	
	public void insertPoint(long ts, Point p) throws Exception{
		if( (point_cnt + 1) > Constraints.Max_LineString_Points)
		{
			throw new Exception("reach the high bound of lingstring's point number");
		}
		
		if(point_cnt == 0)
		{
			base_timestamp = ts;
		}
		
		if((point_cnt + 1) >= point_capability)
		{
			point_capability = point_capability * 2 < Constraints.Max_LineString_Points ? point_capability * 2 : Constraints.Max_LineString_Points;
			double[] new_xvec = new double[point_capability];
			double[] new_yvec = new double[point_capability];
			int[] new_tvec = new int[point_capability];
			
			System.arraycopy(x_vector, 0, new_xvec, 0, point_cnt);
			System.arraycopy(y_vector, 0, new_yvec, 0, point_cnt);
			System.arraycopy(timestamp_vector, 0, new_tvec, 0, point_cnt);

			this.x_vector = new_xvec;
			this.y_vector = new_yvec;
			this.timestamp_vector = new_tvec;
		}
		
		timestamp_vector[point_cnt] = (int)(ts - base_timestamp);
		x_vector[point_cnt] = p.x;
		y_vector[point_cnt] = p.y;
		updateBoundingBox(p.x, p.y);
		++point_cnt;
	}
	
	public double cptTotalDistance(DistanceCalculator distcalc)
	{
		double dist = 0.0;
		for( int i = 0; i < point_cnt -1; ++i )
		{
			dist += distcalc.cptDistance(x_vector[i], y_vector[i], x_vector[i+1], y_vector[i+1]);
		}
		return dist;
	}
	
	public double cptAverageSpeed_KmPerhour(DistanceCalculator distcalc)
	{
		if(point_cnt <= 1)
			return 0.0;
		
		int secs = timestamp_vector[point_cnt - 1] - timestamp_vector[0];
		
		double raw_speed = (cptTotalDistance(distcalc)*1000)/secs;
		
		return DistanceCalculator.cptKmPerhour(raw_speed);
	}
	
	public double cptAverageSpeed_MeterPersecond(DistanceCalculator distcalc)
	{
		if(point_cnt <= 1)
			return 0.0;
		
		int secs = timestamp_vector[point_cnt - 1] - timestamp_vector[0];
		
		double raw_speed = (cptTotalDistance(distcalc)*1000)/secs;
		
		return (raw_speed);
	}
	
	protected void updateBoundingBox(double x, double y)
	{
		this.x_min = this.x_min > x ? x: this.x_min;
		this.x_max = this.x_max > x ? this.x_max : x;
		this.y_min = this.y_min > y ? y: this.y_min;
		this.y_max = this.y_max > y ? this.y_max : y;
	}
	
	@Override
	  public TemporalLineString clone() {
	    return new TemporalLineString(this);
	  }

	@Override
	public void readFields(DataInput in) throws IOException {
		this.ts_id = in.readLong();
		this.base_timestamp = in.readLong();
		this.point_cnt = in.readInt();
	    this.x_min = in.readDouble();
	    this.y_min = in.readDouble();
	    this.x_max = in.readDouble();
	    this.y_max = in.readDouble();
		
		for(int i = 0; i < point_cnt - 1; ++i)
		{
			this.timestamp_vector[i] = in.readInt();
			this.x_vector[i] = in.readDouble();
			this.y_vector[i] = in.readDouble();
		}
		
		this.timestamp_vector[point_cnt - 1] = in.readInt();
		this.x_vector[point_cnt - 1] = in.readDouble();
		this.y_vector[point_cnt - 1] = in.readDouble();
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(this.ts_id);
		out.writeLong(this.base_timestamp);
		out.writeInt(this.point_cnt);
	    out.writeDouble(this.x_min);
	    out.writeDouble(this.y_min);
	    out.writeDouble(this.x_max);
	    out.writeDouble(this.y_max);
		for(int i = 0; i < point_cnt - 1; ++i)
		{
			out.writeInt(this.timestamp_vector[i]);
			out.writeDouble(this.x_vector[i]);
			out.writeDouble(this.y_vector[i]);
		}
		out.writeInt(this.timestamp_vector[point_cnt - 1]);
		out.writeDouble(this.x_vector[point_cnt - 1]);
		out.writeDouble(this.y_vector[point_cnt - 1]);
		
	}

	  @Override
	  public Text toText(Text text) {
		TextSerializerHelper.serializeLong(ts_id, text, ',');
		TextSerializerHelper.serializeLong(base_timestamp, text, ',');
		TextSerializerHelper.serializeInt(point_cnt, text, ',');

		TextSerializerHelper.serializeDouble(x_min, text, ',');
	    TextSerializerHelper.serializeDouble(y_min, text, ',');
	    TextSerializerHelper.serializeDouble(x_max, text, ',');
	    TextSerializerHelper.serializeDouble(y_max, text, ',');
		for(int i = 0; i < point_cnt - 1; ++i)
		{
			TextSerializerHelper.serializeInt(timestamp_vector[i], text, ',');
			TextSerializerHelper.serializeDouble(x_vector[i], text, ',');
			TextSerializerHelper.serializeDouble(y_vector[i], text, ',');
		}
		TextSerializerHelper.serializeInt(timestamp_vector[ point_cnt - 1], text, ',');
		TextSerializerHelper.serializeDouble(x_vector[ point_cnt - 1], text, ',');
		TextSerializerHelper.serializeDouble(y_vector[ point_cnt - 1], text, '\0');
	    return text;
	  }
	  
	  @Override
	  public void fromText(Text text) {
		ts_id = TextSerializerHelper.consumeLong(text, ','); 
		base_timestamp = TextSerializerHelper.consumeLong(text, ','); 
		point_cnt = TextSerializerHelper.consumeInt(text, ','); 
		
		/*allociate memory for vector via point_cnt*/
//		if(point_cnt > Constraints.Default_LineString_Points)
		{
			timestamp_vector = new int[point_cnt];
			x_vector = new double[point_cnt];
			y_vector = new double[point_cnt];
			point_capability = point_cnt;
		}
		
		x_min = TextSerializerHelper.consumeDouble(text, ',');
		y_min = TextSerializerHelper.consumeDouble(text, ',');
		x_max = TextSerializerHelper.consumeDouble(text, ',');
		y_max = TextSerializerHelper.consumeDouble(text, ',');
		for(int i = 0; i < point_cnt - 1; ++i)
		{
			timestamp_vector[i] = TextSerializerHelper.consumeInt(text, ',');
			x_vector[i] = TextSerializerHelper.consumeDouble(text, ',');
			y_vector[i] = TextSerializerHelper.consumeDouble(text, ',');
		}
		timestamp_vector[point_cnt - 1] = TextSerializerHelper.consumeInt(text, ',');
		x_vector[point_cnt - 1] = TextSerializerHelper.consumeDouble(text, ',');
		y_vector[point_cnt - 1] = TextSerializerHelper.consumeDouble(text, '\0');
	  }

	  @Override
	  public String toString() {
	    return "Rectangle: ("+x_min+","+y_min+")-("+x_max+","+y_max+")";
	  }

	@Override
	public Rectangle getMBR() {
		if(boundingbox == null)
			boundingbox = new Rectangle(x_min,y_min,x_max,y_max);
		return boundingbox;
	}

	@Override
	public double distanceTo(double x, double y) {
		return getMBR().distanceTo(x, y);
	}

	@Override
	public boolean isIntersected(Shape s) {
		return getMBR().isIntersected(s);
	}

	@Override
	public void draw(Graphics g, Rectangle fileMBR, int imageWidth,
			int imageHeight, double scale) {
	    int s_x1 = (int) Math.round((this.x_min - fileMBR.x1) * imageWidth / fileMBR.getWidth());
	    int s_y1 = (int) Math.round((this.y_min - fileMBR.y1) * imageHeight / fileMBR.getHeight());
	    int s_x2 = (int) Math.round((this.x_max - fileMBR.x1) * imageWidth / fileMBR.getWidth());
	    int s_y2 = (int) Math.round((this.y_max - fileMBR.y1) * imageHeight / fileMBR.getHeight());
	    g.fillRect(s_x1, s_y1, s_x2 - s_x1 + 1, s_y2 - s_y1 + 1);		
	}

	@Override
	public int compareTo(TemporalLineString o) {
		return boundingbox.compareTo(o.boundingbox);
	}

}
