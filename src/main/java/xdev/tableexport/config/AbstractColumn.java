package xdev.tableexport.config;

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


public abstract class AbstractColumn implements Column
{
	private String		fieldName;
	private Class<?>	valueClass;
	private ColumnStyle	style	= DefaultColumnStyle.DEFAULT_COLUMN_STYLE.clone();
	

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void setColumnValueClass(Class<?> valueClass)
	{
		this.valueClass = valueClass;
	}
	

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void setStyle(ColumnStyle style)
	{
		this.style = style;
	}
	

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String getFieldName()
	{
		return this.fieldName;
	}
	

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Class<?> getColumnValueClass()
	{
		return this.valueClass;
	}
	

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public ColumnStyle getStyle()
	{
		return this.style;
	}
	
}
