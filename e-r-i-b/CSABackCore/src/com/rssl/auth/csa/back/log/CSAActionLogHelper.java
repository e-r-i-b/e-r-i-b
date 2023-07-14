package com.rssl.auth.csa.back.log;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogProfileIdentificationContext;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.csa.CSAOperationKind;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.csaAction.CSAActionGuestLogEntry;
import com.rssl.phizic.logging.csaAction.CSAActionLogConfig;
import com.rssl.phizic.logging.csaAction.CSAActionLogEntry;
import com.rssl.phizic.logging.csaAction.CSAActionLogEntryBase;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.CSAActionLogWriter;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.logging.Log;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 * Хелпер по журнализированию входов клиента.
 */
public class CSAActionLogHelper
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	/**
	 * Запись в журнал входов.
	 * @param requestInfo - исходный запрос
	 * @param context - контекст, идентифицирующий профиль.
	 * @param error - исключение.
	 */
	public static void writeToActionLog(RequestInfo requestInfo, LogIdentificationContext context, Throwable error)
	{
		try
		{
			writeToActionLog(createEntry(requestInfo, context, error));
		}
		catch (Throwable t)
		{
			//падение записи в журнал не должно нарушать работу системы.
			log.error(t);
		}
	}

	/**
	 * Запись в журнал входов.
	 * @param requestInfo - исходный запрос
	 * @param context - контекст, идентифицирующий профиль.
	 * @param error - исключение.
	 * @param confirmableOperation - операция подтверждения.
	 */
	public static void writeToActionLog(ConfirmableOperationBase confirmableOperation, RequestInfo requestInfo, LogIdentificationContext context,  Throwable error)
	{
		try
		{
			CSAActionLogEntry entry = createEntry(requestInfo, context,  error);

			if (confirmableOperation != null)
			{
				ConfirmStrategyType strategyType = confirmableOperation.getConfirmStrategyType();
				if (ConfirmStrategyType.card == strategyType)
					entry.setCardNumber(confirmableOperation.getCardNumberFoSendConfirm());

				entry.setConfirmationType(confirmableOperation.getConfirmStrategyType().name());
			}
			writeToActionLog(entry);
		}
		catch (Throwable t)
		{
			//падение записи в журнал не должно нарушать работу системы.
			log.error(t);
		}
	}

	/**
	 * Сделать запись в лог о входе гостя
	 * @param requestInfo - инфрмация о запросе
	 * @param identificationContext - контекст идентификации
	 */
	public static void writeToGuestActionLog(RequestInfo requestInfo, LogProfileIdentificationContext identificationContext)
	{
		writeToGuestActionLog(requestInfo, identificationContext, null);
	}

	/**
	 * Сделать запись в лог о входе гостя
	 * @param requestInfo - информация о запросе
	 * @param identificationContext - контекст идентификации
	 * @param error - ошибка
	 */
	public static void writeToGuestActionLog(RequestInfo requestInfo, LogProfileIdentificationContext identificationContext, Throwable error)
	{
		writeToActionLog(createGuestEntry(requestInfo, identificationContext, error));
	}

	private static CSAActionGuestLogEntry createGuestEntry(RequestInfo requestInfo, LogProfileIdentificationContext identificationContext, Throwable error)
	{
		CSAActionGuestLogEntry entry = new CSAActionGuestLogEntry();
		entry.setStartDate(Calendar.getInstance());
		entry.setClientFirstName(LogThreadContext.getFirstName());
		entry.setClientPatrName(LogThreadContext.getPatrName());
		entry.setClientSurName(LogThreadContext.getSurName());
		entry.setPhoneNumber(LogThreadContext.getGuestPhoneNumber());

		entry.setErrorMessage(createErrorMessage(error));

		entry.setIdentificationType(identificationContext.getIdentificationType());
		entry.setIdentificationParam(identificationContext.getIdentificationParam());

		entry.setIpAddress(LogThreadContext.getIPAddress());
		entry.setLogUID(LogThreadContext.getLogUID());
		entry.setOperationType(CSAOperationKind.fromValue(requestInfo.getType()));
		entry.setPassport(LogThreadContext.getNumber());
		entry.setBirthDate(LogThreadContext.getBirthday());
		entry.setTb(LogThreadContext.getDepartmentRegion());

		return entry;
	}

	private static CSAActionLogEntry createEntry(RequestInfo requestInfo, LogIdentificationContext logContext, Throwable error) throws Exception
	{
		CSAActionLogEntry entry = new CSAActionLogEntry();
		IdentificationContext context = logContext.getIdentificationContext();
		Element element = requestInfo.getBody().getDocumentElement();
		entry.setStartDate(Calendar.getInstance());
		entry.setClientFirstName(context.getProfile().getFirstname().trim());
		entry.setClientSurName(context.getProfile().getSurname().trim());
		entry.setClientPatrName(context.getProfile().getPatrname() != null ? context.getProfile().getPatrname().trim() : null);
		entry.setCardNumber(context.getCardNumber());
		entry.setErrorMessage(createErrorMessage(error));
		entry.setIdentificationType(logContext.getIdentificationType());
		entry.setIdentificationParam(logContext.getIdentificationParam());
		entry.setIpAddress(LogThreadContext.getIPAddress());
		entry.setLogUID(LogThreadContext.getLogUID());
		entry.setOperationType(CSAOperationKind.fromValue(requestInfo.getType()));
		entry.setPassport(context.getProfile().getPassport().replaceAll(" ",""));
		entry.setBirthDate(context.getProfile().getBirthdate());
		entry.setTb(context.getProfile().getTb());
		//для запросов исходящих со стороны АРМ струдника логируем сотрудника(данные из тела запроса).
		entry.setEmployeeFio(XmlHelper.getSimpleElementValue(element, Constants.EMPLOYEE_NAME));
		entry.setEmployeeLogin(XmlHelper.getSimpleElementValue(element, Constants.EMPLOYEE_GUID));
		return entry;
	}

	/**
	 * Запись в лог записи журнала входов ЦСА.
	 * @param entry - сущность.
	 */
	private static void writeToActionLog(CSAActionLogEntryBase entry)
	{
		if(entry == null)
			return;
		CSAActionLogConfig config = ConfigFactory.getConfig(CSAActionLogConfig.class);
		CSAActionLogWriter writer = config.getWriter();
		try
		{
			if (writer != null)
			{
				writer.write(entry);
			}
		}
		catch (Throwable t)
		{
			//падение записи в журнал не должно нарушать работу системы.
			log.error(t);
		}
	}

	private static String createErrorMessage(Throwable error)
	{
		StringBuffer buf = new StringBuffer();

		if (error != null)
		{
			buf.append("[ERROR] - ");
			buf.append(" <");
			buf.append(error.toString());
			buf.append(">");

			java.io.StringWriter sw = new java.io.StringWriter(1024);
			java.io.PrintWriter pw = new java.io.PrintWriter(sw);
			try
			{
				pw = new java.io.PrintWriter(sw);
				error.printStackTrace(pw);
			}
			finally
			{
				pw.close();
			}
			buf.append(sw.toString());
		}
		return buf.toString();
	}
}
