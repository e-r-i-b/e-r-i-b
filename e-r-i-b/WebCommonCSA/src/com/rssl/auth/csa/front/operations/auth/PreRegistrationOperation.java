package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class PreRegistrationOperation extends InterchangeCSABackOperationBase
{
	private RegistrationOperationInfo info;

	public void initialize(OperationInfo info, String cardNumber)
	{
		this.info = (RegistrationOperationInfo) info;
		this.info.setCardNumber(cardNumber);
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		try
		{
			return CSABackRequestHelper.sendStartUserRegistrationRq(info.getCardNumber(), ConfirmStrategyType.sms);
		}
		// окно ввода смс пароля отображается всегда, даже если номер МКБ не найден
		catch (FailureIdentificationException ignored)
		{
			info.setValid(false);
			updateStubConfirmParams();
			return null;
		}
		catch (NoMoreAttemptsRegistrationException ignored)
		{
			info.setValid(false);
			updateStubConfirmParams();
			return null;
		}
		catch (MobileBankRegistrationNotFoundException ignored)
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
		catch (CheckIMSIException ignored)
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
			info.setOUID(CSABackResponseSerializer.getOUID(responce));
			info.setConfirmParams(CSABackResponseSerializer.getConfirmParameters(responce));
			info.setConnectorInfos(CSABackResponseSerializer.getConnectorInfos(responce));
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
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
