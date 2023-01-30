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

import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

/**
 * Alignment of the content.
 * 
 * @author XDEV Software(FHAE)
 */
public enum ColumnAlignment
{
	/**
	 * Indicates that the content is left aligned in the column.
	 *
	 */
	LEFT(HorizontalTextAlignEnum.LEFT),

	/**
	 * Indicates that the content is centered in the column.
	 *
	 */
	CENTER(HorizontalTextAlignEnum.CENTER),

	/**
	 * Indicates that the content is right aligned in the column.
	 *
	 */
	RIGHT(HorizontalTextAlignEnum.RIGHT);
	
	private HorizontalTextAlignEnum	horizontalAlignment;
	

	private ColumnAlignment(final HorizontalTextAlignEnum horizontalAlignment)
	{
		this.horizontalAlignment = horizontalAlignment;
	}
	

	/**
	 * Return the mapped {@link HorizontalTextAlignEnum}.
	 * 
	 * @return the {@link HorizontalTextAlignEnum}
	 */
	public HorizontalTextAlignEnum getHorizontalTextAlignEnum()
	{
		return this.horizontalAlignment;
	}
}
