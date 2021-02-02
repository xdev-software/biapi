package xdev.tableexport.utils;

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
import java.io.OutputStream;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;


/**
 * <p>
 * The {@code ExportUtils} class provides utility methods for preparing the {@link JRAbstractExporter}
 * </p>
 * 
 * 
 * @author XDEV Software(FHAE)
 */
@SuppressWarnings("deprecation")
public class ExportUtils
{
	
	/**
	 * Prepare the {@code exporter} with all required {@link JRExporterParameter}.
	 * 
	 * @param exporter
	 * 
	 * @param jasperPrint An instance of the {@link JasperPrint} represents a report document that can be exported to other formats.
	 * 
	 * @param sheetName An string representing custom sheet name
	 * 
	 * @param stream the outputstream
	 * 
	 * @throws IllegalArgumentException
	 */
	public static void prepareExcelExporter(final JRXlsAbstractExporter exporter,
			final JasperPrint jasperPrint, final String sheetName, final Boolean freezeHeadline, final OutputStream stream)
			throws IllegalArgumentException
	{
		initDefaultParameter(exporter,jasperPrint,sheetName,freezeHeadline);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,stream);
	}
	

	/**
	 * Prepare the {@code exporter} with all required {@link JRExporterParameter}.
	 * 
	 * @param exporter
	 * 
	 * @param jasperPrint An instance of the {@link JasperPrint} represents a report document that can be exported to other formats.
	 * 
	 * @param sheetName An string representing custom sheet name
	 * 
	 * @param file the output file
	 * 
	 * @throws IllegalArgumentException
	 */
	public static void prepareExcelExporter(final JRXlsAbstractExporter exporter,
			final JasperPrint jasperPrint, final String sheetName, final Boolean freezeHeadline, final File file)
			throws IllegalArgumentException
	{
		initDefaultParameter(exporter,jasperPrint,sheetName,freezeHeadline);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE,file);
	}
	
	private static void initDefaultParameter(final JRXlsAbstractExporter exporter,
			final JasperPrint jasperPrint, final String sheetName, final Boolean freezeHeadline) throws IllegalArgumentException
	{
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS,Boolean.FALSE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
		
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BACKGROUND,
				Boolean.FALSE);
		exporter.setParameter(JRXlsAbstractExporterParameter.SHEET_NAMES,new String[]{sheetName});
		
		
		
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
				Boolean.TRUE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
				Boolean.TRUE);
		
		if(freezeHeadline)
		{
			final JRPropertiesUtil util = JRPropertiesUtil
					.getInstance(exporter.getJasperReportsContext());
			util.setProperty(JRXlsAbstractExporter.PROPERTY_FREEZE_ROW_EDGE,"2");
		}
	}
}
