package com.rssl.phizic.web.loans.loanOffer.load;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.io.FileReader;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

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
}
