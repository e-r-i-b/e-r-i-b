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
import com.rssl.phizic.operations.ext.sbrf.mail.EditClientMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ListClientMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.RemoveClientMailOperation;
import com.rssl.phizic.utils.DeclensionUtils;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.mail.ListMailActionBase;
import com.rssl.phizic.web.common.client.mail.ListSentMailForm;
import com.rssl.phizic.web.component.DatePeriodFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kligina
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListSentMailAction extends ListMailActionBase
{
	private final String ID_PARAMETER_NAME = "id";
	private final String NEW_MAIL_FORWARD  = "Edit";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> keys = super.getAditionalKeyMethodMap();
		keys.put("button.add", "newMail");
		return keys;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListClientMailOperation.class, "ClientMailManagment");
	}

	protected String getQueryName(ListFormBase frm)
	{
		return "sentList";
	}
	
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListSentMailForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		super.fillQuery(query, filterParams);
		query.setParameter("senderId", AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		query.setParameter("type", filterParams.get("type"));
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveClientMailOperation.class, "ClientMailManagment");
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		frm.setField("message1", ConfigFactory.getConfig(MailConfig.class).getListMessage());
		int lastMonth = ConfigFactory.getConfig(MailConfig.class).getLastMonthDelete();
		saveMessage(currentRequest(), String.format(
				ConfigFactory.getConfig(MailConfig.class).getAttentionMessage(),
				lastMonth, DeclensionUtils.numeral(lastMonth, "мес€ц", "", "а", "ев")));
	}
	
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		Object typePeriod = frm.getFilter(DatePeriodFilter.TYPE_PERIOD);
		if (typePeriod != null)
		{
			parameters.put(DatePeriodFilter.TYPE_PERIOD, typePeriod);
		}
		return parameters;
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
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		Long id = operation.createNewMail();
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(NEW_MAIL_FORWARD));
		redirect.addParameter(ID_PARAMETER_NAME, id);
		return redirect;
	}
}
