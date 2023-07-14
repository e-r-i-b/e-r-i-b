package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.common.forms.state.StateMachineInfo;
import com.rssl.common.forms.state.Type;
import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.owner.PersonName;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

import static com.rssl.phizic.business.documents.payments.JurPayment.IS_CHANGE_KEY_FIELDS_ATTRIBUTE_NAME;
import static com.rssl.phizic.business.documents.templates.Constants.NODE_NUMBER_ATTRIBUTE_NAME;
import static com.rssl.phizic.business.documents.templates.Constants.TEMPORARY_NODE_ID_ATTRIBUTE_NAME;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class BusinessDocumentBase extends AttributableBase implements BusinessDocument<Department>
{
	protected static final PersonService personService = new PersonService();
	protected static final DepartmentService departmentService = new DepartmentService();

	private static final DocumentService documentService = new DocumentService();
	private static final SimpleService simpleService = new SimpleService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat SHORT_YEAR_FORMAT = new SimpleDateFormat("dd.MM.yy");

	public static final String CURRENCY_ATTRIBUTE_SUFFIX = "-currency";
	public static final String EXTRA_PARAMETERS_XPATH = "extra-parameters/parameter";
	public static final String EXTRA_PARAMETER_XPATH_TEMPLATE = "extra-parameters/parameter[@name='%s']";
	public static final String DOCUMENT_DATE_ATTRIBUTE_NAME = "document-date";
	public static final String DOCUMENT_TIME_ATTRIBUTE_NAME = "document-time";
	public static final String CREATION_DATE_ATTRIBUTE_NAME = "creation-date";
	public static final String OPERATION_DATE_ATTRIBUTE_NAME = "operation-date";
	public static final String ADMISSION_DATE_ATTRIBUTE_NAME = "admission-date";
	public static final String EXECUTION_DATE_ATTRIBUTE_NAME = "execution-date";
	public static final String OPERATION_TIME_ATTRIBUTE_NAME = "operation-time";       // время нужно для чеков
	public static final String DEPARTMENT_ID_ATTRIBUTE_NAME = "departmentId";
	public static final String OPERATION_DATE_SHORT_YEAR_ATTRIBUTE_NAME = "operation-date-short-year";  // дата формата dd.mm.yy нужна для чеков
	public static final String REASON_FOR_ADDITIONAL_CONFIRM = "reason-for-additional-confirm";         // причина для перевода документа в статус "требуется дополнительное подтверждение"
	public static final String FRAUD_MONITORING_CONFIRM_REASON = "fraud-monitoring-confirm";
	public static final String SECURITY_OFFICER_TEXT           = "security-officer-text";
	public static final String CLIENT_TRANSACTION_ID           = "client-transaction-id";
	public static final String AUTHORIZE_CODE_ATTRIBUTE_NAME = "AUTHORIZE_CODE";
	public static final String AUTHORIZE_DATE_ATTRIBUTE_NAME = "AUTHORIZE_DATE";
	public static final String OFFLINE_DELAYED_ATTRIBUTE_NAME = "isOfflineDelayed";
	public static final String OFFLINE_DELAYED_TO_DATE_ATTRIBUTE_NAME = "offlineDelayedToDate";
	public static final String CHECK_STATUS_COUNT_ATTRIBUTE_NAME = "check-status-count-result";
	public static final String MB_OPER_CODE_ATTRIBUTE_NAME = "mbOperCode";
	public static final String SMS_COMMAND = "smsCommand";
	public static final String RECHARGE_PHONE_NUMBER = "rechargePhoneNumber";
	public static final String MOBILE_OPERATOR_NAME = "mobileOperatorName";
	public static final String ERMB_PAYMENT_TYPE = "ermbPaymentType";
	public static final String TEMPLATE_NAME = "templateName";
	public static final String RECEIVER_SMS_ALIAS = "receiverSmsAlias";
	public static final String ERMB_SMS_REQUEST_ID = "ermbSmsRequestId";
	public static final String WRITE_DOWN_OPERATION_PREFIX = "_wdo_";
	public static final String NEED_SAVE_TEMPLATE_ATTRIBUTE_NAME = "need-save-template";
	public static final String DEVICE_INFO = "device_info";
	public static final String CREDIT_REPORT_STATUS = "credit_report_status";
	public static final String REMINDER_MARK_ATTRIBUTE_NAME = "process-invoice-mark";
	public static final String ADDITIONAL_OPERATION_DATE_ATTRIBUTE_NAME = "additional-operation-date";
	public static final String TARIFF_PLAN_ESB = "tariff-plan-esb";

	public static final String EVENT_MESSAGE_KEY = "eventMessageKey";

	public static final String ATTRIBUTE_NOTICE_CLASS = "notice-type";
	public static final String ATTRIBUTE_NOTICE_TITLE = "notice-title";
	public static final String ATTRIBUTE_NOTICE_TEXT = "notice-text";

	private static final String LAST_STATE_INDEX = "lastStateIndex";
	private static final String DOCUMENT_STATE_ENTRY_PREFIX = "documentState";

	/**
	 * Список тех аттрибутов, которые не должны скопироваться при повторе платежа.
	 */
	private static final Set<String> extendedAttributeCopyRestriction;
	static
	{
		extendedAttributeCopyRestriction = new HashSet<String>();
		String[] attributes = {
				AUTHORIZE_CODE_ATTRIBUTE_NAME,
				AUTHORIZE_DATE_ATTRIBUTE_NAME,
				OFFLINE_DELAYED_ATTRIBUTE_NAME,
				CHECK_STATUS_COUNT_ATTRIBUTE_NAME,
				IS_CHANGE_KEY_FIELDS_ATTRIBUTE_NAME
		};
		extendedAttributeCopyRestriction.addAll(Arrays.asList(attributes));
	}

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_WEB);

	private Calendar executionDate;
	private Long id;
	private String formName;
	protected Login clientLogin;
	private ActivePerson person;
	private String documentNumber;
	private State state;
	private DocumentSignature signature;
	private Calendar dateCreated;
	private Calendar documentDate;
	private Calendar operationDate;
	private Calendar admissionDate;
	private String payerName;
	private Department department;
	private Money commission;
	private Long formId;
	private Date changed;
	private CreationType creationType;
	private CreationType clientOperationChannel;
	private boolean archive = false;
	private String refusingReason;
	private String stateMachineName;
	private Long templateId;
	private Long countError = 0L;
	private CreationSourceType creationSourceType;
	private String systemName;
	private ConfirmStrategyType confirmStrategyType;
	private String operationUID;
	private String promoCode;
	private Long createdEmployeeLoginId;
	private Long confirmedEmployeeLoginId;
	private Long additionalConfirmedEmployeeLoginId;
	private String sessionId;
	private String codeATM;
	private LoginType loginType;                        //тип входа клиента (вход клиента по логину-паролю iPas не должен влиять на ограничение входа под другим профилем)
	private CreationType additionalOperationChannel;    // дополнительное подтверждение(через КЦ или УС)
	private LongOfferPayDay longOfferPayDay;

	protected DateFormat getDateFormat()
	{
		return (DateFormat) DATE_FORMAT.clone();
	}

	protected DateFormat getTimeFormat()
	{
		return (DateFormat) TIME_FORMAT.clone();
	}

	protected DateFormat getShortYearFormat()
	{
		return (DateFormat) SHORT_YEAR_FORMAT.clone();
	}

	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject() throws SecurityException, SecurityLogicException
	{
		try
		{
			FormDataConverter formDataConverter = new FormDataConverter(this);
			return formDataConverter.toSignableForm();
		}
		catch (BusinessLogicException e)
		{
			throw new SecurityLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}

	public void setId(Long id)
	{
		if (id == null && this.id != null)
			log.error(new Exception("Установлено не допустимое значение идентификатора платежа. Изменено с id=" + this.id + " на null."));
		this.id = id;
	}

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	/**
	 * @return разновидность ClientBusinessDocumentOwner с отложенной загрузкой персоны
	 */
	public BusinessDocumentOwner getOwner() throws BusinessException
	{
		if (clientLogin == null)
			return null;

		// noinspection AnonymousInnerClassWithTooManyMethods,OverlyComplexAnonymousInnerClass
		return new BusinessDocumentOwnerBase()
		{
			public Login getLogin()
			{
				return clientLogin;
			}

			public ActivePerson getPerson() throws BusinessException
			{
				if (person == null)
					person = personService.findByLogin(clientLogin);
				return person;
			}

			public boolean isGuest()
			{
				return false;
			}

			public String getSynchKey()
			{
				return makeClientSynchKey(clientLogin);
			}
		};
	}

	public void setOwner(BusinessDocumentOwner owner) throws BusinessException
	{
		if (owner.isGuest())
			throw new UnsupportedOperationException("Гостевой логин не подходит: " + owner);

		this.clientLogin = owner.getLogin();
		this.person = owner.getPerson();
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		setDateCreated(Calendar.getInstance());
		setDocumentNumberInternal(document);
		setDocumentDate(getDateCreated());                  //Изначально дату документа заполняем текущей
		setCodeATM(LogThreadContext.getCodeATM());

		readFromDom(document, InnerUpdateState.INIT, messageCollector);
	}

	public void initialize(TemplateDocument template) throws DocumentException
	{
		try
		{
			OperationContextUtil.synchronizeObjectAndOperationContext(this);
			setDepartment(departmentService.findByCode(new ExtendedCodeImpl(template.getOffice().getCode())));
			setCreationType(template.getClientCreationChannel());
			setState(template.getState());
			setTemplateId(template.getId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	protected void setDocumentNumberInternal(Document document) throws DocumentException
	{
		setDocumentNumber(documentService.getNextDocumentNumber());
	}

	public BusinessDocument createCopy() throws DocumentException, DocumentLogicException
	{
		return createCopy(null);
	}

	/**
	 * Создать копию как класс-наследник
	 * @param instanceClass класс наследника
	 * @return копия
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	public BusinessDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		BusinessDocumentBase document = newInstance(instanceClass);

		document.addAttributes(getAttributes().values(), extendedAttributeCopyRestriction);
		document.setFormName(getFormName());
		try
		{
			document.setOwner(getOwner());
			document.setDepartment(getDepartment());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		document.setDateCreated(Calendar.getInstance());
		document.setDocumentDate(document.getDateCreated());//При копировании дату документа заполняем текущей
		document.setDocumentNumberInternal(null);
		document.setCreationType(getCreationType());
		document.setCreationSourceType(getCreationSourceType());
		document.setStateMachineName(getStateMachineName());
		document.setCountError(Long.valueOf(0));       //при копии мы не должны сохранять количество ошибок создания прошлого платежа
		                                               //если оставить null то может быть ошибка при записи в базу

		return document;
	}

	public Document convertToDom() throws DocumentException
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

		Document document = documentBuilder.newDocument();

		Element root = document.createElement("document");
		document.appendChild(root);

		appendNullSaveDate(root, CREATION_DATE_ATTRIBUTE_NAME, getDateCreated());
		appendNullSaveTime(root, DOCUMENT_TIME_ATTRIBUTE_NAME, getDocumentDate());
		appendNullSaveDate(root, DOCUMENT_DATE_ATTRIBUTE_NAME, getDocumentDate());
		appendNullSaveDate(root, OPERATION_DATE_ATTRIBUTE_NAME, getOperationDate());
		appendNullSaveDate(root, ADMISSION_DATE_ATTRIBUTE_NAME, getAdmissionDate());
		appendNullSaveDate(root, EXECUTION_DATE_ATTRIBUTE_NAME, getExecutionDate());
		appendNullSaveTime(root, OPERATION_TIME_ATTRIBUTE_NAME, getOperationDate());

		try
		{
			appendNullSaveString(root, DEPARTMENT_ID_ATTRIBUTE_NAME, getDepartment() != null ? getDepartment().getId().toString() : null);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		appendNullSaveShortYear(root, OPERATION_DATE_SHORT_YEAR_ATTRIBUTE_NAME, getOperationDate());
		appendNullSaveMoney(root, "commission", getCommission());

		appendNullSaveString(root, "document-number", getDocumentNumber());
		if(getState() != null)
		{
			appendNullSaveString(root, "state", getState().getCode());
			appendNullSaveString(root, "state-description", getState().getDescription());
		}

		if (!StringHelper.isEmpty(getPromoCode()))
			appendNullSaveString(root, "promoCode", getPromoCode());

//TODO department,refusingReason????
		convertExtendedAttributesToDom(root);
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		Element root = document.getDocumentElement();
		if (XmlHelper.tagTest(DOCUMENT_DATE_ATTRIBUTE_NAME,root))
		{
			setDocumentDate(getDateFromDom(root, DOCUMENT_DATE_ATTRIBUTE_NAME));
		}

		readExtendedFields(root);
	}

	public void readFromDom(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		readFromDom(document, InnerUpdateState.UPDATE, messageCollector);
	}

	public Class<? extends GateDocument> getType()
	{
		return null;
	}

	public void setCreationType(CreationType type)
	{
		this.creationType = type;
	}

	public CreationType getCreationType()
	{
		return creationType;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public DocumentSignature getSignature()
	{
		return signature;
	}

	public void setSignature(DocumentSignature signature)
	{
		this.signature = signature;
	}

	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public Calendar getDocumentDate()
	{
		return documentDate;
	}

	public void setDocumentDate(Calendar documentDate)
	{
		this.documentDate = documentDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public Calendar getAdmissionDate()
	{
		return admissionDate;
	}

	public void setAdmissionDate(Calendar admissionDate)
	{
		this.admissionDate = admissionDate;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public Department getDepartment() throws BusinessException
	{
		return department;
	}

	public String getMbOperCode()
	{
		return getNullSaveAttributeStringValue(MB_OPER_CODE_ATTRIBUTE_NAME);
	}

	public void setMbOperCode(String mbOperCode)
	{
		setNullSaveAttributeStringValue(MB_OPER_CODE_ATTRIBUTE_NAME, mbOperCode);
	}

	public Long getSendNodeNumber()
	{
		return getNullSaveAttributeLongValue(NODE_NUMBER_ATTRIBUTE_NAME);
	}

	public void setSendNodeNumber(Long sendNodeNumber)
	{
		setNullSaveAttributeLongValue(NODE_NUMBER_ATTRIBUTE_NAME, sendNodeNumber);
	}

	public Long getTemporaryNodeId()
	{
		return getNullSaveAttributeLongValue(TEMPORARY_NODE_ID_ATTRIBUTE_NAME);
	}

	public void setTemporaryNodeId(Long nodeId)
	{
		setNullSaveAttributeLongValue(TEMPORARY_NODE_ID_ATTRIBUTE_NAME, nodeId);
	}

	/**
	 * @return комиссия документа
	 */

	public Money getCommission()
	{
		return commission;
	}

	/**
	 * Установка комиссии документа
	 * @param commission комиссия документа
	 */
	public void setCommission(Money commission)
	{
		this.commission = commission;
	}

	public CommissionOptions getCommissionOptions()
	{
		return null;
	}

	protected BusinessDocumentBase newInstance(Class<? extends BusinessDocument> instanceClass)
	{
		try
		{
			if(instanceClass != null)
				return (BusinessDocumentBase) instanceClass.newInstance();

			return this.getClass().newInstance();
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected String getNullSaveAttributeStringValue(String attributeName)
	{
		ExtendedAttribute extendedAttribute = getAttribute(attributeName);
		if (extendedAttribute == null)
		{
			return null;
		}
		return extendedAttribute.getStringValue();
	}

	protected void setNullSaveAttributeStringValue(String name, String value)
	{
		setAttributeValue(ExtendedAttribute.STRING_TYPE, name, value);
	}

	protected void setNullSaveAttributeBooleanValue(String name, Boolean value)
	{
		setAttributeValue(ExtendedAttribute.BOOLEAN_TYPE, name, value);
	}

	protected void setNullSaveAttributeDecimalValue(String name, BigDecimal value)
	{
		setAttributeValue(ExtendedAttribute.DECIMAL_TYPE, name, value);
	}

	protected void setNullSaveAttributeLongValue(String name, Long value)
	{
		setAttributeValue(ExtendedAttribute.LONG_TYPE, name, value);
	}

	protected Calendar getNullSaveAttributeCalendarValue(String name)
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(name));
	}

	protected Boolean getNullSaveAttributeBooleanValue(String name)
	{
		return BooleanUtils.toBoolean((Boolean) getNullSaveAttributeValue(name));
	}

	protected void setNullSaveAttributeCalendarValue(String name, Calendar value)
	{
		setAttributeValue(ExtendedAttribute.DATE_TYPE, name, DateHelper.toDate(value));
	}

	protected void setNullSaveAttributeIntegerValue(String name, Integer value)
	{
		setAttributeValue(ExtendedAttribute.INTEGER_TYPE, name, value);
	}

	protected Integer getNullSaveAttributeIntegerValue(String name)
	{
		return (Integer) getNullSaveAttributeValue(name);
	}

	protected Calendar getNullSaveAttributeDateTimeValue(String name)
	{
		Object value = getNullSaveAttributeValue(name);
		if (value instanceof Date)
			return DateHelper.toCalendar((Date) value);

		return (Calendar) value;
	}

	protected Long getNullSaveAttributeLongValue(String name)
	{
		String value = getNullSaveAttributeStringValue(name);
		return StringHelper.isNotEmpty(value) ? Long.parseLong(value) : null;
	}

	protected void setNullSaveAttributeDateTimeValue(String name, Calendar value)
	{
		ExtendedAttribute attribute = getAttribute(name);
		if (attribute != null && attribute.getType().equals(ExtendedAttribute.DATE_TYPE))
			removeAttribute(name);

		setAttributeValue(ExtendedAttribute.DATE_TIME_TYPE, name, value);
	}

	protected <T extends Enum<T>> T getNullSaveAttributeEnumValue(Class<T> enumType, String name)
	{
		String value = getNullSaveAttributeStringValue(name);
		if (StringHelper.isEmpty(value))
			return null;
		else
			return Enum.valueOf(enumType, value);
	}

	protected <T extends Enum<T>> void setNullSaveAttributeEnumValue(String name, T value)
	{
		setAttributeValue(ExtendedAttribute.STRING_TYPE, name, value!=null ? value.toString() : null);
	}

	protected Object getNullSaveAttributeValue(String attributeName)
	{
		ExtendedAttribute extendedAttribute = getAttribute(attributeName);
		if (extendedAttribute == null)
		{
			return null;
		}
		return extendedAttribute.getValue();
	}

	protected void readExtendedFields(Element root) throws DocumentException
	{
		try
		{
			NodeList parameterList = XmlHelper.selectNodeList(root, EXTRA_PARAMETERS_XPATH);
			readExtendedFields(parameterList);
		}
		catch (TransformerException e)
		{
			throw new DocumentException(e);
		}
	}

	protected void readExtendedFields(NodeList fieldValuesNodeList) throws DocumentException
	{
		for (int i = 0; i < fieldValuesNodeList.getLength(); i++)
		{
			Element element = (Element) fieldValuesNodeList.item(i);

			setAttributeValue(
					element.getAttribute("type"),
					element.getAttribute("name"),
					element.getAttribute("value"));
		}
	}

	protected Money getMoneyFromDom(Element root, String tagName) throws DocumentException
	{
		String amountText = XmlHelper.getSimpleElementValue(root, tagName);
		String currencyTagName = tagName + CURRENCY_ATTRIBUTE_SUFFIX;
		String currencyText = XmlHelper.getSimpleElementValue(root, currencyTagName);
		return createMoney(amountText, currencyText);
	}

	protected Money createMoney(String amountText, String currencyText)
	{
		if (StringHelper.isEmpty(amountText))
		{
			return null;
		}
		try
		{
			Currency currency;
			//TODO странный код. почему при непереданной валюте используем национальную?
			if (StringHelper.isEmpty(currencyText))
			{
				currency = findNationalCurrency();
			}
			else
			{
				currency = findCurrencyByISOCode(currencyText);
			}
			return new Money(new BigDecimal(amountText), currency);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected Calendar getDateFromDom(Element root, String tagName) throws DocumentException
	{
		String dateText = XmlHelper.getSimpleElementValue(root, tagName);
		if (dateText == null || dateText.length() == 0)
		{
			return null;
		}
		try
		{
			//TODO бардак!!!!!
			if (dateText.contains("."))
			{
				return DateHelper.parseCalendar(dateText);
			}
			return DateHelper.toCalendar(DateHelper.fromXMlDateToDate(dateText));
		}
		catch (ParseException e)
		{
			throw new DocumentException(e);
		}
	}

	private void convertExtendedAttributesToDom(Element root)
	{
		Document document = root.getOwnerDocument();

		Element extraParameters = document.createElement("extra-parameters");
		root.appendChild(extraParameters);

		Map<String, ExtendedAttribute> attributes = getAttributes();

		for (Map.Entry<String, ExtendedAttribute> entry : attributes.entrySet())
		{
			ExtendedAttribute value = entry.getValue();

			Element elementParameter = document.createElement("parameter");

			elementParameter.setAttribute("type", value.getType());
			elementParameter.setAttribute("name", entry.getKey());
			elementParameter.setAttribute("value", value.getStringValue());
			elementParameter.setAttribute("isChanged", String.valueOf(value.getIsChanged()));
			extraParameters.appendChild(elementParameter);
		}
	}

	protected void appendNullSaveString(Element root, String tagName, String value)
	{
		if (value != null)
		{
			XmlHelper.appendSimpleElement(root, tagName, value);
		}
	}

	protected void appendNullSaveDate(Element root, String tagName, Calendar calendar)
	{
		if (calendar != null)
		{
			appendNullSaveString(root, tagName, getDateFormat().format(DateHelper.toDate(calendar)));
		}
	}

	protected void appendNullSaveTime(Element root, String tagName, Calendar calendar)
	{
		if (calendar != null)
		{
			appendNullSaveString(root, tagName, getTimeFormat().format(DateHelper.toDate(calendar)));
		}
	}

	protected void appendNullSaveShortYear(Element root, String tagName, Calendar calendar)
	{
		if (calendar != null)
		{
			appendNullSaveString(root, tagName, getShortYearFormat().format(DateHelper.toDate(calendar)));
		}
	}

	protected void appendNullSaveMoney(Element root, String tagName, Money money)
	{
		if (money != null)
		{
			appendNullSaveString(root, tagName, money.getDecimal().toPlainString());
			appendNullSaveString(root, tagName + CURRENCY_ATTRIBUTE_SUFFIX, money.getCurrency().getCode());
		}
	}

	//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!
	/**
	 * @return id formId платежа
	 */
	public Long getFormId()
	{
		return formId;
	}

	/**
	 * @param formId Имя формы по которой создан платеж
	 */
	public void setFormId(Long formId)
	{
		this.formId = formId;
	}

	//TODO пронести во все платежные формы, исполнитель Худяков А
	//объеденить с formId (оставить что-то одно)
	public FormType getFormType()
	{
		return null;
	}

	public Date getChanged()
	{
		return changed;
	}

	public void setChanged(Date changed)
	{
		this.changed = changed;
	}

	public void setArchive(boolean archive)
	{
		this.archive = archive;
	}

	public boolean isArchive()
	{
		return archive;
	}

	public String getRefusingReason()
	{
		return refusingReason;
	}

	public void setRefusingReason(String refusingReason)
	{
		this.refusingReason = refusingReason;
	}

	public StateMachineInfo getStateMachineInfo()
	{
		return new StateMachineInfo(getStateMachineName(), Type.PAYMENT_DOCUMENT);
	}

	public String getStateMachineName()
	{
		return stateMachineName;
	}

	public void setStateMachineName(String stateMachineName)
	{
		this.stateMachineName = stateMachineName;
	}

	public Long getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
	}

	/**
	 * Утилитный метод получения валюты по ISO коду
	 * @param currencyCode код валюты
	 * @return валюта или null, если не найдено.
	 */
	protected final Currency findCurrencyByISOCode(String currencyCode) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		return currencyService.findByAlphabeticCode(currencyCode);
	}

	/**
	 * Утилитный метод получения валюты по цифровому коду
	 * @param currencyCode код валюты
	 * @return валюта или null, если не найдено.
	 */
	protected Currency findCurrencyByNumericCode(String currencyCode) throws DocumentException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			return currencyService.findByNumericCode(currencyCode);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Утилитный метод получения национальной валюы
	 * @return национальная валюта.
	 */
	protected final Currency findNationalCurrency() throws DocumentException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			return currencyService.getNationalCurrency();
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Утилитный метод получения персоны - владельца документа(по текущему состонию БД)
	 * @return владелец
	 * @throws DocumentException
	 */
	public String getFormattedPersonName() throws DocumentException
	{
		try
		{
			return PersonHelper.getFormattedPersonName(getOwner().getPerson());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Возвращает количество системных ошибок, возникших при проведении платежа
	 * @return количество ошибок
	 */
	public Long getCountError()
	{
		return countError;
	}

	/**
	 * Устанавливает новое значение количества системных ошибок при проведении платежа
	 * @param countError - кол-во ошибок
	 */
	public void setCountError(Long countError)
	{
		this.countError = countError;
	}

	/**
	 * Прибавление 1 к количеству ошибок
	 */
	public void incrementCountError()
	{
		countError++;
	}

	public CreationSourceType getCreationSourceType()
	{
		return creationSourceType;
	}

	public void setCreationSourceType(CreationSourceType creationSourceType)
	{
		this.creationSourceType = creationSourceType;
	}

	public boolean isTemplate()
	{
		return false;
	}

	public String getSystemName()
	{
		return systemName;
	}

	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public String getPromoCode()
	{
		return promoCode;
	}

	public void setPromoCode(String promoCode)
	{
		this.promoCode = promoCode;
	}

	public Long getCreatedEmployeeLoginId()
	{
		return createdEmployeeLoginId;
	}

	public void setCreatedEmployeeLoginId(Long createdEmployeeLoginId)
	{
		this.createdEmployeeLoginId = createdEmployeeLoginId;
	}

	public Long getConfirmedEmployeeLoginId()
	{
		return confirmedEmployeeLoginId;
	}

	public void setConfirmedEmployeeLoginId(Long confirmedEmployeeLoginId)
	{
		this.confirmedEmployeeLoginId = confirmedEmployeeLoginId;
	}

	public Long getAdditionalConfirmedEmployeeLoginId()
	{
		return additionalConfirmedEmployeeLoginId;
	}

	public void setAdditionalConfirmedEmployeeLoginId(Long additionalConfirmedEmployeeLoginId)
	{
		this.additionalConfirmedEmployeeLoginId = additionalConfirmedEmployeeLoginId;
	}

	/**
	 * @return канал создания клиентом
	 */
	public Calendar getClientCreationDate()
	{
		return getDateCreated();
	}

	public void setClientCreationDate(Calendar clientCreationDate)
	{
		setDateCreated(clientCreationDate);
	}

	public CreationType getClientCreationChannel()
	{
		return getCreationType();
	}

	public void setClientCreationChannel(CreationType clientCreationChannel)
	{
		setCreationType(clientCreationChannel);
	}

	public void setClientCreationChannel(String clientCreationChannel)
	{
		setCreationType(CreationType.valueOf(clientCreationChannel));
	}

	/**
	 * @return дата подтверждения клиентом
	 */
	public Calendar getClientOperationDate()
	{
		return getOperationDate();
	}

	/**
	 * Установить дату подтверждения клиентом
	 * @param clientOperationDate дата подтверждения клиентом
	 */
	public void setClientOperationDate(Calendar clientOperationDate)
	{
		setOperationDate(clientOperationDate);
	}

	/**
	 * @return канал подтверждения клиентом
	 */
	public CreationType getClientOperationChannel()
	{
		return clientOperationChannel;
	}

	public void setClientOperationChannel(CreationType clientOperationChannel)
	{
		this.clientOperationChannel = clientOperationChannel;
	}

	public void setClientOperationChannel(String clientOperationChannel)
	{
		this.clientOperationChannel = CreationType.valueOf(clientOperationChannel);
	}

	/**
	 * @return дата подтверждения сотрудником
	 */
	public Calendar getAdditionalOperationDate()
	{
		return getNullSaveAttributeDateTimeValue(ADDITIONAL_OPERATION_DATE_ATTRIBUTE_NAME);
	}

	public void setAdditionalOperationDate(Calendar date)
	{
		setNullSaveAttributeDateTimeValue(ADDITIONAL_OPERATION_DATE_ATTRIBUTE_NAME, date);
	}

	/**
	 * @return канал подтверждения сотрудником
	 */
	public CreationType getAdditionalOperationChannel()
	{
		return additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(CreationType additionalOperationChannel)
	{
		this.additionalOperationChannel = additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(String additionalOperationChannel)
	{
		this.additionalOperationChannel = CreationType.valueOf(additionalOperationChannel);
	}

	/**
	 * @return информация создавшену операцию сотруднику
	 * @throws GateException
	 */
	public EmployeeInfo getCreatedEmployeeInfo() throws GateException
	{
		try
		{
			Long loginId = getCreatedEmployeeLoginId();
			return loginId == null ? null : createImployeeInfo(loginId);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @return информация по подтвердившему операцию (в АРМ сотрудника (автоплатежи)) сотруднику
	 * @throws GateException
	 */
	public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
	{
		try
		{
			Long loginId = getConfirmedEmployeeLoginId();
			return loginId == null ? null : createImployeeInfo(loginId);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void setConfirmedEmployeeInfo(EmployeeInfo info)
	{

	}

	private EmployeeInfo createImployeeInfo(Long loginId) throws BusinessException
	{
		BankLogin login = simpleService.findById(BankLogin.class, loginId);
		if (login == null)
		{
			throw new BusinessException("Не найден сотрудник c loginId " + loginId);
		}

		Employee employee = employeeService.findByLogin(login);
		if (employee == null)
		{
			throw new BusinessException("Не найден сотрудник c loginId " + loginId);
		}

		Department employeeOffice = simpleService.findById(Department.class, employee.getDepartmentId());
		PersonName personName = new PersonNameImpl(employee.getSurName(), employee.getFirstName(), employee.getPatrName());

		return new EmployeeInfoImpl(String.valueOf(loginId), login.getUserId(), personName, employeeOffice);
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getCodeATM()
	{
		return codeATM;
	}

	public void setCodeATM(String codeATM)
	{
		this.codeATM = codeATM;
	}

	public boolean isPaymentByConfirmTemplate() throws DocumentException
	{
		return false;
	}

	public String getReasonForAdditionalConfirm()
	{
		return getNullSaveAttributeStringValue(REASON_FOR_ADDITIONAL_CONFIRM);
	}

	public void setReasonForAdditionalConfirm(String reason)
	{
		setNullSaveAttributeStringValue(REASON_FOR_ADDITIONAL_CONFIRM, reason);
	}

	public LoginType getLoginType()
	{
		return loginType;
	}

	public void setLoginType(LoginType loginType)
	{
		this.loginType = loginType;
	}

	public String getDeviceInfo()
	{
		return getNullSaveAttributeStringValue(DEVICE_INFO);
	}

	public void setDeviceInfo(String deviceInfo)
	{
		setNullSaveAttributeStringValue(DEVICE_INFO, deviceInfo);
	}

	/**
	 * @return дефолтное описание статуса
	 */
	public String getDefaultStateDescription()
	{
		StateMachine machine = paymentStateMachineService.getStateMachineByFormName(getFormName());
		MachineState machineState = machine.getMachineStateByObjectStateCode(getState().getCode());
		if (machineState == null)
			return null;

		return machineState.getDescription();
	}

	/**
	 * Флаг, обозначающий превышение допустимого значения проверок статуса платежа во ВС
	 * @return true - превышен
	 */
	public boolean getCheckStatusCountResult()
	{
		return BooleanUtils.isTrue((Boolean) getNullSaveAttributeValue(CHECK_STATUS_COUNT_ATTRIBUTE_NAME));
	}

	/**
	 * Установить флаг, обозначающий превышение допустимого значения проверок статуса платежа во ВС
	 * @param result - флаг
	 */
	public void setCheckStatusCountResult(boolean result)
	{
		setNullSaveAttributeBooleanValue(CHECK_STATUS_COUNT_ATTRIBUTE_NAME, result);
	}

	public Calendar getOfflineDelayedDate()
	{
		return (Calendar) getNullSaveAttributeValue(BusinessDocumentBase.OFFLINE_DELAYED_TO_DATE_ATTRIBUTE_NAME);
	}

	public boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException
	{
		throw new UnsupportedOperationException("Данная операция не поддерживается этим типом документа");
	}

	public boolean isByTemplate()
	{
		return CreationSourceType.template == creationSourceType && templateId != null;
	}

	public boolean isByMobileTemplate()
	{
		return CreationSourceType.mobiletemplate == creationSourceType;
	}

	public boolean isCopy()
	{
		return CreationSourceType.copy == creationSourceType && templateId != null;
	}

	public PaymentAbilityERL getChargeOffResourceLink() throws DocumentException
	{
		return null;
	}

	public ExternalResourceLink getDestinationResourceLink() throws DocumentException
	{
		return null;
	}

	public void setSmsCommand(String command)
	{
		setNullSaveAttributeStringValue(SMS_COMMAND, command);
	}
	public String getSmsCommand()
	{
		return getNullSaveAttributeStringValue(SMS_COMMAND);
	}

	public void setRechargePhoneNumber(String rechargePhoneNumber)
	{
		setNullSaveAttributeStringValue(RECHARGE_PHONE_NUMBER, rechargePhoneNumber);
	}

	public String getRechargePhoneNumber()
	{
		return getNullSaveAttributeStringValue(RECHARGE_PHONE_NUMBER);
	}

	public String getMobileOperatorName()
	{
		return getNullSaveAttributeStringValue(MOBILE_OPERATOR_NAME);
	}

	public void setMobileOperatorName(String mobileOperatorName)
	{
		setNullSaveAttributeStringValue(MOBILE_OPERATOR_NAME, mobileOperatorName);
	}

	/**
	 * получить точную(введенную пользователем) сумму документа
	 * @return точная сумма(null если документ без суммы)
	 */
	public Money getExactAmount()
	{
		return null;
	}

	public String getErmbPaymentType()
	{
		return getNullSaveAttributeStringValue(ERMB_PAYMENT_TYPE);
	}

	public void setErmbPaymentType(String ermbPaymentType)
	{
		setNullSaveAttributeStringValue(ERMB_PAYMENT_TYPE, ermbPaymentType);
	}

	public String getTemplateName()
	{
		return getNullSaveAttributeStringValue(TEMPLATE_NAME);
	}

	public void setTemplateName(String templateName)
	{
		setNullSaveAttributeStringValue(TEMPLATE_NAME, templateName);
	}

	public String getReceiverSmsAlias()
	{
		return getNullSaveAttributeStringValue(RECEIVER_SMS_ALIAS);
	}

	public void setReceiverSmsAlias(String receiverSmsAlias)
	{
		setNullSaveAttributeStringValue(RECEIVER_SMS_ALIAS, receiverSmsAlias);
	}

	public String getErmbSmsRequestId()
	{
		return  getNullSaveAttributeStringValue(ERMB_SMS_REQUEST_ID);
	}

	public void setErmbSmsRequestId(String ermbSmsRequestId)
	{
		setNullSaveAttributeStringValue(ERMB_SMS_REQUEST_ID, ermbSmsRequestId.toString());
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		Map<String, ExtendedAttribute> attributes = getAttributes();
		List<WriteDownOperation> list = new ArrayList<WriteDownOperation>();
		for(String name: attributes.keySet())
			if(name.startsWith(WRITE_DOWN_OPERATION_PREFIX))
				list.add(writeDownOperationFromString((String)attributes.get(name).getValue()));
		return list;
	}

	public void setWriteDownOperations(List<WriteDownOperation> list)
	{
		clearWriteDownOperations(); //т.к. комиссии в хвостах, сначала затираем старые.
		for (int i = 0; i < list.size(); i++)
			setNullSaveAttributeStringValue(WRITE_DOWN_OPERATION_PREFIX + i, list.get(i).asString());
	}

	private void clearWriteDownOperations()
	{
		Iterator it = getAttributes().keySet().iterator();
		while (it.hasNext())
		{
			String attributeName = (String)it.next();
			if(attributeName.startsWith(WRITE_DOWN_OPERATION_PREFIX))
				it.remove();
		}
	}

	private WriteDownOperation writeDownOperationFromString(String operationAsString)
	{
		WriteDownOperation operation = new WriteDownOperation();
		String[] array = operationAsString.split("\\|");
		operation.setOperationName(array[0]);
		Money amount = createMoney(array[1],array[2]);
		operation.setCurAmount(amount);
		operation.setTurnOver(array[3]);
		return operation;
	}

	/**
	 *
	 * @param name - ключ сообщения об исполнении платежа/перевода
	 */
	public void setEventMessageKey(String name)
	{
		setNullSaveAttributeStringValue(EVENT_MESSAGE_KEY, name);
	}

	/**
	 *
	 * @return ключ сообщения об исполнении платежа/перевода
	 */
	public String getEventMessageKey()
	{
		return getNullSaveAttributeStringValue(EVENT_MESSAGE_KEY);
	}

	public String getDefaultTemplateName() throws BusinessException, BusinessLogicException
	{
		return StringUtils.EMPTY;
	}

	public boolean isNeedSaveTemplate()
	{
		return getNullSaveAttributeBooleanValue(NEED_SAVE_TEMPLATE_ATTRIBUTE_NAME);
	}

	public void setNeedSaveTemplate(boolean needSaveTemplate)
	{
		setNullSaveAttributeBooleanValue(NEED_SAVE_TEMPLATE_ATTRIBUTE_NAME, needSaveTemplate);
	}

	protected String getDefaultTemplateName(Metadata metadata) throws DocumentException
	{
		String templateName = metadata.getForm().getTemplateName();
		if (StringHelper.isEmpty(templateName))
		{
			return metadata.getForm().getDescription();
		}
		return templateName;
	}

	public boolean isLongOffer()
	{
		return false;
	}

	public String getTarifPlanCodeType() throws DocumentException
	{
		return TariffPlanHelper.getUnknownTariffPlanCode();
	}

	public LongOfferPayDay getLongOfferPayDay()
	{
		return longOfferPayDay;
	}

	public void setLongOfferPayDay(LongOfferPayDay longOfferPayDay)
	{
		this.longOfferPayDay = longOfferPayDay;
	}

	public void setMarkReminder(boolean reminder)
	{
		setNullSaveAttributeBooleanValue(REMINDER_MARK_ATTRIBUTE_NAME, reminder);
	}

	public boolean isMarkReminder()
	{
		return BooleanUtils.isTrue(getNullSaveAttributeBooleanValue(REMINDER_MARK_ATTRIBUTE_NAME));
	}

	/**
	 * @return наименование (имя) плательщика
	 */
	public String getPayerName()
	{
		return payerName;
	}

	/**
	 * Установка наименования (имени) плательщика
	 * @param payerName наименование (имя) плательщика
	 */
	public void setPayerName(String payerName)
	{
		this.payerName = payerName;
	}

	/**
	 * Внутреннее состояние обновления
	 */
	protected enum InnerUpdateState
	{
		/**
		 * Инициализация
		 */
		INIT,

		/**
		 * Обновление
		 */
		UPDATE
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{
		setNullSaveAttributeStringValue(TARIFF_PLAN_ESB, tariffPlanESB);
	}

	public String getTariffPlanESB()
	{
		return getNullSaveAttributeStringValue(TARIFF_PLAN_ESB);
	}

	public boolean isUpgradable()
	{
		return false;
	}

	public boolean isAlwaysIMSICheck()
	{
		return false;
	}

	public DocumentNotice getNotice()
	{
		String clazz = getNullSaveAttributeStringValue(ATTRIBUTE_NOTICE_CLASS);
		String title = getNullSaveAttributeStringValue(ATTRIBUTE_NOTICE_TITLE);
		String text = getNullSaveAttributeStringValue(ATTRIBUTE_NOTICE_TEXT);
		if (StringHelper.isEmpty(text))
			return null;
		DocumentNotice notice = new DocumentNotice(clazz, title, text);
		return notice;
	}

	public void removeNotice()
	{
		removeAttribute(ATTRIBUTE_NOTICE_CLASS);
		removeAttribute(ATTRIBUTE_NOTICE_TITLE);
		removeAttribute(ATTRIBUTE_NOTICE_TEXT);
	}

	public void setNotice(DocumentNotice notice)
	{
		if (notice == null)
			return;
		setNullSaveAttributeStringValue(ATTRIBUTE_NOTICE_CLASS, notice.getType().toString());
		setNullSaveAttributeStringValue(ATTRIBUTE_NOTICE_TITLE, notice.getTitle());
		setNullSaveAttributeStringValue(ATTRIBUTE_NOTICE_TEXT, notice.getText());
	}

	public boolean checkApplicationRestriction()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
			return getCreatedEmployeeLoginId() != null;
		return getCreatedEmployeeLoginId() == null;
	}

	public boolean supportStatusHistory()
	{
		return false;
	}

	public List<BusinessDocumentStatusEntry> getStatusHistory()
	{
		if (!supportStatusHistory())
			return Collections.emptyList();

		Long lastStateIndex = getNullSaveAttributeLongValue(LAST_STATE_INDEX);
		if (lastStateIndex == null)
			return Collections.emptyList();

		List<BusinessDocumentStatusEntry> historyList = new ArrayList<BusinessDocumentStatusEntry>();

		for (int i = 1; i <= lastStateIndex; i++)
		{
			ExtendedAttribute extendedAttribute = getAttribute(DOCUMENT_STATE_ENTRY_PREFIX + i);
			historyList.add(new BusinessDocumentStatusEntry(extendedAttribute.getDateCreated(), extendedAttribute.getStringValue()));
		}

		return historyList;
	}

	public void saveStateInHistory(String stateCode)
	{
		if (!supportStatusHistory())
			return;

		Long lastStateIndex = getNullSaveAttributeLongValue(LAST_STATE_INDEX);
		if (lastStateIndex == null)
			lastStateIndex = 0L;
		lastStateIndex++;

		setNullSaveAttributeLongValue(LAST_STATE_INDEX, lastStateIndex);
		setNullSaveAttributeStringValue(DOCUMENT_STATE_ENTRY_PREFIX + lastStateIndex, stateCode);
	}

	public boolean isFraudReasonForAdditionalConfirm()
	{
		if (!RSAConfig.getInstance().isSystemActive())
		{
			return false;
		}

		String reason = getReasonForAdditionalConfirm();
		if (StringHelper.isEmpty(reason))
		{
			return false;
		}

		return reason.equals(FRAUD_MONITORING_CONFIRM_REASON);
	}

	public String getSecurityOfficerText()
	{
		return getNullSaveAttributeStringValue(SECURITY_OFFICER_TEXT);
	}

	public void setSecurityOfficerText(String text)
	{
		setNullSaveAttributeStringValue(SECURITY_OFFICER_TEXT, text);
	}

	public String getClientTransactionId()
	{
		return getNullSaveAttributeStringValue(CLIENT_TRANSACTION_ID);
	}

	public void setClientTransactionId(String transactionId)
	{
		setNullSaveAttributeStringValue(CLIENT_TRANSACTION_ID, transactionId);
	}
}
