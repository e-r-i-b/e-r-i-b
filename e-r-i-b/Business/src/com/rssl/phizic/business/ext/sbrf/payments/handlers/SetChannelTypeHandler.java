package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.logging.LogThreadContext;

/**
 * Хендлер для установки значения канал создания подписки(IB - интернет банк, VSP - ВСП, US - устройство самообслуживания)
 * @author niculichev
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class SetChannelTypeHandler  extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(document instanceof CreateMoneyBoxPayment)
		{
			updateChannelTypeParams((CreateMoneyBoxPayment)document);
			return;
		}

		if(!(document instanceof CardPaymentSystemPaymentLongOffer))
			throw new DocumentException("Ожидается CardPaymentSystemPaymentLongOffer");

		if(!(document instanceof JurPayment))
			throw new DocumentException("Ожидается JurPayment");

		updateChannelTypeParams((JurPayment) document);
	}

	/**
	 * Обновелние заявки данными о канале создания(тип канала подтверждения и код УС).
	 */
	private void updateChannelTypeParams(AbstractLongOfferDocument payment) throws DocumentLogicException, DocumentException
	{
		try
		{
			if(payment instanceof EditAutoSubscriptionPayment || payment instanceof EditMoneyBoxClaim)
			{
				String autoSubNumber = "";
				if(payment instanceof EditAutoSubscriptionPayment)
					autoSubNumber = payment.getAttribute(AutoSubscriptionPaymentBase.AUTO_SUBSCRIPTION_NUMBER).getStringValue();
				else
					autoSubNumber = payment.getAttribute("long-offer-number").getStringValue();

				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				AutoSubscriptionLink link = personData.getAutoSubscriptionLink(Long.parseLong(autoSubNumber));
				payment.setCodeATM(link.getAutoSubscriptionInfo().getCodeATM());    //номер устройсва как и канал в случае редакторования оставляем неизменными.
				return;
			}
			setApplication(payment);
			payment.setCodeATM(LogThreadContext.getCodeATM());
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void setApplication(AbstractLongOfferDocument payment)
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		Application currentApplication = applicationConfig.getLoginContextApplication();
		switch (currentApplication)
		{
			case atm: payment.setChannelType(ChannelType.US); break;
			case mobile9: payment.setChannelType(ChannelType.IB); break;
			case PhizIA: payment.setChannelType(ChannelType.VSP); break;
			case PhizIC: payment.setChannelType(ChannelType.IB); break;
			default: payment.setChannelType(ChannelType.IB); break;   //в случае если канал null, будет ошибка при отправке сообщения.
		}
	}
}
