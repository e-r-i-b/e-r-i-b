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
 * јдаптер дл€ ссылок
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
		 * ≈сли url == null - это значит, что пришедшее значение неполное.
		 * - localhost:8888/PhizIC/...;
		 * - PhizIC/...
		 */
		if (url == null)
		{
			/*
			 * ƒл€ url без протокола, но содержащего порт (localhost:8888/PhizIC/...) работает следующий вариант
			 */
			URI uri = new URI(fieldValue);
			if (StringHelper.isNotEmpty(uri.getScheme()))
			{
				url = uri.toURL();
			}

            /*
             * Ётот вариант дл€ случаев когда не указан ни порт, ни протокол.
             * ѕросто приписываютс€ недостающие части и возвращаетс€ результат, поскольку нормального
             * способа вычленить хост из приведенных ниже ссылок нет (вариант брать первое слово вообще не катит):
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
		 * ƒелаетс€ предположение, что мен€ть ничего не нужно.
		 *
		 * ƒанное допущение делаетс€ ещЄ и дл€ того, чтобы не было ситуаций в которых можно получить
		 * вместо ссылки на внешний ресурс, ссылку на внутренний, или ситуаций, когда выставл€етс€ неверный протокол,
		 * т.е. вместо https://youtube.com/sberbank получить https://127.0.0.1:8080/sberbank, или, вместо http://ok.ru
		 * получить https://ok.ru.
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