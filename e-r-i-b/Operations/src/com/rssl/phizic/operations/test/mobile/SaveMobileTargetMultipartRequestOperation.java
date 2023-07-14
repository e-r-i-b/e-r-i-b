package com.rssl.phizic.operations.test.mobile;

import com.rssl.phizic.utils.RandomGUID;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author koptyaev
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
public class SaveMobileTargetMultipartRequestOperation extends SendMobileRequestOperation
{
	private static final String CRLF = "\r\n";
	private Map<String, Object> paramsMap; //то же, что и super.params, только в виде мапы
	private String boundary;

	protected String createContentType()
	{
		boundary = new RandomGUID().getStringValue();
		return "multipart/form-data; boundary=" + boundary;
	}

	/**
	 * @param paramsMap мапа с параметрами запроса
	 */
	public void setParamsMap(Map<String, Object> paramsMap)
	{
		this.paramsMap = paramsMap;
	}

	@SuppressWarnings({"TooBroadScope", "IOResourceOpenedButNotSafelyClosed"})
	protected void sendMobileAppRequest(URLConnection urlConn, String params) throws IOException
	{
		PrintWriter writer = null;
		try {
			OutputStream output = urlConn.getOutputStream();
			writer = new PrintWriter(new OutputStreamWriter(output), true);

			for (Map.Entry<String, Object> entry : paramsMap.entrySet())
			{
				String name = entry.getKey();
				Object value = entry.getValue();

				if (name.equals("file") || value == null)
					continue;

				writer.append("--").append(boundary).append(CRLF);
				writer.append("Content-Disposition: form-data; name=\"").append(name).append("\"").append(CRLF);
				writer.append(CRLF);
				writer.append((String)value).append(CRLF).flush();
			}

			String fileName = (String) paramsMap.get("fileName");
			byte[] file = (byte[]) paramsMap.get("file");

			writer.append("--").append(boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(fileName).append("\"").append(CRLF);
			writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName)).append(CRLF);
			writer.append(CRLF).flush();
			output.write(file, 0, file.length);
			writer.append(CRLF).flush();

			writer.append("--").append(boundary).append("--").append(CRLF).flush();
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
