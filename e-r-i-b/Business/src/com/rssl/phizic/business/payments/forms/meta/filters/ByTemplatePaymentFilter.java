package com.rssl.phizic.business.payments.forms.meta.filters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * Фильтр выполнения хендлера, если документ создается по шаблону документа.
 *
 * @author khudyakov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ByTemplatePaymentFilter extends HandlerFilterBase<BusinessDocument>
{
	public boolean isEnabled(BusinessDocument document) throws DocumentException
	{
		return document.isByTemplate();
	}
}
