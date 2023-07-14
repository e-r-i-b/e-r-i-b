<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:html>
    <head><title>������������ �������������� ���� � ���</title></head>
    <body>

    <html:form action="/private/loanclaim/crm/test" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:importAttribute/>
        <c:if test="${not empty form.infoMessage}">
          <font size="4" color="red">
              <c:out  value="${form.infoMessage}"/>
          <font size="4" color="red">
        </c:if>

        <table>
            <tr>
                <td>
                    ����������  ����� ������ �� ������� ������� (�����/������������� ������ � ����) [1]
                </td>
                <td>
                    <html:text property="number" maxlength="50" size="50" value="1234567890"/>
                </td>
            </tr>
            <tr>
                <td>
                    �������� ������, �� �� ����� �������� ������ (1=Web, 2=�� ����������, 3=�� �������, 4=���, 5=����-����, 6=����-��, 7=����-��, 8=����-��, 9=����-��������) [1]
                </td>
                <td>
                    <html:select property="channelType">
                        <html:option value="1">Web</html:option>
                        <html:option value="2">�� ����������</html:option>
                        <html:option value="3">�� �������</html:option>
                        <html:option value="4">���</html:option>
                        <html:option value="5">����-����</html:option>
                        <html:option value="6">����-��</html:option>
                        <html:option value="7">����-��</html:option>
                        <html:option value="8">����-��</html:option>
                        <html:option value="9">����-��������</html:option>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td>
                    ����� ����������, ����������� ������ � ���� (Login ���������� � �����) [0-1]
                </td>
                <td>
                    <html:text property="employerLogin" maxlength="50" size="50" value="1234567890"/>
                </td>
            </tr>
            <tr>
                <td>
                    ��� ����������, ����������� ������ ���� [0-1]
                </td>
                <td>
                    <html:text property="employerFIO" maxlength="255" size="50" value="����������� ��������� �������������"/>
                </td>
            </tr>
            <tr>
                <td>
                    ��� ������� [1]
                </td>
                <td>
                    <html:text property="firstName" maxlength="160" size="50" value="������"/>
                </td>
            </tr>
            <tr>
                <td>
                    ������� ������� [1]
                </td>
                <td>
                    <html:text property="lastName" maxlength="160" size="50" value="��������"/>
                </td>
            </tr>
            <tr>
                <td>
                    �������� ������� [0-1]
                </td>
                <td>
                    <html:text property="middleName" maxlength="160" size="50" value=""/>
                </td>
            </tr>
            </tr>
            <tr>
                <td>
                    ���� ��������(dd.mm.YYYY)[1]
                </td>
                <td>
                    <html:text  property="birthDay" maxlength="10" size="10" value="21.10.1983"/>
                </td>
            </tr>
            <tr>
                <td>
                    ����� �������� ��: ����� + ����� ����� ������ [1]
                </td>
                <td>
                    <html:text property="passportNumber" maxlength="32" size="32" value="1234 123456"/>
                </td>
            </tr>
            <tr>
                <td>
                    ����� ����� � Way (������������� ����� � ��������� �������). ��� ������,  ���� ������ ����������� � ������ ��ѻ [0-1]
                </td>
                <td>
                    <html:text property="wayCardNumber" maxlength="30" size="30" value="123456"/>
                </td>
            </tr>
            <tr>
                <td>
                    ������������� ��������� ��������.  ����������� � ������ ������� ������� � �������� [0-1]
                </td>
                <td>
                    <html:text property="campaingMemberId" maxlength="50" size="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    ��������� ������� [1]
                </td>
                <td>
                    <html:text property="mobilePhone" maxlength="12"  size="12" value="79510734345"/>
                </td>
            </tr>
            <tr>
                <td>
                    ������� ������� [0-1]
                </td>
                <td>
                    <html:text property="workPhone" maxlength="12" size="12" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    ��������������  ������� [0-1]
                </td>
                <td>
                    <html:text property="addPhone" maxlength="12" size="12" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    ������������ �������� �� ����������� ��������� ��������� ����  [1]
                </td>
                <td>
                    <html:text property="productName" maxlength="250" size="50" value="������ �� ������� �����"/>
                </td>
            </tr>
            <tr>
                <td>
                    ��� ���� �������� � TSM [0-1]
                </td>
                <td>
                    <html:text property="targetProductType" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    ��� �������� � TSM [0-1]
                </td>
                <td>
                    <html:text property="targetProduct" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td>--%>
                    <%--��� �������� ������, ����� ������� ������� � ������ ("Consumer Credit") [1]--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<html:text property="productType" maxlength="250" size="50" value="������ �� ������� �����"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>
                    ��� ����������� � TSM [0-1]
                </td>
                <td>
                    <html:text property="targetProductSub" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                   ������ ������� [1]
                </td>
                <td>
                    <html:select property="currency">
                        <html:option value="RUB"/>
                        <html:option value="USD"/>
                        <html:option value="EUR"/>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td>
                    ����� ������� [1]
                </td>
                <td>
                    <html:text property="amount" maxlength="50" size="50" value="500000"/>
                </td>
            </tr>
            <tr>
                <td>
                    ���� ������������.  ���-�� �������. ����� ����� � ��������� 1-360. [1]
                </td>
                <td>
                    <html:text property="duration" maxlength="3" size="3" value="36"/>
                </td>
            </tr>
            <tr>
                <td>
                    ���������� ������, %.  ����� � ��������� �� ���� ������ ����� ������� � ��������� �� 0 �� 100. [1]
                </td>
                <td>
                    <html:text property="interestRate" maxlength="5" size="3" value="10.5"/>
                </td>
            </tr>
            <tr>
                <td>
                    ����������� [0-1]
                </td>
                <td>
                    <html:text property="comments" maxlength="250" size="50" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    �� [1]
                </td>
                <td>
                    <html:text property="tb" maxlength="2" size="2" value="77"/>
                </td>
            </tr>
            <tr>
                <td>
                   ��� [1]
                </td>
                <td>
                    <html:text property="osb" maxlength="4" size="4" value="1573"/>
                </td>
            </tr>
            <tr>
                <td>
                    ��� [1]
                </td>
                <td>
                    <html:text property="vsp" maxlength="5" size="5" value="15730"/>
                </td>
            </tr>
            <tr>
                <td>
                    ����������� ���� ������ � ��� (dd.mm.YYYY)[0-1]
                </td>
                <td>
                    <html:text  property="plannedVisitDate" maxlength="10" size="10" value="21.10.2014"/>
                </td>
            </tr>
            <tr>
                <td>
                    ����������� ����� ������ � ��� [0-1]
                </td>
                <td>
                    <html:select property="plannedVisitTime">
                        <html:option value="1">� 09:00 �� 11:00</html:option>
                        <html:option value="2">� 11:00 �� 13:00</html:option>
                        <html:option value="3">� 13:00 �� 15:00</html:option>
                        <html:option value="4">� 15:00 �� 17:00</html:option>
                        <html:option value="5">� 17:00 �� 19:00</html:option>
                        <html:option value="6">� 19:00 �� 20:00</html:option>
                    </html:select>
                </td>
            </tr>
        </table>
        <html:submit property="operation" value="Send"/>
    </html:form>
</body>
</html:html>
