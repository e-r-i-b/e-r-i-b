package com.rssl.phizic.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Krenev
 * @ created 03.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class EncodeHelper
{
	private final static long multiplier = 0x5DEECE66DL;
	private final static long addend = 0xBL;
	private final static long mask = (1L << 48) - 1;
	/**
	 * ������ �������������� ������ ������������: <������������_������>*N+E
	 * E - ��������� ���� � ��������� �� 0 �� <������������_������>-1
	 */
	private static final int N = 4;

	/**
	 * �������  ������� �������������
	 * @param input ������� ������ ������
	 * @return
	 */
	public static byte[] encode(byte[] input)
	{
		if (input.length==0){
			return input;
		}
		int originalSize = input.length;
		//���������� ������ ������
		Random random = new Random();
		int E = random.nextInt(N);
//		System.out.println("E=" + E);
		int newSize = originalSize * N + E;
		byte[] rezult = new byte[newSize];
		Arrays.fill(rezult, (byte) "-".toCharArray()[0]);
		//��������� ��� ������� �����
		Set<Integer> stored = new HashSet<Integer>();
		for (int i = 0; i < originalSize; i++)
		{
			byte curent = input[i];
			int newPozition = calcStorePozition(i, input, newSize);
			while (stored.contains(newPozition))
			{
				newPozition = (newPozition + 1) % newSize;
			}
			stored.add(newPozition);
			rezult[newPozition] = curent;
		}
		//System.out.println(new String(rezult));
		//��������� ������� ����������
		for (int i = 0; i < newSize; i++)
		{
			if (!stored.contains(i))
			{
				int pos = random.nextInt(originalSize);
				rezult[i] = input[pos];
			}
		}
		return rezult;
	}

	public static byte[] decode(byte[] input)
	{
		int originalSize = input.length;
		int decodedSize = input.length / N;
		byte[] rezult = new byte[decodedSize];
		Set<Integer> used = new HashSet<Integer>();
		for (int i = 0; i < decodedSize; i++)
		{
			int newPosition = calcStorePozition(i, rezult, originalSize);
			while (used.contains(newPosition))
			{
				newPosition = (newPosition + 1) % originalSize;
			}
			used.add(newPosition);
			rezult[i] = input[newPosition];
		}
		return rezult;
	}

	/**
	 * ��������� ������� 
	 * @param i ������� � ������� ������
	 * @param data ����� ������������ ��� ��� ������������ ������
	 * @param newSize ����� ������ ������
	 * @return ����� �������
	 */
	private static int calcStorePozition(int i, byte[] data, int newSize)
	{
		int count = 1;
		if (i > 0)
		{
			for (int k = 0; k < i - 1; k++)
			{
				if (i % 2 == 0)
				{
					if (data[k] < data[i - 1])
					{
						count+=7;
					}
				}
				else
				{
					if (data[k] > data[i - 1])
					{
						count*=31;
					}
				}
			}
		}
		int E = newSize % data.length;
		return (int) ((((E + i + count) * multiplier + addend) & mask) % newSize);
	}
}
