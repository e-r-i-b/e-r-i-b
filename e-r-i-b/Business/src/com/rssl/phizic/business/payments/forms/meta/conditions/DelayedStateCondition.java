package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.DefaultClaim;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ReIssueCardClaim;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author Gainanov
 * @ created 01.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class DelayedStateCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		try
		{
			GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
			ReceptionTimeService receptionTimeService = new ReceptionTimeService();

			BusinessDocument document = (BusinessDocument) stateObject;
			if (document instanceof DefaultClaim || document instanceof ReIssueCardClaim || document instanceof CardBlockingClaim)
			{
				return false;
			}

			GateDocument doc = ((ConvertableToGateDocument) document).asGateDocument();

			//биллинговые автоплатежи идут без времени приема документов
			if (ESBHelper.isAutoSubscriptionPayment(doc))
			{
				return false;
			}

			boolean isAccepted = false;
			if (document instanceof AbstractAccountsTransfer)
			{
				AbstractAccountsTransfer payment = (AbstractAccountsTransfer) document;
				ResourceType chargeOffResourceType = payment.getChargeOffResourceType();
				ResourceType destinationResourceType = payment.getDestinationResourceType();
				isAccepted = ApplicationUtil.isScheduler() && ResourceType.CARD.equals(chargeOffResourceType) &&
						(ResourceType.CARD.equals(destinationResourceType) || ResourceType.EXTERNAL_CARD.equals(destinationResourceType));
			}

			// Если сейчас рабочее время (в т.ч. не выходной) или это шедулер и перево карта-карта, то не интересно
			if (receptionTimeService.getWorkTimeInfo(document).isWorkTimeNow() == TimeMatching.RIGHT_NOW || isAccepted)
				return false;
				//Тогда смотрим поддерживает ли шлюз отсылку документов в любое время
			return !gateInfoService.isDelayedPaymentNeedSend(doc.getOffice());
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
