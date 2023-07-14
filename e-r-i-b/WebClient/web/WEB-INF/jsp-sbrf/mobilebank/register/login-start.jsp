<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    Первый шаг процедуры подключения МБ: предложение подключиться, выбор тарифа
--%>

<tiles:importAttribute/>
<%--@elvariable id="globalUrl" type="java.lang.String"--%>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<%--@elvariable id="skinUrl" type="java.lang.String"--%>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="login">
    <tiles:put name="pageTitle" type="string" value="Подключение услуги Мобильный Банк"/>
    <tiles:put name="headerGroup" value="true"/>
    <tiles:put name="needHelp" value="true"/>
    <tiles:put name="data" type="string">

        <br/>
        <div class="login-register-mobilebank">
            <html:form action="/login/register-mobilebank/start" onsubmit="return setEmptyAction()">
                <%-- Сообщения об ошибках --%>
                <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp" flush="false">
                    <tiles:put name="bundle" type="string" value="commonBundle"/>
                </tiles:insert>
                <%-- Конец сообщений об ошибках --%>
                <div class="clear"></div>
                <jsp:include page="start_data.jsp">
                    <jsp:param name="afterLogin" value="true"/>
                </jsp:include>
            </html:form>
        </div>
    </tiles:put>
</tiles:insert>