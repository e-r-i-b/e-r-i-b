create or replace and compile java source named "crypto" as

import java.io.UnsupportedEncodingException;

public class crypto 
{
	public static byte[] hash(byte[] m)
	{
		return hashSber(m);
	}

	private static byte[] hashSber(byte[] data)
	{
		int[] ACCM = {0, 0, 0, 0, 0, 0, 0, 0};
		int[] HASH = {0, 0, 0, 0, 0, 0, 0, 0};
		int[] SIZE = {0, 0, 0, 0, 0, 0, 0, 0};

		for (int i = 0, size = data.length, j, v, n, ii, ij, code; size > 0;)
		{
			n = (size < 32) ? (size << 3) : 256;

			int[] BUFF = {0, 0, 0, 0, 0, 0, 0, 0};

			for (j = 0, v = 0; j < 32; ++j)
			{
				ii = (j & 3) << 3;
				ij = j >> 2;

				if (n != 0)
				{
					n += (SIZE[ij] >>> ii) & 0xff;

					SIZE[ij] &= ~(0xff << ii);
					SIZE[ij] |= (n & 0xff) << ii;

					n >>>= 8;
				}

				if (size != 0)
				{
					--size;

					code = data[i++] & 0xff;

					BUFF[ij] |= code << ii;

					v += code;
				}
				else if (v == 0)
				{
					continue;
				}

				v += (ACCM[ij] >>> ii) & 0xff;

				ACCM[ij] &= ~(0xff << ii);
				ACCM[ij] |= (v & 0xff) << ii;

				v >>>= 8;
			}

			hash_step(BUFF, HASH, CP_SBER, CNA8_SBER);
		}

		hash_step(SIZE, HASH, CP_SBER, CNA8_SBER);

		return IntArrayToBytes(hash_step(ACCM, HASH, CP_SBER, CNA8_SBER));
	}

	/*
		 * Java реализация построения хеша буфера данных
		 * с использованием варианта алгоритма ГОСТ 34.11-95.
		 *
		 * Версия 1.1 Copyright (C) 2007 R-Style Softlab
		 *
		 * Автор: Иванов Александр
		 *
		 *  Параметр: шестнадцатиричное представление данных для которых необходимо построить хеш
		 * Результат: строка содержащая шестнадцатиричное представление хеша
		 *
		 */

	private static final int[][] CNA4_SBER =
			{
					{0x8, 0x7, 0x3, 0xC, 0xE, 0xD, 0x2, 0x0, 0xB, 0xA, 0x4, 0x1, 0x5, 0xF, 0x9, 0x6},
					{0xC, 0x8, 0x9, 0xD, 0x3, 0x4, 0x1, 0x5, 0x7, 0x6, 0x2, 0xF, 0xA, 0x0, 0xB, 0xE},
					{0xA, 0x5, 0xC, 0x8, 0xD, 0x2, 0x1, 0x9, 0xB, 0x7, 0xE, 0xF, 0x0, 0x3, 0x6, 0x4},
					{0xA, 0xC, 0xB, 0x0, 0x6, 0x2, 0xE, 0x8, 0xF, 0x5, 0x7, 0xD, 0x3, 0x9, 0x4, 0x1},
					{0x5, 0x3, 0xF, 0xE, 0xA, 0x0, 0xB, 0x8, 0x7, 0x1, 0xD, 0x9, 0x2, 0x4, 0xC, 0x6},
					{0x3, 0x9, 0x4, 0x0, 0xE, 0x7, 0x8, 0xF, 0x5, 0xD, 0x6, 0xA, 0xB, 0x2, 0x1, 0xC},
					{0x9, 0x4, 0x0, 0xF, 0x7, 0xD, 0xA, 0xB, 0x2, 0x3, 0x5, 0x6, 0xE, 0x1, 0xC, 0x8},
					{0x3, 0x6, 0xA, 0xE, 0x2, 0xB, 0x1, 0x9, 0xD, 0xC, 0x8, 0xF, 0x4, 0x5, 0x0, 0x7}
			};

	private static final int[][] CNA8_SBER;

	static
	{
		CNA8_SBER = new int[4][256];

		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 256; ++j)
			{
				CNA8_SBER[i][j] = (CNA4_SBER[(i << 1) + 1][j >> 4] << 4) | CNA4_SBER[i << 1][j & 15];
			}
		}
	}

	private static final int[] CP_SBER =
			{
					0xFF00FF00, 0xFF00FF00, 0x00FF00FF, 0x00FF00FF, 0x00FFFF00, 0xFF0000FF, 0x000000FF, 0xFF00FFFF
			};

	private static int[] Loop32_Encode(int[] B, int[] X, int[][] CNA)
	{
		for (int i = 0, j, n, v; ;)
		{
			n = B[0] + X[(i < 24) ? (i & 7) : (31 - i)];

			for (j = 0, v = 0; j < 4; ++j, n >>>= 8)
			{
				v |= CNA[j][n & 255] << (j << 3);
			}

			v = ((v << 11) | (v >>> 21)) ^ B[1];

			if (++i < 32)
			{
				B[1] = B[0];
				B[0] = v;

				continue;
			}

			B[1] = v;

			return B;
		}
	}

	private static int[] fnShift64(int[] BUFF, int count)
	{
		int[] B = new int[2];

		while (count-- > 0)
		{
			B[0] = BUFF[0] ^ BUFF[2];
			B[1] = BUFF[1] ^ BUFF[3];

			BUFF[0] = BUFF[2];
			BUFF[1] = BUFF[3];
			BUFF[2] = BUFF[4];
			BUFF[3] = BUFF[5];
			BUFF[4] = BUFF[6];
			BUFF[5] = BUFF[7];
			BUFF[6] = B[0];
			BUFF[7] = B[1];
		}

		return BUFF;
	}

	private static int[] fnShift16(int[] BUFF, int count)
	{
		int v;

		while (count-- > 0)
		{
			v = BUFF[0] ^ (BUFF[0] >>> 16) ^ BUFF[1] ^ (BUFF[1] >>> 16) ^ BUFF[6] ^ (BUFF[7] >>> 16);

			BUFF[0] = (BUFF[0] >>> 16) | (BUFF[1] << 16);
			BUFF[1] = (BUFF[1] >>> 16) | (BUFF[2] << 16);
			BUFF[2] = (BUFF[2] >>> 16) | (BUFF[3] << 16);
			BUFF[3] = (BUFF[3] >>> 16) | (BUFF[4] << 16);
			BUFF[4] = (BUFF[4] >>> 16) | (BUFF[5] << 16);
			BUFF[5] = (BUFF[5] >>> 16) | (BUFF[6] << 16);
			BUFF[6] = (BUFF[6] >>> 16) | (BUFF[7] << 16);
			BUFF[7] = (BUFF[7] >>> 16) | (v << 16);
		}

		return BUFF;
	}

	private static int[] fnSum(int[] A1, int[] A2, int[] S)
	{
		for (int i = 0; i < 8; ++i)
		{
			S[i] = A1[i] ^ A2[i];
		}

		return S;
	}

	private static int[] hash_step(int[] BUFF, int[] HASH, int[] CP, int[][] CNA)
	{
		int[] V = {BUFF[0], BUFF[1], BUFF[2], BUFF[3], BUFF[4], BUFF[5], BUFF[6], BUFF[7]};
		int[] U = {HASH[0], HASH[1], HASH[2], HASH[3], HASH[4], HASH[5], HASH[6], HASH[7]};

		int[] X = new int[8];
		int[] E = new int[8];
		int[] W = new int[8];
		int[] B = new int[8];

		for (int i = 0, j, k, l; i < 4; ++i)
		{
			if (i > 0)
			{
				fnShift64(V, 2);
				fnShift64(U, 1);

				if (i == 2)
				{
					fnSum(U, CP, U);
				}
			}

			fnSum(U, V, W);

			for (k = 0; k < 8; ++k)
			{
				for (j = 0, l = (k & 3) << 3, X[k] = 0; j < 4; ++j)
				{
					X[k] |= ((W[(j << 1) + (k >> 2)] >>> l) & 0xff) << (j << 3);
				}
			}

			B[0] = HASH[j = i << 1];
			B[1] = HASH[j + 1];
			E[j++] = Loop32_Encode(B, X, CNA)[0];
			E[j] = B[1];
		}

		return fnShift16(fnSum(fnShift16(fnSum(fnShift16(E, 12), BUFF, E), 1), HASH, HASH), 61);
	}

	private static byte[] IntArrayToBytes(int[] data)
	{
		byte[] buff = new byte[32];

		for (int i = 0; i < 32; ++i)
		{
			buff[i] = (byte) (data[i >> 2] >>> ((i & 3) << 3));
		}

		return buff;
	}

	private static final int HI_MASK   = 0xF0;
	private static final int LO_MASK   = 0x0F;

	private static char forDigit(int digit)
	{
		if (digit < 10)
		{
			return (char) ('0' + digit);
		}
		return (char) ('A' - 10 + digit);
	}

    public static String toHexString(byte[] buffer)
    {
        StringBuffer sb=new StringBuffer(buffer.length*2);
        for(int i=0; i<buffer.length; i++)
        {
            byte b = buffer[i];
            sb.append(forDigit((b & HI_MASK) >> 4));
            sb.append(forDigit( b & LO_MASK)      );
        }
        return sb.toString();
    }

	public static String passwordHash(java.lang.String m)
	{
        
		try
		{                 
			return toHexString(hash(m.getBytes("UTF-16LE")));
		}
		catch (UnsupportedEncodingException e)
		{
			return "E";
		}
	}
}