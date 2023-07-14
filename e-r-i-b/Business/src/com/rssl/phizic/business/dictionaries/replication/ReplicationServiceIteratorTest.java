package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ReplicationService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class ReplicationServiceIteratorTest extends TestCase
{
	private static final int PAGE_SIZE = 60;

	public void testManual() throws Exception
	{
		TestReplicationService service = new TestReplicationService(0);
		ReplicationServiceIterator iterator = new ReplicationServiceIterator<TestDictionaryRecord>(service, PAGE_SIZE);
		assertFalse(iterator.hasNext());

		testIterate(PAGE_SIZE - 1);
		testIterate(PAGE_SIZE);
		testIterate(PAGE_SIZE + 1);

		testIterate(PAGE_SIZE - PAGE_SIZE / 2);
		testIterate(PAGE_SIZE + PAGE_SIZE / 2);
		testIterate(2 * PAGE_SIZE - 1);
		testIterate(2 * PAGE_SIZE);
		testIterate(2 * PAGE_SIZE + 1);
		testIterate(6 * PAGE_SIZE + PAGE_SIZE / 2 + 1);
	}

	private void testIterate(int size)
	{
		TestReplicationService service = new TestReplicationService(size);
		ReplicationServiceIterator iterator = new ReplicationServiceIterator<TestDictionaryRecord>(service, PAGE_SIZE);
		assertTrue(iterator.hasNext());
		int i = 0;
		while (iterator.hasNext())
		{
			assertEquals(i, iterator.next().getSynchKey());
			i++;
		}
		assertEquals(i, size);
	}

	private static class TestDictionaryRecord implements DictionaryRecord
	{
		private Integer i;

		TestDictionaryRecord(Integer i)
		{

			this.i = i;
		}

		public Comparable getSynchKey()
		{
			return i;
		}

		public void updateFrom(DictionaryRecord that)
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class TestReplicationService implements ReplicationService<TestDictionaryRecord>
	{
		private int size;

		TestReplicationService(int size)
		{
			this.size = size;
		}

		public int getSize()
		{
			return size;
		}

		public List<TestDictionaryRecord> getAll(int firstResult, int maxResults) throws GateException, GateLogicException
		{
			List<TestDictionaryRecord> list = new ArrayList<TestDictionaryRecord>();

			for (int i = firstResult; i < Math.min(firstResult + maxResults, size); i++)
			{
				list.add(new TestDictionaryRecord(i));
			}
			return list;
		}

		public List<TestDictionaryRecord> getAll(TestDictionaryRecord template, int firstResult, int listLimit) throws GateException
		{
			throw new UnsupportedOperationException();
		}

		public GateFactory getFactory()
		{
			throw new UnsupportedOperationException();
		}
	}
}
