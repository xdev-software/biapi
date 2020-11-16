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


import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import xdev.lang.NotNull;


public class JRDataSourceMapper implements JRDataSource
{
	
	private final JRDataSource			wrappedDataSource;
	private final Map<String, String>	fieldMapping;
	

	public JRDataSourceMapper(final JRDataSource dataSource, final Map<String, String> fieldMapping)
	{
		this.wrappedDataSource = dataSource;
		this.fieldMapping = fieldMapping;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean next() throws JRException
	{
		return this.wrappedDataSource.next();
	}
	

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public Object getFieldValue(@NotNull final JRField jrField) throws JRException
	{		
		String mappedName = this.fieldMapping.get(jrField.getName());
		
		JRField field = jrField;
		if(mappedName != null)
		{
			field = new JRMappedField(jrField,mappedName);
		}
		
		return this.wrappedDataSource.getFieldValue(field);
	}
	
}
