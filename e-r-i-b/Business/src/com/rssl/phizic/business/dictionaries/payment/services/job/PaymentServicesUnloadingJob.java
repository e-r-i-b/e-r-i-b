package com.rssl.phizic.business.dictionaries.payment.services.job;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.mail.file.TransportException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.io.*;
import java.sql.Clob;
import java.util.List;

/**
 * ���� �������� ����������� ����� � xml-����
 *
 * @ author: Gololobov
 * @ created: 28.05.14
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServicesUnloadingJob extends BaseJob implements StatefulJob
{
	private File folder = null;
	private String prefix = null;
	private String suffix = null;

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final int CHAR_SIZE = 10000;
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();
	private static final String IS_NOT_DIRECTORY_ERROR_MSG = "���� � ���������� ����� �� �������� �����������";
	private static final String MAKE_DIRECTORY_ERROR_MSG = "���������� ������� ����� ��� ���������� �����";
	private static final String FILE_PREFIX_ERROR_MSG = "������������ ������� �����";
	private static final String FILE_SUFFIX_ERROR_MSG = "������������ ���������� �����";
	private static final String DICTIONARY_DESCRIPTION = "C��������� �����";

	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		//��������� �����
		init();
		//�������� ������ � ����
		saveDataToFile();
	}

	protected String getDictionaryDescription()
	{
		return DICTIONARY_DESCRIPTION;
	}

	/**
	 * ������������� ���������� �����
	 * @throws JobExecutionException
	 */
	private void init() throws JobExecutionException
	{
		try
		{
			AtmApiConfig atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
			String servicesFileFolder = getFolderFromConfig(atmApiConfig);

			folder = new File(servicesFileFolder).getCanonicalFile();
			if(folder.exists())
			{
				if(!folder.isDirectory())
					throw new TransportException(IS_NOT_DIRECTORY_ERROR_MSG);
			}
			else if(!folder.mkdirs())
				throw new TransportException(MAKE_DIRECTORY_ERROR_MSG);

			prefix = getPrefixFromConfig(atmApiConfig);
			if(StringHelper.isEmpty(prefix))
				throw new TransportException(FILE_PREFIX_ERROR_MSG);

			suffix = getSuffixFromConfig(atmApiConfig);
			if(StringHelper.isEmpty(suffix))
				throw new TransportException(FILE_SUFFIX_ERROR_MSG);
		}
		catch (Exception e)
		{
			String dictionaryDescrription = getDictionaryDescription();
			log.error(String.format("%s �� ��������. �� ������� ������� ��������� \"%s\" ����.",
					StringHelper.isEmpty(dictionaryDescrription) ? "����������" : dictionaryDescrription, suffix), e);
			throw new JobExecutionException(e);
		}
	}

	/**
	 * �������� ������ ����������� � ����
	 */
	private void saveDataToFile() throws JobExecutionException
	{
		File file = null;
		try
		{
			List<Object[]> dictionary = getDictionaryData();
			if (CollectionUtils.isEmpty(dictionary))
			{
				String dictionaryDescription = getDictionaryDescription();
				log.error(String.format("%s. �� ���������� ������ ��� ��������.",
						StringHelper.isEmpty(dictionaryDescription) ? "����������" : dictionaryDescription));
				return;
			}

			Reader reader = null;
			try
	        {
		        for (Object[] dictionaryItem : dictionary)
		        {
			        if (dictionaryItem == null)
				        continue;

			        Long serviceCategoryId = (Long) dictionaryItem[0];
			        Clob xmlData = (Clob) dictionaryItem[1];

	                //����� �� ���� OutOfMemoryError ��������� ������ � ���� ��������� ����� �����
			        char[] buf = new char[1024*CHAR_SIZE];
			        reader = xmlData.getCharacterStream();
					//������ �� ������ ��������� ����� � ��������� ����
			        OutputStream out = new FileOutputStream(file = File.createTempFile(prefix+"_"+serviceCategoryId+"_", suffix, folder));
			        try
			        {
				        int length;
		                while ((length = reader.read(buf)) != -1)
		                {
		                    byte[] byteBuf = (new String(buf)).trim().getBytes();
			                out.write(byteBuf, 0, length);
			                buf = new char[1024*CHAR_SIZE];
		                }
				        out.write("\r\n".getBytes());
			        }
			        finally
			        {
				        out.close();
			        }
			    }
			}
			finally
			{
				if (reader != null)
					reader.close();
			}
		}
		catch (Throwable e)
		{
			if (file != null)
			{
				try
				{
					file.deleteOnExit();
				}
				catch (Throwable ex) {}
			}
			throw new JobExecutionException(e);
		}
	}

	/**
	 * ��������� ���� � ����� ��� �������� ����� �����������
	 * @param atmApiConfig
	 * @return
	 */
	protected String getFolderFromConfig(AtmApiConfig atmApiConfig)
	{
		return atmApiConfig.getServicesPath();
	}

	/**
	 * ��������� �������� ����� �����������
	 * @param atmApiConfig
	 * @return
	 */
	protected String getPrefixFromConfig(AtmApiConfig atmApiConfig)
	{
		return atmApiConfig.getServicesPrefix();
	}

	/**
	 * ��������� ���������� ����� �����������
	 * @param atmApiConfig
	 * @return
	 */
	protected String getSuffixFromConfig(AtmApiConfig atmApiConfig)
	{
		return atmApiConfig.getServicesSuffix();
	}

	/**
	 * ��������� ������ ����������� �� ��
	 * @return
	 * @throws BusinessException
	 */
	protected List<Object[]> getDictionaryData() throws BusinessException
	{
		try
		{
			return paymentServiceService.getPaymentServiceDataForUnloadingToFile();
		}
		catch (BusinessException e)
		{
			String dictionaryDescrription = getDictionaryDescription();
			log.error(String.format("%s �� ��������. ������ ��� ��������� ������ �� ��.",
					StringHelper.isEmpty(dictionaryDescrription) ? "����������" : dictionaryDescrription), e);
			throw e;
		}
	}
}
