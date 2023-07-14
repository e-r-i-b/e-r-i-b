package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author usachev
 * @ created 17.08.15
 * @ $Author$
 * @ $Revision$
 * ’ендлер предназначен дл€ проверки доступности внешних систем при открытии вклада. ≈сли системы не доступны, то проставл€етс€ соответствующий признак в документ.
 */
public class OfflineDelayedAccountOpeningClaimHandler extends OfflineDelayedHandlerBase
{

	@Override
	protected void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Ќекорректный тип документа. ќжидаетс€ AccountOpeningClaim.");
		}
		AccountOpeningClaim claim = (AccountOpeningClaim)document;
		if (checkDocumentOffice(claim))
		{
			return;
		}

		ExternalResourceLink link = document.getChargeOffResourceLink();
		if (link != null)
		{
			if (link instanceof CardLink)
			{
				AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
				checkSystemByUUID(config.getCardWayAdapterCode(), claim);
			}
			else
			{
				checkSystemByUUID(ESBHelper.parseSystemId(link.getExternalId()), claim);
			}
		}
	}
}
