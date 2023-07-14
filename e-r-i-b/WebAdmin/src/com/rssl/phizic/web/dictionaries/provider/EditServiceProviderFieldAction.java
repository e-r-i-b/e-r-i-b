package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProviderFieldOperation;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.criterion.Order;

import java.math.BigInteger;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderFieldAction extends EditActionBase
{
	private static final String SEPARATOR = ",";    //–азделитель в RequisiteTypes

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditServiceProviderFieldForm frm = (EditServiceProviderFieldForm) form;
		EditServiceProviderFieldOperation operation = createOperation("EditServiceProviderFieldOperation");
		Long fieldId = frm.getFieldId();
		if (fieldId != null && fieldId != 0)
		{
			operation.initialize(fieldId);
		}
		else
		{
			operation.initializeNew(frm.getId());
		}
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditServiceProviderFieldForm frm = (EditServiceProviderFieldForm) form;
		EditServiceProviderFieldOperation operation = createOperation("ViewServiceProviderFieldOperation");
		Long fieldId = frm.getFieldId();
		if (fieldId != null && fieldId != 0)
		{
			operation.initialize(fieldId);
		}
		else
		{
			operation.initializeNew(frm.getId());
		}
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditServiceProviderFieldForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		FieldDescription description = (FieldDescription) entity;
		String externalId = (String) data.get("exteranlId");
		description.setExternalId(externalId);
		description.setName((String) data.get("name"));
		description.setDescription((String) data.get("description"));
		description.setHint((String) data.get("hint"));
		description.setType(FieldDataType.fromValue((String) data.get("type")));
		description.setMaxLength(NumericUtil.toLong((BigInteger) data.get("maxlength")));
		description.setMinLength(NumericUtil.toLong((BigInteger) data.get("minlength")));
		description.setNumberPrecision(NumericUtil.toInt((BigInteger) data.get("numberPrecision")));
		description.setRequired((Boolean) data.get("mandatory"));
		description.setEditable((Boolean) data.get("editable"));
		description.setVisible((Boolean) data.get("visible"));
		description.setMainSum((Boolean) data.get("sum"));
		description.setKey((Boolean) data.get("key"));
		description.setRequiredForBill((Boolean) data.get("isForBill"));
		description.setRequiredForConformation((Boolean) data.get("isIncludeInSMS"));
		description.setSaveInTemplate((Boolean) data.get("isSaveInTemplate"));
		description.setHideInConfirmation((Boolean) data.get("isHideInConfirmation"));
		description.setDefaultValue((String) data.get("defaultValue"));
		description.setValuesAsString((String) data.get("values"), FieldDescription.VALUES_SPLITER);
		description.setRequisiteTypes(getRequisiteTypes((String) data.get("requisiteTypes")));
		description.setMask((String) data.get("mask"));
		description.setExtendedDescId((String) data.get("extendedDescId"));
		String businessSubType = (String)data.get("businessSubType");
		description.setBusinessSubType(StringHelper.isNotEmpty(businessSubType) ? BusinessFieldSubType.valueOf(businessSubType) : null);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		FieldDescription description = (FieldDescription) entity;
		frm.setId(description.getHolderId());
		frm.setField("exteranlId", description.getExternalId());
		frm.setField("name", description.getName());
		frm.setField("description", description.getDescription());
		frm.setField("hint", description.getHint());
		if (description.getType() != null)
		{
			frm.setField("type", description.getType().name());
		}
		frm.setField("maxlength", description.getMaxLength());
		frm.setField("minlength", description.getMinLength());
		frm.setField("numberPrecision", description.getNumberPrecision());
		frm.setField("mandatory", description.isRequired());
		frm.setField("editable", description.isEditable());
		frm.setField("visible", description.isVisible());
		frm.setField("sum", description.isMainSum());
		frm.setField("key", description.isKey());
		frm.setField("isForBill",   description.isRequiredForBill());
		frm.setField("isIncludeInSMS",  description.isRequiredForConformation());
		frm.setField("isSaveInTemplate",    description.isSaveInTemplate());
		frm.setField("isHideInConfirmation",    description.isHideInConfirmation());
		frm.setField("defaultValue", description.getDefaultValue());
		frm.setField("values", description.getValuesAsString(FieldDescription.VALUES_DELIMITER));
		frm.setField("requisiteTypes", getRequisiteTypesInString(description.getRequisiteTypes()));
		frm.setField("mask", description.getMask());
		frm.setField("extendedDescId", description.getExtendedDescId());
		frm.setField("businessSubType", StringHelper.getEmptyIfNull(description.getBusinessSubType()));
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditServiceProviderFieldForm frm = (EditServiceProviderFieldForm) form;
		boolean hasAccess = false;
		//Ќеобходимо задизаблить пол€ если нет прав редактировать пол€ поставщика.
		try
		{
			createEditOperation(form);
			hasAccess = true;
		}
		catch (AccessControlException e)
		{
			//Ћибо операци€ не создалась, либо сработал рестрикшен - дизаблим пол€.
			hasAccess = false;
		}
		frm.setEditable(hasAccess);
		EditServiceProviderFieldOperation op = (EditServiceProviderFieldOperation) operation;
		frm.setField("providerName", op.getProviderName());
		frm.setField("billingName", op.getBillingName());
		frm.setField("serviceName", op.getServiceName());
		frm.setRequisiteTypeList(ListUtil.fromArray(RequisiteType.values()));
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		EditServiceProviderFieldOperation op = (EditServiceProviderFieldOperation) operation;
		FieldDescription field = op.getEntity();
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?fieldId=" + field.getId());

		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.dictionaries.provider.save.success"), null);
		return forward;
	}

	/**
	 * ѕолучить список реквизитов из строки
	 * @param value перечисление реквизитов в строке через разделитель ","
	 * @return List<RequisiteType>
	 */
	private List<RequisiteType> getRequisiteTypes(String value)
	{
		// ≈сли строка пуста€, не обрабатываем
		if (StringUtils.isEmpty(value))
			return null;
		
		String[] requisiteTypeArray = value.split(SEPARATOR);
		List<RequisiteType> requisiteTypes = new ArrayList<RequisiteType>(requisiteTypeArray.length);
		for (int i = 0; i < requisiteTypeArray.length; i++)
		{
			requisiteTypes.add(RequisiteType.fromValue(requisiteTypeArray[i]));
		}
		return requisiteTypes;
	}

	/**
	 * ѕреобразовать список реквизитов в строку
	 * @param requisiteTypes список реквизитов
	 * @return перечисление реквизитов в одной строке через разделитель ","
	 */
	private String getRequisiteTypesInString(List<RequisiteType> requisiteTypes)
	{
		// ≈сли список пустой, не обрабатываем
		if (CollectionUtils.isEmpty(requisiteTypes))
			return null;

		StringBuilder builder = new StringBuilder();
		for (RequisiteType requisiteType : requisiteTypes)
		{
			builder.append(requisiteType.getDescription()).append(SEPARATOR);
		}
		builder.delete(builder.lastIndexOf(SEPARATOR), builder.length());

		return builder.toString();
	}
}
