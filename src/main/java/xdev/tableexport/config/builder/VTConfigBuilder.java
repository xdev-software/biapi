package xdev.tableexport.config.builder;

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


import java.util.HashMap;

import javax.swing.SwingConstants;

import xdev.tableexport.config.ColumnAlignment;
import xdev.tableexport.config.ColumnStyle;
import xdev.tableexport.config.ContentColumn;
import xdev.tableexport.config.DefaultColumnStyle;
import xdev.tableexport.config.HeaderColumn;
import xdev.tableexport.config.TemplateColumn;
import xdev.tableexport.config.TemplateConfig;
import xdev.tableexport.utils.PatternConverter;
import xdev.tableexport.utils.VirtualTableColumnPatternConverter;
import xdev.vt.VirtualTable;
import xdev.vt.VirtualTableColumn;


public class VTConfigBuilder implements ExportConfigBuilder
{
	
	private VirtualTable							vt;
	private ColumnStyle								defaultContentStyle	= DefaultColumnStyle.DEFAULT_COLUMN_STYLE;
	private ColumnStyle								defaultHeaderStyle	= DefaultColumnStyle.DEFAULT_HEADER_STYLE;
	private PatternConverter<VirtualTableColumn<?>>	patternConverter	= new VirtualTableColumnPatternConverter();
	private HashMap<String, TemplateColumn>			fieldnameToColumn	= new HashMap<String, TemplateColumn>();
	
	
	public VTConfigBuilder(VirtualTable vt)
	{
		this.vt = vt;
	}
	
	
	@Override
	public TemplateConfig createConfig()
	{
		final TemplateConfig config = new TemplateConfig();
		
		config.setSheetName(this.vt.getName());
		
		for(VirtualTableColumn<?> vtColumn : this.vt.columns())
		{
			if(!vtColumn.isVisible())
				continue;
			
			final String columnIdentifier = vtColumn.getName();
			
			// Create templatecolumn
			TemplateColumn tColumn = null;
			if(this.fieldnameToColumn.get(columnIdentifier) != null)
			{
				tColumn = this.fieldnameToColumn.get(columnIdentifier);
			}
			else
			{
				
				// create new template column and prepare the column with the
				// default values
				tColumn = new TemplateColumn();
				
				tColumn.setWidth(vtColumn.getPreferredWidth());
				tColumn.setHasHeaderColumn(true);
				
				// build header column
				tColumn.setHasHeaderColumn(true);
				final HeaderColumn headerColumn = new HeaderColumn();
				headerColumn.setStyle(this.defaultHeaderStyle.clone());
				headerColumn.setProperty(columnIdentifier);
				tColumn.setHeaderColumn(headerColumn);
				
				// build content column
				final ContentColumn contentColumn = new ContentColumn();
				contentColumn.setFieldName(vtColumn.getName());
				contentColumn.setColumnValueClass(vtColumn.getType().getJavaClass());
				contentColumn.setStyle(this.defaultContentStyle.clone());
				contentColumn.setProperty(patternConverter.getExportPattern(vtColumn));
				contentColumn.getStyle().setHorizontalAlignment(getColumnAlignmentFromVtAlignment(
						this.vt.getColumn(columnIdentifier).getHorizontalAlignment()));
				
				tColumn.setContentColumn(contentColumn);
				tColumn.setColumnName(vtColumn.getName());
			}
			
			config.addColumn(tColumn);
		}
		
		return config;
	}
	
	
	/**
	 * Changes the VT-HorizontalAlignment to a
	 * ContentColumn-HorizontalAlignment.
	 * 
	 * @param vtAlignment
	 *            {@link SwingConstants} with the alignment
	 * @return {@link ColumnAlignment} suitable for the VT-HorizontalAlignment,
	 *         of {@link ColumnAlignment}.LEFT if it isn't (VT-HorizontalAlignment)RIGHT,LEFT,CENTER.
	 */
	private ColumnAlignment getColumnAlignmentFromVtAlignment(int vtAlignment)
	{
		switch(vtAlignment)
		{
			case SwingConstants.CENTER:
				return ColumnAlignment.CENTER;
			
			case SwingConstants.LEFT:
				return ColumnAlignment.LEFT;
			
			case SwingConstants.RIGHT:
				return ColumnAlignment.RIGHT;
			
			default:
				return ColumnAlignment.LEFT;
			
		}
		
	}
	
	
	@Override
	public void setPredefinedColumnMap(HashMap<String, TemplateColumn> fieldnameToColumn)
	{
		this.fieldnameToColumn = fieldnameToColumn;
	}
	
	
	@Override
	public void setDefaultContentColumnStyle(ColumnStyle style)
	{
		this.defaultContentStyle = style;
	}
	
	
	@Override
	public void setDefaultHeaderColumnStyle(ColumnStyle style)
	{
		this.defaultHeaderStyle = style;
	}
	
}
