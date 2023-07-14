package com.rssl.phizic.operations.loans.offert;

import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.common.CodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.etsm.offer.OfferAgrimentHelper;
import com.rssl.phizic.business.etsm.offer.OfferConfirmed;
import com.rssl.phizic.business.etsm.offer.OfferEribPrior;
import com.rssl.phizic.business.etsm.offer.service.OfferEribService;
import com.rssl.phizic.business.etsm.offer.service.OfferPriorWebService;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.bank.BankDetailsConfig;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.chart.DocumentOrientation;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.utils.pdf.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author EgorovaA
 * @ created 18.07.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferDetailsOperation extends OperationBase
{
	private static final OfferEribService offerEribService = new OfferEribService();
	private static final SimpleService simpleService = new SimpleService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final OfferPriorWebService offerPriorWebService = new OfferPriorWebService();

	private static final String TEMPLATE_PROPERTY_KEY_EMAIL = "OfferDetails";

	public static final String IMG_LOGO_TAG = "images\\logoSBRFNew.png";
	private static final String RESOURCES_PATH = ConfigFactory.getReaderByFileName("pdf.properties").getProperty("resources.path");
    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy в HH:mm";
	//Информация по офису
	private Department claimDrawDepartment;
	//Текст оферты
	private String offerText;

    private String formatedConfirmOfferDate;
    private String claimETSMId;

	public void initialize (String appNum, Long offerId) throws BusinessException, BusinessLogicException
	{
		// Если offerId, значит, оферта уже подтверждена
		if (offerId == null)
			initializeConfirmed(appNum);
		else
			initializePrior(appNum, offerId);
	}

	/**
	 * Инициализация операции подтвержденной офертой
	 * @param appNum - номер заявки на получение КД
	 * @throws BusinessException
	 */
	private void initializeConfirmed(String appNum) throws BusinessException
	{
		OfferConfirmed offerConfirmed = offerEribService.getOfferConfirmed(appNum);
		if(offerConfirmed == null)
			throw new BusinessException("Не найдена подтвержденная оферта с applicationNumber = " + appNum);
        formatedConfirmOfferDate = new SimpleDateFormat(DATE_TIME_FORMAT).format(offerConfirmed.getOfferDate().getTime());
		ExtendedLoanClaim cliam = simpleService.findById(ExtendedLoanClaim.class, offerConfirmed.getClaimId());
		if (cliam == null)
			throw new BusinessException("Не удалось найти кредитную заявку с id:" + offerConfirmed.getClaimId());

		claimDrawDepartment = cliam.getClaimDrawDepartment();
        claimETSMId = cliam.getOperationUID();
		offerText = OfferAgrimentHelper.getConfirmedOfferAgreementText(offerConfirmed);
	}

	/**
	 * Инициализация операции неподтвержденной офертой.
	 * Сначала ищем оферту в ЕРИБ. Если не найдена - пробуем найти ее в центразизованной БД
	 * @param appNum - номер заявки на получение КД
	 * @param offerId - id записи оферты в БД
	 * @throws BusinessException
	 */
	private void initializePrior(String appNum, Long offerId) throws BusinessException, BusinessLogicException
	{
		OfferEribPrior offerEribPrior = offerEribService.getOneOfferEribPrior(appNum, offerId);

		if (offerEribPrior == null)
			initializeOfficePrior(appNum, offerId);

		ExtendedLoanClaim cliam = simpleService.findById(ExtendedLoanClaim.class, offerEribPrior.getClaimId());
		if (cliam == null)
			throw new BusinessException("Не удалось найти кредитную заявку с id:" + offerEribPrior.getClaimId());

		claimDrawDepartment = cliam.getClaimDrawDepartment();

		offerText = OfferAgrimentHelper.getEribOfferAgreementText(offerEribPrior, cliam);
	}

	/**
	 * Инициализация операции неподтвержденной офертой (для заявок, созданных в каналах, отличных от УКО)
	 * @param appNum - номер заявки на получение КД
	 * @param offerId - id записи оферты в БД
	 * @throws BusinessException
	 */
	private void initializeOfficePrior(String appNum, Long offerId) throws BusinessException, BusinessLogicException
	{
		OfferOfficePrior offerOfficePrior = null;
		List<OfferOfficePrior> offerOfficePriorList = offerPriorWebService.getOfferOfficePrior(appNum);
		for (OfferOfficePrior officePrior : offerOfficePriorList)
		{
			if (offerId.equals(officePrior.getId()))
				offerOfficePrior = officePrior;
		}
		if(offerOfficePrior == null)
			throw new BusinessException("Не найдена оферта с applicationNumber = " + appNum);

		//тер. банк (2 цифры), ОСБ (4 цифры), ВСП (5 цифр)
		String departmentStr = offerOfficePrior.getDepartment();
		if (StringHelper.isEmpty(departmentStr))
			throw new BusinessException("В оферте не указан департамент (applicationNumber:" + appNum + ")");

		String tb = departmentStr.substring(0, 2);
		String osb = departmentStr.substring(2, 6);
		String vsp = departmentStr.substring(6, 11);
		Code code = new ExtendedCodeImpl(StringHelper.removeLeadingZeros(tb), StringHelper.removeLeadingZeros(osb), StringHelper.removeLeadingZeros(vsp));
		claimDrawDepartment = departmentService.findByCode(code);
		if (claimDrawDepartment == null)
			throw new BusinessException(String.format("Не найдено подразделение по данным из оферты: tb=%s,osb=%s,vsp=%s", tb, osb, vsp));

		offerText = OfferAgrimentHelper.getOfficeOfferAgreementText(offerOfficePrior);
	}

	/**
	 * Получение офиса из строки (для заявок, созданных в каналах, отличных от УКО)
	 * @return офис
	 */
	private Department getDepartment(String departmentCode) throws BusinessException
	{
		if (StringHelper.isEmpty(departmentCode))
			return null;

		Code code  = new CodeImpl();
		Map<String, String> fields = code.getFields();
		fields.put("region",departmentCode.substring(0, 2));
		fields.put("branch", departmentCode.substring(2, 6));
		fields.put("office", departmentCode.substring(6));
		return departmentService.findByCode(code);
	}

	public String getOfferText()
	{
		return offerText;
	}

	public Department getClaimDrawDepartment()
	{
		return claimDrawDepartment;
	}

	public byte[] getPDF() throws BusinessException, BusinessLogicException
	{
		try
		{
			PDFBuilder builder = PDFBuilder.getInstance(null, RESOURCES_PATH + "pdf\\" + PDFBuilder.FONT_NAME, 12, DocumentOrientation.VERTICAL);
			//Для логотипа и данных ВСП делаем табл.без границ
			PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(4, RESOURCES_PATH + "pdf\\" + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
			tableBuilder.setTableWidthPercentage(100);

			CellStyle thCellStyleLeft = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);
			thCellStyleLeft.setHorizontalAlignment(Alignment.left);

			String logoImgPath = RESOURCES_PATH + IMG_LOGO_TAG;
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

			if (claimDrawDepartment != null)
			{
				Map<String, String> officeFields = claimDrawDepartment.getCode().getFields();
				String officeInfo = claimDrawDepartment.getName()+"\n"+"№"+officeFields.get("branch")+"/"+officeFields.get("office");
				tableBuilder.addValueToCell(officeInfo, PDFTableStyles.TEXT_FONT, thCellStyleLeft);
			}
			else
			{
				tableBuilder.addEmptyValueToCell(thCellStyleLeft);
			}
			tableBuilder.addToPage(TableBreakRule.twoLineMinimumInPage, Alignment.bottom);

			builder.addParagraph("\n");
			builder.addParagraph("\n");
			ParagraphStyle titleStyle = PDFBuilder.TITLE_PARAGRAPH;
			titleStyle.setAlignment(Alignment.left);

			String description = "Индивидуальные условия кредита";
			String detailSource = null;

			builder.addParagraph(description, titleStyle, PDFBuilder.TITLE_FONT);
			builder.addParagraph(detailSource, titleStyle, PDFBuilder.TITLE_FONT);
			builder.addEmptyParagraph();
			builder.addEmptyParagraph();

			builder.addTextString(offerText);
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
	 * Отправить реквизиты на EMAIL
	 * @param address адрес
	 * @param subject тема
	 * @param comment сообщение
	 */
	public void sendMail(String address, String subject, String comment) throws BusinessException, BusinessLogicException
	{
		try
		{
			MailHelper.sendEMail(subject, address, TEMPLATE_PROPERTY_KEY_EMAIL, getMailData(comment));
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить текст письма
	 * @param comment - комментарий
	 * @return текст письма
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private Map<String, Object> getMailData(String comment) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> result = new HashMap<String, Object>();

		//Логотип банка
		String emailImageUrl = ConfigFactory.getConfig(BankDetailsConfig.class).getSendToEmailImageUrl();
		result.put("img", emailImageUrl);

		String description = "Индивидуальные условия кредита";
		result.put("title1", description);
		result.put("offerText", offerText);
		//Комментарий к письму
		result.put("comment", comment);
		return result;
	}

    public String getFormatedConfirmOfferDate()
    {
        return formatedConfirmOfferDate;
    }

    public String getClaimETSMId()
    {
        return claimETSMId;
    }
}
