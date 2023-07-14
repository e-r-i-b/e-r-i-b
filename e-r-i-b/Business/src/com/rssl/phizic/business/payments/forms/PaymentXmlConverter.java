package com.rssl.phizic.business.payments.forms;

import com.rssl.phizic.business.xml.AbstractXmlConverter;
import com.rssl.phizic.business.payments.ListsURIResolver;
import com.rssl.phizic.business.documents.metadata.Metadata;

import javax.xml.transform.URIResolver;
import javax.xml.transform.Templates;

/**
 * @author Evgrafov
 * @ created 20.02.2006
 * @ $Author: kosyakov $
 * @ $Revision: 1314 $
 */
// todo: Переименовать и упереть! Класс используется в реализациях интерфейса Metadata 
public class PaymentXmlConverter extends AbstractXmlConverter
{
	private Metadata metadata;

	/**
	 * @param metadata метаданные для получениея списка
	 * @param templates XSLT шаблон
	 */
	public PaymentXmlConverter(Metadata metadata, Templates templates)
	{
		super(templates);
		this.metadata = metadata;
	}

	protected URIResolver getDictionaryURIResolver()
	{
		return new CompositeURIResolver(
				super.getDictionaryURIResolver(),
				new ListsURIResolver(),
				new InlineListResolver(metadata)
		);
	}
}