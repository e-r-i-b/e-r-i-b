package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.payments.autosubscriptions.DelayCardToCardLongOffer;
import com.rssl.phizic.logging.LogThreadContext;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * Заявка на приостановку выполнения автоплатежа карта-карта
 * @author lukina
 * @ created 22.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class DelayP2PAutoTransferClaim extends P2PAutoTransferClaimBase implements DelayCardToCardLongOffer
{
	public Class<? extends GateDocument> getType()
	{
		return DelayCardToCardLongOffer.class;
	}

	@Override
	public FormType getFormType()
	{
		return FormType.DELAY_P2P_AUTO_TRANSFER_CLAIM;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);

		AutoSubscriptionLink subscriptionLink = findAutoSubscriptionLink();
		if (isOperationDenied(subscriptionLink))
		{
			throw new DocumentException("Приостановка автоплатежа разрешено только, если он находится в статусе Прошел регистрацию, Не зарегистрирован или Активный");
		}

		storeAutoSubscriptionData(subscriptionLink, messageCollector);
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		setChannelType(getCurrentChannelType());
		setCodeATM(LogThreadContext.getCodeATM());
	}

	public Calendar getUpdateDate()
	{
		return null;
	}

	@Override
	protected boolean isOperationDenied(AutoSubscriptionLink subscriptionLink)
	{
		AutoPayStatusType statusType = subscriptionLink.getAutoPayStatusType();
		return statusType != AutoPayStatusType.Confirmed && statusType != AutoPayStatusType.ErrorRegistration && statusType != AutoPayStatusType.Active;
	}
}
