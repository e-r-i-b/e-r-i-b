package com.rssl.phizic.business.documents.payments.form;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.forms.DocumentContext;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jatsky
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */

public class SocialPaymentFormBuilder extends PaymentFormBuilder
{
	public SocialPaymentFormBuilder(DocumentContext context, TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		super(context, transformInfo, formInfo);

		converter.setParameter("additionInfo",          StringHelper.getEmptyIfNull(formInfo.getAdditionMobileInfo()));
		converter.setParameter("changedFields",         StringHelper.getEmptyIfNull(StringUtils.join(formInfo.getChangedFields(), ",")));
	}

	protected String getDocumentStateName()
	{
		return "documentStatus";
	}
}
