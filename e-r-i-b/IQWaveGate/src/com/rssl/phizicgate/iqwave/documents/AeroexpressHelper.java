package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.ListField;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.rssl.phizicgate.iqwave.messaging.Constants.*;

/**
 * @author niculichev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class AeroexpressHelper
{
	public static final String DATE_FIELD_NAME            = "Date";
	public static final String CITY_FIELD_NAME            = "City";
	public static final String BRANCH_ROUTE_FIELD_NAME    = "branchName";
	public static final String TARIF_FIELD_NAME           = "tariffType";
	public static final String COUNT_PLACE_FIELD_NAME     = "numberOfTickets";

	public static final String TRIP_TIME_FIELD_NAME       = "aeroexpress-trip-time";
	public static final String CHOICE_PLACE_FIELD_NAME    = "aeroexpress-choice-place";
	public static final String MAX_COUNT_TICKETS_FIELD_NAME = "aeroexpress-max-count-tickets";
	public static final String PHONE_NUMBER_FIELD_NAME    = "aeroexpress-phone-number";
	public static final String EMAIL_FIELD_NAME           = "aeroexpress-email";
	public static final String ORDER_NUMBER_FIELD_NAME    = "aeroexpress-order-number";
	public static final String AGREEMENT_FIELD_NAME       = "aeroexpress-agreement";
	public static final String TICKET_PREFIX              = "aeroexpress-ticket-";
	public static final String VISIBLE_SELECTED_PLACES_FIELD_NAME = "aeroexpress-visible-selected-place";

	public static final String TARIF_SEATS_SELECT_FIELD_NAME = "aeroexpress-tarif-seats-select";
	public static final String TARIF_MAX_TICKETS_FIELD_NAME = "aeroexpress-tarif-max-tickets";
	public static final String ORDER_TYPE_PRICE_FIELD_NAME = "aeroexpress-order-type-price";

	public static final String INFO_ABOUT_TICKETS_GROUP_NAME = "InfoAboutTicketsGroupName";
	public static final String CONTACTS_INFO_GROUP_NAME = "ContactsInfoGroupName";

	private static final String BRANCH_TO_ROUTE_DELEMITER = ";";
	private static final String SELECTED_VALUE_DELEMITER = "@";
	private static final String URL_PARAMETER_DELIMITER = "&";

	private static final ListValue EMPTY_BRANCH_LIST_VALUE = new ListValue("�������� �����������", "");;
	private static final ListValue EMPTY_TARIF_LIST_VALUE =  new ListValue("�������� �����", "");
	private static final ListValue EMPTY_TRAIN_LIST_VALUE =  new ListValue("�������� ����", "");

	private static final String EMPTY_TRAINS_ERROR_MESSAGE = "�� ������� ������ �� ������� ������ �� ������������. ����������, �������� ������ ��������� ��� ������� �������.";

	private static final String PHONE_NUMBER_DESCRIPTION_MESSAGE = "������� ����� �������� - 10 ����, ��������, 9065552233.";
	private static final String PHONE_NUMBER_ERROR_MESSAGE = "������� ���������� ����� ��������.";
	private static final String PHONE_NUMBER_REXEXP = "^[0-9]{10}$";
	private static final String E_MAIL_ERROR_MESSAGE = "������� ���������� �������� E-mail";
	private static final String E_MAIL_REXEXP = "^.+@.+\\..+$";

	/**
	 * ������ ����������
	 * @param departDate ���� �������
	 * @return ����� � ���� Document
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Document sendAeroexpressScheduleRequest(Calendar departDate, Integer cityId) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(AEROEXPRESS_SCHEDULE_REQUEST);
		message.addParameter("departDate", XMLDatatypeHelper.formatDateTimeWithoutMillis(departDate));
		if(cityId != null)
			message.addParameter("departTown", cityId);

		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * ���������� ������� ����� ������ ��������
	 * @param payment ������
	 * @param responce �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public static void fillPaymentByAeroexpressSchedule(CardPaymentSystemPayment payment, Document responce) throws GateLogicException, GateException
	{
		final List<ListValue> listValues = new ArrayList<ListValue>();
		listValues.add(EMPTY_BRANCH_LIST_VALUE); // ������ �������� ��������� �� ���������

		try
		{
			XmlHelper.foreach(XmlHelper.selectSingleNode(responce.getDocumentElement(), "branches"), "branch", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					Map<Integer, String> stations = parseStationsElement(element);
					int branchId = Integer.parseInt(XmlHelper.getSimpleElementValue(element, "branchId"));

					NodeList routeNodeList = XmlHelper.selectNodeList(XmlHelper.selectSingleNode(element, "routes"), "route");
					for(int i = 0; i < routeNodeList.getLength(); i++)
					{
						Element route = (Element) routeNodeList.item(i);

						int firstStationId = Integer.parseInt(XmlHelper.getSimpleElementValue(route, "firstStationId"));
						int lastStationId = Integer.parseInt((XmlHelper.getSimpleElementValue(route, "lastStationId")));
						int routeId = Integer.parseInt((XmlHelper.getSimpleElementValue(route, "routeId")));

						listValues.add(new ListValue(
								stations.get(firstStationId) + " - " + stations.get(lastStationId),
								branchId + BRANCH_TO_ROUTE_DELEMITER + routeId));
					}
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		BillingPaymentHelper.getExtendedFields(payment).add(createBranchField(listValues));
	}

	private static Map<Integer, String> parseStationsElement(Element stationsElement) throws GateException
	{
		final Map<Integer, String> stations = new HashMap<Integer, String>();
		try
		{
			XmlHelper.foreach(XmlHelper.selectSingleNode(stationsElement, "stations"), "station", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					Integer stationId = Integer.valueOf(XmlHelper.getSimpleElementValue(element, "stationId"));
					String strStationName = XmlHelper.getSimpleElementValue(element, "stationName");

					stations.put(Integer.valueOf(stationId), strStationName);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return stations;
	}

	private static Field createBranchField(List<ListValue> listValues)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(BRANCH_ROUTE_FIELD_NAME);
		field.setName("�����������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setSaveInTemplate(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);
		field.setValues(listValues);
		return field;
	}

	/**
	 * ������ ����������
	 * @return ����� � ���� ���������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Document sendAeroexpressPricelistRequest() throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(AEROEXPRESS_PRICELIST_REQUEST);
		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * ����������� ������� ����� ������ ������
	 * @param payment ������
	 * @param responce ����� �� ������ �����-�����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public static void fillPaymentByAeroexpressPricelist(CardPaymentSystemPayment payment, Document responce) throws GateLogicException, GateException
	{
		List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);

		final List<ListValue> tarifs = new ArrayList<ListValue>();
		tarifs.add(EMPTY_TARIF_LIST_VALUE); // �������� �� ���������
		// ��������� ������ � ����������� ��� ��������� �����
		final List<ListValue> seatsSelectTarifs = new ArrayList<ListValue>();
		final List<ListValue> maxTicketsByTarif = new ArrayList<ListValue>();
		final List<ListValue> orderTypeByTarif = new ArrayList<ListValue>();

		try
		{
			XmlHelper.foreach(responce.getDocumentElement(), "price", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String priceId = XmlHelper.getSimpleElementValue(element, "priceId");

					String isSeatsSelect = XmlHelper.getSimpleElementValue(element, "seatsSelect");
					// ���� ����� ������������ ����� ����, � ��������� ����������� � false, �� ����������
					if(Boolean.parseBoolean(isSeatsSelect) && !ConfigFactory.getConfig(DocumentConfig.class).isSupportChoiceSeatTarif())
						return;

					seatsSelectTarifs.add(new ListValue(isSeatsSelect, priceId));

					String name = XmlHelper.getSimpleElementValue(element, "name");
					tarifs.add(new ListValue(name, priceId));

					String maxTickets = XmlHelper.getSimpleElementValue(element, "maxTickets");
					maxTicketsByTarif.add(new ListValue(maxTickets, priceId));

					String orderType = XmlHelper.getSimpleElementValue(element, "orderType");
					orderTypeByTarif.add(new ListValue(orderType, priceId));
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		extendedFields.add(createTarifListField(tarifs));
		// ��������� ��������� ���� � ����������� ��� ��������� �����
		extendedFields.add(createSeatsSelectByTrarifField(seatsSelectTarifs));
		extendedFields.add(createMaxTicketsByTarifField(maxTicketsByTarif));
		extendedFields.add(createOrderTypeByTarifField(orderTypeByTarif));
	}

	/**
	 * ��������� ���� � ������������ �� ������������� �������� � ������������� �����������
	 * @param branchRoute ���� � ������������
	 * @return ���� �� ������������� ����������� � ������������� ��������
	 */
	public static Pair<Integer, Integer> parseBranchRoute(String branchRoute)
	{
		if(StringHelper.isEmpty(branchRoute))
			return null;

		String[] items = branchRoute.split(BRANCH_TO_ROUTE_DELEMITER, 2);
		return new Pair<Integer, Integer>(Integer.valueOf(items[0]), Integer.valueOf(items[1]));

	}

	/**
	 * ������ ��������� ����
	 * @param departDate ���� �������
	 * @param priceId ������������� �������� �� �����-�����
	 * @return ����� � ���� Document
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Document sendAeroexpressFreeSeatsGlobalRequest(Calendar departDate, int priceId) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(AEROEXPRESS_FREE_SEATS_GLOBAL_REQUEST);
		message.addParameter("departDate", XMLDatatypeHelper.formatDateTimeWithoutMillis(departDate));
		message.addParameter("priceId", priceId);

		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * ������ ���������������� ���������� � ��������� ������(�� ���� ������ �������� ����)
	 * @param departDate ����
	 * @param scheduleId ������������� ����� �� ������ �� ������ AeroexpressFreeSeatsGlobalResponse
	 * @return ����� � ���� Document
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Document sendAeroexpressFreeSeatsDetailRequest(Calendar departDate, int scheduleId) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(AEROEXPRESS_FREE_SEATS_DETAIL_REQUEST);
		message.addParameter("departDate", XMLDatatypeHelper.formatDateTimeWithoutMillis(departDate));
		message.addParameter("scheduleId", scheduleId);

		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * ���������� ������� �� ������ �� ������ ����������������� ������� ��������� ����
	 * @param payment ������
	 * @param responce ����� �� ������
	 * @param extendedFieldsMap ��� extendedFields(������ ����� �� ����������� �� ����� � �� ������ ���� ������)
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public static void fillPaymentByAeroexpressFreeSeatsDetail(CardPaymentSystemPayment payment, Document responce, Map<String, Field> extendedFieldsMap) throws GateLogicException, GateException
	{
		Element root = responce.getDocumentElement();

		Field priceId = extendedFieldsMap.get(AeroexpressHelper.TARIF_FIELD_NAME);
		ListField maxTicketsByTarifField = (ListField) extendedFieldsMap.get(AeroexpressHelper.TARIF_MAX_TICKETS_FIELD_NAME);
		String maxCountStr = AeroexpressHelper.getValueByIdFromListField(maxTicketsByTarifField, (String) priceId.getValue());

		BillingPaymentHelper.getExtendedFields(payment).add(createChoiceSeatsField(getSeats(root), Integer.parseInt(maxCountStr)));
	}

	/**
	 * ������ �� ��������� ������ ������ � ����� ����� �������
	 * @param departDate ���� �������
	 * @param priceId ������������� ������
	 * @param orderType ��� ������
	 * @param scheduleId ������������� ������
	 * @param seats �������������� ����
	 * @return ����� � ���� Document
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Document sendAeroexpressGetTicketRequest(Calendar departDate, int priceId, int orderType, Integer scheduleId, List<String> seats) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(AEROEXPRESS_GET_TICKETS_REQUEST);
		message.addParameter("departDate", XMLDatatypeHelper.formatDateTimeWithoutMillis(departDate));
		message.addParameter("priceId", priceId);
		message.addParameter("orderType", orderType);

		if(CollectionUtils.isNotEmpty(seats))
		{
			Document request = message.getDocument();
			Element seatsElement = XmlHelper.appendSimpleElement(request.getDocumentElement(), "seats");

			for(String seatId : seats)
			{
				Element seatElement = request.createElement("seat");
				XmlHelper.appendSimpleElement(seatElement, "seatNumber", seatId);
				seatsElement.appendChild(seatElement);
			}
		}

		if(scheduleId != null)
			message.addParameter("scheduleId", scheduleId);

		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * ��������� ������ �� ������ �� ������ ��������� ����� ����� � ������ ������
	 * @param payment ������
	 * @param responce �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public static void fillPaymentByAeroexpressGetTicket(CardPaymentSystemPayment payment, Document responce) throws GateLogicException, GateException
	{
		Element root = responce.getDocumentElement();
		List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);
		
		int orderId = Integer.parseInt(XmlHelper.getSimpleElementValue(root, "orderId"));
		extendedFields.add(0, createOrderNumberField(orderId));

		String amount = XmlHelper.getSimpleElementValue(root, "summa");
		extendedFields.add(1,BillingPaymentHelper.createAmountField("�����", false, amount));

		// ��������� � ����������, ���� �� ���������� ����� ����������� ����� ���������
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		payment.setDestinationAmount(new Money(new BigDecimal(amount), currencyService.getNationalCurrency()));
	}

	/**
	 * �������������� ������� �� ��������� ����
	 * @param payment ������
	 * @param extendedFieldsMap ��� ����������� ��������� �������(���� �� ����������� �� ����� � �� ������ ���� ������)
	 */
	public static void completeAdditionProcess(CardPaymentSystemPayment payment, Map<String, Field> extendedFieldsMap) throws GateException
	{
		List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);

		// ���� � ������������ ����� ������ ����� ���������� �� ����� ������������� �������� - ������ ��������� �����
		// ������� ��� ��������, � ��������� ����, ������� ����� ������������ ������ ����
		CommonField choicePlacesField = (CommonField) extendedFieldsMap.get(CHOICE_PLACE_FIELD_NAME);
		if(choicePlacesField != null)
		{
			choicePlacesField.setVisible(false);
			String[] values = ((String)choicePlacesField.getValue()).split(SELECTED_VALUE_DELEMITER);
			extendedFields.add(extendedFields.indexOf(choicePlacesField) + 1, createVisibleSelectedPlaces(values));
		}
	}

	/**
	 * ������� ���� ��� ����������� ��������� ����
	 * @param selectedPlaces ��������� �����
	 * @return ������� ����
	 */
	public static Field createVisibleSelectedPlaces(String[] selectedPlaces)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(VISIBLE_SELECTED_PLACES_FIELD_NAME);
		field.setName("������� �����");
		field.setRequired(true);
		field.setVisible(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);

		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < selectedPlaces.length; i++)
			builder.append(i == 0 ? "": ",").append(selectedPlaces[i]);

		field.setValue(builder.toString());
		field.setDefaultValue(builder.toString());

		return field;
	}

	/**
	 * �������� ������� �� ��������� �������
	 * @param payment ������, �� �������� ���������
	 * @return ����� � ���� Document
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Document sendExecutionRequest(CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(AEROEXPRESS_PAY_TICKETS_REQUEST);

		Map<String, Field> extendedFields =
				BillingPaymentHelper.convertExtendedFieldsToMap(BillingPaymentHelper.getExtendedFields(payment));
		//��� ������� (��������)
		RequestHelper.appendRouteCode(message, "Route", Long.valueOf(payment.getReceiverPointCode()));
		//���������� �� ����� ��������
		RequestHelper.appendCardInf(message, payment.getChargeOffCard(), payment.getChargeOffCardExpireDate());
		// orderId
		Field orderId = extendedFields.get(AeroexpressHelper.ORDER_NUMBER_FIELD_NAME);
		message.addParameter("orderId", orderId.getValue());
		// summa
		RequestHelper.appendSumma(message, payment.getDestinationAmount(), "summa");
		// email
		Field email = extendedFields.get(AeroexpressHelper.EMAIL_FIELD_NAME);
		if(StringHelper.isNotEmpty((String) email.getValue()))
			message.addParameter("e-mail", email.getValue());
		// phone
		Field phone = extendedFields.get(AeroexpressHelper.PHONE_NUMBER_FIELD_NAME);
		if(StringHelper.isNotEmpty((String) phone.getValue()))
			message.addParameter("phone", phone.getValue());

		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * ���������� ������� �� ������ �� ������ ����������
	 * @param responce �����
	 * @param payment ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static void fillPaymentByExecutionResponce(Document responce, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		final List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);

		try
		{
			XmlHelper.foreach(responce.getDocumentElement(), "ticket", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String mainLink = XmlHelper.getSimpleElementValue(element, "ticketUrl");
					String id = XmlHelper.getSimpleElementValue(element, "ticketId");
					String ticketGUID = XmlHelper.getSimpleElementValue(element, "ticketGUID");
					String token = XmlHelper.getSimpleElementValue(element, "token");
					String orderId = (String) BillingPaymentHelper.getFieldById(extendedFields, ORDER_NUMBER_FIELD_NAME).getValue();

					// �� ���������� �� ���������� ticketUrl ������ ���� ��� �� ������ "?" � �����
					StringBuilder resultLink = new StringBuilder(mainLink)
							.append("type=html").append(URL_PARAMETER_DELIMITER)
							.append("oid=").append(orderId).append(URL_PARAMETER_DELIMITER)
							.append("guid=").append(ticketGUID).append(URL_PARAMETER_DELIMITER)
							.append("token=").append(token);

					extendedFields.add(createTicketField(id, resultLink.toString()));
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public static Field createTicketField(String id, String url)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.link);
		field.setExternalId(TICKET_PREFIX + id);
		field.setName("������");
		field.setVisible(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);

		String value = "���������� � ������|" + url;
		field.setValue(value);
		field.setDefaultValue(value);

		return field;
	}


	/**
	 * �������� ���� "����� ������"
	 * @param orderId ����� ������
	 * @return ����
	 */
	public static Field createOrderNumberField(int orderId)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.integer);
		field.setExternalId(ORDER_NUMBER_FIELD_NAME);
		field.setName("����� ������");
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(false);
		field.setKey(true);
		field.setValue(Integer.toString(orderId));
		field.setDefaultValue(Integer.toString(orderId));

		return field;
	}



	/**
	 * ��������� ������� ������ ������ ����� � ��������� � ��� ���������� �������
	 * @param payment ������
	 * @param responce ����� �� ������ ��������� ����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public static void fillPaymentByAeroexpressFreeSeatsGlobal(CardPaymentSystemPayment payment, Document responce) throws GateLogicException, GateException
	{
		List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);

		final List<ListValue> trains = new ArrayList<ListValue>();
		trains.add(EMPTY_TRAIN_LIST_VALUE);

		try
		{
			Element trainsElement = XmlHelper.selectSingleNode(responce.getDocumentElement(), "trains");
			if(trainsElement == null)
				throw new GateLogicException(EMPTY_TRAINS_ERROR_MESSAGE);

			XmlHelper.foreach(trainsElement, "train", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String scheduleId = XmlHelper.getSimpleElementValue(element, "scheduleId");
					String trip = XmlHelper.getSimpleElementValue(element, "trip");

					trains.add(new ListValue(trip, scheduleId));
				}
			});
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		extendedFields.add(createTrainsListField(trains));
	}

	private static List<Pair<String, String>> getSeats(Element root) throws GateException, GateLogicException
	{
		final List<Pair<String, String>> seats = new ArrayList<Pair<String, String>>();
		try
		{
			Element coachesElement = XmlHelper.selectSingleNode(root, "coaches");
			if(coachesElement == null)
				throw new GateLogicException(EMPTY_TRAINS_ERROR_MESSAGE);
			
			Element coachElement = XmlHelper.selectSingleNode(coachesElement, "coach");
			if(coachElement == null)
				throw new GateLogicException(EMPTY_TRAINS_ERROR_MESSAGE);

			Element seatsElement = XmlHelper.selectSingleNode(coachElement, "seats");
			if(seatsElement == null)
				throw new GateLogicException(EMPTY_TRAINS_ERROR_MESSAGE);

			XmlHelper.foreach(seatsElement, "seat", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					seats.add(new Pair<String, String>(
							XmlHelper.getSimpleElementValue(element, "seatNumber"),
							XmlHelper.getSimpleElementValue(element, "status")));
				}
			});
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return seats;
	}

	private static String seatsToString(List<Pair<String, String>> seats)
	{
		StringBuilder builder = new StringBuilder();
		if(CollectionUtils.isEmpty(seats))
			return builder.toString();

		for(Pair<String, String> seat : seats)
		{
			builder.append(seat.getFirst())
					.append(",")
					.append(seat.getSecond())
					.append(";");
		}

		return builder.toString();
	}

	/**
	 * @param maxCount ������������ ���������� ��������� ����
	 * @return ����-������ ������ ���������� ���� ��� ��������������
	 */
	public static Field createChoiceCountFreeSeatsField(int maxCount)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(COUNT_PLACE_FIELD_NAME);
		field.setName("���������� �������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setSaveInTemplate(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);

		List<ListValue> values = new ArrayList<ListValue>();
		for(int i = 1; i <= maxCount; i++)
		{
			String value = Integer.toString(i);
			values.add(new ListValue(value, value));
		}

		field.setValues(values);

		return field;
	}

	/**
	 * @param seats ����� � �� ���������
	 * @return ���� ��� ������ ��������� ����
	 */
	public static Field createChoiceSeatsField(List<Pair<String, String>> seats, int maxCount)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.graphicset);
		field.setExternalId(CHOICE_PLACE_FIELD_NAME);
		field.setGraphicTemplateName("aeroexpress-car");
		field.setName("����� ����");
		field.setRequired(false);
		field.setEditable(true);
		field.setVisible(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);

		List<ListValue> values = new ArrayList<ListValue>(seats.size());
		// ��������� ��������� ����
		if(CollectionUtils.isNotEmpty(seats))
		{
			for(Pair<String, String> seat : seats)
				values.add(new ListValue(seat.getFirst() + ";" + seat.getSecond(), "place"));
		}
		// ��������� ��������� ������������� ���������� ����
		values.add(new ListValue(Integer.toString(maxCount),"maxCount"));
		field.setValues(values);

		return field;
	}



	/**
	 * @return ���� ��� ����� ������ ��������
	 */
	public static Field createPhoneNumberField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(PHONE_NUMBER_FIELD_NAME);
		field.setName("����� ��������");
		field.setEditable(true);
		field.setVisible(true);
		field.setGroupName(CONTACTS_INFO_GROUP_NAME);
		field.setMask("###***####");
		field.setDescription(PHONE_NUMBER_DESCRIPTION_MESSAGE);
		field.setFieldValidationRules(
				Collections.singletonList(BillingPaymentHelper.createRegexpValidator(PHONE_NUMBER_REXEXP, PHONE_NUMBER_ERROR_MESSAGE)));

		return field;
	}

	/**
	 * @return ���� ��� ����� ����������� �����
	 */
	public static Field createEmailField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.email);
		field.setExternalId(EMAIL_FIELD_NAME);
		field.setName("E-mail");
		field.setEditable(true);
		field.setVisible(true);
		field.setGroupName(CONTACTS_INFO_GROUP_NAME);
		
		field.setFieldValidationRules(
				Collections.singletonList(BillingPaymentHelper.createRegexpValidator(E_MAIL_REXEXP, E_MAIL_ERROR_MESSAGE)));

		return field;
	}

	public static List<Field> getContanctInfoFields()
	{
		List<Field> result = new ArrayList<Field>();

		result.add(RequestHelper.createDisableTextField(
					CONTACTS_INFO_GROUP_NAME, "", "�������� ��� �������� ���������� � ������", CONTACTS_INFO_GROUP_NAME));
		result.add(createPhoneNumberField());
		result.add(createEmailField());

		return result;
	}

	/**
	 * @return ����-���������� � ��������� ������� �������
	 */
	public static Field createAgreementField()
	{
		CommonField field = BillingPaymentHelper.createAgreementField(
				AGREEMENT_FIELD_NAME, "� �������� � [url]���������[/url] ������� �������", "aeroexpress-rule");
		// �.�. ��������� ��������, �������� ����� ��� ������������ �������� ���������
		// AeroexpressPaymentSender#formCurrentState
		field.setRequired(false);
		return field;
	}

	/**
	 * @return ���� ��� ������ ����
	 */
	public static Field createDateField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.date);
		field.setExternalId(DATE_FIELD_NAME);
		field.setName("���� �������");
		field.setMaxLength(10L);
		field.setMinLength(10L);
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setSaveInTemplate(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);

		return field;
	}

	/**
	 * @return ���� ��� ������ ������
	 */
	public static Field createCityField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(CITY_FIELD_NAME);
		field.setName("�����");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setSaveInTemplate(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);

		List<ListValue> values = new ArrayList<ListValue>();
		values.add(new ListValue("������", "1"));
		field.setValues(values);

		return field;
	}

	/**
	 * ������� ���� ��� ������ ������
	 * @param values ��������
	 * @return ���� ��� ������ ������
	 */
	public static Field createTarifListField(List<ListValue> values)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(TARIF_FIELD_NAME);
		field.setName("�����");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setSaveInTemplate(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);
		field.setValues(values);

		return field;
	}

	/**
	 * ������� ���� c ������������� ������ � ������ ��� ������ ����
	 * @param values ��������
	 * @return ���� c ������������� ������ � ������ ��� ������ ����
	 */
	public static Field createSeatsSelectByTrarifField(List<ListValue> values)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(TARIF_SEATS_SELECT_FIELD_NAME);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);
		field.setValues(values);

		return field;
	}

	/**
	 * ������� ���� � ������������� ������ � ������������� ���������� ������� �� ���� �����
	 * @param values ��������
	 * @return ���� � ������������� ������ � ������������� ���������� ������� �� ���� �����
	 */
	public static Field createMaxTicketsByTarifField(List<ListValue> values)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(TARIF_MAX_TICKETS_FIELD_NAME);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);
		field.setValues(values);

		return field;
	}

	/**
	 * ������� ���� c ������������� ����� � ���� � ������
	 * @param values ��������
	 * @return ���� c ������������� ����� � ���� � ������
	 */
	public static Field createOrderTypeByTarifField(List<ListValue> values)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(ORDER_TYPE_PRICE_FIELD_NAME);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);
		field.setValues(values);

		return field;
	}

	/**
	 * ������� ���� �� ������� ������
	 * @param values ��������
	 * @return ���� �� ������� ������
	 */
	public static Field createTrainsListField(List<ListValue> values)
	{
		CommonField field = new CommonField();
		field.setName("����");
		field.setType(FieldDataType.list);
		field.setExternalId(TRIP_TIME_FIELD_NAME);
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setGroupName(INFO_ABOUT_TICKETS_GROUP_NAME);
		field.setValues(values);

		return field;
	}

	private static List<String> getChoicePlaceList(int selectedCountPlace)
	{
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < selectedCountPlace; i++)
			result.add("0");

		return result;
	}

	private static List<String> getChoicePlaceList(String selectedPlaces)
	{
		if(StringHelper.isEmpty(selectedPlaces))
			return Collections.emptyList();

		List<String> result = new ArrayList<String>();
		for(String value : selectedPlaces.split(SELECTED_VALUE_DELEMITER))
			result.add(value);

		return result;
	}

	public static List<String> getSeats(Map<String, Field> extendedFieldsMap)
	{
		if(extendedFieldsMap.get(CHOICE_PLACE_FIELD_NAME) != null)
		{
			Field selectedPlaces = extendedFieldsMap.get(CHOICE_PLACE_FIELD_NAME);
			return getChoicePlaceList((String)selectedPlaces.getValue());
		}
		else           
		{
			Field selectedCountPlaces = extendedFieldsMap.get(COUNT_PLACE_FIELD_NAME);
			return getChoicePlaceList(Integer.valueOf((String)selectedCountPlaces.getValue()));
		}
	}

	/**
	 * �������� �������� �� id �� ������ �������� � ��������� ����
	 * @param listField ��������� ����
	 * @param id �������������
	 * @return value
	 * @throws GateException
	 */
	public static String getValueByIdFromListField(ListField listField, String id) throws GateException
	{
		if(id == null)
			throw new IllegalArgumentException("id �� ������ ���� null");

		for(ListValue value : listField.getValues())
		{
			if(id.equals(value.getId()))
				return value.getValue();
		}

		return null;
	}

	/**
	 * �������� ���� �� ������������
	 * @param dateField ���� ����
	 * @param errorMessage ��������� ��� ������ ��� ���������� ����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static void checkTravelDate(Field dateField, String errorMessage) throws GateException, GateLogicException
	{
		if(dateField == null)
			throw new IllegalArgumentException("dateField �� ����� ���� null");

		if(!DATE_FIELD_NAME.equals(dateField.getExternalId()) || dateField.getType() != FieldDataType.date)
			throw new IllegalArgumentException("dateField �� �������� \"����� �������\"");

		try
		{
			if(DateHelper.getCurrentDate().compareTo(DateHelper.parseCalendar((String)dateField.getValue())) > 0)
				throw new GateLogicException(errorMessage);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}
}
