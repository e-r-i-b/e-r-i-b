package com.rssl.phizic.gate.payments;

import com.rssl.phizic.ConfigurationCheckedException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.errors.ErrorMessagesMatcher;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.RedirectGateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.util.List;

/**
 * @author Krenev
 * @ created 22.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class ErrorProcessor
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private ErrorSystem system;
	private ThreadLocal<GateDocument> document = new ThreadLocal<GateDocument>();

	public void setDocument(GateDocument document)
	{
		this.document.set(document);
	}

	public ErrorProcessor(ErrorSystem system)
	{
		this.system = system;
	}

	/**
	 * ������ ������, � ����������� �� �������� ������� ��� ����� �����
	 * @param error
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void parseError(String error) throws GateException, GateLogicException
	{
		try
		{
			ErrorMessagesMatcher matcher = ErrorMessagesMatcher.getInstance();
			List<ErrorMessage> messages = matcher.matchMessage(error, system);
			if (messages == null || messages.size() == 0)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("��������� �� ������ ������� ").append(system).append(" �� ��������:").append(error).append(". ");
				sb.append(getDocumentData());
				log.warn(sb.toString());
				throw new TemporalGateException(sb.toString());
			}
			else
			{
				if (messages.size() > 1)
				{
					StringBuilder sb = new StringBuilder();
					sb.append("��� ���������:\"").append(error).append("\" ���� ������� ��������� ��������:");
					for (ErrorMessage errorMessage : messages)
					{
						sb.append(errorMessage.getMessage());
					}
					sb.append(getDocumentData());
					log.warn(sb.toString());
					//TODO �������� ������������ ����������� ���������� 1 ��������� ���������
					throw new RedirectGateLogicException(messages.get(0).getMessage());
				}
				else
				{
					throw new RedirectGateLogicException(messages.get(0).getMessage());
				}
			}
		}
		catch (ConfigurationCheckedException e)
		{
			throw new GateException(e);
		}
	}

	private String getDocumentData()
	{
		if (document.get() == null){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("��������� ID ���������:").append(document.get().getId()).append(". ");
		if (document instanceof SynchronizableDocument)
			sb.append(" ������� ID ���������:").append(((SynchronizableDocument)document).getExternalId()).append(". ");
		return sb.toString();
	}
}
