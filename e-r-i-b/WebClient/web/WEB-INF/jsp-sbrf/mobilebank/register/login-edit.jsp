<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%--
    Второй шаг процедуры подключения МБ: заполнение заявки
--%>

<tiles:insert definition="login">
    <tiles:put name="pageTitle" type="string">
        Подключение услуги "Мобильный Банк"
    </tiles:put>
    <tiles:put name="headerGroup" value="true"/>
    <tiles:put name="data" type="string">

        <br/>
        
        <html:form action="/login/register-mobilebank/edit">
            <%-- Сообщения об ошибках --%>
            <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp" flush="false">
                <tiles:put name="bundle" type="string" value="commonBundle"/>
            </tiles:insert>
            <%-- Конец сообщений об ошибках --%>
            <div class="clear"></div>
            <jsp:include page="edit_data.jsp">
                <jsp:param name="afterLogin" value="true"/>
            </jsp:include>
        </html:form>

    </tiles:put>
</tiles:insert>
