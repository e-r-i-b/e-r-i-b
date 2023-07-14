package com.rssl.phizic.business.documents.templates.forms;

import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author khudyakov
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileTemplateFormBuilder extends TemplateFormBuilder
{
	public MobileTemplateFormBuilder(TemplateDocument template, Metadata metadata, TransformInfo transformInfo, FormInfo formInfo)
	{
		super(template, metadata, transformInfo, formInfo);

		converter.setParameter("additionInfo",          StringHelper.getEmptyIfNull(formInfo.getAdditionMobileInfo()));
		converter.setParameter("mobileApiVersion",      MobileApiUtil.getApiVersionNumber().toString());
	}

	protected String getDocumentStateName()
	{
		return "documentStatus";
	}
}
