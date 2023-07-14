<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%--
    ������ ��� ��������� ����������� ��: ������������� �����������
--%>

<tiles:insert definition="main">
    <tiles:put name="pageTitle" type="string" value="����������� ������ ��������� ����"/>
    <tiles:put name="headerGroup" value="true"/>
    <tiles:put name="data" type="string">

        <br/>

        <html:form action="/private/register-mobilebank/confirm">
            <%@ include file="confirm_data.jsp" %>
        </html:form>

    </tiles:put>
</tiles:insert>
