package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.OrderDirection;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.RemoveMailOperation;
import com.rssl.phizic.operations.mail.ListMailOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.struts.HttpServletRequestUtils;
import com.rssl.phizic.web.mail.ListSentMailForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kligina
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListSentMailAction extends com.rssl.phizic.web.mail.ListSentMailAction
{
	private final String ID_PARAMETER_NAME = "id";
	private final String NEW_MAIL_FORWARD  = "Edit";
	private static final String GRID_ID = "MailSentList";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> keys = super.getAditionalKeyMethodMap();
		keys.put("button.add", "newMail");
		return keys;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMailOperation.class, "MailManagment");
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setParameter("fromDate", filterParams.get("fromDate"));

		Date toDate = null;		
		if(filterParams.get("toDate")!=null && !"".equals(filterParams.get("toDate")))
		{
			toDate = (Date)filterParams.get("toDate");
			// прибавляем 1 день, чтобы учитывались письма, отправленные после 00:00 выбранной toDate даты.
			// в связи с этим, в hql установлено     mail.CREATION_DATE < :extra_toDate
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}

		Long num = null;
		if( !( filterParams.get("num") == null || filterParams.get("num").equals("") ) )
			num = Long.parseLong((String) filterParams.get("num"));

		query.setParameter("toDate", toDate);
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("groupName", filterParams.get("groupName"));
		query.setParameter("recipientType", filterParams.get("recipientType"));
		query.setParameter("subject", filterParams.get("subject"));
		query.setParameter("isAttach", filterParams.get("isAttach"));
		query.setParameter("senderId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setParameter("fioEmpl", filterParams.get("fioEmpl"));
		query.setParameter("login", filterParams.get("login"));
		query.setParameter("type", filterParams.get("type") );
		query.setParameter("theme", filterParams.get("theme"));
		query.setParameter("response_method", filterParams.get("response_method"));
		query.setMaxResults(webPageConfig().getListLimit()+1);
		query.setParameter("num", num);
		query.setParameter("user_TB", filterParams.get("user_TB"));
		query.setParameter("area_uuid", filterParams.get("area_uuid"));
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveMailOperation.class, "MailManagment");
	}

	protected void setDefaultOrderParameters(HttpServletRequest request)
	{
		HttpServletRequestUtils.addSortParameters(request, new OrderParameter("mCreationDate", OrderDirection.DESC));
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListSentMailForm form = (ListSentMailForm) frm;
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
	public final ActionForward newMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		EditMailOperation operation = createOperation(EditMailOperation.class);
		Long id = operation.createNewMail();
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(NEW_MAIL_FORWARD));
		redirect.addParameter(ID_PARAMETER_NAME, id);
		return redirect;
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
