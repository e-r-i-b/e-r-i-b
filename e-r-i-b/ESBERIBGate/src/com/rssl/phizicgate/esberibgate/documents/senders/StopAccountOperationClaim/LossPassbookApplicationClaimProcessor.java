package com.rssl.phizicgate.esberibgate.documents.senders.StopAccountOperationClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса об утере сберегательной книжки
 */

class LossPassbookApplicationClaimProcessor extends OnlineMessageProcessorBase<SetAccountStateRs>
{
	private static final String OK_CODE = "0";

	private static final String REQUEST_TYPE = SetAccountStateRq.class.getSimpleName();

	private final BackRefInfoRequestHelper requestHelper;
	private final LossPassbookApplicationClaim claim;
	private SetAccountStateRq request;

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param claim документ
	 * @param serviceName имя сервиса
	 */
	LossPassbookApplicationClaimProcessor(GateFactory factory, LossPassbookApplicationClaim claim, String serviceName)
	{
		super(ESBSegment.federal, serviceName);
		requestHelper = new BackRefInfoRequestHelper(factory);
		this.claim = claim;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getDepAcctId().getSystemId();
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
		request = buildRequestObject(claim);

		claim.setExternalId(ExternalIdGenerator.generateExternalId(request));

		return request;
	}

	@Override
	protected String getResponseId(SetAccountStateRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected String getResponseErrorCode(SetAccountStateRs response)
	{
		return String.valueOf(response.getStatus().getStatusCode());
	}

	@Override
	protected String getResponseErrorMessage(SetAccountStateRs response)
	{
		return response.getStatus().getStatusDesc();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<SetAccountStateRs>> request, Response<SetAccountStateRs> response) throws GateException, GateLogicException
	{
		if (!OK_CODE.equals(response.getErrorCode()))
			processError(request, response);
	}

	private SetAccountStateRq buildRequestObject(LossPassbookApplicationClaim claim) throws GateLogicException, GateException
	{
		Client client = requestHelper.getBusinessOwner(claim);
		Account account = requestHelper.getAccount(claim.getDepositAccount(), claim.getOffice());

		SetAccountStateRq setAccountStateRq = new SetAccountStateRq();
		setAccountStateRq.setRqUID(RequestHelper.generateUUID());
		setAccountStateRq.setRqTm(RequestHelper.generateRqTm());
		setAccountStateRq.setOperUID(RequestHelper.generateOUUID());
		setAccountStateRq.setSPName(SPNameType.BP_ERIB);

		BankInfoType bankInfo = RequestHelper.makeBankInfo(requestHelper.getRbTbBrch(client.getInternalOwnerId()));
		setAccountStateRq.setBankInfo(bankInfo);

		DepAcctIdType depAcctId = new DepAcctIdType();
		EntityCompositeId compositeId = EntityIdHelper.getAccountCompositeId(account);
		depAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		depAcctId.setAcctId(account.getNumber());
		depAcctId.setBankInfo(bankInfo);
		setAccountStateRq.setDepAcctId(depAcctId);

		List<? extends ClientDocument> documents = client.getDocuments();
		if (CollectionUtils.isEmpty(documents))
			throw new GateException("Не найден документ клиента id=" + client.getId());
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document = documents.get(0);

		PersonInfoType personInfo = RequestHelper.getPersonInfo(client, document);
		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(personInfo);
		setAccountStateRq.setCustInfo(custInfo);

		setAccountStateRq.setAccountStateAction(AccountStateActionType.STATE_SAVE_BOOK_LOST);

		return setAccountStateRq;
	}
}
