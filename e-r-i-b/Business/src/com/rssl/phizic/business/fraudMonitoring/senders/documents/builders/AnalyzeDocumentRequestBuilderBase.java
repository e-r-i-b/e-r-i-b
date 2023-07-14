package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.builders.AnalyzeRequestBuilderBase;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.types.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Ѕазовый класс билдера Analyze запросов по документам во ¬— ‘ћ
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeDocumentRequestBuilderBase<BD extends BusinessDocument> extends AnalyzeRequestBuilderBase
{
	private static final BankDictionaryService bankService = new BankDictionaryService();

	private boolean IMSI;

	/**
	 * ƒобавить парамметр перечитывании сим карты
	 * @param initializationData initializationData
	 * @return this
	 */
	public AnalyzeDocumentRequestBuilderBase append(InitializationData initializationData)
	{
		IMSI = initializationData.isIMSI();
		return this;
	}

	/**
	 * ƒобавить информацию по документу
	 * @param document документ
	 * @return билдер
	 */
	public abstract AnalyzeDocumentRequestBuilderBase append(BD document);

	/**
	 * @return документ
	 */
	protected abstract BD getBusinessDocument();

	/**
	 * @return AccountData счета зачислени€
	 */
	protected abstract AccountData createOtherAccountData() throws GateException;

	/**
	 * @return AccountData счета списани€
	 */
	protected abstract AccountData createMyAccountData();

	/**
	 * @return тип счета получател€ платежа
	 */
	protected abstract AccountPayeeType getAccountPayeeType();

	/**
	 * @return отношение счета получател€ к "нашему" банку - счет получател€ в нашем \ в другом банке
	 */
	protected abstract BeneficiaryType getBeneficiaryType() throws GateException;

	/**
	 * @return направление, в котором средства передаютс€
	 */
	protected abstract DirectionTransferFundsType getDirectionTransferFundsType();

	/**
	 * @return направление, в котором средства передаютс€
	 */
	protected abstract ExecutionPeriodicityType getExecutionPeriodicityType();

	/**
	 * @return способ перевода средств
	 */
	protected abstract WayTransferOfFundsType getWayTransferOfFundsType() throws GateException;

	/**
	 * @return способ перевода средств
	 */
	protected abstract ClientDefinedEventType getClientDefinedEventType() throws GateException;

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.PAYMENT;
	}

	protected IdentificationData createIdentificationData()
	{
		AuthenticationContext context = AuthenticationContext.getContext();

		IdentificationData identificationData = new IdentificationData();
		identificationData.setUserLoginName(context.getUserAlias());
		identificationData.setOrgName(context.getTB());
		identificationData.setUserType(WSUserType.PERSISTENT);
		identificationData.setUserStatus(UserStatus.VERIFIED);

		Long profileId = FraudMonitoringRequestHelper.getUserName(context);
		if (profileId != null)
		{
			identificationData.setUserName(Long.toString(profileId));
		}

		ActivePerson person = PersonHelper.getContextPerson();
		identificationData.setClientTransactionId(FraudMonitoringRequestHelper.generateAndStoreClientTransactionId(profileId, person.getId(), person.getLogin().getId()));
		return identificationData;
	}

	@Override
	protected EventData createEventData() throws GateException, GateLogicException
	{
		EventData eventData = super.createEventData();
		ClientDefinedEventType clientDefinedEventType = getClientDefinedEventType();
		if (clientDefinedEventType != null)
		{
			eventData.setClientDefinedEventType(clientDefinedEventType.getType());
			eventData.setEventDescription(clientDefinedEventType.getDescription());
		}

		eventData.setTransactionData(createTransactionalData());
		eventData.setClientDefinedAttributeList(createClientDefinedAttributeList());
		return eventData;
	}

	protected TransactionData createTransactionalData() throws GateException
	{
		TransactionData transactionData = new TransactionData();

		WayTransferOfFundsType wayTransferOfFundsType = getWayTransferOfFundsType();
		if (wayTransferOfFundsType != null)
		{
			transactionData.setTransferMediumType(TransferMediumType.fromValue(wayTransferOfFundsType.name()));
		}

		ExecutionPeriodicityType executionPeriodicityType = getExecutionPeriodicityType();
		if (executionPeriodicityType != null)
		{
			transactionData.setSchedule(Schedule.fromValue(executionPeriodicityType.name()));
			transactionData.setExecutionSpeed(getExecutionSpeed(executionPeriodicityType));
		}

		AccountPayeeType accountPayeeType = getAccountPayeeType();
		if (accountPayeeType != null)
		{
			transactionData.setOtherAccountType(OtherAccountType.fromValue(accountPayeeType.name()));
		}

		BeneficiaryType beneficiaryType = getBeneficiaryType();
		if (beneficiaryType != null)
		{
			transactionData.setOtherAccountBankType(OtherAccountBankType.fromValue(beneficiaryType.name()));
		}

		DirectionTransferFundsType directionTransferFundsType = getDirectionTransferFundsType();
		if (directionTransferFundsType != null)
		{
			transactionData.setOtherAccountOwnershipType(OtherAccountOwnershipType.fromValue(directionTransferFundsType.name()));
		}

		AccountData myAccountData = createMyAccountData();
		if (myAccountData != null)
		{
			transactionData.setMyAccountData(myAccountData);
		}

		AccountData otherAccountData = createOtherAccountData();
		if (otherAccountData != null)
		{
			transactionData.setOtherAccountData(otherAccountData);
		}

		Amount amount = getAmount();
		if (amount != null)
		{
			transactionData.setAmount(amount);
		}

		if (getBusinessDocument().getAdmissionDate() != null)
		{
			transactionData.setDueDate(XMLDatatypeHelper.formatDateWithoutTimeZone(getBusinessDocument().getAdmissionDate()));
		}

		return transactionData;
	}

	protected ExecutionSpeed getExecutionSpeed(ExecutionPeriodicityType executionPeriodicityType)
	{
		switch (executionPeriodicityType)
		{
			case SCHEDULED : return ExecutionSpeed.FEW_HOURS;
			case RECURRING : return ExecutionSpeed.SEVERAL_DAYS;
			case IMMEDIATE : return ExecutionSpeed.REAL_TIME;
			default : return null;
		}
	}

	protected ActivePerson getDocumentOwner()
	{
		return PersonHelper.getContextPerson();
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		BusinessDocument document = getBusinessDocument();
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(getClientData())
				.append(getExactAmountData())
				.append(DOCUMENT_NUMBER_ATTRIBUTE_NAME, document.getDocumentNumber(), DataType.STRING)
				.append(IS_BY_TEMPLATE_DOCUMENT_ATTRIBUTE_NAME, document.isByTemplate() ? YES : NO, DataType.STRING)
				.append(OFFLINE_LOAD_FIELD_NAME, NO, DataType.STRING)
				.append(createIMSIDefinedFactData());

		if (document.isByTemplate())
		{
			builder.append(getByTemplateData());
		}

		return builder.build();
	}

	protected ClientDefinedFact[] createIMSIDefinedFactData() throws GateException
	{
		if (IMSI)
		{
			return new ClientDefinedFactBuilder()
					.append(IS_IMSI_CHECK_ATTRIBUTE_NAME, YES, DataType.STRING)
					.build();
		}
		return null;
	}

	/**
	 * @return вернуть доп. информацию по клиенту
	 */
	protected ClientDefinedFact[] getClientData() throws GateException, GateLogicException
	{
		ActivePerson person = getDocumentOwner();
		return new ClientDefinedFactBuilder()
				.append(getPersonFIO(person))
				.append(getPersonDocument(person))
				.build();
	}

	protected ClientDefinedFact[] getPersonFIO(ActivePerson person) throws GateException
	{
		try
		{
			return new ClientDefinedFactBuilder()
					.append(PERSON_FIO_FIELD_NAME, StringHelper.getEmptyIfNull(person.getFIO()), DataType.STRING)
					.append(PERSON_SURNAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(person.getSurName()), DataType.STRING)
					.append(PERSON_FIRST_NAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(person.getFirstName()), DataType.STRING)
					.append(PERSON_PATRNAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(person.getPatrName()), DataType.STRING)
					.append(PERSON_BIRTHDAY_FIELD_NAME, String.format("%1$td/%1$tm/%1$tY", person.getBirthDay()), DataType.STRING)
					.append(PERSON_VSP_NUMBER_FIELD_NAME, StringHelper.getEmptyIfNull(PersonHelper.getPersonOSBVSP(person)), DataType.STRING)
					.build();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected ClientDefinedFact[] getPersonDocument(ActivePerson person) throws GateException, GateLogicException
	{
		List<PersonDocument> documents = PersonHelper.getDocumentForProfile(person.getPersonDocuments());
		if (CollectionUtils.isEmpty(documents))
		{
			log.error("ѕо клиенту personId = " + person.getId() + " не найдено ни одного документа.");
			return null;
		}

		PersonDocument document = documents.get(0);
		return new ClientDefinedFactBuilder()
				.append(PERSON_DOCUMENT_NUMBER_FIELD_NAME, (StringHelper.getEmptyIfNull(document.getDocumentSeries()) + " " + StringHelper.getEmptyIfNull(document.getDocumentNumber())).trim(), DataType.STRING)
				.append(PERSON_DOCUMENT_TYPE_FIELD_NAME, document.getDocumentType().name(), DataType.STRING)
				.build();
	}

	/**
	 * @return вернуть доп. информацию по шаблону
	 */
	protected ClientDefinedFact[] getByTemplateData() throws GateException, GateLogicException
	{
		BusinessDocument document = getBusinessDocument();
		if (!document.isByTemplate())
		{
			return null;
		}

		try
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
			return new ClientDefinedFactBuilder()
					.append(TEMPLATE_DOCUMENT_NAME_ATTRIBUTE_NAME, template.getTemplateInfo().getName(), DataType.STRING)
					.append(TEMPLATE_DOCUMENT_TYPE_ATTRIBUTE_NAME, StateCode.TEMPLATE.name().equals(template.getState().getCode()) ? TEMPLATE_DOCUMENT_UN_LIMIT_TYPE_ATTRIBUTE_NAME : TEMPLATE_DOCUMENT_DEFAULT_TYPE_ATTRIBUTE_NAME, DataType.STRING)
					.build();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected Amount getAmount()
	{
		Money money = getBusinessDocument().getExactAmount();
		if (money == null)
		{
			return null;
		}

		return new Amount(money.getAsCents(), null, money.getCurrency().getCode());
	}

	/**
	 * @return вернуть доп. информацию по клиенту
	 */
	protected ClientDefinedFact[] getExactAmountData()
	{
		Money money = getBusinessDocument().getExactAmount();
		if (money == null)
		{
			return null;
		}

		return new ClientDefinedFactBuilder()
				.append(AMOUNT_FIELD_NAME, Long.valueOf(money.getAsCents()).toString(), DataType.STRING)
				.append(AMOUNT_CURRENCY_FIELD_NAME, money.getCurrency().getCode(), DataType.STRING)
				.build();
	}

	protected boolean checkAccess(String operationKey, String serviceKey) throws GateException
	{
		try
		{
			return new OperationFactoryImpl(new RestrictionProviderImpl()).checkAccess(operationKey, serviceKey);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected BeneficiaryType getBeneficiaryType(ResidentBank receiverBank) throws GateException
	{
		if (receiverBank == null)
		{
			return null;
		}

		return getBeneficiaryType(receiverBank.getBIC());
	}

	protected BeneficiaryType getBeneficiaryType(String bic) throws GateException
	{
		try
		{
			ResidentBank residentBank = bankService.findByBIC(bic);
			if (residentBank == null)
			{
				return null;
			}
			return BooleanUtils.toBoolean(residentBank.isOur()) ? BeneficiaryType.SAME_BANK : BeneficiaryType.OTHER_BANK;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected WayTransferOfFundsType getWayTransferOfFundsType(ResidentBank receiverBank) throws GateException
	{
		if (receiverBank == null)
		{
			return null;
		}

		return getWayTransferOfFundsType(receiverBank.getBIC());
	}

	protected WayTransferOfFundsType getWayTransferOfFundsType(String bic) throws GateException
	{
		try
		{
			ResidentBank residentBank = bankService.findByBIC(bic);
			if (residentBank == null)
			{
				return null;
			}
			return BooleanUtils.toBoolean(residentBank.isOur()) ? WayTransferOfFundsType.INTERNAL : WayTransferOfFundsType.WIRE;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected String getExtendedFieldValue(Field field)
	{
		if (field.getValue() == null)
		{
			return StringUtils.EMPTY;
		}

		return field.getValue().toString();
	}

	/**
	 * ѕолучение лицевого счета получател€ платежа (дл€ бил. документов)
	 * @param rurPayment бил. документ
	 * @return лицевой счет
	 * @throws GateException
	 */
	protected String getOtherAccountNumber(RurPayment rurPayment) throws GateException
	{
		try
		{
			String receiverAccount = rurPayment.getReceiverAccount();
			if (StringHelper.isNotEmpty(receiverAccount))
			{
				return receiverAccount;
			}

			List<Field> fields = rurPayment.getExtendedFields();
			if (CollectionUtils.isEmpty(fields))
			{
				return null;
			}

			boolean empty = true;
			StringBuilder builder = new StringBuilder();

			for (Field field : fields)
			{
				if (field.isKey())
				{
					if (!empty)
					{
						builder.append(DELIMITER);
					}
					builder.append(getExtendedFieldValue(field));
					empty = false;
				}
			}
			return builder.toString();
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	protected ClientDefinedFact getBICReceiverAccountClientDefinedFact(String bic, String receiverAccount)
	{
		String value = StringHelper.getEmptyIfNull(bic) + AMPERSAND_ATTRIBUTE + StringHelper.getEmptyIfNull(receiverAccount);
		return new ClientDefinedFact(RECEIVER_BIC_COR_ACCOUNT_ATTRIBUTE_NAME, value, DataType.STRING);
	}

	protected ClientDefinedFact getClientBICReceiverAccountClientDefinedFact(Long id, String bic, String receiverAccount)
	{
		String value = id + AMPERSAND_ATTRIBUTE + StringHelper.getEmptyIfNull(bic) + AMPERSAND_ATTRIBUTE + StringHelper.getEmptyIfNull(receiverAccount);
		return new ClientDefinedFact(RECEIVER_CLIENT_BIC_COR_ACCOUNT_ATTRIBUTE_NAME, value, DataType.STRING);
	}

}
