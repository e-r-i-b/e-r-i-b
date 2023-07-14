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
 * ������� ��������� ������������� ������������ �����������
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

			log.info("�������� BUG080907: start");
			log.info("�������� BUG080907: ������ �� �������� P2P ������������ id=" + autoTransfer.getId() +
					" � ������� " + autoTransfer.getState().toString() + " ����� ��������: " + autoTransfer.getChargeOffCard() +
					" ����� ����������: " + autoTransfer.getReceiverCard());

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
					throw new DocumentLogicException("���������� ��� ��������� ���� ��� ����������. ������� ������ ����� �������� ��� ����� ���������� ��� ����������� ����������� ������.");
				}
			}
			log.info("�������� BUG080907: end");
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
		log.info("�������� BUG080907: ���������� - ����� ��������: " + autoSubscription.getCardNumber() + " ����� ����������: " + autoSubscription.getReceiverCard());
		return autoTransfer.getReceiverCard().equals(autoSubscription.getReceiverCard()) && autoTransfer.getChargeOffCard().equals(autoSubscription.getCardNumber());
	}
}
