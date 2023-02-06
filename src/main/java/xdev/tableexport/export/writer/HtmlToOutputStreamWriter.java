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
package xdev.tableexport.export.writer;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;


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
	public void write(final JasperPrint jasperPrint) throws ExportWriterException
	{
		try
		{
			final HtmlExporter exporter = new  HtmlExporter();
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,this.outputStream);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			exporter.exportReport();
			
		}
		catch(final JRException e)
		{
			throw new ExportWriterException(e);
		}
	}
}
