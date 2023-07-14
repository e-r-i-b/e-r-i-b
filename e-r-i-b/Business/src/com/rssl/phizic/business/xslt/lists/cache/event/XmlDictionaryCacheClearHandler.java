package com.rssl.phizic.business.xslt.lists.cache.event;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import java.util.List;

/**
 * Обработчик событий очистки кеша xml справочников
 * @author Puzikov
 * @ created 02.12.13
 * @ $Author$
 * @ $Revision$
 */

public class XmlDictionaryCacheClearHandler implements EventHandler<XmlDictionaryCacheClearEvent>
{
	private static final DepositProductService depositProductService = new DepositProductService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public XmlDictionaryCacheClearHandler()
	{
	}

	public void process(XmlDictionaryCacheClearEvent event)
	{
		Class objectClass = event.getObjectClazz();
		Long recordId = event.getRecordId();
		try
		{
			if (!objectClass.equals(DepositProduct.class))
			{
				throw new IllegalArgumentException(objectClass.getSimpleName());
			}

			if(recordId != null)
			{
				DepositProduct product = depositProductService.findById(recordId);
				if (product != null)
					XmlEntityListCacheSingleton.getInstance().clearCache(product, objectClass);
			}
			else
			{
				List<DepositProduct> products = depositProductService.getAllProducts();
				for (DepositProduct product : products)
					XmlEntityListCacheSingleton.getInstance().clearCache(product, objectClass);
			}
		}
		catch (BusinessException e)
		{
			log.error("Ошибка очистки кэша для " + objectClass.getSimpleName(), e);
		}
	}
}
