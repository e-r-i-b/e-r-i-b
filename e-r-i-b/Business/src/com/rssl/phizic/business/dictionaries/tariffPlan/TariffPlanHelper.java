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
	 * ������� ��� ��������� �����. � ������ ������� �������� ������� ��� �� ������������� ��
	 * @param code - ��� ��� ��������
	 * @return ��� ��
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
			unknownTariffPlan.setName("����� �� ����������");
			return unknownTariffPlan;
		}
		return tariffPlan;
	}

	/**
	 * �������� ��� ������ ����������� �������� ������
	 * @return ������ �� �� �����������
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
	 * �������� ��� �� �� ������� ���������� �������� (��� ������� ��������, ���� �������� ���)
	 * @param synonym - ��� �� ��� ��������
	 * @return �������� ��� ��
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
	 * �������� ��� ��������� �������� �����.
	 * ���� � ����������� ����������� "�� ������������" �� (� ����� "0"), ��������� ��� � �������������� ������
	 * @return ������ ���� ��������� ��
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
	 * ��������� ����� �������� ������, ����������� � ����
	 * @return ������ �����
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
	 * ����� �� ���� ����������� �������� ����
	 * @param code - ��� ��
	 * @return ���������� ��, ���� ���� � �����������
	 * @throws BusinessException
	 */
	public static TariffPlanConfig getActualTariffPlanByCode(String code) throws BusinessException
	{
		return tarifPlanConfigService.getActualTarifPlanConfigByTarifPlanCodeType(code);
	}

	/**
	 * ����� �� ���� ����������� �������� ����
	 * @param code - ��� ��
	 * @return ���������� ��, ���� ���� � �����������
	 * @throws BusinessException
	 */
	public static TariffPlanConfig getActualLocaledTariffPlanByCode(String code) throws BusinessException
	{
		return tarifPlanConfigService.getActualLocaledTarifPlanConfigByTarifPlanCodeType(code);
	}
}
