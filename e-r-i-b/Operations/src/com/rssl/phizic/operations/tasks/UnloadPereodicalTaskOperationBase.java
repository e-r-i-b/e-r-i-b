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
	private static final String GET_FILE_NAME_ERROR = " ������ ��� ������� �������� ��� �����";
	private static final String FILE_CREATE_ERROR = " ������ �������� �����";
	private static final String NO_PATH_ERRPR = " ��������� ������� �������� �� ��������";
	protected static final String GET_PART_ERROR = " ������ � �������� ���������  ������ ���������";
	protected static final String CLOSE_STREAM_ERROR = " ������ �������� ������";
	protected static final String WRITE_STREAM_ERROR = " ������ ��� ������� ������ � �����";
	protected static final String FLUSH_STREAM_ERROR = " ������ ��� ������� ������ ������ � �����";
	protected static final String DELET_FILE_ERROR = " ������ ��� ������� ������� ����";
	protected static final String NO_FILE_PAIR = " �����/���� �� ������";
	protected static final String DOCUMENT_UPDATE_STATUS_ERROR = " �������� ��� ���������� �������� ����������";

	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private PereodicalTaskService pereodicalTaskService = new PereodicalTaskService();
	private final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();
	private static BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected Calendar startDate = null;
	protected Calendar endDate = null;
	protected String unloadDir = "";                 //���������� ��������
	protected int partCount;                  // ������ "�����" ��������
	protected int unloadRepeatInterval;       // �������� ��������� �������� � ����
	private PereodicalTaskResult result;               //��������� ���������� ��������
	protected List<PereodicalTaskError> errors = new ArrayList<PereodicalTaskError>(); //������ ������ �� ��������
	protected boolean isAuto = false;                                  //������� ������������� �������� � �������������� ��������

	/**
	 * �������������� ��������� �������� ������� �� �����
	 * @param task
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(DirInteractionPereodicalTask task) throws BusinessException, BusinessLogicException
	{
		PereodicalTaskResult lastResult = (PereodicalTaskResult) pereodicalTaskService.getLastResultByTask(task);
		log.error("������ �������������� �������. Operation: " + task.getOperationName() + " CronExp:" + task.getCronExp() + "TimeInterval:" + task.getTimeInterval());
		//this.operationName = task.getOperationName();
		this.unloadDir     = task.getDir();
		this.partCount =  getUnloadPartCount();
		this.unloadRepeatInterval = getRepeatInterval();
		this.result = new PereodicalTaskResult();
		this.result.setTask(task);

		this.endDate   =  Calendar.getInstance();
		if (lastResult != null)
			/*��������� ���� ��������, ���� ���� ��������*/
			this.startDate =  lastResult.getStartDate();
		else
			/*���� ��� �� ���������� �� ����� ���������� � ������� ���� �� "�����" n ����*/
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
		{   /*��������� ��������*/
			unloadCycle();
		   /* ��������������� �������� */
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
	 * �������� ��������
	 * @throws BusinessException
	 */
	private void unloadCycle() throws BusinessException
	{
		List<? extends GateExecutableDocument> partList;
		while (!(partList = getDataPack(this.partCount)).isEmpty())
		{
			 int size = partList.size();
			/*���������� ���� ����������, � ������������� � � �������� ���� ������ */
			this.startDate = partList.get(partList.size()-1).getExecutionDate();
			this.result.setTotalResultCount(this.result.getTotalResultCount()+size);
			packProcessor(partList);
			/*�� ����� ������ ������ ��� ���� ������ ���� ������ ����� ������ ����������*/
			if (size<this.partCount) break;
		}
	}

	/**
	 *  ����� ��������������� � ���� ������(���������)
	 *	��������� ��
	 *  ��������  �����
	 *	���. ��������� �����
	 *	�������� �������� �������
	 *  ���������� �������� ���������
	 *	���������� �������
	 * @param partList ������� ���������
	 * @throws BusinessException
	 */
	private void packProcessor(List<? extends GateExecutableDocument> partList) throws BusinessException
	{    /* ������� ��������� ��� ��������, ���������� id ������� ��������� !!!claimsIds!!!*/

		if (partList != null && !partList.isEmpty())
		{
			Set<Long> claimsIds = new HashSet<Long>();      //id ����������� ����������
			StringBuilder  sb = new StringBuilder();
			String str = "";
			for (GateExecutableDocument loan: partList)
			{
				try
				{
					str	= getUnloadedDataString(loan);
				}
				catch(ClaimUnloadExceptoin e)
				{   /*���������  �������� � ����������*/
				    addReportError(e.getMessage());
					continue;
				}
				sb.append(str);
				claimsIds.add(loan.getId());
			}

			String	unloadDataString = sb.toString();
			/*��� �������������� ���������(�.� ���������� ��������, �������� ������ �.�)*/
			unloadDataString = additionalResultStringUpdate(unloadDataString);

			if (!StringHelper.isEmpty(unloadDataString))
			{   /* �������  ����, �����*/
			   String fileName = getFileName();
			   Pair<FileOutputStream,File> tempFilePair = creatFileAndStream(fileName,this.unloadDir);
			   try
			   {   /* ��������������� ���������  ����� � tmp. �� ���������� ����� �� ��������� */
				   if (!writToFile(unloadDataString,tempFilePair)) return;
				   /*���������� �������������� ���������,(�.� ��������������� � �������� ���� ��� ������� �����)*/
				  String endFileName = additionalFileUpdate(tempFilePair);
				   if (StringHelper.isEmpty(endFileName)) return;

				   try
				   {   /*���������� �������� ���������*/
					   documentUpdateState(claimsIds);
				   }
				   catch(BusinessException e)
				   {  /*� ������ ������� � ����������� ��������, ������� ���� ����, ���� �� ��������� ��������� ��������*/
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
	 * ������� �����, ��������� ����
	 * @param filePair ���� �����, ����
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
	 * ����� ������ � �����
	 * @param dataString  ��������������� ������ � �������
	 * @param filePair �������� �����
	 * @return ���� ������ �� true/ ���� ��� �� false
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
	 * ��������� ��������� � �������
	 * @param errText ����� ������
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
	 * ��������� ��������� � ������� � ���������� + ��������
	 * @param errText ����� ������
	 * @param e ������
	 */
	public void addReportError(String errText,Throwable e)
	{
		log.error(appendError(errText), e);
	}

	/**
	 * ��������� ��������� � ���������� + ��������
	 * @param errText ����� ������
	 */
	public void addReportError(String errText)
	{
		log.error(appendError(errText));
	}

	/**
	 * ��������� ������� ��������� � ��� ������
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
	 * ������� ����
	 * @param fileName ��� �����
	 * @param unloadDir ���� � �������� ��������
	 * @return ��������� ����� � ����
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
	 * ��������� ������� �� ADOPT ����������� ��������� (claimsIds)
	 * @param claimsIds id ����������
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
					log.error("�� ������� ������ � id " + claimId);
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
	 * ��� �������������� ���������(�.� ���������� ��������, �������� ������ �.�)
 	 * @param str ������� ������ ��� ��� ���������
	 * @return �������� ������
	 */
	protected abstract String additionalResultStringUpdate(String str);

	/**
	 * ���������� �������������� ���������,(�.� ��������������� � �������� ���� ��� ������� �����)
	 *!����������� � ����������� ��������!���� �����
	 * @param filePair ����� � ���� ���������������� �����
	 * @return ��� ��������� �����, ������� ������� �� ��������� ����
	 */
    protected abstract String additionalFileUpdate(Pair<FileOutputStream,File> filePair);

	/**
	 * �������� �������� ��� ������
	 * @param filePair ���� �����, ����
	 */
    protected abstract void finalFileAction(Pair<FileOutputStream,File> filePair);

	/**
	 * �������� ����� c �������
	 *!����������� � ����������� ��������!
	 * @param maxResults �������� �������
	 * @return ����������� ���������
	 */
	public  abstract List<? extends GateExecutableDocument> getDataPack(Integer maxResults);

	/**
	 * ������������� ����������� ���������
	 * @param document ������ ��������
	 * @return ��������� ��� ��������
	 * @throws ClaimUnloadExceptoin
	 */
	public abstract String getUnloadedDataString(GateExecutableDocument document) throws ClaimUnloadExceptoin;

	/**
	 * ����� ��� ��������� ������ ��������� ��������
	 * !����������� � ����������� ��������!
	 * @return ������ ��������� �������� � ����
	 */
	public abstract int getRepeatInterval() throws BusinessException;

	/**
	 * ��� ������������ �����
	 *!����������� � ����������� ��������!
	 * @return ��� �����
	 * @throws BusinessException
	 */
	public abstract  String getFileName() throws BusinessException;

	/**
	 * @return ��������� ������������ �����
	 */
	public abstract String getEncoding();

	/**
	 * �������� ����
	 * @param path ����
	 * @param fileName ��� �����
	 * @return ���� ��� ��������
	 */
	private File createFile(String path, String fileName) throws IOException
	{
		return new File(FileHelper.getCurrentFilePath(path, fileName));
	}

	public DirInteractionPereodicalTask createBackroundTask() throws BusinessException, BusinessLogicException
	{
		return null;  //���� ��� ����������
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
