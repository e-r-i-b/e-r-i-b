package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.claims.IMAOpeningClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.gate.payments.AccountOrIMAOpeningClaimBase;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningClaimFromAccountSender extends ConvertionSenderBase
{
	private static final String AGREEMENT_TYPE = "IMA";
	protected final PaymentsRequestHelper paymentsRequestHelper;

	public IMAOpeningClaimFromAccountSender(GateFactory factory) throws GateException
	{
		super(factory);
		paymentsRequestHelper = new PaymentsRequestHelper(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return true;
	}

	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountOrIMAOpeningClaimBase))
		{
			throw new GateException("Ожидается IMAOpeningClaim или IMAOpeningFromCardClaim");
		}
		IMAOpeningClaim claim = (IMAOpeningClaim) document;
		XferOperStatusInfoRq_Type request = PaymentsRequestHelper.createXferOperStatusInfoRq(claim);

		request.setSystemId(getSystemId(claim));

		BankInfo_Type bankInfo_Type = paymentsRequestHelper.createAuthBankInfo(claim.getInternalOwnerId());
		request.setBankInfo(bankInfo_Type);
		request.setOperName(getOperationName(claim));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferOperStatusInfoRq(request);
		return ifxRq;
	}

	public IFXRq_Type createRepeatExecRequest(GateDocument document) throws GateException, GateLogicException
	{
		return createRequest(document, paymentsRequestHelper);
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = getStatusType(ifxRs);
		long statusCode = statusType.getStatusCode();
		if (document instanceof SynchronizableDocument)
		{
			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(getExternalId(ifxRs));
		}
		if (document instanceof IMAOpeningClaim)
		{
			if (statusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
			{
				if (!isXferOperStatusInfoRs(ifxRs))
				{
					IMAOpeningClaim imaOpeningClaim = (IMAOpeningClaim) document;
					/*Идентификатор операции*/
					imaOpeningClaim.setOperUId(getOperUid(ifxRs));
					/*Дата передачи сообщения*/
					imaOpeningClaim.setOperTime(getOperTime(ifxRs));
				}
				//Возникновение таймаута
				throw new GateTimeOutException(statusType.getStatusDesc());
			}
		}
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		if(statusCode == -433 && commissionTBSerivice.isCalcCommissionSupport(document))
		{
			//это рассчет комиссии.
			fillCommissions(document, ifxRs);
			throw new PostConfirmCalcCommission();
		}
		if (statusCode != 0)
		{
			//Все ошибки пользовательские. Если описание ошибки не пришло, то выдаем сообщение по умолчанию
			throwGateLogicException(statusType, isXferOperStatusInfoRs(ifxRs)? XferOperStatusInfoRs_Type.class : DepToNewIMAAddRs_Type.class);
		}

		//Для запроса на уточнение статуса операции определим результат выполнения операции (приходит при "statusCode = 0")
		if (isXferOperStatusInfoRs(ifxRs))
		{
			Status_Type originalRequesStatusType = ifxRs.getXferOperStatusInfoRs().getStatusOriginalRequest();
			if (originalRequesStatusType != null)
			{
				long originalRequesStatusCode = originalRequesStatusType.getStatusCode();

				//Запрос не обработан
				if (originalRequesStatusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
					//Возникновение таймаута
					throw new GateTimeOutException(originalRequesStatusType.getServerStatusDesc());
				//АБС вернула ошибку обработки документа
				else if (originalRequesStatusCode != 0)
					throwGateLogicException(originalRequesStatusType, XferOperStatusInfoRs_Type.class);
			}
		}

		fillClaimData(document, ifxRs);
	}

	protected SrcLayoutInfo_Type getSrcLayoutInfo(IFXRs_Type ifxRs)
	{
		return ifxRs.getDepToNewIMAAddRs().getSrcLayoutInfo();
	}

	/**
	 * Заполнение данных заявки: установка счета получателя
	 * @param document
	 * @param ifxRs
	 */
	protected void fillClaimData(GateDocument document, IFXRs_Type ifxRs)
	{
		//Установка счета получателя
		IMAOpeningClaim imaOpeningClaim = (IMAOpeningClaim) document;
		imaOpeningClaim.setReceiverAccount(parseAccountNumber(ifxRs));
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getDepToNewIMAAddRs().getStatus();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID() : ifxRs.getDepToNewIMAAddRs().getRqUID();
	}

	protected String getSystemId(IMAOpeningClaim claim) throws GateLogicException, GateException
	{
		BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
		String accountId = backRefBankrollService.findAccountExternalId(claim.getChargeOffAccount());
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(accountId);
		return compositeId.getSystemIdActiveSystem();
	}

	protected String parseAccountNumber(IFXRs_Type ifxRs)
	{
		
		AgreemtInfoResponse_Type agreemtInfoResponse = isXferOperStatusInfoRs(ifxRs) ?
				ifxRs.getXferOperStatusInfoRs().getTDIO().getAgreemtInfo() : ifxRs.getDepToNewIMAAddRs().getAgreemtInfo();
		if (agreemtInfoResponse == null)
			return null;

		IMAInfoResponse_Type imaInfoResponse = agreemtInfoResponse.getIMAInfo();
		if (imaInfoResponse == null)
			return null;
		return imaInfoResponse.getAcctId();
	}

	protected Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAOpeningClaim))
			throw new GateException("Неверный тип документа, должен быть - IMAOpeningClaim.");

		IMAOpeningClaim imaOpeningClaim = (IMAOpeningClaim) document;
		return imaOpeningClaim.getDestinationAmount().getCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAOpeningClaim))
			throw new GateException("Неверный тип документа, должен быть - IMAOpeningClaim.");

		IMAOpeningClaim imaOpeningClaim = (IMAOpeningClaim) document;
		return imaOpeningClaim.getChargeOffAmount().getCurrency();
	}

	/**
	 * сформировать запрос на исполнение документа.
	 * Заполянеяем заголовки и общие поля.
	 * @param document данные о документе
	 * @return запрос
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAOpeningClaim))
			throw new GateException("Неверный тип документа, должен быть - IMAOpeningClaim, а пришел " + document.getClass());

		IMAOpeningClaim claim = (IMAOpeningClaim) document;

		DepToNewIMAAddRq_Type depToNewIMAAddRq_type = new DepToNewIMAAddRq_Type();
		depToNewIMAAddRq_type.setRqUID(PaymentsRequestHelper.generateUUID());
		depToNewIMAAddRq_type.setRqTm(PaymentsRequestHelper.generateRqTm());
		depToNewIMAAddRq_type.setOperUID(PaymentsRequestHelper.generateOUUID());
		depToNewIMAAddRq_type.setSPName(SPName_Type.BP_ERIB);
		depToNewIMAAddRq_type.setBankInfo(paymentsRequestHelper.createAuthBankInfo(claim.getInternalOwnerId()));

		XferIMAInfo_Type xferInfo = createBodyXferIMAInfo(claim);
		//если подразделение не поддерживает расчет комиссий в цод - ничего не заполняем.
		if(getFactory().service(BackRefCommissionTBSettingService.class).isCalcCommissionSupport(claim))
			xferInfo.setSrcLayoutInfo(getSrcLayoutInfo(claim));

		depToNewIMAAddRq_type.setXferInfo(xferInfo);

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setDepToNewIMAAddRq(depToNewIMAAddRq_type);
		return ifxRq;
	}
	

	private XferIMAInfo_Type createBodyXferIMAInfo(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof IMAOpeningClaim))
			throw new GateException("Неверный тип документа, должен быть - IMAOpeningClaim.");

		IMAOpeningClaim imaOpeningClaim = (IMAOpeningClaim) document;
		XferIMAInfo_Type xferIMAInfo = new XferIMAInfo_Type();
		Client owner = getBusinessOwner(imaOpeningClaim);
		xferIMAInfo.setDepAcctIdFrom(createDepAcctId(getAccount(imaOpeningClaim.getChargeOffAccount(), document.getOffice()), owner));
		xferIMAInfo.setAgreemtInfo(getAgreementInfo(imaOpeningClaim));
		xferIMAInfo.setPurpose(imaOpeningClaim.getGround());
		xferIMAInfo.setCurAmt(imaOpeningClaim.getDestinationAmount().getDecimal());
		xferIMAInfo.setAcctCur(imaOpeningClaim.getDestinationAmount().getCurrency().getCode());
		xferIMAInfo.setCurAmtConv(getCurAmtConvType(imaOpeningClaim));
		xferIMAInfo.setExecute(true);

		return xferIMAInfo;
	}

	/**
	 * заполнить стpуктуру информация по счету.
	 * @param account счет
	 * @param owner владелец
	 * @return DepAcctId_Type
	 */
	protected DepAcctId_Type createDepAcctId(Account account, Client owner) throws GateLogicException, GateException
	{
		DepAcctId_Type depAcct =  super.createDepAcctId(account, owner);
		BankInfo_Type bankInfo = depAcct.getBankInfo();
		bankInfo.setRbTbBrchId(paymentsRequestHelper.getRbTbBrch(owner.getInternalOwnerId()));

		return depAcct;
	}
	
	public AgreemtInfo_Type getAgreementInfo(AbstractTransfer transfer) throws GateException, GateLogicException
	{
		IMAOpeningClaim claim = (IMAOpeningClaim)transfer;

		AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
		agreemtInfo.setAgreemtType(AGREEMENT_TYPE);
		agreemtInfo.setIMAInfo(getIMAInfo(claim));

		return agreemtInfo;
	}

	private IMAInfo_Type getIMAInfo(IMAOpeningClaim claim) throws GateException, GateLogicException
	{
		IMAInfo_Type imaInfo_Type = new IMAInfo_Type();
		CustRec_Type custRec_type = getCutRec(claim);
		imaInfo_Type.setCustRec(custRec_type);
		imaInfo_Type.setAcctCur(claim.getDestinationAmount().getCurrency().getCode());

		imaInfo_Type.setAcctCode(claim.getIMAProductType());
		imaInfo_Type.setAcctSubCode(claim.getIMAProductSubType());

		imaInfo_Type.setBankInfo(getBankInfo_Type(claim));
		
		return imaInfo_Type;
	}

	private com.rssl.phizicgate.esberibgate.ws.generated.MinBankInfo_Type getBankInfo_Type(IMAOpeningClaim claim) throws GateException, GateLogicException
	{
		com.rssl.phizicgate.esberibgate.ws.generated.MinBankInfo_Type bankInfo_type = new  com.rssl.phizicgate.esberibgate.ws.generated.MinBankInfo_Type();
		bankInfo_type.setBranchId(claim.getOfficeVSP());
		bankInfo_type.setAgencyId(claim.getOfficeBranch());

		return bankInfo_type;
	}

	private  CustRec_Type getCutRec(IMAOpeningClaim claim) throws GateException, GateLogicException
	{
		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustInfo(getCustInfoType(claim));
		return custRec;
	}

	private  CustInfo_Type getCustInfoType(IMAOpeningClaim claim)  throws GateException, GateLogicException
	{
		CustInfo_Type custInfo_type = new CustInfo_Type();
		PersonInfo_Type personInfo_type = getPersonInfo(claim);
		custInfo_type.setPersonInfo(personInfo_type);
		return custInfo_type;
	}

	private PersonInfo_Type getPersonInfo(IMAOpeningClaim claim) throws GateException, GateLogicException
	{
		PersonInfo_Type personInfo_type = new PersonInfo_Type();
		Client owner = getBusinessOwner(claim);
		PersonName_Type personName_type = new  PersonName_Type();
		personName_type.setFirstName(owner.getFirstName());
		personName_type.setLastName(owner.getSurName());
		if (!StringHelper.isEmpty(owner.getPatrName()))
			personName_type.setMiddleName(owner.getPatrName());
		personInfo_type.setPersonName(personName_type);
		if (owner.getBirthDay() != null)
			personInfo_type.setBirthday(RequestHelperBase.getStringDate(owner.getBirthDay()));
		personInfo_type.setIdentityCard(getIdentityCard(owner));
		return personInfo_type;
	}

	private IdentityCard_Type getIdentityCard(Client owner)
	{
		List<? extends ClientDocument> documents = owner.getDocuments();
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument clientDocument = documents.get(0);
		IdentityCard_Type identityCard_type = new IdentityCard_Type();
		identityCard_type.setIdType(PassportTypeWrapper.getPassportType(clientDocument.getDocumentType()));
		identityCard_type.setIdSeries(clientDocument.getDocSeries());
		identityCard_type.setIdNum(clientDocument.getDocNumber());
		identityCard_type.setIssuedBy(clientDocument.getDocIssueBy());
		identityCard_type.setIssuedCode(clientDocument.getDocIssueByCode());
		identityCard_type.setIssueDt(RequestHelperBase.getStringDate(clientDocument.getDocIssueDate()));
		identityCard_type.setExpDt(RequestHelperBase.getStringDate(clientDocument.getDocTimeUpDate()));
		return identityCard_type;
	}


	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TDIO;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{

	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getDepToNewIMAAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		return parseCalendar(ifxRs.getDepToNewIMAAddRs().getRqTm());
	}
}
