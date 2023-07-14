package com.rssl.phizic.test.fns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author gulov
 * @ created 24.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * �����, � ������� �������� ����������� ���� ��� ��������� ��� 
 */
class MockPacket
{
	/**
	 * ������ ��� ��������� �������� �����
	 */
	private static final Random random = new Random(System.currentTimeMillis());

	/**
	 * ������ ��������� ED101 ���������
	 */
	private List<PacketItem> itemList = new ArrayList<PacketItem>();

	/**
	 * ��������� ���������
	 */
	private PacketHeader header;

	MockPacket(int countPayment)
	{
		header = new PacketHeader(random, countPayment);

		int temp = 0;

		for (int i = 0; i < Integer.parseInt(header.getQuantity()); i++)
		{
			itemList.add(new PacketItem(random));

			temp += Integer.parseInt(itemList.get(itemList.size() - 1).getSum());
		}

		header.setSum(String.valueOf(temp));
	}

	public PacketHeader getHeader()
	{
		return header;
	}

	public List<PacketItem> getItemList()
	{
		return itemList;
	}
}
