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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXmlExporterOutput;
import net.sf.jasperreports.export.parameters.ParametersExporterInput;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import xdev.io.IOUtils;
import xdev.lang.NotNull;


/**
 * The {@link ReportBuilder} class provides a easy way to access the Jasper
 * Reports API from XDEV API.
 * 
 * 
 * <p>
 * The {@link JasperPrint} instance for this {@link ReportBuilder} is cached
 * internally to allow multiple outputs from one {@link ReportBuilder} instance
 * for non-rewindable {@link JRDataSource}s.
 * </p>
 * 
 * @author XDEV Software Corp.
 */
@SuppressWarnings({"unchecked","deprecation"})
// OK, because JasperAPI maps ship without generic types specified
public class ReportBuilder
{
	/**
	 * Primary data source.
	 */
	private final JRDataSource	dataSource;
	
	/**
	 * Compiled report design.
	 */
	private final JasperReport	report;
	
	/**
	 * Parameters.
	 */
	private final Map			parameters			= new HashMap();
	
	/**
	 * The cached JasperPrint object for this {@link ReportBuilder}.
	 */
	private JasperPrint			cachedJasperPrint	= null;
	
	
	public ReportBuilder(final ReportStub reportStub) throws JRException
	{
		this.parameters.putAll(reportStub.getParameters());
		this.report = reportStub.getReportTemplate();
		this.dataSource = reportStub.getDataSource();
	}
	
	
	/**
	 * Creates a {@link ReportBuilder} with all needed information to build many
	 * different outputs of your report.
	 * 
	 * 
	 * 
	 * 
	 * @param reportLocation
	 *            a String describing the location of the compiled
	 *            {@link JasperReport} design to load. Must not be null.
	 * 
	 *            <p>
	 *            Searches for a resource in the application's classpath and the
	 *            filesystem.
	 *            </p>
	 * 
	 * @param parameters
	 *            report parameters {@link Map}
	 * @param dataSource
	 *            {@link JRDataSource} object that contains the data, you want
	 *            the report to be filled with. Must not be null.
	 * 
	 * @throws JRException
	 *             if the report could not be found or a problem occurred
	 *             loading the report
	 */
	public ReportBuilder(@NotNull final String reportLocation, final Map parameters,
			@NotNull final JRDataSource dataSource) throws JRException
	{
		
		if(reportLocation == null)
		{
			throw new IllegalArgumentException("reportLocation must not be null");
		}
		else
		{
			JasperReport loadedReport = null;
			
			try
			{
				// try to find report file with xdev api
				final InputStream inputStream = IOUtils.findResource(reportLocation);
				loadedReport = (JasperReport)JRLoader.loadObject(inputStream);
			}
			catch(final FileNotFoundException e)
			{
				// fallback try to find report file with jasper api
				loadedReport = (JasperReport)JRLoader.loadObjectFromFile(reportLocation);
			}
			catch(final IOException e)
			{
				throw new JRException(e);
			}
			
			this.report = loadedReport;
		}
		
		if(parameters != null)
		{
			this.parameters.putAll(parameters);
		}
		
		/*
		 * prohibit null vales for dataSource as it is a common pitfall for
		 * empty reports
		 */
		if(dataSource == null)
		{
			throw new IllegalArgumentException(
					"dataSource must not be null. The use of JREmptyDataSource may be appropriate.");
		}
		else
		{
			this.dataSource = dataSource;
		}
		
	}
	
	
	/**
	 * Creates a {@link ReportBuilder} with all needed information to build many
	 * different outputs of your report.
	 * 
	 * 
	 * 
	 * 
	 * @param reportLocation
	 *            a {@link File} describing the location of the compiled
	 *            {@link JasperReport} design to load. Must not be null.
	 * @param parameters
	 *            report parameters {@link Map}
	 * @param dataSource
	 *            {@link JRDataSource} object that contains the data, you want
	 *            the report to be filled with. Must not be null.
	 * @throws JRException
	 *             if the report could not be found or a problem occurred
	 *             loading the report
	 */
	
	public ReportBuilder(@NotNull final File reportLocation, final Map parameters,
			@NotNull final JRDataSource dataSource) throws JRException
	{
		
		if(reportLocation == null)
		{
			throw new IllegalArgumentException("reportLocation must not be null");
		}
		else
		{
			
			JasperReport loadedReport = null;
			
			try
			{
				// try to find report file with xdev api
				final InputStream inputStream = IOUtils.findResource(reportLocation
						.getAbsolutePath());
				loadedReport = (JasperReport)JRLoader.loadObject(inputStream);
			}
			catch(final FileNotFoundException e)
			{
				// fallback try to find report file with jasper api
				loadedReport = (JasperReport)JRLoader.loadObject(reportLocation);
			}
			catch(final IOException e)
			{
				throw new JRException(e);
			}
			
			this.report = loadedReport;
		}
		
		if(parameters != null)
		{
			this.parameters.putAll(parameters);
		}
		
		/*
		 * prohibit null vales for dataSource as it is a common pitfall for
		 * empty reports
		 */
		if(dataSource == null)
		{
			throw new IllegalArgumentException(
					"dataSource must not be null. The use of JREmptyDataSource may be appropriate.");
		}
		else
		{
			this.dataSource = dataSource;
		}
	}
	
	
	/**
	 * Creates a {@link ReportBuilder} with all needed information to build many
	 * different outputs of your report.
	 * 
	 * @param inputStream
	 *            a {@link InputStream} to load the compiled
	 *            {@link JasperReport} design from. Must not be null.
	 * 
	 * @param parameters
	 *            report parameters {@link Map}
	 * 
	 * @param dataSource
	 *            {@link JRDataSource} object that contains the data, you want
	 *            the report to be filled with. Must not be null.
	 * 
	 * @throws JRException
	 *             if the report could not be found or a problem occurred
	 *             loading the report
	 */
	
	public ReportBuilder(@NotNull final InputStream inputStream, final Map parameters,
			@NotNull final JRDataSource dataSource) throws JRException
	{
		super();
		
		if(inputStream == null)
		{
			throw new IllegalArgumentException("inputStream must not be null");
		}
		else
		{
			this.report = (JasperReport)JRLoader.loadObject(inputStream);
		}
		
		if(parameters != null)
		{
			this.parameters.putAll(parameters);
		}
		
		/*
		 * prohibit null vales for dataSource as it is a common pitfall for
		 * empty reports
		 */
		if(dataSource == null)
		{
			throw new IllegalArgumentException(
					"dataSource must not be null. The use of JREmptyDataSource may be appropriate.");
		}
		else
		{
			this.dataSource = dataSource;
		}
		
	}
	
	
	/**
	 * Creates a {@link ReportBuilder} with all needed information to build many
	 * different outputs of your report.
	 * 
	 * @param report
	 *            {@link JasperReport} instance to fill. Must not be null.
	 * 
	 * @param parameters
	 *            Map with parameters which are not in the DataSource
	 * @param dataSource
	 *            JRDataSource which is suitable for your Report. Must not be
	 *            null.
	 */
	public ReportBuilder(final JasperReport report, final Map parameters,
			@NotNull final JRDataSource dataSource)
	{
		super();
		
		if(report == null)
		{
			throw new IllegalArgumentException("report must not be null");
		}
		else
		{
			this.report = report;
			
			if(parameters != null)
			{
				this.parameters.putAll(parameters);
			}
			
			/*
			 * prohibit null vales for dataSource as it is a common pitfall for
			 * empty reports
			 */
			if(dataSource == null)
			{
				throw new IllegalArgumentException(
						"dataSource must not be null. The use of JREmptyDataSource may be appropriate.");
			}
			else
			{
				this.dataSource = dataSource;
			}
		}
		
	}
	
	
	/**
	 * Creates a {@link JasperPrint} instance using the previously set
	 * parameters, data source and report template, if there is not already a
	 * cached {@link JasperPrint} object for this {@link ReportBuilder}
	 * available.
	 * 
	 * @return a {@link JasperPrint} instance
	 * @throws JRException
	 *             Indicates problems creating the {@link JasperPrint}
	 */
	protected JasperPrint getJasperPrint() throws JRException
	{
		if(this.cachedJasperPrint == null)
		{
			this.cachedJasperPrint = JasperFillManager.fillReport(this.report,new HashMap(
					this.parameters),this.dataSource);
		}
		
		return this.cachedJasperPrint;
	}
	
	
	/**
	 * Returns a new {@link JRViewer} instance for this {@link ReportBuilder}.
	 * 
	 * @param withToolbar
	 *            <code>true</code> to show the toolbar, <code>false</code> to
	 *            hide it
	 * 
	 * @return JRViewer a new {@link JRViewer} instance.
	 * @throws JRException
	 *             Indicates problems creating the {@link JRViewer}
	 */
	public JRViewer getJRViewer(final boolean withToolbar) throws JRException
	{
		final JRViewer viewer = new JRViewer(this.getJasperPrint());
		if(!withToolbar)
		{
			viewer.remove(0);
		}
		return viewer;
	}
	
	
	/**
	 * Opens the JasperViewer in a separate Window, with the given Content.
	 * 
	 * @throws JRException
	 *             if a problem occurs while filling the report
	 */
	public void previewReport() throws JRException
	{
		JasperViewer.viewReport(this.getJasperPrint(),false);
	}
	
	
	
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as PDF to the provided
	 * {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writePdfFile(final OutputStream out) throws JRException
	{
		final JRPdfExporter exporter = new JRPdfExporter();
		final Map<JRExporterParameter, Object> parameters = new HashMap<>();
		parameters.put(JRExporterParameter.JASPER_PRINT, this.getJasperPrint());
		final ParametersExporterInput exporterInput = new ParametersExporterInput(this.parameters);
		
		exporter.setExporterInput(exporterInput);
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
		
		exporter.exportReport();
	}
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as HTML to the provided
	 * {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writeHtmlFile(final OutputStream out) throws JRException
	{
		final HtmlExporter exporter = new HtmlExporter();
		final Map<JRExporterParameter, Object> parameters = new HashMap<>();
		parameters.put(JRExporterParameter.JASPER_PRINT, this.getJasperPrint());
		final ParametersExporterInput exporterInput = new ParametersExporterInput(this.parameters);
		
		exporter.setExporterInput(exporterInput);
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
		
		exporter.exportReport();
	}
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as XLS (MS Excel) to the
	 * provided {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writeExcelFile(final OutputStream out) throws JRException
	{
		final JRXlsExporter exporter = new JRXlsExporter();
		final Map<JRExporterParameter, Object> parameters = new HashMap<>();
		parameters.put(JRExporterParameter.JASPER_PRINT, this.getJasperPrint());
		final ParametersExporterInput exporterInput = new ParametersExporterInput(this.parameters);
		
		exporter.setExporterInput(exporterInput);
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
		
		exporter.exportReport();
	}
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as RTF to the provided
	 * {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writeRtfFile(final OutputStream out) throws JRException
	{
		final JRRtfExporter exporter = new JRRtfExporter();
		final Map<JRExporterParameter, Object> parameters = new HashMap<>();
		parameters.put(JRExporterParameter.JASPER_PRINT, this.getJasperPrint());
		final ParametersExporterInput exporterInput = new ParametersExporterInput(this.parameters);
		
		exporter.setExporterInput(exporterInput);
		exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
		
		exporter.exportReport();
	}
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as CSV to the provided
	 * {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writeCsvFile(final OutputStream out) throws JRException
	{
		final JRCsvExporter exporter = new JRCsvExporter();
		final Map<JRExporterParameter, Object> parameters = new HashMap<>();
		parameters.put(JRExporterParameter.JASPER_PRINT, this.getJasperPrint());
		final ParametersExporterInput exporterInput = new ParametersExporterInput(this.parameters);
		
		exporter.setExporterInput(exporterInput);
		exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
		
		exporter.exportReport();
	}
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as XML to the provided
	 * {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writeXmlFile(final OutputStream out) throws JRException
	{
		final JRXmlExporter exporter = new JRXmlExporter();
		final Map<JRExporterParameter, Object> parameters = new HashMap<>();
		parameters.put(JRExporterParameter.JASPER_PRINT, this.getJasperPrint());
		final ParametersExporterInput exporterInput = new ParametersExporterInput(this.parameters);
		
		exporter.setExporterInput(exporterInput);
		exporter.setExporterOutput(new SimpleXmlExporterOutput(out));
		
		exporter.exportReport();
	}
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as plain text to the provided
	 * {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * 
	 * 
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writeTextFile(final OutputStream out) throws JRException
	{
		this.writeTextFile(out,80,30);
	}
	
	
	/**
	 * Writes this {@link ReportBuilder} formatted as plain text to the provided
	 * {@link OutputStream}.
	 * 
	 * @param out
	 *            {@link OutputStream} to write to.
	 * 
	 * @param pageWidth
	 * 
	 * @param pageHeight
	 * 
	 * @throws JRException
	 *             if a problem occurs while writing the report.
	 */
	public void writeTextFile(final OutputStream out, final int pageWidth, final int pageHeight)
			throws JRException
	{
		
		final JRTextExporter exporter = new JRTextExporter();
		
		final Map<JRExporterParameter, Object> parameters = new HashMap<>();
		parameters.put(JRExporterParameter.JASPER_PRINT, this.getJasperPrint());
		parameters.put(JRTextExporterParameter.PAGE_WIDTH,pageWidth);
		parameters.put(JRTextExporterParameter.PAGE_HEIGHT,pageHeight);
		
		final ParametersExporterInput exporterInput = new ParametersExporterInput(this.parameters);
		
		exporter.setExporterInput(exporterInput);
		exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
		
		exporter.exportReport();
	}
	
	
	public void print() throws JRException
	{
		this.print(false,false);
	}
	
	
	public void print(final boolean displayPageDialog, final boolean displayPrintDialog) throws JRException
	{
		this.print(displayPageDialog,false,displayPrintDialog,false);
	}
	
	
	public void print(final boolean displayPageDialog, final boolean displayPageDialogOnlyOnce,
			final boolean displayPrintDialog, final boolean displayPrintDialogOnlyOnce) throws JRException
	{
		final JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,this.getJasperPrint());
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,displayPageDialog);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG_ONLY_ONCE,
				displayPageDialogOnlyOnce);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
				displayPrintDialog);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG_ONLY_ONCE,
				displayPrintDialogOnlyOnce);
		exporter.exportReport();
	}
	
	
	/**
	 * @return the dataSource
	 * @see #dataSource
	 */
	protected JRDataSource getDataSource()
	{
		return this.dataSource;
	}
	
	
	/**
	 * @return the report
	 * @see #report
	 */
	protected JasperReport getReport()
	{
		return this.report;
	}
	
	
	/**
	 * @return the parameters
	 * @see #parameters
	 */
	protected Map getParameters()
	{
		return this.parameters;
	}
}
