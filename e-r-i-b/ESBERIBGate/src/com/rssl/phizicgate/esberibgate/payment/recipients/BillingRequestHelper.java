package com.rssl.phizicgate.esberibgate.payment.recipients;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.DelayInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.RecoveryInvoiceSubscription;
import com.rssl.phizic.gate.payments.longoffer.*;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.owner.PersonName;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.autopayments.AutoSubscriptionsRequestHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.payment.AutoSubscriptionHelper;
import com.rssl.phizicgate.esberibgate.types.*;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * @author krenev
 * @ created 30.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class BillingRequestHelper extends PaymentsRequestHelper
{
	public BillingRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Сформировать запрос BillingPayPrepRq по данным платежа
	 * @param payment платеж
	 * @return BillingPayPrepRq
	 */
	public BillingPayPrepRq_Type createBillingPayPrepRq(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		BillingPayPrepRq_Type result = fillBillingPayPrepRqHeader(payment);
		result.setRecipientRec(createRecipientRec(payment, false));
		return result;
	}

	/**
	 * Сформировать запрос BillingPayExecRq по данным платежа(без информации об источнике списания)
	 * @param payment платеж
	 * @return BillingPayExecRq
	 */
	public BillingPayExecRq_Type createBillingPayExecRq(CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		BillingPayExecRq_Type result = fillBillingPayExecRqHeader(payment.getInternalOwnerId(), payment.getBillingCode());
		result.setSBOLTm(getStringDateTime(payment.getClientCreationDate()));
		result.setSBOLUID(payment.getOperationUID());
		result.setRecipientRec(createRecipientRec(payment, true));
		Money commission = payment.getCommission();
		result.setCommission(commission.getDecimal());
		result.setCommissionCur(commission.getCurrency().getCode());
		result.setMadeOperationId(payment.getIdFromPaymentSystem());
		result.setOperUID(payment.getOperationUID());
		return result;
	}

	protected BillingPayExecRq_Type fillBillingPayExecRqHeader(Long loginId, String systemId) throws GateLogicException, GateException
	{
		BillingPayExecRq_Type result = new BillingPayExecRq_Type();
		result.setRqUID(BillingRequestHelper.generateUUID());
		result.setRqTm(BillingRequestHelper.generateRqTm());
		result.setSPName(SPName_Type.BP_ERIB);
		result.setSystemId(ExternalSystemHelper.getCode(systemId));
		result.setBankInfo(createAuthBankInfo(loginId));
		return result;
	}

	/**
	 * Сформитровать структуру BillingPayPrepRq и заполнить заголовок
	 * @param payment - платеж
	 * @return BillingPayPrepRq c заполненным заголовком.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private BillingPayPrepRq_Type fillBillingPayPrepRqHeader(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		BillingPayPrepRq_Type result = new BillingPayPrepRq_Type();
		result.setRqUID(BillingRequestHelper.generateUUID());
		result.setRqTm(BillingRequestHelper.generateRqTm());
		result.setOperUID(payment.getOperationUID());
		result.setSPName(SPName_Type.BP_ERIB);
		result.setSystemId(ExternalSystemHelper.getCode(payment.getBillingCode()));
		result.setIsTemplate(payment.isTemplate());
		result.setBankInfo(generateBankInfo(payment));

		return result;
	}

	/**
	 * Заполнить структуру RecipientRec данными поставщика, без доп полей.
	 * @param recipient информация о поставщике
	 * @param recipientInfo расширенная информация о поставщике.
	 * @return RecipientRec.
	 */
	public RecipientRec_Type createRecipientRec(Recipient recipient, RecipientInfo recipientInfo)
	{
		RecipientRec_Type result = new RecipientRec_Type();
		result.setCodeRecipientBS(getCodeRecipientBS(recipient.getSynchKey().toString()));
		result.setName(recipient.getName());
		result.setCodeService(recipient.getService().getCode());
		result.setNameService(recipient.getService().getName());
		result.setTaxId(recipientInfo.getINN());
		result.setKPP(recipientInfo.getKPP());
		result.setAcctId(recipientInfo.getAccount());
		ResidentBank bank = recipientInfo.getBank();
		if (bank != null)
		{
			result.setCorrAccount(bank.getAccount());
			result.setBIC(bank.getBIC());
		}
		return result;
	}

	/**
	 * Заполнить структуру RecipientRec данными получателе из платежа, включая доп пля
	 * @param payment платеж
	 * @param needBankInfo признак необходимости включать в информацию об офисе
	 * @return RecipientRec.
	 */
	public RecipientRec_Type createRecipientRec(CardPaymentSystemPayment payment, boolean needBankInfo) throws GateException
	{
		RecipientRec_Type result = new RecipientRec_Type();
		result.setCodeRecipientBS(getCodeRecipientBS(payment.getReceiverPointCode()));
		result.setName(payment.getReceiverName());
		Service service = payment.getService();
		result.setCodeService(service.getCode());
		result.setNameService(service.getName());
		result.setTaxId(payment.getReceiverINN());
		result.setKPP(payment.getReceiverKPP());
		result.setAcctId(payment.getReceiverAccount());
		ResidentBank bank = payment.getReceiverBank();
		result.setCorrAccount(bank.getAccount());
		result.setBIC(bank.getBIC());
		if (needBankInfo)
		{
			Code officeCode = payment.getReceiverOfficeCode();
			if (officeCode == null)
			{
				throw new GateException("Неизвестен офис, в котором заключен договор с получателем");
			}
			result.setBankInfo(getBankInfo(officeCode, null, null));
		}
		try
		{
			result.setRequisites(createRequisites(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		return result;
	}

	/**
	 * Сформировать список описаний полей.
	 * @param payment платеж.
	 * @return Requisites
	 */
	public Requisite_Type[] createRequisites(CardPaymentSystemPayment payment) throws DocumentException
	{
		List<Field> fields = payment.getExtendedFields();
		if (CollectionUtils.isEmpty(fields))
		{
			return null;
		}
		Map<String, Field> nameToField = new HashMap<String, Field>();
		for (Field keyField: fields)
		{
			nameToField.put(keyField.getExternalId(), keyField);
		}

		//Для заявок на автоплатеж нужно восстановить поле главной суммы из дополнительного поля MAIN_SUM_FIELD_NAME_FIELD.
		String mainSumFieldName = nameToField.containsKey(BillingPaymentHelper.MAIN_SUM_FIELD_NAME_FIELD) ?
				(String) nameToField.get(BillingPaymentHelper.MAIN_SUM_FIELD_NAME_FIELD).getValue() : null;

		Field mainSumField = nameToField.get(mainSumFieldName);

		Requisite_Type[] requisites = new Requisite_Type[StringHelper.isEmpty(mainSumFieldName) ? fields.size() : fields.size() - 1];
		int i = 0;
		for (Field keyField : fields)
		{
			if (!BillingPaymentHelper.MAIN_SUM_FIELD_NAME_FIELD.equals(keyField.getExternalId()))
			{
				Requisite_Type requisite = createRequisite(keyField);
				if (mainSumField == keyField)
					requisite.setIsSum(true);
				requisites[i++] = requisite;
			}
		}
		return requisites;
	}

	/**
	 * сформировать описание поля
	 * @param field гейтовое представление поля
	 * @return транспортное представление поля или null при отсутсвии.
	 */
	public Requisite_Type createRequisite(Field field)
	{
		if (field == null)
		{
			return null;
		}
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible(field.getName());
		result.setNameBS(field.getExternalId());
		result.setDescription(field.getHint());
		result.setComment(field.getDescription());
		result.setType(field.getType().name());
		if (field.getType() == FieldDataType.number)
		{
			NumberField numberField = (NumberField) field;
			result.setNumberPrecision(convertToBigInteger(numberField.getNumberPrecision()));
		}
		result.setIsRequired(field.isRequired());
		result.setIsSum(field.isMainSum());
		result.setIsKey(field.isKey());
		result.setIsEditable(field.isEditable());
		result.setIsVisible(field.isVisible());
		result.setIsForBill(field.isRequiredForBill());
		result.setIncludeInSMS(field.isRequiredForConformation());
		result.setSaveInTemplate(field.isSaveInTemplate());
		result.setHideInConfirmation(field.isHideInConfirmation());

		List<RequisiteType> requisiteTypes = field.getRequisiteTypes();
		if (CollectionUtils.isNotEmpty(requisiteTypes))
		{
			String[] requisiteTypeArray = new String[requisiteTypes.size()];
			int i = 0;
			for (RequisiteType requisiteType : requisiteTypes)
			{
				requisiteTypeArray[i++] = requisiteType.getDescription();
			}
			result.setRequisiteTypes(requisiteTypeArray);
		}

		if (field.getMaxLength() != null || field.getMinLength() != null)
		{
			result.setAttributeLength(new AttributeLength_Type(convertToBigInteger(field.getMaxLength()), convertToBigInteger(field.getMinLength())));
		}
		result.setValidators(createValidators(field.getFieldValidationRules()));
		if (field.getType() == FieldDataType.set || field.getType() == FieldDataType.list)
		{
			ListField listField = (ListField) field;
			List<ListValue> values = listField.getValues();
			if (CollectionUtils.isNotEmpty(values))
			{
				String[] menu = new String[values.size()];
				int i = 0;
				for (ListValue listValue : listField.getValues())
				{
					menu[i++] = listValue.getId();
				}
				result.setMenu(menu);
			}
		}
		result.setEnteredData(createEnteredData(field));
		result.setDefaultValue(field.getDefaultValue());
		//error не передается.
		return result;
	}

	/**
	 * сформировать список валидаторов
	 * @param fieldValidationRules гейтовое представление валидаторов
	 * @return транспортный список валидаторов или null при отсутсвии.
	 */
	public Validator_Type[] createValidators(List<FieldValidationRule> fieldValidationRules)
	{
		if (CollectionUtils.isEmpty(fieldValidationRules))
		{
			return null;
		}
		Validator_Type[] result = new Validator_Type[fieldValidationRules.size()];
		int i = 0;
		for (FieldValidationRule fieldValidationRule : fieldValidationRules)
		{
			result[i++] = createFieldValidator(fieldValidationRule);
		}
		return result;
	}

	/**
	 * сформировать структуру Validator
	 * @param fieldValidationRule - гейтовое описание валидатора поля
	 * @return транспортное представление валидатора или null при отсутсвии.
	 */
	public Validator_Type createFieldValidator(FieldValidationRule fieldValidationRule)
	{
		if (fieldValidationRule == null)
		{
			return null;
		}
		if (fieldValidationRule.getFieldValidationRuleType() != FieldValidationRuleType.REGEXP)
		{
			throw new IllegalArgumentException("Поддерживается только тип REGEXP. пришел:" + fieldValidationRule.getFieldValidationRuleType());
		}
		Validator_Type result = new Validator_Type();
		result.setType("regexp");
		result.setMessage(fieldValidationRule.getErrorMessage());
		Map<String, Object> parameters = fieldValidationRule.getParameters();
		if (parameters == null || parameters.size() != 1)
		{
			throw new IllegalArgumentException("Список параметров для REGEXP валидатора должен состоять ровно из 1 значения");
		}
		result.setParameter(String.valueOf(parameters.values().iterator().next()));
		return result;
	}

	/**
	 * сформировать массив значений для поля EnteredData
	 * @param field поле
	 * @return готовый для передачи массив значений
	 */
	private String[] createEnteredData(Field field)
	{
		if (field.getValue() == null)
		{
			return null;
		}
		FieldDataType type = field.getType();
		if (type == FieldDataType.set)
		{
			//значения для типа set передаются в виде листа данных
			List<String> value = Arrays.asList(((String) field.getValue()).split("@"));
			return value.toArray(new String[value.size()]);
		}
		return new String[]{String.valueOf(field.getValue())};
	}

	private BigInteger convertToBigInteger(Long number)
	{
		if (number == null)
		{
			return null;
		}
		return BigInteger.valueOf(number);
	}

	private BigInteger convertToBigInteger(Integer number)
	{
		if (number == null)
		{
			return null;
		}
		return BigInteger.valueOf(number.longValue());
	}


	private static final char SERVICE_DELIMITER = '@';
	private static final char ADAPTER_DELIMITER = '|';
	/**
	 * получить из идентикатора получателя идентфикатор поставщика в БС
	 * в интегрированном шлюзе не устанавливается песистентная прослойка.
	 * поля передаеются как есть из БД <код услуги>@<код поставщика>|<код адаптера>
	 * получаем из такой строки код поставщика
	 * @param recipientId код поставщика
	 * @return идентфикатор поставщика в БС
	 */
	private String getCodeRecipientBS(String recipientId)
	{
		if (StringHelper.isEmpty(recipientId))
		{
			return null;
		}
		int end = recipientId.indexOf(ADAPTER_DELIMITER);
		if (end < 0)
		{
			//когда оплата происходит по реквизитам, идентификатор приходит из шины и для получателя нет идентификатра адаптера.
			end = recipientId.length();
		}
		return recipientId.substring(recipientId.indexOf(SERVICE_DELIMITER)+1, end);
	}

	public BillingPayInqRq_Type createBillingPayInqRq(CardPaymentSystemPayment payment) throws GateLogicException, GateException
	{
		BillingPayInqRq_Type result = new BillingPayInqRq_Type();
		result.setRqUID(BillingRequestHelper.generateUUID());
		result.setRqTm(BillingRequestHelper.generateRqTm());
		result.setOperUID(BillingRequestHelper.generateOUUID()); 
		result.setSPName(SPName_Type.BP_ERIB);
		result.setBankInfo(createAuthBankInfo(payment.getInternalOwnerId()));

		// данные о получателе
		RecipientRec_Type recipientRec = new RecipientRec_Type();
		recipientRec.setTaxId(payment.getReceiverINN());
		recipientRec.setAcctId(payment.getReceiverAccount());

		ResidentBank bank = payment.getReceiverBank();
		recipientRec.setCorrAccount(bank.getAccount());
		recipientRec.setBIC(bank.getBIC());

		result.setRecipientRec(recipientRec);
		result.setIsTemplate(payment.isTemplate());
		return result;
	}

	/**
	 * Запрос на создание/изменение подписки формирет и напрявляет ЕРИБ в КСШ для предачи в АС Автоплатежи
	 * @param payment
	 * @return
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private AutoSubscriptionModRq_Type createAutoSubscriptionModRq(CardPaymentSystemPaymentLongOffer payment, Client owner, Card card, Office office) throws GateLogicException, GateException
	{
		AutoSubscriptionModRq_Type request = new AutoSubscriptionModRq_Type();
		request.setRqUID(BillingRequestHelper.generateUUID());
		request.setRqTm(BillingRequestHelper.generateRqTm());
		//result.setOperUID(BillingRequestHelper.generateOUUID()); В настоящее время СБОЛ не передает данное поле.
		request.setSPName(SPName_Type.BP_ERIB);
		request.setBankInfo(generateBankInfo(payment));

		AutoSubscriptionRec_Type autoSubRec_type = new AutoSubscriptionRec_Type();
		autoSubRec_type.setAutoSubscriptionInfo(createAutoSubscriptionInfo(payment, office));
		autoSubRec_type.setAutoPaymentTemplate(createAutoPaymentTemplate(payment, owner, card));

		request.setMBCInfo(new MBCInfo_Type(payment.isConnectChargeOffResourceToMobileBank(), null, null));
		request.setExecute(payment.isExecutionNow());
		request.setNeedConfirmation(payment.isNeedConfirmation());
		request.setAutoSubscriptionRec(autoSubRec_type);

		return request;
	}

	private AutoSubscriptionId_Type createAutoSubscriptionId(AutoSubscriptionClaim payment) throws GateException
	{
		AutoSubscriptionId_Type autoSubId_type = new AutoSubscriptionId_Type();
		autoSubId_type.setAutopayId(Long.parseLong(payment.getNumber()));
		autoSubId_type.setSystemId(ExternalSystemHelper.getCode(AutoSubscriptionsRequestHelper.SYSTEM_ID));
		return autoSubId_type;
	}

	private AutoSubscriptionInfo_Type createAutoSubscriptionInfo(CardPaymentSystemPaymentLongOffer payment, Office office) throws GateException
	{
		AutoSubscriptionInfo_Type autoSubInfo_type = createAutoSubscriptionInfo(payment);
		autoSubInfo_type.setAutopayName(payment.getFriendlyName());

		ExecutionEventType eventType = payment.getExecutionEventType();
		SumType sumType = payment.getSumType();

		autoSubInfo_type.setExeEventCode(ExeEventCodeASAP_Type.fromValue(eventType.toString()));
		autoSubInfo_type.setSummaKindCode(SummaKindCodeASAP_Type.fromValue(sumType.toString()));
		autoSubInfo_type.setStartDate(getStringDateTime(payment.getCreateDate()));

		// если регулярный автоплатеж
		if (ExecutionEventType.isPeriodic(eventType))
		{
			autoSubInfo_type.setNextPayDate(getStringDateTime(payment.getNextPayDate()));
			// если автоплатеж на фиксированную сумму
			if(sumType == SumType.FIXED_SUMMA_IN_RECIP_CURR )
				autoSubInfo_type.setCurAmt(payment.getAmount().getDecimal());
			// если автоплатеж по выставленному счету
			else if (sumType == SumType.BY_BILLING && payment.getMaxSumWritePerMonth() != null)
				autoSubInfo_type.setMaxSumWritePerMonth(payment.getMaxSumWritePerMonth().getDecimal());
		}
		// если пороговый платеж
		else if(SumType.FIXED_SUMMA_IN_RECIP_CURR == sumType && ExecutionEventType.ON_REMAIND == eventType)
		{
			autoSubInfo_type.setIrreducibleAmt(payment.getFloorLimit().getDecimal());
			autoSubInfo_type.setCurAmt(payment.getAmount().getDecimal());
		}
		else
		{
			throw new GateException("Не известный тип автоплатежа sumType = " + sumType +  " eventType = " + eventType);
		}

		autoSubInfo_type.setChannelType(Channel_Type.fromString(payment.getChannelType().toString()));

		if(autoSubInfo_type.getChannelType().equals(Channel_Type.US))
		{
			//Номер устройства самообслуживания, через которое регистрируется подписка
			autoSubInfo_type.setSPNum(payment.getCodeATM());
		}

		Code officeCode = office.getCode();
		if (officeCode == null)
		{
			throw new GateException("Неизвестен офис, в котором заключен договор с получателем");
		}

		autoSubInfo_type.setBankInfo(getBankInfo(officeCode, null, null));
		autoSubInfo_type.setAutopayNumber(payment.getAutopayNumber());

		return autoSubInfo_type;
	}

	private AutoPaymentTemplate_Type createAutoPaymentTemplate(CardPaymentSystemPaymentLongOffer payment, Client owner, Card card) throws GateException, GateLogicException
	{
		AutoPaymentTemplate_Type autoPayTemp_type = new AutoPaymentTemplate_Type();

		autoPayTemp_type.setSystemId(payment.getBillingCode());

		RecipientRec_Type recipientRec = createRecipientRec(payment, true);
		recipientRec.setGroupService(payment.getGroupService());
		autoPayTemp_type.setRecipientRec(recipientRec);

		//на первом этапе только один иструмент, с которого будет производится оплата
		BankAcctRec_Type bankAcctRec_type = new BankAcctRec_Type();
		CardAcctId_Type cardAcctId = createCardAcctIdForAutoPaymentTemplate(card, owner, payment.getChargeOffCardExpireDate(), true, true);
		fillCardAccIdAdditionalInfo(cardAcctId, card, false);
		bankAcctRec_type.setCardAcctId(cardAcctId);
		autoPayTemp_type.setBankAcctRec(new BankAcctRec_Type[]{bankAcctRec_type});

		return autoPayTemp_type;
	}


	public AutoSubscriptionModRq_Type createAutoSubscriptionModRq(CardPaymentSystemPaymentLongOffer payment, Client owner, Card card) throws GateLogicException, GateException
	{
		return createAutoSubscriptionModRq(payment, owner, card, card.getOffice());
	}

	public AutoSubscriptionModRq_Type createEditAutoSubscriptionModRq(EditCardPaymentSystemPaymentLongOffer payment, Client owner, Card card) throws GateLogicException, GateException
	{
		AutoSubscriptionModRq_Type result = createAutoSubscriptionModRq(payment, owner, card, card.getOffice());

		AutoSubscriptionRec_Type autoSubRec_type = result.getAutoSubscriptionRec();
		autoSubRec_type.setAutoSubscriptionId(createAutoSubscriptionId(payment));

		AutoSubscriptionInfo_Type autoSubscriptionInfo = autoSubRec_type.getAutoSubscriptionInfo();
		autoSubscriptionInfo.setUpdateDate(getStringDateTime(payment.getUpdateDate()));

		return result;
	}

	public AutoSubscriptionModRq_Type createEmployeeAutoSubscriptionModRq(EmployeeCardPaymentSystemPaymentLongOffer payment, Client owner, Card card) throws GateLogicException, GateException
	{
		AutoSubscriptionModRq_Type request = createAutoSubscriptionModRq(payment, owner, card, payment.getCreatedEmployeeInfo().getEmployeeOffice());
		request.setEmployeeOfTheVSP(createEmployeeOfTheVSP(payment));
		return request;
	}

	public AutoSubscriptionModRq_Type createEmployeeEditAutoSubscriptionModRq(EmployeeEditCardPaymentSystemPaymentLongOffer payment, Client owner, Card card) throws GateLogicException, GateException
	{
		AutoSubscriptionModRq_Type result = createAutoSubscriptionModRq(payment, owner, card, payment.getCreatedEmployeeInfo().getEmployeeOffice());

		AutoSubscriptionRec_Type autoSubRec_type = result.getAutoSubscriptionRec();
		autoSubRec_type.setAutoSubscriptionId(createAutoSubscriptionId(payment));

		AutoSubscriptionInfo_Type autoSubscriptionInfo = autoSubRec_type.getAutoSubscriptionInfo();
		autoSubscriptionInfo.setUpdateDate(getStringDateTime(payment.getUpdateDate()));

		result.setEmployeeOfTheVSP(createEmployeeOfTheVSP(payment));
		return result;
	}

	private EmployeeOfTheVSP_Type createEmployeeOfTheVSP(CardPaymentSystemPaymentLongOffer payment) throws GateException
	{
		EmployeeInfo employeeInfo = payment.getConfirmedEmployeeInfo();
		PersonName personName = employeeInfo.getPersonName();
		PersonName_Type personName_type = new PersonName_Type(personName.getLastName(), personName.getFirstName(), personName.getMiddleName(), null);
		BankInfo_Type bankInfo_type = getBankInfo(employeeInfo.getEmployeeOffice(), null, null);

		return new EmployeeOfTheVSP_Type(employeeInfo.getLogin(), personName_type, bankInfo_type);
	}

	public AutoSubscriptionStatusModRq_Type createAutoSubscriptionStatusModRq(AutoSubscriptionClaim longOffer) throws GateLogicException, GateException
	{
		AutoSubscriptionStatusModRq_Type request = new AutoSubscriptionStatusModRq_Type();

		request.setRqUID(BillingRequestHelper.generateUUID());
		request.setRqTm(BillingRequestHelper.generateRqTm());
		//result.setOperUID(BillingRequestHelper.generateOUUID()); В настоящее время СБОЛ не передает данное поле.
		request.setSPName(SPName_Type.BP_ERIB);

		if (!(longOffer instanceof CardToAccountLongOffer))
			request.setBankInfo(createAuthBankInfo(getRbTbBrch(longOffer.getInternalOwnerId())));

		AutoSubscriptionRec_Type autoSubRec_type = new AutoSubscriptionRec_Type();
		autoSubRec_type.setAutoSubscriptionInfo(createAutoSubscriptionInfo(longOffer));
		autoSubRec_type.setAutoSubscriptionId(createAutoSubscriptionId(longOffer));

		request.setAutoSubscriptionRec(autoSubRec_type);
		request.setActionType(getActionType(longOffer.getType()));
		request.setNeedConfirmation(longOffer.isNeedConfirmation());
		request.setChannelType(Channel_Type.fromString(longOffer.getChannelType().toString()));
		request.setMBCInfo(new MBCInfo_Type(longOffer.isConnectChargeOffResourceToMobileBank(), null, null));

		return request;
	}

	private AutoSubscriptionInfo_Type createAutoSubscriptionInfo(AutoSubscriptionClaim payment)
	{
		AutoSubscriptionInfo_Type infoType = new AutoSubscriptionInfo_Type();
		infoType.setRequestId(payment.getOperationUID());
		return infoType;
	}

	public AutoSubscriptionStatusModRq_Type createEmployeeAutoSubscriptionStatusModRq (EmployeeCardPaymentSystemPaymentLongOffer payment) throws GateLogicException, GateException
	{
		AutoSubscriptionStatusModRq_Type request = createAutoSubscriptionStatusModRq(payment);
		request.setEmployeeOfTheVSP(createEmployeeOfTheVSP(payment));
		return request;
	}

	private ChangeStatusAction_Type getActionType(Class<? extends GateDocument> type) throws GateException
	{
		//приостановка выполнения автоплатежа
		if (isPauseStatusNeed(type))
		{
			return ChangeStatusAction_Type.PAUSE;
		}

		//возобновление выполнения автоплатежа
		if (isUnpauseStatusNeed(type))
		{
			return ChangeStatusAction_Type.UNPAUSE;
		}

		//закрытие автоплатежа
		if (isCloseStatusNeed(type))
		{
			return ChangeStatusAction_Type.CLOSE;
		}

		throw new GateException("Используется некорректное действие над подпиской.");
	}

	private boolean isPauseStatusNeed(Class<? extends GateDocument> type)
	{
		return DelayCardPaymentSystemPaymentLongOffer.class == type
						|| EmployeeDelayCardPaymentSystemPaymentLongOffer.class == type
						|| DelayInvoiceSubscription.class == type
						|| RefuseCardToAccountLongOffer.class == type;
	}

	private boolean isUnpauseStatusNeed(Class<? extends GateDocument> type)
	{
		return RecoveryCardPaymentSystemPaymentLongOffer.class == type
						|| EmployeeRecoveryCardPaymentSystemPaymentLongOffer.class == type
						|| RecoveryInvoiceSubscription.class == type
						|| RecoverCardToAccountLongOffer.class == type ;
	}

	private boolean isCloseStatusNeed(Class<? extends GateDocument> type)
	{
		return CloseCardPaymentSystemPaymentLongOffer.class == type
						|| EmployeeCloseCardPaymentSystemPaymentLongOffer.class == type
						|| CloseInvoiceSubscription.class == type
						|| CloseCardToAccountLongOffer.class == type;
	}

	public CardAcctId_Type createCardAcctId(Card card, Client owner, Calendar expireDate, boolean fillCustInfo, boolean needOffice)
			throws GateLogicException, GateException
	{
		CardAcctId_Type cardAcctId = super.createCardAcctId(card, owner, expireDate, fillCustInfo, needOffice);
		cardAcctId.setCardType(CardTypeWrapper.getCardTypeForRequest(card.getCardType()));
		cardAcctId.setAcctCur(CurrencyHelper.getCurrencyCode(card.getCurrency().getCode()));

		PersonInfo_Type personInfo = cardAcctId.getCustInfo().getPersonInfo();
		Address address = owner.getLegalAddress();

		FullAddress_Type fullAddress = new FullAddress_Type();
		fullAddress.setAddr3(address != null && StringHelper.isNotEmpty(address.getUnparseableAddress()) ? address.getUnparseableAddress() : " ");
		fullAddress.setAddrType(AddressType.REGISTRATION.getType());

		ContactInfo_Type contactInfo = new ContactInfo_Type();
		contactInfo.setPostAddr(new FullAddress_Type[]{fullAddress});
		personInfo.setContactInfo(contactInfo);

		return  cardAcctId;
	}

	/**
	 * Дозаполнение дополнительнй информации о карте для запроса.(Тип карты, вид карты, принадлежность к бонусной программе, офис)
	 * @param cardAcctId - заполняемая структура
	 * @param card - карта, источник данных для заполнен6ия
	 * @param needrbBrchId - признак необходимости добавления информации о подразделении в информацию о карте.(только rbBrchId!!)
	 */
	protected void fillCardAccIdAdditionalInfo(CardAcctId_Type cardAcctId, Card card, boolean needrbBrchId)
	{
		cardAcctId.setCardLevel(CardLevelWrapper.getCardLevelForRequest(card.getCardLevel()));
		cardAcctId.setCardBonusSign(CardBonusSignWrapper.getCardBonusSingForRequest(card.getCardBonusSign()));
		cardAcctId.setCardType(CardTypeWrapper.getCardTypeForRequest(card.getCardType()));

		if (needrbBrchId)
		{
			BankInfo_Type bankInfo_type = new BankInfo_Type();
			EntityCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
			bankInfo_type.setRbBrchId(compositeId.getRbBrchId());
			cardAcctId.setBankInfo(bankInfo_type);
		}
	}

	private CardAcctId_Type createCardAcctIdForAutoPaymentTemplate(Card card, Client owner, Calendar expireDate, boolean fillCustInfo, boolean needOffice) throws GateException, GateLogicException
	{
		CardAcctId_Type cardAcctId = createCardAcctId(card, owner, expireDate, true, true);
		//добавляем обязательные параметры Birthday, IdentityCard
		PersonInfo_Type personInfo = cardAcctId.getCustInfo().getPersonInfo();
		personInfo.setBirthday(getStringDate(owner.getBirthDay()));
		IdentityCard_Type identityCard_type = new IdentityCard_Type();
		AutoSubscriptionHelper.fillIdentityCard_Type(identityCard_type, owner);
		personInfo.setIdentityCard(identityCard_type);
		return cardAcctId;
	}
}
