package com.rssl.phizic.captcha;

import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.commons.logging.Log;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author bogdanov
 * @ created 07.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class CaptchaServlet extends HttpServlet
{
	private String servletName;
	/**
	 * ���� ��� �������� ���� ������ � ������.
	 */
	private static final String CAPTCHA_KEY_CODE = ".captcha_key_code";

	/**
	 *  ���� � ���������, ������������ ��� ����� ������� � ������� ������
	 */
	private static final String IS_ACTIVE_CAPTCHA = ".is_active_captcha";

	/**
	 * ��� ���������, ��� ������� �������� �����, ���������� � �������
	 */
	public static final String CAPTCHA_CODE_PARAMETER_NAME = "field(captchaCode)";

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		servletName = config.getServletName();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		response.setHeader("Cache-Control", "no-cache, must-revalidate, no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Max-Age", 0);
		response.setContentType("image/png");

		String userColor = request.getParameter("color");
		Integer color = null;
		String userBgColor = request.getParameter("bgcolor");
		Integer bgColor = null;
		try
		{
			color = Integer.parseInt(userColor);
			bgColor = Integer.parseInt(userBgColor);
		}
		catch (Throwable ignore)
		{
		}
		Captcha captcha = new Captcha(color, bgColor);

		HttpSession session = request.getSession();
		session.setAttribute(servletName + CAPTCHA_KEY_CODE, captcha.getCode());

		OutputStream outputStream = response.getOutputStream();
		ImageIO.write(captcha.getCaptcha(), "png", outputStream);
		outputStream.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	/**
	 * ���������� �� ��� ����� � ������ ������ ����������� � �������
	 * @param req ������
	 * @param servletName ��� ��������
	 * @return true - ����������
	 */
	public static boolean isActiveCaptha(HttpServletRequest req, String servletName)
	{
		HttpSession session = req.getSession(false);
		if (session == null)
			return false;

		synchronized (session)
		{
			Boolean isActiveCaptcha = (Boolean) session.getAttribute(servletName + IS_ACTIVE_CAPTCHA);
			return isActiveCaptcha != null && isActiveCaptcha;
		}
	}

	/**
	 * ���������� ������� ���������� �����
	 * @param req ������
	 * @param servletName ��� ��������
	 */
	public static void setActiveCaptha(HttpServletRequest req, String servletName)
	{
		HttpSession session = req.getSession();
		synchronized (session)
		{
			session.setAttribute(servletName + IS_ACTIVE_CAPTCHA, Boolean.TRUE);
		}
	}

	/**
	 * �������� ������� ���������� �����
	 * @param req ������
	 * @param servletName ��� ��������
	 */
	public static void resetActiveCaptha(HttpServletRequest req, String servletName)
	{
		HttpSession session = req.getSession(false);
		if(session == null)
			return;

		synchronized (session)
		{
			session.removeAttribute(servletName + IS_ACTIVE_CAPTCHA);
		}
	}

	protected static CheckState checkCaptchaCode(HttpServletRequest req, String code, String captchaCodeAttributeName, String activeAttributeName)
	{
		if (!ConfigFactory.getConfig(CaptchaConfig.class).isWorking())
			return CheckState.NOT_ACTIVE;

		HttpSession session = req.getSession(false);
		if (session == null)
			return CheckState.NOT_ACTIVE;

		synchronized (session)
		{
			// ���� ����� �� �������
			Boolean isActiveCaptcha = (Boolean) session.getAttribute(activeAttributeName);
			if(isActiveCaptcha == null || !isActiveCaptcha)
				return CheckState.NOT_ACTIVE;

			String savedCaptchaCode = (String) session.getAttribute(captchaCodeAttributeName);
			session.removeAttribute(captchaCodeAttributeName);

			if (StringHelper.isEmpty(savedCaptchaCode))
				return CheckState.INVALID_CODE;

			if(StringHelper.equalsNullIgnore(savedCaptchaCode.toLowerCase(), code))
			{
				session.removeAttribute(activeAttributeName);
				return CheckState.VALID_CODE;
			}

			return CheckState.INVALID_CODE;
		}
	}

	/**
	 * ���������� �������� ���� ������.
	 * ��� ������ �������� �����������, ��� ������ ��������� �� ������.
	 *
	 * @param req �������.
	 * @param code ��� ��� ��������.
	 * @return ������� ���� �� ������������ ��� ���.
	 */
	public static CheckState checkCaptchaCode(HttpServletRequest req, String code, String servletName)
	{
		return checkCaptchaCode(req, code, servletName + CAPTCHA_KEY_CODE, servletName + IS_ACTIVE_CAPTCHA);
	}

	/**
	 * ��������� �������� �����
	 */
	public static enum CheckState
	{
		/**
		 * ����� �� �������
		 */
		NOT_ACTIVE,

		/**
		 * �� ������ ���
		 */
		INVALID_CODE,

		/**
		 * ������ ���
		 */
		VALID_CODE,
	}

	/**
	 * ������������� ����, ����������� �� �������
	 * @param code ��� �����, ���������� �� �������
	 * @return �������������� ��� �����
	 */
	public static String convertCaptchaCode(String code)
	{
		if (StringHelper.isEmpty(code))
		{
			return null;
		}

		StringBuilder realCode = new StringBuilder();
		String[] s = code.split("_");
		try
		{
			for (int i = 0; i < s.length; i++)
			{
				realCode.append((char) Integer.parseInt(s[i]));
			}
		}
		catch (NumberFormatException ex)
		{
			log.error(ex);
			return null;
		}

		return realCode.toString().toLowerCase();
	}

	/**
	 * ��������, ���������� �� ����� ��� ���
	 * @param request ������
	 * @param servletName ��� ��������, ��� ������� ��������� ����� � ������
	 * @param parameterName ��� ���������, ��� ������� �������� �����, ���������� � �������
	 * @return ��, ���� ����� ����������. ��� � ��������� ������
	 */
	public static boolean wrongCaptcha(HttpServletRequest request, String servletName, String parameterName)
	{
		//���������� �����
		String code = convertCaptchaCode(request.getParameter(parameterName));
		CaptchaServlet.CheckState checkState = CaptchaServlet.checkCaptchaCode(request, code, servletName);

		// ���� ����� ��������
		if (checkState == CaptchaServlet.CheckState.VALID_CODE)
			return false;

		// ���� �� ���������� ���
		return (checkState == CaptchaServlet.CheckState.INVALID_CODE);
	}

	/**
	 * ��������, ���������� �� ����� ��� ���
	 * @param request ������
	 * @param servletName ��� ��������, ��� ������� ��������� ����� � ������
	 * @return ��, ���� ����� ����������. ��� � ��������� ������
	 */
	public static boolean wrongCaptcha(HttpServletRequest request, String servletName)
	{
		return wrongCaptcha(request, servletName, CAPTCHA_CODE_PARAMETER_NAME);
	}
}
