package com.rssl.phizic.web.modulus;

import com.rssl.phizic.config.build.WebModule;

/**
 * @author Erkin
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class WebModuleRequestURL
{
	private final WebModule module;

	private final String folder;

	private final String action;

	public WebModuleRequestURL(WebModule module, String folder, String action)
	{
		this.module = module;
		this.folder = folder;
		this.action = action;
	}

	public WebModule getModule()
	{
		return module;
	}

	public String getFolder()
	{
		return folder;
	}

	public String getAction()
	{
		return action;
	}

	public String getPath()
	{
		return folder + action;
	}

	public String toString()
	{
		return folder + action;
	}
}
