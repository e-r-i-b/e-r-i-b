package com.rssl.phizic.business.documents.templates.impl.activity;

import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.ActiveTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ReadyToPaymentTemplateFilter;

/**
 * Получение информации по автивности шаблона
 *
 * @author khudyakov
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */
public class DefaultTransferTemplateInformer extends TemplateTransferInformerBase
{
	private boolean active;
	private boolean readyToPay;

	public DefaultTransferTemplateInformer(TemplateDocument template)
	{
		active      = new ActiveTemplateFilter().accept(template);
		readyToPay  = new ReadyToPaymentTemplateFilter().accept(template);
	}

	public ActivityInfo inform()
	{
		return new ActivityInfo(active, active, readyToPay, false);
	}
}
