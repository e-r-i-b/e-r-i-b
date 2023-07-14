package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.ima.IMAccountState;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ima.IMAccountAbstractImpl;
import com.rssl.phizicgate.esberibgate.ima.IMAccountImpl;
import com.rssl.phizicgate.esberibgate.ima.IMAccountTransactionImpl;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.ClientImpl;
import com.rssl.phizicgate.esberibgate.types.IMAOperationTypeWrapper;
import com.rssl.phizicgate.esberibgate.types.IMAccountStateWrapper;
import com.rssl.phizicgate.esberibgate.types.LimitType;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.*;

/**
 * @author Balovtsev
 * @created 27.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class IMAResponseSerializer extends BaseResponseSerializer
{
	private static final String IMACCOUNT_ABSTRACT_NOT_FOUND_ERROR= "Не пришли результаты запроса на получение короткой выписки по ОМС №";
	/**
	 * Заполнение информации по короткой выписке ОМС.
	 * @param ifxRq запрос
	 * @param ifxRs ответ шины
	 * @param imAccount ОМС для которого получена выписка
	 * @return выписка для ОМС
	 */
	public IMAccountAbstract fillShortIMAccountAbstract(IFXRq_Type ifxRq, IFXRs_Type ifxRs, IMAccount imAccount) throws GateException, GateLogicException
	{
		BankAcctStmtInqRs_Type response_type = ifxRs.getBankAcctStmtInqRs();
		if (response_type == null)
			throw new GateException(IMACCOUNT_ABSTRACT_NOT_FOUND_ERROR+imAccount.getNumber());

		IMSAcctRec_Type[] records = response_type.getIMSAcctRec();
		IMSAcctRec_Type record_type = findInIMAccountsByNumber(imAccount.getNumber(), records);
		if (record_type == null)
			throw new GateException(IMACCOUNT_ABSTRACT_NOT_FOUND_ERROR+imAccount.getNumber());

		//Пришедшее сообщение об ошибке будет отображено пользователю при попытке посмотреть короткую выписку
		if (record_type.getStatus().getStatusCode() != CORRECT_MESSAGE_STATUS)
			ESBERIBExceptionStatisticHelper.throwErrorResponse(record_type.getStatus(), IMSAcctRec_Type.class, ifxRq);

		return fillShortIMAccountAbstract(record_type.getBankAcctStmtRec());
	}

	private IMAccountAbstract fillShortIMAccountAbstract(BankAcctStmtRec_Type[] rec_types) throws GateException
	{
		IMAccountAbstractImpl imAAbstract  = new IMAccountAbstractImpl();
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();

		if(rec_types != null)
		{
			for (BankAcctStmtRec_Type rec_type : rec_types)
			{
				IMAccountTransactionImpl transaction = new IMAccountTransactionImpl();

				StmtSummAmt_Type amount_type = rec_type.getStmtSummAmt();
				if (amount_type == null)
					throw new GateException("Не пришла информация об операции в выписке по ОМС.");
				Money money = safeCreateMoney(amount_type.getCurAmt(),getCurrencyByString(amount_type.getCurAmtCur()));
				//Списание
				if (amount_type.getStmtSummType().equals("1"))
				{
					transaction.setCreditSum(money);
				}
				//Зачисление
				else
				{
					transaction.setDebitSum(money);
				}

				Date date = null;
				try
				{
					date = DateHelper.fromXMlDateToDate(rec_type.getEffDate());
				}
				catch (ParseException e)
				{
					log.error("Ошибка преобразования даты проведения операции.", e);
				}

				transaction.setDate(DateHelper.toCalendar(date));
				/*
				 * TODO В пилотной версии не возвращается
				 * transaction.setCorrespondentAccount( amount_type.getContrAccount() );
				 */

				//Признак: операция с металлом или денежными средствами
				IMAOperConvInfo_Type imaOperConvInfo = rec_type.getIMAOperConvInfo();
			    if (imaOperConvInfo != null)
			    {
					if (imaOperConvInfo.getCurType() != null)
						transaction.setOperationType(IMAOperationTypeWrapper.getIMAOperationType(imaOperConvInfo.getCurType()));
					//Сумма операции в рублях
				    if (imaOperConvInfo.getCurAmt() != null)
				    {
					    String acctCurStr = imaOperConvInfo.getAcctCur();
					    Currency acctCur = (StringHelper.isNotEmpty(acctCurStr)) ? getCurrencyByString(acctCurStr) : getCurrencyByString("RUR");

					    Money operationRurSumm = safeCreateMoney(imaOperConvInfo.getCurAmt(),acctCur);
						transaction.setOperationRurSumm(operationRurSumm);
				    }
				    else
					    throw new GateException("Не пришла информация о сумме операции в рублях при получении выписки по ОМС.");
			    }
				transactions.add(transaction);
			}
		}
		imAAbstract.setTransactions(transactions);
		return imAAbstract;
	}

	/**
	* Заполнение информации по расширенной выписке ОМС.
	 * @param ifxRq запрос
	* @param ifxRs ответ шины
	* @param imAccount ОМС для которого получена выписка
	* @return выписка для ОМС
	*/
	public IMAccountAbstract fillFullIMAccountAbstract(IFXRq_Type ifxRq, IFXRs_Type ifxRs, IMAccount imAccount) throws GateException, GateLogicException
	{
		if(imAccount == null || ifxRs == null)
		{
			return null;
		}

		BankAcctFullStmtInqRs_Type response_type = ifxRs.getBankAcctFullStmtInqRs();
		IMSAcctRec_Type[] records = response_type.getIMSAcctRec();

		IMSAcctRec_Type record_type = findInIMAccountsByNumber(imAccount.getNumber(), records);

		if (record_type != null)
		{
			if (record_type.getStatus().getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				BankAcctFullStmtRec_Type[] rec_type = record_type.getBankAcctFullStmtRec();
				BankAcctFullStmtInfo_Type info_type = record_type.getBankAcctFullStmtInfo();

				return fillFullIMAccountAbstract(rec_type, info_type);
			}
			ESBERIBExceptionStatisticHelper.throwErrorResponse(record_type.getStatus(), IMSAcctRec_Type.class, ifxRq);
		}
		throw new GateException("Не пришли результаты запроса на получение расширенной выписки по ОМС №"+imAccount.getNumber());
	}

	/**
	 * Поиск результатов (короткой) выписки по ОМС в пришедшем сообщении по номеру ОМС
	 * @param number
	 * @param record_types
	 * @return
	 */
	private IMSAcctRec_Type findInIMAccountsByNumber(String number, IMSAcctRec_Type... record_types)
	{
		for(IMSAcctRec_Type record_type : record_types)
		{
			if(record_type.getBankAcctId().getAcctId().equals(number))
				return record_type;
		}
		return null;
	}

	private IMAccountAbstract fillFullIMAccountAbstract(BankAcctFullStmtRec_Type[] rec_type, BankAcctFullStmtInfo_Type info_type)
	{
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();

		final Currency physicalForm = getCurrencyByString( info_type.getCurAmtCur() );
		final Currency    paperForm = getCurrencyByString( "RUB" );

		if(rec_type != null)
		{
			for(BankAcctFullStmtRec_Type transaction : rec_type)
			{
				IMAccountTransactionImpl mutating = new IMAccountTransactionImpl();
				mutating.setOperationCode(transaction.getCode());
				mutating.setOperationNumber(transaction.getNumber());
				mutating.setCorrespondentAccount(transaction.getCorAcc());
				mutating.setDate(parseCalendar(transaction.getEffDate()));
				mutating.setDocumentNumber(transaction.getDocumentNumber());

				mutating.setBalance(new Money( transaction.getBalance(), physicalForm ));
				mutating.setCreditSum(new Money( transaction.getAmt(),   physicalForm ));

				mutating.setCreditSumInPhizicalForm( new Money( transaction.getAmtPhiz(),   physicalForm ) );
				mutating.setBalanceInPhizicalForm( new Money( transaction.getBalancePhiz(), physicalForm ) );
				transactions.add(mutating);
			}
		}

		IMAccountAbstractImpl abstractImpl = new IMAccountAbstractImpl();
		abstractImpl.setRate( info_type.getRate() );

		abstractImpl.setToDate(   parseCalendar(info_type.getToDate())   );
		abstractImpl.setFromDate( parseCalendar(info_type.getFromDate()) );

		abstractImpl.setOpeningBalance( new Money( info_type.getStartBalance().getAmt(), physicalForm ) );
		abstractImpl.setClosingBalance( new Money( info_type.getEndBalance().getAmt(),   physicalForm ) );

		abstractImpl.setOpeningBalanceInRub( new Money( info_type.getStartBalance().getAmtRub(), paperForm ) );
		abstractImpl.setClosingBalanceInRub( new Money( info_type.getEndBalance().getAmtRub(),   paperForm ) );

		abstractImpl.setPreviousOperationDate( parseCalendar(info_type.getLastDate()) );

		abstractImpl.setTransactions(transactions);
		return abstractImpl;
	}

	/**
	 * Заполнение списка ОМС клиента
	 * @param ifxRs - тип ответа, содержащего информацию о ОМС
	 * @param loginId - номер логина пользователя
	 * @return заполненный список ОМС клиента
	 */
	public List<IMAccount> extractIMAccountsFormResponse(IFXRs_Type ifxRs, Long loginId)
	{
		if (ifxRs == null)
			return Collections.emptyList();

	    BankAcctInqRs_Type response_type = ifxRs.getBankAcctInqRs();
		if(response_type == null || response_type.getStatus().getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			return Collections.emptyList();			
		}

		BankAcctRec_Type[] records = response_type.getBankAcctRec();
		if (records == null || records.length == 0)
		{
			return Collections.emptyList();
		}

		List<IMAccount> result = new ArrayList<IMAccount>(records.length);
		Map<String, IMAccount> imAccountMap = new HashMap<String, IMAccount>();
		for(BankAcctRec_Type record_type : records) {
			try
			{
				//ОМС
				IMAccount imAccount = migrateToImaccount(record_type, loginId);
				//Ранее добавленный такой же ОМС
				IMAccount existingImAccount = imAccountMap.get(imAccount.getNumber());
				if (existingImAccount != null) { //Пришёл дубль ОМС
					//Обновяем данные по ранее добавленному ОМС, если состояние счета изменилось на неактивное
					if (existingImAccount.getState() == IMAccountState.opened &&
						imAccount.getState() != IMAccountState.opened)
					{
						result.remove( existingImAccount );
					}
					//В противном случаем ранее добавленные данные по этому ОМС не меняем
					else continue;
				}
				result.add( imAccount );
				imAccountMap.put(imAccount.getNumber(), imAccount);
			}
			catch (Exception e)
			{
				if (record_type != null && record_type.getBankAcctId() != null)
					log.error("Ошибка при заполнении ОМС №" + record_type.getBankAcctId().getAcctId(), e);
				else
					log.error("Ошибка при заполнении ОМС", e);
			}
		}
		return result;
	}

	private IMAccount migrateToImaccount(BankAcctRec_Type record_type, Long loginId) throws GateLogicException, GateException
	{
		BankAcctId_Type acctIds  = record_type.getBankAcctId();
		AcctBal_Type[]  acctBals = record_type.getAcctBal();

		if(acctBals == null || acctIds == null)
		{
			return null;
		}

		IMAccountImpl imAccount = new IMAccountImpl();

		String id = EntityIdHelper.createIMAccountCompositeId(record_type, loginId);
		Currency currency = getCurrencyByString(acctIds.getAcctCur());
		imAccount.setId( id );
		imAccount.setNumber( acctIds.getAcctId() );
		imAccount.setOpenDate( parseCalendar(acctIds.getStartDate()) );
		imAccount.setCurrency( currency );
		imAccount.setName( acctIds.getAcctName() );
		imAccount.setOffice( getOffice(record_type.getBankInfo()) );

		ImsAcctInfo_Type imsAcctInfo = record_type.getImsAcctInfo();
		if (imsAcctInfo != null)
		{
			imAccount.setState(IMAccountStateWrapper.getIMAccountState(imsAcctInfo.getStatus()));
			imAccount.setClosingDate(parseCalendar(imsAcctInfo.getEndDate()));
			imAccount.setAgreementNumber(imsAcctInfo.getAgreementNumber());
		}

		setLimits(imAccount, acctBals, currency);

		return imAccount;
	}

	private void setLimits(IMAccountImpl imAccount, AcctBal_Type[] acctBals, Currency currency)
	{
		if (acctBals == null)
			return;
		for (AcctBal_Type acctBal : acctBals)
		{
			if (acctBal.getBalType().equals(LimitType.Avail.toString()))
			{
				imAccount.setBalance(safeCreateMoney(acctBal.getCurAmt(), currency));
			}
			if (acctBal.getBalType().equals(LimitType.AvailCash.toString()))
			{
				imAccount.setMaxSumWrite(safeCreateMoney(acctBal.getCurAmt(), currency));
			}
		}
	}

	/**
	 * Заполнение списка ОМС
	 * @param ifxRs - полученный ответ
	 * @param externalIds - список идентификаторов ОМС
	 * @return групповая информация по ОМС (<идентификатор, ОМС>)
	 */
	@SuppressWarnings({"ThrowableInstanceNeverThrown"})
	public GroupResult<String, IMAccount> fillIMAccounts(IFXRs_Type ifxRs, String... externalIds)
	{
		ImaAcctInRs_Type response_type = ifxRs.getImaAcctInRs();
		if(response_type == null)
		{
			return GroupResultHelper.getOneErrorResult(externalIds,
					new GateException("Не получена информация по ОМС с идентификаторами " + StringUtils.join(externalIds, ';')));
		}

		ImsRec_Type[] records = response_type.getImsRec();
		if(records == null || records.length == 0)
		{
			return GroupResultHelper.getOneErrorResult(externalIds,
					new GateException("Не получена информация по ОМС с идентификаторами " + StringUtils.join(externalIds, ';')));
		}

		GroupResult<String, IMAccount> result = new GroupResult<String, IMAccount>();
		for(ImsRec_Type record : records)
		{
			if(record == null || record.getImsAcctId() == null)
			{
				return GroupResultHelper.getOneErrorResult(externalIds,
				    new GateException("Нарушение спецификации со стороны КСШ. Неверная информация по ОМС с идентификаторами " + StringUtils.join(externalIds, ';')));
			}
			String imAccountId = findId(record.getImsAcctId().getAcctId(), externalIds);
			Status_Type status = record.getStatus();
			EntityCompositeId imaCompositeId = EntityIdHelper.getCommonCompositeId(imAccountId);
			if (status.getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				try
				{
					IMAccount imAccount = migrateToImaccount(record, imaCompositeId.getLoginId());
					result.putResult(imAccountId, imAccount);
					StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);
					storedResourcesService.updateStoredIMAccount(imaCompositeId.getLoginId(), imAccount);
				}
				catch (IKFLException e)
				{
					result.putException(imAccountId, e);
				}
			}
			else
			{
				if (OFFLINE_SYSTEM_STATUSES.contains(status.getStatusCode()))
					ESBERIBExceptionStatisticHelper.addOfflineError(result, imAccountId, status, ImsRec_Type.class, imaCompositeId);
				else
					ESBERIBExceptionStatisticHelper.addError(result, imAccountId, status, ImsRec_Type.class, imaCompositeId);
				log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". ОМС ID "
						+ imAccountId + ". " + status.getStatusDesc());
			}
		}
		return result;
	}

	private IMAccount migrateToImaccount(ImsRec_Type record, Long loginId) throws GateLogicException, GateException
	{
		IMAccountImpl imAccount = new IMAccountImpl();

		ImsAcctId_Type   main = record.getImsAcctId();
		ImsAcctInfo_Type info = record.getImsAcctInfo();

		String id = EntityIdHelper.createIMAccountCompositeId(record, loginId);
		Currency currency = getCurrencyByString( info.getAcctCur() );

		imAccount.setId(id);

		imAccount.setState(IMAccountStateWrapper.getIMAccountState(info.getStatus()));
		imAccount.setCurrency(currency);
		imAccount.setOpenDate(parseCalendar(info.getStartDate()));
		imAccount.setNumber(main.getAcctId());
		imAccount.setName(info.getAcctName());
		imAccount.setAgreementNumber(info.getAgreementNumber());
		imAccount.setBalance(safeCreateMoney(info.getAmount(), currency));
		imAccount.setOffice(getOffice(info.getBankInfo()));
		imAccount.setClosingDate(parseCalendar(info.getEndDate()));
		if (info.getMaxSumWrite() != null)
			imAccount.setMaxSumWrite(safeCreateMoney(info.getMaxSumWrite(), currency));

		return imAccount;
	}

	/**
	 * Метод для получения данных клиента, полученных на основе ОМС.
	 * @param ifxRs ответ
	 * @param imAccounts список омс
	 * @return владелец ОМС
	 */
	@SuppressWarnings({"ThrowableInstanceNeverThrown"})
	public GroupResult<IMAccount, Client> fillClientFromIMAResponse(IFXRs_Type ifxRs, IMAccount... imAccounts)
	{
		GroupResult<IMAccount, Client> res = new GroupResult<IMAccount, Client>();
		ImsRec_Type[] imsRecs = ifxRs.getImaAcctInRs().getImsRec();

		for (ImsRec_Type imsRec : imsRecs)
		{
			IMAccount imAccount = null;
			try
			{
				imAccount = findObject(imsRec.getImsAcctId().getAcctId(), imAccounts);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
				continue;
			}
			
			Status_Type status = imsRec.getStatus();
			if (status.getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				CustRec_Type custRec = imsRec.getImsAcctInfo().getCustRec();
				if (custRec == null)
				{
					res.putException(imAccount, new GateLogicException("Информация по клиенту недоступна."));
					continue;
				}

				CustInfo_Type custInfo = custRec.getCustInfo();

				ClientImpl client = fillClient(custInfo);
				client.setId(custRec.getCustId());

				res.putResult(imAccount, client);
			}
			else
			{
				ESBERIBExceptionStatisticHelper.addError(res, imAccount, status, ImsRec_Type.class, EntityIdHelper.getCommonCompositeId(imAccount.getId()));
				log.error("Вернулось сообщение с ошибкой. Ошибка номер " + status.getStatusCode() + ". ОМС № "
						+ imAccount.getNumber() + ". " + status.getStatusDesc());
			}
		}

		return res;
	}

	/**
	 * Получение офиса обслуживания
	 * @param ifxRs ответ шины
	 * @return Офис
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Office getFilledOfficeFromIMARequest(IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		ImaAcctInRs_Type response_type = ifxRs.getImaAcctInRs();
		if (ifxRs == null || response_type == null)
		{
			return null;
		}
		
		ImsRec_Type imsRec_type = response_type.getImsRec(0);
		BankInfo_Type info_type = null;

		if(imsRec_type != null && imsRec_type.getStatus().getStatusCode() == CORRECT_MESSAGE_STATUS)
		{
			info_type = response_type.getImsRec(0).getImsAcctId().getBankInfo();
		}

		return fillOffice(info_type);
	}

	public Office getOffice(BankInfo_Type info_type) throws GateLogicException, GateException
	{
		return getCorrectedOffice(info_type);
	}

	/**
	 * Заполнение офиса, которому принадлежит ОМС
	 * @param info_type - полученный ответ
	 * @return офис
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	private Office fillOffice(BankInfo_Type info_type) throws GateException, GateLogicException
	{
		Office office = getOffice(info_type);

		if (office == null)
			throw new GateException("Не удалось получить офис.");
		return office;
	}
}
