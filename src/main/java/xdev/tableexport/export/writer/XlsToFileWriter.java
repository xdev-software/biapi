package xdev.tableexport.export.writer;

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


import xdev.tableexport.utils.ExportUtils;

import java.io.File;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;


/**
 * Write the generated report object into XLS format.
 * 
 * <p>
 * The generated XLS content is placed into a {@code File}.
 * </p>
 * 
 * @author XDEV Software(FHAE)
 */
public class XlsToFileWriter implements ExportWriter
{
	private final File		file;
	private final String	sheetName;
	private final Boolean	freezeHeadline;
	
	
	/**
	 * Constructs a {@link XlsToFileWriter} object given a {@link File} object.
	 * No freeze headline are created.
	 * 
	 * <p>
	 * Note - Excel allows sheet names up to 31 chars in length but other
	 * applications allow more. Excel does not crash with names longer than 31
	 * chars, but silently truncates such names to 31 chars.
	 * </p>
	 * 
	 * @param file
	 *            a {@link File} object to write to.
	 * @param sheetName
	 *            the excel sheet name
	 */
	public XlsToFileWriter(final File file, final String sheetName)
	{
		this(file,sheetName,false);
	}
	
	
	/**
	 * Constructs a {@link XlsToFileWriter} object given a {@link File} object.
	 * 
	 * <p>
	 * Note - Excel allows sheet names up to 31 chars in length but other
	 * applications allow more. Excel does not crash with names longer than 31
	 * chars, but silently truncates such names to 31 chars.
	 * </p>
	 * 
	 * @param file
	 *            a {@link File} object to write to.
	 * @param freezeHeadline
	 *            if <code>true</code> the freeze pane for the first row is
	 * @param sheetName
	 *            the excel sheet name
	 */
	public XlsToFileWriter(final File file, final String sheetName, final Boolean freezeHeadline)
	{
		this.file = file;
		this.sheetName = sheetName;
		this.freezeHeadline = freezeHeadline;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(JasperPrint jasperPrint) throws ExportWriterException
	{
		final JRXlsExporter exporterXLS = new JRXlsExporter();
		try
		{
			ExportUtils.prepareExcelExporter(exporterXLS,jasperPrint,this.sheetName,
					this.freezeHeadline,this.file);
			exporterXLS.exportReport();
		}
		catch(JRException e)
		{
			throw new ExportWriterException(e);
		}
		catch(Exception ie)
		{
			throw new ExportWriterException(ie);
		}
	}
}
