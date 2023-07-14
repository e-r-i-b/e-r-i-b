package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import junit.framework.TestCase;

/**
 * @author Puzikov
 * @ created 01.12.14
 * @ $Author$
 * @ $Revision$
 */

public class LoadClientsStatsTest extends TestCase
{
	public void testStatsCounter()
	{
		LoadClientsStats stats = new LoadClientsStats();

		stats.read();
		stats.read();
		stats.read();

		Client client1 = new Client();
		client1.setSegment_1(true);
		client1.setSegment_4(true);
		Client client2 = new Client();
		client2.setSegment_1(true);
		client2.setSegment_2_1(true);
		Client client3 = new Client();
		client3.setSegment_1(true);
		client3.setSegment_2_2(true);
		Client client4 = new Client();
		Client client5 = new Client();
		client5.setSegment_1(true);
		client5.setSegment_5_1(true);

		stats.ok(client1);
		stats.ok(client2);
		stats.ok(client3);
		stats.ok(client4);
		stats.ok(client5);

		stats.newConflict();
		stats.newConflict();

		stats.resolveConflict();

		System.out.println(stats.getInfo());
	}
}
