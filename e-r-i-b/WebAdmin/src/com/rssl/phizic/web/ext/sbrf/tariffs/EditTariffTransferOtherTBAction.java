package com.rssl.phizic.web.ext.sbrf.tariffs;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.gate.commission.TransferType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.tariffs.EditTariffOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import static com.rssl.phizic.web.ext.sbrf.tariffs.Constants.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * экшн создания/редактирования тарифов на перевод в другой ТБ
 * @author niculichev
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTariffTransferOtherTBAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditTariffOperation operation =	createOperation("EditTariffTransferOtherTBOperation", "TariffTransferOtherTBService");

		if(frm.getId() == null || frm.getId() == 0L)
		{
			operation.initializeNew();
		}
		else
		{
			operation.initialize(frm.getId());
		}

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditTariffTransferOtherTBForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Tariff tariff = (Tariff) entity;

		// тип операции и валюту обновляем только при создании тарифа
		if(tariff.getId() == null)
		{
			tariff.setTransferType(TransferType.valueOf((String) data.get(OPERATION_FIELD_NAME)));
			tariff.setCurrencyCode((String) data.get(CARRENCY_FIELD_NAME));
		}
		tariff.setPercent((BigDecimal) data.get(PERCENT_FIELD_NAME));
		tariff.setMinAmount((BigDecimal) data.get(MIN_AMOUNT_FIELD_NAME));
		tariff.setMaxAmount((BigDecimal) data.get(MAX_AMOUNT_FIELD_NAME));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Tariff tariff = (Tariff) entity;
		TransferType transferType = tariff.getTransferType();
		frm.setField(OPERATION_FIELD_NAME, transferType == null ? null : transferType.name());
		frm.setField(CARRENCY_FIELD_NAME, tariff.getCurrencyCode());
		frm.setField(PERCENT_FIELD_NAME, tariff.getPercent());
		frm.setField(MIN_AMOUNT_FIELD_NAME, tariff.getMinAmount());
		frm.setField(MAX_AMOUNT_FIELD_NAME, tariff.getMaxAmount());
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		Map<String, Object> fields = frm.getFields();
		fields.put(IS_EDIT_FIELD_NAME, (frm.getId() == null || frm.getId() == 0L)? Boolean.FALSE : Boolean.TRUE);

		return new MapValuesSource(fields);
	}
}
