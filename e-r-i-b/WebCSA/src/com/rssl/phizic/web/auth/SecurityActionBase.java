package com.rssl.phizic.web.auth;

import com.rssl.auth.csa.front.security.IPSecurityManager;
import com.rssl.auth.security.SecurityManager;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.util.RequestHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн отвечающий за отображение капчти.
 *
 * @author bogdanov
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class SecurityActionBase extends AuthenticationActionBase
{
	private static final String SPECIFIC_CAPTCHA_METHOD_NAME    = "specificCaptcha";
	private static final String COMMON_CAPTCHA_METHOD_NAME      = "commonCaptcha";
	protected static final String CAPTCHA_CODE_PARAMETER_NAME   = "field(captchaCode)";
	protected static final String TURING_TEST_FORWARD           = "turingTest";
	protected static final String INVALID_CODE_MESSAGE          = "Введенный код не совпадает с кодом на картинке.";

	/**
	 * Проверка каптчи.
	 */
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);

		// если проверка капчи не требуется то сразу идем в веб метод
		if (config.isUseCaptchaRestrict())
		{
			String parameter = getParameter(mapping, form, request, response);
			String methodName = getMethodName(mapping, form, request, response, parameter);

			if(processCommonCaptha(request, methodName))
			{
				setReplaceMethodName(request, COMMON_CAPTCHA_METHOD_NAME);
			}
			else if(processSpecificCaptha(form, request, methodName))
			{
				setReplaceMethodName(request, SPECIFIC_CAPTCHA_METHOD_NAME);
			}
		}

		return super.doExecute(mapping, form, request, response);
	}

	/**
	 * Веб метод, срабатывающий если не прошли проверку определенной капчи
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward specificCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}

	/**
	 * Веб метод, срабатывающий если не прошли проверку общей для всех веб методов капчи
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward commonCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(TURING_TEST_FORWARD);
	}

	protected CaptchaServlet.CheckState getCaptchaCheckState(HttpServletRequest request, String servletName)
	{
		String code = CaptchaServlet.convertCaptchaCode(request.getParameter(CAPTCHA_CODE_PARAMETER_NAME));
		return CaptchaServlet.checkCaptchaCode(request, code, servletName);
	}

	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager, String key, boolean isAlwaysShow, boolean isProcessAction)
	{
		return isRequiredShowCaptcha(request, servletName, securityManager, key, isAlwaysShow, isProcessAction, null);
	}
	/**
	 * Требуется ли показать капчу
	 * @param request запрос
	 * @param servletName имя сервлета который отвечает за капчу(из web.xml)
	 * @param securityManager менеджер капчи
	 * @param key ключ капчи
	 * @param isAlwaysShow требуется ли показывать всегда капчу
	 * @param isProcessAction требуется ли обработать действие(или оно уже обработано ранее)
	 * @param checkState статус проверки капчи (если null - значит не было прочитано, и вычисляем статус внутри метода)
	 * @return true - трубется показать капчу
	 */
	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager, String key, boolean isAlwaysShow, boolean isProcessAction, CaptchaServlet.CheckState checkState)
	{
		if (checkState == null)
		{
			checkState = getCaptchaCheckState(request, servletName);
		}

		// если верно ответили
		if(checkState == CaptchaServlet.CheckState.VALID_CODE)
			return false;

		// если не правильный код
		if(checkState == CaptchaServlet.CheckState.INVALID_CODE)
		{
			saveError(request, new ActionMessage(CAPTCHA_CODE_PARAMETER_NAME, new ActionMessage(INVALID_CODE_MESSAGE, false)));
			return true;
		}

		if(isAlwaysShow)
			return true;

		try
		{
			if(isProcessAction)
				securityManager.processUserAction(key);
		}
		catch (Throwable t)
		{
			log.error("Ошибка при обновлении кэша для проверки частоты запросов", t);
		}

		// если слишком часто
		if(!securityManager.userTrusted(key))
			return true;

		return false;
	}

	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager, String key)
	{
		return isRequiredShowCaptcha(request, servletName, securityManager, key, false, false);
	}

	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager,String key, boolean isAlwaysShow)
	{
		return isRequiredShowCaptcha(request, servletName, securityManager, key, isAlwaysShow, true);
	}

	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager,String key, boolean isAlwaysShow, CaptchaServlet.CheckState checkState)
	{
		return isRequiredShowCaptcha(request, servletName, securityManager, key, isAlwaysShow, true, checkState);
	}

	protected boolean processCommonCaptha(HttpServletRequest request, String methodName) throws Exception
	{
		//если активна настройка постоянной проверки по капче
		if (getCaptchaMethodNames().contains(methodName))
		{
			String ipAddress = RequestHelper.getIpAddress(request);
			if(isRequiredShowCaptcha(request, DEFAULT_CAPTCHA_SERVLET_NAME, getIPSecurityManager(), ipAddress, isNeedConstantTuringTest()))
			{
				CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
				return true;
			}
			else
			{
				CaptchaServlet.resetActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			}
		}

		return false;
	}

	/**
	 * @return список веб медотов для проверки капчи
	 */
	protected List<String> getCaptchaMethodNames()
	{
		return Collections.emptyList();
	}

	/**
	 * Обработка специфичных условий отображения капчи
	 * @param form форма
	 * @param request запрос
	 * @return true - требуется отображение капчи
	 */
	protected boolean processSpecificCaptha(ActionForm form, HttpServletRequest request, String operationName) throws Exception
	{
		return false;
	}

	/**
	 * @return актуальный менеджер проверки ip
	 */
	protected IPSecurityManager getIPSecurityManager()
	{
		return ipSecurityManager;
	}
}
