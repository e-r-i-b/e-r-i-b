package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.RedirectDocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.payments.forms.meta.conditions.OfflineDelayedCondition;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author mihaylov
 * @ created 22.03.2010
 * @ $Author$
 * @ $Revision$
 */

public class CheckCommissionSumAction extends BusinessDocumentHandlerBase
{
	private static final String MESSAGE = "Сумма комиссии изменилась.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
		{
			throw new DocumentException("Action поддерживает работу только с платежами");
		}

		try
		{
			if (new OfflineDelayedCondition().accepted(document, stateMachineEvent))
				return;
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Ошибка при проверке документа на недоступность внешней системы", e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e.getMessage(), e);
		}

		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		BackRefCommissionTBSettingService commissionSettingService = GateSingleton.getFactory().service(BackRefCommissionTBSettingService.class);
		ConvertableToGateDocument convertable = (ConvertableToGateDocument) document;
		try
		{
			GateDocument gateDocument = convertable.asGateDocument();
			//если подразделение поддерживает онлайн рассчет комиссии в цод для данного типа документа, механизм другой. CommissionCalculator не используем.
			if(commissionSettingService.isCalcCommissionSupport(gateDocument))
		        return;
			Money oldCommision = gateDocument.getCommission();
			documentService.calcCommission(gateDocument);
			Money newCommision = gateDocument.getCommission();
			if (oldCommision != null && oldCommision.compareTo(newCommision) != 0)
			{
				processFailCheck(newCommision, oldCommision, (AbstractPaymentDocument) document, stateMachineEvent);
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	protected void processFailCheck(Money newCommision, Money oldCommision, AbstractPaymentDocument document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		throw new RedirectDocumentLogicException(MESSAGE);
	}
}
