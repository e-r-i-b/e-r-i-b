package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.StateMachineInfo;
import com.rssl.common.forms.state.Type;
import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentService;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedFieldFilter;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.ClientComparator;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.csa.ProfileHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.clients.GUIDComparator;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * Базовый класс шаблона документа
 *
 * @author khudyakov
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class PaymentTemplateBase extends AttributableBase implements TemplateDocument
{
	private static final PersonService personService = new PersonService();
	private static final DocumentService documentService = new DocumentService();

	private Long id;                                    //идентификатор
	private String externalId;                          //внешний идентификатор
	private String documentNumber;                      //номер документа
	private String clientExternalId;                    //идентификатор клиента
	private Calendar clientCreationDate;                //дата создания клиентом
	private CreationType clientCreationChannel;         //канал создания клиентом
	private Calendar clientOperationDate;               //дата подтверждения клиентом
	private CreationType clientOperationChannel;        //канал подтверждения клиентом
	private Calendar additionalOperationDate;           //дата подтверждения сотрудником
	private CreationType additionalOperationChannel;    //канал подтверждения сотрудником
	private EmployeeInfo createdEmployeeInfo;           //информация о сотруднике создавшем платеж/шаблон
	private EmployeeInfo confirmedEmployeeInfo;         //информация о сотруднике подтвердиышем платеж/шаблон
	private String operationUID;                        //operUID объекта
	private String regionId;                            //Номер территориального банка
	private String agencyId;                            //Номер отделения
	private String branchId;                            //Номер филиала
	private State state;                                //статус шаблона операции
	private Date changed;
	private FormType formType;                          //тип формы
	private String classType;                           //тип шаблона
	private TemplateInfo templateInfo;                  //информация по шаблону
	private String stateMachineName;                    //машина состояний
	private Client clientOwner;
	private ActivityInfo activityInfo;                  //информация по активности шаблона
	private ReminderInfo reminderInfo;                  //информация о напоминании


	public void initialize() throws BusinessException, BusinessLogicException
	{
		try
		{
			setClientCreationDate(Calendar.getInstance());
			setDocumentNumber(documentService.getNextDocumentNumber());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initialize(BusinessDocument document) throws BusinessException
	{

	}

	public ActivityInfo getActivityInfo()
	{
		return activityInfo;
	}

	public void setActivityInfo(ActivityInfo activityInfo)
	{
		this.activityInfo = activityInfo;
	}

	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = new HashMap<String, String>();

		appendExtendedAttributes(formData, getExtendedAttributes());

		//даты
		appendNullSaveTime(formData, DOCUMENT_TIME_ATTRIBUTE_NAME, getClientCreationDate());
		appendNullSaveDate(formData, DOCUMENT_DATE_ATTRIBUTE_NAME, getClientCreationDate());
		appendNullSaveDate(formData, OPERATION_DATE_ATTRIBUTE_NAME, getClientOperationDate());

		//номер документ
		appendNullSaveString(formData, DOCUMENT_NUMBER_ATTRIBUTE_NAME, getDocumentNumber());

		//статус
		appendNullSaveString(formData, OPERATION_STATE_ATTRIBUTE_NAME, getState().getCode());

		return formData;
	}

	public void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException
	{
		if (MapUtils.isEmpty(source.getAllValues()))
		{
			return;
		}

		//все остальные пары помещаем в доп. атрибуты шаблона
		for (Map.Entry<String, String> entry : source.getAllValues().entrySet())
		{
			if (ExtendedFieldFilter.filter(entry.getKey()))
			{
				continue;
			}
			setNullSaveAttributeStringValue(entry.getKey(), entry.getValue());
		}
	}

	public String generateDefaultName(Metadata metadata) throws BusinessException
	{
		String templateName = metadata.getForm().getTemplateName();
		if (StringHelper.isEmpty(templateName))
		{
			return metadata.getForm().getDescription();
		}
		return templateName;
	}

	public Person getOwner() throws BusinessException
	{
		return personService.findById(Long.valueOf(getClientExternalId()));
	}

	public Long getId()
	{
		return id;
	}

	public StateMachineInfo getStateMachineInfo()
	{
		return new StateMachineInfo(getStateMachineName(), Type.TEMPLATE_DOCUMENT);
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getClientExternalId()
	{
		return clientExternalId;
	}

	public void setClientExternalId(String clientExternalId)
	{
		this.clientExternalId = clientExternalId;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public Office getOffice() throws GateException
	{
		ConfigImpl config = ConfigFactory.getConfig(ConfigImpl.class);
		if (config.isUSCTEnabled())
		{
			return getClientOwner().getOffice();
		}

		return new OfficeImpl(new CodeImpl(regionId, agencyId, branchId));
	}

	public void setOffice(Office office)
	{
		if (office == null)
		{
			return;
		}

		Map<String, String> fields = office.getCode().getFields();

		regionId = fields.get("region");
		agencyId = fields.get("branch");
		branchId = fields.get("office");
	}

	public String getRegionId()
	{
		return regionId;
	}

	public void setRegionId(String regionId)
	{
		this.regionId = regionId;
	}

	public String getAgencyId()
	{
		return agencyId;
	}

	public void setAgencyId(String agencyId)
	{
		this.agencyId = agencyId;
	}

	public String getBranchId()
	{
		return branchId;
	}

	public void setBranchId(String branchId)
	{
		this.branchId = branchId;
	}

	public Money getCommission()
	{
		//в шаблоне платежа комиссия не используется
		return null;
	}

	public void setCommission(Money commission)
	{
		//в шаблоне платежа комиссия не используется
	}

	public CommissionOptions getCommissionOptions()
	{
		//в шаблоне платежа комиссия не используется
		return null;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public Calendar getAdmissionDate()
	{
		//в шаблоне платежа дата приема платежа внешней системой не проставляется
		return null;
	}

	public boolean isTemplate()
	{
		return true;
	}

	public Calendar getExecutionDate()
	{
		//в шаблоне платежа даты исполнения операции быть не должно
		return null;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		//в шаблоне платежа даты исполнения операции быть не должно
	}

	public Calendar getClientCreationDate()
	{
		return clientCreationDate;
	}

	public void setClientCreationDate(Calendar clientCreationDate)
	{
		this.clientCreationDate = clientCreationDate;
	}

	public CreationType getClientCreationChannel()
	{
		return clientCreationChannel;
	}

	public void setClientCreationChannel(CreationType clientCreationChannel)
	{
		this.clientCreationChannel = clientCreationChannel;
	}

	public void setClientCreationChannel(String clientCreationChannel)
	{
		this.clientCreationChannel = CreationType.valueOf(clientCreationChannel);
	}

	public Calendar getClientOperationDate()
	{
		return clientOperationDate;
	}

	public void setClientOperationDate(Calendar clientOperationDate)
	{
		this.clientOperationDate = clientOperationDate;
	}

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

	public Calendar getAdditionalOperationDate()
	{
		return additionalOperationDate;
	}

	public void setAdditionalOperationDate(Calendar additionalOperationDate)
	{
		this.additionalOperationDate = additionalOperationDate;
	}

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

	public Client getClientOwner() throws GateException
	{
		if (clientOwner != null)
			return clientOwner;

		try
		{   clientOwner = new ClientImpl(personService.findById(Long.valueOf(getClientExternalId())));
			return clientOwner;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void setClientOwner(Client client) throws GateException, GateLogicException
	{
		try
		{
			setClientExternalId(getInternalOwnerId(client).toString());
			clientOwner = null;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e);
		}
	}

	private Long getInternalOwnerId(final Client client) throws BusinessException, BusinessLogicException
	{
		ConfigImpl config = ConfigFactory.getConfig(ConfigImpl.class);
		if (!config.isUSCTEnabled())
		{
			return client.getInternalId();
		}

		try
		{
			//если контекст клиента установлен
			if (PersonContext.isAvailable())
			{
				//проверяем соответсвие контекста и владельца шаблона
				ActivePerson person = PersonHelper.getContextPerson();
				if (new ClientComparator().compare(client, person.asClient()) == 0)
				{
					return person.getId();
				}
			}

			//иначе определяем владельца по информации из шаблона и ЦСА
			Pair<Profile, List<? extends GUID>> history = ProfileHelper.getStoredProfileHistory(client);
			if (history == null || CollectionUtils.isEmpty(history.getSecond()))
			{
				throw new BusinessException("По клиенту id = " + client.getId() + " пришел пустой список истории изменений профиля.");
			}

			return getInternalOwnerId(history, new Profile(client));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private Long getInternalOwnerId(Pair<Profile, List<? extends GUID>> history, Profile profile) throws BusinessException, BusinessLogicException
	{
		GUIDComparator comparator = new GUIDComparator();
		for (GUID guid : history.getSecond())
		{
			if (comparator.compare(profile, guid) == 0)
			{
				GUID first = history.getFirst();

				ActivePerson person = personService.findByGUID(first);
				if (person != null)
				{
					return person.getId();
				}

				log.error("По ФИО: " + first.getSurName() + " " + first.getFirstName() + " " + first.getPatrName() + ", ДУЛ: "+ first.getPassport() + ", ДР: " + String.format("%1$td.%1$tm.%1$tY", first.getBirthDay()) + ", ТБ: " + first.getTb() + " основного профиля ЦСА не найден клиент в БД ЕРИБ.");
			}
		}
		throw new BusinessLogicException("Выбранный шаблон не принадлежит вам.");
	}

	public EmployeeInfo getCreatedEmployeeInfo()
	{
		return createdEmployeeInfo;
	}

	public void setCreatedEmployeeInfo(EmployeeInfo createdEmployeeInfo)
	{
		this.createdEmployeeInfo = createdEmployeeInfo;
	}

	public EmployeeInfo getConfirmedEmployeeInfo()
	{
		return confirmedEmployeeInfo;
	}

	public void setConfirmedEmployeeInfo(EmployeeInfo confirmedEmployeeInfo)
	{
		this.confirmedEmployeeInfo = confirmedEmployeeInfo;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public Date getChanged()
	{
		return changed;
	}

	public void setChanged(Date changed)
	{
		this.changed = changed;
	}

	public void setFormType(FormType formType)
	{
		this.formType = formType;
	}

	public void setFormType(String formType)
	{
		this.formType = FormType.valueOf(formType);
	}

	public Class<? extends GateDocument> getType()
	{
		try
		{
			return ClassHelper.loadClass(classType);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String getClassType()
	{
		return classType;
	}

	public void setClassType(String classType)
	{
		this.classType = classType;
	}

	public TemplateInfo getTemplateInfo()
	{
		return templateInfo;
	}

	public void setTemplateInfo(TemplateInfo templateInfo)
	{
		this.templateInfo = templateInfo;
	}

	public void setSystemName(String name)
	{

	}

	public String getStateMachineName()
	{
		return stateMachineName;
	}

	public void setStateMachineName(String stateMachineName)
	{
		this.stateMachineName = stateMachineName;
	}

	/**
	 * @param source источник значений
	 * @return строка формата dd.mm.yyyy hh:mm:ss
	 */
	private String getFullDocumentStringDate(FieldValuesSource source)
	{
		return source.getValue(DOCUMENT_DATE_ATTRIBUTE_NAME) + " " + source.getValue(DOCUMENT_TIME_ATTRIBUTE_NAME);
	}

	public byte[] getSignableObject() throws SecurityException, SecurityLogicException
	{
		return new byte[0];
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

	public void setMbOperCode(String mbOperCode)
	{
		setNullSaveAttributeStringValue(MB_OPER_CODE_ATTRIBUTE_NAME, mbOperCode);
	}

	public String getMbOperCode()
	{
		return getNullSaveAttributeStringValue(MB_OPER_CODE_ATTRIBUTE_NAME);
	}

	public Long getSendNodeNumber()
	{
		return getNullSaveAttributeLongValue(NODE_NUMBER_ATTRIBUTE_NAME);
	}

	public void setSendNodeNumber(Long nodeNumber)
	{
		setNullSaveAttributeLongValue(NODE_NUMBER_ATTRIBUTE_NAME, nodeNumber);
	}

	public Long getInternalOwnerId()
	{
		try
		{
			return getClientOwner().getInternalOwnerId();
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String getExternalOwnerId()
	{
		try
		{
			return getClientOwner().getId();
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
	 	//функционал не подерживается.
	}

	public String getPayerName()
	{
		//функционал не подерживается.
		return null;
	}

	public ReminderInfo getReminderInfo()
	{
		return reminderInfo;
	}

	/**
	 * @param reminderInfo информация о напоминании
	 */
	public void setReminderInfo(ReminderInfo reminderInfo)
	{
		this.reminderInfo = reminderInfo;
	}

	public boolean isEinvoicing()
	{
		return false;
	}

	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		//шаблон не должен сохранять эту инфу
		return null;
	}

	public String getClientTransactionId()
	{
		return getNullSaveAttributeStringValue(CLIENT_TRANSACTION_ID);
	}

	public void setClientTransactionId(String transactionId)
	{
		setNullSaveAttributeStringValue(CLIENT_TRANSACTION_ID, transactionId);
	}

	public void setRefusingReason(String refusingReason) {}
}
