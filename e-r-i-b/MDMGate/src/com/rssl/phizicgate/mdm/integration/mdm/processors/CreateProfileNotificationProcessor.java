package com.rssl.phizicgate.mdm.integration.mdm.processors;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.mdm.common.CreateNotificationClientInfo;
import com.rssl.phizicgate.mdm.integration.mdm.generated.*;
import com.rssl.phizicgate.mdm.integration.mdm.message.OfflineMessageProcessorBase;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Процессор запроса на создание подписки профиля в мдм
 */

public class CreateProfileNotificationProcessor extends OfflineMessageProcessorBase
{
	private static final String SYSTEM_ID = RequestHelper.getMdmSystemName();
	private static final String REQUEST_TYPE = IFX.class.getSimpleName();

	private final CreateNotificationClientInfo clientInfo;
	private IFX request;

	/**
	 * конструктор
	 * @param clientInfo информация для подписки
	 */
	public CreateProfileNotificationProcessor(CreateNotificationClientInfo clientInfo)
	{
		this.clientInfo = clientInfo;
	}

	@Override
	protected String getRequestId()
	{
		return request.getBaseSvcRq().getRqUID();
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
		request = new IFX();
		request.setBaseSvcRq(getBaseSvcRq());
		return request;
	}

	private BaseSvcRq getBaseSvcRq()
	{
		BaseSvcRq baseSvcRq = new BaseSvcRq();
		String rqUID = RequestHelper.generateUUID();
		baseSvcRq.setRqUID(rqUID);
		baseSvcRq.setRqTm(Calendar.getInstance());
		baseSvcRq.setOperUID(RequestHelper.generateOUUID());
		baseSvcRq.setSPName(RequestHelper.getRequestSPName());
		baseSvcRq.setCustModIdRq(getCustModIdRq(rqUID));
		return baseSvcRq;
	}

	private CustModIdRq getCustModIdRq(String rqUID)
	{
		CustModIdRq custModIdRq = new CustModIdRq();
		custModIdRq.setRqUID(rqUID);
		custModIdRq.setCustId(RequestHelper.getEribCustId(clientInfo.getInnerId()));
		custModIdRq.setCustInfo(getCustInfo());
		return custModIdRq;
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
		personInfo.setPersonName(RequestHelper.getPersonName(clientInfo.getLastName(), clientInfo.getFirstName(), clientInfo.getMiddleName()));
		personInfo.setBirthday(clientInfo.getBirthday());
		personInfo.setIdentityCard(RequestHelper.getIdentityCard(clientInfo.getDocumentType(), clientInfo.getDocumentSeries(), clientInfo.getDocumentNumber()));
		return personInfo;
	}

	private IntegrationInfo getIntegrationInfo()
	{
		IntegrationInfo integrationInfo = new IntegrationInfo();
		integrationInfo.getIntegrationIds().add(RequestHelper.createMDMIntegrationId(clientInfo.getMdmId()));
		return integrationInfo;
	}
}
