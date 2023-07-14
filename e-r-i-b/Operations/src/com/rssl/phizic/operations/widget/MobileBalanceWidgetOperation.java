package com.rssl.phizic.operations.widget;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.web.MobileBalanceWidget;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.HttpConnector;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Calendar;
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
 @ created: 05.07.2012
 @ $Author$
 @ $Revision$
 */
public class MobileBalanceWidgetOperation extends WidgetOperation<MobileBalanceWidget>
{
	private static final String START_ADDRESS_BEELINE = "https://uslugi.beeline.ru/loginPage.do?userName=%s&password=%s";
	private static final String BALANCE_ADDRESS_BEELINE = "https://uslugi.beeline.ru/vip/prepaid/refreshedPrepaidBalance.jsp";

	private static final String START_ADDRESS_MTS = "https://login.mts.ru/amserver/UI/Login?gx_charset=UTF-8&service=lk&IDToken1=%s&IDToken2=%s";
	private static final String BALANCE_ADDRESS_MTS = "https://login.mts.ru/profile/mobile/update";

	private static final String MEGAFON_ROBOT_ADDRESS = "https://moscowsg.megafon.ru/ROBOTS/SC_TRAY_INFO?X_Username=%s&X_Password=%s";
	private static final String START_ADDRESS_MEGAFON = "https://moscowsg.megafon.ru/ps/scc/php/check.php?CHANNEL=WWW&";
	private static final String BALANCE_ADDRESS_MEGAFON = "https://moscowsg.megafon.ru/SCWWW/ACCOUNT_INFO?CHANNEL=WWW&P_USER_LANG_ID=1&SESSION_ID=";

	private static final String PAYMENT_URL = "?recipient=%s&field(RecIdentifier)=%s";

	private static final int ACTUAL_TIME = 5*60*1000;

	private static final Pattern  MEGAFON_PATTERN = Pattern.compile("(?<=<SESSION_ID>).+(?=</SESSION_ID>)");
	private static final Pattern  MTS_PATTERN = Pattern.compile("\\{.+\\}");

	/**
	 * ���������, �������� �� ����� �� ����� �������, ��� ��������� ��������
	 * @param balance - ����� �� �����
	 * @return true, ���� ������
	 */
	public Boolean isLowBalance(Money balance)
	{
		if (balance == null)
			return false;
		BigDecimal thresholdValue = getThresholdValue();
		if (thresholdValue == null)
		{
			if (balance.getDecimal().compareTo(new BigDecimal(0)) < 0)
				return true;
		}
		else if (balance.getDecimal().compareTo(thresholdValue) < 0 || balance.getDecimal().compareTo(new BigDecimal(0)) < 0)
			return true;
		return false;
	}

	/**
	 * ��������� ���������� �������� �������
	 * @return ��������� ��������
	 */
	private BigDecimal getThresholdValue ()
	{
		MobileBalanceWidget mobileBalanceWidget = getWidget();
		try
		{
			String thresholdValue = mobileBalanceWidget.getThresholdValue();
			if (StringHelper.isEmpty(thresholdValue))
				return null;
			else
			{

				String formatValue = thresholdValue.replace(" ", "").replace(",", ".");
				BigDecimal value = new BigDecimalParser().parse(formatValue);
				return value;
			}
		}
		catch (ParseException e)
		{
			log.error("������ ��� �������������� ���������� �������� �������", e);
			return null;
		}
	}

	/**
	 * ���������, ����� �� ��������� ������ � ������� ���������, ��� �� ��� ����������.
	 * ���� ����� - ����������� ����� � ��������� ��� � ���. ���� ��� - ���������� ������������.
	 * @return ������ �� �����
	 */
	public Money getMobileBalance ()
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		try
		{
			Currency rurCurrency = currencyService.findByAlphabeticCode("RUB");
			BigDecimal currentBalance = null;
			MobileBalanceWidget mobileBalanceWidget = getWidget();
			String savedBalance = mobileBalanceWidget.getBalance();
			Long updateTime = mobileBalanceWidget.getLastUpdateDate();
			// ���� � ���������� ���� ������ � ������� � ������� ��� ����������
			if (StringHelper.isNotEmpty(savedBalance) && updateTime != null)
			{
				// � ���� � ������� ���������� ������� ������ �� �����, ��� "����� ������������"
				// �� ���������� ������ �� ����������. �����- ����������� ������
				if (Calendar.getInstance().getTimeInMillis() - updateTime.longValue() < ACTUAL_TIME)
					return new Money(new BigDecimalParser().parse(savedBalance), rurCurrency);
				else
					currentBalance = getBalanceFromService(mobileBalanceWidget.getProvider());
			}
			else
				currentBalance = getBalanceFromService(mobileBalanceWidget.getProvider());

		//���� �� �������� ������ �� ������� �������, �� �������� ��� ������ � ���
		if (currentBalance != null)
			{
				mobileBalanceWidget.setBalance(currentBalance.toString());
				mobileBalanceWidget.setLastUpdateDate(Calendar.getInstance().getTimeInMillis());
				return new Money(currentBalance, rurCurrency);
			}
			else
				return null;
		}
		catch (GateException e)
		{
			log.error("������ ��� ��������� ������", e);
			return null;
		}
		catch (ParseException e)
		{
			log.error("������ ��� �������������� �������", e);
			return null;
		}
		catch (IOException e)
		{
			log.error("������ ��� ������� ��������� ���������� �������", e);
			return null;
		}
	}

	/**
	 * �������� ������ �� ������� �������
	 * @param provider - �������� �����
	 * @return ����� �� �����
	 */
	public BigDecimal getBalanceFromService(String provider) throws IOException
	{
		if (StringHelper.equalsNullIgnore(provider, "������"))
			return getBeelineBalance();
		if (StringHelper.equalsNullIgnore(provider, "���"))
			return getMTSBalance();
		if (StringHelper.equalsNullIgnore(provider, "�������"))
			return getMegafonBalance();
		return null;
	}

	/**
	 * ��������� ������� ��������. ������� �������� ����� ������, �� �� ����� �� ��������.
	 * � ���� ������ �������� ������ ����� ������-���
	 * @return ������
	 */
	private BigDecimal getMegafonBalance() throws IOException
	{
		BigDecimal balance = getMegafonRobotBalance();
		if (balance != null)
			return balance;
		else
			return getMegafonServiceGuideBalance();
	}

	/**
	 * ��������� ������� �������� � ������� ������
	 * @return ������
	 */
	private BigDecimal getMegafonRobotBalance()
	{
		MobileBalanceWidget mobileBalanceWidget = getWidget();
		HttpConnector httpConnector = new HttpConnector();
		String url = String.format(MEGAFON_ROBOT_ADDRESS, mobileBalanceWidget.getPhoneNumber(), mobileBalanceWidget.getPassword());

		try
		{
			URLConnection urlConn = httpConnector.connect(url);
			DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
			Document doc = documentBuilder.parse(urlConn.getInputStream());

			String balance = XmlHelper.getElementValueByPath(doc.getDocumentElement(), "//BALANCE");
			// ���� ������ ��� � ����������� � �������
			if (StringHelper.isNotEmpty(balance))
				return new BigDecimalParser().parse(balance);
		}
		catch (IOException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + url + " ��������� ������������ ��������", e);
		}
		catch (GeneralSecurityException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + url + " ��������� ������������ ��������", e);
		}
		catch (SAXException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + url + " ��������� ������������ ��������", e);
		}

		catch (TransformerException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + url + " ��������� ������������ ��������", e);
		}
		catch (ParseException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + url + " ��������� ������������ ��������", e);
		}
		return null;
	}

	/**
	 * ��������� ������� ����� ������ ������� ��������
	 * @return ������
	 */
	private BigDecimal getMegafonServiceGuideBalance() throws IOException
	{
		MobileBalanceWidget mobileBalanceWidget = getWidget();
		HttpConnector httpConnector = new HttpConnector();
		String params = "LOGIN=" + mobileBalanceWidget.getPhoneNumber() + "&PASSWORD=" + mobileBalanceWidget.getPassword();
		BufferedReader startPageReader = null;
		URLConnection balanceConn = null;
		try
		{
			URL startURL = new URL(START_ADDRESS_MEGAFON);
			URLConnection startConn = httpConnector.openConnect(startURL);
			httpConnector.sendRequestParams(startConn, params);

			startPageReader = new BufferedReader(new InputStreamReader(startConn.getInputStream()));


			String line;
			String sessionId = "";
			while ((line = startPageReader.readLine()) != null)
			{
				Matcher m = MEGAFON_PATTERN.matcher(line);
				if (m.find())
				{
					sessionId = m.group();
				}
			}

			// ���� ������ ��� � SESSION_ID, �.�. ��� ������ �����������
			if (StringHelper.isNotEmpty(sessionId.toString()))
			{
				balanceConn = httpConnector.connect(BALANCE_ADDRESS_MEGAFON + sessionId);

				//���������� html-������ jTidy
				Tidy tidy = new Tidy();
				tidy.setQuiet(true);
				tidy.setShowWarnings(false);
				Document doc = tidy.parseDOM(balanceConn.getInputStream(), null);

				XPathFactory factory = XPathFactory.newInstance();
				XPath xpath = factory.newXPath();
				Object balanceObj = xpath.evaluate("//div[@class='balance_good td_def' or @class='balance_not_good td_def']/text()", doc, XPathConstants.STRING);
				String[] balance = balanceObj.toString().split("\\p{Zs}", 2);
				return new BigDecimalParser().parse(balance[0]);
			}
		}
		catch (ParseException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + START_ADDRESS_MEGAFON + params + " ��������� ������������ ��������", e);
		}
		catch (GeneralSecurityException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + START_ADDRESS_MEGAFON + params + " ��������� ������������ ��������", e);
		}
		catch (XPathExpressionException e)
		{
			log.error("��� ������� ��������� ���������� ������� �� ������ " + START_ADDRESS_MEGAFON + params + " ��������� ������������ ��������", e);
		}
		finally
		{
			startPageReader.close();
			balanceConn.getInputStream().close();
		}
		return null;
	}

	/**
	 * ��������� ������� ���������� �������� (�������� - ������)
	 * @return ������, ���������� ����� �� �����
	 */
	public BigDecimal getBeelineBalance() throws IOException
	{
		MobileBalanceWidget mobileBalanceWidget = getWidget();
		HttpConnector httpConnector = new HttpConnector();
		String url = String.format(START_ADDRESS_BEELINE, mobileBalanceWidget.getPhoneNumber(), mobileBalanceWidget.getPassword());
		BufferedReader balanceBR = null;
		try
		{
			URLConnection urlConn = httpConnector.connect(url);

			if (!StringHelper.equals(urlConn.getURL().getPath(), "/loginPage.do"))
			{
				URLConnection balanceConn = httpConnector.connect(BALANCE_ADDRESS_BEELINE);
				balanceConn.connect();

				balanceBR = new BufferedReader(new InputStreamReader(balanceConn.getInputStream()));

				//���������� html-������ jTidy
				Tidy tidy = new Tidy();
				tidy.setXHTML(true);
				Document doc = tidy.parseDOM(balanceBR, null);

				XPathFactory factory = XPathFactory.newInstance();
				XPath xpath = factory.newXPath();
				try
				{
					Object balanceObj = xpath.evaluate("//form[@id='refreshedPrepaidBalanceForm']/table/tr/td/text()", doc, XPathConstants.STRING);
					String[] balanceArray = balanceObj.toString().split("\\p{Zs}", 2);
					BigDecimal b = new BigDecimalParser().parse(balanceArray[0]);

					return b;
				}
				catch (XPathExpressionException e)
				{
					log.error("��� ������� ��������� ���������� ������� �� ������ " + url + " ��������� ������������ ��������", e);
				}
				catch (ParseException e)
				{
					log.error("��� ������� ��������� ���������� ������� �� ������ " + url + " ��������� ������������ ��������", e);
				}
			}
		}
		catch (GeneralSecurityException e)
		{
			log.error("���� ��� ������� �������� ������ ���������� ����� �� ������ " + url, e);
		}
		finally
		{
			balanceBR.close();
		}
		return null;
	}

	/**
	 * ��������� ������� ���������� �������� (�������� - ���)
	 * @return ������, ���������� ����� �� �����
	 */
	public BigDecimal getMTSBalance() throws IOException
	{
		MobileBalanceWidget mobileBalanceWidget = getWidget();
		HttpConnector httpConnector = new HttpConnector();
		String url = String.format(START_ADDRESS_MTS, mobileBalanceWidget.getPhoneNumber(), mobileBalanceWidget.getPassword());
		BufferedReader startPageReader = null;
		try
		{
			URLConnection urlConn = httpConnector.connect(url);

			if (!StringHelper.equals(urlConn.getURL().getPath(), "/amserver/UI/Login"))
			{
				httpConnector.setAccept(null);
				URLConnection balanceConn = httpConnector.connect(BALANCE_ADDRESS_MTS);

				startPageReader = new BufferedReader(new InputStreamReader(balanceConn.getInputStream()));

				String line;
				String responseBalance = "";
				while ((line = startPageReader.readLine()) != null)
				{
					Matcher m = MTS_PATTERN.matcher(line);
					if (m.find())
					{
						responseBalance = m.group();
					}
				}
				JsonObject jsonObject = (JsonObject) new JsonParser().parse(responseBalance);
				BigDecimal balance = jsonObject.get("balance").getAsBigDecimal();
				return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			}
		}
		catch (GeneralSecurityException e)
		{
			log.error("���� ��� ������� �������� ������ ���������� ����� �� ������ " + url, e);
		}
		catch (JsonSyntaxException e)
		{
			log.error("������� ������� ������ � �������", e);
		}
		finally
		{
			startPageReader.close();
		}
		return null;
	}

	public void save() throws BusinessException
	{
		super.save();
		clearCacheIfRequired();
	}

	/**
	 * ���������� ��� (������ � ����� ��� ����������), ���� :
	 * 1. ���������� ���� �� ���� �� "��������" ����� (�������� �����, ����� ��� ������)
	 * 2. ���� ������� ����� ������������ ������������ ������
	 */
	private void clearCacheIfRequired()
	{
		MobileBalanceWidget newWidget = getWidget();
		MobileBalanceWidget oldWidget = getOldWidget();

		boolean needClearCache = false;
		if (!StringHelper.equalsNullIgnore(newWidget.getProvider(), oldWidget.getProvider()))
			needClearCache = true;
		if (!StringHelper.equalsNullIgnore(newWidget.getPhoneNumber(), oldWidget.getPhoneNumber()))
			needClearCache = true;
		if (!StringHelper.equalsNullIgnore(newWidget.getPassword(), oldWidget.getPassword()))
			needClearCache = true;
		if (oldWidget.getLastUpdateDate() != null)
		{
			if (Calendar.getInstance().getTimeInMillis() - oldWidget.getLastUpdateDate() > ACTUAL_TIME)
				needClearCache = true;
		}

		if (needClearCache)
		{
			newWidget.setBalance(null);
			newWidget.setLastUpdateDate(null);
		}
		else
		{
			newWidget.setBalance(oldWidget.getBalance());
			newWidget.setLastUpdateDate(oldWidget.getLastUpdateDate());
		}
	}

	/**
	 * ��������� ������ ��� ������ ��������� �����
	 * @return ������
	 */
	public String createPaymentUrl()
	{
		MobileBalanceWidget mobileBalanceWidget = getWidget();
		String provider = mobileBalanceWidget.getProvider();
		String recipient = "";
		if (StringHelper.equalsNullIgnore(provider, "������"))
			recipient = "4";
		if (StringHelper.equalsNullIgnore(provider, "���"))
			recipient = "9";
		if (StringHelper.equalsNullIgnore(provider, "�������"))
			recipient = "1001";
		if (StringHelper.isNotEmpty(recipient))
			return String.format(PAYMENT_URL, recipient, mobileBalanceWidget.getPhoneNumber());
		return PAYMENT_URL;
	}
}
