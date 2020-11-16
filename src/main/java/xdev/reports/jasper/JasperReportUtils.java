package xdev.reports.jasper;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import xdev.io.IOUtils;


public class JasperReportUtils
{
	
	public static JasperReport loadReport(final File reportTemplate) throws JRException
	{
		JasperReport loadedReport = null;
		
		try
		{
			// try to find report file with xdev api
			final InputStream inputStream = IOUtils.findResource(reportTemplate.getAbsolutePath());
			loadedReport = (JasperReport)JRLoader.loadObject(inputStream);
		}
		catch(FileNotFoundException e)
		{
			// fallback try to find report file with jasper api
			loadedReport = (JasperReport)JRLoader.loadObject(reportTemplate);
		}
		catch(IOException e)
		{
			throw new JRException(e);
		}
		
		return loadedReport;
	}
	

	public static JRDataSource createMappedDataSource(final JRDataSource dataSource,
			final Map<String, String> fieldMapping)
	{
		if(dataSource instanceof JRRewindableDataSource)
		{
			return new JRRewindableDataSourceMapper((JRRewindableDataSource)dataSource,fieldMapping);
		}
		else
		{
			return new JRDataSourceMapper(dataSource,fieldMapping);
		}		
	}	
}
