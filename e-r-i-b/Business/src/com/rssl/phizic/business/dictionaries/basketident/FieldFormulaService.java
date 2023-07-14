package com.rssl.phizic.business.dictionaries.basketident;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ �� �������� ���������� � �����������
 */
public class FieldFormulaService
{
	private static SimpleService simpleService = new SimpleService();

	/**
	 * �������� ��� �������� �������
	 * @param formulas ������ ������
	 * @throws BusinessException
	 */
	public void addOrUpdate(List<FieldFormula> formulas) throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(formulas))
		{
			return;
		}

		try
		{
			simpleService.addOrUpdateListWithConstraintViolationException(formulas);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("�� �� ������ ��������� �������� � ����������� ����������� ���������� �����.", e);
		}
	}

	/**
	 * ������� �������
	 * @param formulas ������ ������ ��� ��������
	 * @throws BusinessException
	 */
	public void remove(List<FieldFormula> formulas) throws BusinessException
	{
		if (CollectionUtils.isEmpty(formulas))
		{
			return;
		}

		simpleService.removeList(formulas);
	}

	/**
	 * ����� ��� ������� �� �������������� ���������(�������������� �������)
	 * @param identId ������������� ���������(�������������� �������)
	 * @param providerId ������������� ��
	 * @return ������ ������
	 * @throws BusinessException
	 */
	public List<FieldFormula> getByIdentIdAndProviderId(final Long identId, final Long providerId) throws BusinessException
	{
		if (identId == null)
		{
			throw new IllegalArgumentException("������������� ��������� �� ����� ���� null.");
		}
		if (providerId == null)
		{
			throw new IllegalArgumentException("������������� ���������� ����� �� ����� ���� null.");
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FieldFormula>>()
			{
				public List<FieldFormula> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.dictionaries.basketident.FieldFormula.getByIdentIdAndProviderId")
							.setParameter("ident_id", identId)
							.setParameter("provider_id", providerId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ������ ������ � ������� ����� �� �� �������������� ���������
	 * @param identId ������������� ���������
	 * @return ������ ������
	 * @throws BusinessException
	 */
	public List<Object[]> getWithName(final Long identId) throws BusinessException
	{
		if (identId == null)
		{
			throw new IllegalArgumentException("������������� ��������� �� ����� ���� null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.dictionaries.basketident.FieldFormula.getWithName")
							.setParameter("ident_id", identId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������ � ������� ����� �� �� �������������� ��������� ������������ ����������
	 * @param identId ������������� ���������
	 * @return ���(id ���������� -> ������ ���[��� ���� ����������, ������� ��� ����������])
	 * @throws BusinessException
	 */
	public Map<Long, Map<String, FieldFormula>> getWithNameRelatedProvider(final Long identId) throws BusinessException
	{
		if (identId == null)
		{
			throw new IllegalArgumentException("������������� ��������� �� ����� ���� null.");
		}

		// ������������ ���������� -> ������ ��� [��� ���� ����������, ������� ��� ����������]
		Map<Long, Map<String, FieldFormula>> result = new HashMap<Long, Map<String, FieldFormula>>();

		List<Object[]> formulas = getWithName(identId);
		if(CollectionUtils.isEmpty(formulas))
			return result;

		for(Object[] formulaData : formulas)
		{
			FieldFormula fieldFormula = (FieldFormula) formulaData[1];

			Map<String, FieldFormula> fieldsSetting = result.get(fieldFormula.getProviderId());
			if(fieldsSetting == null)
			{
				fieldsSetting = new HashMap<String, FieldFormula>();
				result.put(fieldFormula.getProviderId(), fieldsSetting);
			}

			fieldsSetting.put(fieldFormula.getFieldExternalId(), fieldFormula);
		}

		return result;
	}
}
