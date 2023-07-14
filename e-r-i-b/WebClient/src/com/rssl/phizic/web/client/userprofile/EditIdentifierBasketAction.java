package com.rssl.phizic.web.client.userprofile;


import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.basketident.AttributeSystemId;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.operations.userprofile.EditIdentifierBasketOperation;
import com.rssl.phizic.operations.userprofile.RemoveIdentifierBasketOperation;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.ActionMessageHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 18.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditIdentifierBasketAction  extends OperationalActionBase
{
	private static final String CONFIRM_FORWARD = "ConfirmForm";
	private static final String REMOVE_START_FORWARD = "RemoveStart";
	private static final String REMOVE_CONFIRM_FORWARD = "RemoveConfirm";
	private static final String SHOW_PROFILE_FORWARD = "ShowProfile";
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmSMS", "changeToSMS");
		map.put("button.confirm",    "confirm");
		map.put("button.save",       "save");
		map.put("button.remove",       "remove");
		map.put("button.removeSMS", "removeSMS");
		map.put("button.remove.confirm", "removeConfirm");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditIdentifierBasketForm frm = (EditIdentifierBasketForm) form;
		EditIdentifierBasketOperation  operation = createOperation("EditIdentifierBasketOperation");
		if (frm.getId() != null)
		{
			operation.initialize(frm.getId());
			UserDocument userDocument = operation.getUserDocument();
			frm.setField("name", userDocument.getName());
			frm.setField("number", userDocument.getNumber());
			frm.setField("series", userDocument.getSeries());
			frm.setField("issueBy", userDocument.getIssueBy());
			if (userDocument.getIssueDate() != null)
				frm.setField("issueDate", userDocument.getIssueDate().getTime());
			if (userDocument.getExpireDate() != null)
				frm.setField("expireDate", userDocument.getExpireDate().getTime());
			frm.setUserDocument(userDocument);
			frm.setLinks(operation.getLink());
		}
		frm.setBasketIndetifierType(operation.getAllowedTypes().get(frm.getDocumentType()));

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward changeToSMS(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditIdentifierBasketOperation operation = getOperation(request);
		EditIdentifierBasketForm form = (EditIdentifierBasketForm) frm;
		operation.setUserStrategyType(ConfirmStrategyType.sms);

		ConfirmationManager.sendRequest(operation);
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		operation.getRequest().setPreConfirm(true);
		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		callBackHandler.setAdditionalCheck();
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			ConfirmRequest confirmRequest = operation.getRequest();
			confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(SecurityMessages.translateException(e)));
		}
		return forwardShow(mapping, operation, form, request);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditIdentifierBasketOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);
		try
		{
			EditIdentifierBasketForm form = (EditIdentifierBasketForm) frm;
			form.setConfirmStrategy(operation.getConfirmStrategy());

			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (errors.isEmpty())
				return doConfirm(mapping, operation, form, request);

			ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
			return mapping.findForward(CONFIRM_FORWARD);
		}
		catch (TemporalBusinessException ignore)
		{
			String exceptionMessage = getResourceMessage("paymentsBundle", "message.operation.not.available");
			ConfirmHelper.saveConfirmErrors(confirmRequest, Collections.singletonList(exceptionMessage));
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditIdentifierBasketOperation operation = createOperation(EditIdentifierBasketOperation.class);
		EditIdentifierBasketForm form = (EditIdentifierBasketForm) frm;
		if (form.getId() == null || form.getId()== 0)
			operation.initializeNew(form.getDocumentType());
		else
			operation.initialize(form.getId());

		form.setBasketIndetifierType(operation.getAllowedTypes().get(form.getDocumentType()));
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(form.getFields()), form.createForm());
		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		try
		{
			Map<String, Object> result = processor.getResult();
			operation.setName((String)result.get("name"));
			operation.setNumber((String)validate(operation.getAllowedTypes().get(form.getDocumentType()), AttributeSystemId.NUMBER, (String)result.get("number"), "number", request));
			operation.setSeries((String)validate(operation.getAllowedTypes().get(form.getDocumentType()), AttributeSystemId.SERIES, (String)result.get("series"), "series", request));
			operation.setIssueBy((String)validate(operation.getAllowedTypes().get(form.getDocumentType()), AttributeSystemId.ISSUE_BY, (String)result.get("issueBy"), "issueBy", request));
			Date issueDate = (Date) validate(operation.getAllowedTypes().get(form.getDocumentType()), AttributeSystemId.ISSUE_DATE, (String)result.get("issueDate"), "issueDate", request);
			if (issueDate != null)
				operation.setIssueDate(DateHelper.toCalendar(issueDate));
			Date expireDate = (Date) validate(operation.getAllowedTypes().get(form.getDocumentType()), AttributeSystemId.EXPIRE_DATE, (String)result.get("expireDate"), "expireDate", request);
			if (expireDate != null)
				operation.setExpireDate(DateHelper.toCalendar(expireDate));
		}
		catch (BusinessLogicException e)
		{
			return mapping.findForward(FORWARD_START);
		}

		saveOperation(request, operation);
		operation.resetConfirmStrategy();
    	ConfirmationManager.sendRequest(operation);
		form.setField("confirmableObject", operation.getConfirmableObject());
		form.setActionType("view");

		return mapping.findForward(FORWARD_START);
	}

	private Object validate(BasketIndetifierType tp, AttributeSystemId attribute, String field, String fieldName, HttpServletRequest request) throws BusinessLogicException
	{
		try
		{
			if (tp == null)
				return field;
			AttributeForBasketIdentType attr = tp.getAttributes().get(attribute.name());
			if (attr == null)
				return field;

			if (attr.isMandatory() && StringHelper.isEmpty(field)) {
				throw new BusinessLogicException("Поле "+ field +" обязательно");
			}

			if (!StringHelper.isEmpty(field) && !StringHelper.isEmpty(attr.getRegexp()) && !field.matches(attr.getRegexp()))
			{
				throw new BusinessLogicException("Поле \"" + attr.getName() + "\" не удовлетворяет условиям ввода");
			}

			try
			{
				return attr.getDataType().parse(field);
			}
			catch (Exception e)
			{
				throw new BusinessLogicException("Поле \"" + attr.getName() + "\" должно содержать данные: " + attr.getDataType().getName());
			}
		}
		catch (BusinessLogicException e)
		{
			ActionMessages errors = new ActionMessages();
			ActionMessage am = new ActionMessage(fieldName,new ActionMessage(e.getMessage(), false));
			errors.add(ActionMessages.GLOBAL_MESSAGE, am);
			saveErrors(request, errors);
			throw e;
		}
	}

	private ActionForward forwardShow(ActionMapping mapping, EditIdentifierBasketOperation operation, EditIdentifierBasketForm form, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		form.setConfirmStrategy(operation.getConfirmStrategy());

		form.setConfirmableObject(operation.getConfirmableObject());
		UserDocument userDocument = operation.getUserDocument();
		form.setField("name", userDocument.getName());
		form.setField("number", userDocument.getNumber());
		form.setField("series", userDocument.getSeries());
		form.setField("issueBy", userDocument.getIssueBy());
		if (userDocument.getIssueDate() != null)
			form.setField("issueDate", userDocument.getIssueDate().getTime());
		if (userDocument.getExpireDate() != null)
			form.setField("expireDate", userDocument.getExpireDate().getTime());
		form.setUserDocument(userDocument);
		form.setDocumentType(userDocument.getDocumentType().name());
		form.setBasketIndetifierType(operation.getAllowedTypes().get(userDocument.getDocumentType().name()));

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(CONFIRM_FORWARD);
	}

	private ActionForward doConfirm(ActionMapping mapping, EditIdentifierBasketOperation operation, EditIdentifierBasketForm form, HttpServletRequest request) throws Exception
	{
		try
		{
			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
			ActionMessageHelper.saveSessionMessage(currentRequest(), operation.getMessageCollector().getMessages());
			return mapping.findForward(SHOW_PROFILE_FORWARD);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));

			return forwardShow(mapping, operation, form, request);
		}
	}

	public ActionForward remove(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoveIdentifierBasketOperation operation = createOperation(RemoveIdentifierBasketOperation.class);
		EditIdentifierBasketForm form = (EditIdentifierBasketForm) frm;
		operation.initialize(form.getId());

		saveOperation(request, operation);
		operation.resetConfirmStrategy();

		ConfirmationManager.sendRequest(operation);
		form.setUserDocument(operation.getUserDocument());
		form.setConfirmableObject(operation.getConfirmableObject());
		form.setConfirmStrategy(operation.getConfirmStrategy());
		return mapping.findForward(REMOVE_START_FORWARD);
	}

	public ActionForward removeSMS(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoveIdentifierBasketOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		EditIdentifierBasketForm form = (EditIdentifierBasketForm) frm;
		ConfirmationManager.sendRequest(operation);
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		operation.getRequest().setPreConfirm(true);
		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			ConfirmRequest confirmRequest = operation.getRequest();
			confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(SecurityMessages.translateException(e)));
		}
		form.setConfirmStrategy(operation.getConfirmStrategy());

		form.setConfirmableObject(operation.getConfirmableObject());
		return mapping.findForward(REMOVE_CONFIRM_FORWARD);
	}


	public ActionForward removeConfirm(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoveIdentifierBasketOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);
		try
		{
			EditIdentifierBasketForm form = (EditIdentifierBasketForm) frm;
			form.setConfirmStrategy(operation.getConfirmStrategy());

			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (errors.isEmpty())
				return  doRemove(mapping, operation, form);

			ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
			return mapping.findForward(CONFIRM_FORWARD);
		}
		catch (TemporalBusinessException ignore)
		{
			String exceptionMessage = getResourceMessage("paymentsBundle", "message.operation.not.available");
			ConfirmHelper.saveConfirmErrors(confirmRequest, Collections.singletonList(exceptionMessage));
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
	}

	private ActionForward doRemove(ActionMapping mapping, RemoveIdentifierBasketOperation operation, EditIdentifierBasketForm form) throws Exception
	{
		try
		{
			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
			return mapping.findForward(SHOW_PROFILE_FORWARD);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));
			form.setConfirmStrategy(operation.getConfirmStrategy());

			form.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(REMOVE_CONFIRM_FORWARD);
		}
	}

}
