package xdev.tableexport.cmd;

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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.xml.JRXmlWriter;


public interface TableExportDebugger
{
	
	public boolean isDebugModeOn();
	
	
	public String getFilePath();
	
	
	public void exportJasperReportToJrxml(final JasperReport jReport) throws JRException;
	
	
	
	public class DefaultTableExportDebuggerImpl implements TableExportDebugger
	{
		private boolean	debugMode	= false;
		private String	filePath	= null;
		
		
		@Override
		public boolean isDebugModeOn()
		{
			return debugMode;
		}
		
		
		@Override
		public String getFilePath()
		{
			return filePath;
		}
		
		
		@Override
		public void exportJasperReportToJrxml(final JasperReport jReport) throws JRException
		{
			JRXmlWriter.writeReport(jReport,this.getFilePath(),"UTF-8");
		}
		
	}
}
