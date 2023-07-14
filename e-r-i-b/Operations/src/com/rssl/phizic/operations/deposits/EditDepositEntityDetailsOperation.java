package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositEntityVisibilityService;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityHelper;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibilityInfo;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Операция для настройки видимости вкладных продуктов (с использованием сущностей таблиц ЦАС НСИ)
 * @author EgorovaA
 * @ created 20.04.15
 * @ $Author$
 * @ $Revision$
 */
public class EditDepositEntityDetailsOperation extends EditDictionaryEntityOperationBase
{
	private static final DepositEntityVisibilityService depositVisibilityService = new DepositEntityVisibilityService();
	private DepositEntityVisibility entityVisibility;

	public void initialize(Long productType) throws BusinessException
	{
		DepositEntityVisibility visibility = depositVisibilityService.getDepositEntityVisibility(productType);
		if (visibility != null)
			entityVisibility = visibility;
		else
		{
			entityVisibility = new DepositEntityVisibility();
			entityVisibility.setDepositType(productType);
			entityVisibility.setAllowedDepartments(new ArrayList<String>());
			entityVisibility.setDepositSubTypes(new ArrayList<Long>());
			entityVisibility.setName(depositVisibilityService.getDepositEntityName(productType));
		}
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		depositVisibilityService.updateVisibility(entityVisibility);
	}

	public DepositEntityVisibility getEntity()
	{
		return entityVisibility;
	}

	/**
	 * Получить подразделения, в которых разрешено открытие вклада
	 * @param tbs идентификаторы подразделений, в которых разрешено открытие вклада
	 * @return список подразделений
	 * @throws BusinessException
	 */
	public List<String> getProductAllowedTBList(String[] tbs) throws BusinessException
	{
		List<String> chosedDepartments = new ArrayList<String>();
		for (String tb : tbs)
		{
			if(AllowedDepartmentsUtil.isDepartmentsAllowedByCode(tb, null, null))
				chosedDepartments.add(tb);
		}

		List<String> oldAllowedDepartments = entityVisibility.getAllowedDepartments();
		if (!CollectionUtils.isEmpty(oldAllowedDepartments))
		{
			// Чтобы не потерять настройку для тех подразделений, к которым нет доступа у сотрудника,
			// проверяем старый набор доступных ТБ
			List<String> notEmployeeAllowedTBs = new ArrayList<String>(oldAllowedDepartments);
			List<String> employeeAllowedTBs = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

			notEmployeeAllowedTBs.removeAll(employeeAllowedTBs);

			if (!CollectionUtils.isEmpty(chosedDepartments))
			{
				// Из доступных сотруднику подразделений добавляем те, которые он выбрал
				notEmployeeAllowedTBs.addAll(chosedDepartments);
			}
			return notEmployeeAllowedTBs;
		}

		return chosedDepartments;
	}

	public List<DepositEntityVisibilityInfo> getDepositEntitySubTypes(Long type) throws BusinessException
	{
		List<DepositEntityVisibilityInfo> visibilityInfoList = depositVisibilityService.getDepositVisibilityInfoList(type);

		List<Long> activeSubTypes = entityVisibility.getDepositSubTypes();

		Iterator<DepositEntityVisibilityInfo> iterator =  visibilityInfoList.iterator();
		while (iterator.hasNext())
		{
			DepositEntityVisibilityInfo visibilityInfo = iterator.next();
			List<DepositsDCFTAR> rates = DepositProductHelper.getDepositsDCFTARByTypeAndSubType(visibilityInfo.getType(), visibilityInfo.getSubType());

			Collections.sort(rates, new Comparator<DepositsDCFTAR>()
			{
				public int compare(DepositsDCFTAR o1, DepositsDCFTAR o2)
				{
					int compareResult = o2.getDateBegin().compareTo(o1.getDateBegin());
					if (compareResult != 0)
						return compareResult;
					return o1.getBaseRate().compareTo(o2.getBaseRate());
				}
			});
			if (CollectionUtils.isEmpty(rates))
			{
				iterator.remove();
				continue;
			}
			visibilityInfo.setRate(rates.get(0).getBaseRate());
			visibilityInfo.setPeriod(DepositEntityHelper.parsePeriod(visibilityInfo.getPeriod()));
			if (activeSubTypes.contains(visibilityInfo.getSubType()))
				visibilityInfo.setAvailableOnline(true);
		}
		return visibilityInfoList;
	}

}
