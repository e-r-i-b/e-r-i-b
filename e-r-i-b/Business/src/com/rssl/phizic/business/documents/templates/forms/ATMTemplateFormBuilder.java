package com.rssl.phizic.business.documents.templates.forms;

import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;

/**
 * @author khudyakov
 * @ created 21.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ATMTemplateFormBuilder extends TemplateFormBuilder
{
	public ATMTemplateFormBuilder(TemplateDocument template, Metadata metadata, TransformInfo transformInfo, FormInfo formInfo)
	{
		super(template, metadata, transformInfo, formInfo);

		converter.setParameter("checkAvailable",            false);
		converter.setParameter("templateAvailable",         false);
		converter.setParameter("longOffer",                 false);
		converter.setParameter("quickLongOfferAvailable",   AutoPaymentHelper.isAutoPaymentAllowedForTemplate(template));
		converter.setParameter("postConfirmCommission",     false);
	}

	protected String getDocumentStateName()
	{
		return "documentStatus";
	}
}
