<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<table>
    <tr>
        <td>
            �������(*)
        </td>
        <td>
            <html:text property="lastName" maxlength="40" size="40" value="msh"/>
        </td>
    </tr>
    <tr>
        <td>
            ���(*)
        </td>
        <td>
            <html:text property="firstName" maxlength="40" size="40" value="alex"/>
        </td>
    </tr>
    <tr>
        <td>
            ��������
            ���� �� �����������
            ��� �������� ��� ��������.

        </td>
        <td>
            <html:text property="middleName" maxlength="40" size="40" value="msh"/>
        </td>
    </tr>
    <tr>
        <td>
            ���� ��������(dd.mm.YYYY)(*)
        </td>
        <td>
            <html:text  property="birthday" maxlength="10" size="10" value="21.10.2013"/>
        </td>
    </tr>
    <tr>
        <td>
            ���(*)
        </td>
    </tr>
    <tr>
        <td>
            ���� ����������, �������������� ��������(*)
        </td>
        <td>
            <html:text property="idType" maxlength="4" size="4" value="21"/>
        </td>
    </tr>
    <tr>
        <td>
            ����� ���������
        </td>
        <td>
            <html:text property="idSeries" maxlength="12" size="12" value="1234"/>
        </td>
    </tr>
    <tr>
        <td>
            ����� ���������
            ��� �������� way ����� ��������� ���������� � ���� ���� ��� �������� (*)
        </td>
        <td>
            <html:text property="idNum" maxlength="12" size="12" value="123456"/>
        </td>
    </tr>
    <tr>
        <td>
            ��� �����
        </td>
        <td>
            <html:text property="issuedBy" maxlength="80" size="80"/>
        </td>
    </tr>
    <tr>
        <td>
            ���� ������(dd.mm.YYYY)
        </td>
        <td>
            <html:text property="issueDt" maxlength="10" size="10"/>
        </td>
    </tr>
    <tr>
        <td>
            ����� �������� (��)(*)
        </td>
        <td>
            <html:text property="regionId" maxlength="3" size="3" value="40"/>
        </td>
    </tr>
</table>