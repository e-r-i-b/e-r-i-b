package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Moshenko
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ������ ������� �� ��������� ���������.
 */
public class ListCreditProductConditionOperation extends OperationBase implements ListEntitiesOperation
{
	private CreditProductTypeService typeService = new CreditProductTypeService();
	/**
	 *
	 * �������� ������� ��������� ��������� � ������� ����� ��������� ���������.
	 * ���� �� ���� ��� ������� ������ ���������� ������ ������ ������ �������.
	 * @param conditionList C����� �������.
	 * @param filterForm  ���� �������.
	 * @return ������ ��� ��� ����, ������ ������� ����.
	 */
	public List<Map.Entry<CreditProductType,List<CreditProductCondition>>> getUpdateDataList(List<CreditProductCondition> conditionList,Map<String, Object> filterForm) throws BusinessException
	{
		Map<CreditProductType,List<CreditProductCondition>> typeConditionMap = new HashMap<CreditProductType,List<CreditProductCondition>>();
		String status = (String) filterForm.get("status");
		Long  productId = (Long)filterForm.get("creditProductId");
		Long typetId = (Long)filterForm.get("creditProductTypeId");
		if ((typetId == null && productId == null && StringHelper.isEmpty(status)))
			for(CreditProductType type:typeService.getAll())
				typeConditionMap.put(type,new ArrayList<CreditProductCondition>());
		else if (typetId != null && productId == null &&  StringHelper.isEmpty(status))
		{
			CreditProductType filterType = typeService.findById(typetId);
			if (filterType == null)
				throw new BusinessException("��� ���������� �������� � id:" + typetId +" �� ���������");
			typeConditionMap.put(filterType,new ArrayList<CreditProductCondition>());
		}

		for (CreditProductCondition condition:conditionList)
		{
			CreditProductType type = condition.getCreditProductType();
			List<CreditProductCondition> conditions = typeConditionMap.get(type);
			if (conditions == null)
				conditions = new ArrayList<CreditProductCondition>();
			conditions.add(condition);
			typeConditionMap.put(type,conditions);
		}


		return new ArrayList<Map.Entry<CreditProductType, List<CreditProductCondition>>>(typeConditionMap.entrySet());
	}
}
