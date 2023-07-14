package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.business.profile.Utils;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class PreRecoverPasswordOperation extends InterchangeCSABackOperationBase
{
	private RecoverPasswordOperationInfo info;

	public void initialize(OperationInfo info, String login)
	{
		this.info = (RecoverPasswordOperationInfo) info;
		this.info.setLogin(login);
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		try
		{
			return CSABackRequestHelper.sendStartRecoverPasswordRq(info.getLogin(), ConfirmStrategyType.sms);
		}
		catch (FailureIdentificationException ignored)
		{
			// окно ввода SMS-пароля отображается даже если логин не найдена
			// ставим флажок что контекст не корретный
			info.setValid(false);
			updateStubConfirmParams();
			return null;
		}
		catch (LoginNotFoundException ignored)
		{
			info.setValid(false);
			updateStubConfirmParams();
			return null;
		}
		catch (SendSmsMessageException ignored)
		{
			info.setValid(false);
			updateStubConfirmParams();
			return null;
		}
	}

	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
		try
		{
			List<ConnectorInfo> connectorInfos = CSABackResponseSerializer.getConnectorInfos(responce);

			updateOperationInfoViaConnectorInfos(connectorInfos);

			info.setOUID(CSABackResponseSerializer.getOUID(responce));
			info.setConfirmParams(CSABackResponseSerializer.getConfirmParameters(responce));
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
		}
	}

	private void updateOperationInfoViaConnectorInfos(List<ConnectorInfo> connectorInfos)
	{
		if(CollectionUtils.isNotEmpty(connectorInfos))
		{
			ConnectorInfo connectorInfo = connectorInfos.get(0);
			info.setConnectorType(connectorInfo.getType());
			info.setUserId(connectorInfo.getUserId());
			String tb = Utils.getCutTBByCBCode(connectorInfo.getCbCode());
			info.setTB(tb);
		}
	}

	private void updateStubConfirmParams()
	{
		Map<String, Object> confirmParams = new HashMap<String, Object>();
		MobileApiConfig config = ConfigFactory.getConfig(MobileApiConfig.class);
		confirmParams.put(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME, Long.valueOf(config.getConfirmationAttemptsCount()));
		confirmParams.put(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME, Long.valueOf(config.getConfirmationTimeout()));
		confirmParams.put(StubConfirmOperationBase.TIME_RECEIVE_ANSWER_CONFIRM_PARAM_NAME, System.currentTimeMillis());

		info.setConfirmParams(confirmParams);
	}
}
