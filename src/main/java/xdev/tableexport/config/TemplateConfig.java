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

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JasperReport;


/**
 * A {@link TemplateConfig} is a pre-configuration object of a
 * {@link JasperReport}.
 * 
 * 
 * @author XDEV Software(FHAE)
 */
public class TemplateConfig
{
	/**
	 * Default height of the header label.
	 */
	public static int				DEFAULT_HEADER_LABEL_HEIGHT		= 25;
	/**
	 * Default height of the header band.
	 */
	public static int				DEFAULT_HEADER_BAND_HEIGHT		= 30;
	/**
	 * Default height of the detail textfield.
	 */
	public static int				DEFAULT_DETAIL_TEXTFIELD_HEIGHT	= 19;
	/**
	 * Default height of the detail band.
	 */
	public static int				DEFAULT_DETAIL_BAND_HEIGHT		= 20;
	
	/**
	 * Default y-position of component's in the bands.
	 */
	public static int				DEFAULT_COMPONENT_Y_POSITION	= 0;
	
	/**
	 * Default sheet name.
	 */
	private static String			DEFAULT_SHEET_NAME				= "Sheet1";
	
	private String					sheetName						= DEFAULT_SHEET_NAME;
	private PageProperties			pagePropertiesObj				= new DefaultPageProperties();
	private List<TemplateColumn>	allColumns						= new ArrayList<>();
	
	private boolean					blankWhenNullValue				= false;
	
	
	public TemplateConfig()
	{
		
	}
	
	/**
	 * Add a {@link TemplateColumn} to the current list.
	 * 
	 * @param col
	 * 			the {@link TemplateColumn} to be added in the list
	 */
	public void addColumn(TemplateColumn col)
	{
		this.allColumns.add(col);
	}
	
	/**
	 * Return a list of all {@link TemplateColumn} of this {@link TemplateConfig}.
	 * 
	 * @return 
	 * 			a {@link List} with all {@link TemplateColumn}
	 */
	public List<TemplateColumn> getColumns()
	{
		return this.allColumns;
	}
	
	
	/**
	 * Returns the {@link ContentColumn} with the specified fieldName.
	 * 
	 * @param fieldName
	 *            as a {@link String}
	 * 
	 * @return the {@link ContentColumn} for the specified fieldName, or null if
	 *         no column is found.
	 */
	public ContentColumn getContentColumnByFieldName(final String fieldName)
	{
		for(TemplateColumn col : getColumns())
		{
			final ContentColumn contentCol = col.getContentColumn();
			if(contentCol == null)
			{
				continue;
			}
			else if(contentCol.getFieldName().equals(fieldName))
			{
				return contentCol;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Returns the {@link HeaderColumn} with the specified headerText.
	 * 
	 * @param headerText
	 *            as a {@link String}
	 * 
	 * @return the {@link HeaderColumn} for the specified headerText, or null if
	 *         no column is found.
	 * 
	 * @see HeaderColumn#getProperty()
	 */
	public HeaderColumn getHeaderColumnByFieldName(final String headerText)
	{
		for(TemplateColumn col : getColumns())
		{
			final HeaderColumn headerCol = col.getHeaderColumn();
			if(headerCol == null)
			{
				continue;
			}
			else if(headerCol.getProperty().equals(headerText))
			{
				return headerCol;
			}
		}
		
		return null;
	}
	
	public TemplateColumn getTemplateColumn(final String columnName)
	{
		for(TemplateColumn col : getColumns())
		{
			if(col.getColumnName().equals(columnName))
			{
				return col;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Check the template if any header column is set.
	 * 
	 * @return {@code true} if one or more header columns are define, otherwise
	 *         {@code false}
	 */
	public boolean hasAnyHeader()
	{
		for(TemplateColumn col : getColumns())
		{
			if(col.hasHeaderColumn())
			{
				return true;
			}
		}
		return false;
	}
	
	
	// ////////////////////////////////////////////
	// /////SETTER
	// ////////////////////////////////////////////
	
	/**
	 * Set the excel sheet name for this {@link TemplateConfig}.
	 * <p>
	 * Note - Excel allows sheet names up to 31 chars in length but other
	 * applications allow more. Excel does not crash with names longer than 31
	 * chars, but silently truncates such names to 31 chars.
	 * </p>
	 * 
	 * @param name
	 *            the excel sheet name
	 */
	public void setSheetName(String name)
	{
		this.sheetName = name;
	}
	
	
	public void setBlankWhenNullValue(boolean blankWhenNullValue)
	{
		this.blankWhenNullValue = blankWhenNullValue;
	}
	

	// ////////////////////////////////////////////
	// /////GETTER
	// ////////////////////////////////////////////
	
	
		
	public PageProperties getPageProperties()
	{
		return this.pagePropertiesObj;
	}
	
	
	public String getSheetName()
	{
		return this.sheetName;
	}
	
	
	public boolean isBlankWhenNullValue()
	{
		return this.blankWhenNullValue;
	}
	
}
