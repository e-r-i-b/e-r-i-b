package com.rssl.phizicgate.mdm.operations;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.mdm.business.MDMDatabaseServiceBase;
import com.rssl.phizicgate.mdm.business.profiles.Profile;
import com.rssl.phizicgate.mdm.common.ClientInfo;
import com.rssl.phizicgate.mdm.common.ClientProductsInfo;
import com.rssl.phizicgate.mdm.common.ClientWithProductsInfo;
import com.rssl.phizicgate.mdm.common.UpdateClientInfoResult;
import org.hibernate.Session;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * операция обработки нотификации об изменении клиента/продукта
 */

public class CreateOrReplaceProfileAndProductsOperation extends UpdateClientInfoOperationBase<ClientWithProductsInfo, UpdateClientInfoResult>
{
	public UpdateClientInfoResult execute()
	{
		final ClientInfo clientInfo = getSource().getClientInfo();
		final ClientProductsInfo productsInfo = getSource().getProductsInfo();

		try
		{
			MDMDatabaseServiceBase.executeAtomic(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Profile profile = updateProfile(clientInfo.getInnerId(), clientInfo);
					updateProducts(productsInfo, profile.getId());
					return null;
				}
			});

			notifyCSA(clientInfo.getInnerId(), clientInfo.getMdmId());
			return UpdateClientInfoResult.getOkResult();
		}
		catch (Exception e)
		{
			log.error("Ошибка обновления информации по профилю и продуктам МДМ.", e);
			return UpdateClientInfoResult.getOkResult();
		}
	}
}
