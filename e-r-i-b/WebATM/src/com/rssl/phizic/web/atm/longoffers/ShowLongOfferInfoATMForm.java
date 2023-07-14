package com.rssl.phizic.web.atm.longoffers;

import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoForm;

/**
 * @author Pankin
 * @ created 29.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class ShowLongOfferInfoATMForm extends ShowLongOfferInfoForm
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

