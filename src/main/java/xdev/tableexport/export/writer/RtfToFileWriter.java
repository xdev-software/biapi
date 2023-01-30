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

import java.io.File;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;


/**
 * Write the generated report object into RTF format.
 * 
 * <p>
 * The generated RTF content is placed into a {@code File}.
 * </p>
 * 
 * @author XDEV Software(FHAE)
 */
@SuppressWarnings("deprecation")
public class RtfToFileWriter implements ExportWriter
{
	
	private final File		file;
	

	/**
	 * Constructs a {@link RtfToFileWriter} object given a {@link File} object.
	 * 
	 * @param file a {@link File} object to write to.
	 */
	public RtfToFileWriter(final File file)
	{
		this.file = file;
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(JasperPrint jasperPrint) throws ExportWriterException
	{
		final JRExporter exporter = new JRRtfExporter();
		try
		{
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,this.file);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
	
			exporter.exportReport();
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
