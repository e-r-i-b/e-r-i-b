package com.rssl.phizic.web.common.socialApi.longoffers;

import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoForm;

/**
 * @author Dorzhinov
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferInfoMobileForm extends ShowLongOfferInfoForm
{
	private ExternalResourceLink payerResourceLink;

	public ExternalResourceLink getPayerResourceLink()
	{
		return payerResourceLink;
	}

	public void setPayerResourceLink(ExternalResourceLink payerResourceLink)
	{
		this.payerResourceLink = payerResourceLink;
	}
}
