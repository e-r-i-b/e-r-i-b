package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.auth.passwordcards.PasswordCardNotAvailableException;
import com.rssl.phizic.business.*;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.limits.*;
import com.rssl.phizic.business.ext.sbrf.payments.forms.meta.ESBERIBPaymentsCondition;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.OperationType;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.payments.InvalidUserIdBusinessException;
import com.rssl.phizic.business.payments.forms.meta.conditions.CardsTransferCondition;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.UserIdClientTypes;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;
import com.rssl.phizic.gate.payments.CardRUSPayment;
import com.rssl.phizic.gate.payments.ReIssueCardClaim;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.gate.payments.longoffer.AccountPaymentSystemPaymentLongOfer;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.payment.ConfirmFormOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sim.CheckIMSIOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MoneyHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.DocumentActionBase;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.common.confirm.AutoConfirmRequestType;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import com.rssl.phizic.web.util.MoneyFunctions;
import com.rssl.phizic.web.util.RecipientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.business.limits.OperationType.IMPOSSIBLE_PERFORM_OPERATION;
import static com.rssl.phizic.business.limits.OperationType.NEED_ADDITIONAL_CONFIRN;
import static com.rssl.phizic.messaging.OperationType.PAYMENT_OPERATION;

/**
 * @author Roshka
 * @ created 18.05.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public abstract class ConfirmDocumentActionBase extends DocumentActionBase
{
	protected static final String JUR_PAYMENT_FORWARD = "JurPayment";
	protected static final String CREDIT_REPORT_PAYMENT_FORWARD = "CreditReportPayment";
	protected static final String PAYMENTS = "Payments";
	protected static final String HISTORY = "History";
	protected static final String PAYMENT_VIEW_FORWARD = "PaymentView";
    private static final String INVOICE_FORWARD = "invoiceForward";
	public static final String FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION = "ShowLoanClaimWithoutRegistration";
	protected static final String CHOOSE_SERVICE_URL = "/PhizIC/private/payments/servicesPayments.do?serviceId=%s";

	/**
	 * Документы, для которых не надо делать автоподтверждение.
	 */
	private static final Set<Class> notAutoConfirmForms = new HashSet<Class>();

	static
	{
		notAutoConfirmForms.add(AccountOpeningClaim.class);
	}

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();

	    map.put("button.edit", "edit");
	    map.put("button.dispatch", "confirm");
		map.put("button.preConfirm", "preConfirm");
	    map.put("button.confirmCard", "showCardsConfirm");
	    map.put("confirmBySelectedCard","changeToCard");
	    map.put("button.confirmSMS", "changeToSMS");
	    map.put("button.confirmCap", "changeToCap");
	    map.put("button.confirmPush", "changeToPush");
	    map.put("button.remove", "remove");
	    map.put("button.nextStage", "doNextStage");
        return map;
    }

	protected <T extends Operation> T getOperation(HttpServletRequest request, boolean throwIfNull) throws NoActiveOperationException
	{
		T operation = super.<T>getOperation(request, throwIfNull);
		if (operation instanceof ConfirmableOperation)
		{
			ConfirmableOperation confirmableOperation = (ConfirmableOperation) operation;
			OperationContextUtil.synchronizeObjectAndOperationContext(confirmableOperation.getConfirmableObject());
		}
		return operation;
	}

    protected abstract ConfirmFormPaymentOperation getConfirmOperation(HttpServletRequest request, ConfirmPaymentByFormForm frm) throws BusinessException, BusinessLogicException;

	protected ActionForward autoConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    try
	    {
			ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
			ConfirmFormPaymentOperation operation = getConfirmOperation(request, frm);

			saveOperation(request, operation);

		    BusinessDocument document = operation.getDocument();
		    if (operation.upgrade())
			    return DefaultDocumentAction.createDefaultEditForward(document.getId(), operation.getDocumentSate());
		    // если документ изменился, то сохраняем его
		    if (document.isUpgradable() | showMessageBox(document))
			    operation.save();

		    if (needAutoConfirm(operation, request))
		    {
			    operation.initializeNew();
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

			return forwardSimpleShow(mapping, operation, frm, request, true);
	    }
	    catch (TemporalBusinessException e)
	    {
		    String exceptionMessage = "По техническим причинам операция временно недоступна. Повторите попытку позже";
		    ActionMessage error = new ActionMessage(exceptionMessage, false);
		    saveSessionError(exceptionMessage, error, null);
		    return mapping.findForward(FORWARD_SHOW_FORM);
	    }
    }

	protected boolean needAutoConfirm(ConfirmFormPaymentOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		BusinessDocument document = operation.getDocument();
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		//проверка включения настройки
		if (!securityConfig.getNeedPaymentConfirmAutoselect())
			return false;

		//при наличии ошибок не отображаем
		if(!getErrors(request).isEmpty())
			return false;

		//для некоторых форм платежей отображать окно не надо
		if (notAutoConfirmForms.contains(document.getClass()))
			return false;
		//проверка запрета операции по заградительным лимитам
		if (LimitHelper.needObstructOperation(document))
			return false;

		//проверка запрета операции с помощью стратегии учета лимитов по группам риска
		ClientAccumulateLimitsInfo limitsInfo = operation.getClientAccumulateLimitsInfo();
		GroupRiskDocumentLimitStrategy strategy = new GroupRiskDocumentLimitStrategy(document);
		if (strategy.check(limitsInfo))
			return true;

		return strategy.getCurrentLimit() == null || strategy.getCurrentLimit().getOperationType() != OperationType.IMPOSSIBLE_PERFORM_OPERATION;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    try
	    {
			ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
			ConfirmFormPaymentOperation operation = getConfirmOperation(request, frm);

			saveOperation(request, operation);

			return forwardSimpleShow(mapping, operation, frm, request, true);
	    }
	    catch (TemporalBusinessException e)
	    {
		    String exceptionMessage = "По техническим причинам операция временно недоступна. Повторите попытку позже";
		    ActionMessage error = new ActionMessage(exceptionMessage, false);
		    saveSessionError(exceptionMessage, error, null);
		    return mapping.findForward(FORWARD_SHOW_FORM);
	    }
    }

    /**
     * Переход на forward "ShowForm"
     * @param mapping
     * @param operation
     * @param frm
     * @param request
     * @param sendRequest
     * @return замапленный ActionForward
     * @throws BusinessException
     * @throws BusinessLogicException
     */
    protected ActionForward forwardShow(ActionMapping mapping, ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm frm, HttpServletRequest request, boolean sendRequest, boolean showAllErrors) throws BusinessException, BusinessLogicException
    {
	    updateForm(operation, frm);

	    if(sendRequest)
	        ConfirmationManager.sendRequest(operation);

	    frm.setConfirmStrategy(operation.getConfirmStrategy());
	    frm.setGuest(operation.isGuest());
	    frm.setMobileBankExist(operation.isMobileBankExist());

	    updateAdditionInfo(operation, request);
		if (operation.getWarning() != null)
		{
			// если ошибки уже есть, предупреждения не показываем
			if (getErrors(request).isEmpty())
			{
				Exception warning = operation.getWarning();
				if(warning instanceof CompositeException)
				{
					for(Exception exception : ((CompositeException) warning).getExceptions())
						saveRequestError(request, exception, showAllErrors);
				}
				else
				{
					saveRequestError(request, warning, showAllErrors);
				}
			}
		}

	    // добавляем сообщения на форму
	    saveInfoMessages(request, operation);

	    frm.setConfirmStrategyType(operation.getStrategyType());
	    frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
	    if (operation.getConfirmableObject() instanceof AbstractPaymentDocument)
	    {
		    AbstractPaymentDocument document = (AbstractPaymentDocument) operation.getConfirmableObject();
		    frm.setAnotherStrategyAvailable(operation.isAnotherStrategy() && !document.getSumIncreasedOverLimit());
	    }
	    frm.setConfirmStrategy(operation.getConfirmStrategy());
	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

	    ActionForward forward = mapping.findForward(FORWARD_SHOW_FORM + operation.getMetadata().getName());
	    if (LoanClaimHelper.isUnregisteredClientClaim(frm.getForm()))
		    forward = mapping.findForward(FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION);
	    return forward != null ? forward : mapping.findForward(FORWARD_SHOW_FORM);
    }

	private void saveRequestError(HttpServletRequest request, Exception exception, boolean showAllErrors)
	{
		if (showAllErrors || !(exception instanceof PasswordCardNotAvailableException))
			saveError(request, new BusinessLogicException(exception));
	}

	protected ActionForward forwardSimpleShow(ActionMapping mapping, ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm frm, HttpServletRequest request, boolean sendRequest) throws BusinessException, BusinessLogicException
	{
		return forwardShow(mapping, operation, frm, request, sendRequest, false);
	}

	protected ActionForward forwardShow(ActionMapping mapping, ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm frm, HttpServletRequest request, boolean sendRequest) throws BusinessException, BusinessLogicException
	{
		return forwardShow(mapping, operation, frm, request, sendRequest, true);
	}

	protected void updateForm(ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm frm) throws BusinessException, BusinessLogicException
	{
		Metadata metadata = operation.getMetadata();
		frm.setHtml(buildFormHtml(operation, frm));
		frm.setFormName(metadata.getName());
		frm.setFormDescription(metadata.getForm().getDescription());
		frm.setMetadata(metadata);
		frm.setMetadataPath(operation.getMetadataPath());
		BusinessDocument document = operation.getDocument();
		frm.setDocument(document);
		frm.setStateDescription(document.getState().getDescription());

		if (document instanceof AbstractAccountsTransfer)
		{
			AbstractAccountsTransfer abstractAccountsTransfer = (AbstractAccountsTransfer) document;
			frm.setProviderName(abstractAccountsTransfer.getReceiverName()); // название поставщика нам нужно для вывода в загаловке платежа
			frm.setExternal(operation.getExternalPayment());
			frm.setFns(operation.getFNS());
		}
	}

	protected String buildFormHtml(ConfirmFormPaymentOperation operation, ActionForm form) throws BusinessException
	{
		return operation.buildFormHtml(getTransformInfo(), getFormInfo(form));
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", "html");
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		String webRoot = WebContext.getCurrentRequest().getContextPath();
		String resourceRoot = currentServletContext().getInitParameter("resourcesRealPath");

		return new FormInfo(webRoot, resourceRoot, getSkinUrl(form), isAjax());
	}

	/* добовляем сообщения пришедшие из confirmRequest, если есть */
	private void updateAdditionInfo(ConfirmFormPaymentOperation operation, HttpServletRequest request)
	{
		ConfirmRequest confirmRequest = operation.getRequest();
		if (confirmRequest!=null)
		{
			List<String> additionInfo = confirmRequest.getAdditionInfo();
			if (CollectionUtils.isNotEmpty(additionInfo))
			{
				for (String info : additionInfo)
				{
					if (!StringHelper.isEmpty(info) &&
						(confirmRequest.getMessages() == null || !confirmRequest.getMessages().contains(info)))
					{
						addConfirmMessage(info, request, confirmRequest);
					}
				}
			}
		}
	}


	/** Обработка заполненой формы */
    public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    ConfirmFormPaymentOperation operation = getOperation(request);
	    ConfirmRequest confirmRequest = operation.getRequest();
	    clearConfirmErrors(request, confirmRequest);
	    try
	    {
			ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
			frm.setMetadataPath(operation.getMetadataPath());
		    frm.setMetadata(operation.getMetadata());
			frm.setConfirmStrategyType(operation.getStrategyType());
			ActionForward actionForward;

			List<String> errors = ConfirmationManager.readResponse(operation, getFieldValuesSource(request));

			if (!errors.isEmpty() )
			{
				saveConfirmErrors(errors,request,confirmRequest);
				actionForward = forwardShow(mapping, operation, frm, request, false);
			}
			else
			{
				actionForward = doConfirm(mapping, operation, frm, request, response);
			}

			return actionForward;
		}
		catch (TemporalBusinessException e)
		{
			String exceptionMessage = getResourceMessage("paymentsBundle", "message.operation.not.available");
			saveConfirmErrors(Collections.singletonList(exceptionMessage),request,confirmRequest);
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
    }

	/** Отправить в обработку */
	protected ActionForward doConfirm(ActionMapping mapping, ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    ActionForward forward;
 	    try
        {
            operation.confirm();
            saveStateMachineEventMessages(request, operation, false);
	        if(!isAjax())
	            resetOperation(request);
	        addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
	        forward = createSuccessConfirmForward(mapping, operation, response);
        }
		catch (InactiveExternalSystemException e)
		{
		   	saveInactiveESMessage(request, e);
            saveStateMachineEventMessages(request, operation, true);
			forward = forwardShow(mapping, operation, frm, request, false);
		}
        catch (SecurityLogicException e) // ошибка подтверждения
        {
	        saveConfirmErrors(Collections.singletonList(e.getMessage()),request,operation.getRequest());
            saveStateMachineEventMessages(request, operation, true);
	        //если код CSA_ERR_AGAIN то перерисовываем без forwardShow() // TODO
	        forward = forwardShow(mapping, operation, frm, request, false);
        }
	    catch(TemporalBusinessException ex)
	    {
		    ActionMessages actionErrors = new ActionMessages();
		    actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				    getResourceMessage("paymentsBundle", "message.operation.not.available"), false));
		    saveErrors(request, actionErrors);
            saveStateMachineEventMessages(request, operation, true);
	        forward = forwardShow(mapping, operation, frm, request, false);
	        operation.getDocument().incrementCountError();
	        operation.save();
	    }
	    catch(RedirectBusinessLogicException ex)
	    {
		    saveSessionError(ActionMessages.GLOBAL_MESSAGE,new ActionMessage(ex.getMessage(),false), null);
            saveStateMachineEventMessages(request, operation, true);
			forward = createNextStageDocumentForward(operation, false);
	    }
	    catch (IndicateFormFieldsException e)
	    {
		    // Ошибки и сообщения, для которых должны быть подсвечены поля
		    ActionMessages actionErrors = new ActionMessages();
		    boolean isEmptyFieldMessages = true;
		    for (Map.Entry<String, String> entry : e.getFieldMessages().entrySet())
		    {
			    actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(entry.getKey(), new ActionMessage(entry.getValue(), false)));
			    if (!StringHelper.isEmpty(entry.getValue()) && isEmptyFieldMessages)
			    {
				    isEmptyFieldMessages = false;
			    }
		    }
		    if (e.isError())
		    {
			    saveErrors(request, actionErrors);
		    }
		    else
		    {
			    // Если ни для одного из полей нет сообщения с описанием проблемы, добавляем общее сообщение в
			    // блоке информационных сообщений
			    if (isEmptyFieldMessages)
			    {
				    actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			    }
			    saveMessages(request, actionErrors);
		    }
            saveStateMachineEventMessages(request, operation, true);
		    forward = forwardShow(mapping, operation, frm, request, false);
	    }
	    catch (BusinessLogicWithBusinessDocumentException e)
		{
			// Не показываем popup-окно с предложением ввести пароль
	        operation.getRequest().setPreConfirm(false);

			ActionForward fwd = processDocumentInError(e, request, mapping);
			forward = fwd != null ? fwd : createBadConfirmForward(operation, response);
		}
        catch (BusinessLogicException e) // ошибка логики
        {
	        // Не показываем popup-окно с предложением ввести пароль
	        operation.getRequest().setPreConfirm(false);

	        if (isAjax())
	        {
		        saveSessionError(e.getMessage(), null);
		        updateForm(operation, frm);
		        forward = createBadConfirmForward(operation, response);
	        }
	        else
	        {
				saveError(request, e);
                saveStateMachineEventMessages(request, operation, true);
		        forward = forwardShow(mapping, operation, frm, request, false);
	        }
        }
	    catch (SecurityException e) //упал сервис
	    {
		    saveConfirmErrors(Collections.singletonList("Сервис временно недоступен, попробуйте позже"),request,operation.getRequest());
            saveStateMachineEventMessages(request, operation, true);
		    forward = forwardShow(mapping, operation, frm, request, false);
	        operation.getDocument().incrementCountError();
	        operation.save();
	    }
	    if(operation.getRequest().getStrategyType() == ConfirmStrategyType.card)
			frm.setField("confirmByCard",true);

	    return forward;
    }

	protected ActionForward createSuccessConfirmForward(ActionMapping mapping, ConfirmFormPaymentOperation operation, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		BusinessDocument document = operation.getDocument();
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		ESBERIBPaymentsCondition esbEribCondition = new ESBERIBPaymentsCondition();
		CardsTransferCondition cardsTransferCondition = new CardsTransferCondition();
		String formName = document.getFormName();

        final ActionForward forward = createNextStageDocumentForward(operation, false);
        final String targetUrl = forward.getPath();

        if (document.getState().equals(new State("DELAYED_DISPATCH")))
		{
			String strDate =  DateHelper.toStringTime(document.getAdmissionDate().getTime());
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				StrutsUtils.getMessage("message.operation.delayed.with.date", "paymentsBundle", strDate), false), targetUrl);

			if (formName.equals(FormConstants.IMA_PAYMENT_FORM) || formName.equals(FormConstants.IMA_OPENING_CLAIM))
			{
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						StrutsUtils.getMessage("message.operation.delayed.ima.canceled", "paymentsBundle", strDate), false), targetUrl);
			}

			if(formName.equals(FormConstants.RECALL_DEPOSITARY_CLAIM_FORM))
			{
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					getResourceMessage("paymentsBundle", "message.operation.warning"), false), targetUrl);
			}
		}
		else if (document.getState().equals(new State("WAIT_CONFIRM")))
		{
			if (document instanceof RemoteConnectionUDBOClaim)
			{
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", "payment.limit.exceeded.view.CONNECT_UDBO"), false), targetUrl);
			}
			else
			{
				String message = StringHelper.isEmpty(document.getReasonForAdditionalConfirm()) ? "payment.limit.exceeded.view" : "payment.limit.exceeded.view." + document.getReasonForAdditionalConfirm();

				if (applicationInfo.isMobileApi() || applicationInfo.isATM())
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", message), false), targetUrl);
				else
					saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", message), false), targetUrl);
			}
		}
        else if (document.getState().equals(new State("OFFLINE_DELAYED")))
		{
			Calendar offlineToDate = document.getOfflineDelayedDate();
			String message = null;
			if (offlineToDate != null)
			{
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy в HH:mm");
				String timeString = getResourceMessage("paymentsBundle", "message.operation.offline.delayed.time", format.format(offlineToDate.getTime()));
				message = getResourceMessage("paymentsBundle", "message.operation.offline.delayed", timeString);
			}
			else
				message = getResourceMessage( "paymentsBundle", "message.operation.offline.delayed", "");

			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), targetUrl);
		}
		else if ((formName.equals(FormConstants.ACCOUNT_OPENING_CLAIM_FORM) || (formName.equals(FormConstants.ACCOUNT_OPENING_CLAIM_WITH_CLOSE_FORM)))
				&& document.getState().equals(new State("SAVED")))
		{
			/* Ничего не делаем, все сообщения добавлены ранее. */		
		}
		else if (formName.equals(FormConstants.LOSS_PASSBOOK_APPLICATION))
		{
			//здесь ничего не добавляем
		}
		else if (FormConstants.EXTENDED_LOAN_CLAIM.equals(formName) && PersonContext.getPersonDataProvider().getPersonData().isGuest())
		{
			//здесь ничего не добавляем
		}
		else if(formName.equals(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM) && (document.getState().equals(new State("INITIAL")) || document.getState().equals(new State("SAVED"))))
		{
			/* Ничего не делаем, все сообщения добавлены ранее. */
		}
        else if(applicationInfo.isMobileApi()
        					&& (formName.equals(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM) && document.getState().getCode().equals("EXECUTED")))
        {
                saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        getResourceMessage("paymentsBundle", "message.account-closing-claim.success"), false), targetUrl);
        }
        else if(formName.equals(FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM) && applicationInfo.isMobileApi() && document.getState().getCode().equals("DISPATCHED"))
        {
            return getCurrentMapping().findForward(INVOICE_FORWARD);
        }
		/*
		 * Для онлайн операций выводим универсальную текстовку (все сообщения через шину - онлайн),
		 * кроме операции по открытию вклада.
		 */
		else if (esbEribCondition.accepted(document, operation.getStateMachineEvent()) || cardsTransferCondition.accepted(document, operation.getStateMachineEvent()))
		{
			if("UNKNOW".equals(document.getState().getCode()) || "SENT".equals(document.getState().getCode()))
			{
				if ((document instanceof LongOffer && ((AbstractLongOfferDocument) document).isLongOffer() && FormConstants.SERVICE_PAYMENT_FORM.equals(formName))
						|| (document instanceof AutoSubscriptionPaymentBase))
				{
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						getResourceMessage("paymentsBundle", "message.modify-autosub.success"), false), targetUrl);
				}
				else if (formName.equals(FormConstants.ACCOUNT_OPENING_CLAIM_FORM) || formName.equals(FormConstants.ACCOUNT_OPENING_CLAIM_WITH_CLOSE_FORM)
						|| formName.equals(FormConstants.IMA_OPENING_CLAIM))
				{
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							getResourceMessage("paymentsBundle", "message.claim.success"), false), targetUrl);
				}
				else
				{
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							getResourceMessage("paymentsBundle", "message.jur-payment.success"), false), targetUrl);
				}
			}
			else if (formName.equals(FormConstants.ACCOUNT_OPENING_CLAIM_FORM) && document.getState().getCode().equals("EXECUTED"))
			{
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						getResourceMessage("paymentsBundle", "message.account-opening-claim.success"), false), targetUrl);
			}
			else if (formName.equals(FormConstants.ACCOUNT_OPENING_CLAIM_FORM) && document.getState().getCode().equals("REFUSED"))
			{
				/*Ничего не выводим все сообщения добавлены ранее.*/
			}
			// переводы карта - счет физ.лица и карта - счет физ.лица в другом банке в статусе SAVED
			else if ("SAVED".equals(document.getState().getCode())
					&& (
						(document instanceof RurPayment
							&& (((RurPayment) document).getType() == CardIntraBankPayment.class	|| ((RurPayment) document).getType() == CardRUSPayment.class)
						) || document instanceof IMAOpeningClaim
					)
			)
			{
				/* Ничего не делаем, все сообщения добавлены ранее. */
			}
			else if (document instanceof LongOffer && ((AbstractLongOfferDocument) document).isLongOffer() && FormConstants.SERVICE_PAYMENT_FORM.equals(formName))
			{
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						getResourceMessage("paymentsBundle", "message.long.offer.success"), false), targetUrl);
			}
			else if(document instanceof AutoSubscriptionPaymentBase)
			{
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						getResourceMessage("paymentsBundle", "message.modify-autosub.success"), false), targetUrl);
			}
			else
			{
				//если не было ошибок и документ исполнен
				if (document.getState().getCode().equals("EXECUTED"))
				{
					if (applicationInfo.isMobileApi())
					{
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								getResourceMessage("paymentsBundle", "message.online-operation.success"), false), targetUrl);
					}

					if (formName.equals(FormConstants.LOAN_PAYMENT_FORM)){
						LoanPayment payment = (LoanPayment)document;
						LoanLink link = PersonContext.getPersonDataProvider().getPersonData().getLoan(payment.getLoanLinkId());
						Loan loan = link.getLoan();
						if (loan.getBalanceAmount().getDecimal().equals(BigDecimal.ZERO))
							saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								getResourceMessage("paymentsBundle", "message.payment.loan.close"), false), targetUrl);
					}
				}
			}
		}
		else if (formName.equals(FormConstants.PFR_STATEMENT_CLAIM))
		{
			// Все сообщения операции в одно
			String message = StringUtils.join(operation.getMessageCollector().getMessages(), "\n\n");
			if (!StringHelper.isEmpty(message))
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), targetUrl);
		}
		else if (document instanceof RefuseAutoPayment || document instanceof EditAutoPayment || document instanceof ReIssueCardClaim)
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					getResourceMessage("paymentsBundle", "message.claim.success"), false), targetUrl);
		}
        else if(document instanceof CreateMoneyBoxPayment)
        {
	        saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    getResourceMessage("moneyboxBundle", "moneyBox.create.message.success"), false), targetUrl);
        }
		else if (document instanceof LongOffer && ((AbstractLongOfferDocument) document).isLongOffer())
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					getResourceMessage("paymentsBundle", "message.long.offer.success"), false), targetUrl);
		}
		else if ((document instanceof LoanClaimBase  || document instanceof VirtualCardClaim) && document.getState().getCode().equals("REFUSED"))
		{
			String message = StringUtils.join(operation.getMessageCollector().getMessages(), "\n\n");
			if (!StringHelper.isEmpty(message))
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), targetUrl);
		}
        else if (formName.equals(FormConstants.LOAN_CARD_OFFER_FORM) && ((LoanCardOfferClaim) document).getOfferTypeString() == LoanCardOfferType.newCard)
        {
	        saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			        getResourceMessage("paymentsBundle", "message.loan.card.offer.new.card"), false), targetUrl);
        }
		else if (formName.equals(FormConstants.LOAN_CARD_OFFER_FORM) && ((LoanCardOfferClaim) document).getOfferTypeString() == LoanCardOfferType.changeLimit)
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					getResourceMessage("paymentsBundle", "message.loan.card.offer.change.limit"), false), targetUrl);
		}
		else if (!StringHelper.isEmpty(operation.getMetadata().getForm().getConfirmDescription()))
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					operation.getMetadata().getForm().getConfirmDescription(), false), targetUrl);
		}
        else if (formName.equals(FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM))
		{
			//здесь ничего не добавляем
		}
		if (formName.equals(FormConstants.CHANGE_CREDIT_LIMIT_CLAIM))
		{
			if (document.getCreationType() == CreationType.internet)
				currentRequest().getSession().setAttribute("documentNotice", new DocumentNotice("TICK", StrutsUtils.getMessage("message.change.limit.cancel", "paymentsBundle"), StrutsUtils.getMessage("message.change.limit.title", "paymentsBundle")));
			else
				return new ActionForward(getCurrentMapping().findForward(PAYMENT_VIEW_FORWARD).getPath() + getIdParams(operation), true);
		}
		else
        {
	        if (document instanceof JurPayment)
	        {
	            showChangeOperatorMessage(operation);
	        }

	        if (applicationInfo.isMobileApi())
	        {
		        saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				        getResourceMessage("paymentsBundle", "message.payment.success"), false), targetUrl);
	        }
        }

        return forward;
	}

	/**
	 * Используется для редиректа с окна подтверждения одноразовым паролем, открытым через ajax, на следующий шаг.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		resetOperation(request);
		//Сохранение сообщений
		saveSessionMessages(request, getMessages(request));
		return createSuccessConfirmForward(mapping, operation, response);
	}

	/**
	 * создать форвард на следующую стадию работы с документом
	 * @param operation
	 * @param backToEdit шаг назад к редактированию
     * @return
	 */
	protected ActionForward createNextStageDocumentForward(ConfirmFormPaymentOperation operation, boolean backToEdit) throws BusinessException
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(DefaultDocumentAction.createStateUrl(operation.getDocumentSate()));
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getConfirmableObject().getId()));
        if (backToEdit && (operation.getConfirmableObject() instanceof LoanCardProductClaim))
        {
            LoanCardProductClaim claim = (LoanCardProductClaim) operation.getConfirmableObject();
            String incomeId = claim.getIncome();
            if(StringHelper.isNotEmpty(incomeId))
                urlBuilder.addParameter("income", incomeId);
        }
		return new ActionForward(urlBuilder.toString(), true);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		frm.setMetadataPath(operation.getMetadataPath());
	    frm.setConfirmStrategyType(operation.getStrategyType());
		operation.edit();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return createEditForward(operation, response);
	}

	protected ActionForward createEditForward(ConfirmFormPaymentOperation operation, HttpServletResponse response) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();
		if (document instanceof JurPayment)
		{
			JurPayment payment = (JurPayment) document;
			if (payment.getReceiverInternalId() == null && isServicePayment(payment))
			{
			    return new ActionForward(getCurrentMapping().findForward(JUR_PAYMENT_FORWARD).getPath() + getIdParams(operation), true);
			}
		}
		if (FormConstants.CREDIT_REPORT_PAYMENT.equals(document.getFormName()))
			return new ActionForward(getCurrentMapping().findForward(CREDIT_REPORT_PAYMENT_FORWARD).getPath() + getIdParams(operation), true);

		return createNextStageDocumentForward(operation, true);
	}

	private boolean isServicePayment(JurPayment payment)
	{
		Class<? extends GateDocument> type = payment.getType();
		return AccountPaymentSystemPaymentLongOfer.class == type || AccountPaymentSystemPayment.class == type
				|| CardPaymentSystemPayment.class == type;
	}

	private String getIdParams(ConfirmFormOperation operation)
	{
		return "?id=" + operation.getConfirmableObject().getId();
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		String metadataPath = frm.getMetadataPath();
		if (metadataPath == null)
			return "";
		if(metadataPath.contains("\t"))
		{
			//TODO Подумать, как избавиться от этой ереси!
			//Использовать в хелповом мапинге RegExp? Но XPath их не поддерживает:(
			metadataPath = metadataPath.substring(0, metadataPath.indexOf("\t"));
		}
		String strategyName = null;
		switch(frm.getConfirmStrategyType())
		{
			case card: strategyName = "Card";
				break;
			case crypto: strategyName = "Agava";
				break;
			case sms: strategyName = "Sms";
				break;
			case push: strategyName = "Push";
				break;
			case captcha: strategyName = "Captcha";
				break;
			case none: strategyName = "Dummy";
		}
		boolean isLongOffer = frm.getDocument() instanceof AbstractLongOfferDocument && frm.getDocument().isLongOffer();
		return mapping.getPath() + "/" + metadataPath + "/" + strategyName + (isLongOffer ? "/" + "LongOffer" : "");
	}

	/*
	 */
	public ActionForward preConfirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, CallBackHandler callBackHandler) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmPaymentByFormForm frm   = (ConfirmPaymentByFormForm) form;
        BusinessDocument document = operation.getDocument();

		//При совершении операции всегда проверяется Суточный кумулятивный заградительный лимит по клиенту вне зависимости от групп получателей – в случае превышения операцию выполнить невозможно.
		DocumentLimitStrategy obstructionStrategy = new ObstructionDocumentLimitStrategy(document);
		ActivePerson person = PersonHelper.getContextPerson();
		if (!isGuestPerson()
			&& !obstructionStrategy.check(DocumentLimitManager.buildLimitAmountInfoByPerson(person, LimitHelper.getChannelType(document))))
		{
			//т.к. символ валюты может быть с точкой (например руб.), то нужно заменить двойную точку в конце предложения
			String message = String.format(getResourceMessage("paymentsBundle", "payment.limit.exceeded.general.confirm"), MoneyHelper.formatAmount(obstructionStrategy.getAccumulatedAmount()), MoneyHelper.formatAmount(obstructionStrategy.getCurrentLimit().getAmount())).replace("..", ".");
			saveConfirmErrors(Collections.singletonList(message), request, operation.getRequest());

			return forwardShow(mapping, operation,  frm, request, false);
		}

		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(PersonHelper.getContextPerson().getLogin());

		try
		{
			if (checkAccess(CheckIMSIOperation.class, "CheckIMSIService") && (document.isAlwaysIMSICheck() || LimitHelper.needAdditionalCheck(document)))
			{
				callBackHandler.setOperationType(PAYMENT_OPERATION);
				callBackHandler.setAdditionalCheck();
			}

			PreConfirmObject preConfirmObject = operationPreConfirm(request, callBackHandler);
			ConfirmRequest confirmRequest = operation.getRequest();

			List<String> messages = new ArrayList<String>();
			String preconfirmString = ConfirmHelper.getPreConfirmString(preConfirmObject);
			if(StringHelper.isNotEmpty(preconfirmString))
				messages.add(preconfirmString);
			if (operation.getExternalPayment())
				messages.add(ConfirmHelper.getPreConfirmOZONString());
			String clientInfo = "";
			if (preConfirmObject != null)
				clientInfo = (String) preConfirmObject.getPreConfirmParam(SmsPasswordConfirmStrategy.CLIENT_SEND_MESSAGE_KEY);
			if (!StringHelper.isEmpty(clientInfo))
				messages.add(clientInfo);

			saveConfirmMessages(messages, request, confirmRequest);

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch(BusinessLogicException e)
		{
			saveConfirmErrors(Collections.singletonList(e.getMessage()), request, operation.getRequest());
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			//для блокировки карты и отключении автоплатежа при неактивности внешней системы - текстовка обращения в КЦ
			boolean ccText = e.getCause() instanceof InactiveExternalSystemException;
			ccText = ccText && DocumentHelper.isCallCentreExecutionAvailable(document);

			String text = ccText
					? StrutsUtils.getMessage("message.callCentre", "paymentsBundle")
					: SecurityMessages.translateException(e);
			saveConfirmErrors(Collections.singletonList(text), request, operation.getRequest());
		}

		return forwardShow(mapping, operation, frm, request, false);
	}

	/**
	 * Отобразить пользователю список карт по чекам с которых может быть подтверждена оперция.
	 * Если в списке только одна карта то список не отображается и переходим на следующий шаг.
	 */
	public ActionForward showCardsConfirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		EditDocumentForm frm = (EditDocumentForm) form;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> cardLinks = personData.getCards();
		List<CardLink> result = new ArrayList<CardLink>();
		//фильтр использовать нельзя. проверяем только на то что карта основная.
		for(CardLink link:cardLinks)
		{
			if(link.isMain() && link.isActive())
				result.add(link);
		}
		//если ошибок не было и только одна карта по коорой возможно подтверждение чеком не отображаем формы с выбором карт.
		if(result.size() == 1 && frm.getField("cardConfirmError") == null)
		{
			frm.setField("confirmCardId", result.get(0).getId().toString());
			return changeToCard(mapping, form, request, response);
		}
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(false);
		frm.setField("confirmCards", result);
		frm.setField("confirmByCard",true);
		return forwardShow(mapping, operation, (ConfirmPaymentByFormForm)frm, request, false);
	}

	/**
	 * В случае подтверждения по чеку. UserId в запросе для iPasPasswordCardConfirmStrategy
	 * необходимо указывать для выбранной карты клиента, полученный из МБ или из нашей базы.
	 * @param frm
	 * @param operation
	 * @throws BusinessException
	 */
	private void addConfirmCardParameter(FilterActionForm frm, ConfirmableOperationBase operation, boolean useStoredValue) throws BusinessException, BusinessLogicException
	{
		String confirmCardNumberId = (String) frm.getField("confirmCardId");
		//если передан идентифакатор карты подтверждения, то подтверждать будем по ней.
		if (!StringHelper.isEmpty(confirmCardNumberId))
		{
			BusinessDocument document = (BusinessDocument) operation.getConfirmableObject();
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Pair<UserIdClientTypes, String> userId = ConfirmHelper.getUserIdByConfirmCard(Long.valueOf(confirmCardNumberId), documentOwner.getLogin(), useStoredValue);

			if (operation.getPreConfirm() == null)
			{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("confirmUserId", userId);
				operation.setPreConfirm(new PreConfirmObject(params));
			}
			else
				operation.getPreConfirm().getPreConfimParamMap().put("confirmUserId", userId);

			operation.doPreFraudControl();

			//устанавливаем поле "карта подтверждения" для отображения на странице подтверждения
			frm.setField("confirmCard", PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(confirmCardNumberId)).getNumber());
		}
	}

	public ActionForward changeToCard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		ConfirmRequest confirmRequest;
		try
		{
			frm.setField("confirmByCard",true);
			addConfirmCardParameter(frm, operation, true);
			operation.setUserStrategyType(ConfirmStrategyType.card);
			confirmRequest = sendChangeToCardRequest(operation, frm);
		}
		catch (BusinessLogicException e)
		{
			frm.setField("cardConfirmError", e.getMessage());
			frm.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
			return showCardsConfirm(mapping, frm, request, response);
		}
		confirmRequest.setPreConfirm(true);

		// Если сменилась стратегия подтверждения из-за ошибки, пишем причину и отправляем СМС-пароль
		// Сейчас фактически других стратегий быть не может. Если появятся, нужно будет уточнять, что с ними делать.
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.cap || currentType == ConfirmStrategyType.push)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			saveConfirmErrors(Collections.singletonList(operation.getWarning().getMessage()), request, confirmRequest);
			// Эта ошибка уже обработана, сеттим null, чтобы не выводилась в основном окне.
			operation.setWarning(null);
	        return preConfirm(mapping, frm, request, response, new CallBackHandlerSmsImpl());
		}

		//иначе - подтверждение чековым паролем:
        iPasPasswordCardConfirmRequest ippccr = (iPasPasswordCardConfirmRequest) confirmRequest;
		List<String> additionInfo = ConfirmHelper.getPasswordCardConfirmStrategyAdditionalInfo(ippccr.getPasswordsLeft());

		if (operation.getExternalPayment())
			additionInfo.add(ConfirmHelper.getPreConfirmOZONString());

		ippccr.setAdditionInfo(additionInfo);
		return forwardShow(mapping, operation, frm, request, false);

	}

	public ActionForward changeToCap( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		ConfirmationManager.sendRequest(operation);
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(true);
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.card)
		{
			saveConfirmErrors(Collections.singletonList(operation.getWarning().getMessage()), request, confirmRequest);
			operation.setWarning(null);
	        return preConfirm(mapping, frm, request, response, new CallBackHandlerSmsImpl());
		}
		List<String> messages = new ArrayList<String>();
		
		if (operation.getExternalPayment())
				messages.add(ConfirmHelper.getPreConfirmOZONString());
		saveConfirmMessages(messages, request, confirmRequest);

		return forwardShow(mapping, operation, frm, request, false);

	}

	public ActionForward changeToSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, response, new CallBackHandlerSmsImpl());
	}

	public ActionForward changeToPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.push);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, response, new CallBackHandlerPushImpl());
	}


	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		RemoveDocumentOperation removeOperation = createOperation(RemoveDocumentOperation.class, operation.getMetadata().getName());
		removeOperation.initialize(operation.getDocument());
		removeOperation.remove();
		return createRemoveForward(mapping, form, request, removeOperation);

	}

	protected FieldValuesSource getFieldValuesSource(HttpServletRequest request)
	{
		return new RequestValuesSource(request);
	}

	protected PreConfirmObject operationPreConfirm(HttpServletRequest request, CallBackHandler callBackHandler) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		// todo confirm guest claim generate
		return operation.preConfirm(callBackHandler);
	}

	protected void saveConfirmErrors(List<String> errors, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
	}
	
	protected void clearConfirmErrors(HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ConfirmHelper.clearConfirmErrors(confirmRequest);
	}

	protected void saveConfirmMessages(List<String> messages, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		for(String message : messages)
			confirmRequest.addMessage(message);
	}

	protected void addConfirmMessage(String message, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		confirmRequest.addMessage(message);
	}

	/**
	 * Добавление текстовых сообщений на форму
	 * @param request запрос
	 * @param operation операция
	 */
	protected void saveInfoMessages(HttpServletRequest request, ConfirmFormPaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		BusinessDocument document = operation.getDocument();

		// сообщения из хендлеров
		saveStateMachineEventMessages(request, operation, true);
		// сообщения по лимитам
		addDocumentLimitsInfoMessages(operation, request);

		// в случае порогового автоплатежа сообщение о максимальной сумме
		if(document instanceof AutoPaymentBase)
		{
			AutoPaymentBase autoPayment = (AutoPaymentBase) document;

			if(autoPayment.getExecutionEventType() == ExecutionEventType.REDUSE_OF_BALANCE
					&& BooleanUtils.isTrue(autoPayment.isSupportTotalAmount())
					&& autoPayment.getClientTotalAmountLimit() == null)
			{
				try
				{
					BillingServiceProvider provider = (BillingServiceProvider) autoPayment.getServiceProvider();
					ThresholdAutoPayScheme thresholdScheme = provider.getThresholdAutoPayScheme();
					// если настройки поставщика позволяют
					if(thresholdScheme != null && thresholdScheme.isAccessTotalMaxSum() && thresholdScheme.getTotalMaxSum() != null)
					{
						String period = StrutsUtils.getMessage("autopay.period.max.summa." + thresholdScheme.getPeriodMaxSum(), "providerBundle");
						saveMessage(request, StrutsUtils.getMessage("message.autopay.threshold.total.max.sum.template", "paymentsBundle", period, thresholdScheme.getTotalMaxSum()));
					}
				}
				catch (DocumentException e)
				{
					throw new BusinessException(e);
				}
			}
		}

		saveMessages(request, operation.collectInfo());
	}

	protected void addDocumentLimitsInfoMessages(ConfirmFormPaymentOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		if (isGuestPerson())
			return;
		BusinessDocument document = operation.getDocument();
		ClientAccumulateLimitsInfo limitsInfo = operation.getClientAccumulateLimitsInfo();
		//в первую очередь проверяем не превысили ли мы общий суточный лимит, если превысили, то такая операция отклоняется в дальнейшем
		DocumentLimitStrategy obstructionStrategy = new ObstructionDocumentLimitStrategy(document);
		DocumentLimitStrategy recipientLimitStrategy = getRecipientLimitStrategy(document);

		// если установлен суточный кумулятивный лимит
		if (!new OverallAmountPerDayDocumentLimitStrategy(document).check(limitsInfo))
		{
			saveError(request, getResourceMessage("paymentsBundle", "payment.limit.exceeded.view.OVERALL_AMOUNT_PER_DAY"));
		}
		//если запрещена оплата/перевод по справочнику доверенных получателей в Light-схеме mAPI
		else if (!new MobileLightPlusLimitStrategy(document).check(limitsInfo))
		{
			saveError(request, getResourceMessage("paymentsBundle", "payment.limit.need.additional.confirm." + RestrictionType.AMOUNT_IN_DAY));
		}
		//если превысили сумму мобильного кошелька то такая операция в дальнейшем отклоняется
		else if (!obstructionStrategy.check(limitsInfo))
		{
			//т.к. символ валюты может быть с точкой (например руб.), то нужно заменить двойную точку в конце предложения
			String message = String.format(getResourceMessage("paymentsBundle", "payment.limit.exceeded.general.confirm"), MoneyHelper.formatAmount(obstructionStrategy.getAccumulatedAmount()), MoneyHelper.formatAmount(obstructionStrategy.getCurrentLimit().getAmount())).replace("..", ".");
			saveError(request, message);
		}
		else if (recipientLimitStrategy != null && !recipientLimitStrategy.check(limitsInfo))
		{
			addRecipientLimitMessages(recipientLimitStrategy, request);
		}
		else
		{
			//далее проверяем лимит по группе риска
			DocumentLimitStrategy groupRiskLimitStrategy = new GroupRiskDocumentLimitStrategy(document);
			if (!groupRiskLimitStrategy.check(limitsInfo) && groupRiskLimitStrategy.getCurrentLimit() != null)
			{
				Limit limit = groupRiskLimitStrategy.getCurrentLimit();
				if (NEED_ADDITIONAL_CONFIRN == limit.getOperationType())
				{
					//если необходимо доп. подтверждение
					ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
					if (applicationInfo.isMobileApi() || applicationInfo.isATM() || applicationInfo.isSocialApi())
					{
						saveMessage(request, getResourceMessage("paymentsBundle", "payment.limit.need.additional.confirm." + limit.getRestrictionType().name()));
					}
					else
					{
						saveErrors(request, Collections.singletonList(getResourceMessage("paymentsBundle", "payment.limit.need.additional.confirm." + limit.getRestrictionType().name())));
					}
				}
				if (IMPOSSIBLE_PERFORM_OPERATION == limit.getOperationType())
				{
					//если операцию выполнить нельзя
					if (RestrictionType.AMOUNT_IN_DAY == limit.getRestrictionType())
					{
						//т.к. символ валюты может быть с точкой (например руб.), то нужно заменить двойную точку в конце предложения
						String message = String.format(getResourceMessage("paymentsBundle", "payment.limit.impossible.perform.confirm.AMOUNT_IN_DAY"), MoneyHelper.formatAmount(groupRiskLimitStrategy.getAccumulatedAmount()), MoneyHelper.formatAmount(limit.getAmount())).replace("..", ".");
						saveError(request, message);
					}
                    else
                    {
					    saveErrors(request, Collections.singletonList(getResourceMessage("paymentsBundle", "payment.limit.impossible.perform.confirm." + limit.getRestrictionType().name())));
                    }
				}
			}
			else
			{
				//если уложились и в заградительный лимит, и в лимит по группе риска
				if (obstructionStrategy.getAvailableAmount() != null && groupRiskLimitStrategy.getAvailableAmount() != null)
				{
					BigDecimal groupRiskAvailableAmount = groupRiskLimitStrategy.getAvailableAmount().getDecimal();
					BigDecimal obstructionAvailableAmount = obstructionStrategy.getAvailableAmount().getDecimal();

					String defaultMessage = getDefaultAvailableLimitAmountMessage(groupRiskAvailableAmount, obstructionAvailableAmount);
					if(StringHelper.isNotEmpty(defaultMessage))
						saveMessage(request, defaultMessage);
				}
			}
		}
		if (document instanceof RurPayment)
		{
			RurPayment payment = (RurPayment) document;
			// сработало ограничение на вывод информации о получателе при переводе по номеру телефора
			if(BooleanUtils.isTrue(payment.hasRestrictReceiverInfoByPhone()))
			{
				saveMessage(request, StrutsUtils.getMessage("com.rssl.phizic.web.client.payments.service.payment.limit.request.receiver.info", "paymentsBundle"));
			}
		}
	}

	protected DocumentLimitStrategy getRecipientLimitStrategy(BusinessDocument document) throws BusinessLogicException, BusinessException
	{
		return null;
	}

	protected void addRecipientLimitMessages(DocumentLimitStrategy recipientLimitStrategy, HttpServletRequest request)
	{
		//пока что здесь не предусмотрено лимитов на получателя
	}

	protected String getDefaultAvailableLimitAmountMessage(BigDecimal groupRiskAvailableAmount, BigDecimal obstructionAvailableAmount)
	{
		if (obstructionAvailableAmount.compareTo(groupRiskAvailableAmount) < 1)
		{
		 	return String.format(getResourceMessage("paymentsBundle", "payment.limit.obstruct.confirm.info"), MoneyFunctions.formatAmount(obstructionAvailableAmount));
		}
		return String.format(getResourceMessage("paymentsBundle", "payment.limit.confirm.info"), MoneyFunctions.formatAmount(groupRiskAvailableAmount), MoneyFunctions.formatAmount(obstructionAvailableAmount));
	}

	protected ActionForward createBadConfirmForward(ConfirmFormPaymentOperation operation, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		return null;
	}

	private ConfirmRequest sendChangeToCardRequest(ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm  frm) throws BusinessException, BusinessLogicException
	{
		try
		{
			ConfirmationManager.sendRequest(operation);
		}
		catch (InvalidUserIdBusinessException ignore)
		{
			//в случае если по запросу вернулась ошибка неверного userId полученого по карте из базы.
			//Актуализируеим значение userId через МБ и отправляем запрос снова.
			addConfirmCardParameter(frm, operation, false);
			ConfirmationManager.sendRequest(operation);
		}
		finally
		{
			frm.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.	
		}
		return operation.getRequest();
	}

	/**
	 * Передача предупреждения о смене мобильного оператора через сессию
	 * @param operation - операция
	 */
	private void showChangeOperatorMessage(ConfirmFormPaymentOperation operation)
	{
		JurPayment payment = (JurPayment)operation.getDocument();
		String externalId = operation.getProviderExternalIdByInternalId(payment.getReceiverInternalId());

		boolean byTemplate = payment.isByTemplate();
		boolean needShowMessage = RecipientUtil.needShowChangeProviderMessage(byTemplate, externalId);

		if (needShowMessage)
		{
			ProvidersConfig providerConfig = ConfigFactory.getConfig(ProvidersConfig.class);
			ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
			StringBuilder messageKey = new StringBuilder("message.mobile.operator.change.");
			messageKey.append(byTemplate).append(".");
			if (applicationInfo.isMobileApi())
				messageKey.append("mobile");
			else if (applicationInfo.isATM())
				messageKey.append("atm");
			else
				messageKey.append("client");

			String message = String.format(getResourceMessage("paymentsBundle", messageKey.toString()),
					String.format(CHOOSE_SERVICE_URL, providerConfig.getMobileServiceId()));
			saveAdditionalMessage(WebContext.getCurrentRequest(), message);
		}
	}
	protected ActionForward createRemoveForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, RemoveDocumentOperation operation)
	{
		if (!StringHelper.isEmpty(request.getParameter("history")))
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", "com.rssl.phizic.web.client.payments.remove.payment"), false), null);
			return mapping.findForward(HISTORY);
		}
		else
		{
			return mapping.findForward(PAYMENTS);
		}
	}

	private boolean isGuestPerson()
	{
		return PersonContext.getPersonDataProvider().getPersonData().isGuest();
	}
}
