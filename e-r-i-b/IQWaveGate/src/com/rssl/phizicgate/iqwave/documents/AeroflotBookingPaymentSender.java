package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
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
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizgate.common.payments.PaymentCompositeId;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Сендер для оплаты брони "Аэрофлота"
 * @author niculichev
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class AeroflotBookingPaymentSender extends IQWaveAbstractDocumentSender
{
	private static final String DATE_EXPIRATION_ERROR_MESSAGE = "Срок Вашей брони истек";

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public AeroflotBookingPaymentSender(GateFactory factory)
	{
		super(factory);
	}

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

		// проверяем истекла ли дата брони
		if(isDateExpiration(payment))
			throw new GateLogicException(DATE_EXPIRATION_ERROR_MESSAGE);

		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_RES_REQUEST);
		fillExecutionMessage(message, payment);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		String externalId = getExternalId(response);
		payment.setExternalId(externalId);
		payment.setIdFromPaymentSystem(null);
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

		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_RES_REQUEST);
		fillExecutionMessage(message, payment);
		// отправляем повторно
		PaymentCompositeId compositeId = new PaymentCompositeId(payment.getExternalId());
		MessageHead messageHead = new MessageHeadImpl(compositeId.getMessageId(), XMLDatatypeHelper.parseDate(compositeId.getMessageDate()), null, null, null, null);
		serviceFacade.sendOnlineMessage(message, messageHead);
	}

	protected String getConfirmRequestName(GateDocument document)
	{
		return Constants.PAYMENT_RES_RESPONSE;
	}

	private void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		try
		{
			List<Field> extendedFields = payment.getExtendedFields();
			//Код сервиса (маршрута)
			RequestHelper.appendRouteCode(message, "Route", Long.valueOf(payment.getReceiverPointCode()));
			//Информация по карте списания
			RequestHelper.appendCardInf(message, payment.getChargeOffCard(), payment.getChargeOffCardExpireDate());

			// Номер брони (алфавитно-цифровой)
			Field recIdentifier = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if(recIdentifier == null || StringHelper.isEmpty((String) recIdentifier.getValue()))
				throw new GateException("Не задан номер брони " + Constants.REC_IDENTIFIER_FIELD_NAME);
			message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, recIdentifier.getValue());

			// идентификатор системы бронирования
			Field reservationId = BillingPaymentHelper.getFieldById(extendedFields, Constants.RESERVATION_ID);
			if(reservationId == null || StringHelper.isEmpty((String) reservationId.getValue()))
				throw new GateException("Не задан идентификатор " + Constants.RESERVATION_ID + " системы бронирования");
			message.addParameter(Constants.RESERVATION_ID, reservationId.getValue());

			// Тип карточного продукта
			message.addParameter(Constants.CARD_PRODUCT_TYPE, payment.getChargeOffCardDescription());
			
			// идентификатор заказа
			Field identifierOrder = BillingPaymentHelper.getFieldById(extendedFields, Constants.RESEVRV_FULL_NUMBER);
			if(identifierOrder == null || StringHelper.isEmpty((String) identifierOrder.getValue()))
				throw new GateException("Не задан атрибут " +  Constants.RESEVRV_FULL_NUMBER + " идентифицирующий заказ");
			message.addParameter(Constants.RESV_FULL_NUMBER, identifierOrder.getValue());

			Money amount = payment.getDestinationAmount();
			// валюта операции
			message.addParameter(Constants.CURR_CODE, amount.getCurrency().getCode());
			// сумма платежа(задолженности)
			message.addParameter(Constants.SUMMA_TEG, amount.getDecimal());
		    //Код мобильного банка
		    fillMBOperCodeField(message, payment);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * подготовить документ
	 * @param document документ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		if (StringHelper.isEmpty(payment.getChargeOffCard())
				|| payment.getChargeOffCardExpireDate() == null)
			throw new GateException("Поставщик поддерживает только карточные переводы");

		try
		{
			List<Field> extendedFields = payment.getExtendedFields();
			CommonField identifierField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if(identifierField == null)
			{
				// если нет номера брони, формируем и возвращаем пользователю для заполнения
				extendedFields.add(AeroflotBookingHelper.createIdentifierBooking());
				//добавляем поле указывающее что платеж-оплата брони авиабилетов
				extendedFields.add(AeroflotBookingHelper.createAirlineIdentifyField());
			}
			else
			{
				if(!document.isTemplate())
				{
					// отправляем запрос информации о броне и обрабатываем ответ
					AeroflotBookingHelper.sendBookingInfoRequest(payment);
				}	
				//конец. устанавливаем идентификатор поля во внешней системе
				payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * валидация платежа
	 * @param document  платеж
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}

	private boolean isDateExpiration(CardPaymentSystemPayment payment) throws GateException
	{
		try
		{
			Field dateExpirationField = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.RESERV_EXPIRATION);
			String dateValue = (String) dateExpirationField.getValue();
			Date dateExpiration = DateHelper.parseStringTimeWithoutSecond(dateValue);
			return new Date().compareTo(dateExpiration) > 0;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}
}
