package com.rssl.phizic.web.client.bki;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.loanreport.CreditReportOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.download.DownloadAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн дл€ работы с кредитным отчетом.
 * ќтображает страницу с запросом кредитной истории, с самой историей и с детальной информацией по кредиту, или карте
 * и с выгрузкой истории в pdf формате
 */
public class CreditReportAction extends ViewActionBase
{
	private static final String CREDIT_HISTORY_RESULT_OPERATION_TYPE = "CreditHistoryResult";
	private static final String FORWARD_SHOW_REPORT = "Report";
	private static final String FORWARD_SHOW_DETAIL = "Detail";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.unloadPDF", "unload");
		return map;
	}

	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		CreditReportOperation operation = createOperation(CreditReportOperation.class, "CreditReportService");
		CreditReportForm form = (CreditReportForm) frm;
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		if (form.getCreditId() != null && form.getCreditId() != -1)
			operation.initialize(person, form.getCreditId());
		else
			operation.initialize(person);
		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		CreditReportOperation oper = (CreditReportOperation) operation;
		CreditReportForm form = (CreditReportForm) frm;
		form.setCost(oper.getCost());
		form.setProviderId(oper.getProviderId());
		form.setProfile(oper.getProfile());
		if (form.getCreditId() != null && form.getCreditId() != -1)
		{
			form.setCreditDetail(oper.getCreditDetail());
		}
		if (oper.hasReport())
			form.setReport(oper.getEntity());
		if (form.getActiveCreditViewBlock() == null)
			if (PersonContext.getPersonDataProvider().getPersonData().getActiveCreditViewBlock() == null)
				form.setActiveCreditViewBlock(false);
			else
				form.setActiveCreditViewBlock(PersonContext.getPersonDataProvider().getPersonData().getActiveCreditViewBlock());
		else
			PersonContext.getPersonDataProvider().getPersonData().setActiveCreditViewBlock(form.getActiveCreditViewBlock());
		form.setWaitingNew(oper.isWaitingNew());
		form.setBkiError(oper.isError());
		if (oper.isError())
		{
			saveError(currentRequest(), StrutsUtils.getMessage("credit.history.bki.error", "creditHistoryBundle"));
		}
	}

	protected ActionForward forwardSuccessShow(ActionMapping mapping, ViewEntityOperation operation)
	{
		CreditReportOperation oper = (CreditReportOperation) operation;
		if (oper.getCreditDetail() != null)
			return mapping.findForward(FORWARD_SHOW_DETAIL);
		else if (oper.hasReport())
			return mapping.findForward(FORWARD_SHOW_REPORT);
		else
			return mapping.findForward(FORWARD_START);
	}

	/**
	 * ћетод создает данные дл€ выгрузки, сохран€ет во временном файле
	 * и преоткрывает страницу выгрузки
	 * @param mapping стратс.маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард дл€ обновлени€ страницы после нажати€ на кнопку выгрузить
	 */
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreditReportOperation operation = createOperation(CreditReportOperation.class, "CreditReportService");
		operation.initialize(PersonContext.getPersonDataProvider().getPersonData().getPerson());
		DownloadAction.unload(operation.createPDF(), CREDIT_HISTORY_RESULT_OPERATION_TYPE, operation.getFileName() + ".pdf", (CreditReportForm) form, request);
		return start(mapping, form, request, response);
	}
}
