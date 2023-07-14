package com.rssl.phizic.business.ermb.text;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.text.MessageComposer;
import com.rssl.phizic.text.TextEngine;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация движка текстов
 */
public class TextEngineImpl extends EngineBase implements TextEngine
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public TextEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.TEXT_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}

	public MessageComposer createMessageComposer()
	{
		return new MessageComposerImpl();
	}
}
