package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.account.AccountOpeningErrorsAutoRefreshConfig;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;

import java.rmi.RemoteException;
import java.util.*;
import java.math.BigDecimal;

/**
 * Карта < Логин_ID, Список номеров счетов, открытых в текущей сессии >
 * Номера заполняются из response-ов на открытие счета (TDO).
 * Используется в GFL для отображения счетов, открытых в текущей сессии, без перезахода в систему.
 * @author Dorzhinov
 * @ created 02.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningHelper extends BaseResponseHelper
{
	private PersonService personService = new PersonService();
	private AccountResponseHelper accountResponseHelper = new AccountResponseHelper();
	private static Map<Long, List<String>> accountNumbers = new HashMap<Long, List<String>>();

	/**
	 * Добавление в карту номера только что открытого счета для указанного логина
	 * @param loginId
	 * @param accountNumber
	 */
	private void addAccountNumber(Long loginId, String accountNumber)
	{
		if (accountNumbers.get(loginId) == null)
			accountNumbers.put(loginId, new ArrayList<String>());

		accountNumbers.get(loginId).add(accountNumber);
	}

	/**
	 * Получение списка номеров всех открытых в текущую сессию счетов для указанного логина
	 * Если для логина список отсутствует, то возвращаем пустой список.
	 * @param loginId
	 * @return
	 */
	public static List<String> getAccountNumbers(Long loginId)
	{
		List<String> list = accountNumbers.get(loginId);
		return list == null ? new ArrayList<String>() : list;
	}

	/**
	 * Удаление из карты списка номеров всех открытых в текущую сессию счетов для указанного логина
	 * @param loginId
	 */
	public static void clear(Long loginId)
	{
		accountNumbers.remove(loginId);
	}

	/**
	 * Открытие нового срочного вклада путем перевода средств с существующего вклада
	 * @param parameters параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createDepToNewDep(IFXRq_Type parameters) throws RemoteException
	{
		IFXRs_Type ifxRs = new IFXRs_Type();
		DepToNewDepAddRq_Type request = parameters.getDepToNewDepAddRq();
		DepToNewDepAddRs_Type responce = new DepToNewDepAddRs_Type();

		//если это предварительный расчет комиссий - добалвяем микрооперации.
		SrcLayoutInfo_Type srcLayoutInfo_type = request.getXferInfo().getSrcLayoutInfo();
		Status_Type status = new Status_Type();
		boolean isWithPrepare = !request.getXferInfo().getExecute();
		int rand = new Random().nextInt(100);
		//при подготовке платежа(prepare запрос) всегда возврщаем хоть какие-нибудь операции, по ним считаются суммы перевода.
		if (srcLayoutInfo_type != null && srcLayoutInfo_type.getIsCalcOperation() && (isWithPrepare || rand % 10 != 3))
		{
			status.setStatusCode(isWithPrepare ? 0 : -433);
			responce.setStatus(status);
			SrcLayoutInfo_Type srcLayoutInfo_typeRs = new SrcLayoutInfo_Type();
			srcLayoutInfo_typeRs.setWriteDownOperation(PaymentsHelper.generateWriteDownOperations(OperName_Type.TDDO));
			responce.setSrcLayoutInfo(srcLayoutInfo_typeRs);
		}
		else
			responce.setStatus(getStatus());

		//Для тестирования ВСП по открытию вкладов
//		DepAcctId_Type depAcctId = request.getXferInfo().getDepAcctIdTo();
//		if (depAcctId != null)
//		{
//			BankInfo_Type bankInfo = depAcctId.getBankInfo();
//			if (bankInfo != null && bankInfo.getBranchId() != null && bankInfo.getAgencyId() != null)
//			{
//				//Status_Type statusType = new Status_Type(-438L, "Некорректное значение ТБ, ОСБ или ВСП", null, SERVER_STATUS_DESC);
//				Status_Type statusType = new Status_Type(-424L, "ТБ в запросе отличается от ТБ счета списания", null, SERVER_STATUS_DESC);
//				responce.setStatus(statusType);
//			}
//		}

		responce.setOperUID(request.getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());

		if (responce.getStatus().getStatusCode() == 0L)
		{
			AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
			DepInfo_Type depInfo = new DepInfo_Type();
			if (request.getXferInfo().getExecute())
			{
				depInfo.setAcctId(accountResponseHelper.generateNewAccount());
				agreemtInfo.setDepInfo(depInfo);
				responce.setAgreemtInfo(agreemtInfo);

				//сохраняем добавленный счет в AccountOpeningHelper
				PersonInfo_Type personInfo = request.getXferInfo().getAgreemtInfo().getDepInfo().getCustRec().getCustInfo().getPersonInfo();
				addNewAccount(personInfo, depInfo.getAcctId());
			}
			else
			{
				Random random = new Random();
				if (random.nextInt(5) == 1)
				{
					depInfo.setDaysToEndOfSaving(Long.valueOf(random.nextInt(365)));
					depInfo.setDateToEndOfSaving("21.12.2012");
					depInfo.setEarlyTermRate(new IntRate_Type(new BigDecimal(random.nextInt(50))));
				}
				depInfo.setHaveForm190(random.nextInt(10) == 2);

				agreemtInfo.setDepInfo(depInfo);
				responce.setAgreemtInfoClose(agreemtInfo);
				if(srcLayoutInfo_type != null && srcLayoutInfo_type.getIsCalcOperation() && (isWithPrepare || rand % 10 != 3))
					responce.setSrcCurAmt(null);
				else
					responce.setSrcCurAmt(getRandomDecimal());
				responce.setDstCurAmt(getRandomDecimal());
			}
		}

		ifxRs.setDepToNewDepAddRs(responce);
		return ifxRs;
	}

	/**
	 * Запрос на открытие бессрочного вклада без первоначального взноса
	 * @param parameters - параметры запроса
	 * @return ответ
	 */
	public IFXRs_Type createNewDepAdd(IFXRq_Type parameters) throws RemoteException
	{
		IFXRs_Type ifxRs = new IFXRs_Type();
		DepToNewDepAddRq_Type request = parameters.getNewDepAddRq();
		DepToNewDepAddRs_Type responce = new DepToNewDepAddRs_Type();

		responce.setStatus(getStatus());
		responce.setOperUID(request.getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());

		if (responce.getStatus().getStatusCode()==0L)
		{
			AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
			DepInfo_Type depInfo = new DepInfo_Type();
			depInfo.setAcctId(accountResponseHelper.generateNewAccount());
			agreemtInfo.setDepInfo(depInfo);
			responce.setAgreemtInfo(agreemtInfo);

			//сохраняем добавленный счет в AccountOpeningHelper
			PersonInfo_Type personInfo = request.getXferInfo().getAgreemtInfo().getDepInfo().getCustRec().getCustInfo().getPersonInfo();
			addNewAccount(personInfo, depInfo.getAcctId());
		}

		ifxRs.setNewDepAddRs(responce);
		return ifxRs;
	}

	/**
	 * Запрос на открытие нового срочного вклада путем перевода средств с карты
	 * @param parameters параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createCardToNewDep(IFXRq_Type parameters) throws RemoteException
	{
		IFXRs_Type ifxRs = new IFXRs_Type();
		CardToNewDepAddRq_Type request = parameters.getCardToNewDepAddRq();
		CardToNewDepAddRs_Type responce = new CardToNewDepAddRs_Type();

		//Status_Type status = new Status_Type(-105L, "Описание ошибки, готовое для отображения пользователю.", null, SERVER_STATUS_DESC);
		//responce.setStatus(status);
		responce.setStatus(getStatus());
		responce.setOperUID(request.getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());

		if (responce.getStatus().getStatusCode() == 0L)
		{
			CardAuthorization_Type cardAuthorization = new CardAuthorization_Type();
			cardAuthorization.setAuthorizationCode(1L);
			responce.setCardAuthorization(cardAuthorization);
			AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
			DepInfo_Type depInfo = new DepInfo_Type();
			depInfo.setAcctId(accountResponseHelper.generateNewAccount());
			agreemtInfo.setDepInfo(depInfo);
			responce.setAgreemtInfo(agreemtInfo);
		}

		ifxRs.setCardToNewDepAddRs(responce);
		return ifxRs;
	}

	private void addNewAccount(PersonInfo_Type personInfo, String accountNumber) throws RemoteException
	{
		PersonName_Type name = personInfo.getPersonName();
		IdentityCard_Type document = personInfo.getIdentityCard();
		Calendar birthDate = null;
		if (!StringHelper.isEmpty(personInfo.getBirthday()))
			birthDate = XMLDatatypeHelper.parseDate(personInfo.getBirthday());

		Person person;
		try
		{
			person = personService.getByFIOAndDocUnique(
					name.getLastName(), name.getFirstName(), name.getMiddleName(),
					document.getIdSeries(), document.getIdNum(),
					birthDate, null);
		}
		catch (BusinessLogicException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
		catch (BusinessException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}

		if (person != null)
			addAccountNumber(person.getLogin().getId(), accountNumber);
	}
}
