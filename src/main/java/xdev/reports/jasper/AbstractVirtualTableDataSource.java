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

import java.io.IOException;
import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import xdev.db.DBException;
import xdev.lang.NotNull;
import xdev.vt.VirtualTable;
import xdev.vt.XdevBlob;


/**
 * This class provides a skeletal implementation of the
 * {@link JRRewindableDataSource} interface backed by a {@link VirtualTable}.
 * <p>
 * The developer only needs to subclass this abstract class and define the
 * <code>getFieldValue</code> method.
 * </p>
 * 
 * @author XDEV Software
 * 
 */
public abstract class AbstractVirtualTableDataSource implements JRRewindableDataSource
{
	
	/**
	 * The VirtualTable instance to be wrapped.
	 */
	protected final VirtualTable	virtualTable;
	/**
	 * The current row index. Initialized to -1 as first row is retrieved by the
	 * first next() call.
	 */
	protected int					currentRowIndex	= -1;
	

	/**
	 * Straight forward constructor to wrap a given (non-null)
	 * {@link VirtualTable}.
	 * 
	 * @param virtualTable
	 *            the {@link VirtualTable} to wrap
	 */
	public AbstractVirtualTableDataSource(@NotNull final VirtualTable virtualTable)
	{
		super();
		if(virtualTable == null)
		{
			throw new IllegalArgumentException("virtualTable must not be null");
		}
		this.virtualTable = virtualTable;
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
		return ++this.currentRowIndex < this.virtualTable.getRowCount();
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
	 * Retrieves a binary value for column <code>jrField</code> in the current
	 * row. Lookup is done by field name (column name).
	 * 
	 * @param jrField
	 *            the {@link JRField} instance defining (by name) the column of
	 *            the {@link VirtualTable} whose value shall be retrieved.
	 * @return the binary value in the wrapped {@link VirtualTable} instance
	 *         from current row, column <code>jrField</code>
	 * 
	 * @throws DBException
	 *             if the column (specified by <code>jrField.getName()</code>)
	 *             could not be found or accessed
	 * @throws IOException
	 *             if the column (specified by <code>jrField.getName()</code>)
	 *             could not be found or accessed
	 */
	protected Object getBinaryFieldValue(@NotNull final JRField jrField) throws DBException,
			IOException
	{
		XdevBlob blob = (XdevBlob)this.virtualTable.getValueAt(this.currentRowIndex,
				jrField.getName());
		InputStream is;
		if(blob != null)
		{
			is = blob.getBinaryStream();
		}
		else
		{
			is = null;
		}
		
		return is;
	}
	
}
