<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<html:form action="/private/depo/documents" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="depoAccountLink" value="${form.depoAccountLink}" scope="request"/>
    <c:set var="depoAccount" value="${depoAccountLink.depoAccount}" scope="request"/>
    <c:set var="depoAccDocList" value="true"/>
    <c:set var="detailsPage" value="true"/>
<tiles:insert definition="depoAccountInfo">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Счета депо"/>
            <tiles:put name="action" value="/private/depo/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><span class="bold">${depoAccountLink.accountNumber}</span></tiles:put>
            <tiles:put name="action" value="/private/depo/info/position.do?id=${depoAccountLink.id}"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Список документов по счету депо"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="data">


                <c:set var="nameOrNumber">
                    <c:choose>
                       <c:when test="${not empty depoAccountLink.name}">
                          <c:out value="«${depoAccountLink.name}»"/>
                       </c:when>
                       <c:otherwise>
                          ${depoAccountLink.accountNumber}
                       </c:otherwise>
                    </c:choose>
                </c:set>

                <c:set var="pattern">
                    <c:choose>
                       <c:when test="${not empty depoAccountLink.name}">
                          «${depoAccountLink.patternForFavouriteLink}»
                       </c:when>
                       <c:otherwise>
                          ${depoAccountLink.patternForFavouriteLink}
                       </c:otherwise>
                    </c:choose>
                </c:set>

                <div class="abstractContainer3">
                    <c:set var="baseInfo">
                        <bean:message key="favourite.link.depo" bundle="paymentsBundle"/>
                    </c:set>
                    <div class="favouriteLinksButton">
                        <tiles:insert definition="addToFavouriteButton" flush="false">
                            <tiles:put name="formName"><c:out value='${baseInfo} ${nameOrNumber}'/></tiles:put>
                            <tiles:put name="patternName"><c:out value='${baseInfo} ${pattern}'/></tiles:put>
                            <tiles:put name="typeFormat">DEPO_LINK</tiles:put>
                            <tiles:put name="productId">${form.id}</tiles:put>
                        </tiles:insert>
                    </div>
                </div>

                <%@ include file="/WEB-INF/jsp/depo/depoAccountTemplate.jsp"%>

                <div class="tabContainer">
                    <tiles:insert definition="paymentTabs" flush="false">
                        <tiles:put name="count" value="2"/>
                        <c:set var="position" value="last"/>
                        <c:if test="${depoAccount.state == 'closed'}">
                            <tiles:put name="count" value="1"/>
                            <c:set var="position" value="first-last"/>
                        </c:if>
                        <tiles:put name="tabItems">
                            <c:if test="${depoAccount.state != 'closed'}">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first"/>
                                    <tiles:put name="active" value="false"/>
                                    <tiles:put name="title" value="Информация по позиции"/>
                                    <tiles:put name="action" value="/private/depo/info/position.do?id=${depoAccountLink.id}"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="position" value="${position}"/>
                                <tiles:put name="active" value="true"/>
                                <tiles:put name="title" value="Список документов"/>
                                <tiles:put name="action" value="/private/depo/documents.do?id=${depoAccountLink.id}"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <div class="clear">&nbsp;</div>

                    <%--Фильтр--%>
                    <div class="clear"></div>
                    <tiles:insert definition="filterDataPeriod" flush="false">
                        <tiles:put name="week" value="false"/>
                        <tiles:put name="month" value="false"/>
                        <tiles:put name="title" value="Показать документы"/>
                        <tiles:put name="name" value="Date"/>
                        <tiles:put name="needErrorValidate" value="false"/>
                        <tiles:put name="isNeedTitle" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="filter" flush="false">
                        <tiles:put name="buttonBundle" value="depoBundle"/>
                        <tiles:put name="buttonKey" value="button.filter"/>
                        <tiles:put name="hideFilterButton" value="true"/>
                        <tiles:put name="showClearButton" value="true"/>
                        <tiles:put name="hiddenData">
                            <table class="depoDocuments-filter">
                                <tr>
                                    <td class="align-right">Номер документа: &nbsp;</td>
                                    <td>
                                        <html:text property="filter(docNumber)" styleClass="documentNumber" size="10"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right">Статус документа: &nbsp;</td>
                                    <td>
                                        <html:select property="filter(docState)" styleClass="documentStatus">
                                            <html:option value="">
                                                <bean:message key="depo.document.state.ALL" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="SAVED">
                                                <bean:message key="depo.document.state.SAVED" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="DISPATCHED">
                                                <bean:message key="depo.document.state.DISPATCHED" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="EXECUTED">
                                                <bean:message key="depo.document.state.EXECUTED" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="RECALLED">
                                                <bean:message key="depo.document.state.RECALLED" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="REFUSED">
                                                <bean:message key="depo.document.state.REFUSED" bundle="depoBundle"/>
                                            </html:option>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right">Тип документа: &nbsp;</td>
                                    <td>
                                        <html:select property="filter(docType)" styleClass="documentType">
                                            <html:option value="">
                                                <bean:message key="depo.document.ALL" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="SecuritiesTransferClaim">
                                                <bean:message key="depo.document.SecuritiesTransferClaim" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="SecurityRegistrationClaim">
                                                <bean:message key="depo.document.SecurityRegistrationClaim" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="DepositorFormClaim">
                                                <bean:message key="depo.document.DepositorFormClaim" bundle="depoBundle"/>
                                            </html:option>
                                            <html:option value="RecallDepositaryClaim">
                                                <bean:message key="depo.document.RecallDepositaryClaim" bundle="depoBundle"/>
                                            </html:option>
                                        </html:select>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                    <%--Список документов--%>
                    <div class="depoDocumentsList">
                    <c:set var="confilmURL" value="${phiz:calculateActionURL(pageContext,'/private/payments/confirm')}"/>
                    <c:set var="viewURL" value="${phiz:calculateActionURL(pageContext,'/private/payments/view')}"/>
                    <c:set var="editURL" value="${phiz:calculateActionURL(pageContext,'/private/payments/payment')}"/>
                    <tiles:insert definition="simpleTableTemplate" flush="false">
                        <tiles:put name="grid">
                            <sl:collection id="businessDocument" model="simple-pagination" property="data">
                                <sl:collectionParam id="selectType" value="radio"/>
                                <sl:collectionParam id="selectName" value="selectedIds"/>
                                <sl:collectionParam id="selectProperty" value="id"/>

                                <c:set var="code" value="${businessDocument.state.code}" scope="request"/>

                                <c:choose>
                                    <c:when test="${code == 'SAVED' and depoAccount.state != 'closed'}">
                                         <c:set var="click" value="${confilmURL}?id=${businessDocument.id}"/>
                                    </c:when>
                                    <c:when test="${code == 'INITIAL' and depoAccount.state != 'closed'}">
                                        <c:set var="click" value="${editURL}?id=${businessDocument.id}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="click" value="${viewURL}?id=${businessDocument.id}"/>
                                    </c:otherwise>
                                </c:choose>

                                <sl:collectionItem styleClass="align-left" title="Номер &nbsp;">
                                    <html:link href="${click}" >${businessDocument.documentNumber}</html:link>
                                </sl:collectionItem>

                                <sl:collectionItem styleClass="align-left" title="Дата создания &nbsp;">
                                    <html:link href="${click}" >
                                        ${phiz:formatDateDependsOnSysDate(businessDocument.dateCreated, true, false)}
                                    </html:link>
                                </sl:collectionItem>

                                <sl:collectionItem styleClass="align-left" title="Тип документа &nbsp;">
                                    <html:link href="${click}" >
                                        <c:choose>
                                            <c:when test="${businessDocument.formName == 'RurPayment'}">
                                                <c:choose>
                                                    <c:when test="${businessDocument.receiverSubType == 'externalAccount'}">
                                                        Перевод частному лицу в другой банк по реквизитам
                                                    </c:when>
                                                    <c:when test="${businessDocument.receiverSubType == 'ourCard' or businessDocument.receiverSubType == 'ourAccount' or  businessDocument.receiverSubType == 'ourPhone'}">
                                                        Перевод клиенту Сбербанка
                                                    </c:when>
                                                    <c:otherwise>
                                                        Перевод на карту в другом банке
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <bean:message bundle="paymentsBundle" key="paymentform.${businessDocument.formName}" failIfNone="false"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </html:link>
                                </sl:collectionItem>

                                <sl:collectionItem styleClass="align-left" title="Дата исполнения &nbsp;">
                                    <html:link href="${click}" >
                                        ${phiz:formatDateDependsOnSysDate(businessDocument.executionDate, true, false)}
                                    </html:link>
                                </sl:collectionItem>

                                <sl:collectionItem styleClass="align-left" title="Статус &nbsp;">
                                    <c:if test="${not empty code}">
                                        <html:link href="${click}" ><bean:message key="depo.document.state.${code}" bundle="depoBundle"/></html:link>
                                    </c:if>
                                </sl:collectionItem>

                                <sl:collectionItem hidden="true" value="${businessDocument.formName}"/>
                                <sl:collectionItem hidden="true" value="${code}"/>

                            </sl:collection>
                        </tiles:put>
                        <tiles:put name="isEmpty" value="${empty form.data}"/>
                        <tiles:put name="emptyMessage">
                            <bean:message bundle="depoBundle" key="label.documents.emptyMessage"/>
                        </tiles:put>
                    </tiles:insert>
                    </div>

                    <%--Кнопки--%>
                    <div class="buttonsArea">
                        <c:if test="${not empty form.data}">
                        <c:if test="${depoAccount.state != 'closed'}">
                            <tiles:insert definition="clientButton" service="RecallDepositaryClaim" flush="false">
                                <tiles:put name="commandTextKey" value="button.withdraw"/>
                                <tiles:put name="commandHelpKey" value="button.withdraw.help"/>
                                <tiles:put name="bundle"         value="paymentsBundle"/>
                                <tiles:put name="viewType"       value="buttonGrey"/>
                                <tiles:put name="onclick"        value="tryRecall();"/>
                            </tiles:insert>

                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.copy"/>
                                <tiles:put name="commandHelpKey" value="button.copy.help"/>
                                <tiles:put name="bundle"         value="depoBundle"/>
                                <tiles:put name="onclick"        value="addCopy();"/>
                            </tiles:insert>
                        </c:if>

                        <tiles:insert definition="confirmationButton" flush="false">
                            <tiles:put name="winId" value="confirmation"/>
                            <tiles:put name="title" value="Подтверждение удаления документа"/>
                            <tiles:put name="currentBundle"  value="commonBundle"/>
                            <tiles:put name="confirmCommandKey" value="button.remove"/>
                            <tiles:put name="validationFunction" value="validate();"/>
                            <tiles:put name="getConfirmMessageFunction" value="getInfo();"/>
                        </tiles:insert>
                        </c:if>

                    </div>
                </div>
            </tiles:put>
        </tiles:insert>
        <c:if test="${not empty form.anotherDepoAccounts}">
            <div id="another-cards">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <c:set var="depoAccountCount" value="${phiz:getClientProductLinksCount('DEPO_ACCOUNT')}"/>
                    <tiles:put name="title">
                        Остальные счета депо
                        (<a href="${phiz:calculateActionURL(pageContext, '/private/depo/list')}" class="blueGrayLink">показать все ${depoAccountCount}</a>)
                    </tiles:put>
                    <tiles:put name="data">
                        <div class="another-items">
                            &nbsp;
                            <c:set var="depoInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/depo/info/position.do?id=')}"/>
                            <c:forEach items="${form.anotherDepoAccounts}" var="others">
                                <div class="another-container">
                                    <a href="${depoInfoUrl}${others.id}">
                                        <img src="${image}/icon_depositariy32.jpg" alt=""/>
                                    </a>
                                    <a class="another-number decoration-none" href="${depoInfoUrl}${others.id}">
                                        <c:choose>
                                            <c:when test="${empty others.name}">
                                                ${others.accountNumber}
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${others.name}(${others.accountNumber})"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                    <div class="another-name">
                                        <a class="another-name" href="${depoInfoUrl}${others.id}">${others.depoAccount.agreementNumber}</a>
                                        <c:set var="state" value="${others.depoAccount.state}"/>
                                        <c:set var="className">
                                            <c:if test="${state eq 'closed'}">
                                                red
                                            </c:if>
                                        </c:set>

                                        <span class="${className}">
                                            <span class="prodStatus status" style="font-weight:normal;">
                                                <c:if test="${not empty className}">
                                                    <nobr>(${state.description})</nobr>
                                                </c:if>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                            &nbsp;
                            <div class="clear"></div>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </tiles:put>
</tiles:insert>
<script type="text/javascript">

    function getFirstSelectedElem(name)
    {
        if (name == null) name = "selectedIds";
        var ids = document.getElementsByName(name);
        for (i = 0; i < ids.length; i++) if (ids[i].checked) return ids[i];
        return null;
    }

    var addUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/payment')}";

    function getInfo()
    {
         var ids = document.getElementsByName("selectedIds");
         var key = getRadioValue(ids);
         for (var i = 0; i < ids.length; i++)
         {
            if (ids.item(i).checked)
            {
                var r = ids.item(i).parentNode.parentNode;
                var docNum  = trim(getElementTextContent(r.cells[1]));
                var docType = trim(getElementTextContent(r.cells[3]));
                var info = "Вы действительно хотите удалить документ №"+ docNum +
                       "<br><br>"+ "\"" +docType + "\"" + "?"
                return info;
            }
         }
    }

    function validate()
    {
        if(!checkOneSelection())
        {
            return false;
        }
        var elem = getFirstSelectedElem("selectedIds");
        var r = elem.parentNode.parentNode;
        var state = getElementTextContent(r.cells[7]);
        if(state != "SAVED" && state != "INITIAL")
        {
            removeError();
            addError("Удалять можно только документы со статусом «Введен». Для отзыва документов воспользуйтесь кнопкой «Отозвать»");
            return false;
        }
        return true;
    }

    function addCopy()
    {
        if(!checkOneSelection())
        {
            return;
        }
        var elem = getFirstSelectedElem("selectedIds");
        var r = elem.parentNode.parentNode;
        var state = getElementTextContent(r.cells[7]);
        var docType = getElementTextContent(r.cells[6]);
        if(state == "SAVED")
        {
            addError("Копировать документы со статусом 'Введен' запрещено. Выберите другой документ.");
            return;
        }
        if(docType == "RecallDepositaryClaim")
        {
            addMessage("Отзывы копировать нельзя. Выберите другой документ.");
            return;
        }
        var paymentId = getFirstSelectedId("selectedIds");
        window.location = addUrl + "?copying=true&id=" + paymentId;
    }

    function checkOneSelection(mes)
    {
        checkIfOneItem("selectedIds");
        var qnt = getSelectedQnt("selectedIds");
        removeError();
        if (qnt > 1)
        {
            addError("Укажите только одну запись");
            return false;
        }
        else if(qnt == 0)
        {
            var message =  mes || "Укажите одну запись";
            addError(message);
            return false;
        }
        return true;
    }

    //очистить результаты поиска
    function clearFilter()
    {
        <c:set var="startAction" value="/private/depo/documents"/>
        <c:set var="startUrl" value="${phiz:calculateActionURL(pageContext, startAction)}"/>
        window.location = "${startUrl}" + "?id=" + "${form.id}";
    }

    var recallUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/payment')}"
    function tryRecall()
    {
        if(!checkOneSelection('Пожалуйста, отметьте в списке документ по счету депо, который Вы хотите отозвать.'))
        {
            return;
        }
        var elem = getFirstSelectedElem("selectedIds");
        var r = elem.parentNode.parentNode;
        var state = getElementTextContent(r.cells[7]);
        if(state == 'DISPATCHED')
        {
           var paymentId = getFirstSelectedId("selectedIds");
           window.location = recallUrl + "?form=RecallDepositaryClaim&recallDocumentId=" + paymentId;
        }
        else if(state =='DELAYED_DISPATCH')
        {
             new CommandButton('button.withdraw').click();
        }
        else
        {
            removeError();
            addError("Вы не можете отозвать этот документ.");
        }

    }
</script>
</html:form>
