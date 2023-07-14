package com.rssl.phizic.operations.internetshop;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.shop.FNS;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 16.12.2010
 * @ $Author$
 * @ $Revision$
 */

public class FnsListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	/**
	 * Возвращает соответствие статусов оплаченных документов из ФНС по налоговым индексам
	 * @param fns - список пришедших из ФНС документов
	 * @param login - логин пользователя
	 * @return - хеш соответствий статуса документа и налогового индекса
	 * @throws BusinessException
	 */
	public Map<String, String> getLinkTaxIndexState(List fns, Login login) throws BusinessException
	{
		List<String> taxDocuments = new ArrayList<String>();
		for(Object elementFns : fns)
		{
			Object[] elems = (Object[]) elementFns;
			taxDocuments.add(((FNS)elems[1]).getIndexTaxationDocument());
		}

		return getLinkTaxIndexState(taxDocuments.toArray(new String[taxDocuments.size()]), login);
	}

	private  Map<String, String> getLinkTaxIndexState(String[] taxDocuments, Login login) throws BusinessException
	{
		return  businessDocumentService.findStateByIndexTaxPayment(taxDocuments, login);
	}
}
