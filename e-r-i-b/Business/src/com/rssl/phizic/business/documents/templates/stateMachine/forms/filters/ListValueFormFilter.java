package com.rssl.phizic.business.documents.templates.stateMachine.forms.filters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.FormFilterBase;
import com.rssl.common.forms.state.StateObject;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Фильтр для выборок из списка значений
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListValueFormFilter<O, SO extends StateObject> extends FormFilterBase<SO>
{
	protected static final String DELIMITER                           = "(\\|)|(\\;)";


	protected abstract O getValue(SO stateObject);
	protected abstract List<O> getValues();

	protected boolean verify(SO stateObject)
	{
		O value = getValue(stateObject);
		if (value == null)
		{
			return true;
		}

		List<O> values = getValues();
		if (CollectionUtils.isEmpty(values))
		{
			return true;
		}

		for (O o : values)
		{
			if (isInverted() ? !value.equals(o) : value.equals(o))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isEnabled(SO template) throws DocumentException
	{
		return verify(template);
	}
}
