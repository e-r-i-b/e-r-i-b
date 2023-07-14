package com.rssl.auth.csa.front.operations.auth.verification;

import com.rssl.auth.csa.front.operations.auth.InterchangeCSABackOperationBase;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csa.wsclient.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * Операция начала подтверждения верификации данных
 *
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InitializeVerifyBusinessEnvironmentOperation extends InterchangeCSABackOperationBase
{
	private static final Long DEFAULT_ATTEMPTS_CONFIRM_COUNT = 3L;
	private static final Long DEFAULT_TIMEOUT_CONFIRM = 600L;

	private BusinessEnvironmentOperationInfo operationInfo;

	/**
	 * инициализация операции
	 * @param operationInfo информация об операции
	 * @param cardId внутренний идентификатор карты карты подтверждения
	 */
	public void initialize(BusinessEnvironmentOperationInfo operationInfo, String cardId)
	{
		this.operationInfo = operationInfo;
		Map<String, String> confirmationSource = operationInfo.getCardConfirmationSource();
		if (MapUtils.isNotEmpty(confirmationSource))
			operationInfo.setCardNumber(confirmationSource.get(cardId));
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		try
		{
			operationInfo.setValid(true);
			return CSABackRequestHelper.sendInitializeVerifyBusinessEnvironmentRq(operationInfo.getAuthToken(), operationInfo.getClientExternalId(), operationInfo.getConfirmType(), operationInfo.getCardNumber());
		}
		// окно ввода смс пароля отображается всегда, даже если номер МКБ не найден
		catch (FailureIdentificationException ignored)
		{
			operationInfo.setValid(false);
			updateStubConfirmParams();
			return null;
		}
		// не получили информации из мобильного банка
		catch (MobileBankRegistrationNotFoundException ignored)
		{
			operationInfo.setValid(false);
			updateStubConfirmParams();
			return null;
		}
	}

	protected void processResponce(Document responce) throws FrontException
	{
		try
		{
			operationInfo.setOUID(CSABackResponseSerializer.getOUID(responce));
			operationInfo.setConfirmParams(CSABackResponseSerializer.getConfirmParameters(responce));
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
		}
	}

	private void updateStubConfirmParams()
	{
		Map<String, Object> confirmParams = new HashMap<String, Object>();
		confirmParams.put(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME, DEFAULT_ATTEMPTS_CONFIRM_COUNT);
		confirmParams.put(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME,  DEFAULT_TIMEOUT_CONFIRM);
		operationInfo.setConfirmParams(confirmParams);
	}
}
