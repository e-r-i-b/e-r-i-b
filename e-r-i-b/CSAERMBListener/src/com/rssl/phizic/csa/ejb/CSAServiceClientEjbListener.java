package com.rssl.phizic.csa.ejb;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.common.type.UpdateClientRequest;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoException;
import com.rssl.phizic.csa.exceptions.CSAGetNodeInfoLogicException;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Слушатель очереди сообщений служебного канала на обновление профиля
 */
public class CSAServiceClientEjbListener extends CSAEjbListenerBase
{
	@Override
	protected String getMessageType()
	{
		return "sos-UpdateClient";
	}

	@Override
	protected boolean writeAvailable()
	{
		return ConfigFactory.getConfig(CSAFrontConfig.class).isUpdateClientMessageLogAvailable();
	}

	@Override
	protected MQInfo getMQInfo(String message) throws Exception
	{
		UpdateClientRequest request = sosMessageHelper.getUpdateClientRqInfo(message);
		//Пишем сообщение в лог
		writeMessageToLog(message, request.getRqUID());

		UserInfo newUserInfo = fillUserInfo(request.getClientIdentity());
		UserInfo oldUserInfo = fillUserInfo(request.getClientOldIdentity());
		NodeInfo nodeInfo = getNodeInfo(newUserInfo, oldUserInfo);

		return nodeInfo.getErmbQueueMQ();
	}

	private NodeInfo getNodeInfo(UserInfo newUserInfo, UserInfo oldUserInfo) throws Exception
	{
		try
		{
			Document response = CSABackRequestHelper.sendUpdateProfileRq(newUserInfo, oldUserInfo);
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
