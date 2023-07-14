package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.pages.Page;
import com.rssl.phizic.business.dictionaries.pages.messages.Importance;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessage;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessageState;
import com.rssl.phizic.business.dictionaries.pages.messages.ViewType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pages.messages.EditInformMessagesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.*;

/**
 * Action редактирования информационного сообщения.
 * @author komarov
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditInformMessagesAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditInformMessagesOperation editOperation =  createOperation(EditInformMessagesOperation.class, "InformationMessageService");
		Long id = frm.getId();
		if(id != null && id != 0)
		{
			editOperation.initialize(id);
			return editOperation;
		}
		editOperation.initialize();
		return editOperation;
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditInformMessagesForm form = (EditInformMessagesForm) frm;
		ActionMessages         msgs = new ActionMessages();

		if(ArrayUtils.isEmpty(form.getSelectedIds()))
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Выберите страницы для публикации!", false));
			return msgs;
		}

		if(notSelectedDepartments(form, operation))
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Выберите департаменты для публикации!", false));
			return msgs;
		}

		return msgs;
	}

	private boolean notSelectedDepartments(EditInformMessagesForm form, EditEntityOperation operation)
	{
		if ("on".equals(form.getField("saveAsTemplate")))
			return ArrayUtils.isEmpty(form.getSelectedDepartmentsIds());
		else
		{
			return CollectionUtils.isEmpty(((EditInformMessagesOperation)operation).getAllowedDepartmentsFromIds(form.getSelectedDepartmentsIds()));
		}
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditInformMessagesForm.INFORM_MESSAGES_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Calendar fromDate = DateHelper.createCalendar(data.get("fromDate"), data.get("fromTime"));
		Calendar toDate   = DateHelper.createCalendar(data.get("toDate"),   data.get("toTime"));
		Importance importance = Importance.valueOf((String)data.get("importance"));
		
		InformMessage informMessage = (InformMessage) entity;
		boolean saveAsTemplate = (Boolean)data.get(EditInformMessagesForm.SAVE_AS_TEMPLATE_FIELD);
		if(saveAsTemplate)
		{
			if(informMessage.getState() != InformMessageState.TEMPLATE)
			{
				informMessage.setId(null);
			}
			informMessage.setName((String)data.get("name"));
			informMessage.setState(InformMessageState.TEMPLATE);
			informMessage.setStartPublishDate(Calendar.getInstance());
		}
		else
		{
			if(informMessage.getState() == InformMessageState.TEMPLATE)
			{
				informMessage.setId(null);
				informMessage.setName(null);
			}
			informMessage.setState(InformMessageState.PUBLISED);
			informMessage.setStartPublishDate(fromDate);
		}
		informMessage.setCancelPublishDate(toDate);
		informMessage.setText((String)data.get("text"));
		informMessage.setImportance(importance);
		informMessage.setVievType(ViewType.valueOf((String)data.get("viewType")));
	}

    protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
    {
	   EditInformMessagesOperation operation = (EditInformMessagesOperation)editOperation;
	   EditInformMessagesForm      form      = (EditInformMessagesForm)editForm;

	   operation.setPagesAndDepartments(form.getSelectedIds(), form.getSelectedDepartmentsIds());
    }

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditInformMessagesForm form = (EditInformMessagesForm) frm;
		EditInformMessagesOperation editOperation = (EditInformMessagesOperation)operation;

		if (!frm.isFromStart())
		{
			List<Long> selectedPagesIds = new ArrayList<Long>(Arrays.asList(form.getSelectedIds()));
			Set<Page> selectedPages = new HashSet<Page>(selectedPagesIds.size());
			Set<Page> pagesFromEntity = ((InformMessage) operation.getEntity()).getPages();
			if (pagesFromEntity !=null)
			{
				for (Page page : pagesFromEntity)
				{
					Long currentId = page.getId();
					if (selectedPagesIds.contains(currentId))
					{
						selectedPagesIds.remove(currentId);
						selectedPages.add(page);
					}
				}
			}

			if (!selectedPagesIds.isEmpty())
			{
				selectedPages.addAll(editOperation.getPages(selectedPagesIds.toArray(new Long[selectedPagesIds.size()])));
			}
			form.setPages(selectedPages);
		}
		else
		{
			form.setPages(((InformMessage) operation.getEntity()).getPages());
		}


		Set<String> departments = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(((InformMessage) operation.getEntity()).getDepartments()))
			departments.addAll(((InformMessage) operation.getEntity()).getDepartments());

		if (form.isFromStart())
		{
			if (!departments.isEmpty())
			{
				String[] selectedDepartmentsIds = departments.toArray( new String[departments.size()] );
				form.setSelectedDepartmentsIds(selectedDepartmentsIds);
			}
		}
		Set<String> allowedDepartments = editOperation.getAllowedDepartments();
		departments.addAll(allowedDepartments);
		form.setDepartments(departments);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		InformMessage informMessage = (InformMessage) entity;
		EditInformMessagesForm form = (EditInformMessagesForm) frm;

		//Если сообщение создаётся из шаблона, то дату ставить не нужно.
		form.setField(EditInformMessagesForm.SAVE_AS_TEMPLATE_FIELD,  Boolean.FALSE);
		if(informMessage.getState() != InformMessageState.TEMPLATE)
		{
			Date fromDate = DateHelper.toDate(informMessage.getStartPublishDate());
		    Date toDate =   DateHelper.toDate(informMessage.getCancelPublishDate());

			if (informMessage.getId() == null)
			{
				fromDate = Calendar.getInstance().getTime();
				toDate = DateHelper.add(fromDate, 0, 0, 1);
			}
			form.setField("fromDate", fromDate);
			form.setField("toDate",   toDate);
			form.setField("fromTime", fromDate);
			form.setField("toTime",   toDate);
			//Если у информационного сообщения не проставлена дата окончания публикации, то оно бессрочно.
			form.setField(EditInformMessagesForm.INDEFINITELY_FIELD, fromDate != null && toDate == null);
		}

		form.setField("text", informMessage.getText());
		form.setField("name", informMessage.getName());
		form.setField("importance", informMessage.getImportance());
		form.setField("viewType", informMessage.getVievType().name());
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		//Фиксируем данные, введенные пользователем
		addLogParameters(frm);
		operation.save();
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("informMessagesBundle", "message.data-saved"), false), null);
		return createSaveActionForward(operation, frm);
	}

}