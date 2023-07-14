<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/messageTranslate/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="loggingEdit">
    <c:choose>
        <c:when test="${form.isCSA}">
            <c:set var="submenu" value="CSAMessageDictionary"/>
        </c:when>
        <c:otherwise>
            <c:set var="submenu" value="SettingMessageTranslate"/>
        </c:otherwise>
    </c:choose>
	<tiles:put name="submenu" type="string" value="${submenu}"/>

	<tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
            <tiles:put name="bundle"         value="messagetranslateBundle"/>
            <tiles:put name="action"         value="/messageTranslate/list.do?isCSA=${form.isCSA}"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
	</tiles:put>

    <tiles:put name="data" type="string">
            <c:set var="headerText"><bean:message key="label.form.edit.name" bundle="messagetranslateBundle"/></c:set>
            <c:set var="descriptionText"><bean:message key="label.form.edit.description" bundle="messagetranslateBundle"/></c:set>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="MessageTranslateEdit"/>
				<tiles:put name="name" value="${headerText}"/>
				<tiles:put name="description" value="${descriptionText}"/>
				<tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Название запроса/ответа
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(code)" size="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Комментарий
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(translate)" size="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Тип сообщения
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="field(type)">
                                <html:option value="R">Запрос</html:option>
                                <html:option value="A">Ответ</html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="messagetranslateBundle" key="label.logType"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="field(logType)">
                                <html:option value="nonFinancial"><bean:message bundle="messagetranslateBundle" key="label.nonFinancial"/></html:option>
                                <html:option value="financial"><bean:message bundle="messagetranslateBundle" key="label.financial"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="messagetranslateBundle" key="label.isNew"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(isNew)"/>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <input name="isCSA" value="${form.isCSA}" type="hidden"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="messagetranslateBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>                
        </tiles:insert>
    </tiles:put>

    </tiles:insert>

</html:form>