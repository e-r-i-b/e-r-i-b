package com.rssl.phizic.csa.ejb;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoException;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoLogicException;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.messaging.ermb.TransportMessageSerializer;
import com.rssl.phizic.synchronization.types.IdentityType;
import com.rssl.phizic.synchronization.types.ServiceFeeResultRq;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

/**
 * User: Moshenko
 * Date: 06.06.14
 * Time: 9:25
 * Слушатель очереди сообщений транспартного канала, подтверждающих списание аб. платы
 */
public class CSAServiceFeeEjbListener extends CSAEjbListenerBase
{
	private final TransportMessageSerializer messageSerializer = new TransportMessageSerializer();

	@Override
	protected String getMessageType()
	{
		return "sos-FeeResult";
	}

	@Override
	protected boolean writeAvailable()
	{
		return ConfigFactory.getConfig(CSAFrontConfig.class).isFeeResultMessageLogAvailable();
	}

	protected MQInfo getMQInfo(String message) throws Exception
	{
		ServiceFeeResultRq request = messageSerializer.unmarshallServiceFeeResultRequest(message);
		//Пишем сообщение в лог
		writeMessageToLog(message, request.getRqUID());

		NodeInfo nodeInfo = getNodeInfo(fillUserInfo(request.getClientIdentity()));
		return nodeInfo.getErmbQueueMQ();
	}

	protected UserInfo fillUserInfo(IdentityType person)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstname(person.getFirstname());
		userInfo.setSurname(person.getLastname());
		userInfo.setPatrname(person.getMiddlename());
		userInfo.setBirthdate(person.getBirthday());
		String passport = StringHelper.getEmptyIfNull(person.getIdentityCard().getIdSeries()) + StringHelper.getEmptyIfNull(person.getIdentityCard().getIdNum());
		userInfo.setPassport(passport);
		userInfo.setCbCode(person.getTb());

		return userInfo;
	}

	private NodeInfo getNodeInfo(UserInfo userInfo) throws Exception
	{
		try
		{
			Document response = CSABackRequestHelper.sendFindProfileNodeByUserInfoRq(userInfo, false, CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES);
			return CSAResponseUtils.createNodeInfo(response.getDocumentElement());
		}
		catch (BackException e)
		{
			throw new CSAGetNodeInfoException(e);
		}
		catch (BackLogicException e)
		{
			throw new CSAGetNodeInfoLogicException(e.getMessage(), e);
		}
		catch (TransformerException e)
		{
			throw new CSAGetNodeInfoException(e);
		}
	}
}
