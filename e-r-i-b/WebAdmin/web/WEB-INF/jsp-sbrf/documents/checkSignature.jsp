<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html:form action="/documents/checkSignature" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${CheckPaymentSignatureForm}"/>
<tiles:insert definition="serviceMain">
	<tiles:put name="submenu" type="string" value="СheckSignature"/>

    <tiles:put name="menu" type="string">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.check.signature"/>
            <tiles:put name="commandHelpKey" value="button.check.signature.help"/>
            <tiles:put name="bundle" value="documentsBundle"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </tiles:put>

<tiles:put name="data" type="string">
    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="id" value="checkSignature"/>
		<tiles:put name="name" value="Проверка АСП"/>
        <tiles:put name="data">

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Идентификатор платежа\заявки
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="paymentId"/>
                </tiles:put>
            </tiles:insert>

            <c:set var="metadata"  value="${form.metadata}"/>
            <c:set var="signature" value="${form.signature}"/>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Результат первоначальной проверки
                </tiles:put>
                <tiles:put name="data"></tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Время
                </tiles:put>
                <tiles:put name="data">
                    <c:if test="${not empty signature}">
                        <bean:write name="signature" property="checkDate.time" format="yyyy.MM.dd HH:mm:ss"/>
                    </c:if>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Результат
                </tiles:put>
                <tiles:put name="data">
                    <c:if test="${not empty signature}">Успешно</c:if>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Идентификатор сессии клиента
                </tiles:put>
                <tiles:put name="data">
                    <c:out value="${signature.sessionId}"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Контрольная сумма
                </tiles:put>
                <tiles:put name="data">
                    <c:out value="${fn:substringAfter(signature.text,':')}"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Название документа
                </tiles:put>
                <tiles:put name="data">
                    <c:out value="${metadata.form.description}"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Результат повторной проверки
                </tiles:put>
                <tiles:put name="data">

                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Результат
                </tiles:put>
                <tiles:put name="data">
                    <c:set var="success" value="${form.success}"/>
                    <c:if test="${success}">Успешно</c:if>
                    <c:if test="${not success and not empty success}">Неуспешно</c:if>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Поля документа
                </tiles:put>
                <tiles:put name="data">
                    <c:out value="${form.documentContent}"/>
                </tiles:put>
            </tiles:insert>
	    </tiles:put>
        <tiles:put name="buttons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.check.signature"/>
                <tiles:put name="commandHelpKey" value="button.check.signature.help"/>
                <tiles:put name="bundle" value="documentsBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
	    </tiles:put>
	<tiles:put name="alignTable" value="center"/>
    </tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>