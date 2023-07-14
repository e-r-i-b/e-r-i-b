package com.rssl.phizic.gate.dictionaries.tests;

import com.rssl.phizic.gate.dictionaries.OneWayReplicator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.LinkedList;

import junit.framework.TestCase;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class OneWayReplicatorTest extends TestCase
{
	private LinkedList<ReplicaTestData> sourceList;
	private LinkedList<ReplicaTestData> destList;
	private String currTest = "";

	public void testReplication() throws Exception
	{
		fillEquals();
		check();
		fillGreater();
		check();
		fillLess();
		check();
		fillAdded();
		check();
		fillDeleted();
		check();
		fillAddedAtStart();
		check();
		fillDeletedAtStart();
		check();
		fillEmpty();
		check();
		fillDestEmpty();
		check();
		fillSrcEmpty();
		check();
		fillOneDest();
		check();
		fillOneSrc();
		check();
		fillOneSrcDestEmpty();
		check();
		fillOneDestSrcEmpty();
		check();
	}

	private void check() throws Exception
	{
		try
		{
			ComparatorTestImpl comparator = new ComparatorTestImpl();
			ReplicaDestinationTestImpl destSource = new ReplicaDestinationTestImpl(destList);
			ReplicaSourceTestImpl replSource = new ReplicaSourceTestImpl(sourceList);
			System.out.println("--------------------------------");
			System.out.println(currTest);
			destSource.printInit();
			replSource.printInit();
			OneWayReplicator replicator = new OneWayReplicator(replSource, destSource, comparator);
			replicator.replicate();
			destSource.printResult();
			assertTrue("Кол-во элементов не совпадает", destSource.getSize()==replSource.getSize());
		}
		catch(Exception ex)
		{
			throw new GateException(currTest, ex);
		}
	}

	private void fillEquals()
	{
		currTest = "fillEquals";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(3)));
		destList.addLast(new ReplicaTestData( new Long(4)));

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));
		sourceList.addLast(new ReplicaTestData( new Long(4)));
	}

	private void fillLess()
	{
		currTest = "fillLess";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(3)));

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));
		sourceList.addLast(new ReplicaTestData( new Long(4)));//<<added
	}

	private void fillGreater()
	{
		currTest = "fillGreater";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(3)));
		destList.addLast(new ReplicaTestData( new Long(4)));//<<added

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));
	}
	private void fillAdded()
	{
		currTest = "fillAdded";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(4)));
		destList.addLast(new ReplicaTestData( new Long(5)));

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));//<<added
		sourceList.addLast(new ReplicaTestData( new Long(4)));
		sourceList.addLast(new ReplicaTestData( new Long(5)));
	}

	private void fillDeleted()
	{
		currTest = "fillDeleted";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(3)));//<<deleted
		destList.addLast(new ReplicaTestData( new Long(4)));
		destList.addLast(new ReplicaTestData( new Long(5)));

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(4)));
		sourceList.addLast(new ReplicaTestData( new Long(5)));
	}

	private void fillAddedAtStart()
	{
		currTest = "fillAddedAtStart";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(4)));
		destList.addLast(new ReplicaTestData( new Long(5)));

		sourceList.addLast(new ReplicaTestData( new Long(0)));//<<added
		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));
		sourceList.addLast(new ReplicaTestData( new Long(4)));
	}

	private void fillDeletedAtStart()
	{
		currTest = "fillDeletedAtStart";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(0)));//<<deleted
		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(4)));
		destList.addLast(new ReplicaTestData( new Long(5)));

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));
		sourceList.addLast(new ReplicaTestData( new Long(4)));
	}

	private void fillEmpty()
	{
		currTest = "fillEmpty";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();
	}

	private void fillDestEmpty()
	{
		currTest = "fillDestEmpty";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));
		sourceList.addLast(new ReplicaTestData( new Long(4)));
	}

	private void fillSrcEmpty()
	{
		currTest = "fillSrcEmpty";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(0)));
		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(4)));
	}

	private void fillOneSrc()
	{
		currTest = "fillOneSrc";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		sourceList.addLast(new ReplicaTestData( new Long(1)));

		destList.addLast(new ReplicaTestData( new Long(0)));
		destList.addLast(new ReplicaTestData( new Long(1)));
		destList.addLast(new ReplicaTestData( new Long(2)));
		destList.addLast(new ReplicaTestData( new Long(4)));
	}

	private void fillOneDest()
	{
		currTest = "fillOneDest";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		sourceList.addLast(new ReplicaTestData( new Long(1)));
		sourceList.addLast(new ReplicaTestData( new Long(2)));
		sourceList.addLast(new ReplicaTestData( new Long(3)));
		sourceList.addLast(new ReplicaTestData( new Long(4)));

	}

	private void fillOneSrcDestEmpty()
	{
		currTest = "fillOneSrcDestEmpty";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		sourceList.addLast(new ReplicaTestData( new Long(1)));
	}

	private void fillOneDestSrcEmpty()
	{
		currTest = "fillOneDestSrcEmpty";

		sourceList = new LinkedList<ReplicaTestData>();
		destList = new LinkedList<ReplicaTestData>();

		destList.addLast(new ReplicaTestData( new Long(0)));

	}
}
