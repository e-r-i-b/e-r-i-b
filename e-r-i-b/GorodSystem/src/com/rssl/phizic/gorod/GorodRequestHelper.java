package com.rssl.phizic.gorod;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.CalendarFieldPeriod;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.gorod.messaging.Constants;
import com.rssl.phizic.gorod.messaging.XMLMessagingService;
import com.rssl.phizic.gorod.recipients.FieldWithByNameAndType;
import com.rssl.phizic.gorod.recipients.GorodFieldComparator;
import com.rssl.phizic.gorod.recipients.GorodSubServiceFieldComparator;
import com.rssl.phizic.gorod.recipients.GorodSubServiceFieldWithByNameAndTypeComparator;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author khudyakov
 * @ created 17.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class GorodRequestHelper
{
	private static final String TERMINAL_ID_ATTRIBUTE_NAME = "com.rssl.gate.terminal.id";
	private static final String IS_DEBT_NEGATIVE_PROPERTY_NAME = "com.rssl.gate.gorod.is.debt.negative";

	private final XMLMessagingService messagingService;
	private final CurrencyService currencyService;

	public GorodRequestHelper(GateFactory factory) throws GateException
	{
		messagingService = XMLMessagingService.getInstance(factory);
		currencyService  = factory.service(CurrencyService.class);
	}

	/**
	 * Формирование запроса ReqCard
	 * @param cardId идентификатор карты
	 * @return запрос ReqCard
	 * @throws GateException
	 */
	public GateMessage buildReqCardRequest(String cardId) throws GateException
	{
		GateMessage msg = messagingService.createRequest("ReqCard");

		Document doc = msg.getDocument();
		Element root = doc.getDocumentElement();
		Element card = doc.createElement("Card");
		root.appendChild(card);

		Element pan = doc.createElement("pan");
		pan.setTextContent(cardId);
		card.appendChild(pan);

		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);

		Element moreInfo = doc.createElement("moreInfo");
		moreInfo.setAttribute("request", "true");
		moreInfo.setAttribute("maxItems", "all");
		moreInfo.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfLinkAbonent/Abonent/Service");
		moreInfo.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo);

		Element moreInfo1 = (Element) moreInfo.cloneNode(false);
		moreInfo1.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfLinkAbonent/Abonent/Service/Provider/Org");
		listOfMoreInfo.appendChild(moreInfo1);

		Element moreInfo2 = (Element) moreInfo.cloneNode(false);
		moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfAddService/AddService/Service/Provider/Org/ListOfOrgAccount/OrgAccount");
		listOfMoreInfo.appendChild(moreInfo2);

		return msg;
	}

	/**
	 * Формирование запроса ReqGlobal
	 * @param codeService     код услуги
	 * @param licAccountValue значение кличевого поля licAccount
	 * @return запрос ReqGlobal
	 * @throws GateException
	 */
	public GateMessage buildReqGlobalRequest(String codeService, String licAccountValue) throws GateException
	{
		GateMessage message = messagingService.createRequest("ReqGlobal");

		Document doc = message.getDocument();
		Element root = doc.getDocumentElement();

		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);

		Element moreInfo = doc.createElement("moreInfo");
		moreInfo.setAttribute("request", "true");
		moreInfo.setAttribute("maxItems", "all");
		moreInfo.setAttribute("xpRef", "/RSASMsg/AnsGlobal/Global/ListOfAbonent");
		moreInfo.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo);

		Element filter = doc.createElement("ListOfFilterVal");

		Element val1 = doc.createElement("val");
		val1.setAttribute("code", "SERVICEID");
		Text text = doc.createTextNode(codeService.substring(codeService.indexOf("-")+1));
		val1.appendChild(text);
		filter.appendChild(val1);

		Element val2 = doc.createElement("val");
		val2.setAttribute("code", "ACCOUNT");
		Text text2 = doc.createTextNode(licAccountValue);
		val2.appendChild(text2);
		filter.appendChild(val2);

		Element val3 = doc.createElement("val");
		val3.setAttribute("code", "plusAddress");
		val3.appendChild(doc.createTextNode("false"));
		filter.appendChild(val3);
		moreInfo.appendChild(filter);

		Element moreInfo1 = doc.createElement("moreInfo");
		moreInfo1.setAttribute("request", "true");
		moreInfo1.setAttribute("maxItems", "all");
		moreInfo1.setAttribute("xpRef", "/RSASMsg/AnsGlobal/Global/ListOfAbonent/Abonent/Debt");
		moreInfo1.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo1);

		Element moreInfo2 = doc.createElement("moreInfo");
		moreInfo2.setAttribute("request", "true");
		moreInfo2.setAttribute("maxItems", "all");
		moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsGlobal/Global/ListOfAbonent/Abonent/Debt/ListOfSubDebt/SubDebt/SubService/ListOfExtent/Extent/ListOfPrmVal/val");
		moreInfo2.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo2);

		Element moreInfo3 = doc.createElement("moreInfo");
		moreInfo3.setAttribute("request", "true");
		moreInfo3.setAttribute("maxItems", "all");
		moreInfo3.setAttribute("xpRef", "/RSASMsg/AnsGlobal/Global/ListOfAbonent/Abonent/Service/ListOfSubService/SubService");
		moreInfo3.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo3);

		Element moreInfo4 = doc.createElement("moreInfo");
		moreInfo4.setAttribute("request", "true");
		moreInfo4.setAttribute("maxItems", "all");
		moreInfo4.setAttribute("xpRef", "/RSASMsg/AnsGlobal/Global/ListOfAbonent/Abonent/Service/ExtentType/ListOfExtent/Extent/ListOfPrmVal/val");
		moreInfo4.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo4);

		Element terminalId = doc.createElement("TerminalID");
		terminalId.appendChild(doc.createTextNode(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(TERMINAL_ID_ATTRIBUTE_NAME)));
		root.appendChild(terminalId);

		return message;
	}

    /**
     * Формирование запроса ReqXObject,
     * запрос доп. полей по коду поставщика услуг
     * @param codeService код сервиса
     * @return запрос ReqXObject
     * @throws GateException
     * @throws GateLogicException
     */
    public GateMessage buildReqXObjectRequest(String codeService, boolean mainService) throws GateException, GateLogicException
    {
        GateMessage message = messagingService.createRequest("ReqXObject");

        Document doc = message.getDocument();
        Element root = doc.getDocumentElement();

        Element xObject = doc.createElement("XObject");
	    //непонятное поведение города
        xObject.setAttribute("id", mainService ? ("Service-" + codeService) : ("AddService-" + codeService));
        root.appendChild(xObject);

        Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
        listOfMoreInfo.setAttribute("request", "false");
        root.appendChild(listOfMoreInfo);

        String xpRef1 = mainService ?
                "/RSASMsg/AnsXObject/XObject/Service/ExtentType/ListOfExtent/Extent/ListOfPrmVal/Val" :
                "/RSASMsg/AnsXObject/XObject/AddService/Service/ExtentType/ListOfExtent/Extent/ListOfPrmVal/Val";

        Element moreInfo = doc.createElement("moreInfo");
        moreInfo.setAttribute("request", "true");
        moreInfo.setAttribute("maxItems", "all");
        moreInfo.setAttribute("xpRef", xpRef1);
        moreInfo.setAttribute("action", "stop");
        listOfMoreInfo.appendChild(moreInfo);

        String xpRef2 = mainService ?
                "/RSASMsg/AnsXObject/XObject/Service/AgrPU/ListOfAttrVal/AGroup/val" :
                "/RSASMsg/AnsXObject/XObject/AddService/Service/AgrPU/ListOfAttrVal/AGroup/val";

        Element moreInfo1 = doc.createElement("moreInfo");
        moreInfo1.setAttribute("request", "true");
        moreInfo1.setAttribute("maxItems", "all");
        moreInfo1.setAttribute("xpRef", xpRef2);
        moreInfo1.setAttribute("action", "stop");
        listOfMoreInfo.appendChild(moreInfo1);

        return message;
    }

	/**
	 * Формирование запроса ReqXObject
     * запрос доп. полей по полю связке abonentId и
     * коду поставщика услуг
	 * @param abonentId   идентификатор (связка между поставщиком и услугой)
	 * @param codeService код услуги
	 * @return запрос ReqXObject
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public GateMessage buildReqXObjectRequest(String abonentId, String codeService, boolean mainService) throws GateException, GateLogicException
	{
		GateMessage message = messagingService.createRequest("ReqXObject");

		Document doc = message.getDocument();
		Element root = doc.getDocumentElement();

		Element xObject = doc.createElement("XObject");
		//непонятное поведение города
		xObject.setAttribute("id", mainService ? abonentId : ("AddService-" + codeService));
		root.appendChild(xObject);

		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);

		String xpRef1 = mainService ?
				"/RSASMsg/AnsXObject/XObject/Abonent/Service/ExtentType/ListOfExtent/Extent/ListOfPrmVal/Val" :
				"/RSASMsg/AnsXObject/XObject/AddService/Service/ExtentType/ListOfExtent/Extent/ListOfPrmVal/Val";

		Element moreInfo = doc.createElement("moreInfo");
		moreInfo.setAttribute("request", "true");
		moreInfo.setAttribute("maxItems", "all");
		moreInfo.setAttribute("xpRef", xpRef1);
		moreInfo.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo);

		String xpRef2 = mainService ?
				"/RSASMsg/AnsXObject/XObject/Abonent/Service/AgrPU/ListOfAttrVal/AGroup/val" :
				"/RSASMsg/AnsXObject/XObject/AddService/Service/AgrPU/ListOfAttrVal/AGroup/val";

		Element moreInfo1 = doc.createElement("moreInfo");
		moreInfo1.setAttribute("request", "true");
		moreInfo1.setAttribute("maxItems", "all");
		moreInfo1.setAttribute("xpRef", xpRef2);
		moreInfo1.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo1);

		if (mainService)
		{
			Element moreInfo2 = doc.createElement("moreInfo");
			moreInfo2.setAttribute("request", "true");
			moreInfo2.setAttribute("maxItems", "all");
			moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/Abonent/Service/ListOfSubService/SubService");
			moreInfo2.setAttribute("action", "stop");
			listOfMoreInfo.appendChild(moreInfo2);
		}

		return message;
	}

	//TODO в идеале бы хотелось использовать один и тот же метод для формирования запроса ReqCalcEOrder для режимов calcmode (1,2),
	//TODO но не получается... скопировал как есть, обязательно необходим рефакторинг
	/**
	 * Формирование запроса ReqCalcEOrder
	 * @param abonentId       идентификатор (связка услуги и поставщика услуг)
	 * @param recipientId     идентификатор поставщика услуг
	 * @param extendedFields  доп. поля
	 * @return запрос ReqCalcEOrder
	 * @throws GateException
	 */
	public GateMessage buildReqCalcEOrderCalcMode1Request(String abonentId, String recipientId, List<FieldWithByNameAndType> extendedFields, boolean mainService) throws GateException
	{
		GateMessage message = messagingService.createRequest("ReqCalcEOrder");
		Document document = message.getDocument();
		Element root = document.getDocumentElement();

		Element eOrder = document.createElement("EOrder");
		root.appendChild(eOrder);

		Element payOrder = document.createElement("PayOrder");
		payOrder.setAttribute("amount", "0");
		payOrder.setAttribute("calcmode", "1");
		eOrder.appendChild(payOrder);

		Element abonent = document.createElement(mainService ? "Abonent" : Constants.ADD_SERVICE_PREFIX);
		abonent.setAttribute("id", getPointCode(abonentId, recipientId, mainService));
		payOrder.appendChild(abonent);

		if (!mainService)
			addPayerInfo(payOrder, null, "Payer");

		payOrder.appendChild(isComplexService(extendedFields) ?
				createListOfSubOrderElement(document, extendedFields) : createMainOrderElement(document, extendedFields));

		return message;
	}

	/**
	 * построить структуру ListOfSubOrder для сложной услуги
	 * @param document документ
	 * @param extendedFields допполя
	 * @return структура ListOfSubOrder
	 */
	public Element createListOfSubOrderElement(Document document, List<FieldWithByNameAndType> extendedFields)
	{
		//обнуляем параметры перед формированием структуры
		String subServiceId = "";
		Element subOrder = null;
		Element subService = null;
		Element mainOrder = null;
		Element listOfOrderExtent = null;
		//начало структуры
		Element listOfSubOrder = document.createElement("ListOfSubOrder");
		//упорядочиваем список допполей
		Collections.sort(extendedFields, new GorodSubServiceFieldWithByNameAndTypeComparator());

		for (FieldWithByNameAndType extendedField : extendedFields)
		{
			Field field = extendedField.getField();
			//наши поля не обрабатываем
			if (!isOurField(field.getExternalId()))
			{
				String[] idArray = field.getExternalId().split(Constants.SS_DELIMITER);
				String ssPrefixWithId = idArray[0];
				//если очередной элемент списка не содержит идентификатор подуслуги, значит, начинаем струтктуру для новой подуслуги
				if (StringHelper.isEmpty(subServiceId) || !ssPrefixWithId.endsWith(subServiceId))
				{
					if (subOrder != null)
						listOfSubOrder.appendChild(subOrder);
					//получаем идентификатор подуслуги
					subServiceId = ssPrefixWithId.substring(Constants.SS_PREFIX.length(), ssPrefixWithId.length());
					//создаем структуру SubOrder
					subOrder = document.createElement("SubOrder");
					//создаем структуру SubService с ИД подуслуги
					subService = document.createElement("SubService");
					subService.setAttribute("id", subServiceId);
					subOrder.appendChild(subService);
					//создаем структуру MainOrder
					mainOrder = document.createElement("MainOrder");
					mainOrder.setAttribute("amount", "0");
					subOrder.appendChild(mainOrder);
					//строим структуру ListOfOrderExtent
					listOfOrderExtent = document.createElement("ListOfOrderExtent");
					mainOrder.appendChild(listOfOrderExtent);
				}
				//добовляем поля подуслуги
				listOfOrderExtent.appendChild(createOderExtentElement(document, field.getDefaultValue(), idArray[1]));
			}
		}
		//добавляем последний сформированный элемент
		listOfSubOrder.appendChild(subOrder);
		return listOfSubOrder;
	}

	/**
	 * построить структуру ListOfSubOrder для сложной услуги
	 * @param document документ
	 * @param payment платеж
	 * @param hasPaymentPeriods наличие периодов
	 * @param calendarFieldPeriod поле период
	 * @return структура ListOfSubOrder
	 * @throws GateException
	 */
	public Element createListOfSubOrderElement(Document document, AccountPaymentSystemPayment payment, boolean hasPaymentPeriods, CalendarFieldPeriod calendarFieldPeriod) throws GateException
	{
		try
		{
			//обнуляем параметры перед формированием структуры
			String subServiceId = "";
			Element subOrder = null;
			Element subService = null;
			Element mainOrder = null;
			Element listOfOrderExtent = null;
			//начало структуры
			Element listOfSubOrder = document.createElement("ListOfSubOrder");
			//для формирования получаем и упорядочиваем список допполей
			List<Field> extendedFields = new ArrayList<Field>(payment.getExtendedFields());
			Collections.sort(extendedFields, new GorodSubServiceFieldComparator());

			for (Field field : extendedFields)
			{
				if (Constants.CALENDAR_FIELD_NAME.equals(field.getExternalId()))
				{
					hasPaymentPeriods = true;
					calendarFieldPeriod = ((CommonField) field).getPeriod();
					continue;
				}
				//наши поля не обрабатываем
				if (!isOurField(field.getExternalId()))
				{
					String[] idArray = field.getExternalId().split(Constants.SS_DELIMITER);
					String ssPrefixWithId = idArray[0];
					//если очередной элемент списка не содержит идентификатор подуслуги, значит, начинаем струтктуру для новой подуслуги
					if (StringHelper.isEmpty(subServiceId) || !ssPrefixWithId.endsWith(subServiceId))
					{
						if (subOrder != null)
							listOfSubOrder.appendChild(subOrder);
						//получаем идентификатор подуслуги
						subServiceId = ssPrefixWithId.substring(Constants.SS_PREFIX.length(), ssPrefixWithId.length());
						//создаем структуру SubOrder
						subOrder = document.createElement("SubOrder");
						//создаем структуру SubService с ИД подуслуги
						subService = document.createElement("SubService");
						subService.setAttribute("id", subServiceId);
						subOrder.appendChild(subService);
						//создаем структуру MainOrder
						mainOrder = document.createElement("MainOrder");
						//получаем поле сумма подуслуги, что бы передать его значение
						Field amount = BillingPaymentHelper.getFieldById(extendedFields, generateSubServiceAmountExternalId(subServiceId));
						mainOrder.setAttribute("amount", getFieldValue(amount));
						subOrder.appendChild(mainOrder);
						//строим структуру ListOfOrderExtent
						listOfOrderExtent = document.createElement("ListOfOrderExtent");
						mainOrder.appendChild(listOfOrderExtent);
					}
					//добовляем поля подуслуги
					String branch = payment.getReceiverOfficeCode().getFields().get("branch");
					listOfOrderExtent.appendChild(createOderExtentElement(document, getOrderExtentValue(field, branch), idArray[1]));
				}
			}
			//добавляем последний сформированный элемент
			listOfSubOrder.appendChild(subOrder);
			return listOfSubOrder;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * создать структуру MainOrder для простой услуги
	 * @param document документ
	 * @param extendedFields дополнительные поля
	 * @return структура MainOrder
	 */
	private Element createMainOrderElement(Document document, List<FieldWithByNameAndType> extendedFields)
	{
		Element mainOrder = document.createElement("MainOrder");
		mainOrder.setAttribute("amount", "0");

		Element listExtents = document.createElement("ListOfOrderExtent");
		mainOrder.appendChild(listExtents);

		for (FieldWithByNameAndType extendField : extendedFields)
		{
			Field field = extendField.getField();
			//в запрос не добавляем наши (искусственно созданные) поля
			if (!isOurField(extendField.getField().getExternalId()))
				listExtents.appendChild(createOderExtentElement(document, field.getDefaultValue(), field.getExternalId()));
		}
		return mainOrder;
	}

	/**
	 * создать структуру MainOrder для простой услуги
	 * @param document документ
	 * @param payment платеж
	 * @param hasPaymentPeriods наличие периодов
	 * @param calendarFieldPeriod поле Период
	 * @param amount значение суммы ввиде строки
	 * @return структура MainOrder
	 * @throws GateException
	 */
	private Element createMainOrderElement(Document document, AccountPaymentSystemPayment payment, boolean hasPaymentPeriods, CalendarFieldPeriod calendarFieldPeriod, String amount) throws GateException
	{
		try
		{
			Element mainOrder = document.createElement("MainOrder");
			mainOrder.setAttribute("amount", amount);

			Element listExtents = document.createElement("ListOfOrderExtent");
			mainOrder.appendChild(listExtents);

			List<Field> extendedFields = payment.getExtendedFields();
			Collections.sort(extendedFields);
			for (Field field : extendedFields)
			{
				if (Constants.CALENDAR_FIELD_NAME.equals(field.getExternalId()))
				{
					hasPaymentPeriods = true;
					calendarFieldPeriod = ((CommonField) field).getPeriod();
					continue;
				}
				//в запрос не добавляем наши (искусственно созданные) поля
				if (!isOurField(field.getExternalId()))
				{
					String branch = payment.getReceiverOfficeCode().getFields().get("branch");
					listExtents.appendChild(createOderExtentElement(document, getOrderExtentValue(field, branch), field.getExternalId()));
				}
			}
			return mainOrder;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * построить структуру запроса OrderExtent
	 * @param document документ
	 * @param defValue значение
	 * @param id идентификатор
	 * @return структура OrderExtent
	 */
	protected Element createOderExtentElement(Document document, String defValue, String id)
	{
		Element orderExtent = document.createElement("OrderExtent");
		orderExtent.setAttribute("val", defValue);
		Element extent = document.createElement("Extent");
		extent.setAttribute("id", id);
		orderExtent.appendChild(extent);
		return orderExtent;
	}

	/**
	 * Формирование запроса ReqCalcEOrder
	 * @param abonentId идентификатор
	 * @param payment   платеж
	 * @return запрос ReqCalcEOrder
	 * @throws GateException
	 */
	public GateMessage buildReqCalcEOrderCalcMode2Request(String abonentId, AccountPaymentSystemPayment payment, boolean isComplexService,  boolean mainService) throws GateException
	{
		GateMessage message = messagingService.createRequest("ReqCalcEOrder");

		Document document = message.getDocument();
		Element root = document.getDocumentElement();

		Element eOrder = document.createElement("EOrder");
		root.appendChild(eOrder);

		Element payOrder = document.createElement("PayOrder");
		payOrder.setAttribute("amount", convertAmountToString(payment));
		payOrder.setAttribute("calcmode", "2");
		eOrder.appendChild(payOrder);

		Element abonent = mainService ?	document.createElement("Abonent") : document.createElement("AddService");
		abonent.setAttribute("id", getPointCode(abonentId, payment.getService().getCode(), mainService));
		payOrder.appendChild(abonent);

		if (mainService)
			addPayerInfo(payOrder, payment.getExternalOwnerId(), "Person");
		else
			addPayerInfo(payOrder, payment.getExternalOwnerId(), "Payer");

		boolean hasPaymentPeriods = false;
		CalendarFieldPeriod calendarFieldPeriod = null;

		if (isComplexService)
			payOrder.appendChild(createListOfSubOrderElement(document, payment, hasPaymentPeriods, calendarFieldPeriod));
		else
			payOrder.appendChild(createMainOrderElement(document, payment, hasPaymentPeriods, calendarFieldPeriod, convertAmountToString(payment)));

		if (hasPaymentPeriods)
			payOrder.appendChild(createPayPeriodsElement(document, getPayPeriod(payment), calendarFieldPeriod));

		return message;
	}

	/**
	 * Формирование запроса ReqSaveEOrder
	 * @param abonentId идентификатор
	 * @param payment платеж
	 * @return запрос ReqSaveEOrder
	 * @throws GateException
	 * @throws DocumentException
	 * @throws GateLogicException
	 */
	public GateMessage buildReqSaveEOrderRequest(String abonentId, AccountPaymentSystemPayment payment, boolean isComplexService,  boolean mainService) throws GateException, GateLogicException
	{
		GateMessage message = messagingService.createRequest("ReqSaveEOrder");

		try
		{
			Document document = message.getDocument();
			Element root = document.getDocumentElement();

			Element eOrder = document.createElement("EOrder");
			root.setAttribute("delOld", "false");
			root.appendChild(eOrder);

			Element payOrder = document.createElement("PayOrder");
			payOrder.setAttribute("amount", getAmountFieldValue(payment.getExtendedFields()));
			payOrder.setAttribute("calcmode", "3");
			eOrder.appendChild(payOrder);

			Element abonent = mainService ? document.createElement("Abonent") : document.createElement("AddService");
			abonent.setAttribute("id", getPointCode(abonentId, payment.getService().getCode(), mainService));
			payOrder.appendChild(abonent);

			if (mainService)
				addPayerInfo(payOrder, payment.getExternalOwnerId(), "Person");
			else
				addPayerInfo(payOrder, payment.getExternalOwnerId(), "Payer");

			boolean hasPaymentPeriods = false;
			CalendarFieldPeriod calendarFieldPeriod = null;

			if (isComplexService)
				payOrder.appendChild(createListOfSubOrderElement(document, payment, hasPaymentPeriods, calendarFieldPeriod));
			else
				payOrder.appendChild(createMainOrderElement(document, payment, hasPaymentPeriods, calendarFieldPeriod, getAmountFieldValue(payment.getExtendedFields())));

			if (hasPaymentPeriods)
				payOrder.appendChild(createPayPeriodsElement(document, getPayPeriod(payment), calendarFieldPeriod));

			Element payForm = document.createElement("PayForm");
			payForm.setAttribute("type", "cashless");
			payOrder.appendChild(payForm);

			Element ePay = document.createElement("EPay");
			ePay.setAttribute("id", "new");
			eOrder.appendChild(ePay);

			Element listOfMoreInfo = document.createElement("ListOfMoreInfo");
			listOfMoreInfo.setAttribute("request", "false");
			root.appendChild(listOfMoreInfo);
			Element moreInfo = document.createElement("moreInfo");
			moreInfo.setAttribute("request", "true");
			moreInfo.setAttribute("maxItems", "all");
			moreInfo.setAttribute("xpRef", "/RSASMsg/AnsSaveEOrder/EOrder/PayOrder/CommissOrder");
			moreInfo.setAttribute("action", "stop");
			listOfMoreInfo.appendChild(moreInfo);

			Element moreInfo1 = document.createElement("moreInfo");
			moreInfo1.setAttribute("request", "true");
			moreInfo1.setAttribute("maxItems", "all");
			moreInfo1.setAttribute("xpRef", "/RSASMsg/AnsSaveEOrder/EOrder/EPay");
			moreInfo1.setAttribute("action", "stop");
			listOfMoreInfo.appendChild(moreInfo1);

			Element moreInfo2 = document.createElement("moreInfo");
			moreInfo2.setAttribute("request", "true");
			moreInfo2.setAttribute("maxItems", "all");
			moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsSaveEOrder/EOrder/PayOrder/MainOrder/ListOfOrderExtent/OrderExtent/Extent");
			moreInfo2.setAttribute("action", "stop");
			listOfMoreInfo.appendChild(moreInfo2);

			Element moreInfo3 = document.createElement("moreInfo");
			moreInfo3.setAttribute("request", "true");
			moreInfo3.setAttribute("maxItems", "all");
			moreInfo3.setAttribute("xpRef", "/RSASMsg/AnsSaveEOrder/EOrder/PayOrder/Payee/Rekv");
			moreInfo3.setAttribute("action", "stop");
			listOfMoreInfo.appendChild(moreInfo3);

			Element moreInfo4 = document.createElement("moreInfo");
			moreInfo4.setAttribute("request", "true");
			moreInfo4.setAttribute("maxItems", "all");
			moreInfo4.setAttribute("xpRef", "/RSASMsg/AnsSaveEOrder/EOrder/PayOrder/Abonent");
			moreInfo4.setAttribute("action", "stop");
			listOfMoreInfo.appendChild(moreInfo4);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}

		return message;
	}

	/**
	 * Получить abonent-id (связка между поставщиком и клиентом)
	 * @param payment платеж
	 * @return abonent-id
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public String getAbonentId(AccountPaymentSystemPayment payment, boolean mainService) throws GateException, GateLogicException
	{
		String codeService = payment.getService().getCode();

		if (!mainService)
			return null;

		try
		{
			for (Field field : payment.getExtendedFields())
			{
				if (Constants.ACCOUNT_FIELD_NAME.equals(field.getExternalId()) || isLicAccountField(field))
				{
					String fieldValue = (String) field.getValue();
					if (StringHelper.isEmpty(fieldValue))
						return null;

					return getAbonentId(codeService, fieldValue);
				}
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}

		throw new GateLogicException("Введенный лицевой счет не найден в базе данных!");
	}

	/**
	 * Получить abonent-id (связка между поставщиком и клиентом)
	 * @param recipientId иденти-р поставщика
	 * @param keyValue       значение поля лицевой счет
	 * @return
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public String getAbonentId(String recipientId, String keyValue) throws GateException, GateLogicException
	{
		GateMessage message = buildReqGlobalRequest(recipientId, keyValue);
		Document responce   = messagingService.sendOnlineMessage(message, null);
		try
		{
			Element node = XmlHelper.selectSingleNode(responce.getDocumentElement(), "Global/ListOfAbonent/Abonent");
			if (node == null)
				throw new GateLogicException("Введенный лицевой счет не найден в базе данных!");

			return node.getAttribute("id");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить abonent-id (связка между поставщиком и клиентом)
	 * @param field ключевое поле licAccount
	 * @return abonent-id (связка между поставщиком и клиентом)
	 * @throws GateException
	 */
	public String getLicAccountId(CommonField field) throws GateException
	{
		if (FieldDataType.list == field.getType())
		{
			List<ListValue> values = field.getValues();
			if (values.size() > 1)
			{
				//значение клиент уже выбрал
				return (String) field.getValue();
			}
			if (values.size() == 1)
			{
				//поле клиенту не показывали, поэтому берем как есть
				return values.get(0).getId();
			}
			throw new GateException("Не найден соответствие переданного абонента карте.");
		}

		return (String) field.getValue();
	}

	/**
	 * Получить значение ключевого поля licAccount
	 * @param field ключевое поле licAccount
	 * @return значение
	 * @throws GateException
	 */
	public String getLicAccountValue(CommonField field) throws GateException
	{
		if (FieldDataType.list == field.getType())
		{
			List<ListValue> values = field.getValues();
			if (values.size() > 1)
			{
				String fieldValue = (String) field.getValue();
				//значение клиент уже выбрал
				for (ListValue value : values)
				{
					if (fieldValue.equals(value.getId()))
						return value.getValue();
				}
				return null;
			}
			if (values.size() == 1)
			{
				//поле клиенту не показывали, поэтому берем как есть
				return values.get(0).getValue();
			}
			throw new GateException("Не найден соответствие переданного абонента карте.");
		}

		return (String) field.getValue();
	}

	/**
	 * @param payment платеж
	 * @return периоды оплаты.
	 * @throws GateException
	 */
	private String getPayPeriod(AccountPaymentSystemPayment payment) throws GateException
	{
		Field payPeriod = BillingPaymentHelper.getFieldById(payment, Constants.CALENDAR_FIELD_NAME);
		return payPeriod == null ? "" : (String) payPeriod.getValue();
	}

	/**
	 * Формирование под. информации
	 * @param payOrder
	 * @param id
	 * @param tagName
	 */
	public void addPayerInfo(Element payOrder, String id, String tagName)
	{
		// Пока Банк утверждает, что заполнение структуры рельными данными о клиенте не обязательно.
		//Если потребуется, то надо будет делать поиск клиента по id и заполнять структуру.
		Document doc = payOrder.getOwnerDocument();

		Element payer = doc.createElement(tagName);
		payer.setAttribute("lastName", "");
		payer.setAttribute("firstName", "");
		payer.setAttribute("middleName", "");
		payOrder.appendChild(payer);

		Element address = doc.createElement("Address");
		address.setAttribute("building", "");
		address.setAttribute("apartment", "");
		payer.appendChild(address);

		Element street = doc.createElement("Street");
		street.setAttribute("name", "");
		address.appendChild(street);

		Element city = doc.createElement("City");
		city.setAttribute("name", "");
		street.appendChild(city);

			//TODO выяснить в банке окончательно, нужна ли инфа о регионе и стране в идентификации плательщика
			//TODO пока (на 26,02,2009) она не нужна
//		Element region = doc.createElement("Region");
//		region.setAttribute("name", clientAddress.getProvince());
//		city.appendChild(region);
//
//		Element country = doc.createElement("Country");
//		country.setAttribute("name", "Россия"); //у нас нет данных о стране проживания. Однако логично предположить,
//		region.appendChild(country);    		// что плательщик будет жить в России.. по крайней мере регистрация у него должна быть (хотя бы формальная)
//		}
	}

    /**
     * Получение списка доп. полей из внешней системы
     * (для запроса ключевых полей)
     * @param codeService код сервиса
     * @return список полей
     * @throws GateLogicException
     * @throws GateException
     */
    public List<Field> getExtendedFields(String codeService, boolean mainService) throws GateLogicException, GateException
    {
        //получаем список дополнительных полей
        GateMessage message = buildReqXObjectRequest(codeService, mainService);
        Document response = messagingService.sendOnlineMessage(message, null);
        List<Field> fields = new ArrayList<Field>();
        try
        {
            NodeList nodeList = mainService ?
                    XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/Service/ExtentType/ListOfExtent/Extent") :
                    XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/AddService/Service/ExtentType/ListOfExtent/Extent");

            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Element node = (Element) nodeList.item(i);
                CommonField field = new CommonField();
	            field.setVisible(true);

                String dataType = node.getAttribute("datatype");
                int num = Integer.parseInt(node.getAttribute("num"));
                Element el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='LINES']");

                //определяемся с типом поля
				FieldDataType fieldType = FieldDataType.fromValue(dataType);
				if (FieldDataType.list == fieldType || node.getAttribute("isArray").equals("true"))
				{
					//по умолчанию всегда используен значение, а не индекс
					String byName = "YES";
					String esc = node.getAttribute("ecs");
					String sep = node.getAttribute("sep");

					//для списков определяем byName
					if (FieldDataType.list == fieldType)
                    {
						if (XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='BYNAME']") != null)
							byName = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='BYNAME']").getTextContent();
                    }
	                field.setValues(getFieldValuesByByNameValue(node, byName, esc + sep));
	                field.setType(FieldDataType.list);
                }
                else
                {
                    if (el != null)
                    {
                        field.setLinesNumber(Integer.parseInt(el.getTextContent()));
                    }
                    field.setType(fieldType);
                }

                //Заполняем остальные атриибуты поля.
                el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='MAINSUM']");
                if (el != null)
                    field.setMainSum(el.getTextContent().equalsIgnoreCase("YES"));

                el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='SUM']");
                if (el != null && el.getTextContent().equalsIgnoreCase("YES"))
                    field.setType(FieldDataType.money);

                el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='VISIBLE']");
                if (el != null)
                    field.setVisible(!el.getTextContent().equalsIgnoreCase("NO"));

                field.setRequiredForBill(field.isVisible());

                //по умолчанию редактируемость поля равна видимости.
                field.setEditable(field.isVisible());

                el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='EDITABLE']");
                if (el != null)
                {
                    field.setEditable(field.isVisible() && !el.getTextContent().equalsIgnoreCase("NO"));
                }
                field.setNum(num);
                field.setDefaultValue(node.getAttribute("defval"));
                field.setExternalId(node.getAttribute("id"));
                String dataLength = node.getAttribute("datalength");
                if (!StringHelper.isEmpty(dataLength))
                    field.setMaxLength(Long.parseLong(dataLength));
                field.setName(node.getAttribute("name"));
                field.setRequired(!node.getAttribute("isOptional").equalsIgnoreCase("true"));

            /*
                Получив поля «ФИО», «Адрес» («name="ФИО" , name="АДРЕС"»),
                надо выставить атрибуты visible=false, editable=true.
                Идея следующая: нам надо скрыть данные поля на форме редактирования и отобразить их на формах подтверждения и просмотра платежа.
                Для этого можно обработать набор атрибутов visible/editable, при сочетании false/true скрыть на форме редактирования и
                отобразить на форме просмотра (она же форма подтверждения).
                Для того, что бы такой «катавасии» не происходило с другими полями, предлагается полям с атрибутом visible=false
                автоматически выставлять editable=false (т.к. эти атрибуты нужны только для формы платежа, и клиент явно не будет
                заполнять невидимые поля).
            */
                if (Constants.FIO_FIED_NAME.equals(field.getName()) || Constants.ADDRESS_FIED_NAME.equals(field.getName()))
                {
                    field.setVisible(false);
                    field.setEditable(true);
                }

                if (isLicAccountField(field) && mainService)
                {
                    //Лицевой счет идентифицирует клиента - он всегда видим, и он всегда ключевое поле
                    field.setType(FieldDataType.string);
                    field.setVisible(true);
                    field.setEditable(true);
                    field.setRequired(true);
                    field.setRequiredForConformation(true);
	                field.setSaveInTemplate(true);
	                field.setRequiredForBill(true);
                    field.setKey(true);
                }

                fields.add(field);
            }
        }
        catch (TransformerException e)
        {
            throw new GateException(e);
        }

	    return fields;
    }
	/**
	 * Получение заполненных полей из внешней системы
	 * @param abonentId   идентификатор (связка услуги и поставщика услуг)
	 * @param recipientId идентификатор поставщика услуг
	 * @return список полей
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<Field> getExtendedFields(String abonentId, String recipientId, boolean mainService) throws GateLogicException, GateException
	{
		//получаем список дополнительных полей
		GateMessage reqXObjectMessage = buildReqXObjectRequest(abonentId, recipientId, mainService);
		Document reqXObjectResponse   = messagingService.sendOnlineMessage(reqXObjectMessage, null);

		List<FieldWithByNameAndType> extendedFields = getGateFields(reqXObjectResponse, abonentId, mainService);
		//запрашиваем первоначальные значения для доп. полей (calcmode=1)
		GateMessage reqCalcEOrderMessage = buildReqCalcEOrderCalcMode1Request(abonentId, recipientId, extendedFields, mainService);
		Document reqCalcEOrderResponse   = messagingService.sendOnlineMessage(reqCalcEOrderMessage, null);

		Map<String, FieldWithByNameAndType> fieldMap = new HashMap<String, FieldWithByNameAndType>();
		for (FieldWithByNameAndType field : extendedFields)
			fieldMap.put(field.getField().getExternalId(), field);

		//заполняем полученные поля первоначальными данными
		try
		{
			List <Field> result = new ArrayList<Field>();
			//обработка ответа простой услуги
			if (XmlHelper.selectSingleNode(reqCalcEOrderResponse.getDocumentElement(), "EOrder/PayOrder/ListOfSubOrder") == null)
			{
				NodeList nodeList = XmlHelper.selectNodeList(reqCalcEOrderResponse.getDocumentElement(), "EOrder/PayOrder/MainOrder/ListOfOrderExtent/OrderExtent");
				for (int i = 0; i < nodeList.getLength(); i++)
				{
					Element orderExtent = (Element) nodeList.item(i);
					String id = XmlHelper.selectSingleNode(orderExtent, "Extent").getAttribute("id");
					FieldWithByNameAndType extraField = fieldMap.get(id);
					if (extraField != null)
						updateField(extraField, orderExtent);
				}
				for (FieldWithByNameAndType f : fieldMap.values())
					result.add(f.getField());
				Collections.sort(result, new GorodFieldComparator());
			}
			//обработка ответа сложной услуги
			else
			{
				NodeList subOrders = XmlHelper.selectNodeList(reqCalcEOrderResponse.getDocumentElement(), "EOrder/PayOrder/ListOfSubOrder/SubOrder");
				for (int i = 0; i < subOrders.getLength(); i++)
				{
					Element subOrder = (Element) subOrders.item(i);
					Element subService = XmlHelper.selectSingleNode(subOrder, "SubService");
					String subServiceId = subService.getAttribute("id");
					//получаем идетификатор поля "начисление"
					String chargedFieldId = getChargedFieldId(reqXObjectResponse);
					//строим идентификатор поля "начисление" для подуслуги
					String subServiceChargedId = StringHelper.isEmpty(chargedFieldId) ? null : generateSubExternalId(subServiceId, chargedFieldId);

					NodeList orderExtents = XmlHelper.selectNodeList(subOrder, "MainOrder/ListOfOrderExtent/OrderExtent");
					for (int j = 0; j < orderExtents.getLength(); j++)
					{
						Element orderExtent = (Element) orderExtents.item(j);
						Element extent = XmlHelper.selectSingleNode(orderExtent, "Extent");
						String extentId = extent.getAttribute("id");
						//чтобы найти вернувшееся поле в списке допполей, формируем ИД так же, как при заполнении списка
						String externalId = generateSubExternalId(subServiceId, extentId);
						FieldWithByNameAndType extraField = fieldMap.get(externalId);

						if (extraField != null)
							updateField(extraField, orderExtent);

						//если есть поле "начисление" то значение поля устанваливаем в поле Сумма подуслуги
						if (!StringHelper.isEmpty(subServiceChargedId) && externalId.equals(subServiceChargedId))
						{
							String val = orderExtent.getAttribute("val");
							String defVal = orderExtent.getAttribute("defVal");
							//ищем поле сумма подуслуги и устанавливаем значение из начисления
							FieldWithByNameAndType amountField = fieldMap.get(generateSubServiceAmountExternalId(subServiceId));
							if (amountField != null)
							{
								CommonField amountFieldImpl = (CommonField) amountField.getField();
								String resultValue = StringHelper.isEmpty(val) ? defVal : val;
								amountFieldImpl.setDefaultValue(StringHelper.isEmpty(resultValue) ? "0" : resultValue);
								//если значение поля начисление пустое, то не отображаем поле
								if (StringHelper.isEmpty(resultValue))
								{
									FieldWithByNameAndType chargedField = fieldMap.get(subServiceChargedId);
									CommonField chargedFieldImpl = (CommonField) chargedField.getField();
									chargedFieldImpl.setVisible(false);
								}
							}
						}
					}
				}
				for (FieldWithByNameAndType f : fieldMap.values())
					result.add(f.getField());

				result.add(createComlexServiceAmountField());
			}
			return result;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private void updateField(FieldWithByNameAndType extraField, Element orderExtent) throws GateLogicException, GateException
	{
		try
		{
			String val = orderExtent.getAttribute("val");
			String defVal = orderExtent.getAttribute("defval");
			CommonField field = (CommonField) extraField.getField();
			if (field.getType().equals(FieldDataType.list))
			{
				String byname = extraField.getByName().equalsIgnoreCase("yes") ? "YES" : "NO";
				String separator = ";";
				if (extraField.getFieldType().equalsIgnoreCase(FieldDataType.list.toString()))
					separator = getEscapeStringForField(field.getExternalId());

				//выбрать более длинный список
				field.setDefaultValue(tokenaze(defVal, separator).size() > tokenaze(val,separator).size() ? defVal : val);

				//получить список, состоящий из элементов, разделенных сепаратором
				List<String> values = tokenaze(field.getDefaultValue(), separator);

				//Результирующий список, содержащий либо правую, либо левую часть элемента, полученного на предыдущем шаге
				List<String> values_byname = new ArrayList<String>();
				for (int j = 0; j < values.size(); j++)
				{
					int ind = values.get(j).indexOf("=");
					values_byname.add(ind != -1 ? values.get(j).substring(0, ind) : values.get(j));
				}

				List<ListValue> listValiues = new ArrayList<ListValue>(values_byname.size());
				Integer idx = 0;
				for (String value: values_byname)
				{
					listValiues.add(byname.equalsIgnoreCase("no") ?
							new ListValue(value, idx.toString()) : new ListValue(value, value));
					idx++;
				}
				field.setValues(listValiues);
			}
			else
			{
				field.setDefaultValue(StringHelper.isEmpty(val) ? defVal : val);
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * получить идентификатор поля "начисление"
	 * @param response ответ Города
	 * @return идетификатор
	 * @throws GateException
	 */
	private String getChargedFieldId(Document response) throws GateException
	{
		try
		{
			NodeList extents = XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/Abonent/Service/ExtentType/ListOfExtent/Extent");
			for (int i = 0; i < extents.getLength(); i++)
			{
				Element extent = (Element) extents.item(i);
				if (isChargedField(extent))
					return extent.getAttribute("id");
			}
			return null;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @param extent елемент Поле услуги
	 * @return является поле полем "начисление"
	 */
	private boolean isChargedField(Element extent)
	{
		return extent.getAttribute("short").equals("DEBT") && extent.getAttribute("datatype").equals("NUMBER");
	}

	/**
	 * Получение незаполненных полей из внешней системы
	 * @param response ответ Города
	 * @param abonentId   идентификатор (связка услуги и поставщика услуг)
	 * @return доп. поля
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	private List<FieldWithByNameAndType> getGateFields(Document response, String abonentId, boolean mainService) throws GateLogicException, GateException
	{
		List<FieldWithByNameAndType> extendedList = new ArrayList<FieldWithByNameAndType>();
		List<FieldWithByNameAndType> result = new ArrayList<FieldWithByNameAndType>();
		try
		{
			boolean isComplexService = XmlHelper.selectSingleNode(response.getDocumentElement(), "XObject/Abonent/Service/ListOfSubService") != null;

			NodeList nodeList = mainService ?
					XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/Abonent/Service/ExtentType/ListOfExtent/Extent") :
					XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/AddService/Service/ExtentType/ListOfExtent/Extent");

			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);
				CommonField field = new CommonField();
				//для сложных услуг делаем все поля изначально невидимыми
				field.setVisible(!isComplexService);

				//по умолчанию всегда используен значение, а не индекс
				String byName   = "YES";
				String dataType = node.getAttribute("datatype");
				int num = Integer.parseInt(node.getAttribute("num"));
				Element el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='LINES']");

				//определяемся с типом поля
				FieldDataType fieldType = FieldDataType.fromValue(dataType);
				if (FieldDataType.list == fieldType || node.getAttribute("isArray").equals("true"))
				{
					String esc = node.getAttribute("ecs");
					String sep = node.getAttribute("sep");

					//для списков определяем byName
					if (FieldDataType.list == fieldType)
                    {
						if (XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='BYNAME']") != null)
							byName = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='BYNAME']").getTextContent();
                    }
	                field.setValues(getFieldValuesByByNameValue(node, byName, esc + sep));
	                field.setType(FieldDataType.list);
                }
				else
				{
					if (el != null)
						field.setLinesNumber(Integer.parseInt(el.getTextContent()));
					field.setType(fieldType);
				}

				//Заполняем остальные атриибуты поля.
				el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='MAINSUM']");
				if (el != null)
					field.setMainSum(el.getTextContent().equalsIgnoreCase("YES"));

				el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='SUM']");
				if (el != null && el.getTextContent().equalsIgnoreCase("YES"))
					field.setType(FieldDataType.money);

				el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='VISIBLE']");
				//для сложных услуг делаем все поля изначально невидимыми
				if (el != null)
					field.setVisible(!(isComplexService || el.getTextContent().equalsIgnoreCase("NO")));

				field.setRequiredForBill(field.isVisible());

				//по умолчанию редактируемость поля равна видимости.
				field.setEditable(field.isVisible());

				el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='EDITABLE']");
				if (el != null)
					field.setEditable(field.isVisible() && !el.getTextContent().equalsIgnoreCase("NO"));

				field.setNum(num);
				field.setDefaultValue(node.getAttribute("defval"));
				field.setExternalId(node.getAttribute("id"));
				String dataLength = node.getAttribute("datalength");
				if (!StringHelper.isEmpty(dataLength))
					field.setMaxLength(Long.parseLong(dataLength));
				field.setName(node.getAttribute("name"));
				field.setRequired(!node.getAttribute("isOptional").equalsIgnoreCase("true"));

				if (isLicAccountField(field) && mainService)
				{
					//Лицевой счет идентифицирует клиента - он всегда видим, и он всегда ключевое поле
					field.setType(FieldDataType.string);
					field.setVisible(true);
					field.setEditable(true);
					field.setRequired(true);
					field.setRequiredForConformation(true);
					field.setSaveInTemplate(true);
					field.setKey(true);
				}
				//если услуга сложная и поле является полем Начисление, устанавливаем значения
				//нередактируемое и видимое, устанавливаем порядок отображения
				if (isComplexService && isChargedField(node))
				{
					field.setVisible(true);
					field.setEditable(false);
					field.setType(FieldDataType.money);
				}

				FieldWithByNameAndType fl = new FieldWithByNameAndType();
				fl.setField(field);
				fl.setByName(byName);
				fl.setFieldType(fieldType.name());
				extendedList.add(fl);
			}
			//для сложных подуслуг формируем поля сумм с префиксами SSAMOUNT- и по своему набору дополнительных полей с префиксами SS-
			if (isComplexService)
			{
				NodeList subServices = XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/Abonent/Service/ListOfSubService/SubService");
				List<FieldWithByNameAndType> ssNamesList = new ArrayList<FieldWithByNameAndType>(subServices.getLength());

				for (int i = 0; i < subServices.getLength(); i++)
				{
					Element subService = (Element) subServices.item(i);
					//создаем поле сумма для каждой подуслуги
					ssNamesList.add(createSSAmountField(subService));
					//создаем поле имя для каждой подуслуги
					ssNamesList.add(createSSNameField(subService));
					//создаем наборы допполей для каждой подуслуги
					for (FieldWithByNameAndType extendField: extendedList)
					{
						result.add(createSSField(extendField, subService.getAttribute("id")));
					}
				}
				result.addAll(ssNamesList);
			}
			else
				result.addAll(extendedList);

			//Создаем, если надо поле lastпериод оплаты.
			NodeList agrAttr = mainService ?
					XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/Abonent/Service/AgrPU/ListOfAttrVal/AGroup") :
					XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/AddService/Service/AgrPU/ListOfAttrVal/AGroup");

			int calendarFieldCount = 0;
			for (int i = 0; i < agrAttr.getLength(); i++)
			{
				Element node = (Element) agrAttr.item(i);
				if (node.getAttribute("id").indexOf("CALENDAR") > -1)
				{
					calendarFieldCount++;

					if (calendarFieldCount > 1)
						throw new GateException("Cоздается несколько полей с периодом оплаты. Бизнес умеет работать только с 1.");

					String month = XmlHelper.selectSingleNode(node, "val[@code='MONTH']").getTextContent();
					if(!StringHelper.isEmpty(month))
					{
						CommonField field = new CommonField();
						field.setType(FieldDataType.calendar);

						if (month.equalsIgnoreCase("BROKEN"))
						{
							field.setPeriod(CalendarFieldPeriod.broken);
						}
						else if (month.equalsIgnoreCase("UNBROKEN"))
						{
							field.setPeriod(CalendarFieldPeriod.unbroken);
						}
						field.setExternalId(Constants.CALENDAR_FIELD_NAME);
						field.setName("Период оплаты (месяц/год)");
						field.setRequired(true);
						field.setVisible(true);
						field.setEditable(true);
						field.setRequiredForBill(true);

						FieldWithByNameAndType fl=new FieldWithByNameAndType();
						fl.setField(field);
						fl.setFieldType(field.getType().toString());
						result.add(fl);
					}
				}
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		return result;
	}

	/**
	 * построить поле подуслуги, создаем на основе поля услуги, копируем данные и генерируем идентификатор
	 * с учетом подуслуги, устанвливаем номер для упорядоченного отображения
	 * @param extendField поле услуги
	 * @param subServiceId идентификатор подуслуги
	 * @return поле подуслуги
	 */
	private FieldWithByNameAndType createSSField(FieldWithByNameAndType extendField, String subServiceId)
	{
		CommonField field = (CommonField) extendField.getField();
		FieldWithByNameAndType ssExtendField = new FieldWithByNameAndType();
		CommonField ssField = new CommonField();
		//генерируем идентификатор вида SS- + идентификатор подуслуги + _ + название поля
		ssField.setExternalId(generateSubExternalId(subServiceId, field.getExternalId()));
		ssField.setDefaultValue(field.getDefaultValue());
		ssField.setEditable(field.isEditable());
		ssField.setKey(field.isKey());
		ssField.setMainSum(false);
		ssField.setGroupName(subServiceId);
		ssField.setMaxLength(field.getMaxLength());
		ssField.setName(field.getName());
		ssField.setRequired(field.isRequired());
		ssField.setRequiredForBill(field.isRequiredForBill());
		ssField.setRequiredForConformation(field.isRequiredForConformation());
		ssField.setType(field.getType());
		ssField.setValue(field.getValue());
		ssField.setVisible(field.isVisible());

		ssExtendField.setField(ssField);
		ssExtendField.setFieldType(ssField.getType().toString());
		ssExtendField.setByName(extendField.getByName());

		return ssExtendField;
	}

	/**
	 * построить поле сумма подуслуги, наше поле
	 * @param subService елемент подуслуга
	 * @return поле сумма подуслуги
	 */
	private FieldWithByNameAndType createSSAmountField(Element subService)
	{
		FieldWithByNameAndType ssAmountExtendfield = new FieldWithByNameAndType();
		CommonField ssAmountField = new CommonField();

		ssAmountField.setExternalId(generateSubServiceAmountExternalId(subService.getAttribute("id")));
		ssAmountField.setEditable(true);
		ssAmountField.setVisible(true);
		ssAmountField.setName(Constants.SS_AMOUNT_FIELD_NAME);
		ssAmountField.setType(FieldDataType.money);
		ssAmountField.setGroupName(subService.getAttribute("id"));
		ssAmountField.setDefaultValue("0");
		//сумму печатаем в чеке
		ssAmountField.setRequiredForBill(true);

		ssAmountExtendfield.setField(ssAmountField);
		ssAmountExtendfield.setFieldType(ssAmountField.getType().toString());

		return ssAmountExtendfield;
	}

	/**
	 * построить поле Имя подуслуги, наше поле
	 * @param subService элемент подуслуга
	 * @return поле Имя подуслуги
	 */
	private FieldWithByNameAndType createSSNameField(Element subService)
	{
		FieldWithByNameAndType ssNameExtentField = new FieldWithByNameAndType();
		CommonField ssNameField = new CommonField();

		ssNameField.setExternalId(generateSubServiceNameExternalId(subService.getAttribute("id")));
		ssNameField.setName(Constants.SS_NAME_FIELD_NAME);
		ssNameField.setType(FieldDataType.string);
		ssNameField.setDefaultValue(subService.getAttribute("name"));
		ssNameField.setGroupName(subService.getAttribute("id"));
		ssNameField.setEditable(false);
		ssNameField.setVisible(true);
		//наименование подуслуги печатаем в чеке
		ssNameField.setRequiredForBill(true);

		ssNameExtentField.setField(ssNameField);
		ssNameExtentField.setFieldType(ssNameField.getType().toString());

		return ssNameExtentField;
	}

	public void refreshBillingPayment(String abonentId, AccountPaymentSystemPayment payment, boolean mainService) throws GateLogicException, GateException
	{
		try
		{
			//валидируем поля платежа, пока не получем некую сумму.
			//если было использовано 20 попыток, в течение которых так и не удалось получить amoumt, отличный от 0 - кидаем Exception
			for (int i = 0; i < Constants.MAX_ITERATION_SIZE; i++)
			{
				List<Field> fields = payment.getExtendedFields();
				boolean isComplexService = isComplexService(fields);

				//перед тем как отправлять запрос, считаем сумму и заполняем нулями пустые значения сумм подуслуг, если услуга сложная
				if (isComplexService)
					updateAmountFields(payment);

				GateMessage prepareMessage = buildReqCalcEOrderCalcMode2Request(abonentId, payment, isComplexService, mainService);
				Document prepareResponse   = messagingService.sendOnlineMessage(prepareMessage, new MessageHeadImpl(null, null, null, payment.getExternalId(), null, null));
				updateExtendedFields(payment, prepareResponse);

				String amount = XmlHelper.selectSingleNode(prepareResponse.getDocumentElement(), "EOrder/PayOrder").getAttribute("amount");
				//если значение суммы не получено выполняем перерасчет полей еще раз
				if (StringHelper.isEmpty(amount) || amount.equals("0") || (new BigDecimal(amount)).compareTo(new BigDecimal("0")) <= 0)
					continue;

				updateMainSumField(payment, amount);

				//Отсылаем запрос на сохранение платежа в системе Город, необходимо, т.к. на это этапе проводится финальная валидация по "формулам" Города.
				GateMessage saveMessage = buildReqSaveEOrderRequest(abonentId, payment, isComplexService, mainService);
				Document saveResponse   = messagingService.sendOnlineMessage(saveMessage, new MessageHeadImpl(null, null, null, payment.getExternalId(), null, null));
				updateExtendedFields(payment, saveResponse);

				if (!payment.isTemplate())
				{
					Element comissionOrder = XmlHelper.selectSingleNode(saveResponse.getDocumentElement(), "EOrder/PayOrder/CommissOrder");
					String commission = (comissionOrder != null) ? comissionOrder.getAttribute("amount") : "0";
					payment.setCommission(new Money(new BigDecimal(commission), currencyService.getNationalCurrency()));
				}

				prepareBillingFields(payment);

				//устанавливаем идентификатор платежа во внешней системе
				Element ePay = XmlHelper.selectSingleNode(saveResponse.getDocumentElement(), "EOrder/EPay");
				payment.setIdFromPaymentSystem(ePay.getAttribute("unp"));

				//прекращаем дальнейшую обработку
				return;
			}
			throw new GateLogicException("Неверно заполнены реквизиты платежа. Исправьте введенные данные.");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (GateMessagingException e)
		{
			String message = e.getMessage();
			//Если сообщение содержит определенный код, вырезаем из сообщения всё лишнее
			if (message.contains("Сумма к оплате должна быть больше"))
			{
				int beginIndex = message.indexOf("Сумма к оплате должна быть больше");
				int endIndex   = message.indexOf("*@");

				throw new GateLogicException(message.substring(beginIndex, endIndex));
			}
			else
			{
				throw new GateException(e);
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Подготовка/корректировка полей для отображения клиенту
	 * @param payment платеж
	 */
	private void prepareBillingFields(AccountPaymentSystemPayment payment) throws GateException
	{
		/*
			Полученные из Города поля «ФИО», «Адрес» («name="ФИО" , name="АДРЕС"»),
			имеют параметры полей visible=false, editable=true. Но для отображения их на форме необходимо
			сделать, чтобы поле было отображаемое, т.е. visible=true.
		*/
		try
		{
			for (Field field : payment.getExtendedFields())
			{
				if (field instanceof CommonField)
				{
					CommonField cf = (CommonField) field;
					if (Constants.FIO_FIED_NAME.equals(cf.getName()) || Constants.ADDRESS_FIED_NAME.equals(cf.getName()))
					{
						cf.setVisible(true);
					}
				}
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Посчитать значение общей суммы сложной услуги. Складываются суммы подуслуг, поле сумма делается видимым
	 * Если значение суммы подуслуги пустое, то устанвливаем 0
	 * @param payment платеж
	 * @throws GateException
	 */
	private void updateAmountFields(AccountPaymentSystemPayment payment) throws GateException
	{
		try
		{
			Money amount = payment.getDestinationAmount();
			//тк суммируем значения, то изначально всегда устанавливем 0
			BigDecimal amountValue = new BigDecimal("0");
			//суммируем значения полей сумм подуслуг
			for (Field field : payment.getExtendedFields())
			{
				if (field.getExternalId().startsWith(Constants.SS_AMOUNT_PREFIX))
				{
					BigDecimal subAmountValue = new BigDecimal(getFieldValue(field));
					amountValue = amountValue.add(subAmountValue);
					//проверяем на пустоту суммы подуслуги
					if (StringHelper.isEmpty((String) field.getValue()))
					{
						CommonField subAmountField = (CommonField) field;
						subAmountField.setValue("0");
					}
				}
			}
			//устнавливаем посчитанное значение в платеж
			payment.setDestinationAmount(new Money(amountValue, amount.getCurrency()));
			//Отображаем поле Сумма на форме подтверждения
			CommonField amountField = (CommonField) BillingPaymentHelper.getMainSumField(payment);
			amountField.setVisible(true);
			amountField.setValue(amountValue.toString());
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private GateMessage buildRequireForDefineServiceType(String serviceId) throws GateException
	{
		GateMessage msg = messagingService.createRequest("ReqCard");

		Document doc = msg.getDocument();
		Element root = doc.getDocumentElement();

		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);

		Element moreInfo1 = doc.createElement("moreInfo");
		moreInfo1.setAttribute("request", "true");
		moreInfo1.setAttribute("maxItems", "all");
		moreInfo1.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfService");
		moreInfo1.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo1);

		Element filter = doc.createElement("ListOfFilterVal");
		Element val = doc.createElement("val");
		val.setAttribute("code", "SERVICEID");
		val.appendChild(doc.createTextNode(serviceId));
		filter.appendChild(val);
		moreInfo1.appendChild(filter);

		Element moreInfo2 = (Element) moreInfo1.cloneNode(false);
		moreInfo2.setAttribute("xpRef", "/RSASMsg/AnsCard/Card/ListOfService/Service/ServiceType");
		listOfMoreInfo.appendChild(moreInfo2);

		return msg;
	}


	/**
	 * Определяем тип оплачиваемой услуги
	 * @param codeService идентификатор
	 * @return true - основная, false - дополнительная
	 */
	public boolean isMainService(String codeService) throws GateException, GateLogicException
	{

	    GateMessage message = buildRequireForDefineServiceType(codeService);
		Document response   = messagingService.sendOnlineMessage(message, null);
		try
		{
			Element serviceTypeElement = XmlHelper.selectSingleNode(response.getDocumentElement(),
					"/AnsCard/Card//ListOfService/Service/ServiceType");

			return !Constants.SERVICE_TYPE_ADD.equals(serviceTypeElement.getAttribute("id"));
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Создать поле "Номер лицевого счета"
	 *
	 * @param codeService код поставщика услуг
	 * @return поле "Номер лицевого счета"
	 * @throws GateException
	 */
	public CommonField createLicAccountField(String codeService, boolean mainService) throws GateException, GateLogicException
	{
		if (!mainService)
			return null;

		CommonField field = createLicAccountField();

		field.setMaxLength(Constants.FIELD_MAX_LENGTH);
		field.setMinLength(Constants.FIELD_MIN_LENGTH);
		field.setType(FieldDataType.string);

		return field;
	}

	/**
	 * Создать поле "Номер лицевого счета" (для персональных платежей)
	 * @param codeService      код поставщика услуг
	 * @param billingClientId  идентификатор клиента в биллинге
	 * @return
	 * @throws GateException
	 */
	public CommonField createLicAccountField(final String codeService, String billingClientId) throws GateException
	{
		final List<ListValue> values = new ArrayList<ListValue>();

		try
		{
			GateMessage message = buildReqCardRequest(billingClientId);
			Document response   = messagingService.sendOnlineMessage(message, null);

			XmlHelper.foreach(response.getDocumentElement(), "Card/ListOfLinkAbonent/Abonent", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String serviceId = XmlHelper.selectSingleNode(element, "Service").getAttribute("id");
					serviceId = serviceId.replace("Service-", "");
					if (serviceId.equals(codeService))
					{
						values.add(new ListValue(element.getAttribute("account"), element.getAttribute("id")));
					}
				}
			});
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		if (values.size() == 0)
			throw new GateException("Не найдено соответствие переданного абонента карте");

		CommonField field = createLicAccountField();

		if (values.size() >= 1)
		{
			field.setType(FieldDataType.list);
			field.setValues(values);

			return field;
		}

		return field;
	}

	/**
	 * Создать список полей "задолженность"
	 * @param codeService код поставщика услуг
	 * @param abonentId abonentId
	 * @return список задолженностей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<CommonField> createDebtFields(String codeService, String abonentId) throws GateException, GateLogicException
	{
		List<CommonField> debtsList = new ArrayList<CommonField>();
		try
		{
			GateMessage message = buildReqGlobalRequest(codeService, abonentId);
			Document result = messagingService.sendOnlineMessage(message, null);
			//Сложная услуга имеет список задолженностей для подуслуг ListOfSubDebt
			if (XmlHelper.selectSingleNode(result.getDocumentElement() ,"/AnsGlobal/Global/ListOfAbonent/Abonent/Debt/ListOfSubDebt") == null)
			{
				//Для основной услуги берем значение из Debt
				Element element = XmlHelper.selectSingleNode(result.getDocumentElement() ,"/AnsGlobal/Global/ListOfAbonent/Abonent/Debt");
				debtsList.add(createDebtField(Constants.DEBT_FIELD_NAME, element.getAttribute("amount")));
			}
			else
			{
				//Для сложной услуги создаем поля для каждой задолженности подуслуг
				NodeList nodeList  = XmlHelper.selectNodeList(result.getDocumentElement(), "/AnsGlobal/Global/ListOfAbonent/Abonent/Debt/ListOfSubDebt/SubDebt");
				//Сумма всех задолженностей
				BigDecimal complexDebtAmount = new BigDecimal("0");
				for (int i = 0; i < nodeList.getLength(); i++)
				{
					Element subDebt = (Element) nodeList.item(i);
					Element subService = XmlHelper.selectSingleNode(subDebt, "SubService");
					String externalId = generateSubExternalId(subService.getAttribute("id"), Constants.DEBT_FIELD_NAME);
					debtsList.add(createSubDebtField(externalId, subDebt.getAttribute("amount"), subService.getAttribute("id")));
					complexDebtAmount = complexDebtAmount.add(new BigDecimal(subDebt.getAttribute("amount")));
				}
				//Создаем невидимое поле общей задолженности с суммированным значением всех задолженностей подуслуг, что бы отображать его в смс
				CommonField complexDebt = createDebtField(Constants.DEBT_FIELD_NAME, complexDebtAmount.toString());
				complexDebt.setVisible(false);
				debtsList.add(complexDebt);
			}
		}
		catch (TransformerException e)
		{
			throw new GateException("Ошибка получения задолженности", e);
		}
		catch (GateLogicException e)
		{
			throw new GateLogicException(e);
		}
		return debtsList;
	}

	/**
	 * Построить externalId поля подуслуги вида: SS- + идентификатор подуслуги + разделитель + название поля
	 * @param id идентификатор подуслуги
	 * @param fieldName имя поля
	 * @return externalId
	 */
	private String generateSubExternalId(String id, String fieldName)
	{
		StringBuilder externalId = new StringBuilder();
		externalId.append(Constants.SS_PREFIX);
		externalId.append(id);
		externalId.append(Constants.SS_DELIMITER);
		externalId.append(fieldName);

		return externalId.toString();
	}

	/**
	 * Построить externalId поля суммы подуслуги
	 * @param id идентификатор подуслуги
	 * @return externalId
	 */
	private String generateSubServiceAmountExternalId(String id)
	{
		StringBuilder externalId = new StringBuilder();
		externalId.append(Constants.SS_AMOUNT_PREFIX);
		externalId.append(id);

		return externalId.toString();
	}

	/**
	 * Построить externalId поля Имя подуслуги
	 * @param id идентификатор подуслуги
	 * @return externalId
	 */
	private String generateSubServiceNameExternalId(String id)
	{
		StringBuilder externalId = new StringBuilder();
		externalId.append(Constants.SS_NAME_PREFIX);
		externalId.append(id);

		return externalId.toString();
	}

	/**
	 * Создать поле Задолженность
	 * @param externalId внешний идентификатор
	 * @param amountValue значение задолженности
	 * @return поле Задолженность
	 */
	private CommonField createDebtField(String externalId, String amountValue)
	{
		CommonField field = new CommonField();

		field.setExternalId(externalId);
		field.setType(FieldDataType.money);
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(true);
		field.setKey(true);
		field.setRequiredForConformation(true);
		field.setRequiredForBill(true);
		fillDebtFeldNameAndValue(field, amountValue);
		return field;
	}

	private void fillDebtFeldNameAndValue(CommonField field, String amountValue)
	{
		Boolean isDebtNedative = isDebtNegative();
		if (isDebtNedative == null)
		{
			//настройка не задана. выводим как есть.
			field.setName("Задолженность");
			field.setDefaultValue(amountValue);
			return;
		}
		try
		{
			BigDecimal debt = new BigDecimal(amountValue);
			field.setName(isDebtNedative ^ debt.compareTo(BigDecimal.ZERO) > 0 ? "Задолженность" : "Переплата");
			field.setDefaultValue(debt.abs().toPlainString());
		}
		catch (Exception ignored)
		{
			//не смогли распарсить значение задолженности. выводим как есть.
			field.setName("Задолженность");
			field.setDefaultValue(amountValue);
			return;
		}
	}

	/**
	 * Создать поле Задолженность для подуслуги
	 * @param externalId внешний индетификатор
	 * @param amountValue значение задолженности
	 * @param groupName название группы, к которой принадлежит поле
	 * @return поле Задолженность
	 */
	private CommonField createSubDebtField(String externalId, String amountValue, String groupName)
	{
		//создаем обычно поле задолженность
		CommonField field = createDebtField(externalId, amountValue);
		//устанавливаем имя группы
		field.setGroupName(groupName);
		//задолженность подуслуг в чеке не печатаем
		field.setRequiredForBill(false);
		//задолженность подуслуг не отображаем в смс при подтверждении
		field.setRequiredForConformation(false);

		return field;
	}

	/**
	 * Определяем является ли данное поле полем licAccount
	 * @param field доп. поле
	 * @return true/false
	 */
	public Boolean isLicAccountField(Field field)
	{
		String fieldName = field.getName();
		return (Constants.ACCPU_FIELD_NAME.equals(field.getExternalId()) ||
				(fieldName.contains("Лиц") || fieldName.contains("лиц") || fieldName.contains("ЛИЦ")) &&
					(fieldName.contains("Счёт") || fieldName.contains("Счет") || fieldName.contains("счёт") || fieldName.contains("счет") || fieldName.contains("СЧЁТ") || fieldName.contains("СЧЕТ")));
	}

	/**
	 * @param fields доп. поля платежа
	 * @return поле licAccount
	 */
	public Field getLicAccountField(List<Field> fields)
	{
		if (fields != null)
		{
			for (Field field : fields)
			{
				if (isLicAccountField(field))
					return field;
			}
		}
		return null;
	}

	private String getEscapeStringForField(String externalId) throws GateException, GateLogicException, TransformerException
	{
		GateMessage message = messagingService.createRequest("ReqXObject");
		Document doc = message.getDocument();
		Element root = doc.getDocumentElement();

		Element xObject = doc.createElement("XObject");
		xObject.setAttribute("id", externalId);
		root.appendChild(xObject);

		Element listOfMoreInfo = doc.createElement("ListOfMoreInfo");
		listOfMoreInfo.setAttribute("request", "false");
		root.appendChild(listOfMoreInfo);

		Element moreInfo = doc.createElement("moreInfo");
		moreInfo.setAttribute("request", "true");
		moreInfo.setAttribute("maxItems", "all");
		moreInfo.setAttribute("xpRef", "/RSASMsg/AnsXObject/XObject/Extent/");
		moreInfo.setAttribute("action", "stop");
		listOfMoreInfo.appendChild(moreInfo);

		Document response = messagingService.sendOnlineMessage(message, null);
		Element node = XmlHelper.selectSingleNode(response.getDocumentElement(), "XObject/Extent");
		if(node != null)
		  	return node.getAttribute("esc")+node.getAttribute("sep");

		return null;
	}

	/**
	 * @return поле "Номер лицевого счета"
	 */
	public CommonField createLicAccountField()
	{
		CommonField field = new CommonField();

		field.setName("Номер лицевого счета");
		field.setExternalId(Constants.ACCOUNT_FIELD_NAME);
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setKey(true);
		//тк по интеграции признака «Атрибут является важным для подтверждения» нет, то устанавливаем по признаку isKey
		field.setRequiredForConformation(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);

		return field;
	}

	/**
	 *
	 * @param field поле
	 * @return наше поле или же биллинговой системы
	 */
	public boolean isOurField(Field field)
	{
		return isOurField(field.getExternalId());
	}

	/**
	 * @param externalId внешний идентификатор поля
	 * @return наше поле или же биллинговой системы
	 */
	public boolean isOurField(String externalId)
	{
		return Constants.ACCOUNT_FIELD_NAME.equals(externalId) || externalId.endsWith(Constants.DEBT_FIELD_NAME)
				|| Constants.CALENDAR_FIELD_NAME.equals(externalId) || BillingPaymentHelper.REQUEST_BILLING_ATTRIBUTES_FIELD_NAME.equals(externalId)
					|| BillingPaymentHelper.DEFAULT_AMOUNT_FIELD_ID.equals(externalId) || externalId.startsWith(Constants.SS_AMOUNT_PREFIX )
						|| externalId.startsWith(Constants.SS_NAME_PREFIX) || externalId.equals(Constants.NAME_MAIN_SERVICE_FIELD)
							|| Constants.ACCPU_FIELD_NAME.equals(externalId) || Constants.PAYORDER_AMOUNT_FIELD_NAME.equals(externalId)
								|| Constants.MAIN_SUM_NAME_FIELD_NAME.equals(externalId);
	}

	private List<String> tokenaze(String str, String separator)
	{
		List<String> values = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(str, separator);

		while (tokenizer.hasMoreTokens())
		{
			values.add(tokenizer.nextToken());
		}
		return values;
	}

	private String convertAmountToString(AccountPaymentSystemPayment payment) throws GateException
	{
		try
		{
			List<Field> extendedFields = payment.getExtendedFields();

			// вытаскиваем поле с названием поля главной суммы
			CommonField mainSumFieldName =
					(CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.MAIN_SUM_NAME_FIELD_NAME);

			// если такое поле есть, значит был сделан хак в методе updateMainSumField, это возможно только елси
			// после этого произошла логическая ошибка и клиент по новой сделал prepare
			if(mainSumFieldName != null)
			{
				// вытаскиваем поле главной суммы
				CommonField mainSumField = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);

				// вытаскиваем поля по названию
				CommonField prevMainSumField =
						(CommonField) BillingPaymentHelper.getFieldById(extendedFields, (String) mainSumFieldName.getValue());

				String value = (String) prevMainSumField.getValue();
				mainSumField.setValue(value);

				return value;
			}

			Money amount = payment.getDestinationAmount();
			return amount == null ? "0" : amount.getDecimal().toString();

		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}

	}

	private String getFieldValue(Field field)
	{
		if (FieldDataType.money == field.getType())
		{
			return (field.getValue() == null || StringHelper.isEmpty(field.getValue().toString())) ?
					"0" : field.getValue().toString();
		}
		return field.getValue() == null ? "" : field.getValue().toString();
	}

	/**
	 * Проверяем составная ли сумма у платежа (количество полей с типом сумма > 1)
	 * @param fields доп. поля
	 * @return true составная
	 */
	private boolean isComplexSum(List<Field> fields)
	{
		int count = 0;
		for (Field field : fields)
		{
			if (FieldDataType.money == field.getType() && !isOurField(field))
				count++;

			if (count > 1)
				return true;
		}
		return false;
	}

	private void updateMainSumField(AccountPaymentSystemPayment payment, String paymentAmount) throws GateException
	{
		try
		{
			List<Field> fields = payment.getExtendedFields();
			CommonField mainSumField = (CommonField) BillingPaymentHelper.getMainSumField(fields);

			// в любом случае сумму нужно просетить в дестинайшн
			payment.setDestinationAmount(new Money(new BigDecimal(paymentAmount), currencyService.getNationalCurrency()));

			// поле сумма одно, делаем без извращений
			if (!isComplexSum(fields))
			{
				mainSumField.setValue(paymentAmount);
				return;
			}

			// если мы уже делали хак, который ниже
			if(Constants.PAYORDER_AMOUNT_FIELD_NAME.equals(mainSumField.getExternalId())
					&& BillingPaymentHelper.getFieldById(fields, Constants.MAIN_SUM_NAME_FIELD_NAME) != null)
			{
				// сеттим в общую сумму
				mainSumField.setValue(paymentAmount);
				return;
			}

			//хак
			//убираем признак главной суммы уполя с признаком главной суммы
			mainSumField.setMainSum(false);

			//сохраняем externalId поля с признаком главной суммы в доп. поле
			fields.add(createMainSumNameField(mainSumField.getExternalId()));

			//добавляем новое поле с общей суммой платежа
			fields.add(createAmountBaseField(paymentAmount));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Создаем поле общей суммы платежа
	 * @param value значение
	 * @return поле общей суммы платежа
	 */
	private Field createAmountBaseField(String value)
	{
		CommonField field = new CommonField();
		field.setName("Общая сумма платежа");
		field.setExternalId(Constants.PAYORDER_AMOUNT_FIELD_NAME);
		field.setEditable(false);
		field.setVisible(true);
		field.setValue(value);
		field.setType(FieldDataType.money);
		field.setMainSum(true);
		return field;
	}

	private Field createMainSumNameField(String value)
	{
		CommonField nameField = new CommonField();
		nameField.setName(Constants.MAIN_SUM_NAME_FIELD_NAME);
		nameField.setExternalId(Constants.MAIN_SUM_NAME_FIELD_NAME);
		nameField.setType(FieldDataType.string);
		nameField.setEditable(false);
		nameField.setVisible(false);
		nameField.setValue(value);

		return nameField;
	}

	/**
	 * извлекает значение поля amount
	 * @param fields
	 * @return
	 * @throws GateException
	 */
	private String getAmountFieldValue(List<Field> fields) throws GateException
	{
		if (isComplexSum(fields))
		{
			return getFieldValue(BillingPaymentHelper.getFieldById(fields, Constants.PAYORDER_AMOUNT_FIELD_NAME));
		}

		return getFieldValue(BillingPaymentHelper.getMainSumField(fields));
	}

	/**
	 * Получить отправляемый в город идентификатор поставщика
	 * (для остновной услуги - это abonentId, для доп. услуги - это recipientId)
	 * @param abonentId   идентификатор (связка между поставщиком и услугой)
	 * @param codeService код услуги
	 * @return идентификатор поставщика услуг
	 */
	private String getPointCode(String abonentId, String codeService, boolean mainService)
	{
		if (mainService)
			return abonentId;

		int idx = codeService.indexOf("|");
		return idx > -1 ? codeService.substring(0, idx) : codeService;
	}

	private void updateExtendedFields(AccountPaymentSystemPayment payment, Document document) throws GateException
	{
		try
		{
			List<Field> extendedFields = payment.getExtendedFields();
			//сложная услуга имеет структуру ListOfSubOrder
			if (XmlHelper.selectSingleNode(document.getDocumentElement(), "EOrder/PayOrder/ListOfSubOrder") == null)
			{
				NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), "EOrder/PayOrder/MainOrder/ListOfOrderExtent/OrderExtent");
				for (int i = 0; i < nodeList.getLength(); i++)
				{
					Element node = (Element) nodeList.item(i);
					String id  = XmlHelper.selectSingleNode(node, "Extent").getAttribute("id");
					String val = node.getAttribute("val");

					CommonField field = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, id);
					if (field != null)
						field.setValue(val);
				}
			}
			else
			{
				//получаем список элементов SubOrder
				NodeList subOrders = XmlHelper.selectNodeList(document.getDocumentElement(), "EOrder/PayOrder/ListOfSubOrder/SubOrder");
				for (int i = 0; i < subOrders.getLength(); i++)
				{
					Element subOrder = (Element) subOrders.item(i);
					Element subService = XmlHelper.selectSingleNode(subOrder, "SubService");
					String subServiceId = subService.getAttribute("id");
					//получаем список полей подуслуги
					NodeList orderExtents = XmlHelper.selectNodeList(subOrder, "MainOrder/ListOfOrderExtent/OrderExtent");
					for (int j = 0; j < orderExtents.getLength(); j++)
					{
						Element orderExtent = (Element) orderExtents.item(j);
						Element extent = XmlHelper.selectSingleNode(orderExtent, "Extent");
						String extentId = extent.getAttribute("id");
						String val = orderExtent.getAttribute("val");
						//externalId формируем так же, как и при создании доп полей подуслуг
						String externalId = generateSubExternalId(subServiceId, extentId);

						CommonField field = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, externalId);
						if (field != null)
							field.setValue(val);
					}
				}
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private Element createPayPeriodsElement(Document document, String payPeriods, CalendarFieldPeriod calendarFieldPeriod) throws GateException
	{
		StringTokenizer tokenizer = new StringTokenizer(payPeriods, ";");

		switch (calendarFieldPeriod)
		{
			case broken :
			{
				while (tokenizer.hasMoreTokens())
				{
					String period = tokenizer.nextToken();
					if (StringHelper.isEmpty(period))
						continue;

					int month = Integer.parseInt(period.substring(0, period.indexOf("/")))-1;
					int year = Integer.parseInt(period.substring(period.indexOf("/") + 1, period.length()));

					Element payPeriod = document.createElement("PayPeriod");
					int days = DateHelper.getDaysInMonth(month, year);
					payPeriod.setAttribute("startDate", String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + "1");
					payPeriod.setAttribute("endDate", String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(days));

					return payPeriod;
				}
			}
			case unbroken :
			{
				String period = tokenizer.nextToken();

				int month = Integer.parseInt(period.substring(0, period.indexOf("/")))-1;
				int year = Integer.parseInt(period.substring(period.indexOf("/") + 1, period.length()));

				Element payPeriod = document.createElement("PayPeriod");
				payPeriod.setAttribute("startDate", String.valueOf(year) + "-" + (String.valueOf(month + 1).length() == 1 ? "0" + String.valueOf(month + 1) : String.valueOf(month + 1)) + "-" + "01");

				while (tokenizer.hasMoreTokens())
				{
					period = tokenizer.nextToken();
					month  = Integer.parseInt(period.substring(0, period.indexOf("/")))-1;
					year   = Integer.parseInt(period.substring(period.indexOf("/") + 1, period.length()));
				}

				int days = DateHelper.getDaysInMonth(month, year);
				payPeriod.setAttribute("endDate", String.valueOf(year) + "-" + (String.valueOf(month + 1).length() == 1 ? "0" + String.valueOf(month + 1) : String.valueOf(month + 1)) + "-" + String.valueOf(days));

				return payPeriod;
			}

			default : throw new GateException("Некорректный тип периода для поля payPeriod.");
		}
	}

	protected String getOrderExtentValue(Field field, String branch)
	{
		if (field.getName().equals("ППП"))
		{
			return branch + "/" + "00888";
		}
		else if (field.getName().equals("ТИП_П"))
		{
			return "СБОЛ";
		}
		else
		{
			return (String) field.getValue();
		}
	}

	/**
	 * Определить, является ли услуга сложной по дополнительным полям
	 * поля сложной подуслуги имеют префик SS
	 * @param extendedFields дополнительные поля
	 * @return сложная ли подуслуга
	 */
	public boolean isComplexService(List extendedFields)
	{
		for (Object object : extendedFields)
		{
			Field field = (object instanceof FieldWithByNameAndType) ? ((FieldWithByNameAndType) object).getField() :(Field) object;
			String externalId = field.getExternalId();
			if (externalId.startsWith(Constants.SS_PREFIX) || externalId.startsWith(Constants.SS_AMOUNT_PREFIX))
				return true;
		}
		return false;
	}

	private List<ListValue> getFieldValuesByByNameValue(Element node, String byName, String separator)
	{
		List<String> defValues = tokenaze(node.getAttribute("defval"), separator);
		List<ListValue> values = new ArrayList<ListValue>(defValues.size());

		Integer idx = 0;
		for (String value: defValues)
		{
			values.add(byName.equalsIgnoreCase("no") ? new ListValue(value, idx.toString()) : new ListValue(value, value));
			idx++;
		}
		return values;
	}

	/**
	 * Создать поле "сумма" для сложной услуги
	 * @return поле "сумма"
	 */
	private Field createComlexServiceAmountField()
	{
		CommonField amountField = BillingPaymentHelper.createAmountField();
		amountField.setEditable(false);
		amountField.setVisible(false);
		amountField.setDefaultValue("0");
		return amountField;
	}

	/**
	 * Создание скрытого флажка
	 * @param name ExternalId флажка
	 * @param value значение
	 * @return сформированный field
	 */
	public Field createHiddenFlag(String name, Boolean value)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setName(name);
		field.setExternalId(name);
		field.setVisible(false);
		field.setRequired(false);
		field.setEditable(false);
		field.setValue(value.toString());
		field.setDefaultValue(value.toString());
		return field;
	}

	/**
	 * true - отрицательные суммы задолженности воспринимаются как задолженность. положительные как переплата
	 * false - отрицательные суммы задолженности воспринимаются как переплата. положительные как задолженность
	 * если значение не задано, то знак задолженности не учитывается.
	 * @return является ли задолженность отрицательной.
	 */
	private Boolean isDebtNegative()
	{
		String property = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(IS_DEBT_NEGATIVE_PROPERTY_NAME);
		if (StringHelper.isEmpty(property))
			return null;
		else
			return Boolean.valueOf(property);
	}

}
