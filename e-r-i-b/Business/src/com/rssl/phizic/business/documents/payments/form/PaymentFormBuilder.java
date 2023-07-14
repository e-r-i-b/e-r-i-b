package com.rssl.phizic.business.documents.payments.form;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.forms.DocumentContext;
import com.rssl.phizic.business.documents.forms.DocumentFormBuilderBase;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import org.apache.commons.lang.StringUtils;

/**
 *
 *
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public class PaymentFormBuilder extends DocumentFormBuilderBase
{
	public PaymentFormBuilder(DocumentContext context, TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		super(context.getMetadata(), transformInfo, formInfo);

		BusinessDocument document = context.getDocument();
		converter.setParameter(getDocumentStateName(),      document.getState().getCode());
		converter.setParameter("longOffer",                 document.isLongOffer());
		converter.setParameter("documentId",                document.getId() == null ? StringUtils.EMPTY : document.getId());
		converter.setParameter("postConfirmCommission",     DocumentHelper.postConfirmCommissionSupport(document));

		if (context.getOperationData() != null)
		{
			converter.setParameter("checkAvailable",            context.getOperationData().isPrintCheck());
		}

		converter.setParameter("isITunes", DocumentHelper.isITunesProvider(document));
	}

	protected String getDocumentStateName()
	{
		return "documentState";
	}
}
