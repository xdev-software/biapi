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
package xdev.tableexport;
import xdev.lang.LibraryMember;
import xdev.tableexport.cmd.ExportCreation;
import xdev.tableexport.export.ExportException;


@LibraryMember
public class Export
{
	/**
	 * No instanciation.
	 */
	private Export()
	{
	}
	

	/**
	 * Creates a report and shows it on the screen or sends it to the printer.
	 * 
	 * @param data
	 *            The {@link ExportCreation} containing the report content
	 */
	public static void Create(ExportCreation data) throws ExportException
	{
		data.init();
		data.execute();
	}
}