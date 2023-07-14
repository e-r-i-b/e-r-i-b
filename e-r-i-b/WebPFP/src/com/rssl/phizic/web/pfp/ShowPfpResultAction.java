package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.operations.pfp.ShowPfpResultOperation;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.common.download.DownloadAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 19.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowPfpResultAction extends PFPZeroStepActionBase
{
	private static final String PFP_RESULT_OPERATION_TYPE = "PfpResult";

	private static final String NOT_ENDED_PLANING_MESSAGE = "У Вас есть незавершенное финансовое планирование. " +
			"Если Вы хотите его продолжить, нажмите на кнопку «Продолжить планирование». " +
			"Если Вы хотите выполнить новое планирование, нажмите на кнопку «Пройти новое планирование». " +
			"В результате незавершенное планирование будет удалено.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.unload", "unload");
		map.put("button.replan", "startReplanning");
	    return map;
	}

	protected ShowPfpResultOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(ShowPfpResultOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase op) throws BusinessException, BusinessLogicException
	{
		ShowPfpResultForm frm = (ShowPfpResultForm) form;
		ShowPfpResultOperation operation = (ShowPfpResultOperation) op;
		PersonalFinanceProfile profile = operation.getProfile();
		frm.setRiskProfile(profile.getDefaultRiskProfile());
		frm.setPortfolioList(profile.getPortfolioList());
		frm.setPdfName(operation.getFileName());
		frm.setField("isViewMode", operation.viewMode());
		frm.setField("isAvailableStartPFP", operation.isAvailableStartPlaning());
		frm.setHasNotCoplitePFP(operation.getNotCompletedPFP() != null);
		if(frm.getHasNotCoplitePFP() && !operation.viewMode())
		{
			saveMessage(currentRequest(), NOT_ENDED_PLANING_MESSAGE);
		}
	}

	/**
	 * Метод создает данные для выгрузки, сохраняет во временном файле
	 * и преоткрывает страницу выгрузки
	 * @param mapping стратс.маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард для обновления страницы после нажатия на кнопку выгрузить
	 */
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPfpResultOperation operation = getOperation((ShowPfpResultForm) form);
		DownloadAction.unload(operation.createPDF(), PFP_RESULT_OPERATION_TYPE, operation.getFileName() + ".pdf", (FilterActionForm) form, request);
		return start(mapping, form, request, response);
	}
}
