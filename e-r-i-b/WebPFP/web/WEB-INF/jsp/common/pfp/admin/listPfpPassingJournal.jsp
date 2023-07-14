<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/passingPfpJournal" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="description"><bean:message key="pfpList.title" bundle="pfpJournalBundle"/></tiles:put>
        <tiles:put name="filter">
            <tiles:insert definition="filterDataSpan" flush="false">
                <tiles:put name="label" value="label.date"/>
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="Date"/>
                <tiles:put name="template" value="DATE_TEMPLATE"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.client"/>
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="size" value="50"/>
                <tiles:put name="maxlength" value="255"/>
                <tiles:put name="name" value="user_fio"/>
                <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.manager"/>
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="size" value="50"/>
                <tiles:put name="maxlength" value="128"/>
                <tiles:put name="name" value="employee_fio"/>
                <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
                <tiles:put name="editable"      value="${phiz:impliesService('ListPFPPassingJournalServiceTB')}"/>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="label" value="label.state"/>
                <tiles:put name="data">
                    <html:select property="filter(state)" styleClass="select">
                        <html:option value="">Все</html:option>
                        <html:option value="INITIAL">Прервано</html:option>
                        <html:option value="COMPLITE">Выполнено</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="label" value="label.riskProfile"/>
                <tiles:put name="data">
                    <html:select property="filter(risk_profile)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="pfpJournalBundle"/></html:option>
                        <c:forEach items="${phiz:getAllRiskProfiles()}" var="riskProfile">
                            <c:set var="riskProfilename" value="${riskProfile.name}"/>
                            <c:if test="${riskProfile.isDefault}">
                                 <c:set var="riskProfilename" value="Неопределен"/>
                            </c:if>
                            <html:option value="${riskProfile.id}">${riskProfilename}</html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filter2TextField" flush="false">
                <tiles:put name="label" value="label.document"/>
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="name"   value="documentSeries"/>
                <tiles:put name="size"   value="5"/>
                <tiles:put name="maxlength"  value="16"/>
                <tiles:put name="isDefault" value="Серия"/>
                <tiles:put name="name2"   value="documentNumber"/>
                <tiles:put name="size2"   value="10"/>
                <tiles:put name="maxlength2"  value="16"/>
                <tiles:put name="default2" value="Номер"/>
            </tiles:insert>
            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label" value="label.birthDay"/>
                <tiles:put name="bundle" value="pfpJournalBundle"/>                
                <tiles:put name="name" value="birthday"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.managerId"/>
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="size" value="20"/>
                <tiles:put name="maxlength" value="13"/>
                <tiles:put name="name" value="manager_id"/>
                <tiles:put name="editable"      value="${phiz:impliesService('ListPFPPassingJournalServiceTB')}"/>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="bundle" value="pfpJournalBundle"/>
                <tiles:put name="label" value="label.channels"/>
                <tiles:put name="data">
                    <html:select property="filters(channelId)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="pfpJournalBundle"/></html:option>
                        <c:forEach items="${phiz:getAllChannels()}" var="channel">
                            <html:option value="${channel.id}"><c:out value="${channel.name}"/></html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data">
            <script type="text/javascript">
                /* перенаправляем на другой экшен */
                function clearFilter()
                {
                    ajaxQuery("url=/passingPfpJournal", "${phiz:calculateActionURL(pageContext,'/async/clearFilter')}", clearFilterParams, null, true);
                }

                <c:if test="${form.fields.relocateToDownload != null && form.fields.relocateToDownload == 'true'}">
                    $(document).ready(
                        function()
                        {
                            <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=PFPPassingJournal&clientFileName=${form.fields.clientFileName}"/>
                            clientBeforeUnload.showTrigger=false;
                            goTo('${downloadFileURL}');
                            clientBeforeUnload.showTrigger=false;
                       });
                </c:if>
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="pfpList"/>

                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="UnloadPPFJournalOperation" service="UnloadPPFJournalService">
                        <tiles:put name="commandKey"     value="button.unload"/>
                        <tiles:put name="commandHelpKey" value="button.unload.help"/>
                        <tiles:put name="bundle"         value="pfpJournalBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return true;
                            }
                        </tiles:put>                        
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpBundle" selectBean="person">                                              
                        <c:set var="pfpId" value="${listElement.pfpId}"/>
                        <c:set var="region" value="${listElement.userTB}"/>
                        <c:set var="date">
                             <fmt:formatDate value="${listElement.creationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                        </c:set>
                        <c:set var="endDate">
                            <fmt:formatDate value="${listElement.executionDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                        </c:set>
                        <c:set var="user" value="${listElement.userSurName} ${listElement.userFirstName} ${listElement.userPatrName}"/>
                        <c:set var="employee" value="${listElement.managerFIO}"/>
                        <c:set var="state" ><bean:message key="label.break" bundle="pfpJournalBundle"/></c:set>
                        <c:if test="${listElement.stateCode== 'COMPLITE' or listElement.stateCode=='COMPLITE_OLD'}">
                            <c:set var="state" ><bean:message key="label.complite" bundle="pfpJournalBundle"/></c:set>
                        </c:if>
                        <sl:collectionItem width="10px">
                            <sl:collectionItemParam id="title"><input type="checkbox" name="isSelectAll" onclick="switchSelection('isSelectAll','selectedIds');"></sl:collectionItemParam>
                            <sl:collectionItemParam id="value"><input type="checkbox" name="selectedIds" value="${pfpId}"></sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="Начало">
                            <c:set var="birthDay">
                                <fmt:formatDate value="${listElement.userBirthday.time}" pattern="dd.MM.yyyy"/>
                            </c:set>

                            <c:set var="params" value="?field(surName)=${listElement.userSurName}"/>
                            <c:set var="params" value="${params}&field(region)=${region}"/>
                            <c:set var="params" value="${params}&field(firstName)=${listElement.userFirstName}"/>
                            <c:set var="params" value="${params}&field(patrName)=${listElement.userPatrName}"/>
                            <c:set var="params" value="${params}&field(birthDay)=${birthDay}"/>

                            <c:set var="params" value="${params}&field(documentType)=${listElement.userDocumentType}"/>
                            <c:set var="params" value="${params}&field(documentName)=${listElement.userDocumentName}"/>
                            <c:set var="params" value="${params}&field(documentSeries)=${listElement.userDocumentSeries}"/>
                            <c:set var="params" value="${params}&field(documentNumber)=${listElement.userDocumentNumber}"/>


                            <html:link contextPathName="application" action="/pfp/person/search.do${params}">
                                 ${date}
                            </html:link>
                        </sl:collectionItem>
                        <sl:collectionItem title="Клиент" value="${user}"/>
                        <sl:collectionItem title="Канал" value="${listElement.channelName}"/>
                        <sl:collectionItem title="Сотрудник" value="${employee}"/>
                        <sl:collectionItem title="Статус" value="${state}"/>
                        <sl:collectionItem title="Окончание" value="${endDate}"/>
                    </sl:collection>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        Не найдено ни одного документа, соответствующего заданному фильтру!
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>