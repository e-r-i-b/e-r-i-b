package com.rssl.phizicgate.iqwave.listener;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.Counter;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.documents.AeroflotBookingHelper;
import com.rssl.phizicgate.iqwave.documents.RequestHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizicgate.iqwave.messaging.Constants.*;

/**
 * Обработчик ответа об исполнении заявки оплаты аэрофлоту
 * @author niculichev
 * @ created 12.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class AeroflotBookingExecutionResultProcessor extends ExecutionResultProcessorBase
{
	private static final Map<String, String> states = new HashMap<String, String>();
	static
	{
		states.put("0", "Заказ не оплачен");
		states.put("1", "Заказ оплачен, билеты выпущены");
		states.put("2", "Заказ оплачен, билеты не выпущены");
		states.put("3", "Заказ отменен");
		states.put("4", "Заказ оплачен другой Агентской системой");
		states.put("5", "Заказ оплачен без участия ЕПР");

	}

	public void fillPaymentData(SynchronizableDocument document, Document bodyContent) throws GateException
	{

		try
		{
			if(document.getType() != CardPaymentSystemPayment.class)
				throw new GateException("Документ " + document.getExternalId() + " имеет неверный тип");

			CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
			List<Field> extendedFields = payment.getExtendedFields();
			Element response = bodyContent.getDocumentElement();

			// проверяем совпадение брони в документе и в ответе
			Field recIdentifier = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			String recIdentifierResponse = XmlHelper.getSimpleElementValue(response, REC_IDENTIFIER_FIELD_NAME);
			if(!recIdentifierResponse.equals(recIdentifier.getValue()))
				throw new GateException("Код брони в документе(" + recIdentifier.getValue() + ") не совпадает с кодом брони в ответе на исполнение(" + recIdentifierResponse +  ")");

			// удаляем старое поле с сообщением клиенту, при добавлении инфы о билетах будет добавлено вновь пришеднее поле
			// с сообщением в качестве всплывающей подсказки
			extendedFields.remove(BillingPaymentHelper.getFieldById(extendedFields, USER_MESSAGE));

			// добавляем информацию о билетах
			extendedFields.addAll(getTicketInfoFields(response));
			// добавляем поле о маршрут квитанции
			String externalLink = XmlHelper.getSimpleElementValue(response, ITINERARY_URL);
			extendedFields.add(AeroflotBookingHelper.createExternalLink(ITINERARY_URL, "Детали","Маршрут-квитанция", externalLink));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private List<Field> getTicketInfoFields(Element response) throws Exception
	{
		final List<Field> newExtendedFields = new ArrayList<Field>();

		newExtendedFields.add(RequestHelper.createDisableTextField(
				TICKETS_GROUP_NAME, "", "Информация о билетах", TICKETS_GROUP_NAME));

		final Counter counter = new Counter(-1);
		// добавляем номера билетов
		XmlHelper.foreach(response, TICKETS_LIST, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				counter.increment();
				String number = XmlHelper.getSimpleElementValue(element, TICKET_NUMBER);
				newExtendedFields.add(RequestHelper.createDisableTextField(
						TICKET_NUMBER + counter.getValue(), "Билет", number, TICKETS_GROUP_NAME));
			}
		});

		// добавляем статус билетов
		String stateTicket = XmlHelper.getSimpleElementValue(response, TICKETS_STATUS);
		CommonField statusTicket = RequestHelper.createDisableTextField(
				TICKETS_STATUS, "Состояние билетов", states.get(stateTicket), TICKETS_GROUP_NAME);
		// сеттим сообщением клиенту как всплывающую посдказку
		statusTicket.setPopupHint(XmlHelper.getSimpleElementValue(response, USER_MESSAGE));
		newExtendedFields.add(statusTicket);

		// добавляем количество билетов
		String ticketsNumbers = XmlHelper.getSimpleElementValue(response, TICKETS_NUMBERS);
		newExtendedFields.add(RequestHelper.createDisableTextField(
				TICKETS_NUMBERS, "Количество билетов", ticketsNumbers, INFO_BOOKING_GROUP_NAME));

		return newExtendedFields;
	}
}
