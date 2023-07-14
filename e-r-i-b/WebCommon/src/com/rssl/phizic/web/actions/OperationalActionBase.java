package com.rssl.phizic.web.actions;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.state.FieldValueChange;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.CompositeInactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.WebPageConfig;
import com.rssl.phizic.web.common.messages.MessageConfigRouter;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.web.struts.forms.FormHelper;
import com.rssl.phizic.web.util.HttpSessionUtils;
import com.rssl.phizic.web.util.TokenStack;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.taglib.html.Constants;

import java.security.AccessControlException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.web.actions.ActionMessageHelper.SESSION_MESSAGES_KEY;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 15:00:52
 */
public abstract class OperationalActionBase extends LookupDispatchAction
{
    //actionForwards
    protected static final String FORWARD_START = "Start";
    protected static final String FORWARD_SHOW = "Show";
    protected static final String FORWARD_SHOW_FORM = "ShowForm";

	//session messages & errors
	private static final String SESSION_INACTIVE_ES_MESSAGE_KEY = "com.rssl.phizic.web.actions.SESSION_INACTIVE_ES_MESSAGE_KEY";   // недоступность внешней системы
	protected static final String SESSION_CHANGED_FIELDS_KEY = "com.rssl.phizic.web.actions.CHANGED_FIELDS"; //ключ для атрибута сессии, хранящего Set названий изменившихся полей
	protected static final String SESSION_ADDITIONAL_MESSAGE_KEY = "com.rssl.phizic.web.actions.SESSION_ADDITIONAL_MESSAGE_KEY";   //смена мобильного оператора

    //session tokens
	protected static final String SESSION_ADDITIONAL_TOKEN_KEY = "com.rssl.phizic.web.actions.SESSION_ADDITIONAL_TOKEN_KEY";   //теперь запоминается два токена

	private static final MessageConfig messageConfig = MessageConfigRouter.getInstance();

	private OperationFactory operationFactory = new StrutsOperationFactory(messageConfig);
	private ThreadLocal<ActionMapping> mapping = new ThreadLocal<ActionMapping>();

	protected static WebPageConfig webPageConfig()
	{
		return ConfigFactory.getConfig(WebPageConfig.class);
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

	    ActionForward actionForward;
	    try
	    {
            setCurrentMapping(mapping);
		    writeSessionMessages(request);
		    writeSessionErrors(request);
		    writeSessionInactiveESMessage(request);

		    // сохраняем для базовой формы общие признаки
		    if(form instanceof ActionFormBase)
		    {
			    ActionFormBase formBase = (ActionFormBase) form;

			    String parameter = getParameter(mapping, form, request, response);
                String methodName = getMethodName(mapping, form, request, response, parameter);
			    // устанавливаем признак выполнения дефолтного метода
			    formBase.setFromStart(getDefaultMethodName().equals(methodName));
			     // сохраняем на форме версию mAPI
			    if(ApplicationUtil.isMobileApi())
			    {
				    VersionNumber apiVersionNumber = MobileApiUtil.getApiVersionNumber();
				    if(apiVersionNumber != null)
				        formBase.setMobileApiVersion(apiVersionNumber.toString());
			    }
		    }

            actionForward = super.execute(mapping, form, request, response);
		    String forceRedirectUrl = request.getParameter("$$forceRedirect");

		    if(forceRedirectUrl != null && forceRedirectUrl.length() != 0)
		    {
			    actionForward = processForceRedirect(forceRedirectUrl, request, actionForward);
		    }

		    /*
		     *
		     * Отображаем пользователю сообщения сохраненные в сессии если остались на той же странице на
		     * которой были
		     *
		     */
		    if (actionForward != null && !actionForward.getRedirect())
		    {
			   writeSessionMessages(request);
		    }
	    }
	    finally
	    {
		    setCurrentMapping(null);
	    }

		return actionForward;
	}

	protected ActionForward processForceRedirect(String forceRedirectUrl, HttpServletRequest request, ActionForward originalForward) throws ServletException
	{

		// Продолжим обработку в случае ошибок
		if(!getErrors(request).isEmpty())
			return originalForward;

		String newUrl;
		if(forceRedirectUrl.indexOf("$$newId") > 0)
		{
			Object newId = request.getAttribute("$$newId");
			if(newId == null)
			    throw new ServletException("Параметр $$newId для $$forceRedirect не определен");
			else
			    newUrl = forceRedirectUrl.replace("$$newId", newId.toString());
		}
		else
		{
			newUrl = forceRedirectUrl;
		}

		return new ActionForward(newUrl, true);
	}

    /**
     * Перекладывает сообщения из session в request.
     * Если текущий URL не является получателем данных сообщений, то сообщения из session удаляются, но в request не добавляются.
     * Если получатель не задан, то считается, что сообщение адресовано любому прочитавшему (в том числе и текущему action-у).
     * @param request Реквест
     */
    private void writeSessionMessages(HttpServletRequest request)
	{
        String sessionMessagesTarget = HttpSessionUtils.removeSessionAttribute(request, ActionMessagesKeys.sessionNotificationsTarget.getKey());

	    ActionMessages sessionMessages = getSessionMessages(request);
	    if (sessionMessages == null)
	        return;

	    // clear sessionMessages
		HttpSessionUtils.removeSessionAttribute(request, SESSION_MESSAGES_KEY);

        if (StringHelper.isEmpty(sessionMessagesTarget) || sessionMessagesTarget.contains(request.getServletPath()))
	        saveMessages(request, sessionMessages);
	}

    /**
     * Перекладывает ошибки из session в request.
     * Если текущий URL не является получателем данных сообщений, то сообщения из session удаляются, но в request не добавляются.
     * Если получатель не задан, то считается, что сообщение адресовано любому прочитавшему (в том числе и текущему action-у).
     * @param request Реквест
     */
	private void writeSessionErrors(HttpServletRequest request)
	{
        String sessionErrorsTarget = HttpSessionUtils.removeSessionAttribute(request, ActionMessagesKeys.sessionNotificationsTarget.getKey());

		ActionMessages sessionErrors = getSessionErrors(request);
		if (sessionErrors == null)
			return;

		// clear sessionErrors
		HttpSessionUtils.removeSessionAttribute(request, ActionMessagesKeys.sessionError.getKey());

        if (StringHelper.isEmpty(sessionErrorsTarget) || sessionErrorsTarget.contains(request.getServletPath()))
		    saveErrors(request, sessionErrors);
	}

	/**
	 * Запрись из сессии в реквест сообщений о недоступности внешней системы
	 * @param request реквест выполняемой опреации
	 */
	private void writeSessionInactiveESMessage(HttpServletRequest request)
	{
	    ActionMessages sessionMessages = getSessionInactiveESMessage(request);
	    if(sessionMessages == null)
	        return;
	    // clear sessionMessages
		HttpSessionUtils.removeSessionAttribute(request, SESSION_INACTIVE_ES_MESSAGE_KEY);
	    saveInactiveESMessage(request, sessionMessages);
	}

	/**
     * @return фабрика операций
     */
    public OperationFactory getOperationFactory()
    {
        return operationFactory;
    }

    /**
     * Устанавливает активную операцию
     * @param request
     * @param operation
     */
    @Deprecated
    protected void saveOperation(HttpServletRequest request, Operation operation)
    {
	    Store store = getStore();
	    synchronized (store)
        {
	        saveToken(request);
	        store.save(getToken(request), operation);

            // запоминаем предыдущий токен
            TokenStack tokenStack = (TokenStack) store.restore(SESSION_ADDITIONAL_TOKEN_KEY);
            if (tokenStack == null)
	            tokenStack = new TokenStack();
	        tokenStack.push(getToken(request));
            store.save(SESSION_ADDITIONAL_TOKEN_KEY, tokenStack);

            request.setAttribute(Constants.TOKEN_KEY, operation);
        }
    }

    /**
     * @param request
     * @return возвращает активную операцию
     */
    @Deprecated
    protected <T extends Operation> T getOperation(HttpServletRequest request) throws NoActiveOperationException
    {
	    //noinspection RedundantTypeArguments
	    return this.<T>getOperation(request, true);
    }

    /**
     * Возвращает активную операцию
     * @param request
     * @param throwIfNull бросать ли исключение если активной операции нет
     * @return
     * @throws NoActiveOperationException
     */
    @Deprecated
    protected < T extends Operation> T getOperation(HttpServletRequest request, boolean throwIfNull) throws NoActiveOperationException
    {
	    Store store = getStore();
        synchronized (store)
        {
            T operation = (T) request.getAttribute(Constants.TOKEN_KEY);
            if (operation != null)
                return operation;

            if(this.isTokenValid(request, false))
            {
                operation = (T) store.restore(getToken(request));
            }

	        if (operation == null)
	        {
	            // пытаемся получить предыдущую операцию
	            TokenStack tokenStack = (TokenStack) store.restore(SESSION_ADDITIONAL_TOKEN_KEY);
	            if (operation == null && tokenStack != null)
	            {
		            String token = tokenStack.getPreLast();
		            while (StringHelper.isNotEmpty(token))
		            {
			            store.save(Globals.TRANSACTION_TOKEN_KEY, token);
			            if(this.isTokenValid(request, false))
						{
					        operation = (T) store.restore(getToken(request));
							if (operation != null)
								break;
						}
			            token = tokenStack.getPreLast();
		            }
	            }
	        }

            if( throwIfNull && operation == null )
                throw new NoActiveOperationException("Нет активной операции!");

            return operation;
        }
    }

    /**
     * Завершает активную операцию
     * @param request
     */
    @Deprecated
    protected void resetOperation(HttpServletRequest request)
    {
	    Store store = getStore();
        synchronized (store)
        {
            if (!this.isTokenValid(request, false))
                return;

            store.save(getToken(request), null);

            // сбрасываем и предыдущие токены
            store.remove(SESSION_ADDITIONAL_TOKEN_KEY);

            resetToken(request);
            request.removeAttribute(Constants.TOKEN_KEY);
        }
    }


    private Store getStore()
    {
        return StoreManager.getCurrentStore();
    }

    /**
     * @param request
     * @return возвращает текущий токен или null если активного токенв нет
     */
    private String getToken(HttpServletRequest request)
    {
        return HttpSessionUtils.getSessionAttribute(request, Globals.TRANSACTION_TOKEN_KEY);
    }

	public <T extends Operation> T createOperation(Class<T> operationClass) throws AccessControlException, BusinessException
	{
		return operationFactory.create(operationClass);
	}

	public <T extends Operation> T createOperation(String key) throws AccessControlException, BusinessException
	{
		return operationFactory.<T>create(key);
	}

	public <T extends Operation> T createOperation(Class<T> operationClass, String serviceKey) throws AccessControlException, BusinessException
	{
		return operationFactory.create(operationClass, serviceKey);
	}

	public <T extends Operation> T createOperation(String key, String ServiceKey) throws BusinessException
	{
		return operationFactory.<T>create(key, ServiceKey);
	}

	public boolean checkAccess(Class operationClass) throws BusinessException
	{
		return operationFactory.checkAccess(operationClass);
	}

	public boolean checkAccess(String operationKey) throws BusinessException
	{
		return operationFactory.checkAccess(operationKey);
	}

	public boolean checkAccess(Class operationClass, String serviceKey) throws AccessControlException, BusinessException
	{
		return operationFactory.checkAccess(operationClass, serviceKey);
	}

	/**
	 *
	 * Сохраняем сообщения пришедшие из хэндлеров в сессию
	 *
	 * @param request
     * @param operation операция содержащая сообщения
     * @param toRequest Куда сохранять имена изменившихся полей: true - в риквест, false - в сессию
     */
	protected void saveStateMachineEventMessages(HttpServletRequest request, Operation<?> operation, boolean toRequest)
	{
		StateMachineEvent  stateMachineEvent = operation.getStateMachineEvent();
		Collection<String> collectorMessages = stateMachineEvent.getMessageCollector().getMessages();
		Collection<String> collectorErrorMessages = stateMachineEvent.getMessageCollector().getErrors();

		ActionMessages msgs = new ActionMessages();

		//в мобильном API все изменившиеся поля кладем в errors, а также в сессию (для дальнейшей простановки признака changed для полей формы в mobile.xslt).
		if(ApplicationUtil.isApi())
		{
			Set<String> changedFields = new HashSet<String>();
            for (FieldValueChange fieldValueChange : stateMachineEvent.getChangedFields())
            {
                String fieldMessage = StringHelper.isNotEmpty(fieldValueChange.getFieldMessage()) ? fieldValueChange.getFieldMessage() : "Значение данного поля изменилось. При необходимости Вы можете его отредактировать.";
                if(toRequest)
                    saveError(request, new ActionMessage(fieldValueChange.getFieldName(), new ActionMessage(fieldMessage, false)));
                else
                    saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(fieldValueChange.getFieldName(), new ActionMessage(fieldMessage, false)), null);
                changedFields.add(fieldValueChange.getFieldName());
            }
            request.getSession().setAttribute(SESSION_CHANGED_FIELDS_KEY, changedFields);
        }
		else //в основном приложении все изменившиеся поля кладем в messages
        {
            for (FieldValueChange fieldValueChange : stateMachineEvent.getChangedFields())
            {
                String fieldMessage = StringHelper.getEmptyIfNull(fieldValueChange.getFieldMessage());
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(fieldValueChange.getFieldName(), new ActionMessage(fieldMessage, false)));
            }
        }

        //после того, как сообщения сохранены, список изменившихся полей нужно очистить
        stateMachineEvent.clearChangedFields();

		// Добавляем информацилнные сообщения
		if (!collectorMessages.isEmpty())
		{
			for (String message : collectorMessages)
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			}
		}
		if (!msgs.isEmpty())
		{
			saveSessionMessages(request, msgs);
		}

		// Добавляем сообщения об ошибках
		ActionMessages errs = new ActionMessages();
		if (!collectorErrorMessages.isEmpty())
		{
			for (String message : collectorErrorMessages)
			{
				errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			}
		}
		stateMachineEvent.getMessageCollector().clear();
		if (!errs.isEmpty())
		{
			saveSessionErrors(request, errs);
		}

	}
	/**
     * Сохранить сообщение для пользователя.
     * @param property - банд для пропертей где находится тело сообщения
     * @param messageKey - название свойства сообщения
     * @param request
     */
    protected void saveError(String property, String messageKey, HttpServletRequest request)
    {
        ActionMessages msgs = new ActionMessages();
        msgs.add(property, new ActionMessage(messageKey));
        saveErrors(request, msgs);
    }

	protected void saveError(HttpServletRequest request, Exception e)
	{
		ActionMessageHelper.saveError(request, e);
	}

	protected void saveError(HttpServletRequest request, ActionMessage message)
	{
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, message);

		saveErrors(request, errors);
	}
    
    protected void saveError(HttpServletRequest request, String message)
	{
		saveError(request, new ActionMessage(message, false));
	}

	protected void saveMessage(HttpServletRequest request, String message)
	{
		ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		saveMessages(request, actionMessages);
	}

	protected void saveMessages(HttpServletRequest request, Collection<String> messages)
	{
		ActionMessageHelper.saveMessages(request, messages);
	}

	protected void saveErrors(HttpServletRequest request, List<String> errors)
	{
		ActionMessageHelper.saveErrors(request, errors);
    }

	/**
	 *
	 * Групповое сохранение сообщений в сессию
	 *
	 * @param request
	 * @param messages
	 */
	protected void saveSessionMessages(HttpServletRequest request, ActionMessages messages)
	{
		ActionMessageHelper.saveSessionMessages(request, messages);
	}

    /**
     * Сохранение сообщения в сессию
     * @param property ключ сообщения
     * @param message сообщение
     * @param targetUrl URL адресата сообщения. Если null или пустая строка, то любому (первому прочитавшему).
     *  Если адресат уже задан, то он будет перезаписан.
     */
    protected void saveSessionMessage(String property, ActionMessage message, String targetUrl)
    {
        ActionMessages msgs = new ActionMessages();
        msgs.add(property, message);

	    HttpSession session = currentRequest().getSession();
	    ActionMessages allMessages = (ActionMessages) session.getAttribute(SESSION_MESSAGES_KEY);
	    if (allMessages != null)
		    allMessages.add(msgs);
	    else allMessages = msgs;

        session.setAttribute(SESSION_MESSAGES_KEY, allMessages);
        //сохраняем URL адресата сообщений
        if (StringHelper.isNotEmpty(targetUrl))
            session.setAttribute(ActionMessagesKeys.sessionNotificationsTarget.getKey(), targetUrl);
    }

    /**
     * Сохранение ошибки в сессию
     * @param property ключ сообщения
     * @param message сообщение
     * @param targetUrl URL адресата сообщения. Если null или пустая строка, то любому (первому прочитавшему).
     *  Если адресат уже задан, то он будет перезаписан. 
     */
    protected void saveSessionError(String property, ActionMessage message, String targetUrl)
	{
		ActionMessages msgs = new ActionMessages();
		msgs.add(property, message);

		HttpSession session = currentRequest().getSession();
		ActionMessages allErrors = (ActionMessages) session.getAttribute(ActionMessagesKeys.sessionError.getKey());
		if (allErrors != null)
			allErrors.add(msgs);
		else allErrors = msgs;
		
		session.setAttribute(ActionMessagesKeys.sessionError.getKey(), allErrors);
        //сохраняем URL адресата сообщений
        if (StringHelper.isNotEmpty(targetUrl))
            session.setAttribute(ActionMessagesKeys.sessionNotificationsTarget.getKey(), targetUrl);
	}

	/**
	 * Сохранение ошибки в сессию с ключом org.apache.struts.action.GLOBAL_MESSAGE
	 * @param message сообщение
	 * @param targetUrl URL адресата сообщения. Если null или пустая строка, то любому (первому прочитавшему).
	 *  Если адресат уже задан, то он будет перезаписан.
	 */
	protected void saveSessionError(String message, String targetUrl)
	{
		if(StringHelper.isNotEmpty(message))
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), null);
	}

	/**
	 * Сохранение сообщений об ошибках в сессию.
	 * @param request
	 * @param messages
	 */
	protected void saveSessionErrors(HttpServletRequest request, ActionMessages messages)
	{
		HttpSession session = request.getSession();
		ActionMessages allErrors = (ActionMessages) session.getAttribute(ActionMessagesKeys.sessionError.getKey());

		if (allErrors != null)
		{
			allErrors.add(messages);
		}
		else
		{
			allErrors = messages;
		}
		session.setAttribute(ActionMessagesKeys.sessionError.getKey(), allErrors);
	}

	// Сохранение сообщений о недоступности внешней системы в сессию
	protected void saveInactiveESMessage(HttpSession session, String message)
	{
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));

		ActionMessages allMessages = (ActionMessages) session.getAttribute(SESSION_INACTIVE_ES_MESSAGE_KEY);
		if (allMessages != null)
			allMessages.add(msgs);
		else allMessages = msgs;

		session.setAttribute(SESSION_INACTIVE_ES_MESSAGE_KEY, allMessages);
	}

	// Сохранение сообщений о недоступности внешней системы в реквест
	protected void saveInactiveESMessage(HttpServletRequest request, InactiveExternalSystemException e)
	{
		if (e instanceof CompositeInactiveExternalSystemException)
		{
			//обрабатываем ошибку от нескольких внешних систем
			CompositeInactiveExternalSystemException ciese = (CompositeInactiveExternalSystemException) e;
			for (String error : ciese.getErrors())
			{
				saveInactiveESMessage(request, error);
			}
			return;
		}

		saveInactiveESMessage(request, e.getMessage());
	}

	protected void saveInactiveESMessage(HttpServletRequest request, String message)
	{
		ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		saveInactiveESMessage(request, actionMessages);
	}

	protected void saveInactiveESMessage(HttpServletRequest request, ActionMessages actionMessages)
	{
		ActionMessages allErrors = HttpSessionUtils.getSessionAttribute(request, ActionMessagesKeys.inactiveExternalSystem.getKey());
		if (allErrors != null)
			allErrors.add(actionMessages);
		else allErrors = actionMessages;

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
        if(applicationConfig.getLoginContextApplication() == Application.PhizIA)
	        saveErrors(request, allErrors);    
        else
            request.getSession().setAttribute(ActionMessagesKeys.inactiveExternalSystem.getKey(), allErrors);
	}

	/**
	 * Сохранение сообщений о смене мобильного оператора в реквест
	 * @param request - реквест
	 * @param message - сообщение
	 */
	protected void saveAdditionalMessage(HttpServletRequest request, String message)
	{
		request.getSession().setAttribute(SESSION_ADDITIONAL_MESSAGE_KEY, message);
	}

	protected ActionMessages getSessionMessages(HttpServletRequest request)
    {
		return HttpSessionUtils.getSessionAttribute(request, SESSION_MESSAGES_KEY);
    }

	protected ActionMessages getSessionErrors(HttpServletRequest request)
	{
		return HttpSessionUtils.getSessionAttribute(request, ActionMessagesKeys.sessionError.getKey());
	}

	/**
	 * @param request реквест выполняемой опреации
	 * @return сообщения (о недоступности внешней системы), сохраненные в сессии с ключом com.rssl.phizic.web.actions.SESSION_INACTIVE_ES_MESSAGE
	 */
	protected ActionMessages getSessionInactiveESMessage(HttpServletRequest request)
	{
		return HttpSessionUtils.getSessionAttribute(request, SESSION_INACTIVE_ES_MESSAGE_KEY);
	}

	protected FormProcessor<ActionMessages, ?> createFormProcessor(FieldValuesSource valuesSource, Form form)
	{
		return FormHelper.newInstance(valuesSource, form);
	}

	/**
	 *
	 * Создается форм-процессор валидирующий только поля указанные в changedFields.
	 *
	 * @param valuesSource валидируемые значения
	 * @param form форма
	 * @param changedFieldNames валидируемые поля
	 * @return FormProcessor<ActionMessages, ?>
	 */
	protected FormProcessor<ActionMessages, ?> createFormProcessor(FieldValuesSource valuesSource, Form form, List<String> changedFieldNames)
	{
		return FormHelper.newInstance(valuesSource, form, changedFieldNames);
	}

	protected FormProcessor<ActionMessages, ?> createFormProcessor(FieldValuesSource valuesSource, Form form, ValidationStrategy strategy)
	{
		return FormHelper.newInstance(valuesSource, form, strategy);
	}

	protected HttpServletRequest currentRequest()
	{
		return WebContext.getCurrentRequest();
	}

	protected ServletContext currentServletContext()
	{
		return getServlet().getServletContext();
	}

	protected HttpServletResponse currentResponse()
	{
		return WebContext.getCurrentResponce();
	}

	protected ActionForward sendRedirectToSelf(HttpServletRequest request)
	{
		String queryString = request.getQueryString();
		String url = request.getServletPath() + ( queryString != null ? "?" + queryString : "" );
		return new ActionForward(url, true);
	}

	protected void setCurrentMapping(ActionMapping mapping)
	{
		this.mapping.set(mapping);
	}

	protected ActionMapping getCurrentMapping()
	{
		return mapping.get();
	}

	/**
	 * Возвращает текстовое сообщение по бандлу и ключу
	 * Если сообщение не найдено, возвращается пустая строка
	 * и в лог пишется сообщение об ошибке
	 * @param bundle - бандл с сообщением
	 * @param key - ключ сообщения
	 * @return текст сообщения
	 *  или пустая строка, если не сообщение не найдено в бандле
	 */
	protected String getResourceMessage(String bundle, String key)
	{
		try
		{
			return messageConfig.message(bundle, key);
		}
		catch (ConfigurationException ex)
		{
			// TODO: а куда ещё можно написать?
			log.error("Ошибка получения текстовки", ex);
			return "";
		}
	}

	/**
	 * Возвращает текстовое сообщение по бандлу и ключу
	 * Если сообщение не найдено, возвращается пустая строка
	 * и в лог пишется сообщение об ошибке
	 * @param bundle - бандл с сообщением
	 * @param key - ключ сообщения
	 * @param args - параметры
	 * @return текст сообщения
	 *  или пустая строка, если не сообщение не найдено в бандле
	 */
	protected String getResourceMessage(String bundle, String key, Object... args)
	{
		try
		{
			return messageConfig.message(bundle, key, args);
		}
		catch (ConfigurationException ex)
		{

			log.error("Ошибка получения текстовки", ex);
			return "";
		}
	}
}
