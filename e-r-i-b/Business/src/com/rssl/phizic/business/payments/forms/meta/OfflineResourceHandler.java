package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * Хэндлер для проверки ресурса списания на активность внешней системы. Запрещает платежи в случае
 * технологического перерыва.
 * @author Pankin
 * @ created 20.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfflineResourceHandler extends BusinessDocumentHandlerBase
{
	private static final String DEFAULT_MESSAGE = "По техническим причинам Вы не можете совершить данную " +
			"операцию. Пожалуйста, повторите попытку позже.";
	private static final String CARD_MESSAGE = "По техническим причинам Вы не можете совершить данную " +
			"операцию с карты. Пожалуйста, повторите попытку позже или в качестве счета списания выберите вклад.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() +
					" (Ожидается AbstractPaymentDocument)");

		AbstractPaymentDocument paymentDocument = (AbstractPaymentDocument) document;
		if (checkResource(paymentDocument.getChargeOffResourceLink(), paymentDocument))
			throwException(paymentDocument);
	}

	private void throwException(AbstractPaymentDocument document) throws DocumentException, DocumentLogicException
	{
		ExternalResourceLink link = document.getChargeOffResourceLink();
		if (link instanceof CardLink)
			throw new DocumentLogicException(CARD_MESSAGE);
		else
			throw new DocumentLogicException(DEFAULT_MESSAGE);
	}

	private boolean checkResource(ExternalResourceLink link, AttributableBase document) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (link == null)
				return false;

			if (!ESBHelper.isESBSupported(link.getExternalId()))
				return false;

			if (link instanceof CardLink)
			{
				AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
				return checkSystemByUUID(config.getCardWayAdapterCode(), document);
			}
			else
			{
				return checkSystemByUUID(ESBHelper.parseSystemId(link.getExternalId()), document);
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	private boolean checkSystemByUUID(String UUID, AttributableBase document) throws DocumentException
	{
		try
		{
			ExternalSystemHelper.check(UUID);
		}
		catch (InactiveExternalSystemException ignored)
		{
			return true;
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		return false;
	}
}
