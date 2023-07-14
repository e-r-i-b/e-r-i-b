package com.rssl.phizic.web.client.ext.sbrf.payment.template;

import com.rssl.phizic.web.actions.payments.CatalogActionBase;
import com.rssl.phizic.web.actions.payments.IndexAction;
import com.rssl.phizic.web.actions.payments.IndexForm;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author khudyakov
 * @ created 05.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class SelectCategoryAction extends CatalogActionBase
{
	private static final List<String> paymentList = new ArrayList<String>();
	//список платежей, которые будем искать в поиске
	static
	{
		paymentList.add("ConvertCurrencyPayment");
		paymentList.add("IMAPayment");
		paymentList.add("InternalPayment");
		paymentList.add("JurPayment");
		paymentList.add("LoanPayment");
		paymentList.add("RurPayment");

	}
	protected boolean onlyTemplateSupportedProvider()
	{
		return true;
	}

	protected List<String> getPaymenyList()
	{
		return Collections.unmodifiableList(paymentList);
	}

	protected boolean needSearchPayments()
	{
		return true;
	}

	protected boolean isInternetBank()
	{
		return true;
	}

	protected String getChanel()
	{
		return "WEB";
	}

	protected String[] getFunctionality(IndexForm frm)
	{
		return new String[]{"TEMPLATES"};
	}
}
