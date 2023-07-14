package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentIsNotServicedException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.struts.forms.FormHelper;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import com.rssl.phizic.web.webApi.exceptions.ExtendedAbstractNotAvailableException;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.exceptions.InvalidSessionException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Error;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Warning;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.RequestOperationConstant;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.Response;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.SimpleResponse;
import org.apache.commons.collections.MapUtils;

import java.security.AccessControlException;
import java.util.*;

/**
 *
 * Базовый класс предоставляющий функционал формирования ответа
 *
 * @author Balovtsev
 * @since 24.04.14
 */
public abstract class JAXBResponseConstructor<RequestType extends Request, ResponseType extends Response>
{
	protected static final String ACCESS_DENIED_ERROR_MESSAGE = "Нет доступа к операции";
	protected static final String COMMON_ERROR_MESSAGE = "Операция временно недоступна";
	protected static final String DUPKICATE_SESSION_ERROR_MESSAGE = "Сеанс работы в системе прерван, так как Ваша учетная запись используется на другом компьютере. Для возобновления работы повторно зайдите в «Сбербанк Онлайн».";
	protected static final Log LOG = PhizICLogFactory.getLog(LogModule.Web);

	private static final OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());

	/**
	 * @return ответ
	 * @throws Exception
	 */
	protected abstract ResponseType makeResponse(final RequestType request) throws Exception;

	/**
	 * @param request запрос
	 * @return ответ
	 */
	public final Response construct(final RequestType request)
	{
		try
		{
			if (!request.getOperation().equals(RequestOperationConstant.LOGIN_OPERATION))
				Utils.checkSession();
			if (PersonHelper.getPersonLogonSessionId() != null && !WebContext.getCurrentRequest().getSession(false).getId().equals(PersonHelper.getPersonLogonSessionId()))
				return Utils.constructFailResponse(StatusCode.SESSION_UNAVAILABLE, DUPKICATE_SESSION_ERROR_MESSAGE, null);
			return makeResponse(request);
		}
		catch (AccessControlException e)
		{
			Utils.error(request, e);
			return Utils.constructFailResponse(StatusCode.ACCESS_DENIED, ACCESS_DENIED_ERROR_MESSAGE, null);
		}
		catch (DepartmentIsNotServicedException e)
		{
			return getCriticalErrorResponse(e, e.getMessage(), request);
		}
		catch (BusinessException e)
		{
			return getCriticalErrorResponse(e, COMMON_ERROR_MESSAGE, request);
		}
		catch (BusinessLogicException e)
		{
			Utils.error(request, e);
			return Utils.constructFailResponse(StatusCode.ERROR, e.getMessage(), null);
		}
		catch (InvalidSessionException e)
		{
			Utils.error(request, e);
			return Utils.constructFailResponse(StatusCode.SESSION_UNAVAILABLE, e.getMessage(), null);
		}
		catch (ConfigurationException e)
		{
			return getCriticalErrorResponse(e, COMMON_ERROR_MESSAGE, request);
		}
		catch (ExtendedAbstractNotAvailableException e)
		{
			Utils.error(request, e);
			return Utils.constructFailResponse(StatusCode.ERROR, e.getMessage(), null);
		}
		catch (FormValidationException e)
		{
			Utils.error(request, e);
			return Utils.constructFailResponse(StatusCode.ERROR, e.getErrors(), null);
		}
		catch (Exception e)
		{
			return getCriticalErrorResponse(e, COMMON_ERROR_MESSAGE, request);
		}
	}

	private Response getCriticalErrorResponse(Exception exception, String defaultMessage, Request request)
	{
		String logMessage = Utils.getMessage(request, exception);
		String errorMessage = ExceptionLogHelper.processExceptionEntryWithWriteLog(exception, logMessage);
		if (StringHelper.isEmpty(errorMessage))
			errorMessage = defaultMessage;
		return Utils.constructFailResponse(StatusCode.CRITICAL_ERROR, errorMessage, null);
	}

	/**
	 * По-умолчанию данный метод возвращает не заполненный объект
	 *
	 * @see AbstractConstructorWithRequestValidation
	 * @return данные использующиеся при валидации запроса
	 */
	protected MapValuesSource getMapValueSource(RequestType request) throws Exception
	{
		return new MapValuesSource(Collections.emptyMap());
	}

	/**
	 * По-умолчанию валидация не включена и форма пуста
	 *
	 * @see AbstractConstructorWithRequestValidation
	 * @return форма для проверки запроса
	 */
	protected Form getForm()
	{
		return FormBuilder.EMPTY_FORM;
	}

	/**
	 * По-умолчанию валидация отключена, если необходима проверка - то необходимо сделать следующее:
	 * 1. Создать свой конструктор на основе абстрактного класса AbstractConstructorWithRequestValidation;
	 * 2. Переопределить методы getForm и getMapValueSource.
	 *
	 * @see AbstractConstructorWithRequestValidation
	 * @param request объект запроса
	 * @return процессор для валидации запроса
	 * @throws Exception
	 */
	protected final FormProcessor<Map<String, String>, MapErrorCollector> createFormProcessor(RequestType request) throws Exception
	{
		return FormHelper.newInstance(getMapValueSource(request), getForm(), new MapErrorCollector());
	}

	/**
	 * @see OperationFactory#checkAccess(Class)
	 */
	protected boolean checkAccess(Class clazz) throws BusinessException
	{
		return operationFactory.checkAccess(clazz);
	}

	/**
	 * @see OperationFactory#checkAccess(Class, String)
	 */
	protected boolean checkAccess(Class clazz, String service) throws BusinessException
	{
		return operationFactory.checkAccess(clazz, service);
	}

	/**
	 * @see OperationFactory#create(Class)
	 */
	protected <T extends Operation> T createOperation(Class<T> operationClass) throws AccessControlException, BusinessException
	{
		return operationFactory.create(operationClass);
	}

	/**
	 * @see OperationFactory#create(Class, String)
	 */
	protected <T extends Operation> T createOperation(Class<T> operationClass, String service) throws AccessControlException, BusinessException
	{
		return operationFactory.create(operationClass, service);
	}

	protected <T extends Operation> T createOperation(String key, String serviceKey) throws BusinessException
	{
		return operationFactory.<T>create(key, serviceKey);
	}

	static final class Utils
	{
		private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

		static void checkSession() throws InvalidSessionException
		{
			if (!SecurityUtil.isAuthenticationComplete())
			{
				throw new InvalidSessionException();
			}
		}

		/**
		 * Логирование ошибок
		 */
		static void error(final Request request, final Throwable e)
		{
			log.error(getMessage(request, e), e);
		}

		static String getMessage(final Request request, final Throwable e)
		{
			return request.getOperation() + " : " + e.getMessage();
		}

		static void error(final String message, final Throwable e)
		{
			log.error(message, e);
		}

		/**
		 * Создает сообщение отсылаемое при возникновениие ошибок
		 *
		 * @param code код ответа
		 * @param error ошибка
		 * @param elementId идентификатор элемента
		 * @return Response
		 */
		static Response constructFailResponse(final StatusCode code, final String error, final String elementId)
		{
			Map<String, String> errors = new HashMap<String, String>();
			errors.put(error, elementId);

			return constructFailResponse(code, errors, null);
		}

		/**
		 * Создает сообщение отсылаемое при возникновениие ошибок
		 *
		 * @param code код ответа
		 * @param errors список ошибок
		 * @param messages сообщения
		 * @return Response
		 */
		static Response constructFailResponse(final StatusCode code, final Map<String, String> errors, final Map<String, String> messages)
		{
			Status status = new Status(code);

			if (MapUtils.isNotEmpty(errors))
			{
				List<Error> list = new ArrayList<Error>();
				for (String text : errors.keySet())
				{
					list.add(new Error(text, errors.get(text)));
				}

				status.setErrors(list);
			}

			if (MapUtils.isNotEmpty(messages))
			{
				List<Warning> list = new ArrayList<Warning>();
				for (String text : messages.keySet())
				{
					list.add(new Warning(text, messages.get(text)));
				}

				status.setWarnings(list);
			}

			return new SimpleResponse(status);
		}
	}
}

