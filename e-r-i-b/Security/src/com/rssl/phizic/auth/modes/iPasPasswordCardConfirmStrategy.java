package com.rssl.phizic.auth.modes;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.authgate.*;
import com.rssl.phizic.authgate.passwords.PasswordGateService;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.UserIdClientTypes;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.security.password.InvalidUserIdException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.BlockedException;

/**
 * Author: Moshenko
 * Date: 26.04.2010
 * Time: 12:38:34
 */
public class iPasPasswordCardConfirmStrategy extends PasswordCardConfirmStrategy
{
	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId, PreConfirmObject preConfirm) throws SecurityException
	{
		//признак того что userId получен по выбраной клиентом карте из кардлинка.
		String userId = login.getCsaUserId();
		boolean isUserChoiseCardFromDB = false;
		try
		{
			PasswordGateService passwordGateService = AuthGateSingleton.getPasswordService();
			//если подтвержденире по определнной карте, то userId берем по ней из ћЅ.
			if (preConfirm != null && preConfirm.getPreConfirmParam("confirmUserId") != null)
			{
				Pair<UserIdClientTypes, String> userIdPair = (Pair) preConfirm.getPreConfirmParam("confirmUserId");
				userId = userIdPair.getSecond();
				isUserChoiseCardFromDB = userIdPair.getFirst() == UserIdClientTypes.DB;
			}
			AuthParamsContainer container = new AuthParamsContainer();
		    container.addParameter("UserId", userId);

			AuthParamsContainer result = passwordGateService.prepareOTP(container);
			Integer passwordNumber  = Integer.decode(result.getParameter("PasswordNo"));
			String  cardNumber      = result.getParameter("ReceiptNo");
			String  SID             = result.getParameter("SID");
			String passwordsLeft    = result.getParameter("PasswordsLeft");
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().initializeCardSuccess(cardNumber, result.getParameter("PasswordNo"));
			return new iPasPasswordCardConfirmRequest(cardNumber, passwordNumber, SID, passwordsLeft, null);
		}

	    catch (AuthGateException e)
		{
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().initializeCardFailed(userId);
			throw new SecurityException(e);
		}
		catch (AuthGateLogicException e)
		{
			//если пришел признак неверного имени пользовател€, кидаем специфическое исключение и в дальнейшем актуализируем userId и повтор€ем запрос снова
			if (e.getErrCode() == AuthStatusType.ERR_USRSRV && isUserChoiseCardFromDB)
				throw new InvalidUserIdException("Ќеверное им€ пользовател€(userId)", e);
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().initializeCardFailed(userId);
			throw new SecurityException(e);
		}
	}


	 public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityException, SecurityLogicException
	 {
		if (response == null)
			throw new SecurityException("Ќе установлен запрос на подтверждение");

		if (response == null)
			throw new SecurityException("Ќе установлен ответ на подтверждение");

		if (!(request instanceof iPasPasswordCardConfirmRequest))
			throw new SecurityException("некорректный тип ConfirmRequest, ожидалс€ iPasPasswordCardConfirmRequest");

		iPasPasswordCardConfirmRequest req = (iPasPasswordCardConfirmRequest) request;
		PasswordCardConfirmResponse res = (PasswordCardConfirmResponse) response;
		req.resetMessages();
		String confirmCode = res.getPassword();

		try
		{
			PasswordGateService passwordGateService = AuthGateSingleton.getPasswordService();
			AuthParamsContainer container = new AuthParamsContainer();

			container.addParameter("SID",req.getSID() );
			container.addParameter("password", confirmCode);

			passwordGateService.verifyOTP(container);

			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmSuccess(ConfirmType.CARD, confirmCode);
			return new ConfirmStrategyResult(true);
		}
		catch (AuthGateException e)
	    {
		    ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(ConfirmType.CARD, confirmCode);
			throw new SecurityException(e);

	    }
		catch (AuthGateLogicException e)
		{
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(ConfirmType.CARD, confirmCode);
			req.setErrorFieldPassword(true);
			AuthStatusType errCode = e.getErrCode();
			boolean block = errCode != null && errCode.equals(AuthStatusType.ERR_NOTRIES);
			block = block || errCode != null && errCode.equals(AuthStatusType.ERR_AGAIN) && getAttemps(e.getMessage())==0;
			if (block)
				throw new BlockedException("ѕользователь заблокирован.");
			throw new SecurityLogicException(e.getMessage());
		}
	 }

	private short getAttemps(String erragain)
	{
	    return Short.valueOf(String.valueOf(erragain.toCharArray(),erragain.length()-1,1));
	}
}



