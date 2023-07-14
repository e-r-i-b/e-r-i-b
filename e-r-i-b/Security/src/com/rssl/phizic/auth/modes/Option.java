package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.modes.generated.ChoiceDescriptor;
import com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 17.04.2007
 * @ $Author: filimonova $
 * @ $Revision: 18001 $
 */

public class Option implements Serializable
{
	private String name;
	private String value;

	public Option(ChoiceDescriptor.OptionType descriptor)
	{
		this.name = descriptor.getName();
		this.value = descriptor.getValue();
	}
	public Option(AuthChoiceDescriptor.OptionType descriptor)
	{
		this.name = descriptor.getName();
		this.value = descriptor.getValue();
	}

	public String getName()
	{
		return name;
	}

	public String getValue()
	{
		return value;
	}
}