package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.payments.forms.CompositeURIResolver;
import com.rssl.phizic.business.xml.AbstractXmlConverter;
import com.rssl.phizic.business.payments.ListsURIResolver;
import com.rssl.phizic.utils.xml.StaticResolver;

import javax.xml.transform.URIResolver;
import javax.xml.transform.Templates;

/**
 * @author Kosyakov
 * @ created 25.12.2006
 * @ $Author: erkin $
 * @ $Revision: 69392 $
 */
public class DepositHtmlConverter extends AbstractXmlConverter
{
	public DepositHtmlConverter(Templates templates)
	{
		super(templates);
	}

	protected URIResolver getDictionaryURIResolver()
	{
		return new CompositeURIResolver(
				super.getDictionaryURIResolver(),
				new ListsURIResolver(),
				new StaticResolver()
		);
	}
}
