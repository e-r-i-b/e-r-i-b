package com.rssl.phizic.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @author koptyaev
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */
public class UserImageUtils
{

	/**
	 * Декодирование картинки из base64
	 * @param imageData картинка в base64
	 * @return картинка в виде потока байт
	 */
	public static byte[] decodeImage(byte[] imageData)
	{
		final String base64 = "data:image/_;base64,";

		int start = 0;
		int pos = 0;
		if (imageData[start] == base64.charAt(0)) {
			for(int i = 0; i < base64.length(); i++) {
				if (base64.charAt(i) == '_') {
					while (imageData[start + pos] != ';')
					{
						pos++;
					}
					continue;
				}
				if (imageData[start + pos] != base64.charAt(i)) {
					start = 0;
					break;
				}
				pos++;
			}
			start += pos;
		}

		if (start == 0)
			return imageData;

		byte[] bts = new byte[imageData.length - start];
		System.arraycopy(imageData, start, bts, 0, bts.length);
		return Base64.decodeBase64(bts);
	}
}
