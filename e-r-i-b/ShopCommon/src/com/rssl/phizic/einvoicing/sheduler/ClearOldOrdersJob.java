package com.rssl.phizic.einvoicing.sheduler;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.invoicing.InvoicingConfig;
import com.rssl.phizic.einvoicing.ShopOrderServiceImpl;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author Mescheryakova
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class ClearOldOrdersJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private ShopOrderServiceImpl shopService = new ShopOrderServiceImpl(null);

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			Integer days = ConfigFactory.getConfig(InvoicingConfig.class).getClearOrderDaysCount();
			if (days == null)
			{
				log.warn("�� ������ ���������� ����, ������ ������� ������� ��� ������, �� ��������� �� � ����� �������� � ������ ��������� > ����������� �� ��������");
				return;
			}
			shopService.removeOrders(days);
		}
		catch(Exception e)
		{
			log.error("������ �������� ������ �������", e);
		}
	}
}
