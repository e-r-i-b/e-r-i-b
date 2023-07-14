package com.rssl.phizic.web.persons.search.pfp;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.person.search.multinode.ChangeNodeLogicException;
import com.rssl.phizic.operations.person.search.pfp.EditPotentialPersonInfoMultiNodeOperation;
import com.rssl.phizic.operations.person.search.pfp.EditPotentialPersonInfoOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.struts.action.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования данных потенциального клиента
 */

public class EditPotentialPersonInfoAction extends OperationalActionBase
{
	private static final String SUCCESS_FORWARD = "Success";
	private static final String CHANGE_NODE_FORWARD = "ChangeNode";
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.singletonMap("button.save", "save");
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPotentialPersonInfoForm frm = (EditPotentialPersonInfoForm) form;
		EditPotentialPersonInfoOperation operation = createEditOperation();
		if(frm.isContinueSave())
		{
			operation.continueSave();
			return getCurrentMapping().findForward(SUCCESS_FORWARD);
		}
		operation.initialize();
		ActivePerson person = operation.getEntity();
		//фиксируем начальные данные редактируемой сущности
		addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", person));

		updateForm(frm, person);
		updateFormAdditionalData(frm, operation);

		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected EditPotentialPersonInfoOperation createEditOperation() throws BusinessException, BusinessLogicException
	{
		EditPotentialPersonInfoOperation operation;
		CSAAdminGateConfig config = ConfigFactory.getConfig(CSAAdminGateConfig.class);
		if(config.isMultiBlockMode())
		{
			operation = createOperation(EditPotentialPersonInfoMultiNodeOperation.class);
		}
		else
		{
			operation = createOperation(EditPotentialPersonInfoOperation.class);
		}
		return operation;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPotentialPersonInfoForm frm = (EditPotentialPersonInfoForm) form;
		EditPotentialPersonInfoOperation operation = createEditOperation();
		operation.initialize();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditPotentialPersonInfoForm.EDIT_FORM);
		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			PersonalSubscriptionData subscriptionData = operation.getSubscriptionData();
			subscriptionData.setEmailAddress((String) result.get("email"));
			subscriptionData.setMobilePhone((String)result.get("mobilePhone"));
			try
			{
				operation.save();
				addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", EditPotentialPersonInfoForm.EDIT_FORM, frm.getFields()));
			}
			catch (ChangeNodeLogicException e)
			{
				ActionRedirect redirect = new ActionRedirect(mapping.findForward(CHANGE_NODE_FORWARD));
				redirect.addParameter("nodeId",e.getNodeId());
				redirect.addParameter("action",request.getServletPath());
				redirect.addParameter("parameters(continueSave)","true");
				return redirect;
			}
			catch (BusinessLogicException e)
			{
				updateFormAdditionalData(frm,operation);
				saveError(request, e.getMessage());
				return getCurrentMapping().findForward(FORWARD_START);
			}
		}
		else
		{
			updateFormAdditionalData(frm,operation);
			saveErrors(request, processor.getErrors());
			return getCurrentMapping().findForward(FORWARD_START);
		}
		return getCurrentMapping().findForward(SUCCESS_FORWARD);
	}

	protected void updateForm(EditFormBase frm, ActivePerson person) throws Exception
	{
		frm.setField(EditPotentialPersonInfoForm.FIRST_NAME_FIELD_NAME, person.getFirstName());
		frm.setField(EditPotentialPersonInfoForm.PATR_NAME_FIELD_NAME, person.getPatrName());
		frm.setField(EditPotentialPersonInfoForm.SUR_NAME_FIELD_NAME, person.getSurName());

		PersonDocument personDocument = getPersonDocument(person);
		frm.setField(EditPotentialPersonInfoForm.DOC_SERIES_FIELD_NAME, personDocument.getDocumentSeries());
		frm.setField(EditPotentialPersonInfoForm.DOC_NUMBER_FIELD_NAME, personDocument.getDocumentNumber());

		frm.setField(EditPotentialPersonInfoForm.BIRTHDAY_FIELD_NAME, new SimpleDateFormat(DATE_FORMAT).format(person.getBirthDay().getTime()));
		if (person.getId()!= null)
		{
			frm.setField(EditPotentialPersonInfoForm.MOBILE_PHONE_FIELD_NAME, person.getMobilePhone());
			if (person.getEmail() != null)
				frm.setField(EditPotentialPersonInfoForm.EMAIL_FIELD_NAME, person.getEmail());
		}

	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation editOperation) throws Exception
	{
		EditPotentialPersonInfoOperation operation = (EditPotentialPersonInfoOperation) editOperation;
		frm.setField(EditPotentialPersonInfoForm.DEPARTMENT_NAME_FIELD_NAME, operation.getDepartmentName());
		EditPotentialPersonInfoForm form = (EditPotentialPersonInfoForm) frm;
		ActivePerson person = operation.getEntity();
		form.setActivePerson(person);
		PersonDocument personDocument = getPersonDocument(person);
		frm.setField(EditPotentialPersonInfoForm.DOC_TYPE_FIELD_NAME, personDocument.getDocumentType());
	}

	private PersonDocument getPersonDocument(ActivePerson person)
	{
		return person.getPersonDocuments().iterator().next();
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditPotentialPersonInfoOperation operation = (EditPotentialPersonInfoOperation) editOperation;
		PersonalSubscriptionData subscriptionData = operation.getSubscriptionData();
		subscriptionData.setEmailAddress((String)validationResult.get("email"));
		subscriptionData.setMobilePhone((String)validationResult.get("mobilePhone"));
	}
}
