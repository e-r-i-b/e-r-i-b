package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.pfr.PFRStatement;
import com.rssl.phizic.business.pfr.PFRStatementService;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.pfr.PFRService;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import org.quartz.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Джоб для выемки выписок
 * получает все заявки со статусом STATEMENT_READY,
 * для каждой из которых получает выписку и записывает ее в соответствующую таблицу с помощью PFRStatementService
 * @author Mescheryakova
 * @ created 29.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class PFRStatementJob extends BaseJob implements StatefulJob, InterruptableJob
{
	private final static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	private final static BusinessDocumentService documentService = new BusinessDocumentService();
	private final static PFRStatementService pfrStatementService = new PFRStatementService();
	private volatile boolean isInterrupt;

	public PFRStatementJob() throws JobExecutionException
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		PFRService pfrService = GateSingleton.getFactory().service(PFRService.class);
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);

			int startFormRow = 0;
			while(!isInterrupt)
			{
				int maxRows = ConfigFactory.getConfig(JobRefreshConfig.class).getMaxRowsFromFetch();
				try
				{
					List<PFRStatementClaim> pfrStatementClaims = documentService.findPFRClaimByState(DocumentEvent.STATEMENT_READY.name(), startFormRow, maxRows);
					if (pfrStatementClaims.isEmpty())   // ничего в базе нет => можно выходить из джоба
						break;

					for (PFRStatementClaim pfrStateClaim : pfrStatementClaims)
					{
						try
						{
							// если требуется остановится выходим из цикла
							if(isInterrupt)
								break;

							String statementXml = pfrService.getStatement(pfrStateClaim);
							
							if (statementXml == null)
							{
								log.error("Выписка не получена для платежа с id=" + pfrStateClaim.getId());
								pfrStateClaim.incrementCountError();
								updateDocumentService.update(pfrStateClaim);

								if (pfrStateClaim.getCountError() > 3)
									throw new BusinessException("Превышено максимально возможное количество неполучения выписки");									

								startFormRow++;  // не будем больше его получать в данном запуске джооба
								continue;								
							}

							PFRStatement statement = new PFRStatement();
							statement.setClaimId(pfrStateClaim.getId());
							statement.setStatementXml(statementXml);
							pfrStatementService.add(statement);

							updateDocumentService.update(pfrStateClaim, new DocumentCommand(DocumentEvent.EXECUTE, new HashMap<String, Object>()));      // получено
						}
						catch (InactiveExternalSystemException e)
						{
							//внешняя система в данный момент неактивна, обработку платежа пропускаем
							log.error(e.getMessage() + " Обрабатываемый платеж id = " + pfrStateClaim.getId(), e);
						}
						catch (GateTimeOutException e)
						{
							//внешняя система в данный момент неактивна, обработку платежа пропускаем
							log.error(e.getMessage() + " Обрабатываемый платеж id = " + pfrStateClaim.getId(), e);
						}
						catch(Throwable e)
						{
							log.error("Ошибка при обработке платежа с id=" + pfrStateClaim.getId(), e);
							try
							{
								Map<String, Object> additionalFields = new HashMap<String, Object>();
								additionalFields.put(DocumentCommand.ERROR_TEXT, "Отказано по причине ошибки в документе");
								updateDocumentService.update(pfrStateClaim, new DocumentCommand(DocumentEvent.REFUSE, additionalFields));      //  отказ документа
							}
							catch(Throwable ge)
							{
								log.error("Ошибка обновления статуса у платежа с id=" + pfrStateClaim.getId(), ge);
								startFormRow++;  // если ошибка обновления статуса у документа, то его не  будем получать из БД в следующей итерации (иначе будет зациклевание)
							}
						}
					}
				}
				catch(Exception e)
				{
					// неизвестная ошибка, выйдем из джоба, чтобы не было зациклевания
					log.error("Ошибка в джобе для выемки выписок ", e);
				}
			}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
