package com.rssl.phizicgate.mdm.integration.mdm.processors;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.mdm.common.ClientWithProductsInfo;
import com.rssl.phizicgate.mdm.common.SearchClientInfo;
import com.rssl.phizicgate.mdm.integration.mdm.generated.*;
import com.rssl.phizicgate.mdm.integration.mdm.message.OnlineMessageProcessor;
import com.rssl.phizicgate.mdm.integration.mdm.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.mdm.integration.mdm.message.Request;
import com.rssl.phizicgate.mdm.integration.mdm.message.Response;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Процессор запроса поиска инфы
 */

public class SearchProfileProcessor extends OnlineMessageProcessorBase<CustAgreemtInqRs>
{
	private static final String SYSTEM_ID = RequestHelper.getMdmSystemName();
	private static final String REQUEST_TYPE = CustAgreemtInqRq.class.getSimpleName();

	private static final int OK_STATUS_CODE = 0;

	private final SearchClientInfo searchClientInfo;
	private final String mdmId;

	private CustAgreemtInqRq request;
	private ClientWithProductsInfo result;

	/**
	 * конструктор
	 * @param searchClientInfo информация для поиска
	 * @param mdmId идентификатор мдм
	 */
	public SearchProfileProcessor(SearchClientInfo searchClientInfo, String mdmId)
	{
		this.searchClientInfo = searchClientInfo;
		this.mdmId = mdmId;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return SYSTEM_ID;
	}

	@Override
	protected String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = new CustAgreemtInqRq();
		request.setRqUID(RequestHelper.generateUUID());
		request.setRqTm(Calendar.getInstance());
		request.setOperUID(RequestHelper.generateOUUID());
		request.setSPName(RequestHelper.getRequestSPName());
		request.setCustInfo(getCustInfo());
		request.setCustId(getCustId());
		request.setCardAcctId(getCardAcctId());
		return request;
	}

	private CardAcctIdRqType getCardAcctId()
	{
		CardAcctIdRqType cardAcctId = new CardAcctIdRqType();
		cardAcctId.setCardNum(searchClientInfo.getCardNum());
		return cardAcctId;
	}

	private CustIdRqType getCustId()
	{
		if (StringHelper.isEmpty(mdmId))
			return null;

		return RequestHelper.getMDMCustId(mdmId);
	}

	private CustInfoRqType getCustInfo()
	{
		CustInfoRqType custInfo = new CustInfoRqType();
		custInfo.setPersonInfo(getPersonInfo());
		custInfo.setIntegrationInfo(getIntegrationInfo());
		return custInfo;
	}

	private PersonInfoRqType getPersonInfo()
	{
		PersonInfoRqType personInfo = new PersonInfoRqType();
		personInfo.setPersonName(RequestHelper.getPersonName(searchClientInfo.getLastName(), searchClientInfo.getFirstName(), searchClientInfo.getMiddleName()));
		personInfo.setBirthday(searchClientInfo.getBirthday());
		personInfo.setIdentityCard(RequestHelper.getIdentityCard(searchClientInfo.getDocumentType(), searchClientInfo.getDocumentSeries(), searchClientInfo.getDocumentNumber()));
		return personInfo;
	}

	private IntegrationInfo getIntegrationInfo()
	{
		IntegrationInfo integrationInfo = new IntegrationInfo();
		integrationInfo.getIntegrationIds().add(RequestHelper.createEribIntegrationId(searchClientInfo.getInnerId()));
		return integrationInfo;
	}

	@Override
	protected String getResponseId(CustAgreemtInqRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<CustAgreemtInqRs>> request, Response<CustAgreemtInqRs> response) throws GateException
	{
		CustAgreemtInqRs custAgreemtInqRs = response.getResponse();

		Status status = custAgreemtInqRs.getStatus();
		long statusCode = status.getStatusCode();
		if (statusCode != OK_STATUS_CODE)
			processError(request, response, status);

		result = MDMObjectConverter.convert(custAgreemtInqRs);
	}

	/**
	 * @return результат
	 */
	public ClientWithProductsInfo getResult()
	{
		return result;
	}
}
