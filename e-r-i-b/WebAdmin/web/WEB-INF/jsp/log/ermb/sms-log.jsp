<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>


<html:form action="/log/ermb/sms">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="ermbLog">
        <tiles:put name="pageTitle" type="string">
            Журнал сообщений
        </tiles:put>
        <tiles:put name="submenu" type="string" value="ErmbSmsLog"/>
        <tiles:put name="filter" type="string">
            <c:if test="${empty form.personId or form.personId == 0}">
                <%--Клиент--%>
                <tiles:insert definition="filterTextField" flush="false">
                    <tiles:put name="label" value="label.client"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="size"   value="50"/>
                    <tiles:put name="maxlength"  value="255"/>
                    <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
                    <tiles:put name="name" value="fio"/>
                </tiles:insert>
                <%--Документ--%>
                <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label" value="ermb.log.sms.document.type"/>
                    <tiles:put name="bundle" value="ermbBundle"/>
                    <tiles:put name="data">
                        <html:select property="filter(documentType)" styleClass="select">
                            <c:forEach var="documentType" items="${form.documentTypes}">
                                <html:option value="${documentType.value}">
                                    <bean:message key="document.type.${documentType.key}" bundle="personsBundle"/>
                                </html:option>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="filter2TextField" flush="false">
                    <tiles:put name="label" value="label.document"/>
                    <tiles:put name="bundle" value="claimsBundle"/>
                    <tiles:put name="name"   value="documentSeries"/>
                    <tiles:put name="size"   value="5"/>
                    <tiles:put name="maxlength"  value="16"/>
                    <tiles:put name="isDefault" value="Серия"/>
                    <tiles:put name="name2"   value="documentNumber"/>
                    <tiles:put name="size2"   value="10"/>
                    <tiles:put name="maxlength2"  value="16"/>
                    <tiles:put name="default2" value="Номер"/>
                </tiles:insert>
                <%--Дата рождения--%>
                <tiles:insert definition="filterDateField" flush="false">
                    <tiles:put name="label" value="label.birthDay"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="name" value="birthDay"/>
                </tiles:insert>
                <%--Телефон--%>
                <tiles:insert definition="filterTextField" flush="false">
                    <tiles:put name="label" value="ermb.log.sms.phone"/>
                    <tiles:put name="bundle" value="ermbBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="maxlength" value="20"/>
                    <tiles:put name="isDefault" value="Телефон"/>
                    <tiles:put name="name" value="phone"/>
                </tiles:insert>
                <%--Тербанк--%>
                <tiles:insert definition="filterTextField" flush="false">
                    <tiles:put name="label" value="ermb.log.sms.tb"/>
                    <tiles:put name="bundle" value="ermbBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="maxlength" value="3"/>
                    <tiles:put name="isDefault" value="ТБ"/>
                    <tiles:put name="name" value="tb"/>
                </tiles:insert>
            </c:if>
            <%--Дата и время--%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="ermb.log.sms.date.time.period"/>
                <tiles:put name="bundle" value="ermbBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="white-space:nowrap;">
                        с&nbsp;
                        <span style="font-weight:normal;overflow:visible;cursor:default;">
                            <input type="text"
                                    size="10" name="filter(fromDate)" class="dot-date-pick"
                                    value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                            <input type="text"
                                    size="8" name="filter(fromTime)" class="time-template"
                                    value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                                    onkeydown="onTabClick(event,'filter(toDate)');"/>
                        </span>
                            &nbsp;по&nbsp;
                        <span style="font-weight:normal;cursor:default;">
                            <input type="text"
                                    size="10" name="filter(toDate)" class="dot-date-pick"
                                    value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                            <input type="text"
                                    size="8" name="filter(toTime)" class="time-template"
                                    value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                        </span>
                    </span>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <input type="hidden" name="sessionId" value="${form.sessionId}"/>
            <input type="hidden" name="personId" value="${form.personId}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="smsLog"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="wide-list" property="data" bundle="ermbBundle" styleClass="standartTable">
                        <sl:collectionItem title="ermb.log.sms.title.phone">
                            <c:out value="${listElement.phone}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.lognumber">
                            <c:out value="${listElement.inRqID}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.inReceiveTime" width="105px">
                            <c:out value="${phiz:formatDateToStringOnPattern(listElement.inReceiveTime, 'HH:mm dd.MM.yyyy')}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.textIn">
                            <c:out value="${listElement.inMessage}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.lognumber">
                            <c:out value="${listElement.outRqID}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.outDeliverTime">
                            <c:out value="${phiz:formatDateToStringOnPattern(listElement.outDeliverTime, 'HH:mm dd.MM.yyyy')}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.outSendTime">
                            <c:out value="${phiz:formatDateToStringOnPattern(listElement.outSendTime, 'HH:mm dd.MM.yyyy')}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.status">
                            <c:out value="${listElement.outState}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.product">
                            <c:out value="${listElement.outResource}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.textOut">
                            <c:out value="${listElement.outMessage}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.system">
                            <c:out value="${listElement.system}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.log.sms.title.extinfo">
                            <c:out value="${listElement.additionalInfo}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>

                <tiles:put name="emptyMessage">
                    <c:choose>
                        <c:when test="${form.fromStart}">
                            <script type="text/javascript">switchFilter(this);</script>
                            Для поиска сообщений в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                        </c:when>
                        <c:otherwise>
                            Не найдено ни одного сообщения, <br/>соответствующего заданному фильтру!
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

<script type="text/javascript">
    doOnLoad(function()
    {
        <%--показывать фильт всегда при поиске по клиенту--%>
        var personId = $("input[name='personId']").val();
        if (personId != '0' && personId != '')
        {
            switchFilter(this);
        }
    });
</script>
