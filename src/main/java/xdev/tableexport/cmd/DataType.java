/*
 * XDEV BI Suite - XDEV BI Suite
 * Copyright © 2011 XDEV Software (https://xdev.software)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package xdev.tableexport.cmd;
import xdev.vt.XdevBlob;
import xdev.vt.XdevClob;






public enum DataType
{
	
	/**
	 * 8-bit integer value between 0 and 255, signed or unsigned.
	 */
	TINYINT(Byte.class),
	
	/**
	 * Single precision floating point number that supports 7 digits of
	 * mantissa.
	 */
	REAL(Float.class),
	

	/**
	 * Severe fixed-precision decimal number which takes precision and scale
	 * parameters.
	 */
	NUMERIC(Double.class),
	
	/**
	 * Lax fixed-precision decimal number which takes precision and scale
	 * parameters.
	 */
	DECIMAL(Double.class),
	
	
	/**
	 * Fixed length character string.
	 */
	CHAR(String.class),
	/**
	 * Big variable length character string with length param.
	 */
	LONGVARCHAR(String.class),
	
	/**
	 * Hours, minutes and seconds.
	 */
	TIME(java.util.Date.class),
	
	/**
	 * Big variable length character string.
	 */
	CLOB(XdevClob.class),
	
	
	
	/**
	 * BLOB is not supported in table export.
	 * The data type is exported as a string.
	 */
	BLOB(XdevBlob.class),
	
	/**
	 * BINARY is not supported in table export.
	 * The data type is exported as a string.
	 */
	BINARY(byte[].class),
	
	/**
	 * VARBINARY is not supported in table export.
	 * The data type is exported as a string.
	 */
	VARBINARY(byte[].class),
	
	/**
	 * LONGVARBINARY is not supported in table export.
	 * The data type is exported as a string.
	 */
	LONGVARBINARY(byte[].class),
	
	
	
	
	/**
	 * 16-bit signed integer value between -32768 and 32767.
	 */
	SMALLINT(Short.class),
	
	/**
	 * 32-bit signed integer value between -2147483648 and 2147483647.
	 */
	INTEGER(Integer.class),
	
	/**
	 * 64-bit signed integer value between -9223372036854775808 and
	 * 9223372036854775807.
	 */
	BIGINT(Long.class),
		
	/**
	 * Double precision floating point number that supports 15 digits of
	 * mantissa.
	 */
	DOUBLE(Double.class),
	
	FLOAT(Float.class),
	
	/**
	 * 1-bit value, which is either 1 or 0, true of false.
	 */
	BOOLEAN(Boolean.class),
	
	
	/**
	 * Small variable length character string with length param.
	 */
	VARCHAR(String.class),
		
	/**
	 * Day, month and year.
	 */
	DATE(java.util.Date.class),
		
	/**
	 * Object and Others
	 */
	OBJECT(Object.class),
	
	/**
	 * {@link #DATE} + {@link #TIME} + Nanoseconds.
	 */
	TIMESTAMP(java.util.Date.class);
	
	private Class<?>	javaClass;
	
	
	/**
	 * Instantiates a new DataType.
	 * 
	 * @param javaClass
	 *            the java class
	 *  
	 */
	private DataType(Class<?> javaClass)
	{
		this.javaClass = javaClass;
	}
	
	
	/**
	 * Gets the java class for this {@link DataType}.
	 * 
	 * @return the java class
	 */
	public Class<?> getJavaClass()
	{
		return javaClass;
	}
	
	
	public static DataType get(Class<?> jClass)
	{
		
		for(DataType type : values())
		{
			if(type.getJavaClass() == jClass)
			{
				return type;
			}
		}
		return null;
	}
	
}
