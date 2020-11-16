package xdev.tableexport.export;

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


import java.util.HashMap;

import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import xdev.tableexport.export.writer.ExportWriter;


public class ReportExporter
{
	
	private JasperReport			internalReport;
	private JRRewindableDataSource	internalDataSource;
	private JasperPrint				jasperPrint;
	private ExportWriter			exportWriter;
	

	public ReportExporter(JasperReport report, JRRewindableDataSource ds, ExportWriter writer)
	{
		this.internalDataSource = ds;
		this.internalReport = report;
		this.exportWriter = writer;
	}
	

	public void export() throws ExportException, IllegalArgumentException
	{
		if(this.internalDataSource == null)
		{
			throw new IllegalArgumentException("dataSource should not be null");
		}
		if(this.internalReport == null)
		{
			throw new IllegalArgumentException("report should not be null");
		}
		
		try
		{
			this.jasperPrint = JasperFillManager.fillReport(this.internalReport,
					new HashMap<String, Object>(),this.internalDataSource);
		}
		catch(Exception e)
		{
			throw new ExportException("The Report can't fill." + e.getMessage(), e);
		}
		
		this.exportWriter.write(this.jasperPrint);
	}
	
}
