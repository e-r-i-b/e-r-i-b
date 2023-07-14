package com.rssl.phizic.web.common.client.ext.sbrf.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.*;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ListClientMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.RemoveClientMailOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.mail.ListMailActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kligina
 * @ created 20.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveMailAction extends ListMailActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.rollback", "rollback");
		return map;
	}
	
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "removedList";
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ArchiveMailForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		super.fillQuery(query, filterParams);
		query.setParameter("recipientId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setParameter("senderId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setParameter("mailType", filterParams.get("mailType"));
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListClientMailOperation.class, "ClientMailManagment");
	}

	public ActionForward rollback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		List<Long> ids = StrutsUtils.parseIds(frm.getSelectedIds());

		RemoveEntityOperation operation = createRemoveOperation(frm);
		ActionMessages errors = new ActionMessages();
		for (Long id : ids)
		{
			errors.add(doRollback(operation, id));
			//Фиксируем удаляемые сущности.
			addLogParameters(new BeanLogParemetersReader("Данные удаленной сущности", operation.getEntity()));
		}
		saveErrors(request, errors);
		stopLogParameters();
		return filter(mapping, form, request, response);
	}

	protected ActionMessages doRollback(RemoveEntityOperation operation, Long id) throws Exception
	{
		operation.initialize(id);
		((RemoveClientMailOperation) operation).rollback();
		return null;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveClientMailOperation.class, "ClientMailManagment");
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		frm.setField("message1", ConfigFactory.getConfig(MailConfig.class).getListMessage());
	}

}
