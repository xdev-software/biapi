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


import xdev.lang.LibraryMember;
import xdev.reports.jasper.cmd.ReportCreation;


@LibraryMember
public final class Report
{
	/**
	 * No instanciation.
	 */
	private Report()
	{
	}
	

	/**
	 * Creates a report and shows it on the screen or sends it to the printer.
	 * 
	 * @param data
	 *            The {@link ReportCreation} containing the report content
	 */
	public static void Create(ReportCreation data) throws ReportException
	{
		data.init();
		data.execute();
	}
}
