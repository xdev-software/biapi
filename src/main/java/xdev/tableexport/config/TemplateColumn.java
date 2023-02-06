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
 * The class encapsulates the two column object's {@link HeaderColumn} and
 * {@link ContentColumn}. <br>
 * <p>
 * The class has general information like as <code>width</code>,
 * <code>name of the column</code>.
 * </p>
 * 
 * @author XDEV Software(FHAE)
 * 
 */
public class TemplateColumn
{
	private int				width;
	private boolean			hasHeaderColumn	= true;
	private HeaderColumn	headerColumn	= null;
	private ContentColumn	contentColumn	= null;
	private String			columnName		= null;
	private boolean			visible			= true;
	
	
	/**
	 * Create a instance of this {@link TemplateColumn}. The {@link #columnName}
	 * default value is set to <code>NewColumn</code>
	 */
	public TemplateColumn()
	{
		this("NewColumn");
	}
	
	/**
	 * Create a instance of this {@link TemplateColumn}. 
	 * 
	 * @param columnName
	 */
	public TemplateColumn(final String columnName)
	{
		this.columnName = columnName;
		this.headerColumn = new HeaderColumn();
		this.contentColumn = new ContentColumn();
	}
	
	
	/**
	 * 
	 * @param contentColumn
	 */
	public void setContentColumn(ContentColumn contentColumn)
	{
		this.contentColumn = contentColumn;
	}
	
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	public void setHasHeaderColumn(boolean hasHeaderColumn)
	{
		this.hasHeaderColumn = hasHeaderColumn;
	}
	
	
	public void setHeaderColumn(HeaderColumn headerColumn)
	{
		this.headerColumn = headerColumn;
	}
	
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	
	
	public int getWidth()
	{
		return this.width;
	}
	
	
	public HeaderColumn getHeaderColumn()
	{
		return this.headerColumn;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public ContentColumn getContentColumn()
	{
		return this.contentColumn;
	}
	
	
	public boolean hasHeaderColumn()
	{
		return this.hasHeaderColumn;
	}
	
	
	public String getColumnName()
	{
		return columnName;
	}
	
}
