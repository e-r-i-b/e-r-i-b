package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.Response;

import java.util.Map;

/**
 * ������� ����������� ��� ������� ������������ �������� ������ (��������� �������� ���������� ���� ��� ��� �������� � �������)
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
	 * ����������, ���� �� ������������� � ��������� �������
	 * @return true - ������ ���������� ������������, false � ��������� ������
	 */
	protected boolean requestShouldChecked()
	{
		return !(FormBuilder.EMPTY_FORM == getForm());
	}

	/**
	 * @param result ��������� �������, ��� ���� - ��� ��������, � �������� - ��� ���������� ��������
	 * @return �������������� �����
	 * @throws Exception
	 */
	protected abstract ResponseType doMakeResponse(Map<String, Object> result) throws Exception;
}
