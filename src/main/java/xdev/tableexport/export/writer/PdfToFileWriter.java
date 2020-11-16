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

import java.io.File;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;


/**
 * Write the generated report object into PDF format.
 * 
 * <p>
 * The generated PDF content is placed into a {@code File}.
 * </p>
 * 
 * @author XDEV Software(FHAE)
 */
public class PdfToFileWriter implements ExportWriter
{
	private final File file;
	
	/**
	 * Constructs a {@link PdfToFileWriter} object given a {@link File} object.
	 * 
	 * 
	 * @param file a {@link File} object to write to.
	 */
	public PdfToFileWriter(final File file)
	{
		this.file = file;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(JasperPrint jasperPrint)
			throws ExportWriterException
	{
		try
		{
			JasperExportManager.exportReportToPdfFile(jasperPrint,this.file.getAbsolutePath());
		}
		catch(JRException e)
		{
			throw new ExportWriterException(e);
		}
	}

}
