package com.rssl.phizic.web.client.documents.templates;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.iPasPasswordCardConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.validators.ERIBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardLinkHelper;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.documents.templates.RemoveTemplateOperation;
import com.rssl.phizic.operations.payment.ConfirmTemplateOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.AutoConfirmRequestType;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: filimonova
 * @ created: 15.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmTemplateAction extends ConfirmTemplateActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();

		map.put("button.edit",              "edit");
		map.put("button.dispatch",          "confirm");
		map.put("button.preConfirm",        "preConfirm");
		map.put("button.confirmCard",       "showCardsConfirm");
		map.put("confirmBySelectedCard",    "changeToCard");
		map.put("button.confirmSMS",        "changeToSMS");
		map.put("button.confirmCap",        "changeToCap");
		map.put("button.confirmPush",       "changeToPush");
		map.put("button.remove",            "remove");
		map.put("button.nextStage",         "doNextStage");
		map.put("button.edit_template",     "edit");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    return autoConfirm(mapping, form, request, response);
    }

	protected ActionForward autoConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmTemplateForm frm = (ConfirmTemplateForm) form;
			ConfirmTemplateOperation operation = createConfirmEntityOperation(frm);

			saveOperation(request, operation);

			if (needAutoConfirm(operation, request))
			{
				String userOptionType =  AuthenticationContext.getContext().getPolicyProperties().getProperty("userOptionType");
				if (userOptionType==null)
					userOptionType="sms";

				ConfirmStrategyType type = ConfirmStrategyType.valueOf(userOptionType);
				if (type == ConfirmStrategyType.sms &&
						ConfirmHelper.strategySupported(operation.getConfirmStrategy(), ConfirmStrategyType.sms))
				{
					frm.setAutoConfirmRequestType(AutoConfirmRequestType.payment);
					return changeToSMS(mapping, form, request, response);
				}
				else if (type == ConfirmStrategyType.card &&
						ConfirmHelper.strategySupported(operation.getConfirmStrategy(), ConfirmStrategyType.card))
				{
					frm.setAutoConfirmRequestType(AutoConfirmRequestType.payment);
					ConfirmationManager.sendRequest(operation);
					return showCardsConfirm(mapping, form, request, response);
				}
			}

			updateForm(frm, operation);

			ConfirmationManager.sendRequest(operation);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return mapping.findForward(FORWARD_SHOW);

		}
		catch (TemporalBusinessException ignore)
		{
			ActionMessage error = new ActionMessage(Constants.TEMPLATE_OPERATION_ERROR_MESSAGE, false);
			saveSessionError(Constants.TEMPLATE_OPERATION_ERROR_MESSAGE, error, null);
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
	}

	public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmTemplateOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, response, ConfirmStrategyType.sms);
	}

	public ActionForward changeToPush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmTemplateOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.push);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, response, ConfirmStrategyType.push);
	}

	public ActionForward showCardsConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmTemplateForm frm = (ConfirmTemplateForm) form;
		ConfirmTemplateOperation operation = getOperation(request);

		List<CardLink> links = CardLinkHelper.getMainCardLinks();
		//если ошибок не было и только одна карта по коорой возможно подтверждение чеком не отображаем формы с выбором карт.
		if (links.size() == 1 && frm.getField("cardConfirmError") == null)
		{
			frm.setField("confirmCardId", links.get(0).getId().toString());
			return changeToCard(mapping, form, request, response);
		}

		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(false);
		frm.setField("confirmCards", links);
		frm.setField("confirmByCard", true);

		updateForm(frm, operation);
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	public ActionForward changeToCard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmTemplateForm frm = (ConfirmTemplateForm) form;
		ConfirmTemplateOperation operation = getOperation(request);

		ConfirmRequest confirmRequest;
		try
		{
			frm.setField("confirmByCard",true);
			addConfirmCardParameter(frm, operation, true);
			operation.setUserStrategyType(ConfirmStrategyType.card);
			confirmRequest = sendChangeToCardRequest(frm, operation);
			confirmRequest.setPreConfirm(true);
		}
		catch (BusinessLogicException e)
		{
			frm.setField("cardConfirmError", e.getMessage());
			frm.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
			return showCardsConfirm(mapping, frm, request, response);
		}

		// Если сменилась стратегия подтверждения из-за ошибки, пишем причину и отправляем СМС-пароль
		// Сейчас фактически других стратегий быть не может. Если появятся, нужно будет уточнять, что с ними делать.
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.cap || currentType == ConfirmStrategyType.push)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			saveConfirmErrors(Collections.singletonList(operation.getWarning().getMessage()), confirmRequest);
			// Эта ошибка уже обработана, сеттим null, чтобы не выводилась в основном окне.
			operation.setWarning(null);
			return preConfirm(mapping, frm, request, response, currentType);
		}
		//иначе - подтверждение чековым паролем:
		iPasPasswordCardConfirmRequest ippccr = (iPasPasswordCardConfirmRequest) confirmRequest;
		ippccr.setAdditionInfo(ConfirmHelper.getPasswordCardConfirmStrategyAdditionalInfo(ippccr.getPasswordsLeft()));

		updateForm(frm, operation);
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmTemplateForm frm = (ConfirmTemplateForm) form;

		RemoveTemplateOperation operation = createRemoveEntityOperation(frm);
		operation.remove();

		addLogParameters(new BeanLogParemetersReader("Данные удаляемой сущности", operation.getEntity()));

		return mapping.findForward(OPEN_PAYMENTS_AND_TEMPLATES);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ConfirmTemplateForm frm = (ConfirmTemplateForm) form;

		EditTemplateDocumentOperation operation = createEditEntityOperation(frm);
		operation.edit();

		addLogParameters(new BeanLogParemetersReader("Редактируемая сущность", operation.getTemplate()));

		return createEditActionForward(operation);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmTemplateForm frm = (ConfirmTemplateForm) form;
			ConfirmTemplateOperation operation = getOperation(request);

			ConfirmRequest confirmRequest = operation.getRequest();
			clearConfirmErrors(request, confirmRequest);

			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (!errors.isEmpty())
			{
				saveConfirmErrors(errors, confirmRequest);

				updateForm(frm, operation);
				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

				return mapping.findForward(FORWARD_SHOW_FORM);
			}

			return doConfirm(mapping, frm, request, response);
		}
		catch (TemporalBusinessException e)
		{
			ActionMessage error = new ActionMessage(Constants.TEMPLATE_OPERATION_ERROR_MESSAGE, false);
			saveSessionError(Constants.TEMPLATE_OPERATION_ERROR_MESSAGE, error, null);
			return mapping.findForward(FORWARD_SHOW);
		}
	}

	@Override
	protected TemplateValidator[] getTemplateValidators()
	{
		return new TemplateValidator[]{new OwnerTemplateValidator(), new ERIBTemplateValidator()};
	}

	protected ActionForward createEditActionForward(EditTemplateDocumentOperation operation) throws BusinessException
	{
		TemplateDocument template = operation.getTemplate();
		UrlBuilder urlBuilder = new UrlBuilder(DefaultDocumentAction.createStateUrl(template, operation.getExecutor().getCurrentState()));
		urlBuilder.addParameters(addEditParameters(template));
		return new ActionForward(urlBuilder.toString(), true);
	}

	private Map<String, String> addEditParameters(TemplateDocument template) throws BusinessException
	{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("id", StringHelper.getEmptyIfNull(template.getId()));

		FormType formType = template.getFormType();
		if (!(FormType.isPaymentSystemPayment(formType) || FormType.JURIDICAL_TRANSFER == formType))
		{
			return parameters;
		}

		PaymentAbilityERL fromResource = template.getChargeOffResourceLink();
		if (fromResource != null)
		{
			parameters.put("fromResource", StringHelper.getEmptyIfNull(fromResource.getCode()));
		}

		if (FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER == formType || FormType.JURIDICAL_TRANSFER == formType)
		{
			parameters.put("field(receiverAccount)",   StringHelper.getEmptyIfNull(template.getReceiverAccount()));
			parameters.put("field(receiverINN)",       StringHelper.getEmptyIfNull(template.getReceiverINN()));
			parameters.put("field(receiverBIC)",       StringHelper.getEmptyIfNull(template.getReceiverBank().getBIC()));
		}

		return parameters;
	}
}