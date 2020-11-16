package xdev.tableexport.datasource;

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


import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import xdev.lang.NotNull;
import xdev.reports.jasper.JRJTableRendererDataSource;


/**
 * 
 * A data source implementation that allows to access the renderer output of a
 * {@link JTable}.
 * 
 * <p>
 * <strong>Waring:</strong> Works only for columns which are rendered by a
 * {@link TableCellRenderer} extending {@link JLabel} or {@link JCheckBox}.
 * If the {@link TableCellRenderer} extending a {@link JCheckBox} the {@link ValueConverter} is used to determine the {@link String} value. 
 * </p>
 * 
 * @author XDEV Software(FHAE)
 * 
 * @see ValueConverter
 * @see DefaultBooleanToStringConverter
 * 
 */
public class XdevJTableRendererDataSource extends JRJTableRendererDataSource
{
	private ValueConverter<Boolean,String> booleanToStringConverter = new DefaultBooleanToStringConverter();
	
	
	/**
	 * Straight forward constructor to wrap a given (non-null) {@link JTable}.
	 * 
	 * @param table
	 *            the {@link JTable} to wrap
	 */
	public XdevJTableRendererDataSource(@NotNull final JTable table)
	{
		super(table);
	}
	
	
	public void setBooleanToStringConverter(ValueConverter<Boolean,String> booleanToStringConverter)
	{
		this.booleanToStringConverter = booleanToStringConverter;
	}

	/**
	 * Returns the {@link TableCellRenderer} value for the described item.
	 * 
	 * @param value
	 *            value to be processed by the renderer
	 * @param row
	 *            rowIndex of the {@link TableCellRenderer} to use
	 * @param col
	 *            columnIndex of the {@link TableCellRenderer} to use
	 * @return the {@link TableCellRenderer} value for the described item.
	 */
	protected String getRendererValue(final Object value, final int row, final int col)
	{
		Component cpn = this.table.getCellRenderer(row,col).getTableCellRendererComponent(
				this.table,value,false,false,row,col);
		
		if(cpn instanceof JLabel)
		{
			return ((JLabel)cpn).getText();
		}
		else if(cpn instanceof JCheckBox)
		{
			return booleanToStringConverter.getValue(((JCheckBox)cpn).isSelected());
		}
		else
		{
			return "";
		}
	}
	
}
