package com.rssl.phizic.web.pfr;

import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author gulov
 * @ created 01.03.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRTemplateForm extends EditFormBase
{
	private FormFile xsltLoadable;
	private FormFile xsltUnloadable;
	private FormFile xsdLoadable;
	private FormFile xsdUnloadable;

	public FormFile getXsltLoadable()
	{
		return xsltLoadable;
	}

	public void setXsltLoadable(FormFile xsltLoadable)
	{
		this.xsltLoadable = xsltLoadable;
	}

	public FormFile getXsltUnloadable()
	{
		return xsltUnloadable;
	}

	public void setXsltUnloadable(FormFile xsltUnloadable)
	{
		this.xsltUnloadable = xsltUnloadable;
	}

	public FormFile getXsdLoadable()
	{
		return xsdLoadable;
	}

	public void setXsdLoadable(FormFile xsdLoadable)
	{
		this.xsdLoadable = xsdLoadable;
	}

	public FormFile getXsdUnloadable()
	{
		return xsdUnloadable;
	}

	public void setXsdUnloadable(FormFile xsdUnloadable)
	{
		this.xsdUnloadable = xsdUnloadable;
	}
}
