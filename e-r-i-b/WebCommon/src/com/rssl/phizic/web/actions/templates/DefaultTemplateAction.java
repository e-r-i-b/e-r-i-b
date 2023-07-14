package com.rssl.phizic.web.actions.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.documents.templates.TemplateStateMachineService;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import org.apache.struts.action.ActionForward;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class DefaultTemplateAction extends DefaultDocumentAction
{
	private final TemplateStateMachineService stateMachineService = new TemplateStateMachineService();

	private static Map<String, String> addEditParameters(TemplateDocument template) throws BusinessException
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

	@Override
	protected void additionalParameters(HttpServletRequest request, UrlBuilder urlBuilder)
	{
		super.additionalParameters(request, urlBuilder);

		String fromFinanceCalendar = request.getParameter("fromFinanceCalendar");
		String extractId = request.getParameter("extractId");

		if (StringHelper.isNotEmpty(fromFinanceCalendar))
		{
			urlBuilder.addParameter("fromFinanceCalendar", fromFinanceCalendar);
		}
		if (StringHelper.isNotEmpty(extractId))
		{
			urlBuilder.addParameter("extractId", extractId);
		}
	}

	protected StateMachine getStateObjectMachine(String formName)
	{
		return stateMachineService.getStateMachineByFormName(formName);
	}

	/**
	 * Метод перенаправляет на страницу редактирования шаблона. Значения для полей передаются GET запросом.
	 * @param template  шаблон
	 * @param machineState машина состояний
	 * @return
	 * @throws BusinessException
	 */
	public static ActionForward redirectEditTemplateForm(TemplateDocument template, MachineState machineState) throws BusinessException{
		UrlBuilder urlBuilder = new UrlBuilder(DefaultDocumentAction.createStateUrl(template, machineState));
		urlBuilder.addParameters(addEditParameters(template));
		return new ActionForward(urlBuilder.toString(), true);
	}
}
