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
	 * задать тип страховой программы
	 * @param id идентификатор типа
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void setType(Long id) throws BusinessException, BusinessLogicException
	{
		InsuranceProduct product = getEntity();
		if (product.getType() != null && product.getType().getId().equals(id))
			return;

		if (insuranceTypeService.hasChild(id, getInstanceName()))
			throw new BusinessLogicException("Невозможно добавить тип страховой программы.");

		InsuranceType parentInsuranceType = insuranceTypeService.getById(id, getInstanceName());
		if (parentInsuranceType == null)
			throw new BusinessLogicException("В системе не найден тип страховой программы с id: " + id);

		product.setType(parentInsuranceType);
	}

	/**
	 * задать страховую компанию
	 * @param id идентификатор страховой компании
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
			throw new ResourceNotFoundBusinessException("В системе не найдена страховая компания с id: " + id, InsuranceCompany.class);

		product.setInsuranceCompany(insuranceCompany);
	}

	/**
	 * Очистка информации о периодах
	 */
	public void clearPeriodsInformation()
	{
		getEntity().clearPeriodInformation();
	}

	/**
	 * задать список типов периодов
	 * @param periodTypeIds идентификаторы типов периодов
	 * @throws BusinessException
	 */
	public void setPeriodTypes(Long[] periodTypeIds) throws BusinessException
	{
		periodTypes = new HashMap<Long, PeriodType>();
		for (PeriodType type: periodTypeService.getListByIds(periodTypeIds, getInstanceName()))
			periodTypes.put(type.getId(), type);
	}

	/**
	 * Добавить информацию о типе периода
	 * @param isDefault дефолтное значение
	 * @param typeId идентификатор типа периода
	 * @param period  период
	 * @param minSum  минимальная сумма
	 * @param maxSum  максимальная сумма
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
	 * @return список всех типов страховых продуктов
	 * @throws BusinessException
	 */
	public List<InsuranceType> getInsuranceType() throws BusinessException
	{
		return insuranceTypeService.getAll(getInstanceName());
	}

	/**
	 * определяем, можно ли сохранить продукт
	 * @param newForComplex - новое значение флага "для комплексного продукта"
	 * @return true - можно/false - нельзя
	 * @throws BusinessException
	 */
	public boolean canSave(boolean newForComplex) throws BusinessException
	{
		InsuranceProduct product = getEntity();
		return product.getId()== null || newForComplex || !complexProductService.containsInComplexProduct(product, getInstanceName());
	}

}
