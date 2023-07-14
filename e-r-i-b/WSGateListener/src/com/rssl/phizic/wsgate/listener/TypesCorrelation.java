package com.rssl.phizic.wsgate.listener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author egorova
 * @ created 25.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	private static final Map<Class, Class> types= new HashMap<Class,Class>();

	public static Map<Class, Class> getTypes()
	{
		return types;
	}

	static
	{
		types.put(com.rssl.phizic.wsgate.listener.generated.CommissionOptions.class, com.rssl.phizgate.common.services.types.CommissionOptionsImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.Field.class, com.rssl.phizgate.common.payments.systems.recipients.CommonField.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.ListValue.class, com.rssl.phizic.gate.payments.systems.recipients.ListValue.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.ResidentBank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.Currency.class, com.rssl.phizic.wsgate.types.CurrencyImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.CurrencyRate.class, com.rssl.phizic.common.types.CurrencyRate.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.Office.class, com.rssl.phizic.wsgate.types.OfficeImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.Code.class, com.rssl.phizic.wsgate.types.CodeImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.DateSpan.class, com.rssl.phizic.common.types.DateSpan.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.State.class, com.rssl.phizic.common.types.documents.State.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.DocumentCommand.class, com.rssl.common.forms.doc.DocumentCommand.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.QuestionnaireAnswer.class, com.rssl.phizgate.common.listener.types.QuestionnaireAnswerImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.GateDocument.class, com.rssl.phizgate.common.listener.types.GateDocumentImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.DebtImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.DebtRowImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.FieldValidationRule.class, com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.ServiceImpl.class, com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.PersonName.class, com.rssl.phizic.wsgate.listener.types.PersonNameImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.EmployeeInfo.class, com.rssl.phizic.wsgate.listener.types.EmployeeInfoImpl.class);
		types.put(com.rssl.phizic.wsgate.listener.generated.WriteDownOperation.class, com.rssl.phizic.common.types.commission.WriteDownOperation.class);
		//обратное преобразованиеEmployeeInfo
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.wsgate.listener.generated.Money.class);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.wsgate.listener.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.CurrencyRate.class, com.rssl.phizic.wsgate.listener.generated.CurrencyRate.class);
		types.put(com.rssl.phizic.common.types.CurrencyRateType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.DynamicExchangeRate.class, null);  //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.TotalAmountPeriod.class, null);  //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.DateSpan.class, com.rssl.phizic.wsgate.listener.generated.DateSpan.class);
		types.put(com.rssl.phizic.gate.documents.GateDocument.class, com.rssl.phizic.wsgate.listener.generated.GateDocument.class);
		types.put(com.rssl.phizic.gate.documents.CommissionOptions.class, com.rssl.phizic.wsgate.listener.generated.CommissionOptions.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Field.class, com.rssl.phizic.wsgate.listener.generated.Field.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.ListValue.class, com.rssl.phizic.wsgate.listener.generated.ListValue.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizic.wsgate.listener.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.wsgate.listener.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.wsgate.listener.generated.Code.class);
		types.put(com.rssl.phizic.gate.loans.QuestionnaireAnswer.class, com.rssl.phizic.wsgate.listener.generated.QuestionnaireAnswer.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Debt.class, com.rssl.phizic.wsgate.listener.generated.DebtImpl.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.DebtRow.class, com.rssl.phizic.wsgate.listener.generated.DebtRowImpl.class);
		types.put(com.rssl.phizic.common.types.documents.State.class, com.rssl.phizic.wsgate.listener.generated.State.class);
		types.put(com.rssl.common.forms.doc.DocumentCommand.class, com.rssl.phizic.wsgate.listener.generated.DocumentCommand.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule.class, com.rssl.phizic.wsgate.listener.generated.FieldValidationRule.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class, com.rssl.phizic.wsgate.listener.generated.ServiceImpl.class);
		types.put(com.rssl.phizic.gate.payments.owner.PersonName.class, com.rssl.phizic.wsgate.listener.generated.PersonName.class);
		types.put(com.rssl.phizic.gate.payments.owner.EmployeeInfo.class, com.rssl.phizic.wsgate.listener.generated.EmployeeInfo.class);
		types.put(com.rssl.phizic.common.types.commission.WriteDownOperation.class, com.rssl.phizic.wsgate.listener.generated.WriteDownOperation.class);

		types.put(com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.ExecutionEventType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.SumType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.documents.CommissionTarget.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.common.forms.doc.DocumentEvent.class, null); //прописан в BeanFormatterMap
		types.put(java.lang.Class.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.documents.FormType.class, null);
		types.put(com.rssl.common.forms.doc.CreationType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldDataType.class, null);
		types.put(com.rssl.phizic.common.types.BusinessFieldSubType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.CalendarFieldPeriod.class, null);
		types.put(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus.class, null);
		types.put(com.rssl.phizic.gate.longoffer.autopayment.ScheduleItemState.class, null);
		types.put(com.rssl.phizic.gate.documents.InputSumType.class, null);
		types.put(com.rssl.phizic.gate.payments.ReceiverCardType.class, null);
		types.put(com.rssl.phizic.gate.documents.WithdrawMode.class, null); //прописан в BeanFormatterMap
	}
}