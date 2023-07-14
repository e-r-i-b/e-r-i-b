package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.bankroll.AccountAbstractImpl;
import com.rssl.phizicgate.esberibgate.bankroll.AccountTransactionImpl;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.*;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 @author Pankin
 @ created 27.12.2010
 @ $Author$
 @ $Revision$
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"})
public class AccountResponseSerializer extends BaseResponseSerializer
{
	private static final String ACCOUNT_REGEX = "^(423|426|40817|40820)\\d+";

	private static final BigDecimal ACCOUNT_RATE_FACTOR = new BigDecimal(100);

	// ���� ��������, ������������ ����� ��� ���������� ������� BUG032427
	private static final Calendar DEMAND_ACCOUNT_CLOSING_DATE = parseCalendar("1800-01-01");

	/**
	 * ���������� ������ ������, ����������� �� GFL �������
	 * @param ifxRs - ���������� �����
	 * @param clientId - Id ������� �������� ����������� �����
	 * @return ������ ������
	 */
	public List<Pair<Account, AdditionalProductData>> fillAccounts(IFXRs_Type ifxRs, Long clientId)
	{
		if (ifxRs == null)
			return Collections.emptyList();

		DepAcctRec_Type[] accRecs = ifxRs.getBankAcctInqRs().getDepAcctRec();
		if ((accRecs == null) || (accRecs.length == 0))
			return Collections.emptyList();

		List<Pair<Account, AdditionalProductData>> accounts = new ArrayList<Pair<Account, AdditionalProductData>>(accRecs.length);
		for (DepAcctRec_Type accRec : accRecs)
		{
			try
			{
				if (accRec.getDepAcctId().getAcctId().matches(ACCOUNT_REGEX))
				{
					AccountImpl account = fillAccount(accRec);
					// ��������� ID
					account.setId(EntityIdHelper.createAccountCompositeId(accRec, clientId));
					if (account.getOffice() == null)
						account.setOffice(getOffice(accRec.getBankInfo()));
					accounts.add(new Pair<Account, AdditionalProductData>(account, fillProductPermission(accRec.getBankAcctPermiss())));
				}
			}
			catch (Exception e)
			{
				if (accRec != null && accRec.getDepAcctId() != null)
					log.error("������ ��� ���������� ����� �" + accRec.getDepAcctId().getAcctId(), e);
				else
					log.error("������ ��� ���������� �����", e);
			}
		}
		//������� ����� ������, ���� ������� ����, �� ��������� (ENH031163: ����� ������������ ������������� ����� � ������ GFL) 
		return getAccountsWithoutDouble(accounts);
	}

	/**
	 * ���������� ������ ������ � ��������������� ������� �� ����, ��� ���� ��������� ����� �� ���������
	 * (ENH031163: ����� ������������ ������������� ����� � ������ GFL)
	 * @param accounts ������ ������ � ���.������� � ���������� �������
	 * @return List<Pair<Account, AdditionalProductData>> ������ ������ � ��������������� ������� �� ����, ��� ������
	 */
	private List<Pair<Account, AdditionalProductData>> getAccountsWithoutDouble(List<Pair<Account, AdditionalProductData>> accounts)
	{
		List<Pair<Account, AdditionalProductData>> accountsWithoutDouble = new ArrayList<Pair<Account, AdditionalProductData>>(accounts.size());

		if (CollectionUtils.isNotEmpty(accounts)) 
		{
			Map<String,Pair<Account, AdditionalProductData>> existingAccountMap = new HashMap <String,Pair<Account, AdditionalProductData>>();

			for (Pair<Account, AdditionalProductData> accountPair : accounts) {
				//����
				Account accountItem = accountPair.getFirst();
				//��������� �����
				ProductPermissionImpl productPermissionItem = (ProductPermissionImpl) accountPair.getSecond();

				//�������� � �� ���������� �� ��� ���� ����
				Pair<Account, AdditionalProductData> existingAccountPair = existingAccountMap.get(accountItem.getNumber());

				if (existingAccountPair != null) { //������� ����� ����� ������������ �����
					//������ ����� ������������ �����
					Account existingAccount = existingAccountPair.getFirst();
					//������ �� ��������� ����� �����
					ProductPermissionImpl existingProductPermission = (ProductPermissionImpl) existingAccountPair.getSecond();

					//�������� ������ �� ����� ������������ ����� � ���� �������:
					//-��������� ����� ���������� �� ����������
					//-���������� ��������� �������� (�����)
					if ( //�������� ���������� �� ��������� ����� �� ����������
						 (existingAccount.getAccountState() == AccountState.OPENED && accountItem.getAccountState() != AccountState.OPENED) ||
					     //���������� �� ��������� ��������
						 (!existingProductPermission.showInES().equals(productPermissionItem.showInES())) ||
						 (!existingProductPermission.showInSbol().equals(productPermissionItem.showInSbol())) ) {
						accountsWithoutDouble.remove(existingAccountPair);
					}
					//� ��������� ������ ����� ����������� ������ �� ������.
					else continue;
				}
				accountsWithoutDouble.add(accountPair);
				existingAccountMap.put(accountItem.getNumber(), accountPair);
			}
		}
		return accountsWithoutDouble; 
	}

	/**
	 * ���������� ��������� ���������� �� ������
	 * @param ifxRs - ����� ���� (��������� ACC_DI ��������� ��������� ���������� �� ������)
	 * @param accountId - ������������� �����
	 * @param externalIds - �������������� ������
	 * @return ��������� ���������� � ������
	 */
	public GroupResult<String, Account> fillAccountByAccountInfo(IFXRs_Type ifxRs, String accountId, String... externalIds) throws GateException
	{
		GroupResult<String, Account> result = new GroupResult<String, Account>();
		DetailAcctInfo_Type[] detailAcctInfos = ifxRs.getAcctInfoRs().getDetailAcctInfo();

		for (DetailAcctInfo_Type detailAcctInfo : detailAcctInfos)
		{
			Status_Type status = detailAcctInfo.getStatus();

			//��� �������� ��� "�����" ���. ���������� �� �������� ������� �� ������������, ����� �� ������ � ���������� �� ������ �������
			if (status != null && status.getStatusCode() == OFFLINE_SYSTEM_STATUS_400)
			{
				ESBERIBExceptionStatisticHelper.addOfflineError(result, accountId, status, DetailAcctInfo_Type.class, EntityIdHelper.getCardOrAccountCompositeId(accountId));
				log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����" +
						  " � ID " + accountId + ". " + status.getStatusDesc());
				continue;
			}

			if (detailAcctInfo.getDepAcctId() == null)
			{
				throw new GateException("�������� ������������: ������������� ����� ����� null");
			}

			String externalId = findId(detailAcctInfo.getDepAcctId().getAcctId(), externalIds);

			if (status.getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				try
				{
					AccountImpl account = fillAccountFromDetailInfo(detailAcctInfo);
					Long loginId = EntityIdHelper.getCommonCompositeId(externalId).getLoginId();
					account.setId(EntityIdHelper.createAccountCompositeId(detailAcctInfo, loginId));
					StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);
					storedResourcesService.updateStoredAccount(loginId, account);

					CustRec_Type custRec = detailAcctInfo.getDepAccInfo().getCustRec();
					if (custRec != null)
					{
						CustInfo_Type custInfo = custRec.getCustInfo();
						ClientImpl client = fillClient(custInfo);
						client.setId(custRec.getCustId());
						account.setAccountClient(client);
					}

					result.putResult(externalId, account);
				}
				catch (IKFLException e)
				{
					result.putException(externalId, e);
				}
			}
			else
			{
				if (OFFLINE_SYSTEM_STATUSES.contains(status.getStatusCode()))
					ESBERIBExceptionStatisticHelper.addOfflineError(result, externalId, status, DetailAcctInfo_Type.class, EntityIdHelper.getCardOrAccountCompositeId(externalId));
				else
					ESBERIBExceptionStatisticHelper.addError(result, externalId, status, DetailAcctInfo_Type.class, EntityIdHelper.getCardOrAccountCompositeId(externalId));
				log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����" +
						" � ID " + externalId + ". " + status.getStatusDesc());
			}
		}

		return result;
	}

	private AccountImpl fillAccountFromDetailInfo(DetailAcctInfo_Type detailAcctInfo) throws GateLogicException, GateException
	{
		AccountImpl accountImpl = new AccountImpl();
		DepAccInfo_Type depAcctInfo = detailAcctInfo.getDepAccInfo();
		if (depAcctInfo == null)
			throw new GateException("�� ������ ��������� ���������� �� ������.");
		DepAcctId_Type depAcctId = detailAcctInfo.getDepAcctId();

		Currency currency = getCurrencyByString(depAcctInfo.getAcctCur());
		accountImpl.setId(depAcctId.getAcctId());

		accountImpl.setOffice(getOffice(depAcctInfo.getBankInfo()));
		accountImpl.setAccountState(AccountStateWrapper.getAccountState(depAcctInfo.getStatus()));
		accountImpl.setBalance(safeCreateMoney(depAcctInfo.getCurAmt(),currency));

		// BUG032515 �������� ��������� ������������ ���������/��������� �������� � ���� � � ��� ��������������
		accountImpl.setCreditAllowed(depAcctInfo.getIsDebitAllowed());

		accountImpl.setDebitAllowed(depAcctInfo.getIsCreditAllowed());

		accountImpl.setCurrency(currency);
		accountImpl.setInterestRate(getAccountRate(depAcctInfo));
		accountImpl.setDescription(depAcctInfo.getAcctName());

		if (depAcctInfo.getMaxSumWrite() != null)
			accountImpl.setMaxSumWrite(new Money(depAcctInfo.getMaxSumWrite(), currency));

		if (depAcctInfo.getIrreducibleAmt() != null)
			accountImpl.setMinimumBalance(new Money(depAcctInfo.getIrreducibleAmt(), currency));

		accountImpl.setNumber(depAcctId.getAcctId());
		String openDate = depAcctInfo.getOpenDate();

		if (!StringHelper.isEmpty(openDate))
			accountImpl.setOpenDate(parseCalendar(openDate));

		accountImpl.setKind(depAcctInfo.getAcctCode());
		accountImpl.setSubKind(depAcctInfo.getAcctSubCode());

        if (StringHelper.isNotEmpty(depAcctInfo.getClientKind()))
		    accountImpl.setClientKind(Long.valueOf(depAcctInfo.getClientKind()));

		accountImpl.setDemand(parseDemand(depAcctInfo.getOpenDate(), depAcctInfo.getEndDate()));
		accountImpl.setPassbook(depAcctInfo.getIsPassBook());
		accountImpl.setCloseDate(parseCalendar(depAcctInfo.getEndDate()));
		accountImpl.setCreditCrossAgencyAllowed(depAcctInfo.getIsCreditCrossAgencyAllowed());
		accountImpl.setDebitCrossAgencyAllowed(depAcctInfo.getIsDebitCrossAgencyAllowed());
		accountImpl.setProlongationAllowed(depAcctInfo.getIsProlongationAllowed());
		accountImpl.setClearBalance(depAcctInfo.getClearBalance());
		accountImpl.setMaxBalance(depAcctInfo.getMaxBalance());
		accountImpl.setProlongationDate(parseCalendar(depAcctInfo.getProlongationDate()));

		InterestOnDeposit_Type interestOnDeposit = depAcctInfo.getInterestOnDeposit();
		if (interestOnDeposit != null)
		{
			accountImpl.setInterestTransferAccount(interestOnDeposit.getAcctId());
			accountImpl.setInterestTransferCard(interestOnDeposit.getCardNum());
		}

		Long period = depAcctInfo.getPeriod();
		if (period != null)
		{
			accountImpl.setPeriod(new DateSpan(0, 0, period.intValue()));
		}

		return accountImpl;
	}

	private BigDecimal getAccountRate(DepAccInfo_Type depAcctInfo)
	{
		BigDecimal rate = depAcctInfo.getRate();
		if (rate == null)
			return null;


		// CHG032007: ����������� ���������� ������ �� ������
		// �� ���� ������ �������� � �����, � ��� ����� � ���������
		// ������� �� ���� 0.035 �� ����������� � 3.5
		return rate.multiply(ACCOUNT_RATE_FACTOR);
	}

	/**
	 * ���������� ��������� ���������� �� ������
	 * @param ifxRs - ����� ���� (��������� ACC_DI ��������� ��������� ���������� �� ������)
	 * @param acountInfos - ���� <����<����� �����, �������������>, �������������>
	 * @return ��������� ���������� � ������
	 */
	public GroupResult<Pair<String, Office>, Account> fillAccountByAccountInfo(IFXRs_Type ifxRs, Pair<Pair<String, Office>, String>... acountInfos) throws GateException
	{
		GroupResult<Pair<String, Office>, Account> result = new GroupResult<Pair<String, Office>, Account>();

		DetailAcctInfo_Type[] detailAcctInfos = ifxRs.getAcctInfoRs().getDetailAcctInfo();

		for (DetailAcctInfo_Type detailAcctInfo : detailAcctInfos)
		{

			Pair<Pair<String, Office>, String> acountInfo = findAccountInfo(detailAcctInfo.getDepAcctId().getAcctId(), acountInfos);
			Status_Type status = detailAcctInfo.getStatus();

			if (status.getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				try
				{
					AccountImpl account = fillAccountFromDetailInfo(detailAcctInfo);
					account.setId(EntityIdHelper.createAccountCompositeId(detailAcctInfo,
							EntityIdHelper.getCommonCompositeId(acountInfo.getSecond()).getLoginId()));
					result.putResult(acountInfo.getFirst(), account);
				}
				catch (IKFLException e)
				{
					result.putException(acountInfo.getFirst(), e);
				}
			}
			else
			{
				if (OFFLINE_SYSTEM_STATUSES.contains(status.getStatusCode()))
					ESBERIBExceptionStatisticHelper.addOfflineError(result, acountInfo.getFirst(), status, DetailAcctInfo_Type.class, EntityIdHelper.getCommonCompositeId(acountInfo.getSecond()));
				else
				    ESBERIBExceptionStatisticHelper.addError(result, acountInfo.getFirst(), status, DetailAcctInfo_Type.class, EntityIdHelper.getCommonCompositeId(acountInfo.getSecond()));
				log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����" +
						" ����� " + acountInfo.getFirst() + ". " + status.getStatusDesc());
			}
		}

		return result;
	}

	private Pair<Pair<String, Office>, String> findAccountInfo(String number, Pair<Pair<String, Office>, String>... acountInfos)
	{
		for (Pair<Pair<String, Office>, String> acountInfo : acountInfos)
		{
			if (number.equals(acountInfo.getFirst().getFirst()))
				return acountInfo;
		}

		throw new RuntimeException("���������� ��������� �� �� ���� �������");
	}

	/**
	 * ���������� ������� �� �������
	 * @param ifxRs - ���������� �����
	 * @param fromDate - ��������� ���� �������
	 * @param toDate - �������� ���� �������
	 * @param accounts - �����
	 * @return �������
	 */
	public GroupResult<Account, AbstractBase> fillGroupAccountAbstract(IFXRs_Type ifxRs, Calendar fromDate, Calendar toDate, Account... accounts)
	{
		if (accounts == null)
			return null;
		DepAcct_Type[] depAcct_types = ifxRs.getDepAcctExtRs().getDepAcct();
		IKFLException exception = checkResponse(depAcct_types);
		if (exception != null)
		{
			log.error(exception);
			return GroupResultHelper.getOneErrorResult(accounts, exception);
		}

		GroupResult<Account, AbstractBase> res = new GroupResult<Account, AbstractBase>();

		for (DepAcct_Type depAcct : depAcct_types)
		{
			Account account = null;
			try
			{
				account = findObject(depAcct.getDepAcctId().getAcctId(), accounts);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
				continue;
			}

			long statusCode = depAcct.getStatus().getStatusCode();
			if (statusCode != CORRECT_MESSAGE_STATUS)
			{
				log.error(depAcct.getStatus().getStatusDesc());
				ESBERIBExceptionStatisticHelper.addError(res, account, depAcct.getStatus(), DepAcct_Type.class, EntityIdHelper.getAccountCompositeId(account));
				continue;
			}

			AccountAbstractImpl accountAbstract = new AccountAbstractImpl();
			List<TransactionBase> transactions = fillTransactions(depAcct.getDepAcctStmtRec(), account);
			accountAbstract.setTransactions(transactions);
			accountAbstract.setFromDate(fromDate);
			accountAbstract.setToDate(toDate);
			res.putResult(account, accountAbstract);
		}

		return res;
	}

	/*
	���������� ���������� �� ������ �����
	*/

	private AccountImpl fillAccount(DepAcctRec_Type acctRec) throws GateLogicException, GateException
	{
		AccountImpl account = new AccountImpl();
		// ���������� �������� ���������� �� �����
		setAccountMainInfo(account, acctRec.getDepAcctId());

		// ���������� �������������� ���������� �� �����
		setAccountAdditionalInfo(account, acctRec.getDepAccInfo());

		// ���������� ���������� � �������
		setAccountLimits(account, acctRec.getAcctBal());

		return account;
	}

	/*
	���������� �������� ���������� �� c�����
	 */
	private void setAccountMainInfo(AccountImpl account, DepAcctId_Type depAcctId) throws GateLogicException, GateException
	{
		account.setId(depAcctId.getAcctId());
		account.setNumber(depAcctId.getAcctId());
		account.setDescription(depAcctId.getAcctName());
		account.setKind(depAcctId.getAcctCode());
		account.setSubKind(depAcctId.getAcctSubCode());
		
		String openDate = depAcctId.getOpenDate();
		if (!StringHelper.isEmpty(openDate))
			account.setOpenDate(parseCalendar(depAcctId.getOpenDate()));
		account.setAccountState(AccountStateWrapper.getAccountState(depAcctId.getStatus()));

		Currency accountCurrency = getCurrencyByString(depAcctId.getAcctCur());
		account.setCurrency(accountCurrency);
		if (depAcctId.getMaxSumWrite() != null)
			account.setMaxSumWrite(new Money(depAcctId.getMaxSumWrite(), accountCurrency));
		account.setOffice(getOffice(depAcctId.getBankInfo()));
	}

	private void setAccountAdditionalInfo(AccountImpl account, DepAccInfo_Type depAccInfo)
	{
		if (depAccInfo == null)
			return;
		account.setInterestRate(getAccountRate(depAccInfo));
		// BUG032515 �������� ��������� ������������ ���������/��������� �������� � ���� � � ��� ��������������
		account.setCreditAllowed(depAccInfo.getIsDebitAllowed());
		account.setDebitAllowed(depAccInfo.getIsCreditAllowed());
		account.setDemand(parseDemand(depAccInfo.getOpenDate(), depAccInfo.getEndDate()));
		account.setPassbook(depAccInfo.getIsPassBook());

        if (StringHelper.isNotEmpty(depAccInfo.getClientKind()))
            account.setClientKind(Long.valueOf(depAccInfo.getClientKind()));

		account.setProlongationAllowed(depAccInfo.getIsProlongationAllowed());
		account.setCloseDate(parseCalendar(depAccInfo.getEndDate()));
		account.setDebitCrossAgencyAllowed(depAccInfo.getIsDebitCrossAgencyAllowed());
		account.setCreditCrossAgencyAllowed(depAccInfo.getIsCreditCrossAgencyAllowed());
		account.setProlongationDate(parseCalendar(depAccInfo.getProlongationDate()));

		InterestOnDeposit_Type interestOnDeposit = depAccInfo.getInterestOnDeposit();
		if (interestOnDeposit != null)
		{
			account.setInterestTransferAccount(interestOnDeposit.getAcctId());
			account.setInterestTransferCard(interestOnDeposit.getCardNum());
		}

		Long period = depAccInfo.getPeriod();
		if (period != null)
		{
			account.setPeriod(new DateSpan(0, 0, period.intValue()));
		}

		CustRec_Type custRec = depAccInfo.getCustRec();
		if (custRec != null)
		{
			CustInfo_Type custInfo = custRec.getCustInfo();
			ClientImpl client = fillClient(custInfo);
			client.setId(custRec.getCustId());
			account.setAccountClient(client);
		}
	}

	/*
	���������� ���������� � �������
	 */
	private void setAccountLimits(AccountImpl account, AcctBal_Type[] acctBals)
	{
		for (AcctBal_Type acctBal : acctBals)
		{
			String balType = acctBal.getBalType();

			if (balType.equals(LimitType.Avail.toString()))
			{
				account.setBalance(safeCreateMoney(acctBal.getCurAmt(), account.getCurrency()));
				continue;
			}

			if (balType.equals(LimitType.AvailCash.toString()))
			{
				account.setMaxSumWrite(safeCreateMoney(acctBal.getCurAmt(), account.getCurrency()));
				continue;
			}

			if (balType.equals(LimitType.MinAvail.toString()))
			{
				account.setMinimumBalance(safeCreateMoney(acctBal.getCurAmt(), account.getCurrency()));
				continue;
			}

			if (balType.equals("ClearBalance"))
			{
				account.setClearBalance(acctBal.getCurAmt());
				continue;
			}

			if (balType.equals("MaxBalance"))
			{
				account.setMaxBalance(acctBal.getCurAmt());
			}
		}
	}

	/*
	���������� ���������� ����������
	 */
	private List<TransactionBase> fillTransactions(DepAcctStmtRec_Type[] depAcctStmtRecs, Object obj)
	{
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();
		if (depAcctStmtRecs == null || depAcctStmtRecs.length == 0)
		{
			return transactions;
		}

		for (DepAcctStmtRec_Type depAcctStmtRec : depAcctStmtRecs)
		{
			AccountTransactionImpl accountTransaction = new AccountTransactionImpl();
			String operationDate = depAcctStmtRec.getEffDate();
			if (!StringHelper.isEmpty(operationDate))
				accountTransaction.setDate(parseCalendar(operationDate));
			accountTransaction.setOperationCode(depAcctStmtRec.getAspect());

			//�� ������������ ��� depAcctStmtRec.getIsDebit()
			//True � �������� ����������, False � �������� ��������.
			boolean isOperationDebit = !depAcctStmtRec.getIsDebit();

			if (isOperationDebit)
				accountTransaction.setDebitSum(new Money(depAcctStmtRec.getAmount().abs(), getCurrencyByString(depAcctStmtRec.getAmountCur())));
			else
				accountTransaction.setCreditSum(new Money(depAcctStmtRec.getAmount().abs(), getCurrencyByString(depAcctStmtRec.getAmountCur())));
			accountTransaction.setBalance(new Money(depAcctStmtRec.getBalance(), getCurrencyByString(depAcctStmtRec.getBalanceCur())));

			accountTransaction.setRecipient(depAcctStmtRec.getRecipient());
			accountTransaction.setCounteragentCorAccount(depAcctStmtRec.getRecipientAccount());
			accountTransaction.setCounteragentAccount(depAcctStmtRec.getCorrespondent());
			if (depAcctStmtRec.getName() != null)
				accountTransaction.setDescription(depAcctStmtRec.getName());
			transactions.add(accountTransaction);
		}
		return transactions;
	}

	/*
	* ��������� ����� ��� ������� ����, � �������������������� �� ������ ����� (CHG027259)
	* */
	protected Office getOffice(BankInfo_Type bankInfo) throws GateLogicException, GateException
	{
		return getCorrectedOffice(bankInfo);
	}

	/**
	 * ���������� ���������� � �������-��������� �����
	 * @param ifxRs ����� ���� (��������� ACC_DI ��������� ��������� ���������� �� ������)
	 * @param accounts ������ ������
	 * @return ��������� ���������� � �������-��������� �����
	 */
	public GroupResult<Account, Client> fillOwnerInfo(IFXRs_Type ifxRs, Account... accounts)
	{
		GroupResult<Account, Client> result = new GroupResult<Account, Client>();
		DetailAcctInfo_Type[] detailAcctInfos = ifxRs.getAcctInfoRs().getDetailAcctInfo();

		for (DetailAcctInfo_Type detailAcctInfo : detailAcctInfos)
		{
			Account account = null;
			try
			{
				account = findObject(detailAcctInfo.getDepAcctId().getAcctId(), accounts);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
				continue;
			}

			Status_Type status = detailAcctInfo.getStatus();
			if (status.getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				CustRec_Type custRec = detailAcctInfo.getDepAccInfo().getCustRec();
				if (custRec == null)
				{
					result.putException(account, new GateLogicException("���������� �� ������� ����������."));
					continue;
				}

				CustInfo_Type custInfo = custRec.getCustInfo();

				ClientImpl client = fillClient(custInfo);
				client.setId(custRec.getCustId());

				result.putResult(account, client);
			}
			else
			{
				ESBERIBExceptionStatisticHelper.addError(result, account, status, DetailAcctInfo_Type.class, EntityIdHelper.getAccountCompositeId(account));
				log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����" +
						" ����� " + account.getNumber() + ". " + status.getStatusDesc());
			}
		}

		return result;
	}

	private Boolean parseDemand(String depoStartDate, String depoEndDate)
	{
		//���� ��������� � �������� ���� ���������, �� ��� ���������� ����.
		if (depoStartDate != null && StringHelper.equalsNullIgnore(depoStartDate, depoEndDate))
			return true;

		Calendar endDate = parseCalendar(depoEndDate);

		if (endDate == null)
			return null;

		// ������ ����� �� ������, ���� ���, �����, ������ ����
		DateHelper.clearTime(endDate);
		return (DateHelper.nullSafeCompare(DEMAND_ACCOUNT_CLOSING_DATE, endDate) == 0);
	}
}
