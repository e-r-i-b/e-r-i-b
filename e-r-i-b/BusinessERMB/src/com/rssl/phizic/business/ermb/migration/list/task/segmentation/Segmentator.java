package com.rssl.phizic.business.ermb.migration.list.task.segmentation;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;

import java.util.Arrays;
import java.util.List;

/**
 * @author Gulov
 * @ created 09.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сегментация клиента
 */
public class Segmentator
{
	/**
	 * Список определителей сегмента
	 */
	public final List<Resolver> resolvers =
			Arrays.asList(
					new SegmentResolver1(),
					new SegmentResolver2(),
					new SegmentResolver3(),
					new SegmentResolver4(),
					new SegmentResolver5());

	/**
	 * Выполнить сегментацию
	 * @param client - клиент
	 */
	public void exec(Client client) throws Exception
	{
		for (Resolver resolver : resolvers)
			if (resolver.evaluate(client))
				break;
	}
}
