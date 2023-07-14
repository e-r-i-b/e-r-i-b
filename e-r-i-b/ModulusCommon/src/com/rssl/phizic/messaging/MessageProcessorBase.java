package com.rssl.phizic.messaging;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.work.WorkManager;

/**
 * @author Erkin
 * @ created 09.03.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class MessageProcessorBase<T extends XmlMessage> implements MessageProcessor<T>
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final Module module;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - ������
	 */
	protected MessageProcessorBase(Module module)
	{
		this.module = module;
	}

	public Module getModule()
	{
		return module;
	}

	protected abstract void doProcessMessage(T xmlRequest);

	public final void processMessage(final T xmlRequest)
	{
		WorkManager workManager = module.getWorkManager();
		workManager.beginWork();
		try
		{
			if (log.isDebugEnabled())
				log.debug("[" + module.getName() + "] ��������� ��������� " + xmlRequest.getMessage());
			doProcessMessage(xmlRequest);
			if (log.isDebugEnabled())
				log.debug("[" + module.getName() + "] ��������� ����������");
		}
		catch (Exception e)
		{
			log.error("[" + module.getName() + "] ���� �� ��������� ���������", e);
		}
		finally
		{
			// ������ � ��� ���������
			//TODO: (����) �������������� ������� xml. ����������� ����� �.
			if (writeToLog())
				new MessageLogger(new com.rssl.phizic.common.types.TextMessage(xmlRequest.getMessage()), getModule().getApplication(),xmlRequest.getRequestClass().getSimpleName()).makeAndWrite();
			workManager.endWork();
		}
	}

	public boolean writeToLog()
	{
		return true;
	}
}
