package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.accounts.MockAccount;
import com.rssl.phizic.business.cards.MockCard;
import com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;

/**
 * Хэндлит очистки кэша связанных с документом объектов.
 * @author Dorzhinov
 * @ created 18.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class CacheClearHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		try
		{
			for (ExternalResourceLink link : gateDocument.getLinks())
			{
				resetLink(link);
			}

			Class type = gateDocument.getType();

			//костыли для заявок на создание объектов
			if (type == CreateAutoPayment.class)
			{
				BusinessDocumentOwner documentOwner = gateDocument.getOwner();
				if (documentOwner.isGuest())
					throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
				AutoPaymentLink.reset((AutoPayment) gateDocument, documentOwner.getLogin());
			}
			else if (type == EditAutoPayment.class || type == RefuseAutoPayment.class)
			{
				AutoPaymentLink link = ((AutoPaymentBase) gateDocument).getAutoPaymentLink();
				link.reset();
			}
			else if (document instanceof AccountOpeningClaim)
			{
				MockAccount account = new MockAccount();//чтобы не запрашивать счет из ВС используем заглушку для очистки кеша
				account.setNumber(((AccountOpeningClaim) document).getReceiverAccount());
				XmlEntityListCacheSingleton.getInstance().clearCache(account, Account.class);
			}
			else if (document instanceof AccountChangeInterestDestinationClaim)
			{
				MockCard card = new MockCard();
				XmlEntityListCacheSingleton.getInstance().clearCache(card, Card.class);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка при очистке кеша для документа " + gateDocument.getId(), e);
		}
	}

	private void resetLink(ExternalResourceLink link)
	{
		try
		{
			link.reset();
		}
		catch (Exception e)
		{
			log.error("Ошибка при очистке кеша для ресурса " + link.toString(), e);
		}
	}
}
