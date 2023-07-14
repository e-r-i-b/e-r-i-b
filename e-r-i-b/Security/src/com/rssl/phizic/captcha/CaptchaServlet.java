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
	 * Ключ для хранения кода каптчи в сессии.
	 */
	private static final String CAPTCHA_KEY_CODE = ".captcha_key_code";

	/**
	 *  Ключ к аттрибуту, указывающему что капча активна в текущей сессии
	 */
	private static final String IS_ACTIVE_CAPTCHA = ".is_active_captcha";

	/**
	 * Имя парамента, под которым хранится капча, присланная с клиента
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
	 * Установлен ли код капчи в рамках сессии привязанной к запросу
	 * @param req запрос
	 * @param servletName имя сервлета
	 * @return true - установлен
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
	 * Установить признак активности капчи
	 * @param req запрос
	 * @param servletName имя сервлета
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
	 * Сбросить признак активности капчи
	 * @param req запрос
	 * @param servletName имя сервлета
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
			// если капча не активна
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
	 * Производит проверку кода каптчи.
	 * Как только проверка выполнилась, код каптчи удаляется из сессии.
	 *
	 * @param req реквест.
	 * @param code код для проверки.
	 * @return пройден тест на человечность или нет.
	 */
	public static CheckState checkCaptchaCode(HttpServletRequest req, String code, String servletName)
	{
		return checkCaptchaCode(req, code, servletName + CAPTCHA_KEY_CODE, servletName + IS_ACTIVE_CAPTCHA);
	}

	/**
	 * Результат проверки капчи
	 */
	public static enum CheckState
	{
		/**
		 * Капча не активна
		 */
		NOT_ACTIVE,

		/**
		 * Не верный код
		 */
		INVALID_CODE,

		/**
		 * Верный код
		 */
		VALID_CODE,
	}

	/**
	 * Декодирование кода, присланного от клиента
	 * @param code Код капчи, присланный от клиента
	 * @return Декодированный код капчи
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
	 * Проверка, правильная ли капча или нет
	 * @param request Запрос
	 * @param servletName Имя сервлета, под которым сохранена капча в сессии
	 * @param parameterName Имя парамента, под которым хранится капча, присланная с клиента
	 * @return Да, если капча правильная. Нет в противном случае
	 */
	public static boolean wrongCaptcha(HttpServletRequest request, String servletName, String parameterName)
	{
		//Декодируем капчу
		String code = convertCaptchaCode(request.getParameter(parameterName));
		CaptchaServlet.CheckState checkState = CaptchaServlet.checkCaptchaCode(request, code, servletName);

		// если верно ответили
		if (checkState == CaptchaServlet.CheckState.VALID_CODE)
			return false;

		// если не правильный код
		return (checkState == CaptchaServlet.CheckState.INVALID_CODE);
	}

	/**
	 * Проверка, правильная ли капча или нет
	 * @param request Запрос
	 * @param servletName Имя сервлета, под которым сохранена капча в сессии
	 * @return Да, если капча правильная. Нет в противном случае
	 */
	public static boolean wrongCaptcha(HttpServletRequest request, String servletName)
	{
		return wrongCaptcha(request, servletName, CAPTCHA_CODE_PARAMETER_NAME);
	}
}
