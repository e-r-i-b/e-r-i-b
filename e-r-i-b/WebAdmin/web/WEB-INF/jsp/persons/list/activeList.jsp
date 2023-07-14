<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/persons/active/list/full">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="dictionary">
        <tiles:put name="mainmenu" type="string" value="MailManagment"/>
        <tiles:put name="submenu" type="string" value="SentMailList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="form.list.full.title" bundle="personsBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <nobr>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="mailBundle"/>
                    <tiles:put name="onclick" value="window.close()"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </nobr>
        </tiles:put>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.client"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="name"   value="fio"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="100"/>
                <tiles:put name="isDefault"><bean:message key="form.list.full.filter.hint.fio" bundle="personsBundle"/></tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.dul"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="name"   value="document"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="30"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"  value="label.terrbank"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(tb)" styleClass="select">
                        <html:option value=""><bean:message key="form.list.full.filter.hint.all" bundle="personsBundle"/></html:option>
                        <c:forEach var="tb" items="${form.allowedTB}">
                            <html:option value="${tb.key}">
                                <c:out value="${tb.value}"/>
                            </html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label"  value="label.birthDay"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="name"   value="birthday"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.login"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="name"   value="login"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="30"/>
                <tiles:put name="isDefault"><bean:message key="form.list.full.filter.hint.login" bundle="personsBundle"/></tiles:put>
            </tiles:insert>

            <%--Тип договора--%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"  value="label.agreementType"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(creationType)" styleClass="select">
                        <html:option value="">Все</html:option>
                        <html:option value="UDBO"><bean:message key="label.agreementType.UDBO" bundle="personsBundle"/></html:option>
                        <html:option value="SBOL"><bean:message key="label.agreementType.SBOL" bundle="personsBundle"/></html:option>
                        <html:option value="CARD"><bean:message key="label.agreementType.CARD" bundle="personsBundle"/></html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <%--Номер заявления--%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.agreementNumber"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="maxlength" value="16"/>
                <tiles:put name="name" value="agreementNumber"/>
            </tiles:insert>
            <%--Мобильный телефон--%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.mobilePhone"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="maxlength" value="20"/>
                <tiles:put name="name" value="mobilePhone"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="emptyProfileNodeMessage"><bean:message key="label.list.client.node.empty" bundle="personsBundle"/></c:set>

            <input type="hidden" name="blockReason" id="blockReason" value=""/>
            <input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
            <input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>
            <script type="text/javascript">
                function validateNodes()
                {
                    var result = true;
                    $('[name=selectedIds]:checked').each(function(index, element){
                        var id = $(element).val();
                        var nodeIdElement = $('[name=field(nodeId' + id + ')]');
                        if (isEmpty(nodeIdElement.val()))
                        {
                            result = false;
                            return false;
                        }
                    });
                    return result;
                }

                function sendClientInfo(event)
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Выберите клиента.') || (!checkOneSelection("selectedIds", 'Выберите одного клиента.')))
                        return;

                    if (!validateNodes())
                    {
                        alert('${emptyProfileNodeMessage}');
                        return false;
                    }

                    var dataContainer = $('[name=selectedIds]:checked:first').parents('tr:first');
                    var a = [];
                    a['name'] = trim($(dataContainer.find('td')[1]).text());
                    a['firstName'] = dataContainer.find('[name^=field(firstName]').val();
                    a['patrname'] = dataContainer.find('[name^=field(patrname]').val();
                    a['surname'] = dataContainer.find('[name^=field(surname]').val();
                    a['passport'] = dataContainer.find('[name^=field(passport]').val();
                    a['birthDate'] = dataContainer.find('[name^=field(birthDate]').val();
                    a['tb'] = dataContainer.find('[name^=field(tb]').val();
                    a['nodeId'] = dataContainer.find('[name^=field(nodeId]').val();
                    window.opener.setGroupData(a);
                    window.close();
                }

                <c:if test="${form.fromStart}">
                    //показываем фильтр при старте
                    $(document).ready(function(){switchFilter(this)});
                </c:if>
            </script>
            <c:set var="currentNode" value="${phiz:getCurrentNode()}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="personList"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.select"/>
                        <tiles:put name="commandHelpKey" value="button.select.help"/>
                        <tiles:put name="bundle" value="mailBundle"/>
                        <tiles:put name="onclick" value="sendClientInfo(event)"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="item" model="list" property="data" bundle="personsBundle" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                        <sl:collectionItem title="label.FIO" styleClass="data">
                            <c:set var="clientNodeId"   value=""/>
                            <c:set var="clientNodeType" value=""/>
                            <c:set var="isSelected"  value="${false}"/>
                            <c:forEach var="node" items="${item.nodes}">
                                <c:set var="currentNodeType">${node.type}</c:set>
                                <c:set var="clientCurrentNode" value="${phiz:getNodeInfo(node.id)}"/>
                                <c:if test="${clientCurrentNode.adminAvailable and not isSelected and currentNodeType eq 'MAIN'}">
                                    <c:set var="clientNodeId"   value="${node.id}"/>
                                    <c:set var="clientNodeType" value="${currentNodeType}"/>
                                    <c:set var="isSelected"     value="${node.id eq currentNode.id}"/>
                                </c:if>
                            </c:forEach>
                            <input type="hidden" name="field(firstName${item.id})"  value="${item.firstname}"/>
                            <input type="hidden" name="field(patrname${item.id})"   value="${item.patrname}"/>
                            <input type="hidden" name="field(surname${item.id})"    value="${item.surname}"/>
                            <input type="hidden" name="field(passport${item.id})"   value="${item.document}"/>
                            <c:if test="${not empty item.birthday}">
                                <c:set var="birthDay"><fmt:formatDate value="${item.birthday.time}" pattern="dd.MM.yyyy"/></c:set>
                                <input type="hidden" name="field(birthDate${item.id})" value="${birthDay}"/>
                            </c:if>
                            <input type="hidden" name="field(tb${item.id})"         value="${item.tb}"/>
                            <input type="hidden" name="field(nodeId${item.id})"     value="${clientNodeId}"/>

                            <c:out value="${item.surname} ${item.firstname} ${item.patrname}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.agreementNumber" property="agreementNumber"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage"><bean:message key="form.list.full.table.message.emptyList" bundle="personsBundle"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <c:if test="${form.failureIMSICheck}">
        <script>
            $(document).ready(function ()
            {
                $('[name=selectedIds]').each(function (index, element)
                {
                    var id = $(element).val();
                    if (id == ${form.selectedIds[0]})
                    {
                        element.checked = true;
                    }
                });
                win.open('failureIMSICheckWin');
            });
        </script>
    </c:if>

    <tiles:insert definition="failureIMSICheckWin" flush="false">
        <tiles:put name="id" value="failureIMSICheckWin"/>
    </tiles:insert>

</html:form>
