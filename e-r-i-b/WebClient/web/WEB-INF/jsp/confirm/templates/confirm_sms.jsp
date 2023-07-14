<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

	<c:if test="${not confirmRequest.error}">
        <div id="paymentForm" onkeypress="onEnterKey(event);">

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">
                 <c:choose>
                    <c:when test="${confirmableObject == 'login'}">
                        Для подтверждения входа
                    </c:when>
                    <c:when test="${confirmableObject == 'securitySettings'}">
                        Для подтверждения настроек безопасности
                    </c:when>
                    <c:when test="${confirmableObject == 'personalSettings'}">
                        Для подтверждения персональной информации
                    </c:when>
                     <c:when test="${confirmableObject == 'viewSettings'}">
                        Для подтверждения настроек интерфейса
                    </c:when>
                    <c:when test="${confirmableObject == 'activePerson'}">
                         Для завершения операции
                    </c:when>
                    <c:otherwise>
                        Для подтверждения платежа
                    </c:otherwise>
                 </c:choose>
                <div class="simple">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.preConfirm"/>
                        <tiles:put name="commandTextKey" value="button.preConfirm"/>
                        <tiles:put name="commandHelpKey" value="button.preConfirm.help"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                        <tiles:put name="bundle" value="securityBundle"/>
                    </tiles:insert>
                </div>
            </tiles:put>
            <tiles:put name="data">
                <input type="text" class="confirmSmsInput" name="$$confirmSmsPassword" size="10"/>
            </tiles:put>
            <c:choose>
                <c:when test="${confirmableObject == 'login'}">
                  <c:set var="confirmText" value="Войти"/>
                </c:when>
                <c:otherwise>
                  <c:set var="confirmText" value="Подтвердить"/>
                </c:otherwise>
            </c:choose>
            <tiles:put name="description">
                Введите полученный по SMS пароль в это поле и нажмите кнопку "${confirmText}".
                <c:if test="${anotherStrategy}">
                    Также Вы можете использовать для подтверждения
                    <div class="simple">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.changeToCard"/>
                            <tiles:put name="commandTextKey" value="button.changeToCard"/>
                            <tiles:put name="commandHelpKey" value="button.changeToCard.help"/>
                            <tiles:put name="viewType" value="simpleLink"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                        </tiles:insert>
                    </div>
                </c:if>
            </tiles:put>
        </tiles:insert>
        <script type="text/javascript">
            function setFocus()
            {
                if (document.getElementsByName('$$confirmSmsPassword').length == 1)
                    document.getElementsByName('$$confirmSmsPassword')[0].focus();
            }
            doOnLoad(setFocus);
        </script>
	</c:if>
	<c:if test="${not empty message}">
		<div><br>${message}</div>
	</c:if>
	<c:if test="${confirmRequest.error}">
		<div class="error">
				<c:out value="${confirmRequest.errorMessage}"/>
		</div>
	</c:if>
</div>