package com.rssl.phizic.auth.modes.readers;

import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.ConfirmResponseReader;
import com.rssl.common.forms.processing.FieldValuesSource;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 9379 $
 */

public class NullConfirmResponseReader implements ConfirmResponseReader
{

	public void setValuesSource(FieldValuesSource valuesSource)
	{
	}

	public boolean read()
	{
		return true;
	}

	public ConfirmResponse getResponse()
	{
		return new ConfirmResponse(){};
	}

	public List<String> getErrors()
	{
		return null;
	}
}