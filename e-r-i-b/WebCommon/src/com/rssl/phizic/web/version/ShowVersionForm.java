package com.rssl.phizic.web.version;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author lukina
 * @ created 24.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class ShowVersionForm extends ActionFormBase
{
	private String version;       // версия
	private String revision;      // ревизия

	public String getVersion()
	{
		return version;
	}

	public String getRevision()
	{
		return revision;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public void setRevision(String revision)
	{
		this.revision = revision;
	}
}
