package xdev.tableexport.config;

/*-
 * #%L
 * XDEV BI Suite
 * %%
 * Copyright (C) 2011 - 2020 XDEV Software
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */


import java.awt.Color;


/**
 * A class which describes the properties of an column border.
 * 
 * 
 * @author XDEV Software(FHAE)
 * 
 * @see Color
 * @see LineStyle
 * @see EmptyColumnBorder
 */
public class ColumnBorder
{
	
	private int			lineWidth;
	private Color		lineColor;
	private LineStyle	lineStyle;
	
	
	/**
	 * Creates a column border with the specified lineWidth, lineColor and
	 * lineStyle.
	 * 
	 * @param lineWidth
	 *            the line width of the painted border
	 * @param lineColor
	 *            the line color of the painted border
	 * @param lineStyle
	 *            the line style of the painted border
	 * 
	 * @see Color
	 * @see LineStyle
	 */
	public ColumnBorder(int lineWidth, Color lineColor, LineStyle lineStyle)
	{
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.lineStyle = lineStyle;
	}
	
	
	/**
	 * Return the line color.
	 * 
	 * @return the {@link Color} object for the line color
	 */
	public Color getLineColor()
	{
		return this.lineColor;
	}
	
	
	/**
	 * Sets the line color.
	 * 
	 * @param lineColor
	 *            a {@link Color}
	 */
	public void setLineColor(Color lineColor)
	{
		this.lineColor = lineColor;
	}
	
	
	/**
	 * Indicates the line style used for the column.
	 * 
	 * @return a value representing one of the style constants in
	 *         {@link LineStyle}
	 */
	public LineStyle getLineStyle()
	{
		return this.lineStyle;
	}
	
	
	/**
	 * Specifies the {@link LineStyle}.
	 * 
	 * @param lineStyle
	 *            a value representing one of the style constants in
	 *            {@link LineStyle}
	 */
	public void setLineStyle(LineStyle lineStyle)
	{
		this.lineStyle = lineStyle;
	}
	
	
	/**
	 * Return the line width used for the column.
	 * 
	 * @return line width
	 */
	public int getLineWidth()
	{
		return this.lineWidth;
	}
	
	
	/**
	 * Sets the line width.
	 * 
	 * @param lineWidth
	 *            the line width
	 */
	public void setLineWidth(int lineWidth)
	{
		this.lineWidth = lineWidth;
	}
	
}
