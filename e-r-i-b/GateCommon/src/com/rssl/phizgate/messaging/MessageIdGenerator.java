package com.rssl.phizgate.messaging;

import java.util.Random;

/**
 * @author Omeliyanchuk
 * @ created 12.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class MessageIdGenerator
{
    private static  final   byte[]  table_hex = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    private static	final   int[][] table_mix = {
	                                                {0, 18, 23, 14, 11, 16, 21, 1},
	                                                {18, 23, 0, 21, 1, 11, 14, 16},
	                                                {16, 1, 14, 11, 0, 21, 23, 18},
	                                                {18, 0, 11, 21, 16, 23, 14, 1},
	                                                {23, 18, 16, 1, 21, 14, 11, 0},
	                                                {11, 14, 21, 0, 23, 18, 1, 16},
	                                                {14, 21, 23, 18, 16, 1, 0, 11},
	                                                {1, 23, 11, 16, 18, 0, 21, 14}
                                                };

	public static String getId(int value)
    {
	    Random rnd = new Random();

	    byte[]  buff = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        int f = rnd.nextInt(8);
        int e = rnd.nextInt(8);

        buff[9]  = MessageIdGenerator.table_hex[f + 5];
        buff[10] = MessageIdGenerator.table_hex[e + 7];

		for(int i = 0; i < 8; ++i)
		{
			buff[MessageIdGenerator.table_mix[e][i] + f] = MessageIdGenerator.table_hex[(value >>> (i << 2)) & 15];
		}

        for(int i = 0; i < buff.length; ++i) if(buff[i] == 0)
        {
            buff[i] = MessageIdGenerator.table_hex[rnd.nextInt(16)];
        }

        return new String(buff);
    }
}