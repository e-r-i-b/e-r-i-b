package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.products.simple.ForComplexProductDiscriminator;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.Product;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.EditProductOperationBase;
import com.rssl.phizic.operations.dictionaries.pfp.products.simple.EditProductOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.beans.Introspector;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditProductAction extends EditSimpleProductActionBase
{
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages messages = super.validateAdditionalFormData(frm, operation);
		EditProductOperation op = (EditProductOperation) operation;
		String productName = Introspector.decapitalize(ProductType.getByDictionaryProductTypeSingle(op.getEntity().getProductType()).getName());
		String forComplex = (String) frm.getField("forComplex");
		ForComplexProductDiscriminator forComplexValue = ForComplexProductDiscriminator.none;
		if (StringHelper.isNotEmpty(forComplex))
			forComplexValue = ForComplexProductDiscriminator.valueOf(forComplex);
		if(!op.canSave(forComplexValue))
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не можете отредактировать "+productName+", который входит в состав комплексного продукта. Пожалуйста, укажите другой "+productName+" для комплексного продукта, а затем повторите операцию.",false));
		return messages;
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Product product = (Product) entity;

		String forComplex = (String) data.get("forComplex");
		ForComplexProductDiscriminator forComplexValue = ForComplexProductDiscriminator.none;
		if (!StringHelper.isEmpty(forComplex))
			forComplexValue = ForComplexProductDiscriminator.valueOf(forComplex);

		product.setForComplex(forComplexValue);
		product.setSumFactor((BigDecimal)data.get("sumFactor"));
		super.updateEntity(entity, data);
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		Product product = (Product) entity;
		frm.setField("forComplex",  product.getForComplex());
		frm.setField("sumFactor",   product.getSumFactor());
		frm.setField("complexKind", product.getForComplex());
		frm.setField("forSomebodyComplex", product.getForComplex() != null && product.getForComplex() != ForComplexProductDiscriminator.none);
	}

	@Override
	protected void setViewParametersToEntity(EditProductOperationBase editOperation, Map<String, Object> data)
	{
		if (!(Boolean) data.get("forSomebodyComplex"))
			super.setViewParametersToEntity(editOperation, data);
		else
			clearViewParametersToEntity(editOperation.getEntity());
	}
}
