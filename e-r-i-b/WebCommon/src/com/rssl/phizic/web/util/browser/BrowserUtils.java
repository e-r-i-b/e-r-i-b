package com.rssl.phizic.web.util.browser;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.util.PersonInfoUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lepihina
 * @ created 05.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class BrowserUtils
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	/**
	 * @return true, если на странице нужен фреймворк Dojo
	 */
	public static boolean isDojoRequired()
	{
		try
		{
			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			Application application = applicationConfig.getLoginContextApplication();

			switch (application)
			{
				case PhizIC:
					return PersonInfoUtil.isAvailableWidget() && !isOldBrowser();

				default:
					return false;
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Проверяет является ли браузер клиента устаревшим.
	 * @return тип браузера
	 */
	public static boolean isOldBrowser()
	{
		Pair<String, Browser> browserInfo = getBrowserInfo();
		if (browserInfo == null)
			return true;
		return compareBrowserVersions(browserInfo.getFirst(), browserInfo.getSecond().getWidgetVersion()) < 0;
	}

	/**
	 * Проверяет является ли браузер клиента IE.
	 * @return true -- браузер клиента IE.
	 */
	public static boolean isIE()
	{
		Pair<String, Browser> browserInfo = getBrowserInfo();
		if (browserInfo == null)
			return false;
		Browser browser = browserInfo.getSecond();
		return Browser.MSIE == browser;
	}

	/**
	 * @return Поддерживает ли браузер CSS3
	 */
	public static boolean isModernCSS()
	{
		Pair<String, Browser> browserInfo = getBrowserInfo();
		if (browserInfo == null)
			return true;   // если браузер не определен (например IE11), предполагаем, что он поддерживает css3
		return compareBrowserVersions(browserInfo.getFirst(), browserInfo.getSecond().getCssVersion()) >= 0;
	}

	/**
	 * Получаем версию браузера и браузер
	 * @return версия браузера и браузер
	 */
	private static Pair<String, Browser> getBrowserInfo()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		String browserInfo = request.getHeader("User-Agent");
		if (browserInfo == null || browserInfo.trim().length()==0)
			return null;

        String info = browserInfo.trim().toLowerCase();

		// IE
        if (info.indexOf("msie") != -1 && info.indexOf("opera") == -1)
        {
	        // в режиме совместимости операемся на версию trident
			if(info.indexOf("trident") != -1)
			{
				//Определяем версию trident
				String tridentVersion = parseVersion(info.substring(info.indexOf("trident") + 8));
				int dotIndex = tridentVersion.indexOf(".");
				Long ieVersionAsNumber = Long.valueOf(tridentVersion.substring(0, dotIndex));
				// реальная версия браузера отличается от версии trident на 4
				String version = (ieVersionAsNumber + 4) + tridentVersion.substring( dotIndex);
				return new Pair<String, Browser>(version, Browser.MSIE);
			}
	        String version = parseVersion(info.substring(info.indexOf("msie") + 5));
			return new Pair<String, Browser>(version, Browser.MSIE);
        }
		//Firefox
        if (info.indexOf("firefox") != -1)
        {
	        String version = parseVersion(info.substring(info.indexOf("firefox") + 8));
	        return new Pair<String, Browser>(version, Browser.FIREFOX);
        }
		//Opera
        if (info.indexOf("opera") != -1)
        {
	        String version = parseVersion(info.substring(info.indexOf("version") + 8));
	        return new Pair<String, Browser>(version, Browser.OPERA);
        }
        //Chrome. На chrome надо проверять раньше safari. Он тоже содержит подстроку safari.
        if (info.indexOf("chrome") != -1)
        {
	        String version = parseVersion(info.substring(info.indexOf("chrome") + 7));
	        return new Pair<String, Browser>(version, Browser.CHROME);
        }
		//Safari
        if (info.indexOf("safari") != -1)
        {
	        String version = parseVersion(info.substring(info.indexOf("version") + 8));
	        return new Pair<String, Browser>(version, Browser.SAFARI);
        }
        return null;
	}

	/**
	 * Парсит версию браузера
	 * @param version -версия браузера
	 * @return версия брайзера
	 */
	private static String parseVersion(String version)
    {
        if (version == null || version.length() == 0)
        {
            return "";
        }

        int endOfNumbers = 0;
        while (version.length() > endOfNumbers && (Character.isDigit(version.charAt(endOfNumbers)) || version.charAt(endOfNumbers) == '.'))
        {
            endOfNumbers++;
        }

        try
        {
            return version.substring(0, endOfNumbers);
        }
        catch (NumberFormatException ex)
        {
            return "";
        }
    }

	/**
	 * Сравнивает версии браузера
	 * @param version1 версия браузера
	 * @param version2 версия браузера
	 * @return результат сравнения
	 */
	private static int compareBrowserVersions(String version1, String version2)
	{
		int pointIndex1 = 0;
		int pointIndex2 = 0;
		while (version1.length() > 0 && version2.length() > 0)
		{
			pointIndex1 = version1.indexOf(".") > 0 ? version1.indexOf(".") : version1.length();
			pointIndex2 = version2.indexOf(".") > 0 ? version2.indexOf(".") : version2.length();

			int num1 = Integer.parseInt(version1.substring(0, pointIndex1));
			int num2 = Integer.parseInt(version2.substring(0, pointIndex2));

			if (num1 > num2) return 1;
			if (num1 < num2) return -1;

			version1 = version1.length() > pointIndex1 ? version1.substring(pointIndex1+1) : "";
			version2 = version2.length() > pointIndex2 ? version2.substring(pointIndex2+1) : "";
		}

		return version1.length() - version2.length();
	}
}
