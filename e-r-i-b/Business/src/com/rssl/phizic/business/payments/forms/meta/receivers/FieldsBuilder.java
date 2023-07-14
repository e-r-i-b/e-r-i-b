package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import com.rssl.phizic.business.BusinessException;

import java.util.List;

import org.w3c.dom.Element;

/**
 * @author Krenev
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 */
interface FieldsBuilder
{
	public List<Field> buildFields() throws BusinessException;
	public Element buildFieldsDictionary();
}
