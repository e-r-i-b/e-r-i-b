package com.rssl.phizic.logging.monitoring;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.reports.BusinessReportConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.DateHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Сервис для построения отчетов.
 *
 * @author bogdanov
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringReportService
{
	public static final int DEFAULT_REPORT_BUILD_PERIOD = 61;
	public static final int PROVIDER_COUNT = 50;

	private static final String EMPTY_DATA_ERROR = "Недостаточно данных для предоставления отчета";

	private String csaAdminDBInstancePrefix;

	/**
	 * ctor
	 * @param csaAdminDBInstancePrefix - префикс БД CSAAdmin
	 */
	public MonitoringReportService(String csaAdminDBInstancePrefix)
	{
		this.csaAdminDBInstancePrefix = csaAdminDBInstancePrefix;
	}

	/**
	 * @param fromDate - начальная дата.
	 * @param toDate - конечная дата.
	 * @param blockNumber   - номер блока
	 * @param channel   - канал создания платежа
	 * @param d количество дней, за которые есть агрегированные данные
	 * @param n количество дней, за которые строится отчет
	 * @return Отчет по платежам в СБОЛ.
	 * @throws SystemException
	 */
	public List<ServicePaymentReportEntry> getReportForServicePayment(Calendar fromDate, Calendar toDate, int blockNumber, String channel, double d, long n)  throws SystemException
	{

		Map<String, String> serviceProviderList = findServiceProvider();
		List<Object[]> listEntries = findServicePayment(fromDate, toDate, blockNumber, channel, serviceProviderList.keySet());
		List<Object[]> listNormEntries = findServicePayment(DateHelper.addDays(toDate, -DEFAULT_REPORT_BUILD_PERIOD), toDate, blockNumber, channel, serviceProviderList.keySet());
		List<ServicePaymentReportEntry> servicePaymentReportEntries = new ArrayList<ServicePaymentReportEntry>();
		ServicePaymentReportEntry servicePaymentReportEntry = new ServicePaymentReportEntry();
		int i=0;
		Long count;
		String currPoviderUUID  = "";
		for (Object[] normEntry : listNormEntries)
		{
			String providerUUID = (String)normEntry[0];
			MonitoringDocumentType documentType = MonitoringDocumentType.valueOf(normEntry[1].toString());
			Long normCount = Long.valueOf(normEntry[2].toString());
			if (listEntries.isEmpty() || !normEntry[1].equals(listEntries.get(i)[1]))
			{
				count = 0L;
			}
			else
			{
				count = Long.valueOf(listEntries.get(i)[2].toString());
				i++;
			}
			if (!providerUUID.equals(currPoviderUUID))
			{
				servicePaymentReportEntries.add(servicePaymentReportEntry);
				servicePaymentReportEntry = new ServicePaymentReportEntry();
				servicePaymentReportEntry.setServiceProviderName(serviceProviderList.get(providerUUID));
				currPoviderUUID = providerUUID;
			}
			if (documentType == MonitoringDocumentType.SPAP)
			{
				servicePaymentReportEntry.setCreatedAutoPaymentsCount(count);
				servicePaymentReportEntry.setCreatedAutoPaymentsCountNorma((normCount / d) * n);
			}
			if (documentType == MonitoringDocumentType.SPP)
			{
				servicePaymentReportEntry.setExecutedPaymentsCount(count);
				servicePaymentReportEntry.setExecutedPaymentsCountNorma((normCount/d)*n);
			}
			if (documentType == MonitoringDocumentType.SPPBR)
			{
				servicePaymentReportEntry.setBasketExecutedPaymentsCount(count);
				servicePaymentReportEntry.setBasketExecutedPaymentsCountNorma((normCount/d)*n);
			}
			if (documentType == MonitoringDocumentType.SPPBT)
			{
				servicePaymentReportEntry.setTemplateExecutedPaymentsCount(count);
				servicePaymentReportEntry.setTemplateExecutedPaymentsCountNorma((normCount/d)*n);
			}
			if (documentType == MonitoringDocumentType.SPT)
			{
				servicePaymentReportEntry.setCreatedTemplatesCount(count);
				servicePaymentReportEntry.setCreatedTemplatesCountNorma((normCount/d)*n);
			}
		}
		servicePaymentReportEntries.add(servicePaymentReportEntry);
		servicePaymentReportEntries.remove(0);
		return servicePaymentReportEntries;
	}

	private List<Object[]> findServicePayment(final Calendar fromDate,final Calendar toDate, final int blockNumber, final String channel, final Collection<String> providers) throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<List<Object[]> >()
			{
				public List<Object[]>  run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.logging.monitoring.findServicePayment");
					query.setParameter("toDate", toDate);
					query.setParameter("fromDate", fromDate);
					query.setParameter("blockNumber", blockNumber);
					query.setParameter("channel", channel);
					query.setParameterList("providers", providers);
					return  query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw  new SystemException(ex);
		}
	}

	private Map<String, String> findServiceProvider() throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(csaAdminDBInstancePrefix).execute(new HibernateAction<Map<String, String> >()
			{
				public Map<String, String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase.findProviderSortByPriority");
					query.setMaxResults(PROVIDER_COUNT);
					List<Object[]> list = query.list();
					Map<String, String> providerMap = new HashMap<String, String>();
					for (Object[] entry : list) {
						providerMap.put((String)entry[0], (String)entry[1]);
					}
					return providerMap;
				}
			});

		}
		catch (Exception ex)
		{
			throw  new SystemException(ex);
		}
	}



	/**
	 * @param fromDate - начальная дата.
	 * @param toDate - конечная дата.
	 * @param blockNumber   - номер блока
	 * @param channel   - канал создания платежа
	 * @param d количество дней, за которые есть агрегированные данные
	 * @param n количество дней, за которые строится отчет
	 * @return Отчет по переводам в СБОЛ.
	 * @throws SystemException
	 */
	public List<TransferPaymentReportEntry> getReportForTransferPayment(Calendar fromDate,Calendar toDate, int blockNumber, String channel, double d, long n) throws SystemException
	{
		List<Object[]> listEntries = findTransferPayment(fromDate, toDate, blockNumber, channel);
		List<Object[]> listNormEntries = findTransferPayment(DateHelper.addDays(toDate, -DEFAULT_REPORT_BUILD_PERIOD), toDate, blockNumber, channel);

		List<TransferPaymentReportEntry> transferPaymentReportEntries  = new ArrayList<TransferPaymentReportEntry>();
		TransferPaymentReportEntry transferPaymentReportEntry = new TransferPaymentReportEntry();
		Map<String, Long> executedTransferForTbCount= new HashMap<String, Long>();
		Map<String, BigDecimal> totalAmountForTb = new HashMap<String, BigDecimal>();

		Map<String, Double> executedTransferForTbCountNorma=  new HashMap<String, Double>();
		Map<String, BigDecimal> totalAmountForTbNorma = new HashMap<String, BigDecimal>();
		Long executedTransferCount  = 0L;
		Long normExecutedTransferCount  = 0L;
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal normTotalAmount = new BigDecimal(0);
		MonitoringDocumentType currDocumentType = null;
		Long count;
		BigDecimal amount;
		int i=0;
		for (Object[] normEntry : listNormEntries)
		{
			MonitoringDocumentType documentType = MonitoringDocumentType.valueOf(normEntry[1].toString());
			String tb = normEntry[0].toString();

			if (listEntries.size() <= i || !listEntries.get(i)[0].equals(normEntry[0]) || !listEntries.get(i)[1].equals(normEntry[1]))
			{
				count  = 0L;
				amount = new BigDecimal(0);
			}
			else
			{
				count = Long.valueOf(listEntries.get(i)[2].toString());
				amount = toBigDecimal(listEntries.get(i)[3]);
				i++;
			}

			Long normCount = Long.valueOf(normEntry[2].toString());
			BigDecimal normAmount = toBigDecimal(normEntry[3]);


			if (documentType != currDocumentType)
			{
				transferPaymentReportEntries.add(transferPaymentReportEntry);

				transferPaymentReportEntry = new TransferPaymentReportEntry();

				currDocumentType = documentType;
				executedTransferCount = 0L;
				normExecutedTransferCount  = 0L;
				totalAmount =  new BigDecimal(0);
				normTotalAmount = new BigDecimal(0);

				executedTransferForTbCount = new HashMap<String, Long>();
				totalAmountForTb = new HashMap<String, BigDecimal>();
				executedTransferForTbCountNorma = new HashMap<String, Double>();
				totalAmountForTbNorma = new HashMap<String, BigDecimal>();
			}

			executedTransferForTbCount.put(tb, count);

			executedTransferForTbCountNorma.put(tb, (normCount/d)*n);

			totalAmountForTb.put(tb, amount);
			totalAmountForTbNorma.put(tb, (normAmount.divide(BigDecimal.valueOf(d), 2)).multiply(BigDecimal.valueOf(n)));

			executedTransferCount =  executedTransferCount + count;
			normExecutedTransferCount = normExecutedTransferCount + normCount;

			totalAmount = totalAmount.add(amount);
			normTotalAmount = normTotalAmount.add(normAmount);

			transferPaymentReportEntry.setDocumentType(documentType);
			transferPaymentReportEntry.setTotalAmount(totalAmount);
			transferPaymentReportEntry.setExecutedTransferCount(executedTransferCount);
			transferPaymentReportEntry.setExecutedTransferForTbCount(executedTransferForTbCount);
			transferPaymentReportEntry.setTotalAmount(totalAmount);
			transferPaymentReportEntry.setTotalAmountForTb(totalAmountForTb);

			transferPaymentReportEntry.setExecutedTransferCountNorma((normExecutedTransferCount/d)*n);
			transferPaymentReportEntry.setExecutedTransferForTbCountNorma(executedTransferForTbCountNorma);
			transferPaymentReportEntry.setTotalAmountForTbNorma(totalAmountForTbNorma);
			transferPaymentReportEntry.setTotalAmountNorma((normTotalAmount.divide(BigDecimal.valueOf(d), 2)).multiply(BigDecimal.valueOf(n)));

		}
		transferPaymentReportEntries.add(transferPaymentReportEntry);
		transferPaymentReportEntries.remove(0); // удаляем первую пустую строчку
		return transferPaymentReportEntries;

	}

	private BigDecimal toBigDecimal(Object val)
	{
		return new BigDecimal(val == null ? "0" : val.toString());
	}

	private List<Object[]> findTransferPayment(final Calendar fromDate,final Calendar toDate, final int blockNumber, final String channel) throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<List<Object[]> >()
			{
				public List<Object[]>  run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.logging.monitoring.getReportForTransferPayment");
					query.setParameter("toDate", toDate);
					query.setParameter("fromDate", fromDate);
					query.setParameter("blockNumber", blockNumber);
					query.setParameter("channel", channel);
					return  query.list();
				}
			});

		}
		catch (Exception ex)
		{
			throw  new SystemException(ex);
		}
	}

	/**
	 * @param fromDate - начальная дата.
	 * @param toDate- конечная дата.
	 * @param blockNumber   - номер блока
	 * @param channel   - канал создания платежа
	 * @param d количество дней, за которые есть агрегированные данные
	 * @param n количество дней, за которые строится отчет
	 * @return  Отчет по вкладам в СБОЛ.
	 * @throws SystemException
	 */
	public List<AccountOpeningClaimReportEntry> getReportForAccountOpening(Calendar fromDate, Calendar toDate, int blockNumber, String channel, double d, long n)  throws SystemException
	{
		List<Object[]> listEntries = findAccountOpeningClaim(fromDate, toDate, blockNumber, channel);
		List<Object[]> listNormEntries = findAccountOpeningClaim(DateHelper.addDays(toDate, -DEFAULT_REPORT_BUILD_PERIOD), toDate, blockNumber, channel);
		List<AccountOpeningClaimReportEntry> openingClaimReportEntries = new ArrayList<AccountOpeningClaimReportEntry>();
		AccountOpeningClaimReportEntry accountOpeningClaimReportEntry = new AccountOpeningClaimReportEntry();

		Long openedClaimsCount = 0L;
		Map<String, Long> openedClaimsCountTB = new HashMap<String, Long>();
		Long openedClaimsCountNorma = 0L;
		Map<String, Double> openedClaimsCountTBNorma = new HashMap<String, Double>();
		Long openedClaimsCountALF = 0L;
		Map<String, Long> openedClaimsCountTBALF = new HashMap<String, Long>();
		Long openedClaimsCountALFNorma = 0L;
		Map<String, Double> openedClaimsCountTBALFNorma = new HashMap<String, Double>();

		Long curCount = 0L;
		String currAccType = "";
		int i=0;
		for (Object[] normEntry : listNormEntries)
		{
			String normAccType = normEntry[0].toString();
			String normTB = normEntry[1].toString();
			Long normCount = Long.valueOf(normEntry[3].toString());
			MonitoringDocumentType documentType = MonitoringDocumentType.valueOf(normEntry[2].toString());

			if (listEntries.size() <= i || !normEntry[0].equals(listEntries.get(i)[0]) || !normEntry[1].equals(listEntries.get(i)[1]) || !normEntry[2].equals(listEntries.get(i)[2]))
			{
				curCount = 0L;
			}
			else
			{
				curCount = Long.valueOf(listEntries.get(i)[3].toString());
				i++;
			}

			if (!currAccType.equals(normAccType))
			{
				openingClaimReportEntries.add(accountOpeningClaimReportEntry);
				accountOpeningClaimReportEntry = new AccountOpeningClaimReportEntry();
				currAccType = normAccType;

				openedClaimsCount = 0L;
				openedClaimsCountALF = 0L;

				openedClaimsCountTB = new HashMap<String, Long>();
				openedClaimsCountTBNorma = new HashMap<String, Double>();
				openedClaimsCountTBALF = new HashMap<String, Long>();
				openedClaimsCountTBALFNorma = new HashMap<String, Double>();
			}
			if (documentType == MonitoringDocumentType.AOC)
			{
				openedClaimsCount = openedClaimsCount + curCount;
				openedClaimsCountNorma = openedClaimsCountNorma + normCount;
				openedClaimsCountTB.put(normTB, curCount);
				openedClaimsCountTBNorma.put(normTB, (normCount/d)*n);
			}
			else
			{
				openedClaimsCountALF = openedClaimsCountALF + curCount;
				openedClaimsCountALFNorma = openedClaimsCountALFNorma + normCount;
				openedClaimsCountTBALF.put(normTB, curCount);
				openedClaimsCountTBALFNorma.put(normTB, (normCount/d)*n);
			}


			accountOpeningClaimReportEntry.setName(normAccType);
			accountOpeningClaimReportEntry.setOpenedClaimsCount(openedClaimsCount);
			accountOpeningClaimReportEntry.setOpenedClaimsCountTB(openedClaimsCountTB);

			accountOpeningClaimReportEntry.setOpenedClaimsCountALF(openedClaimsCountALF);
			accountOpeningClaimReportEntry.setOpenedClaimsCountTBALF(openedClaimsCountTBALF);

			accountOpeningClaimReportEntry.setOpenedClaimsCountALFNorma((openedClaimsCountALFNorma/d)*n);
			accountOpeningClaimReportEntry.setOpenedClaimsCountNorma((openedClaimsCountNorma/d)*n);

			accountOpeningClaimReportEntry.setOpenedClaimsCountTBALFNorma(openedClaimsCountTBALFNorma);
			accountOpeningClaimReportEntry.setOpenedClaimsCountTBNorma(openedClaimsCountTBNorma);
		}

		openingClaimReportEntries.add(accountOpeningClaimReportEntry);
		openingClaimReportEntries.remove(0);
		return openingClaimReportEntries;
	}


	private List<Object[]> findAccountOpeningClaim(final Calendar fromDate,final Calendar toDate, final int blockNumber, final String channel) throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<List<Object[]> >()
			{
				public List<Object[]>  run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.logging.monitoring.getAccountOpeningClaim");
					query.setParameter("toDate", toDate);
					query.setParameter("fromDate", fromDate);
					query.setParameter("blockNumber", blockNumber);
					query.setParameter("channel", channel);
					return  query.list();
				}
			});

		}
		catch (Exception ex)
		{
			throw  new SystemException(ex);
		}
	}

	private Integer getMetricCount(final Calendar toDate) throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.logging.monitoring.getMetricCount");
					query.setParameter("toDate", toDate);
					return  Integer.decode(query.uniqueResult().toString());
				}
			});

		}
		catch (Exception ex)
		{
			throw new SystemException(ex);
		}
	}

	/**
	 * Получение списка дат, за которые не выполнено агрегирование данных.
	 * @param fromDate начальная дата
	 * @param toDate конечная дата
	 * @return список дат (в виде списка строк формата dd.mm), за которые не выполнено агрегирование данных
	 * @throws SystemException
	 */
	public List<String> getMissingDate(final Calendar fromDate, final Calendar toDate) throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.logging.monitoring.getMissingDate");
					query.setParameter("fromDate", fromDate);
					query.setParameter("toDate", toDate);
					query.setParameter("period", DateHelper.daysDiff(fromDate, toDate) + 1);
					return query.list();
				}
			});

		}
		catch (Exception ex)
		{
			throw new SystemException(ex);
		}
	}

	/**
	 * @param params мапа параметров
	 * @return построеный отчет по бизнес операциям.
	 */
	public byte[] buildReport(Map<String, Object> params) throws IOException, SystemException, LogicException
	{
		Calendar from = (Calendar)params.get(BusinessReportConfig.FROM_PERIOD);
		Calendar to = (Calendar)params.get(BusinessReportConfig.TO_PERIOD);
		double d  = getMetricCount(to);

		if (d == 0)
			throw new LogicException(EMPTY_DATA_ERROR);

		Workbook wb = new HSSFWorkbook();

		long n  = DateHelper.diff(to, from)/DateHelper.MILLISECONDS_IN_DAY;
		String channel = (String)params.get(BusinessReportConfig.REPORTS_CHANNEL_KEY);
		int blockNumber = (Integer)params.get(BusinessReportConfig.REPORTS_BLOCK_KEY);
		int devi = Math.min((Integer)params.get(BusinessReportConfig.REPORTS_DEVIATION_ALL_KEY), (Integer)params.get(BusinessReportConfig.REPORTS_DEVIATION_OPEN_ACCOUNT_KEY));
		if ((Boolean)params.get(BusinessReportConfig.REPORTS_PAYMENT_ON_KEY))
			buildServicePaymentReport(wb, getReportForServicePayment(from, to, blockNumber, channel, d, n), devi);
		if ((Boolean)params.get(BusinessReportConfig.REPORTS_TRANSFER_ON_KEY))
			buildTransferPaymentReport(wb, getReportForTransferPayment(from, to, blockNumber, channel, d, n), devi);
		if ((Boolean)params.get(BusinessReportConfig.REPORTS_OPEN_ACCOUNT_ON_KEY))
			buildAccountOpeningClaimReport(wb, getReportForAccountOpening(from, to, blockNumber, channel, d, n), devi);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		out.close();

		return out.toByteArray();
	}

	private Sheet createSheet(Workbook wb, String name)
	{
		return wb.createSheet(WorkbookUtil.createSafeSheetName(name));
	}

	private void setBorder(CellStyle cellStyle, short color)
	{
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(color);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(color);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(color);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(color);
	}

	private void setFont(Sheet s, CellStyle cellStyle, short size, short bold, String name)
	{
		Font font = s.getWorkbook().createFont();
		font.setFontHeightInPoints(size);
		font.setFontName(name);
		font.setBoldweight(bold);
		cellStyle.setFont(font);
	}

	private void buildTableHeader(Sheet s, String... columnsName)
	{
		Row r = s.createRow(0);

		CellStyle style = s.getWorkbook().createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		setBorder(style, IndexedColors.BLACK.getIndex());
		setFont(s, style, (short) 10, Font.BOLDWEIGHT_BOLD, "Arial");
		style.setWrapText(true);

		int num = 0;
		for (String name : columnsName)
		{
			Cell cell = r.createCell(num++);
			s.setColumnWidth(num - 1, 400 * s.getDefaultColumnWidth());
			cell.setCellValue(name);
			cell.setCellStyle(style);
		}
	}

	private void buildTableRow(Sheet s, int rowNum, Set<Integer> redCell, String... columnsName)
	{
		Row r = s.createRow(rowNum);

		CellStyle firstColumnStyle = s.getWorkbook().createCellStyle();
		setBorder(firstColumnStyle, IndexedColors.BLACK.getIndex());
		setFont(s, firstColumnStyle, (short) 10, Font.BOLDWEIGHT_NORMAL, "Arial");
		firstColumnStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		CellStyle style = s.getWorkbook().createCellStyle();
		setBorder(style, IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		setFont(s, style, (short) 10, Font.BOLDWEIGHT_NORMAL, "Arial");
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		CellStyle redStyle = s.getWorkbook().createCellStyle();
		setBorder(redStyle, IndexedColors.BLACK.getIndex());
		redStyle.setAlignment(CellStyle.ALIGN_CENTER);
		setFont(s, redStyle, (short) 10, Font.BOLDWEIGHT_BOLD, "Arial");
		redStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		redStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		int num = 0;
		for (String name : columnsName)
		{
			Cell cell = r.createCell(num);
			cell.setCellValue(name);
			cell.setCellStyle(num == 0 ? firstColumnStyle : (redCell.contains(num) ? redStyle : style));
			num++;
		}
	}

	private void buildAccountOpeningClaimReport(Workbook wb, List<AccountOpeningClaimReportEntry> reportForAccountOpening, int devi)
	{
		Sheet s = createSheet(wb, "Отчет по вкладам в СБОЛ");

		Set<String> tbSet = new HashSet<String>();
		for (AccountOpeningClaimReportEntry entry : reportForAccountOpening)
		{
			tbSet.addAll(entry.getOpenedClaimsCountTB().keySet());
		}
		List<String> tbs = new LinkedList<String>(tbSet);
		Collections.sort(tbs);

		List<String> titles = new LinkedList<String>();
		titles.add("Операция");
		titles.add("Количество открытых вкладов за сутки.");
		titles.add("Количество открытых вкладов за сутки под цель АЛФ.");

		for (String tb : tbs)
		{
			titles.add("Количество открытых вкладов за сутки в ТБ" + tb + ".");
			titles.add("Количество открытых вкладов за сутки под цель АЛФ в ТБ" + tb + ".");
		}

		titles.add("НОРМА. Количество открытых вкладов за сутки.");
		titles.add("НОРМА. Количество открытых вкладов за сутки под цель АЛФ.");

		for (String tb : tbs)
		{
			titles.add("НОРМА. Количество открытых вкладов за сутки в ТБ" + tb + ".");
			titles.add("НОРМА. Количество открытых вкладов за сутки под цель АЛФ в ТБ" + tb + ".");
		}

		buildTableHeader(s, titles.toArray(new String[titles.size()]));

		int rowNum = 1;
		for (AccountOpeningClaimReportEntry entry : reportForAccountOpening)
		{
			List<String> cols = new LinkedList<String>();
			HashSet<Integer> redCells = new HashSet<Integer>();
			cols.add("Открытие вклада "+entry.getName());

			addCol(cols, redCells, devi, entry.getOpenedClaimsCount(), entry.getOpenedClaimsCountNorma());
			addCol(cols, redCells, devi, entry.getOpenedClaimsCountALF(), entry.getOpenedClaimsCountALFNorma());

			for (String tb : tbs)
			{
				addCol(cols, redCells, devi, entry.getOpenedClaimsCountTB().get(tb), entry.getOpenedClaimsCountTBNorma().get(tb));
				addCol(cols, redCells, devi, entry.getOpenedClaimsCountTBALF().get(tb), entry.getOpenedClaimsCountTBALFNorma().get(tb));
			}

			addColNorm(cols, entry.getOpenedClaimsCountNorma());
			addColNorm(cols, entry.getOpenedClaimsCountALFNorma());
			for (String tb : tbs)
			{
				addColNorm(cols, entry.getOpenedClaimsCountTBNorma().get(tb));
				addColNorm(cols, entry.getOpenedClaimsCountTBALFNorma().get(tb));
			}

			buildTableRow(s, rowNum++, redCells, cols.toArray(new String[cols.size()]));
		}
	}

	private void buildTransferPaymentReport(Workbook wb, List<TransferPaymentReportEntry> reportForTransferPayment, int devi)
	{
		Sheet s = createSheet(wb, "Отчет по переводам в СБОЛ");

		Set<String> tbSet = new HashSet<String>();
		for (TransferPaymentReportEntry entry : reportForTransferPayment)
		{
			tbSet.addAll(entry.getExecutedTransferForTbCount().keySet());
		}
		List<String> tbs = new LinkedList<String>(tbSet);
		Collections.sort(tbs);

		List<String> titles = new LinkedList<String>();
		titles.add("Операция");
		titles.add("Общее кол. успешно выполненных переводов.");
		titles.add("Общая сумма успешно выполненных переводов.");

		for (String tb : tbs)
		{
			titles.add("Кол. успешно выполненных переводов в ТБ" + tb + ".");
			titles.add("Сумма успешно выполненных рублевыхпереводов в ТБ" + tb + ".");
		}

		titles.add("НОРМА. Общее кол. успешно выполненных переводов.");
		titles.add("НОРМА. Сумма успешно выполненных рублевых переводов.");

		for (String tb : tbs)
		{
			titles.add("НОРМА. Кол. успешно выполненных переводов в ТБ" + tb + ".");
			titles.add("НОРМА. Сумма Успешно выполненных рублевых переводов в ТБ" + tb + ".");
		}

		buildTableHeader(s, titles.toArray(new String[titles.size()]));

		int rowNum = 1;
		for (TransferPaymentReportEntry entry : reportForTransferPayment)
		{
			List<String> cols = new LinkedList<String>();
			HashSet<Integer> redCells = new HashSet<Integer>();
			cols.add(entry.getName());

			addCol(cols, redCells, devi, entry.getExecutedTransferCount(), entry.getExecutedTransferCountNorma());
			addCol(cols, redCells, devi, entry.getTotalAmount(), entry.getTotalAmountNorma());

			for (String tb : tbs)
			{
				addCol(cols, redCells, devi, entry.getExecutedTransferForTbCount().get(tb), entry.getExecutedTransferForTbCountNorma().get(tb));
				addCol(cols, redCells, devi, entry.getTotalAmountForTb().get(tb),  entry.getTotalAmountForTbNorma().get(tb));
			}

			addColNorm(cols, entry.getExecutedTransferCountNorma());
			addColNorm(cols, entry.getTotalAmountNorma());
			for (String tb : tbs)
			{
				addColNorm(cols, entry.getExecutedTransferForTbCountNorma().get(tb));
				addColNorm(cols, entry.getTotalAmountForTbNorma().get(tb));
			}

			buildTableRow(s, rowNum++, redCells, cols.toArray(new String[cols.size()]));
		}
	}

	private void buildServicePaymentReport(Workbook wb, List<ServicePaymentReportEntry> reportForServicePayment, int devi)
	{
		Sheet s = createSheet(wb, "Отчет по платежам в СБОЛ");
		buildTableHeader(s,
				"Поставщики услуг",
				"Кол. успешно выполненных платежей за сутки",
				"Кол. успешно созданных автоплатежей за сутки",
				"Кол. успешно выполненных платежей по шаблонам за сутки",
				"Кол. созданых шаблонов за сутки",
				"Кол. успешно выполненных платежей через  корзину за сутки.",
				"НОРМА. Кол. успешно выполненных платежей за сутки",
				"НОРМА. Кол. успешно созданных автоплатежей за сутки",
				"НОРМА. Кол. успешно выполненных платежей по шаблонам за сутки",
				"НОРМА. Кол. созданых шаблонов за сутки",
				"НОРМА. Кол. успешно выполненных платежей через  корзину за сутки."
		);

		int rowNum = 1;
		for (ServicePaymentReportEntry entry : reportForServicePayment)
		{
			List<String> cols = new LinkedList<String>();
			HashSet<Integer> redCells = new HashSet<Integer>();
			cols.add(entry.getName());

			addCol(cols, redCells, devi, entry.getExecutedPaymentsCount(), entry.getExecutedPaymentsCountNorma());
			addCol(cols, redCells, devi, entry.getCreatedAutoPaymentsCount(), entry.getCreatedAutoPaymentsCountNorma());
			addCol(cols, redCells, devi, entry.getTemplateExecutedPaymentsCount(), entry.getTemplateExecutedPaymentsCountNorma());
			addCol(cols, redCells, devi, entry.getCreatedTemplatesCount(), entry.getCreatedTemplatesCountNorma());
			addCol(cols, redCells, devi, entry.getBasketExecutedPaymentsCount(), entry.getBasketExecutedPaymentsCountNorma());
			addColNorm(cols, entry.getExecutedPaymentsCountNorma());
			addColNorm(cols, entry.getCreatedAutoPaymentsCountNorma());
			addColNorm(cols, entry.getTemplateExecutedPaymentsCountNorma());
			addColNorm(cols, entry.getCreatedTemplatesCountNorma());
			addColNorm(cols, entry.getBasketExecutedPaymentsCountNorma());

			buildTableRow(s, rowNum++, redCells, cols.toArray(new String[cols.size()]));
		}
	}

	private void addColNorm(List<String> cols, Double norm)
	{
		norm = (norm == null ? 0 : norm);
		cols.add(new DecimalFormat("#0.00").format(norm).replace(',', '.'));
	}

	private void addColNorm(List<String> cols, BigDecimal norm)
	{
		norm = (norm == null ? new BigDecimal(0) : norm);
		cols.add(norm.setScale(2).toString());
	}

	private void addCol(List<String> cols, Set<Integer> redCells, int devi, Long value, Double norm)
	{
		value = (value == null ? 0L : value);
		norm = (norm == null ? 0 : norm);
		if (!isNorm(devi, value, norm))
		{
			redCells.add(cols.size());
		}
		cols.add(new BigDecimal(value).setScale(2).toString());
	}

	private void addCol(List<String> cols, Set<Integer> redCells, int devi, BigDecimal value, BigDecimal norm)
	{
		value = (value == null ? new BigDecimal(0) : value);
		norm = (norm == null ? new BigDecimal(0) : norm);
		if (!isNorm(devi, value.doubleValue(), norm.doubleValue()))
		{
			redCells.add(cols.size());
		}
		cols.add(value.setScale(2).toString());
	}

	private boolean isNorm(int devi, double value, double norm)
	{
		return norm * (1 - devi / 100f) <= value && value <= norm * (1 + devi / 100f);
	}
}
