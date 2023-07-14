package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.*;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ClientDocumentController;
import com.rssl.phizic.test.esberibmock.*;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 @author Pankin
 @ created 08.09.2010
 @ $Author$
 @ $Revision$
 */
public class ResponseHelper extends BaseResponseHelper
{
	private PersonService personService = new PersonService();
	private LongOfferHelper longOfferHelper = new LongOfferHelper();
	private LoanHelper loanHelper = new LoanHelper();
	private DepoAccountHelper depoAccountHelper = new DepoAccountHelper();
	private IMAccountResponseHelper imAccountResponseHelper = new IMAccountResponseHelper();
	private ClientResponseHelper clientResponseHelper = new ClientResponseHelper();
	private AccountResponseHelper accountResponseHelper = new AccountResponseHelper();
	private CardResponseHelper cardResponseHelper = new CardResponseHelper();
	private BillingHelper billingHelper = new BillingHelper();
	private PFRResponseHelper pfrResponseHelper = new PFRResponseHelper();
	private AccountOpeningHelper accountOpeningHelper = new AccountOpeningHelper();
	private AutoSubscriptionHelper autoSubscriptionHelper = new AutoSubscriptionHelper();
	private IMAOpeningResponseHelper imaOpeningResponseHelper = new IMAOpeningResponseHelper();
	private IMAPaymentResponseHelper imaPaymentResponseHelper = new IMAPaymentResponseHelper();
	private AbsOperStatusFromEribResponseHelper absOperStatusFromEribResponseHelper = new AbsOperStatusFromEribResponseHelper();
	private InsuranceHelper insuranceHelper = new InsuranceHelper();
	private SecurityAccountHelper securityAccountHelper = new SecurityAccountHelper();

	private final MockCardResponseHelper mockCardResponseHelper = new MockCardResponseHelper();
	private final MockAccountResponseHelper mockAccountResponseHelper = new MockAccountResponseHelper();
	private final MockIMAccountResponseHelper mockIMAccountResponseHelper = new MockIMAccountResponseHelper();
	private final MockCreditResponseHelper mockCreditResponseHelper = new MockCreditResponseHelper();
	private final Boolean isUsedMockDB;

	private static final MockProductService mockProductService = new MockProductService();

	public ResponseHelper()
	{
		PropertyReader propertyReader = ConfigFactory.getReaderByFileName("esberib.mock.properties");
		isUsedMockDB =  Boolean.parseBoolean(propertyReader.getProperty("esberib.mock.db.used"));
	}
	/**
	 * Генерация детальной информации по ОМС
	 * @param parameters параметры запроса
	 * @return липовый ответ(в целях отладки)
	 */
	public IFXRs_Type createImaAcctInRs(IFXRq_Type parameters) throws BusinessException
	{
		if (isUsedMockDB)
			return mockIMAccountResponseHelper.createImaAcctInRs(parameters);
		else
			return imAccountResponseHelper.createImaAcctInRs(parameters);
	}

	/**
	 * Генерация короткой выписки по ОМС
	 * @param parameters параметры запроса
	 * @return липовый ответ(в целях отладки)
	 */
	public IFXRs_Type createBankAcctStmtInqRs(IFXRq_Type parameters)
	{
		return imAccountResponseHelper.createBankAcctStmtInqRs(parameters);
	}

	/**
	 * Получение полной выписки по ОМС
	 * @param parameters параметры запроса
	 * @return липовый ответ(в целях отладки)
	 */
	public IFXRs_Type createBankAcctFullStmtInqRs(IFXRq_Type parameters)
	{
		return imAccountResponseHelper.createBankAcctFullStmtInqRs(parameters);
	}


	/**
	 * GFL
	 * @param parameters
	 * @return
	 */
	public IFXRs_Type createBankAcctInqRs(IFXRq_Type parameters) throws RemoteException
	{
		try
		{
			BankAcctInqRq_Type bankAcctInqRq = parameters.getBankAcctInqRq();
			BankAcctInqRs_Type bankAcctInqRs = new BankAcctInqRs_Type();

			IdentityCard_Type identityCard_type = bankAcctInqRq.getCustInfo().getPersonInfo().getIdentityCard();
			verifyIdentityCard(identityCard_type, false);

			bankAcctInqRs.setRqUID(bankAcctInqRq.getRqUID());
			bankAcctInqRs.setRqTm(getRqTm());
			bankAcctInqRs.setOperUID(bankAcctInqRq.getOperUID());

			Status_Type responseStatus = getStatusForGFL(bankAcctInqRq);
			bankAcctInqRs.setStatus(responseStatus);

			if (responseStatus.getStatusCode() == 0L)
			{
				PersonInfo_Type personInfo = bankAcctInqRq.getCustInfo().getPersonInfo();
				Calendar birthDate = null;
				if (!StringHelper.isEmpty(personInfo.getBirthday()))
					birthDate = XMLDatatypeHelper.parseDate(personInfo.getBirthday());
				PersonName_Type name = personInfo.getPersonName();
				List<Person> persons = personService.getByAttribute(name.getFirstName(), name.getLastName(), name.getMiddleName(), birthDate, null);//, loginHelper.getTBFromCB_CODE(bankAcctInqRq.getBankInfo().getRbTbBrchId()));
				RequestGFL requestGFL = null;
				if (isUsedMockDB)
				{
					requestGFL = mockProductService.getGFL(bankAcctInqRq);
				}

				if (parameters.getBankAcctInqRq().getAcctType()==null)
					buildProducts(bankAcctInqRs, null, persons, bankAcctInqRq.getCustInfo().getPersonInfo(), requestGFL);
				else
				{
					for (AcctType_Type acctType: parameters.getBankAcctInqRq().getAcctType())
					{
						buildProducts(bankAcctInqRs, acctType.getValue(), persons, bankAcctInqRq.getCustInfo().getPersonInfo(), requestGFL);
					}
				}
			}
			IFXRs_Type response = new IFXRs_Type();
			response.setBankAcctInqRs(bankAcctInqRs);
			return response;
		}
		catch (BusinessException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
		catch (GateException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
		catch (BusinessLogicException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	private void buildProducts(BankAcctInqRs_Type bankAcctInqRs, String acctType, List<Person> persons, PersonInfo_Type personInfo_type, RequestGFL requestGFL) throws BusinessException, BusinessLogicException
	{
		Login login;
		if (persons.isEmpty())
		{
			login = null;
		}
		else
		{
			login = persons.get(0).getLogin();
		}

		if ((acctType==null) || (acctType.equals(BankProductType.Card.name())))
		{
			if (requestGFL!=null)
			{
				mockCardResponseHelper.buildMockCards(bankAcctInqRs, requestGFL.getId());
			}
			else
			{
				boolean onlyCardWay = "300".equals(personInfo_type.getIdentityCard().getIdType());
				cardResponseHelper.buildCards(bankAcctInqRs, login, onlyCardWay);
			}
		}
		if ((acctType==null) || (acctType.equals(BankProductType.Deposit.toString())))
		{
			if (requestGFL!=null)
				bankAcctInqRs.setDepAcctRec(mockAccountResponseHelper.getDepAcctRec(requestGFL.getId()));
			else
				bankAcctInqRs.setDepAcctRec(accountResponseHelper.getDepAcctRec(login));
		}
		if ((acctType==null) || (acctType.equals(BankProductType.LongOrd.toString())))
		{
			bankAcctInqRs.setSvcsAcct(longOfferHelper.getSvcsAcct(login));
		}
		if ((acctType==null) || (acctType.equals(BankProductType.Credit.toString())))
		{
			if (requestGFL!=null)
				bankAcctInqRs.setLoanAcctRec(mockCreditResponseHelper.getLoanAcctRecs(requestGFL.getId()));
			else
				bankAcctInqRs.setLoanAcctRec(loanHelper.getLoanAcctRecs(login));
		}
		if ((acctType==null) || (acctType.equals(BankProductType.DepoAcc.toString())))
		{
			bankAcctInqRs.setDepoAccounts(depoAccountHelper.getDepoAccountRecs(login));
		}
		if ((acctType==null) || (acctType.equals(BankProductType.IMA.toString())))
		{
			if (requestGFL!=null)
				mockIMAccountResponseHelper.buildMockIMAccounts(bankAcctInqRs, requestGFL.getId());
			else
				imAccountResponseHelper.buildIMAccounts(bankAcctInqRs, login);
		}
		if ((acctType==null) || (acctType.equals(BankProductType.Securities.toString())))
		{
			bankAcctInqRs.setSecuritiesAcctInfo(securityAccountHelper.getSecurityAccountRecs(login));
		}

	}

	/**
	 * Получение детальной информации по карте для CRDWI
	 */
	public IFXRs_Type createCardAcctDInqRs(IFXRq_Type parameters) throws BusinessException
	{
		if (isUsedMockDB)
			return mockCardResponseHelper.createCardAcctDInqRs(parameters);
		else
			return cardResponseHelper.createCardAcctDInqRs(parameters);
	}

	/* Получение выписки по счету
	* */
	public IFXRs_Type createAccountAbstract(IFXRq_Type parameters)
	{
		return accountResponseHelper.createAccountAbstract(parameters);
	}

	public IFXRs_Type createAcctInqRs(IFXRq_Type parameters) throws RemoteException
	{
		if(parameters.getAcctInqRq().getCardAcctId()!=null)
		{
			return cardResponseHelper.createCardAcctInqRs(parameters);
		}
		//кроме карт могут быть ОМС и депо
		else
			throw new RemoteException("Некорректный запрос");
	}

	/**
	 * Получение детальной информации по кредиту
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public IFXRs_Type createLoanInfoRs(IFXRq_Type parameters) throws BusinessException
	{
		if (isUsedMockDB)
			return mockCreditResponseHelper.createLoanInfoRs(parameters);
		else
			return loanHelper.createLoanInfoRs(parameters);
	}

	public IFXRs_Type createLoanInqRs(IFXRq_Type parameters)
	{
		return loanHelper.createLoanInqRs(parameters);
	}

	public IFXRs_Type createLoanPaymentRs(IFXRq_Type parameters)
	{
		return loanHelper.createLoanPaymentRs(parameters);
	}

	public IFXRs_Type createBankAcctStmtImgInqRs(IFXRq_Type parameters)
	{
		return cardResponseHelper.createBankAcctStmtImgInqRs(parameters);
	}

	public IFXRs_Type createCCAcctExtStmtInqRs(IFXRq_Type parameters)
	{
		return cardResponseHelper.createCCAcctExtStmtInqRs(parameters);
	}

	public IFXRs_Type createCardBlockRs(IFXRq_Type parameters)
	{
		return cardResponseHelper.createCardBlockRs(parameters);
	}

	public IFXRs_Type createCardReissuePlaceRs(IFXRq_Type parameters)
	{
		return cardResponseHelper.createCardReissuePlaceRs(parameters);
	}

	public IFXRs_Type createXferAddRs(IFXRq_Type parameters) throws RemoteException
	{
		PaymentsHelper paymentsHelper = new PaymentsHelper();
		return paymentsHelper.doIFX(parameters);
	}

	public IFXRs_Type createSvcAddRq(IFXRq_Type parameters)
	{
		return longOfferHelper.createSvcAddRq(parameters);
	}

	public IFXRs_Type createSvcAcctAudRs(IFXRq_Type parameters) throws RemoteException
	{
		return longOfferHelper.createSvcAcctAudRq(parameters);
	}

	public IFXRs_Type createSvcAcctDelRs(IFXRq_Type parameters) throws RemoteException
	{
		return longOfferHelper.createSvcAcctDelRs(parameters);
	}

	public IFXRs_Type createServiceStmtRs(IFXRq_Type parameters) throws RemoteException
	{
		return longOfferHelper.createServiceStmtRs(parameters);	
	}

	/*
	* Заполнение ответа на ACC_DI-запрос (получение детальной информации по вкладу)
	* */
	public IFXRs_Type createAcctInfoRs(IFXRq_Type parameters) throws BusinessException
	{
		if (isUsedMockDB)
			return mockAccountResponseHelper.getAccountDetailInfo(parameters);
		else
			return accountResponseHelper.getAccountDetailInfo(parameters);
	}

	public IFXRs_Type createDepoAccTranRs(IFXRq_Type parameters) throws RemoteException
	{
		CorrOwnerDetail_Type ownerDetail_type = parameters.getDepoAccTranRq().getTransferInfo().getTransferRcpInfo().getCorrOwnerDetail();
		/**
		 * Необязательный параметр
		 */
		if(ownerDetail_type != null)
		{
			IdentityCard_Type identityCard_type = ownerDetail_type.getIdentityCard();
			try
			{
				verifyIdentityCard(identityCard_type, false);
			}
			catch (GateException e)
			{
				throw new RemoteException(e.getMessage());
			}
		}

		return depoAccountHelper.createDepoAccTranRs(parameters);
	}

	public IFXRs_Type createDepoAccSecRegRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createDepoAccSecRegRs(parameters);
	}

	public IFXRs_Type createDepoArRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createDepoArRs(parameters);
	}

	public IFXRs_Type createDepoAccInfoRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createDepoAccInfoRs(parameters);
	}

	public IFXRs_Type createDepoDeptsInfoRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createDepoDeptsInfoRs(parameters);
	}

	public IFXRs_Type createDepoAccSecInfoRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createDepoAccSecInfoRs(parameters);
	}

	public IFXRs_Type createDepoDeptDetInfoRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createDepoDeptDetInfoRs(parameters);
	}

	public IFXRs_Type createDepoRevokeDocRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createDepoRevokeDocRs(parameters);
	}

	public IFXRs_Type createMessageRecvRs(IFXRq_Type parameters)
	{
		return depoAccountHelper.createMessageRecvRs(parameters);
	}
	public IFXRs_Type createDepoClientRegRs(IFXRq_Type parameters) throws RemoteException
	{
		IdentityCard_Type identityCard_type = parameters.getDepoClientRegRq().getCustInfo().getPersonInfo().getIdentityCard();
		try
		{
			verifyIdentityCard(identityCard_type, false);
		}
		catch (GateException e)
		{
			throw new RemoteException(e.getMessage());
		}

		return depoAccountHelper.createDepoClientRegRs(parameters);
	}


	/**
	 * @param parameters параметры запроса
	 * @return Ответ (получение ДУЛ клиента и информации о наличии заключенного УДБО)
	 */
	public IFXRs_Type createCustInqRs(IFXRq_Type parameters) throws RemoteException
	{
		CustInfo_Type custInfo_type = parameters.getCustInqRq().getCustInfo();
		/**
		 * необязательный параметр
		 */
		if (custInfo_type != null)
		{
			IdentityCard_Type identityCard_type = custInfo_type.getPersonInfo().getIdentityCard();
			try
			{
				verifyIdentityCard(identityCard_type, true);
			}
			catch (GateException e)
			{
				throw new RemoteException(e.getMessage());
			}
		}
		return clientResponseHelper.createCustInqRs(parameters);
	}

	public IFXRs_Type createBillingPayInqRs(IFXRq_Type parameters) throws RemoteException
	{
		return billingHelper.createBillingPayInqRs(parameters);
	}

	public IFXRs_Type createBillingPayPrepRs(IFXRq_Type parameters) throws RemoteException
	{
		return billingHelper.createBillingPayPrepRs(parameters);
	}

	public IFXRs_Type createBillingPayExecRs(IFXRq_Type parameters) throws RemoteException
	{
		return billingHelper.createBillingPayExecRs(parameters);
	}

	public IFXRs_Type createBillingPayCanRs(IFXRq_Type parameters) throws RemoteException
	{
		return billingHelper.createBillingPayCanRs(parameters);
	}

	/**
	 * @param parameters запрос
	 * @return ответ на запрос изменения видимости счетов в УКО
	 */
	public IFXRs_Type createBankAcctPermissModRs(IFXRq_Type parameters) throws RemoteException
	{
		BankAcctPermissModRq_Type request = parameters.getBankAcctPermissModRq();
		BankAcctPermissModRs_Type response = new BankAcctPermissModRs_Type();
		response.setOperUID(request.getOperUID());
		response.setRqTm(PaymentsRequestHelper.generateRqTm());
		response.setRqUID(PaymentsRequestHelper.generateUUID());
		response.setStatus(getStatus());
		List<DepAcctRec_Type> recs = new ArrayList<DepAcctRec_Type>(); 
		for (DepAcctRec_Type acctId : request.getDepAcctRec())
		{
			DepAcctRec_Type rec = new DepAcctRec_Type();
			rec.setDepAcctId(acctId.getDepAcctId());
			rec.setStatus(getStatus());
			recs.add(rec);
		}
		BankAcctResult_Type bankAcctResult = new BankAcctResult_Type();
		bankAcctResult.setDepAcctRec(recs.toArray(new DepAcctRec_Type[recs.size()]));
		response.setBankAcctResult(bankAcctResult);

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setBankAcctPermissModRs(response);
		return ifxRs;
	}

	/**
	 * @param parameters запрос
	 * @return ответ на запрос изменения ограничения на печать и использование одноразовых паролей по карте
	 */
	public IFXRs_Type createOTPRestrictionModRs(IFXRq_Type parameters) throws RemoteException
	{
		OTPRestrictionModRq_Type request = parameters.getOTPRestrictionModRq();
		OTPRestrictionModRs_Type response = new OTPRestrictionModRs_Type();
		response.setOperUID(request.getOperUID());
		response.setRqTm(PaymentsRequestHelper.generateRqTm());
		response.setRqUID(PaymentsRequestHelper.generateUUID());
		response.setStatus(getStatus());

		List<CardAcctRec_Type> recs = new ArrayList<CardAcctRec_Type>();
		for (CardAcctRec_Type cardId : request.getCardAcctRec())
		{
			CardAcctRec_Type rec = new CardAcctRec_Type();
			rec.setCardAcctId(cardId.getCardAcctId());
			rec.setStatus(getStatus());
			recs.add(rec);
		}
		BankAcctResult_Type bankAcctResult = new BankAcctResult_Type();
		bankAcctResult.setCardAcctRec(recs.toArray(new CardAcctRec_Type[recs.size()]));
		response.setBankAcctResult(bankAcctResult);

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setOTPRestrictionModRs(response);
		return ifxRs;
	}

	/**
	 * Запрос на наличие выписки в шине
	 * @param parameters - параметры запроса
	 * @return - ответ
	 */
	public IFXRs_Type createHasInfoInqRs(IFXRq_Type parameters) throws RemoteException
	{
		IdentityCard_Type identityCard = parameters.getPfrHasInfoInqRq().getCustInfo().getPersonInfo().getIdentityCard();
		/**
		 * Передается либо документ удостоверяющий личность, либо номер СНИЛС клиента. 
		 */
		if (identityCard != null)
		{
			try
			{
				verifyIdentityCard(identityCard, false);
			}
			catch (GateException e)
			{
				throw new RemoteException(e.getMessage());
			}
		}
		else
		{
			String account = parameters.getPfrHasInfoInqRq().getCustInfo().getPersonInfo().getPFRAccount();
			if (StringHelper.isEmpty(account))
				throw new RemoteException("Не заполнен Страховой Номер Индивидуального Лицевого Счёта");
		}
		return pfrResponseHelper.createHasInfoInqRs(parameters);
	}

	/**
	 * Запрос на получение информации ПФР
	 * @param parameters - параметры запроса
	 * @return - ответ
	 */
	public IFXRs_Type createGetInfoInqRs(IFXRq_Type parameters) throws Exception
	{
		return pfrResponseHelper.createGetInfoInqRs(parameters);
	}

	/**
	 * Запрос на открытие нового срочного вклада путем перевода средств с существующего вклада
	 * @param parameters параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createDepToNewDep(IFXRq_Type parameters) throws RemoteException
	{
		DepToNewDepAddRq_Type request = parameters.getDepToNewDepAddRq();

		IdentityCard_Type identityCard_type = request.getXferInfo().getAgreemtInfo().getDepInfo().getCustRec().getCustInfo().getPersonInfo().getIdentityCard();
		try
		{
			verifyIdentityCard(identityCard_type, false);
		}
		catch (GateException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}

		return accountOpeningHelper.createDepToNewDep(parameters);
	}

	/**
	 * Запрос на открытие бессрочного вклада без первоначального взноса
	 * @param parameters - параметры запроса
	 * @return ответ
	 */
	public IFXRs_Type createNewDepAdd(IFXRq_Type parameters) throws RemoteException
	{
		DepToNewDepAddRq_Type request = parameters.getNewDepAddRq();

		IdentityCard_Type identityCard_type = request.getXferInfo().getAgreemtInfo().getDepInfo().getCustRec().getCustInfo().getPersonInfo().getIdentityCard();
		try
		{
			verifyIdentityCard(identityCard_type, false);
		}
		catch (GateException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}

		return accountOpeningHelper.createNewDepAdd(parameters);
	}

	/**
	 * Запрос на открытие нового срочного вклада путем перевода средств с карты
	 * @param parameters параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createCardToNewDep(IFXRq_Type parameters) throws RemoteException
	{
		CardToNewDepAddRq_Type request = parameters.getCardToNewDepAddRq();

		IdentityCard_Type identityCard_type = request.getXferInfo().getAgreemtInfo().getDepInfo().getCustRec().getCustInfo().getPersonInfo().getIdentityCard();
		try
		{
			verifyIdentityCard(identityCard_type, false);
		}
		catch (GateException e)
		{
			throw new RemoteException(e.getMessage());
		}

		return accountOpeningHelper.createCardToNewDep(parameters);
	}

	/**
	 * Генерация информации по дополнительным картам
	 * @param ifxRq запрос
	 * @return информации по дополнительным картам
	 */
	public IFXRs_Type createCardAdditionalInfoRs(IFXRq_Type ifxRq)
	{
		return cardResponseHelper.createCardAdditionalInfoRs(ifxRq);
	}

	private void verifyIdentityCard(IdentityCard_Type identityCard_type, boolean isCEDBO) throws GateException
	{
		String idType = identityCard_type.getIdType();
		String series = identityCard_type.getIdSeries();
		String number = identityCard_type.getIdNum();

		ClientDocumentType documentType = PassportTypeWrapper.getClientDocumentType(idType);
		ClientDocumentController.validateDocument(series, number, documentType.name(), isCEDBO);
	}


	/**
	 * Преобразуем массив строк в список данных типа Long
	 * Используется для преобразования идентификаторов
	 * Входной параметр не может быть пустым 
	 * @param strIds -строка, содержащих численные символы (id), разделенные запятой
	 * @return
	 */
	public static List<Long> arrayStrToListLong(String strIds)
	{
		String[] ids = strIds.replaceAll(" ", "").split(",");
		List<Long> listIds = new ArrayList<Long>(ids.length);
		for (int i = 0; i < ids.length; i++)
		{
			listIds.add(Long.parseLong(ids[i]));
		}
		return listIds;
	}

	/**
	 * Заявка на утерю сберкнижки (SACS)
	 * @param ifxRq - параметры запроса
	 * @return - ответ
	 */
	public IFXRs_Type createSetAccountStateRs(IFXRq_Type ifxRq)
	{
		//Параметры запроса
		SetAccountStateRq_Type request = ifxRq.getSetAccountStateRq();

		SetAccountStateRs_Type setAccountStateRs = new SetAccountStateRs_Type();
		setAccountStateRs.setOperUID(request.getOperUID());
		setAccountStateRs.setRqTm(PaymentsRequestHelper.generateRqTm());
		setAccountStateRs.setRqUID(PaymentsRequestHelper.generateUUID());
		setAccountStateRs.setStatus(getStatus());

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setSetAccountStateRs(setAccountStateRs);
		return ifxRs;
	}

	/**
	 * Интерфейс GAPL получения списка платежей по подписке
	 * @param ifxRq запрос.
	 * @return ответ (структура GetAutoPaymentListRs)
	 */
	public IFXRs_Type createAutoSubscriptionListRs(IFXRq_Type ifxRq)
	{
		return autoSubscriptionHelper.createAutoSubscriptionListRs(ifxRq);
	}

	/**
	 * Интерфейс ASM cоздания/изменения подписки
	 * @param ifxRq запрос
	 * @return ответ (структура AutoSubscriptionModRs)
	 */
	public IFXRs_Type createAutoSubscriptionModRs(IFXRq_Type ifxRq)
	{
		return autoSubscriptionHelper.createAutoSubscriptionModRs(ifxRq);
	}

	/**
	 * Интерфейс GASDI получения детальной информации по подписке
	 * @param ifxRq запрос
	 * @return ответ (структура GetAutoSubscriptionDetailInfoRs)
	 */
	public IFXRs_Type createAutoSubscriptionDetailInfoRs(IFXRq_Type ifxRq)
	{
	    return autoSubscriptionHelper.createAutoSubscriptionDetailInfoRs(ifxRq);
	}

	/**
	 * Интерфейс GAPL получения списка платежей по подписке
	 * @param ifxRq запрос.
	 * @return ответ (структура GetAutoPaymentListRs)
	 */
	public IFXRs_Type createAutoPaymentListRs(IFXRq_Type ifxRq)
	{
	    return autoSubscriptionHelper.createAutoPaymentListRs(ifxRq);
	}

	/**
	 * Интерфейс GAPDI получения детальной информации по платежам
	 * @param ifxRq запрос
	 * @return ответ (структура GetAutoPaymentDetailInfoRs)
	 */
	public IFXRs_Type createGetAutoPaymentDetailInfoRs(IFXRq_Type ifxRq)
	{
	    return autoSubscriptionHelper.createGetAutoPaymentDetailInfoRs(ifxRq);
	}

	/**
	 * Интерфейс ASSM приостановки/возобновления/закрытия подписки
	 * @param ifxRq запрос
	 * @return ответ (структура AutoSubscriptionStatusModRq)
	 */
	public IFXRs_Type createAutoSubscriptionStatusModRs(IFXRq_Type ifxRq)
	{
		return autoSubscriptionHelper.createAutoSubscriptionStatusModRs(ifxRq);
	}

	/**
	 * Запрос на открытие ОМС путем перевода денежных средств со вклада
	 * @param ifxRq параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createDepToNewIMAAddRs(IFXRq_Type ifxRq)
	{
		return imaOpeningResponseHelper.createDepToNewIMAAddRs(ifxRq);
	}

	/**
	 * Перевод карта - ОМС
	 * @param ifxRq параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createCardToIMAAddRs(IFXRq_Type ifxRq)
	{
		return imaPaymentResponseHelper.createCardToIMAAddRs(ifxRq);
	}

	/**
	 * Перевод ОМС - карта
	 * @param ifxRq параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createIMAToCardAddRs(IFXRq_Type ifxRq)
	{
		return imaPaymentResponseHelper.createIMAToCardAddRs(ifxRq);
	}

	/**
	 * Запрос на открытие ОМС путем перевода денежных средств с карты
	 * @param ifxRq параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createCardToNewIMAAddRs(IFXRq_Type ifxRq)
	{
		return imaOpeningResponseHelper.createCardToNewIMAAddRs(ifxRq);
	}

	/**
	 * Запрос на уточнение статуса операции в АБС из ЕРИБ 
	 * @param ifxRq параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createXferOperStatusInfoRs(IFXRq_Type ifxRq)
	{
		return absOperStatusFromEribResponseHelper.createXferOperStatusInfoRs(ifxRq);
	}

	/**
	 * Запрос на получение списка страховых продуктов
	 * @param ifxRq параметры запроса
	 * @return ответ заглушки шины
	 */
	public IFXRs_Type createGetInsuranceListRs(IFXRq_Type ifxRq) throws BusinessException
	{
		GetInsuranceListRq_Type getInsuranceListRq = ifxRq.getGetInsuranceListRq();
		PersonInfo_Type personInfo = getInsuranceListRq.getCustInfo().getPersonInfo();
		Calendar birthDate = null;
		if (!StringHelper.isEmpty(personInfo.getBirthday()))
			birthDate = XMLDatatypeHelper.parseDate(personInfo.getBirthday());
		PersonName_Type name = personInfo.getPersonName();
		IFXRs_Type response = new IFXRs_Type();
		List<Person> persons = personService.getByAttribute(name.getFirstName(), name.getLastName(), name.getMiddleName(), birthDate, null);

		Login login;
		if (persons.isEmpty())
		{
			login = null;
		}
		else
		{
			login = persons.get(0).getLogin();
		}
		response.setGetInsuranceListRs(insuranceHelper.createGetInsuranceListRs(ifxRq, login));
		return response;
	}


	public IFXRs_Type createGetInsuranceAppRs(IFXRq_Type ifxRq)
	{
		return insuranceHelper.createGetInsuranceAppRs(ifxRq);
	}

	public IFXRs_Type createSecuritiesInfoInqRs(IFXRq_Type ifxRq)
	{
		return securityAccountHelper.createSecuritiesInfoInqRs(ifxRq);
	}

	/**
	 * @param ifxRq запрос на изменение условий вклада
	 * @return ответ на заявку изменения условий вклада
	 */
	public IFXRs_Type createChangeAccountInfoRs(IFXRq_Type ifxRq)
	{
		ChangeAccountInfoRq_Type request = ifxRq.getChangeAccountInfoRq();

		ChangeAccountInfoRs_Type changeAccountInfoRs = new ChangeAccountInfoRs_Type();
		changeAccountInfoRs.setOperUID(request.getOperUID());
		changeAccountInfoRs.setRqTm(PaymentsRequestHelper.generateRqTm());
		changeAccountInfoRs.setRqUID(PaymentsRequestHelper.generateUUID());
		changeAccountInfoRs.setStatus(getStatus());
		//Для тестирования уточнения статуса операции
//		Status_Type statusOriginal = new Status_Type();
//		statusOriginal.setStatusCode(UNKNOW_DOCUMENT_STATE_ERROR_CODE);
//		statusOriginal.setStatusDesc("Тестовая ошибка для тестирования уточнения статуса операции");
//		changeAccountInfoRs.setStatus(statusOriginal);
		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setChangeAccountInfoRs(changeAccountInfoRs);
		return ifxRs;
	}


}
