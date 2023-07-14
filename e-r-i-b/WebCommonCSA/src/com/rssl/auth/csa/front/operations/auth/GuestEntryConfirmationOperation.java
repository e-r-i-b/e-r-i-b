package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.NodeInfo;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

/**
 * @author tisov
 * @ created 26.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Операция по подтвеждению смс-пароля
 */
public class GuestEntryConfirmationOperation extends ConfirmOperationBase
{
	private static final String HOST_TAG_NAME = "host";

	private String password;          //смс-пароль
	private String ouid;              //идентификатор операции

	public GuestEntryConfirmationOperation(String ouid, String password, OperationInfo info)
	{
		this.ouid = ouid;
		this.password = password;
		this.info = info;
	}

	@Override
	protected Document doConfirm() throws BackLogicException, BackException
	{
		return CSABackRequestHelper.sendGuestEntryConfirmationRq(ouid, password);
	}

	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
		GuestEntryOperationInfo operationInfo = (GuestEntryOperationInfo) info;
		try
		{
			NodeInfo nodeInfo = CSABackResponseSerializer.getNodeInfo(responce);
			operationInfo.setHost(nodeInfo.getHost());
			operationInfo.setAuthToken(CSABackResponseSerializer.getOUID(responce));
			operationInfo.setUserRegistered(CSABackResponseSerializer.getUserRegistered(responce));
		}
		catch (TransformerException e)
		{
			throw new FrontException("Ошибка обработки ответа от Back", e);
		}
	}
}
