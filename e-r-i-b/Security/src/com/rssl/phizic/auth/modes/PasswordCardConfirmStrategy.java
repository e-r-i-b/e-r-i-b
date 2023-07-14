package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CardPasswordValidator;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.readers.PasswordCardConfirmResponseReader;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.common.forms.doc.DocumentSignature;

import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author$
 * @ $Revision$
 */


public class PasswordCardConfirmStrategy implements ConfirmStrategy
{
	private Exception warning;

	private static PasswordCardService passwordCardService = new PasswordCardService();

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.card;
	}

	public boolean hasSignature()
	{
		return false;
	}

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfirm) throws SecurityException
	{
		try
		{
			PasswordCard card = passwordCardService.getActiveCard((Login) login);

			if(card == null)
				// Если активной карты нет, то возвращаем request с сообщением об ошибке
				return new ErrorConfirmRequest(getType(), "Нет активной карты ключей");

			Integer passwordNumber = passwordCardService.getNextUnusedCardPasswordNumber(card);
			String  cardNumber     = card.getNumber();

			return new PasswordCardConfirmRequest(cardNumber, passwordNumber,null);
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityException, SecurityLogicException
	{
		if (response == null)
			throw new SecurityException("Не установлен запрос на подтверждение");

		if (response == null)
			throw new SecurityException("Не установлен ответ на подтверждение");

		if (!(request instanceof PasswordCardConfirmRequest))
			throw new SecurityException("некорректный тип ConfirmRequest, ожидался PasswordCardConfirmRequest");

		PasswordCardConfirmRequest req = (PasswordCardConfirmRequest) request;
		PasswordCardConfirmResponse res = (PasswordCardConfirmResponse) response;

		CardPasswordValidator validator = new CardPasswordValidator();
		String userId = login.getUserId();

		char[] passwordInfo = CardPasswordValidator.codePasswordInfo(res.getPassword() , req.getPasswordNumber(), req.getCardNumber());

		validator.validateLoginInfo(userId, passwordInfo);

		return new ConfirmStrategyResult(true);
	}

	public PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		// ничего делать не надо
		return null;
	}

	/**
	 * Получить SignatureCreator
	 */
	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException
	{
			throw new UnsupportedOperationException();
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return new PasswordCardConfirmResponseReader();
	}

	public void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		//nothing
	}

	public Exception getWarning()
	{
		return warning;
	}

	public void setWarning(Exception warning)
	{
		this.warning = warning;
	}
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object)
	{
		if(conditions!=null)
		{
			if (conditions.get(getType())!=null)
				{
					for (StrategyCondition condition: conditions.get(getType()))
					{
						if (!condition.checkCondition(object))
						{
							String message = condition.getWarning();
							setWarning(message == null ? null : new SecurityLogicException(message));
							return false;
						}
					}
				}
		}
		return true;
	}
}