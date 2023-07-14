package com.rssl.phizic.business.xml;

import com.rssl.phizic.business.payments.ListsURIResolver;
import com.rssl.phizic.business.payments.forms.CompositeURIResolver;

import javax.xml.transform.*;

/**
 * @author Evgrafov
 * @ created 20.02.2006
 * @ $Author: erkin $
 * @ $Revision: 69392 $
 */

public class CommonXmlConverter extends AbstractXmlConverter
{
	/**
	 * @param templates XSLT øàáëîí
	 */
	public CommonXmlConverter(Templates templates)
	{
		super(templates);
	}

	/**
	 * İÒÎÒ ÊÎÍÑÒĞÓÊÒÎĞ ÌÅÅÅÅÄËÅÍÍÛÉ ÑÄÅËÀÍ ÒÎËÜÊÎ ÄËß ÑÎÂÌÅÑÒÈÌÎÑÒÈ!!!
	 * Èñïîëüçóéòå êîíñòğóêòîğ ñ Templates
	 * @param source xslt source
	 */
	@Deprecated
	public CommonXmlConverter(Source source)
	{
		super(createTemplates(source));
	}

	protected URIResolver getDictionaryURIResolver()
	{
		return new CompositeURIResolver(super.getDictionaryURIResolver(), new ListsURIResolver());
	}
}