package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.*;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexProductService;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.operations.dictionaries.pfp.products.EditProductOperationBase;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsuranceProductOperation extends EditProductOperationBase<InsuranceProduct>
{
	private static final InsuranceTypeService insuranceTypeService = new InsuranceTypeService();
	private static final InsuranceCompanyService insuranceCompanyService = new InsuranceCompanyService();
	private static final PeriodTypeService periodTypeService = new PeriodTypeService();
	private static final ComplexProductService complexProductService = new ComplexProductService();
	private Map<Long, PeriodType> periodTypes;

	protected Class<InsuranceProduct> getProductClass()
	{
		return InsuranceProduct.class;
	}

	@Override
	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.INSURANCE;
	}

	protected InsuranceProduct getNewProduct()
	{
		InsuranceProduct insuranceProduct = new InsuranceProduct();
		insuranceProduct.setPeriods(new ArrayList<InsuranceDatePeriod>());
		return insuranceProduct;
	}

	/**
	 * ������ ��� ��������� ���������
	 * @param id ������������� ����
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void setType(Long id) throws BusinessException, BusinessLogicException
	{
		InsuranceProduct product = getEntity();
		if (product.getType() != null && product.getType().getId().equals(id))
			return;

		if (insuranceTypeService.hasChild(id, getInstanceName()))
			throw new BusinessLogicException("���������� �������� ��� ��������� ���������.");

		InsuranceType parentInsuranceType = insuranceTypeService.getById(id, getInstanceName());
		if (parentInsuranceType == null)
			throw new BusinessLogicException("� ������� �� ������ ��� ��������� ��������� � id: " + id);

		product.setType(parentInsuranceType);
	}

	/**
	 * ������ ��������� ��������
	 * @param id ������������� ��������� ��������
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void setCompany(Long id) throws BusinessException, BusinessLogicException
	{
		InsuranceProduct product = getEntity();
		if (product.getInsuranceCompany() != null && product.getInsuranceCompany().getId().equals(id))
			return;

		InsuranceCompany insuranceCompany = insuranceCompanyService.getById(id, getInstanceName());
		if (insuranceCompany == null)
			throw new ResourceNotFoundBusinessException("� ������� �� ������� ��������� �������� � id: " + id, InsuranceCompany.class);

		product.setInsuranceCompany(insuranceCompany);
	}

	/**
	 * ������� ���������� � ��������
	 */
	public void clearPeriodsInformation()
	{
		getEntity().clearPeriodInformation();
	}

	/**
	 * ������ ������ ����� ��������
	 * @param periodTypeIds �������������� ����� ��������
	 * @throws BusinessException
	 */
	public void setPeriodTypes(Long[] periodTypeIds) throws BusinessException
	{
		periodTypes = new HashMap<Long, PeriodType>();
		for (PeriodType type: periodTypeService.getListByIds(periodTypeIds, getInstanceName()))
			periodTypes.put(type.getId(), type);
	}

	/**
	 * �������� ���������� � ���� �������
	 * @param isDefault ��������� ��������
	 * @param typeId ������������� ���� �������
	 * @param period  ������
	 * @param minSum  ����������� �����
	 * @param maxSum  ������������ �����
	 */
	public void addPeriodTypeInfo(boolean isDefault, Long typeId, String period,
	                              BigDecimal minSum, BigDecimal maxSum)
	{
		InsuranceDatePeriod datePeriod = new InsuranceDatePeriod();
		datePeriod.setDefaultPeriod(isDefault);
		datePeriod.setType(periodTypes.get(typeId));
		datePeriod.setPeriod(period);
		datePeriod.setMinSum(minSum);
		datePeriod.setMaxSum(maxSum);
		getEntity().addPeriodInformation(datePeriod);
	}

	@Override
	protected void doSave(InsuranceProduct product) throws BusinessException
	{
		if (product.isForComplex())
		{
			product.setNewTargetGroup(Collections.<SegmentCodeType>emptySet());
			product.setUniversal(false);
		}
		super.doSave(product);
	}

	/**
	 * @return ������ ���� ����� ��������� ���������
	 * @throws BusinessException
	 */
	public List<InsuranceType> getInsuranceType() throws BusinessException
	{
		return insuranceTypeService.getAll(getInstanceName());
	}

	/**
	 * ����������, ����� �� ��������� �������
	 * @param newForComplex - ����� �������� ����� "��� ������������ ��������"
	 * @return true - �����/false - ������
	 * @throws BusinessException
	 */
	public boolean canSave(boolean newForComplex) throws BusinessException
	{
		InsuranceProduct product = getEntity();
		return product.getId()== null || newForComplex || !complexProductService.containsInComplexProduct(product, getInstanceName());
	}

}
