package com.rssl.phizic.config.build;

/**
 * @author Erkin
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕараметры прив€зки веб-модул€ к веб-приложению
 */
public class WebModuleBinding
{
	private final WebModule webModule;

	private final String urlFolder;

	WebModuleBinding(WebModule webModule, String urlFolder)
	{
		this.webModule = webModule;
		this.urlFolder = urlFolder;
	}

	public WebModule getWebModule()
	{
		return webModule;
	}

	public String getUrlFolder()
	{
		return urlFolder;
	}
}
