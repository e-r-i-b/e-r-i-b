package com.rssl.phizic.business.dictionaries.pfp.products;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author akrenev
 * @ created 12.04.2012
 * @ $Author$
 * @ $Revision$
 *  ������� ����� ���������
 */
public abstract class ProductBase extends PFPDictionaryRecord
{
	private String name;        // ������������
	private String description; // ��������
	private Long imageId;       // ������������� ��������
	private BigDecimal minIncome;       //����������� ����������
	private BigDecimal maxIncome;       //������������ ����������
	private BigDecimal defaultIncome;   //���������� �� ���������
	private Set<SegmentCodeType> targetGroup; //�������
	private Long axisX;
	private Long axisY;
	private TableViewParameters tableParameters; // ��������� ����������� � ���� �������
	private boolean universal;
	private boolean enabled = true;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public BigDecimal getMinIncome()
	{
		return minIncome;
	}

	public void setMinIncome(BigDecimal minIncome)
	{
		this.minIncome = minIncome;
	}

	public BigDecimal getMaxIncome()
	{
		return maxIncome;
	}

	public void setMaxIncome(BigDecimal maxIncome)
	{
		this.maxIncome = maxIncome;
	}

	public BigDecimal getDefaultIncome()
	{
		return defaultIncome;
	}

	public void setDefaultIncome(BigDecimal defaultIncome)
	{
		this.defaultIncome = defaultIncome;
	}

	public Set<SegmentCodeType> getTargetGroup()
	{
		return targetGroup;
	}

	public void setTargetGroup(Set<SegmentCodeType> targetGroup)
	{
		this.targetGroup = targetGroup;
	}

	/**
	 * ������ ����� �������
	 * @param targetGroup ����� �������
	 */
	public void setNewTargetGroup(Set<SegmentCodeType> targetGroup)
	{
		if (this.targetGroup == null)
		{
			this.targetGroup = targetGroup;
			return;
		}
		this.targetGroup.clear();
		if (CollectionUtils.isNotEmpty(targetGroup))
			this.targetGroup.addAll(targetGroup);
	}

	/**
	 * @return ��������� ����������� �������� � ��������� ����
	 */
	public TableViewParameters getTableParameters()
	{
		return tableParameters;
	}

	/**
	 * @param tableParameters ��������� ����������� �������� � ��������� ����
	 */
	public void setTableParameters(TableViewParameters tableParameters)
	{
		this.tableParameters = tableParameters;
	}

	/**
	 * @return ���������� x
	 */
	public Long getAxisX()
	{
		return axisX;
	}

	/**
	 * @param axisX ���������� x
	 */
	public void setAxisX(Long axisX)
	{
		this.axisX = axisX;
	}

	/**
	 * @return ���������� y
	 */
	public Long getAxisY()
	{
		return axisY;
	}

	/**
	 * @param axisY ���������� y
	 */
	public void setAxisY(Long axisY)
	{
		this.axisY = axisY;
	}

	/**
	 * @return ������� �������� ������������� ��/���(true/false)
	 */
	public boolean isUniversal()
	{
		return universal;
	}

	/**
	 * @param universal ������� �������� ������������� ��/���(true/false)
	 */
	public void setUniversal(boolean universal)
	{
		this.universal = universal;
	}

	/**
	 * @return ������������ �� ������� �������� ��/���(true/false)
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * @param enabled ������������ �� ������� �������� ��/���(true/false)
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public Comparable getSynchKey()
	{
		return getName();
	}

	public void updateFrom(DictionaryRecord that)
	{
		ProductBase	source = (ProductBase) that;
		setName(source.getName());
		setDescription(source.getDescription());
		setImageId(source.getImageId());
		setMinIncome(source.getMinIncome());
		setMaxIncome(source.getMaxIncome());
		setDefaultIncome(source.getDefaultIncome());
		setNewTargetGroup(source.getTargetGroup());
		setTableParameters(getUpdatedTableParameters(source.getTableParameters(), getTableParameters()));
		setAxisX(source.getAxisX());
		setAxisY(source.getAxisY());
		setUniversal(source.isUniversal());
		setEnabled(source.isEnabled());
	}

	private TableViewParameters clearTableParameters(TableViewParameters destination)
	{
		if (destination == null)
			return null;

		if (MapUtils.isNotEmpty(destination.getColumns()))
			destination.getColumns().clear();

		return destination;
	}

	private TableViewParameters getUpdatedTableParameters(TableViewParameters source, TableViewParameters destination)
	{
		if (source == null)
			return clearTableParameters(destination);

		TableViewParameters updatedParameters = destination;
		if (updatedParameters == null)
		{
			updatedParameters = new TableViewParameters();
			updatedParameters.setColumns(new HashMap<TableColumn, String>());
		}

		Map<TableColumn, String> updatedParametersColumns = updatedParameters.getColumns();
		updatedParametersColumns.clear();
		updatedParametersColumns.putAll(source.getColumns());
		return updatedParameters;
	}

	/**
	 * @return ��� ��������
	 */
	public abstract DictionaryProductType getProductType();
}
