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
 * ���� ��� ������ �������
 * �������� ��� ������ �� �������� STATEMENT_READY,
 * ��� ������ �� ������� �������� ������� � ���������� �� � ��������������� ������� � ������� PFRStatementService
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
					if (pfrStatementClaims.isEmpty())   // ������ � ���� ��� => ����� �������� �� �����
						break;

					for (PFRStatementClaim pfrStateClaim : pfrStatementClaims)
					{
						try
						{
							// ���� ��������� ����������� ������� �� �����
							if(isInterrupt)
								break;

							String statementXml = pfrService.getStatement(pfrStateClaim);
							
							if (statementXml == null)
							{
								log.error("������� �� �������� ��� ������� � id=" + pfrStateClaim.getId());
								pfrStateClaim.incrementCountError();
								updateDocumentService.update(pfrStateClaim);

								if (pfrStateClaim.getCountError() > 3)
									throw new BusinessException("��������� ����������� ��������� ���������� ����������� �������");									

								startFormRow++;  // �� ����� ������ ��� �������� � ������ ������� ������
								continue;								
							}

							PFRStatement statement = new PFRStatement();
							statement.setClaimId(pfrStateClaim.getId());
							statement.setStatementXml(statementXml);
							pfrStatementService.add(statement);

							updateDocumentService.update(pfrStateClaim, new DocumentCommand(DocumentEvent.EXECUTE, new HashMap<String, Object>()));      // ��������
						}
						catch (InactiveExternalSystemException e)
						{
							//������� ������� � ������ ������ ���������, ��������� ������� ����������
							log.error(e.getMessage() + " �������������� ������ id = " + pfrStateClaim.getId(), e);
						}
						catch (GateTimeOutException e)
						{
							//������� ������� � ������ ������ ���������, ��������� ������� ����������
							log.error(e.getMessage() + " �������������� ������ id = " + pfrStateClaim.getId(), e);
						}
						catch(Throwable e)
						{
							log.error("������ ��� ��������� ������� � id=" + pfrStateClaim.getId(), e);
							try
							{
								Map<String, Object> additionalFields = new HashMap<String, Object>();
								additionalFields.put(DocumentCommand.ERROR_TEXT, "�������� �� ������� ������ � ���������");
								updateDocumentService.update(pfrStateClaim, new DocumentCommand(DocumentEvent.REFUSE, additionalFields));      //  ����� ���������
							}
							catch(Throwable ge)
							{
								log.error("������ ���������� ������� � ������� � id=" + pfrStateClaim.getId(), ge);
								startFormRow++;  // ���� ������ ���������� ������� � ���������, �� ��� ��  ����� �������� �� �� � ��������� �������� (����� ����� ������������)
							}
						}
					}
				}
				catch(Exception e)
				{
					// ����������� ������, ������ �� �����, ����� �� ���� ������������
					log.error("������ � ����� ��� ������ ������� ", e);
				}
			}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
