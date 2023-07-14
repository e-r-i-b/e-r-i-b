package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.pfp.common.MarkDeletedRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ����-�������
 */
public class RiskProfile extends PFPDictionaryRecord implements MarkDeletedRecord
{
	private String name;
	private SegmentCodeType segment;
	private Long minWeight;
	private Long maxWeight;
	private String description;
	private Map<ProductType, Long> productsWeights;
	private boolean deleted = false;
	private boolean isDefault = false;

	/**
	 * �����������
	 */
	public RiskProfile()
	{}

	/**
	 * ���������� �����������
	 * @param riskProfile �������� ������
	 */
	public RiskProfile(RiskProfile riskProfile)
	{
		setName(riskProfile.getName());
		setSegment(riskProfile.getSegment());
		setMinWeight(riskProfile.getMinWeight());
		setMaxWeight(riskProfile.getMaxWeight());
		setDescription(riskProfile.getDescription());
		setProductsWeights(new HashMap<ProductType, Long>(riskProfile.getProductsWeights()));
	}

	/**
	 * @return ������������ ����-�������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ������������ ����-�������
	 * @param name ������������ ����-�������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������� ��������, �������� ������������ ����-�������
	 */
	public SegmentCodeType getSegment()
	{
		return segment;
	}

	/**
	 * ������ ������� ��������, �������� ������������ ����-�������
	 * @param segment �������
	 */
	public void setSegment(SegmentCodeType segment)
	{
		this.segment = segment;
	}

	/**
	 * @return ������ ������� �������
	 */
	public Long getMinWeight()
	{
		return minWeight;
	}

	/**
	 * ������ ������ ������� �������
	 * @param minWeight ������ ������� �������
	 */
	public void setMinWeight(Long minWeight)
	{
		this.minWeight = minWeight;
	}

	/**
	 * @return ������� ������� �������
	 */
	public Long getMaxWeight()
	{
		return maxWeight;
	}

	/**
	 * ������ ������� ������� �������
	 * @param maxWeight ������� ������� �������
	 */
	public void setMaxWeight(Long maxWeight)
	{
		this.maxWeight = maxWeight;
	}

	/**
	 * @return �������� �������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ������ �������� �������
	 * @param description �������� �������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ���������� ����������� �� ���������
	 */
	@SuppressWarnings({"JpaAttributeTypeInspection", "ReturnOfCollectionOrArrayField"})
	public Map<ProductType, Long> getProductsWeights()
	{
		return productsWeights;
	}

	/**
	 * ������ ���������� ����������� �� ���������
	 * @param productsWeights ���������� ����������� �� ���������
	 */
	public void setProductsWeights(Map<ProductType, Long> productsWeights)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.productsWeights = productsWeights;
	}

	/**
	 * �������� ���������� ����������� �� ���������
	 * @param productsWeights ���������� ����������� �� ���������
	 */
	public void updateProductsWeights(Map<ProductType, Long> productsWeights)
	{
		this.productsWeights.clear();
		this.productsWeights.putAll(productsWeights);
	}

	/**
	 * @return ������ �� ����-�������
	 */
	public boolean isDeleted()
	{
		return deleted;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * @return ��������� �� ����-�������
	 */
	public boolean getIsDefault()
	{
		return isDefault;
	}

	/**
	 * ������ ������� ����������� ��������
	 * @param isDefault ������� ����������� ��������
	 */
	public void setIsDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	public Comparable getSynchKey()
	{
		return String.valueOf(isDeleted()) + segment + String.valueOf(minWeight) + String.valueOf(maxWeight);
	}

	public void updateFrom(DictionaryRecord that)
	{
		RiskProfile source = (RiskProfile) that;
		setName(source.getName());
		setSegment(source.getSegment());
		setDescription(source.getDescription());
		setDeleted(source.isDeleted());
		setMinWeight(source.getMinWeight());
		setMaxWeight(source.getMaxWeight());
		updateProductsWeights(source.getProductsWeights());
	}
}
