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
import java.awt.Font;

import xdev.lang.Copyable;


/**
 * A interface which describes the style properties of an column.
 * 
 * @see Color
 * @see ColumnAlignment
 * @see ColumnBorder
 * @see Font
 * 
 * @author XDEV Software(FHAE)
 */
public interface ColumnStyle extends Copyable<ColumnStyle>
{
	/**
	 * Return the background color for this {@link ColumnStyle}.
	 * 
	 * @return the {@link Color} of the background
	 * 
	 * @see #setBackground(Color)
	 */
	public abstract Color getBackground();
	
	
	/**
	 * Return the foreground color for this {@link ColumnStyle}.
	 * 
	 * @return the {@link Color} of the foreground
	 * 
	 * @see #setForeground(Color)
	 */
	public abstract Color getForeground();
	
	
	/**
	 * Sets the background color for this {@link ColumnStyle}.
	 * 
	 * @param background
	 *            the {@link Color} of the background
	 * 
	 * @see #getBackground()
	 */
	public abstract void setBackground(Color background);
	
	
	/**
	 * Sets the foreground color for this {@link ColumnStyle}.
	 * 
	 * @param foreground
	 *            the {@link Color} of the foreground
	 * 
	 * @see #getForeground()
	 */
	public abstract void setForeground(Color foreground);
	
	
	/**
	 * Return the border of this {@link ColumnStyle}.
	 * 
	 * @return the {@link ColumnBorder} object
	 * 
	 * @see #setColBorder(ColumnBorder)
	 */
	public abstract ColumnBorder getColBorder();
	
	
	/**
	 * Sets the border of this {@link ColumnStyle}.
	 * 
	 * @param colBorder
	 *            a {@link ColumnBorder} object
	 * 
	 * @see #getColBorder()
	 */
	public abstract void setColBorder(ColumnBorder colBorder);
	
	
	/**
	 * Return the font of this {@link ColumnStyle}.
	 * 
	 * @return the {@link Font} object
	 * 
	 * @see #setFont(Font)
	 */
	public abstract Font getFont();
	
	
	/**
	 * Sets the font of this {@link ColumnStyle}.
	 * 
	 * @param font
	 *            a {@link Font} object
	 * 
	 * @see #getFont()
	 */
	public abstract void setFont(Font font);
	
	
	/**
	 * Return the alignment of the column content along the X axis.
	 * 
	 * @return the {@link ColumnAlignment} of this {@link ColumnStyle}
	 */
	public abstract ColumnAlignment getHorizontalAlignment();
	
	
	/**
	 * Sets the alignment of the column contents along the X axis.
	 * 
	 * @param horizontalAlignment
	 *            One of the following constants defined in
	 *            {@link ColumnAlignment}:<br>
	 *            <ul>
	 *            <li>LEFT</li>
	 *            <li>CENTER (the default)</li>
	 *            <li>RIGHT</li>
	 *            </ul>
	 */
	public abstract void setHorizontalAlignment(ColumnAlignment horizontalAlignment);
	
	
	public abstract void setColumnPadding(ColumnPadding colPadding);
	
	
	public abstract ColumnPadding getColumnPadding();
	
}
