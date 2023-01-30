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
package xdev.tableexport.config;



/**
 * A class which describes the properties of the column padding.
 * 
 * 
 * @author XDEV Software(FHAE)
 * 
 */
public class ColumnPadding
{
	
	protected int leftWidth, rightWidth, topWidth, bottomWidth;
	
		
	/**
	 * Creates a column padding object with the specified leftWidth, rightWidth, topWidth and
	 * bottomWidth.
	 * 
	 * @param leftWidth
	 * @param rightWidth
	 * @param topWidth
	 * @param bottomWidth
	 * 
	 */
	public ColumnPadding(int leftWidth, int rightWidth,int topWidth,int bottomWidth)
	{
		this.leftWidth = leftWidth;
		this.rightWidth = rightWidth;
		this.topWidth = topWidth;
		this.bottomWidth = bottomWidth;
	}
	
	public int getBottomWidth()
	{
		return bottomWidth;
	}
	
	public int getLeftWidth()
	{
		return leftWidth;
	}
	
	public int getRightWidth()
	{
		return rightWidth;
	}
	
	public int getTopWidth()
	{
		return topWidth;
	}

}
