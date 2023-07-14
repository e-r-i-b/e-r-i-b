package com.rssl.phizic.web.blockingrules;

import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRules;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRulesService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 20.11.2012
 * @ $Author$
 * @ $Revision$
 * Показать сообщение причины блокировки целиком
 */
public class ShowMessageAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String,String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BlockingRulesService service = new BlockingRulesService();
		ListBlockingRulesForm frm = (ListBlockingRulesForm) form;
		String id = (String) frm.getField("id");
		if(StringHelper.isEmpty(id))
			throw new BusinessException("Идентификатор не задан");
		BlockingRules rule = service.findById(Long.valueOf(id));
		if (rule == null)
			throw new BusinessLogicException("По указаному идентификатору не найдено правило блокировки.");
		frm.setSelectedMessage(rule.getERIBMessage());
		return mapping.findForward(FORWARD_START);
	}
}
