<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/sendsmsmethod/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="sendSMSPreferredMethod">
        <tiles:put name="submenu" type="string" value="SendSMSPreferredMethod"/>

        <tiles:put name="data" type="string">
            <c:choose>
                <c:when test="${not empty frm.departments}">
                    <tiles:insert definition="paymentForm" flush="false">
                        <tiles:put name="name" value="Способ получения номера телефона клиента"/>
                        <tiles:put name="description"
                                   value="Выберите для интересующего Вас подразделения предпочтительный способ получения номера телефона клиента для рассылки SMS-сообщений"/>
                        <tiles:put name="data">
                            <table id="" class="standartTable" cellspacing="0" cellpadding="0" width="100%">
                                <tr class="tblInfHeader">
                                    <td>Подразделение банка</td>
                                    <td>Способ получения</td>
                                </tr>
                                <c:forEach items="${frm.departments}" var="dep">
                                    <tr>
                                        <td width="60%">
                                            <c:out value="${dep.name}"/>
                                        </td>
                                        <td align="center">
                                            <html:select property="departmentMethods(${dep.id})">
                                                <html:option
                                                        value="PROFILE">Анкета клиента</html:option>
                                                <html:option
                                                        value="MOBILE_BANK">Мобильный банк</html:option>
                                                <html:option
                                                        value="MOBILE_BANK_ONLY">Только мобильный банк</html:option>
                                            </html:select>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </tiles:put>
                        <tiles:put name="buttons">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                <tiles:put name="bundle" value="departmentsBundle"/>
                                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false"
                                          service="DepartmentsManagement">
                                <tiles:put name="commandKey" value="button.save"/>
                                <tiles:put name="commandHelpKey" value="button.save.help"/>
                                <tiles:put name="bundle" value="commonBundle"/>
                                <tiles:put name="isDefault" value="true"/>
                                <tiles:put name="postbackNavigation" value="true"/>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="alignTable" value="center"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td class="messageTab" align="center">
                            У Вас нет доступа ни к одному ТБ для редактирования способа получения номера
                            телефона клиента.
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>