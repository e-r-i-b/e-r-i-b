package com.rssl.phizic.business.image;

import com.rssl.phizic.test.BusinessTestCaseBase;

import java.io.*;

/**
 * @author akrenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ImageServiceTest  extends BusinessTestCaseBase
{
	private static final ImageService imageService = new ImageService();

	public void testSaveImage() throws Exception
	{
		String filePath = "Путь к сохраняемой картинке";
		Image image = new Image();
		BufferedReader bufferedReader = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		byte[] imgData = null;
		try
		{
			fileInputStream = new FileInputStream(filePath);
			inputStreamReader = new InputStreamReader(fileInputStream, "ISO-8859-1");
			bufferedReader = new BufferedReader(inputStreamReader);
			byteArrayOutputStream = new ByteArrayOutputStream();
			int n;
			while (-1 != (n = bufferedReader.read()))
			{
				byteArrayOutputStream.write(n);
			}
			imgData = byteArrayOutputStream.toByteArray();
		}
		finally
		{
			if (fileInputStream != null)
			{
				fileInputStream.close();
			}
			if (inputStreamReader != null)
			{
				inputStreamReader.close();
			}
			if (bufferedReader != null)
			{
				bufferedReader.close();
			}
			if (byteArrayOutputStream != null)
			{
				byteArrayOutputStream.close();
			}
		}
		imageService.addOrUpdateImageAndImageData(image, imgData, null);
	}
}
