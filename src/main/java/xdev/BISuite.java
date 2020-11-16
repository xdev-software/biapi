package xdev;

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


import java.util.Map;

/**
 * <p>A {@link Extension} implementation which initialize needed classes for BI Suite.</p>
 * 
 * @author XDEV Software
 * @deprecated Will no longer be maintained and removed in the future
 */
@Deprecated
public class BISuite implements Extension
{
	/**
	 * Current version of the bi suite.
	 */
	public final static Version		VERSION					= new Version(5,0,3,0);
	
	/**
	 * X-API version required for the current version of the bi suite.
	 */
	private final static Version	REQUIRED_XAPI_VERSION	= new Version(5,0,0,0);
	
	public BISuite()
	{
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Map<String, String> args) throws ExtensionInitializationException
	{
		// Version check
		if(xdev.API.VERSION.isOlderThan(REQUIRED_XAPI_VERSION))
		{
			throw new ExtensionInitializationException("XAPI Version "
					+ REQUIRED_XAPI_VERSION.toScreen() + " or newer is required for the "
					+ toString() + ", current version: " + xdev.API.VERSION.toScreen());
		}
				
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return "XDEV BI Suite";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Version getVersion()
	{
		return VERSION;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getName() + " " + getVersion().toScreen();
	}
}
