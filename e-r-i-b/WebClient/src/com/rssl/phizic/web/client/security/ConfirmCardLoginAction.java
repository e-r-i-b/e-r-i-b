package com.rssl.phizic.web.client.security;

import com.rssl.phizic.auth.CardPasswordValidator;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.passwordcards.NoActivePasswordCardException;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.web.security.ConfirmLoginActionBase;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kosyakov
 * @ created 22.12.2005
 * @ $Author: sergunin $
 * @ $Revision: 61877 $
 */
@SuppressWarnings({"JavaDoc"})
//TODO убрать повторяющиеся строковые литералы.... в ресурсы!
public class ConfirmCardLoginAction extends ConfirmLoginActionBase
{
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.confirmByCard" , "next");
        return map;
    }

	public ActionForward start ( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response )
			throws Exception
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		Calendar start = GregorianCalendar.getInstance();
		Login                login               = (Login) getAuthenticationContext().getLogin();
		PasswordCardService  passwordCardService = new PasswordCardService();
		PasswordCard         card                = passwordCardService.getActiveCard(login);

		ConfirmCardLoginForm frm = (ConfirmCardLoginForm) form;

		if ( card == null )
		{
			if (securityConfig.isCardPasswordAutoAssign()){
				throw new NoActivePasswordCardException();
			}
			ActionMessage error = new ActionMessage("Ошибка регистрации. У Вас нет активной карты ключей. Обратитесь в банк.", false);
			String message = "Ошибка регистрации. У Вас нет активной карты ключей. Обратитесь в банк.";
			writeToOperationLog(start, message);
			return restartAuthentication(request, error);
		}

		frm.setPasswordNumber( passwordCardService.getNextUnusedCardPasswordNumber(card) );
		frm.setPasswordCard( card.getNumber() );
		getAuthenticationContext().setPasswordNumber(frm.getPasswordNumber());
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward next ( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		Calendar start = GregorianCalendar.getInstance();
		Login                login = (Login) getAuthenticationContext().getLogin();
		PasswordCardService  passwordCardService = new PasswordCardService();
		PasswordCard         card                = passwordCardService.getActiveCard(login);

		ConfirmCardLoginForm frm = (ConfirmCardLoginForm) form;

		if ( card == null )
		{
			String message = "Ошибка регистрации. У Вас нет активной карты ключей. Обратитесь в банк.";
			writeToOperationLog(start, message);
			ActionMessage error = new ActionMessage(message, false);
			return restartAuthentication(request, error);
		}

		if(frm.getPassword()==null)
		{
			ActionMessages errors = new ActionMessages();
			String message = "Ошибка регистрации. Введите ключ";
			writeToOperationLog(start, message);

			errors.add("login", new ActionMessage( message, false) );
			saveErrors(request.getSession(), errors);

			return mapping.findForward(FORWARD_SHOW);
		}

		frm.setPasswordCard( card.getNumber() );

		CardPasswordValidator validator = new CardPasswordValidator();

		try
		{
			char[] pwd = CardPasswordValidator.codePasswordInfo(frm.getPassword(), getAuthenticationContext().getPasswordNumber(), frm.getPasswordCard());
			validator.validateLoginInfo(login.getUserId(), pwd);

			completeStage();

			addLogParameters(new CompositeLogParametersReader(
					new SimpleLogParametersReader("Карта ключей", "Номер", frm.getPasswordCard()),
					new SimpleLogParametersReader("Номер пароля карты ключей", getAuthenticationContext().getPasswordNumber()),
					new SimpleLogParametersReader("Введенный клиентом пароль", frm.getPassword())
							));

			return null;
		}
		catch (SecurityLogicException e)
		{
			saveError(request, SecurityMessages.translateException(e));
            card = passwordCardService.getActiveCard(login);
			if ( card == null )
			{
				String message = "Ошибка регистрации. У Вас нет активной карты ключей. Обратитесь в банк.";
				writeToOperationLog(start, message);
				ActionMessage error = new ActionMessage(message, false);
				return restartAuthentication(request, error);
			}
			String message = "Ошибка регистрации.";
			writeToOperationLog(start, message);
			frm.setPasswordNumber( passwordCardService.getNextUnusedCardPasswordNumber(card) );
			frm.setPasswordCard( card.getNumber() );
			getAuthenticationContext().setPasswordNumber(frm.getPasswordNumber());
			return mapping.findForward(FORWARD_SHOW);
		}
	}
}
