package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.types.LimitType;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ author: filimonova
 * @ created: 11.10.2010
 * @ $Author$
 * @ $Revision$
 * Хелпер для создания ответов на запросы по счетам и вкладам
  */
public class AccountResponseHelper extends BaseResponseHelper
{
	private final static Map<String, String> currencies = new HashMap<String, String>();
	private final static String[] accountMask = {"40817", "40820", "42301", "42307", "42601", "42507"};
	private final static String[] currencyCodes = {"810", "840", "978"};

	private final Random accountNumberRandom = new Random();

	private final Random accountCurrencyRandom = new Random();

	private final Random accountStateRandom = new Random();

	private final Random accountHasOpenDateRandom = new Random();

	static
	{
		currencies.put("810", "RUR");
		currencies.put("840", "USD");
		currencies.put("978", "EUR");
	}



	/**
	* Список счетов
	 * + добавляются счета, открытые за текущую сессию (см. AccountOpeningHelper)
	* @return список счетов
	*/
	public DepAcctRec_Type[] getDepAcctRec(Login login) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accounts = (login==null) ? null : resourceService.getLinks(login, AccountLink.class);

		List<String> accountNumbers = new ArrayList<String>();
		//сначала добавляем счета, открытыие в текущую сессию
		if(login != null)
			accountNumbers.addAll(AccountOpeningHelper.getAccountNumbers(login.getId()));

		if (CollectionUtils.isEmpty(accounts))
		{
			if (isNightBuildsEnabled())
			{
				for (int i=0; i<10; i++)
				{
					accountNumbers.add("");
				}
			}
			else
			{
				// счетов нет, добавим 2 новых
				accountNumbers.add("");
				accountNumbers.add("");
			}
		}
		else {
			for (AccountLink accountLink : accounts)
				accountNumbers.add(accountLink.getNumber());
		}

		DepAcctRec_Type[] depAcctRecs = new DepAcctRec_Type[accountNumbers.size()];

		for (int i=0; i<accountNumbers.size(); i++)
		{
			DepAcctRec_Type depAcctRec = new DepAcctRec_Type();
			/*
			Если необходимо добавить счет из АРМ Сотрудника - регистрация нового СБОЛ,
			то прописываем номер этого счета, как показано ниже, иначе не добавится. (раскомментируем следующие 3 строки)
			*/
//            if (i== 0)
//                depAcctRec.setDepAcctId(createMockAccount("42301810764575648765"));
//            else
				depAcctRec.setDepAcctId(createMockAccount(accountNumbers.get(i)));

			depAcctRec.setDepAccInfo(createDepAccInfo(depAcctRec.getDepAcctId().getStatus()));
			depAcctRec.setBankInfo(getMockBankInfo());
			depAcctRec.setAcctBal(createAccountBalanceInfo(depAcctRec.getDepAcctId().getAcctCur()));
			BankAcctPermiss_Type[] permissions = new BankAcctPermiss_Type[2];
			permissions[0] = new BankAcctPermiss_Type(PermissType_Type.View, false, SPName_Type.BP_ES);//СмартВиста
			permissions[1] = new BankAcctPermiss_Type(PermissType_Type.View, false, SPName_Type.BP_ERIB);//СБОЛ
			depAcctRec.setBankAcctPermiss(permissions);
			depAcctRecs[i] = depAcctRec;
		}

		if(login != null)
			AccountOpeningHelper.clear(login.getId());

		return depAcctRecs;
	}

	/**
	* Выписка по счету
	*/
	public IFXRs_Type createAccountAbstract(IFXRq_Type parameters)
	{
		IFXRs_Type ifxRs = new IFXRs_Type();
		DepAcctExtRs_Type depAcctExtRs = new DepAcctExtRs_Type();
		DepAcctStmtInqRq_Type depAcctStmtInqRq = parameters.getDepAcctStmtInqRq();
		depAcctExtRs.setRqUID(depAcctStmtInqRq.getRqUID());
		depAcctExtRs.setOperUID(depAcctStmtInqRq.getOperUID());
		depAcctExtRs.setRqTm(depAcctStmtInqRq.getRqTm());
		depAcctExtRs.setStatus(getStatus());

		DepAcct_Type[] depAcctRecs = new DepAcct_Type[1];
		DepAcct_Type depAcct = new DepAcct_Type();

		Status_Type responseStatus = getStatus();

		if (depAcctStmtInqRq.getStmtType().equals(StmtType_Type.Full))
		{
		 //В рамках пилота не поддерживается получение полной выписки по вкладу.
		 // При задании типа выписки Full для данного интерфейса будет возвращаться код ошибки -102 и сообщение «Не реализовано».
			responseStatus.setStatusCode(-102);
			responseStatus.setServerStatusDesc("Не реализовано");
		}

		depAcct.setStatus(responseStatus);
		depAcctRecs[0] = depAcct;
		depAcctExtRs.setDepAcct(depAcctRecs);
		ifxRs.setDepAcctExtRs(depAcctExtRs);

		DepAcctId_Type depAcctId = depAcctStmtInqRq.getDepAcctRec().getDepAcctId();
		depAcct.setDepAcctId(depAcctId);

		if (responseStatus.getStatusCode()!=0)
			return ifxRs;

		String accountNumner = depAcctId.getAcctId();
		String currencyCode = currencies.get(accountNumner.substring(5, 8));
		Random rand = new Random();
		int countOfOperation = rand.nextInt(10);

		DepAcctStmtRec_Type[] depAcctStmtRecs = new DepAcctStmtRec_Type[countOfOperation];
		depAcct.setDepAcctStmtRec(depAcctStmtRecs);
		for (int i=countOfOperation-1; i>=0; i--)
		{
			DepAcctStmtRec_Type depAcctStmtRec = new DepAcctStmtRec_Type();
			GregorianCalendar effDate = new GregorianCalendar();
			effDate.add(Calendar.DATE, -1 * rand.nextInt(100));
			depAcctStmtRec.setEffDate(getStringDateTime(effDate));

			depAcctStmtRec.setAspect("JG");
			depAcctStmtRec.setIsDebit(rand.nextBoolean());
			depAcctStmtRec.setAmount(getRandomDecimal());
			depAcctStmtRec.setAmountCur(currencyCode);
			if (rand.nextBoolean()) depAcctStmtRec.setCorrespondent("101010101010101");
			depAcctStmtRec.setBalance(getRandomDecimal());
			depAcctStmtRec.setBalanceCur(currencyCode);
			if (rand.nextBoolean())
				depAcctStmtRec.setRecipientAccount("20202020202020202");
			if (rand.nextBoolean())
				depAcctStmtRec.setRecipientBIC("123456789");
			if (rand.nextBoolean())
				depAcctStmtRec.setDestination("Назначение перевода");
			depAcctStmtRecs[i] = depAcctStmtRec;
		}

		return ifxRs;
	}

	/*
	* Получение детальной информации по вкладу
	* */
	public IFXRs_Type getAccountDetailInfo(IFXRq_Type parameters)
	{
		IFXRs_Type ifxrs_Type = new IFXRs_Type();
		AcctInfoRs_Type acctInfoRs = new AcctInfoRs_Type();

		AcctInfoRq_Type acctInfoRq = parameters.getAcctInfoRq();
		acctInfoRs.setRqUID(acctInfoRq.getRqUID());
		acctInfoRs.setOperUID(acctInfoRq.getOperUID());
		acctInfoRs.setRqTm(acctInfoRq.getRqTm());
		acctInfoRs.setStatus(getStatus());

		DetailAcctInfo_Type[] detailAcctInfo_Types = new DetailAcctInfo_Type[acctInfoRq.getDepAcctRec().length];
		DetailAcctInfo_Type detailAcctInfo_Type = new DetailAcctInfo_Type();

		Status_Type responseStatus = getStatus();
		detailAcctInfo_Type.setStatus(responseStatus);

		DepAcctId_Type depAcctId = new DepAcctId_Type();
		depAcctId.setSystemId("systemX");
		depAcctId.setAcctId(acctInfoRq.getDepAcctRec()[0].getDepAcctId().getAcctId());
		if(responseStatus.getStatusCode() == 0L)
		{
			AccountStatusEnum_Type accountStatusEnum = generateAccountState();
			DepAccInfo_Type depAccInfo = createDepAccInfo(accountStatusEnum);
			String currencyCode = currencies.get(depAcctId.getAcctId().substring(5, 8));

			depAccInfo.setAcctName("Сберегательный");
			depAccInfo.setAcctCode(123L);
			depAccInfo.setAcctSubCode(456L);
			depAccInfo.setCurAmt(getRandomDecimal());
			depAccInfo.setAcctCur(currencyCode);
			depAccInfo.setMaxSumWrite(getRandomDecimal());
			depAccInfo.setIrreducibleAmt(getRandomDecimal());
			depAccInfo.setMaxBalance(getRandomDecimal());
			depAccInfo.setClearBalance(getRandomDecimal());

			depAccInfo.setOpenDate(getStringDate(DateHelper.getPreviousMonth()));
			depAccInfo.setStatus(accountStatusEnum);

			try
			{
				BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
				String accountId = backRefBankrollService.findAccountExternalId(depAcctId.getAcctId());
				depAccInfo.setBankInfo(getBankInfo(EntityIdHelper.getCommonCompositeId(accountId).getLoginId()));
			}
			catch (Exception ignored)
			{
				depAccInfo.setBankInfo(getMockBankInfo());
			}

			CustRec_Type custRec = new CustRec_Type();
			custRec.setCustInfo(getMockCustInfo());
			depAccInfo.setCustRec(custRec);

			detailAcctInfo_Type.setDepAccInfo(depAccInfo);
		}
		detailAcctInfo_Type.setDepAcctId(depAcctId);
		detailAcctInfo_Types[0] = detailAcctInfo_Type;
		acctInfoRs.setDetailAcctInfo(detailAcctInfo_Types);
		ifxrs_Type.setAcctInfoRs(acctInfoRs);
		return ifxrs_Type;
	}

	public String generateNewAccount()
	{
		return accountMask[accountNumberRandom.nextInt(6)] + currencyCodes[accountCurrencyRandom.nextInt(3)]+ RandomHelper.rand(12, RandomHelper.DIGITS);
	}

	private DepAcctId_Type createMockAccount(String accountNumber)
	{
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		if (StringHelper.isEmpty(accountNumber))
		{
			// Создаем новый номер
			accountNumber = generateNewAccount();
		}
		if (accountHasOpenDateRandom.nextBoolean())
			depAcctId.setOpenDate(getStringDate(DateHelper.getPreviousMonth()));

		String currencyCode = currencies.get(accountNumber.substring(5, 8));
		depAcctId.setAcctCur(currencyCode);

		depAcctId.setAcctId(accountNumber);
		depAcctId.setAcctName("Вклад сберегательный");
		depAcctId.setSystemId("systemX");
		depAcctId.setAcctCode(123L);
		depAcctId.setAcctSubCode(456L);
		depAcctId.setStatus(generateAccountState());

		return depAcctId;
	}

	private AccountStatusEnum_Type generateAccountState()
	{
		int rand = accountStateRandom.nextInt(100);
		if (rand <= 40) // Opened
			return AccountStatusEnum_Type.value1;
		if (rand <= 70) // Closed
			return AccountStatusEnum_Type.value2;
		if (rand <= 85) // Arrested
			return AccountStatusEnum_Type.value3;
		// Lost-passbook
		return AccountStatusEnum_Type.value4;
	}

	private DepAccInfo_Type createDepAccInfo(AccountStatusEnum_Type status)
	{
		Random rand = new Random();
		DepAccInfo_Type depAccInfo = new DepAccInfo_Type();

		// данные приходят не во всех ТБ
		if (rand.nextBoolean())
		{
			depAccInfo.setInterestOnDeposit(new InterestOnDeposit_Type(generateNewAccount(),
					RandomHelper.rand(16, RandomHelper.DIGITS)));
			depAccInfo.setPeriod(180L);
			depAccInfo.setRate(new BigDecimal("0.0001"));
			if (status.equals(AccountStatusEnum_Type.value2))
				depAccInfo.setEndDate(rand.nextBoolean() ? "1800-01-01" : getStringDate(DateHelper.getPreviousMonth()));
			depAccInfo.setIsCreditAllowed(rand.nextBoolean());
			depAccInfo.setIsDebitAllowed(rand.nextBoolean());
			depAccInfo.setIsProlongationAllowed(rand.nextBoolean());
			depAccInfo.setIsCreditCrossAgencyAllowed(rand.nextBoolean());
			depAccInfo.setIsDebitCrossAgencyAllowed(rand.nextBoolean());
			depAccInfo.setIsPassBook(rand.nextBoolean());
            depAccInfo.setClientKind("106");
 		    depAccInfo.setProlongationDate(getStringDate(DateHelper.getPreviousMonth()));

			CustRec_Type custRec = new CustRec_Type();
			custRec.setCustInfo(getMockCustInfo());
			depAccInfo.setCustRec(custRec);
		}

		return depAccInfo;
	}

	private AcctBal_Type[] createAccountBalanceInfo(String code)
	{
		AcctBal_Type acctBal = new AcctBal_Type();
		acctBal.setBalType(LimitType.Avail.toString());
		acctBal.setCurAmt(getRandomDecimal());
		acctBal.setAcctCur(code);
		AcctBal_Type[] acctBals = new AcctBal_Type[5];
		acctBals[0] = acctBal;
		acctBal = new AcctBal_Type();
		acctBal.setBalType(LimitType.AvailCash.toString());
		acctBal.setCurAmt(getRandomDecimal());
		acctBal.setAcctCur(code);
		acctBals[1] = acctBal;
		acctBal = new AcctBal_Type();
		acctBal.setBalType(LimitType.MinAvail.toString());
		acctBal.setCurAmt(getRandomDecimal());
		acctBal.setAcctCur(code);
		acctBals[2] = acctBal;
		acctBal = new AcctBal_Type();
		acctBal.setBalType("ClearBalance");
		acctBal.setCurAmt(getRandomDecimal());
		acctBal.setAcctCur(code);
		acctBals[3] = acctBal;
		acctBal = new AcctBal_Type();
		acctBal.setBalType("MaxBalance");
		acctBal.setCurAmt(getRandomDecimal());
		acctBal.setAcctCur(code);
		acctBals[4] = acctBal;
		return acctBals;
	}
}
