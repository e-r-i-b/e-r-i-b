<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.ViewRegistrationForm"--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="roundBorder" flush="false">
    <c:if test="${param.afterLogin == 'true'}">
        <tiles:put name="color" value="greenTop"/>
    </c:if>
    <tiles:put name="title">
        <div class="align-left">Подключение услуги "Мобильный Банк"</div>
    </tiles:put>
    <tiles:put name="data">

        <%-- Заголовок --%>
        <tiles:insert definition="formHeader" flush="false">
            <tiles:put name="description">
                <c:choose>
                    <c:when test="${fromSystem == true}">
                        <h3>
                            <h3><bean:message bundle="mobilebankBundle" key="button.continue.help.text"/><span
                                    class="textToLowercase">&laquo;<bean:message bundle="mobilebankBundle"
                                                                                 key="button.register.next"/>&raquo;</span>.
                            </h3>
                        </h3>
                    </c:when>
                    <c:otherwise>
                        <h3><bean:message bundle="mobilebankBundle" key="button.enter.help.text"/>
                            <span class="textToLowercase">&laquo;<bean:message bundle="mobilebankBundle"
                                                                               key="button.register.next"/>&raquo;</span>.
                        </h3>
                    </c:otherwise>
                </c:choose>
            </tiles:put>
        </tiles:insert>

        <%-- Линия жизни --%>
        <div id="paymentStripe" <c:if test="${param.afterLogin == 'true'}">class="login-register-stripe"</c:if>>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="выбор пакета"/>
                <tiles:put name="future" value="false"/>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="заполнение заявки"/>
                <tiles:put name="future" value="false"/>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="подтверждение"/>
                <tiles:put name="future" value="false"/>
            </tiles:insert>
            <tiles:insert definition="stripe" flush="false">
                <tiles:put name="name" value="регистрация заявки"/>
                <tiles:put name="current" value="true"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>

        <%-- Форма --%>
        <%@ include file="view_fields.jsp" %>

        <%-- Кнопки --%>
        <div class="buttonsArea">
            <c:choose>
                <c:when test="${not empty form.returnURL}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.register.next"/>
                        <tiles:put name="commandHelpKey" value="button.register.next"/>
                        <tiles:put name="bundle" value="mobilebankBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="onclick">goTo('${form.returnURL}');</tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="next"/>
                        <tiles:put name="commandTextKey" value="button.register.next"/>
                        <tiles:put name="commandHelpKey" value="button.register.next"/>
                        <tiles:put name="bundle" value="mobilebankBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </div>
    </tiles:put>
</tiles:insert>
