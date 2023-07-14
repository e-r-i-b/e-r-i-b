package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.query.OrderDirection;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.mail.ListMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.RemoveMailOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.struts.HttpServletRequestUtils;
import com.rssl.phizic.context.EmployeeContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
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
public class ArchiveMailAction extends ListActionBase
{
	private static final String GRID_ID = "MailArchiveList";

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

	protected void setDefaultOrderParameters(HttpServletRequest request)
	{
		HttpServletRequestUtils.addSortParameters(request, new OrderParameter("mailStateDescription"), new OrderParameter("mCreationDate", OrderDirection.DESC));
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ArchiveMailForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("employeeId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setParameter("senderId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setParameter("fromDate",filterParams.get("fromDate"));
		Date toDate = (Date)filterParams.get("toDate");
		if (toDate != null)
		{
			// прибавляем 1 день, чтобы учитывались письма, отправленные после 00:00 выбранной toDate даты.
			// в связи с этим, в hql установлено     mail.CREATION_DATE < :extra_toDate
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}

		query.setParameter("toDate", toDate);
		query.setParameter("num", filterParams.get("num"));
		query.setParameter("type", filterParams.get("type"));
		query.setParameter("subject", filterParams.get("subject"));
		query.setParameter("mailType", filterParams.get("mailType"));
		query.setParameter("clientType", filterParams.get("clientType"));
		query.setParameter("name", filterParams.get("name"));
		query.setParameter("surNameEmpl", filterParams.get("surNameEmpl"));
		query.setParameter("firstNameEmpl", filterParams.get("firstNameEmpl"));
		query.setParameter("partNameEmpl", filterParams.get("patrNameEmpl"));
		query.setParameter("login", filterParams.get("employeeLogin"));
		query.setParameter("response_method", filterParams.get("response_method"));
		query.setParameter("theme", filterParams.get("theme"));
		query.setParameter("user_TB", filterParams.get("user_TB"));
		query.setParameter("area_uuid", filterParams.get("area_uuid"));
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMailOperation.class, "MailManagment");
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
		((RemoveMailOperation) operation).rollback();
		return null;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveMailOperation.class, "MailManagment");
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ArchiveMailForm form = (ArchiveMailForm) frm;
		form.setField("gridId", GRID_ID);
		if(form.getPaginationSize() != null)
		{
			Long paginationSize = Long.parseLong(form.getPaginationSize());
			EmployeeContext.getEmployeeDataProvider().getEmployeeData().setNumberDisplayedEntry(GRID_ID, paginationSize);

		}
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		Date toDate = new Date();
		Date fromDate = new Date();
		filterParameters.put("toDate", toDate);
		Long defaultPeriod = ConfigFactory.getConfig(MailConfig.class).getDefaultPeriod();
		fromDate.setDate(toDate.getDate() - defaultPeriod.intValue());
		filterParameters.put("fromDate", fromDate);

		return filterParameters;
	}
}
