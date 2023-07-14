package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.AccountRUSPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

/**
 * @author akrenev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый билдер запроса на перевод физическому лицу со вклада на счет (ƒлительное поручение).
 */

abstract class AccountToAccountBankPaymentLongOfferProcessorBase<D extends LongOffer & AccountRUSPayment> extends OnlineMessageProcessorBase<SvcAddRs>
{
	private static final String REQUEST_TYPE = SvcAddRq.class.getSimpleName();

	private final BackRefInfoRequestHelper requestHelper;

	private final D document;
	private SvcAddRq request;

	/**
	 * конструктор
	 * @param factory гейтова€ фабрика
	 * @param document документ
	 */
	protected AccountToAccountBankPaymentLongOfferProcessorBase(GateFactory factory, D document, String serviceName)
	{
		super(ESBSegment.federal, serviceName);
		requestHelper = new BackRefInfoRequestHelper(factory);
		this.document = document;
	}

	protected abstract XferMethodType getXferMethod(D document) throws GateException;

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getXferInfo().getDepAcctIdFrom().getSystemId();
	}

	@Override
	protected String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return Request.SKIP_MONITORING;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = buildRequestObject(document);

		document.setExternalId(ExternalIdGenerator.generateExternalId(request));

		return request;
	}

	@Override
	protected String getResponseId(SvcAddRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected String getResponseErrorCode(SvcAddRs response)
	{
		return String.valueOf(response.getStatus().getStatusCode());
	}

	@Override
	protected String getResponseErrorMessage(SvcAddRs response)
	{
		return response.getStatus().getStatusDesc();
	}

	private SvcAddRq buildRequestObject(D document) throws GateException, GateLogicException
	{
		SvcAddRq svcAddRq= new SvcAddRq();
		svcAddRq.setRqUID(PaymentsRequestHelper.generateUUID());
		svcAddRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		svcAddRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		svcAddRq.setSPName(SPNameType.BP_ERIB);
		svcAddRq.setOperName(OperNameType.SDP);
		svcAddRq.setBankInfo(RequestHelper.makeBankInfo(requestHelper.getRbTbBrch(document.getInternalOwnerId())));

		Client owner = requestHelper.getBusinessOwner(document);
		Account account = requestHelper.getAccount(document.getChargeOffAccount(), document.getOffice());

		XferInfo xferInfo = new XferInfo();

		xferInfo.setXferMethod(getXferMethod(document).toValue());
		xferInfo.setTaxIdTo(document.getReceiverINN());

		xferInfo.setTaxIdFrom(owner.getINN());

		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(RequestHelper.getPersonInfo(document.getReceiverSurName(), document.getReceiverFirstName(), document.getReceiverPatrName()));
		xferInfo.setCustInfo(custInfo);

		xferInfo.setDepAcctIdTo(RequestHelper.createDepAcctId(document.getReceiverAccount(), document.getReceiverBank()));
		xferInfo.setPurpose(document.getGround());

		DepAcctIdType depAcctId = RequestHelper.createDepAcctId(account, owner, requestHelper);
		if (StringHelper.isEmpty(depAcctId.getSystemId()))
			throw new GateLogicException("¬ы не можете выполнить перевод с этого счета. ѕожалуйста, выберите другой счет списани€.");

		xferInfo.setDepAcctIdFrom(depAcctId);

		fillAmount(document, xferInfo);

		svcAddRq.setXferInfo(xferInfo);
		svcAddRq.setRegular(RequestHelper.getRegular(document));
		return svcAddRq;
	}

	private void fillAmount(D document, XferInfo xferInfo) throws GateException
	{
		SumType sumType = document.getSumType();
		if (sumType != SumType.FIXED_SUMMA && sumType != SumType.REMAIND_OVER_SUMMA && sumType != SumType.FIXED_SUMMA_IN_RECIP_CURR)
			return;

		Money amount = document.getAmount();
		if (amount == null)
			throw new GateException("не задана сумма платежа");

		xferInfo.setCurAmt(amount.getDecimal());
		xferInfo.setAcctCur(amount.getCurrency().getCode());
	}
}
