package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.DublicateDepositProductNameException;
import com.rssl.phizic.business.dictionaries.DepositProductReplicaSource;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Iterator;

/**
 @author Pankin
 @ created 07.02.2011
 @ $Author$
 @ $Revision$
 */
public class DepositProductUploadTest extends BusinessTestCaseBase
{
	public void testDepositUpload() throws Exception
	{
		try
		{
			DepositProductReplicaSource source = new DepositProductReplicaSource();
			Iterator iter = source.iterator();
			while (iter.hasNext())
			{
				add((DepositProductSBRF) iter.next());
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при загрузке справочника депозитных продуктов", e);
		}
	}

	private void add(DepositProductSBRF depositProduct) throws BusinessException, DublicateDepositProductNameException
	{
		DepositProductService depositService = new DepositProductService();
		if (depositService.findByName(depositProduct.getName()) != null)
		{
			depositService.update(depositProduct);
		}
		else
		{
			depositService.add(depositProduct);
		}
	}
}
