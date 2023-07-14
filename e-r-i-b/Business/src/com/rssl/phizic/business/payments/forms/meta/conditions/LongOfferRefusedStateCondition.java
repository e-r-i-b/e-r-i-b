package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.payments.forms.meta.SetBusinessDocumentDateAction;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author: vagin
 * @ created: 11.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferRefusedStateCondition extends DelayedStateCondition
{
	private static final String REFUSE_CAUSE = "Клиенту отказано в исполнении операции, т.к. дата начала действия платежа, указанная клиентом, должна быть больше плановой даты обработки платежа в банке"; 

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{

		if (!(stateObject instanceof LongOffer))
			return false;

		AbstractLongOfferDocument longOffer = (AbstractLongOfferDocument) stateObject;
		if (!longOffer.isLongOffer())
			return false;

		boolean notInTime = false;
		//пересчитываем плановую дату исполнения документа.
		//В случае если подветржается платеж из АРМ сотрудника(данные клиентского платежа мы не должны меняеть)
		SetBusinessDocumentDateAction setBusinessDocumentDateAction = new SetBusinessDocumentDateAction();
		try
		{
			// Плановая дата исполнения  не может быть больше даты начала действия
			// Чистим время, тк дата исполнения и дата начала могут совпадать
			Calendar admissionDate  = DateHelper.clearTime(setBusinessDocumentDateAction.calculateDate(longOffer));
			Calendar startDate      = DateHelper.toCalendar(DateHelper.setTime(longOffer.getStartDate().getTime(), 0, 0, 0, 0));

			if(startDate.compareTo(admissionDate) < 0)
				notInTime = true;

			boolean res = super.accepted(stateObject, stateMachineEvent) && (stateObject instanceof LongOffer) && notInTime;
			if (res)
				stateMachineEvent.addMessage(REFUSE_CAUSE);
			return res;
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}
}
