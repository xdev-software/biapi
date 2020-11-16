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


/**
 * An representation of a data source field. Each row in a dataset consists of one or more fields with unique
 * names.
 * 
 * @author XDEV Software(FHAE)
 */
public interface Column
{
	
	/**
	 * Return the style for this {@link Column}.
	 * 
	 * @return the {@link ColumnStyle}
	 *  
	 * @see #setStyle(ColumnStyle)
	 */
	public ColumnStyle getStyle();
	

	public String getProperty();
	

	/**
	 * Return the column (field) value class. Field types cannot be primitives.
	 * 
	 * @return the {@link Class} for this {@link Column}
	 */
	public Class<?> getColumnValueClass();
	

	/**
	 * Return the field unique name of this {@code Column}.
	 * 
	 * @return the unique name
	 */
	public String getFieldName();
	

	/**
	 * Sets the style for this {@link Column}.
	 * 
	 * @see #setStyle(ColumnStyle)
	 */
	public void setStyle(ColumnStyle style);
	

	public void setProperty(String property);
	

	/**
	 * Sets the column (field) value class. Field types cannot be primitives.
	 */
	public void setColumnValueClass(Class<?> valueClass);
	

	public void setFieldName(String fieldName);
	
}
