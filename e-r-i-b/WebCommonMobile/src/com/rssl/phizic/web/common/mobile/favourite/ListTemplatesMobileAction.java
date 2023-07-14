package com.rssl.phizic.web.common.mobile.favourite;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.comparators.TemplateNameComparator;
import com.rssl.phizic.business.documents.templates.service.filters.ActiveTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ChannelActivityTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.StateTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.TemplateDocumentFilter;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * Список шаблонов
 * @author Dorzhinov
 * @ created 07.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTemplatesMobileAction extends ListActionBase
{
	private static final String SUPPORTED_FORMS = "supportedForms";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListTemplatesOperation operation = createOperation(ListTemplatesOperation.class);
		operation.initialize(getFilters());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ListTemplatesMobileForm frm = (ListTemplatesMobileForm) form;
		ListTemplatesOperation op = (ListTemplatesOperation) operation;

		frm.setTemplates(op.getEntity());
		frm.setSupportedForms(getSupportedForms());
	}

	private String getSupportedForms() throws BusinessException
	{
		//поддерживаемые платежные формы
		String supportedForms = currentServletContext().getInitParameter(SUPPORTED_FORMS);
		if (StringHelper.isEmpty(supportedForms))
		{
			throw new BusinessException("Не задан параметр " + SUPPORTED_FORMS);
		}

		return supportedForms;
	}

	private TemplateDocumentFilter[] getFilters()
	{
		return new TemplateDocumentFilter[]{
				new ChannelActivityTemplateFilter(CreationType.mobile),
				new ActiveTemplateFilter(),
				MobileApiUtil.isFullScheme() ?
					new StateTemplateFilter(StateCode.TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE, StateCode.SAVED_TEMPLATE, StateCode.DRAFTTEMPLATE) :
					new StateTemplateFilter(StateCode.TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE)
			};
	}
}
