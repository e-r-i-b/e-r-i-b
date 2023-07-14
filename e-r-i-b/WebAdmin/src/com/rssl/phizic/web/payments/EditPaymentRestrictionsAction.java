package com.rssl.phizic.web.payments;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.businessProperties.LoanReceptionTimeHelper;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.payment.EditPaymentRestrictionsOperation;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

import java.sql.Time;
import java.util.Map;

import static com.rssl.phizic.web.payments.EditPaymentRestrictionsForm.*;

/**
 * @author gladishev
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentRestrictionsAction extends EditPropertiesActionBase<EditPaymentRestrictionsOperation>
{
	protected EditPaymentRestrictionsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditPaymentRestrictionsOperation.class);
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditPaymentRestrictionsForm) frm).createForm(
					PropertyHelper.getTableSettingNumbers(frm.getFields(), LOAN_SETTING_ID_KEY));
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);
		EditPaymentRestrictionsForm frm = (EditPaymentRestrictionsForm) form;
		EditPaymentRestrictionsOperation op = (EditPaymentRestrictionsOperation) operation;

		Map<String, String> map = op.getLoanProperties();
		long i = 0;
		for(String key : map.keySet())
		{
			Triplet<Time, Time, String> time = LoanReceptionTimeHelper.getTimeFromValue(map.get(key));

			frm.setField(LOAN_SETTING_ID_KEY+i, i);
			frm.setField(LOAN_SETTING_SYSTEM_NAME_KEY+i, LoanReceptionTimeHelper.getNameLoanSystem(key));
			frm.setTimeField(LOAN_SETTING_FROM_TIME_KEY+i, time.getFirst());
			frm.setTimeField(LOAN_SETTING_TO_TIME_KEY+i, time.getSecond());
			frm.setField(LOAN_SETTING_TIME_ZONE_KEY+i, time.getThird());
			i++;
		}
		Property basketEnableProperty = op.getBasketListenerProperty();
		frm.setField(EditPaymentRestrictionsOperation.BASKET_ENABLE_PROPERTY_KEY, basketEnableProperty != null ? basketEnableProperty.getValue() : false);

		Property basketModeProperty = op.getBasketListenerModeProperty();
		frm.setField(EditPaymentRestrictionsOperation.BASKET_QUEUE_MODE_PROPERTY_KEY, basketModeProperty != null ? basketModeProperty.getValue() : "autopay");

        frm.setOverallLimit(op.getOverallLimit());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessLogicException, BusinessException
	{
		EditPaymentRestrictionsOperation op = (EditPaymentRestrictionsOperation) editOperation;

		Map<String, String> entity = op.getEntity();
		Map<String, String> map = op.getLoanProperties();
		for (Integer index : PropertyHelper.getTableSettingNumbers(validationResult, LOAN_SETTING_ID_KEY))
		{
			String value = LoanReceptionTimeHelper.getValueFromTime((Time) validationResult.get(LOAN_SETTING_FROM_TIME_KEY + index),
					(Time) validationResult.get(LOAN_SETTING_TO_TIME_KEY + index), (String) validationResult.get(LOAN_SETTING_TIME_ZONE_KEY + index));
			String key = LoanReceptionTimeHelper.getFullKey((String) validationResult.get(LOAN_SETTING_SYSTEM_NAME_KEY + index));
			map.put(key, value);
			entity.remove(LOAN_SETTING_ID_KEY + index);
			entity.remove(LOAN_SETTING_FROM_TIME_KEY + index);
			entity.remove(LOAN_SETTING_TO_TIME_KEY + index);
			entity.remove(LOAN_SETTING_TIME_ZONE_KEY + index);
			entity.remove(LOAN_SETTING_SYSTEM_NAME_KEY + index);
		}
		String value = validationResult.get(EditPaymentRestrictionsOperation.BASKET_ENABLE_PROPERTY_KEY).toString();
		op.setBasketListenerProperty(value);

		String basketQueueMode = validationResult.get(EditPaymentRestrictionsOperation.BASKET_QUEUE_MODE_PROPERTY_KEY).toString();
		op.setBasketListenerModeProperty(basketQueueMode);
	}
}
