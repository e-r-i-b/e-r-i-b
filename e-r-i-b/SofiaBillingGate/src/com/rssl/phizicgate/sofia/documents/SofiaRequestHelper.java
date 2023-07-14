package com.rssl.phizicgate.sofia.documents;

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
import com.rssl.phizicgate.sofia.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author gladishev
 * @ created 14.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class SofiaRequestHelper
{
	private static final String CODE_RECIPIENT_TAG = "CodeRecipient";
	private static final String CODE_SERVICE_TAG = "CodeService";
	private static final String TOTAL_SUM_TAG = "TotalSum";
	private static final String ATTRIBUTES_TAG = "Attributes";
	private static final String ATTRIBUTE_TAG = "Attribute";
	private static final String PAYER_OFFICE_TAG = "PayerCodeOffice";
	private static final String PAYER_ACCOUNT_TAG = "PayerAccount";
	private static final String CODE_TAG = "NameBS";
	private static final String VALUE_TAG = "Value";
	private static final String ERROR_CODE_TAG = "ErrorCode";
	private static final String ERROR_DESCRIPTION_TAG = "ErrorDescription";
	private static final String PAYMENT_ID_TAG = "PaymentID";
	private static final String VALIDATOR_TYPE = "regexp";

	private WebBankServiceFacade serviceFacade;

	public SofiaRequestHelper(GateFactory factory)
	{
		serviceFacade = factory.service(WebBankServiceFacade.class);
	}

	/**
	 * Отправляет запрос проверки платежа в БС
	 * @param payment платеж
	 * @return ответ из БС София
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public final Document sendPreparePaymentRequest(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		try
		{
			GateMessage message = createPaymentMessage(payment, Constants.PREPARE_REQUEST, payment.getExtendedFields());
			Document response = serviceFacade.sendOnlineMessage(message, null);
			if (!validateResponse(payment, response))
				throw new GateLogicException(Constants.BILLING_INTERACTION_ERROR);

			return response;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	public final Document sendDebtAndValuesRequest(String codeRecipient, String codeService, List<Field> extendedFields) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(Constants.PREPARE_REQUEST);
		message.addParameter(CODE_RECIPIENT_TAG, codeRecipient);
		message.addParameter(CODE_SERVICE_TAG, codeService);

		Element docElement = message.getDocument().getDocumentElement();
		Element attributes = XmlHelper.appendSimpleElement(docElement, ATTRIBUTES_TAG);
		for (Field field : extendedFields)
		{
			//не отправляем в БС наши поля
			if (isOurField(field))
				continue;

			Element attribute = XmlHelper.appendSimpleElement(attributes, ATTRIBUTE_TAG);
			XmlHelper.appendSimpleElement(attribute, CODE_TAG, field.getExternalId());
			XmlHelper.appendSimpleElement(attribute, VALUE_TAG, StringHelper.getEmptyIfNull(field.getValue()));
		}
		return serviceFacade.sendOnlineMessage(message, null);
	}

	public final Document sendPreparePaymentRequest(final Document document) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(Constants.PREPARE_REQUEST);
		Element messageRoot = message.getDocument().getDocumentElement();
		Element docRoot = document.getDocumentElement();
		String docCodeRecipient = XmlHelper.getSimpleElementValue(docRoot, CODE_RECIPIENT_TAG);
		XmlHelper.appendSimpleElement(messageRoot, CODE_RECIPIENT_TAG, docCodeRecipient);
		String docCodeService = XmlHelper.getSimpleElementValue(docRoot, CODE_SERVICE_TAG);
		XmlHelper.appendSimpleElement(messageRoot, CODE_SERVICE_TAG, docCodeService);
		XmlHelper.appendSimpleElement(messageRoot, TOTAL_SUM_TAG, XmlHelper.getSimpleElementValue(docRoot, TOTAL_SUM_TAG));
		try
		{
			NodeList elements = XmlHelper.selectNodeList(docRoot, ATTRIBUTES_TAG);
			Element attributes = XmlHelper.appendSimpleElement(messageRoot, ATTRIBUTES_TAG);
			for (int i = 0; i < elements.getLength(); i++)
			{
				Element element = (Element) elements.item(i);

				Element attribute = XmlHelper.appendSimpleElement(attributes, ATTRIBUTE_TAG);
 				XmlHelper.appendSimpleElement(attribute, CODE_TAG, XmlHelper.getSimpleElementValue(element, CODE_TAG));
 				XmlHelper.appendSimpleElement(attribute, VALUE_TAG, XmlHelper.getSimpleElementValue(element, VALUE_TAG));
			}
		}
		catch (TransformerException te)
		{
			throw new GateException(te);
		}
		Document response = serviceFacade.sendOnlineMessage(message, null);

		Element responseRoot = response.getDocumentElement();
		String responseCodeRecipient = XmlHelper.getSimpleElementValue(responseRoot, CODE_RECIPIENT_TAG);
		if (StringHelper.isEmpty(responseCodeRecipient) || !responseCodeRecipient.equals(docCodeRecipient))
			throw new GateLogicException(Constants.BILLING_INTERACTION_ERROR);
		String responseCodeService = XmlHelper.getSimpleElementValue(responseRoot, CODE_SERVICE_TAG);
		if (StringHelper.isEmpty(responseCodeService) || !responseCodeService.equals(docCodeService))
			throw new GateLogicException(Constants.BILLING_INTERACTION_ERROR);

		return response;
	}

	/**
	 * Отправляет запрос исполнения платежа в БС
	 * @param payment платеж
	 * @return ответ из БС София
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Document sendExecutePaymentRequest(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		try
		{
			GateMessage message = createPaymentMessage(payment, Constants.EXECUTE_REQUEST, payment.getExtendedFields());
			message.addParameter(PAYER_OFFICE_TAG,  payment.getOffice().getSynchKey().toString().trim());
			message.addParameter(PAYER_ACCOUNT_TAG, payment.getChargeOffAccount());

			Document response = serviceFacade.sendOnlineMessage(message, null);
			if (!validateExecuteResponse(payment, response))
				throw new GateException();

			if (!"0".equals(XmlHelper.getSimpleElementValue(response.getDocumentElement(), ERROR_CODE_TAG)))
			{
				String errorText = XmlHelper.getSimpleElementValue(response.getDocumentElement(), ERROR_DESCRIPTION_TAG);
				throw new GateException(errorText);
			}

			return response;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Удаление платежа из БС "София ВМС"
	 * @param payment - платеж
	 * @return ответ из БС. тег документа "ErrorCode" - результат удаления платежа
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Document sendRevokePaymentRequest(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		GateMessage message = serviceFacade.createRequest(Constants.REVOKE_REQUEST);
		message.addParameter(PAYMENT_ID_TAG, payment.getIdFromPaymentSystem());
		message.addParameter(TOTAL_SUM_TAG, payment.getDestinationAmount());
		Document response = serviceFacade.sendOnlineMessage(message, null);

		if (!"0".equals(XmlHelper.getSimpleElementValue(response.getDocumentElement(), ERROR_CODE_TAG)))
		{
			String errorText = XmlHelper.getSimpleElementValue(response.getDocumentElement(), ERROR_DESCRIPTION_TAG);
			throw new GateException(errorText);
		}

		return response;
	}

	private GateMessage createPaymentMessage(AccountPaymentSystemPayment payment, String requestName, List<Field> extendedFields) throws GateException
	{
		GateMessage message = serviceFacade.createRequest(requestName);
		message.addParameter(CODE_RECIPIENT_TAG, payment.getReceiverPointCode());
		message.addParameter(CODE_SERVICE_TAG, payment.getService().getCode());
		if (payment.getDestinationAmount() != null)
			message.addParameter(TOTAL_SUM_TAG, payment.getDestinationAmount().getDecimal().toString());
		
		Element docElement = message.getDocument().getDocumentElement();
		Element attributes = XmlHelper.appendSimpleElement(docElement, ATTRIBUTES_TAG);
		for (Field field: extendedFields)
		{
			//не отправляем в БС наши поля
			if (isOurField(field))
				continue;

			Element attribute = XmlHelper.appendSimpleElement(attributes, ATTRIBUTE_TAG);
			XmlHelper.appendSimpleElement(attribute, CODE_TAG, field.getExternalId());
			XmlHelper.appendSimpleElement(attribute, VALUE_TAG, StringHelper.getEmptyIfNull(field.getValue()));
		}

		return message;
	}

	/**
	 * Запрос дополнительных полей поставщика услуг
	 * @param recipient поставщик услуг
	 * @return доп. поля
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Document getRecipientFieldsDocument(Recipient recipient) throws GateException, GateLogicException
	{
		return getRecipientFieldsDocument(recipient.getSynchKey().toString(), recipient.getService().getCode(), recipient.getName());
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
	public Document getRecipientFieldsDocument(String recipientCode, String serviceCode, String recipientName) throws GateException, GateLogicException
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
		String codeRecipient = XmlHelper.getSimpleElementValue(docElement, CODE_RECIPIENT_TAG);
		if (StringHelper.isEmpty(codeRecipient) || !recipientCode.equals(codeRecipient))
			return false;
		String codeService = XmlHelper.getSimpleElementValue(docElement, CODE_SERVICE_TAG);
		if (StringHelper.isEmpty(codeService) || !serviceCode.equals(codeService))
			return false;

		return true;
	}

	private boolean validateExecuteResponse(AccountPaymentSystemPayment payment, Document response)
	{
		Element docElement = response.getDocumentElement();
		String totalSum = XmlHelper.getSimpleElementValue(docElement, TOTAL_SUM_TAG);
		if (StringHelper.isEmpty(totalSum))
			return false;
		Money paymentTotalSum = payment.getDestinationAmount();
		Money responseTotalSum = new Money(new BigDecimal(totalSum), paymentTotalSum.getCurrency());
		if (!paymentTotalSum.equals(responseTotalSum))
			return false;
		return validateResponse(payment, response);
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
	 * Получение ключевых полей
	 * @param document ответ из БС
	 * @param key      признак ключевое/не ключевое false - возвращаем все поля
	 * @return список ключевых полей
	 */
	public List<Field> getExtendedFields(Document document, boolean key) throws GateException
	{
		List<Field> extendedFields = new ArrayList<Field>();
		try
		{
			NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), "//" + ATTRIBUTE_TAG);
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

	private boolean getTagBoolean(Element element, String tag) throws TransformerException
	{
		Element node = XmlHelper.selectSingleNode(element, tag);
		if(node == null)
			return false;
		else
			return node.getTextContent().trim().equals("1");
	}

	/**
	 * Получить поле
	 * @param element элемент
	 * @return поле
	 * @throws GateException
	 */
	public Field parseField(Element element) throws GateException, TransformerException
	{
		CommonField field = new CommonField();
		field.setExternalId(XmlHelper.getSimpleElementValue(element, "NameBS"));
		field.setName(XmlHelper.getSimpleElementValue(element, "NameVisible"));
		field.setDescription(XmlHelper.getSimpleElementValue(element, "Comment"));
		field.setHint(XmlHelper.getSimpleElementValue(element, "Description"));
		Element attributeLength = null;
		try
		{
			field.setType(FieldDataType.fromValue(XmlHelper.getSimpleElementValue(element, "Type")));
			attributeLength = XmlHelper.selectSingleNode(element, "AttributeLength");
		}
		catch (IllegalArgumentException e)
		{
			throw new GateException(e);
		}
		catch (TransformerException te)
		{
			throw new GateException(te);
		}
		String length = XmlHelper.getSimpleElementValue(attributeLength, "Length");
		if (length == null || length.equals(""))
		{
			String minLength = XmlHelper.getSimpleElementValue(attributeLength, "MinLength");
			String maxLength = XmlHelper.getSimpleElementValue(attributeLength, "MaxLength");
			field.setMinLength(Long.decode(minLength));
			field.setMaxLength(Long.decode(maxLength));
		}
		else
		{
			Long len = Long.decode(length);
			field.setMinLength(len);
			field.setMaxLength(len);
		}

		field.setRequired("1".equals(XmlHelper.getSimpleElementValue(element, "IsRequired")));
		field.setEditable("1".equals(XmlHelper.getSimpleElementValue(element, "IsEditable")));
		field.setVisible("1".equals(XmlHelper.getSimpleElementValue(element, "IsVisible")));
		//выводим в чеке только видимые поля
		field.setRequiredForBill(field.isVisible());
		field.setMainSum("1".equals(XmlHelper.getSimpleElementValue(element, "IsSum")));

		boolean key = "1".equals(XmlHelper.getSimpleElementValue(element, "IsKey"));
		field.setKey(key);
		//тк по интеграции признака «Атрибут является важным для подтверждения» нет, то устанавливаем по признаку isKey
		field.setRequiredForConformation(key);
		field.setSaveInTemplate(key);

		field.setDefaultValue(XmlHelper.getSimpleElementValue(element, "DefaultValue"));
		String values = XmlHelper.getSimpleElementValue(element, "Values");
		if (values != null && !values.equals(""))
		{
			List<ListValue> listValues = new ArrayList<ListValue>();

			for (String value: tokenaze(values, "|"))
				listValues.add(new ListValue(value, value));

			field.setValues(listValues);
		}

		NodeList validatorElements = null;
		try
		{
			validatorElements = XmlHelper.selectNodeList(element, "Validators/Validator");
		}
		catch (TransformerException ex)
		{
			throw new GateException(ex);
		}

		List<FieldValidationRule> rules = new ArrayList<FieldValidationRule>();
		for (int j = 0; j < validatorElements.getLength(); j++)
		{
			Element validator = (Element) validatorElements.item(j);
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
