package com.rssl.phizicgate.enisey.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.enisey.RecipientImpl;
import com.rssl.phizicgate.enisey.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author mihaylov
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class EniseyRequestHelper
{
	private static final String VALIDATOR_TYPE = "regexp";

	private GateFactory factory;
	private WebBankServiceFacade serviceFacade;

	public EniseyRequestHelper(GateFactory factory)
	{
		this.factory = factory;
		serviceFacade = factory.service(WebBankServiceFacade.class);
	}

	/**
	 * Сформировать и послать запрос prepareBillingPayment_q.
	 * @param payment платеж
	 * @return ответ БС на запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public final Document sendPreparePaymentRequest(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		List<Field> extendedFields;
		try
		{
			extendedFields = payment.getExtendedFields();
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		RecipientImpl recipient = new RecipientImpl();
		recipient.setName(payment.getReceiverName());
		recipient.setSynchKey(payment.getReceiverPointCode());
		GateMessage message = createPrepareOrExecuteRequest(payment, Constants.PREPARE_REQUEST, getVisibleFields(extendedFields));
		Document response = serviceFacade.sendOnlineMessage(message, null);

		if (!validateResponse(payment, response))
			throw new GateLogicException(Constants.BILLING_INTERACTION_ERROR);

		return response;
	}

	/**
	 * Сформировать и послать запрос prepareBillingPayment_q.
	 * @param document - документ из которого строится новый запрос
	 * @return ответ БС
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public final Document sendPreparePaymentRequest(Document document) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(Constants.PREPARE_REQUEST);
		Element messageRoot = message.getDocument().getDocumentElement();
		Element docRoot = document.getDocumentElement();
		copyValue(docRoot, messageRoot, Constants.CODE_RECIPIENT_TAG);
		copyValue(docRoot, messageRoot, Constants.CODE_SERVICE_TAG);
		copyValue(docRoot, messageRoot, Constants.TOTAL_SUM_TAG);
		try
		{
			NodeList elements = XmlHelper.selectNodeList(docRoot, Constants.ATTRIBUTES_TAG);
			Element attributes = XmlHelper.appendSimpleElement(messageRoot, Constants.ATTRIBUTES_TAG);
			for (int i = 0; i < elements.getLength(); i++)
			{
				Element element = (Element) elements.item(i);
				//Если значения пустые, то не передаем полностью тег, иначе приведет к проблемам на БС
				if (!StringHelper.isEmpty(XmlHelper.getSimpleElementValue(element, Constants.VALUE_TAG)))
				{
					Element attribute = XmlHelper.appendSimpleElement(attributes, Constants.ATTRIBUTE_TAG);
					copyValue(element, attribute, Constants.CODE_TAG);
					copyValue(element, attribute, Constants.VALUE_TAG);
				}
			}
		}
		catch (TransformerException te)
		{
			throw new GateException(te);
		}
		Document response = serviceFacade.sendOnlineMessage(message, null);

		Element responseRoot = response.getDocumentElement();
		String responseCodeRecipient = XmlHelper.getSimpleElementValue(responseRoot, Constants.CODE_RECIPIENT_TAG);
		String docCodeRecipient = XmlHelper.getSimpleElementValue(docRoot, Constants.CODE_RECIPIENT_TAG);
		if (StringHelper.isEmpty(responseCodeRecipient) || !responseCodeRecipient.equals(docCodeRecipient))
			throw new GateLogicException(Constants.BILLING_INTERACTION_ERROR);
		String responseCodeService = XmlHelper.getSimpleElementValue(responseRoot, Constants.CODE_SERVICE_TAG);
		String docCodeService = XmlHelper.getSimpleElementValue(docRoot, Constants.CODE_SERVICE_TAG);
		if (StringHelper.isEmpty(responseCodeService) || !responseCodeService.equals(docCodeService))
			throw new GateLogicException(Constants.BILLING_INTERACTION_ERROR);

		return response;
	}

	private GateMessage createPrepareOrExecuteRequest(AccountPaymentSystemPayment payment, String requestName, List<Field> fields) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(requestName);
		if (payment.getDestinationAmount() != null)
			message.addParameter(Constants.TOTAL_SUM_TAG, payment.getDestinationAmount().getDecimal().toString());
		message.addParameter(Constants.CODE_RECIPIENT_TAG, payment.getReceiverPointCode());
		message.addParameter(Constants.CODE_SERVICE_TAG, payment.getService().getCode());

		Element docElement = message.getDocument().getDocumentElement();
		Element attributes = XmlHelper.appendSimpleElement(docElement, Constants.ATTRIBUTES_TAG);
		for (Field field : fields)
		{
			//не отправляем в БС наши поля
			if (isOurField(field))
				continue;

			String value = (String) field.getValue();
			//Если значения пустые, то не передаем тег, иначе приведет к проблемам на БС
			if (!StringHelper.isEmpty(value))
			{
				Element attribute = XmlHelper.appendSimpleElement(attributes, Constants.ATTRIBUTE_TAG);
				XmlHelper.appendSimpleElement(attribute, Constants.CODE_TAG, field.getExternalId());
				XmlHelper.appendSimpleElement(attribute, Constants.VALUE_TAG, StringHelper.getEmptyIfNull(value));
			}
		}

		return message;
	}

	private void copyValue(Element from, Element to, String tagName)
	{
		XmlHelper.appendSimpleElement(to, tagName, XmlHelper.getSimpleElementValue(from, tagName));
	}

	/**
	 * Сформировать и послать запрос executeBillingPayment_q
	 * @param payment платеж
	 * @return ответ БС на запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public final Document sendExecutePaymentRequest(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		List<Field> extendedFields;
		try
		{
			extendedFields = payment.getExtendedFields();
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}

		GateMessage message = createPrepareOrExecuteRequest(payment, Constants.EXECUTE_REQUEST, extendedFields);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		if (!validateExecuteResponse(payment, response))
			throw new GateException();

		if (StringHelper.isEmpty(XmlHelper.getSimpleElementValue(response.getDocumentElement(), Constants.PAYMENT_ID_TAG)))
		{
			throw new GateException("Ошибка при проведении платежа в биллинге");
		}

		return response;
	}

	/**
	 * Сформировать и послать запрос revokeBillingPayment_q
	 * @param payment платеж
	 * @return ответ БС на запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public final Document sendRevokeBillingPayment(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(Constants.REVOKE_REQUEST);
		message.addParameter(Constants.PAYMENT_ID_TAG, payment.getIdFromPaymentSystem());
		message.addParameter(Constants.TOTAL_SUM_TAG, payment.getDestinationAmount().add(payment.getCommission()));
		return serviceFacade.sendOnlineMessage(message, null);
	}

	public Document sendDebtAndValuesRequest(String codeRecipient, String codeService, List<Field> extendedFields) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(Constants.PREPARE_REQUEST);
		message.addParameter(Constants.CODE_RECIPIENT_TAG, codeRecipient);
		message.addParameter(Constants.CODE_SERVICE_TAG, codeService);

		Element docElement = message.getDocument().getDocumentElement();
		Element attributes = XmlHelper.appendSimpleElement(docElement, Constants.ATTRIBUTES_TAG);
		for (Field field: extendedFields)
		{
			//не отправляем в БС наши поля
			if (isOurField(field))
				continue;

			String value = (String) field.getValue();
			//Если значения пустые, то не передаем тег, иначе приведет к проблемам на БС
			if (!StringHelper.isEmpty(value))
			{
				Element attribute = XmlHelper.appendSimpleElement(attributes, Constants.ATTRIBUTE_TAG);
				XmlHelper.appendSimpleElement(attribute, Constants.CODE_TAG, field.getExternalId());
				XmlHelper.appendSimpleElement(attribute, Constants.VALUE_TAG, StringHelper.getEmptyIfNull(value));
			}
		}

		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * Запрос дополнительных полей поставщика услуг
	 * @param recipient поставщик услуг
	 * @return доп. поля
	 * @throws GateException
	 * @throws GateLogicException

	 */
	public Document getRecipientExtendedFieldsDocument(Recipient recipient) throws GateException, GateLogicException
	{
		return getRecipientExtendedFieldsDocument(recipient.getSynchKey().toString(), recipient.getService().getCode(), recipient.getName());
	}

	/**
	 * Запрос дополнительных полей поставщика услуг
	 * @param recipientCode код поставщика услуг
	 * @param serviceCode   код услуги услуг
	 * @param recipientName название поставщика услуг
	 * @return доп. поля
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Document getRecipientExtendedFieldsDocument(String recipientCode, String serviceCode, String recipientName) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(Constants.ATTRIBUTES_REQUEST);
		message.addParameter("CodeRecipient", recipientCode);
		message.addParameter("CodeService", serviceCode);
		message.addParameter("Name", recipientName);

		Document response = serviceFacade.sendOnlineMessage(message, null);

		if (!validateResponse(recipientCode, serviceCode, response))
			throw new GateLogicException(Constants.BILLING_INTERACTION_ERROR);

		return response;
	}

	/**
	 * Проверка на соответствие значений в запросе и ответе
	 * кода получателя и кода услуги
	 * @param payment - платеж
	 * @param response - ответ из БС
	 * @return true - получен корректный ответ
	 */
	public boolean validateResponse(AccountPaymentSystemPayment payment, Document response)
	{
		return validateResponse(payment.getReceiverPointCode(), payment.getService().getCode(), response);
	}

	/**
	 * Проверка на соответствие значений в запросе и ответе
	 * кода получателя и кода услуги
	 * @param recipient поставщик услуг
	 * @param response  ответ из БС
	 * @return true - получен корректный ответ
	 */
	public boolean validateResponse(Recipient recipient, Document response)
	{
		return validateResponse(recipient.getSynchKey().toString(), recipient.getService().getCode(), response);
	}

	private boolean validateResponse(String recipientCode, String serviceCode, Document response)
	{
		Element docElement = response.getDocumentElement();
		String codeRecipient = XmlHelper.getSimpleElementValue(docElement, Constants.CODE_RECIPIENT_TAG);
		if (StringHelper.isEmpty(codeRecipient) || !recipientCode.equals(codeRecipient))
			return false;
		String codeService = XmlHelper.getSimpleElementValue(docElement, Constants.CODE_SERVICE_TAG);
		if (StringHelper.isEmpty(codeService) || !serviceCode.equals(codeService))
			return false;

		return true;
	}

	private boolean validateExecuteResponse(AccountPaymentSystemPayment payment, Document response)
	{
		Element docElement = response.getDocumentElement();
		String totalSum = XmlHelper.getSimpleElementValue(docElement, Constants.TOTAL_SUM_TAG);
		if (StringHelper.isEmpty(totalSum))
			return false;
		Money paymentTotalSum = payment.getDestinationAmount();
		Money responseTotalSum = new Money(new BigDecimal(totalSum), paymentTotalSum.getCurrency());
		if (!paymentTotalSum.equals(responseTotalSum))
			return false;
		return validateResponse(payment, response);
	}

	/**
	 * Выбирает из fields только нескрытые поля
	 *
	 *@param fields - доп.поля платежа
	 * @return редактируемые поля
	 */
	private List<Field> getVisibleFields(List<Field> fields)
	{
		List<Field> result = new ArrayList<Field>();

		for (Field field : fields)
		{
			if (field.isVisible())
				result.add(field);
		}
		return result;
	}

	/**
	 * Возвращает список (всех/ключевых) полей
	 * @param document ответ из БС
	 * @param key      true - только ключевые поля
	 * @return доп. поля
	 */
	public List<Field> getExtendedFields(Document document, boolean key) throws GateException, GateLogicException
	{
		List<Field> extendedFields = new ArrayList<Field>();
		try
		{
			NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), "//" + Constants.ATTRIBUTE_TAG);
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);
				if (key && !getTagBoolean(node,"IsKey"))
					continue;

				extendedFields.add(parseField(node));
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}

		return extendedFields;
	}

	private Field parseField(Element node) throws TransformerException, GateException
	{
		CommonField field = new CommonField();
		String dataType = getTagString(node,"Type");
		if(FieldDataType.list.equals(FieldDataType.fromValue(dataType)))
		{
			List<ListValue> listValues = new ArrayList<ListValue>();

			for (String value: tokenaze(getTagString(node,"Values"),"|"))
				listValues.add(new ListValue(value, value));

			field.setValues(listValues);
			field.setType(FieldDataType.list);
		}
		else
		{
			field.setType(FieldDataType.fromValue(dataType));
		}

		field.setExternalId(getTagString(node,"NameBS"));
		field.setName(getTagString(node,"NameVisible"));

		field.setDescription(getTagString(node,"Comment"));
		field.setHint(getTagString(node,"Description"));

		field.setRequired(getTagBoolean(node,"IsRequired"));
		field.setEditable(getTagBoolean(node,"IsEditable"));
		field.setVisible(getTagBoolean(node,"IsVisible"));
		// в чеке отображаем только видимые поля
		field.setRequiredForBill(field.isVisible());
		field.setMainSum(getTagBoolean(node,"IsSum"));

		boolean key = getTagBoolean(node,"IsKey");
		field.setKey(key);
		//тк по интеграции признака «Атрибут является важным для подтверждения» нет, то устанавливаем по признаку isKey
		field.setRequiredForConformation(key);
		field.setSaveInTemplate(key);

		field.setDefaultValue(getTagString(node,"DefaultValue"));

		addLengthAttribute(field,node);
		addValidators(field,node);
	    return field;
	}

	private List<String> tokenaze(String str, String separtor)
	{
		StringTokenizer tokenizer = new StringTokenizer(str, separtor);
		List<String> values = new ArrayList<String>();
		while (tokenizer.hasMoreTokens())
		{
			values.add(tokenizer.nextToken());
		}
		return values;
	}

	private void addLengthAttribute(CommonField field, Element node) throws TransformerException
	{
		Element attributeNode = XmlHelper.selectSingleNode(node, "AttributeLength");
		if(getTagLong(attributeNode,"Length") == null)
		{
			field.setMinLength(getTagLong(attributeNode,"MinLength"));
			field.setMaxLength(getTagLong(attributeNode,"MaxLength"));
		}
		else
		{
			field.setMinLength(getTagLong(attributeNode,"Length"));
			field.setMaxLength(getTagLong(attributeNode,"Length"));
		}
	}

	private void addValidators(CommonField field, Element node) throws TransformerException, GateException
	{
		NodeList nodeList = XmlHelper.selectNodeList(node, "Validators/Validator");
		List<FieldValidationRule> rules = new ArrayList<FieldValidationRule>();
		for (int j = 0; j < nodeList.getLength(); j++)
		{
			Element validator = (Element) nodeList.item(j);
			FieldValidationRuleImpl rule = new FieldValidationRuleImpl();
			String type = XmlHelper.getSimpleElementValue(validator, "Type");
			if (!VALIDATOR_TYPE.equalsIgnoreCase(type))
				throw new GateException("Неизвестный тип поля");
			rule.setFieldValidationRuleType(FieldValidationRuleType.REGEXP);
			rule.setErrorMessage(XmlHelper.getSimpleElementValue(validator, "Message"));
			String parameter = XmlHelper.getSimpleElementValue(validator, "Parameter");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(VALIDATOR_TYPE, parameter);
			rule.setParameters(parameters);

			rules.add(rule);
		}
		field.setFieldValidationRules(rules);
	}

	private boolean getTagBoolean(Element element, String tag) throws TransformerException
	{
		Element node = XmlHelper.selectSingleNode(element, tag);
		if(node == null)
			return false;
		else
			return node.getTextContent().trim().equals("1");
	}

	private String getTagString(Element element, String tag) throws TransformerException
	{
		Element node = XmlHelper.selectSingleNode(element, tag);
		if(node == null)
			return null;
		else
			return node.getTextContent().trim();
	}

	private Long getTagLong(Element element, String tag) throws TransformerException
	{
		Element node = XmlHelper.selectSingleNode(element, tag);
		if(node == null)
			return null;
		else
			return new Long(node.getTextContent().trim());
	}

	/**
	 * Создать поле "Сумма платежа"
	 * @return поле "Сумма платежа"
	 */
	public static Field createAmountField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.money);
		field.setExternalId(Constants.AMOUNT_FIELD_NAME);
		field.setName("Сумма платежа");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(true);

		return field;
	}

	/**
	 * Создать поле "Задолженность"
	 * @return поле "Задолженность"
	 */
	public static Field createDebtField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.money);
		field.setExternalId(Constants.DEBT_FIELD_NAME);
		field.setName("Задолженность");
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(true);
		field.setRequiredForBill(true);

		return field;
	}

	/**
	 * @param field поле
	 * @return true - поле создано нами (не в биллинге)
	 */
	public static boolean isOurField(Field field)
	{
		String externalId = field.getExternalId();
		return Constants.AMOUNT_FIELD_NAME.equals(externalId) || Constants.DEBT_FIELD_NAME.equals(externalId)
				|| BillingPaymentHelper.REQUEST_BILLING_ATTRIBUTES_FIELD_NAME.equals(externalId);
	}
}
