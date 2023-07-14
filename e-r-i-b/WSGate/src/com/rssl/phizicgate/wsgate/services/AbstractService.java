package com.rssl.phizicgate.wsgate.services;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.utils.EncodeHelper;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Krenev
 * @ created 07.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class AbstractService extends com.rssl.phizic.gate.impl.AbstractService
{
	private static final byte[] info = new byte[64];

	public AbstractService(GateFactory factory) {super(factory);}

	protected static String encodeData(String data)
	{
		//кодируем в base64 идентификатор
		byte[] encodedBase64 = Base64.encodeBase64(data.getBytes());
		byte contralInfo = getContralInfo(encodedBase64);
		//вырезаем конечные "="
		int index = encodedBase64.length;
		String end = "";
		while (index > 0 && encodedBase64[index - 1] == '=')
		{
			end = (char) encodedBase64[index - 1] + end;
			index--;
		}
		//заполняем для кодирования
		byte[] forEncode = new byte[index + 1];
		forEncode[0] = contralInfo;
		System.arraycopy(encodedBase64, 0, forEncode, 1, index);
		//кодируем
		byte[] bytes = EncodeHelper.encode(forEncode);

		return new String(bytes) + end;
	}

	private static byte getContralInfo(byte[] data)
	{
		int sum = 0;
		for (byte b : data)
		{
			sum += b;
		}
		return info[sum % info.length];
	}

	static
	{
		info[0] = 'A';info[1] = 'B';info[2] = 'C';
		info[3] = 'D';info[4] = 'E';info[5] = 'F';
		info[6] = 'G';info[7] = 'H';info[8] = 'I';
		info[9] = 'J';info[10] = 'K';info[11] = 'L';
		info[12] = 'M';info[13] = 'N';info[14] = 'O';
		info[15] = 'P';info[16] = 'Q';info[17] = 'R';
		info[18] = 'S';info[19] = 'T';info[20] = 'U';
		info[21] = 'V';info[22] = 'W';info[23] = 'X';
		info[24] = 'Y';info[25] = 'Z';info[26] = 'a';
		info[27] = 'b';info[28] = 'c';info[29] = 'd';
		info[30] = 'e';info[31] = 'f';info[32] = 'g';
		info[33] = 'h';info[34] = 'i';info[35] = 'j';
		info[36] = 'k';info[37] = 'l';info[38] = 'm';
		info[39] = 'n';info[40] = 'o';info[41] = 'p';
		info[42] = 'q';info[43] = 'r';info[44] = 's';
		info[45] = 't';info[46] = 'u';info[47] = 'v';
		info[48] = 'w';info[49] = 'x';info[50] = 'y';
		info[51] = 'z';info[52] = '0';info[53] = '1';
		info[54] = '2';info[55] = '3';info[56] = '4';
		info[57] = '5';info[58] = '6';info[59] = '7';
		info[60] = '8';info[61] = '9';info[62] = '+';
		info[63] = '/';
	}
}
