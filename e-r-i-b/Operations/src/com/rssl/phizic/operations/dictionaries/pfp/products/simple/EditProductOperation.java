package com.rssl.phizic.operations.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexProductService;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.ForComplexProductDiscriminator;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.Product;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.operations.dictionaries.pfp.products.EditProductOperationBase;

import java.util.Collections;

/**
 * @author akrenev
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditProductOperation<P extends Product> extends EditProductOperationBase<P>
{
	private static final ComplexProductService complexProductService = new ComplexProductService();

	/**
	 * @param discriminator новое отношение к комплексным продуктам
	 * @return можно ли изменить отношение к комплексному продукту
	 * @throws BusinessException
	 */
	public boolean canSave(ForComplexProductDiscriminator discriminator) throws BusinessException
	{
		P product = getEntity();
		return product.getId()== null || product.getForComplex() == discriminator || !complexProductService.containsInComplexProduct(product, getInstanceName());
	}

	@Override
	protected void doSave(P product) throws BusinessException
	{
		if (product.getForComplex() != ForComplexProductDiscriminator.none)
		{
			product.setNewTargetGroup(Collections.<SegmentCodeType>emptySet());
			product.setUniversal(false);
		}
		
		super.doSave(product);
	}
}
