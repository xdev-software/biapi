package xdev.tableexport.datasource;

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


/**
 * 
 * ValueConverter to convert an input type {@code I} into a the output type
 * {@code O}.
 * 
 * @author XDEV Software(FHAE)
 * 
 * @param <I>
 *            the input type
 * @param <O>
 *            the output type
 */
public interface ValueConverter<I, O>
{
	/**
	 * Convert the given input type {@code I} into the output type {@code O}.
	 * 
	 * @param value
	 *            the input type instance to be mapped to an output type
	 *            instance.
	 * @return the output type instance for the passed input type instance.
	 * 
	 */
	public O getValue(I value);
}
