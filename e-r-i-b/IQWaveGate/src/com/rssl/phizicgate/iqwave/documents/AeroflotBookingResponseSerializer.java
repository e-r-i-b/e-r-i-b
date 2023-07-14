package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.Counter;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import static com.rssl.phizicgate.iqwave.messaging.Constants.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * ���������� ��� ������ ������ ���������
 * @author niculichev
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class AeroflotBookingResponseSerializer
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	private static final Map<String, String> tickets = new HashMap<String, String>();

	static
	{
		tickets.put("1", "��������");
		tickets.put("2", "����������");
		tickets.put("3", "������� � ������");
		tickets.put("4", "������� ��� �����");
	}

	/**
	 * ��������� �������� ������ ���������� � ����� �� ������ ���������� � �����
	 * @param response �����
	 * @param payment ��������
	 * @throws GateException
	 */
	public static void fillInfoByBooking(Document response, CardPaymentSystemPayment payment) throws GateException
	{
		try
		{
			Element root = response.getDocumentElement();
			List<Field> extendedFields = payment.getExtendedFields();

			// �������� ���� �����
			Field recIdentifier = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			String responceBookingCode = XmlHelper.getSimpleElementValue(root, REC_IDENTIFIER_FIELD_NAME);
			if(!recIdentifier.getValue().equals(responceBookingCode))
				throw new GateException("��� ����� � ������� (" + recIdentifier.getValue() + ") �� ��������� � ����� ����� � ������ (" + responceBookingCode + ")");
			extendedFields.remove(recIdentifier);

			// �������� ������, ������ ���� �����
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			String currCode = XmlHelper.getSimpleElementValue(root, RESERV_CURR_CODE);
			Currency currency = currencyService.findByAlphabeticCode(currCode);
			if(!currencyService.getNationalCurrency().compare(currency))
			{
				log.error("��� ������� ��������� � ����� � RecIdentifier = " + recIdentifier.getValue() + "������ �� �������� ������");
				throw new GateException("�������� �������� ����������");
			}

			extendedFields.add(BillingPaymentHelper.createAmountField(
					"����� � ������", false,  XmlHelper.getSimpleElementValue(root, RESERV_SUMMA)));
			// ������������� ������
			extendedFields.add(AeroflotBookingHelper.createIdentifierOrder(XmlHelper.getSimpleElementValue(root, RESEVRV_FULL_NUMBER)));
			// �������� ��� ������� � ���������� � �����
			extendedFields.addAll(getBookingInfoFields(response, (CommonField) recIdentifier));
			// ��������� ��� ������ � ����������� � ����������
			extendedFields.addAll(getPassengersFields(response));
			// ��������� ��� ������ � ����������� � ��������
			extendedFields.addAll(getRouteFields(response));
			// ��������� ��� �������
			extendedFields.add(RequestHelper.createDisableTextField(
							USER_MESSAGE, "���������", XmlHelper.getSimpleElementValue(root, USER_MESSAGE), null));
			if(BillingPaymentHelper.getFieldById(extendedFields, Constants.AIRLINE_IDENTIFY_FIELD) ==null)
				extendedFields.add(AeroflotBookingHelper.createAirlineIdentifyField());
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private static List<Field> getPassengersFields(Document response) throws Exception
	{
		 
		final List<Field> newExtendedFields = new ArrayList<Field>();
		// ��������� ������ �����������
		newExtendedFields.add(RequestHelper.createDisableTextField(PASSENGERS_GROUP_NAME, "", "���������� � ����������", PASSENGERS_GROUP_NAME));
		final Counter counter = new Counter(-1);
		XmlHelper.foreach(response.getDocumentElement(), PASSENGER_LIST, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String firstName = XmlHelper.getSimpleElementValue(element, PASSENGER_FIRST_NAME);
				String lastName = XmlHelper.getSimpleElementValue(element, PASSENGER_LAST_NAME);
				String ticketType = XmlHelper.getSimpleElementValue(element, PASSENGER_TYPE);
				counter.increment();

				newExtendedFields.add(RequestHelper.createDisableTextField(
						PASSENGER_FULL_NAME + counter.getValue(),  "��������", lastName + " " + firstName, PASSENGERS_GROUP_NAME));
				
				newExtendedFields.add(RequestHelper.createDisableTextField(
						PASSENGER_TYPE + counter.getValue(), "��� ������", tickets.get(ticketType), PASSENGERS_GROUP_NAME));
			}
		});

		return newExtendedFields;
	}

	private static List<Field> getRouteFields(Document response) throws Exception
	{
		final List<Field> newExtendedFields = new ArrayList<Field>();
		// ��������� ������ ������
		newExtendedFields.add(RequestHelper.createDisableTextField(ROUTES_INFO_GROUP_NAME, "", "���������� � ��������", ROUTES_INFO_GROUP_NAME));
		final Counter counter = new Counter(-1);
		XmlHelper.foreach(response.getDocumentElement(), ROUTES_LIST, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				counter.increment();

				String routesDepFlight = XmlHelper.getSimpleElementValue(element, ROUTES_DEP_FLIGHT);
				newExtendedFields.add(RequestHelper.createDisableTextField(
						ROUTES_DEP_FLIGHT + counter.getValue(), "����� �����", routesDepFlight, ROUTES_INFO_GROUP_NAME));

				newExtendedFields.add(RequestHelper.createDisableTextField(
						ROUTES_DEP_DATETIME + counter.getValue(), "�����", StringHelper.formatDateStringAirlinePayment(XmlHelper.getSimpleElementValue(element, ROUTES_DEP_DATETIME)), ROUTES_INFO_GROUP_NAME));

				String routesDepAirport = XmlHelper.getSimpleElementValue(element, ROUTES_DEP_AIRPORT);
				String routesDepCity = XmlHelper.getSimpleElementValue(element, ROUTES_DEP_CITY);
				newExtendedFields.add(RequestHelper.createDisableTextField(
						ROUTES_DEP_AIRPORT + counter.getValue(), "��������", routesDepCity + ", " + routesDepAirport, ROUTES_INFO_GROUP_NAME));

				newExtendedFields.add(RequestHelper.createDisableTextField(
						ROUTES_ARRIVAL_DATETIME + counter.getValue(), "��������", StringHelper.formatDateStringAirlinePayment(XmlHelper.getSimpleElementValue(element, ROUTES_ARRIVAL_DATETIME)), ROUTES_INFO_GROUP_NAME));

				String routesArrivalAirport = XmlHelper.getSimpleElementValue(element, ROUTES_ARRIVAL_AIRPORT);
				String routesArrivalCity = XmlHelper.getSimpleElementValue(element, ROUTES_ARRIVAL_CITY);
				newExtendedFields.add(RequestHelper.createDisableTextField(
						ROUTES_ARRIVAL_AIRPORT + counter.getValue(), "��������", routesArrivalCity + ", " + routesArrivalAirport, ROUTES_INFO_GROUP_NAME));
			}
		});

		return newExtendedFields;
	}

	private static List<Field> getBookingInfoFields(Document response, CommonField recIdentifier)
	{
		final List<Field> newExtendedFields = new ArrayList<Field>();
		Element root = response.getDocumentElement();
		// ���������� � �����
		newExtendedFields.add(RequestHelper.createDisableTextField(INFO_BOOKING_GROUP_NAME, "", "���������� � �����", INFO_BOOKING_GROUP_NAME));
		// ��������� ��� �����
		recIdentifier.setGroupName(INFO_BOOKING_GROUP_NAME);
		newExtendedFields.add(recIdentifier);

		newExtendedFields.add(RequestHelper.createDisableTextField(
						RESERV_EXPIRATION, "��������� ��", StringHelper.formatDateStringAirlinePayment(XmlHelper.getSimpleElementValue(root, RESERV_EXPIRATION)), INFO_BOOKING_GROUP_NAME));

		String passengerNumbers = XmlHelper.getSimpleElementValue(root, PASSENGER_NUMBERS);
		newExtendedFields.add(RequestHelper.createDisableTextField(
				PASSENGER_NUMBERS, "���������� ����������", passengerNumbers, INFO_BOOKING_GROUP_NAME));

		String routesNumbers = XmlHelper.getSimpleElementValue(root, ROUTES_NUMBERS);
		newExtendedFields.add(RequestHelper.createDisableTextField(
						ROUTES_NUMBERS, "���������� ������", routesNumbers, INFO_BOOKING_GROUP_NAME));

		return newExtendedFields;
	}
}
