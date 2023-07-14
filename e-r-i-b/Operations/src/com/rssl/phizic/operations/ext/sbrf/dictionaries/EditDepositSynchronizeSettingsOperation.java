package com.rssl.phizic.operations.ext.sbrf.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;
import java.util.Set;

/**
 * ќпераци€ редактировани€ настроек загрузки справочника вкладов
 * @author Pankin
 * @ created 20.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditDepositSynchronizeSettingsOperation extends EditPropertiesOperation<Restriction>
{
	private MinMax<Long> minMax;

	public MinMax<Long> getMinMax()
	{
		return minMax;
	}

	@Override
	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		super.initialize(propertyCategory);
		minMax = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
	}

	/**
	 * инициализаци€ операции
	 */
	@Override
	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		minMax = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
	}

	/**
	 * ќбновл€ет вклад, составленный из полученных строковых массивов видов и подвидов.
	 * @param kinds ћассив видов
	 * @param subkinds ћассив подвидов, перечисленных через зап€тую
	 */
	public void updateEntity(String[] kinds, String[] subkinds) throws BusinessLogicException, BusinessException
	{
		StringBuilder res = new StringBuilder();

		if(kinds != null)
			for(int i = 0; i < kinds.length; i++)
			{
				if(StringHelper.isEmpty(kinds[i]))
					throw new IllegalArgumentException("¬ид не может быть null");

				if(i > 0)
					res.append(";");

				res.append(kinds[i]);

				if(!StringHelper.isEmpty(subkinds[i]))
					res.append('.').append(subkinds[i]);
			}

		String str = SynchronizeSettingsUtil.getAllowedDepositProducts(SynchronizeSettingsUtil.readAllowedCardProducts(res.toString()));

		Map<String, String> properties = getEntity();
		properties.put(DepositConfig.DEPOSITPRODUCT_KINDS_ALLOWED_UPLOADING, str);
	}
}
