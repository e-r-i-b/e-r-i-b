package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import org.hibernate.util.StringHelper;

/**
 * Фильтр шаблонов документов по счету списания
 *
 * @author khudyakov
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffResourceTemplateFilter implements TemplateDocumentFilter
{
	private String chargeOffResource;


	public ChargeOffResourceTemplateFilter(String chargeOffResource)
	{
		this.chargeOffResource = chargeOffResource;
	}

	public boolean accept(TemplateDocument template)
	{
		if (StringHelper.isEmpty(template.getChargeOffAccount()))
		{
			return false;
		}

		return template.getChargeOffAccount().equals(chargeOffResource);
	}
}
