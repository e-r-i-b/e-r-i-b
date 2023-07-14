package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.pfp.configure.PFPConfigHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class InsuranceTypeService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();
	private static final String MAX_INSURANCE_TYPES_KEY = "insurance.types.max";

	/**
	 * @param id ������������� ���� ���������
	 * @return ��� �������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public InsuranceType getById(final Long id) throws BusinessException
	{
		return getById(id, null);
	}

	/**
	 * @param id ������������� ���� ���������
	 * @param instance ��� �������� ������ ��
	 * @return ��� �������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public InsuranceType getById(final Long id, String instance) throws BusinessException
	{
		return service.findById(InsuranceType.class, id, instance);
	}

	/**
	 * �������� ��� ���������
	 * @param type  ��� ���������
	 * @param instance ��� �������� ������ ��
	 * @return ��� �������
	 * @throws BusinessException
	 */
	public InsuranceType addOrUpdate(final InsuranceType type, String instance) throws BusinessException
	{
		return service.addOrUpdate(type, instance);
	}

	/**
	 * ������� ��� ���������
	 * @param type ��������� ��� ���������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(final InsuranceType type, final String instance) throws BusinessException
	{
		service.removeWithImage(type, instance);
	}

	/**
	 * @param type ���
	 * @param instance ��� �������� ������ ��
	 * @return ����� �� ��� �������� ����
	 * @throws BusinessException
	 */
	public boolean hasChild(InsuranceType type, String instance) throws BusinessException
	{
		if (type == null)
			return false;

		return hasChild(type.getId(), instance);
	}


	/**
	 * @param insuranceTypeId ������������� ���� ��������
	 * @param instance ��� �������� ������ ��
	 * @return ����� �� ��� �������� ����
	 * @throws BusinessException
	 */
	public boolean hasChild(final Long insuranceTypeId, String instance) throws BusinessException
	{
		if(insuranceTypeId == null)
			return false;
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(InsuranceType.class.getName() + ".hasChild");
					query.setParameter("typeId", insuranceTypeId);
					return BooleanUtils.toBooleanObject((Integer) query.uniqueResult());
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param instance ��� �������� ������ ��
	 * @return ������ ���� ����� ��������� ���������
	 * @throws BusinessException
	 */
	public List<InsuranceType> getAll(String instance) throws BusinessException
	{
		return service.getAll(InsuranceType.class, instance);
	}

	private Long getImageId(final Class clazz, final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session)
				{
					Query query = session.getNamedQuery(clazz.getName() + ".getImageId");
					query.setParameter("id", id);
					return (Long) query.uniqueResult();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param productId �������������
	 * @return ������������� �������� ��� ��������� ��������
	 */
	public Long getInsuranceProductImageId(Long productId) throws BusinessException
	{
		return getImageId(InsuranceProduct.class, productId);
	}

	/**
	 * @param typeId �������������
	 * @return ������������� �������� ��� ����/���� ��������� ��������
	 */
	public Long getInsuranceTypeImageId(Long typeId) throws BusinessException
	{
		return getImageId(InsuranceType.class, typeId);
	}

	/**
	 * @param instance ��� �������� ������ ��
	 * @return ���������� ����� �������
	 * @throws BusinessException
	 */
	public Long getCountInsuranceType(String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Long>()
			{
				public Long run(Session session)
				{
					Query query = session.getNamedQuery(InsuranceType.class.getName() + ".countInsuranceType");
					return (Long) query.uniqueResult();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ����������� ���������� ���������� ����� ��������� ��������� �� �� ��� pfpDictionary.properties
	 * @return ���������� ����� ��������� ���������
	 */
	public Long getMaxInsuranceTypes() throws BusinessException
	{
		try
		{
			String propertyValue = ConfigFactory.getConfig(PFPConfigHelper.class).getValue(MAX_INSURANCE_TYPES_KEY);
			return Long.valueOf(propertyValue);
		}
		catch (Exception e)
		{
			throw new BusinessException("���������� �������� ������ � ������������ ���������� ����� ��������� ���������.", e);
		}
	}
}
