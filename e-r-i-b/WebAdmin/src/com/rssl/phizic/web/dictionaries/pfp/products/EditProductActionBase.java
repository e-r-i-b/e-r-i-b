package com.rssl.phizic.web.dictionaries.pfp.products;

import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.EditProductOperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author akrenev
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditProductActionBase extends ImageEditActionBase
{
	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		ProductBase product = (ProductBase) entity;
		product.setName((String) data.get("name"));
		product.setDescription((String) data.get("description"));
		product.setMinIncome((BigDecimal) data.get(PFPProductEditFormHelper.MIN_INCOME_FIELD_NAME));
		product.setMaxIncome((BigDecimal) data.get(PFPProductEditFormHelper.MAX_INCOME_FIELD_NAME));
		product.setDefaultIncome((BigDecimal) data.get(PFPProductEditFormHelper.DEFAULT_INCOME_FIELD_NAME));
		
		product.setUniversal((Boolean) data.get(EditProductFormBase.UNIVERSAL_PARAMETER_NAME));
		product.setEnabled((Boolean) data.get(EditProductFormBase.ENABLED_PARAMETER_NAME));
		Set<SegmentCodeType> targetGroup = new HashSet<SegmentCodeType>();
		for (SegmentCodeType segment : SegmentCodeType.values())
			if (data.get(PFPProductEditFormHelper.TARGET_GROUP_FIELD_NAME_PREFIX + segment.name()) != null)
				targetGroup.add(segment);

		product.setNewTargetGroup(targetGroup);
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		ProductBase product = (ProductBase) entity;
		frm.setField("name",        product.getName());
		frm.setField("description", product.getDescription());
		frm.setField(EditProductFormBase.UNIVERSAL_PARAMETER_NAME, product.isUniversal());
		frm.setField(EditProductFormBase.ENABLED_PARAMETER_NAME, product.isEnabled());
		frm.setField(PFPProductEditFormHelper.MIN_INCOME_FIELD_NAME, product.getMinIncome());
		frm.setField(PFPProductEditFormHelper.MAX_INCOME_FIELD_NAME, product.getMaxIncome());
		frm.setField(PFPProductEditFormHelper.DEFAULT_INCOME_FIELD_NAME, product.getDefaultIncome());
		Set<SegmentCodeType> targetGroup = product.getTargetGroup();
		if (CollectionUtils.isNotEmpty(targetGroup))
			for (SegmentCodeType segment : targetGroup)
				frm.setField(PFPProductEditFormHelper.TARGET_GROUP_FIELD_NAME_PREFIX + segment.name(), segment.name());

		TableViewParameters tableParameters = product.getTableParameters();
		Map<TableColumn, String> columns = tableParameters.getColumns();
		frm.setField(PFPProductEditFormHelper.USE_ICON_PARAMETER_NAME, tableParameters.isUseIcon());
		for (Map.Entry<TableColumn, String> entry : columns.entrySet())
		{
			frm.setField(PFPProductEditFormHelper.TABLE_PARAMETER_NAME_PREFIX + entry.getKey().getOrderIndex(), entry.getValue());
		}
		frm.setField(PFPProductEditFormHelper.AXIS_X_PARAMETER_NAME, product.getAxisX());
		frm.setField(PFPProductEditFormHelper.AXIS_Y_PARAMETER_NAME, product.getAxisY());
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}

	protected void setViewParametersToEntity(EditProductOperationBase operation, Map<String, Object> data)
	{
		ProductBase product = operation.getEntity();

		ProductTypeParameters productTypeParameters = operation.getProductTypeParameters();
		if(productTypeParameters != null)
		{
			if(productTypeParameters.isUseOnTable())
			{
				TableViewParameters tableParameters = product.getTableParameters();
				Map<TableColumn, String> columns = tableParameters.getColumns();
				tableParameters.setUseIcon((Boolean) data.get(PFPProductEditFormHelper.USE_ICON_PARAMETER_NAME));
				for(TableColumn column : productTypeParameters.getTableParameters().getColumns())
				{
					String value = (String)data.get(PFPProductEditFormHelper.TABLE_PARAMETER_NAME_PREFIX + column.getOrderIndex());
					columns.put(column, value);
				}
			}
	        if(productTypeParameters.isUseOnDiagram())
	        {
				product.setAxisX((Long) data.get(PFPProductEditFormHelper.AXIS_X_PARAMETER_NAME));
				product.setAxisY((Long) data.get(PFPProductEditFormHelper.AXIS_Y_PARAMETER_NAME));
	        }
		}
	}

	protected void clearViewParametersToEntity(ProductBase product)
	{
		TableViewParameters tableParameters = product.getTableParameters();
		tableParameters.getColumns().clear();
		tableParameters.setUseIcon(false);
		product.setAxisX(null);
		product.setAxisY(null);
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		setViewParametersToEntity((EditProductOperationBase) editOperation, validationResult);
		updateOperationImageData(editOperation, editForm, validationResult);
	}
}