<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>����������� (�������������): ������� ����� ������ �������/�� ����� �������� ����</title>
    </head>

    <body>
    <h1>�����������: ������� ����� ������ �������/�� ����� �������� ����</h1>

    <html:form action="/atm/quicklyCreateP2PAutoTransfer" show="true">
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>

            <tiles:put name="data">
                <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <tr><td colspan="2">������� id ���������, ���� �������� �� ���������, ��������������� �������� ������������,</td></tr>
                    <tr><td colspan="2"> ����� �������, ��� ������� ����� ����������� � ������� ����.</td></tr>
                    <tr>
                        <td>id ���������</td>
                        <td><html:text styleId="id" property="id" onchange="changeOperation()"/></td>
                    </tr>
                </table>
            </tiles:put>

            <tiles:put name="operation" value="makeAutoTransfer"/>
            <tiles:put name="formName"  value="CreateP2PAutoTransferClaim"/>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
