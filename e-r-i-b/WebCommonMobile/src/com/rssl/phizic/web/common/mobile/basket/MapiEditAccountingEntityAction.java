package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.common.types.basket.AccountingEntityType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.basket.accountingEntity.EditAccountingEntityOperation;
import com.rssl.phizic.operations.basket.accountingEntity.RemoveAccountingEntityOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @since 15.10.14.
 */
public class MapiEditAccountingEntityAction extends EditActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(2);
		keyMethodMap.put("save",   "save");
		keyMethodMap.put("remove", "remove");
		return keyMethodMap;
	}

	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAccountingEntityOperation operation = createOperation(EditAccountingEntityOperation.class);
		operation.initialize(frm.getId(), getType(frm));
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return MapiEditAccountEntityForm.EDIT_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		AccountingEntity accountingEntity = (AccountingEntity) entity;
		accountingEntity.setName((String) data.get(MapiEditAccountEntityForm.NAME_FIELD));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception {}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MapiEditAccountEntityForm frm = (MapiEditAccountEntityForm) form;

		Map parameters = request.getParameterMap();
		for (Object key : parameters.keySet())
		{
			String[] value = (String[]) parameters.get(key);
			if (ArrayUtils.isNotEmpty(value))
			{
				frm.setField((String) key, value[0]);
			}
		}

		return super.save(mapping, form, request, response);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoveAccountingEntityOperation operation = createOperation(RemoveAccountingEntityOperation.class);
		operation.initialize(((MapiEditAccountEntityForm) form).getId());
		operation.remove();

		return mapping.findForward(FORWARD_START);
	}

	@Override
	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		AccountingEntity entity = (AccountingEntity) operation.getEntity();
		if (entity.getId() != null)
		{
			((MapiEditAccountEntityForm) frm).setType(entity.getType().name());
		}

		return super.getFormProcessorValueSource(frm, operation);
	}

	protected AccountingEntityType getType(EditFormBase frm)
	{
		String type = ((MapiEditAccountEntityForm) frm).getType();
		return StringHelper.isEmpty(type) ? null: AccountingEntityType.toValue(type);
	}
}
