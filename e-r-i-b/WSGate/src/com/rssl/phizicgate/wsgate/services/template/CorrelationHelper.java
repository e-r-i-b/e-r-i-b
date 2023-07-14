package com.rssl.phizicgate.wsgate.services.template;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.business.documents.templates.impl.ReminderInfoImpl;
import com.rssl.phizic.business.documents.templates.TemplateInfoImpl;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizgate.common.documents.payments.EmployeeInfoImpl;
import com.rssl.phizicgate.wsgate.services.template.generated.*;
import com.rssl.phizicgate.wsgate.services.types.ClientImpl;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * WS хелпер
 *
 * @author khudyakov
 * @ created 15.05.14
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
	public static List toGeneratedTemplates(List<? extends GateTemplate> gate) throws GateException
	{
		if (CollectionUtils.isEmpty(gate))
		{
			return Collections.emptyList();
		}

		try
		{
			List<com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate> generated = new ArrayList<com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate>();
			BeanHelper.copyPropertiesWithDifferentTypes(generated, gate, TypesCorrelation.types);
			return generated;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Привести List<? extends GUID> к WS виду
	 * @param gate List<? extends Profile>
	 * @return WS вид
	 */
	public static List<com.rssl.phizicgate.wsgate.services.template.generated.Profile> toGeneratedProfiles(List<? extends com.rssl.phizic.gate.clients.GUID> gate) throws GateException
	{
		if (CollectionUtils.isEmpty(gate))
		{
			return Collections.emptyList();
		}

		try
		{
			List<com.rssl.phizicgate.wsgate.services.template.generated.Profile> generated = new ArrayList<com.rssl.phizicgate.wsgate.services.template.generated.Profile>();
			BeanHelper.copyPropertiesWithDifferentTypes(generated, gate, TypesCorrelation.types);
			return generated;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Привести employeeInfo к гейтовому виду
	 * @param generated WS вид
	 * @return employeeInfo
	 */
	public static com.rssl.phizic.gate.payments.owner.EmployeeInfo toGate(EmployeeInfo generated) throws GateException
	{
		if (generated == null)
		{
			return null;
		}

		try
		{
			EmployeeInfoImpl result = new EmployeeInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(result, generated, TypesCorrelation.types);
			return result;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Привести templateInfo к гейтовому виду
	 * @param generated WS вид
	 * @return templateInfo
	 */
	public static com.rssl.phizic.gate.payments.template.TemplateInfo toGate(TemplateInfo generated) throws GateException
	{
		if (generated == null)
		{
			return null;
		}

		try
		{
			TemplateInfoImpl result = new TemplateInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(result, generated, TypesCorrelation.types);
			return result;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Привести field к гейтовому виду
	 * @param generated WS вид
	 * @return field
	 */
	public static com.rssl.phizic.gate.payments.systems.recipients.Field toGate(Field generated) throws GateException
	{
		if (generated == null)
		{
			return null;
		}

		try
		{
			CommonField field = new CommonField();
			BeanHelper.copyPropertiesWithDifferentTypes(field, generated, TypesCorrelation.types);
			return field;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Привести client к гейтовому виду
	 * @param generated WS вид
	 * @return client
	 */
	public static com.rssl.phizic.gate.clients.Client toGate(Client generated) throws GateException
	{
		if (generated == null)
		{
			return null;
		}

		try
		{
			ClientImpl client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, generated, TypesCorrelation.types);
			return client;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Привести информацию о напоминании к гейтовому виду
	 * @param generated WS вид
	 * @return информация о напоминании
	 * @throws GateException
	 */
	public static com.rssl.phizic.gate.payments.template.ReminderInfo toGate(ReminderInfo generated) throws GateException
	{
		if (generated == null)
		{
			return null;
		}

		try
		{
			ReminderInfoImpl reminderInfo = new ReminderInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(reminderInfo, generated, TypesCorrelation.types);
			return reminderInfo;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
