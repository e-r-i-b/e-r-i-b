package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.Response;

import java.util.Map;

/**
 * Базовый конструктор для классов валидирующих входящий запрос (валидация работает аналогично тому как это делается в экшенах)
 *
 * @author Balovtsev
 * @since 21.05.2014
 */
public abstract class AbstractConstructorWithRequestValidation<RequestType extends Request, ResponseType extends Response> extends JAXBResponseConstructor<RequestType, ResponseType>
{
	@Override
	protected ResponseType makeResponse(RequestType request) throws Exception
	{
		if (requestShouldChecked())
		{
			FormProcessor<Map<String, String>, MapErrorCollector> processor = createFormProcessor(request);

			if (!processor.process())
			{
				throw new FormValidationException(processor.getErrors());
			}
		}

		return doMakeResponse(getMapValueSource(request).getValuesAsIs());
	}

	/**
	 * Определяет, есть ли необходимость в валидации запроса
	 * @return true - данные необходимо валидировать, false в противном случае
	 */
	protected boolean requestShouldChecked()
	{
		return !(FormBuilder.EMPTY_FORM == getForm());
	}

	/**
	 * @param result параметры запроса, где ключ - имя элемента, а значение - это содержимое элемента
	 * @return сформированный ответ
	 * @throws Exception
	 */
	protected abstract ResponseType doMakeResponse(Map<String, Object> result) throws Exception;
}
