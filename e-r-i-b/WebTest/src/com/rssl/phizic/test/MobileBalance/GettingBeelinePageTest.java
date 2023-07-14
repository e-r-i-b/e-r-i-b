package com.rssl.phizic.test.MobileBalance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
 @author: Egorovaa
 @ created: 27.06.2012
 @ $Author$
 @ $Revision$
 */
public class GettingBeelinePageTest extends TestCase
{
	private static final String START_ADDRESS   = "https://uslugi.beeline.ru/loginPage.do?userName=%s&password=%s";
	private static final String PHONE_NUMBER    = "changeIt";
	private static final String PASSWORD        = "changeIt";
	private static final String BALANCE_ADDRESS = "https://uslugi.beeline.ru/vip/prepaid/refreshedPrepaidBalance.jsp";
	private static final String HOSTNAME = "myth";
	private static final int PORT_NUMBER = 8080;

	public void testGettingBeelinePageTest() throws Exception
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

			if (urlConn.getHeaderField("Set-Cookie") != null)
			{
				for (String cook : urlConn.getHeaderFields().get("Set-Cookie"))
				{
					cookie = cookie + cook + ";";
				}
			}
			// защита от бесконечного редиректа
			if (count == 99)
				throw new BusinessException("Бесконечное зацикливание");

			count++;
		}

		URL balanceUrl = new URL(BALANCE_ADDRESS);
		URLConnection balanceConn = createConnection(balanceUrl, cookie, proxy);
		balanceConn.connect();
		System.out.println("URL " + balanceUrl + " connected...");
		BufferedReader balanceBR = new BufferedReader(new InputStreamReader(balanceConn.getInputStream()));

		//Используем html-парсер jTidy
		Tidy tidy = new Tidy();
		tidy.setXHTML(true);
		Document doc = tidy.parseDOM(balanceBR, null);
		balanceBR.close();

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Object balanceObj = xpath.evaluate("//form[@id='refreshedPrepaidBalanceForm']/table/tr/td/text()", doc, XPathConstants.STRING);
		Object timeObj = xpath.evaluate("//form[@id='refreshedPrepaidBalanceForm']/table/tr/td/small/text()", doc, XPathConstants.STRING);

		String[] balance = balanceObj.toString().split("\\p{Zs}", 2);
		String time = timeObj.toString().replaceAll("[^:0-9]", "");

		System.out.println("Остаток на счете: " + balance[0]);
		System.out.println("Валюта: " + balance[1]);
		System.out.println("Актуально на: " + time);
	}

	private URLConnection createConnection(URL url, String cookie, Proxy proxy) throws IOException
	{
		HttpURLConnection urlConn;
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


