package com.rssl.phizic.web.client.ext.sbrf.mobilebank.ermb;

import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.business.documents.BusinessDocumentBase;

import java.util.List;

/**
 @author: Egorovaa
 @ created: 16.10.2012
 @ $Author$
 @ $Revision$
 */
public class SmsHistoryForm extends ActionFormBase
{
	private List<BusinessDocumentBase> payments;

	public List<BusinessDocumentBase> getPayments()
	{
		return payments;
	}

	public void setPayments(List<BusinessDocumentBase> payments)
	{
		this.payments = payments;
	}
}
