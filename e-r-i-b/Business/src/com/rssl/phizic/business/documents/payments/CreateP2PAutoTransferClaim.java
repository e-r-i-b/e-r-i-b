package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * Заявка на создание автоперевода
 *
 * @author khudyakov
 * @ created 07.09.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateP2PAutoTransferClaim extends P2PAutoTransferClaimBase
{
	public Class<? extends GateDocument> getType()
	{
		String receiverType = getReceiverType();
		if (StringHelper.isEmpty(receiverType) || SEVERAL_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return InternalCardsTransferLongOffer.class;
		}

		if (RurPayment.PHIZ_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			if (StringHelper.isNotEmpty(getChargeOffCard()) && StringHelper.isNotEmpty(getReceiverCard()))
			{
				try
				{
					if (isSameTb(getCardOfficeCode(getChargeOffCard()), getCardOfficeCode(getReceiverCard())))
					{
						return ExternalCardsTransferToOurBankLongOffer.class;
					}
					return ExternalCardsTransferToOtherBankLongOffer.class;
				}
				catch (GateException e)
				{
					throw new IllegalStateException("Некорректный тип документа.", e);
				}
				catch (GateLogicException e)
				{
					throw new IllegalStateException("Некорректный тип документа.", e);
				}
			}

			return ExternalCardsTransferToOurBankLongOffer.class;
		}

		throw new IllegalStateException("Некорректный тип документа.");
	}

	@Override
	public FormType getFormType()
	{
		return FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM;
	}

	protected boolean isOperationDenied(AutoSubscriptionLink subscriptionLink)
	{
		return true;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		setNextPayDate(getStartDate());
		setChannelType(getCurrentChannelType());
		setCodeATM(LogThreadContext.getCodeATM());
	}

	@Override
	public Calendar getNextPayDate()
	{
		return getStartDate();
	}

	public Calendar getUpdateDate()
	{
		//для заявки создания равна null
		return null;
	}
}
