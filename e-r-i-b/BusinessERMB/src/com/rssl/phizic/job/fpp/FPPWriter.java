package com.rssl.phizic.job.fpp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbSubscribeFeeConfig;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterNameGenerator;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.MoneyFunctions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Писатель ФПП-файла для задачи "Выгрузка ФПП для абонентской платы ЕРМБ"
 * @author Rtischeva
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 */
public class FPPWriter
{
	private static final CounterService counterService = new CounterService();
	private final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);
	private final String transactionCode;
	private final int maxTransactionsCount;
	private final Calendar writeDate;
	private final File fppPath;
	private final String filePrefix;

	private Map<String, FPPFileData> fppFilesDataMap = new HashMap<String, FPPFileData>(); //мапа <тербанк, данные для ФПП-файла>. Нужна, чтобы накапливать данные для файла в рамках одного тербанка
	private Map<File, FileWriter> fppFilesAngLogWritersMap = new HashMap<File, FileWriter>(); //мапа <фпп-файл, писатель лог-файла>. Нужна для того, чтобы не потерять файлы логов для фпп-файлов

	private static final char TAB = '\t';
	private static final String CRLF = "\r\n";
	private static final String TRANSACTION_CODE_HEADER = "CSHDR";
	private static final String TRANSACTION_CODE_TRANSACTION = "CSHTX";
	private static final String TRANSACTION_CODE_FOOTER = "ZZSUM";
	private static final String TRANSACTION_CODE_QUALIFIER = "00";

	private static final char RESERVED_ZERO = '0';
	private static final char RESERVED_BLANK = ' ';
	private static final char ORIGINAL_INDICATOR = '0';
	private static final char REVERSAL_INDICATOR = '1';
	private static final String CHECK_NUMBER = "000000";
	private static final String AUTHORIZATION_CODE = "      ";
	private static final String BANK_ACCOUNT_1 = ";ERM=Y;";

	/**
	 * конструктор
	 */
	public FPPWriter(Calendar writeDate)
	{
		ErmbSubscribeFeeConfig ermbSubscribeFeeConfig = ConfigFactory.getConfig(ErmbSubscribeFeeConfig.class);
		transactionCode = ermbSubscribeFeeConfig.getTransactionCode();
		maxTransactionsCount = ermbSubscribeFeeConfig.getMaxTransactionCount();
		this.writeDate = writeDate;
		fppPath = ermbSubscribeFeeConfig.getFppPath();
		filePrefix = buildFilePrefix();
	}

	/**
	 * записать в ФПП-файл информацию об абонентской плате для профиля
	 * @param profile - ЕРМБ-профиль клиента
	 * @param cost стоимость списания (ненулевая)
	 * @throws BusinessException
	 */
	public void writeSubscribeFee(ErmbProfileImpl profile, Money cost) throws BusinessException
	{
		FPPFileData fppFileData = buildFPPTransaction(profile, cost);
		if (fppFileData != null && fppFileData.getTransactions().size() >= maxTransactionsCount)
		{
			writeFile(fppFileData);
			String tb = fppFileData.getTb();
			fppFilesDataMap.remove(tb);
		}
	}

	/**
	 * сохраняем в файл профили, которые обработали, но еще не успели сохранить
	 * и переименовываем файлы
	 * @throws Exception
	 */
	public void commit() throws BusinessException
	{
		Iterator<String> iterator = fppFilesDataMap.keySet().iterator();
		while (iterator.hasNext())
		{
			String tb = iterator.next();
			FPPFileData fppFileData = fppFilesDataMap.get(tb);
			writeFile(fppFileData);
			iterator.remove();
		}

		for (File fppFile : fppFilesAngLogWritersMap.keySet())
		{
			renameFile(fppFile);
			writeToLog(fppFilesAngLogWritersMap.get(fppFile), " Файл ФПП успешно записан");
		}

		closeLogFiles();
	}

	/**
	 * удаляем все созданные файлы
	 * @throws Exception
	 */
	public void rollback() throws BusinessException
	{
		for(File file: fppFilesAngLogWritersMap.keySet())
		{
			file.delete();
			writeToLog(fppFilesAngLogWritersMap.get(file), " ФПП файл удален");
			log.error(" ФПП файл "+ file.getAbsolutePath() +" удален");
		}

		closeLogFiles();
	}

	private void writeFile(FPPFileData fppFileData) throws BusinessException
	{
		String tb = fppFileData.getTb();
		String fppFileNumber = String.valueOf(getFppFileNumber(tb));

		String name = buildFileName(tb, fppFileNumber);
		String fileName = name + ".tmp";
		File file = new File(fppPath, fileName);

		FileWriter logFileWriter = createLogFile(name);
		fppFilesAngLogWritersMap.put(file, logFileWriter);

		FileWriter fileWriter = null;
		try
		{
			fileWriter = new FileWriter(file);
			log.error("BUG082388: Записываем ФПП файл. Количество транзакций:" + fppFileData.getTransactions().size());
			writeFppHeader(fileWriter, fppFileData, fppFileNumber);
			writeFppTransactions(fileWriter, fppFileData);
			writeFppFooter(fileWriter, fppFileData, fppFileNumber);
		}
		catch (IOException e)
		{
			writeToLog(logFileWriter, " Ошибка записи в файл ФПП " + e.getMessage());
			throw new BusinessException(e);
		}
		finally
		{
			try
			{
				fileWriter.close();
			}
			catch (IOException e)
			{
				writeToLog(logFileWriter, " Не удалось закрыть файл ФПП " + e.getMessage());
				throw new BusinessException(e);
			}
		}
	}

	private FPPFileData buildFPPTransaction(ErmbProfileImpl profile, Money cost) throws BusinessException
	{
		CardLink primaryCard = PrimaryCardResolver.getPrimaryLinkForAbonentPay(profile);
		try
		{
			primaryCard.reset();
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка очистки кэша по карте", e);
		}

		String tb = null;
		String osb = null;
		String vsp = null;

		Code officeCode = primaryCard.getOriginalTbCode();
		if (officeCode != null)
		{
			Map<String, String> fields = officeCode.getFields();
			String region = StringHelper.removeLeadingZeros(fields.get("region"));
			String branch = StringHelper.removeLeadingZeros(fields.get("branch"));
			String office = StringHelper.removeLeadingZeros(fields.get("office"));
			tb = StringHelper.isEmpty(region) ? null : StringHelper.addLeadingZeros(region, 2);
			osb = branch == null ? null : StringHelper.addLeadingZeros(branch, 4);
			vsp = office == null ? null : StringHelper.addLeadingZeros(office, 4);
		}

		if (StringHelper.isEmpty(tb))
			throw new BusinessException("Не найден ТБ для приоритетной карты списания ермб-профиля " + profile.getId());

		FPPFileTransactionData fppFileTransactionData = new FPPFileTransactionData(cost.getDecimal(), primaryCard.getNumber(), osb, vsp);

		FPPFileData fppFileData = fppFilesDataMap.get(tb);
		if (fppFileData == null)
		{
			fppFileData = new FPPFileData(tb);
			fppFilesDataMap.put(tb, fppFileData);
		}

		fppFileData.addTransaction(fppFileTransactionData);
		log.error("BUG082388: Добавили транзакцию для профиля с id=" + profile.getId());
		return fppFileData;
	}

	private void renameFile(File file) throws BusinessException
	{
		String newName = file.getName().replace(".tmp", "");
		File newFile = new File(fppPath, newName);
		boolean renamed = file.renameTo(newFile);
		if (!renamed)
		{
			throw new BusinessException("ошибка при переименовании файла ФПП: файл с именем " + file.getName() + " не удалось переименовать в " + newName);
		}
	}

	private FileWriter createLogFile(String fileName) throws BusinessException
	{
		String logFileName = "_log" + fileName;
		File logFile = new File(fppPath, logFileName);
		try
		{
			return new FileWriter(logFile);
		}
		catch (IOException e)
		{
			throw new BusinessException("Ошибка создания лог-файла ФПП", e);
		}
	}

	private void writeToLog(FileWriter logWriter, String message) throws BusinessException
	{
		try
		{
			logWriter.write(message);
		}
		catch (IOException e)
		{
			throw new BusinessException("Ошибка записи в лог-файл ФПП", e);
		}
		finally
		{
			try
			{
				logWriter.flush();
			}
			catch (IOException e)
			{
				throw new BusinessException("Ошибка записи в лог-файл ФПП", e);
			}
		}
	}

	private void closeLogFiles() throws BusinessException
	{
		try
		{
			for (FileWriter logWriter : fppFilesAngLogWritersMap.values())
			{
				logWriter.close();
			}
		}
		catch (IOException e)
		{
			throw new BusinessException("Ошибка записи в лог-файл ФПП", e);
		}
	}

	private String buildFileName(String tb, String fppFileNumber) throws BusinessException
	{
		return filePrefix + tb + "II." + fppFileNumber;
	}

	private String buildFilePrefix()
	{
		String formattedDate= DateHelper.formatDateDDMMYY(writeDate);
		char lastYearNumber = formattedDate.charAt(formattedDate.length() - 1);
		String julianDate = String.valueOf(DateHelper.getJulianDate(writeDate));
		String formattedJulianDate = StringHelper.appendLeadingZeros(julianDate, 3);

		return lastYearNumber + formattedJulianDate;
	}
	
	private void writeFppHeader(FileWriter writer, FPPFileData fppFileData, String fppFileNumber) throws IOException
	{
		String formattedDate = DateHelper.formatDateYYYYMMDD(writeDate);
		String amount = MoneyFunctions.formatAmount(fppFileData.getRubTransactionAmount(), 2, ',', false);

		writer.write(TAB);
		writer.write(TRANSACTION_CODE_HEADER);
		writer.write(TRANSACTION_CODE_QUALIFIER);
		writer.write(TAB);
		writer.write(formattedDate);
		writer.write(TAB);
		writer.write(StringHelper.appendLeadingZeros(fppFileNumber, 6));
		writer.write(TAB);
		for (int i=0; i < 6; i++)
			writer.write(RESERVED_ZERO);
		writer.write(TAB);
		writer.write(StringHelper.appendLeadingZeros(String.valueOf(fppFileData.getTransactions().size()), 16));
		writer.write(TAB);
		writer.write('-');
		for (int i=0; i < 12; i++)
			writer.write(RESERVED_ZERO);
		writer.write(TAB);
		writer.write(RESERVED_BLANK);
		writer.write(TAB);
		writer.write('-');

		writer.write(StringHelper.addLeadingZeros(amount.replace(",", ""), 12));
		writer.write(TAB);
		for (int i=0; i < 7; i++)
			writer.write(RESERVED_ZERO);
		writer.write(TAB);
		for (int i=0; i < 6; i++)
			writer.write(RESERVED_BLANK);
		writer.write(TAB);
		writer.write(fppFileData.getTb());
		writer.write(TAB);
		for (int i=0; i < 14; i++)
			writer.write(RESERVED_ZERO);
		writer.write(TAB);
		for (int i=0; i < 144; i++)
			writer.write(RESERVED_BLANK);
		writer.write(CRLF);
	}
	
	private void writeFppTransactions(FileWriter writer, FPPFileData fppFileData) throws IOException
	{
		String formattedDate = DateHelper.formatDateYYYYMMDD(writeDate);
		List<FPPFileTransactionData> transactionsData = fppFileData.getTransactions();

		for (int count = 1; count <= transactionsData.size(); count++)
		{
			FPPFileTransactionData transactionData = transactionsData.get(count - 1);
			String tb = fppFileData.getTb();
			String osb = transactionData.getCardOSB();
			String vsp = transactionData.getCardVSP();
			String amount = MoneyFunctions.formatAmount(transactionData.getChargeAmount(), 2, ',', false);
			String time = DateHelper.getTimeFormat(writeDate.getTime());
			String cardNumber = transactionData.getCardNumber();

			writer.write(TAB);
			writer.write(TRANSACTION_CODE_TRANSACTION);
			writer.write(TRANSACTION_CODE_QUALIFIER);
			writer.write(TAB);
			writer.write(formattedDate);
			writer.write(TAB);
			writer.write(StringHelper.addLeadingZeros(String.valueOf(count), 6));
			writer.write(TAB);
			writer.write(transactionCode);
			writer.write(TAB);
			writer.write(RESERVED_ZERO);
			writer.write(TAB);
			writer.write(ORIGINAL_INDICATOR);
			writer.write(TAB);
			writer.write(cardNumber);
			for (int i = cardNumber.length(); i < 19; i++)
				writer.write(RESERVED_BLANK);
			writer.write(TAB);

			writer.write('-');
			writer.write(StringHelper.addLeadingZeros(amount.replace(",", ""), 12));

			writer.write(TAB);
			writer.write(RESERVED_BLANK);
			writer.write(TAB);
			writer.write("810");
			writer.write(TAB);
			writer.write(CHECK_NUMBER);
			writer.write(TAB);
			writer.write(formattedDate);
			writer.write(TAB);
			writer.write(formattedDate);
			writer.write(TAB);
			writer.write(AUTHORIZATION_CODE);
			writer.write(TAB);
			writer.write(tb);
			writer.write(TAB);

			writer.write(osb == null ? "0000" : osb);
			writer.write(vsp == null ? "0000" : vsp);
			writer.write(TAB);

			writer.write(tb);
			writer.write("0001");
			writer.write(TAB);

			for (int i=0; i < 17; i++)
				writer.write(RESERVED_BLANK);
			writer.write(TAB);
			writer.write(time.replace(":", ""));  //время выполнения транзакции в ФОСБ
			writer.write(TAB);
			writer.write('0');
			writer.write(TAB);
			for (int i=0; i < 12; i++)
				writer.write(RESERVED_ZERO);
			writer.write(TAB);
			writer.write(BANK_ACCOUNT_1);
			for (int i=0; i < 84; i++)
				writer.write(RESERVED_BLANK);
			writer.write(CRLF);
		}
	}

	private void writeFppFooter(FileWriter writer, FPPFileData fppFileData, String fppFileNumber) throws IOException
	{
		String formattedDate = DateHelper.formatDateYYYYMMDD(writeDate);
		writer.write(TAB);
		writer.write(TRANSACTION_CODE_FOOTER);
		writer.write(TRANSACTION_CODE_QUALIFIER);
		writer.write(TAB);
		writer.write(fppFileData.getTb());
		writer.write(TAB);
		writer.write(formattedDate);
		writer.write(TAB);
		writer.write(fppFileNumber);
		writer.write(TAB);
		for (int i=0; i < 11; i++)
			writer.write(RESERVED_ZERO);
		for (int i=0; i < 220; i++)
			writer.write(RESERVED_BLANK);
		writer.write(CRLF);
	}

	private long getFppFileNumber(String tb) throws BusinessException
	{
		try
		{
			Counter counter = Counter.createExtendedCounter("FPP_FILE_NUMBER" + tb, Long.MAX_VALUE, CounterNameGenerator.EVERY_DAY);
			return counterService.getNext(counter) + 899;
		}
		catch (CounterException e)
		{
			log.error("Ошибка при создании имени файла ФПП");
			throw new BusinessException(e);
		}
	}
}
