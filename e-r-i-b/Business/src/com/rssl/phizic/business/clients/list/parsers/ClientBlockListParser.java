package com.rssl.phizic.business.clients.list.parsers;

import com.rssl.phizic.business.clients.list.ClientBlock;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ѕарсер блокировок слиента
 */

public class ClientBlockListParser extends ParserBase
{
	private static final String FROM_TAG_NAME = "lockFrom";
	private static final String TO_TAG_NAME = "lockTo";
	private static final String REASON_TAG_NAME = "reason";
	private static final String BLOCKER_FIO_TAG_NAME = "lockerFIO";

	private List<ClientBlock> blocks = new ArrayList<ClientBlock>();

	public void execute(Element element) throws Exception
	{
		ClientBlock block = new ClientBlock();
		block.setFrom(getCalendarValue(element, FROM_TAG_NAME));
		block.setTo(getCalendarValue(element, TO_TAG_NAME));
		block.setReason(getStringValue(element, REASON_TAG_NAME));
		block.setBlockerFIO(getStringValue(element, BLOCKER_FIO_TAG_NAME));
		blocks.add(block);
	}

	/**
	 * @return список блокировок
	 */
	public List<ClientBlock> getBlocks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return blocks;
	}
}
