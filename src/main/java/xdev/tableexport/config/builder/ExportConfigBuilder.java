package xdev.tableexport.config.builder;

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


import xdev.tableexport.config.ColumnStyle;
import xdev.tableexport.config.ContentColumn;
import xdev.tableexport.config.HeaderColumn;
import xdev.tableexport.config.TemplateColumn;
import xdev.tableexport.config.TemplateConfig;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperReport;


/**
 * Builder used to construct a {@link TemplateConfig}. A {@link TemplateConfig}
 * is a pre-configuration object of a {@link JasperReport}.
 * 
 * @author XDEV Software(FHAE)
 * 
 */
public interface ExportConfigBuilder
{
	/**
	 * Create a default {@link TemplateConfig}.
	 * 
	 * @return the {@link TemplateConfig}
	 */
	public TemplateConfig createConfig();
	
	/**
	 * Map a default {@link HeaderColumn} to the caption string ( identifier)
	 * 
	 * @param fieldnameToColumn
	 */
	public void setPredefinedColumnMap(HashMap<String, TemplateColumn> fieldnameToColumn);
	
	
	/**
	 * Set the default {@link ColumnStyle} for the {@link HeaderColumn}.
	 */
	public void setDefaultHeaderColumnStyle(final ColumnStyle style);
	
	
	/**
	 * Set the default {@link ColumnStyle} for the {@link ContentColumn}.
	 */
	public void setDefaultContentColumnStyle(final ColumnStyle style);
	
}
