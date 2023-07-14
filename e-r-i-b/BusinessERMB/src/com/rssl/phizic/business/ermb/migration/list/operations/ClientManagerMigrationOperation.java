package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbInstructionMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.business.ermb.migration.onthefly.fpp.DepartmentIdentity;
import com.rssl.phizic.business.ermb.migration.onthefly.fpp.FPPMigrationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;

/**
 * @author Gulov
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция выгрузки отчета для клиентских менеджеров
 */
public class ClientManagerMigrationOperation extends MigrationReportOperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static DepartmentService departmentService = new DepartmentService();
	/**
	 * Выбранные идентификаторы подразделений
	 */
	private String[] selectedDepartments;

	@Override
	String getFileTemplate()
	{
		return "KM_%s_%s.xls";
	}

	@Override
	Counter getCounter()
	{
		return Counters.ERMB_MIGRATION_CM_REPORT_NUMBER;
	}

	@Override
	String[] getHeader()
	{
		return new String[]{
				"ФИО, ДУЛ, ДР",
				"Список телефонов из МБК и МБВ",
				"Подразделение",
				"Инструкция"
		};
	}

	List findData() throws Exception
	{
		if (ArrayUtils.isEmpty(selectedDepartments))
			throw new IllegalArgumentException("Список подразделений пуст");
		List<String> departmentCodeList = new ArrayList<String>(selectedDepartments.length);
		for (String departmentId : selectedDepartments)
		{
			Department department = departmentService.findById(Long.valueOf(departmentId));
			if (department == null)
			{
				log.error("Не найдено подразделение");
				continue;
			}
			Map<String, String> fields = department.getCode().getFields();
			String tb = StringHelper.getEmptyIfNull(fields.get("region"));
			String osb = StringHelper.getEmptyIfNull(fields.get("branch"));
			String vsp = StringHelper.getEmptyIfNull(fields.get("office"));
			departmentCodeList.add(tb + '|' + osb + '|' + vsp);
		}
		return service.findClientsBySegments(getSegments(), departmentCodeList);
	}

	@Override
	protected void writeLog(Calendar startTime) throws Exception
	{
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader("АС Мигратор. Операция выгрузка отчетов для КМ.");
		SimpleLogParametersReader parametersReader = new SimpleLogParametersReader("Отчет сформирован по сегментам: ", StringUtils.join(getSegments(), ","));
		reader.addParametersReader(parametersReader);
		logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
	}

	@Override
	void makeContent(List data, HSSFSheet sheet, int firstRow, int firstCell) throws BusinessException
	{
		//noinspection unchecked
		List<Client> clients = (List<Client>) data;
		ErmbInstructionMigrationConfig config = ConfigFactory.getConfig(ErmbInstructionMigrationConfig.class);
		Map<Segment, String> instructions = config.getInstructions();
		int i = firstCell;
		for (Client client : clients)
		{
			Row row = sheet.createRow(firstRow++);
			row.createCell(i++).setCellValue(getClientInfo(client));
			row.createCell(i++).setCellValue(getPhones(client.getPhones()));
			row.createCell(i++).setCellValue(getDepartmentName(client));
			String instruction = getInstruction(client, instructions);
			row.createCell(i++).setCellValue(instruction);
			i = firstCell;
		}
	}

	private String getDepartmentName(Client client) throws BusinessException
	{
		String tb = client.getTb();
		String osb = client.getOsb();
		String vsp = client.getVsp();
		Code code = new ExtendedCodeImpl(tb, osb, vsp);

		Department department = departmentService.findByCode(code);
		if (department == null)
			return String.format("%s/%s/%s", tb, osb, vsp);
		else
			return department.getFullName();
	}

	@Override
	String getSheetTitle()
	{
		return "Отчёт для клиентских менеджеров";
	}

	private String getClientInfo(Client client)
	{
		String[] result = new String[] {
				client.getLastName() + " " + client.getFirstName() + " " + client.getMiddleName(),
				client.getDocument(),
				DateHelper.formatDateToStringWithPoint(client.getBirthday())
		};
		return StringUtils.join(result, ", ");
	}

	private String getInstruction(Client client, Map<Segment, String> instructions)
	{
		List<String> resultSet = new LinkedList<String>();
		for (Segment segment : client.getSegments())
		{
			resultSet.add(instructions.get(segment));
		}
		return StringUtils.join(resultSet, ", ");
	}

	private String getPhones(Set<Phone> phones)
	{
		if (phones.size() == 0)
			return "";
		Set<String> numbers = new HashSet<String>();
		for (Phone phone : phones)
			numbers.add(phone.getPhoneNumber());
		return StringUtils.join(numbers, ", ");
	}

	/**
	 * @return подразделения пилотной зоны (по умолчанию для выгрузки отчетов)
	 */
	public List<Department> getPilotZoneDepartments() throws BusinessException
	{
		FPPMigrationConfig config = ConfigFactory.getConfig(FPPMigrationConfig.class);

		List<Department> result = new ArrayList<Department>();
		for (DepartmentIdentity departmentIdentity : config.getDepartments())
		{
			String tb = departmentIdentity.getTb();
			String osb = departmentIdentity.getOsb();
			String vsp = departmentIdentity.getVsp();
			Code code = new ExtendedCodeImpl(tb, osb, vsp);

			Department department = departmentService.findByCode(code);
			if (department == null)
			{
				String errorMessage = String.format("Не найдено подразделение пилотной зоны по данным из конфига: tb=%s,osb=%s,vsp=%s", tb, osb, vsp);
				log.error(errorMessage);
				continue;
			}

			result.add(department);
		}

		return result;
	}

	public void setSelectedDepartments(String[] selectedDepartments)
	{
		this.selectedDepartments = selectedDepartments;
	}
}
