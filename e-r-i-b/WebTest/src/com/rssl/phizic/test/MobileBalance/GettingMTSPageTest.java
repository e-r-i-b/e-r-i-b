package com.rssl.phizic.test.MobileBalance;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.BigDecimal;

/**
 @author: Egorovaa
 @ created: 02.07.2012
 @ $Author$
 @ $Revision$
 */
public class GettingMTSPageTest  extends TestCase
{
	private static final String START_ADDRESS   = "https://login.mts.ru/amserver/UI/Login?gx_charset=UTF-8&service=lk&IDToken1=%s&IDToken2=%s";
	private static final String PHONE_NUMBER    = "changeIt";
	private static final String PASSWORD        = "changeIt";
	private static final String BALANCE_ADDRESS = "https://login.mts.ru/profile/mobile/update";
	private static final String HOSTNAME = "myth";
	private static final int PORT_NUMBER = 8080;

	public void testGettingMTSPageTest() throws IOException, BusinessException
	{
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HOSTNAME, PORT_NUMBER));

		URLConnection urlConn = null;
		String status = null;
		String cookie = "";
		int count = 0;

		while (status == null || urlConn.getHeaderField("Location") != null)
		{
			URL url = new URL((count > 0) ? urlConn.getHeaderField("Location") : String.format(START_ADDRESS, PHONE_NUMBER, PASSWORD));
			urlConn = createConnection(url, cookie, proxy);
			status = urlConn.getHeaderField(0);
			if (urlConn.getHeaderField("SET-COOKIE") != null)
			{
				if (urlConn.getHeaderFields().get("SET-COOKIE") != null)
				{
					for (String cook : urlConn.getHeaderFields().get("SET-COOKIE"))
					{
						cookie = cookie + cook + ";";
					}
				}
				// else, т.к. на первом проходе нужны куки SET-COOKIE, а далее Set-Cookie
				else if (urlConn.getHeaderFields().get("Set-Cookie") != null)
				{
					for (String cook : urlConn.getHeaderFields().get("Set-Cookie"))
					{
						cookie = cookie + cook + ";";
					}
				}
			}
			// защита от бесконечного редиректа
			if (count == 99)
				throw new BusinessException("Бесконечное зацикливание");

			count++;
		}
		URL url = new URL(BALANCE_ADDRESS);
		URLConnection balanceConn = createConnection(url, cookie, proxy);

		BufferedReader startPageReader = new BufferedReader(new InputStreamReader(balanceConn.getInputStream()));

		Pattern p = Pattern.compile("\\{.+\\}");
		String line;
		String responseBalance = "";
		while ((line = startPageReader.readLine()) != null)
		{
			Matcher m = p.matcher(line);
			if (m.find())
			{
				responseBalance = m.group();
			}
		}
		startPageReader.close();

		JsonObject jsonObject = (JsonObject) new JsonParser().parse(responseBalance);
		BigDecimal balance = jsonObject.get("balance").getAsBigDecimal();
		System.out.println("Остаток на счете: " + balance.setScale(2, BigDecimal.ROUND_HALF_EVEN));

		String date = jsonObject.get("actual").toString();
		Pattern datePat = Pattern.compile("\\d{2}:\\d{2}");
		Matcher m = datePat.matcher(date);
		if (m.find())
		{
			System.out.println("Актуально на: " + m.group());
		}

	}

	private URLConnection createConnection(URL url, String cookie, Proxy proxy) throws IOException
	{
		HttpURLConnection urlConn = null;

		if (proxy != null)
			urlConn = (HttpURLConnection) url.openConnection(proxy);
		else
			urlConn = (HttpURLConnection) url.openConnection();

		urlConn.setInstanceFollowRedirects(false);
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);
		urlConn.setReadTimeout(5 * 60 * 1000);
		urlConn.setConnectTimeout(5 * 60 * 1000);
		if (!StringHelper.isEmpty(cookie))
			urlConn.setRequestProperty("Cookie", cookie);

		return urlConn;
	}
}
