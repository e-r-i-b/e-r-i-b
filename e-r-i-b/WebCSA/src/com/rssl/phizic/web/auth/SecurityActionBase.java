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
 * ���� ���������� �� ����������� ������.
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
	protected static final String INVALID_CODE_MESSAGE          = "��������� ��� �� ��������� � ����� �� ��������.";

	/**
	 * �������� ������.
	 */
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);

		// ���� �������� ����� �� ��������� �� ����� ���� � ��� �����
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
	 * ��� �����, ������������� ���� �� ������ �������� ������������ �����
	 * @param mapping ������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward specificCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}

	/**
	 * ��� �����, ������������� ���� �� ������ �������� ����� ��� ���� ��� ������� �����
	 * @param mapping ������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
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
	 * ��������� �� �������� �����
	 * @param request ������
	 * @param servletName ��� �������� ������� �������� �� �����(�� web.xml)
	 * @param securityManager �������� �����
	 * @param key ���� �����
	 * @param isAlwaysShow ��������� �� ���������� ������ �����
	 * @param isProcessAction ��������� �� ���������� ��������(��� ��� ��� ���������� �����)
	 * @param checkState ������ �������� ����� (���� null - ������ �� ���� ���������, � ��������� ������ ������ ������)
	 * @return true - �������� �������� �����
	 */
	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager, String key, boolean isAlwaysShow, boolean isProcessAction, CaptchaServlet.CheckState checkState)
	{
		if (checkState == null)
		{
			checkState = getCaptchaCheckState(request, servletName);
		}

		// ���� ����� ��������
		if(checkState == CaptchaServlet.CheckState.VALID_CODE)
			return false;

		// ���� �� ���������� ���
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
			log.error("������ ��� ���������� ���� ��� �������� ������� ��������", t);
		}

		// ���� ������� �����
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
		//���� ������� ��������� ���������� �������� �� �����
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
	 * @return ������ ��� ������� ��� �������� �����
	 */
	protected List<String> getCaptchaMethodNames()
	{
		return Collections.emptyList();
	}

	/**
	 * ��������� ����������� ������� ����������� �����
	 * @param form �����
	 * @param request ������
	 * @return true - ��������� ����������� �����
	 */
	protected boolean processSpecificCaptha(ActionForm form, HttpServletRequest request, String operationName) throws Exception
	{
		return false;
	}

	/**
	 * @return ���������� �������� �������� ip
	 */
	protected IPSecurityManager getIPSecurityManager()
	{
		return ipSecurityManager;
	}
}
