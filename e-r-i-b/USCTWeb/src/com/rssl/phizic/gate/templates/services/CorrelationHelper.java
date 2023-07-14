package com.rssl.phizic.gate.templates.services;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.money.CurrencyImpl;
import com.rssl.phizic.gate.office.ExtendedOfficeImpl;
import com.rssl.phizic.gate.owners.client.ClientImpl;
import com.rssl.phizic.gate.owners.employee.EmployeeInfoImpl;
import com.rssl.phizic.gate.templates.impl.ReminderInfoImpl;
import com.rssl.phizic.gate.templates.impl.TemplateInfoImpl;
import com.rssl.phizic.gate.templates.services.generated.*;
import com.rssl.phizic.utils.BeanHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CorrelationHelper
{
	/**
	 * Привести List<? extends GateTemplate> к WS виду
	 * @param gate List<? extends GateTemplate>
	 * @return WS вид
	 */
	public static List<GateTemplate> toGeneratedTemplates(List<? extends com.rssl.phizic.gate.documents.GateTemplate> gate) throws Exception
	{
		if (CollectionUtils.isEmpty(gate))
		{
			return Collections.emptyList();
		}

		List<GateTemplate> generated = new ArrayList<GateTemplate>();
		BeanHelper.copyPropertiesWithDifferentTypes(generated, gate, TypesCorrelation.types);
		return generated;
	}

	/**
	 * Привести GateTemplate к WS виду
	 * @param gate GateTemplate
	 * @return WS вид
	 */
	public static GateTemplate toGenerated(com.rssl.phizic.gate.documents.GateTemplate gate) throws Exception
	{
		if (gate == null)
		{
			return null;
		}

		GateTemplate generated = new GateTemplate();
		BeanHelper.copyPropertiesWithDifferentTypes(generated, gate, TypesCorrelation.types);
		return generated;
	}

	/**
	 * Привести список профилей к Gate виду
	 * @param generated WS вид
	 * @return List<GUID>
	 */
	public static List<com.rssl.phizic.gate.clients.GUID> toGateProfiles(List generated) throws Exception
	{
		if (CollectionUtils.isEmpty(generated))
		{
			return Collections.emptyList();
		}

		List<com.rssl.phizic.gate.clients.GUID> gate = new ArrayList<com.rssl.phizic.gate.clients.GUID>();
		BeanHelper.copyPropertiesWithDifferentTypes(gate, generated, TypesCorrelation.types);
		return gate;
	}

	/**
	 * Привести employeeInfo к гейтовому виду
	 * @param generated WS вид
	 * @return employeeInfo
	 */
	public static com.rssl.phizic.gate.payments.owner.EmployeeInfo toGate(EmployeeInfo generated) throws Exception
	{
		if (generated == null)
		{
			return null;
		}

		EmployeeInfoImpl result = new EmployeeInfoImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(result, generated, TypesCorrelation.types);
		return result;
	}

	/**
	 * Привести templateInfo к гейтовому виду
	 * @param generated WS вид
	 * @return templateInfo
	 */
	public static com.rssl.phizic.gate.payments.template.TemplateInfo toGate(TemplateInfo generated) throws Exception
	{
		if (generated == null)
		{
			return null;
		}

		TemplateInfoImpl result = new TemplateInfoImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(result, generated, TypesCorrelation.types);
		return result;
	}

	/**
	 * Привести field к гейтовому виду
	 * @param generated WS вид
	 * @return field
	 */
	public static com.rssl.phizic.gate.payments.systems.recipients.Field toGate(Field generated) throws Exception
	{
		if (generated == null)
		{
			return null;
		}

		CommonField field = new CommonField();
		BeanHelper.copyPropertiesWithDifferentTypes(field, generated, TypesCorrelation.types);
		return field;
	}

	/**
	 * Привести Money к гейтовому виду
	 * @param generated WS вид
	 * @return money
	 */
	public static com.rssl.phizic.common.types.Money toGate(Money generated) throws GateException
	{
		if (generated == null)
		{
			return null;
		}

		return new com.rssl.phizic.common.types.Money(generated.getDecimal(), new CurrencyImpl(generated.getCurrency().getCode()));
	}

	/**
	 * Привести Office к гейтовому виду
	 * @param generated WS вид
	 * @return Office
	 */
	public static com.rssl.phizic.gate.dictionaries.officies.Office toGate(Office generated) throws Exception
	{
		if (generated == null)
		{
			return null;
		}

		ExtendedOfficeImpl office = new ExtendedOfficeImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(office, generated, TypesCorrelation.types);
		return office;
	}


	/**
	 * Привести client к гейтовому виду
	 * @param generated WS вид
	 * @return client
	 */
	public static com.rssl.phizic.gate.owners.client.ExtendedClient toGate(Client generated) throws Exception
	{
		if (generated == null)
		{
			return null;
		}

		ClientImpl client = new ClientImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(client, generated, TypesCorrelation.types);
		return client;
	}

	/**
	 * Привести ReminderInfo к гейтовому виду
	 * @param generated WS вид
	 * @return ReminderInfo
	 * @throws Exception
	 */
	public static com.rssl.phizic.gate.payments.template.ReminderInfo toGate(ReminderInfo generated) throws Exception
	{
		if (generated == null)
		{
			return null;
		}

		ReminderInfoImpl reminderInfo = new ReminderInfoImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(reminderInfo, generated, TypesCorrelation.types);
		return reminderInfo;
	}
}
