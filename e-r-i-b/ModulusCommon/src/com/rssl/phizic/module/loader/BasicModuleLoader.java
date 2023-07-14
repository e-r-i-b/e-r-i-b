package com.rssl.phizic.module.loader;

import com.rssl.phizic.engine.Engine;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.work.WorkManager;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Erkin
 * @ created 15.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��������� ������
 */
public class BasicModuleLoader implements ModuleLoader
{
	// �� ����� ��������/�������� ������ ikfl-����������� �� ��������, ������� ���������� apache
	protected static final Log log = LogFactory.getLog(ModuleLoader.class);

	private final Module module;

	private final Object lock = new Object();

	/**
	 * ������� ������� ��������
	 */
	private volatile int startCounter = 0;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - ������, ������� ����� ���������
	 */
	public BasicModuleLoader(Module module)
	{
		this.module = module;
	}

	/**
	 * @return ����������� ������
	 */
	public Module getModule()
	{
		return module;
	}

	public final void start()
	{
		boolean startOK = true;
		if (startCounter == 0)
		{
			synchronized (lock)
			{
				if (startCounter == 0)
					startOK = safeStart();
			}
		}
		if (startOK)
			startCounter++;
	}

	public final void stop()
	{
		if (startCounter > 0)
		{
			boolean stopOK = true;
			if (startCounter == 1)
			{
				synchronized (lock)
				{
					if (startCounter == 1)
						stopOK = safeStop();
				}
			}
			if (stopOK)
				startCounter--;
		}
	}

	///////////////////////////////////////////////////////////////////////////

	private boolean safeStart()
	{
		WorkManager workManager = module.getWorkManager();
		workManager.beginWork();
		try
		{
			log.info("[" + module.getName() + "] �������� ������");
			doStart();
			log.info("[" + module.getName() + "] ������ ��������");
			return true;
		}
		catch (Exception e)
		{
			log.error("[" + module.getName() + "] ���� �� �������� ������", e);
			return false;
		}
		finally
		{
			workManager.endWork();
		}
	}

	private boolean safeStop()
	{
		WorkManager workManager = module.getWorkManager();
		workManager.beginWork();
		try
		{
			log.info("[" + module.getName() + "] �������� ������");
			doStop();
			log.info("[" + module.getName() + "] ������ ��������");
			return true;
		}
		catch (Exception e)
		{
			log.error("[" + module.getName() + "] ���� �� �������� ������", e);
			return false;
		}
		finally
		{
			workManager.endWork();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ��������� �������������� ����� ��������
	 */
	protected void doStart()
	{
		XmlHelper.initXmlEnvironment();

		startEngines();
	}

	/**
	 * ��������� �������������� ����� ��������
	 */
	protected void doStop()
	{
		stopEngines();

		// TODO: (����) ���������������, ����� ����������� Cache �������� ����������� ������ � �������. ����������� ����� �.
//		CacheProvider.shutdown();
	}

	private void startEngines()
	{
		EngineManager engineManager = module.getEngineManager();
		for (Engine engine : engineManager.getEngines())
		{
			log.info("[" + module.getName() + "] ����������� ������ " + engine.getName());
			engine.start();
			log.info("[" + module.getName() + "] �������� ������ " + engine.getName());
		}
	}

	private void stopEngines()
	{
		EngineManager engineManager = module.getEngineManager();
		List<Engine> engines = new ArrayList<Engine>(engineManager.getEngines());
		ListIterator<Engine> iterator = engines.listIterator(engines.size());
		while (iterator.hasPrevious())
		{
			Engine engine = iterator.previous();
			log.info("[" + module.getName() + "] ����������� ������ " + engine.getName());
			engine.stop();
			log.info("[" + module.getName() + "] �������� ������ " + engine.getName());
		}
	}
}
