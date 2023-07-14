package com.rssl.phizic.business.documents.payments.form;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.forms.DocumentContext;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public class MobilePaymentFormBuilder extends PaymentFormBuilder
{
	public MobilePaymentFormBuilder(DocumentContext context, TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		super(context, transformInfo, formInfo);

		converter.setParameter("additionInfo",          StringHelper.getEmptyIfNull(formInfo.getAdditionMobileInfo()));
		converter.setParameter("mobileApiVersion",      MobileApiUtil.getApiVersionNumber().toString());
		converter.setParameter("changedFields",         StringHelper.getEmptyIfNull(StringUtils.join(formInfo.getChangedFields(), ",")));
        converter.setParameter("autoTransferAvailable",   AutoPaymentHelper.isAutoTransferAllowed(context.getDocument()));
	}

	protected String getDocumentStateName()
	{
		return "documentStatus";
	}
}
