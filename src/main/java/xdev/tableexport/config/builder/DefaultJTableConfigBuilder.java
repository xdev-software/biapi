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

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import xdev.tableexport.config.ColumnStyle;
import xdev.tableexport.config.ContentColumn;
import xdev.tableexport.config.DefaultColumnStyle;
import xdev.tableexport.config.HeaderColumn;
import xdev.tableexport.config.TemplateColumn;
import xdev.tableexport.config.TemplateConfig;
import xdev.tableexport.utils.NullPatternConverter;


/**
 * The {@link DefaultJTableConfigBuilder} class provides a easy way to analyzes
 * {@code T} and create a {@link TemplateConfig}.
 * 
 * @author XDEV Software(FHAE)
 * 
 * @param <T>
 *            The implementor's type
 */
public class DefaultJTableConfigBuilder<T extends JTable> implements ExportConfigBuilder
{
	private T								table;
	
	private ColumnStyle						defaultContentStyle	= DefaultColumnStyle.DEFAULT_COLUMN_STYLE;
	private ColumnStyle						defaultHeaderStyle	= DefaultColumnStyle.DEFAULT_HEADER_STYLE;
	private NullPatternConverter			patternConverter	= new NullPatternConverter();
	private HashMap<String, TemplateColumn>	fieldnameToColumn	= new HashMap<>();
	
	
	/**
	 * Create a instance of the {@link DefaultJTableConfigBuilder}.
	 * 
	 * @param table
	 *            a object extends {@link JTable}
	 */
	public DefaultJTableConfigBuilder(T table)
	{
		this.table = table;
	}
	
	
	/**
	 * Create a {@link TemplateColumn} from a {@link JTable}.<br>
	 * Loop through the {@link TableColumnModel} columns and build for each
	 * column a {@link HeaderColumn} and {@link ContentColumn}.
	 * 
	 * 
	 * @return the generated {@link TemplateConfig}
	 */
	@Override
	public TemplateConfig createConfig()
	{
		final TemplateConfig config = new TemplateConfig();
		
		config.setSheetName("JTable-Export");
		
		final TableColumnModel columnModel = this.table.getColumnModel();
		for(int col = 0; col < columnModel.getColumnCount(); col++)
		{
			
			final TableColumn tableHeaderColumn = table.getTableHeader().getColumnModel()
					.getColumn(col);
			// Header
			final String columnIdentifier = tableHeaderColumn.getIdentifier().toString();	
			
			TemplateColumn tColumn = null;
			
			if(this.fieldnameToColumn.get(columnIdentifier) != null)
			{
				tColumn = this.fieldnameToColumn.get(columnIdentifier);
			}
			else
			{
				//create new template column and prepare the column with the default values
				tColumn = new TemplateColumn();
				
				tColumn.setWidth(tableHeaderColumn.getWidth());
				tColumn.setHasHeaderColumn(true);
				
				//build header column
				final HeaderColumn headerColumn = new HeaderColumn();
				headerColumn.setStyle(this.defaultHeaderStyle.clone());
				headerColumn.setProperty(columnIdentifier);
				tColumn.setHeaderColumn(headerColumn);
				
				//build content column
				final ContentColumn contentColumn = new ContentColumn();
				contentColumn.setFieldName(columnIdentifier);
				contentColumn.setStyle(this.defaultContentStyle.clone());
				// Search the Model index of the column
				int modelIndex = columnModel.getColumn(col).getModelIndex();
				handleColumnValueClass(contentColumn,table.getModel().getColumnClass(modelIndex));
				contentColumn.setProperty(patternConverter.getExportPattern(null));
				tColumn.setContentColumn(contentColumn);
				
				tColumn.setColumnName(columnIdentifier);
			}
			
			config.addColumn(tColumn);
		}
		
		return config;
	}
	
	
	public void handleColumnValueClass(ContentColumn column, Class<?> columnValueClass)
	{
		column.setColumnValueClass(columnValueClass);
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
