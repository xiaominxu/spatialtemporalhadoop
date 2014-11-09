/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the
 * NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package edu.umn.cs.spatialHadoop.mapred;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.RecordReader;

import com.WhaleDB.spatiotemp.datamodel.datatype.Constraints;
import com.WhaleDB.spatiotemp.datamodel.datatype.TemporalLineString;

import edu.umn.cs.spatialHadoop.OperationsParams;
import edu.umn.cs.spatialHadoop.core.Point;
import edu.umn.cs.spatialHadoop.core.Rectangle;
import edu.umn.cs.spatialHadoop.core.Shape;
import edu.umn.cs.spatialHadoop.core.SpatialSite;

/**
 * A record reader that generates random shapes in a specified area out of
 * the blue.
 * @author Ahmed Eldawy
 *
 * @param <S>
 */
public class RandomShapeGenerator<S extends Shape> implements RecordReader<Rectangle, S> {

  private static final byte[] NEW_LINE =
      System.getProperty("line.separator").getBytes();
  
  public enum DistributionType {
    UNIFORM, GAUSSIAN, CORRELATED, ANTI_CORRELATED, CIRCLE
  }
 
  /**Correlation factor for correlated, anticorrelated data*/
  private final static double rho = 0.9;

  /**The area whereshapes are generated*/
  private Rectangle mbr;

  /**Type of distribution to use for generating points*/
  private DistributionType type;

  /**Maximum edge length for generated rectangles*/
  private int rectsize;

  /**Random generator to use for generating shapes*/
  private Random random;

  /**Total size to be generated in bytes*/
  private long totalSize;

  /**The shape to use for generation*/
  private S shape;
  
  /**Size generated so far*/
  private long generatedSize;
  
  /**A temporary text used to serialize shapes to determine its size*/
  private Text text = new Text();
  
  
  /**
   * Initialize from a FileSplit
   * @param job
   * @param split
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  public RandomShapeGenerator(Configuration job,
      RandomInputFormat.GeneratedSplit split) throws IOException {
    this(split.length, OperationsParams.getShape(job, "mbr").getMBR(),
        SpatialSite.getDistributionType(job, "type", DistributionType.UNIFORM),
        job.getInt("rectsize", 100),
        split.index + job.getLong("seed", System.currentTimeMillis()));
    setShape((S) SpatialSite.createStockShape(job));
  }

  public RandomShapeGenerator(long size, Rectangle mbr,
      DistributionType type, int rectsize, long seed) {
    this.totalSize = size;
    this.mbr = mbr;
    this.type = type;
    this.rectsize = rectsize;
    this.random = new Random(seed);
    this.generatedSize = 0;
  }
  
  public void setShape(S shape) {
    this.shape = shape;
  }

  @Override
  public boolean next(Rectangle key, S value) throws IOException {
    // Generate a random shape
    generateShape(value, mbr, type, rectsize, random);
    
    // Serialize it to text first to make it easy count its size
    text.clear();
    value.toText(text);
    
    // Check if desired generated size has been reached
    if (text.getLength() + NEW_LINE.length + generatedSize > totalSize)
      return false;

    generatedSize += text.getLength() + NEW_LINE.length;

    return true;
  }

  @Override
  public Rectangle createKey() {
    Rectangle key = new Rectangle();
    key.invalidate();
    return key;
  }

  @Override
  public S createValue() {
    return shape;
  }

  @Override
  public long getPos() throws IOException {
    return generatedSize;
  }

  @Override
  public void close() throws IOException {
    // Nothing
  }

  @Override
  public float getProgress() throws IOException {
    if (totalSize == 0) {
      return 0.0f;
    } else {
      return Math.min(1.0f, generatedSize / (float) totalSize);
    }
  }
  
  private static void generateShape(Shape shape, Rectangle mbr,
      DistributionType type, int rectSize, Random random) {
    if (shape instanceof Point) {
      generatePoint((Point)shape, mbr, type, random);
    } else if (shape instanceof Rectangle) {
      ((Rectangle)shape).x1 = random.nextDouble() * (mbr.x2 - mbr.x1) + mbr.x1;
      ((Rectangle)shape).y1 = random.nextDouble() * (mbr.y2 - mbr.y1) + mbr.y1;
      ((Rectangle)shape).x2 = Math.min(mbr.x2, ((Rectangle)shape).x1 + random.nextInt(rectSize) + 2);
      ((Rectangle)shape).y2 = Math.min(mbr.y2, ((Rectangle)shape).y1 + random.nextInt(rectSize) + 2);
    } else if (shape instanceof TemporalLineString){ //xuxiaomin added
    	long startts = System.currentTimeMillis();
    	TemporalLineString obj = (TemporalLineString) shape;
    	obj.ts_id = 1;
    	try {
			generateLineString(startts,1000,obj,mbr,type,random);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    else {
      throw new RuntimeException("Cannot generate random shapes of type: "+shape.getClass());
    }
  }
  // The standard deviation is 0.2
  public static double nextGaussian(Random rand) {
    double res = 0;
    do {
      res = rand.nextGaussian() / 5.0;
    } while(res < -1 || res > 1);
    return res;
  }
  
  public static void generateLineString(long starttimetamp, long deltats, TemporalLineString linestring, Rectangle mbr, DistributionType type, Random rand) throws Exception {
	  int size = rand.nextInt(Constraints.Default_LineString_Points) + 2;
	  for( int i = 0; i < size; ++i )
	  {
		  Point p = new Point();
		  generatePoint(p,mbr,type,rand);
		  linestring.insertPoint(starttimetamp + i * deltats, p);
	  }
  }
  
  
  public static void generatePoint(Point p, Rectangle mbr, DistributionType type, Random rand) {
    double x, y;
    switch (type) {
    case UNIFORM:
      p.x = rand.nextDouble() * (mbr.x2 - mbr.x1) + mbr.x1;
      p.y = rand.nextDouble() * (mbr.y2 - mbr.y1) + mbr.y1; 
      break;
    case GAUSSIAN:
      p.x = nextGaussian(rand) * (mbr.x2 - mbr.x1) / 2.0 + (mbr.x1 + mbr.x2) / 2.0;
      p.y = nextGaussian(rand) * (mbr.y2 - mbr.y1) / 2.0 + (mbr.y1 + mbr.y2) / 2.0;
      break;
    case CORRELATED:
    case ANTI_CORRELATED:
      x = rand.nextDouble() * 2 - 1;
      do {
        y = rho * x + Math.sqrt(1 - rho * rho) * nextGaussian(rand);
      } while(y < -1 || y > 1) ;
      p.x = x * (mbr.x2 - mbr.x1) / 2.0 + (mbr.x1 + mbr.x2) / 2.0;
      p.y = y * (mbr.y2 - mbr.y1) / 2.0 + (mbr.y1 + mbr.y2) / 2.0;
      if (type == DistributionType.ANTI_CORRELATED)
        p.y = mbr.y2 - (p.y - mbr.y1);
      break;
    case CIRCLE:
      double degree = rand.nextDouble() * Math.PI * 2;
      double xradius;
      do {
        xradius = (mbr.x2 - mbr.x1) / 2  * (0.8 + rand.nextGaussian() / 30);
      } while (xradius > (mbr.x2 - mbr.x1) / 2);
      double yradius;
      do {
        yradius = (mbr.y2 - mbr.y1) / 2  * (0.8 + rand.nextGaussian() / 30);
      } while (yradius > (mbr.y2 - mbr.y1) / 2);
      double dx = Math.cos(degree) * xradius;
      double dy = Math.sin(degree) * yradius;
      p.x = (mbr.x1 + mbr.x2) / 2 + dx;
      p.y = (mbr.y1 + mbr.y2) / 2 + dy;
      break;
    default:
      throw new RuntimeException("Unrecognized distribution type: "+type);
    }
  }
  
}