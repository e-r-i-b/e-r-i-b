package com.rssl.phizic.web.common.client.cards.delivery;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.auth.passwordcards.PasswordCardNotAvailableException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.documents.CardReportDeliveryClaim;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.payments.InvalidUserIdBusinessException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.UserIdClientTypes;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage;
import com.rssl.phizic.gate.bankroll.ReportDeliveryType;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.operations.card.delivery.EditEmailDeliveryOperation;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 30.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * экшен редактирования подписки
 */

public class EditEmailDeliveryAction extends AsyncOperationalActionBase
{
	private static final String FORM_NAME = CardReportDeliveryClaim.FORM_NAME;

	private static final String START_FORWARD   = "StartForm";
	private static final String CONFIRM_FORWARD = "ConfirmForm";
	private static final String VIEW_FORWARD    = "ViewResult";

	private static final String VIEW_MODE = "view";
	private static final String EDIT_MODE = "edit";

	private static final String USE_PARAMETER_KEY       = "use";
	private static final String EMAIL_PARAMETER_KEY     = "email";
	private static final String TYPE_PARAMETER_KEY      = "type";
	private static final String LANGUAGE_PARAMETER_KEY  = "language";
	public static final String DEFAULT_TYPE     = ReportDeliveryType.HTML.name();
	public static final String DEFAULT_LANGUAGE = ReportDeliveryLanguage.RU.name();

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmSMS",    "changeToSMS");
		map.put("button.confirmCard",   "showCardsConfirm");
		map.put("confirmBySelectedCard","changeToCard");
		map.put("button.confirmCap",    "changeToCap");
		map.put("button.confirmPush",   "changeToPush");
		map.put("button.confirm",       "confirm");
		return map;
	}

	private boolean isShowAdditionalReportDeliveryParameters()
	{
		return CardsUtil.isShowAdditionalReportDeliveryParameters();
	}

	private FieldValuesSource getInitialValueSource(Long cardId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		CardLink cardLink = personData.getCard(cardId);

		boolean useReportDelivery   = cardLink.isUseReportDelivery();
		String emailAddress         = cardLink.getEmailAddress();
		String type                 = StringHelper.getNullIfNull(cardLink.getReportDeliveryType());
		String language             = StringHelper.getNullIfNull(cardLink.getReportDeliveryLanguage());

		if (!useReportDelivery)
		{
			useReportDelivery   = true;
			emailAddress        = personData.getPerson().getEmail();
			type                = DEFAULT_TYPE;
			language            = DEFAULT_LANGUAGE;
		}

		Map<String, String> initialValuesSource = new HashMap<String, String>();
		initialValuesSource.put(USE_PARAMETER_KEY,      Boolean.toString(useReportDelivery));
		initialValuesSource.put(EMAIL_PARAMETER_KEY,    emailAddress);
		initialValuesSource.put(TYPE_PARAMETER_KEY,     type);
		initialValuesSource.put(LANGUAGE_PARAMETER_KEY, language);
		return new MapValuesSource(initialValuesSource);
	}

	private EditEmailDeliveryOperation getEditOperation(EditEmailDeliveryForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditEmailDeliveryOperation operation = createOperation(EditEmailDeliveryOperation.class, FORM_NAME);
		Long cardId = form.getCardId();
		DocumentSource documentSource = new NewDocumentSource(FORM_NAME, getInitialValueSource(cardId), DocumentHelper.getChannelType(), CreationSourceType.ordinary, messageCollector);
		operation.initialize(documentSource, cardId);
		return operation;
	}

	private EditEmailDeliveryOperation getViewOperation(EditEmailDeliveryForm form) throws BusinessException, BusinessLogicException
	{
		EditEmailDeliveryOperation operation = createOperation(EditEmailDeliveryOperation.class, FORM_NAME);
		Long claimId = form.getClaimId();
		DocumentSource documentSource = new ExistingSource(claimId, new IsOwnDocumentValidator());
		operation.initialize(documentSource);
		return operation;
	}

	private EditEmailDeliveryOperation getCurrentOperation(HttpServletRequest request) throws NoActiveOperationException
	{
		//noinspection deprecation
		EditEmailDeliveryOperation operation = getOperation(request);
		OperationContextUtil.synchronizeObjectAndOperationContext(operation.getDocument());
		return operation;
	}

	private void setCurrentOperation(HttpServletRequest request, EditEmailDeliveryOperation operation) throws NoActiveOperationException
	{
		//noinspection deprecation
		saveOperation(request, operation);
	}

	private void updateFormConfirmData(EditEmailDeliveryForm form, EditEmailDeliveryOperation operation)
	{
		form.setConfirmableObject(operation.getConfirmableObject());

		ConfirmStrategy strategy = operation.getConfirmStrategy();
		form.setConfirmStrategy(strategy);
		if(strategy instanceof CompositeConfirmStrategy)
		{
			boolean anatherStrategy =((CompositeConfirmStrategy)strategy).getStrategies().size()>1;
			form.setAnotherStrategyAvailable(anatherStrategy);
		}
		else
		{
			form.setAnotherStrategyAvailable (false);
		}
		form.setDocument(operation.getDocument());
	}

	private void addHTML(EditEmailDeliveryForm form, EditEmailDeliveryOperation operation, FieldValuesSource valuesSource, String mode) throws BusinessException
	{
		form.setHtml(operation.buildFormHtml(valuesSource,getTransformInfo(mode)));
	}

	protected TransformInfo getTransformInfo(String mode)
	{
		return new TransformInfo(mode, "html");
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditEmailDeliveryForm form = (EditEmailDeliveryForm) frm;
		MessageCollector messageCollector = new MessageCollector();
		if (form.getClaimId() != null)
		{
			EditEmailDeliveryOperation operation = getViewOperation(form);
			addHTML(form, operation, operation.getFieldValuesSource(), VIEW_MODE);
			form.setDocument(operation.getDocument());
			saveStateMachineEventMessages(request, operation, true);
			return mapping.findForward(VIEW_FORWARD);
		}

		EditEmailDeliveryOperation operation = getEditOperation(form, messageCollector);
		setCurrentOperation(request, operation);

		ConfirmationManager.sendRequest(operation);

		FieldValuesSource valuesSource = operation.getFieldValuesSource();
		updateFormConfirmData(form, operation);

		addHTML(form, operation, valuesSource, EDIT_MODE);

		addLogParameters(new BeanLogParemetersReader("Данные документа", operation.getDocument()));
		saveStateMachineEventMessages(request, operation, true);

		return mapping.findForward(START_FORWARD);
	}

	private RequestValuesSource getRequestValuesSource(HttpServletRequest request)
	{
		return new RequestValuesSource(request);
	}

	private FieldValuesSource getFormValueSource(EditEmailDeliveryForm form)
	{
		Map<String, Object> valuesSource = new HashMap<String, Object>();
		valuesSource.put(USE_PARAMETER_KEY,         form.isUse());
		valuesSource.put(EMAIL_PARAMETER_KEY,       form.getEmail());

		if (isShowAdditionalReportDeliveryParameters())
		{
			valuesSource.put(TYPE_PARAMETER_KEY,        form.getType());
			valuesSource.put(LANGUAGE_PARAMETER_KEY,    form.getLanguage());
		}
		else
		{
			valuesSource.put(TYPE_PARAMETER_KEY,        DEFAULT_TYPE);
			valuesSource.put(LANGUAGE_PARAMETER_KEY,    DEFAULT_LANGUAGE);
		}
		return new MapValuesSource(valuesSource);
	}

	protected Map<String, Object> checkFormData(EditEmailDeliveryForm form, EditEmailDeliveryOperation operation, ValidationStrategy strategy) throws BusinessException
	{
		Metadata metadata = operation.getMetadata();

		Form metadataForm = metadata.getForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(getFormValueSource(form), metadataForm, strategy);

		if (processor.process())
			return processor.getResult();

		saveErrors(currentRequest(), processor.getErrors());
		return null;
	}

	protected ActionForward errorForwardShow(ActionMapping mapping, EditEmailDeliveryForm form, HttpServletRequest request, EditEmailDeliveryOperation operation) throws BusinessException
	{
		addHTML(form, operation, getFormValueSource(form), EDIT_MODE);

		form.setConfirmStrategy(operation.getConfirmStrategy());

		form.setConfirmableObject(operation.getConfirmableObject());
		form.setDocument(operation.getDocument());

		updateAdditionInfo(operation);
		processWarning(operation, request);
		saveStateMachineEventMessages(request, operation, true);

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(START_FORWARD);
	}

	protected boolean save(EditEmailDeliveryForm form, HttpServletRequest request, EditEmailDeliveryOperation operation) throws BusinessException
	{
		addLogParameters(new BeanLogParemetersReader("Первоначальные данные", operation.getDocument()));
		ValidationStrategy strategy = DocumentValidationStrategy.getInstance();
		Map<String, Object> formData = checkFormData(form, operation, strategy);
		if (formData == null)
			return false;
		operation.resetConfirmStrategy();
		addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", operation.getMetadata().getForm(), formData));
		try
		{
			operation.updateDocument(formData);
			return true;
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return false;
		}
	}

	private void saveConfirmErrors(ConfirmRequest request, List<String> errors)
	{
		ConfirmHelper.saveConfirmErrors(request, errors);
	}

	protected ActionForward preConfirm(ActionMapping mapping, EditEmailDeliveryForm form, HttpServletRequest request, CallBackHandler callBackHandler) throws Exception
	{
		EditEmailDeliveryOperation operation = getCurrentOperation(request);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
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
			saveConfirmErrors(operation.getRequest(), Collections.singletonList(SecurityMessages.translateException(e)));
		}

		return forwardShow(mapping, operation, form, request);
	}

	/* добовляем сообщения пришедшие из confirmRequest, если есть */
	private void updateAdditionInfo(EditEmailDeliveryOperation operation)
	{
		ConfirmRequest confirmRequest = operation.getRequest();
		if (confirmRequest == null)
			return;
		List<String> additionInfo = confirmRequest.getAdditionInfo();
		if (CollectionUtils.isEmpty(additionInfo))
			return;

		for (String info : additionInfo)
			if (!StringHelper.isEmpty(info) && (confirmRequest.getMessages() == null || !confirmRequest.getMessages().contains(info)))
				confirmRequest.addMessage(info);
	}

	private void processWarning(EditEmailDeliveryOperation operation, HttpServletRequest request)
	{
		Exception warning = operation.getWarning();
		if (warning == null || !getErrors(request).isEmpty() || warning instanceof PasswordCardNotAvailableException)
			return;

		if (warning instanceof CompositeException)
		{
			for(Exception exception : ((CompositeException) warning).getExceptions())
				saveError(request, new BusinessLogicException(exception));
		}
		else
		{
			saveError(request, new BusinessLogicException(warning));
		}
	}

	protected ActionForward forwardShow(ActionMapping mapping, EditEmailDeliveryOperation operation, EditEmailDeliveryForm form, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource fieldValuesSource = operation.getFieldValuesSource();

		addHTML(form, operation, fieldValuesSource, VIEW_MODE);

		form.setConfirmStrategy(operation.getConfirmStrategy());

		form.setConfirmableObject(operation.getConfirmableObject());
		form.setDocument(operation.getDocument());

		updateAdditionInfo(operation);
		processWarning(operation, request);
		saveStateMachineEventMessages(request, operation, true);

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(CONFIRM_FORWARD);
	}

	/**
	 * подтверждение через смс
	 * @param mapping   стратс-маппинг
	 * @param frm       стратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return акшен-форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward changeToSMS(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditEmailDeliveryOperation operation = getCurrentOperation(request);
		EditEmailDeliveryForm form = (EditEmailDeliveryForm) frm;

		if (form.isNeedSave() && !save(form, request, operation))
			return errorForwardShow(mapping, form, request, operation);

		operation.setUserStrategyType(ConfirmStrategyType.sms);
		ConfirmationManager.sendRequest(operation);

		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, new CallBackHandlerSmsImpl());
	}

	/**
	 * подтверждение через push
	 * @param mapping   стратс-маппинг
	 * @param frm       стратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return акшен-форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
	public ActionForward changeToPush( ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditEmailDeliveryOperation operation = getCurrentOperation(request);
		EditEmailDeliveryForm form = (EditEmailDeliveryForm) frm;

		if (form.isNeedSave() && !save(form, request, operation))
			return errorForwardShow(mapping, form, request, operation);

		operation.setUserStrategyType(ConfirmStrategyType.push);
		ConfirmationManager.sendRequest(operation);

		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, new CallBackHandlerPushImpl());
	}

	/**
	 * подтверждение через Cap
	 * @param mapping   стратс-маппинг
	 * @param frm       стратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return акшен-форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward changeToCap( ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditEmailDeliveryOperation operation = getCurrentOperation(request);
		EditEmailDeliveryForm form = (EditEmailDeliveryForm) frm;

		if (form.isNeedSave() && !save(form, request, operation))
			return errorForwardShow(mapping, form, request, operation);

		operation.setUserStrategyType(ConfirmStrategyType.cap);
		ConfirmationManager.sendRequest(operation);
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(true);
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.card)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			saveConfirmErrors(confirmRequest, Collections.singletonList(operation.getWarning().getMessage()));
			operation.setWarning(null);
			return preConfirm(mapping, form, request, new CallBackHandlerSmsImpl());
		}

		return forwardShow(mapping, operation, form, request);
	}

	/**
	 * В случае подтверждения по чеку. UserId в запросе для iPasPasswordCardConfirmStrategy
	 * необходимо указывать для выбранной карты клиента, полученный из МБ или из нашей базы.
	 */
	private void addConfirmCardParameter(EditEmailDeliveryForm form, EditEmailDeliveryOperation operation, boolean useStoredValue) throws BusinessException, BusinessLogicException
	{
		String confirmCardNumberId = (String) form.getField("confirmCardId");
		//если передан идентифакатор карты подтверждения, то подтверждать будем по ней.
		if (!StringHelper.isEmpty(confirmCardNumberId))
		{
			BusinessDocument document = (BusinessDocument) operation.getDocument();
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

			//устанавливаем поле "карта подтверждения" для отображения на странице подтверждения
			form.setField("confirmCard", PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(confirmCardNumberId)).getNumber());
		}
	}

	private ConfirmRequest sendChangeToCardRequest(EditEmailDeliveryOperation operation, EditEmailDeliveryForm  form) throws BusinessException, BusinessLogicException
	{
		try
		{
			ConfirmationManager.sendRequest(operation);
		}
		catch (InvalidUserIdBusinessException ignore)
		{
			//в случае если по запросу вернулась ошибка неверного userId полученого по карте из базы.
			//Актуализируеим значение userId через МБ и отправляем запрос снова.
			addConfirmCardParameter(form, operation, false);
			ConfirmationManager.sendRequest(operation);
		}
		finally
		{
			form.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
		}
		return operation.getRequest();
	}

	/**
	 * Отобразить пользователю список карт по чекам с которых может быть подтверждена оперция.
	 * Если в списке только одна карта то список не отображается и переходим на следующий шаг.
	 * @param mapping   стратс-маппинг
	 * @param frm       стратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return акшен-форвард
	 * @throws Exception
	 */
	public ActionForward showCardsConfirm( ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		EditEmailDeliveryOperation operation = getCurrentOperation(request);
		EditEmailDeliveryForm form = (EditEmailDeliveryForm) frm;

		if (form.isNeedSave() && !save(form, request, operation))
			return errorForwardShow(mapping, form, request, operation);

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
		if(result.size() == 1 && form.getField("cardConfirmError") == null)
		{
			form.setField("confirmCardId", result.get(0).getId().toString());
			return changeToCard(mapping, form, request, response);
		}

		operation.setUserStrategyType(ConfirmStrategyType.card);
		operation.getDocument().setConfirmStrategyType(ConfirmStrategyType.card);
		sendChangeToCardRequest(operation, form);

		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(false);
		form.setField("confirmCards", result);
		form.setField("confirmByCard", true);
		return forwardShow(mapping, operation, form, request);
	}

	/**
	 * подтверждение через card
	 * @param mapping   стратс-маппинг
	 * @param frm       стратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return акшен-форвард
	 * @throws Exception
	 */
	public ActionForward changeToCard( ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		EditEmailDeliveryOperation operation = getCurrentOperation(request);
		EditEmailDeliveryForm form = (EditEmailDeliveryForm) frm;

		if (form.isNeedSave() && !save(form, request, operation))
			return errorForwardShow(mapping, form, request, operation);

		ConfirmRequest confirmRequest;
		try
		{
			form.setField("confirmByCard", true);
			addConfirmCardParameter(form, operation,  true);
			operation.setUserStrategyType(ConfirmStrategyType.card);
			operation.getDocument().setConfirmStrategyType(ConfirmStrategyType.card);

			confirmRequest = sendChangeToCardRequest(operation, form);
		}
		catch (BusinessLogicException e)
		{
			form.setField("cardConfirmError", e.getMessage());
			form.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
			return showCardsConfirm(mapping, form, request, response);
		}
		confirmRequest.setPreConfirm(true);

		// Если сменилась стратегия подтверждения из-за ошибки, пишем причину и отправляем СМС-пароль
		// Сейчас фактически других стратегий быть не может. Если появятся, нужно будет уточнять, что с ними делать.
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.cap)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			saveConfirmErrors(confirmRequest, Collections.singletonList(operation.getWarning().getMessage()));
			// Эта ошибка уже обработана, сеттим null, чтобы не выводилась в основном окне.
			operation.setWarning(null);
			return preConfirm(mapping, form, request, new CallBackHandlerSmsImpl());
		}
		//иначе - подтверждение чековым паролем:
		iPasPasswordCardConfirmRequest iPasConfirmRequest = (iPasPasswordCardConfirmRequest) confirmRequest;
		List<String> additionInfo = ConfirmHelper.getPasswordCardConfirmStrategyAdditionalInfo(iPasConfirmRequest.getPasswordsLeft());
		iPasConfirmRequest.setAdditionInfo(additionInfo);
		return forwardShow(mapping, operation, form, request);

	}

	private ActionForward doConfirm(ActionMapping mapping, EditEmailDeliveryOperation operation, EditEmailDeliveryForm form, HttpServletRequest request) throws Exception
	{
		try
		{
			operation.confirm();
			saveStateMachineEventMessages(request, operation, false);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
			addHTML(form, operation, operation.getFieldValuesSource(), VIEW_MODE);
			form.setDocument(operation.getDocument());
			return mapping.findForward(VIEW_FORWARD);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			saveStateMachineEventMessages(request, operation, true);
			return forwardShow(mapping, operation, form, request);
		}
		catch (SecurityLogicException e)
		{
			saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));
			saveStateMachineEventMessages(request, operation, true);
			return forwardShow(mapping, operation, form, request);
		}
	}

	/**
	 * подтверждение операции
	 * @param mapping   стратс-маппинг
	 * @param frm       стратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return акшен-форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward confirm(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditEmailDeliveryOperation operation = getCurrentOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);
		try
		{
			EditEmailDeliveryForm form = (EditEmailDeliveryForm) frm;
			form.setConfirmStrategy(operation.getConfirmStrategy());

			List<String> errors = ConfirmationManager.readResponse(operation, getRequestValuesSource(request));

			if (errors.isEmpty())
				return doConfirm(mapping, operation, form, request);

			saveConfirmErrors(confirmRequest, errors);
			return forwardShow(mapping, operation, form, request);
		}
		catch (TemporalBusinessException ignore)
		{
			String exceptionMessage = getResourceMessage("paymentsBundle", "message.operation.not.available");
			saveConfirmErrors(confirmRequest, Collections.singletonList(exceptionMessage));
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
	}
}
