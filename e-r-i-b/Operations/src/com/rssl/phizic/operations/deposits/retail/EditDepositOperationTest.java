package com.rssl.phizic.operations.deposits.retail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 06.04.2007
 * @ $Author: khudyakov $
 * @ $Revision: 40139 $
 */

@SuppressWarnings({"JavaDoc"}) public class EditDepositOperationTest extends BusinessTestCaseBase
{
	private DepositProduct product;

	protected void tearDown() throws Exception
	{
		if(product != null)
			new SimpleService().remove(product);

		super.tearDown();
	}

	public void testEditDepositOperation() throws BusinessException, GateLogicException, GateException,
			BusinessLogicException
	{
		EditDepositProductOperation operation = new EditDepositProductOperation();

		List<String> list = new ArrayList<String>();

		list.add("»м—ерт360дн");
		list.add("»м—ерт720дн");
		list.add("»м—ерт1080дн");

		operation.ininitializeNew(null);
		operation.setRetailDeposits(list);
		operation.getProductSource();
		operation.save();

		product = operation.getEntity();

		operation = new EditDepositProductOperation();
		operation.initialize(product.getId());
		operation.setRetailDeposits(list);
		operation.getProductSource();
		operation.save();
	}
}
