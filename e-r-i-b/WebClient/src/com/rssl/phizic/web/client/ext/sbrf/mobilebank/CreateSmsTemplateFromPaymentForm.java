package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.business.ext.sbrf.mobilebank.PaymentTemplateShortcut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Erkin
 * @ created 21.06.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class CreateSmsTemplateFromPaymentForm extends MobileBankFormBase
{
	private List<PaymentTemplateShortcut> templates;

	private Long selectedTemplateId;

	///////////////////////////////////////////////////////////////////////////

	public List<PaymentTemplateShortcut> getTemplates()
	{
		if (templates == null)
			return null;
		else return Collections.unmodifiableList(templates);
	}

	public void setTemplates(List<PaymentTemplateShortcut> templates)
	{
		if (templates == null)
			this.templates = null;
		else this.templates = new LinkedList<PaymentTemplateShortcut>(templates);
	}

	public Long getSelectedTemplateId()
	{
		return selectedTemplateId;
	}

	public void setSelectedTemplateId(Long selectedTemplateId)
	{
		this.selectedTemplateId = selectedTemplateId;
	}
}
