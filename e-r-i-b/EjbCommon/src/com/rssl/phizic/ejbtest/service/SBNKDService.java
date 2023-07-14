package com.rssl.phizic.ejbtest.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author bogdanov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */

public class SBNKDService
{
	private static final JMSClaimTestSender sender = new JMSClaimTestSender();

	public String[] getMessage(String rqUid)
	{
		try
		{
			FileReader in = new FileReader("D://uid//" + rqUid + "_in.txt");
			FileReader out = new FileReader("D://uid//" + rqUid + "_out.txt");
			String income = "";
			while (true)
			{
				int ch = in.read();
				if (ch == -1)
					break;
				else
					income += (char)ch;
			}
			in.close();
			String answer = "";
			while (true)
			{
				int ch = out.read();
				if (ch == -1)
					break;
				else
					answer += (char)ch;
			}
			out.close();
			return new String[] {income, answer};
		}
		catch (IOException ignore)
		{
			return null;
		}
	}

	public void addMessage(String rqUid, String income, String answer)
	{
		if (!new File("D://uid").exists())
			new File("D://uid").mkdir();

		try
		{
			FileWriter in = new FileWriter("D://uid//" + rqUid + "_in.txt");
			FileWriter out = new FileWriter("D://uid//" + rqUid + "_out.txt");
			in.append(income);
			in.close();
			out.append(answer);
			out.close();
		}
		catch (IOException ignore)
		{
			return;
		}
	}

	public void sendMessage(String rqUid, String xml) throws Exception
	{
		sender.send(rqUid, xml);
		File in = new File("D://uid//" + rqUid + "_in.txt");
		in.delete();
		File out = new File("D://uid//" + rqUid + "_out.txt");
		out.delete();
	}
}
