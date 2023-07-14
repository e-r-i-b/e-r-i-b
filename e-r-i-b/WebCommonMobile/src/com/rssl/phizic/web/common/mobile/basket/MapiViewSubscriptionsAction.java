package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.ViewPaymentsBasketOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author Balovtsev
 * @since 13.10.14.
 */
public class MapiViewSubscriptionsAction extends ViewActionBase
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
		ViewPaymentsBasketOperation o  = (ViewPaymentsBasketOperation) operation;
		MapiViewSubscriptionsForm form = (MapiViewSubscriptionsForm) frm;

		form.setAutoSubscriptions(o.getAutoSubscriptions());
		form.setActiveSubscriptions(o.getActiveSubscriptions());
		form.setStoppedSubscriptions(o.getStoppedSubscriptions());
		form.setRecommendedSubscriptions(o.getRecommendedSubscriptions());
		form.setImageIds(o.getImageIds());
	}
}
