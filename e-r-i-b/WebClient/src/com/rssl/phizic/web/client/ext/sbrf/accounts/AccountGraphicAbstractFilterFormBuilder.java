package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.DatePeriodMultiFieldValidator;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;

/**
 * @author Erkin
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 */
class AccountGraphicAbstractFilterFormBuilder extends AccountOperationsFilterFormBuilder
{
	private static final long MAX_PERIOD = 185 * 24 * 60 * 60 * 1000L; // 185 дней или чуть больше, чем полгода

	///////////////////////////////////////////////////////////////////////////

	protected FormBuilder prepareFilterFormBuilder()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typePeriod == 'period' ");
		FormBuilder formBuilder = getFilterFormBuilder();

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		compareValidator.setMessage(" онечна€ дата должна быть больше начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);
		formBuilder.addFormValidators(compareValidator);

		DatePeriodMultiFieldValidator datePeriodValidator = new DatePeriodMultiFieldValidator();
		datePeriodValidator.setMaxTimeSpan(MAX_PERIOD);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, "fromPeriod");
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, "toPeriod");
		datePeriodValidator.setMessage("ѕериод дл€ формировани€ графической выписки должен быть не более полугода. " +
				"ѕожалуйста, измените период.");

		formBuilder.addFormValidators(datePeriodValidator);

		return formBuilder;
	}
}
