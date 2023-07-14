package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl;
import com.rssl.phizgate.mobilebank.MobileBankTemplateImpl;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.TechnicalException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLStringWriter;
import com.rssl.phizic.utils.xml.XMLWriter;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.commons.collections.CollectionUtils;
import org.dom4j.io.OutputFormat;

import java.sql.*;
import java.util.*;
import javax.sql.rowset.CachedRowSet;
import javax.xml.bind.JAXBException;

import static com.rssl.phizicgate.mobilebank.MBKConstants.MBK_PHONE_NUMBER_FORMAT;

/**
 * User: Moshenko
 * Date: 29.08.13
 * Time: 9:10
 * Экшен вызывающий хранимую процедуру миграции МБК.
 * Хранимая процедура возвращает описание Профиля Клиента в МБК в виде стандартного резалтсета MS SQL.
 */
public class BeginMigrationJDBCAction extends JDBCActionBase<BeginMigrationResult>
{
	// RETCODE
	//УСПЕШНО –Миграционная транзакция инициирована. МБК ожидает вызов COMMIT MIGRATION или ROLLBACK MIGRATION. В резалтсете –описание Подключений МБК.
	private static final int SUCCESSFUL = 0;
	//УСПЕШНО –Миграционная транзакция инициирована. МБК ожидает вызов COMMIT MIGRATION или ROLLBACK MIGRATION. Резалтсет не возвращался
	private static final int SUCCESSFUL_EMPTY = 1401000000;
	//УСПЕШНО - Переданным критериям не соответствует ни одно из Подключений в МБК. Миграция НЕ инициирована
	private static final int NOT_FOUND = 1401000001;

	//ОШИБКА – ошибка в формате одного из параметров хранимой процедуры
	private static final int FORMAT_ERR = 1401270001;
	//ОШИБКА – один и тот же телефон упомянут и в параметре
	private static final int FORMAT_ERR_DUPL_PHONE = 1401270002;
	//ОШИБКА – не могут быть одновременно пусты параметры @PhoneListToMigrate, @CardListToMigrate и @PhoneListToDelete
	private static final int FORMAT_ERR_EMPTY_PARAMS = 1401270003;
	//ОШИБКА – не могут быть одновременно: пуст параметр @CardListToMigrate и НЕ пуст параметр @PhoneListToDelete
	private static final int FORMAT_ERR_EMPTY_CARD_DELETE = 1401270004;
	//ОШИБКА –  @MigrationSource = «F», при этом в @CardListToMigrate используется дополнительная карта
	private static final int FORMAT_ERR_FPP_BUT_ADDITIONAL = 1401270005;
	//ОШИБКА – Уже существует инициированная и не подтвержденная миграционная транзакция
	private static final int INITIATED_MIGRATION_ERR = 1401270010;
	//ОШИБКА – на один из телефонов, упомянутых в @PhoneListToMigrate, @PhoneListToDelete в МБК установлена миграционная блокировка
	private static final int PHONE_BLOCKED_ERR = 1401270011;
	//ОШИБКА –  конфликт с реверсом миграции.
	private static final int REVERSED_ERR = 1401270012;
	//ОШИБКА – при миграции «на лету» для ФПП, МБК выявил подключение имеющее конфликт по телефону
	private static final int FPP_CONFLICT_ERR = 1401270100;
	//ОШИБКА – Карта упомянутая в @CardListToMigrate как дополнительная используется в МБК в качестве платежной карты подключения
	private static final int NOT_ADDITIONAL_ERR = 1401270101;
	//ОШИБКА –в МБК найдено подключение, у которого среди карт, являющихся платёжными и информационными, только часть упомянута в @CardListToMigrate
	private static final int NOT_ENOUGH_CARDS_ERR = 1401270102;
	//Техническая ошибка на стороне АС МБК – повторите запрос
	private static final int TECHNICAL_ERR = 1401278888;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	private String cardNumbersXml;
	private String phoneMigrateNumbersXml;
	private String phoneDeleteNumbersXml;
	private String migrationType;

	BeginMigrationJDBCAction(Set<MbkCard> cards, Set<String> phoneMigrateNumbers, Set<String> phoneDeleteNumbers, MigrationType migrationType) throws GateException
	{
		if ((phoneMigrateNumbers != null && phoneDeleteNumbers != null) && CollectionUtils.containsAny(phoneMigrateNumbers, phoneDeleteNumbers))
			throw new GateException("Список телефонов подлежащих миграции и список телефонов на отключение, содержат пересекающиеся значения.");

		if (CollectionUtils.isNotEmpty(cards))
		{
			cardNumbersXml = makeCardsXml(cards);
		}

		if (CollectionUtils.isNotEmpty(phoneMigrateNumbers))
		{
			phoneMigrateNumbersXml = makePhonesXml(phoneMigrateNumbers);
		}

		if (CollectionUtils.isNotEmpty(phoneDeleteNumbers))
		{
			phoneDeleteNumbersXml = makePhonesXml(phoneDeleteNumbers);
		}

		this.migrationType = migrationType.getCode();
	}

	public BeginMigrationResult doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("ERMB_BeginMigration");
		messageLogger.setSQLStatement("? = call ERMB_BeginMigration");
		messageLogger.addInputParam("CardsToMigrate", cardNumbersXml);
		messageLogger.addInputParam("PhoneListToMigrate", phoneMigrateNumbersXml);
		messageLogger.addInputParam("PhoneListToDelete", phoneDeleteNumbersXml);
		messageLogger.addInputParam("MigrationSource", migrationType);

		CachedRowSet rs = null;
		CallableStatement cstmt =
				con.prepareCall("{ ? = call ERMB_BeginMigration" +
						"(@CardsToMigrate = ?,\n" +
						"@PhoneListToMigrate = ?,\n" +
						"@PhoneListToDelete = ?,\n" +
						"@MigrationSource = ?,\n" +
						"@MigrationID = ?,\n" +
						"@ErrNumber = ?,\n" +
						"@strErrDescr = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, cardNumbersXml);
			cstmt.setString(3, phoneMigrateNumbersXml);
			cstmt.setString(4, phoneDeleteNumbersXml);
			cstmt.setString(5, migrationType);
			cstmt.registerOutParameter(6, Types.BIGINT);  // MigrationID
			cstmt.registerOutParameter(7, Types.INTEGER);
			cstmt.registerOutParameter(8, Types.VARCHAR);

			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			int returnCode = cstmt.getInt(1);
			long migrationId = cstmt.getLong(6);
			int errNum = cstmt.getInt(7);
			String errDesk = cstmt.getString(8);

			messageLogger.setResultSet(rs);
			messageLogger.setResultCode(returnCode);
			messageLogger.addOutputParam("MigrationID", migrationId);
			messageLogger.addOutputParam("ErrNumber", errNum);
			messageLogger.addOutputParam("strErrDescr", errDesk);

			processStatus(returnCode, errNum, errDesk);
			return processResult(rs, migrationId, returnCode);
		}
		finally
		{
			messageLogger.finishEntry();
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
			if (rs != null)
				try { rs.close(); } catch (Exception ignore) {}
		}
	}

	private BeginMigrationResult processResult(ResultSet rs, long migrationId, int returnCode) throws SQLException, SystemException
	{
		BeginMigrationResult result = new BeginMigrationResult();

		List<MbkConnectionInfo> entityList = new LinkedList<MbkConnectionInfo>();
		if (rs != null)
		{
			while (rs.next())
			{
				MbkConnectionInfo info = new MbkConnectionInfo();
				info.setLinkID(rs.getInt(1));
				String phoneNumberStr = rs.getString(2);
				info.setPhoneNumber(PhoneNumber.fromString(phoneNumberStr));
				String paymentCard = rs.getString(3);
				info.setPymentCard(paymentCard);
				info.setInfoCards(getInfoCards(rs.getString(4)));
				info.setLinkTariff(rs.getInt(5));
				int linkPaymentBlickID = rs.getInt(6);
				info.setLinkPaymentBlockID(linkPaymentBlickID);
				info.setPhoneBlockCode(rs.getInt(7));
				info.setPhoneQuickServices(rs.getInt(8));
	            info.setPhoneOffers(getPhoneOffers(rs.getString(9)));
	            info.setTemplates(getTemplates(rs.getString(10), paymentCard, getStatus(linkPaymentBlickID), phoneNumberStr));
				entityList.add(info);
			}
		}
		//МБК ожидает вызов COMMIT по возвращенному migrationId только при коде возврата 0 или 1401000000
		//т.к. при 1401000001 migrationId успешно возвращается, но не используется,
		//в остальных (ошибочных) случаях возвращается невалидный (отрицательный) migrationId
		if (returnCode == SUCCESSFUL || returnCode == SUCCESSFUL_EMPTY)
		{
			result.setMigrationId(migrationId);
		}
		result.setMbkConnectionInfo(entityList);

		return result;
	}

	private MobileBankCardStatus getStatus(int satus) throws SystemException
	{
		switch (satus)
		{
			case 0:
				return MobileBankCardStatus.ACTIVE;
			case 1:
				return MobileBankCardStatus.INACTIVE;
			default:
				throw new SystemException("Неизвестный статус подключения.");
		}
	}

	private List<MobileBankTemplate> getTemplates(String xmlTemplatesStr, String cardNumber, MobileBankCardStatus mobileBankCardStatus, String phoneNumber) throws SystemException, SQLException
    {
        if (StringHelper.isEmpty(xmlTemplatesStr))
            return  null;

		try
		{
			TemplatesBean templatesBean = JAXBUtils.unmarshalBean(TemplatesBean.class, xmlTemplatesStr);
			List<TemplatesBean.Template> templates = templatesBean.getTemplate();
			List<MobileBankTemplate> mobileBankTemplatesList = new ArrayList<MobileBankTemplate>(templates.size());
			for (TemplatesBean.Template template : templates)
			{
				MobileBankTemplateImpl mobileBankTemplate = new MobileBankTemplateImpl();
				mobileBankTemplate.setRecipient(template.getOperationID());
				mobileBankTemplate.setPayerCodes(new String[]{template.getClientAccountID()});
				MobileBankCardInfoImpl cardInfo = new MobileBankCardInfoImpl();
				cardInfo.setCardNumber(cardNumber);
				cardInfo.setPhoneNumber(phoneNumber);
				cardInfo.setStatus(mobileBankCardStatus);
				mobileBankTemplate.setCardInfo(cardInfo);
				mobileBankTemplatesList.add(mobileBankTemplate);
			}
			return mobileBankTemplatesList;
		}
		catch (JAXBException e)
		{
			throw new SystemException(e);
		}

	}

    private List<InfoCardImpl> getInfoCards(String infoCardsStr) throws SQLException, SystemException
    {
       InfoCardsBean infoCards = null;
       try
       {
           infoCards = JAXBUtils.unmarshalBean(InfoCardsBean.class, infoCardsStr);
       }
       catch (JAXBException e)
       {
           throw new SystemException(e);
       }

        List<InfoCardImpl> infoList = new ArrayList<InfoCardImpl>();
        for (InfoCardsBean.CardType cardType:infoCards.getCards())
        {
            InfoCardImpl infoCard = new InfoCardImpl();
            infoCard.setPan(cardType.getPan());
            infoCard.setBlcok(cardType.isBlock());
            infoList.add(infoCard);
        }
        return infoList;
    }


    private List<String> getPhoneOffers(String phoneOffersStr) throws SQLException, SystemException
    {
        if (StringHelper.isEmpty(phoneOffersStr))
            return null;

        PhoneOffersBean phoneOffers = null;
        try
        {
            phoneOffers = JAXBUtils.unmarshalBean(PhoneOffersBean.class, phoneOffersStr);
        }
        catch (JAXBException e)
        {
            throw new SystemException(e);
        }
        return phoneOffers.getOffers();
    }

    private void processStatus(int returnCode, int errNum, String errDesk) throws SQLException, TechnicalException
	{
		StandInUtils.checkStandInAndThrow(returnCode);
		switch (returnCode)
		{
			case SUCCESSFUL:
			case SUCCESSFUL_EMPTY:
			case NOT_FOUND:
				break;
			case FORMAT_ERR:
			case FORMAT_ERR_DUPL_PHONE:
			case FORMAT_ERR_EMPTY_PARAMS:
			case FORMAT_ERR_EMPTY_CARD_DELETE:
			case FORMAT_ERR_FPP_BUT_ADDITIONAL:
			case INITIATED_MIGRATION_ERR:
			case PHONE_BLOCKED_ERR:
			case REVERSED_ERR:
			case FPP_CONFLICT_ERR:
			case NOT_ADDITIONAL_ERR:
			case NOT_ENOUGH_CARDS_ERR:
			{
				log.error("ERMB_BeginMigration вернуло ошибку. Код ошибки: " + returnCode + ". Описание:  " + errDesk);
				throw new SQLException(errDesk);
			}
			case TECHNICAL_ERR:
			{
				log.error("ERMB_BeginMigration вернуло ошибку. Код ошибки: " + returnCode + ". Описание:  " + errDesk);
				throw new TechnicalException(errDesk);
			}
			default:
			{
				log.error("ERMB_BeginMigration вернуло неизвестную ошибку. Код ошибки: " + returnCode + ". Описание:  " + errDesk);
				throw new SQLException(errDesk);
			}
		}
	}

    private String makeCardsXml(Set<MbkCard> cards)
    {
	    XMLWriter xmlWriter = new XMLStringWriter(OutputFormat.createCompactFormat());

	    xmlWriter.startElement("cards_to_migrate");
	    for (MbkCard card : cards)
	    {
		    xmlWriter.startElement("card");
		    {
			    xmlWriter.writeTextElement("nmbr", card.getNumber());
			    xmlWriter.writeTextElement("t", card.getCardType().getCode());
			    xmlWriter.writeTextElement("ermb", card.isErmbConnected() ? "1" : "0");
		    }
		    xmlWriter.endElement();
	    }
	    xmlWriter.endElement();

	    return xmlWriter.toString();
    }

    private String makePhonesXml(Set<String> phones)
    {
	    XMLWriter xmlWriter = new XMLStringWriter(OutputFormat.createCompactFormat());

	    xmlWriter.startElement("phones");
	    for (String phone : phones)
		    xmlWriter.writeTextElement("phone", MBK_PHONE_NUMBER_FORMAT.translate(phone));
	    xmlWriter.endElement();

        return xmlWriter.toString();
    }

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}

