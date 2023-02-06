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
 * 
 * @author XDEV Software(FHAE)
 */
public interface PageProperties
{
	
	
	/**
	 * Return page height (including margins etc.).
	 * 
	 * @return the page height value as <code>int</code>
	 */
	public int getPageHeight();
	

	/**
	 * Return the spacing between columns.
	 * 
	 * @return the spacing value between columns as <code>int</code>
	 */
	public int getColumnSpacing();
	

	/**
	 * Return the left margin. The working space is calculated by subtracting the margins from the page width.
	 * 
	 * @return the left margin value as <code>int</code>
	 */
	public int getLeftMargin();
	

	/**
	 * Return the right margin. The working space is calculated by subtracting the margins from the page width.
	 * 
	 * @return the right margin value as <code>int</code>
	 */
	public int getRightMargin();
	

	/**
	 * Return the top margin. The working space is calculated by subtracting the margins from the page height.
	 * 
	 * @return the top margin value as <code>int</code>
	 */
	public int getTopMargin();
	

	/**
	 * Return the bottom margin. The working space is calculated by subtracting the margins from the page height.
	 * 
	 * @return the bottom margin value as <code>int</code>
	 */
	public int getBottomMargin();
	
}
