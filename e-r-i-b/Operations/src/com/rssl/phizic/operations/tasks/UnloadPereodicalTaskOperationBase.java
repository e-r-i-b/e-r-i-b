package com.rssl.phizic.operations.tasks;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskError;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskService;
import com.rssl.phizic.business.pereodicalTask.unload.DirInteractionPereodicalTask;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loan.UnloadConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.loanOffer.ClaimUnloadExceptoin;
import com.rssl.phizic.operations.pereodicalTask.PeriodicalTaskOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.files.FileHelper;

import java.io.*;
import java.util.*;

/**
 * User: Moshenko
 * Date: 03.11.2011
 * Time: 13:39:35
 */
public abstract class UnloadPereodicalTaskOperationBase extends PeriodicalTaskOperationBase<DirInteractionPereodicalTask, Restriction>
{
	private static final String GET_FILE_NAME_ERROR = " Ошибка при попытке получить имя файла";
	private static final String FILE_CREATE_ERROR = " Ошибка создания фалйа";
	private static final String NO_PATH_ERRPR = " Указанный каталог выгрузки не актуален";
	protected static final String GET_PART_ERROR = " Ошибка в процессе получение  бизнес сущностей";
	protected static final String CLOSE_STREAM_ERROR = " Ошибка закрытия потока";
	protected static final String WRITE_STREAM_ERROR = " Ошибка при попытке записи в поток";
	protected static final String FLUSH_STREAM_ERROR = " Ошибка при попытке сброса данных в поток";
	protected static final String DELET_FILE_ERROR = " Ошибка при попытке удалить файл";
	protected static final String NO_FILE_PAIR = " Поток/Файл не создан";
	protected static final String DOCUMENT_UPDATE_STATUS_ERROR = " Проблемы при обновлении статусов документов";

	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private PereodicalTaskService pereodicalTaskService = new PereodicalTaskService();
	private final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();
	private static BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected Calendar startDate = null;
	protected Calendar endDate = null;
	protected String unloadDir = "";                 //Директория выгрузки
	protected int partCount;                  // размер "пачки" выгрузки
	protected int unloadRepeatInterval;       // интервал повторной выгрузки в днях
	private PereodicalTaskResult result;               //результат выполнения выгрузки
	protected List<PereodicalTaskError> errors = new ArrayList<PereodicalTaskError>(); //список ошибок по выгрузке
	protected boolean isAuto = false;                                  //признак использования операции в автоматической выгрузки

	/**
	 * инициализируем параметры операции данными из таска
	 * @param task
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(DirInteractionPereodicalTask task) throws BusinessException, BusinessLogicException
	{
		PereodicalTaskResult lastResult = (PereodicalTaskResult) pereodicalTaskService.getLastResultByTask(task);
		log.error("Запуск периодического задания. Operation: " + task.getOperationName() + " CronExp:" + task.getCronExp() + "TimeInterval:" + task.getTimeInterval());
		//this.operationName = task.getOperationName();
		this.unloadDir     = task.getDir();
		this.partCount =  getUnloadPartCount();
		this.unloadRepeatInterval = getRepeatInterval();
		this.result = new PereodicalTaskResult();
		this.result.setTask(task);

		this.endDate   =  Calendar.getInstance();
		if (lastResult != null)
			/*начальная дата прошлого, есть дата текущего*/
			this.startDate =  lastResult.getStartDate();
		else
			/*если еще не запускался то берем промежуток с текущей даты по "минус" n дней*/
			this.startDate = DateHelper.getPreviousNDay(this.endDate,unloadRepeatInterval);

		this.result.setStartDate(this.endDate);
		this.isAuto = true;
	}


	public void manualInitialize(Calendar startDate, Calendar endDate) throws BusinessException, BusinessLogicException
	{
		this.result = new PereodicalTaskResult();
		this.startDate =  startDate;
		this.endDate   =  endDate;
		this.isAuto = false;
	}

	public PereodicalTaskResult execute() throws BusinessException, BusinessLogicException
	{
      try
		{   /*основоная выгрузка*/
			unloadCycle();
		   /* дополнителльная выгрузка */
			this.endDate = getResult().getStartDate();
			this.startDate = DateHelper.getPreviousNDay(this.endDate,unloadRepeatInterval);
			unloadCycle();

		}
		finally
		{
			this.result.setEndDate(Calendar.getInstance());
			if (this.isAuto)
				updateReport();
		}

		return this.result;
	}

	/**
	 * пачковая выгрузка
	 * @throws BusinessException
	 */
	private void unloadCycle() throws BusinessException
	{
		List<? extends GateExecutableDocument> partList;
		while (!(partList = getDataPack(this.partCount)).isEmpty())
		{
			 int size = partList.size();
			/*запоминаем дату последнего, и устанавливает её в качестве даты начала */
			this.startDate = partList.get(partList.size()-1).getExecutionDate();
			this.result.setTotalResultCount(this.result.getTotalResultCount()+size);
			packProcessor(partList);
			/*не имеет смысла делать еще один запрос если размер пачки меньше ожидаемого*/
			if (size<this.partCount) break;
		}
	}

	/**
	 *  Метод инкапсулирующий в себя логику(попорядку)
	 *	обработки БС
	 *  созданию  файла
	 *	доп. обработка файла
	 *	закрытие открытых потоков
	 *  обновление статусов докуметов
	 *	обновление репорта
	 * @param partList входный документы
	 * @throws BusinessException
	 */
	private void packProcessor(List<? extends GateExecutableDocument> partList) throws BusinessException
	{    /* генерим сущьности для выгрузки, запоминаем id успешно созданных !!!claimsIds!!!*/

		if (partList != null && !partList.isEmpty())
		{
			Set<Long> claimsIds = new HashSet<Long>();      //id выгруженных сущьностей
			StringBuilder  sb = new StringBuilder();
			String str = "";
			for (GateExecutableDocument loan: partList)
			{
				try
				{
					str	= getUnloadedDataString(loan);
				}
				catch(ClaimUnloadExceptoin e)
				{   /*ошибочные  логируем и пропускаем*/
				    addReportError(e.getMessage());
					continue;
				}
				sb.append(str);
				claimsIds.add(loan.getId());
			}

			String	unloadDataString = sb.toString();
			/*для дополнительной обработки(т.е добавление хеддоров, названий файлов т.д)*/
			unloadDataString = additionalResultStringUpdate(unloadDataString);

			if (!StringHelper.isEmpty(unloadDataString))
			{   /* создаем  файл, пачки*/
			   String fileName = getFileName();
			   Pair<FileOutputStream,File> tempFilePair = creatFileAndStream(fileName,this.unloadDir);
			   try
			   {   /* сгинерированные сущьности  пишим в tmp. По завершению поток не закрываем */
				   if (!writToFile(unloadDataString,tempFilePair)) return;
				   /*производим дополнительную обработку,(т.е переименовываем в конечный файл или создаем архив)*/
				  String endFileName = additionalFileUpdate(tempFilePair);
				   if (StringHelper.isEmpty(endFileName)) return;

				   try
				   {   /*обновление статусов докуметов*/
					   documentUpdateState(claimsIds);
				   }
				   catch(BusinessException e)
				   {  /*в случаи проблем с обновлением статусов, удаляем весь файл, дабы не допустить повторной выгрузки*/
					  Pair<FileOutputStream,File> endFilePair =  creatFileAndStream(endFileName,this.unloadDir);
					  close(endFilePair);
					  addReportError(DOCUMENT_UPDATE_STATUS_ERROR,e);
				   }
			   }
			   finally
			   {
				   finalFileAction(tempFilePair);
			   }
			}
		}
	}

	/**
	 * удаляем поток, закрываем файл
	 * @param filePair пара Поток, Файл
	 */
	protected void close(Pair<? extends Closeable,File> filePair)
	{
		if (filePair == null)
		{
		    addReportError(NO_FILE_PAIR);
			return;
		}

		File file = filePair.getSecond();
		String name = "";
		try
		{
			Closeable stream = filePair.getFirst();
			if (stream != null)
			{
				stream.close();
			}
			if (file != null)
			{
				name = file.getName();
				if (!file.delete())
					addReportError(name + DELET_FILE_ERROR );
			}
		}
		catch (IOException e)
		{
			String errText = name + CLOSE_STREAM_ERROR;
			addReportError(errText,e);
		}
	}

	/**
	 * Пишем строку в поток
	 * @param dataString  Сгенерированная строка с данными
	 * @param filePair выходной поток
	 * @return если удачто то true/ если нет то false
	 */
	private boolean writToFile(String dataString,Pair<FileOutputStream,File> filePair)
	{
		FileOutputStream fileOutputStream = filePair.getFirst();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		if(fileOutputStream != null)
		{
			try
			{
				bufferedOutputStream.write(dataString.getBytes(getEncoding()));
				bufferedOutputStream.flush();
			}
			catch (IOException e)
			{
				addReportError(WRITE_STREAM_ERROR,e);
				return false;
			}
			finally
			{
				close(new Pair(bufferedOutputStream,null));
				close(new Pair(fileOutputStream,null));
			}
			return true;
		}
		return false;
	}

	/**
	 * добавляем сообщение с ошибкой
	 * @param errText текст ошибки
	 */
	public String appendError(String errText)
	{
		PereodicalTaskError error = new PereodicalTaskError();
		error.setResult(this.result);
		error.setErrText(errText);
		this.errors.add(error);
		return errText;
	}

	/**
	 * добавляем сообщение с ошибкой к результату + лигируем
	 * @param errText текст ошибки
	 * @param e ошибка
	 */
	public void addReportError(String errText,Throwable e)
	{
		log.error(appendError(errText), e);
	}

	/**
	 * добавляем сообщение к результату + логируем
	 * @param errText текст ошибки
	 */
	public void addReportError(String errText)
	{
		log.error(appendError(errText));
	}

	/**
	 * сохраняем текущий результат и все ошибки
	 */
	private void updateReport()
	{
		try
		{
			simpleService.addOrUpdate(result);
			simpleService.addList(this.errors);
		}
		catch (BusinessException e)
		{
			log.error("Task id = " + result.getTask().getId() +  FILE_CREATE_ERROR, e);
		}
	}

	/**
	 * создаем файл
	 * @param fileName имя файла
	 * @param unloadDir путь к каталогу выгрузки
	 * @return созданный поток и файл
	 */
	private Pair<FileOutputStream,File> creatFileAndStream(String fileName,String unloadDir)
	{
		File file = null;
	    FileOutputStream outputStream = null;
		try
		{
			if (!StringHelper.isEmpty(unloadDir))
			{
				file = createFile(unloadDir, fileName);
				outputStream = new FileOutputStream(file);
			}
			else
			{
				addReportError(NO_PATH_ERRPR);
				throw new BusinessException(NO_PATH_ERRPR);
			}
		}
		catch (BusinessException e)
		{
			addReportError(GET_FILE_NAME_ERROR,e);
		}
		catch (FileNotFoundException e)
		{
			addReportError(FILE_CREATE_ERROR,e);

		}
		catch (IOException e)
		{
			addReportError(FILE_CREATE_ERROR,e);
		}
		return new Pair(outputStream,file);
	}

	/**
	 * Обновляем статусы на ADOPT выгруженным сущностям (claimsIds)
	 * @param claimsIds id документов
	 * @throws BusinessException
	 */
	private void documentUpdateState(Set<Long> claimsIds) throws BusinessException
	{
		try
		{
			for (Long claimId : claimsIds)
			{

                BusinessDocument claim = businessDocumentService.findById(claimId);
				if (claim == null)
					log.error("Не найдена заявка с id " + claimId);
				StateMachineExecutor executor = new StateMachineExecutor(paymentStateMachineService.getStateMachineByFormName(claim.getFormName()));
						executor.initialize(claim);
						if (claim instanceof ExtendedLoanClaim)
							executor.fireEvent(new ObjectEvent(DocumentEvent.EXECUTE, "system"));
						else
							executor.fireEvent(new ObjectEvent(DocumentEvent.ADOPT, "system"));
						businessDocumentService.addOrUpdate(claim);
						this.result.successRecordProcessed();
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private int getUnloadPartCount()
	{
		return ConfigFactory.getConfig(UnloadConfig.class).getUnloadPartCount();
	}

	/**
	 * для дополнительной обработки(т.е добавление хеддоров, названий файлов т.д)
 	 * @param str входная строка для доп обработки
	 * @return конечную строку
	 */
	protected abstract String additionalResultStringUpdate(String str);

	/**
	 * производим дополнительную обработку,(т.е переименовываем в конечный файл или создаем архив)
	 *!РЕАЛИЗОВАТЬ в вышестоящей операции!ЕСЛИ НУЖНО
	 * @param filePair поток и файл предварительного файла
	 * @return имя конечного файла, признак удалять ли первичный файл
	 */
    protected abstract String additionalFileUpdate(Pair<FileOutputStream,File> filePair);

	/**
	 * конечные действия над файлом
	 * @param filePair пара Поток, Файл
	 */
    protected abstract void finalFileAction(Pair<FileOutputStream,File> filePair);

	/**
	 * получаем пачку c данными
	 *!РЕАЛИЗОВАТЬ в вышестоящей операции!
	 * @param maxResults конечный элемент
	 * @return выгружаемые документы
	 */
	public  abstract List<? extends GateExecutableDocument> getDataPack(Integer maxResults);

	/**
	 * Сгинерировать выгружаемую сущьность
	 * @param document бизнес документ
	 * @return сущьность для выгрузки
	 * @throws ClaimUnloadExceptoin
	 */
	public abstract String getUnloadedDataString(GateExecutableDocument document) throws ClaimUnloadExceptoin;

	/**
	 * метод для получения перида повторной выгрузки
	 * !РЕАЛИЗОВАТЬ в вышестоящей операции!
	 * @return период повторной выгрузки в днях
	 */
	public abstract int getRepeatInterval() throws BusinessException;

	/**
	 * имя выгружаемого файла
	 *!РЕАЛИЗОВАТЬ в вышестоящей операции!
	 * @return имя файла
	 * @throws BusinessException
	 */
	public abstract  String getFileName() throws BusinessException;

	/**
	 * @return кодировка выгружаемого файла
	 */
	public abstract String getEncoding();

	/**
	 * получить файл
	 * @param path Путь
	 * @param fileName Имя файла
	 * @return Файл для выгрузки
	 */
	private File createFile(String path, String fileName) throws IOException
	{
		return new File(FileHelper.getCurrentFilePath(path, fileName));
	}

	public DirInteractionPereodicalTask createBackroundTask() throws BusinessException, BusinessLogicException
	{
		return null;  //пока что пропускаем
	}

	public PereodicalTaskResult getResult()
	{
		return result;
	}

	public void setResult(PereodicalTaskResult result)
	{
		this.result = result;
	}

	public List<PereodicalTaskError> getErrors()
	{
		return errors;
	}

	public void setErrors(List<PereodicalTaskError> errors)
	{
		this.errors = errors;
	}



}
