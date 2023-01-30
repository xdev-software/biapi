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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportToPreviewWriter implements ExportWriter
{
	

	/**
	 * Show the report in a the {@link JasperViewer}. 
	 * 
	 * @param jasperPrint the generated {@link JasperPrint} object
	 * 
	 */
	@Override
	public void write(JasperPrint jasperPrint) throws ExportWriterException
	{
		JasperViewer.viewReport(jasperPrint,false);
	}
	
}
