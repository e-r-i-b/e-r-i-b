package com.rssl.phizic.web.client.basket.accountingEntity;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityBase;
import com.rssl.phizic.common.types.basket.AccountingEntityType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.basket.accountingEntity.EditAccountingEntityOperation;
import com.rssl.phizic.operations.basket.accountingEntity.RemoveAccountingEntityOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 13.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовый метод редактирования объeкта учета
 */
public abstract class EditAccountEntityActionBase extends EditActionBase
{
	protected static final String FORWARD_ASYNC_SUCCESS = "AsyncSuccess";
	protected static final String FORWARD_ASYNC_FAILED = "AsyncFailed";

	@Override
	protected Map<String, String> getAdditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.remove", "remove");
		return map;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditAccountingEntityForm.EDIT_FORM;
	}

	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAccountingEntityOperation operation = createOperation(EditAccountingEntityOperation.class);
		operation.initialize(frm.getId(), getType());
		return operation;
	}

	protected abstract AccountingEntityType getType();

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.start(mapping, form, request, response);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Удалить объект учета
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditAccountingEntityForm frm = (EditAccountingEntityForm) form;
		RemoveAccountingEntityOperation operation = createOperation(RemoveAccountingEntityOperation.class);
		operation.initialize(frm.getId());
		operation.remove();
		return mapping.findForward(FORWARD_ASYNC_SUCCESS);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditAccountingEntityForm frm = (EditAccountingEntityForm) form;
		EditAccountingEntityOperation op = (EditAccountingEntityOperation) operation;

		frm.setNeedUngroupSubscriptions(op.needUngroupSubscriptions());
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		AccountingEntityBase accountingEntity = (AccountingEntityBase) entity;
		frm.setField(EditAccountingEntityForm.NAME_FIELD, accountingEntity.getName());
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		AccountingEntityBase accountingEntity = (AccountingEntityBase) entity;
		accountingEntity.setName((String) data.get(EditAccountingEntityForm.NAME_FIELD));
	}


}
