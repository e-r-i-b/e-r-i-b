package com.rssl.phizic.web.common.client.ext.sbrf.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditClientMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ListClientMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.RemoveClientMailOperation;
import com.rssl.phizic.utils.DeclensionUtils;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.mail.ListMailActionBase;
import com.rssl.phizic.web.common.client.mail.ListMailForm;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kligina
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListMailAction extends ListMailActionBase
{
	private final String ID_PARAMETER_NAME  = "id";
	private final String REPLY_MAIL_FORWARD = "Reply";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> keys = super.getAditionalKeyMethodMap();
		keys.put("button.reply", "reply");
		keys.put("button.view",  "view");
		return keys;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListClientMailOperation.class, "ClientMailManagment");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListMailForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		super.fillQuery(query, filterParams);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		query.setParameter("recipientId", personData.getPerson().getLogin().getId());
		query.setParameter("state", filterParams.get("state"));
	}
	
	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveClientMailOperation.class, "ClientMailManagment");
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

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		frm.setField("message1", ConfigFactory.getConfig(MailConfig.class).getListMessage());
		int lastMonth = ConfigFactory.getConfig(MailConfig.class).getLastMonthDelete();
		saveMessage(currentRequest(), String.format(
				ConfigFactory.getConfig(MailConfig.class).getAttentionMessage(),
				lastMonth, DeclensionUtils.numeral(lastMonth, "�����", "", "�", "��")));
	}

	/**
	 * �������� ������ (���� ���� ����������� �������� ������, �� ���������� ���)
	 * @param mapping  �������
	 * @param form     �����
	 * @param request  �������
	 * @param response �������
	 * @return ActionForward
	 * @throws BusinessException
	 */
	public final ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		ListFormBase frm = (ListFormBase) form;
		String[] ids =  frm.getSelectedIds();
		if (ids.length != 1)
			throw new BusinessException("������� ��������� ����� ��� ���������.");

		Long parentId = new Long(ids[0]);
		Long id = operation.createView(parentId);
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(REPLY_MAIL_FORWARD));
		redirect.addParameter(ID_PARAMETER_NAME, id);
		return redirect;
	}

	/**
	 * ����� �� ������
	 * @param mapping  �������
	 * @param form     �����
	 * @param request  �������
	 * @param response �������
	 * @return ActionForward
	 * @throws BusinessException
	 */
	public final ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		ListFormBase frm = (ListFormBase) form;
		String[] ids =  frm.getSelectedIds();
		if (ids.length != 1)
			throw new BusinessException("������� ��������� ����� ��� ������.");

		Long id = operation.createReply(new Long(ids[0]));
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(REPLY_MAIL_FORWARD));
		redirect.addParameter(ID_PARAMETER_NAME, id);
		return redirect;
	}
}
