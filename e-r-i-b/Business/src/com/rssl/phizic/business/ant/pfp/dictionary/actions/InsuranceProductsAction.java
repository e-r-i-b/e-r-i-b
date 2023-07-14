package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ant.pfp.dictionary.PFPDictionaryRecordWrapper;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.*;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга страхового продукта из xml
 */
public class InsuranceProductsAction extends ProductsAction<InsuranceProduct>
{
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> periodTypes;
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> insuranceCompanies;
	private Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>> insuranceTypes;
	private List<InsuranceType> insuranceTypesChilds;

	public void initialize(Map<PFPDictionaryConfig, Map<String, PFPDictionaryRecordWrapper<PFPDictionaryRecord>>> allRecords)
	{
		periodTypes = allRecords.get(PFPDictionaryConfig.periodTypes);
		insuranceCompanies = allRecords.get(PFPDictionaryConfig.insuranceCompanies);
		insuranceTypes = allRecords.get(PFPDictionaryConfig.insuranceTypes);

		//получае список всех "листов" дерева типов, тк добавляем только к ним
		insuranceTypesChilds = new ArrayList<InsuranceType>();
		List<InsuranceType> parentsInsuranceTypeList = new ArrayList<InsuranceType>();
		for (PFPDictionaryRecordWrapper<PFPDictionaryRecord> recordWrapper : insuranceTypes.values())
		{
			InsuranceType type = (InsuranceType) recordWrapper.getRecord();
			insuranceTypesChilds.add(type);
			if (type.getParent() != null)
			{
				parentsInsuranceTypeList.add(type.getParent());
			}
		}
		insuranceTypesChilds.removeAll(parentsInsuranceTypeList);
	}

	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		String description = XmlHelper.getSimpleElementValue(element, "description");
		String insuranceCompanyKey = XmlHelper.getSimpleElementValue(element, "insuranceCompany");
		String typeKey = XmlHelper.getSimpleElementValue(element, "insuranceType");
		String minIncome = XmlHelper.getSimpleElementValue(element, "minIncome");
		String maxIncome = XmlHelper.getSimpleElementValue(element, "maxIncome");
		String defaultIncome = XmlHelper.getSimpleElementValue(element, "defaultIncome");
		String forComplex = XmlHelper.getSimpleElementValue(element, "forComplex");
		String minAge = XmlHelper.getSimpleElementValue(element, "minAge");
		String maxAge = XmlHelper.getSimpleElementValue(element, "maxAge");

		//получение страховой компании
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> insuranceCompanyWrapper = insuranceCompanies.get(insuranceCompanyKey);
		if (insuranceCompanyWrapper == null)
			throw new BusinessLogicException("Невозможно добавить страховую компанию с ключом " + insuranceCompanyKey);
		PFPDictionaryRecord insuranceCompany = insuranceCompanyWrapper.getRecord();
		if (!(insuranceCompany instanceof InsuranceCompany))
			throw new BusinessLogicException("Невозможно добавить страховую компанию с ключом " + insuranceCompanyKey);

		//получение типа страхового продукта
		PFPDictionaryRecordWrapper<PFPDictionaryRecord> typeWrapper = insuranceTypes.get(typeKey);
		if (typeWrapper == null)
			throw new BusinessLogicException("Невозможно добавить тип страховки с ключом " + typeKey);
		PFPDictionaryRecord type = typeWrapper.getRecord();
		//noinspection SuspiciousMethodCalls
		if (!(type instanceof InsuranceType && insuranceTypesChilds.contains(type)))
			throw new BusinessLogicException("Невозможно добавить тип страховки с ключом " + typeKey);

		//добавление периодов
		final List<InsuranceDatePeriod> periods = new ArrayList<InsuranceDatePeriod>();
		try
		{
			Element periodsElement = XmlHelper.selectSingleNode(element, "periods");
			XmlHelper.foreach(periodsElement, "period", new ForeachElementAction()
			{
				public void execute(Element element) throws BusinessLogicException
				{
					//получение периода
					InsuranceDatePeriod insuranceDatePeriod = new InsuranceDatePeriod();
					Boolean defaultPeriod = getBooleanValue(XmlHelper.getSimpleElementValue(element, "defaultPeriod"));
					String periodTypeKey = XmlHelper.getSimpleElementValue(element, "periodType");

					PFPDictionaryRecordWrapper<PFPDictionaryRecord> periodTypeWrapper = periodTypes.get(periodTypeKey);
					if (periodTypeWrapper == null)
						throw new BusinessLogicException("Невозможно добавить тип периода с ключом " + periodTypeKey);
					PFPDictionaryRecord periodType = periodTypeWrapper.getRecord();
					if (!(periodType instanceof PeriodType))
						throw new BusinessLogicException("Невозможно добавить тип периода с ключом " + periodTypeKey);

					String minSum = XmlHelper.getSimpleElementValue(element, "minSum");
					String maxSum = XmlHelper.getSimpleElementValue(element, "maxSum");
					String period = XmlHelper.getSimpleElementValue(element, "period");

					//создание периода
					insuranceDatePeriod.setDefaultPeriod(defaultPeriod);
					insuranceDatePeriod.setType((PeriodType) periodType);
					insuranceDatePeriod.setMinSum(getBigDecimalValue(minSum));
					insuranceDatePeriod.setMaxSum(getBigDecimalValue(maxSum));
					insuranceDatePeriod.setPeriod(period);

					periods.add(insuranceDatePeriod);
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		//создание сущности
		InsuranceProduct product = new InsuranceProduct();
		product.setName(name);
		product.setDescription(description);
		product.setInsuranceCompany((InsuranceCompany) insuranceCompany);
		product.setType((InsuranceType) type);
		product.setForComplex(getBooleanValue(forComplex));
		product.setTargetGroup(super.getTargetGroup(element));
		product.setMinAge(getLongValue(minAge));
		product.setMaxAge(getLongValue(maxAge));
		product.setPeriods(periods);
		product.setMinIncome(getBigDecimalValue(minIncome));
		product.setMaxIncome(getBigDecimalValue(maxIncome));
		product.setDefaultIncome(getBigDecimalValue(defaultIncome));
		addRecord(key, product);
	}
}
