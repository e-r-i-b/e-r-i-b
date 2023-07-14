package com.rssl.phizic.business.documents.payments.form;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.forms.DocumentContext;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public class AtmPaymentFormBuilder extends PaymentFormBuilder
{
	public AtmPaymentFormBuilder(DocumentContext context, TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		super(context, transformInfo, formInfo);

		BusinessDocument document = context.getDocument();
		converter.setParameter("changedFields",             StringHelper.getEmptyIfNull(StringUtils.join(formInfo.getChangedFields(), ",")));
		converter.setParameter("quickLongOfferAvailable",   AutoPaymentHelper.isAutoPaymentAllowed(document) || DocumentHelper.isMoneyBoxSupported(document));
		converter.setParameter("templateAvailable",         DocumentHelper.isTemplateATMSupported(document) );
	}

	protected String getDocumentStateName()
	{
		return "documentStatus";
	}
}
