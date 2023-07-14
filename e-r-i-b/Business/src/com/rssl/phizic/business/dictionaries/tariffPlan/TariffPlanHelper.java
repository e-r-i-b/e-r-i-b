package com.rssl.phizic.business.dictionaries.tariffPlan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 27.08.14
 * @ $Author$
 * @ $Revision$
 */
public class TariffPlanHelper
{
	private static String UNKNOWN_TARIFF_PLAN_CODE = "0";

	private static final TarifPlanConfigService tarifPlanConfigService = new TarifPlanConfigService();
	protected static final Map<String, String> tariffPlans = new HashMap<String, String>();
	static
	{
		tariffPlans.put("UNKNOWN", "0");
		tariffPlans.put("PREMIER", "1");
		tariffPlans.put("GOLD", "2");
		tariffPlans.put("FIRST", "3");
	}

	/**
	 * Вернуть код тарифного плана. В случае пустого значения вернуть код не определенного ТП
	 * @param code - код для проверки
	 * @return код ТП
	 */
	public static String getTariffPlanCode(String code)
	{
		if (StringHelper.isEmpty(code))
			return getUnknownTariffPlanCode();
		return code;
	}

	public static String getUnknownTariffPlanCode()
	{
		return UNKNOWN_TARIFF_PLAN_CODE;
	}

	public static TariffPlanConfig getUnknownTariffPlan() throws BusinessException
	{
		TariffPlanConfig tariffPlan = tarifPlanConfigService.getTarifPlanConfigByTarifPlanCodeType(getUnknownTariffPlanCode());

		if (tariffPlan == null)
		{
			TariffPlanConfig unknownTariffPlan = new TariffPlanConfig();
			unknownTariffPlan.setCode(getUnknownTariffPlanCode());
			unknownTariffPlan.setName("Тариф не установлен");
			return unknownTariffPlan;
		}
		return tariffPlan;
	}

	/**
	 * Получить все записи справочника тарифных планов
	 * @return список ТП из справочника
	 * @throws BusinessException
	 */
	public static List<TariffPlanConfig> getDictionaryTariffPlans() throws BusinessException
	{
		try
		{
			return	HibernateExecutor.getInstance().execute(new HibernateAction<List<TariffPlanConfig> >()
			{
				public List<TariffPlanConfig>  run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig.getAll");
					return query.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public static boolean isUnknownTariffPlan(String code)
	{
		return StringHelper.equalsNullIgnore(getTariffPlanCode(code), getUnknownTariffPlanCode());
	}

	/**
	 * Получить код ТП по старому текстовому синониму (или вернуть числовой, если передали его)
	 * @param synonym - код ТП для проверки
	 * @return числовой код ТП
	 */
	public static String getCodeBySynonym(String synonym)
	{
		String code = getTariffPlanCode(synonym);
		if (code.length() > 1)
			return tariffPlans.get(code);
		else
			return code;
	}

	/**
	 * Получить все возможные тарифные планы.
	 * Если в справочнике отсутствует "не определенный" ТП (с кодом "0"), добавляем его в результирующий список
	 * @return список всех возможных ТП
	 * @throws BusinessException
	 */
	public static List<TariffPlanConfig> getAllTariffPlans() throws BusinessException
	{
		List<TariffPlanConfig> dictionaryTariffPlans = getDictionaryTariffPlans();

		for (TariffPlanConfig dictionaryTariffPlan : dictionaryTariffPlans)
		{
			if (StringHelper.equalsNullIgnore(dictionaryTariffPlan.getCode(), getUnknownTariffPlanCode()))
				return dictionaryTariffPlans;
		}

		dictionaryTariffPlans.add(getUnknownTariffPlan());
		return dictionaryTariffPlans;
	}

	/**
	 * Получение кодов тарифных планов, загруженных в ЕРИБ
	 * @return список кодов
	 * @throws BusinessException
	 */
	public static List<Long> getDictionaryTariffCodes() throws BusinessException
	{
		List<TariffPlanConfig> tariffPlans = TariffPlanHelper.getAllTariffPlans();
		List<Long> tariffCodes = new ArrayList<Long>();
		for (TariffPlanConfig tariffPlanConfig : tariffPlans)
		{
			tariffCodes.add(Long.valueOf(tariffPlanConfig.getCode()));
		}
		return tariffCodes;
	}

	/**
	 * Найти по коду действующий тарифный план
	 * @param code - код ТП
	 * @return актуальный ТП, если есть в справочнике
	 * @throws BusinessException
	 */
	public static TariffPlanConfig getActualTariffPlanByCode(String code) throws BusinessException
	{
		return tarifPlanConfigService.getActualTarifPlanConfigByTarifPlanCodeType(code);
	}

	/**
	 * Найти по коду действующий тарифный план
	 * @param code - код ТП
	 * @return актуальный ТП, если есть в справочнике
	 * @throws BusinessException
	 */
	public static TariffPlanConfig getActualLocaledTariffPlanByCode(String code) throws BusinessException
	{
		return tarifPlanConfigService.getActualLocaledTarifPlanConfigByTarifPlanCodeType(code);
	}
}
