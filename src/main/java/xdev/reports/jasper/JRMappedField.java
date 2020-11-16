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


import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;


public class JRMappedField implements JRField
{
	
	private final String	name;
	private final JRField	wrappedField;
	

	public JRMappedField(final JRField jrField, final String name)
	{
		this.name = name;
		this.wrappedField = jrField;
	}
	

	@Override
	public String getDescription()
	{
		return this.wrappedField.getDescription();
	}
	

	@Override
	public String getName()
	{
		return this.name;
	}
	

	@Override
	public Class<?> getValueClass()
	{
		return this.wrappedField.getValueClass();
	}
	

	@Override
	public String getValueClassName()
	{
		return this.wrappedField.getValueClassName();
	}
	

	@Override
	public void setDescription(String arg0)
	{
		this.wrappedField.setDescription(arg0);
	}
	

	@Override
	public JRPropertiesHolder getParentProperties()
	{
		return this.wrappedField.getParentProperties();
	}
	

	@Override
	public JRPropertiesMap getPropertiesMap()
	{
		return this.wrappedField.getPropertiesMap();
	}
	

	@Override
	public boolean hasProperties()
	{
		return this.wrappedField.hasProperties();
	}
	

	public Object clone()
	{
		return this.wrappedField.clone();
	}
	
}
