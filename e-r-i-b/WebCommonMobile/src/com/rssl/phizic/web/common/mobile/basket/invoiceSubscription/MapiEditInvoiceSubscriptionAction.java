package com.rssl.phizic.web.common.mobile.basket.invoiceSubscription;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.forms.DocumentContext;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.form.MobilePaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.basket.ViewPaymentsBasketOperation;
import com.rssl.phizic.operations.basket.invoiceSubscription.*;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.common.mobile.payments.forms.EditMobilePaymentAction;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;

/**
 * @author Balovtsev
 * @since 05.11.14.
 */
public class MapiEditInvoiceSubscriptionAction extends EditMobilePaymentAction
{

    private static final String MAKE_AUTO_INVOCE_SUBSCRIPTION_SERVICE = "MakeAutoInvoiceSubscriptionService";
    private static final String PRE_CONFIRM_FORWARD = "preConfirm";
	private static final String SHOW_STATUS = "ShowStatus";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("save",   "save");
		map.put("init",   "start");
		map.put("update", "update");
		map.put("remove", "remove");
		return map;
	}

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditInvoiceSubscriptionClaimOperation operation = createOperation(EditInvoiceSubscriptionClaimOperation.class, FormConstants.EDIT_INVOICE_SUBSCRIPTION_CLAIM);
		MapiEditInvoiceSubscriptionForm form = (MapiEditInvoiceSubscriptionForm) frm;
		operation.initialize(form.getSubscriptionId());
		return operation;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm)form;
		EditInvoiceSubscriptionClaimOperation operation = getOperation(request);
		Form validateForm = operation.getMetadata().getForm();
		FieldValuesSource valuesSource = operation.getFieldValuesSource();
		FieldValuesSource requestSource = getValidateFormFieldValuesSource(frm, operation);

		ValidationStrategy strategy = getValidationStrategy(operation);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new CompositeFieldValuesSource(requestSource, valuesSource), validateForm, strategy);
		if (!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			return forwardShow(operation, frm);
		}
		Map<String, Object> formData = processor.getResult();
		try{
			operation.updateDocument(formData);
		}
		catch (BusinessException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return forwardShow(operation, frm);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return forwardShow(operation, frm);
		}
		try{
			operation.save();
		}
		catch (BusinessException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return forwardDocument(frm, operation);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return forwardDocument(frm, operation);
		}

		resetOperation(request);
		if (operation.getDocument().getId() == null)
		{
			return mapping.findForward(SHOW_STATUS);
		}
		return forwardDocument(frm, operation);
	}

	private ActionForward forwardDocument(CreatePaymentForm form, EditInvoiceSubscriptionClaimOperation operation) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();
		Metadata metadata = operation.getMetadata();

		form.setDocument(document);
		form.setMetadata(metadata);

		DocumentContext documentContext = new DocumentContext(document, metadata);
		MobilePaymentFormBuilder builder = new MobilePaymentFormBuilder(documentContext, new TransformInfo("view", "mobile"), getFormInfo(form));
		FieldValuesSource fieldValuesSource = operation.getFieldValuesSource();
		if(MaskPaymentFieldUtils.isRequireMasking(document))
			fieldValuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(operation, fieldValuesSource), fieldValuesSource);
		Source source =  new FormDataConverter(metadata.getForm(),fieldValuesSource).toFormData();
		form.setHtml(builder.build(source));
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    MapiEditInvoiceSubscriptionForm frm = (MapiEditInvoiceSubscriptionForm) form;

        ViewPaymentsBasketOperation operation = createOperation(ViewPaymentsBasketOperation.class);
        operation.initialize();

        if (operation.isInvoiceActive(frm.getId()))
        {
	        return closeSubscription(frm);
        }
        else
        {
            RemoveInvoiceSubscriptionOperation reoveOperation = createOperation(RemoveInvoiceSubscriptionOperation.class, "PaymentBasketManagment");

            try
            {
                reoveOperation.initialize(frm.getId());
                reoveOperation.remove();
            }
            catch (BusinessLogicException e)
            {
                saveError(request, e);
            }
            catch (Exception e)
            {
                log.error(e.getMessage(), e);
                saveError(request, "Операция временно недоступна.");
            }

            return getCurrentMapping().findForward(FORWARD_START);
        }
	}

	private ActionForward closeSubscription(MapiEditInvoiceSubscriptionForm frm) throws BusinessException, BusinessLogicException
	{
		if (frm.isRecoverAutoSubscription())
		{
			return closeWithRecoverAutoSub(frm);
		}

		CreateInvoiceSubscriptionClaimOperation removeOperation = createOperation(CreateInvoiceSubscriptionClaimOperation.class, "CloseInvoiceSubscriptionClaim");
		removeOperation.initialize(frm.getId(), "CloseInvoiceSubscriptionClaim");

		removeOperation.save();

		//Подготавливаем операцию подтверждения
		ConfirmFormPaymentOperation confirmOperation = createConfirmOperation(removeOperation.getDocument());

		//отправляем запрос на подтверждение
		ConfirmationManager.sendRequest(confirmOperation);
		confirmOperation.getRequest().setPreConfirm(true);
		saveOperation(currentRequest(), confirmOperation);

		preConfirm(confirmOperation);
		updateForm(confirmOperation, frm);

		return getCurrentMapping().findForward(PRE_CONFIRM_FORWARD);
	}

	private ActionForward closeWithRecoverAutoSub(MapiEditInvoiceSubscriptionForm frm)
	{
		try
		{
			CloseInvoiceSubscriptionOperation operation = createOperation("RemoveGeneratedInvoiceSubscriptionOperation", FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM);
			operation.initializeForRecoverAutoSub(frm.getId());
			operation.remove();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(currentRequest(), "Операция временно недоступна.");
		}
		return getCurrentMapping().findForward(FORWARD_START);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MapiEditInvoiceSubscriptionForm frm = (MapiEditInvoiceSubscriptionForm) form;
		UpdateInvoiceSubscriptionOperation operation = createOperation(UpdateInvoiceSubscriptionOperation.class, "PaymentBasketManagment");

		try
		{
			operation.initialize(frm.getId());
			operation.update();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, "Операция временно недоступна.");
		}

		return getCurrentMapping().findForward(FORWARD_START);
	}

    private ConfirmFormPaymentOperation createConfirmOperation(BusinessDocument document) throws BusinessException, BusinessLogicException
   	{
   		try
   		{
   			ExistingSource source = new ExistingSource(document, new IsOwnDocumentValidator());
   			ConfirmFormPaymentOperation operation = createOperation(source);
   			operation.initialize(source);

   			return operation;
   		}
   		catch (NotOwnDocumentException e)
   		{
   			throw new AccessException("У данного пользователя нет прав на просмотр платежа с id=" + document.getId(), e);
   		}
   	}

    private ConfirmFormPaymentOperation createOperation(ExistingSource source) throws BusinessException
   	{
   		if(isMakeAutoInvoiceSub(source.getDocument()))
   		{
   			return createOperation(ConfirmAutoInvoiceSubscriptionOperation.class, MAKE_AUTO_INVOCE_SUBSCRIPTION_SERVICE);
   		}

   		return createOperation(ConfirmInvoiceSubscriptionClaimOperation.class, source.getMetadata().getName());
   	}

    private boolean isMakeAutoInvoiceSub(BusinessDocument document)
   	{
   		return document.getFormName().equals(FormConstants.SERVICE_PAYMENT_FORM) && document.isLongOffer();
   	}

    private void preConfirm(ConfirmFormPaymentOperation operation) throws BusinessLogicException, BusinessException
    {
        ConfirmRequest confirmRequest = operation.getRequest();

        try
        {
            CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
            callBackHandler.setConfirmableObject(operation.getConfirmableObject());
            callBackHandler.setLogin(PersonContext.getPersonDataProvider().getPersonData().getLogin());

            confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(operation.preConfirm(callBackHandler)));

            addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
        }
        catch (SecurityException e)
        {
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(currentRequest(), errors);
        }
        catch (SecurityLogicException e)
        {
            confirmRequest.setErrorMessage(SecurityMessages.translateException(e));
        }
    }

    private void updateForm(ConfirmFormPaymentOperation operation, MapiEditInvoiceSubscriptionForm form) throws BusinessException
    {
        BusinessDocument document = operation.getDocument();

        form.setDocument(document);
        form.setMetadata(operation.getMetadata());
        form.setConfirmStrategy(operation.getConfirmStrategy());
        form.setAnotherStrategyAvailable(operation.isAnotherStrategy());
        // для создания автоподписки отображаем полную форму
        if(isMakeAutoInvoiceSub(document))
            form.setHtml(operation.buildFormHtml(new TransformInfo("view", "html"), getFormInfo(form)));
    }

    private FormInfo getFormInfo(ActionForm form) throws BusinessException
    {
        String webRoot = WebContext.getCurrentRequest().getContextPath();
        String resourceRoot = currentServletContext().getInitParameter("resourcesRealPath");

        return new FormInfo(webRoot, resourceRoot, getSkinUrl(form), isAjax());
    }
}
