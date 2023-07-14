package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;

/**
 * Билдер шаблонов документов частному лицу
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public class IndividualTransferTemplateBuilder<T extends IndividualTransferTemplate> extends TransferTemplateBuilderBase<T>
{
	@Override
	protected void setBaseData(T template, GateTemplate generated) throws GateException, GateLogicException
	{
		super.setBaseData(template, generated);

		//наименование получателя платежа
		template.setReceiverName(generated.getReceiverName());
	}
}
