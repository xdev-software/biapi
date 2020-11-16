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


import xdev.ui.text.TextFormat;
import xdev.vt.VirtualTableColumn;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;


public class VirtualTableColumnPatternConverter implements PatternConverter<VirtualTableColumn<?>>
{
	@Override
	public String getExportPattern(VirtualTableColumn<?> obj)
	{
		TextFormat t = obj.getTextFormat();
		Format format = t.createFormat(t.getType());
		
		if(format instanceof SimpleDateFormat)
		{
			return ((SimpleDateFormat)format).toPattern();
		}
		else if(format instanceof DecimalFormat)
		{
			String pattern = ((DecimalFormat)format).toPattern();
			
			if(pattern.contains("\u00A4"))
			{
				String symbol = ((DecimalFormat)format).getCurrency().getSymbol();
				pattern = pattern.replace("\u00A4",symbol);
			}
			return pattern;
		}
		return null;
	}
	
}
