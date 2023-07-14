package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.documents.debts.DebtsHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import static com.rssl.phizicgate.iqwave.messaging.Constants.PAYMENT_CHECK_RES_REQUEST;
import org.w3c.dom.Document;

/**
 * Хелпер для заявок на оплату брони аэрофлоту
 * @author niculichev
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class AeroflotBookingHelper
{
	public static void sendBookingInfoRequest(CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(PAYMENT_CHECK_RES_REQUEST);
		fillDebtRequest(message, payment);
		Document responce = serviceFacade.sendOnlineMessage(message, null);

		AeroflotBookingResponseSerializer.fillInfoByBooking(responce, payment);
	}

	/**
	 * Заполнение запроса на информации о броне
	 * @param message формируемый запрос
	 * @param payment платеж
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static void fillDebtRequest(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		try
		{
			//формируем основную чать запросa задолженности
			DebtsHelper.fillDebtRequest(message, payment);

			// идентификатор системы бронирования
			Field reservationId = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.RESERVATION_ID);
			if(reservationId == null || StringHelper.isEmpty((String) reservationId.getValue()))
				throw new GateException("Не задан идентификатор " + Constants.RESERVATION_ID + " системы бронирования");
			message.addParameter(Constants.RESERVATION_ID, reservationId.getValue());

			// тип карточного продукта
			message.addParameter(Constants.CARD_PRODUCT_TYPE, payment.getChargeOffCardDescription());

		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Создание поля номера заказа
	 * @param value начальное значение
	 * @return сформированное поле
	 */
	public static Field createIdentifierOrder(String value)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.RESEVRV_FULL_NUMBER);
		field.setName("Номер заказа");
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(true);
		field.setDefaultValue(value);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(false);
		field.setKey(true);

		return field;
	}

	/**
	 * создание поля-ссылки
	 * @param externalId внешний идентификтор поля
	 * @param name имя поля
	 * @param value начальное значение
	 * @return сформированное поле
	 */
	public static Field createExternalLink(String externalId, String name, String value, String link)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.link);
		field.setExternalId(externalId);
		field.setName(name);
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(true);
		field.setDefaultValue(value + '|' + link);
		field.setRequiredForBill(false);
		field.setSaveInTemplate(false);

		return field;
	}

	/**
	 * Cформировать поле "Идентификатор брони"
	 * @return поле идентификатор брони
	 */
	public static Field createIdentifierBooking()
	{
		CommonField recIdentifier = RequestHelper.createIdentifierField("Код брони");
		recIdentifier.setRequiredForConformation(true);
		recIdentifier.setKey(true);
		recIdentifier.setMaxLength(6L);
		recIdentifier.setMinLength(6L);
		return recIdentifier;
	}

	/**
	 * Создание поля идентифицирующего оплату брони авиабилетов
	 * @return сформированное поле
	 */
	public static Field createAirlineIdentifyField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.integer);
		field.setExternalId(Constants.AIRLINE_IDENTIFY_FIELD);
		field.setName("Идентификатор платежа оплаты брони авиабилетов");
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(false);
		field.setRequiredForBill(false);
		field.setSaveInTemplate(false);
		field.setKey(false);

		return field;
	}
}
