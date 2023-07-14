package com.rssl.phizic.test.MobileBalance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 @author: Egorovaa
 @ created: 28.06.2012
 @ $Author$
 @ $Revision$
 */
public class GettingMegafonPageTest extends TestCase
{
	private static final String ROBOT_ADDRESS = "https://moscowsg.megafon.ru/ROBOTS/SC_TRAY_INFO?X_Username=%s&X_Password=%s";
	private static final String SERVICE_GUIDE_ADDRESS = "https://moscowsg.megafon.ru/ps/scc/php/check.php?CHANNEL=WWW&";
	private static final String BALANCE_ADDRESS = "https://moscowsg.megafon.ru/SCWWW/ACCOUNT_INFO?CHANNEL=WWW&P_USER_LANG_ID=1&SESSION_ID=";

	private static final String PHONE_NUMBER = "9252809443";
	private static final String PASSWORD = "056051";
	private static final String HOSTNAME = "myth";
	private static final int PORT_NUMBER = 8080;

	public void testGettingMegafonPageTest() throws IOException, XPathExpressionException, SAXException, TransformerException
	{
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HOSTNAME, PORT_NUMBER));

		try
		{
			// Пробуем получить баланс через робота, но он часто не отвечает
			getRobotBalance(proxy);
		}
		catch (Exception e)
		{
			// Если через робота не получилось - запрашиваем страницу сервис-гида
			getServiceGuidePage (proxy);
		}
	}

	/**
	 * Получить баланс через робота
	 * @param proxy - настройка прокси-сервера
	 * @throws IOException
	 * @throws XPathExpressionException
	 * @throws SAXException
	 */
	private void getRobotBalance(Proxy proxy) throws IOException, XPathExpressionException, SAXException, BusinessException, TransformerException
	{
		URL url = new URL(String.format(ROBOT_ADDRESS, PHONE_NUMBER, PASSWORD));
		URLConnection urlConn = createConnection(url, proxy);

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document doc = documentBuilder.parse(urlConn.getInputStream());

		String balance = XmlHelper.getElementValueByPath(doc.getDocumentElement(), "//BALANCE");
		// Если найден тег с информацией о балансе
		if (StringHelper.isNotEmpty(balance))
		{
			System.out.println("Остаток на счете: " + balance);
			String date = XmlHelper.getElementValueByPath(doc.getDocumentElement(), "//DATE");
			if (StringHelper.isNotEmpty(date))
			{
				Pattern p = Pattern.compile("\\d{2}:\\d{2}");
				Matcher m = p.matcher(date);
				if (m.find())
				{
					System.out.println("Актуально на: " + m.group());
				}
			}
		}
		else
			throw new BusinessException("Информация о балансе недоступна");
	}

	/**
	 * Получение баланса через страницу сервис-гида
	 * @param proxy - настройка прокси-сервера
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	private void getServiceGuidePage(Proxy proxy) throws IOException, XPathExpressionException, TransformerException
	{
		URL startURL = new URL(SERVICE_GUIDE_ADDRESS);
		URLConnection startConn = createConnection(startURL, proxy);

		sendMobileAppRequest(startConn);

		BufferedReader startPageReader = new BufferedReader(new InputStreamReader(startConn.getInputStream()));
		Pattern p = Pattern.compile("(?<=<SESSION_ID>).+(?=</SESSION_ID>)");

		String line;
		String sessionId = "";
		while ((line = startPageReader.readLine()) != null)
		{
			Matcher m = p.matcher(line);
			if (m.find())
			{
				sessionId = m.group();
			}

		}
		startPageReader.close();

		// если найден тег с SESSION_ID, т.е. нет ошибки подключения
		if (StringHelper.isNotEmpty(sessionId.toString()))
		{

			URL balanceURL = new URL(BALANCE_ADDRESS + sessionId);
			URLConnection balanceConn = createConnection(balanceURL, proxy);

			//Используем html-парсер jTidy
			Tidy tidy = new Tidy();
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			Document doc = tidy.parseDOM(balanceConn.getInputStream(), null);

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			Object balanceObj = xpath.evaluate("//div[@class='balance_good td_def' or @class='balance_not_good td_def']/text()",
					doc, XPathConstants.STRING);
			String[] balance = balanceObj.toString().split("\\p{Zs}", 2);
			System.out.println("Остаток на счете: " + balance[0]);
			System.out.println("Валюта: " + balance[1]);
		}
		else
		{
			System.out.println("Ошибка при подключении к сервис-гиду");
		}
	}

	private void sendMobileAppRequest(URLConnection urlConn) throws IOException
	{
		String params = "LOGIN=" + PHONE_NUMBER + "&PASSWORD=" + PASSWORD;
		DataOutputStream cgiInput = new DataOutputStream(urlConn.getOutputStream());
		cgiInput.writeBytes(params);
		cgiInput.flush();
	}

	private URLConnection createConnection(URL url, Proxy proxy) throws IOException
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

		return urlConn;
	}
}
