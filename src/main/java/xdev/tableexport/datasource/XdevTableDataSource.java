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
package xdev.tableexport.datasource;

import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import xdev.lang.NotNull;
import xdev.ui.TableSupport;
import xdev.ui.VirtualTableEditor;
import xdev.ui.XdevTable;
import xdev.vt.VirtualTable;
import xdev.vt.VirtualTableColumn;
import xdev.vt.XdevClob;


/**
 * 
 * {@link XdevTable} data source implementation that allows to access
 * the data of a {@link JTable} with a {@link VirtualTableEditor}.
 * 
 * <p>
 * Specially column moving and sorting are supported.
 * </p>
 * 
 * 
 * @author XDEV Software (FHAE)
 * 
 * @param <T>
 *            type of the table
 * 
 */
public class XdevTableDataSource<T extends JTable & VirtualTableEditor> implements
		JRRewindableDataSource
{
	
	/**
	 * The current row index. Initialized to -1 as first row is retrieved by the
	 * first next() call.
	 */
	protected int				currentRowIndex	= -1;
	
	private final T				table;
	private final VirtualTable	vt;
	
	
	/**
	 * Straight forward constructor to wrap a given (non-null)
	 * {@link XdevTable}
	 * 
	 * @param table
	 *            the T to wrap
	 */
	public XdevTableDataSource(@NotNull final T table)
	{
		super();
		if(table == null)
		{
			throw new IllegalArgumentException("table must not be null");
		}
		this.table = table;
		this.vt = this.table.getVirtualTableWrapper().getVirtualTable();
	}
	
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException
	{
		try
		{
			int index = TableSupport.getTableRowConverter().viewToModel(table,currentRowIndex);
			
			final VirtualTableColumn<?> col= vt.getColumn(jrField.getName());
			switch(col.getType())
			{
				
				case BINARY:
					return "BINARY";
					
				case LONGVARBINARY:
					return "LONGVARBINARY";
					
				case VARBINARY:
					return "VARBINARY";
					
				case BLOB:
					return "BLOB";	
					
				case CLOB:
					
					final XdevClob xClob = ((XdevClob)this.vt.getValueAt(index,jrField.getName()));
					return xClob == null ? null : xClob.toString();
				default:
					return vt.getRow(index).get(vt.getColumnIndex(jrField.getName()));
			}
		}
		catch(Exception e)
		{
			throw new JRException("Unable to get value for field '" + jrField.getName(),e);
		}
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
	
}
