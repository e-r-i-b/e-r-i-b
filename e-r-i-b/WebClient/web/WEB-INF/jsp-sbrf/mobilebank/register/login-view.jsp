<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    ������ ��� ��������� ����������� ��: ������������� �����������
--%>
<tiles:insert definition="login">
    <tiles:put name="pageTitle" type="string" value="����������� ������ ��������� ����"/>
    <tiles:put name="headerGroup" value="true"/>
    <tiles:put name="data" type="string">

        <br/>
        <c:set var="fromSystem" value="false" scope="request"/>
        <html:form action="/login/register-mobilebank/view">
            <%-- ��������� �� ������� --%>
            <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp" flush="false">
                <tiles:put name="bundle" type="string" value="commonBundle"/>
            </tiles:insert>
            <%-- ����� ��������� �� ������� --%>
            <div class="clear"></div>
            <jsp:include page="view_data.jsp">
                <jsp:param name="afterLogin" value="true"/>
            </jsp:include>
        </html:form>

    </tiles:put>
</tiles:insert>
