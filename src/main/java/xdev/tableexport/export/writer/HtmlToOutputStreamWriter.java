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
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;


/**
 * Write the generated report object into HTML format.
 * 
 * <p>
 * The generated HTML content is placed into a {@code OutputStream}.
 * </p>
 * 
 * @author XDEV Software(FHAE)
 */
@SuppressWarnings("deprecation")
public class HtmlToOutputStreamWriter implements ExportWriter
{
	private final OutputStream	outputStream;
	
	
	/**
	 * Constructs a {@link HtmlToOutputStreamWriter} object given a
	 * {@link OutputStream} object.
	 * 
	 * 
	 * @param outputStream
	 *            a {@link OutputStream} object to write to.
	 */
	public HtmlToOutputStreamWriter(final OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(JasperPrint jasperPrint) throws ExportWriterException
	{
		try
		{
			final JRExporter exporter = new JRHtmlExporter();
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,this.outputStream);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			exporter.exportReport();
			
		}
		catch(JRException e)
		{
			throw new ExportWriterException(e);
		}
	}
}
