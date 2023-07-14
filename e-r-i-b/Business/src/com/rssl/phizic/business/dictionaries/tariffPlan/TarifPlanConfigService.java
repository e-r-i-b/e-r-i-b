package com.rssl.phizic.business.dictionaries.tariffPlan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.locale.MultiLocaleDetachedCriteria;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * ������ ��� ������ � ����������� �������� ������ ������ �����
 * @ author: Gololobov
 * @ created: 21.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TarifPlanConfigService extends SimpleService
{
	/**
	 * ������ �������� ���� ���������� �������� ������
	 * @return
	 */
	public List<TariffPlanConfig> getTarifPlanConfigsList() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(TariffPlanConfig.class);
		Calendar calendar = Calendar.getInstance();
		criteria.add(Expression.le("dateBegin", calendar));
		criteria.add(Expression.or(Expression.gt("dateEnd", calendar), Expression.isNull("dateEnd")));
		return find(criteria);
	}

	/**
	 * ����� �������� ��������� ����� �� ���� ��������� �����
	 * @param tarifPlanCodeType - ��� ��������� �����
	 * @return TarifPlanConfig - ��������� ��������� �����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public TariffPlanConfig getTarifPlanConfigByTarifPlanCodeType(String tarifPlanCodeType) throws BusinessException
	{
		if (StringHelper.isEmpty(tarifPlanCodeType))
		{
			log.error("�� ������� �������� ���� ��� ������ ��� ��������.");
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(TariffPlanConfig.class);
		criteria.add(Expression.eq("code", tarifPlanCodeType));
		return findSingle(criteria);
	}

	/**
	 * ����� �������� ��������� ����� �� ���� ��������� �����
	 * @param tarifPlanCodeType - ��� ��������� �����
	 * @return TarifPlanConfig - ��������� ��������� �����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public TariffPlanConfig getLocaledTarifPlanConfigByTarifPlanCodeType(String tarifPlanCodeType) throws BusinessException
	{
		if (StringHelper.isEmpty(tarifPlanCodeType))
		{
			log.error("�� ������� �������� ���� ��� ������ ��� ��������.");
			return null;
		}
		DetachedCriteria criteria = MultiLocaleDetachedCriteria.forClassInLocale(TariffPlanConfig.class, MultiLocaleContext.getLocaleId());
		criteria.add(Expression.eq("code", tarifPlanCodeType));
		return findSingle(criteria);
	}

	/**
	 * ����� �� ���� ����������� �������� ����
	 * @param tarifPlanCodeType - ��� ��
	 * @return ���������� ��, ���� ���� � �����������
	 * @throws BusinessException
	 */
	public TariffPlanConfig getActualTarifPlanConfigByTarifPlanCodeType(String tarifPlanCodeType) throws BusinessException
	{
		return getActualTarifPlanConfigByTarifPlanCodeType(tarifPlanCodeType, DetachedCriteria.forClass(TariffPlanConfig.class));
	}

	/**
	 * ����� �� ���� ����������� �������� ����
	 * @param tarifPlanCodeType - ��� ��
	 * @return ���������� ��, ���� ���� � �����������
	 * @throws BusinessException
	 */
	public TariffPlanConfig getActualLocaledTarifPlanConfigByTarifPlanCodeType(String tarifPlanCodeType) throws BusinessException
	{
		return getActualTarifPlanConfigByTarifPlanCodeType(tarifPlanCodeType, MultiLocaleDetachedCriteria.forClassInLocale(TariffPlanConfig.class, MultiLocaleContext.getLocaleId()));
	}

	private TariffPlanConfig getActualTarifPlanConfigByTarifPlanCodeType(String tarifPlanCodeType, DetachedCriteria criteria) throws BusinessException
	{
		if (StringHelper.isEmpty(tarifPlanCodeType))
		{
			log.error("�� ������� �������� ���� ��� ������ ��� ��������.");
			return null;
		}

		criteria.add(Expression.eq("code", tarifPlanCodeType));
		Calendar calendar = Calendar.getInstance();
		criteria.add(Expression.le("dateBegin", calendar));
		criteria.add(Expression.or(Expression.gt("dateEnd", calendar), Expression.isNull("dateEnd")));
		return findSingle(criteria);
	}
}
