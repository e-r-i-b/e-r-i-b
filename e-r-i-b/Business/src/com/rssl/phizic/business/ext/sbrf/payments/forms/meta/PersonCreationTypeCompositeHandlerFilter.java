package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.CompositeHandlerFilterBase;
import com.rssl.phizic.business.payments.forms.meta.GateDocumentTypeFilter;

/**
 *
 * ����������� ������ ��� ��������
 * ��������� ��������� ������������� ����, � ����� ���������� ���������
 *
 * @author egorova
 * @ created 05.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class PersonCreationTypeCompositeHandlerFilter extends CompositeHandlerFilterBase
{
	public PersonCreationTypeCompositeHandlerFilter()
	{
		super.addHandler(new GateDocumentTypeFilter());
		super.addHandler(new GateDocumentIsLongOfferFilter());
	}
}
