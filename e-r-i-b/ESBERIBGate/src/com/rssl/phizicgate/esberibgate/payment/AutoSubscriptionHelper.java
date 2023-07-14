package com.rssl.phizicgate.esberibgate.payment;

import com.rssl.phizic.common.types.Day;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.autosubscriptions.*;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.owner.PersonName;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.autopayments.AutoSubscriptionsRequestHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.payment.recipients.BillingRequestHelper;
import com.rssl.phizicgate.esberibgate.types.AddressType;
import com.rssl.phizicgate.esberibgate.types.CardBonusSignWrapper;
import com.rssl.phizicgate.esberibgate.types.CardLevelWrapper;
import com.rssl.phizicgate.esberibgate.types.CardTypeWrapper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Хелпер создания автоплатежа в АС Аптоплатежи
 *
 * @author khudyakov
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionHelper extends PaymentsRequestHelper
{
	public AutoSubscriptionHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Сформировать запрос создания заявки на автоплатеж
	 * @param payment заявка
	 * @return запрос
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AutoSubscriptionModRq_Type createAutoSubscriptionModRqBase(AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		AutoSubscriptionModRq_Type request = new AutoSubscriptionModRq_Type();
		fillAutoSubscriptionModRq_Type(request, payment);
		return request;
	}

	/**
	 * Сформировать запрос приостановки/возобновления/закрытия автоплатежа
	 * @param payment заявка
	 * @return запрос
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AutoSubscriptionStatusModRq_Type createAutoSubscriptionStatusModRq(AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		AutoSubscriptionStatusModRq_Type request = new AutoSubscriptionStatusModRq_Type();

		request.setRqUID(BillingRequestHelper.generateUUID());
		request.setRqTm(BillingRequestHelper.generateRqTm());
		request.setSPName(SPName_Type.BP_ERIB);

		request.setBankInfo(createAuthBankInfo(getRbTbBrch(payment.getInternalOwnerId())));

		AutoSubscriptionRec_Type autoSubRec_type = new AutoSubscriptionRec_Type();
		AutoSubscriptionId_Type autoSubId_type = new AutoSubscriptionId_Type();
		autoSubId_type.setAutopayId(Long.parseLong(payment.getNumber()));
		autoSubId_type.setSystemId(ExternalSystemHelper.getCode(AutoSubscriptionsRequestHelper.SYSTEM_ID));
		autoSubRec_type.setAutoSubscriptionId(autoSubId_type);

		AutoSubscriptionInfo_Type infoType = new AutoSubscriptionInfo_Type();
		infoType.setRequestId(payment.getOperationUID());
		autoSubRec_type.setAutoSubscriptionInfo(infoType);

		request.setAutoSubscriptionRec(autoSubRec_type);
		request.setActionType(getActionType(payment.getType()));
		request.setNeedConfirmation(payment.isNeedConfirmation());
		request.setChannelType(Channel_Type.fromString(payment.getChannelType().toString()));
		request.setMBCInfo(new MBCInfo_Type(payment.isConnectChargeOffResourceToMobileBank(), null, null));

		return request;
	}

	/**
	 * Заполнить структуру AutoSubscriptionModRq_Type
	 * @param autoSubscriptionModRq_type структура
	 * @param payment платеж
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected void fillAutoSubscriptionModRq_Type(AutoSubscriptionModRq_Type autoSubscriptionModRq_type, AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		ExternalSystemHelper.check(AutoSubscriptionsRequestHelper.SYSTEM_ID);

		autoSubscriptionModRq_type.setRqUID(BillingRequestHelper.generateUUID());
		autoSubscriptionModRq_type.setRqTm(BillingRequestHelper.generateRqTm());
		//result.setOperUID(BillingRequestHelper.generateOUUID()); В настоящее время СБОЛ не передает данное поле.
		autoSubscriptionModRq_type.setSPName(SPName_Type.BP_ERIB);
		autoSubscriptionModRq_type.setBankInfo(generateBankInfo(payment));

		autoSubscriptionModRq_type.setMBCInfo(new MBCInfo_Type(payment.isConnectChargeOffResourceToMobileBank(), null, null));
		autoSubscriptionModRq_type.setExecute(payment.isExecutionNow());
		autoSubscriptionModRq_type.setNeedConfirmation(payment.isNeedConfirmation());

		AutoSubscriptionRec_Type autoSubRec_type = new AutoSubscriptionRec_Type();
		fillAutoSubscriptionRec_Type(autoSubRec_type, payment);

		autoSubscriptionModRq_type.setAutoSubscriptionRec(autoSubRec_type);

		if (payment.getConfirmedEmployeeInfo() != null)
		{
			autoSubscriptionModRq_type.setEmployeeOfTheVSP(createEmployeeOfTheVSP(payment));
		}
	}

	//определение действия с подпиской
	private ChangeStatusAction_Type getActionType(Class<? extends GateDocument> type) throws GateException
	{
		//приостановка выполнения автоплатежа
		if (type.isAssignableFrom(DelayCardToCardLongOffer.class))
		{
			return ChangeStatusAction_Type.PAUSE;
		}

		//возобновление выполнения автоплатежа
		if (type.isAssignableFrom(RecoveryCardToCardLongOffer.class))
		{
			return ChangeStatusAction_Type.UNPAUSE;
		}

		//закрытие автоплатежа
		if (type.isAssignableFrom(CloseCardToCardLongOffer.class))
		{
			return ChangeStatusAction_Type.CLOSE;
		}

		throw new GateException("Используется некорректное действие над подпиской.");
	}

	/**
	 * Заполнить структуру AutoSubscriptionRec_Type
	 * @param autoSubRec_type структура
	 * @param payment платеж
	 * @throws GateException
	 */
	protected void fillAutoSubscriptionRec_Type(AutoSubscriptionRec_Type autoSubRec_type, AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		AutoSubscriptionInfo_Type autoSubInfo_type = new AutoSubscriptionInfo_Type();
		fillAutoSubscriptionInfo_Type(autoSubInfo_type, payment);

		AutoPaymentTemplate_Type autoPaymentTemplate_type = new AutoPaymentTemplate_Type();
		fillAutoPaymentTemplate_Type(autoPaymentTemplate_type, payment);

		autoSubRec_type.setAutoSubscriptionInfo(autoSubInfo_type);
		autoSubRec_type.setAutoPaymentTemplate(autoPaymentTemplate_type);
	}

	protected void fillAutoSubscriptionId_Type(AutoSubscriptionId_Type autoSubscriptionId_type, AutoSubscriptionClaim payment) throws GateException
	{
		autoSubscriptionId_type.setAutopayId(Long.parseLong(payment.getNumber()));
		autoSubscriptionId_type.setSystemId(ExternalSystemHelper.getCode(AutoSubscriptionsRequestHelper.SYSTEM_ID));
	}

	private EmployeeOfTheVSP_Type createEmployeeOfTheVSP(AutoSubscriptionClaim payment) throws GateException
	{
		EmployeeInfo employeeInfo = payment.getConfirmedEmployeeInfo();
		PersonName personName = employeeInfo.getPersonName();
		PersonName_Type personName_type = new PersonName_Type(personName.getLastName(), personName.getFirstName(), personName.getMiddleName(), null);
		BankInfo_Type bankInfo_type = getBankInfo(employeeInfo.getEmployeeOffice(), null, null);

		return new EmployeeOfTheVSP_Type(employeeInfo.getLogin(), personName_type, bankInfo_type);
	}

	/**
	 * Заполнить структуру AutoSubscriptionInfo_Type
	 * @param autoSubInfo_type структура
	 * @param payment платеж
	 * @throws GateException
	 */
	protected void fillAutoSubscriptionInfo_Type(AutoSubscriptionInfo_Type autoSubInfo_type, AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		autoSubInfo_type.setRequestId(payment.getOperationUID());
		autoSubInfo_type.setAutopayName(payment.getFriendlyName());

		ExecutionEventType eventType = payment.getExecutionEventType();
		SumType sumType = payment.getSumType();

		autoSubInfo_type.setExeEventCode(ExeEventCodeASAP_Type.fromValue(eventType.toString()));
		autoSubInfo_type.setSummaKindCode(SummaKindCodeASAP_Type.fromValue(sumType.toString()));
		autoSubInfo_type.setStartDate(getStringDateTime(payment.getCreateDate()));
		autoSubInfo_type.setAutopayNumber(payment.getAutopayNumber());
		if (payment.getUpdateDate() != null)
		{
			autoSubInfo_type.setUpdateDate(getStringDateTime(payment.getUpdateDate()));
		}

		// если регулярный автоплатеж
		if (isRegular(eventType))
		{
			autoSubInfo_type.setNextPayDate(getStringDateTime(payment.getNextPayDate()));
			// если автоплатеж на фиксированную сумму
			if (SumType.FIXED_SUMMA_IN_RECIP_CURR == sumType || SumType.RUR_SUMMA == sumType || SumType.FIXED_SUMMA == sumType)
			{
				autoSubInfo_type.setCurAmt(payment.getAmount().getDecimal());
			}
			// если автоплатеж по выставленному счету
			if (sumType == SumType.BY_BILLING && payment.getMaxSumWritePerMonth() != null)
			{
				autoSubInfo_type.setMaxSumWritePerMonth(payment.getMaxSumWritePerMonth().getDecimal());
			}

			PayDay_Type payDay_type = new PayDay_Type();
			fillPayDay(payDay_type, payment);
			autoSubInfo_type.setPayDay(payDay_type);
		}

		// если пороговый платеж
		if (SumType.FIXED_SUMMA_IN_RECIP_CURR == sumType && ExecutionEventType.ON_REMAIND == eventType)
		{
			autoSubInfo_type.setIrreducibleAmt(payment.getFloorLimit().getDecimal());
			autoSubInfo_type.setCurAmt(payment.getAmount().getDecimal());
		}

		//если платеж от суммы покупок/зачислений
		if(sumType == SumType.PERCENT_BY_ANY_RECEIPT || sumType == SumType.PERCENT_BY_DEBIT)
		{
			autoSubInfo_type.setPercent(payment.getPercent());
			//поле суммы не является обязательным для этого типа заявки.
			Money amount = payment.getAmount();
			if (amount != null)
				autoSubInfo_type.setMaxSumWritePerMonth(amount.getDecimal());
		}

		autoSubInfo_type.setChannelType(Channel_Type.fromString(payment.getChannelType().toString()));

		if (autoSubInfo_type.getChannelType().equals(Channel_Type.US))
		{
			//Номер устройства самообслуживания, через которое регистрируется подписка
			autoSubInfo_type.setSPNum(payment.getCodeATM());
		}

		Code officeCode = payment.getOffice().getCode();
		if (officeCode == null)
		{
			throw new GateException("Неизвестен офис, в котором заключен договор с получателем");
		}

		autoSubInfo_type.setBankInfo(getBankInfo(officeCode, null, null));
		autoSubInfo_type.setTransDirection(getTransDirection(payment));
		autoSubInfo_type.setMessage(payment.getMessageToRecipient());
	}

	private void fillPayDay(PayDay_Type payDay_type, AutoSubscriptionClaim payment) throws GateException
	{
		ExecutionEventType eventType = payment.getExecutionEventType();
		if (ExecutionEventType.ONCE_IN_MONTH == eventType)
		{
			payDay_type.setDay(String.valueOf(payment.getStartDate().get(Calendar.DATE)));
		}
		if (ExecutionEventType.ONCE_IN_WEEK == eventType)
		{
			payDay_type.setWeekDay(getWeeklyDay(payment.getStartDate()));
		}
		if (ExecutionEventType.ONCE_IN_QUARTER == eventType)
		{
			payDay_type.setDay(String.valueOf(payment.getStartDate().get(Calendar.DATE)));
			payDay_type.setMonthInQuarter(String.valueOf(getMonthInQuarter(payment.getStartDate())));
		}
		if (ExecutionEventType.ONCE_IN_YEAR == eventType)
		{
			payDay_type.setMonthInYear(String.valueOf(payment.getStartDate().get(Calendar.MONTH) + 1));
		}
	}

	private int getMonthInQuarter(Calendar nextPayDate)
	{
		int monthInQuarter = (nextPayDate.get(Calendar.MONTH) + 1) % 3;
		if (monthInQuarter == 0)
		{
			return 3;
		}
		return monthInQuarter;
	}

	private String getWeeklyDay(Calendar startDate) throws GateException
	{
		int day = startDate.get(Calendar.DAY_OF_WEEK);
		switch (day)
		{
			case 1: return Day.SUNDAY.toFullName();
			case 2: return Day.MONDAY.toFullName();
			case 3: return Day.TUESDAY.toFullName();
			case 4: return Day.WEDNESDAY.toFullName();
			case 5: return Day.THURSDAY.toFullName();
			case 6: return Day.FRIDAY.toFullName();
			case 7: return Day.SATURDAY.toFullName();
		}

		throw new GateException();
	}

	private TransDirection_Type getTransDirection(AutoSubscriptionClaim payment) throws GateLogicException, GateException
	{
		Class<? extends GateDocument> type = payment.getType();
		if (type.isAssignableFrom(InternalCardsTransferLongOffer.class))
		{
			return TransDirection_Type.TCC_OWN;
		}
		if (ExternalCardsTransferToOtherBankLongOffer.class == type)
		{
			return TransDirection_Type.TCC_P2P_FOREIGN;
		}
		if (ExternalCardsTransferToOurBankLongOffer.class == type)
		{
			return TransDirection_Type.TCC_P2P;
		}
		return null;
	}

	protected void fillAutoPaymentTemplate_Type(AutoPaymentTemplate_Type autoPaymentTemplate_type, AutoSubscriptionClaim payment) throws GateException, GateLogicException {}

	protected void fillCardAcctId_Type(CardAcctId_Type cardAcctId_type, Client client, Card card, CardOrAccountCompositeId compositeId) throws GateLogicException, GateException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());

		cardAcctId_type.setSystemId(getNotNullSystemId(compositeId));
		cardAcctId_type.setCardNum(card.getNumber());
		cardAcctId_type.setEndDt(getStringDate(card.getExpireDate()));
		cardAcctId_type.setAcctId(card.getPrimaryAccountNumber());
		cardAcctId_type.setBankInfo(getBankInfo(compositeId, null, true));
		cardAcctId_type.setCardType(CardTypeWrapper.getCardTypeForRequest(card.getCardType()));
		cardAcctId_type.setCardLevel(CardLevelWrapper.getCardLevelForRequest(card.getCardLevel()));
		cardAcctId_type.setCardBonusSign(CardBonusSignWrapper.getCardBonusSingForRequest(card.getCardBonusSign()));
		cardAcctId_type.setAcctCur(card.getCurrency().getCode());

		CustInfo_Type custInfo_type = new CustInfo_Type();
		fillCustInfo_Type(custInfo_type, client);

		cardAcctId_type.setCustInfo(custInfo_type);
	}

	private String getNotNullSystemId(CardOrAccountCompositeId compositeId)
	{
		String systemId = compositeId.getSystemId();
		if (StringHelper.isNotEmpty(systemId))
		{
			return systemId;
		}

		//согласовано с СБТ
		return ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way();
	}

	protected void fillInternalCardAcctId_Type(CardAcctId_Type cardAcctId_type, Client client, Card card) throws GateLogicException, GateException
	{
		fillCardAcctId_Type(cardAcctId_type, client, card, getInternalCardCompositeId(card, client));
		fillInternalCardPrimaryAccount(cardAcctId_type, card);
	}

	protected void fillExternalCardAcctId_Type(CardAcctId_Type cardAcctId_type, Card card) throws GateException, GateLogicException
	{
		try
		{
			fillCardAcctId_Type(cardAcctId_type, card.getCardClient(), card, getExternalCardCompositeId(card));
			fillExternalCardPrimaryAccount(cardAcctId_type, card);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
		catch (GateException e)
		{
			throw new GateException(e);
		}
	}

	protected void fillCustInfo_Type(CustInfo_Type custInfo_type, Client client) throws GateLogicException, GateException
	{
		if (client == null)
		{
			throw new GateException("Не найден клиент.");
		}

		PersonInfo_Type personInfo_type = new PersonInfo_Type();
		fillPersonInfo_Type(personInfo_type, client);

		custInfo_type.setPersonInfo(personInfo_type);
	}

	protected void fillPersonInfo_Type(PersonInfo_Type personInfo_type, Client client) throws GateException
	{
		personInfo_type.setPersonName(new PersonName_Type(client.getSurName(), client.getFirstName(), client.getPatrName(), null));
		personInfo_type.setBirthday(getStringDate(client.getBirthDay()));

		IdentityCard_Type identityCard_type = new IdentityCard_Type();
		fillIdentityCard_Type(identityCard_type, client);

		personInfo_type.setIdentityCard(identityCard_type);

		ContactInfo_Type contactInfo_type = new ContactInfo_Type();
		fillContactInfo_Type(contactInfo_type, client.getLegalAddress());

		personInfo_type.setContactInfo(contactInfo_type);
	}

	/**
	 * Заполнение удостоверения личности(IdentityCard_Type).
	 * @param identityCard_type - информация по карте
	 * @param client - клиент
	 * @throws GateException
	 */
	public static void fillIdentityCard_Type(IdentityCard_Type identityCard_type, Client client) throws GateException
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		if (documents.isEmpty())
		{
			throw new GateException("Не найден документ клиента id = " + client.getId());
		}

		//Сортируем документы клиента.
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document = documents.get(0);

		identityCard_type.setIdSeries(document.getDocSeries());
		identityCard_type.setIdNum(document.getDocNumber());
		identityCard_type.setIdType(PassportTypeWrapper.getPassportType(document.getDocumentType()));
		identityCard_type.setIssuedBy(document.getDocIssueBy());
		identityCard_type.setIssuedCode(document.getDocIssueByCode());
		identityCard_type.setIssueDt(getStringDate(document.getDocIssueDate()));
		identityCard_type.setExpDt(getStringDate(document.getDocTimeUpDate()));
	}

	protected void fillContactInfo_Type(ContactInfo_Type contactInfo_type, Address address)
	{
		FullAddress_Type fullAddress_type = new FullAddress_Type();
		fullAddress_type.setAddr3(address != null && StringHelper.isNotEmpty(address.getUnparseableAddress()) ? address.getUnparseableAddress() : " ");
		fullAddress_type.setAddrType(AddressType.REGISTRATION.getType());

		contactInfo_type.setPostAddr(new FullAddress_Type[]{fullAddress_type});
	}

	private CardOrAccountCompositeId getInternalCardCompositeId(Card card, Client client) throws GateLogicException, GateException
	{
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
		if (StringHelper.isEmpty(compositeId.getSystemId()) || StringHelper.isEmpty(compositeId.getRbBrchId()))
		{
			//поля могут н придти в CRWDI.. если не пришли ... ищем в БД идентифкатор, сформированный после получения GFL
			String cardExternalId = getFactory().service(BackRefBankrollService.class).findCardExternalId(client.getInternalOwnerId(), card.getNumber());
			if (cardExternalId == null)
			{
				throw new GateException("Не найдена информация в БД по карте " + card.getNumber());
			}
			return EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
		}

		return compositeId;
	}

	/**
	 * Получение информации по карте
	 * @param card карта
	 * @return информация по карте
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private CardOrAccountCompositeId getExternalCardCompositeId(Card card) throws GateLogicException, GateException
	{
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
		if (StringHelper.isEmpty(compositeId.getSystemId()) || StringHelper.isEmpty(compositeId.getRbBrchId()))
		{
			//поля могут не придти в CRWDI.. если не пришли ... ищем в БД идентифкатор, сформированный после получения GFL
			String cardExternalId = getFactory().service(BackRefBankrollService.class).findCardExternalId(card.getNumber());
			if (StringHelper.isNotEmpty(cardExternalId))
			{
				return EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
			}
		}

		if (StringHelper.isEmpty(compositeId.getRbBrchId()))
		{
			//RbBrchId необходимо обязательно передать
			throw new GateException("Не найден RbBrchId карты зачисления.");
		}

		return compositeId;
	}

	/**
	 * Заполнить структуру DepAcctId_Type
	 * @param toDepAcctId_Type структура
	 * @param owner владелец
	 * @param account счет
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected void fillDepAcctId_Type(CardAcctId_Type toDepAcctId_Type, Client owner, Account account) throws GateException, GateLogicException
	{
		CardOrAccountCompositeId compositeId = getAccountCompositeId(account);
		toDepAcctId_Type.setSystemId(compositeId.getSystemId());
		toDepAcctId_Type.setAcctId(account.getNumber());
		toDepAcctId_Type.setAcctCur(account.getCurrency().getCode());
		toDepAcctId_Type.setAcctName(account.getDescription());
		toDepAcctId_Type.setAcctCode(account.getKind());

		if (owner != null)
		{
			toDepAcctId_Type.setCustInfo(createCustInfoType(owner));
		}

		toDepAcctId_Type.setBankInfo(getBankInfo(compositeId, null, true));

		PersonInfo_Type personInfo = toDepAcctId_Type.getCustInfo().getPersonInfo();
		Address address = owner.getLegalAddress();

		FullAddress_Type fullAddress = new FullAddress_Type();
		fullAddress.setAddr3(address != null && StringHelper.isNotEmpty(address.getUnparseableAddress()) ? address.getUnparseableAddress() : " ");
		fullAddress.setAddrType(AddressType.REGISTRATION.getType());

		ContactInfo_Type contactInfo = new ContactInfo_Type();
		contactInfo.setPostAddr(new FullAddress_Type[]{fullAddress});
		personInfo.setContactInfo(contactInfo);
	}

	private CardOrAccountCompositeId getAccountCompositeId(Account account) throws GateLogicException, GateException
	{
		CardOrAccountCompositeId compositeId = EntityIdHelper.getAccountCompositeId(account);
		if (StringHelper.isEmpty(compositeId.getSystemId()) || StringHelper.isEmpty(compositeId.getRbBrchId()))
		{
			//поля могут не придти в CRWDI.. если не пришли ... ищем в БД идентифкатор, сформированный после получения GFL
			String cardExternalId = getFactory().service(BackRefBankrollService.class).findAccountExternalId(account.getNumber());
			if (cardExternalId == null)
			{
				throw new GateException("Не найдена информация в БД по вкладу " + account.getNumber());
			}
			return EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
		}

		return compositeId;
	}

	protected Client getBusinessOwner(AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		return getFactory().service(BackRefClientService.class).getClientById(payment.getInternalOwnerId());
	}

	/**
	 * @param eventType тип
	 * @return true - регулярный автоплатеж
	 */
	protected boolean isRegular(ExecutionEventType eventType)
	{
		return eventType == ExecutionEventType.ONCE_IN_WEEK || eventType == ExecutionEventType.ONCE_IN_MONTH
				|| eventType == ExecutionEventType.ONCE_IN_QUARTER || eventType == ExecutionEventType.ONCE_IN_YEAR;
	}

	/**
	 * Заполнение СКС карты списания.
	 * @param cardAcctId_type - структука карты списания
	 * @param card - карта списания.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected void fillInternalCardPrimaryAccount(CardAcctId_Type cardAcctId_type, Card card) throws GateLogicException, GateException
	{
		Account cardPrimaryAccount = null;
		try
		{
			//если доступен СКС
			if(StringHelper.isNotEmpty(card.getPrimaryAccountNumber()))
			{
				cardAcctId_type.setAcctId(card.getPrimaryAccountNumber());
				return;
			}
			//если СКС не доступен, делаем отдельным запросом.
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			cardPrimaryAccount = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}

		if(cardPrimaryAccount == null)
			throw new GateLogicException("Операция по данной карте временно недоступна.");

		cardAcctId_type.setAcctId(cardPrimaryAccount.getNumber());
	}

	/**
	 * Заполнение СКС карты списания.
	 * @param cardAcctId_type - структука карты списания
	 * @param card - карта списания.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private void fillExternalCardPrimaryAccount(CardAcctId_Type cardAcctId_type, Card card) throws GateLogicException, GateException
	{
		if (StringHelper.isNotEmpty(cardAcctId_type.getAcctId()))
		{
			return;
		}

		BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
		Account primaryAccount = backRefBankrollService.getCardAccount(card.getNumber());

		cardAcctId_type.setAcctId(primaryAccount == null ? null : primaryAccount.getNumber());
	}
}
