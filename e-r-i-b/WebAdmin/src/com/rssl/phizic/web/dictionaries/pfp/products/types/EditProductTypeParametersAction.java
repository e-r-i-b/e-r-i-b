package com.rssl.phizic.web.dictionaries.pfp.products.types;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.types.*;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.types.EditProductTypeParametersOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования параметров отображения типов продуктов в ПФП
 */

public class EditProductTypeParametersAction extends ImageEditActionBase
{
	private static final List<DictionaryProductType> availableChangeDiagramViewTypes =
			Arrays.asList(DictionaryProductType.ACCOUNT, DictionaryProductType.FUND,
						  DictionaryProductType.IMA, DictionaryProductType.TRUST_MANAGING);

	private static final List<DictionaryProductType> availableChangeTableViewTypes =
			Arrays.asList(DictionaryProductType.ACCOUNT, DictionaryProductType.FUND,
						  DictionaryProductType.IMA, DictionaryProductType.TRUST_MANAGING,
						  DictionaryProductType.COMPLEX_INSURANCE, DictionaryProductType.COMPLEX_INVESTMENT);

	protected EditProductTypeParametersOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditProductTypeParametersOperation operation = createOperation(EditProductTypeParametersOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditProductTypeParametersForm) frm).getForm();
	}

	private void updateAxis(DiagramAxis axis, String type, Map<String, Object> data)
	{
		axis.setName((String) data.get(type.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_NAME_FIELD_NAME)));
		Boolean useSteps = (Boolean) data.get(type.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_USE_FIELD_NAME));
		axis.setUseSteps(useSteps);
		List<DiagramStep> steps = axis.getSteps();
		steps.clear();
		if (!useSteps)
			return;

		long stepCount = (Long) data.get(type.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_COUNT_FIELD_NAME));
		for (int i = 0; i< stepCount; i++)
		{
			String index = String.valueOf(i);
			Long from = (Long) data.get(type.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_FROM_FIELD_NAME_PREFIX).concat(index));
			Long to =   (Long) data.get(type.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_TO_FIELD_NAME_PREFIX).concat(index));
			String name = (String) data.get(type.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_NAME_FIELD_NAME_PREFIX).concat(index));

			if (from != null && to != null && StringHelper.isNotEmpty(name))
			{
				DiagramStep step = new DiagramStep();
				step.setFrom(from);
				step.setTo(to);
				step.setName(name);
				steps.add(step);
			}
		}
	}

	private void updateDiagramParameters(ProductTypeParameters parameters, Map<String, Object> data)
	{
		parameters.setUseOnDiagram(true);
		DiagramParameters diagramParameters = parameters.getDiagramParameters();
		diagramParameters.setUseZero((Boolean) data.get(EditProductTypeParametersForm.DIAGRAM_AXIS_USE_ZERO_FIELD_NAME));
		updateAxis(diagramParameters.getAxisX(), EditProductTypeParametersForm.DIAGRAM_AXIS_TYPE_X, data);
		if (diagramParameters.getAxisY() == null)
			diagramParameters.setAxisY(new DiagramAxis());
		diagramParameters.getAxisY().setName((String) data.get(EditProductTypeParametersForm.DIAGRAM_AXIS_TYPE_Y.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_NAME_FIELD_NAME)));
	}

	private void clearDiagramParameters(ProductTypeParameters parameters)
	{
		parameters.setUseOnDiagram(false);
		DiagramParameters diagramParameters = parameters.getDiagramParameters();
		diagramParameters.setUseZero(false);
		diagramParameters.getAxisX().clear();
		if (diagramParameters.getAxisY() != null)
			diagramParameters.getAxisY().clear();
	}

	private void updateTableParameters(ProductTypeParameters parameters, Map<String, Object> data)
	{
		parameters.setUseOnTable(true);

		TableParameters tableParameters = parameters.getTableParameters();
		List<TableColumn> columns = tableParameters.getColumns();
		List<TableColumn> newColumns = new ArrayList<TableColumn>(columns);
		columns.clear();

		for (int i = 0; i < EditProductTypeParametersForm.TABLE_PARAMETERS_MAX_COLUMN_COUNT; i++)
		{
			Long orderIndex  = (Long) data.get(EditProductTypeParametersForm.TABLE_COLUMN_ORDER_FIELD_NAME_PREFIX.concat(String.valueOf(i)));
			if (orderIndex == null)
				return;
			String index = String.valueOf(orderIndex);
			String name = (String) data.get(EditProductTypeParametersForm.TABLE_COLUMN_NAME_FIELD_NAME_PREFIX.concat(index));
			Long   id   = (Long) data.get(EditProductTypeParametersForm.TABLE_COLUMN_ID_FIELD_NAME_PREFIX.concat(index));

			if (StringHelper.isNotEmpty(name))
			{
				TableColumn column = getNullSafeExistColumn(id, newColumns);
				column.setOrderIndex((long)columns.size());
				column.setValue(name);
				columns.add(column);
			}
		}
	}

	private TableColumn getNullSafeExistColumn(Long id, List<TableColumn> newColumns)
	{
		for(TableColumn column : newColumns)
		{
			if(column.getId().equals(id))
			{
				return column;
			}
		}
		return new TableColumn();
	}

	private void clearTableParameters(ProductTypeParameters parameters)
	{
		parameters.setUseOnTable(false);
		parameters.getTableParameters().clear();
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		ProductTypeParameters productTypeParameters = (ProductTypeParameters) entity;

		productTypeParameters.setName((String) data.get(EditProductTypeParametersForm.NAME_FIELD_NAME));
		productTypeParameters.setUse((Boolean) data.get(EditProductTypeParametersForm.USE_FIELD_NAME));

		List<SegmentCodeType> targetGroup = new ArrayList<SegmentCodeType>();
		for (SegmentCodeType segment : SegmentCodeType.values())
		{
			if (data.get(PFPProductEditFormHelper.TARGET_GROUP_FIELD_NAME_PREFIX + segment.name()) != null)
				targetGroup.add(segment);
		}
		productTypeParameters.setTargetGroup(targetGroup);

		productTypeParameters.setDescription((String) data.get(EditProductTypeParametersForm.DESCRIPTION_FIELD_NAME));
		ProductTypeLink link = productTypeParameters.getLink();
		link.setName((String) data.get(EditProductTypeParametersForm.LINK_NAME_FIELD_NAME));
		link.setHint((String) data.get(EditProductTypeParametersForm.LINK_HINT_FIELD_NAME));

		DictionaryProductType productType = productTypeParameters.getType();

		if (availableChangeDiagramViewTypes.contains(productType) && (Boolean) data.get(EditProductTypeParametersForm.USE_ON_DIAGRAM_FIELD_NAME))
			updateDiagramParameters(productTypeParameters, data);
		else
			clearDiagramParameters(productTypeParameters);

		if (availableChangeTableViewTypes.contains(productType) && (Boolean) data.get(EditProductTypeParametersForm.USE_ON_TABLE_FIELD_NAME))
			updateTableParameters(productTypeParameters, data);
		else
			clearTableParameters(productTypeParameters);

	}

	private void updateFormAxisInformation(EditFormBase frm, DiagramAxis axis, String axisType)
	{
		frm.setField(axisType.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_NAME_FIELD_NAME), axis.getName());
		frm.setField(axisType.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_USE_FIELD_NAME), axis.getUseSteps());
		List<DiagramStep> axisSteps = axis.getSteps();
		int axisSize = axisSteps.size();
		frm.setField(axisType.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_COUNT_FIELD_NAME), axisSize);
		for (int i = 0; i < axisSize; i++)
		{
			DiagramStep step = axisSteps.get(i);
			frm.setField(axisType.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_FROM_FIELD_NAME_PREFIX).concat(String.valueOf(i)), step.getFrom());
			frm.setField(axisType.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_TO_FIELD_NAME_PREFIX).concat(String.valueOf(i)),   step.getTo());
			frm.setField(axisType.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_STEP_NAME_FIELD_NAME_PREFIX).concat(String.valueOf(i)), step.getName());
		}
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		ProductTypeParameters productTypeParameters = (ProductTypeParameters) entity;
		
		frm.setField(EditProductTypeParametersForm.TYPE_FIELD_NAME, productTypeParameters.getType());
		frm.setField(EditProductTypeParametersForm.NAME_FIELD_NAME, productTypeParameters.getName());
		frm.setField(EditProductTypeParametersForm.USE_FIELD_NAME,  productTypeParameters.isUse());
		
		List<SegmentCodeType> targetGroup = productTypeParameters.getTargetGroup();
		for (SegmentCodeType segment : targetGroup)
			frm.setField(PFPProductEditFormHelper.TARGET_GROUP_FIELD_NAME_PREFIX + segment.name(), segment.name());

		frm.setField(EditProductTypeParametersForm.DESCRIPTION_FIELD_NAME,    productTypeParameters.getDescription());
		frm.setField(EditProductTypeParametersForm.USE_ON_DIAGRAM_FIELD_NAME, productTypeParameters.isUseOnDiagram());
		frm.setField(EditProductTypeParametersForm.USE_ON_TABLE_FIELD_NAME,   productTypeParameters.isUseOnTable());

		ProductTypeLink link = productTypeParameters.getLink();
		frm.setField(EditProductTypeParametersForm.LINK_NAME_FIELD_NAME, link.getName());
		frm.setField(EditProductTypeParametersForm.LINK_HINT_FIELD_NAME, link.getHint());

		if (productTypeParameters.isUseOnDiagram())
		{
			DiagramParameters diagramParameters = productTypeParameters.getDiagramParameters();
			frm.setField(EditProductTypeParametersForm.DIAGRAM_AXIS_USE_ZERO_FIELD_NAME, diagramParameters.getUseZero());
			updateFormAxisInformation(frm, diagramParameters.getAxisX(), EditProductTypeParametersForm.DIAGRAM_AXIS_TYPE_X);
			if (diagramParameters.getAxisY() != null)
				frm.setField(EditProductTypeParametersForm.DIAGRAM_AXIS_TYPE_Y.concat(EditProductTypeParametersForm.DIAGRAM_AXIS_NAME_FIELD_NAME), diagramParameters.getAxisY().getName());
		}

		if (productTypeParameters.isUseOnTable())
		{
			List<TableColumn> tableColumnNames = productTypeParameters.getTableParameters().getColumns();
			int size = tableColumnNames.size();
			frm.setField(EditProductTypeParametersForm.TABLE_COLUMN_NAME_COUNT_FIELD_NAME, size);
			for (TableColumn column : tableColumnNames)
			{
				frm.setField(EditProductTypeParametersForm.TABLE_COLUMN_NAME_FIELD_NAME_PREFIX + column.getOrderIndex(), column.getValue());
				frm.setField(EditProductTypeParametersForm.TABLE_COLUMN_ID_FIELD_NAME_PREFIX + column.getOrderIndex(), column.getId());
			}
		}
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
	}
}
