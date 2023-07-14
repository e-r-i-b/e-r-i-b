package com.rssl.phizicgate.esberibgate.documents.senders.PaymentSystemPayment;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер платежа "Оплата товаров и услуг с карты."
 */

public class CardPaymentSystemPaymentSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public CardPaymentSystemPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new CardPaymentSystemPaymentProcessor((CardPaymentSystemPayment) document, getServiceName(document)));
	}

	public void prepare(GateDocument document) throws GateException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		if (StringHelper.isEmpty(payment.getChargeOffCard()) || payment.getChargeOffCardExpireDate() == null)
			throw new GateException("Поставщик поддерживает только карточные переводы");

		try
		{
			List<Field> extendedFields    = payment.getExtendedFields();
			List<Field> newExtendedFields = CardPaymentSystemPaymentProcessor.getNewExtendedFields(extendedFields);

			//показываем форму клиеннту
			if (CollectionUtils.isNotEmpty(newExtendedFields))
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//конец. устанавливаем идентификатор поля во внешней системе
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	public void validate(GateDocument document){}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("Подтверждение платежа \"Оплата товаров и услуг с карты.\" не поддерживается");
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка платежа \"Оплата товаров и услуг с карты.\" не поддерживается");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}
}