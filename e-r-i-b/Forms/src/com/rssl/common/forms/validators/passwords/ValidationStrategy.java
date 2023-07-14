package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.transmiters.Pair;

/**
 * @author Roshka
 * @ created 01.02.2007
 * @ $Author$
 * @ $Revision$
 */

public interface ValidationStrategy
{
	public Pair<Boolean, String> validate(String value) throws TemporalDocumentException;
}
