package com.rssl.phizic.web.pfp.recomendation.show;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.operations.pfp.recommendation.show.ShowRecommendationOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.pfp.PassingPFPActionBase;
import com.rssl.phizic.web.pfp.PassingPFPFormInterface;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 28.06.2013 
 * @ $Author$
 * @ $Revision$
 */

public class ShowRecommendationAction extends PassingPFPActionBase
{  	
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keyMap = super.getKeyMethodMap();
		keyMap.put("button.next","next");
		return keyMap;
	}

	protected EditPfpOperationBase getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{		
		return getOperation(ShowRecommendationOperation.class, form);
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase operation) throws BusinessException, BusinessLogicException
	{
		ShowRecommendationForm frm = (ShowRecommendationForm) form;
		ShowRecommendationOperation op = (ShowRecommendationOperation) operation;
		frm.setOutcomeMoney(operation.getProfile().getOutcomeMoney());
		frm.setCardRecommendation(op.getRecommendation());
	}

	/**
	 * Перейти к следущему шагу планирования
	 * @param mapping mapping
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowRecommendationForm frm = (ShowRecommendationForm) form;
		ShowRecommendationOperation operation = (ShowRecommendationOperation) getOperation(frm);
		operation.nextStep();
		return getRedirectForward(operation);
	}
}
