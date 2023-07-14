package com.rssl.phizic.web.mail;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.csaadmin.node.LastNodeInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditMailOperation;
import com.rssl.phizic.operations.person.list.ChooseClientOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 23.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен выбора клиента дл€ отправки письма
 */

public class ChooseClientAction extends com.rssl.phizic.web.ext.sbrf.mail.EditMailAction
{
	public static final String MAIL_KEY = "mail";
	private static final String SAVE_ERROR_FORWARD = "SaveError";

	@Override
	protected Map<String,String> getAdditionalKeyMethodMap()
	{
		HashMap<String, String> keys = new HashMap<String, String>();
		keys.put("button.send", "send");
		return keys;
	}

	private Profile createUserProfile(Map<String,Object> clientInfo)
	{
		Profile profile = new Profile();
		profile.setSurName((String) clientInfo.get("surname"));
		profile.setFirstName((String) clientInfo.get("firstname"));
		profile.setPatrName((String) clientInfo.get("patrname"));
		profile.setPassport((String) clientInfo.get("passport"));
		profile.setBirthDay(DateHelper.toCalendar((Date) clientInfo.get("birthDay")));
		profile.setTb((String) clientInfo.get("tb"));
		return profile;
	}

	private EditMailOperation getEditMailOperation() throws BusinessException
	{
		return createOperation(EditMailOperation.class, "MailManagment");
	}

	private Store getCurrentStore()
	{
		return StoreManager.getCurrentStore();
	}

	private Mail getRestoredMail()
	{
		return (Mail) getCurrentStore().restore(MAIL_KEY);
	}

	private void storeMail(Mail mail)
	{
		getCurrentStore().save(MAIL_KEY, mail);
	}

	private void removeStoredMail()
	{
		getCurrentStore().remove(MAIL_KEY);
	}

	private Mail getRestoredMailFromAnotherNode() throws BusinessException, BusinessLogicException
	{
		LastNodeInfoOperation operation = createOperation(LastNodeInfoOperation.class);
		Map<String,Object> operationContext = operation.getOperationContext();
		return (Mail) operationContext.get(MAIL_KEY);
	}

	@Override
	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		if (frm.getId() != null)
			return super.createViewOperation(frm);

		Mail source = getRestoredMail();
		if (source != null)
		{
			frm.setId(source.getId());
			EditMailOperation viewOperation = (EditMailOperation) super.createViewOperation(frm);
			viewOperation.updateMailData(source);
			return viewOperation;
		}
		EditMailOperation editMailOperation = getEditMailOperation();
		Mail storedMail = getRestoredMailFromAnotherNode();
		editMailOperation.initializeNew(storedMail);
		Mail newMailEntity = editMailOperation.getEntity();
		frm.setId(newMailEntity.getId());
		storeMail(newMailEntity);
		return editMailOperation;

	}

	@Override
	protected void doStart(EditEntityOperation editOperation, EditFormBase frm) throws Exception
	{
		ChooseClientForm form = (ChooseClientForm) frm;
		EditMailOperation editMailOperation = (EditMailOperation) editOperation;
		Mail mail = editMailOperation.getEntity();
		form.setId(mail.getId());
		form.setField("mailState", mail.getState());
		ChooseClientOperation operation = createOperation(ChooseClientOperation.class);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFilters()), com.rssl.phizic.web.persons.list.ChooseClientForm.SEARCH_FORM);
		if(processor.process())
		{
			Profile profile = createUserProfile(processor.getResult());
			operation.initialize(profile);
			List<ActivePerson> clients = operation.getClients();
			form.setData(clients);
		}
		else
		{
			saveErrors(currentRequest(), processor.getErrors());
		}
	}

	private ActionForward getSaveErrorActionForward(String message, ActionMapping mapping, com.rssl.phizic.web.ext.sbrf.mail.EditMailForm form, EditMailOperation operation) throws BusinessException
	{
		if (operation != null)
		{
			storeOperation(operation);
			updateForm(form, operation.getEntity());
			updateFormAdditionalData(form, operation);
		}
		saveError(currentRequest(), new ActionMessage(message, false));
		return mapping.findForward(SAVE_ERROR_FORWARD);
	}

	private ActionForward doSend(ChooseClientForm editMailForm, EditMailOperation operation, Long recipientId, String recipientName) throws BusinessException
	{
		Mail mail = operation.getEntity();

		operation.updateMailData(getRestoredMail());

		mail.setRecipientId(recipientId);
		mail.setRecipientName(recipientName);

		updateForm(editMailForm, mail);
		try
		{
			ActionForward actionForward = doInBlockSave(operation, editMailForm);
			removeStoredMail();
			return actionForward;
		}
		catch (BusinessLogicException e)
		{
			return getSaveErrorActionForward(e.getMessage(), getCurrentMapping(), editMailForm, operation);
		}
		catch (Exception ignore)
		{
			return getSaveErrorActionForward("ѕисьмо не отправлено.", getCurrentMapping(), editMailForm, operation);
		}
	}

	@Override
	protected ActionForward createStartActionForward(ActionForm frm, ActionMapping mapping) throws BusinessException
	{
		ChooseClientForm form = (ChooseClientForm) frm;
		List<ActivePerson> clients = form.getData();
		if (clients.size() != 1)
			return getCurrentMapping().findForward(FORWARD_START);


		EditMailOperation editMailOperation = getEditMailOperation();
		editMailOperation.initialize(form.getId());
		ActivePerson person = clients.get(0);
		return doSend(form, editMailOperation, person.getLogin().getId(), person.getFullName());
	}

	/**
	 * отправить/сохранить письмо
	 * @param mapping  экшен-маппинг
	 * @param form     экшен-форма
	 * @param request  запрос
	 * @param response ответ
	 * @return следующий обработчик запроса
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessLogicException, BusinessException
	{
		ChooseClientForm editMailForm = (ChooseClientForm) form;
		EditMailOperation editOperation = (EditMailOperation) createEditOperation(editMailForm);
		Long recipientId = editMailForm.getMailRecipientId();
		String recipientName = editMailForm.getMailRecipientName();
		return doSend(editMailForm, editOperation, recipientId, recipientName);
	}
}
