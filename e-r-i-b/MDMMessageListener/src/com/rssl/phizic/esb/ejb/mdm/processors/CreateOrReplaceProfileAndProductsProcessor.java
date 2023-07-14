package com.rssl.phizic.esb.ejb.mdm.processors;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.mdm.integration.mdm.generated.CustAgreemtModNf;
import com.rssl.phizicgate.mdm.integration.mdm.processors.MDMObjectConverter;
import com.rssl.phizicgate.mdm.common.ClientInfoUpdateType;
import com.rssl.phizicgate.mdm.operations.CreateOrReplaceProfileAndProductsOperation;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * процессор нотификации об изменении клиента/продукта
 */

public class CreateOrReplaceProfileAndProductsProcessor extends MessageProcessorBase<CustAgreemtModNf>
{
	private static final String MESSAGE_TYPE = CustAgreemtModNf.class.getSimpleName();

	@Override
	protected String getType(CustAgreemtModNf message)
	{
		return MESSAGE_TYPE;
	}

	@Override
	protected String getId(CustAgreemtModNf message)
	{
		return message.getRqUID();
	}

	@Override
	protected CreateOrReplaceProfileAndProductsOperation getOperation(CustAgreemtModNf message) throws GateException, GateLogicException
	{
		CreateOrReplaceProfileAndProductsOperation operation = new CreateOrReplaceProfileAndProductsOperation();
		operation.initialize(MDMObjectConverter.convert(message));
		return operation;
	}

	@Override
	protected ClientInfoUpdateType getUpdateType()
	{
		return ClientInfoUpdateType.updateProfileAndProducts;
	}
}
