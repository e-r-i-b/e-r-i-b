package com.rssl.phizic.web.atm.favourite;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.comparators.TemplateNameComparator;
import com.rssl.phizic.business.documents.templates.service.filters.*;
import com.rssl.phizic.business.documents.templates.service.filters.API.APIFormTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.API.APITemplateStatusTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.API.APITemplateTypeTemplateFilter;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Список шаблонов
 * @author Dorzhinov
 * @ created 07.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTemplatesATMAction extends ListActionBase
{
	private static final String SUPPORTED_FORMS = "supportedForms";


	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ListTemplatesATMForm frm = (ListTemplatesATMForm) form;

		ListTemplatesOperation operation = createOperation(ListTemplatesOperation.class);
		operation.initialize(getFilters(frm));

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListTemplatesATMForm form = (ListTemplatesATMForm) frm;
		ListTemplatesOperation op = (ListTemplatesOperation) operation;

		form.setTemplates(op.getEntity());
		form.setSupportedForms(getSupportedForms());
	}

	private TemplateDocumentFilter[] getFilters(ListTemplatesATMForm frm) throws BusinessException
	{
		return new TemplateDocumentFilter[]{
				new ChannelActivityTemplateFilter(CreationType.atm),
				new ActiveTemplateFilter(),
				new StateTemplateFilter(StateCode.TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE),
				new APITemplateTypeTemplateFilter(frm.getType()),
				new APIFormTemplateFilter(frm.getForm()),
				new APITemplateStatusTemplateFilter(frm.getStatus(), getSupportedForms().split(","))
			};
	}

	private String getSupportedForms() throws BusinessException
	{
		String supportedForms = currentServletContext().getInitParameter(SUPPORTED_FORMS);
		if (StringHelper.isEmpty(supportedForms))
		{
			throw new BusinessException("Не задан параметр " + SUPPORTED_FORMS);
		}

		return supportedForms;
	}
}
