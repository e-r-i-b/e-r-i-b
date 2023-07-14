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
 * риск-профиль
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
	 * конструктор
	 */
	public RiskProfile()
	{}

	/**
	 * копирующий конструктор
	 * @param riskProfile источник данных
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
	 * @return наименование риск-профил€
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать наименование риск-профил€
	 * @param name наименование риск-профил€
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return сегмент клиентов, которому предназначен риск-профиль
	 */
	public SegmentCodeType getSegment()
	{
		return segment;
	}

	/**
	 * задать сегмент клиентов, которому предназначен риск-профиль
	 * @param segment сегмент
	 */
	public void setSegment(SegmentCodeType segment)
	{
		this.segment = segment;
	}

	/**
	 * @return нижн€€ граница профил€
	 */
	public Long getMinWeight()
	{
		return minWeight;
	}

	/**
	 * задать нижнюю границу профил€
	 * @param minWeight нижн€€ граница профил€
	 */
	public void setMinWeight(Long minWeight)
	{
		this.minWeight = minWeight;
	}

	/**
	 * @return верхн€€ граница профил€
	 */
	public Long getMaxWeight()
	{
		return maxWeight;
	}

	/**
	 * задать верхнюю границу профил€
	 * @param maxWeight верхн€€ граница профил€
	 */
	public void setMaxWeight(Long maxWeight)
	{
		this.maxWeight = maxWeight;
	}

	/**
	 * @return описание профил€
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * задать описание профил€
	 * @param description описание профил€
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return процентное соотношение по продуктам
	 */
	@SuppressWarnings({"JpaAttributeTypeInspection", "ReturnOfCollectionOrArrayField"})
	public Map<ProductType, Long> getProductsWeights()
	{
		return productsWeights;
	}

	/**
	 * задать процентное соотношение по продуктам
	 * @param productsWeights процентное соотношение по продуктам
	 */
	public void setProductsWeights(Map<ProductType, Long> productsWeights)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.productsWeights = productsWeights;
	}

	/**
	 * обновить процентное соотношение по продуктам
	 * @param productsWeights процентное соотношение по продуктам
	 */
	public void updateProductsWeights(Map<ProductType, Long> productsWeights)
	{
		this.productsWeights.clear();
		this.productsWeights.putAll(productsWeights);
	}

	/**
	 * @return удален ли риск-профиль
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
	 * @return дефолтный ли риск-профиль
	 */
	public boolean getIsDefault()
	{
		return isDefault;
	}

	/**
	 * задать признак дефолтности сущности
	 * @param isDefault признак дефолтности сущности
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
