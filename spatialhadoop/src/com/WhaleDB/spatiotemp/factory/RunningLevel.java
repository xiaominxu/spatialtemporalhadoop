package com.WhaleDB.spatiotemp.factory;

public class RunningLevel {
	public static final byte mocking = 0x1;
	public static final byte local = 0x2;
	public static final byte hdfs = 0x3;
	
	public static byte current_level;
	
	public static void setRunningLevel(byte level)
	{
		current_level = level;
	}
	
	public static byte getRunningLevel()
	{
		return current_level;
	}
}
