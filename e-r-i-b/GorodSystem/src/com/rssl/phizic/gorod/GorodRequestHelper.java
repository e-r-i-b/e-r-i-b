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
	 * ������������ ������� ReqCard
	 * @param cardId ������������� �����
	 * @return ������ ReqCard
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
	 * ������������ ������� ReqGlobal
	 * @param codeService     ��� ������
	 * @param licAccountValue �������� ��������� ���� licAccount
	 * @return ������ ReqGlobal
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
     * ������������ ������� ReqXObject,
     * ������ ���. ����� �� ���� ���������� �����
     * @param codeService ��� �������
     * @return ������ ReqXObject
     * @throws GateException
     * @throws GateLogicException
     */
    public GateMessage buildReqXObjectRequest(String codeService, boolean mainService) throws GateException, GateLogicException
    {
        GateMessage message = messagingService.createRequest("ReqXObject");

        Document doc = message.getDocument();
        Element root = doc.getDocumentElement();

        Element xObject = doc.createElement("XObject");
	    //���������� ��������� ������
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
	 * ������������ ������� ReqXObject
     * ������ ���. ����� �� ���� ������ abonentId �
     * ���� ���������� �����
	 * @param abonentId   ������������� (������ ����� ����������� � �������)
	 * @param codeService ��� ������
	 * @return ������ ReqXObject
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public GateMessage buildReqXObjectRequest(String abonentId, String codeService, boolean mainService) throws GateException, GateLogicException
	{
		GateMessage message = messagingService.createRequest("ReqXObject");

		Document doc = message.getDocument();
		Element root = doc.getDocumentElement();

		Element xObject = doc.createElement("XObject");
		//���������� ��������� ������
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

	//TODO � ������ �� �������� ������������ ���� � ��� �� ����� ��� ������������ ������� ReqCalcEOrder ��� ������� calcmode (1,2),
	//TODO �� �� ����������... ���������� ��� ����, ����������� ��������� �����������
	/**
	 * ������������ ������� ReqCalcEOrder
	 * @param abonentId       ������������� (������ ������ � ���������� �����)
	 * @param recipientId     ������������� ���������� �����
	 * @param extendedFields  ���. ����
	 * @return ������ ReqCalcEOrder
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
	 * ��������� ��������� ListOfSubOrder ��� ������� ������
	 * @param document ��������
	 * @param extendedFields �������
	 * @return ��������� ListOfSubOrder
	 */
	public Element createListOfSubOrderElement(Document document, List<FieldWithByNameAndType> extendedFields)
	{
		//�������� ��������� ����� ������������� ���������
		String subServiceId = "";
		Element subOrder = null;
		Element subService = null;
		Element mainOrder = null;
		Element listOfOrderExtent = null;
		//������ ���������
		Element listOfSubOrder = document.createElement("ListOfSubOrder");
		//������������� ������ ��������
		Collections.sort(extendedFields, new GorodSubServiceFieldWithByNameAndTypeComparator());

		for (FieldWithByNameAndType extendedField : extendedFields)
		{
			Field field = extendedField.getField();
			//���� ���� �� ������������
			if (!isOurField(field.getExternalId()))
			{
				String[] idArray = field.getExternalId().split(Constants.SS_DELIMITER);
				String ssPrefixWithId = idArray[0];
				//���� ��������� ������� ������ �� �������� ������������� ���������, ������, �������� ���������� ��� ����� ���������
				if (StringHelper.isEmpty(subServiceId) || !ssPrefixWithId.endsWith(subServiceId))
				{
					if (subOrder != null)
						listOfSubOrder.appendChild(subOrder);
					//�������� ������������� ���������
					subServiceId = ssPrefixWithId.substring(Constants.SS_PREFIX.length(), ssPrefixWithId.length());
					//������� ��������� SubOrder
					subOrder = document.createElement("SubOrder");
					//������� ��������� SubService � �� ���������
					subService = document.createElement("SubService");
					subService.setAttribute("id", subServiceId);
					subOrder.appendChild(subService);
					//������� ��������� MainOrder
					mainOrder = document.createElement("MainOrder");
					mainOrder.setAttribute("amount", "0");
					subOrder.appendChild(mainOrder);
					//������ ��������� ListOfOrderExtent
					listOfOrderExtent = document.createElement("ListOfOrderExtent");
					mainOrder.appendChild(listOfOrderExtent);
				}
				//��������� ���� ���������
				listOfOrderExtent.appendChild(createOderExtentElement(document, field.getDefaultValue(), idArray[1]));
			}
		}
		//��������� ��������� �������������� �������
		listOfSubOrder.appendChild(subOrder);
		return listOfSubOrder;
	}

	/**
	 * ��������� ��������� ListOfSubOrder ��� ������� ������
	 * @param document ��������
	 * @param payment ������
	 * @param hasPaymentPeriods ������� ��������
	 * @param calendarFieldPeriod ���� ������
	 * @return ��������� ListOfSubOrder
	 * @throws GateException
	 */
	public Element createListOfSubOrderElement(Document document, AccountPaymentSystemPayment payment, boolean hasPaymentPeriods, CalendarFieldPeriod calendarFieldPeriod) throws GateException
	{
		try
		{
			//�������� ��������� ����� ������������� ���������
			String subServiceId = "";
			Element subOrder = null;
			Element subService = null;
			Element mainOrder = null;
			Element listOfOrderExtent = null;
			//������ ���������
			Element listOfSubOrder = document.createElement("ListOfSubOrder");
			//��� ������������ �������� � ������������� ������ ��������
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
				//���� ���� �� ������������
				if (!isOurField(field.getExternalId()))
				{
					String[] idArray = field.getExternalId().split(Constants.SS_DELIMITER);
					String ssPrefixWithId = idArray[0];
					//���� ��������� ������� ������ �� �������� ������������� ���������, ������, �������� ���������� ��� ����� ���������
					if (StringHelper.isEmpty(subServiceId) || !ssPrefixWithId.endsWith(subServiceId))
					{
						if (subOrder != null)
							listOfSubOrder.appendChild(subOrder);
						//�������� ������������� ���������
						subServiceId = ssPrefixWithId.substring(Constants.SS_PREFIX.length(), ssPrefixWithId.length());
						//������� ��������� SubOrder
						subOrder = document.createElement("SubOrder");
						//������� ��������� SubService � �� ���������
						subService = document.createElement("SubService");
						subService.setAttribute("id", subServiceId);
						subOrder.appendChild(subService);
						//������� ��������� MainOrder
						mainOrder = document.createElement("MainOrder");
						//�������� ���� ����� ���������, ��� �� �������� ��� ��������
						Field amount = BillingPaymentHelper.getFieldById(extendedFields, generateSubServiceAmountExternalId(subServiceId));
						mainOrder.setAttribute("amount", getFieldValue(amount));
						subOrder.appendChild(mainOrder);
						//������ ��������� ListOfOrderExtent
						listOfOrderExtent = document.createElement("ListOfOrderExtent");
						mainOrder.appendChild(listOfOrderExtent);
					}
					//��������� ���� ���������
					String branch = payment.getReceiverOfficeCode().getFields().get("branch");
					listOfOrderExtent.appendChild(createOderExtentElement(document, getOrderExtentValue(field, branch), idArray[1]));
				}
			}
			//��������� ��������� �������������� �������
			listOfSubOrder.appendChild(subOrder);
			return listOfSubOrder;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ������� ��������� MainOrder ��� ������� ������
	 * @param document ��������
	 * @param extendedFields �������������� ����
	 * @return ��������� MainOrder
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
			//� ������ �� ��������� ���� (������������ ���������) ����
			if (!isOurField(extendField.getField().getExternalId()))
				listExtents.appendChild(createOderExtentElement(document, field.getDefaultValue(), field.getExternalId()));
		}
		return mainOrder;
	}

	/**
	 * ������� ��������� MainOrder ��� ������� ������
	 * @param document ��������
	 * @param payment ������
	 * @param hasPaymentPeriods ������� ��������
	 * @param calendarFieldPeriod ���� ������
	 * @param amount �������� ����� ����� ������
	 * @return ��������� MainOrder
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
				//� ������ �� ��������� ���� (������������ ���������) ����
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
	 * ��������� ��������� ������� OrderExtent
	 * @param document ��������
	 * @param defValue ��������
	 * @param id �������������
	 * @return ��������� OrderExtent
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
	 * ������������ ������� ReqCalcEOrder
	 * @param abonentId �������������
	 * @param payment   ������
	 * @return ������ ReqCalcEOrder
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
	 * ������������ ������� ReqSaveEOrder
	 * @param abonentId �������������
	 * @param payment ������
	 * @return ������ ReqSaveEOrder
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
	 * �������� abonent-id (������ ����� ����������� � ��������)
	 * @param payment ������
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

		throw new GateLogicException("��������� ������� ���� �� ������ � ���� ������!");
	}

	/**
	 * �������� abonent-id (������ ����� ����������� � ��������)
	 * @param recipientId ������-� ����������
	 * @param keyValue       �������� ���� ������� ����
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
				throw new GateLogicException("��������� ������� ���� �� ������ � ���� ������!");

			return node.getAttribute("id");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� abonent-id (������ ����� ����������� � ��������)
	 * @param field �������� ���� licAccount
	 * @return abonent-id (������ ����� ����������� � ��������)
	 * @throws GateException
	 */
	public String getLicAccountId(CommonField field) throws GateException
	{
		if (FieldDataType.list == field.getType())
		{
			List<ListValue> values = field.getValues();
			if (values.size() > 1)
			{
				//�������� ������ ��� ������
				return (String) field.getValue();
			}
			if (values.size() == 1)
			{
				//���� ������� �� ����������, ������� ����� ��� ����
				return values.get(0).getId();
			}
			throw new GateException("�� ������ ������������ ����������� �������� �����.");
		}

		return (String) field.getValue();
	}

	/**
	 * �������� �������� ��������� ���� licAccount
	 * @param field �������� ���� licAccount
	 * @return ��������
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
				//�������� ������ ��� ������
				for (ListValue value : values)
				{
					if (fieldValue.equals(value.getId()))
						return value.getValue();
				}
				return null;
			}
			if (values.size() == 1)
			{
				//���� ������� �� ����������, ������� ����� ��� ����
				return values.get(0).getValue();
			}
			throw new GateException("�� ������ ������������ ����������� �������� �����.");
		}

		return (String) field.getValue();
	}

	/**
	 * @param payment ������
	 * @return ������� ������.
	 * @throws GateException
	 */
	private String getPayPeriod(AccountPaymentSystemPayment payment) throws GateException
	{
		Field payPeriod = BillingPaymentHelper.getFieldById(payment, Constants.CALENDAR_FIELD_NAME);
		return payPeriod == null ? "" : (String) payPeriod.getValue();
	}

	/**
	 * ������������ ���. ����������
	 * @param payOrder
	 * @param id
	 * @param tagName
	 */
	public void addPayerInfo(Element payOrder, String id, String tagName)
	{
		// ���� ���� ����������, ��� ���������� ��������� �������� ������� � ������� �� �����������.
		//���� �����������, �� ���� ����� ������ ����� ������� �� id � ��������� ���������.
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

			//TODO �������� � ����� ������������, ����� �� ���� � ������� � ������ � ������������� �����������
			//TODO ���� (�� 26,02,2009) ��� �� �����
//		Element region = doc.createElement("Region");
//		region.setAttribute("name", clientAddress.getProvince());
//		city.appendChild(region);
//
//		Element country = doc.createElement("Country");
//		country.setAttribute("name", "������"); //� ��� ��� ������ � ������ ����������. ������ ������� ������������,
//		region.appendChild(country);    		// ��� ���������� ����� ���� � ������.. �� ������� ���� ����������� � ���� ������ ���� (���� �� ����������)
//		}
	}

    /**
     * ��������� ������ ���. ����� �� ������� �������
     * (��� ������� �������� �����)
     * @param codeService ��� �������
     * @return ������ �����
     * @throws GateLogicException
     * @throws GateException
     */
    public List<Field> getExtendedFields(String codeService, boolean mainService) throws GateLogicException, GateException
    {
        //�������� ������ �������������� �����
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

                //������������ � ����� ����
				FieldDataType fieldType = FieldDataType.fromValue(dataType);
				if (FieldDataType.list == fieldType || node.getAttribute("isArray").equals("true"))
				{
					//�� ��������� ������ ���������� ��������, � �� ������
					String byName = "YES";
					String esc = node.getAttribute("ecs");
					String sep = node.getAttribute("sep");

					//��� ������� ���������� byName
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

                //��������� ��������� ��������� ����.
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

                //�� ��������� ��������������� ���� ����� ���������.
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
                ������� ���� ���λ, ������ (�name="���" , name="�����"�),
                ���� ��������� �������� visible=false, editable=true.
                ���� ���������: ��� ���� ������ ������ ���� �� ����� �������������� � ���������� �� �� ������ ������������� � ��������� �������.
                ��� ����� ����� ���������� ����� ��������� visible/editable, ��� ��������� false/true ������ �� ����� �������������� �
                ���������� �� ����� ��������� (��� �� ����� �������������).
                ��� ����, ��� �� ����� ���������� �� ����������� � ������� ������, ������������ ����� � ��������� visible=false
                ������������� ���������� editable=false (�.�. ��� �������� ����� ������ ��� ����� �������, � ������ ���� �� �����
                ��������� ��������� ����).
            */
                if (Constants.FIO_FIED_NAME.equals(field.getName()) || Constants.ADDRESS_FIED_NAME.equals(field.getName()))
                {
                    field.setVisible(false);
                    field.setEditable(true);
                }

                if (isLicAccountField(field) && mainService)
                {
                    //������� ���� �������������� ������� - �� ������ �����, � �� ������ �������� ����
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
	 * ��������� ����������� ����� �� ������� �������
	 * @param abonentId   ������������� (������ ������ � ���������� �����)
	 * @param recipientId ������������� ���������� �����
	 * @return ������ �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<Field> getExtendedFields(String abonentId, String recipientId, boolean mainService) throws GateLogicException, GateException
	{
		//�������� ������ �������������� �����
		GateMessage reqXObjectMessage = buildReqXObjectRequest(abonentId, recipientId, mainService);
		Document reqXObjectResponse   = messagingService.sendOnlineMessage(reqXObjectMessage, null);

		List<FieldWithByNameAndType> extendedFields = getGateFields(reqXObjectResponse, abonentId, mainService);
		//����������� �������������� �������� ��� ���. ����� (calcmode=1)
		GateMessage reqCalcEOrderMessage = buildReqCalcEOrderCalcMode1Request(abonentId, recipientId, extendedFields, mainService);
		Document reqCalcEOrderResponse   = messagingService.sendOnlineMessage(reqCalcEOrderMessage, null);

		Map<String, FieldWithByNameAndType> fieldMap = new HashMap<String, FieldWithByNameAndType>();
		for (FieldWithByNameAndType field : extendedFields)
			fieldMap.put(field.getField().getExternalId(), field);

		//��������� ���������� ���� ��������������� �������
		try
		{
			List <Field> result = new ArrayList<Field>();
			//��������� ������ ������� ������
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
			//��������� ������ ������� ������
			else
			{
				NodeList subOrders = XmlHelper.selectNodeList(reqCalcEOrderResponse.getDocumentElement(), "EOrder/PayOrder/ListOfSubOrder/SubOrder");
				for (int i = 0; i < subOrders.getLength(); i++)
				{
					Element subOrder = (Element) subOrders.item(i);
					Element subService = XmlHelper.selectSingleNode(subOrder, "SubService");
					String subServiceId = subService.getAttribute("id");
					//�������� ������������ ���� "����������"
					String chargedFieldId = getChargedFieldId(reqXObjectResponse);
					//������ ������������� ���� "����������" ��� ���������
					String subServiceChargedId = StringHelper.isEmpty(chargedFieldId) ? null : generateSubExternalId(subServiceId, chargedFieldId);

					NodeList orderExtents = XmlHelper.selectNodeList(subOrder, "MainOrder/ListOfOrderExtent/OrderExtent");
					for (int j = 0; j < orderExtents.getLength(); j++)
					{
						Element orderExtent = (Element) orderExtents.item(j);
						Element extent = XmlHelper.selectSingleNode(orderExtent, "Extent");
						String extentId = extent.getAttribute("id");
						//����� ����� ����������� ���� � ������ ��������, ��������� �� ��� ��, ��� ��� ���������� ������
						String externalId = generateSubExternalId(subServiceId, extentId);
						FieldWithByNameAndType extraField = fieldMap.get(externalId);

						if (extraField != null)
							updateField(extraField, orderExtent);

						//���� ���� ���� "����������" �� �������� ���� ������������� � ���� ����� ���������
						if (!StringHelper.isEmpty(subServiceChargedId) && externalId.equals(subServiceChargedId))
						{
							String val = orderExtent.getAttribute("val");
							String defVal = orderExtent.getAttribute("defVal");
							//���� ���� ����� ��������� � ������������� �������� �� ����������
							FieldWithByNameAndType amountField = fieldMap.get(generateSubServiceAmountExternalId(subServiceId));
							if (amountField != null)
							{
								CommonField amountFieldImpl = (CommonField) amountField.getField();
								String resultValue = StringHelper.isEmpty(val) ? defVal : val;
								amountFieldImpl.setDefaultValue(StringHelper.isEmpty(resultValue) ? "0" : resultValue);
								//���� �������� ���� ���������� ������, �� �� ���������� ����
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

				//������� ����� ������� ������
				field.setDefaultValue(tokenaze(defVal, separator).size() > tokenaze(val,separator).size() ? defVal : val);

				//�������� ������, ��������� �� ���������, ����������� �����������
				List<String> values = tokenaze(field.getDefaultValue(), separator);

				//�������������� ������, ���������� ���� ������, ���� ����� ����� ��������, ����������� �� ���������� ����
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
	 * �������� ������������� ���� "����������"
	 * @param response ����� ������
	 * @return ������������
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
	 * @param extent ������� ���� ������
	 * @return �������� ���� ����� "����������"
	 */
	private boolean isChargedField(Element extent)
	{
		return extent.getAttribute("short").equals("DEBT") && extent.getAttribute("datatype").equals("NUMBER");
	}

	/**
	 * ��������� ������������� ����� �� ������� �������
	 * @param response ����� ������
	 * @param abonentId   ������������� (������ ������ � ���������� �����)
	 * @return ���. ����
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
				//��� ������� ����� ������ ��� ���� ���������� ����������
				field.setVisible(!isComplexService);

				//�� ��������� ������ ���������� ��������, � �� ������
				String byName   = "YES";
				String dataType = node.getAttribute("datatype");
				int num = Integer.parseInt(node.getAttribute("num"));
				Element el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='LINES']");

				//������������ � ����� ����
				FieldDataType fieldType = FieldDataType.fromValue(dataType);
				if (FieldDataType.list == fieldType || node.getAttribute("isArray").equals("true"))
				{
					String esc = node.getAttribute("ecs");
					String sep = node.getAttribute("sep");

					//��� ������� ���������� byName
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

				//��������� ��������� ��������� ����.
				el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='MAINSUM']");
				if (el != null)
					field.setMainSum(el.getTextContent().equalsIgnoreCase("YES"));

				el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='SUM']");
				if (el != null && el.getTextContent().equalsIgnoreCase("YES"))
					field.setType(FieldDataType.money);

				el = XmlHelper.selectSingleNode(node, "ListOfPrmVal/val[@code='VISIBLE']");
				//��� ������� ����� ������ ��� ���� ���������� ����������
				if (el != null)
					field.setVisible(!(isComplexService || el.getTextContent().equalsIgnoreCase("NO")));

				field.setRequiredForBill(field.isVisible());

				//�� ��������� ��������������� ���� ����� ���������.
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
					//������� ���� �������������� ������� - �� ������ �����, � �� ������ �������� ����
					field.setType(FieldDataType.string);
					field.setVisible(true);
					field.setEditable(true);
					field.setRequired(true);
					field.setRequiredForConformation(true);
					field.setSaveInTemplate(true);
					field.setKey(true);
				}
				//���� ������ ������� � ���� �������� ����� ����������, ������������� ��������
				//��������������� � �������, ������������� ������� �����������
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
			//��� ������� �������� ��������� ���� ���� � ���������� SSAMOUNT- � �� ������ ������ �������������� ����� � ���������� SS-
			if (isComplexService)
			{
				NodeList subServices = XmlHelper.selectNodeList(response.getDocumentElement(), "XObject/Abonent/Service/ListOfSubService/SubService");
				List<FieldWithByNameAndType> ssNamesList = new ArrayList<FieldWithByNameAndType>(subServices.getLength());

				for (int i = 0; i < subServices.getLength(); i++)
				{
					Element subService = (Element) subServices.item(i);
					//������� ���� ����� ��� ������ ���������
					ssNamesList.add(createSSAmountField(subService));
					//������� ���� ��� ��� ������ ���������
					ssNamesList.add(createSSNameField(subService));
					//������� ������ �������� ��� ������ ���������
					for (FieldWithByNameAndType extendField: extendedList)
					{
						result.add(createSSField(extendField, subService.getAttribute("id")));
					}
				}
				result.addAll(ssNamesList);
			}
			else
				result.addAll(extendedList);

			//�������, ���� ���� ���� last������ ������.
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
						throw new GateException("C�������� ��������� ����� � �������� ������. ������ ����� �������� ������ � 1.");

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
						field.setName("������ ������ (�����/���)");
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
	 * ��������� ���� ���������, ������� �� ������ ���� ������, �������� ������ � ���������� �������������
	 * � ������ ���������, ������������ ����� ��� �������������� �����������
	 * @param extendField ���� ������
	 * @param subServiceId ������������� ���������
	 * @return ���� ���������
	 */
	private FieldWithByNameAndType createSSField(FieldWithByNameAndType extendField, String subServiceId)
	{
		CommonField field = (CommonField) extendField.getField();
		FieldWithByNameAndType ssExtendField = new FieldWithByNameAndType();
		CommonField ssField = new CommonField();
		//���������� ������������� ���� SS- + ������������� ��������� + _ + �������� ����
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
	 * ��������� ���� ����� ���������, ���� ����
	 * @param subService ������� ���������
	 * @return ���� ����� ���������
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
		//����� �������� � ����
		ssAmountField.setRequiredForBill(true);

		ssAmountExtendfield.setField(ssAmountField);
		ssAmountExtendfield.setFieldType(ssAmountField.getType().toString());

		return ssAmountExtendfield;
	}

	/**
	 * ��������� ���� ��� ���������, ���� ����
	 * @param subService ������� ���������
	 * @return ���� ��� ���������
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
		//������������ ��������� �������� � ����
		ssNameField.setRequiredForBill(true);

		ssNameExtentField.setField(ssNameField);
		ssNameExtentField.setFieldType(ssNameField.getType().toString());

		return ssNameExtentField;
	}

	public void refreshBillingPayment(String abonentId, AccountPaymentSystemPayment payment, boolean mainService) throws GateLogicException, GateException
	{
		try
		{
			//���������� ���� �������, ���� �� ������� ����� �����.
			//���� ���� ������������ 20 �������, � ������� ������� ��� � �� ������� �������� amoumt, �������� �� 0 - ������ Exception
			for (int i = 0; i < Constants.MAX_ITERATION_SIZE; i++)
			{
				List<Field> fields = payment.getExtendedFields();
				boolean isComplexService = isComplexService(fields);

				//����� ��� ��� ���������� ������, ������� ����� � ��������� ������ ������ �������� ���� ��������, ���� ������ �������
				if (isComplexService)
					updateAmountFields(payment);

				GateMessage prepareMessage = buildReqCalcEOrderCalcMode2Request(abonentId, payment, isComplexService, mainService);
				Document prepareResponse   = messagingService.sendOnlineMessage(prepareMessage, new MessageHeadImpl(null, null, null, payment.getExternalId(), null, null));
				updateExtendedFields(payment, prepareResponse);

				String amount = XmlHelper.selectSingleNode(prepareResponse.getDocumentElement(), "EOrder/PayOrder").getAttribute("amount");
				//���� �������� ����� �� �������� ��������� ���������� ����� ��� ���
				if (StringHelper.isEmpty(amount) || amount.equals("0") || (new BigDecimal(amount)).compareTo(new BigDecimal("0")) <= 0)
					continue;

				updateMainSumField(payment, amount);

				//�������� ������ �� ���������� ������� � ������� �����, ����������, �.�. �� ��� ����� ���������� ��������� ��������� �� "��������" ������.
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

				//������������� ������������� ������� �� ������� �������
				Element ePay = XmlHelper.selectSingleNode(saveResponse.getDocumentElement(), "EOrder/EPay");
				payment.setIdFromPaymentSystem(ePay.getAttribute("unp"));

				//���������� ���������� ���������
				return;
			}
			throw new GateLogicException("������� ��������� ��������� �������. ��������� ��������� ������.");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (GateMessagingException e)
		{
			String message = e.getMessage();
			//���� ��������� �������� ������������ ���, �������� �� ��������� �� ������
			if (message.contains("����� � ������ ������ ���� ������"))
			{
				int beginIndex = message.indexOf("����� � ������ ������ ���� ������");
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
	 * ����������/������������� ����� ��� ����������� �������
	 * @param payment ������
	 */
	private void prepareBillingFields(AccountPaymentSystemPayment payment) throws GateException
	{
		/*
			���������� �� ������ ���� ���λ, ������ (�name="���" , name="�����"�),
			����� ��������� ����� visible=false, editable=true. �� ��� ����������� �� �� ����� ����������
			�������, ����� ���� ���� ������������, �.�. visible=true.
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
	 * ��������� �������� ����� ����� ������� ������. ������������ ����� ��������, ���� ����� �������� �������
	 * ���� �������� ����� ��������� ������, �� ������������ 0
	 * @param payment ������
	 * @throws GateException
	 */
	private void updateAmountFields(AccountPaymentSystemPayment payment) throws GateException
	{
		try
		{
			Money amount = payment.getDestinationAmount();
			//�� ��������� ��������, �� ���������� ������ ������������ 0
			BigDecimal amountValue = new BigDecimal("0");
			//��������� �������� ����� ���� ��������
			for (Field field : payment.getExtendedFields())
			{
				if (field.getExternalId().startsWith(Constants.SS_AMOUNT_PREFIX))
				{
					BigDecimal subAmountValue = new BigDecimal(getFieldValue(field));
					amountValue = amountValue.add(subAmountValue);
					//��������� �� ������� ����� ���������
					if (StringHelper.isEmpty((String) field.getValue()))
					{
						CommonField subAmountField = (CommonField) field;
						subAmountField.setValue("0");
					}
				}
			}
			//������������ ����������� �������� � ������
			payment.setDestinationAmount(new Money(amountValue, amount.getCurrency()));
			//���������� ���� ����� �� ����� �������������
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
	 * ���������� ��� ������������ ������
	 * @param codeService �������������
	 * @return true - ��������, false - ��������������
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
	 * ������� ���� "����� �������� �����"
	 *
	 * @param codeService ��� ���������� �����
	 * @return ���� "����� �������� �����"
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
	 * ������� ���� "����� �������� �����" (��� ������������ ��������)
	 * @param codeService      ��� ���������� �����
	 * @param billingClientId  ������������� ������� � ��������
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
			throw new GateException("�� ������� ������������ ����������� �������� �����");

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
	 * ������� ������ ����� "�������������"
	 * @param codeService ��� ���������� �����
	 * @param abonentId abonentId
	 * @return ������ ��������������
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
			//������� ������ ����� ������ �������������� ��� �������� ListOfSubDebt
			if (XmlHelper.selectSingleNode(result.getDocumentElement() ,"/AnsGlobal/Global/ListOfAbonent/Abonent/Debt/ListOfSubDebt") == null)
			{
				//��� �������� ������ ����� �������� �� Debt
				Element element = XmlHelper.selectSingleNode(result.getDocumentElement() ,"/AnsGlobal/Global/ListOfAbonent/Abonent/Debt");
				debtsList.add(createDebtField(Constants.DEBT_FIELD_NAME, element.getAttribute("amount")));
			}
			else
			{
				//��� ������� ������ ������� ���� ��� ������ ������������� ��������
				NodeList nodeList  = XmlHelper.selectNodeList(result.getDocumentElement(), "/AnsGlobal/Global/ListOfAbonent/Abonent/Debt/ListOfSubDebt/SubDebt");
				//����� ���� ��������������
				BigDecimal complexDebtAmount = new BigDecimal("0");
				for (int i = 0; i < nodeList.getLength(); i++)
				{
					Element subDebt = (Element) nodeList.item(i);
					Element subService = XmlHelper.selectSingleNode(subDebt, "SubService");
					String externalId = generateSubExternalId(subService.getAttribute("id"), Constants.DEBT_FIELD_NAME);
					debtsList.add(createSubDebtField(externalId, subDebt.getAttribute("amount"), subService.getAttribute("id")));
					complexDebtAmount = complexDebtAmount.add(new BigDecimal(subDebt.getAttribute("amount")));
				}
				//������� ��������� ���� ����� ������������� � ������������� ��������� ���� �������������� ��������, ��� �� ���������� ��� � ���
				CommonField complexDebt = createDebtField(Constants.DEBT_FIELD_NAME, complexDebtAmount.toString());
				complexDebt.setVisible(false);
				debtsList.add(complexDebt);
			}
		}
		catch (TransformerException e)
		{
			throw new GateException("������ ��������� �������������", e);
		}
		catch (GateLogicException e)
		{
			throw new GateLogicException(e);
		}
		return debtsList;
	}

	/**
	 * ��������� externalId ���� ��������� ����: SS- + ������������� ��������� + ����������� + �������� ����
	 * @param id ������������� ���������
	 * @param fieldName ��� ����
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
	 * ��������� externalId ���� ����� ���������
	 * @param id ������������� ���������
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
	 * ��������� externalId ���� ��� ���������
	 * @param id ������������� ���������
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
	 * ������� ���� �������������
	 * @param externalId ������� �������������
	 * @param amountValue �������� �������������
	 * @return ���� �������������
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
			//��������� �� ������. ������� ��� ����.
			field.setName("�������������");
			field.setDefaultValue(amountValue);
			return;
		}
		try
		{
			BigDecimal debt = new BigDecimal(amountValue);
			field.setName(isDebtNedative ^ debt.compareTo(BigDecimal.ZERO) > 0 ? "�������������" : "���������");
			field.setDefaultValue(debt.abs().toPlainString());
		}
		catch (Exception ignored)
		{
			//�� ������ ���������� �������� �������������. ������� ��� ����.
			field.setName("�������������");
			field.setDefaultValue(amountValue);
			return;
		}
	}

	/**
	 * ������� ���� ������������� ��� ���������
	 * @param externalId ������� �������������
	 * @param amountValue �������� �������������
	 * @param groupName �������� ������, � ������� ����������� ����
	 * @return ���� �������������
	 */
	private CommonField createSubDebtField(String externalId, String amountValue, String groupName)
	{
		//������� ������ ���� �������������
		CommonField field = createDebtField(externalId, amountValue);
		//������������� ��� ������
		field.setGroupName(groupName);
		//������������� �������� � ���� �� ��������
		field.setRequiredForBill(false);
		//������������� �������� �� ���������� � ��� ��� �������������
		field.setRequiredForConformation(false);

		return field;
	}

	/**
	 * ���������� �������� �� ������ ���� ����� licAccount
	 * @param field ���. ����
	 * @return true/false
	 */
	public Boolean isLicAccountField(Field field)
	{
		String fieldName = field.getName();
		return (Constants.ACCPU_FIELD_NAME.equals(field.getExternalId()) ||
				(fieldName.contains("���") || fieldName.contains("���") || fieldName.contains("���")) &&
					(fieldName.contains("����") || fieldName.contains("����") || fieldName.contains("����") || fieldName.contains("����") || fieldName.contains("�ר�") || fieldName.contains("����")));
	}

	/**
	 * @param fields ���. ���� �������
	 * @return ���� licAccount
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
	 * @return ���� "����� �������� �����"
	 */
	public CommonField createLicAccountField()
	{
		CommonField field = new CommonField();

		field.setName("����� �������� �����");
		field.setExternalId(Constants.ACCOUNT_FIELD_NAME);
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setKey(true);
		//�� �� ���������� �������� �������� �������� ������ ��� �������������� ���, �� ������������� �� �������� isKey
		field.setRequiredForConformation(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);

		return field;
	}

	/**
	 *
	 * @param field ����
	 * @return ���� ���� ��� �� ����������� �������
	 */
	public boolean isOurField(Field field)
	{
		return isOurField(field.getExternalId());
	}

	/**
	 * @param externalId ������� ������������� ����
	 * @return ���� ���� ��� �� ����������� �������
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

			// ����������� ���� � ��������� ���� ������� �����
			CommonField mainSumFieldName =
					(CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.MAIN_SUM_NAME_FIELD_NAME);

			// ���� ����� ���� ����, ������ ��� ������ ��� � ������ updateMainSumField, ��� �������� ������ ����
			// ����� ����� ��������� ���������� ������ � ������ �� ����� ������ prepare
			if(mainSumFieldName != null)
			{
				// ����������� ���� ������� �����
				CommonField mainSumField = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);

				// ����������� ���� �� ��������
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
	 * ��������� ��������� �� ����� � ������� (���������� ����� � ����� ����� > 1)
	 * @param fields ���. ����
	 * @return true ���������
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

			// � ����� ������ ����� ����� ��������� � ����������
			payment.setDestinationAmount(new Money(new BigDecimal(paymentAmount), currencyService.getNationalCurrency()));

			// ���� ����� ����, ������ ��� ����������
			if (!isComplexSum(fields))
			{
				mainSumField.setValue(paymentAmount);
				return;
			}

			// ���� �� ��� ������ ���, ������� ����
			if(Constants.PAYORDER_AMOUNT_FIELD_NAME.equals(mainSumField.getExternalId())
					&& BillingPaymentHelper.getFieldById(fields, Constants.MAIN_SUM_NAME_FIELD_NAME) != null)
			{
				// ������ � ����� �����
				mainSumField.setValue(paymentAmount);
				return;
			}

			//���
			//������� ������� ������� ����� ����� � ��������� ������� �����
			mainSumField.setMainSum(false);

			//��������� externalId ���� � ��������� ������� ����� � ���. ����
			fields.add(createMainSumNameField(mainSumField.getExternalId()));

			//��������� ����� ���� � ����� ������ �������
			fields.add(createAmountBaseField(paymentAmount));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ������� ���� ����� ����� �������
	 * @param value ��������
	 * @return ���� ����� ����� �������
	 */
	private Field createAmountBaseField(String value)
	{
		CommonField field = new CommonField();
		field.setName("����� ����� �������");
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
	 * ��������� �������� ���� amount
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
	 * �������� ������������ � ����� ������������� ����������
	 * (��� ��������� ������ - ��� abonentId, ��� ���. ������ - ��� recipientId)
	 * @param abonentId   ������������� (������ ����� ����������� � �������)
	 * @param codeService ��� ������
	 * @return ������������� ���������� �����
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
			//������� ������ ����� ��������� ListOfSubOrder
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
				//�������� ������ ��������� SubOrder
				NodeList subOrders = XmlHelper.selectNodeList(document.getDocumentElement(), "EOrder/PayOrder/ListOfSubOrder/SubOrder");
				for (int i = 0; i < subOrders.getLength(); i++)
				{
					Element subOrder = (Element) subOrders.item(i);
					Element subService = XmlHelper.selectSingleNode(subOrder, "SubService");
					String subServiceId = subService.getAttribute("id");
					//�������� ������ ����� ���������
					NodeList orderExtents = XmlHelper.selectNodeList(subOrder, "MainOrder/ListOfOrderExtent/OrderExtent");
					for (int j = 0; j < orderExtents.getLength(); j++)
					{
						Element orderExtent = (Element) orderExtents.item(j);
						Element extent = XmlHelper.selectSingleNode(orderExtent, "Extent");
						String extentId = extent.getAttribute("id");
						String val = orderExtent.getAttribute("val");
						//externalId ��������� ��� ��, ��� � ��� �������� ��� ����� ��������
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

			default : throw new GateException("������������ ��� ������� ��� ���� payPeriod.");
		}
	}

	protected String getOrderExtentValue(Field field, String branch)
	{
		if (field.getName().equals("���"))
		{
			return branch + "/" + "00888";
		}
		else if (field.getName().equals("���_�"))
		{
			return "����";
		}
		else
		{
			return (String) field.getValue();
		}
	}

	/**
	 * ����������, �������� �� ������ ������� �� �������������� �����
	 * ���� ������� ��������� ����� ������ SS
	 * @param extendedFields �������������� ����
	 * @return ������� �� ���������
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
	 * ������� ���� "�����" ��� ������� ������
	 * @return ���� "�����"
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
	 * �������� �������� ������
	 * @param name ExternalId ������
	 * @param value ��������
	 * @return �������������� field
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
	 * true - ������������� ����� ������������� �������������� ��� �������������. ������������� ��� ���������
	 * false - ������������� ����� ������������� �������������� ��� ���������. ������������� ��� �������������
	 * ���� �������� �� ������, �� ���� ������������� �� �����������.
	 * @return �������� �� ������������� �������������.
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
