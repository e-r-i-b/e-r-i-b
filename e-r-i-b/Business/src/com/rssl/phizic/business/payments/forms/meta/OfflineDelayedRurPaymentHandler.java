package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizicgate.manager.config.AdaptersConfig;

import java.util.List;

/**
 * Хэндлер для определения недоступности внешних систем при переводе физическому лицу
 * @author Pankin
 * @ created 07.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedRurPaymentHandler extends OfflineDelayedHandlerBase
{
	private static final String INACTIVE_EXTERNAL_SYSTEM_MESSAGE = "По техническим причинам операция " +
			"временно недоступна. Повторите попытку позже.";

	public void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof RurPayment))
			throw new DocumentException("Некорректный тип документа. Ожидается RurPayment.");

		RurPayment payment = (RurPayment) document;

		if (payment.getDestinationResourceType() == ResourceType.CARD || payment.getDestinationResourceType() == ResourceType.EXTERNAL_CARD)
		{
			// На карты частному лицу нельзя переводить при недоступности внешних систем
			if (checkResource(payment.getChargeOffResourceLink(), payment))
				throw new DocumentLogicException(INACTIVE_EXTERNAL_SYSTEM_MESSAGE);

			// проверяем карточную систему на недоступность (если нужно, проставляем оффлайновость)
			checkSystemByUUID(ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter().getUUID(), payment);
			return;
		}

		if (payment.getChargeOffResourceType() == ResourceType.ACCOUNT)
		{
			// Проверяем адаптер ЦОД, использующийся при прямой интеграции, на недоступность
			if (checkDocumentOffice(payment))
				return;

			// Проверяем счет списания на недоступность
			if (checkResource(payment.getChargeOffResourceLink(), payment))
				return;

			// Для длительных поручений нужна шина
			if (payment.isLongOffer() && checkESB(payment))
				return;
		}
		else if (payment.getChargeOffResourceType() == ResourceType.CARD)
		{
			// Проверяем шину
			if (checkESB(payment))
				return;
			// Проверяем way
			else if (checkResource(payment.getChargeOffResourceLink(), payment))
				return;
			// Проверяем вкладную внешнюю систему
			else if (checkAccountsExternalSystems(payment))
				return;
		}
	}

	private boolean checkAccountsExternalSystems(RurPayment payment) throws DocumentException
	{
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
		try
		{
			List<? extends ExternalSystem> accountsExternalSystems = externalSystemGateService.findByProduct(payment.getOffice(), BankProductType.Deposit);
			for (ExternalSystem accountsExternalSystem : accountsExternalSystems)
			{
				if (checkSystemByUUID(accountsExternalSystem.getUUID(), payment))
					return true;
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		return false;
	}
}
