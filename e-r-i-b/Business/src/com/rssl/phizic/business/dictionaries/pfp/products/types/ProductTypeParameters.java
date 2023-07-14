package com.rssl.phizic.business.dictionaries.pfp.products.types;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ���������� ����� ��������� ���
 */

public class ProductTypeParameters  extends PFPDictionaryRecord
{
	private Long id;
	private DictionaryProductType type;
	private String name;
	private boolean use;
	private List<SegmentCodeType> targetGroup;
	private Long imageId;
	private String description;
	private boolean useOnDiagram;
	private boolean useOnTable;
	private DiagramParameters diagramParameters;
	private TableParameters tableParameters;
	private ProductTypeLink link;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ��������
	 */
	public DictionaryProductType getType()
	{
		return type;
	}

	/**
	 * ������ ��� ��������
	 * @param type ���
	 */
	public void setType(DictionaryProductType type)
	{
		this.type = type;
	}

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������������ �� ��� �������� (true - ������������)
	 */
	public boolean isUse()
	{
		return use;
	}

	/**
	 * ������ ������� ������������� ���� ��������
	 * @param use ������������ �� ��� �������� (true - ������������)
	 */
	public void setUse(boolean use)
	{
		this.use = use;
	}

	/**
	 * @return �������� ��������, ��� ������� �������� ��� ��������
	 */
	public List<SegmentCodeType> getTargetGroup()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return targetGroup;
	}

	/**
	 * ������ �������� ��������, ��� ������� �������� ��� ��������
	 * @param targetGroup �������� ��������
	 */
	public void setTargetGroup(List<SegmentCodeType> targetGroup)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.targetGroup = targetGroup;
	}

	/**
	 * @return ������������� ��������
	 */
	public Long getImageId()
	{
		return imageId;
	}

	/**
	 * ��������� ������ ��� ����������
	 * @return ���� ������
	 */
	public Comparable getSynchKey()
	{
		return type.name();
	}

	/**
	 * �������� ���������� ������ �� ������
	 * @param that ������ �� ������� ���� ��������
	 */
	public void updateFrom(DictionaryRecord that)
	{
		setName(((ProductTypeParameters) that).getName());
		setUse(((ProductTypeParameters) that).isUse());
		setType(((ProductTypeParameters) that).getType());
		setTargetGroup(((ProductTypeParameters) that).getTargetGroup());
		setImageId(((ProductTypeParameters) that).getImageId());
		setDescription(((ProductTypeParameters) that).getDescription());
		setUseOnDiagram(((ProductTypeParameters) that).isUseOnDiagram());
		setUseOnTable(((ProductTypeParameters) that).isUseOnTable());
		setDiagramParameters(getUpdatedDiagramParameters(((ProductTypeParameters) that).getDiagramParameters(), getDiagramParameters()));
		setTableParameters(getUpdatedTableParameters(((ProductTypeParameters) that).getTableParameters(), getTableParameters()));
		setLink(((ProductTypeParameters) that).getLink());
	}

	private TableColumn getUpdatedTableColumn(TableColumn source, TableColumn destination)
	{
		destination.setValue(source.getValue());
		destination.setOrderIndex(source.getOrderIndex());
		return destination;
	}

	private TableParameters getUpdatedTableParameters(TableParameters source, TableParameters destination)
	{
		if (source == null)
			return null;

		TableParameters updatedParameters = destination;
		if (updatedParameters == null)
		{
			updatedParameters = new TableParameters();
			updatedParameters.setColumns(new ArrayList<TableColumn>());
		}

		List<TableColumn> sourceColumns = source.getColumns();
		List<TableColumn> updatedParametersColumns = updatedParameters.getColumns();

		updatedParametersColumns.clear();
		for (TableColumn sourceColumn : sourceColumns)
			updatedParametersColumns.add(sourceColumn);

		return updatedParameters;
	}

	private DiagramAxis getNewDiagramAxis()
	{
		DiagramAxis updatedAxis = new DiagramAxis();
		updatedAxis.setSteps(new ArrayList<DiagramStep>());
		return updatedAxis;
	}

	private DiagramAxis getUpdatedAxis(DiagramAxis source, DiagramAxis destination)
	{
		if (source == null)
			return null;

		DiagramAxis updatedAxis = destination;
		if (updatedAxis == null)
			updatedAxis = getNewDiagramAxis();

		updatedAxis.clear();
		updatedAxis.setName(source.getName());
		updatedAxis.setUseSteps(source.getUseSteps());
		if (CollectionUtils.isNotEmpty(source.getSteps()))
			updatedAxis.getSteps().addAll(source.getSteps());
		return updatedAxis;
	}

	private DiagramParameters getUpdatedDiagramParameters(DiagramParameters source, DiagramParameters destination)
	{
		if (source == null)
			return null;

		DiagramParameters updatedParameters = destination;
		if (updatedParameters == null)
			updatedParameters = new DiagramParameters();

		updatedParameters.setUseZero(source.getUseZero());
		updatedParameters.setAxisX(getUpdatedAxis(source.getAxisX(), updatedParameters.getAxisX()));
		updatedParameters.setAxisY(getUpdatedAxis(source.getAxisY(), updatedParameters.getAxisY()));

		return updatedParameters;
	}

	/**
	 * ������ ������������� ��������
	 * @param imageId �������������
	 */
	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ������ ��������
	 * @param description ��������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ������������ �� ��� �������� �� ������� (true - ������������)
	 */
	public boolean isUseOnDiagram()
	{
		return useOnDiagram;
	}

	/**
	 * ������ ������� ������������� ���� �������� �� �������
	 * @param useOnDiagram ������������ �� ��� �������� �� ������� (true - ������������)
	 */
	public void setUseOnDiagram(boolean useOnDiagram)
	{
		this.useOnDiagram = useOnDiagram;
	}

	/**
	 * @return ������������ �� ��� �������� � ������� (true - ������������)
	 */
	public boolean isUseOnTable()
	{
		return useOnTable;
	}

	/**
	 * ������ ������� ������������� ���� �������� � �������
	 * @param useOnTable ������������ �� ��� �������� � ������� (true - ������������)
	 */
	public void setUseOnTable(boolean useOnTable)
	{
		this.useOnTable = useOnTable;
	}

	/**
	 * @return ��������� ��������
	 */
	public DiagramParameters getDiagramParameters()
	{
		return diagramParameters;
	}

	/**
	 * ������ ��������� ��������
	 * @param diagramParameters ���������
	 */
	public void setDiagramParameters(DiagramParameters diagramParameters)
	{
		this.diagramParameters = diagramParameters;
	}

	/**
	 * @return ��������� �������
	 */
	public TableParameters getTableParameters()
	{
		return tableParameters;
	}

	/**
	 * ������ ��������� �������
	 * @param tableParameters ��������� �������
	 */
	public void setTableParameters(TableParameters tableParameters)
	{
		this.tableParameters = tableParameters;
	}

	/**
	 * @return ������, ����������� ������� ������� �����
	 */
	public ProductTypeLink getLink()
	{
		return link;
	}

	/**
	 * ������ ������, ����������� ������� ������� �����
	 * @param link ������
	 */
	public void setLink(ProductTypeLink link)
	{
		this.link = link;
	}
}
