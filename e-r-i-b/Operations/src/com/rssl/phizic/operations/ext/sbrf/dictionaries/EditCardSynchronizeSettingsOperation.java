package com.rssl.phizic.operations.ext.sbrf.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;
import java.util.Set;

/**
 * �������� �������������� �������� �������� ����������� ����
 * @author Dorzhinov
 * @ created 29.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCardSynchronizeSettingsOperation extends EditPropertiesOperation<Restriction>
{
	private MinMax<Long> minMax;     //���. � ���. ���� �� ��������� ��������� ����� �������

	@Override
	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		super.initialize(propertyCategory);
		minMax = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
	}

	/**
	 * ������������� ��������
	 */
	@Override
	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		minMax = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
	}

	/**
	 * ��������� �����, ������������ �� ���������� ��������� �������� ����� � ��������.
	 * ���������� � ����� �������� ������ ���������.
	 * @param kinds ������ �����
	 * @param subkinds ������ ��������, ������������� ����� �������
	 */
	public void updateEntity(String[] kinds, String[] subkinds) throws BusinessLogicException, BusinessException
	{
		StringBuilder res = new StringBuilder();
		
		if(kinds != null)
			for(int i = 0; i < kinds.length; i++)
			{
				if(StringHelper.isEmpty(kinds[i]))
					throw new IllegalArgumentException("��� �� ����� ���� null");

				if(i > 0)
					res.append(";");

				res.append(kinds[i]);

				if(!StringHelper.isEmpty(subkinds[i]))
					res.append('.').append(subkinds[i]);
			}

		Map<String, String> properties = getEntity();
		properties.put(CardsConfig.CARD_PRODUCT_USED_KINDS, res.toString());
	}

	public MinMax<Long> getMinMax()
	{
		return minMax;
	}
}
