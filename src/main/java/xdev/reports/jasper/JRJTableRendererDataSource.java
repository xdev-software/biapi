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
package xdev.reports.jasper;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import xdev.lang.NotNull;


/**
 * 
 * A data source implementation that allows to access the renderer output of a
 * {@link JTable}.
 * 
 * <p>
 * <strong>Waring:</strong> Works only for columns which are rendered by a
 * {@link TableCellRenderer} extending {@link JLabel}.
 * </p>
 * 
 * This data source does not consider a {@link TableCellRenderer} extending
 * {@link javax.swing.JCheckBox}. Use the XdevJTableRendererDataSource instead.
 * 
 * @author XDEV Software
 * 
 */
public class JRJTableRendererDataSource implements JRRewindableDataSource
{
	
	/**
	 * The {@link JTable} instance to be wrapped.
	 */
	protected final JTable	table;
	/**
	 * The current row index. Initialized to -1 as first row is retrieved by the
	 * first next() call.
	 */
	protected int			currentRowIndex	= -1;
	
	
	/**
	 * Straight forward constructor to wrap a given (non-null) {@link JTable}.
	 * 
	 * @param table
	 *            the {@link JTable} to wrap
	 */
	public JRJTableRendererDataSource(@NotNull final JTable table)
	{
		
		if(table == null)
		{
			throw new IllegalArgumentException("table must not be null");
		}
		this.table = table;
	}
	
	
	/**
	 * Retrieves the value for column <code>jrField</code> in the current row.
	 * Lookup is done by field name (column name).
	 * 
	 * @param jrField
	 *            the {@link JRField} instance defining (by name) the column of
	 *            the {@link JTable} whose value shall be retrieved.
	 * @return the value returned from the {@link TableCellRenderer} instance of
	 *         current row and column <code>jrField</code>
	 * @throws JRException
	 *             if the column (specified by <code>jrField.getName()</code>)
	 *             could not be found or the value of the
	 *             {@link TableCellRenderer} could not be accessed
	 */
	@Override
	public Object getFieldValue(JRField jrField) throws JRException
	{
		int rowIndex = this.currentRowIndex;
		int columnIndex = this.getColumnIndex(this.table,jrField.getName());
		
		return this.getRendererValue(this.table.getValueAt(rowIndex,columnIndex),rowIndex,
				columnIndex);
		
	}
	
	
	/**
	 * Moves the internal row cursor to the next row.
	 * 
	 * @return if the internal row cursor points to an existing row
	 * @throws JRException
	 *             can never happen
	 */
	@Override
	public boolean next() throws JRException
	{
		return ++this.currentRowIndex < this.table.getRowCount();
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveFirst() throws JRException
	{
		this.currentRowIndex = -1;
	}
	
	
	/**
	 * Returns the index for the given columnName.
	 * 
	 * @param table
	 *            {@link JTable}
	 * @param columnName
	 *            Name of the column to get the index for.
	 * @return the index for the given columnName.
	 */
	protected int getColumnIndex(JTable table, String columnName)
	{
		return table.getColumnModel().getColumnIndex(columnName);
	}
	
	
	/**
	 * Returns the name for the given {@link TableColumn}.
	 * 
	 * @param column
	 *            {@link TableColumn} to get the name for
	 * @return the name for the given {@link TableColumn}.
	 */
	protected String getTableColumnName(final TableColumn column)
	{
		return String.valueOf(column.getHeaderValue());
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
		
		return "";
	}
	
}
