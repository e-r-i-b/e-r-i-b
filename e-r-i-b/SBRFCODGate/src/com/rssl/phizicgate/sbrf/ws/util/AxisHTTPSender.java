package com.rssl.phizicgate.sbrf.ws.util;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.components.net.BooleanHolder;
import org.apache.axis.encoding.Base64;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.axis.utils.Messages;

import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import java.io.*;
import java.net.*;
import java.util.*;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author Roshka
 * @ created 02.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class AxisHTTPSender extends BasicHandler
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final String ACCEPT_HEADERS = "Accept: application/soap+xml, application/dime, multipart/related, text/*\r\nUser-Agent: " + Messages.getMessage("axisUserAgent") + "\r\n";
	private static final String CHUNKED_HEADER = HTTPConstants.HEADER_TRANSFER_ENCODING + ": " + HTTPConstants.HEADER_TRANSFER_ENCODING_CHUNKED + "\r\n";
	private static final String HEADER_CONTENT_TYPE_LC = "Content-Type".toLowerCase();
	private static final String HEADER_LOCATION_LC = "Location".toLowerCase();
	private static final String HEADER_CONTENT_LOCATION_LC = "Content-Location".toLowerCase();

	private URLConnection m_urlConnection;

	public void invoke(MessageContext msgContext)
			throws AxisFault
	{
		if (log.isDebugEnabled())
			log.debug(Messages.getMessage("enter00", "AxisHTTPSender::invoke"));
		try
		{
			BooleanHolder useFullURL = new BooleanHolder(false);
			StringBuffer otherHeaders = new StringBuffer();
			URL targetURL = new URL(msgContext.getStrProp("transport.url"));
			String host = targetURL.getHost();
			int port = targetURL.getPort();

			writeToURLConnection(msgContext, targetURL, otherHeaders, host, port, useFullURL);

			Hashtable<String, String> headers = new Hashtable<String, String>();
			readHeadersURLConnection(headers);
			dumpHeaders("responseHeaders", m_urlConnection.getHeaderFields());

			handleErrors(headers);

			readFromURLConnection(msgContext, headers);
		}
		catch (Exception e)
		{
			log.debug(e);
			throw AxisFault.makeFault(e);
		}
		if (log.isDebugEnabled())
			log.debug(Messages.getMessage("exit00", "AxisHTTPSender::invoke"));
	}

	private void handleErrors(Hashtable<String, String> headers)
	{
		String error = headers.get("error");
		StringTokenizer st = new StringTokenizer(error, " ");
		st.nextToken();
		String errorCode = st.nextToken();
		if (!"200".equals(errorCode))
			throw new RuntimeException(error);
	}

	private void writeToURLConnection(MessageContext msgContext,
	                                  URL tmpURL,
	                                  StringBuffer otherHeaders,
	                                  String host,
	                                  int port,
	                                  BooleanHolder useFullURL) throws Exception
	{
		String userID = msgContext.getUsername();
		String passwd = msgContext.getPassword();
		String action = msgContext.useSOAPAction() ? msgContext.getSOAPActionURI() : "";
		if (action == null)
			action = "";
		if (userID == null && tmpURL.getUserInfo() != null)
		{
			String info = tmpURL.getUserInfo();
			int sep = info.indexOf(':');
			if (sep >= 0 && sep + 1 < info.length())
			{
				userID = info.substring(0, sep);
				passwd = info.substring(sep + 1);
			}
			else
			{
				userID = info;
			}
		}
		if (userID != null)
		{
			StringBuffer tmpBuf = new StringBuffer();
			tmpBuf.append(userID).append(":").append(passwd != null ? passwd : "");
			otherHeaders.append("Authorization").append(": Basic ").append(Base64.encode(tmpBuf.toString().getBytes())).append("\r\n");
		}
		m_urlConnection = getConnection(tmpURL.toExternalForm(), action);
		if (msgContext.getMaintainSession())
		{
			fillHeaders(msgContext, "Cookie", otherHeaders);
			fillHeaders(msgContext, "Cookie2", otherHeaders);
		}
		StringBuffer header2 = new StringBuffer();
		String webMethod = null;
		boolean posting = true;
		Message reqMessage = msgContext.getRequestMessage();
		boolean http10 = true;
		boolean httpChunkStream = false;
		String httpConnection = null;
		String httpver = msgContext.getStrProp("axis.transport.version");
		if (httpver == null)
			httpver = HTTPConstants.HEADER_PROTOCOL_V10;
		httpver = httpver.trim();
		if (httpver.equals(HTTPConstants.HEADER_PROTOCOL_V11))
			http10 = false;
		Hashtable userHeaderTable = (Hashtable) msgContext.getProperty("HTTP-Request-Headers");
		if (userHeaderTable != null)
		{
			if (otherHeaders == null)
				otherHeaders = new StringBuffer(1024);
			for (Object o : userHeaderTable.entrySet())
			{
				Map.Entry me = (Map.Entry) o;
				Object keyObj = me.getKey();
				if (keyObj != null)
				{
					String key = keyObj.toString().trim();
					if (key.equalsIgnoreCase(HTTPConstants.HEADER_TRANSFER_ENCODING))
					{
						if (!http10)
						{
							String val = me.getValue().toString();
							if (val != null && val.trim().equalsIgnoreCase(HTTPConstants.HEADER_TRANSFER_ENCODING_CHUNKED))
								httpChunkStream = true;
						}
					}
					else if (key.equalsIgnoreCase("Connection"))
					{
						if (!http10)
						{
							String val = me.getValue().toString();
							if (val.trim().equalsIgnoreCase(HTTPConstants.HEADER_CONNECTION_CLOSE))
								httpConnection = HTTPConstants.HEADER_CONNECTION_CLOSE;
						}
					}
					else
					{
						otherHeaders.append(key).append(": ").append(me.getValue()).append("\r\n");
					}
				}
			}

		}
		if (!http10)
			httpConnection = HTTPConstants.HEADER_CONNECTION_CLOSE;
		header2.append(" ");
		header2.append(http10 ? "HTTP/1.0" : "HTTP/1.1").append("\r\n");
		MimeHeaders mimeHeaders = reqMessage.getMimeHeaders();
		if (posting)
		{
			String header[] = mimeHeaders.getHeader("Content-Type");
			String contentType;
			if (header != null && header.length > 0)
				contentType = mimeHeaders.getHeader("Content-Type")[0];
			else
				contentType = reqMessage.getContentType(msgContext.getSOAPConstants());
			if (contentType == null || contentType.equals(""))
				throw new Exception(Messages.getMessage("missingContentType"));
			header2.append("Content-Type").append(": ").append(contentType).append("\r\n");
		}
		header2.append(ACCEPT_HEADERS).append("Host").append(": ").append(host).append(port != -1 ? ":" + port : "").append("\r\n").append("Cache-Control: no-cache\r\nPragma: no-cache\r\n").append("SOAPAction").append(": \"").append(action).append("\"\r\n");
		if (posting && httpChunkStream)
			header2.append(CHUNKED_HEADER);
		if (mimeHeaders != null)
		{
			for (Iterator i = mimeHeaders.getAllHeaders(); i.hasNext();)
			{
				MimeHeader mimeHeader = (MimeHeader) i.next();
				String headerName = mimeHeader.getName();
				if (!headerName.equals("Content-Type") && !headerName.equals("SOAPAction"))
					header2.append(mimeHeader.getName()).append(": ").append(mimeHeader.getValue()).append("\r\n");
			}

		}
		if (httpConnection != null)
		{
			header2.append("Connection");
			header2.append(": ");
			header2.append(httpConnection);
			header2.append("\r\n");
		}
		if (otherHeaders != null)
			header2.append(otherHeaders.toString());
		header2.append("\r\n");
		StringBuffer header = new StringBuffer();
		if (msgContext.getSOAPConstants() == SOAPConstants.SOAP12_CONSTANTS)
			webMethod = msgContext.getStrProp("soap12.webmethod");
		if (webMethod == null)
			webMethod = "POST";
		else
			posting = webMethod.equals("POST");
		header.append(webMethod).append(" ");
		if (useFullURL.value)
			header.append(tmpURL.toExternalForm());
		else
			header.append(tmpURL.getFile() != null && !tmpURL.getFile().equals("") ? tmpURL.getFile() : "/");
		header.append(header2.toString());

		m_urlConnection.connect();
		OutputStream out = m_urlConnection.getOutputStream();
		if (!posting)
		{
			out.write(header.toString().getBytes("iso-8859-1"));
			out.flush();
			return;
		}
		ByteArrayOutputStream baos = null;
		if (log.isDebugEnabled())
		{
			log.debug(Messages.getMessage("xmlSent00"));
			log.debug("---------------------------------------------------");
			baos = new ByteArrayOutputStream();
		}
		reqMessage.writeTo(out);
		out.flush();
		if (log.isDebugEnabled())
			log.debug(header + new String(baos.toByteArray()));
	}

	private void fillHeaders(MessageContext msgContext, String header, StringBuffer otherHeaders)
	{
		Object ck1 = msgContext.getProperty(header);
		if (ck1 != null)
			if (ck1 instanceof String[])
			{
				String cookies[] = (String[]) ck1;
				for (String cooky : cookies)
					addCookie(otherHeaders, header, cooky);
			}
			else
			{
				addCookie(otherHeaders, header, (String) ck1);
			}
	}

	private void addCookie(StringBuffer otherHeaders, String header, String cookie)
	{
		otherHeaders.append(header).append(": ").append(cookie).append("\r\n");
	}

	private void readFromURLConnection(MessageContext msgContext, Hashtable<String, String> headers)
			throws IOException
	{
		Message outMsg;
		try
		{
			String contentType = headers.get(HEADER_CONTENT_TYPE_LC);
			contentType = contentType != null ? contentType.trim() : null;
			String location = headers.get(HEADER_LOCATION_LC);
			//noinspection UnusedAssignment
			location = location != null ? location.trim() : null;
			String contentLocation = headers.get(HEADER_CONTENT_LOCATION_LC);
			contentLocation = contentLocation != null ? contentLocation.trim() : null;
			String contentLength = headers.get("Content-Length");
			InputStream inp = m_urlConnection.getInputStream();
			outMsg = new Message(inp, false, contentType, contentLocation);
			outMsg.setMessageType("response");
			msgContext.setResponseMessage(outMsg);
			if (log.isDebugEnabled())
			{
				if (contentLength == null)
					log.debug("\n" + Messages.getMessage("no00", "Content-Length"));
				log.debug("\n" + Messages.getMessage("xmlRecd00"));
				log.debug("-----------------------------------------------");
				log.debug(outMsg.getSOAPEnvelope().toString());
			}
		}
		catch (Exception e)
		{
			log.error("getInputStream: ", e);
			Throwable e1 = e;
			String message;
			for (message = e1.getMessage(); message == null && e1 != null; message = e1.getMessage())
				e1 = e.getCause();

			if (message.startsWith("Server returned HTTP response code: "))
			{
				String r = message.substring("Server returned HTTP response code: ".length(), "Server returned HTTP response code: ".length() + 3);
				int httpStatus = Integer.parseInt(r, 10);
				switch (httpStatus)
				{
					case 400:
						message = "Bad request";
						break;

					case 411:
						message = "Length required";
						break;

					case 405:
						message = "Method Not Allowed";
						break;

					case 401:
						message = "Unauthorised";
						break;

					case 500:
						message = "Internal Server Error";
						break;

					default:
						message = "unknown: " + httpStatus;
						break;
				}
			}
			throw new RuntimeException(message);
		}
	}

	public void handleCookie(String cookieName, String setCookieName, String cookie, MessageContext msgContext)
	{
		cookie = cleanupCookie(cookie);
		int keyIndex = cookie.indexOf("=");
		String key = keyIndex == -1 ? cookie : cookie.substring(0, keyIndex);
		ArrayList<String> cookies = new ArrayList<String>();
		Object oldCookies = msgContext.getProperty(cookieName);
		boolean alreadyExist = false;
		if (oldCookies != null)
			if (oldCookies instanceof String[])
			{
				String oldCookiesArray[] = (String[]) oldCookies;
				for (String anOldCookie : oldCookiesArray)
				{
					if (key != null && anOldCookie.indexOf(key) == 0)
					{
						anOldCookie = cookie;
						alreadyExist = true;
					}
					cookies.add(anOldCookie);
				}
			}
			else
			{
				String oldCookie = (String) oldCookies;
				if (key != null && oldCookie.indexOf(key) == 0)
				{
					oldCookie = cookie;
					alreadyExist = true;
				}
				cookies.add(oldCookie);
			}
		if (!alreadyExist)
			cookies.add(cookie);
		if (cookies.size() == 1)
			msgContext.setProperty(cookieName, cookies.get(0));
		else if (cookies.size() > 1)
			msgContext.setProperty(cookieName, cookies.toArray(new String[cookies.size()]));
	}

	private String cleanupCookie(String cookie)
	{
		cookie = cookie.trim();
		int index = cookie.indexOf(';');
		if (index != -1)
			cookie = cookie.substring(0, index);
		return cookie;
	}

	private URLConnection getConnection(String spec, String soapAction)
			throws IOException
	{
		URL url;
		try
		{
			url = new URL(spec);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new MalformedURLException(spec);
		}

		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		if (log.isDebugEnabled())
		{
			log.debug(spec);
			log.debug(urlConnection.getClass().getName());
		}

		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setDefaultUseCaches(false);
		urlConnection.setRequestProperty("Accept", "application/soap+xml, application/dime, multipart/related, text/*");
		urlConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		urlConnection.setRequestProperty("SOAPaction", "\"" + (soapAction != null ? soapAction : "") + "\"");

		if (log.isDebugEnabled())
			log.debug(urlConnection.getRequestProperties().toString());

		return urlConnection;
	}

	private void readHeadersURLConnection(Hashtable<String, String> headers)
			throws IOException
	{
		if (headers == null)
			headers = new Hashtable<String, String>();
		Map<String, List<String>> h = m_urlConnection.getHeaderFields();
		for (Object o : h.entrySet())
		{
			Map.Entry me = (Map.Entry) o;
			Object key = me.getKey();
			Object value1 = me.getValue();
			if (key == null)
				key = "error";
			if (value1 != null)
				headers.put(key.toString(), value1.toString());
		}
	}

	private void dumpHeaders(String title, Map<String, List<String>> headers)
	{
		if (log.isDebugEnabled())
		{
			StringBuffer h = new StringBuffer(title + "\n");
			for (Map.Entry<String, List<String>> entry : headers.entrySet())
			{
				String line = entry.getKey() + "=" + entry.getValue() + "\n";
				h.append(line);
			}
			log.debug(h.toString());
		}
	}
}