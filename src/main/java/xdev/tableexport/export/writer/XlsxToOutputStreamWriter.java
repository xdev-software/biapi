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


import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import xdev.tableexport.utils.ExportUtils;


/**
 * Write the generated report object into XLSX format.
 * 
 * <p>
 * The generated XLSX content is placed into a {@code OutputStream}.
 * </p>
 * 
 * @author XDEV Software(FHAE)
 */
public class XlsxToOutputStreamWriter implements ExportWriter
{
	private final OutputStream	stream;
	private final String		sheetName;
	private final Boolean		freezeHeadline;
	
	
	/**
	 * Constructs a {@link XlsxToOutputStreamWriter} object given a
	 * {@link OutputStream} object. No freeze headline are created.
	 * <p>
	 * Note - Excel allows sheet names up to 31 chars in length but other
	 * applications allow more. Excel does not crash with names longer than 31
	 * chars, but silently truncates such names to 31 chars.
	 * </p>
	 * 
	 * @param stream
	 *            a {@link OutputStream} object to write to.
	 * @param sheetName
	 *            the excel sheet name
	 */
	public XlsxToOutputStreamWriter(final OutputStream stream, final String sheetName)
	{
		this(stream,sheetName,false);
	}
	
	
	/**
	 * Constructs a {@link XlsxToOutputStreamWriter} object given a
	 * {@link OutputStream} object.
	 * 
	 * <p>
	 * Note - Excel allows sheet names up to 31 chars in length but other
	 * applications allow more. Excel does not crash with names longer than 31
	 * chars, but silently truncates such names to 31 chars.
	 * </p>
	 * 
	 * @param stream
	 *            a {@link OutputStream} object to write to.
	 * @param sheetName
	 *            the excel sheet name
	 * @param freezeHeadline
	 *            if <code>true</code> the freeze pane for the first row is
	 */
	public XlsxToOutputStreamWriter(final OutputStream stream, final String sheetName,
			final Boolean freezeHeadline)
	{
		this.stream = stream;
		this.sheetName = sheetName;
		this.freezeHeadline = freezeHeadline;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(JasperPrint jasperPrint) throws ExportWriterException
	{
		final JRXlsxExporter exporterXLSX = new JRXlsxExporter();
		try
		{
			ExportUtils.prepareExcelExporter(exporterXLSX,jasperPrint,this.sheetName,
					this.freezeHeadline,this.stream);
			exporterXLSX.exportReport();
		}
		catch(JRException e)
		{
			throw new ExportWriterException(e);
		}
		catch(IllegalArgumentException ie)
		{
			throw new ExportWriterException(ie);
		}
	}
	
}
