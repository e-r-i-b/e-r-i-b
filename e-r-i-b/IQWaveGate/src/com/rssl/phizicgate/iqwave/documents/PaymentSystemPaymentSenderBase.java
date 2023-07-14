package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.iqwave.documents.debts.DebtsHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizgate.common.payments.PaymentCompositeId;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author krenev
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class PaymentSystemPaymentSenderBase extends IQWaveAbstractDocumentSender
{
	protected PaymentSystemPaymentSenderBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @return <имя запроса на исполнение платежа, имя запроса присылаемое ВС на исполнение документа>
	 */
	protected abstract Pair<String, String> getExecutionMessageName(GateDocument document);

	/**
	 * @return имя запроса задолжности
	 */
	protected abstract String getDebtMessageName();

	/**
	 * Добавить дополнительные поля в запрос задолжности
	 * @param message запрос
	 * @param extendedFields дополнительные поля
	 * @throws GateException
	 */
	protected abstract void addExtendedFieldsToDebtRequest(GateMessage message, List<Field> extendedFields) throws GateException, GateLogicException;

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		if (document.getType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");
		}
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (StringHelper.isEmpty(payment.getChargeOffCard()) || payment.getChargeOffCardExpireDate() == null)
		{
			throw new GateException("Поставщик поддерживает только карточные переводы");
		}
		GateMessage message = serviceFacade.createRequest(getExecutionMessageName(document).getFirst());
		fillExecutionMessage(message, payment);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		String externalId = getExternalId(response);
		payment.setExternalId(externalId);
		//затираем это значение, в IqWave не нужно отображать в чеке номер операции во внешней системе.
		payment.setIdFromPaymentSystem(null);
	}

	/**
	 * Заполнить сообщение на исполение платежа
	 * @param message сообщение
	 * @param payment платеж
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	protected abstract void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException;

	/**
	 * получить из платежа реквизит, идентифицирующий плательщика
	 * @param payment платеж.
	 * @return Реквизит, идентифицирующий плательщика
	 */
	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		Field field = null;
		try
		{
			field = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.REC_IDENTIFIER_FIELD_NAME);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		if (field == null)
		{
			throw new GateException("Не найден атрибут " + Constants.REC_IDENTIFIER_FIELD_NAME + ", идентифицирующи плательщика");
		}
		return field.getValue();
	}

	/**
	 * Получить задолженность
	 * @param payment платеж
	 * @return задолженность
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected Document getDebtResponse(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		try
		{
			WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
			GateMessage message = serviceFacade.createRequest(getDebtMessageName());

			DebtsHelper.fillDebtRequest(message, payment);
			addExtendedFieldsToDebtRequest(message, payment.getExtendedFields());

			return serviceFacade.sendOnlineMessage(message, null);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		if (document.getType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");
		}
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (StringHelper.isEmpty(payment.getChargeOffCard()) || payment.getChargeOffCardExpireDate() == null)
		{
			throw new GateException("Поставщик поддерживает только карточные переводы");
		}

		if (payment.getExternalId() == null)
			throw new GateException(String.format("Ошибка повторной отправки платежа с id=%s: не заполнен ExternalId", document.getId()));

		GateMessage message = serviceFacade.createRequest(getExecutionMessageName(document).getFirst());
		fillExecutionMessage(message, payment);

		PaymentCompositeId compositeId = new PaymentCompositeId(payment.getExternalId());
		MessageHead messageHead = new MessageHeadImpl(compositeId.getMessageId(), XMLDatatypeHelper.parseDate(compositeId.getMessageDate()), null, null, null, null);
		serviceFacade.sendOnlineMessage(message, messageHead);
	}

	protected String getConfirmRequestName(GateDocument document)
	{
		return getExecutionMessageName(document).getSecond();
	}
}
