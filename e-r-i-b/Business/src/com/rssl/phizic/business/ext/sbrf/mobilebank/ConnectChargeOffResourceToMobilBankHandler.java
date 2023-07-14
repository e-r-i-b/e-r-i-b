package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;

/**
 * Установка флажка на подключение к услуге "Мобильный банк" ресурса списания
 * @author niculichev
 * @ created 22.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConnectChargeOffResourceToMobilBankHandler extends BusinessDocumentHandlerBase<AbstractLongOfferDocument>
{
	public void process(AbstractLongOfferDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		PaymentAbilityERL resourceLink = document.getChargeOffResourceLink();
		if(!(resourceLink instanceof CardLink))
		{
			document.setConnectChargeOffToMobilBank(null);
			return;
		}

		document.setConnectChargeOffToMobilBank(MobileBankManager.hasAnyMB(resourceLink.getNumber()));
	}
}
