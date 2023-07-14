package com.rssl.phizic.operations.account;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.TerBankDetails;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.bank.BankDetailsConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.chart.DocumentOrientation;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.utils.pdf.*;
import org.jfree.chart.encoders.ImageEncoder;
import org.jfree.chart.encoders.SunPNGEncoderAdapter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;

/**
 * Операция получения реквизитов банка
 * @author Pankin
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountBankDetailsOperation extends OperationBase
{
	private static final BankDictionaryService bankDictionaryService = new BankDictionaryService();
	private static final DepartmentService     departmentService     = new DepartmentService();

	private static final String TEMPLATE_PROPERTY_KEY_EMAIL = "BankDetails";

	private static final String MAIL_SUBJECT = "Реквизиты перевода на счет %s %s %s";
	private static final String FULL_FIO_MSG = "Для совершения операций со счетом %s необходимо указывать Вашу фамилию, имя, отчество полностью.";
	private static final String RESOURCES_PATH = ConfigFactory.getReaderByFileName("pdf.properties").getProperty("resources.path");
	private static final String CLASS_TYPE_ERROR_MSG = "Для ресурса «%s» нельзя получить банковские реквизиты";

	private static final String BANK_NAME = "bankName";
	private static final String BIC = "BIC";
	private static final String CORR_ACCOUNT = "corrAccount";
	private static final String INN = "INN";
	private static final String KPP = "KPP";
	private static final String OKPO = "OKPO";
	private static final String OGRN = "OGRN";
	private static final String CA_ADDRESS = "caAddress";
	private static final String TB_ADDRESS = "tbAddress";
	private static final String VSP_ADDRESS = "vspAddress";
	private static final String RECEIVER = "RECEIVER";
	private static final String ACCOUNT_NUMBER = "accountNumber";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String DETAIL_TITLE = "Реквизиты перевода на счет %s:";
	private static final String RECEIVER_TITLE = "Получатель: ";
	private static final String ACCOUNT_NUMBER_TITLE = "Номер счета: ";
	private static final String BANK_NAME_TITLE = "Банк получателя: ";
	private static final String BIC_TITLE = "БИК: ";
	private static final String CORR_ACCOUNT_TITLE = "Корреспондентский счет: ";
	private static final String KPP_TITLE = "КПП: ";
	private static final String INN_TITLE = "ИНН: ";
	private static final String OKPO_TITLE = "ОКПО: ";
	private static final String OGRN_TITLE = "ОГРН: ";
	private static final String CA_ADDRESS_TITLE = "Юридический адрес банка: ";
	private static final String TB_ADDRESS_TITLE = "Почтовый адрес банка: ";
	private static final String VSP_ADDRESS_TITLE = "Почтовый адрес доп. офиса: ";

	private static final StyleContext STYLE_CONTEXT = new StyleContext();
	private static final Style TITLE = STYLE_CONTEXT.addStyle("title", null);
	private static final Style TEXT = STYLE_CONTEXT.addStyle("text", null);
	private static final Style TEXT10 = STYLE_CONTEXT.addStyle("text10", null);
	private static final Style BOLD = STYLE_CONTEXT.addStyle("bold", TEXT);
	private static final Style BOLD14 = STYLE_CONTEXT.addStyle("bold14", null);

	private static final List<Class> acceptedClassesList = new ArrayList<Class>();
	static
	{
		acceptedClassesList.add(AccountLink.class);
		acceptedClassesList.add(CardLink.class);
	}

	public static final String IMG_LOGO_TAG = "img";
	public static final String TEXT_BOLD = "bold";
	public static final String TEXT_LEFT = "left";
	public static final String TEXT_RIGHT = "right";
	public static final String TEXT_CENTER = "center";
	public static final String IMG_INSERT_TEXT = "{\\\\pict\\\\pngblip %s}";
	public static final String OFFICE_INFO_TAG_OPEN = "office_info_open";
	public static final String OFFICE_INFO =
			"{\\\\shp\n"+
			"{\\\\*\\\\shpinst\\\\shpleft0\\\\shptop0\\\\shpright2500\\\\shpbottom982\\\\\n"+
			"{\\\\sp{\\\\sn shapeType}{\\\\sv 202}}\n"+
			"{\\\\sp{\\\\sn fLine}{\\\\sv 0}}\n"+
			"{\\\\sp{\\\\sn posh}{\\\\sv 3}}\n"+
			"{\\\\sp{\\\\sn fBehindDocument}{\\\\sv 0}}\n"+
			"{\\\\sp{\\\\sn fHidden}{\\\\sv 0}}\n"+
			"{\\\\shptxt \\\\ltrpar \\\\pard\\\\plain \\\\ltrpar ";
	public static final String OFFICE_INFO_TAG_CLOSE = "office_info_close";

	public static final String BANK_DETAIL_INFO_TAG_OPEN = "bank_detail_info_open";
	public static final String BANK_DETAIL_INFO =
				"{\\\\shp\n"+
				"{\\\\*\\\\shpinst\\\\shpleft%s\\\\shptop%s\\\\shpright%s\\\\shpbottom%s\\\\\n"+
				"{\\\\sp{\\\\sn shapeType}{\\\\sv 202}}\n"+
				"{\\\\sp{\\\\sn fLine}{\\\\sv 0}}\n"+
				"{\\\\sp{\\\\sn posh}{\\\\sv 3}}\n"+
				"{\\\\sp{\\\\sn fBehindDocument}{\\\\sv 0}}\n"+
				"{\\\\sp{\\\\sn fHidden}{\\\\sv 0}}\n"+
				"{\\\\shptxt \\\\ltrpar \\\\pard\\\\plain \\\\ltrpar ";
	public static final String BANK_DETAIL_INFO_TAG_CLOSE = "bank_detail_info_close";

	static
	{
		StyleConstants.setFontSize(TITLE, 16);
		StyleConstants.setBold(TITLE, true);
		StyleConstants.setFontFamily(TITLE, "Arial");

		StyleConstants.setFontSize(TEXT, 12);
		StyleConstants.setFontFamily(TEXT, "Arial");

		StyleConstants.setFontSize(TEXT10, 10);
		StyleConstants.setFontFamily(TEXT10, "Arial");

		StyleConstants.setBold(BOLD, true);

		StyleConstants.setFontSize(BOLD14, 14);
		StyleConstants.setFontFamily(BOLD14, "Arial");
		StyleConstants.setBold(BOLD14, true);
	}

	private AccountLink accountLink;
	private CardLink cardLink;
	//Банк по коду типа участника расчетов в сети Банка России
	private ResidentBank bank;
	//Реквизиты тербанка вклада или счета карты
	private TerBankDetails terBankDetails;
	//Банк резидент
	private ResidentBank residentBank;

	public void initialize(Long id, Class clazz) throws BusinessLogicException, BusinessException
	{
		if (!acceptedClassesList.contains(clazz))
			throw new BusinessLogicException(String.format(CLASS_TYPE_ERROR_MSG, clazz.toString()));

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		String regionCode = null;
		String officeCode = null;

		if (clazz == AccountLink.class)
		{
			accountLink = personData.getAccount(id);
			regionCode  = accountLink.getNumber().substring(9,  11);
			officeCode  = accountLink.getNumber().substring(11, 13);
		}
		else if (clazz == CardLink.class)
		{
			cardLink = personData.getCard(id);
			Account cardAccount = cardLink.getCardAccount();
			if (cardAccount == null)
			{
				throw new BusinessLogicException("Не обнаружен счет карты ID=" + id);
			}
			//ТБ
			regionCode = cardAccount.getNumber().substring(9, 11);
			//ОСБ
			officeCode = cardAccount.getNumber().substring(11, 13);
		}

		bank = bankDictionaryService.findByParticipantCode(ConfigFactory.getConfig(BankDetailsConfig.class).getParticipantCode());
		//Поиск тербанка по данным из номера счета
		terBankDetails = departmentService.findTerBankDetailsByCode(regionCode, officeCode);
		if (terBankDetails != null && StringHelper.isNotEmpty(terBankDetails.getBIC()))
			residentBank = bankDictionaryService.findByBIC(terBankDetails.getBIC());
	}

	public AccountLink getAccount()
	{
		return accountLink;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	/**
	 * Получить реквизиты банка и другие данные
	 * @return карта {поле : значение} с реквизитами банка
	 */
	public Map<String, Object> getDetails() throws BusinessException, BusinessLogicException
	{
		Map<String, Object> details = new HashMap<String, Object>();
		if (bank != null)
		{
			details.put(INN, bank.getINN());
			//Юридический адрес
			details.put(CA_ADDRESS, bank.getAddress());
		}
		//Некоторые реквизиты тербанка
		if (terBankDetails != null)
		{
			details.put(BANK_NAME, terBankDetails.getName());
			details.put(BIC, terBankDetails.getBIC());
			details.put(OKPO, terBankDetails.getOKPO());
			//Почтовый адрес банка
			details.put(TB_ADDRESS, terBankDetails.getAddress());
		}

		if (residentBank != null)
		{
			details.put(KPP, residentBank.getKPP());
			//Корреспондентский счет
			details.put(CORR_ACCOUNT, residentBank.getAccount());
		}
		details.put(OGRN, ConfigFactory.getConfig(BankDetailsConfig.class).getOGRN());

		//Почтовый адрес доп.офиса
		String address = accountLink != null ? accountLink.getOffice().getAddress() : cardLink.getOffice().getAddress();
		details.put(VSP_ADDRESS, address);

		details.put(RECEIVER, accountLink != null ? accountLink.getAccountClient() : cardLink.getCardClient());

		String accountNumber = null;
		if (accountLink != null)
		{
			accountNumber = accountLink.getNumber();
		}
		else if (cardLink != null)
		{
			Account account = cardLink.getCardAccount();
			accountNumber = account != null ? account.getNumber() : null;
		}
		details.put(ACCOUNT_NUMBER, accountNumber);
		details.put(DESCRIPTION, accountLink != null ? accountLink.getDescription() : cardLink.getDescription());
		return details;
	}

	/**
	 * @return Тема письма по умолчанию
	 */
	public String getDefaultMailSubject()
	{
		String description = null;
		String source = null;
		String cutCardNum = null;

		if (accountLink != null)
		{
			source = "вклада";
			cutCardNum = "";
			description = accountLink.getDescription();
		}
		else
		{
			source = "карты";
			description = cardLink.getDescription();
			cutCardNum = MaskUtil.getCutCardNumber(cardLink.getNumber());
		}
		return String.format(MAIL_SUBJECT, source, description, cutCardNum).trim();
	}

	/**
	 * Отправить реквизиты на EMAIL
	 * @param address адрес
	 * @param subject тема
	 * @param text сообщение
	 * @param emailImageUrl url картинки логотипа
	 */
	public void sendMail(String address, String subject, String text, String emailImageUrl) throws BusinessException, BusinessLogicException
	{
		try
		{
			MailHelper.sendEMail(subject, address, TEMPLATE_PROPERTY_KEY_EMAIL, getMailData(text, emailImageUrl));
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private Map<String, Object> getMailData(String text, String emailImageUrl) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> result = new HashMap<String, Object>(getDetails());
		Office office =null;
		String description = null;
		String detailSource = null;
		Client client = null;
		if (accountLink != null)
		{
			description = String.format(DETAIL_TITLE, "вклада");
			detailSource =  accountLink.getDescription();
			client = accountLink.getAccountClient();
			office = accountLink.getOffice();
		}
		else if (cardLink != null)
		{
			description = String.format(DETAIL_TITLE, "карты", cardLink.getDescription());
			detailSource =  cardLink.getName() + " " + MaskUtil.getCutCardNumber(cardLink.getNumber());
			client = cardLink.getCardClient();
			office = cardLink.getOffice();
		}

		//Логотип банка
		result.put("img", emailImageUrl);
		//Офис банка
		if (office != null)
		{
			result.put("officeName", office.getName());
			ExtendedCodeImpl code = new ExtendedCodeImpl(office.getCode());
			result.put("officeCode", code.getBranch() + "/" + code.getOffice());
		}
		result.put("title1", description);
		result.put("title2", detailSource);
		//Получатель
		if (client != null)
		{
			result.put("owner", PersonHelper.getFormattedPersonName(client.getFirstName(), client.getSurName(), client.getPatrName()));
		}
		//Сообщение для клиента
		result.put("additionalInfo", (accountLink != null ? "вклада" : "карты"));
		//Комментарий к письму
		result.put("comment", text);
		return result;
	}

	/**
	 * @return Файл PDF с реквизитами
	 */
	public byte[] getPDF() throws BusinessException, BusinessLogicException
	{
		Map<String, Object> details = getBankInfo();
		try
		{
			PDFBuilder builder = PDFBuilder.getInstance(null, RESOURCES_PATH + "pdf\\" + PDFBuilder.FONT_NAME, 12, DocumentOrientation.VERTICAL);
			//Для логотипа и данных ВСП делаем табл.без границ
			PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(4, RESOURCES_PATH + "pdf\\" + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
			tableBuilder.setTableWidthPercentage(100);

			CellStyle thCellStyleLeft = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);
			thCellStyleLeft.setHorizontalAlignment(Alignment.left);

			String logoImgPath = (String) details.get(IMG_LOGO_TAG);
			if (StringHelper.isNotEmpty(logoImgPath))
			{
				File logoImgFile = FileHelper.createDirectory(logoImgPath);
				BufferedImage bufferedImage = ImageIO.read(logoImgFile);
				ImageIcon icon = new ImageIcon();
				icon.setImage(bufferedImage);
				tableBuilder.addImageToCurrentRow(bufferedImage, icon.getIconWidth(), icon.getIconHeight(), thCellStyleLeft);
			}
			else
			{
				tableBuilder.addEmptyValueToCell(thCellStyleLeft);
			}

			tableBuilder.addEmptyValueToCell(thCellStyleLeft);
			tableBuilder.addEmptyValueToCell(thCellStyleLeft);

			Office office =	accountLink != null ? accountLink.getOffice() : cardLink.getOffice();
			Map<String, String> officeFields = office.getCode().getFields();
			String officeInfo = office.getName()+"\n"+"№"+officeFields.get("branch")+"/"+officeFields.get("office");
			tableBuilder.addValueToCell(officeInfo, PDFTableStyles.TEXT_FONT, thCellStyleLeft);

			tableBuilder.addToPage(TableBreakRule.twoLineMinimumInPage, Alignment.bottom);

			builder.addParagraph("\n");
			builder.addParagraph("\n");
			ParagraphStyle titleStyle = PDFBuilder.TITLE_PARAGRAPH;
			titleStyle.setAlignment(Alignment.left);

			String description = null;
			String detailSource = null;
			if (accountLink != null)
			{
				description = String.format(DETAIL_TITLE, "вклада");
				detailSource = accountLink.getDescription();
			}
			else if (cardLink != null)
			{
				description = String.format(DETAIL_TITLE, "карты");
				detailSource = cardLink.getDescription()+" "+MaskUtil.getCutCardNumber(cardLink.getNumber());
			}

			builder.addParagraph(description, titleStyle, PDFBuilder.TITLE_FONT);
			builder.addParagraph(detailSource, titleStyle, PDFBuilder.TITLE_FONT);
			builder.addEmptyParagraph();
			builder.addEmptyParagraph();

			PDFTableBuilder tableDetailBuilder = builder.getTableBuilderInstance(2, RESOURCES_PATH + "pdf\\" + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
			tableDetailBuilder.setTableWidthPercentage(100);
			tableDetailBuilder.addValueToCell(RECEIVER_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			//Получатель
			Client client = accountLink != null ? accountLink.getAccountClient() : cardLink.getCardClient();
			if (client != null)
				tableDetailBuilder.addValueToCell(PersonHelper.getFormattedPersonName(client.getFirstName(), client.getSurName(), client.getPatrName()),
						PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			else
				tableDetailBuilder.addEmptyValueToCell(thCellStyleLeft);

			//Номер счета
			CellStyle thCellStyleBorderBottom = new CellStyle(PDFTableStyles.CELL_BORDER_BOTTOM);
			tableDetailBuilder.addValueToCell(ACCOUNT_NUMBER_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleBorderBottom);
			tableDetailBuilder.addValueToCell((String) details.get(ACCOUNT_NUMBER), PDFBuilder.TEXT_FONT, thCellStyleBorderBottom);
			//Банк получателя
			tableDetailBuilder.addValueToCell(BANK_NAME_TITLE, PDFTableStyles.BOLD_FONT, thCellStyleLeft);
			tableDetailBuilder.addValueToCell((String) details.get(BANK_NAME), PDFTableStyles.BOLD_FONT, thCellStyleLeft);
			//БИК
			tableDetailBuilder.addValueToCell(BIC_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			tableDetailBuilder.addValueToCell((String) details.get(BIC), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			//Корреспондентский счет
			tableDetailBuilder.addValueToCell(CORR_ACCOUNT_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			tableDetailBuilder.addValueToCell((String) details.get(CORR_ACCOUNT), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			//КПП
			tableDetailBuilder.addValueToCell(KPP_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			tableDetailBuilder.addValueToCell((String) details.get(KPP), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			//ИНН
			tableDetailBuilder.addValueToCell(INN_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			tableDetailBuilder.addValueToCell((String) details.get(INN), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			//ОКПО
			tableDetailBuilder.addValueToCell(OKPO_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			tableDetailBuilder.addValueToCell((String) details.get(OKPO), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			//ОГРН
			tableDetailBuilder.addValueToCell(OGRN_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleBorderBottom);
			tableDetailBuilder.addValueToCell((String) details.get(OGRN), PDFTableStyles.TEXT_FONT, thCellStyleBorderBottom);
			//Юридический адрес банка
			tableDetailBuilder.addValueToCell(CA_ADDRESS_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			tableDetailBuilder.addValueToCell((String) details.get(CA_ADDRESS), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			//Почтовый адрес банка
			if (!PermissionUtil.impliesService("DontShowPostTBAddressInBankDetails"))
			{
				tableDetailBuilder.addValueToCell(TB_ADDRESS_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
				tableDetailBuilder.addValueToCell((String) details.get(TB_ADDRESS), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			}
			//Почтовый адрес филиала
			if (!PermissionUtil.impliesService("DontShowPostVSPAddressInBankDetails"))
			{
				tableDetailBuilder.addValueToCell(VSP_ADDRESS_TITLE, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
				tableDetailBuilder.addValueToCell((String) details.get(VSP_ADDRESS), PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			}

			tableDetailBuilder.addToPage(TableBreakRule.twoLineMinimumInPage, Alignment.bottom);
			builder.addPhrase(String.format(FULL_FIO_MSG, accountLink != null ? "вклада" : "счета карты"), PDFBuilder.TEXT_FONT);

			return builder.build();
		}
		catch (PDFBuilderException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return Файл RTF с реквизитами
	 */
	public byte[] getRTF() throws BusinessException, BusinessLogicException
	{
		Map<String, Object> details = getBankInfo();
		try
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(124);
			RTFEditorKit rtf = new RTFEditorKit();

			Document document = rtf.createDefaultDocument();
			document.getDefaultRootElement();
			String logoIconHexString = null;

			String logoImgPath = (String) details.get(IMG_LOGO_TAG);
			if (StringHelper.isNotEmpty(logoImgPath))
			{
				File logoImgFile = FileHelper.createDirectory(logoImgPath);
				BufferedImage bufferedImage = ImageIO.read(logoImgFile);

				logoIconHexString = getIconToHexString(bufferedImage, FileHelper.getFileExtension(logoImgFile));
				//вставка логотипа сбербанка в документ
				if (StringHelper.isNotEmpty(logoIconHexString))
				{
					//делаем заметку что тут нужно вставить логотип
					document.insertString(document.getLength(), "<"+ IMG_LOGO_TAG +">", null);
					document.insertString(document.getLength(), "\n\n", null);
				}
			}
			Office office =	accountLink != null ? accountLink.getOffice() : cardLink.getOffice();
			Map<String, String> officeFields = office.getCode().getFields();

			document.insertString(document.getLength(),"<"+OFFICE_INFO_TAG_OPEN+">",null);
			document.insertString(document.getLength(),"<left>"+
					office.getName()+"\n"+
					"№"+officeFields.get("branch")+"/"+officeFields.get("office"), TEXT10);
			document.insertString(document.getLength(),"<"+OFFICE_INFO_TAG_CLOSE+">",null);
			document.insertString(document.getLength(), "\n", null);

			String description = null;
			String detailSource = null;
			if (accountLink != null)
			{
				description = String.format(DETAIL_TITLE, "вклада");
				detailSource = accountLink.getDescription();
			}
			else if (cardLink != null)
			{
				description = String.format(DETAIL_TITLE, "карты");
				detailSource = cardLink.getDescription()+" "+MaskUtil.getCutCardNumber(cardLink.getNumber());
			}
			document.insertString(document.getLength(), "<left>"+description, TITLE);
			document.insertString(document.getLength(), "\n", null);
			document.insertString(document.getLength(), detailSource, TITLE);
			document.insertString(document.getLength(), "\n\n\n", null);
			//Получатель
			document.insertString(document.getLength(), "<left>"+RECEIVER_TITLE, TEXT);
			Client client = accountLink != null ? accountLink.getAccountClient() : cardLink.getCardClient();
			if (client != null)
			{
				String personFIO = PersonHelper.getFormattedPersonName(client.getFirstName(), client.getSurName(), client.getPatrName());
				addBankDetailInfoToDocument(document, personFIO, "_FIO", TEXT);
			}
			document.insertString(document.getLength(), "\n", null);
			//Номер счета
			document.insertString(document.getLength(), ACCOUNT_NUMBER_TITLE, TEXT);
			String accountNumber = null;
			if (accountLink != null)
				accountNumber = accountLink.getNumber();
			else if (cardLink != null)
			{
				Account cardAccount = cardLink.getCardAccount();
				accountNumber = cardAccount.getNumber();
			}
			addBankDetailInfoToDocument(document, AccountsUtil.getFormattedAccountNumber(accountNumber), "_ACC_NUM", TEXT);
			document.insertString(document.getLength(), "\n", null);
			document.insertString(document.getLength(), "________________________________________________________________", null);
			document.insertString(document.getLength(), "\n", null);
			document.insertString(document.getLength(), "\n", null);

			//Банк получателя
			document.insertString(document.getLength(), BANK_NAME_TITLE, BOLD);
			addBankDetailInfoToDocument(document, (String) details.get(BANK_NAME), "_BANK_NAME", BOLD);
			document.insertString(document.getLength(), "\n\n", null);
			//БИК
			document.insertString(document.getLength(), BIC_TITLE, TEXT);
			addBankDetailInfoToDocument(document, (String) details.get(BIC), "_BIC", TEXT);
			document.insertString(document.getLength(), "\n", null);
			//Корреспондентский счет
			document.insertString(document.getLength(), CORR_ACCOUNT_TITLE, TEXT);
			addBankDetailInfoToDocument(document, (String) details.get(CORR_ACCOUNT), "_CORR_ACCOUNT", TEXT);
			document.insertString(document.getLength(), "\n", null);
			//КПП
			document.insertString(document.getLength(), KPP_TITLE, TEXT);
			addBankDetailInfoToDocument(document, (String) details.get(KPP), "_KPP", TEXT);
			document.insertString(document.getLength(), "\n", null);
			//ИНН
			document.insertString(document.getLength(), INN_TITLE, TEXT);
			addBankDetailInfoToDocument(document, (String) details.get(INN), "_INN", TEXT);
			document.insertString(document.getLength(), "\n", null);
			//ОКПО
			document.insertString(document.getLength(), OKPO_TITLE, TEXT);
			addBankDetailInfoToDocument(document, (String) details.get(OKPO), "_OKPO", TEXT);
			document.insertString(document.getLength(), "\n", null);
			//ОГРН
			document.insertString(document.getLength(), OGRN_TITLE, TEXT);
			addBankDetailInfoToDocument(document, (String) details.get(OGRN), "_OGRN", TEXT);
			document.insertString(document.getLength(), "\n", null);

			document.insertString(document.getLength(), "________________________________________________________________", null);
			document.insertString(document.getLength(), "\n", null);
			document.insertString(document.getLength(), "\n", null);

			//Юридический адрес банк
			document.insertString(document.getLength(), CA_ADDRESS_TITLE, TEXT);
			addBankDetailInfoToDocument(document, (String) details.get(CA_ADDRESS), "_CA_ADDRESS", TEXT);
			document.insertString(document.getLength(), "\n", null);

			boolean needShowPostTBAddress = !PermissionUtil.impliesService("DontShowPostTBAddressInBankDetails");
			boolean needShowPostVSPAddress = !PermissionUtil.impliesService("DontShowPostVSPAddressInBankDetails");
			//Почтовый адрес банка
			if (needShowPostTBAddress)
			{
				document.insertString(document.getLength(), "\n", null);
				document.insertString(document.getLength(), TB_ADDRESS_TITLE, TEXT);
				addBankDetailInfoToDocument(document, (String) details.get(TB_ADDRESS), "_TB_ADDRESS", TEXT);
				document.insertString(document.getLength(), "\n", null);
			}
			//Почтовый адрес доп.офиса
			if (needShowPostVSPAddress)
			{
				document.insertString(document.getLength(), "\n", null);
				document.insertString(document.getLength(), VSP_ADDRESS_TITLE, TEXT);
				addBankDetailInfoToDocument(document, (String) details.get(VSP_ADDRESS), "_VSP_ADDRESS", TEXT);
				document.insertString(document.getLength(), "\n", null);
			}

			document.insertString(document.getLength(), "\n\n", null);
			document.insertString(document.getLength(), String.format(FULL_FIO_MSG, accountLink != null ? "вклада" : "счета карты"), TEXT);
			document.insertString(document.getLength(), "\n", null);

			rtf.write(outputStream, document, 0, document.getLength());

			//Т.к. "RTFEditorKit" не умеет вставлять изображения, таблицы, выравнивать текст и ещё куча ограничений :-(
			//То обойдем это явно вставив параметры для отрисовки объектов и выравнивания текста.
			String rtfData = outputStream.toString();
			int marginLeft = 4479;
			int marginTop = 2975;
			int marginRight = 9534;
			int marginBottom = 3525;

			rtfData = rtfData.replaceAll("<"+ IMG_LOGO_TAG +">", String.format(IMG_INSERT_TEXT, logoIconHexString));
			rtfData = rtfData.replaceAll("<"+ OFFICE_INFO_TAG_OPEN +">", OFFICE_INFO);
			rtfData = rtfData.replaceAll("<"+ OFFICE_INFO_TAG_CLOSE +">", "}}}");
			rtfData = initRTFDataView(rtfData, "_FIO", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+550;
			marginBottom = marginBottom+550;
			rtfData = initRTFDataView(rtfData, "_ACC_NUM", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+1080;
			marginBottom = marginBottom+1380;
			rtfData = initRTFDataView(rtfData, "_BANK_NAME", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+820;
			marginBottom = marginBottom+520;
			rtfData = initRTFDataView(rtfData, "_BIC", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+550;
			marginBottom = marginBottom+550;
			rtfData = initRTFDataView(rtfData, "_CORR_ACCOUNT", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+550;
			marginBottom = marginBottom+550;
			rtfData = initRTFDataView(rtfData, "_KPP", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+550;
			marginBottom = marginBottom+550;
			rtfData = initRTFDataView(rtfData, "_INN", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+550;
			marginBottom = marginBottom+550;
			rtfData = initRTFDataView(rtfData, "_OKPO", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+550;
			marginBottom = marginBottom+550;
			rtfData = initRTFDataView(rtfData, "_OGRN", marginLeft, marginTop, marginRight, marginBottom);

			marginTop = marginTop+1100;
			marginBottom = marginBottom+1400;
			rtfData = initRTFDataView(rtfData, "_CA_ADDRESS", marginLeft, marginTop, marginRight, marginBottom);

			if (needShowPostTBAddress)
			{
				marginTop = marginTop+850;
				marginBottom = marginBottom+850;
				rtfData = initRTFDataView(rtfData, "_TB_ADDRESS", marginLeft, marginTop, marginRight, marginBottom);
			}

			if (needShowPostVSPAddress)
			{
				marginTop = marginTop+850;
				marginBottom = marginBottom+850;
				rtfData = initRTFDataView(rtfData, "_VSP_ADDRESS", marginLeft, marginTop, marginRight, marginBottom);
			}
			rtfData = rtfData.replaceAll("<"+TEXT_BOLD+">", "\\\\b1");
			rtfData = rtfData.replaceAll("<"+TEXT_CENTER+">", "\\\\qc");
			rtfData = rtfData.replaceAll("<"+TEXT_RIGHT+">", "\\\\qr");
			rtfData = rtfData.replaceAll("<"+TEXT_LEFT+">", "\\\\ql");

			return rtfData.getBytes();
		}
		catch (BadLocationException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private void addBankDetailInfoToDocument(Document document, String dataValue, String prefix, Style style) throws BadLocationException
	{
		if (document != null)
		{
			document.insertString(document.getLength(), "<"+BANK_DETAIL_INFO_TAG_OPEN + prefix + ">", null);
			document.insertString(document.getLength(), StringHelper.getEmptyIfNull(dataValue), style);
			document.insertString(document.getLength(), "<"+BANK_DETAIL_INFO_TAG_CLOSE + prefix + ">", null);
		}
	}

	private String initRTFDataView(String rtfData, String prefix, int marginLeft, int marginTop, int marginRight, int marginBottom)
	{
		String rtfDataView = rtfData;
		if (marginLeft > 0 || marginTop > 0 || marginRight > 0 || marginBottom > 0)
		{
			rtfDataView = rtfData.replaceAll("<"+ BANK_DETAIL_INFO_TAG_OPEN +prefix+">",
				String.format(BANK_DETAIL_INFO, marginLeft, marginTop, marginRight, marginBottom));
			rtfDataView = rtfDataView.replaceAll("<"+ BANK_DETAIL_INFO_TAG_CLOSE +prefix+">", "}}}");
		}
		return rtfDataView;
	}

	private Map<String, Object> getBankInfo() throws BusinessException, BusinessLogicException
	{
		//Реквизиты банка
		Map<String, Object> bankInfoMap = getDetails();
		//Логотип банка
		bankInfoMap.put(IMG_LOGO_TAG, RESOURCES_PATH+"images\\logoSBRFNew.png");
		return bankInfoMap;
	}

	public String getIconToHexString(BufferedImage bImage, String imgType) throws IOException
	{
		ImageIcon icon = new ImageIcon();
		icon.setImage(bImage);
        if (StringHelper.isEmpty(imgType) || icon == null)
	        return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImage, imgType, baos);
        baos.flush();
        int imageBuferByteSize = baos.toByteArray().length;

        ByteArrayOutputStream os = new ByteArrayOutputStream(imageBuferByteSize);
        ImageEncoder pe = new SunPNGEncoderAdapter();
        bImage.getGraphics().drawImage(icon.getImage(), 0, 0, null);
        pe.encode(bImage, os);
        byte[] ba = os.toByteArray();

        int len = ba.length;

        int i;
        StringBuffer sb = new StringBuffer(len * 2);
        for (i = 0; i < len; i++) {
            String sByte = Integer.toHexString(ba[i] & 0xFF);
            if (sByte.length() < 2) {
                sb.append('0' + sByte);
            } else {
                sb.append(sByte);
            }
        }
		return sb.toString();
    }
}
