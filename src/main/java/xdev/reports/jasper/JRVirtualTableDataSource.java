package xdev.reports.jasper;

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


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import xdev.lang.NotNull;
import xdev.vt.VirtualTable;
import xdev.vt.VirtualTableColumn;
import xdev.vt.XdevClob;


/**
 * 
 * {@link VirtualTable} data source implementation that allows to access the
 * data of a {@link VirtualTable}.
 * 
 * <p>
 * {@link JRVirtualTableDataSource} ignores the format settings of the
 * underlining {@link VirtualTable} and leaves the formating up to the report.
 * </p>
 * 
 * <p>
 * If you want to use the format settings of the {@link VirtualTable} in your
 * report see {@link JRVirtualTableFormattedDataSource}.
 * </p>
 * 
 * @see JRVirtualTableFormattedDataSource
 * 
 * @author XDEV Software
 * 
 */
public class JRVirtualTableDataSource extends AbstractVirtualTableDataSource
{
	
	/**
	 * Straight forward constructor to wrap a given (non-null)
	 * {@link VirtualTable}.
	 * 
	 * @param virtualTable
	 *            the {@link VirtualTable} to wrap
	 */
	public JRVirtualTableDataSource(@NotNull final VirtualTable virtualTable)
	{
		super(virtualTable);
	}
	

	/**
	 * Retrieves the value for column <code>jrField</code> in the current row.
	 * Lookup is done by field name (column name).
	 * 
	 * @param jrField
	 *            the {@link JRField} instance defining (by name) the column of
	 *            the {@link VirtualTable} whose value shall be retrieved.
	 * @return the value in the wrapped {@link VirtualTable} instance from
	 *         current row, column <code>jrField</code>
	 * @throws JRException
	 *             if the column (specified by <code>jrField.getName()</code>)
	 *             could not be found or accessed
	 */
	@Override
	public Object getFieldValue(@NotNull final JRField jrField) throws JRException
	{
		try
		{
			VirtualTableColumn<?> column = this.virtualTable.getColumn(jrField.getName());
			
			switch(column.getType())
			{
				case BINARY:
					return this.getBinaryFieldValue(jrField);
					
				case LONGVARBINARY:
					return this.getBinaryFieldValue(jrField);
					
				case VARBINARY:
					return this.getBinaryFieldValue(jrField);
					
				case BLOB:
					return this.getBinaryFieldValue(jrField);
					
				case CLOB:
					return ((XdevClob)this.virtualTable.getValueAt(this.currentRowIndex,
							jrField.getName())).toJDBCClob();
					
				default:
					return this.virtualTable.getValueAt(this.currentRowIndex,jrField.getName());
			}
			
		}
		catch(Exception e)
		{
			throw new JRException("Unable to get value for field '" + jrField.getName(),e);
		}
	}
	
}
