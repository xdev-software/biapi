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

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import xdev.lang.Copyable;


/**
 * High level representation of the style of a column.
 * 
 * 
 * 
 * @author XDEV Software(FHAE)
 * 
 * @see #DEFAULT_COLUMN_STYLE
 * @see #DEFAULT_HEADER_STYLE
 */
public class DefaultColumnStyle implements ColumnStyle, Copyable<ColumnStyle>
{
	/**
	 * Return a specified default {@link ColumnStyle} of a content column. This
	 * object are initialized with the follow values:
	 * 
	 * <ul>
	 * <li>name: XdevDefaultColumnStyle</li>
	 * <li>background: {@link Color#WHITE}</li>
	 * <li>foreground: {@link Color#BLACK}</li>
	 * <li>font: Arial, Plain, 10</li>
	 * <li>horizontalAlignment: {@link ColumnAlignment#LEFT}</li>
	 * <li>colBorder: {@link EmptyColumnBorder}</li>
	 * </ul>
	 */
	public final static DefaultColumnStyle	DEFAULT_COLUMN_STYLE	= new DefaultColumnStyle();
	/**
	 * Return a specified default {@link ColumnStyle} of a header column. This
	 * object are initialized with the follow values:
	 * 
	 * <ul>
	 * <li>name: XdevDefaultHeaderStyle</li>
	 * <li>background: {@link Color#lightGray}</li>
	 * <li>foreground: {@link Color#BLACK}</li>
	 * <li>font: Arial, Plain, 12</li>
	 * <li>horizontalAlignment: {@link ColumnAlignment#LEFT}</li>
	 * <li>colBorder with this parameters: 1,Color.BLACK,LineStyle.SOLID (
	 * {@link ColumnBorder})</li>
	 * </ul>
	 */
	public final static DefaultColumnStyle	DEFAULT_HEADER_STYLE	= new DefaultColumnStyle(
																			getDefaultHeaderFont(),
																			Color.lightGray,
																			new ColumnBorder(1,
																					Color.BLACK,
																					LineStyle.SOLID));
	
	private Color							background				= Color.WHITE;
	private Color							foreground				= Color.BLACK;
	private Font							font					= getDefaultContentFont();
	private ColumnAlignment					horizontalAlignment		= ColumnAlignment.LEFT;
	private ColumnBorder					colBorder				= new EmptyColumnBorder();
	
	private ColumnPadding					colPadding				= new ColumnPadding(1,1,1,1);
	
	
	/**
	 * Create a {@link DefaultColumnStyle} instance with default values:
	 * 
	 * <ul>
	 * <li>background: {@link Color#WHITE}</li>
	 * <li>foreground: {@link Color#BLACK}</li>
	 * <li>font: Arial, Plain, 10</li>
	 * <li>horizontalAlignment: {@link ColumnAlignment#LEFT}</li>
	 * <li>colBorder: {@link EmptyColumnBorder}</li>
	 * </ul>
	 * 
	 */
	public DefaultColumnStyle()
	{
		
	}
	
	
	
	/**
	 * Create a {@link DefaultColumnStyle} instance with the given parameter.
	 * 
	 * @param font
	 * @param background
	 * @param colBorder
	 */
	public DefaultColumnStyle(Font font, Color background, ColumnBorder colBorder)
	{
		this.font = font;
		this.background = background;
		this.colBorder = colBorder;
	}
	
	
	/**
	 * 
	 * @param font
	 * @param background
	 * @param foreground
	 * @param alignment
	 * @param colBorder
	 */
	public DefaultColumnStyle(Font font, Color background, Color foreground,
			ColumnAlignment alignment, ColumnBorder colBorder)
	{
		this.font = font;
		this.background = background;
		this.foreground = foreground;
		this.colBorder = colBorder;
		this.horizontalAlignment = alignment;
	}
	
	
	public static Font getDefaultHeaderFont()
	{
		FontUIResource fur = (FontUIResource)UIManager.get("Label.font");
		final Font defaultFont = new Font(fur.getFontName(),Font.PLAIN,12);
		return defaultFont;
	}
	public static Font getDefaultContentFont()
	{
		FontUIResource fur = (FontUIResource)UIManager.get("Label.font");
		final Font defaultFont = new Font(fur.getFontName(),Font.PLAIN,10);
		return defaultFont;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getBackground()
	{
		return this.background;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBackground(Color background)
	{
		this.background = background;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ColumnBorder getColBorder()
	{
		return this.colBorder;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setColBorder(ColumnBorder colBorder)
	{
		this.colBorder = colBorder;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Font getFont()
	{
		return this.font;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getForeground()
	{
		return this.foreground;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setForeground(Color foreground)
	{
		this.foreground = foreground;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ColumnAlignment getHorizontalAlignment()
	{
		return this.horizontalAlignment;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHorizontalAlignment(ColumnAlignment horizontalAlignment)
	{
		this.horizontalAlignment = horizontalAlignment;
	}
	
	
	@Override
	public DefaultColumnStyle clone()
	{
		return new DefaultColumnStyle(getFont(),getBackground(),getForeground(),
				getHorizontalAlignment(),getColBorder());
	}
	
	
	@Override
	public void setColumnPadding(ColumnPadding colPadding)
	{
		this.colPadding = colPadding;
	}
	
	
	@Override
	public ColumnPadding getColumnPadding()
	{
		return this.colPadding;
	}
	
}
