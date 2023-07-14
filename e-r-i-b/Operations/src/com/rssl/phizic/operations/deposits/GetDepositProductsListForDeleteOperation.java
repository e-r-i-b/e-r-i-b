package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositGlobal;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Barinov
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class GetDepositProductsListForDeleteOperation extends GetDepositProductsListOperationBase implements ListEntitiesOperation, RemoveEntityOperation
{
	public Source getTemplateSource() throws BusinessException
	{
		DepositGlobal global = depositProductService.getGlobal();
		return new StreamSource(new StringReader(global.getAdminListTransformation()));
	}

	private Map<Long, List<Long>> deposits;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Инициализация операции для удаления видов/подвидов
	 * @param deposits - мапа вид - подвиды
	 */
	public void initialize(Map<Long, List<Long>> deposits)
	{
		this.deposits = deposits;
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		depositProductService.removeDepositProducts(deposits, getInstanceName());
		MultiBlockModeDictionaryHelper.updateDictionary(DepositProduct.class);
	}

	public Object getEntity()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
