package com.rssl.phizic.business.payments.forms.meta.handlers.subscriptions;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * ’ендлер провер€ет существование аналогичного автоплатежа
 *
 * @author khudyakov
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 */
public class CheckExistsCardsAutoTransfersHandler extends BusinessDocumentHandlerBase<P2PAutoTransferClaimBase>
{
	public void process(P2PAutoTransferClaimBase autoTransfer, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			List<AutoSubscriptionLink> links = PersonContext.getPersonDataProvider().getPersonData().getAutoSubscriptionLinks();
			if (CollectionUtils.isEmpty(links))
			{
				return;
			}

			log.info("ѕроверка BUG080907: start");
			log.info("ѕроверка BUG080907: за€вка на создание P2P автоперевода id=" + autoTransfer.getId() +
					" в статусе " + autoTransfer.getState().toString() + " карта списани€: " + autoTransfer.getChargeOffCard() +
					" карта зачислени€: " + autoTransfer.getReceiverCard());

			for (AutoSubscriptionLink link : links)
			{
				AutoSubscription autoSubscription = link.getValue();
				if (!(InternalCardsTransferLongOffer.class == autoSubscription.getType() || ExternalCardsTransferToOurBankLongOffer.class == autoSubscription.getType() ||
						ExternalCardsTransferToOtherBankLongOffer.class == autoSubscription.getType()))
				{
					continue;
				}

				if (isSame(autoTransfer, autoSubscription))
				{
					throw new DocumentLogicException("јвтоплатеж дл€ указанных карт уже существует. ”кажите другую карту списани€ или карту зачислени€ дл€ продолжени€ подключени€ услуги.");
				}
			}
			log.info("ѕроверка BUG080907: end");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	private boolean isSame(P2PAutoTransferClaimBase autoTransfer, AutoSubscription autoSubscription)
	{
		log.info("ѕроверка BUG080907: автоплатеж - карта списани€: " + autoSubscription.getCardNumber() + " карта зачислени€: " + autoSubscription.getReceiverCard());
		return autoTransfer.getReceiverCard().equals(autoSubscription.getReceiverCard()) && autoTransfer.getChargeOffCard().equals(autoSubscription.getCardNumber());
	}
}
