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
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;


/**
 * Write the generated report object into CSV format.
 * 
 * <p>
 * The generated CSV content is placed into a {@code OutputStream}.
 * </p>
 * 
 * @author XDEV Software(FHAE)
 */
@SuppressWarnings("deprecation")
public class CSVToOutputStreamWriter implements ExportWriter
{
	private final OutputStream	stream;
	
	
	/**
	 * Constructs a {@link CSVToOutputStreamWriter} object given a
	 * {@link OutputStream} object.
	 * 	 * 
	 * @param stream
	 *            a {@link OutputStream} object to write to.
	 */
	public CSVToOutputStreamWriter(final OutputStream stream)
	{
		this.stream = stream;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(JasperPrint jasperPrint) throws ExportWriterException
	{
		final JRExporter exporter = new JRCsvExporter();
		try
		{
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,this.stream);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
	
			exporter.exportReport();
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
