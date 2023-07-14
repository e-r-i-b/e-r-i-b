package com.rssl.phizic.business.template;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentTest;
import com.rssl.phizic.auth.*;

import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 22.01.2007 Time: 13:08:16 To change this template use File
 * | Settings | File Templates.
 */
public class AAATemplatesTest extends BusinessTestCaseBase
{
	protected DocTemplate getTemp(Long id) throws BusinessException
	{
		DocTemplateService templateService = new DocTemplateService();

	    return templateService.getById(id);
	}

	public void testTemplateContract() throws Exception
    {
// Создание департамента
			 Department department = DepartmentTest.getTestDepartment();
// создание файла шаблона
			InputStream formDefinition = null;
			DocTemplate docTemplate = new DocTemplate();
			DocTemplate docTemplate2 = new DocTemplate();
	        DocTemplateService templateService = new DocTemplateService();

			byte[] buffer;
			ByteArrayOutputStream out= null;
			int len=0;
			try
			{
			  formDefinition = new FileInputStream("WebResources/web/agreement.doc");
	 	      buffer = new byte[formDefinition.available()];
			  out = new ByteArrayOutputStream(buffer.length);

			  while((len = formDefinition.read(buffer)) >= 0)
			     out.write(buffer, 0, len);

			  docTemplate.setData(out.toByteArray());
			  docTemplate.setName("# test #");
			  docTemplate.setDepartmentId(department.getId());
		      templateService.createDocTemplate(docTemplate);
			}
			catch (DublicateDocTemplateNameException ex)
			{
				throw new BusinessException("Шаблон с таким именем уже существует в текущем отделении");
			}
			catch (Exception ex)
			{
			  throw new BusinessException(ex);
			}
			finally
			{
			     formDefinition.close();
			     out.close();
			}

			  docTemplate2 = getTemp (docTemplate.getId());

			  if (Arrays.equals( docTemplate.getData(), docTemplate2.getData()))
			  {
// создание пакета документов
				try
				{
				  PackageService packageService = new PackageService();
				  List<DocTemplate> s = new ArrayList<DocTemplate>();
				  s.add(docTemplate);

				  PackageTemplate packageTemp = new PackageTemplate();
				  packageTemp.setDepartmentId(department.getId());
				  packageTemp.setDescription("описание");
				  packageTemp.setName("# наименовани #");
				  packageTemp.setTemplates(s);

	              PackageTemplate packageTemplate = packageService.createPackage(packageTemp);
				  BankLogin testBankLogin = CheckBankLoginTest.getTestLogin();
				  
				  ClientsPackageTemplate clientsPackageTemplate = new ClientsPackageTemplate();
			      clientsPackageTemplate.setLogin(testBankLogin);
			      clientsPackageTemplate.setPackageTemplate(packageTemplate);

			      ClientsPackageTemplatesService clientsPackageService = new ClientsPackageTemplatesService();

				   clientsPackageService.AddClientsPackage(clientsPackageTemplate);
				   List list = clientsPackageService.getClientsPackageById(testBankLogin);
				   clientsPackageService.RemoveClientsPackage(clientsPackageTemplate);
				   packageService.remove(packageTemplate);
				}
			    catch (DublicateClientsPackageException ex)
				{
					  templateService.remove(docTemplate);

					  throw new BusinessException("Пакет уже подключен к клиенту");
				}
				catch (DublicatePackageNameException ex)
				{
					templateService.remove(docTemplate);

					throw new BusinessException("Пакет уже существует с таким наименованием");
				}
				catch (Exception ex)
				{
					templateService.remove(docTemplate);

					throw new BusinessException(ex.getMessage());
				}
			  }

			  try
			  {
			      templateService.remove(docTemplate);
			  }
              catch (Exception ex)
			  {
				throw new BusinessException(ex.getMessage());
			  }
	}
}


