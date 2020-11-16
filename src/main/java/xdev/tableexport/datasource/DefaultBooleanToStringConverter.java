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


import java.util.Locale;

import xdev.tableexport.utils.DataSourceResourceBundle;


/**
 * This ValueToStringConverter convert a {@link Boolean} value to a
 * {@link String}. To determine the correct {@link String} the
 * {@link DataSourceResourceBundle} is used.
 * 
 * <p>
 * <strong>Waring:</strong> The local's {@link Locale#GERMANY} and
 * {@link Locale#US} are supported.
 * </p>
 * 
 * @author XDEV Software (FHAE)
 * 
 * @see DataSourceResourceBundle
 *  
 */
public class DefaultBooleanToStringConverter implements ValueConverter<Boolean,String>
{
	private String	trueValue	= DataSourceResourceBundle
										.getString("jtableRendererDataSource.trueValue");
	private String	falseValue	= DataSourceResourceBundle
										.getString("jtableRendererDataSource.falseValue");
	
	/**
	 * Convert the given {@link Boolean} into a String.
	 * The values of the {@link Boolean} determine by reading the {@link DataSourceResourceBundle}.
	 * 
	 * 
	 * @param value
	 *           	a {@link Boolean} to convert
	 * @return a the local specific {@link String} value. If the {@code value} is <code>null</code> a empty string is returned.
	 * 
	 */
	@Override
	public String getValue(Boolean value)
	{
		if(value==null) return "";
		if(value)
		{
			return trueValue;
		}
		else
		{
			return falseValue;
		}
	}
	
}
