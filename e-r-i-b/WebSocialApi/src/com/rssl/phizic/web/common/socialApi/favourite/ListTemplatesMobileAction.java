package com.rssl.phizic.web.common.socialApi.favourite;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;
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
		return createOperation(ListTemplatesOperation.class);
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "listApi";
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListTemplatesMobileForm form = (ListTemplatesMobileForm) frm;
		Query query = operation.createQuery(getQueryName(frm));
		fillQuery(query, filterParams);
		form.setTemplates(query.<Object[]>executeList());
		//поддерживаемые платежные формы
		String supportedForms = currentServletContext().getInitParameter(SUPPORTED_FORMS);
		if(StringHelper.isEmpty(supportedForms))
			throw new BusinessException("Не задан параметр " + SUPPORTED_FORMS);
		form.setSupportedForms(supportedForms);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("mAPI", true);
		query.setParameter("version_api", MobileApiUtil.getApiVersionNumber().getSolid());

		List<String> states = new ArrayList<String>();
		states.add("TEMPLATE");
		states.add("WAIT_CONFIRM_TEMPLATE");
		if (MobileApiUtil.isFullScheme())
		{
			states.add("DRAFTTEMPLATE");
			states.add("SAVED_TEMPLATE");
		}
		query.setParameterList("states", states);
		query.setParameter("template_ignore_provider_availability", ConfigFactory.getConfig(MobileApiConfig.class).isTemplateIgnoreProviderAvailability());
	}
}
