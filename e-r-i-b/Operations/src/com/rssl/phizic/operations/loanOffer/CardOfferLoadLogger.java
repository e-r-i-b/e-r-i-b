package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.common.types.Encodings;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.files.FileHelper;

import java.io.*;

/**
 * @author Moshenko
 * @ created 24.02.2014
 * @ $Author$
 * @ $Revision$
 * Логер для целей записи информации о процедуре загрузки предодобренных кредитных карт.
 */
public class CardOfferLoadLogger
{
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final String DELEMITER = "\n";
	private static final String ERR_DELEMITER = "|";
	public static final String  COMMA_DELIMITER = ",";
	/**
	 * @param bean Информации о процедуре загрузки.
	 */
	public void write(CardOfferLoadLoggerBean bean) throws IOException
	{

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter out = null;
		File file = new File(bean.getFileName());
		String name = file.getName() + Constants.CSV;
		try
		{
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			String fullName = FileHelper.getCurrentFilePath(loanClaimConfig.getUnloadCardOfferReportPath(), name);

			fileOutputStream = new FileOutputStream(fullName);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, Encodings.UTF_8);
			out = new BufferedWriter(outputStreamWriter);
			out.write(bean.getFileName());
			out.write(COMMA_DELIMITER);
			out.write(String.valueOf(bean.getSize()));
			out.write(COMMA_DELIMITER);
			out.write(String.valueOf(bean.getTotalCount()));
			out.write(COMMA_DELIMITER);
			out.write(DateHelper.formatDateToString2(bean.getStartTime()));
			out.write(COMMA_DELIMITER);
			out.write(DateHelper.formatDateToString2(bean.getEndTime()));
			out.write(COMMA_DELIMITER);
			out.write(String.valueOf(bean.getLoadTime()));
			out.write(COMMA_DELIMITER);
			out.write(String.valueOf((bean.getStatus().toValue())));
			out.write(COMMA_DELIMITER);
			out.write(String.valueOf(bean.getLoadCount()));
			out.write(COMMA_DELIMITER);
			out.write(String.valueOf(bean.getTotalCount() - bean.getLoadCount()));
			for (Pair<String,String> err:bean.getErrorString())
			{
				out.write(DELEMITER);
				out.write(err.getFirst());
				out.write(ERR_DELEMITER);
				out.write(err.getSecond());
			}

			out.flush();
			outputStreamWriter.flush();
			fileOutputStream.flush();
		}
		catch (IOException e)
		{
			log.error(Constants.CSV_PROCESS_ERROR,e);
		}
		finally
		{
			if (out != null)
				out.close();
			if  (outputStreamWriter != null)
				outputStreamWriter.close();
			if (fileOutputStream != null)
				fileOutputStream.close();
		}
	}
}
