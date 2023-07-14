package com.rssl.phizic.web.common.client.payments.forms;

/**
 * Ёкшн быстрого асинхронного создани€ шаблона
 * @author niculichev
 * @ created 06.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsyncQuicklyCreateTemplateAction extends QuicklyCreateTemplateAction
{
	protected boolean isAjax()
	{
		return true;
	}
}
