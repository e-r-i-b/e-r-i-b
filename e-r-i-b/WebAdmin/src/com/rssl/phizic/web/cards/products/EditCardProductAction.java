package com.rssl.phizic.web.cards.products;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.parsers.BooleanParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.card.products.EditCardProductOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * @author gulov
 * @ created 04.10.2011
 * @ $Authors$
 * @ $Revision$
 */
public class EditCardProductAction extends EditActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return EditCardProductForm.EDIT_FORM;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditCardProductOperation operation = createOperation(EditCardProductOperation.class, "CardProducts");
		Long id = frm.getId();
		if (id != null && id != 0)
			operation.initialize(id);
		else
			operation.initializeNew();

		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		EditCardProductForm form = (EditCardProductForm) frm;
		CardProduct product = (CardProduct) entity;
		if(product.getId() == null)
			return;
		form.setField("name", product.getName());
		form.setField("online", product.isOnline());
		form.setProduct(product);
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws BusinessException
	{
		CardProduct product = (CardProduct) entity;
		product.setName((String) data.get("name"));
		product.setOnline((Boolean) data.get("online"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditCardProductForm form = (EditCardProductForm) editForm;
		Boolean online = new BooleanParser().parse((String) form.getField("online"));
		((EditCardProductOperation) editOperation).validateAndWrite(form.getStopOpenDate(), online, form.getSubIds(), form.getCurrencies());
	}
}
