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


import net.sf.jasperreports.engine.type.HorizontalAlignEnum;

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
	LEFT(HorizontalAlignEnum.LEFT),

	/**
	 * Indicates that the content is centered in the column.
	 *
	 */
	CENTER(HorizontalAlignEnum.CENTER),

	/**
	 * Indicates that the content is right aligned in the column.
	 *
	 */
	RIGHT(HorizontalAlignEnum.RIGHT);
	
	private HorizontalAlignEnum	horizontalAlignment;
	

	private ColumnAlignment(HorizontalAlignEnum horizontalAlignment)
	{
		this.horizontalAlignment = horizontalAlignment;
	}
	

	/**
	 * Return the mapped {@link HorizontalAlignEnum}.
	 * 
	 * @return the {@link HorizontalAlignEnum}
	 */
	public HorizontalAlignEnum getHorizontalAlignEnum()
	{
		return this.horizontalAlignment;
	}
}
