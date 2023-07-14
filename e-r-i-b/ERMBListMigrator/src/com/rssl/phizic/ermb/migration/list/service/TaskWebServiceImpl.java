package com.rssl.phizic.ermb.migration.list.service;

import com.rssl.phizic.business.ermb.migration.list.ASMigrator;
import com.rssl.phizic.business.ermb.migration.list.task.LoadClientsTask;
import com.rssl.phizic.business.ermb.migration.list.task.SMSDeliveryTask;
import com.rssl.phizic.business.ermb.migration.list.task.TaskManager;
import com.rssl.phizic.context.ModuleContext;
import com.rssl.phizic.ermb.migration.list.service.generated.LoadClientsActionRq;
import com.rssl.phizic.ermb.migration.list.service.generated.SmsDeliveryActionRq;
import com.rssl.phizic.ermb.migration.list.service.generated.StatusRs;
import com.rssl.phizic.ermb.migration.list.service.generated.TaskWebServiceBindingImpl;
import com.rssl.phizic.module.ModuleStaticManager;

/**
 * Серверная часть веб-сервиса отправки задач-одиночек
 * @author Puzikov
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 */

public class TaskWebServiceImpl extends TaskWebServiceBindingImpl
{
	public StatusRs loadClients(LoadClientsActionRq request)
	{
		ModuleContext.setModule(ModuleStaticManager.getInstance().getModule(ASMigrator.class));

		LoadClientsTask task = new LoadClientsTask(request.getFilePath(), request.getNonActivePeriod());
		TaskManager.getInstance().start(task);

		return new StatusRs(task.getStatus());
	}

	public StatusRs smsDelivery(SmsDeliveryActionRq request)
	{
		ModuleContext.setModule(ModuleStaticManager.getInstance().getModule(ASMigrator.class));

		SMSDeliveryTask task = new SMSDeliveryTask(request.getSendsSegment(), request.getNotSendsSegment(), request.getSmsText());
		TaskManager.getInstance().start(task);

		return new StatusRs(task.getStatus());
	}
}
