<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 23.01.14
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:html>
<head>
    <title>���������� ��������� � ���������� �������</title>
</head>
<body>
<H1>������: ���������� ���������</H1>
<html:form action="/limits/add" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <h2>������ �������:</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr>
            <td>�������</td>
            <td><html:text property="surName" size="40"/></td>
        </tr>
        <tr>
            <td>���</td>
            <td><html:text property="firstName" size="40"/></td>
        </tr>
        <tr>
            <td>��������</td>
            <td><html:text property="patrName" size="40"/></td>
        </tr>
        <tr>
            <td>���� �������� (YYYY-MM-DDTHH:mm:SS.fff)</td>
            <td><html:text property="birthDate" size="40"/></td>
        </tr>
        <tr>
            <td>����� ���������</td>
            <td><html:text property="docSeries" size="40"/></td>
        </tr>
        <tr>
            <td>����� ���������</td>
            <td><html:text property="docNumber" size="40"/></td>
        </tr>
        <tr>
            <td>�������</td>
            <td><html:text property="tb" size="40"/></td>
        </tr>
    </table>

    <h2>����������:</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr>
            <td>������� ������������� ������</td>
            <td><html:text property="externalId" size="40"/></td>
        </tr>
        <tr>
            <td>������� ������������� �������</td>
            <td><html:text property="documentExternalId" size="40"/></td>
        </tr>
        <tr>
            <td>�������� �����</td>
            <td><html:text property="amountValue" size="40"/></td>
        </tr>
        <tr>
            <td>������</td>
            <td><html:text property="currency" size="40"/></td>
        </tr>
        <tr>
            <td>���� ���������� (YYYY-MM-DDTHH:mm:SS.fff)</td>
            <td><html:text property="operationDate" size="40"/></td>
        </tr>
        <tr>
            <td>��� ������ ������������� ���������</td>
            <td>
                <html:select property="channelType">
                    <html:option value="MOBILE_API">��������� ����������</html:option>
                    <html:option value="INTERNET_CLIENT">�������� ������</html:option>
                    <html:option value="VSP">���</html:option>
                    <html:option value="CALL_CENTR">��</html:option>
                    <html:option value="SELF_SERVICE_DEVICE">���������� ����������������</html:option>
                    <html:option value="ERMB_SMS">���-����� ���������� �����</html:option>
                </html:select>
            </td>
        </tr>
    </table>

    <h2>������</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
           <tr>
               <td>��� ������</td>
               <td>
                   <select id="limitType">
                       <option value="GROUP_RISK">����� �� ������� �����</option>
                       <option value="OBSTRUCTION_FOR_AMOUNT_OPERATION">�������������� ����� �� ����� ��������</option>
                       <option value="OBSTRUCTION_FOR_AMOUNT_OPERATIONS">�������������� ����� �� ����� ��������</option>
                       <option value="IMSI">IMSI �����</option>
                       <option value="EXTERNAL_PHONE">����� �� ���������� ��� ������ ������ ��������</option>
                       <option value="EXTERNAL_CARD">����� �� ���������� ��� ��������� �� ����� �����</option>
                   </select>
               </td>
           </tr>
           <tr>
               <td>��� �����������</td>
               <td>
                   <select id="restrictionType">
                       <option value="DESCENDING">�� �������� ������</option>
                       <option value="AMOUNT_IN_DAY">�� ����� �������� � �����</option>
                       <option value="CARD_ALL_AMOUNT_IN_DAY">�� ����� �������� � ����� ���� ����������� ������� �� �����</option>
                       <option value="PHONE_ALL_AMOUNT_IN_DAY">�� ����� �������� � ����� ���� ����������� ������� �� ��������</option>
                       <option value="MIN_AMOUNT">�� ����������� ����� ��������</option>
                       <option value="OPERATION_COUNT_IN_DAY">�� ���������� �������� � �����</option>
                       <option value="OPERATION_COUNT_IN_HOUR">�� ���������� �������� � ���</option>
                       <option value="IMSI">����� SIM-�����</option>
                       <option value="MAX_AMOUNT_BY_TEMPLATE">��������� ����� ������ �� �������</option>
                   </select>
               </td>
           </tr>
           <tr>
               <td>������� ������������� ������ �����</td>
               <td><input type="text" id="groupRiskId" width="30"></td>
           </tr>
           <tr>
               <td colspan="2">
                   <button type="button" onclick="add()">Add</button>
               </td>
           </tr>
           <tr>
               <td colspan="2">
                   <html:text property="limits" styleId="limits" size="200"/>
               </td>
           </tr>
       </table>

    <html:textarea property="error" rows="10" cols="50"/>

    <script type="text/javascript">
        function add()
        {
            var limitType = document.getElementById('limitType');
            var restrictionType = document.getElementById('restrictionType');
            var groupRiskId = document.getElementById('groupRiskId');
            var limits = document.getElementById('limits');

            var result = limits.value;

            result += limitType.value + "," + restrictionType.value + "," + groupRiskId.value + ";";

            limitType.value = "GROUP_RISK";
            restrictionType.value = "DESCENDING";
            groupRiskId.value = "";
            limits.value = result;
            return true;
        }
    </script>

    <html:submit property="operation" value="send"/>
</html:form>
</body>
</html:html>