package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;

import java.util.EnumMap;
import java.util.Map;

/**
 * ���������� � ������ ���������
 * �������� ���������� � �������������/���������� � ������ ����/��������� �������� �� ���������
 * @author Puzikov
 * @ created 21.11.14
 * @ $Author$
 * @ $Revision$
 */

class MigrationStats
{
	private class StatsCounter
	{
		private int okCount;
		private int passCount;
		private int errorCount;
	}

	private final StatsCounter global;
	private final Map<Segment, StatsCounter> bySegment;

	MigrationStats()
	{
		this.global = new StatsCounter();
		this.bySegment = new EnumMap<Segment, StatsCounter>(Segment.class);
		for (Segment segment : Segment.values())
		{
			bySegment.put(segment, new StatsCounter());
		}
	}

	void ok(Client client)
	{
		global.okCount++;
		for (Segment segment : client.getSegments())
		{
			bySegment.get(segment).okCount++;
		}
	}

	void pass(Client client)
	{
		global.passCount++;
		for (Segment segment : client.getSegments())
		{
			bySegment.get(segment).passCount++;
		}
	}

	void error(Client client)
	{
		global.errorCount++;
		for (Segment segment : client.getSegments())
		{
			bySegment.get(segment).errorCount++;
		}
	}

	String getInfo()
	{
		StringBuilder result = new StringBuilder("\n");

		appendInfoBlock(result, global.okCount, global.passCount, global.errorCount);

		result.append("� ��� ����� �� ��������� (���� ������ ����� ������� � ���������!)\n");
		for (Segment segment : Segment.values())
		{
			StatsCounter statsBySegment = bySegment.get(segment);
			if (statsBySegment.okCount + statsBySegment.passCount + statsBySegment.errorCount > 0)
			{
				result.append("������� ")
						.append(segment)
						.append('\n');
				appendInfoBlock(result, statsBySegment.okCount, statsBySegment.passCount, statsBySegment.errorCount);
			}
		}

		return result.toString();
	}

	private void appendInfoBlock(StringBuilder builder, int ok, int pass, int error)
	{
		builder.append(ok + pass + error)
				.append("\t����� ����������\n");
		builder.append(ok)
				.append("\t�������� ������������\n");
		builder.append(pass)
				.append("\t�������� ���������� �� �������� � ������ ����\n");
		builder.append(error)
				.append("\t�������� �� ������� ������������\n");
	}
}
