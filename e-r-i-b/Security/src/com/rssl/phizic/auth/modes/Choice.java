package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.modes.generated.ChoiceDescriptor;
import com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 17.04.2007
 * @ $Author: filimonova $
 * @ $Revision: 18001 $
 */

public class Choice implements Serializable
{
	private String       property;
	private List<Option> options;

	Choice(ChoiceDescriptor descriptor)
	{
		this.property = descriptor.getProperty();
		options = new ArrayList<Option>();

		//noinspection unchecked
		List<ChoiceDescriptor.OptionType> ods = descriptor.getOptions();

		for (ChoiceDescriptor.OptionType od : ods)
		{
			options.add(new Option(od));
		}
	}

	Choice(AuthChoiceDescriptor descriptor)
	{
		this.property = descriptor.getProperty();
		options = new ArrayList<Option>();

		//noinspection unchecked
		List<AuthChoiceDescriptor.OptionType> ods = descriptor.getOptions();

		for (AuthChoiceDescriptor.OptionType od : ods)
		{
			options.add(new Option(od));
		}
	}

	/**
	 * @return варианты выбора
	 */
	public List<Option> getOptions()
	{
		return Collections.unmodifiableList(options);
	}

	/**
	 * @return свойство для заполнения
	 */
	public String getProperty()
	{
		return property;
	}
}