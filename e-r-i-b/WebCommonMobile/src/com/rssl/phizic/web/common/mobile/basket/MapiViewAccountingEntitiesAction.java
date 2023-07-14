package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.ViewPaymentsBasketOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author Balovtsev
 * @since 20.10.14.
 */
public class MapiViewAccountingEntitiesAction extends ViewActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewPaymentsBasketOperation operation = createOperation(ViewPaymentsBasketOperation.class);
		operation.initialize();
		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewPaymentsBasketOperation    o    = (ViewPaymentsBasketOperation) operation;
		MapiViewAccountingEntitiesForm form = (MapiViewAccountingEntitiesForm) frm;

		form.setAccountingEntities(o.getAccountingEntities());
		form.setServiceCategories(o.getServiceCategories());
	}
}
