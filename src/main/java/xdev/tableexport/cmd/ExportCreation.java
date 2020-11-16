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


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;

import javax.swing.JTable;

import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JasperReport;
import xdev.io.XdevFile;
import xdev.lang.cmd.CommandException;
import xdev.lang.cmd.XdevCommandObject;
import xdev.tableexport.JRVirtualTableDataSource;
import xdev.tableexport.config.ColumnAlignment;
import xdev.tableexport.config.ColumnBorder;
import xdev.tableexport.config.ColumnStyle;
import xdev.tableexport.config.ContentColumn;
import xdev.tableexport.config.HeaderColumn;
import xdev.tableexport.config.TemplateColumn;
import xdev.tableexport.config.TemplateConfig;
import xdev.tableexport.config.builder.ExportConfigBuilder;
import xdev.tableexport.config.builder.JTableViewConfigBuilder;
import xdev.tableexport.config.builder.VTConfigBuilder;
import xdev.tableexport.config.builder.XdevTableConfigBuilder;
import xdev.tableexport.datasource.XdevJTableRendererDataSource;
import xdev.tableexport.datasource.XdevTableDataSource;
import xdev.tableexport.export.ExportException;
import xdev.tableexport.export.ReportBuilder;
import xdev.tableexport.export.ReportExporter;
import xdev.tableexport.export.writer.CSVToFileWriter;
import xdev.tableexport.export.writer.CSVToOutputStreamWriter;
import xdev.tableexport.export.writer.ExportWriter;
import xdev.tableexport.export.writer.HtmlToFileWriter;
import xdev.tableexport.export.writer.HtmlToOutputStreamWriter;
import xdev.tableexport.export.writer.PdfToFileWriter;
import xdev.tableexport.export.writer.PdfToOutputStreamWriter;
import xdev.tableexport.export.writer.PrintWriter;
import xdev.tableexport.export.writer.ReportToPreviewWriter;
import xdev.tableexport.export.writer.RtfToFileWriter;
import xdev.tableexport.export.writer.RtfToOutputStreamWriter;
import xdev.tableexport.export.writer.XMLToFileWriter;
import xdev.tableexport.export.writer.XMLToOutputStreamWriter;
import xdev.tableexport.export.writer.XlsToFileWriter;
import xdev.tableexport.export.writer.XlsToOutputStreamWriter;
import xdev.tableexport.export.writer.XlsxToFileWriter;
import xdev.tableexport.export.writer.XlsxToOutputStreamWriter;
import xdev.ui.DesktopUtils;
import xdev.ui.VirtualTableEditor;
import xdev.ui.XdevFileChooser;
import xdev.ui.XdevFileChooserFactory;
import xdev.ui.XdevFileFilter;
import xdev.vt.VirtualTable;


public abstract class ExportCreation extends XdevCommandObject
{
	
	private DataSourceType		dataSourceType;
	
	/**
	 * Source types
	 */
	private VirtualTable		virtualTable;
	private VirtualTableEditor	xdevTable;
	private JTable				jTable;
	// //////////////////////////////////////////////////////////////
	// //////// COLUMN ELEMENTE ///////////////////////////
	// ////////////////////////////////////////////////////////////
	private TemplateConfig		templateConfig		= new TemplateConfig();
	
	// //////////////////////////////////////////////////////////////
	// //////// PRINT ELEMENTE ///////////////////////////
	// ////////////////////////////////////////////////////////////
	private boolean				displayPageDialog;
	private boolean				displayPageDialogOnlyOnce;
	private boolean				displayPrintDialog;
	private boolean				displayPrintDialogOnlyOnce;
	
	private ColumnStyle			defaultHeaderStyle	= null;
	private ColumnStyle			defaultContentStyle	= null;
	
	// //////////////////////////////////////////////////////////////
	// //////// OUTPUT ELEMENTE ///////////////////////////
	// ////////////////////////////////////////////////////////////
	private Target				target;
	private OutputFormat		outputFormat;
	/**
	 * Flag to open file after export
	 */
	private boolean				openFileAfterExport;
	private File				file;
	private OutputStream		outputStream;
	
	/** 
	 *	excel specific flag
	 *
	 */
	private boolean				freezeHeadline = false;
	
	private TableExportDebugger exportDebugger = new TableExportDebugger.DefaultTableExportDebuggerImpl();
	
	
	public static enum DataSourceType
	{
		// VTModelConfigBuilder
//		STATIC_VT_SOURCE,
//		// VTModelConfigBuilder
//		STATIC_XDEVTABLE_SOURCE,
		// VTConfigBuilder
		VT_SOURCE,
		// XdevTableConfigBuilder
		XDEVTABLE_SOURCE,
		// JTableViewConfigBuilder
		JTABLE_VIEW_SOURCE
	}
	
	
	
	/**
	 * Specifies an output format to control the table export.
	 */
	public static enum OutputFormat
	{
		PDF("pdf"), HTML("html"), XLS("xls"), XLSX("xlsx"), RTF("rtf"), CSV("csv"), XML("xml");
		
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
		XdevFileChooser chooser = XdevFileChooserFactory.getFileChooser("exportCreationFileChooser",suffix!=null ? new XdevFileFilter(suffix,suffix) : null);
		return chooser.showSaveDialog();	
	}
	
	public XdevFile showFileChooser(final String suffix)
	{
		XdevFileChooser chooser = XdevFileChooserFactory.getFileChooser("exportCreationFileChooser",suffix!=null ? new XdevFileFilter(suffix,suffix) : null);
		return chooser.showSaveDialog();	
	}
	
	public static enum Target
	{
		PREVIEW, PRINT, FILE, STREAM
	}
	
	
	/**
	 * 
	 * @param args
	 */
	public ExportCreation(Object... args)
	{
		super(args);
	}
	
	
	public void setDataSourceType(DataSourceType dataSourceType)
	{
		this.dataSourceType = dataSourceType;
	}
	
	
	public DataSourceType getDataSourceType()
	{
		return dataSourceType;
	}
	
	
	public void setVirtualTable(VirtualTable virtualTable)
	{
		this.virtualTable = virtualTable;
	}
	
	
	public VirtualTable getVirtualTable()
	{
		return virtualTable;
	}
	
	
	public void setJTable(JTable jTable)
	{
		this.jTable = jTable;
	}
	
	
	public JTable getJTable()
	{
		return jTable;
	}
	
	
	public void setXdevTable(VirtualTableEditor xdevTable)
	{
		this.xdevTable = xdevTable;
	}
	
	
	public VirtualTableEditor getXdevTable()
	{
		return xdevTable;
	}
	
	
	public void addColumn(final String columnName)
	{
		final TemplateColumn newColumn = new TemplateColumn(columnName);
		decorateColumn(newColumn);
		this.templateConfig.addColumn(newColumn);
	}
	
	private void decorateColumn(final TemplateColumn newColumn)
	{
		
		if(this.defaultHeaderStyle != null)
		{
			newColumn.getHeaderColumn().setStyle(this.defaultHeaderStyle.clone());
		}
		if(this.defaultContentStyle != null)
		{
			newColumn.getContentColumn().setStyle(this.defaultContentStyle.clone());
		}
	}
	
	
	public void setDefaultHeaderStyle(ColumnStyle defaultHeaderStyle)
	{
		this.defaultHeaderStyle = defaultHeaderStyle;
	}
	public void setDefaultContentStyle(ColumnStyle defaultContentStyle)
	{
		this.defaultContentStyle = defaultContentStyle;
	}
	
	
	public void setColumnWidth(final String columnName, final int width)
	{
		this.templateConfig.getTemplateColumn(columnName).setWidth(width);
	}
	
	
	// //////////////////////////////////////////////////////////////
	// //////// HEADER STYLE METHOD ///////////////////////////
	// ////////////////////////////////////////////////////////////
	
	public void setHeaderStyleForColumn(final String columnName, final ColumnStyle style)
	{
		this.getHeaderColumn(columnName).setStyle(style);
	}
	
	
	public void setHeaderCaptionForColumn(final String columnName, final String columnCaption)
	{
		this.getHeaderColumn(columnName).setProperty(columnCaption);
	}
	
	
	/**
	 * Sets the font of the specified {@link HeaderColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param font
	 *            the new {@link Font}
	 * 
	 */
	public void setHeaderStyleFontProperty(final String columnName, final Font font)
	{
		final ColumnStyle style = this.getHeaderColumn(columnName).getStyle();
		style.setFont(font);
	}
	
	
	/**
	 * Sets the foreground color of the specified {@link HeaderColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param foreground
	 *            the {@link Color} of the foreground
	 * 
	 */
	public void setHeaderStyleForegroundProperty(final String columnName, final Color foreground)
	{
		final ColumnStyle style = this.getHeaderColumn(columnName).getStyle();
		style.setForeground(foreground);
	}
	
	
	/**
	 * Sets the background color of the specified {@link HeaderColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param background
	 *            the {@link Color} of the background
	 * 
	 */
	public void setHeaderStyleBackgroundProperty(final String columnName, final Color background)
	{
		final ColumnStyle style = this.getHeaderColumn(columnName).getStyle();
		style.setBackground(background);
	}
	
	
	/**
	 * Sets the alignment of the specified {@link HeaderColumn} content.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param horizontalAlignment
	 *            the {@link ColumnAlignment} of the content
	 * 
	 */
	public void setHeaderStyleAlignmentProperty(final String columnName,
			final ColumnAlignment horizontalAlignment)
	{
		final ColumnStyle style = this.getHeaderColumn(columnName).getStyle();
		style.setHorizontalAlignment(horizontalAlignment);
	}
	
	
	/**
	 * Sets the border of the specified {@link HeaderColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param colBorder
	 *            the {@link ColumnBorder}
	 * 
	 */
	public void setHeaderStyleBorderProperty(final String columnName, final ColumnBorder colBorder)
	{
		final ColumnStyle style = this.getHeaderColumn(columnName).getStyle();
		style.setColBorder(colBorder);
	}
	
	
	// //////////////////////////////////////////////////////////////
	// //////// CONTENT STYLE METHOD ///////////////////////////
	// ////////////////////////////////////////////////////////////
	
	public void setFieldNameForColumn(final String columnName, final String fieldName)
	{
		this.getContentColumn(columnName).setFieldName(fieldName);
	}
	
	
	public void setFieldClassForColumn(final String columnName, final DataType dataType)
	{
		this.getContentColumn(columnName).setColumnValueClass(dataType.getJavaClass());
	}
	
	
	public void setPatternForColumn(final String columnName, final String pattern)
	{
		this.getContentColumn(columnName).setProperty(pattern);
	}
	
	
	/**
	 * Sets the font of the specified {@link ContentColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param font
	 *            the new {@link Font}
	 * 
	 */
	public void setContentStyleFontProperty(final String columnName, final Font font)
	{
		final ColumnStyle style = this.getContentColumn(columnName).getStyle();
		style.setFont(font);
	}
	
	
	/**
	 * Sets the foreground color of the specified {@link ContentColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param foreground
	 *            the {@link Color} of the foreground
	 * 
	 */
	public void setContentStyleForegroundProperty(final String columnName, final Color foreground)
	{
		final ColumnStyle style = this.getContentColumn(columnName).getStyle();
		style.setForeground(foreground);
	}
	
	
	/**
	 * Sets the background color of the specified {@link ContentColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param background
	 *            the {@link Color} of the background
	 * 
	 */
	public void setContentStyleBackgroundProperty(final String columnName, final Color background)
	{
		final ColumnStyle style = this.getContentColumn(columnName).getStyle();
		style.setBackground(background);
	}
	
	
	/**
	 * Sets the alignment of the specified {@link ContentColumn} content.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param horizontalAlignment
	 *            the {@link ColumnAlignment} of the content
	 * 
	 */
	public void setContentStyleAlignmentProperty(final String columnName,
			final ColumnAlignment horizontalAlignment)
	{
		final ColumnStyle style = this.getContentColumn(columnName).getStyle();
		style.setHorizontalAlignment(horizontalAlignment);
	}
	
	
	/**
	 * Sets the border of the specified {@link ContentColumn}.
	 * 
	 * 
	 * @param columnName
	 *            the column name
	 * @param colBorder
	 *            the {@link ColumnBorder}
	 * 
	 */
	public void setContentStyleBorderProperty(final String columnName, final ColumnBorder colBorder)
	{
		final ColumnStyle style = this.getContentColumn(columnName).getStyle();
		style.setColBorder(colBorder);
	}
	
	
	public void setContentStyleForColumn(final String columnName, final ColumnStyle style)
	{
		this.getContentColumn(columnName).setStyle(style);
	}
	
	public ColumnStyle getDefaultContentStyle()
	{
		return defaultContentStyle;
	}
	public ColumnStyle getDefaultHeaderStyle()
	{
		return defaultHeaderStyle;
	}
	
	private HeaderColumn getHeaderColumn(final String columnName)
	{
		return this.templateConfig.getTemplateColumn(columnName).getHeaderColumn();
	}
	
	
	private ContentColumn getContentColumn(final String columnName)
	{
		return this.templateConfig.getTemplateColumn(columnName).getContentColumn();
	}
	
	
	// //////////////////////////////////////////////////////////////
	// //////// PRINT METHOD ///////////////////////////
	// ////////////////////////////////////////////////////////////
	
	public void setDisplayPageDialogOnlyOnce(boolean displayPageDialogOnlyOnce)
	{
		this.displayPageDialogOnlyOnce = displayPageDialogOnlyOnce;
	}
	
	
	public void setDisplayPrintDialogOnlyOnce(boolean displayPrintDialogOnlyOnce)
	{
		this.displayPrintDialogOnlyOnce = displayPrintDialogOnlyOnce;
	}
	
	
	public void setDisplayPageDialog(boolean displayPageDialog)
	{
		this.displayPageDialog = displayPageDialog;
	}
	
	
	public void setDisplayPrintDialog(boolean displayPrintDialog)
	{
		this.displayPrintDialog = displayPrintDialog;
	}
	
	
	public boolean getDisplayPageDialogOnlyOnce()
	{
		return displayPageDialogOnlyOnce;
	}
	
	
	public boolean getDisplayPageDialog()
	{
		return displayPageDialog;
	}
	
	
	public boolean getDisplayPrintDialog()
	{
		return displayPrintDialog;
	}
	
	
	public boolean getDisplayPrintDialogOnlyOnce()
	{
		return displayPrintDialogOnlyOnce;
	}
	
	
	
	
	// //////////////////////////////////////////////////////////////
	// //////// OUTPUT METHOD ///////////////////////////
	// ////////////////////////////////////////////////////////////
	
	public void setOutputFormat(OutputFormat outputFormat)
	{
		this.outputFormat = outputFormat;
	}
	

	
	
	public void setTarget(Target target)
	{
		this.target = target;
	}
	
	
	public File getFile()
	{
		return file;
	}
	
	
	public void setFile(File file)
	{
		this.file = file;
	}
	
	
	public void setOutputStream(OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}
	
	public void setFreezeHeadline(boolean freezeHeadline)
	{
		this.freezeHeadline = freezeHeadline;
	}
	
	
	public void setOpenFileAfterExport(boolean openFile)
	{
		this.openFileAfterExport = openFile;
	}
	
	
	public Target getTarget()
	{
		return target;
	}
	
	
	public OutputFormat getOutputFormat()
	{
		return outputFormat;
	}
	
	
	public boolean isOpenFileAfterExport()
	{
		return openFileAfterExport;
	}
	
	
	public OutputStream getOutputStream()
	{
		return outputStream;
	}
	
	/**
	 * @return the freezeHeadline
	 */
	public boolean isFreezeHeadline()
	{
		return freezeHeadline;
	}
	
	
	@Override
	public void execute() throws ExportException
	{
		verifyParameter();
		try
		{
			final JasperReport jReport = createReport();
			
			if(this.exportDebugger.isDebugModeOn())
			{
				this.exportDebugger.exportJasperReportToJrxml(jReport);
			}
			final ReportExporter exporter = new ReportExporter(jReport,createDataSource(),
					createWriter());
			exporter.export();
			
			if(target == Target.FILE && isOpenFileAfterExport())
			{
				DesktopUtils.open(this.getFile());
			}
		}
		catch(Exception e)
		{
			throw new ExportException(e);
		}
	}
	
	
	
	/**
	 * Create the suitable {@link ExportConfigBuilder} and prepare the runtime
	 * config builder.
	 * 
	 * @return
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	private <T extends JTable & VirtualTableEditor>ExportConfigBuilder  createRuntimeConfigBuilder()
	{
		ExportConfigBuilder configBuilder = null;
		
		switch(this.getDataSourceType())
		{
			
//			case STATIC_XDEVTABLE_SOURCE:

//			break;
//			case STATIC_VT_SOURCE:
			case VT_SOURCE:
				configBuilder = new VTConfigBuilder(getVirtualTable());
			break;
			
			case XDEVTABLE_SOURCE:
				configBuilder = new XdevTableConfigBuilder((T)getXdevTable());
			break;
			
			case JTABLE_VIEW_SOURCE:
				configBuilder = new JTableViewConfigBuilder(getJTable());
			break;
			
			default:
			break;
		}
		
		return configBuilder;
	}
	
	
	/**
	 * The RuntimeConfigBuilder is prepared with the predefined header and
	 * columns.
	 * 
	 * @param runtimeBuilder
	 */
	private void prepareRuntimeConfigBuilder(final ExportConfigBuilder runtimeBuilder)
	{
		final TemplateConfig draftConfig = this.templateConfig;
		
		final HashMap<String, TemplateColumn> predefinedTemplateColumns = new HashMap<String, TemplateColumn>();
		
		for(TemplateColumn column : draftConfig.getColumns())
		{
			predefinedTemplateColumns.put(column.getColumnName(),column);
		}
		
		runtimeBuilder.setPredefinedColumnMap(predefinedTemplateColumns);		
	}
	
	
	private void verifyParameter()
	{
		if(dataSourceType == null)
		{
			CommandException.throwMissingParameter("dataSourceType");
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
			default:
			break;
		}
	}
	
	
	private JasperReport createReport() throws ExportException
	{
		final ExportConfigBuilder configBuilder = createRuntimeConfigBuilder();
		
		// builder is called when a dynamic ds is
		if(configBuilder != null)
		{
			prepareRuntimeConfigBuilder(configBuilder);
			// set header style for not mapped columns
			configBuilder.setDefaultHeaderColumnStyle(getDefaultHeaderStyle());
			configBuilder.setDefaultContentColumnStyle(getDefaultContentStyle());
			
			this.templateConfig = configBuilder.createConfig();
		}
		
		final ReportBuilder reportBuilder = new ReportBuilder(this.templateConfig);
		
		return reportBuilder.assembleReport();
	}
	
	
	@SuppressWarnings({"unchecked","rawtypes"})
	private JRRewindableDataSource createDataSource()
	{
		JRRewindableDataSource dataSource = null;
		switch(this.getDataSourceType())
		{
//			case STATIC_VT_SOURCE:
			case VT_SOURCE:
				dataSource = new JRVirtualTableDataSource(getVirtualTable());
			break;
			
//			case STATIC_XDEVTABLE_SOURCE:
			case XDEVTABLE_SOURCE:
				dataSource = new XdevTableDataSource((JTable)getXdevTable());
			break;
			
			case JTABLE_VIEW_SOURCE:
				dataSource = new XdevJTableRendererDataSource(getJTable());
			break;
			
			default:
			break;
		}
		
		return dataSource;
	}
	
	
	private ExportWriter createWriter()
	{
		ExportWriter writer = null;
		
		switch(target)
		{
			case PREVIEW:
				writer = new ReportToPreviewWriter();
			break;
			
			case PRINT:
				writer = new PrintWriter(this.displayPageDialog,this.displayPageDialogOnlyOnce,
						this.displayPrintDialog,this.displayPrintDialogOnlyOnce);
			break;
			
			case FILE:
				
				switch(this.getOutputFormat())
				{
					case XLS:
						writer = new XlsToFileWriter(this.getFile(),
								this.templateConfig.getSheetName(),isFreezeHeadline());
					break;
					case XLSX:
						writer = new XlsxToFileWriter(this.getFile(),
								this.templateConfig.getSheetName(),isFreezeHeadline());
					break;
					case PDF:
						writer = new PdfToFileWriter(this.getFile());
					break;
					case HTML:
						writer = new HtmlToFileWriter(this.getFile());
					break;					
					case CSV:
						writer = new CSVToFileWriter(this.getFile());
					break;
					
					case RTF:
						writer = new RtfToFileWriter(this.getFile());
					break;
					
					case XML:
						writer = new XMLToFileWriter(this.getFile());
					break;
					
					default:
					break;
				}
			
			break;
			case STREAM:
				switch(this.getOutputFormat())
				{
					case XLS:
						writer = new XlsToOutputStreamWriter(this.getOutputStream(),
								this.templateConfig.getSheetName(),isFreezeHeadline());
					break;
					case XLSX:
						writer = new XlsxToOutputStreamWriter(this.getOutputStream(),
								this.templateConfig.getSheetName(),isFreezeHeadline());
					break;
					case PDF:
						writer = new PdfToOutputStreamWriter(this.getOutputStream());
					break;
					case HTML:
						writer = new HtmlToOutputStreamWriter(this.getOutputStream());
					break;
					
					case CSV:
						writer = new CSVToOutputStreamWriter(this.getOutputStream());
					break;
					
					case RTF:
						writer = new RtfToOutputStreamWriter(this.getOutputStream());
					break;
					
					case XML:
						writer = new XMLToOutputStreamWriter(this.getOutputStream());
					break;
					
					default:
					break;
				}
			break;
		}
		
		return writer;
	}
	
	
	public void setExportDebugger(TableExportDebugger exportDebugger)
	{
		this.exportDebugger = exportDebugger;
	}
}
