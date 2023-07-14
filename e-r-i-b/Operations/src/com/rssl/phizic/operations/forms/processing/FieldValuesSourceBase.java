package com.rssl.phizic.operations.forms.processing;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.operations.OperationFactory;

/**
 * @author Erkin
 * @ created 26.08.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class FieldValuesSourceBase implements FieldValuesSource
{
	protected final OperationFactory operationFactory;

	protected FieldValuesSourceBase(OperationFactory operationFactory)
	{
		if (operationFactory == null)
			throw new NullPointerException("Argument 'operationFactory' cannot be null");
		this.operationFactory = operationFactory;
	}
}
