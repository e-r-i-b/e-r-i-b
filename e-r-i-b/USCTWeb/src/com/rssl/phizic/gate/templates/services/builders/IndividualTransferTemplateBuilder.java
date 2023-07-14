package com.rssl.phizic.gate.templates.services.builders;

import com.rssl.phizic.gate.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;

/**
 * Билдер шаблонов документов частному лицу
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public class IndividualTransferTemplateBuilder extends TemplateBuilderBase<IndividualTransferTemplate>
{
	@Override
	protected void doBuild(IndividualTransferTemplate template, GateTemplate generated) throws Exception
	{
		setBaseData(template, generated);

		//наименование получателя платежа
		template.setReceiverName(generated.getReceiverName());
	}
}
