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
 * Representation of a header column.
 * 
 * @author XDEV Software(FHAE)
 */
public class HeaderColumn extends AbstractColumn
{
	private String	text;
	

	/**
	 * The Property is used as header text.
	 * 
	 * @return the header text 
	 */
	@Override
	public String getProperty()
	{
		return this.text;
	}
	

	/**
	 * The Property is used as header text.
	 * 
	 * @param text 
	 */
	@Override
	public void setProperty(String text)
	{
		this.text = text;
	}
	
}
