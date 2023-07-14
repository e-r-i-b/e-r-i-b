package com.rssl.phizic.messaging.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.documents.AbstractP2PTransfer;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;

/**
 * Отправляет смс получателю перевода с карты на карту Сбера: 1) по номеру карты или 2) по номеру телефона
 * @author Dorzhinov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MessageToReceiverHandler extends BusinessDocumentHandlerBase<AbstractP2PTransfer>
{
	private static final String SMS_TEMPLATE = "Сбербанк Онлайн. %1$s перевел(а) Вам %2$s %3$s%4$s";
    
    private static final String STATUS_SUCCESS_TEXT = "sent";
    private static final String STATUS_ERROR_TEXT = "not_sent";
    private static final String STATUS_NOT_AVAILABLE_TEXT = "не доступна БД МБ";

    @SuppressWarnings({"OverlyLongMethod"})
    public void process(AbstractP2PTransfer transfer, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		String type = transfer.getReceiverSubType();
		boolean isOurCard = RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE.equals(type) || transfer.isOurBankCard();
		boolean isOurPhone = RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE.equals(type);
		boolean isSocial = RurPayment.SOCIAL_TRANSFER_TYPE_VALUE.equals(type);
		boolean isYandexByContact = RurPayment.OUR_CONTACT_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(type);
		boolean isYandexByPhone = RurPayment.BY_PHONE_TO_YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(type);
		boolean isYandex = isYandexByContact || isYandexByPhone;
		boolean isByContactToOtherCard = RurPayment.OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE.equals(type);
		if (isOurCard || isOurPhone || isSocial || isByContactToOtherCard || isYandex)
		{
			String message = isYandex && !ConfigFactory.getConfig(com.rssl.phizic.business.payments.PaymentsConfig.class).isSendMessageToReceiverYandex() ? null : transfer.getMessageToReceiver();
            Money amount = transfer.getExactAmount();
            String preparedMessage = StringHelper.isEmpty(message) ? "" : (". Сообщение: \"" + message + "\"");
			String smsText = String.format(SMS_TEMPLATE, transfer.getFormattedPersonName(), amount.getDecimal(), amount.getCurrency().getCode(), preparedMessage);

			//перевод по номеру карты
			if (isOurCard)
			{
				String cardNumber = transfer.getReceiverCard();

				//Используя номер карты, ищем номера телефонов
				Collection<String> phones = null;
				try
				{
					phones = MobileBankManager.getMainPhones(cardNumber, true);
				}
				catch (Exception e)
				{
					log.error(STATUS_NOT_AVAILABLE_TEXT, e);
				}

				if (CollectionUtils.isNotEmpty(phones))
				{
					//что то нашли - отправляем
					SmsTransportService smsTransportService = MessagingSingleton.getMbkSmsTransportService(false);
					boolean send = false;

					//исключаем дубликаты
					for (String phone : new HashSet<String>(phones))
					{
						try
						{
							smsTransportService.sendSms(phone, TranslitMode.TRANSLIT, smsText, smsText, TextMessage.DEFAULT_PRIORITY);
							send = true;
						}
						catch (IKFLMessagingException e)
						{
							log.error(STATUS_ERROR_TEXT, e);
						}
					}

					if (send)
						transfer.setMessageToReceiverStatus(STATUS_SUCCESS_TEXT);
					else
						transfer.setMessageToReceiverStatus(STATUS_ERROR_TEXT);
				}
				else
				{
					//пусто - указываем отправителю на невозможность отправки смс
					transfer.setMessageToReceiverStatus(STATUS_ERROR_TEXT);
				}
			}
			//перевод по номеру телефона
			else if (isOurPhone || isByContactToOtherCard || isYandex)
			{
				try
				{
					String phone = isOurPhone || isYandexByPhone ? transfer.getMobileNumber() : transfer.getContactPhone();
					//всегда отправляем sms на указанный номер телефона
					SmsTransportService smsTransportService = MessagingSingleton.getMbkSmsTransportService(false);
					smsTransportService.sendSms(phone, TranslitMode.TRANSLIT, smsText, smsText,TextMessage.DEFAULT_PRIORITY);
					transfer.setMessageToReceiverStatus(STATUS_SUCCESS_TEXT);
				}
				catch (IKFLMessagingException e)
				{
					transfer.setMessageToReceiverStatus(STATUS_ERROR_TEXT);
					log.error(STATUS_ERROR_TEXT, e);
				}
			}
		}
	}
}
