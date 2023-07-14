package com.rssl.phizic.business.cards;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ReportByCardClaim;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.documents.senders.DocumentSenderBase;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import com.rssl.phizicgate.manager.routing.AdapterService;

/**
 * Отправка заявки на выписку по карте на e-mail.
 *
 * @author bogdanov
 * @ created 29.05.15
 * @ $Author$
 * @ $Revision$
 */

public class ReportByCardClaimSender extends DocumentSenderBase
{
	private static final String SYSTEM_ID_VYPISKA = "urn:sbrfsystems:99-vypiska";
	private static final AdapterService adapterService = new AdapterService();
	private RequestHelperBase requestHelperBase;

	public ReportByCardClaimSender(GateFactory factory)
	{
		super(factory);
		requestHelperBase = new RequestHelperBase(factory);
	}

	@Override
	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());
		ExternalSystemHelper.check(adapterService.getAdapterByUUID(SYSTEM_ID_VYPISKA));

		ReportByCardClaim claim = (ReportByCardClaim) document;
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctStmtImgInqRq_Type bankAcctStmtImgInqRq = new BankAcctStmtImgInqRq_Type();

		bankAcctStmtImgInqRq.setRqUID(PaymentsRequestHelper.generateUUID());
		bankAcctStmtImgInqRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		bankAcctStmtImgInqRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		bankAcctStmtImgInqRq.setSPName(SPName_Type.BP_ERIB);
		bankAcctStmtImgInqRq.setBankInfo(requestHelperBase.generateBankInfo(document));
		bankAcctStmtImgInqRq.setSystemId(SYSTEM_ID_VYPISKA);

		SelRangeDt_Type selRangeDt = new SelRangeDt_Type();
		selRangeDt.setStartDate(PaymentsRequestHelper.getStringDate(claim.getReportStartDate()));
		selRangeDt.setEndDate(PaymentsRequestHelper.getStringDate(claim.getReportOrderType().equals("IR") ? claim.getReportEndDate() : claim.getReportStartDate()));
		bankAcctStmtImgInqRq.setSelRangeDt(selRangeDt);

		CardAcctId_Type cardAcctId = new CardAcctId_Type();

		EntityCompositeId compositeId = EntityIdHelper.getCardCompositeId(getCard(getBusinessOwner(claim), claim.getCardNumber(), claim.getOffice()));
		cardAcctId.setSystemId(compositeId.getSystemId());
		cardAcctId.setCardNum(claim.getCardNumber());
		cardAcctId.setContractNumber(claim.getContractNumber());
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		cardAcctId.setBankInfo(bankInfo);
		bankAcctStmtImgInqRq.setCardAcctId(cardAcctId);

		ContactInfo_Type contactInfo = new ContactInfo_Type();
		contactInfo.setEmailAddr(claim.getEmailAddress());
		contactInfo.setMessageDeliveryType(MessageDeliveryType_Type.E);
		contactInfo.setReportDeliveryType(ReportDeliveryType_Type.fromString(claim.getReportFormat().substring(0, 1)));
		contactInfo.setReportLangType(ReportLangType_Type.fromString(claim.getReportLang()));
		contactInfo.setReportOrderType(claim.getReportOrderType());
		bankAcctStmtImgInqRq.setContactInfo(contactInfo);

		ifxRq.setBankAcctStmtImgInqRq(bankAcctStmtImgInqRq);
		return ifxRq;
	}

	@Override
	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type status = ifxRs.getBankAcctStmtImgInqRs().getStatus();
		if (status.getStatusCode() != 0)
			throw new GateException(status.getStatusDesc());
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}
}
