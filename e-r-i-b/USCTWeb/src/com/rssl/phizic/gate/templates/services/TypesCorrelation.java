package com.rssl.phizic.gate.templates.services;

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
		types.put(com.rssl.phizic.gate.templates.services.generated.Money.class,                    com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.Currency.class,                 com.rssl.phizic.gate.money.CurrencyImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.CommissionOptions.class,        com.rssl.phizgate.common.services.types.CommissionOptionsImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.ResidentBank.class,             com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.Office.class,                   com.rssl.phizic.gate.office.ExtendedOfficeImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.Code.class,                     com.rssl.phizic.gate.office.ExtendedCodeImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.State.class,                    com.rssl.phizic.common.types.documents.State.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.Service.class,                  com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.Field.class,                    com.rssl.phizgate.common.payments.systems.recipients.CommonField.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.ListValue.class,                com.rssl.phizic.gate.payments.systems.recipients.ListValue.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.FieldValidationRule.class,      com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.PersonName.class,               com.rssl.phizic.gate.owners.person.PersonNameImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.EmployeeInfo.class,             com.rssl.phizic.gate.owners.employee.EmployeeInfoImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.WriteDownOperation.class,       com.rssl.phizic.common.types.commission.WriteDownOperation.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.Client.class,                   com.rssl.phizgate.common.services.types.ClientImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.ClientDocument.class,           com.rssl.phizgate.common.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.Profile.class,                  com.rssl.phizic.gate.owners.person.Profile.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.TemplateInfo.class,             com.rssl.phizic.gate.templates.impl.TemplateInfoImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.ReminderInfo.class,             com.rssl.phizic.gate.templates.impl.ReminderInfoImpl.class);
		types.put(com.rssl.phizic.gate.templates.services.generated.ExtendedAttribute.class,        com.rssl.phizic.gate.templates.attributable.CommonExtendedAttribute.class);

		//обратное преобразование
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class,                   com.rssl.phizic.gate.templates.services.generated.Service.class);
		types.put(com.rssl.phizic.common.types.Money.class,                                         com.rssl.phizic.gate.templates.services.generated.Money.class);
		types.put(com.rssl.phizic.common.types.Currency.class,                                      com.rssl.phizic.gate.templates.services.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.CurrencyRate.class,                                  com.rssl.phizic.gate.templates.services.generated.CurrencyRate.class);
		types.put(com.rssl.phizic.gate.documents.CommissionOptions.class,                           com.rssl.phizic.gate.templates.services.generated.CommissionOptions.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class,                             com.rssl.phizic.gate.templates.services.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class,                          com.rssl.phizic.gate.templates.services.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class,                            com.rssl.phizic.gate.templates.services.generated.Code.class);
		types.put(com.rssl.phizic.common.types.documents.State.class,                               com.rssl.phizic.gate.templates.services.generated.State.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Field.class,                     com.rssl.phizic.gate.templates.services.generated.Field.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.ListValue.class,                 com.rssl.phizic.gate.templates.services.generated.ListValue.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule.class,       com.rssl.phizic.gate.templates.services.generated.FieldValidationRule.class);
		types.put(com.rssl.phizic.gate.payments.owner.PersonName.class,                             com.rssl.phizic.gate.templates.services.generated.PersonName.class);
		types.put(com.rssl.phizic.gate.payments.owner.EmployeeInfo.class,                           com.rssl.phizic.gate.templates.services.generated.EmployeeInfo.class);
		types.put(com.rssl.phizic.common.types.commission.WriteDownOperation.class,                 com.rssl.phizic.gate.templates.services.generated.WriteDownOperation.class);
		types.put(com.rssl.phizic.gate.clients.Client.class,                                        com.rssl.phizic.gate.templates.services.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class,                                com.rssl.phizic.gate.templates.services.generated.ClientDocument.class);
		types.put(com.rssl.phizic.gate.clients.GUID.class,                                          com.rssl.phizic.gate.templates.services.generated.Profile.class);
		types.put(com.rssl.phizic.gate.payments.template.TemplateInfo.class,                        com.rssl.phizic.gate.templates.services.generated.TemplateInfo.class);
		types.put(com.rssl.phizic.gate.payments.template.ReminderInfo.class,                        com.rssl.phizic.gate.templates.services.generated.ReminderInfo.class);
		types.put(com.rssl.phizic.gate.documents.attribute.ExtendedAttribute.class,                 com.rssl.phizic.gate.templates.services.generated.ExtendedAttribute.class);
		types.put(com.rssl.phizic.gate.documents.GateTemplate.class,                                com.rssl.phizic.gate.templates.services.generated.GateTemplate.class);

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
		types.put(com.rssl.phizic.gate.reminder.ReminderType.class,                                 null);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class,                     null);
		types.put(com.rssl.phizic.gate.depo.TransferOperation.class,                                null);
		types.put(com.rssl.phizic.gate.depo.TransferSubOperation.class,                             null);
		types.put(com.rssl.phizic.gate.depo.DeliveryType.class,                                     null);
	}
}
