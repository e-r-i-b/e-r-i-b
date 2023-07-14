package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.web.component.WidgetForm;

import java.util.List;

/** Форма виджета "Последние платежи"
 * @ author Rtischeva
 * @ created 18.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LastPaymentsWidgetForm extends WidgetForm
{
	//последние платежи
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
