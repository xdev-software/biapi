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
package xdev.tableexport.utils;
import net.sf.jasperreports.engine.design.JRDesignTextField;

/**
 * Converter which converts <T> to an export pattern-String.
 * 
 * @author XDEV Software(FHAE)
 */
public interface PatternConverter<T>
{
	 /**
	  * Return the converted export pattern.
	  * <p><b>
	  * The export pattern is used in the report to define the datatype and the presentation.
	  * {@link JRDesignTextField#setPattern(String)}.
	  * </p></b>
	  * 
	  * @param obj
	  *            the object to convert
	  * @return the converted export pattern
	  */
	public String getExportPattern(T obj);
}
