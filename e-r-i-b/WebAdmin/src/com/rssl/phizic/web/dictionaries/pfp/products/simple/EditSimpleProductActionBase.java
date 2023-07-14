package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.SimpleProductBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductActionBase;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author akrenev
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Базовый экшен редактирования простого продукта ПФП
 */

public abstract class EditSimpleProductActionBase extends EditProductActionBase
{
	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		SimpleProductBase product = (SimpleProductBase) entity;
		for (PortfolioType type: PortfolioType.values())
		{
			ProductParameters productParameters = product.getParameters(type);
			productParameters.setMinSum((BigDecimal) data.get(type.name().concat("minSum")));
		}
		super.updateEntity(entity, data);
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		SimpleProductBase product = (SimpleProductBase) entity;
		for (PortfolioType type: PortfolioType.values())
			frm.setField(type.name().concat("minSum"), product.getParameters(type).getMinSum());
	}
}
