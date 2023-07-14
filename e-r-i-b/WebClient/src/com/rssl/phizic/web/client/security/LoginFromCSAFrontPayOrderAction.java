package com.rssl.phizic.web.client.security;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentIsNotServicedException;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.LoginInfoHelper;
import com.rssl.phizic.business.login.exceptions.DegradationFromUDBOToCardException;
import com.rssl.phizic.business.login.exceptions.StopClientSynchronizationException;
import com.rssl.phizic.business.login.exceptions.StopClientsSynchronizationException;
import com.rssl.phizic.business.operations.restrictions.NullClientRestriction;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.einvoicing.EInvoicingConstants.*;

/**
 * @ author: Vagin
 * @ created: 11.12.2012
 * @ $Author
 * @ $Revision
 * Вход клиента по внешней ссылке из новой ЦСА.
 */
public class LoginFromCSAFrontPayOrderAction extends LoginFromCSAFrontAction
{
	private static final String FORWARD_INVALID = "InvalidPaymentLogin";
	private static final String DEFAULT_ERROR = "Вы не можете войти в систему «Сбербанк Онлайн». Пожалуйста, обратитесь в банк.";

	protected UserVisitingMode getUserVisitingMode()
	{
		return UserVisitingMode.PAYORDER_PAYMENT;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//TODO перевести на использование форм процессора
		AuthenticationContext context = getAuthenticationContext();

		if (StringHelper.isEmpty(context.getAuthenticationParameter(FNS_PAY_INFO)) && StringHelper.isEmpty(context.getAuthenticationParameter(WEBSHOP_REQ_ID)) && StringHelper.isEmpty(context.getAuthenticationParameter(UEC_PAY_INFO)))
		{
			saveError(request, "Данная ссылка предназначена только для оплаты товаров и услуг с других сайтов.");
			return mapping.findForward(FORWARD_INVALID);
		}
		//если пришел, значит клиент уже нажал кнопку "Войти" и прошел проверку в ЦСА. Входим им.
		if (StringHelper.isNotEmpty(getAuthToken(request)))
		{
			return login(mapping, form, request, response);
		}
		saveError(request, DEFAULT_ERROR);
		return mapping.findForward(FORWARD_INVALID);
	}

	/**
	 * Клиент уже залогинился в новой ЦСА. Аутентифицируем его и переходим на след. шаг.
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String authToken = getAuthToken(request);
		//получаем данные аутентификации
		String browserInfo = request.getHeader("User-Agent");
		AuthData authData = getAuthData(authToken, browserInfo);

		//обновляем контекст аутентификации данными аутентификации
		AuthenticationContext context = getAuthenticationContext();
		LoginHelper.updateAuthenticationContext(context, authData);

		AuthentificationSource authentificationSource = AuthentificationSource.full_version;

		log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "Начали процесс аутентификации для клиента.", authentificationSource));

		try
		{
			Person person = LoginHelper.synchronize(authData, authToken, authentificationSource, getUserVisitingMode(), NullClientRestriction.INSTANCE);

            SecurityUtil.checkDepartmentIsServiced(person);

			if (person == null || person.getLogin() == null)
			{
				saveError(request, DEFAULT_ERROR);
				mapping.findForward(FORWARD_INVALID);
			}
			checkLogin(context, authData, person.getLogin(), null);
			return continueStage(mapping, request);
		}
        catch (DepartmentIsNotServicedException e)
        {
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(),false));
            saveErrors(request, errors);
            throw new BusinessException(e);
		}
		catch (StopClientSynchronizationException e)
		{
			if (e.getLogin() == null)
				throw new InactiveExternalSystemException(e.getMessage(), e);

			context.setLogin(e.getLogin());
			if (e.isShowMessage())
				context.putInactiveESMessage(e.getMessage());
			return continueStage(mapping, request);
		}
		catch (DegradationFromUDBOToCardException e)
		{
			checkLogin(context, authData, e.getLogin(), null);
			//Сообщение для клиента
			context.putMessage(e.getMessage());
			//и продолжение аутентификации
			return continueStage(mapping, request);
		}
		catch (StopClientsSynchronizationException e)
		{
			if (CollectionUtils.isEmpty(e.getIds()))
			{
				if (e.isShowMessage())
					throw new InactiveExternalSystemException(e.getMessage(), e);
			}

			log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, request.getParameter("AuthToken"), ". По клиенту найдено " + e.getIds().size() + "  договора(ов) в нашей БД."));
			// У клиента заключен более чем один договор, перенаправляем его на страницу выбора договора,
			// с которым он будет работать. В AuthenticationContext записываем идентификаторы найденных
			// персон для получения списка в следующем шаге аутентификации.
			context.setPersonIds(e.getIds());
			completeStage();

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessLogicException ignore)
		{
			saveError(request, DEFAULT_ERROR);
			return mapping.findForward(FORWARD_INVALID);
		}
	}

	protected ActionForward executeLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		tryStartAuthentication(mapping, request);
		return super.executeLogin(mapping,form,request,response);
	}
}
