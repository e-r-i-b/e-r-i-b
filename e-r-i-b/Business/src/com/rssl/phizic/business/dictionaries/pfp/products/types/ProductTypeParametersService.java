package com.rssl.phizic.business.dictionaries.pfp.products.types;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileService;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class ProductTypeParametersService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();
	private static final RiskProfileService riskProfileService = new RiskProfileService();

	/**
	 * ����� ��������� �� ��������������
	 * @param id �������������
	 * @param instance ��� �������� ������ ��
	 * @return ���������
	 * @throws BusinessException
	 */
	public ProductTypeParameters findById(Long id, String instance) throws BusinessException
	{
		return service.findById(ProductTypeParameters.class, id, instance);
	}

	/**
	 * �������� ��������� ����� ���������
	 * @param parameters ����� ���������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void update(ProductTypeParameters parameters, String instance) throws BusinessException
	{
		service.update(parameters, instance);
	}

	/**
	 * ���������� ��������� ��� ���� ��������
	 * @param productType - ��� ��������
	 * @return ���������
	 * @throws BusinessException
	 */
	public ProductTypeParameters findByProductType(DictionaryProductType productType) throws BusinessException
	{
		return findByProductType(productType, null);
	}

	/**
	 * ���������� ��������� ��� ���� ��������
	 * @param productType - ��� ��������
	 * @param instance ��� �������� ������ ��
	 * @return ���������
	 * @throws BusinessException
	 */
	public ProductTypeParameters findByProductType(DictionaryProductType productType, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductTypeParameters.class);
		criteria.add(Expression.eq("type", productType));
		return service.findSingle(criteria, instance);
	}

	private List<String> getStringListFromEnums(List<? extends Enum> enums)
	{
		List<String> segments = new ArrayList<String>();
		for (Enum enumElement : enums)
			segments.add(enumElement.name());

		return segments;
	}

	/**
	 * ���������� �������� ��� ����� ��������
	 * @param productTypes - ��� ��������
	 * @param instance ��� �������� ������ ��
	 * @return ��������
	 * @throws BusinessException
	 */
	public List<SegmentCodeType> getSegmentsForTypes(final List<DictionaryProductType> productTypes, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<SegmentCodeType>>()
			{
				public List<SegmentCodeType> run(Session session)
				{
					Query query = session.getNamedQuery(ProductTypeParameters.class.getName() + ".getSegmentsForTypes");
					query.setParameterList("productTypes", getStringListFromEnums(productTypes));
					//noinspection unchecked
					List<String> resultList = query.list();
					ArrayList<SegmentCodeType> segments = new ArrayList<SegmentCodeType>(resultList.size());
					for (String segment : resultList)
						segments.add(SegmentCodeType.valueOf(segment));

					return segments;
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
	 * �������� ����-�������
	 * @param parameters ��������� ���� ���������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void updateRiskProfiles(ProductTypeParameters parameters) throws BusinessLogicException, BusinessException
	{
		updateRiskProfiles(parameters, null);
	}

	/**
	 * �������� ����-�������
	 * @param parameters ��������� ���� ���������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void updateRiskProfiles(ProductTypeParameters parameters, String instance) throws BusinessLogicException, BusinessException
	{
		List<ProductType> types = ProductType.getByDictionaryProductType(parameters.getType());
		Map<ProductType, List<SegmentCodeType>> productTypesMap = new HashMap<ProductType, List<SegmentCodeType>>();
		for (ProductType type : types)
		{
			Set<SegmentCodeType> segments = new HashSet<SegmentCodeType>();
			if (parameters.isUse())
				segments.addAll(parameters.getTargetGroup());
			List<DictionaryProductType> dictionaryProductTypes = new ArrayList<DictionaryProductType>(type.getDictionaryProductTypes());
			if (CollectionUtils.size(dictionaryProductTypes) > 1)
			{
				dictionaryProductTypes.remove(parameters.getType());
				segments.addAll(getSegmentsForTypes(dictionaryProductTypes, instance));
			}
			productTypesMap.put(type, new ArrayList<SegmentCodeType>(segments));
		}

		if (riskProfileService.isExistProductWeight(productTypesMap, instance))
			throw new BusinessLogicException("���������� ����-�������, ������������ ��������, � ������� ������������� ������� � ���� ������� ��������.");

		riskProfileService.updateProductWeight(productTypesMap, instance);
	}
}
