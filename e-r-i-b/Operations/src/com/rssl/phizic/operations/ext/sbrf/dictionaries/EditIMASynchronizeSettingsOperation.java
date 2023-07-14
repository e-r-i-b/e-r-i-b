package com.rssl.phizic.operations.ext.sbrf.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.utils.IMAConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Операция редактирования настроек загрузки справочника ОМС
 * @author Pankin
 * @ created 25.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditIMASynchronizeSettingsOperation extends EditPropertiesOperation<Restriction>
{
	private List<Long> iMATypes;

	@Override
	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		super.initialize(propertyCategory);
		iMATypes = SynchronizeSettingsUtil.getIMAProductTypeValues();
	}

	/**
	 * инициализация операции
	 */
	@Override
	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		iMATypes = SynchronizeSettingsUtil.getIMAProductTypeValues();
	}

	public List<Long> getIMATypes()
	{
		return iMATypes;
	}

	/**
	 * Обновляет ОМС, составленный из полученных строковых массивов видов и подвидов.
	 * @param kinds Массив видов
	 * @param subkinds Массив подвидов, перечисленных через запятую
	 */
	public void updateEntity(String[] kinds, String[] subkinds) throws BusinessLogicException, BusinessException
	{
		StringBuilder res = new StringBuilder();

		if(kinds != null)
			for(int i = 0; i < kinds.length; i++)
			{
				if(StringHelper.isEmpty(kinds[i]))
					throw new IllegalArgumentException("Вид не может быть null");

				if(i > 0)
					res.append(";");

				res.append(kinds[i]);

				if(!StringHelper.isEmpty(subkinds[i]))
					res.append('.').append(subkinds[i]);
			}

		String str = SynchronizeSettingsUtil.getAllowedIMAProducts(SynchronizeSettingsUtil.readAllowedCardProducts(res.toString()));

		Map<String, String> properties = getEntity();
		properties.put(IMAConfig.IMA_PRODUCT_USED_KINDS, str);
	}
}
