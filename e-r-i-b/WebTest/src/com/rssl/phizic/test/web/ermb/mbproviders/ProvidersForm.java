package com.rssl.phizic.test.web.ermb.mbproviders;

import org.apache.struts.action.ActionForm;

/**
 * @author EgorovaA
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ProvidersForm extends ActionForm
{
	private String serviceProvidersInfo;
	private boolean submit = false;

	public String getServiceProvidersInfo()
	{
		return serviceProvidersInfo;
	}

	public void setServiceProvidersInfo(String serviceProvidersInfo)
	{
		this.serviceProvidersInfo = serviceProvidersInfo;
	}

	public boolean getSubmit()
	{
		return submit;
	}

	public void setSubmit(boolean submit)
	{
		this.submit = submit;
	}
}
