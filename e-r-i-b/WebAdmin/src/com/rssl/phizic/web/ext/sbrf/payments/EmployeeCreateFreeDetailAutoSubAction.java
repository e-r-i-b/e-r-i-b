package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateFreeDetailAutoSubOperation;
import com.rssl.phizic.operations.payment.EditJurPaymentOperation;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.utils.EntityUtils;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.CreateFreeDetailAutoSubAction;
import com.rssl.phizic.web.actions.payments.forms.CreateFreeDetailAutoSubForm;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentForm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * @author vagin
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeCreateFreeDetailAutoSubAction extends CreateFreeDetailAutoSubAction
{
	private static final String FORWARD_SHOW_PROVIDERS = "ShowProviders";

	protected CreateFreeDetailAutoSubOperation createEditOperation(CreateFreeDetailAutoSubForm form) throws BusinessException, BusinessLogicException
	{
		CreateFreeDetailAutoSubOperation operation = createOperation(CreateFreeDetailAutoSubOperation.class, "EmployeeFreeDetailAutoSubManagement");
		Long paymentId = form.getId();
		if (paymentId != null && paymentId > 0)
		{
			//если пришел идентфикатор платежа, значит здаем операцию на основе шаблона.
			operation.initialize(form.getFromResource(), paymentId, true, true);
		}
		else
		{
			//Ничего не пришло - инициализируем операцию только источником списания.
			operation.initialize(form.getFromResource());
		}
		return operation;
	}

	protected CreateESBAutoPayOperation createESBAutoPayOperation(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		CreateESBAutoPayOperation esbAutoPayOperation = createOperation("CreateESBAutoPayOperation", "CreateEmployeeAutoPayment");
		esbAutoPayOperation.initialize(source, null, ObjectEvent.EMPLOYEE_EVENT_TYPE);
		return esbAutoPayOperation;
	}

	protected void updateFormAdditionalData(EditJurPaymentForm form, EditJurPaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		CreateFreeDetailAutoSubForm frm = (CreateFreeDetailAutoSubForm) form;
		frm.setActivePerson(PersonContext.getPersonDataProvider().getPersonData().getPerson());
		super.updateFormAdditionalData(frm, operation);
	}

	protected ActionForward makeRecipientAutoSubscription(Long providerId, String fromResource) throws BusinessException
	{
		ActionForward forward = getCurrentMapping().findForward(FORWARD_CREATE_RECIPIENT_AUTOSUB);
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameter("recipient", providerId.toString());
		urlBuilder.addParameter("fromResource", fromResource);
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	protected ActionForward findRecipients(CreateFreeDetailAutoSubForm frm, CreateFreeDetailAutoSubOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		//ищем поставщиков в нашей БД
		List<BillingServiceProvider> providers = operation.findRecipientByRequisites();
		if (!CollectionUtils.isEmpty(providers))
		{
			//если ПУ найдено больше чем один-отображаем форму выбора нескольких ПУ.
			if (providers.size() > 1)
				return showRecipientList(providers, frm.getFromResource());

			//если найден один поставщик то переходим на форму создания автоподписки в его адресс
			return makeRecipientAutoSubscription(providers.get(0).getId(), frm.getFromResource());
		}
		return doPrepareExternalProviderPayment(operation, frm, request);
	}

	/**
	 * Переход на форму с выбором поставщиков услуг предоставляющих услуги автоплатежа по введенным реквизитам.
	 * @param providers - список вида ПУ-регион
	 * @return форвард отображения списка доступных ПУ
	 */
	protected ActionForward showRecipientList(List<BillingServiceProvider> providers, String fromResource)
	{
		ActionForward forward = getCurrentMapping().findForward(FORWARD_SHOW_PROVIDERS);
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		Set<Long> ids = EntityUtils.collectEntityIds(providers);
		urlBuilder.addParameter("field(recipientList)", StringUtils.join(ids,"|"));
		urlBuilder.addParameter("fromResource", fromResource);
		return new ActionForward(urlBuilder.getUrl(), true);
	}
}
