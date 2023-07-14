package com.rssl.phizic.web.gate.services.util.calendar;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 04.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class, Class> types= new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.Currency.class, com.rssl.phizic.web.gate.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.CurrencyRate.class, com.rssl.phizic.common.types.CurrencyRate.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.CommissionOptions.class, com.rssl.phizic.gate.documents.CommissionOptions.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.Field.class, com.rssl.phizgate.common.payments.systems.recipients.CommonField.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.ListValue.class, com.rssl.phizic.gate.payments.systems.recipients.ListValue.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.ResidentBank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.DateSpan.class, com.rssl.phizic.common.types.DateSpan.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.QuestionnaireAnswer.class, com.rssl.phizic.web.gate.services.types.QuestionnaireAnswerImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.State.class, com.rssl.phizic.common.types.documents.State.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.ServiceImpl.class, com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.PersonName.class, com.rssl.phizic.web.gate.services.types.PersonNameImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.EmployeeInfo.class, com.rssl.phizic.web.gate.services.types.EmployeeInfoImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.WriteDownOperation.class, com.rssl.phizic.common.types.commission.WriteDownOperation.class);

		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.GateDocument.class, com.rssl.phizgate.common.listener.types.GateDocumentImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.DebtImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtImpl.class);
		types.put(com.rssl.phizic.web.gate.services.util.calendar.generated.DebtRowImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl.class);

		//обратное преобразование
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class, com.rssl.phizic.web.gate.services.util.calendar.generated.ServiceImpl.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Debt.class, com.rssl.phizic.web.gate.services.util.calendar.generated.DebtImpl.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.DebtRow.class, com.rssl.phizic.web.gate.services.util.calendar.generated.DebtRowImpl.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.web.gate.services.util.calendar.generated.Money.class);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.web.gate.services.util.calendar.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.CurrencyRate.class, com.rssl.phizic.web.gate.services.util.calendar.generated.CurrencyRate.class);
		types.put(com.rssl.phizic.common.types.CurrencyRateType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.DynamicExchangeRate.class, null);  //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.documents.GateDocument.class, com.rssl.phizic.web.gate.services.util.calendar.generated.GateDocument.class);
		types.put(com.rssl.phizic.gate.documents.CommissionOptions.class, com.rssl.phizic.web.gate.services.util.calendar.generated.CommissionOptions.class);
		types.put(com.rssl.phizgate.common.payments.systems.recipients.CommonField.class, com.rssl.phizic.web.gate.services.util.calendar.generated.Field.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.ListValue.class, com.rssl.phizic.web.gate.services.util.calendar.generated.ListValue.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizic.web.gate.services.util.calendar.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.web.gate.services.util.calendar.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.services.util.calendar.generated.Code.class);
		types.put(com.rssl.phizic.common.types.DateSpan.class, com.rssl.phizic.web.gate.services.util.calendar.generated.DateSpan.class);
	    types.put(com.rssl.phizic.web.gate.services.types.QuestionnaireAnswerImpl.class, com.rssl.phizic.web.gate.services.util.calendar.generated.QuestionnaireAnswer.class);
		types.put(com.rssl.phizic.common.types.documents.State.class, com.rssl.phizic.web.gate.services.util.calendar.generated.State.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule.class, com.rssl.phizic.web.gate.services.util.calendar.generated.FieldValidationRule.class);
		types.put(com.rssl.phizic.gate.payments.owner.PersonName.class, com.rssl.phizic.web.gate.services.util.calendar.generated.PersonName.class);
		types.put(com.rssl.phizic.gate.payments.owner.EmployeeInfo.class, com.rssl.phizic.web.gate.services.util.calendar.generated.EmployeeInfo.class);
		types.put(com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.documents.CommissionTarget.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.ExecutionEventType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.commission.WriteDownOperation.class, com.rssl.phizic.web.gate.services.util.calendar.generated.WriteDownOperation.class);
		types.put(com.rssl.phizic.gate.longoffer.SumType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.common.forms.doc.DocumentEvent.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldDataType.class, null);
		types.put(com.rssl.phizic.common.types.BusinessFieldSubType.class, null);
		types.put(java.lang.Class.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType.class, null);
		types.put(com.rssl.phizic.gate.documents.InputSumType.class, null);
		types.put(com.rssl.phizic.gate.longoffer.TotalAmountPeriod.class, null);  //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.documents.FormType.class, null);
		types.put(com.rssl.common.forms.doc.CreationType.class, null);
		types.put(com.rssl.phizic.gate.documents.WithdrawMode.class, null); //прописан в BeanFormatterMap
	 }
}