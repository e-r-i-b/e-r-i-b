package com.rssl.phizic.web.cards.delivery;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.card.delivery.EditEmailDeliverySettingsOperation;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.web.actions.ActionMessageHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionForward;

/**
 * @author akrenev
 * @ created 13.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования управления оповещением
 */

public class EditMailDeliverySettingsAction extends EditPropertiesActionBase<EditEmailDeliverySettingsOperation>
{
	@Override
	protected EditEmailDeliverySettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditEmailDeliverySettingsOperation.class);
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation editOperation, EditFormBase frm) throws BusinessException
	{
		EditEmailDeliverySettingsOperation operation = (EditEmailDeliverySettingsOperation) editOperation;

		String useString = operation.getEntity().get(CardsConfig.SHOW_IS_AVAILABLE_EMAIL_REPORT_DELIVERY_MESSAGE_PROPERTY_KEY);
		boolean use = Boolean.parseBoolean(useString);
		String message = "Вы " + (use? "включили": "отключили") + " оповещение об отсутствии подписки по карте.";

		ActionMessageHelper.saveSessionMessage(currentRequest(), message);
		return super.createSaveActionForward(operation, frm);
	}
}
