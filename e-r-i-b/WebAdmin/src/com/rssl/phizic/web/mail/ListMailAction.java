package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.mail.RecipientMailState;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.OrderDirection;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.RemoveMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ViewMailOperation;
import com.rssl.phizic.operations.mail.ListMailOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.struts.HttpServletRequestUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kligina
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListMailAction extends SaveFilterParameterAction
{
	private static final String ID_PARAMETER_NAME  = "id";
	private static final String REPLY_MAIL_FORWARD = "Reply";
	private static final String GRID_ID = "MailList";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> keys = super.getAditionalKeyMethodMap();
		keys.put("button.add",   "newMail");
		return keys;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMailOperation.class, "MailManagment");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListMailForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		Long num = null;
		if( !( filterParams.get("num") == null || filterParams.get("num").equals("") ) )
			num = Long.parseLong((String) filterParams.get("num"));
		
		Date toDate = (Date)filterParams.get("toDate");
		if (toDate != null)
		{
			// прибавляем 1 день, чтобы учитывались письма, отправленные после 00:00 выбранной toDate даты.
			// в связи с этим, в hql установлено     mail.CREATION_DATE < :extra_toDate
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}

		/* привязываем письма к офису клиента, а не к получателям (сотрудникам офиса).	*/

		query.setParameter("employeeId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setParameter("fromDate",filterParams.get("fromDate"));
		query.setParameter("toDate", toDate);
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("fioEmpl", filterParams.get("fioEmpl"));
		query.setParameter("employeeLogin", filterParams.get("employeeLogin"));
		query.setParameter("num", num);
		query.setParameter("type", filterParams.get("type"));
		query.setParameter("subject", filterParams.get("subject"));		
		query.setParameter("isAttach", filterParams.get("isAttach"));
		query.setParameterList("state", getState(filterParams).toArray());
		query.setParameter("theme", filterParams.get("theme"));
		query.setParameter("response_method", filterParams.get("response_method"));
		query.setParameter("user_TB", filterParams.get("user_TB"));
		query.setParameter("area_uuid", filterParams.get("area_uuid"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	private List<String> getState( Map<String, Object> filterParams)
	{
		List<String> param = new ArrayList<String>();
		param.add("");
		if(getFilterValue(filterParams,"showNew"))
		{
		    param.add(RecipientMailState.NEW.name());
		}
		if(getFilterValue(filterParams,"showAnswer"))
		{
			param.add(RecipientMailState.ANSWER.name());
		}
		if(getFilterValue(filterParams,"showReceived"))
		{
			param.add(RecipientMailState.READ.name());
		}
		if(getFilterValue(filterParams,"showDraft"))
		{
			param.add(RecipientMailState.DRAFT.name());
		}
		
		return param;
	}

	private Boolean getFilterValue(Map<String, Object> filterParams, String parameter)
	{
		Object parameterValue = filterParams.get(parameter);
		if(parameterValue == null)
			return false;
		return Boolean.valueOf(parameterValue.toString());
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("showNew", true);
		filterParameters.put("showAnswer", false);
		filterParameters.put("showReceived", true);
		filterParameters.put("showDraft", true);
		Date toDate = new Date();
		Date fromDate = new Date();
		filterParameters.put("toDate", toDate);
		Long defaultPeriod = ConfigFactory.getConfig(MailConfig.class).getDefaultPeriod();
		fromDate.setDate(toDate.getDate() - defaultPeriod.intValue());
		filterParameters.put("fromDate", fromDate);

		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		String patrName = employee.getPatrName() == null ? "" : employee.getPatrName();
		filterParameters.put("fioEmpl", employee.getSurName()+" "+employee.getFirstName()+" "+patrName);

		return filterParameters;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveMailOperation.class, "MailManagment");
	}

	protected ViewMailOperation createViewOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ViewMailOperation.class, "MailManagment");
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException e)
		{

			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}

	protected void setDefaultOrderParameters(HttpServletRequest request)
	{
		HttpServletRequestUtils.addSortParameters(request, new OrderParameter("mailStateDescription"), new OrderParameter("mCreationDate", OrderDirection.DESC));
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListMailForm form = (ListMailForm) frm;
		form.setField("gridId", GRID_ID);		
		if(form.getPaginationSize() != null)
		{
			Long paginationSize = Long.parseLong(form.getPaginationSize());
			EmployeeContext.getEmployeeDataProvider().getEmployeeData().setNumberDisplayedEntry(GRID_ID, paginationSize);

		}
	}

	/**
	 * создание нового письма
	 * @param mapping  маппинг
	 * @param form     форма
	 * @param request  реквест
	 * @param response респунс
	 * @return ActionForward
	 * @throws BusinessException
	 */
	@SuppressWarnings("UnusedDeclaration")
	public final ActionForward newMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		EditMailOperation operation = createOperation(EditMailOperation.class);
		Long id = operation.createNewMail();
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(REPLY_MAIL_FORWARD));
		redirect.addParameter(ID_PARAMETER_NAME, id);
		return redirect;
	}
}
