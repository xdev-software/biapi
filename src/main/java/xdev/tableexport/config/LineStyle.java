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
import net.sf.jasperreports.engine.type.LineStyleEnum;

/**
 * Representation of the frame lines.
 * 
 * @author XDEV Software(FHAE)
 */
public enum LineStyle
{
	/**
	 * Constant useful for specifying solid line style.
	 */
	SOLID(LineStyleEnum.SOLID),

	/**
	 * Constant useful for specifying dashed line style.
	 */
	DASHED(LineStyleEnum.DASHED),

	/**
	 * Constant useful for specifying dotted line style.
	 */
	DOTTED(LineStyleEnum.DOTTED),

	/**
	 * Constant useful for specifying double line style.
	 */
	DOUBLE(LineStyleEnum.DOUBLE);
	
	private LineStyleEnum lineStyleEnum;
	
	private LineStyle(LineStyleEnum lineStyleEnum)
	{
		this.lineStyleEnum = lineStyleEnum;
	}
	
	/**
	 * Return the mapped {@link LineStyleEnum}.
	 * 
	 * @return the {@link LineStyleEnum}
	 */
	public LineStyleEnum getLineStyleEnum()
	{
		return this.lineStyleEnum;
	}
}
