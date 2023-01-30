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
package xdev.tableexport.config;

/**
 * Representation of a content (detail) column.
 * 
 * @author XDEV Software(FHAE)
 */
public class ContentColumn extends AbstractColumn
{
	private String	pattern;
	

	/**
	 * The Property is used as the column pattern.
	 * <br>
	 * Sets the pattern used for this column. <br>
	 * The pattern will be used in a SimpleDateFormat for dates and a DecimalFormat for numeric column fields. <br>
	 * The pattern format must follow one of these two classes formatting rules, as specified in the JDK API docs.<br>
	 * 
	 * @param pattern 
	 */
	@Override
	public void setProperty(String pattern)
	{
		this.pattern = pattern;
	}
	

	/**
	 * Return the column pattern of this {@link ContentColumn}.
	 * @return the column pattern of this {@link ContentColumn}.
	 * 
	 * @see #setProperty(String)
	 */
	@Override
	public String getProperty()
	{
		return this.pattern;
	}	
}
