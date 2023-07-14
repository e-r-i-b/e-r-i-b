package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.NotConvertibleToGateBusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;

/**
 * Хэндлер для определения недоступности внешних систем, при оплате аэрофлотом
 * @author basharin
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedExternalProviderPaymentHandler extends OfflineDelayedHandlerBase
{
	@Override
	protected void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (!(document instanceof ConvertableToGateDocument) || ((ConvertableToGateDocument) document).asGateDocument().getType() != CardPaymentSystemPayment.class)
				throw new DocumentException("Ожидается CardPaymentSystemPayment");
		}
		catch (NotConvertibleToGateBusinessException e)
		{
			throw new DocumentException(e);
		}

		try
		{
			// проверяем карточную систему на недоступность (если нужно, проставляем оффлайновость)
			checkSystemByUUID(ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter().getUUID(), (AttributableBase)document);
		}
		catch (InactiveExternalSystemException e)
		{
			if (e.getToDate() != null)
			{
				throw new InactiveExternalSystemException("Документ нельзя подтвердить до " + DateHelper.formatDateDependsOnSysDate(e.getToDate().getTime()));
			}
			throw e;
		}
		return;
	}
}
