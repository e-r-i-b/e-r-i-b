package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
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
import com.rssl.phizic.rsa.integration.ws.control.generated.WSUserType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.builders.AnalyzeRequestBuilderBase;
import com.rssl.phizic.rsa.senders.types.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Базовый билдер запросов шаблоеов платежей (Analyze)
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeTemplateFraudMonitoringRequestBuilderBase<TD extends TemplateDocument> extends AnalyzeRequestBuilderBase
{
	private static final BankDictionaryService bankService = new BankDictionaryService();

	/**
	 * Добавить данные по шаблону
	 * @param template шаблон
	 * @return билдер
	 */
	public abstract AnalyzeTemplateFraudMonitoringRequestBuilderBase append(TD template);

	/**
	 * @return документ
	 */
	protected abstract TD getTemplateDocument();

	/**
	 * @return AccountData счета списания
	 */
	protected abstract AccountData createMyAccountData();

	/**
	 * @return AccountData счета зачисления
	 */
	protected abstract AccountData createOtherAccountData() throws GateException;

	/**
	 * @return тип счета получателя платежа
	 */
	protected abstract AccountPayeeType getAccountPayeeType();

	/**
	 * @return отношение счета получателя к "нашему" банку - счет получателя в нашем \ в другом банке
	 */
	protected abstract BeneficiaryType getBeneficiaryType() throws GateException;

	/**
	 * @return направление, в котором средства передаются
	 */
	protected abstract DirectionTransferFundsType getDirectionTransferFundsType();

	/**
	 * @return способ перевода средств
	 */
	protected abstract ClientDefinedEventType getClientDefinedEventType();

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.ADD_PAYEE;
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

		transactionData.setAmount(getExactAmount());
		if (getTemplateDocument().getAdmissionDate() != null)
		{
			transactionData.setDueDate(XMLDatatypeHelper.formatDateWithoutTimeZone(getTemplateDocument().getAdmissionDate()));
		}

		return transactionData;
	}

	protected ActivePerson getDocumentOwner()
	{
		return PersonHelper.getContextPerson();
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		TemplateDocument template = getTemplateDocument();
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(getClientData())
				.append(getExactAmountData())
				.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(template.getGround()), DataType.STRING)
				.append(DOCUMENT_NUMBER_ATTRIBUTE_NAME, template.getDocumentNumber(), DataType.STRING)
				.append(TEMPLATE_DOCUMENT_NAME_ATTRIBUTE_NAME, template.getTemplateInfo().getName(), DataType.STRING)
				.append(TEMPLATE_DOCUMENT_TYPE_ATTRIBUTE_NAME, StateCode.TEMPLATE.name().equals(template.getState().getCode()) ? TEMPLATE_DOCUMENT_UN_LIMIT_TYPE_ATTRIBUTE_NAME : TEMPLATE_DOCUMENT_DEFAULT_TYPE_ATTRIBUTE_NAME, DataType.STRING)
				.append(OFFLINE_LOAD_FIELD_NAME, NO, DataType.STRING);

		return builder.build();
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
			log.error("По клиенту personId = " + person.getId() + " не найдено ни одного документа.");
			return null;
		}

		PersonDocument document = documents.get(0);
		return new ClientDefinedFactBuilder()
				.append(PERSON_DOCUMENT_NUMBER_FIELD_NAME, (StringHelper.getEmptyIfNull(document.getDocumentSeries()) + " " + StringHelper.getEmptyIfNull(document.getDocumentNumber())).trim(), DataType.STRING)
				.append(PERSON_DOCUMENT_TYPE_FIELD_NAME, document.getDocumentType().name(), DataType.STRING)
				.build();
	}

	protected Amount getExactAmount()
	{
		Money money = getTemplateDocument().getExactAmount();
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
		Money money = getTemplateDocument().getExactAmount();
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
	 * Получение лицевого счета получателя платежа (для бил. документов)
	 * @param template бил. документ
	 * @return лицевой счет
	 * @throws GateException
	 */
	protected String getOtherAccountNumber(TemplateDocument template) throws GateException
	{
		try
		{
			String receiverAccount = template.getReceiverAccount();
			if (StringHelper.isNotEmpty(receiverAccount))
			{
				return receiverAccount;
			}

			List<Field> fields = template.getExtendedFields();
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
