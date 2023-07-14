package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;

import java.util.EnumMap;
import java.util.Map;

/**
 * ���������� �� ����� �������� ������ �������� ��������� �������� ���->����
 * @author Puzikov
 * @ created 21.11.14
 * @ $Author$
 * @ $Revision$
 */

class LoadClientsStats
{
	private int csvCount;
	private int loadedCount;
	private int conflictCount;
	private int resolvedCount;
	private int disabledCount;
	private final Map<Segment, Integer> loadedBySegment;

	LoadClientsStats()
	{
		this.csvCount = 0;
		this.loadedCount = 0;
		this.conflictCount = 0;
		this.resolvedCount = 0;
		this.loadedBySegment = new EnumMap<Segment, Integer>(Segment.class);
	}

	String getInfo()
	{
		StringBuilder result = new StringBuilder("\n");

		result.append(csvCount)
				.append("\t �������� ������� �� csv �����\n");
		result.append(loadedCount)
				.append("\t �������� ������� ��������� � �� ���������\n");

		if (!loadedBySegment.isEmpty())
		{
			result.append("���������� ����������� (���� ������ ����� ������� � ���������!)\n");
			for (Segment segment : Segment.values())
			{
				Integer bySegment = loadedBySegment.get(segment);
				if (bySegment != null)
					result.append(bySegment)
							.append("\t �������� ������ � ")
							.append(segment)
							.append('\n');
			}
		}
		result.append(disabledCount)
				.append("\t ���������� ��������, �� ���������� �������� (�������, �� �������� �� � ���� �������, ���� 5_1)\n");

		result.append(conflictCount)
				.append("\t ���������� �� ��������� ������� (�������� ������ �� ���������� ���������)\n");
		result.append(resolvedCount)
				.append("\t ���������� �� ��������� ��������� �������������\n");

		return result.toString();
	}

	void read()
	{
		csvCount++;
	}

	void ok(Client client)
	{
		loadedCount++;
		for (Segment segment : client.getSegments())
		{
			Integer bySegment = loadedBySegment.get(segment);
			if (bySegment == null)
				loadedBySegment.put(segment, 1);
			else
				loadedBySegment.put(segment, bySegment + 1);
		}
		if (client.getSegments().isEmpty() || client.getSegment_5_1())
			disabledCount++;
	}

	void newConflict()
	{
		conflictCount++;
	}

	void resolveConflict()
	{
		resolvedCount++;
	}
}
