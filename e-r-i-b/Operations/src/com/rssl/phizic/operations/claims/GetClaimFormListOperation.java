package com.rssl.phizic.operations.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kosyakov
 * @ created 16.03.2007
 * @ $Author: krenev $
 * @ $Revision: 12319 $
 */
public class GetClaimFormListOperation extends OperationBase
{
	PaymentFormService formService;
	public GetClaimFormListOperation()
	{
		formService = new PaymentFormService();
	}

	public List<MetadataBean> getFormList() throws Exception
	{
		List<MetadataBean> paymentFormList = new ArrayList<MetadataBean>();

		//TODO: придумать нормальный способ получения списка форм заявок
		try
		{
			paymentFormList = formService.getAllForms();
		}
		catch (BusinessException e)
		{
			throw new Exception("ошибка при получении списка заявок", e);
		}
		return paymentFormList;
	}
}
