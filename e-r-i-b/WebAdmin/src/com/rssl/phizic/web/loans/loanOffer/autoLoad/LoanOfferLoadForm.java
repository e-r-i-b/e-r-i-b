package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.upload.FormFile;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 15:22:37
 * ����� �������� �������������� ��������� �����������
 */
public class LoanOfferLoadForm extends ActionFormBase
{
   //����� ������ ����������
   private List<String> commonErrors = new ArrayList<String>();

   //������ �� �������������
   private List<String> personErrors = new ArrayList<String>();

    //���� � ����� � �������������
   private FormFile file;

    //����� ������ ������
   private int totalCount;

   //����� ����������� �������
    private int loadCount;

	// ���� � �����
	private String fileName;

	public List<String> getCommonErrors() {
        return commonErrors;
    }

    public void setCommonErrors(List<String> commonErrors) {
        this.commonErrors = commonErrors;
    }

    public List<String> getPersonErrors() {
        return personErrors;
    }

    public void setPersonErrors(List<String> personErrors) {
        this.personErrors = personErrors;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(int loadCount) {
        this.loadCount = loadCount;
    }

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}