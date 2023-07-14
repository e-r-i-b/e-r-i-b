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
 * ���������� ������ �� ���������� ������ ������ ���������
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
		states.put("0", "����� �� �������");
		states.put("1", "����� �������, ������ ��������");
		states.put("2", "����� �������, ������ �� ��������");
		states.put("3", "����� �������");
		states.put("4", "����� ������� ������ ��������� ��������");
		states.put("5", "����� ������� ��� ������� ���");

	}

	public void fillPaymentData(SynchronizableDocument document, Document bodyContent) throws GateException
	{

		try
		{
			if(document.getType() != CardPaymentSystemPayment.class)
				throw new GateException("�������� " + document.getExternalId() + " ����� �������� ���");

			CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
			List<Field> extendedFields = payment.getExtendedFields();
			Element response = bodyContent.getDocumentElement();

			// ��������� ���������� ����� � ��������� � � ������
			Field recIdentifier = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			String recIdentifierResponse = XmlHelper.getSimpleElementValue(response, REC_IDENTIFIER_FIELD_NAME);
			if(!recIdentifierResponse.equals(recIdentifier.getValue()))
				throw new GateException("��� ����� � ���������(" + recIdentifier.getValue() + ") �� ��������� � ����� ����� � ������ �� ����������(" + recIdentifierResponse +  ")");

			// ������� ������ ���� � ���������� �������, ��� ���������� ���� � ������� ����� ��������� ����� ��������� ����
			// � ���������� � �������� ����������� ���������
			extendedFields.remove(BillingPaymentHelper.getFieldById(extendedFields, USER_MESSAGE));

			// ��������� ���������� � �������
			extendedFields.addAll(getTicketInfoFields(response));
			// ��������� ���� � ������� ���������
			String externalLink = XmlHelper.getSimpleElementValue(response, ITINERARY_URL);
			extendedFields.add(AeroflotBookingHelper.createExternalLink(ITINERARY_URL, "������","�������-���������", externalLink));
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
				TICKETS_GROUP_NAME, "", "���������� � �������", TICKETS_GROUP_NAME));

		final Counter counter = new Counter(-1);
		// ��������� ������ �������
		XmlHelper.foreach(response, TICKETS_LIST, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				counter.increment();
				String number = XmlHelper.getSimpleElementValue(element, TICKET_NUMBER);
				newExtendedFields.add(RequestHelper.createDisableTextField(
						TICKET_NUMBER + counter.getValue(), "�����", number, TICKETS_GROUP_NAME));
			}
		});

		// ��������� ������ �������
		String stateTicket = XmlHelper.getSimpleElementValue(response, TICKETS_STATUS);
		CommonField statusTicket = RequestHelper.createDisableTextField(
				TICKETS_STATUS, "��������� �������", states.get(stateTicket), TICKETS_GROUP_NAME);
		// ������ ���������� ������� ��� ����������� ���������
		statusTicket.setPopupHint(XmlHelper.getSimpleElementValue(response, USER_MESSAGE));
		newExtendedFields.add(statusTicket);

		// ��������� ���������� �������
		String ticketsNumbers = XmlHelper.getSimpleElementValue(response, TICKETS_NUMBERS);
		newExtendedFields.add(RequestHelper.createDisableTextField(
				TICKETS_NUMBERS, "���������� �������", ticketsNumbers, INFO_BOOKING_GROUP_NAME));

		return newExtendedFields;
	}
}
