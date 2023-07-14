package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.operations.ext.sbrf.payment.ListCategoryPaymentsOperation;
import com.rssl.phizic.web.common.client.ext.sbrf.payment.services.ListCategoryPaymentsAction;
import com.rssl.phizic.web.common.client.ext.sbrf.payment.services.ListCategoryPaymentsForm;
import org.apache.commons.collections.CollectionUtils;

/**
 * Список групп услуг или список услуг. Для старой иерархии услуг (< 11 релиз).
 * Используется в mAPI < 5.20
 * @author Dorzhinov
 * @ created 17.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCategoryPaymentsMobileAction extends ListCategoryPaymentsAction
{
	protected boolean isMobileApi()
	{
		return true;
	}

	protected boolean isInternetBank(){
		return false;
	}

	protected void updatePaymentServicesList(ListCategoryPaymentsOperation operation, ListCategoryPaymentsForm frm) throws Exception
	{
		super.updatePaymentServicesList(operation, frm);
		
		ListCategoryPaymentsMobileForm form = (ListCategoryPaymentsMobileForm) frm;

		if(!CollectionUtils.isEmpty(form.getGroupServices()))
			form.getList().addAll(form.getGroupServices());

		if (!CollectionUtils.isEmpty(form.getServices()))
			form.getList().addAll(form.getServices());
	}
}
