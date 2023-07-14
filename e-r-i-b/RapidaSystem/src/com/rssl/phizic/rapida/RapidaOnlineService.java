package com.rssl.phizic.rapida;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;

import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateException;
import javax.xml.parsers.DocumentBuilder;
import javax.net.ssl.*;

/**
 * @author Krenev
 * @ created 21.11.2007
 * @ $Author$
 * @ $Revision$
 */
//TODO Log
public class RapidaOnlineService implements RapidaService
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final RapidaConfig conig = new RapidaConfig();
	
	private static final String PROTOCOL = conig.getProtocol();
	private static final String HOST = conig.getHost();
	private static final int TRY_COUNT = conig.getReconnectTryCount();
	private static final long RECONNECT_TIMEOUT = conig.getReconnectTimeout();
	private static final int TIMEOUT = conig.getTimeout();
	private static final String QUERY_PREFIX = conig.getQueryPrefix();

	//пример ручной загрузки хранилищ сертификатов и ключей.
	private void initSSLConext()
	{
		final String ksPath = "C:\\cert.pfx";//путь к контейнеру ключей
		final char[] ksPasswod = "123".toCharArray();//пароль к контейнеру ключей
		final String tsPath = "C:\\trust.store";//путь к контейнеру сертификатов
		final char[] tsPasswod = "123321".toCharArray();//пароль к контейнеру сертификатов

		FileInputStream ksInputStream = null;
		FileInputStream tsInputStream = null;
		try
		{
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ksInputStream = new FileInputStream(ksPath);
			ks.load(ksInputStream, ksPasswod);

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, ksPasswod);

			KeyStore ts = KeyStore.getInstance("JKS");
			tsInputStream = new FileInputStream(tsPath);
			ts.load(tsInputStream, tsPasswod);

			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ts);

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			SSLSocketFactory factory = sslContext.getSocketFactory();
			HttpsURLConnection.setDefaultSSLSocketFactory(factory);
		}
		catch (KeyStoreException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (KeyManagementException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (CertificateException e)
		{
			e.printStackTrace();
		}
		catch (UnrecoverableKeyException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				ksInputStream.close();
				tsInputStream.close();
			}
			catch (IOException e)
			{
				//nothing TO DO;
			}
		}
	}

	/**
	 * Отправка запросса в рапиду.
	 * @param requestStr сообщение (часть урла)
	 * @throws com.rssl.phizic.gate.exceptions.GateException ошибка транспорта или еще что-то в этом духе
	 * @return XML документ - ответ
	 */
	public Document sendPayment(String requestStr) throws GateException
	{
		log.debug("посылаем платеж в рапиду");
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		HttpURLConnection connection = null;
		GateException exception = null;
		int currentTry = 0;
		for (; ;)
		{
			if (currentTry >= TRY_COUNT)
			{
				throw exception;
			}
			currentTry++;
			log.debug("попытка " + currentTry);
			try
			{
				connection = prepereConnection(requestStr);
				connection.connect();
				int responseCode = connection.getResponseCode();
				String responseMessage = connection.getResponseMessage();
				log.debug("соединение установлено. Код ответа: " + responseCode + "(" + responseMessage + ")");
				if (responseCode != HttpURLConnection.HTTP_OK)
				{
					exception = new TemporalGateException("Получен код ответа: " + responseCode + "(" + responseMessage + ")");
					continue;
				}
				InputStream inputStream = connection.getInputStream();
				log.debug("получили поток");
				Document reponse = documentBuilder.parse(inputStream);
				log.debug("получили ответ");
				if (reponse != null)
					return reponse;
				exception = new TemporalGateException("Ответ не получен");
			}

			catch (MalformedURLException e)
			{
				log.warn("MalformedURLException:" + e.getMessage());
				exception = new TemporalGateException("Неверный формат URL", e);
			}
			catch (UnknownHostException e)
			{
				log.warn("UnknownHostException:" + e.getMessage());
				exception = new TemporalGateException("Хост не найден", e);
			}
			catch (SSLException e)
			{
				log.warn("SSLException:" + e.getMessage());
				exception = new TemporalGateException("Ошибка установки SSL соединения", e);
			}
			catch (IOException e)
			{
				log.warn("IOException:" + e.getMessage());
				exception = new TemporalGateException("Ошибка ввода/вывода", e);
			}
			catch (SAXException e)
			{
				log.warn("SAXException:" + e.getMessage());
				exception = new TemporalGateException("Ошибка в формате ответа", e);
			}
			finally
			{
				log.debug("закрываем соединение");
				closeConnection(connection);
			}
			try
			{
				final Object mutex = new Object();
				synchronized(mutex){
					mutex.wait(RECONNECT_TIMEOUT);
				}
			}
			catch (InterruptedException e)
			{
				log.debug("InterruptedException:" + e.getMessage());
			}
		}
	}

	private void closeConnection(HttpURLConnection connection)
	{
		if (connection == null)
		{
			return;
		}
		try
		{
			connection.getInputStream().close();
			connection.getOutputStream().close();
		}
		catch (IOException e)
		{
			//nothing to do
		}
		finally
		{
			connection.disconnect();
		}
	}

	/**
	 * Подготорвить соединение
	 * @param requestStr - строка запроса
	 * @return соединение.
	 */
	private HttpURLConnection prepereConnection(String requestStr) throws IOException
	{
		log.debug("подготавливаем соединение");
		URL url = new URL(PROTOCOL, HOST, QUERY_PREFIX+requestStr);
		log.debug(url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setReadTimeout(TIMEOUT);
		return connection;
	}
}
