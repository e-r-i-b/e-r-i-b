package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailDirection;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.mail.MailState;
import com.rssl.phizic.business.mail.reassign.history.ReassignMailReason;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ext.sbrf.mail.DownloadEmployeeMailAttachOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditMailOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ReassignEmployeeOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ViewMailOperation;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 01.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class ViewMailAction extends UnloadOperationalActionBase
{
	private static final String FORWARD_VIEW_INCOMING = "ViewIncoming";
	private static final String FORWARD_VIEW_SENT = "ViewSent";
	private static final String FORWARD_VIEW_DRAFT = "ViewDraft";
	private static final String FORWARD_REPLY = "Reply";
	private static final String FORWARD_REASSIGN_SUCCESS = "Success";


	private static final String ID_PARAMETER_NAME = "id";

	protected Map<String,String> getAditionalKeyMethodMap()
    {
	    Map<String,String> map = new HashMap<String, String>();
	    map.put("button.reply",  "reply");
	    map.put("button.edit.draft", "editDraft");
	    map.put("button.save", "reassign");
        return map;
    }

	private ViewMailOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		ViewMailOperation operation = null;
		ViewMailForm form = (ViewMailForm) frm;
		if (checkAccess(ViewMailOperation.class, "MailManagment"))
		{
			operation = createOperation(ViewMailOperation.class, "MailManagment");
		}
		else
		{
			operation = createOperation(ViewMailOperation.class, "MailManagementUseClientForm");
		}
		operation.initialize(frm.getId(), BooleanUtils.toBoolean(form.getFromQuestionary()));

		return operation;
	}

	private DownloadEmployeeMailAttachOperation createDownloadOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		DownloadEmployeeMailAttachOperation operation = createOperation(DownloadEmployeeMailAttachOperation.class);
		Long id = frm.getId();
		operation.initialize(id);

		return  operation;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewMailForm frm = (ViewMailForm) form;
		ViewMailOperation operation = createViewEntityOperation(frm);
		addLogParameters(new BeanLogParemetersReader("Данные просматриваемой сущности", operation.getEntity()));
		updateFormData(operation, frm);

		MailState mailState = frm.getMail().getState();
		if (mailState == MailState.NEW)
		{
			if (frm.getSender() != null)
			{
				return mapping.findForward(FORWARD_VIEW_INCOMING);
			}
			else
			{
				return mapping.findForward(FORWARD_VIEW_SENT);
			}
		}
		else if(mailState == MailState.EMPLOYEE_DRAFT)
		{
			return mapping.findForward(FORWARD_VIEW_DRAFT);
		}
		else
		{
			ActionRedirect redirect = new ActionRedirect(mapping.findForward(FORWARD_REPLY));
			redirect.addParameter(ID_PARAMETER_NAME, frm.getId());
			return redirect;
		}
	}

	private void updateFormData(ViewMailOperation operation, ViewMailForm form) throws BusinessException, BusinessLogicException
	{
		if (!checkAccess(ViewMailOperation.class, "MailManagment") && checkAccess(ViewMailOperation.class, "MailManagementUseClientForm"))
		{
			form.setErkc(true);
			updateUserDataForERKC(form);
		}

		Mail mail = operation.getEntity();
		form.setMail(mail);
		form.setField("correspondence", operation.getCorrespondence());
		form.setField("canReply", MailHelper.canReply(mail, operation.getLogin()));
		form.setField("employeeFIO", operation.getResponsibleEmployeeFio());
		form.setField("reassignHistory", operation.getReassignHistory());
		if (mail.getDirection() == MailDirection.ADMIN)
		{
			form.setSender(operation.getPersonByLogin(mail.getSender()));
		}
		else
		{
			form.setField("recipientId", operation.getRecipientId());
		}
	}

	private void updateUserDataForERKC(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> userInfo = ERKCEmployeeUtil.getUserInfo();

		if (userInfo.isEmpty())
		{
			throw new ERKCNotFoundClientBusinessException("Для того чтобы выполнить просмотр записи, перейдите к списку писем из анкеты клиента.");
		}

		ERKCEmployeeUtil.addUserDataForERKC(form, userInfo);
	}

	public Pair<String, byte[]> createData(ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewMailForm form = (ViewMailForm) frm;
		DownloadEmployeeMailAttachOperation operation = createDownloadOperation(form);
		String fileName = new String(operation.getFileName().getBytes("Cp1251"), "Cp1252");
		return new Pair<String, byte[]>(fileName , operation.getData());
	}

	public ActionForward actionAfterUnload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}

	/**
	 * ответ на письмо
	 * @param mapping  маппинг
	 * @param form     форма
	 * @param request  реквест
	 * @param response респунс
	 * @return ActionForward
	 * @throws BusinessException
	 */
	public final ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewMailForm frm = (ViewMailForm) form;
		EditMailOperation operation = createOperation(EditMailOperation.class);
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(FORWARD_REPLY));
		redirect.addParameter(ID_PARAMETER_NAME, operation.createReply(frm.getId()));
		return redirect;
	}

	/**
	 * Редактирование черновика
	 * @param mapping  маппинг
	 * @param form     форма
	 * @param request  реквест
	 * @param response респунс
	 * @return ActionForward
	 * @throws BusinessException
	 */
	public final ActionForward editDraft(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewMailForm frm = (ViewMailForm) form;
		ViewMailOperation operation = createOperation(ViewMailOperation.class, "MailManagment");
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(FORWARD_REPLY));
		try
		{
			redirect.addParameter(ID_PARAMETER_NAME, operation.initializeDraft(frm.getId()));
		}
		catch (BusinessLogicException ble)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ble.getMessage(), false));
			saveErrors(currentRequest(), msgs);
			return start(mapping, form, request, response);
		}
		return redirect;
	}

	/**
	 * Создаёт FormProcessor
	 * @param frm форма
	 * @return FormProcessor
	 * @throws Exception
	 */
	protected FormProcessor<ActionMessages, ?> getFormProcessor(ViewMailForm frm) throws Exception
	{
		Map<String, Object> fields = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(fields);
		Form editForm = isMailMultiBlockMode()? ViewMailForm.MULTI_BLOCK_REASSIGN_FORM : ViewMailForm.EDIT_FORM;
		//Фиксируем данные, введенные пользователе
		addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", editForm, fields));

		return createFormProcessor(valuesSource, editForm);
	}

	private boolean isMailMultiBlockMode()
	{
		return MultiBlockModeDictionaryHelper.isMailMultiBlockMode();
	}

	/**
	 * Назначает нового ответственного
	 * @param mapping  маппинг
	 * @param form     форма
	 * @param request  реквест
	 * @param response респунс
	 * @return ActionForward
	 * @throws BusinessException
	 */
	public final ActionForward reassign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewMailForm frm = (ViewMailForm) form;
		ReassignEmployeeOperation operation = createOperation(ReassignEmployeeOperation.class);

		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			if (isMailMultiBlockMode())
				operation.initialize(frm.getId(), (String)result.get("employeeLoginId"));
			else
				operation.initialize(frm.getId(), (Long)result.get("employeeLoginId"));
			ReassignMailReason reason = operation.getReason();
			reason.setReassignReason((String)result.get("reassignReason"));
			operation.save();
		}
		else
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}
		return mapping.findForward(FORWARD_REASSIGN_SUCCESS);
	}
}
