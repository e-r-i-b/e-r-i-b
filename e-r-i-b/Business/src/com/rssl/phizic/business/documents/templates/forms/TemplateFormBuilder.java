package com.rssl.phizic.business.documents.templates.forms;

import com.rssl.phizic.business.documents.forms.DocumentFormBuilderBase;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import org.apache.commons.lang.StringUtils;

/**
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateFormBuilder extends DocumentFormBuilderBase
{
	public TemplateFormBuilder(TemplateDocument template, Metadata metadata, TransformInfo transformInfo, FormInfo formInfo)
	{
		super(metadata, transformInfo, formInfo);

		converter.setParameter("isTemplate",            "true");
		converter.setParameter("documentId",            template.getId() == null ? StringUtils.EMPTY : template.getId());
		converter.setParameter(getDocumentStateName(),  template.getState().getCode());
		converter.setParameter("longOffer",             false);
	}

	protected String getDocumentStateName()
	{
		return "documentState";
	}
}
