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
package xdev.tableexport.config.builder;
import xdev.tableexport.config.ContentColumn;

import javax.swing.JTable;


public class JTableViewConfigBuilder extends DefaultJTableConfigBuilder<JTable>
{
	public JTableViewConfigBuilder(JTable table)
	{
		super(table);
	}
	
	/**
	 * Ignore the given columnValueClass and call each time {@link ContentColumn#setColumnValueClass ContentColumn#setColumnValueClass(String.class)}.
	 * @param column
	 * @param columnValueClass 
	 */
	@Override
	public void handleColumnValueClass(ContentColumn column, Class<?> columnValueClass)
	{
		super.handleColumnValueClass(column, String.class);
	}



}
