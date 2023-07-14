package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.widget.CurrencyRateWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

/**
 * @author gulov
 * @ created 18.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class CurrencyRateWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(CurrencyRateWidgetOperation.class);
	}

	@Override
	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		CurrencyRateWidgetForm frm = (CurrencyRateWidgetForm) form;
		CurrencyRateWidgetOperation op = (CurrencyRateWidgetOperation) operation;

		frm.setCurrencyCodes(op.getAllCurrencyCodes());
		frm.setImaCodes(op.getAllImaCodes());
	}
}
