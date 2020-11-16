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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import xdev.io.IOUtils;


public abstract class AbstractReportStub implements ReportStub
{
	private JRDataSource		dataSource	= null;
	private String				jasperFilePath;
	private List<ReportStub>	subreports	= new ArrayList<ReportStub>();
	
	
	@Override
	public void setDataSource(JRDataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	
	@Override
	public JRDataSource getDataSource()
	{
		return dataSource;
	}
	
	
	public void setJasperFilePath(String jasperFilePath)
	{
		this.jasperFilePath = jasperFilePath;
	}
	
	
	@Override
	public String getJasperFilePath()
	{
		return jasperFilePath;
	}
	
	
	public void addSubreport(ReportStub subreport)
	{
		subreports.add(subreport);
	}
	
	
	@Override
	public JasperReport getReportTemplate() throws JRException
	{
		try
		{
			// try to find report file with xdev api
			final InputStream inputStream = IOUtils.findResource(jasperFilePath);
			return (JasperReport)JRLoader.loadObject(inputStream);
		}
		catch(FileNotFoundException e)
		{
			// fallback try to find report file with jasper api
			return (JasperReport)JRLoader.loadObject(new File(jasperFilePath));
		}
		catch(IOException e)
		{
			throw new JRException(e);
		}
	}
	
	
	@Override
	public Map<String, Object> getParameters()
	{
		Map<String, Object> parameters = new HashMap<String, Object>()
		{
			@Override
			public Object put(String key, Object value)
			{
				if(value != null)
				{
					return super.put(key,value);
				}
				return null;
			}
		};
		
		for(ReportStub subreport : this.subreports)
		{
			parameters.putAll(subreport.getParameters());
		}
		
		collectParameters(parameters);
		
		return parameters;
	}
	
	
	protected abstract void collectParameters(Map<String, Object> map);
}
