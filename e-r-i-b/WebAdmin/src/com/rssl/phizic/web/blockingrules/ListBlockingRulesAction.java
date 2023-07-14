package com.rssl.phizic.web.blockingrules;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingState;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.csa.blockingrules.BlockingRulesEditOperation;
import com.rssl.phizic.operations.csa.blockingrules.BlockingRulesListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * Экшен отображения списка правил блокировки.
 */
public class ListBlockingRulesAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.blockAll", "blockAll");
		map.put("button.releaseAll", "releaseAll");
		map.put("button.showMessage","showMessage");
		map.put("button.lock","lock");
		map.put("button.unlock","unlock");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(BlockingRulesListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("BlockingRulesRemoveOperation", "BlockingRulesManagement");
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		BlockingRulesListOperation op = (BlockingRulesListOperation)operation;
		frm.setField("globalBlock", op.isGlobalBlock());
	}

	/**
	 * Блокировать все входы клиентов
	 * @throws Exception
	 */
	public final ActionForward blockAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BlockingRulesEditOperation operation = createOperation("BlockingRulesEditOperation", "BlockingRulesManagement");
		operation.createGlobalBlockingRecord(BlockingState.blocked);
		return start(mapping, form, request, response);
	}

	/**
	 * Убрать глобальную блокировку входа клиентов.
	 * @throws Exception
	 */
	public final ActionForward releaseAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BlockingRulesEditOperation operation = createOperation("BlockingRulesEditOperation", "BlockingRulesManagement");
		operation.createGlobalBlockingRecord(BlockingState.unblocked);
		return start(mapping, form, request, response);
	}

	/**
	 * Заблокировать.
	 * @throws Exception
	 */
	public final ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BlockingRulesEditOperation operation = createOperation("BlockingRulesEditOperation", "BlockingRulesManagement");
		ListBlockingRulesForm frm = (ListBlockingRulesForm)form;
		for(String id: frm.getSelectedIds())
		{
			operation.changeState(Long.valueOf(id),BlockingState.blocked);
		}
		return start(mapping, form, request, response);
	}

	/**
	 * Разблокировать.
	 * @throws Exception
	 */
	public final ActionForward unlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BlockingRulesEditOperation operation = createOperation("BlockingRulesEditOperation", "BlockingRulesManagement");
		ListBlockingRulesForm frm = (ListBlockingRulesForm)form;
		for(String id: frm.getSelectedIds())
		{
			operation.changeState(Long.valueOf(id),BlockingState.unblocked);
		}
		return start(mapping, form, request, response);
	}
}
