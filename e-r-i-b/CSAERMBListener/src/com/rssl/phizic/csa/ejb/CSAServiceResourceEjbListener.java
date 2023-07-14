package com.rssl.phizic.csa.ejb;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.common.type.UpdateResourceRequest;
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
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Слушатель очереди сообщений служебного канала на обновление продуктов клиента
 */
public class CSAServiceResourceEjbListener extends CSAEjbListenerBase
{
	@Override
	protected String getMessageType()
	{
		return "sos-UpdateResource";
	}

	@Override
	protected boolean writeAvailable()
	{
		return ConfigFactory.getConfig(CSAFrontConfig.class).isUpdateResourceMessageLogAvailable();
	}

	@Override
	protected MQInfo getMQInfo(String message) throws Exception
	{
		UpdateResourceRequest request = sosMessageHelper.getUpdateResourceRqInfo(message);
		//Пишем сообщение в лог
		writeMessageToLog(message, request.getRqUID());

		NodeInfo nodeInfo = getNodeInfo(fillUserInfo(request.getClientIdentity()));
		return nodeInfo.getErmbQueueMQ();
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
