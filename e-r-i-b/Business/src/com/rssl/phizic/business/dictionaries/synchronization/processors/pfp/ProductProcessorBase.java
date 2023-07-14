package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый процессор продуктов ПФП
 */

public abstract class ProductProcessorBase<P extends ProductBase> extends PFPProcessorBase<P>
{
	protected abstract P getNewProduct();

	@Override
	protected final P getNewEntity()
	{
		P product = getNewProduct();
		product.setTargetGroup(new HashSet<SegmentCodeType>());
		TableViewParameters tableParameters = new TableViewParameters();
		product.setTableParameters(tableParameters);
		return product;
	}

	private TableViewParameters getUpdatedTableParameters(TableViewParameters source, TableViewParameters destination) throws BusinessException
	{
		if (source == null)
			return null;

		TableViewParameters updatedParameters = destination;
		if (updatedParameters == null)
		{
			updatedParameters = new TableViewParameters();
			updatedParameters.setColumns(new HashMap<TableColumn, String>());
		}

		updatedParameters.setUseIcon(source.isUseIcon());
		Map<TableColumn, String> destinationColumns = updatedParameters.getColumns();
		destinationColumns.clear();
		for (Map.Entry<TableColumn, String> tableColumnEntry : source.getColumns().entrySet())
			destinationColumns.put(getLocalVersionByGlobal(tableColumnEntry.getKey()), tableColumnEntry.getValue());

		return destination;
	}

	@Override
	protected void update(P source, P destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setDescription(source.getDescription());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
		destination.setMinIncome(source.getMinIncome());
		destination.setMaxIncome(source.getMaxIncome());
		destination.setDefaultIncome(source.getDefaultIncome());
		destination.setNewTargetGroup(source.getTargetGroup());
		destination.setAxisX(source.getAxisX());
		destination.setAxisY(source.getAxisY());
		destination.setTableParameters(getUpdatedTableParameters(source.getTableParameters(), destination.getTableParameters()));
		destination.setUniversal(source.isUniversal());
		destination.setEnabled(source.isEnabled());
	}

	@Override
	protected void doRemove(P localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
