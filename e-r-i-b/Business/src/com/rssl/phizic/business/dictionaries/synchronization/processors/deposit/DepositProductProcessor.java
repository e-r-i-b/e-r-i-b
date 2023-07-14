package com.rssl.phizic.business.dictionaries.synchronization.processors.deposit;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositProductSBRF;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для синхронизации депозитных продуктов
 */
public class DepositProductProcessor extends ProcessorBase<DepositProductSBRF>
{

	private static final DepositProductService depositProductService = new DepositProductService();

	@Override
	protected Class<DepositProductSBRF> getEntityClass()
	{
		return DepositProductSBRF.class;
	}

	@Override
	protected DepositProductSBRF getNewEntity()
	{
		DepositProductSBRF deposit = new DepositProductSBRF();
		deposit.setAllowedDepartments(new ArrayList<String>());
		return deposit;
	}

	@Override
	protected DepositProductSBRF getEntity(String uuid) throws BusinessException
	{
		return (DepositProductSBRF)depositProductService.findByProductId(Long.valueOf(uuid));
	}

	@Override
	protected void update(DepositProductSBRF source, DepositProductSBRF destination) throws BusinessException
	{
		destination.setProductId(source.getProductId());
		destination.setName(source.getName());
		destination.setDescription(source.getDescription());
		destination.setDetails(source.getDetails());
		destination.setAvailableOnline(source.isAvailableOnline());
		destination.setCapitalization(source.isCapitalization());
		destination.setWithMinimumBalance(source.isWithMinimumBalance());
		destination.setLastUpdateDate(Calendar.getInstance());
		destination.getAllowedDepartments().clear();
		destination.getAllowedDepartments().addAll(source.getAllowedDepartments());
	}

	public Class getClearCacheKey()
	{
		return DepositProduct.class;
	}
}
