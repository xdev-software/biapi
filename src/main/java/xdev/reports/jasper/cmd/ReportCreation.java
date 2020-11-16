package xdev.reports.jasper.cmd;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import xdev.io.IOUtils;
import xdev.io.XdevFile;
import xdev.lang.cmd.CommandException;
import xdev.lang.cmd.XdevCommandObject;
import xdev.reports.jasper.JRVirtualTableDataSource;
import xdev.reports.jasper.JRVirtualTableFormattedDataSource;
import xdev.reports.jasper.JasperReportUtils;
import xdev.reports.jasper.ReportBuilder;
import xdev.reports.jasper.ReportException;
import xdev.reports.jasper.ReportStub;
import xdev.ui.DesktopUtils;
import xdev.ui.XdevFileChooser;
import xdev.ui.XdevFileChooserFactory;
import xdev.ui.XdevFileFilter;
import xdev.vt.VirtualTable;


public abstract class ReportCreation extends XdevCommandObject
{
	public static enum Target
	{
		PREVIEW, PRINT, FILE, STREAM
	}
	
	
	
	public static enum OutputFormat
	{
		PDF("pdf"), HMTL("html"), EXCEL("xls"), RTF("rtf"), CSV("csv"), XML("xml"), TEXT("txt");
		
		private String suffix;
		private OutputFormat(final String suffix)
		{
			this.suffix = suffix;
		}
		
		public String getSuffix()
		{
			return suffix;
		}
	}
	
	public  XdevFile showFileChooser()
	{
		final String suffix = outputFormat.getSuffix();
		XdevFileChooser chooser = XdevFileChooserFactory.getFileChooser("reportCreationFileChooser",suffix!=null ? new XdevFileFilter(suffix,suffix) : null);
		return chooser.showSaveDialog();	
	}
	
	public XdevFile showFileChooser(final String suffix)
	{
		XdevFileChooser chooser = XdevFileChooserFactory.getFileChooser("reportCreationFileChooser",suffix!=null ? new XdevFileFilter(suffix,suffix) : null);
		return chooser.showSaveDialog();	
	}
	
	private ReportStub		reportStub;
	private Target			target;
	private boolean			displayPageDialog;
	private boolean			displayPageDialogOnlyOnce;
	private boolean			displayPrintDialog;
	private boolean			displayPrintDialogOnlyOnce;
	private OutputFormat	outputFormat;
	private File			file;
	private boolean			openFile;
	private OutputStream	outputStream;
	
	
	public ReportCreation(Object... args)
	{
		super(args);
	}
	
	
	protected JRDataSource createDataSource(VirtualTable vt, boolean formatted, String... mapping)
	{
		JRDataSource dataSource = formatted ? new JRVirtualTableFormattedDataSource(vt)
				: new JRVirtualTableDataSource(vt);
		if(mapping != null && mapping.length > 0)
		{
			Map<String, String> map = new HashMap<String, String>();
			for(int i = 0; i < mapping.length;)
			{
				map.put(mapping[i++],mapping[i++]);
			}
			dataSource = JasperReportUtils.createMappedDataSource(dataSource,map);
		}
		return dataSource;
	}
	
	
	public ReportStub getReportStub()
	{
		return reportStub;
	}
	
	
	public void setReportStub(ReportStub reportStub)
	{
		this.reportStub = reportStub;
	}
	
	
	public Target getTarget()
	{
		return target;
	}
	
	
	public void setTarget(Target target)
	{
		this.target = target;
	}
	
	
	public boolean getDisplayPageDialog()
	{
		return displayPageDialog;
	}
	
	
	public void setDisplayPageDialog(boolean displayPageDialog)
	{
		this.displayPageDialog = displayPageDialog;
	}
	
	
	public boolean getDisplayPageDialogOnlyOnce()
	{
		return displayPageDialogOnlyOnce;
	}
	
	
	public void setDisplayPageDialogOnlyOnce(boolean displayPageDialogOnlyOnce)
	{
		this.displayPageDialogOnlyOnce = displayPageDialogOnlyOnce;
	}
	
	
	public boolean getDisplayPrintDialog()
	{
		return displayPrintDialog;
	}
	
	
	public void setDisplayPrintDialog(boolean displayPrintDialog)
	{
		this.displayPrintDialog = displayPrintDialog;
	}
	
	
	public boolean getDisplayPrintDialogOnlyOnce()
	{
		return displayPrintDialogOnlyOnce;
	}
	
	
	public void setDisplayPrintDialogOnlyOnce(boolean displayPrintDialogOnlyOnce)
	{
		this.displayPrintDialogOnlyOnce = displayPrintDialogOnlyOnce;
	}
	
	
	public OutputFormat getOutputFormat()
	{
		return outputFormat;
	}
	
	
	public void setOutputFormat(OutputFormat outputFormat)
	{
		this.outputFormat = outputFormat;
	}
	
	
	public File getFile()
	{
		return file;
	}
	
	
	public void setFile(File file)
	{
		this.file = file;
	}
	
	
	public boolean getOpenFile()
	{
		return openFile;
	}
	
	
	public void setOpenFile(boolean openFile)
	{
		this.openFile = openFile;
	}
	
	
	public OutputStream getOutputStream()
	{
		return outputStream;
	}
	
	
	public void setOutputStream(OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}
	

	@SuppressWarnings("incomplete-switch")
	@Override
	public void execute() throws ReportException
	{
		if(reportStub == null)
		{
			CommandException.throwMissingParameter("reportStub");
		}
		if(target == null)
		{
			CommandException.throwMissingParameter("target");
		}
		switch(target)
		{
			case FILE:
			{
				if(outputFormat == null)
				{
					CommandException.throwMissingParameter("outputFormat");
				}
				if(file == null)
				{
					CommandException.throwMissingParameter("file");
				}
			}
			break;
			
			case STREAM:
			{
				if(outputFormat == null)
				{
					CommandException.throwMissingParameter("outputFormat");
				}
				if(outputStream == null)
				{
					CommandException.throwMissingParameter("outputStream");
				}
			}
			break;
		}
		
		try
		{
			ReportBuilder builder = new ReportBuilder(reportStub);
			
			switch(target)
			{
				case PREVIEW:
				{
					builder.previewReport();
				}
				break;
				
				case PRINT:
				{
					builder.print(displayPageDialog,displayPageDialogOnlyOnce,displayPrintDialog,
							displayPrintDialogOnlyOnce);
				}
				break;
				
				case FILE:
				case STREAM:
				{
					boolean closeStream;
					OutputStream outputStream;
					if(target == Target.FILE)
					{
						closeStream = true;
						outputStream = new FileOutputStream(file);
					}
					else
					{
						closeStream = false;
						outputStream = this.outputStream;
					}
					try
					{
						switch(outputFormat)
						{
							case PDF:
							{
								builder.writePdfFile(outputStream);
							}
							break;
							
							case HMTL:
							{
								builder.writeHtmlFile(outputStream);
							}
							break;
							
							case EXCEL:
							{
								builder.writeExcelFile(outputStream);
							}
							break;
							
							case RTF:
							{
								builder.writeRtfFile(outputStream);
							}
							break;
							
							case CSV:
							{
								builder.writeCsvFile(outputStream);
							}
							break;
							
							case XML:
							{
								builder.writeXmlFile(outputStream);
							}
							break;
							
							case TEXT:
							{
								builder.writeTextFile(outputStream);
							}
							break;
						}
					}
					finally
					{
						if(closeStream)
						{
							IOUtils.closeSilent(outputStream);
						}
					}
					
					if(target == Target.FILE && openFile)
					{
						DesktopUtils.open(file);
					}
				}
				break;
			}
		}
		catch(IOException e)
		{
			throw new ReportException(e);
		}
		catch(JRException e)
		{
			throw new ReportException(e);
		}
	}
}
