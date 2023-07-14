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
 * Сущность параметров типов продуктов ПФП
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
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return тип продукта
	 */
	public DictionaryProductType getType()
	{
		return type;
	}

	/**
	 * задать тип продукта
	 * @param type тип
	 */
	public void setType(DictionaryProductType type)
	{
		this.type = type;
	}

	/**
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return использовать ли тип продукта (true - использовать)
	 */
	public boolean isUse()
	{
		return use;
	}

	/**
	 * задать признак использования типа продукта
	 * @param use использовать ли тип продукта (true - использовать)
	 */
	public void setUse(boolean use)
	{
		this.use = use;
	}

	/**
	 * @return сегменты клиентов, для которых доступен тип продукта
	 */
	public List<SegmentCodeType> getTargetGroup()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return targetGroup;
	}

	/**
	 * задать сегменты клиентов, для которых доступен тип продукта
	 * @param targetGroup сегменты клиентов
	 */
	public void setTargetGroup(List<SegmentCodeType> targetGroup)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.targetGroup = targetGroup;
	}

	/**
	 * @return идентификатор картинки
	 */
	public Long getImageId()
	{
		return imageId;
	}

	/**
	 * необходим только при репликации
	 * @return ключ записи
	 */
	public Comparable getSynchKey()
	{
		return type.name();
	}

	/**
	 * Обновить содержимое записи из другой
	 * @param that запись из которой надо обновить
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
	 * задать идентификатор картинки
	 * @param imageId идентификатор
	 */
	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * задать описание
	 * @param description описание
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return использовать ли тип продукта на графике (true - использовать)
	 */
	public boolean isUseOnDiagram()
	{
		return useOnDiagram;
	}

	/**
	 * задать признак использования типа продукта на графике
	 * @param useOnDiagram использовать ли тип продукта на графике (true - использовать)
	 */
	public void setUseOnDiagram(boolean useOnDiagram)
	{
		this.useOnDiagram = useOnDiagram;
	}

	/**
	 * @return использовать ли тип продукта в таблице (true - использовать)
	 */
	public boolean isUseOnTable()
	{
		return useOnTable;
	}

	/**
	 * задать признак использования типа продукта в таблице
	 * @param useOnTable использовать ли тип продукта в таблице (true - использовать)
	 */
	public void setUseOnTable(boolean useOnTable)
	{
		this.useOnTable = useOnTable;
	}

	/**
	 * @return параметры диаграмы
	 */
	public DiagramParameters getDiagramParameters()
	{
		return diagramParameters;
	}

	/**
	 * задать параметры диаграмы
	 * @param diagramParameters параметры
	 */
	public void setDiagramParameters(DiagramParameters diagramParameters)
	{
		this.diagramParameters = diagramParameters;
	}

	/**
	 * @return параметры таблицы
	 */
	public TableParameters getTableParameters()
	{
		return tableParameters;
	}

	/**
	 * задать параметры таблицы
	 * @param tableParameters параметры таблицы
	 */
	public void setTableParameters(TableParameters tableParameters)
	{
		this.tableParameters = tableParameters;
	}

	/**
	 * @return ссылка, позволяющая выбрать продукт позже
	 */
	public ProductTypeLink getLink()
	{
		return link;
	}

	/**
	 * задать ссылку, позволяющую выбрать продукт позже
	 * @param link ссылка
	 */
	public void setLink(ProductTypeLink link)
	{
		this.link = link;
	}
}
