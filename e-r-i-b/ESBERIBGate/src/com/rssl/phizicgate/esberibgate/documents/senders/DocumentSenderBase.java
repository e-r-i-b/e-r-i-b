package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateSendTimeOutException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.statistics.exception.ExternalSystemErrorCodeException;
import com.rssl.phizicgate.esberibgate.statistics.exception.ExternalSystemTimeoutErrorCodeException;
import com.rssl.phizicgate.esberibgate.ws.TransportProvider;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 20.10.2010
 * @ $Author$
 * @ $Revision$
 *
 * базовый сендер для документов шины.
 */
public abstract class DocumentSenderBase extends AbstractDocumentSenderBase
{
	protected final PaymentsRequestHelper paymentsRequestHelper;
	protected static final long UNKNOW_DOCUMENT_STATE_ERROR_CODE = -105;
	protected static final String UNAVAILABLE_OPERATION_TRY_LATER = "Данная операция временно недоступна. Пожалуйста, повторите попытку позже";

	public DocumentSenderBase(GateFactory factory)
	{
		super(factory);
		paymentsRequestHelper = new PaymentsRequestHelper(getFactory());
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = createRequest(document);
		IFXRs_Type ifxRs = new IFXRs_Type();
		try
		{
			ifxRs = doRequest(ifxRq);
		}
		catch (GateTimeOutException e)
		{
			throw new GateSendTimeOutException(e);
		}

		try
		{
			processResponse(document, ifxRs);
		}
		catch (ExternalSystemErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
		catch (ExternalSystemTimeoutErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = createRepeatExecRequest(document);
		IFXRs_Type ifxRs = doRequest(ifxRq);
		try
		{
			processResponse(document, ifxRs);
		}
		catch (ExternalSystemErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
		catch (ExternalSystemTimeoutErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
	}

	protected IFXRs_Type doRequest(IFXRq_Type ifxRq) throws GateException, GateLogicException
	{
		return TransportProvider.doRequest(ifxRq);
	}

	/**
	 * сформировать запрос на исполнение документа.
	 * @param document данные о документе
	 * @return запрос
	 */
	protected abstract IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException;

	/**
	 * сформировать запрос на исполнение документа.
	 * @param document документ.
	 * @param requestHelper хелпер для генерации запроса.
	 * @return запрос.
	 */
	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		return createRequest(document);
	}

	/**
	 * сформировать запрос на повторное исполнение документа.
	 * @param document документ.
	 * @return запрос.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public IFXRq_Type createRepeatExecRequest(GateDocument document) throws GateException, GateLogicException
	{
		throw new GateException("Повторная отправка не поддерживается");
	}
	/**
	 * обработать ответ с результатом исполнения запроса
	 * @param document документ
	 * @param ifxRs ответ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected abstract void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException;

	/**
	 * Кидает ошибку GateLogicException, если описание ошибки пустое, то отпревлят дефолтное сообщение
	 * @param statusType - статус ответа
	 * @param messageClass класс содержательной части ответа (внутренности IFXRs)
	 * @throws GateLogicException
	 */
	protected void throwGateLogicException(Status_Type statusType, Class messageClass) throws GateLogicException
	{
		String defaultMessage = StringHelper.isEmpty(statusType.getStatusDesc()) ? UNAVAILABLE_OPERATION_TRY_LATER : statusType.getStatusDesc();
		ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, messageClass, defaultMessage);
	}

	protected void throwTimeoutException(Status_Type statusType, Class messageClass) throws GateLogicException
	{
		ESBERIBExceptionStatisticHelper.throwTimeoutErrorResponse(statusType, messageClass, statusType.getStatusDesc());
	}

	protected IFXRq_Type createPrepareRequest(GateDocument document) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("Для документа " + document.getType() + " не предусмотрена подготовка.");
	}

	protected void processPrepareResponse(GateDocument document, IFXRs_Type ifxRs) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("Для документа " + document.getType() + " не предусмотрена подготовка.");
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = createPrepareRequest(document);
		IFXRs_Type ifxRs = doRequest(ifxRq);
		try
		{
			processPrepareResponse(document, ifxRs);
		}
		catch (ExternalSystemErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
		catch (ExternalSystemTimeoutErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	/**
	 * получить карту по номеру
	 * @param client владелец или инициатор запроса
	 * @param number номер счета
	 * @param office офис
	 * @return карта
	 */
	protected Card getCard(Client client, String number, Office office) throws GateException, GateLogicException
	{
		BankrollService bankrollService = getFactory().service(BankrollService.class);
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(number, office)));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	/**
	 * получить карту по номеру, независимо от тех. перерывов
	 * @param client владелец или инициатор запроса
	 * @param number номер счета
	 * @param office офис
	 * @return карта
	 */
	protected Card getCardOfflineSupported(Client client, String number, Office office) throws GateException, GateLogicException
	{
		BackRefBankrollService backRefBankrollService = getFactory().service(BackRefBankrollService.class);
		//проверяем way на недоступность.
		try
		{
			ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
			ExternalSystemHelper.check(eribConfig.getEsbERIBCardSystemId99Way());
		}
		catch (InactiveExternalSystemException ignored)
		{
			//way недоступен - ищем данные в базе.
			return backRefBankrollService.getStoredCard(client.getInternalOwnerId(), number);
		}
		return getCard(client, number, office);
	}

	/**
	 * получить счет по номеру
	 * @param number номер счета
	 * @param office офис
	 * @return счет
	 */
	protected Account getAccount(String number, Office office) throws GateException, GateLogicException
	{
		BankrollService bankrollService = getFactory().service(BankrollService.class);
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(new Pair<String, Office>(number, office)));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	/**
	 * получить ОМС по номеру
	 * @param client владелец или инициатор запроса
	 * @param number номер счета
	 * @return счет
	 */
	protected IMAccount getIMAccount(Client client, String number) throws GateException, GateLogicException
	{
		IMAccountService imAccountService = getFactory().service(IMAccountService.class);

		try
		{
			return GroupResultHelper.getOneResult(imAccountService.getIMAccountByNumber(client, number));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	/**
	 * заполнить стpуктуру информация по карте.
	 * @param card карта
	 * @param owner владелец
	 * @param expireDate дата закрытия
	 * @return CardAcctId_Type
	 */
	protected CardAcctId_Type createCardAcctId(Card card, Client owner, Calendar expireDate) throws GateLogicException, GateException
	{
		//TODO учитываеть isLongOfferMode(для заполнения ТБ ОСБ и ВСП) в задаче Шлюз к шине: отправка заявок на длительное поручение
		return paymentsRequestHelper.createCardAcctId(card, owner, expireDate, true, true);
	}

	/**
	 * заполнить стpуктуру информация по счету.
	 * @param account счет
	 * @param owner владелец
	 * @return DepAcctId_Type
	 */
	protected DepAcctId_Type createDepAcctId(Account account, Client owner) throws GateLogicException, GateException
	{
		//TODO учитываеть isLongOfferMode(для заполнения ТБ ОСБ и ВСП) в задаче Шлюз к шине: отправка заявок на длительное поручение
		return paymentsRequestHelper.createDepAcctId(account, owner);
	}
	/**
	 * заполнить стpуктуру информация по счету списания.
	 * @param account счет
	 * @param owner владелец
	 * @return DepAcctId_Type
	 */
	protected DepAcctId_Type createDepAcctIdFrom(Account account, Client owner) throws GateLogicException, GateException
	{
		return paymentsRequestHelper.createDepAcctIdFrom(account, owner);
	}


	/**
	 * Заполнить информацию по кредиту
	 * @param loanExternalId - id кредита
	 * @param accountNumber - Номер ссудного счета
	 * @param agreementNumber - Номер кредитного договора
	 * @return LoanAcctId_Type
	 */
	protected LoanAcctId_Type createLoanAcctId(String loanExternalId, String accountNumber, String agreementNumber) throws GateLogicException, GateException
	{
		return paymentsRequestHelper.createLoanAcctId(loanExternalId, accountNumber, agreementNumber);
	}

	/**
	 * Признак того, что пришёл ответ на запрос уточнения статуса операции в АБС из ЕРИБ
	 * @param ifxRs
	 * @return
	 */
	protected boolean isXferOperStatusInfoRs(IFXRs_Type ifxRs)
	{
		return ifxRs.getXferOperStatusInfoRs() != null;
	}

	protected Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}
}
