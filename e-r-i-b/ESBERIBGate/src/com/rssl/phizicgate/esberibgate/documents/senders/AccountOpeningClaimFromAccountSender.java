package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.account.AccountOpeningErrorsAutoRefreshConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.gate.payments.AccountOrIMAOpeningClaimBase;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.DeviceInfoObject;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.Report;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @ author: filimonova
 * @ created: 03.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * Открытие вклада путем перевода со СЧЕТА
 */
public class AccountOpeningClaimFromAccountSender extends AccountClosingPaymentSenderBase
{
	private static final String AGREEMENT_TYPE = "Dep";

	public AccountOpeningClaimFromAccountSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public Currency getDestinationCurrency(AbstractTransfer transfer) throws GateException, GateLogicException
	{
		AccountOpeningClaim claim = (AccountOpeningClaim) transfer;
		return claim.getCurrency();
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new GateException("Неверный тип документа, должен быть - AccountOpeningClaim.");
		}
		AccountOpeningClaim transfer = (AccountOpeningClaim) document;
		return transfer.getChargeOffCurrency();
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return null;
	}

	protected void processPrepareResponse(GateDocument document, IFXRs_Type ifxRs) throws GateLogicException, GateException
	{
		processResponse(document, ifxRs);
		saveInfoMessages(document, ifxRs);
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		if(commissionTBSerivice.isCalcCommissionSupport(document))
		{
			fillCommissions(document, ifxRs);
		}
		fillAmounts(document, ifxRs);
	}

	protected void fillAmounts(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		AbstractTransfer transfer = (AbstractTransfer) document;
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		BigDecimal srcCurAmt;
		SrcLayoutInfo_Type srcLayoutInfo_type = getSrcLayoutInfo(ifxRs);
		//в случае xBank и София, если StatusCode=0, то тег SrcCurAmt возвращается всегда; в случае ЦОД, SrcCurAmt возвращается только в случае, если StatusCode=0 и не приходит SrcLayoutInfo.
		/* SrcCurAmt подсчитываем по следующему алгоритму:
		   ....
		  c). в случае TDDO : сумму всех микроопераций с turnover='CHARGE'
		*/
		if (commissionTBSerivice.isCalcCommissionSupport(document) && srcLayoutInfo_type != null)
		{
			srcCurAmt = getSrcAmountFromSrcLayoutInfo(srcLayoutInfo_type);
		}
		else
			srcCurAmt = ifxRs.getDepToNewDepAddRs().getSrcCurAmt();

		BigDecimal dstCurAmt = ifxRs.getDepToNewDepAddRs().getDstCurAmt();

		if (isConvertion(transfer))
		{
			transfer.setChargeOffAmount(new Money(srcCurAmt, getChargeOffCurrency(transfer)));
		}

		transfer.setDestinationAmount(new Money(dstCurAmt, getDestinationCurrency(transfer)));
	}

	/**
	 * Получение суммы списания по документу согласно микрооперациям списания.
	 * равное сумме всех микроопераций с turnover='CHARGE'
	 * @param srcLayoutInfo - информация о микрооперациях списания
	 * @return сумма списания.
	 */
	protected BigDecimal getSrcAmountFromSrcLayoutInfo(SrcLayoutInfo_Type srcLayoutInfo)
	{
		WriteDownOperation_Type[] commissions = srcLayoutInfo.getWriteDownOperation();
		BigDecimal srcCurAmt = BigDecimal.ZERO;
		if (commissions != null)
		{
			for (WriteDownOperation_Type microOperation : commissions)
			{
				if(microOperation.getTurnover() == Turnover_Type.CHARGE)
					srcCurAmt = srcCurAmt.add(microOperation.getCurAmt());
			}
		}
		return srcCurAmt;
	}

	protected IFXRq_Type createPrepareRequest(GateDocument document) throws GateLogicException, GateException
	{
		return createRequest(document, false);
	}

	protected DepInfo_Type getDepInfo(IFXRs_Type ifxRs)
	{
		return ifxRs.getDepToNewDepAddRs().getAgreemtInfoClose().getDepInfo();
	}

	/**
	 * сформировать запрос на исполнение документа.
	 * Для открытия вкладов - DepToNewDepAddRq
	 * @param document данные о документе
	 * @return запрос
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		return createRequest(document, true);
	}

	private IFXRq_Type createRequest(GateDocument document, boolean execute) throws GateLogicException, GateException
	{
		AccountOpeningClaim transfer = (AccountOpeningClaim) document;

		DepToNewDepAddRq_Type depToNewRq = new DepToNewDepAddRq_Type();

		depToNewRq.setRqUID(PaymentsRequestHelper.generateUUID());
		depToNewRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		depToNewRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		depToNewRq.setSPName(SPName_Type.BP_ERIB);
		depToNewRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
		XferInfo_Type xFerInfo = createBody(transfer, execute);
		fillCommissionWriteDownOperation(transfer, xFerInfo);
		xFerInfo.setWithClose(transfer.isWithClose());
		depToNewRq.setXferInfo(xFerInfo);
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setDepToNewDepAddRq(depToNewRq);
		return ifxRq;
	}

	protected XferInfo_Type createBody(AbstractTransfer transfer, boolean execute) throws GateException, GateLogicException
	{
		// Добавили блок с конверсионной операцией.
		if (!(transfer instanceof ClientAccountsTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - ClientAccountsTransfer.");
		}
		XferInfo_Type xFerInfo = createConversationBody((ClientAccountsTransfer) transfer);
		setFromSourceInfo(transfer, xFerInfo);
		xFerInfo.setAgreemtInfo(getAgreementInfo(transfer));
		AccountOpeningClaim claim = (AccountOpeningClaim) transfer;
		xFerInfo.setPurpose(claim.getGround());
		Money transferCurAmt = getTransferCurAmt(transfer);
		xFerInfo.setCurAmt(execute && transferCurAmt != null ? transferCurAmt.getDecimal() : BigDecimal.ZERO);
		xFerInfo.setExecute(execute);
		//Порядок уплаты процентов и ОСБ и ВСП где должен обслуживаться вклад
		setDepAcctIdTo(transfer, xFerInfo);
		return xFerInfo;
	}

	@Override
	protected SrcLayoutInfo_Type getSrcLayoutInfo(IFXRs_Type ifxRs)
	{
		return ifxRs.getDepToNewDepAddRs().getSrcLayoutInfo();
	}

	protected void setFromSourceInfo(AbstractTransfer transfer, XferInfo_Type xFerInfo) throws GateException, GateLogicException
	{
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		AbstractAccountTransfer accountTransfer = (AbstractAccountTransfer)transfer;
		Account account = getAccount(accountTransfer.getChargeOffAccount(), transfer.getOffice());
		CardOrAccountCompositeId accountId = EntityIdHelper.getAccountCompositeId(account);
		if(StringHelper.isEmpty(accountId.getSystemIdActiveSystem()))
			throw new GateLogicException("Вы не можете выполнить перевод с этого счета. Пожалуйста, выберите другой счет списания.");
		depAcctId.setSystemId(accountId.getSystemId());
		depAcctId.setAcctId(accountId.getEntityId());
		depAcctId.setBankInfo(paymentsRequestHelper.getBankInfo(accountId, null, false));
		xFerInfo.setDepAcctIdFrom(depAcctId);
	}

	/**
	 * Порядок уплаты процентов
	 * @param transfer
	 * @param xFerInfo
	 */
	protected void setDepAcctIdTo(AbstractTransfer transfer, XferInfo_Type xFerInfo) throws GateException
	{
		//Для предварительного сообщения о намерении закрыть вклад не отправляем весь блок, т.к.
		//сообщение нужно только для получения актуальных данных по закрываемому вкладу.
		if (xFerInfo.getExecute() != null && !xFerInfo.getExecute())
			return;

		//Порядок уплаты процентов
		AccountOpeningClaim claim = (AccountOpeningClaim) transfer;
		String percentTransferSource = claim.getPercentTransferSource();
		DepAcctId_Type depAcctId = new DepAcctId_Type();

		if (StringHelper.isNotEmpty(percentTransferSource))
		{
			VariantInterestPayment_Type variantInterestPayment = new VariantInterestPayment_Type();
			boolean isInterestToCard = claim.getPercentTransferSource().equalsIgnoreCase("card");
			variantInterestPayment.setIsInterestToCard(isInterestToCard ? "1" : "0");

			if (isInterestToCard)//на карту
			{
				variantInterestPayment.setCardNumber(claim.getPercentCardNumber());
			}
			depAcctId.setVariantInterestPayment(variantInterestPayment);
		}
		//Информация о банке, в котором нужно открыть счет
		if (StringHelper.isNotEmpty(claim.getCuratorOsb()) && StringHelper.isNotEmpty(claim.getCuratorVsp()))
		{
			//Информация о банке, в котором нужно открыть счет
			BankInfo_Type bankInfo = new BankInfo_Type();
			//ОСБ
			bankInfo.setAgencyId(StringHelper.appendLeadingZeros(claim.getCuratorOsb(), 4));
			//ВСП
			bankInfo.setBranchId(StringHelper.appendLeadingZeros(claim.getCuratorVsp(), 4));
			depAcctId.setBankInfo(bankInfo);
		}
		xFerInfo.setDepAcctIdTo(depAcctId);
	}

	protected AgreemtInfo_Type getAgreementInfo(AbstractTransfer transfer) throws GateException, GateLogicException
	{
		AccountOpeningClaim claim = (AccountOpeningClaim)transfer;

		AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
		agreemtInfo.setAgreemtType(AGREEMENT_TYPE);
		DepInfo_Type depInfo = new DepInfo_Type();
		if (claim.getClosingDate() != null)
			depInfo.setMatDt(RequestHelperBase.getStringDate(claim.getClosingDate()));
		depInfo.setCustRec(createCustRec(claim, getBusinessOwner(transfer)));
		depInfo.setAcctCur(claim.getCurrency().getCode());
		depInfo.setAcctCode(claim.getAccountType());
		depInfo.setAcctSubCode(claim.getAccountSubType());
		depInfo.setIrreducibleAmt(claim.getIrreducibleAmmount());
		depInfo.setBonusRate(getBonusSign(claim));
		agreemtInfo.setDepInfo(depInfo);
		return agreemtInfo;
	}

	private CustRec_Type createCustRec(AccountOpeningClaim claim, Client client) throws GateException
	{
		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustInfo(createCustInfoType(client, claim.getMainDocument()));
		return custRec;
	}

	private CustInfo_Type createCustInfoType(Client client, ClientDocument document) throws GateException
	{
		CustInfo_Type custInfo = new CustInfo_Type();

		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(RequestHelperBase.getStringDate(client.getBirthDay()));
		PersonName_Type personName = new PersonName_Type();
		personName.setLastName(client.getSurName());
		personName.setFirstName(client.getFirstName());
		personName.setMiddleName(client.getPatrName());
		personInfo.setPersonName(personName);

		personInfo.setIdentityCard(getDocument(client, document));
		custInfo.setPersonInfo(personInfo);
		return custInfo;
	}

	private IdentityCard_Type getDocument(Client client, ClientDocument document) throws GateException
	{
		IdentityCard_Type identityCard = new IdentityCard_Type();

		if(document.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
		{
			identityCard.setIdNum(StringHelper.getEmptyIfNull(document.getDocSeries()) + StringHelper.getEmptyIfNull(document.getDocNumber()));
		}
		else
		{
			if (StringHelper.isEmpty(document.getDocNumber()))
				throw new GateException("Некорректный документ клиента id=" + client.getId());

			identityCard.setIdSeries(document.getDocSeries());
			identityCard.setIdNum(document.getDocNumber());
		}
		identityCard.setIdType(PassportTypeWrapper.getPassportType(document.getDocumentType()));
		identityCard.setIssuedBy(document.getDocIssueBy());
		identityCard.setIssuedCode(document.getDocIssueByCode());
		identityCard.setIssueDt(RequestHelperBase.getStringDate(document.getDocIssueDate()));
		identityCard.setExpDt(RequestHelperBase.getStringDate(document.getDocTimeUpDate()));
		return identityCard;
	}


	/**
	 * Определение признака промо- или пенсионной ставки
	 * @param claim - заявка
	 * @return
	 */
	private String getBonusSign(AccountOpeningClaim claim)
	{
		String clientSegment = claim.getSegment();
		if (StringHelper.isNotEmpty(clientSegment)  && claim.isUsePromoRate())
			return "promo" + clientSegment;
		if (claim.isPension())
			return "pension";
		return "nobonus";
	}

	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountOrIMAOpeningClaimBase))
		{
			throw new GateException("Ожидается AccountOpeningClaim или AccountOpeningFromCardClaim");
		}

		AccountOpeningClaim claim = (AccountOpeningClaim) document;
		XferOperStatusInfoRq_Type request = PaymentsRequestHelper.createXferOperStatusInfoRq(claim);

		request.setSystemId(getSystemId(claim));

		BankInfo_Type bankInfo_Type = paymentsRequestHelper.createAuthBankInfo(claim.getInternalOwnerId());
		request.setBankInfo(bankInfo_Type);
		request.setOperName(getOperName());

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferOperStatusInfoRq(request);
		return ifxRq;		
	}

	public IFXRq_Type createRepeatExecRequest(GateDocument document) throws GateException, GateLogicException
	{
		return createRequest(document, paymentsRequestHelper);
	}

	protected String getSystemId(AccountOpeningClaim claim) throws GateLogicException, GateException
	{
		Account account = getAccount(claim.getChargeOffAccount(), claim.getOffice());
		CardOrAccountCompositeId accountId = EntityIdHelper.getAccountCompositeId(account);
		return accountId.getSystemIdActiveSystem();
	}

	protected OperName_Type getOperName()
	{
		return OperName_Type.TDDO;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = getStatusType(ifxRs);
		long statusCode = statusType.getStatusCode();

		if (statusCode != 0L)
		{
			try
			{
				//Логируем ошибки.
				MonitoringLogEntry entry = new MonitoringLogEntry();
				entry.setStateCode(Long.toString(statusCode));
				entry.setDocumentType(MonitoringDocumentType.AOC.name());
				entry.setCreationDate(document.getClientCreationDate());
				if (document instanceof AccountOpeningClaim)
					entry.setAccountType(Long.toString(((AccountOpeningClaim) document).getAccountType()));
				entry.setStartDate(Calendar.getInstance());
				entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
				entry.setApplication(document.getClientCreationChannel().name());
				if (document instanceof DeviceInfoObject)
					entry.setPlatform(((DeviceInfoObject) document).getDeviceInfo());
				ConfigFactory.getConfig(MonitoringOperationConfig.class).writeLog(entry);
			}
			catch (Exception e)
			{
				PhizICLogFactory.getLog(LogModule.Web).error(e);
			}
		}

		if (document instanceof SynchronizableDocument)
		{
			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(getExternalId(ifxRs));
		}
		if (document instanceof AccountOpeningClaim)
		{
			AccountOpeningClaim claim = (AccountOpeningClaim) document;
			if (statusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
			{
				if (!isXferOperStatusInfoRs(ifxRs))
				{
					/*Идентификатор операции*/
					claim.setOperUId(getOperUid(ifxRs));
					/*Дата передачи сообщения*/
					claim.setOperTime(getOperTime(ifxRs));
				}
				//Возникновение таймаута
				throw new GateTimeOutException(statusType.getStatusDesc());
			}

			AccountOpeningErrorsAutoRefreshConfig config = ConfigFactory.getConfig(AccountOpeningErrorsAutoRefreshConfig.class);
			Map<String, String> errorsMap = config.getErrors();
			//Если пришла ошибка по ТБ, ОСБ или ВСП открываемого вклада мы ещё не отправляли такой запрос
			if (errorsMap.containsKey(String.valueOf(statusCode)) &&
					StringHelper.isNotEmpty(claim.getCuratorTb()) &&
					StringHelper.isNotEmpty(claim.getCuratorOsb()) &&
					StringHelper.isNotEmpty(claim.getCuratorVsp()))
			{
				//Ошибки при формировании заявки, если были
				String error =  claim.getClaimErrorMsg();
				//Добавление ошибки, пришедшей из шины
				try
				{
                    Report report;
                    if(StringHelper.isNotEmpty(error)) {
                        report = new Report(error);
                    } else {
                        report = new Report();
                        report.setTb(claim.getCuratorTb());
                        report.setOsb(claim.getCuratorOsb());
                        report.setVsp(claim.getCuratorVsp());
                        report.setId(claim.getCuratorType());
                        report.setCuratorId(claim.getCuratorId());
                    }

					String errorMsg = StringHelper.getEmptyIfNull(statusType.getStatusDesc()) +" "+
							StringHelper.getEmptyIfNull(statusType.getServerStatusDesc());

                    report.setError(true);
                    report.setOp("Нет");
                    report.setOpenIn("Cчёт списания");
					report.setDescription(errorMsg);

					log.info(errorMsg);
                    claim.setClaimErrorMsg(report.getErrorMsg());
				}
				catch (TransformerException e)
				{
					throw new GateException(e);
				}
				//Если пришла ошибка при обработке заявки на открытие вклада в ОСБ клиентского менеджера или промоутера,
				//то пробуем отправить ещё раз, но уже с ТВ, ОСБ, ВСП определенными по-старому
				claim.setCuratorTb(null);
				claim.setCuratorOsb(null);
				claim.setCuratorVsp(null);

				send(claim);
				return;
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
			//Все ошибки пользовательские.
			throwGateLogicException(statusType, isXferOperStatusInfoRs(ifxRs)? XferOperStatusInfoRs_Type.class: getMainInfoClass());
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

	/**
	 * @return класс содержательной части ответа
	 */
	protected Class getMainInfoClass()
	{
		return DepToNewDepAddRs_Type.class;
	}

	/**
	 * Заполнение данных заявки: установка номера открытого вклада
	 * @param document
	 * @param ifxRs
	 */
	protected void fillClaimData(GateDocument document, IFXRs_Type ifxRs)
	{
		if (document instanceof AccountOpeningClaim)
		{
			AccountOpeningClaim claim = (AccountOpeningClaim) document;
			claim.setReceiverAccount(parseAccountNumber(ifxRs));
			//Сохранение ТБ, ОСБ и ВСП в котором в результате был открыт вклад
			parseTbOsbVsp(claim);
		}
	}

	/**
	 * Заполнение ТБ, ОСБ, ВСП, в котором в результате был открыт счет
	 * @param claim - заявка
	 */
	private void parseTbOsbVsp(AccountOpeningClaim claim)
	{
		//Если приехал номер счета, то вклад открылся либо в ВСП клиентского менеджера/промоутера либо по-старому
		if (StringHelper.isNotEmpty(claim.getReceiverAccount()))
		{
			claim.setAccountTb(StringHelper.isNotEmpty(claim.getCuratorTb()) ? claim.getCuratorTb(): claim.getAccountTb());
			claim.setAccountOsb(StringHelper.isNotEmpty(claim.getCuratorOsb()) ? claim.getCuratorOsb(): claim.getAccountOsb());
			claim.setAccountVsp(StringHelper.isNotEmpty(claim.getCuratorVsp()) ? claim.getCuratorVsp(): claim.getAccountVsp());
		}
	}

	protected String parseAccountNumber(IFXRs_Type ifxRs)
	{
		//Пришел ответ на уточнение сатуса операции
		if (isXferOperStatusInfoRs(ifxRs))
		{
		    AgreemtInfoResponse_Type agreemtInfo = ifxRs.getXferOperStatusInfoRs().getTDDO().getAgreemtInfo();
			return agreemtInfo != null ? agreemtInfo.getDepInfo().getAcctId() : null;
		}
		//Пришел ответ на заявку открытия вклада
		AgreemtInfo_Type agreemtInfo = ifxRs.getDepToNewDepAddRs().getAgreemtInfo();
		if (agreemtInfo == null || agreemtInfo.getDepInfo() == null)
			return null;

		return agreemtInfo.getDepInfo().getAcctId();
	}

	/*CurAmt в запросе на исполнение нужно формировать также как и при выключенном режиме отображения комиссий, а именно:
	  a) в случае TDDO передаем DstCurAmt (как без расчета комиссий)
	*/
	private Money getTransferCurAmt(AbstractTransfer transfer) throws GateException, GateLogicException
	{
		return transfer.getDestinationAmount();
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getDepToNewDepAddRs().getStatus();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID() : ifxRs.getDepToNewDepAddRs().getRqUID();
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getDepToNewDepAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		return parseCalendar(ifxRs.getDepToNewDepAddRs().getRqTm());
	}
}
