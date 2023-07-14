package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.PaymentFormImport;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 * URI resolver для тэгов import и include.
 * Разрешает тэги вида &lt;xsl:import href="{name}.template.xslt"&gt;. Другие href разрешатся в null.
 * Импортируемый ресурс ищет в БД. В случае отсутствия ресурса или при возникновении ошибки: не падает, возвращает null.
 *
 * @author rusak
 * @ created 19.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class IncludesResolver implements URIResolver
{
	private static final PaymentFormService FORM_SERVICE = new PaymentFormService();
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public Source resolve(String href, String base) throws TransformerException
	{
		Source result = null;
		if (href.endsWith(".template.xslt"))
		{
			try
			{
				PaymentFormImport formImport = FORM_SERVICE.findImportByName(href);
				InputStream stream = new ByteArrayInputStream(formImport.getBody().getBytes("Cp1251"));
				result = new StreamSource(stream);
			}
			catch (Exception e)
			{
				LOG.error("Ошибка при разрешении импортируемого ресурса " + href, e);
			}
		}
		return result;
	}
}
