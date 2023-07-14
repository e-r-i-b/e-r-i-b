<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>����� ��������� � �������� �����</title>
</head>

<body>
<h1>����� ��������� � �������� �����</h1>

<html:form action="/mobileApi/contact/find" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/contacts/search.do"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr>
                    <td>�� ����� (������������)</td>
                    <td><html:text property="byName" value="����"/></td>
                </tr>
                <tr>
                    <td>�� ������ (����������� ���)</td>
                    <td><html:text property="byAlias" value=""/></td>
                </tr>
                <tr>
                    <td>�� �������� (����� ��������)</td>
                    <td><html:text property="byPhone" value=""/></td>
                </tr>
                <tr>
                    <td>�� ��������� ����� (�������� ���)</td>
                    <td><html:text property="bySmallAlias" value=""/></td>
                </tr>
                <tr>
                    <td>�������� ����������</td>
                    <td><html:checkbox property="useLike"/></td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
