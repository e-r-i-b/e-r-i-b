package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.messages.translate.MessageTranslateService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.translateMessages.LogType;
import com.rssl.phizic.logging.translateMessages.MessageTranslate;
import com.rssl.phizic.logging.translateMessages.TypeMessageTranslate;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.StringHelper;

import java.io.*;

/**
 * @author Mescheryakova
 * @ created 14.07.2011
 * @ $Author$
 * @ $Revision$
 * ��������� �������� ����� "��� �������" � "��� ������" ������� ���������
 * ���������: fileName - ���� � �����
 */

public class MessageTranslateTask extends SafeTaskBase
{
	private String fileName;
	private static final String SEPARATOR = ";";  // ����������� ���� � �� ���������
	private static final MessageTranslateService service = new MessageTranslateService();

	public void safeExecute() throws Exception
	{
	      if (StringHelper.isEmpty(fileName))
	        throw new BusinessException("�� ����� ����");

		 File file = new File(fileName);
	     BufferedReader reader = null;

		 if (file == null)
		    throw new BusinessException("�� ����� ���� � �����");

	     try
	     {
	         reader = new BufferedReader(new FileReader(file));
	         String text = null;

	         // ������ ����� � ������ � ��
	         while ((text = reader.readLine()) != null)
	         {
		        String[] results = text.split(SEPARATOR);         // ��������������, ��� ������ csv ����, ����������� ;

	            if (results == null)
	                log("������ " + text + " �� �������� ������������");
		        else if (results.length == 4)
	            {
	    	        MessageTranslate messageTranslate = new MessageTranslate();
	    	        messageTranslate.setCode(results[0]);
	    	        messageTranslate.setTranslate(results[1]);
		            messageTranslate.setType(TypeMessageTranslate.valueOf(results[2]));
		            messageTranslate.setLogType(LogType.valueOf(results[3]));
	    	        service.save(messageTranslate, getInstanceName());
	            }
		        else
		           log("�� ������� ������� ������ " + text + " �� ������� �����, �������, ��� ��������� � ��� �����������");
	         }
	     }
	     catch (FileNotFoundException e)
	     {
	         e.printStackTrace();
	     }
	     catch (IOException e)
	     {
	         e.printStackTrace();
	     }
	     finally
	     {
	         try
	         {
	             if (reader != null)
	             {
	                 reader.close();
	             }
	         }
	         catch (IOException e)
	         {
	             e.printStackTrace();
	         }
	     }
	}

	public void setFileName(String file)
	{
		this.fileName = file;
	}

	private String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}

}
