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

import xdev.tableexport.export.*;


/**
 * An {@code ExportWriterException} is a {@code ExportException}
 * thrown if an attempt to write the report fails.
 * 
 * @author XDEV Software(FHAE)
 */
public class ExportWriterException extends ExportException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3338632252117720093L;


	/**
	 * Constructs an {@code ExportWriterException} with the specified
	 * detail message and nested exception.
	 * 
	 * @param message the detail message
	 * @param cause the nested exception
	 */
	public ExportWriterException(String message, Throwable cause)
	{
		super(message,cause);
	}
	

	/**
	 * Constructs an {@code ExportWriterException} with the specified
	 * detail message.
	 * 
	 * @param message the detail message
	 */
	public ExportWriterException(String message)
	{
		super(message);
	}
	

	/**
	 * Constructs an {@code ExportWriterException} with the specified
	 *  nested exception.
	 * 
	 * @param cause the nested exception
	 */
	public ExportWriterException(Throwable cause)
	{
		super(cause);
	}
}
