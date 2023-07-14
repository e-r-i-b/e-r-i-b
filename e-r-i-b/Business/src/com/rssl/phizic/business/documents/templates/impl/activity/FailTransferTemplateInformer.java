package com.rssl.phizic.business.documents.templates.impl.activity;

import com.rssl.phizic.business.documents.templates.ActivityInfo;

/**
 * Получение информации по автивности шаблона (все запрещено)
 *
 * @author khudyakov
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */
public class FailTransferTemplateInformer extends TemplateTransferInformerBase
{
	@Override
	public ActivityInfo inform()
	{
		return new ActivityInfo(false, false, false, false);
	}
}
