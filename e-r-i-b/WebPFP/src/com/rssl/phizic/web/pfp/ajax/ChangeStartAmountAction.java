package com.rssl.phizic.web.pfp.ajax;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.pfp.ChangeStartAmountOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 23.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChangeStartAmountAction extends OperationalActionBase
{
	private static final String CHANGE_RESULT = "ChangeResult";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("changeStartAmount", "changeStartAmount");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	/**
	 * Изменение суммы для формирования портфеля
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeStartAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeStartAmountForm frm = (ChangeStartAmountForm) form;
		ChangeStartAmountOperation operation = createOperation(ChangeStartAmountOperation.class);
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource, ChangeStartAmountForm.EDIT_FORM);

		if(processor.process())
		{
			operation.initialize(frm.getProfileId(), frm.getPortfolioId());
			if (operation.getPortfolio().getIsEmpty())
			{
				Map<String,Object> result = processor.getResult();
				operation.setNewStartAmount((BigDecimal)result.get("changedStartAmount"));
			}
			else
				saveError(request, new ActionMessage("Невозможно редактировать сумму.", false));

		}
		else
		{
			saveErrors(request,processor.getErrors());
		}

		return mapping.findForward(CHANGE_RESULT);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
