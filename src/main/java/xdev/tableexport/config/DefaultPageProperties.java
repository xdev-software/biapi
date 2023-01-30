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

public class DefaultPageProperties implements PageProperties
{
	/**
	 * Default height of the page.
	 */
	public static int	DEFAULT_PAGE_HEIGHT		= 842;
	/**
	 * Default value of column spacing.
	 */
	public static int	DEFAULT_SPACING			= 0;
	/**
	 * Default value of left margin.
	 */
	public static int	DEFAULT_LEFT_MARGIN		= 40;
	/**
	 * Default value of right margin.
	 */
	public static int	DEFAULT_RIGHT_MARGIN	= 40;
	/**
	 * Default value of top margin.
	 */
	public static int	DEFAULT_TOP_MARGIN		= 50;
	/**
	 * Default value of bottom margin.
	 */
	public static int	DEFAULT_BOTTOM_MARGIN	= 50;
	
	private int			columnSpacing;
	private int			leftMargin;
	private int			rightMargin;
	private int			topMargin;
	private int			bottomMargin;
	private int			pageHeight;
	

	public DefaultPageProperties()
	{
		this.columnSpacing = DEFAULT_SPACING;
		this.leftMargin = DEFAULT_LEFT_MARGIN;
		this.rightMargin = DEFAULT_RIGHT_MARGIN;
		this.topMargin = DEFAULT_TOP_MARGIN;
		this.bottomMargin = DEFAULT_BOTTOM_MARGIN;
		this.pageHeight = DEFAULT_PAGE_HEIGHT;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBottomMargin()
	{
		return this.bottomMargin;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnSpacing()
	{
		return this.columnSpacing;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLeftMargin()
	{
		return this.leftMargin;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPageHeight()
	{
		return this.pageHeight;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRightMargin()
	{
		return this.rightMargin;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTopMargin()
	{
		return this.topMargin;
	}
	
}
