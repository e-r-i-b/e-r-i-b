package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.widget.MobileBalanceWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

/**
 @author: Egorovaa
 @ created: 04.07.2012
 @ $Author$
 @ $Revision$
 */
public class MobileBalanceWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(MobileBalanceWidgetOperation.class);
		
	}

	@Override
	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		MobileBalanceWidgetOperation mobileBalanceWidgetOperation = (MobileBalanceWidgetOperation) operation;
		MobileBalanceWidgetForm frm = (MobileBalanceWidgetForm) form;
		frm.setBalance(mobileBalanceWidgetOperation.getMobileBalance());
		frm.setLowBalance(mobileBalanceWidgetOperation.isLowBalance(frm.getBalance()));
		frm.setPaymentUrl(mobileBalanceWidgetOperation.createPaymentUrl());
	}
}
