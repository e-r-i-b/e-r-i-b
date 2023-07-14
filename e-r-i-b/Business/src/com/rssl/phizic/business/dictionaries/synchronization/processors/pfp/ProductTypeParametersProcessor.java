package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.types.*;
import com.rssl.phizic.common.types.SegmentCodeType;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей типов продуктов
 */

public class ProductTypeParametersProcessor extends PFPProcessorBase<ProductTypeParameters>
{
	private static final ProductTypeParametersService productTypeParametersService = new ProductTypeParametersService();

	@Override
	protected Class<ProductTypeParameters> getEntityClass()
	{
		return ProductTypeParameters.class;
	}

	private DiagramAxis getNewAxis()
	{
		DiagramAxis diagramAxis = new DiagramAxis();
		diagramAxis.setSteps(new ArrayList<DiagramStep>());
		return diagramAxis;
	}

	@Override
	protected ProductTypeParameters getNewEntity()
	{
		ProductTypeParameters productTypeParameters = new ProductTypeParameters();
		productTypeParameters.setTargetGroup(new ArrayList<SegmentCodeType>());

		DiagramParameters diagramParameters = new DiagramParameters();
		diagramParameters.setAxisX(getNewAxis());
		diagramParameters.setAxisY(getNewAxis());
		productTypeParameters.setDiagramParameters(diagramParameters);

		TableParameters tableParameters = new TableParameters();
		tableParameters.setColumns(new ArrayList<TableColumn>());
		productTypeParameters.setTableParameters(tableParameters);

		productTypeParameters.setLink(new ProductTypeLink());

		return productTypeParameters;
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
			return clearDiagramParameters(destination);

		DiagramParameters updatedParameters = destination;
		if (updatedParameters == null)
			updatedParameters = new DiagramParameters();

		updatedParameters.setUseZero(source.getUseZero());
		updatedParameters.setAxisX(getUpdatedAxis(source.getAxisX(), updatedParameters.getAxisX()));
		updatedParameters.setAxisY(getUpdatedAxis(source.getAxisY(), updatedParameters.getAxisY()));
		return updatedParameters;
	}

	private void clearDiagramAxis(DiagramAxis destination)
	{
		if (destination == null)
			return;

		destination.setName(null);
		if (destination.getSteps() == null)
			return;

		destination.getSteps().clear();
	}

	private DiagramParameters clearDiagramParameters(DiagramParameters destination)
	{
		if (destination == null)
			return null;

		destination.setUseZero(false);
		clearDiagramAxis(destination.getAxisX());
		clearDiagramAxis(destination.getAxisY());
		return destination;
	}

	private TableColumn getUpdatedTableColumn(TableColumn source, TableColumn destination)
	{
		destination.setValue(source.getValue());
		destination.setOrderIndex(source.getOrderIndex());
		return destination;
	}

	private TableColumn getColumn(String uuid, List<TableColumn> destinationColumns)
	{
		for (TableColumn destinationColumn : destinationColumns)
		{
			if (uuid.equals(destinationColumn.getUuid()))
				return destinationColumn;
		}
		TableColumn tableColumn = new TableColumn();
		tableColumn.setUuid(uuid);
		return tableColumn;
	}

	private TableParameters clearTableParameters(TableParameters destination)
	{
		if (destination == null)
			return null;

		if (CollectionUtils.isNotEmpty(destination.getColumns()))
			destination.getColumns().clear();

		return destination;
	}

	private TableParameters getUpdatedTableParameters(TableParameters source, TableParameters destination)
	{
		if (source == null)
			return clearTableParameters(destination);

		TableParameters updatedParameters = destination;
		if (updatedParameters == null)
		{
			updatedParameters = new TableParameters();
			updatedParameters.setColumns(new ArrayList<TableColumn>());
		}

		List<TableColumn> sourceColumns = source.getColumns();
		List<TableColumn> updatedParametersColumns = updatedParameters.getColumns();
		List<TableColumn> resultColumns = new ArrayList<TableColumn>();

		for (TableColumn sourceColumn : sourceColumns)
			resultColumns.add(getUpdatedTableColumn(sourceColumn, getColumn(sourceColumn.getUuid(), updatedParametersColumns)));

		updatedParametersColumns.clear();
		updatedParametersColumns.addAll(resultColumns);
		return updatedParameters;
	}

	@Override
	protected void update(ProductTypeParameters source, ProductTypeParameters destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setType(source.getType());
		destination.setName(source.getName());
		destination.setUse(source.isUse());
		destination.getTargetGroup().clear();
		destination.getTargetGroup().addAll(source.getTargetGroup());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
		destination.setDescription(source.getDescription());
		destination.setUseOnDiagram(source.isUseOnDiagram());
		destination.setUseOnTable(source.isUseOnTable());

		destination.setDiagramParameters(getUpdatedDiagramParameters(source.getDiagramParameters(), destination.getDiagramParameters()));
		destination.setTableParameters(getUpdatedTableParameters(source.getTableParameters(), destination.getTableParameters()));

		ProductTypeLink destinationLink = destination.getLink();
		ProductTypeLink sourceLink = source.getLink();
		destinationLink.setName(sourceLink.getName());
		destinationLink.setHint(sourceLink.getHint());
	}

	@Override
	protected void doSave(ProductTypeParameters localEntity) throws BusinessException, BusinessLogicException
	{
		productTypeParametersService.updateRiskProfiles(localEntity);
		super.doSave(localEntity);
	}

	@Override
	protected void doRemove(ProductTypeParameters localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
