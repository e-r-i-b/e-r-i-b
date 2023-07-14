package com.rssl.phizicgate.wsgate.services.template;

import com.rssl.phizic.business.documents.templates.impl.ReminderInfoImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 02.03.14
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class, Class> types = new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.Client.class,              com.rssl.phizicgate.wsgate.services.types.ClientImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.ClientDocument.class,      com.rssl.phizicgate.wsgate.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.Office.class,              com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.Code.class,                com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.EmployeeInfo.class,        com.rssl.phizgate.common.documents.payments.EmployeeInfoImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.PersonName.class,          com.rssl.phizgate.common.documents.payments.PersonNameImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.WriteDownOperation.class,  com.rssl.phizic.common.types.commission.WriteDownOperation.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.CommissionOptions.class,   com.rssl.phizic.gate.documents.CommissionOptions.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.Money.class,               com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.Currency.class,            com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.TemplateInfo.class,        com.rssl.phizic.business.documents.templates.TemplateInfoImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.ReminderInfo.class,        ReminderInfoImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.State.class,               com.rssl.phizic.common.types.documents.State.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.ResidentBank.class,        com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.Service.class,             com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.Field.class,               com.rssl.phizgate.common.payments.systems.recipients.CommonField.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.ListValue.class,           com.rssl.phizic.gate.payments.systems.recipients.ListValue.class);
		types.put(com.rssl.phizicgate.wsgate.services.template.generated.FieldValidationRule.class, com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class);

		//обратное преобразование
		types.put(com.rssl.phizic.gate.clients.Client.class,                                        com.rssl.phizicgate.wsgate.services.template.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class,                                com.rssl.phizicgate.wsgate.services.template.generated.ClientDocument.class);
		types.put(com.rssl.phizic.gate.clients.GUID.class,                                          com.rssl.phizicgate.wsgate.services.template.generated.Profile.class);
		types.put(com.rssl.phizic.gate.documents.CommissionOptions.class,                           com.rssl.phizicgate.wsgate.services.template.generated.CommissionOptions.class);
		types.put(com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class,              com.rssl.phizicgate.wsgate.services.template.generated.Currency.class);
		types.put(com.rssl.phizic.gate.documents.GateTemplate.class,                                com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate.class);
		types.put(com.rssl.phizic.common.types.Money.class,                                         com.rssl.phizicgate.wsgate.services.template.generated.Money.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class,                          com.rssl.phizicgate.wsgate.services.template.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class,                            com.rssl.phizicgate.wsgate.services.template.generated.Code.class);
		types.put(com.rssl.phizic.common.types.documents.State.class,                               com.rssl.phizicgate.wsgate.services.template.generated.State.class);
		types.put(com.rssl.phizic.gate.payments.owner.PersonName.class,                             com.rssl.phizicgate.wsgate.services.template.generated.PersonName.class);
		types.put(com.rssl.phizic.gate.payments.owner.EmployeeInfo.class,                           com.rssl.phizicgate.wsgate.services.template.generated.EmployeeInfo.class);
		types.put(com.rssl.phizic.common.types.commission.WriteDownOperation.class,                 com.rssl.phizicgate.wsgate.services.template.generated.WriteDownOperation.class);
		types.put(com.rssl.phizic.gate.documents.attribute.ExtendedAttribute.class,                 com.rssl.phizicgate.wsgate.services.template.generated.ExtendedAttribute.class);
		types.put(com.rssl.phizic.gate.payments.template.TemplateInfo.class,                        com.rssl.phizicgate.wsgate.services.template.generated.TemplateInfo.class);
		types.put(com.rssl.phizic.gate.payments.template.ReminderInfo.class,                        com.rssl.phizicgate.wsgate.services.template.generated.ReminderInfo.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class,                   com.rssl.phizicgate.wsgate.services.template.generated.Service.class);
		types.put(com.rssl.phizic.common.types.Currency.class,                                      com.rssl.phizicgate.wsgate.services.template.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.CurrencyRate.class,                                  com.rssl.phizicgate.wsgate.services.template.generated.CurrencyRate.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class,                             com.rssl.phizicgate.wsgate.services.template.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Field.class,                     com.rssl.phizicgate.wsgate.services.template.generated.Field.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.ListValue.class,                 com.rssl.phizicgate.wsgate.services.template.generated.ListValue.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule.class,       com.rssl.phizicgate.wsgate.services.template.generated.FieldValidationRule.class);

		types.put(com.rssl.phizic.common.types.CurrencyRateType.class,                              null);
		types.put(com.rssl.phizic.common.types.DynamicExchangeRate.class,                           null);
		types.put(com.rssl.common.forms.doc.CreationType.class,                                     null);
		types.put(com.rssl.phizic.common.types.documents.FormType.class,                            null);
		types.put(java.lang.Class.class,                                                            null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldDataType.class,             null);
		types.put(com.rssl.phizic.common.types.BusinessFieldSubType.class,                          null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.CalendarFieldPeriod.class,       null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType.class,   null);
		types.put(com.rssl.phizic.gate.documents.InputSumType.class,                                null);
		types.put(com.rssl.phizic.gate.payments.ReceiverCardType.class,                             null);
		types.put(com.rssl.phizic.gate.documents.attribute.Type.class,                              null);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class,                     null);
		types.put(com.rssl.phizic.gate.reminder.ReminderType.class,                                 null);
		types.put(com.rssl.phizic.gate.depo.TransferOperation.class,                                null);
		types.put(com.rssl.phizic.gate.depo.TransferSubOperation.class,                             null);
		types.put(com.rssl.phizic.gate.depo.DeliveryType.class,                                     null);
	}
}