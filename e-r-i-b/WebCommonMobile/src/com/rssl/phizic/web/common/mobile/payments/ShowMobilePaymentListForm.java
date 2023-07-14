package com.rssl.phizic.web.common.mobile.payments;

import com.rssl.phizic.web.actions.payments.forms.ViewDocumentListFormBase;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.common.forms.Form;

/**
 * @author Rydvanskiy
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowMobilePaymentListForm extends ViewDocumentListFormBase
{
	private String from;
	private String to;
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		return FilterPeriodHelper.createFilterPeriodFormBuilder(FilterPeriodHelper.PERIOD_TYPE_MONTH).build();
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}
}
