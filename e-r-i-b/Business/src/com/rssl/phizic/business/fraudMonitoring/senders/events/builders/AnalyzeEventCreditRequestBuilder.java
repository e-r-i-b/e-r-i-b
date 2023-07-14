package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.etsm.offer.OfferConfirmed;
import com.rssl.phizic.business.etsm.offer.OfferToFraudInitializationData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.ClientDefinedFactConstants;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Moshenko
 * @ created 28.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEventCreditRequestBuilder extends AnalyzeEventRequestBuilderBase
{
	private static final PersonService personService = new PersonService();

	private OfferConfirmed offer;

	public AnalyzeEventCreditRequestBuilder append(OfferToFraudInitializationData data)
	{
		this.offer = data.getOfferConfirmed();
		return this;
	}

	protected EventData createEventData() throws GateException, GateLogicException
	{
		EventData eventData = super.createEventData();

		eventData.setTransactionData(getTransactionData(offer));
		try
		{
			List<ClientDefinedFact> defList = getClientDefinedFactArr(offer);
			eventData.setClientDefinedAttributeList(defList.toArray(new ClientDefinedFact[defList.size()]));
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		return eventData;

	}

	private TransactionData getTransactionData(OfferConfirmed offer)
	{
		TransactionData transactionData = new TransactionData();

		Amount amount = new Amount();
		//Валюта кредита. Пока только в рублях
		amount.setCurrency("RUB");
		//Сумма кредита
		amount.setAmount(offer.getAltAmount().longValue());
		transactionData.setAmount(amount);
		return  transactionData;
	}

	private List<ClientDefinedFact> getClientDefinedFactArr(OfferConfirmed offer) throws BusinessException, GateLogicException, GateException
	{
		List<ClientDefinedFact> defList = new ArrayList<ClientDefinedFact>();
		//ФИО клиента [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_FIO_FIELD_NAME , StringHelper.getEmptyIfNull(offer.getBorrower()), DataType.STRING));
		//Фамилия клиента [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_SURNAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(offer.getLastName()), DataType.STRING));
		//Имя клиента [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_FIRST_NAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(offer.getFirstName()), DataType.STRING));
		//Отчество клиента [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_PATRNAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(offer.getMiddleName()), DataType.STRING));
		ActivePerson person = personService.findByLogin(offer.getClientLoginId());
		//Дата рождения клиента [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_BIRTHDAY_FIELD_NAME, DateHelper.toXMLDateFormat2(person.getBirthDay()), DataType.STRING));
		//Номер ДУЛ [1]

		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_DOCUMENT_NUMBER_FIELD_NAME, (StringHelper.getEmptyIfNull(offer.getIdSeries()) + " " + StringHelper.getEmptyIfNull(offer.getIdNum())).trim(), DataType.STRING));
		//Тип ДУЛ [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_DOCUMENT_TYPE_FIELD_NAME, offer.getIdType(), DataType.STRING));
		if (StringHelper.isNotEmpty(offer.getAccountNumber()))
		{
			//омер счета получателя [0]
			if (offer.getAccountNumber().length() > 19)
				defList.add(new ClientDefinedFact(ClientDefinedFactConstants.TO_ACCOUNT_NUMBER_FIELD_NAME, StringHelper.getEmptyIfNull(offer.getAccountNumber()), DataType.STRING));
			else
			//Номер карты получателя [0]
				defList.add(new ClientDefinedFact(ClientDefinedFactConstants.TO_CARD_NUMBER_FIELD_NAME, StringHelper.getEmptyIfNull(offer.getAccountNumber()), DataType.STRING));
		}

		if (StringHelper.isNotEmpty(offer.getAccountNumber()))
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			List<Account> accounts = bankrollService.getClientAccounts(person.asClient());
			if (accounts != null)
				for (Account account:accounts)
				{
					if(StringHelper.equals(account.getNumber(),offer.getAccountNumber()))
					{
						//БИК получателя [0]
						defList.add(new ClientDefinedFact(ClientDefinedFactConstants.RECEIVER_BANK_BIC_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(account.getOffice().getBIC()), DataType.STRING));
						break;
					}

				}
		}
		//ФИО получател [0]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.DEST_PERSON_FIO_FIELD_NAME, StringHelper.getEmptyIfNull(offer.getBorrower()), DataType.STRING));
		//Срок кредита [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.LOAN_TERM_FIELD_NAME, offer.getAltPeriod().toString(), DataType.STRING));
		//Процентная ставка [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.LOAN_INTEREST_RATE_FIELD_NAME, offer.getAltInterestRate().toString(), DataType.STRING));
		//Автоматическая выдача [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.LOAN_AUTOMATIC_ISSUE_FIELD_NAME, ClientDefinedFactConstants.YES, DataType.STRING));
		//'ВСП' [1]    НННН/НН
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.PERSON_VSP_NUMBER_FIELD_NAME, PersonHelper.getPersonOSBVSP(person), DataType.STRING));
		//OFF-Line загрузка [1]
		defList.add(new ClientDefinedFact(ClientDefinedFactConstants.OFFLINE_LOAD_FIELD_NAME, ClientDefinedFactConstants.NO, DataType.STRING));
		return defList;
	}


	@Override
	protected String getEventDescription()
	{
		return "Согласие на выдачу кредита";
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.REQUEST_CREDIT;
	}
}
