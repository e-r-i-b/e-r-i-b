package com.rssl.phizicgate.wsgate.services.documents;

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
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.Field.class, com.rssl.phizgate.common.payments.systems.recipients.CommonField.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.ListValue.class, com.rssl.phizic.gate.payments.systems.recipients.ListValue.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.Code.class, com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.CommissionOptions.class, com.rssl.phizic.gate.documents.CommissionOptions.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.Currency.class, com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.CurrencyRate.class, com.rssl.phizic.common.types.CurrencyRate.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.DateSpan.class, com.rssl.phizic.common.types.DateSpan.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.DebtImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.DebtRowImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument.class, com.rssl.phizicgate.wsgate.services.documents.types.GateDocumentImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.PersonName.class, com.rssl.phizgate.common.documents.payments.PersonNameImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo.class, com.rssl.phizgate.common.documents.payments.EmployeeInfoImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.Office.class, com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.Pair.class, com.rssl.phizic.common.types.transmiters.Pair.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.QuestionnaireAnswer.class, com.rssl.phizic.business.documents.QuestionnaireAnswerImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.StateUpdateInfo.class, com.rssl.phizicgate.wsgate.services.documents.types.StateUpdateInfoImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.State.class, com.rssl.phizic.common.types.documents.State.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.ResidentBank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		//types.put(com.rssl.phizicgate.wsgate.services.documents.generated.StateCategory.class, com.rssl.common.forms.doc.StateCategory.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.ServiceImpl.class, com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.FieldValidationRule.class, com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment.class, com.rssl.phizicgate.wsgate.services.types.AutoPaymentImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.GroupResult.class, com.rssl.phizic.common.types.transmiters.GroupResult.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.ScheduleItem.class, com.rssl.phizicgate.wsgate.services.types.ScheduleItemImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.FieldValidationRule.class, com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.documents.generated.WriteDownOperation.class, com.rssl.phizic.common.types.commission.WriteDownOperation.class);
		//обратное преобразование

		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule.class, com.rssl.phizicgate.wsgate.services.documents.generated.FieldValidationRule.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Field.class, com.rssl.phizicgate.wsgate.services.documents.generated.Field.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.ListValue.class, com.rssl.phizicgate.wsgate.services.documents.generated.ListValue.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class, com.rssl.phizicgate.wsgate.services.documents.generated.ServiceImpl.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.documents.generated.Code.class);
		types.put(com.rssl.phizic.gate.documents.CommissionOptions.class, com.rssl.phizicgate.wsgate.services.documents.generated.CommissionOptions.class);
		types.put(com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class, com.rssl.phizicgate.wsgate.services.documents.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.CurrencyRate.class, com.rssl.phizicgate.wsgate.services.documents.generated.CurrencyRate.class);
		types.put(com.rssl.phizic.common.types.CurrencyRateType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.DynamicExchangeRate.class, null);  //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.TotalAmountPeriod.class, null);  //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.DateSpan.class, com.rssl.phizicgate.wsgate.services.documents.generated.DateSpan.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Debt.class, com.rssl.phizicgate.wsgate.services.documents.generated.DebtImpl.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.DebtRow.class, com.rssl.phizicgate.wsgate.services.documents.generated.DebtRowImpl.class);
		types.put(com.rssl.phizic.gate.documents.GateDocument.class, com.rssl.phizicgate.wsgate.services.documents.generated.GateDocument.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgate.services.documents.generated.Money.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.documents.generated.Office.class);
		types.put(com.rssl.phizic.common.types.transmiters.Pair.class, com.rssl.phizicgate.wsgate.services.documents.generated.Pair.class);
		types.put(com.rssl.phizic.gate.loans.QuestionnaireAnswer.class, com.rssl.phizicgate.wsgate.services.documents.generated.QuestionnaireAnswer.class);
		types.put(com.rssl.phizic.gate.documents.StateUpdateInfo.class, com.rssl.phizicgate.wsgate.services.documents.generated.StateUpdateInfo.class);
		types.put(com.rssl.phizic.common.types.documents.State.class, com.rssl.phizicgate.wsgate.services.documents.generated.State.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizicgate.wsgate.services.documents.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.bankroll.Card.class, com.rssl.phizicgate.wsgate.services.documents.generated.Card.class);
		types.put(com.rssl.phizic.gate.longoffer.autopayment.AutoPayment.class, com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment.class);
		types.put(com.rssl.phizic.gate.payments.owner.PersonName.class, com.rssl.phizicgate.wsgate.services.documents.generated.PersonName.class);
		types.put(com.rssl.phizic.gate.payments.owner.EmployeeInfo.class, com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo.class);

		types.put(java.lang.Class.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.documents.FormType.class, null);
		types.put(com.rssl.common.forms.doc.CreationType.class, null);
		types.put(java.lang.Class.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus.class, null);
		types.put(com.rssl.phizic.gate.longoffer.autopayment.ScheduleItemState.class, null);
		types.put(com.rssl.phizic.common.types.commission.WriteDownOperation.class, com.rssl.phizicgate.wsgate.services.documents.generated.WriteDownOperation.class);

		types.put(com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions.class, null); //прописан в BeanFormatterMap
		//types.put(com.rssl.common.forms.doc.StateCategory.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.documents.CommissionTarget.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.ExecutionEventType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.SumType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldDataType.class, null);
		types.put(com.rssl.phizic.common.types.BusinessFieldSubType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.CalendarFieldPeriod.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);
		types.put(com.rssl.phizic.gate.documents.InputSumType.class, null);
		types.put(com.rssl.phizic.gate.payments.ReceiverCardType.class, null);
		types.put(com.rssl.phizic.gate.documents.WithdrawMode.class, null); //прописан в BeanFormatterMap
	}
}