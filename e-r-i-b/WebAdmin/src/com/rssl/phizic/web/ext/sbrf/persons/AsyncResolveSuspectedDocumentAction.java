package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CommissionsHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.operations.ext.sbrf.payment.GetResolutionOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ResolveSuspectedDocumentOperation;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.MoneyFunctions;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tisov
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 * Экшн асинхронного утверждения вердикта фрод-документу
 */
public class AsyncResolveSuspectedDocumentAction extends OperationalActionBase
{
	private static final String ACCEPT_VERDICT = "accept";
	private static final String FORWARD_FINISH = "Finish";
	private static final String FORWARD_GET_RESOLUTION_REJECT = "GetResolutionReject";
	private static final String COMMISSION_NOTIFICATION_FORWARD_NAME = "CommissionNotification";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> result = super.getKeyMethodMap();
		result.put("button.continue", "confirm");
		return result;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncResolveSuspectedDocumentForm frm = (AsyncResolveSuspectedDocumentForm) form;

		if (frm.getVerdict().equals(ACCEPT_VERDICT))
		{
			GetResolutionOperation operation = createOperation(GetResolutionOperation.class);
			operation.initialize(frm.getDocumentId());

			if (operation.isResolutionStatusNegative())
			{
				operation.refuseDocument();
				return mapping.findForward(FORWARD_GET_RESOLUTION_REJECT);
			}
		}
		return mapping.findForward(FORWARD_START);
	}

	private ActionForward createRedirectForwardToList(AsyncResolveSuspectedDocumentForm form, String url)
	{
		UrlBuilder builder = new UrlBuilder(url + "?person=" + form.getPerson());
		ActionForward forward = new ActionForward(builder.getUrl());
		forward.setRedirect(true);
		return forward;
	}

	private ActionForward createRedirectForCommissionNotification(Long documentId, String url)
	{
		UrlBuilder builder = new UrlBuilder(url + "?id=" + documentId);
		ActionForward forward = new ActionForward(builder.getUrl());
		forward.setRedirect(true);
		return forward;
	}

	/**
	 * Вынесение вердикта по фрод-документу
	 * @param mapping - маппинг
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncResolveSuspectedDocumentForm frm = (AsyncResolveSuspectedDocumentForm) form;

		ResolveSuspectedDocumentOperation operation = createOperation(ResolveSuspectedDocumentOperation.class);

		try
		{
			operation.initialize(frm.getDocumentId(), frm.getVerdict(), frm.getVerdictText());
			operation.executeVerdict();
			return createRedirectForwardToList(frm, mapping.findForward(FORWARD_FINISH).getPath());
		}
		catch (PostConfirmCalcCommission e)
		{
			operation.saveDocument();
			BusinessDocument document = operation.getDocument();
			String commissionMessage = CommissionsHelper.getCommissionMessage(MoneyFunctions.getFormatAmount(((BusinessDocumentBase) document).getCommission()), ((BusinessDocumentBase) document).getTariffPlanESB(), frm.getPerson());
			saveMessage(request, commissionMessage);
			return createRedirectForCommissionNotification(frm.getDocumentId(), mapping.findForward(COMMISSION_NOTIFICATION_FORWARD_NAME).getPath());
		}
	}

}
