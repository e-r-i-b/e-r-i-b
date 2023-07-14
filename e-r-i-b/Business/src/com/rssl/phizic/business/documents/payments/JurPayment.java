package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.phizgate.common.payments.ExtendedFieldsHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.payments.longoffer.AccountPaymentSystemPaymentLongOfer;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EmployeeCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author Kidyaev
 * @ created 19.10.2006
 * @ $Author$
 * @ $Revision$
 */
public class JurPayment extends RurPayment implements AccountPaymentSystemPayment, CardPaymentSystemPayment, CardPaymentSystemPaymentLongOffer, AccountPaymentSystemPaymentLongOfer, EmployeeCardPaymentSystemPaymentLongOffer
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	public static final String AUTOSUB_EXECUTE_NOW = "is-auto-sub-execution-now";
	protected static final String AUTOSUB_ALWAYS_AMOUNT = "auto-sub-always-amount";
	public static final String AUTOSUB_NEXT_PAY_DATE = "auto-sub-next-pay-date";

	protected static final String AUTOSUB_INVOICE_MAX_DECIMAL = "auto-sub-invoice-max-decimal";
	protected static final String AUTOSUB_INVOICE_MAX_CURRENCY = "auto-sub-invoice-max-currency";

	public static final String AUTOSUB_FRIENDLY_NAME = "auto-sub-friendy-name";
	public static final String AUTOSUB_CHANNEL_TYPE = "auto-sub-channel-type";
	private static final String AUTOSUB_WITH_COMMISION = "auto-sub-with-commision";
	public static final String CHARGEOFF_CONNECT_TO_MOBILBANK = "chargeoff-connect-to-mobilbank";

	public static final String RECEIVER_INTERNAL_ID = PaymentFieldKeys.PROVIDER_KEY;
	public static final String PROVIDER_EXTERNAL_ID = PaymentFieldKeys.PROVIDER_EXTERNAL_KEY;
	public static final String PROVIDER_GROUP_SERVICE = "provider-group-service";

	public static final String RECEIVER_DESCRIPTION_ATTRIBUTE_NAME = "receiver-description";
	public static final String PHONE_NUMBER_ATTRIBUTE_NAME = "receiver-phone-number";

	public static final String BILLING_CLIENT_ID_ATTRIBUTE_NAME = "billing-client-id";
	public static final String IS_CHANGE_KEY_FIELDS_ATTRIBUTE_NAME = "is-change-key-fields";

	public static final String CODE_SERVICE_ATTRIBUTE_NAME = PaymentFieldKeys.RECEIVER_SERVICE_CODE;
	public static final String NAME_SERVICE_ATTRIBUTE_NAME = PaymentFieldKeys.RECEIVER_SERVICE_NAME;

	private static final String RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME = "receiver-office-region-code";
	private static final String RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME = "receiver-office-branch-code";
	private static final String RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME = "receiver-office-office-code";

	public static final String TRANSIT_RECEIVER_ACCOUNT_ATTRIBUTE_NAME = "transit-receiver-account";
	public static final String TRANSIT_RECEIVER_NAME_ATTRIBUTE_NAME = "transit-receiver-name";
	public static final String TRANSIT_RECEIVER_BIC_ATTRIBUTE_NAME = "transit-receiver-bank-bic";
	public static final String TRANSIT_RECEIVER_CORRACCOUNT_ATTRIBUTE_NAME = "transit-receiver-bank-corraccount";

	public static final String AUTOPAY_NUMBER_ATTRIBUTE_NAME = "autoPay-number";

	public static final String SALES_CHECK_ATTRIBUTE_NAME = "SALES_CHECK";
	public static final String BANK_DETAILS_ATTRIBUTE_NAME = "bank-details";
	public static final String BILLING_CODE_ATTRIBUTE_NAME = "provider-billing-code";

	public static final String NAME_ON_BILL_ATTRIBUTE_NAME = "nameOnBill";

	public static final String DEPO_LINK_ID_ATTRIBUTE_NAME = "depo-link-id";
	public static final String BILLING_DOCUMENT_NUMBER = "billing-document-number";

    private static final String TICKETS_INFO_ATTRIBUTE_NAME = "tickets-info";
	private static final String RESERVATION_ID_ATTRIBUTE_NAME = "airlineReservId";
	private static final String FROM_PANEL_ID = "from-panel-id";
	private static final String RECEIVER_FACILITATOR = "receiverFacilitator";

	private static final String INVOICE_SUBSCRIPTION_ID = "invoice-sub-id";
	private static final String OPERATION_DESCRIPTION = "operation-description";

	private static final String IS_EINVOICING_ATTRIBUTE_NAME = "is-einvoicing";
	public static final String AGREEMENT_FIELD = "Agreement";

	private AlwaysAutoPayScheme alwaysAutoPayScheme;
	private InvoiceAutoPayScheme invoiceAutoPayScheme;
	private ThresholdAutoPayScheme thresholdAutoPayScheme;

	private boolean isSelfMobileNumberPayment = false; //является ли платеж платежом по "своему" номеру телефона в счет поставщика мобильной связи.
	private String orderUUID;
	private boolean uuidInitialized = false;

	@Override
	public Class<? extends GateDocument> getType()
	{
		ResourceType resourceType = getChargeOffResourceType();
		if (resourceType == ResourceType.NULL)
		{
			return null;
		}
		if (resourceType == ResourceType.ACCOUNT)
		{
			return isLongOffer() ? AccountPaymentSystemPaymentLongOfer.class : AccountPaymentSystemPayment.class;
		}
		else if (resourceType == ResourceType.CARD)
		{
			if(isLongOffer())
			{
				return getCreatedEmployeeLoginId() != null ?
						EmployeeCardPaymentSystemPaymentLongOffer.class : CardPaymentSystemPaymentLongOffer.class;
			}
			else
			{
				return CardPaymentSystemPayment.class;
			}
		}
		throw new IllegalStateException("Неверный тип источника списания " + resourceType);
	}

	@Override
	public FormType getFormType()
	{
		return getReceiverInternalId() == null  ? FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER : FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER;
	}

	@Override
	public void initialize(TemplateDocument template) throws DocumentException
	{
		super.initialize(template);

		setExtendedFields(template.getExtendedFields());
		setReceiverOfficeCode(template.getReceiverOfficeCode());
		setService(template.getService());
	}

	@Override
	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		setOperationUID(generateOperationUID());
	}

	private String generateOperationUID()
	{
		return "AA" + new RandomGUID().getStringValue().substring(2);
	}

	@Override
	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();

		appendNullSaveString(root, BILLING_DOCUMENT_NUMBER, getBillingDocumentNumber());
		appendNullSaveString(root, PROVIDER_EXTERNAL_ID, getReceiverPointCode());
		appendNullSaveString(root, RECEIVER_INTERNAL_ID, StringHelper.getEmptyIfNull(getReceiverInternalId()));

		Money amount = getDestinationAmount();
		if (amount == null)
		{
			return document;
		}
		//отдельно обрабатываем поле с признаком главной суммы оно хранится не в хвостах, а в ChargeOffAmount
		Field mainSumField = getMainSumField();
		if (mainSumField == null)
		{
			//поле главная сумма еще не пришла(адаптер или биллинг ее не запрашивал) - значение суммы вставлять некуда.
			return document;
		}
		try
		{
			Element extraParameters = XmlHelper.selectSingleNode(root, "extra-parameters");
			Element mainSum = XmlHelper.selectSingleNode(extraParameters, "./parameter[@name = '" + mainSumField.getExternalId() + "']");
			// если поле уже добавлено, то меняем его значение
			if (mainSum != null)
			{
				mainSum.setAttribute("value", amount.getDecimal().toString());
			}
			else
			{
				Element elementParameter = document.createElement("parameter");
				elementParameter.setAttribute("type", MoneyType.INSTANCE.getName());
				elementParameter.setAttribute("name", mainSumField.getExternalId());
				elementParameter.setAttribute("value", amount.getDecimal().toString());
				extraParameters.appendChild(elementParameter);
			}
		}
		catch (TransformerException e)
		{
			throw new DocumentException(e);
		}
		return document;
	}

	@Override
	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);

		Element root = document.getDocumentElement();
		if (StringHelper.isEmpty(getReceiverKPP()))
		{
			setReceiverKPP(null);
		}

		// обновляем внешний идентификатор поставщика в случае если он есть
		String strReceiverpointCode = XmlHelper.getSimpleElementValue(root, PROVIDER_EXTERNAL_ID);
		if (strReceiverpointCode != null)
		{
			setReceiverPointCode(strReceiverpointCode);
		}
		// обновляем внутренний идентификатор поставщика в случае если он есть
		String strReceiverId = XmlHelper.getSimpleElementValue(root, RECEIVER_INTERNAL_ID);
		if (strReceiverId != null)
		{
			setReceiverInternalId(StringHelper.isEmpty(strReceiverId) ? null : Long.parseLong(strReceiverId));
		}

		// для автоплатежа свое поле суммы
		if(isLongOffer())
			return;

		// получаем национальную валюту
		Currency currency = getNationalCurrency();

		//отдельно обрабатываем поле с признаком главной суммы
		Field mainSumField = getMainSumField();
		if (mainSumField == null)
		{
			return;
		}

		try
		{
			// если в параметрах не присутствовало поле с суммой, значит его не надо обновлять
			if(!XmlHelper.nodeTest(String.format(EXTRA_PARAMETER_XPATH_TEMPLATE, mainSumField.getExternalId()), root))
				return;
		}
		catch (TransformerException e)
		{
			throw new DocumentException(e);
		}

		//вытаскиваем из хвостов значение.
		String mainSumValue = getNullSaveAttributeStringValue(mainSumField.getExternalId());
		//для шаблонов могут отсутсвовать сумма ли еще не пришла в расширенных полях
		if (!StringHelper.isEmpty(mainSumValue) && currency != null)
		{
			//сеттим в сумму списания.
			setDestinationAmount(new Money(new BigDecimal(mainSumValue), currency));
		}
		else
		{
			//клиент стер сумму.
			setDestinationAmount(null);
		}
		//удаляем расширенный атрибут из хвостов
		removeAttribute(mainSumField.getExternalId());
	}

	@Override
	protected void readExtendedFields(NodeList fieldValuesNodeList) throws DocumentException
	{
		List<Field> fields = getExtendedFields();
		if (CollectionUtils.isEmpty(fields))
		{
			super.readExtendedFields(fieldValuesNodeList);
			return;
		}

		Map<String, Field> keyFields = new HashMap<String, Field>();
		for (Field field : fields)
		{
			if (field.isKey())
			{
				keyFields.put(field.getExternalId(), field);
			}
		}
		if (MapUtils.isEmpty(keyFields))
		{
			super.readExtendedFields(fieldValuesNodeList);
			return;
		}

		for (int i = 0; i < fieldValuesNodeList.getLength(); i++)
		{
			Element element = (Element) fieldValuesNodeList.item(i);
			if (keyFields.get(element.getAttribute("name")) != null)
			{
				ExtendedAttribute attribute = getAttribute(element.getAttribute("name"));
				if (attribute != null)
				{
					if (!attribute.getStringValue().equals(element.getAttribute("value")))
					{
						setSomeKeyFieldChanged(true);
					}
				}
			}

			setAttributeValue(element);
		}
	}

	@Override
	protected void setDocumentNumberInternal(Document document) throws DocumentException
	{
        String docNum = null;
        //достаем из document-а documentNumber
        if(document != null)
        {
            NodeList parameters = document.getElementsByTagName("parameter");
            for(int i = 0; i < parameters.getLength(); i++)
            {
                Element element = (Element)parameters.item(i);
                if ("documentNumber".equals(element.getAttribute("name")))
                {
                    docNum = element.getAttribute("value");
                    break;
                }
            }
        }

        if (StringHelper.isNotEmpty(docNum))
            setDocumentNumber(docNum);
        else
		    super.setDocumentNumberInternal(document);
	}

	/**
	 * @return поле с признаком главной суммы(если есть)
	 */
	public Field getMainSumField() throws DocumentException
	{
		List<Field> extendedFields = getExtendedFields();
		if (extendedFields == null)
		{
			return null;
		}
		for (Field extendedField : extendedFields)
		{
			if (extendedField.isMainSum())
			{
				return extendedField;
			}
		}
		return null;
	}

	@Override
	public ResourceType getDestinationResourceType()
	{
		//всегда перевод другому лицу.
		return ResourceType.EXTERNAL_ACCOUNT;
	}

	@Override
	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		//с картой нужно сохранить дату...
		if (chargeOffResourceType == ResourceType.CARD)
		{
			try
			{
				CardLink cardLink = receiveCardLinkByCardNumber(getChargeOffCard(), messageCollector);
				//Сохраняем инфу о сроке действия
				setChargeOffCardExpireDate(cardLink.getExpireDate());
				// сохраняем инфу о типе карты списания
				setChargeOffCardDescription(cardLink.getDescription());
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
		}
	}

	/**
	 * получить запись из о поставщике из БД.
	 * @return запись о поставщике или null в случае оплаты в адрес внешнего получателя, либо поставщик не найден в базе.
	 */
	@Override
	public ServiceProviderBase getServiceProvider() throws DocumentException
	{
		Long receiverId = getReceiverInternalId();
		if (receiverId == null)
		{
			return null; //если происходит оплата в адрес внешнего получателя -> получателя в БД нет.
		}
		try
		{
			return serviceProviderService.findById(receiverId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
	/**
	 * платеж в ФНС
	 * @return true - если платеж в адрес ФНС, false - в остальных случаях.
	 */
	public boolean getFNS() throws DocumentException
	{
		return FormConstants.FNS_PAYMENT_FORM.equals(getFormName());
	}

	/**
	 * @return код биллинга, в который отправляется платеж
	 */
	@Override
	public String getBillingCode()
	{
		return getNullSaveAttributeStringValue(BILLING_CODE_ATTRIBUTE_NAME);
	}

	/**
	 * @return является ли платеж биллинговым
	 */
	public boolean isBillingPayment()
	{
		if (getType() == null)
			return false;
		return  AbstractPaymentSystemPayment.class.isAssignableFrom(getType());
	}

	/**
	 * Установить код биллинга
	 * @param billingCode код биллинга
	 */
	@Override
	public void setBillingCode(String billingCode)
	{
		setNullSaveAttributeStringValue(BILLING_CODE_ATTRIBUTE_NAME, billingCode);
	}

	/**
	 * @return имя получателя для печати в чеках
	 */
	@Override
	public String getReceiverNameForBill()
	{
		return getNullSaveAttributeStringValue(NAME_ON_BILL_ATTRIBUTE_NAME);
	}

	/**
	 * установить имя получателя для печати в чеках
	 * @param receiverNameForBill имя получателя для печати в чеках
	 */
	@Override
	public void setReceiverNameForBill(String receiverNameForBill)
	{
		setNullSaveAttributeStringValue(NAME_ON_BILL_ATTRIBUTE_NAME, receiverNameForBill);
	}

	/**
	 * @return  идентификатор линка счета депо
	 */
	@Override
	public String getDepoLinkId()
	{
		return getNullSaveAttributeStringValue(DEPO_LINK_ID_ATTRIBUTE_NAME);
	}

	/**
	 * установить идентификатор линка счета депо
	 * @param depoLinkId идентификатор линка счета депо
	 */
	public void setDepoLinkId(String depoLinkId)
	{
		setNullSaveAttributeStringValue(DEPO_LINK_ID_ATTRIBUTE_NAME, depoLinkId);
	}

	/**
	 * Получить описание поставщика. используется для отображения описания формы
	 * @return описание поставщика
	 */
	public String getReceiverDescription()
	{
		return getNullSaveAttributeStringValue(RECEIVER_DESCRIPTION_ATTRIBUTE_NAME);
	}

	/**
	 * Получить идентификатор клиента в биллинге.
	 * Наличие данного поля в платеже позволит биллингу не запрашивать часть полей или заполнять их
	 * значениями уже извесными  биллингу. например, лицевой счет, фио, показания счетчиков и пр.
	 * @return идентифкатор клиента в биллинге. может отсутсвовать. в данном случае возвращать null.
	 */
	@Override
	public String getBillingClientId()
	{
		return getNullSaveAttributeStringValue(BILLING_CLIENT_ID_ATTRIBUTE_NAME);
	}

	/**
	 * Установить идентификатор клиента в биллинге.
	 * Наличие данного поля в платеже позволит биллингу не запрашивать часть полей или заполнять их
	 * значениями уже извесными  биллингу. например, лицевой счет, фио, показания счетчиков и пр.
	 * @param billingClientId идентификатор клиента в биллинге
	 */
	public void setBillingClientId(String billingClientId)
	{
		setNullSaveAttributeStringValue(BILLING_CLIENT_ID_ATTRIBUTE_NAME, billingClientId);
	}

	/**
	 * @return наименование услуги
	 */
	public String getServiceName()
	{
		return getNullSaveAttributeStringValue(NAME_SERVICE_ATTRIBUTE_NAME);
	}

	@Override
	public void setService(Service service)
	{
		if (service == null)
		{
			setNullSaveAttributeStringValue(CODE_SERVICE_ATTRIBUTE_NAME, null);
			setNullSaveAttributeStringValue(NAME_SERVICE_ATTRIBUTE_NAME, null);
		}
		else
		{
			setNullSaveAttributeStringValue(CODE_SERVICE_ATTRIBUTE_NAME, service.getCode());
			setNullSaveAttributeStringValue(NAME_SERVICE_ATTRIBUTE_NAME, service.getName());
		}
	}

	/**
	 * Возвращаят доп. поля документа
	 * @return
	 * @throws DocumentException
	 */
	public Map<String,String> getExtendedAttributes() throws DocumentException
	{
		Map<String, ExtendedAttribute>  extendedAttributes = getAttributes();

		Map<String,String> result = new HashMap<String,String>();

		for (String key :extendedAttributes.keySet())
		{
			result.put(key,extendedAttributes.get(key).getStringValue());
		}
		return result;
	}

	/**
	 * Получить значение поля из документа
	 * @param field поле, для которого нужно получить значение. не может быть null
	 * @return значени
	 */
	@Override
	protected String getPaymentFieldValue(Field field)
	{
		if (!field.isMainSum())
		{
			//обычные поля хранятся в хвостах
			return getNullSaveAttributeStringValue(field.getExternalId());
		}
		//главная сумма хранится в DestinationAmount
		Money amount = getDestinationAmount();
		if (amount == null)
		{
			return null;
		}
		return amount.getDecimal().toString();
	}

	@Override
	public void setExtendedFields(List<Field> fields) throws DocumentException
	{
		//получаем имеющихся в платеже поля. Для определения новые они или нет
		Map<String, Field> prevFields = getFieldsMap();

		setNewFieldsBusinessSubType(prevFields, fields);

		//сохраняем описания новых полей.
		super.setExtendedFields(fields);

		if (fields == null)
		{
			return;
		}

		boolean someClientKeyFieldChanged  = isSomeKeyFieldChanged();
		boolean someBillingKeyFieldChanged = false;
		//проверяем на изменение ключевых полей.
		for (Field newField : fields)
		{
			String fieldId = newField.getExternalId();
			if (newField.isKey() && getAttribute(fieldId) != null)
				someBillingKeyFieldChanged = getAttribute(fieldId).getIsChanged();

			if (someBillingKeyFieldChanged)
				break;
		}

		//обновляем старые значения для полей:
		//1) Для старых полей (которые пришли из биллинга НЕ в первый раз) всегда используем значение из field.getValue()
		//2) Для редактируемых полей используем значение:
		//   a) если были изменены ключевые поля (клиентом либо биллингом), то значение из field.getValue() или field.getDefaultValue() (приоритет имеет field.getValue()).
		//   b) если ключевое поле не изменено, то берем текущее значение.
		//3) Для всех остальных полей используем значение из field.getValue() и field.getDefaultValue() (приоритет имеет field.getValue()).
		// Если оба значения пусты, обнуляем значение, имеющееся в платеже.
		for (Field newField : fields)
		{
			String value = null;
			String fieldId = newField.getExternalId();
			Field prevField = prevFields.get(fieldId);
			prevFields.remove(fieldId);

			if (prevField != null)
			{
				//Для старых полей (которые пришли из биллинга НЕ в первый раз) всегда используем значение из field.getValue()
				value = (String) newField.getValue();
			}
			// выносим отдельно костыль для РБЦ
			else if(com.rssl.phizgate.common.payments.BillingPaymentHelper.isRBCBilling(this) && !newField.isVisible())
			{
				value = (String) getBillingFieldValue(newField);
			}
			//для новых полей проверяем редактируемость
			else if (newField.isEditable() && !StringHelper.isEmpty(getPaymentFieldValue(newField)))
			{
				if (someBillingKeyFieldChanged || someClientKeyFieldChanged)
					//если изменилось хотя бы одно ключевое поле, то заменяем значением из биллинга.
					value = (String) getBillingFieldValue(newField);
				else
					//новые редактируемые поля с имеющимися значеними игнорим.
					continue;
			}
			//остался вариант либо новое нередактируемое, либо новое редактируемое без значения.
			else
			{
				//в этом случае обновляем значение пришедшими значениями из field.getValue() и field.getDefaultValue() (приоритет имеет field.getValue()
				value = (String) getBillingFieldValue(newField);
			}

			//обрабатываем отдельно поле с признаком главной суммы: оно приходит в расширенных атрибутах, но храним его в поле DestinationAmount.
			if (newField.isMainSum())
			{
				Currency currency = getNationalCurrency();// получаем национальную валюту
				//сумму обновлям только для старых полей или новых, но с дефолтным значением
				// и если это не автоподписка
				if (!StringHelper.isEmpty(value) && currency != null && !isLongOffer())
				{
					setDestinationAmount(new Money(new BigDecimal(value), currency));
				}
				else
				{
					setDestinationAmount(null);
				}
			}
			//обычные поля храним в доп полях.
			else
			{
				setExtendedFieldValue(fieldId, value);
			}
		}

		//Удаляем все доп. поля, которые были удалены на шлюзе, но были в нашей базе.
		for (String fieldId: prevFields.keySet())
		{
			removeAttribute(fieldId);
		}
	}

	/**
	 * Произошла ли смена значения обного из ключевых полей документа
	 * (во время согласования платежа с биллингом на одной из итераций согласования клиент изменил значение ключевого поля)
	 * @return true - да, на одной из итераций согласования платежа клиент изменил значение одного из ключевых полей
	 */
	private boolean isSomeKeyFieldChanged()
	{
		ExtendedAttribute attribute = getAttribute(IS_CHANGE_KEY_FIELDS_ATTRIBUTE_NAME);
		if (attribute == null)
		{
			return false;
		}
		return BooleanUtils.toBoolean(attribute.getStringValue());
	}

	private void setSomeKeyFieldChanged(boolean value)
	{
		setNullSaveAttributeBooleanValue(IS_CHANGE_KEY_FIELDS_ATTRIBUTE_NAME, value);
	}

	private void setNewFieldsBusinessSubType(Map<String, Field> prevFields, List<Field> fields)
	{
		if (MapUtils.isEmpty(prevFields) || CollectionUtils.isEmpty(fields))
			return;

		for (Field prevField : prevFields.values())
		{
			if (prevField.getBusinessSubType() != null)
			{
				for (Field field : fields)
				{
					// если значения Бизнес аттрибут не пришли, берем старое значение
					if (field.getExternalId().equals(prevField.getExternalId()) && (field instanceof CommonField) && field.getBusinessSubType() == null)
						((CommonField) field).setBusinessSubType(prevField.getBusinessSubType());
				}
			}
		}
	}

	/**
	 * Возвращает значение из биллинга.
	 * Если оно пусто, то значение по умолчанию,
	 * Если оба пусты, то значение из платежа.
	 * @param field поле.
	 * @return значение поля.
	 */
	public Object getBillingFieldValue(Field field)
	{
		if (StringHelper.isNotEmpty((String)field.getValue()))
		{
			return field.getValue();
		}

		if (StringHelper.isNotEmpty(field.getDefaultValue()))
		{
			return field.getDefaultValue();
		}

		return "";
	}

	/**
	 * Установить значение расширенного атрибута
	 * @param fieldId идентификатор поля
	 * @param newValue значение поля
	 */
	private void setExtendedFieldValue(String fieldId, Object newValue)
	{
		setNullSaveAttributeStringValue(fieldId, StringHelper.getEmptyIfNull(newValue));
	}

	/**
	 * Получить мап доп полей: внешний идентфикатор -> поле
	 * @return мап полей или пустой при отсутсвии.
	 */
	private Map<String, Field> getFieldsMap() throws DocumentException
	{
		Map<String, Field> result = new HashMap<String, Field>();
		List<Field> prevFields = getExtendedFields();
		if (prevFields != null)
		{
			for (Field field : prevFields)
			{
				result.put(field.getExternalId(), field);
			}
		}
		return result;
	}

	@Override
	public GateDocument asGateDocument()
	{
		return this;
	}

	/**
	 * номер транзитного счета получателя в офисе, в котором заключен договор с получателем
	 * @param receiverTransitAccount номер транзитного счета
	 */
	@Override
	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		setNullSaveAttributeStringValue(TRANSIT_RECEIVER_ACCOUNT_ATTRIBUTE_NAME, receiverTransitAccount);
	}

	/**
	 * номер транзитного счета получателя в офисе, в котором заключен договор с получателем
	 * @return номер транзитного счета
	 */
	@Override
	public String getReceiverTransitAccount()
	{
		return getNullSaveAttributeStringValue(TRANSIT_RECEIVER_ACCOUNT_ATTRIBUTE_NAME);
	}

	/**
	 * транзитный банк получателя
	 * @return транзитный банк получателя
	 */
	@Override
	public ResidentBank getReceiverTransitBank()
	{
		String bankBIC = getReceiverTransitBankBIC();
		if (StringHelper.isEmpty(bankBIC))
			return null;

		ResidentBank result = new ResidentBank();
		result.setBIC(bankBIC);
		result.setAccount(getReceiverTransitBankCorrAccount());
		result.setName(getReceiverTransitBankName());
		return result;
	}

	/**
	 * Установить транзитный банк получателя
	 * @param bank транзитный банк получателя
	 */
	public void setReceiverTransitBank(ResidentBank bank)
	{
		if (bank == null)
		{
			setReceiverTransitBankName(null);
			setReceiverTransitBankBIC(null);
			setReceiverTransitBankCorrAccount(null);
		}
		else
		{
			setReceiverTransitBankName(bank.getName());
			setReceiverTransitBankBIC(bank.getBIC());
			setReceiverTransitBankCorrAccount(bank.getAccount());
		}
	}

	String getReceiverTransitBankName()
	{
		return getNullSaveAttributeStringValue(TRANSIT_RECEIVER_NAME_ATTRIBUTE_NAME);
	}

	private void setReceiverTransitBankName(String receiverTransitBankName)
	{
		setNullSaveAttributeStringValue(TRANSIT_RECEIVER_NAME_ATTRIBUTE_NAME, receiverTransitBankName);
	}

	String getReceiverTransitBankBIC()
	{
		return getNullSaveAttributeStringValue(TRANSIT_RECEIVER_BIC_ATTRIBUTE_NAME);
	}

	private void setReceiverTransitBankBIC(String receiverTransitBankBIC)
	{
		setNullSaveAttributeStringValue(TRANSIT_RECEIVER_BIC_ATTRIBUTE_NAME, receiverTransitBankBIC);
	}

	String getReceiverTransitBankCorrAccount()
	{
		return getNullSaveAttributeStringValue(TRANSIT_RECEIVER_CORRACCOUNT_ATTRIBUTE_NAME);
	}

	private void setReceiverTransitBankCorrAccount(String receiverTransitBankCorrAccount)
	{
		setNullSaveAttributeStringValue(TRANSIT_RECEIVER_CORRACCOUNT_ATTRIBUTE_NAME, receiverTransitBankCorrAccount);
	}

	/**
	 * @return код офиса, в котором заключен договор с получателем.
	 */
	@Override
	public Code getReceiverOfficeCode()
	{
		String region = getReceiverOfficeRegion();
		String branch = getReceiverOfficeBranch();
		String office = getReceiverOfficeOffice();
		if (region == null)
		{
			return null;
		}
		return new ExtendedCodeImpl(region, branch, office);
	}

	/**
	 * Установить код офиса, в котором заключен договор с получателем.
	 * @param code код офиса, в котором заключен договор с получателем.
	 */
	@Override
	public void setReceiverOfficeCode(Code code)
	{
		ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(code);
		setReceiverOfficeRegion(extendedCode.getRegion());
		setReceiverOfficeBranch(extendedCode.getBranch());
		setReceiverOfficeOffice(extendedCode.getOffice());
	}

	private String getReceiverOfficeRegion()
	{
		return getNullSaveAttributeStringValue(RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME);
	}

	private void setReceiverOfficeRegion(String region)
	{
		setNullSaveAttributeStringValue(RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME, region);
	}

	private String getReceiverOfficeBranch()
	{
		return getNullSaveAttributeStringValue(RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME);
	}

	private void setReceiverOfficeBranch(String branch)
	{
		setNullSaveAttributeStringValue(RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME, branch);
	}

	private String getReceiverOfficeOffice()
	{
		return getNullSaveAttributeStringValue(RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME);
	}

	private void setReceiverOfficeOffice(String office)
	{
		setNullSaveAttributeStringValue(RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME, office);
	}

	/**
	 * @return признак необязательности предьявления банковских реквизитов получателя пользователю.
	 */
	@Override
	public boolean isNotVisibleBankDetails()
	{
		Boolean visibleBankDetails = (Boolean) getNullSaveAttributeValue(BANK_DETAILS_ATTRIBUTE_NAME);
		if (visibleBankDetails == null)
		{
			return false;
		}
		return !visibleBankDetails;
	}

	/**
	 * Установить признак необязательности предьявления банковских реквизитов получателя пользователю.
	 * @param notVisibleBankDetails признак необязательности предьявления банковских реквизитов получателя пользователю.
	 */
	@Override
	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{
		setAttributeValue(ExtendedAttribute.BOOLEAN_TYPE, BANK_DETAILS_ATTRIBUTE_NAME, !notVisibleBankDetails);
	}

	/**
	 * @return телефон получателя для обращения клиентов
	 */
	@Override
	public String getReceiverPhone()
	{
		return getNullSaveAttributeStringValue(PHONE_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * установить телефон получателя для обращения клиентов.
	 * @param receiverPhone телефон получателя для обращения клиентов
	 */
	@Override
	public void setReceiverPhone(String receiverPhone)
	{
		setNullSaveAttributeStringValue(PHONE_NUMBER_ATTRIBUTE_NAME, receiverPhone);
	}

	@Override
	public boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException
	{
		try
		{
			List<Field> templateFields = template.getExtendedFields();
			Map<String, String> templateFormData = template.getFormData();

			for (Field field : getExtendedFields())
			{
				if (!(field.isSaveInTemplate() || field.isKey()))
				{
					continue;
				}

				for (Field templateField : templateFields)
				{
					if (!field.getExternalId().equals(templateField.getExternalId()))
					{
						//сравниваем только совпавшие поля
						continue;
					}

					ExtendedAttribute originalAttr = getAttribute(field.getExternalId());
					if (originalAttr == null)
					{
						//если нет поля
						continue;
					}

					String templateFieldValue = templateFormData.get(templateField.getExternalId());
					if (!StringHelper.equalsNullIgnore(originalAttr.getStringValue(), templateFieldValue))
					{
						//если не совпали значения в платеже и шаблоне платежа
						return false;
					}
				}
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		return super.equalsKeyEssentials(template);
	}

	/**
	 * в биллинговых платежах поле "назначение платежа" служебное и нигде не отображается клиенту, поэтому всегда возвращаем true
	 * @param template шаблон, на основе которого был создан документ
	 * @return true
	 */
	@Override
	protected boolean equalsGrounds(TemplateDocument template)
	{
		return true;
	}

	@Override
	public AbstractAccountsTransfer createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		JurPayment payment = (JurPayment) super.createCopy(instanceClass);
		copyExtendedFields(payment);

		//если поставщик не из нашей базы, начинаем запрос доп. полей с самого начала
		if (getReceiverInternalId() == null)
			payment.setBillingCode(null);

		payment.setReceiverPointCode(getReceiverPointCode());
		payment.setReceiverInternalId(getReceiverInternalId());
		payment.setIdFromPaymentSystem(null);//при копиях не нужно передавать идентифкаторы платежа в биллиге в новый документ
		payment.setOperationUID(generateOperationUID());
		return payment;
	}

	public AlwaysAutoPayScheme getAlwaysAutoPayScheme()
	{
		return alwaysAutoPayScheme;
	}

	public void setAlwaysAutoPayScheme(AlwaysAutoPayScheme alwaysAutoPayScheme)
	{
		this.alwaysAutoPayScheme = alwaysAutoPayScheme;
	}

	public InvoiceAutoPayScheme getInvoiceAutoPayScheme()
	{
		return invoiceAutoPayScheme;
	}

	public void setInvoiceAutoPayScheme(InvoiceAutoPayScheme invoiceAutoPayScheme)
	{
		this.invoiceAutoPayScheme = invoiceAutoPayScheme;
	}

	public ThresholdAutoPayScheme getThresholdAutoPayScheme()
	{
		return thresholdAutoPayScheme ;
	}

	public void setThresholdAutoPayScheme(ThresholdAutoPayScheme thresholdAutoPayScheme)
	{
		this.thresholdAutoPayScheme = thresholdAutoPayScheme;
	}

	public Calendar getUpdateDate()
	{
		// при создании подписки, даты изменения нет
		return null;
	}

	public boolean isWithCommision()
	{
		Boolean isWithCommitionStr = (Boolean) getNullSaveAttributeValue(AUTOSUB_WITH_COMMISION);
		return BooleanUtils.toBoolean(isWithCommitionStr);
	}

	public void setWithCommision(boolean withCommision)
	{
		setNullSaveAttributeBooleanValue(AUTOSUB_WITH_COMMISION, withCommision);
	}

	@Override
	public String getFriendlyName()
	{
		return getNullSaveAttributeStringValue(AUTOSUB_FRIENDLY_NAME);
	}

	public Map<AutoSubType,Object> getAutoPaymentScheme()
	{
		Map<AutoSubType, Object> autoPaySchemes = new HashMap<AutoSubType, Object>();
		if(getThresholdAutoPayScheme() != null)
			autoPaySchemes.put(AutoSubType.THRESHOLD, getThresholdAutoPayScheme());

		if(getAlwaysAutoPayScheme() != null)
			autoPaySchemes.put(AutoSubType.ALWAYS, getAlwaysAutoPayScheme());

		if(getInvoiceAutoPayScheme() != null)
			autoPaySchemes.put(AutoSubType.INVOICE, getInvoiceAutoPayScheme());
		return autoPaySchemes;
	}

	public String getAutoPaySchemeAsString() throws DocumentException
	{
		return AutoSubscriptionTypeHelper.serialize(getAutoPaymentScheme());
	}

	public void setAutoPaySchemeAsString(String autoPaySchemeAsString) throws DocumentException
	{
		Map<AutoSubType, Object> autoPaySchemes = AutoSubscriptionTypeHelper.deserialize(autoPaySchemeAsString);
		if(autoPaySchemes == null)
			return;

		setInvoiceAutoPayScheme((InvoiceAutoPayScheme) autoPaySchemes.get(AutoSubType.INVOICE));
		setAlwaysAutoPayScheme((AlwaysAutoPayScheme) autoPaySchemes.get(AutoSubType.ALWAYS));
		setThresholdAutoPayScheme((ThresholdAutoPayScheme) autoPaySchemes.get(AutoSubType.THRESHOLD));
	}

	/**
	 * Дата и время изменения подписки
	 * @return дата и время изменения подписки
	 */
	public Calendar getUpdatedDate()
	{
		return null;
	}

	/**
	 * Максимальная сумма платежей в месяц
	 * @return максимальную сумму платежей в месяц
	 */
	@Override
	public Money getMaxSumWritePerMonth()
	{
		String autoSubType = getNullSaveAttributeStringValue(AUTOSUB_TYPE_ATTRIBUTE_NAME);

		if(AutoSubType.INVOICE.name().equals(autoSubType))
		{
			return createMoney(
					StringHelper.getEmptyIfNull(getNullSaveAttributeValue(AUTOSUB_INVOICE_MAX_DECIMAL)),
					getNullSaveAttributeStringValue(AUTOSUB_INVOICE_MAX_CURRENCY));
		}

		return null;
	}

	public String getMessageToRecipient()
	{
		return null;
	}

	public boolean isSameTB()
	{
		return false;
	}

	public void setMaxSumWritePerMonth(Money maxSumWritePerMonth)
	{
		if(maxSumWritePerMonth != null)
		{
			setNullSaveAttributeDecimalValue(AUTOSUB_INVOICE_MAX_DECIMAL , maxSumWritePerMonth.getDecimal());
			setNullSaveAttributeStringValue(AUTOSUB_INVOICE_MAX_CURRENCY , maxSumWritePerMonth.getCurrency().getCode());
		}
		else
		{
			setNullSaveAttributeDecimalValue(AUTOSUB_INVOICE_MAX_DECIMAL , null);
			setNullSaveAttributeStringValue(AUTOSUB_INVOICE_MAX_CURRENCY , null);
		}
	}

	/**
	 * @return Код группы услуг
	 */
	public String getGroupService()
	{
		return getNullSaveAttributeStringValue(PROVIDER_GROUP_SERVICE);
	}

	/**
	 * Установить код группы услуг
	 * @param groupService код группы услуг
	 */
	public void setGroupService(String groupService)
	{
		setNullSaveAttributeStringValue(PROVIDER_GROUP_SERVICE, groupService);
	}


    /**
     * Информация о билетах для платежа "Оплата брони авиабилетов"
     * @param ticketsInfo
     */
    public void setTicketInfo(String ticketsInfo)
    {
        setNullSaveAttributeStringValue(TICKETS_INFO_ATTRIBUTE_NAME, ticketsInfo);
    }

	/**
     * @return Информация о билетах для платежа "Оплата брони авиабилетов"
     */
	public String getTicketInfo()
	{
		return getNullSaveAttributeStringValue(TICKETS_INFO_ATTRIBUTE_NAME);
	}

	/**
	 * Номер брони авиабилетов
	 * метод используется при формировании смс-сообщения для подтверждения документа
	 */
	public String getAirlineReservationId()
	{
		return getNullSaveAttributeStringValue(RESERVATION_ID_ATTRIBUTE_NAME);
	}

	/**
	 * @return  Название поставщика-фасилитатора(оплата интернет-магазинов)
	 */
	public String getReceiverFacilitator()
	{
		return getNullSaveAttributeStringValue(RECEIVER_FACILITATOR);
	}

	public void setPanelBlockId(Long id)
	{
		setNullSaveAttributeLongValue(FROM_PANEL_ID, id);
	}

	public Long getPanelBlockId()
	{
		return (Long)getNullSaveAttributeValue(FROM_PANEL_ID);
	}

	public boolean isSelfMobileNumberPayment()
	{
		return isSelfMobileNumberPayment;
	}

	public void setSelfMobileNumberPayment(boolean selfMobileNumberPayment)
	{
		isSelfMobileNumberPayment = selfMobileNumberPayment;
	}

	public void setOrderUuid(String orderUUID)
	{
		this.orderUUID = orderUUID;
		uuidInitialized = true;
	}

	public String getOrderUuid()
	{
		if (!uuidInitialized && getId() != null)
		{
			try
			{
				orderUUID = ShopHelper.get().getOrderUuidByPayment(getId());
				uuidInitialized = true;
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				uuidInitialized = true;
			}
		}

		return orderUUID;
	}

	@Override
	protected String getDefaultTemplateName(Metadata metadata) throws DocumentException
	{
		String templateName = null;
		if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER == getFormType())
		{
			templateName =  getReceiverName();
		}

		if (FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER == getFormType())
		{
			templateName = getReceiverName();
			if (StringHelper.isNotEmpty(getReceiverAccount()))
			{
				templateName = templateName + " " + getReceiverAccount();
			}
		}

		if (StringHelper.isEmpty(templateName))
		{
			return super.getDefaultTemplateName(metadata);
		}
		return templateName;
	}

	/**
	 * @return идентификатор подписки на инвойсы, на основе которой создан платеж
	 */
	public Long getInvoiceSubscriptionId()
	{
		return getNullSaveAttributeLongValue(INVOICE_SUBSCRIPTION_ID);
	}

	/**
	 * Установить идентификатор подписки на инвойсы, на основе которой создан платеж
	 * @param id идентификатор подписки на инвойсы
	 */
	public void setInvoiceSubscriptionId(Long id)
	{
		setNullSaveAttributeLongValue(INVOICE_SUBSCRIPTION_ID, id);
	}

	/**
	 * @return Тест назначения платежа для истории mAPI
	 */
	public String getOperationDescription()
	{
		return getNullSaveAttributeStringValue(OPERATION_DESCRIPTION);
	}

	/**
	 * Установка назначения платежа для истории mAPI
	 * @param operationDescription
	 */
	public void setOperationDescription(String operationDescription)
	{
		setNullSaveAttributeStringValue(OPERATION_DESCRIPTION, operationDescription);
	}

	//Является ли документ платежом по штрих-коду
	public boolean isBarCodeDocument(ServiceProviderShort provider)
	{
		PaymentsConfig config = ConfigFactory.getConfig(PaymentsConfig.class);
		String regionTbCodeField = config.getRegionTBCode();
		//"regionTbCodeField" добавляется только для платежей по штрих-коду, если был выбран регион оплаты, отличный от "Все регионы"
		return (StringHelper.isNotEmpty(regionTbCodeField) && StringHelper.isNotEmpty(getNullSaveAttributeStringValue(regionTbCodeField)))
				|| (CreationType.atm.equals(getCreationType()) && (provider!= null && config.getAllRegionsProviderAtm().equals(provider.getSynchKey())))
				|| (CreationType.mobile.equals(getCreationType()) && (provider!= null && config.getAllRegionsProviderMobile().equals(provider.getSynchKey())));
	}

	public String getAutopayNumber()
	{
		return getNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME);
	}

	public void setAutopayNumber(String number)
	{
		setNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME, number);
	}

	@Override
	public boolean isEinvoicing()
	{
		return getNullSaveAttributeBooleanValue(IS_EINVOICING_ATTRIBUTE_NAME);
	}

	//для АТМ
	public void acceptAgreement()
	{
		setNullSaveAttributeBooleanValue(AGREEMENT_FIELD, true);
	}
}