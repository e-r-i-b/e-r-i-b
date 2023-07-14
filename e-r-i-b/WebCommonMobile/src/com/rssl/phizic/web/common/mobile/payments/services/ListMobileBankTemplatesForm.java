package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentTemplateLink;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author erkin
 * @ created 11.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListMobileBankTemplatesForm extends ActionFormBase
{
	private Map<CardLink, List<PaymentTemplateLink>> cardPaymentTemplateLinks;

	///////////////////////////////////////////////////////////////////////////

	public Map<CardLink, List<PaymentTemplateLink>> getCardPaymentTemplateLinks()
	{
		return cardPaymentTemplateLinks;
	}

	public void setCardPaymentTemplateLinks(Map<CardLink, List<PaymentTemplateLink>> links)
	{
		this.cardPaymentTemplateLinks = links;
	}
}