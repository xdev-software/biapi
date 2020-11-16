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


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;


@SuppressWarnings("deprecation")
public class PrintWriter implements ExportWriter
{
	
	private final boolean	displayPageDialog;
	private final boolean	displayPageDialogOnlyOnce;
	private final boolean	displayPrintDialog;
	private final boolean	displayPrintDialogOnlyOnce;
	
	
	public PrintWriter(boolean displayPageDialog, boolean displayPageDialogOnlyOnce,
			boolean displayPrintDialog, boolean displayPrintDialogOnlyOnce)
	{
		this.displayPageDialog = displayPageDialog;
		this.displayPageDialogOnlyOnce = displayPageDialogOnlyOnce;
		this.displayPrintDialog = displayPrintDialog;
		this.displayPrintDialogOnlyOnce = displayPrintDialogOnlyOnce;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(JasperPrint jasperPrint) throws ExportWriterException
	{
		try
		{
			final JRPrintServiceExporter exporter = new JRPrintServiceExporter();
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
					this.displayPageDialog);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG_ONLY_ONCE,
					this.displayPageDialogOnlyOnce);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					this.displayPrintDialog);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG_ONLY_ONCE,
					this.displayPrintDialogOnlyOnce);
			exporter.exportReport();
		}
		catch(JRException e)
		{
			throw new ExportWriterException(e);
		}
	}
}
