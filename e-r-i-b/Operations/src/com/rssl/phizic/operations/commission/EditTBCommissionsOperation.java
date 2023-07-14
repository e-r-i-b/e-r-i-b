package com.rssl.phizic.operations.commission;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.ext.sbrf.commissions.CommissionTBSettingService;
import com.rssl.phizic.business.ext.sbrf.commissions.CommissionsTBSetting;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author vagin
 * @ created 20.09.13
 * @ $Author$
 * @ $Revision$
 * Операция по работе с комиссиями для различных типов платежей в разрезе тербанков.
 */
public class EditTBCommissionsOperation extends OperationBase implements ListEntitiesOperation
{
	private List<CommissionsTBSetting> settings = new ArrayList<CommissionsTBSetting>();
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * @return - мап синонимов документов.
	 */
	public static Map<String, Pair<String, String>> getSettingsSynonyms()
	{
		return CommissionTBSettingService.getCommissionPaymentsSynonyms();
	}

	/**
	 * @return - мап синонимов документов для платежей с рублевых ресурсов.
	 */
	public static Map<String, Pair<String, String>> getSettingsRurSynonyms()
	{
		return CommissionTBSettingService.getCommissionPaymentsRurSynonyms();
	}

	/**
	 * Инициализация операции
	 * @param departmentId - идентификатор тербанка
	 * @throws BusinessException
	 */
	public void initialize(Long departmentId) throws BusinessException
	{
		Department department = MultiBlockModeDictionaryHelper.getLocalDepartment(departmentId);
		String tb = department.getCode().getFields().get("region");

		for (String paymentType : getSettingsSynonyms().keySet())
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(CommissionsTBSetting.class);
			criteria.add(Expression.eq("TB", tb));
			criteria.add(Expression.eq("paymentType", paymentType));
			CommissionsTBSetting fromDB = simpleService.findSingle(criteria);
			settings.add(fromDB != null ? fromDB : new CommissionsTBSetting(tb, paymentType, false, false));
		}
	}

	/**
	 * @return список настроек отображения коммисии для данного ТБ с валютных счетов
	 */
	public Map<Pair<String,Boolean>, String> getCurrencySettings()
	{
		Map<Pair<String,Boolean>, String> result = new HashMap<Pair<String,Boolean>, String>();
		Map<String, Pair<String, String>> synonyms = getSettingsSynonyms();
		for (CommissionsTBSetting setting : settings)
		{
			Pair<String, String> description = synonyms.get(setting.getPaymentType());
			result.put(new Pair<String, Boolean>(description.getFirst(), setting.isShow()), description.getSecond());
		}
		return sortByValue(result);
	}

	/**
	 * @return список настроек отображения коммисии для данного ТБ с рублевых счетов
	 */
	public Map<Pair<String,Boolean>, String> getRurSettings()
	{
		Map<Pair<String,Boolean>, String> result = new HashMap<Pair<String,Boolean>, String>();
		Map<String, Pair<String, String>> rurSynonyms = getSettingsRurSynonyms();
		for (CommissionsTBSetting setting : settings)
		{
			Pair<String, String> description = rurSynonyms.get(setting.getPaymentType());
			result.put(new Pair<String, Boolean>(description.getFirst(), setting.isShowRub()), description.getSecond());
		}
		return sortByValue(result);
	}

	/**
	 * Сохранение настроек.
	 * @param selectedTypes - выбраные настройки.
	 * @throws BusinessException
	 */
	public void save(String[] selectedTypes) throws BusinessException
	{
		List<CommissionsTBSetting> result = new ArrayList<CommissionsTBSetting>();
		for(CommissionsTBSetting setting: settings)
		{
			boolean oldState = setting.isShow();
			boolean oldRurState = setting.isShowRub();
			boolean newState = false;
			boolean newRurState = false;
			for(String selectedType: selectedTypes)
			{
				if(selectedType.equals(getSettingsSynonyms().get(setting.getPaymentType()).getFirst()))
				{
					newState = true;
					continue;
				}
				if(selectedType.equals(getSettingsRurSynonyms().get(setting.getPaymentType()).getFirst()))
				{
					newRurState = true;
				}
			}
			if (oldState^newState)
			{
				setting.setShow(newState);
			}
			if (oldRurState^newRurState)
			{
				setting.setShowRub(newRurState);
			}
			if((oldState^newState) || (oldRurState^newRurState))
			{
				result.add(setting);
			}
		}
		simpleService.addOrUpdateList(result);
	}

	/**
	 * сортировка коллекции Map<String,Pair<String,Boolean>>
	 * @param map - коллекция
	 * @return отсортированная коллекция
	 */
	private static Map<Pair<String,Boolean>, String> sortByValue(Map<Pair<String,Boolean>, String> map)
	{
		List<Map.Entry<Pair<String,Boolean>, String>> list = new ArrayList<Map.Entry<Pair<String,Boolean>, String>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Pair<String, Boolean>, String>>()
		{
			public int compare(Map.Entry<Pair<String, Boolean>, String> o1, Map.Entry<Pair<String, Boolean>, String> o2)
			{
				return o1.getValue().compareToIgnoreCase(o2.getValue());
			}
		});

		Map<Pair<String,Boolean>, String> result = new LinkedHashMap<Pair<String,Boolean>, String>();
		for (Map.Entry<Pair<String,Boolean>, String> entry : list)
		{
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
