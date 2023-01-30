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
package xdev.tableexport.config.builder;

import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import xdev.tableexport.config.ColumnStyle;
import xdev.tableexport.config.ContentColumn;
import xdev.tableexport.config.DefaultColumnStyle;
import xdev.tableexport.config.HeaderColumn;
import xdev.tableexport.config.TemplateColumn;
import xdev.tableexport.config.TemplateConfig;
import xdev.tableexport.utils.PatternConverter;
import xdev.tableexport.utils.VirtualTableColumnPatternConverter;
import xdev.ui.VirtualTableEditor;
import xdev.vt.VirtualTable;
import xdev.vt.VirtualTableColumn;
import xdev.vt.VirtualTableWrapper;


/**
 * The {@link XdevTableConfigBuilder} class provides a easy way to analyzes
 * {@code T} and create a {@link TemplateConfig}.
 * 
 * @author XDEV Software(FHAE)
 * 
 * @param <T>
 *            The implementor's type
 */
public class XdevTableConfigBuilder<T extends JTable & VirtualTableEditor> implements
		ExportConfigBuilder
{
	private T										table;
	private ColumnStyle								defaultContentStyle	= DefaultColumnStyle.DEFAULT_COLUMN_STYLE;
	private ColumnStyle								defaultHeaderStyle	= DefaultColumnStyle.DEFAULT_HEADER_STYLE;
	private PatternConverter<VirtualTableColumn<?>>	patternConverter	= new VirtualTableColumnPatternConverter();
	private HashMap<String, TemplateColumn>			fieldnameToColumn	= new HashMap<String, TemplateColumn>();
	
	
	/**
	 * Create a instance of the {@link XdevTableConfigBuilder}.
	 * 
	 * @param table
	 *            a object that extends {@link JTable} and
	 *            {@link VirtualTableEditor}
	 */
	public XdevTableConfigBuilder(T table)
	{
		this.table = table;
	}
	
	
	@Override
	public TemplateConfig createConfig()
	{
		final TemplateConfig config = new TemplateConfig();
		
		final VirtualTableWrapper vtWrapper = this.table.getVirtualTableWrapper();
		final VirtualTable vt = vtWrapper.getVirtualTable();
		
		config.setSheetName(vt.getName());
		
		final TableColumnModel columnModel = this.table.getColumnModel();
		for(int col = 0; col < columnModel.getColumnCount(); col++)
		{
			// Search the Model(VT) index of the column
			int modelIndex = columnModel.getColumn(col).getModelIndex();
			// Convert the column index to the vt column index
			int vtColumnIndex = vtWrapper.viewToModelColumn(modelIndex);
			final VirtualTableColumn<?> vtColumn = vt.getColumnAt(vtColumnIndex);
			final String columnIdentifier = getColumnIdentifier(vtColumn);			
			
			
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
				contentColumn.setProperty(patternConverter.getExportPattern(vtColumn));
				contentColumn.setStyle(this.defaultContentStyle.clone());
				tColumn.setContentColumn(contentColumn);
				
				tColumn.setContentColumn(contentColumn);
				tColumn.setColumnName(vtColumn.getName());
			}
			
			config.addColumn(tColumn);
		}
		
		return config;
	}
	
	
	private String getColumnIdentifier(final VirtualTableColumn<?> vtColumn)
	{
		return vtColumn.getCaption().equals("") ? vtColumn.getName() : vtColumn.getCaption();
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
