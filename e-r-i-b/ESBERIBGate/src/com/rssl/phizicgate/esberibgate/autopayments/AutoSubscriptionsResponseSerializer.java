package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl;
import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.payment.recipients.BillingResponseSerializer;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.*;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.lang.ArrayUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionsResponseSerializer extends BillingResponseSerializer
{
	/**
	 * Заполнение данных в AutoSubscription из полученного ответа
	 * @param ifxRq запрос
	 * @param ifxRs ответ
	 * @param cards список карт
	 * @return список подписок на автоплатеж клиента
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<AutoSubscription> getAutoSubscriptions(IFXRq_Type ifxRq, IFXRs_Type ifxRs, List<Card> cards) throws GateException,GateLogicException
	{
		Status_Type status = ifxRs.getGetAutoSubscriptionListRs().getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, GetAutoSubscriptionListRs_Type.class, ifxRq);
		}

		AutoSubscriptionRec_Type[] autoSubscriptionRec_types = ifxRs.getGetAutoSubscriptionListRs().getAutoSubscriptionRec();
		if (ArrayUtils.isEmpty(autoSubscriptionRec_types))
		{
			return null;
		}

		Map<String, Long> cardsMap = new HashMap<String, Long>(cards.size());
		for (Card card : cards)
		{
			CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(card.getId());
			cardsMap.put(compositeId.getEntityId(), compositeId.getLoginId());
		}

		List<AutoSubscription> autoSubscriptions = new ArrayList<AutoSubscription>();
		for(AutoSubscriptionRec_Type autoSubscription_type : autoSubscriptionRec_types)
		{
			AutoPaymentTemplate_Type autoPaymentTemplate_type = autoSubscription_type.getAutoPaymentTemplate();
			if (autoPaymentTemplate_type.getBankAcctRec().length != 1)
				throw new GateException("В ответе по детальной информации по автоплатежу пришло некорректное число карт.");

			// На данный момент автопоиски не должны обрабатываться
			SummaKindCodeASAP_Type summaKindCode = autoSubscription_type.getAutoSubscriptionInfo().getSummaKindCode();
			if(SummaKindCodeASAP_Type.BY_BILLING_BASKET.equals(summaKindCode))
				continue;

			//пока используется только одна карта (расширение функциональности - следующие релизы)
			String cardNumber = autoPaymentTemplate_type.getBankAcctRec()[0].getCardAcctId().getCardNum();
			autoSubscriptions.add(createAutoSubscription(autoSubscription_type, cardsMap.get(cardNumber)));
		}

		return autoSubscriptions;
	}

	/**
	 * Заполнение данных на запрос о получении детальной информации.
	 * @param ifxRq запрос
	 * @param ifxRs ответ.
	 * @param autoSubscriptions подписка на автоплатеж
	 * @return спсиок детальной информации.
	 */
	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscriptionInfo(IFXRq_Type ifxRq, IFXRs_Type ifxRs, AutoSubscription ... autoSubscriptions) throws GateLogicException, GateException
	{
		Status_Type status = ifxRs.getGetAutoSubscriptionDetailInfoRs().getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, GetAutoSubscriptionDetailInfoRs_Type.class, ifxRq);
		}

		Map<String, AutoSubscription> subscriptionsMap = new HashMap<String, AutoSubscription>();
		for (AutoSubscription autoSubscription : autoSubscriptions)
		{
			subscriptionsMap.put(autoSubscription.getNumber(), autoSubscription);
		}

		GetAutoSubscriptionDetailInfoRs_Type autoSubscriptionDetailInfoRs = ifxRs.getGetAutoSubscriptionDetailInfoRs();
		if (autoSubscriptionDetailInfoRs == null)
			return null;

		GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> result = new GroupResult<AutoSubscription, AutoSubscriptionDetailInfo>();
		for (AutoSubscriptionRec_Type autoSubscriptionRec: autoSubscriptionDetailInfoRs.getAutoSubscriptionRec())
		{
			//сквозной идентификатор автоплатежа
			Long id = autoSubscriptionRec.getAutoSubscriptionId().getAutopayId();

			//заполняем подписку дополнительными данными
			AutoSubscription autoSubscription = subscriptionsMap.get(String.valueOf(id));
			if (autoSubscription == null)
			{
				log.error("Некорректная ситуация: при получении детальной информации по подпискам клиента возникло несоответствие запрашиваемых и пришедших идентификаторов подписок на автоплатеж. Запрошеный id = " + id);
				continue;
			}

			result.putResult(autoSubscription, getAbstractTransfer(autoSubscription, autoSubscriptionRec));
		}

		return result;
	}

	/**
	 * Заполнение данных на запрос о получении детальной информации.
	 * @param ifxRq запрос
	 * @param ifxRs ответ.
	 * @param externalId внешний идентификатор подписка на автоплатеж
	 * @return спсиок детальной информации.
	 */
	public GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> getAutoSubscriptionInfo(IFXRq_Type ifxRq, IFXRs_Type ifxRs, String... externalId) throws GateLogicException, GateException
	{
		Status_Type status = ifxRs.getGetAutoSubscriptionDetailInfoRs().getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, GetAutoSubscriptionDetailInfoRs_Type.class, ifxRq);
		}

		GetAutoSubscriptionDetailInfoRs_Type autoSubscriptionDetailInfoRs = ifxRs.getGetAutoSubscriptionDetailInfoRs();
		if (autoSubscriptionDetailInfoRs == null)
			return null;

		GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> result = new GroupResult<AutoSubscription, AutoSubscriptionDetailInfo>();
		for (AutoSubscriptionRec_Type autoSubscriptionRec: autoSubscriptionDetailInfoRs.getAutoSubscriptionRec())
		{
			//заполняем подписку дополнительными данными
			EntityCompositeId compositeId = EntityIdHelper.getLongOfferCompositeId(externalId[0]);
			AutoSubscription autoSubscription = createAutoSubscription(autoSubscriptionRec, compositeId.getLoginId());
			result.putResult(autoSubscription, getAbstractTransfer(autoSubscription, autoSubscriptionRec));
		}

		return result;
	}

	private void fillGateLongOffer(GateLongOfferImpl longOffer, AutoSubscription autoSubscription) throws GateException
	{
		longOffer.setExternalId(autoSubscription.getExternalId());
		longOffer.setNumber(autoSubscription.getNumber());
		longOffer.setOffice(autoSubscription.getOffice());
		longOffer.setStartDate(autoSubscription.getStartDate());
		longOffer.setEndDate(autoSubscription.getEndDate());
		longOffer.setType(autoSubscription.getType());
		longOffer.setExecutionEventType(autoSubscription.getExecutionEventType());
		longOffer.setSumType(autoSubscription.getSumType());
		longOffer.setAmount(autoSubscription.getAmount());
		longOffer.setPercent(autoSubscription.getPercent());
		longOffer.setPayDay(autoSubscription.getPayDay());
		longOffer.setPriority(autoSubscription.getPriority());
		longOffer.setFriendlyName(autoSubscription.getFriendlyName());
		longOffer.setFloorLimit(autoSubscription.getFloorLimit());
		longOffer.setCardNumber(autoSubscription.getCardNumber());
		longOffer.setReceiverName(autoSubscription.getReceiverName());
	}

	private AutoSubscriptionDetailInfo getAbstractTransfer(AutoSubscription autoSubscription, AutoSubscriptionRec_Type autoSubscriptionRec) throws GateException, GateLogicException
	{
		GateLongOfferImpl payment = new GateLongOfferImpl();
		fillGateLongOffer(payment, autoSubscription);

		payment.setType(getSubscriptionType(autoSubscriptionRec));
		fillAutoPaymentTemplate(payment, autoSubscriptionRec.getAutoPaymentTemplate());
		fillAutoSubscriptionInfo(payment, autoSubscriptionRec.getAutoSubscriptionInfo());

		return payment;
	}

	private void fillAutoPaymentTemplate(GateLongOfferImpl payment, AutoPaymentTemplate_Type autoPaymentTemplate) throws GateLogicException, GateException
	{
		BankAcctRec_Type[] bankAcctRec_types = autoPaymentTemplate.getBankAcctRec();
		if (bankAcctRec_types.length != 1)
			throw new GateException("В ответе по детальной информации по автоплатежу пришло некорректное число карт.");

		//пока используется только одна карта (расширение функциональности - следующие релизы)
		payment.setChargeOffCard(bankAcctRec_types[0].getCardAcctId().getCardNum());
		payment.setBillingCode(autoPaymentTemplate.getSystemId());
		//только для автоплатежа P2P
		if (payment.getType() == ExternalCardsTransferToOtherBankLongOffer.class
				|| payment.getType() == ExternalCardsTransferToOurBankLongOffer.class
				|| payment.getType() == InternalCardsTransferLongOffer.class)
		{
			CardAcctId_Type cardAcctIdType = autoPaymentTemplate.getCardAcctTo();

			payment.setReceiverAccount(cardAcctIdType.getCardNum());
			payment.setReceiverCard(cardAcctIdType.getCardNum());

			PersonName_Type personNameType = cardAcctIdType.getCustInfo().getPersonInfo().getPersonName();

			payment.setReceiverFirstName(personNameType.getFirstName());
			payment.setReceiverPatrName(personNameType.getMiddleName());
			payment.setReceiverSurName(personNameType.getLastName());
		}

		if (autoPaymentTemplate.getDepAcctIdTo() != null)
		{
			payment.setReceiverAccount(autoPaymentTemplate.getDepAcctIdTo().getAcctId());
			String destinationCurrencyAlphabeticCode = autoPaymentTemplate.getDepAcctIdTo().getAcctCur();
			String chargeOffAlphabeticCode = autoPaymentTemplate.getBankAcctRec(0).getCardAcctId().getAcctCur();
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			payment.setDestinationCurrency(currencyService.findByAlphabeticCode(destinationCurrencyAlphabeticCode));
			payment.setChargeOffCurrency(currencyService.findByAlphabeticCode(chargeOffAlphabeticCode));
		}

		if (autoPaymentTemplate.getRecipientRec() != null)
			fillReceiverInfo(payment, autoPaymentTemplate.getRecipientRec(), true);
	}

	private String getSumValue(Requisite_Type requisite)
	{
		if(ArrayUtils.isNotEmpty(requisite.getEnteredData()))
		{
			return requisite.getEnteredData()[0];
		}
		else
		{
			return requisite.getDefaultValue();
		}
	}


	private GateLongOfferImpl getAbstractTransfer(AutoSubscription autoSubscription, AutoPaymentRec_Type autoPaymentRec) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		GateLongOfferImpl payment = new GateLongOfferImpl();
		fillGateLongOffer(payment, autoSubscription);
		payment.setType(getSubscriptionType(autoPaymentRec));
		BankAcctRec_Type[] bankAcctRec_types = autoPaymentRec.getBankAcctRec();
		if (bankAcctRec_types.length != 1)
			throw new GateException("В ответе по детальной информации по автоплатежу пришло некорректное число карт.");

		//пока используется только одна карта (расширение функциональности - следующие релизы)
		payment.setChargeOffCard(bankAcctRec_types[0].getCardAcctId().getCardNum());
		payment.setAutoPayStatusType(AutoPayStatusType.Active);

		AutoPaymentInfo_Type autoPaymentInfo = autoPaymentRec.getAutoPaymentInfo();
		payment.setIdFromPaymentSystem(autoPaymentInfo.getMadeOperationId());

		if (autoPaymentInfo.getCommission() != null)
			payment.setCommission(new Money(autoPaymentInfo.getCommission(), currencyService.getNationalCurrency()));

		payment.setNumber(String.valueOf(autoPaymentRec.getAutoSubscriptionId().getAutopayId()));

		payment.setExecutionDate(parseCalendar(autoPaymentRec.getAutoPaymentInfo().getExecStatus().getExecPaymentDate()));

		CardAuthorization_Type cardAuthorization = autoPaymentRec.getCardAuthorization();
		if(cardAuthorization != null)
		{
			payment.setAuthorizeDate(parseCalendar(cardAuthorization.getAuthorizationDtTm()));
			payment.setAuthorizeCode(Long.toString(cardAuthorization.getAuthorizationCode()));
		}

		Currency currency = GateSingleton.getFactory().service(CurrencyService.class).getNationalCurrency();

		if (CardPaymentSystemPaymentLongOffer.class == payment.getType())
		{
			fillReceiverInfo(payment, autoPaymentRec.getRecipientRec(), false);
			AutoSubscriptionImpl autoSubscriptionImpl = (AutoSubscriptionImpl)autoSubscription;
			autoSubscriptionImpl.setReceiverName(autoPaymentRec.getRecipientRec().getName());

			for (Requisite_Type requisite : autoPaymentRec.getRecipientRec().getRequisites())
			{
				if (requisite.getIsSum())
				{
					String sumValue = getSumValue(requisite);
					Money sum = new Money(new BigDecimal(sumValue), currency);
					autoSubscriptionImpl.setAmount(sum);
				}
			}
			autoSubscription = autoSubscriptionImpl;
            if ( autoPaymentInfo.getAcctCur() != null && autoPaymentInfo.getAcctCur().length() > 0)
                currency = currencyService.findByAlphabeticCode(autoPaymentInfo.getAcctCur());
            payment.setAmount(new Money(autoPaymentInfo.getCurAmt(), currency));
		}

		if (ExternalCardsTransferToOurBankLongOffer.class == payment.getType() || InternalCardsTransferLongOffer.class == payment.getType() ||
				ExternalCardsTransferToOtherBankLongOffer.class == payment.getType())
		{
			payment.setReceiverAccount(autoPaymentRec.getCardAcctTo().getCardNum());
			payment.setReceiverCard(autoPaymentRec.getCardAcctTo().getCardNum());
			String currencyISOCode = autoPaymentRec.getAutoPaymentInfo().getAcctCur();
			if (currencyISOCode != null && currencyISOCode.length() > 0);
			{
				currency = currencyService.findByAlphabeticCode(autoPaymentRec.getAutoPaymentInfo().getAcctCur());
			}
			payment.setAmount(new Money(autoPaymentRec.getAutoPaymentInfo().getCurAmt(),currency));
		}

		if (CardToAccountLongOffer.class == payment.getType())
		{
			payment.setReceiverAccount(autoPaymentRec.getDepAcctIdTo().getAcctId());
		}
		AutoSubscriptionInfo_Type autoSubscriptionInfoType = autoPaymentRec.getAutoSubscriptionInfo();
		if (autoSubscriptionInfoType != null)
			payment.setAutopayNumber(autoSubscriptionInfoType.getAutopayNumber());

		return payment;
	}

	/**
	 * @param recipientRec запись о поставщике.
	 * @return заполнение дополнительных полей.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<Field> fillFields(RecipientRec_Type recipientRec) throws GateException, GateLogicException
	{
		List<Field> list = new ArrayList<Field>();
		Requisite_Type[] requisites = recipientRec.getRequisites();

		if (!ArrayUtils.isEmpty(requisites))
		{
			boolean isMainSumDetected = false;
			for (Requisite_Type requisite : requisites)
			{
				CommonField field = (CommonField) fillField(requisite);
				if (field.isMainSum())
				{
					if (isMainSumDetected)
						throw new GateException("Для получателя " + recipientRec.getName() + " в ответе пришло более одного поля главной суммы");

					field.setMainSum(false);
					field.setVisible(false); //Если не выставить false, то будет происходить дублирование поля с главной суммой на JSP
					list.add(BillingPaymentHelper.createMainSumFieldNameField(field.getExternalId()));
				}

				isMainSumDetected = isMainSumDetected || field.isMainSum();
				list.add(field);
			}
		}
		return list;
	}

	private void fillReceiverInfo(GateLongOfferImpl payment, RecipientRec_Type recipientRec_type, boolean needSetBankInfo) throws GateException, GateLogicException
	{
		payment.setReceiverPointCode(recipientRec_type.getCodeRecipientBS());
		payment.setReceiverName(recipientRec_type.getName());
		payment.setReceiverINN(recipientRec_type.getTaxId());
		payment.setReceiverBank(createResideentBank(recipientRec_type.getBIC(), recipientRec_type.getCorrAccount(), recipientRec_type.getName()));
		payment.setReceiverAccount(recipientRec_type.getAcctId());
		payment.setService(new ServiceImpl(recipientRec_type.getCodeService(), recipientRec_type.getNameService()));
		payment.setGroupService(recipientRec_type.getGroupService());
		payment.setExtendedFields(fillFields(recipientRec_type));
		payment.setReceiverNameForBill(recipientRec_type.getNameOnBill());
		payment.setReceiverPhone(recipientRec_type.getPhoneToClient());
		if (recipientRec_type.getNotVisibleBankDetails() != null)
			payment.setNotVisibleBankDetails(recipientRec_type.getNotVisibleBankDetails());
		else
			payment.setNotVisibleBankDetails(false); //по умолчанию считается, что банковские реквизиты необходимо отображать.

		if (needSetBankInfo)
		{
			BankInfo_Type bankInfo = recipientRec_type.getBankInfo();
			payment.setReceiverOfficeCode(
					new ExtendedCodeGateImpl(bankInfo.getRegionId(), bankInfo.getAgencyId(), null));
		}
	}

	private void fillAutoSubscriptionInfo(GateLongOfferImpl payment, AutoSubscriptionInfo_Type autoSubscriptionInfo) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency;
		if (payment.getType() == CardToAccountLongOffer.class)
		{
			currency = payment.getChargeOffCurrency() != null ? payment.getChargeOffCurrency() : currencyService.getNationalCurrency();
		}
		else
		{
			currency = currencyService.getNationalCurrency();
		}
		if(autoSubscriptionInfo.getMaxSumWritePerMonth()!=null)
			payment.setMaxSumWritePerMonth(new Money(autoSubscriptionInfo.getMaxSumWritePerMonth(), currency));
		payment.setAutoPayStatusType(AutopayTypeWrapper.getAccountState(autoSubscriptionInfo.getAutopayStatus()));
		payment.setReasonDescription(autoSubscriptionInfo.getChangeStatus() != null ? autoSubscriptionInfo.getChangeStatus().getReasonDesc() : null);
		payment.setMessageToRecipient(autoSubscriptionInfo.getMessage());
		payment.setCreateDate(parseCalendar(autoSubscriptionInfo.getStartDate()));
		payment.setAutopayNumber(autoSubscriptionInfo.getAutopayNumber());

		if(!StringHelper.isEmpty(autoSubscriptionInfo.getUpdateDate()))
		{
			payment.setUpdateDate(parseCalendar(autoSubscriptionInfo.getUpdateDate()));
		}
		//если это регулярныя подписка или подписка по счету
		if (payment.getSumType() == SumType.RUR_SUMMA || payment.getSumType() == SumType.BY_BILLING || payment.getSumType() == SumType.FIXED_SUMMA_IN_RECIP_CURR && payment.getExecutionEventType() != ExecutionEventType.ON_REMAIND)
		{
			payment.setNextPayDate(parseCalendar(autoSubscriptionInfo.getNextPayDate()));
		}
		payment.setCodeATM(autoSubscriptionInfo.getSPNum());
		payment.setChannelType(ChannelType.valueOf(autoSubscriptionInfo.getChannelType().getValue()));
	}

	private ResidentBank createResideentBank(String bic, String name, String corAccount) throws GateException
	{
		BackRefBankInfoService backRefBankInfoService = GateSingleton.getFactory().service(BackRefBankInfoService.class);
		ResidentBank residentBank = backRefBankInfoService.findByBIC(bic);

		if (residentBank != null)
			return residentBank;

		return new ResidentBank(name, bic, corAccount);
	}

	private AutoSubscription createAutoSubscription(AutoSubscriptionRec_Type autoSubscription_type, Long ownerId) throws GateException, GateLogicException
	{
		AutoSubscriptionImpl autoSubscription = new AutoSubscriptionImpl();
		autoSubscription.setType(getSubscriptionType(autoSubscription_type));
		autoSubscription.setNumber(String.valueOf(autoSubscription_type.getAutoSubscriptionId().getAutopayId()));
		AutoSubscriptionInfo_Type info = autoSubscription_type.getAutoSubscriptionInfo();

		autoSubscription.setStartDate(parseCalendar(info.getStartDate()));
		autoSubscription.setAutopayNumber(info.getAutopayNumber());
		if(!StringHelper.isEmpty(info.getNextPayDate()))
		{
			autoSubscription.setNextPayDate(parseCalendar(info.getNextPayDate()));
		}

		autoSubscription.setFriendlyName(info.getAutopayName());
		autoSubscription.setSumType(SumTypeWrapper.getSumType(info.getSummaKindCode().getValue()));
		autoSubscription.setExecutionEventType(ExecutionEventTypeWrapper.getExecutionEventType(info.getExeEventCode().getValue()));
		AutoPaymentTemplate_Type templateInfo = autoSubscription_type.getAutoPaymentTemplate();

		//Для подписок по счету сумма платежа определяется по задолженности, выставленной ПУ(не является обязательным атрибутом)
		if (info.getCurAmt() != null)
			autoSubscription.setAmount(new Money(info.getCurAmt(), GateSingleton.getFactory().service(CurrencyService.class).getNationalCurrency()));
		else if (autoSubscription.getSumType() != SumType.BY_BILLING && autoSubscription.getSumType() != SumType.PERCENT_BY_ANY_RECEIPT && autoSubscription.getSumType() != SumType.PERCENT_BY_DEBIT)
			throw new GateLogicException("Обязательное для пороговых и регулярных подписок значение суммы платежа null.");
	    autoSubscription.setAutoPayStatusType(AutopayTypeWrapper.getAccountState(info.getAutopayStatus()));
		BankInfo_Type bankInfo = autoSubscription_type.getAutoPaymentTemplate().getBankAcctRec()[0].getCardAcctId().getBankInfo();
		autoSubscription.setOffice(getOffice(bankInfo));

		//Генерируем внешний идентификатор.
		autoSubscription_type.getAutoSubscriptionInfo().setBankInfo(bankInfo);
		autoSubscription.setExternalId(EntityIdHelper.createAutoSubscriptionEntityId(autoSubscription_type, ownerId));
		if (autoSubscription.getType() == InternalCardsTransferLongOffer.class || autoSubscription.getType() == ExternalCardsTransferToOurBankLongOffer.class ||
				autoSubscription.getType() == ExternalCardsTransferToOtherBankLongOffer.class)
		{
			autoSubscription.setReceiverName(getCardAcctId_TypeReceiverName(templateInfo.getCardAcctTo()));
			autoSubscription.setCardNumber(templateInfo.getBankAcctRec()[0].getCardAcctId().getCardNum());
			autoSubscription.setReceiverCard(templateInfo.getCardAcctTo().getCardNum());
			fillPayDayInfo(autoSubscription, info);

			if (templateInfo.getCardAcctTo().getCustInfo() != null)
			{
				PersonName_Type personNameType = templateInfo.getCardAcctTo().getCustInfo().getPersonInfo().getPersonName();
				autoSubscription.setReceiverFirstName(personNameType.getFirstName());
				autoSubscription.setReceiverPatronymicName(personNameType.getMiddleName());
				autoSubscription.setReceiverLastName(personNameType.getLastName());
			}
		}
		else if (autoSubscription.getType() == CardToAccountLongOffer.class)
		{
			fillPayDayInfo(autoSubscription, info);
			autoSubscription.setCardNumber(templateInfo.getBankAcctRec()[0].getCardAcctId().getCardNum());
			autoSubscription.setAccountNumber(templateInfo.getDepAcctIdTo().getAcctId());
			if (info.getCurAmt() != null)
			{
				autoSubscription.setAmount(new Money(info.getCurAmt(), GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode(templateInfo.getBankAcctRec()[0].getCardAcctId().getAcctCur())));
			}
			autoSubscription.setPercent(info.getPercent());
		}
		else if (autoSubscription.getType() == CardPaymentSystemPaymentLongOffer.class)
		{
			//На первом этапе только один инструмент
			autoSubscription.setReceiverName(templateInfo.getRecipientRec().getName());
			autoSubscription.setCardNumber(templateInfo.getBankAcctRec()[0].getCardAcctId().getCardNum());
		}
		return autoSubscription;
	}

	private String getCardAcctId_TypeReceiverName(CardAcctId_Type cardAcctTo)
	{
		if (cardAcctTo == null || cardAcctTo.getCustInfo() == null)
		{
			return null;
		}

		PersonName_Type personName_type = cardAcctTo.getCustInfo().getPersonInfo().getPersonName();
		return personName_type.getFirstName() + ' ' + personName_type.getMiddleName() + ' ' + personName_type.getLastName();
	}

	private void fillPayDayInfo(AutoSubscriptionImpl autoSubscription, AutoSubscriptionInfo_Type info)
	{
		PayDay_Type payDay_type = info.getPayDay();
		SummaKindCodeASAP_Type summaKindCodeASAPType = info.getSummaKindCode();
		if(summaKindCodeASAPType.getValue().equals(SummaKindCodeASAP_Type._FIXED_SUMMA)
				|| summaKindCodeASAPType.getValue().equals(SummaKindCodeASAP_Type._FIXED_SUMMA_IN_RECIP_CURR)
				|| summaKindCodeASAPType.getValue().equals(SummaKindCodeASAP_Type._RUR_SUMMA))
		{
			autoSubscription.setLongOfferPayDay(new LongOfferPayDay(payDay_type.getDay(), payDay_type.getMonthInQuarter(), payDay_type.getMonthInYear(), payDay_type.getWeekDay()));
		}
	}

	private Class<? extends GateDocument> getSubscriptionType(AutoSubscriptionRec_Type autoSubscription_type) throws GateException
	{
		return getSubscriptionType(autoSubscription_type.getAutoSubscriptionInfo().getSummaKindCode(), autoSubscription_type.getAutoSubscriptionInfo().getTransDirection());
	}

	private Class<? extends GateDocument> getSubscriptionType(AutoPaymentRec_Type autoPaymentRec_type) throws GateException
	{
		return getSubscriptionType(autoPaymentRec_type.getAutoSubscriptionInfo().getSummaKindCode(), autoPaymentRec_type.getAutoSubscriptionInfo().getTransDirection());
	}

	private Class<? extends GateDocument> getSubscriptionType(SummaKindCodeASAP_Type summaKindCodeASAP_type, TransDirection_Type transDirection) throws GateException
	{
		if (summaKindCodeASAP_type == null)
		{
			throw new GateException("Для автоплатежа не задан тип суммы SummaKindCodeASAP_Type.");
		}

		if (SummaKindCodeASAP_Type._BY_BILLING.equals(summaKindCodeASAP_type.getValue())
				|| SummaKindCodeASAP_Type._FIXED_SUMMA_IN_RECIP_CURR.equals(summaKindCodeASAP_type.getValue()))
		{
			return CardPaymentSystemPaymentLongOffer.class;
		}

		if (SummaKindCodeASAP_Type._PERCENT_BY_ANY_RECEIPT.equals(summaKindCodeASAP_type.getValue())
				|| SummaKindCodeASAP_Type._PERCENT_BY_DEBIT.equals(summaKindCodeASAP_type.getValue())
					|| SummaKindCodeASAP_Type._FIXED_SUMMA.equals(summaKindCodeASAP_type.getValue()))
		{
			return CardToAccountLongOffer.class;
		}

		if (SummaKindCodeASAP_Type._RUR_SUMMA.equals(summaKindCodeASAP_type.getValue()))
		{
			if (transDirection == null)
			{
				throw new GateException("Для автоплатежа карта-карта не задан тип TransDirection.");
			}
			if (transDirection.getValue().equals(TransDirection_Type._TCC_OWN))
			{
				return InternalCardsTransferLongOffer.class;
			}
			if (transDirection.getValue().equals(TransDirection_Type._TCC_P2P))
			{
				return ExternalCardsTransferToOurBankLongOffer.class;
			}
			if (transDirection.getValue().equals(TransDirection_Type._TCC_P2P_FOREIGN))
			{
				return ExternalCardsTransferToOtherBankLongOffer.class;
			}
		}
		throw new GateException("Вернулся некорректрый тип автоплатежа.");
	}

	private AutoSubscription createAutoSubscription(AutoPaymentRec_Type autoPaymentRec) throws GateException, GateLogicException
	{
		AutoSubscriptionImpl autoSubscription = new AutoSubscriptionImpl();
		autoSubscription.setNumber(String.valueOf(autoPaymentRec.getAutoSubscriptionId().getAutopayId()));
		//На первом этапе только один инструмент
		autoSubscription.setCardNumber(autoPaymentRec.getBankAcctRec()[0].getCardAcctId().getCardNum());
		BankInfo_Type bankInfo = autoPaymentRec.getBankAcctRec()[0].getBankInfo();
		autoSubscription.setOffice(getOffice(bankInfo));

		return autoSubscription;
	}

	/**
	 * Заполнение данных по списку платежей по подписке.
	 * @param ifxRq запрос
	 * @param ifxRs ответ внешней системы.
	 * @return список платежей.
	 */
	public List<ScheduleItem> getAutoPaymentList(IFXRq_Type ifxRq, IFXRs_Type ifxRs, AutoSubscription autoSubscription) throws GateLogicException, GateException
	{
		GetAutoPaymentListRs_Type responce = ifxRs.getGetAutoPaymentListRs();
		Status_Type status = responce.getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, GetAutoPaymentListRs_Type.class, ifxRq);
		}

		AutoPaymentRec_Type autoPaymentRec[] = responce.getAutoPaymentRec();
		List<ScheduleItem> items = new ArrayList<ScheduleItem>();
		if (autoPaymentRec == null)
			return items;

		for (int i = 0; i < autoPaymentRec.length; i++)
		{
			AutoPaymentRec_Type autoPaymentRc = autoPaymentRec[i];
			ScheduleItemImpl scheduleItem = new ScheduleItemImpl();

			scheduleItem.setNumber(Long.parseLong(autoPaymentRc.getAutoPaymentId().getPaymentId()));

			AutoPaymentInfo_Type info = autoPaymentRc.getAutoPaymentInfo();
			scheduleItem.setDate(parseCalendar(info.getExecStatus().getExecPaymentDate()));

			scheduleItem.setCommission(info.getCommission());
			scheduleItem.setSumm(info.getCurAmt());
			RecipientRec_Type recipientRecType = autoPaymentRc.getRecipientRec();
			scheduleItem.setRecipientName(recipientRecType != null ? autoPaymentRc.getRecipientRec().getName() : null);
			scheduleItem.setStatus(PaymentStatusWrapper.getPaymentStatus(info.getPaymentStatus()));
			scheduleItem.getStatus().setDescription(info.getPaymentStatusDesc());
			scheduleItem.setRejectionCause(info.getExecStatus().getNonExecReasonDesc());

			scheduleItem.setExternalId(EntityIdHelper.createAutoSubscriptionPaymentEntityId(autoPaymentRc, autoSubscription));
			items.add(scheduleItem);
		}

		return items;

	}

	/**
	 * Возвращает детальную информацию по совершенному платежу.
	 *
	 * @param ifxRq запрос
	 * @param ifxRs_type cструктутра IFXRs_Type
	 * @return детальную информацию.
	 */
	public GroupResult<Long, AutoSubscriptionDetailInfo> getAutoPaymentDetailInfo(IFXRq_Type ifxRq, IFXRs_Type ifxRs_type) throws GateLogicException, GateException
	{
		GetAutoPaymentDetailInfoRs_Type responce = ifxRs_type.getGetAutoPaymentDetailInfoRs();
		Status_Type status = responce.getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, GetAutoPaymentDetailInfoRs_Type.class, ifxRq);
		}

		AutoPaymentRec_Type autoPaymentRec[] = responce.getAutoPaymentRec();

		GroupResult<Long, AutoSubscriptionDetailInfo> results = new GroupResult<Long, AutoSubscriptionDetailInfo>();
		if (autoPaymentRec == null)
			return results;

		for (int i = 0; i < autoPaymentRec.length; i++)
		{
			AutoPaymentRec_Type autoPaymentRc = autoPaymentRec[i];

			// На данный момент автопоиски не должны обрабатываться
			SummaKindCodeASAP_Type summaKindCode = autoPaymentRc.getAutoSubscriptionInfo().getSummaKindCode();
			if(SummaKindCodeASAP_Type.BY_BILLING_BASKET.equals(summaKindCode))
				continue;

			AutoSubscription autoSubscription = createAutoSubscription(autoPaymentRc);
			AutoSubscriptionDetailInfo payment = getAbstractTransfer(autoSubscription, autoPaymentRc);

			results.putResult(Long.parseLong(autoPaymentRc.getAutoPaymentId().getPaymentId()), payment);
		}

		return results;
	}
}
