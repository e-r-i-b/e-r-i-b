package com.rssl.phizic.web.client.favourite;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.ActiveTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ChannelActivityTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.NotReminderTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.StateTemplateFilter;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Список активных шаблонов, пригодных для создания напоминаний
 */
public class ListTemplatesForRemindersAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.filter", "start");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListTemplatesForRemindersForm frm = (ListTemplatesForRemindersForm) form;
		frm.setTemplates(getTemplates());

		return mapping.findForward(FORWARD_START);
	}

	private List<TemplateDocument> getTemplates() throws BusinessLogicException, BusinessException
	{
		if (!checkAccess(ListTemplatesOperation.class))
		{
			return Collections.emptyList();
		}

		ListTemplatesOperation operation = createOperation(ListTemplatesOperation.class);
		operation.initialize(
				new NotReminderTemplateFilter(),
				new ActiveTemplateFilter(),
				new StateTemplateFilter(StateCode.TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE),
				new ChannelActivityTemplateFilter(CreationType.internet)
		);

		return operation.getEntity();
	}
}
