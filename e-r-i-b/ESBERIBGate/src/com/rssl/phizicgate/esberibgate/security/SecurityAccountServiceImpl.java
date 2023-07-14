package com.rssl.phizicgate.esberibgate.security;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.gate.security.SecurityAccountService;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.SecurityAccountResponseSerializer;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.SecurityAccountCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lukina
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */

public class SecurityAccountServiceImpl extends AbstractService implements SecurityAccountService
{
	private SecurityAccountResponseSerializer respSerializer = new SecurityAccountResponseSerializer();
	private SecurityAccountRequestHelper requestHelper = new SecurityAccountRequestHelper(getFactory());

	protected SecurityAccountServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<SecurityAccount> getSecurityAccountList(Client client) throws GateException, GateLogicException
	{
		List<? extends ClientDocument> documents = client.getDocuments();

		if (documents.isEmpty())
		{
			throw new GateException("Не заполнены документы для клиента с id = " + client.getId());
		}
		Collections.sort(documents, new DocumentTypeComparator());

		List<SecurityAccount> result = new ArrayList<SecurityAccount>();
		for (ClientDocument document : documents)
		{
			ProductContainer productContainer = requestHelper.createSecurityAccountListRequest(client, document);
			if (productContainer.getIfxRq_type() == null)
							throw new InactiveExternalSystemException(productContainer.getProductError(BankProductType.Securities));

			IFXRs_Type ifxRs = getRequest(productContainer.getIfxRq_type());
			result = respSerializer.fillSecurityAccounts(productContainer.getIfxRq_type(), ifxRs, client.getInternalOwnerId());

			if (!result.isEmpty())
			{
				return result;
			}
		}
		return result;
	}

	public SecurityAccount getSecurityAccount(String externalId) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = requestHelper.createSecurityAccount(externalId);
		IFXRs_Type ifxRs = getRequest(ifxRq);
		SecurityAccountCompositeId securityAccountCompositeId = EntityIdHelper.getSecurityAccountCompositeId(externalId);
		SecurityAccount securityAccount = respSerializer.fillSecurityAccount(ifxRq, ifxRs.getSecuritiesInfoInqRs(), securityAccountCompositeId.getLoginId());
		StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);
		storedResourcesService.updateStoredSecurityAccount(securityAccountCompositeId.getLoginId(), securityAccount);
		return securityAccount;
	}
}
