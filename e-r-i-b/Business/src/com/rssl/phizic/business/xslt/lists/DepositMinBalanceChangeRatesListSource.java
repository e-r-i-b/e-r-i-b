package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.common.types.transmiters.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * Используется для создания xml с данными о неснижаемых остатках на один из которых пользователь может перевести вклад.
 *
 * @author Balovtsev
 * @version 19.09.13 9:11
 */
public class DepositMinBalanceChangeRatesListSource extends AbstractDepositMinBalanceChangeListSource<EntityList>
{
	public EntityList buildEntity(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		List<Pair<BigDecimal, String>> sourceList = DepositProductHelper.getMinBalancesToChange(getAccountLink(Long.parseLong(params.get(ACCOUNT_ID))));

		EntityList entityList = new EntityList();
		if (sourceList == null)
		{
			return entityList;
		}

		for (int i=0; i<sourceList.size(); i++)
		{
			Pair<BigDecimal, String> pair = sourceList.get(i);

			Entity entity = new Entity(Integer.valueOf(i).toString(), null);
			entity.addField(new Field("minDepositBalance", pair.getFirst().toString()));
			entity.addField(new Field("interestRate",      pair.getSecond().toString()));
			entityList.addEntity(entity);
		}

		return entityList;
	}
}
