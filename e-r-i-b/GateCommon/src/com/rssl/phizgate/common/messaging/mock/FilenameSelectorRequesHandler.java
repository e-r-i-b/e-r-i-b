package com.rssl.phizgate.common.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author krenev
 * @ created 18.02.2011
 * @ $Author$
 * @ $Revision$
 * Хендлер, возвращающий содержимое одного(случайного) из переданных файлов
 */
public class FilenameSelectorRequesHandler implements MockRequestHandler
{
	private final List<MockRequestHandler> handlersMap = new ArrayList<MockRequestHandler>();

	public FilenameSelectorRequesHandler(String... filenames) throws GateException
	{
		for (String filename : filenames)
		{
			handlersMap.add(new FilenameRequestHandler(filename));
		}
	}

	/**
	 * Обработать запрос
	 * @param request запрос
	 * @return респонс
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public Document proccessRequest(Document request) throws GateException
	{
		return handlersMap.get(new Random().nextInt(handlersMap.size())).proccessRequest(request);
	}
}
