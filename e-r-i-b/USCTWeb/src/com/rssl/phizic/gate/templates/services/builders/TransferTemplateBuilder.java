package com.rssl.phizic.gate.templates.services.builders;

import com.rssl.phizic.gate.templates.impl.TransferTemplateBase;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;

/**
 * Билдер шаблонов документов перевода между моими счетами
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public class TransferTemplateBuilder extends TemplateBuilderBase<TransferTemplateBase>
{
	@Override
	protected void doBuild(TransferTemplateBase template, GateTemplate generated) throws Exception
	{
		setBaseData(template, generated);
	}
}
