package com.rssl.phizic.business.loans;

import com.rssl.phizic.business.payments.forms.CompositeURIResolver;
import com.rssl.phizic.business.xml.AbstractXmlConverter;
import com.rssl.phizic.utils.xml.StaticResolver;

import javax.xml.transform.Templates;
import javax.xml.transform.URIResolver;

/**
 * @author gladishev
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanHtmlConverter  extends AbstractXmlConverter
{
	public LoanHtmlConverter(Templates templates)
	{
		super(templates);
	}

	protected URIResolver getDictionaryURIResolver()
	{
		return new CompositeURIResolver(
				super.getDictionaryURIResolver(),
				new StaticResolver()
		);
	}
}
