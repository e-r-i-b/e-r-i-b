package com.rssl.phizic.auth.modes.readers;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.auth.modes.ConfirmResponseReader;

import java.util.List;

/**
 * @author krenev
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class ConfirmResponseReaderBase implements ConfirmResponseReader
{
	/**
	 * создать форм процессор
	 * @param form форма
	 * @param valuesSource источник данных полей
	 * @return форм процессор
	 */
	protected FormProcessor createFormProcessor(Form form, FieldValuesSource valuesSource)
	{
		return new FormProcessor<List<String>, StringErrorCollector>(valuesSource, form, new StringErrorCollector(), DefaultValidationStrategy.getInstance());
	}
}
