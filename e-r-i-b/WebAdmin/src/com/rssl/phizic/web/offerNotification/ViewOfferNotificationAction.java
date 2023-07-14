package com.rssl.phizic.web.offerNotification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.offerNotification.EditOfferNotificationOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * Предварительный просмотр уведомления о предодобреном предложении
 * @author lukina
 * @ created 04.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class ViewOfferNotificationAction  extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditOfferNotificationOperation editOperation = createOperation(EditOfferNotificationOperation.class, "OfferNotificationManagment");
		Long id = frm.getId();
		if (id != null && id != 0)
			editOperation.initialize(id);
		return editOperation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewOfferNotificationForm form = (ViewOfferNotificationForm)frm;
		form.setPersonalOfferNotification((PersonalOfferNotification)operation.getEntity());
	}
}

