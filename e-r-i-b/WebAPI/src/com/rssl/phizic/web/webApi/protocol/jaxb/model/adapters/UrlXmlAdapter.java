package com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.NodeUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * ������� ��� ������
 * @author Jatsky
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */

public class UrlXmlAdapter extends XmlAdapter<String, String>
{
	protected static final String PROTOCOL_DELIMITER = "://";

	public String unmarshal(String fieldValue) throws Exception
	{
		return fieldValue;
	}

	public String marshal(String fieldValue) throws Exception
	{
		URL url = toUrl(fieldValue);

		/*
		 * ���� url == null - ��� ������, ��� ��������� �������� ��������.
		 * - localhost:8888/PhizIC/...;
		 * - PhizIC/...
		 */
		if (url == null)
		{
			/*
			 * ��� url ��� ���������, �� ����������� ���� (localhost:8888/PhizIC/...) �������� ��������� �������
			 */
			URI uri = new URI(fieldValue);
			if (StringHelper.isNotEmpty(uri.getScheme()))
			{
				url = uri.toURL();
			}

            /*
             * ���� ������� ��� ������� ����� �� ������ �� ����, �� ��������.
             * ������ ������������� ����������� ����� � ������������ ���������, ��������� �����������
             * ������� ��������� ���� �� ����������� ���� ������ ��� (������� ����� ������ ����� ������ �� �����):
             *
             * - PhizIC/private/account.do
             * - localhost/PhizIC/private/account.do
             * - ok.ru
             * - vk.com/PhizIC/private/account.do
             */
			if (url == null)
			{
				return wrapCDATA(ConfigFactory.getConfig(WebAPIConfig.class).getWebClientProtocol() + PROTOCOL_DELIMITER + NodeUtil.getCurrentNode().getHostname() + fieldValue);
			}
		}

		/*
		 * �������� �������������, ��� ������ ������ �� �����.
		 *
		 * ������ ��������� �������� ��� � ��� ����, ����� �� ���� �������� � ������� ����� ��������
		 * ������ ������ �� ������� ������, ������ �� ����������, ��� ��������, ����� ������������ �������� ��������,
		 * �.�. ������ https://youtube.com/sberbank �������� https://127.0.0.1:8080/sberbank, ���, ������ http://ok.ru
		 * �������� https://ok.ru.
		 */
		return wrapCDATA(url.toString());
	}

	protected final String wrapCDATA(final String value)
	{
		return String.format("<![CDATA[%s]]>", value);
	}

	protected final URL toUrl(final String value)
	{
		try
		{
			return new URL(value);
		}
		catch (MalformedURLException ignored)
		{
			return null;
		}
	}
}