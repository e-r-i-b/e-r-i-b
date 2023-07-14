package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.logging.operations.LogParametersReader;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessages;

import java.security.AccessControlException;
import java.util.Collections;
import java.util.Map;

/**
 * @author Erkin
 * @ created 10.09.2012
 * @ $Author$
 * @ $Revision$
 */
abstract class RegistrationWizardBase
{
	private final OperationalActionBase action;

	private final OperationFactory operationFactory;

	protected RegistrationWizardBase(OperationalActionBase action)
	{
		this.action = action;
		this.operationFactory = action.getOperationFactory();
	}

	protected <T extends Operation> T createOperation(Class<T> operationClass) throws AccessControlException, BusinessException
	{
		return operationFactory.create(operationClass);
	}

	protected FormProcessor<ActionMessages, ?> createFormProcessor(FieldValuesSource valuesSource, Form form)
	{
		return FormHelper.newInstance(valuesSource, form);
	}

	protected void addLogParameters(LogParametersReader reader)
	{
		action.addLogParameters(reader);
	}

	protected ActionForward redirect(ActionForward forward, Map<String, Object> fieldValues)
	{
		UrlBuilder urlBuilder = new UrlBuilder(forward.getPath());
		for (Map.Entry<String, Object> entry : fieldValues.entrySet())
			urlBuilder.addParameter(entry.getKey(), StringHelper.getEmptyIfNull(entry.getValue()));
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	protected ActionForward redirect(ActionForward forward, String key, Object value)
	{
		return redirect(forward, Collections.singletonMap(key, value));
	}
}
