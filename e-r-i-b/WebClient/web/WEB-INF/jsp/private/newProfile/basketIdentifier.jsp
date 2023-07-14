<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="hasINN" value="false"/>
<c:set var="hasSNILS" value="false"/>
<c:set var="hasDL" value="false"/>
<c:set var="hasRC" value="false"/>
<c:set var="accessDL" value="${form.accessDL && not empty form.ident['DL']}"/>
<c:set var="accessRC" value="${form.accessRC && not empty form.ident['RC']}"/>
<c:set var="accessINN" value="${form.accessINN && not empty form.ident['INN']}"/>
<c:set var="editIdentifierUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/editIdentifier')}"/>
<c:forEach var="doc" items="${form.userDocumentList}">
    <c:set var="docType" value="${doc.documentType}"/>

        <c:if test="${docType == 'INN'}">
            <c:set var="hasINN" value="true"/>
            <c:set var="colorWin" value="grayBorder"/>
            <c:set var="identType" value="${form.ident['INN']}"/>
        </c:if>
        <c:if test="${docType == 'SNILS'}">
            <c:set var="hasSNILS" value="true"/>
            <c:set var="colorWin" value="greenBorder"/>
        </c:if>
        <c:if test="${docType == 'DL'}">
            <c:set var="hasDL" value="true"/>
            <c:set var="colorWin" value="lilacBorder"/>
            <c:set var="identType" value="${form.ident['DL']}"/>
        </c:if>
        <c:if test="${docType == 'RC'}">
            <c:set var="hasRC" value="true"/>
            <c:set var="colorWin" value="lilacBorder"/>
            <c:set var="identType" value="${form.ident['RC']}"/>
        </c:if>
        <c:if test="${(docType == 'INN' && accessINN) || (docType == 'DL' && accessDL)  || (docType == 'RC' && accessRC) }">
            <div class="person${docType}" onclick="win.open('addIdetnifierBasket${docType}');">
                <c:if test="${docType != 'INN'}"><div class="documentIcon"></div></c:if>
                <div class="documentTitle"><c:out value="${identType.name}"/></div>
                <div class="documentNumber">
                    <c:if test="${not empty identType.attributes['SERIES']}">${phiz:getCutSeriesForDLorRC(doc.series)}  </c:if>
                    <c:if test="${not empty identType.attributes['NUMBER']}">${phiz:getCutNumberForDLorRC(doc.number)}</c:if></div>
            </div>
            <tiles:insert definition="windowLight" flush="false">
                <tiles:put name="id" value="addIdetnifierBasket${docType}"/>
                <tiles:put name="styleClass" value="addIdetnifierBasket containLink"/>
                <tiles:put name="loadAjaxUrl" value="${editIdentifierUrl}?documentType=${docType}&id=${doc.id}&actionType=view"/>
                <tiles:put name="closeCallback" value="reloadProfile"/>
                <tiles:put name="color" value="${colorWin}"/>
            </tiles:insert>
        </c:if>
        <c:if test="${docType == 'SNILS'}">
            <div class="person${docType}" onclick="win.open('addIdetnifierBasket${docType}');">
                <div class="documentIcon"></div>
                <div class="documentTitle"><bean:message key="user.document.${docType}" bundle="userprofileBundle"/></div>
                <div class="documentNumber">
                    ${doc.series}
                    ${doc.number}
                </div>
            </div>
            <tiles:insert definition="windowLight" flush="false">
                <tiles:put name="id" value="addIdetnifierBasket${docType}"/>
                <tiles:put name="styleClass" value="addIdetnifierBasket containLink"/>
                <tiles:put name="loadAjaxUrl" value="${editIdentifierUrl}?documentType=${docType}&id=${doc.id}&actionType=view"/>
                <tiles:put name="closeCallback" value="reloadProfile"/>
                <tiles:put name="color" value="${colorWin}"/>
            </tiles:insert>
        </c:if>
</c:forEach>
<c:set var="accessToBasket" value="${phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment')}"/>
<c:if test="${!hasSNILS}">
    <c:choose>
        <c:when test="${!accessToBasket}">
            <div class="personSNILS" onclick="win.open('addIdetnifierBasketSNILS');">
                <div class="documentTitleEmpty">СТРАХОВОЕ <br/>СВИДЕТЕЛЬСТВО</div>
                <div class="documentDescEmpty">Государственного пенсионного <br/>страхования</div>
                <div class="addDocumentButton">
                    <tiles:insert definition="profileButton" flush="false">
                        <tiles:put name="commandText" value="Добавить СНИЛС"/>
                        <tiles:put name="viewType" value="addSNILSButton"/>
                        <tiles:put name="onclick" value="win.open('addIdetnifierBasketSNILS');"/>
                    </tiles:insert>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="emptyDocWithBasket" onclick="win.open('addIdetnifierBasketSNILS');">
                <div class="personSnilsWithBasket">
                    <div class="snilsShadow"></div>
                    <div class="personSNILS withBasket">
                        <div class="documentTitleEmpty">СТРАХОВОЕ <br/>СВИДЕТЕЛЬСТВО</div>
                        <div class="documentDescEmpty">Государственного пенсионного <br/>страхования</div>
                        <div class="addDocumentButton">
                            <tiles:insert definition="profileButton" flush="false">
                                <tiles:put name="commandText" value="Добавить СНИЛС"/>
                                <tiles:put name="viewType" value="addSNILSButton"/>
                                <tiles:put name="onclick" value="win.open('addIdetnifierBasketSNILS');"/>
                            </tiles:insert>
                        </div>
                    </div>
                    <div class="descriptionTitle">Добавьте номер СНИЛС первым</div>
                    <div class="descriptionText">Совсем скоро оплата государственных услуг станет простой и удобной</div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <tiles:insert definition="windowLight" flush="false">
        <tiles:put name="id" value="addIdetnifierBasketSNILS"/>
        <tiles:put name="styleClass" value="addIdetnifierBasket"/>
        <tiles:put name="loadAjaxUrl" value="${editIdentifierUrl}?documentType=SNILS"/>
        <tiles:put name="closeCallback" value="reloadProfile"/>
        <tiles:put name="color" value="greenBorder"/>
    </tiles:insert>
</c:if>
<c:set var="needAddDL" value="${!hasDL && accessDL}"/>
<c:if test="${needAddDL}">
    <tiles:insert definition="windowLight" flush="false">
        <tiles:put name="id" value="addIdetnifierBasketDL"/>
        <tiles:put name="styleClass" value="addIdetnifierBasket"/>
        <tiles:put name="loadAjaxUrl" value="${editIdentifierUrl}?documentType=DL"/>
        <tiles:put name="closeCallback" value="reloadProfile"/>
        <tiles:put name="color" value="lilacBorder"/>
    </tiles:insert>
</c:if>
<c:set var="needAddRC" value="${!hasRC && accessRC}"/>
<c:if test="${needAddRC}">
    <tiles:insert definition="windowLight" flush="false">
        <tiles:put name="id" value="addIdetnifierBasketRC"/>
        <tiles:put name="styleClass" value="addIdetnifierBasket"/>
        <tiles:put name="loadAjaxUrl" value="${editIdentifierUrl}?documentType=RC"/>
        <tiles:put name="closeCallback" value="reloadProfile"/>
        <tiles:put name="color" value="lilacBorder"/>
    </tiles:insert>
</c:if>
<c:set var="needAddINN" value="${!hasINN && accessINN}"/>
<c:if test="${needAddINN}">
    <tiles:insert definition="windowLight" flush="false">
        <tiles:put name="id" value="addIdetnifierBasketINN"/>
        <tiles:put name="styleClass" value="addIdetnifierBasket"/>
        <tiles:put name="loadAjaxUrl" value="${editIdentifierUrl}?documentType=INN"/>
        <tiles:put name="closeCallback" value="reloadProfile"/>
        <tiles:put name="color" value="grayBorder"/>
    </tiles:insert>
</c:if>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="removeIdentifier"/>
    <tiles:put name="styleClass" value="removeIdentifier"/>
    <tiles:put name="closeCallback" value="reloadProfile"/>
</tiles:insert>
<c:if test="${!hasSNILS || needAddDL || needAddRC || needAddINN}">
    <div class="addDocOrIdent" onclick="win.open('addDocOrIdentBlock')">
        <div class="addDocOrIdentIcon"></div>
        <div class="addDocOrIdentText">ДОБАВИТЬ ДОКУМЕНТ<br/> ИЛИ ИДЕНТИФИКАТОР</div>
    </div>
</c:if>
<script type="text/javascript">
    function addDocument(type)
    {
        win.open('addIdetnifierBasket'+type);
        win.close('addDocOrIdentBlock');
    }
</script>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="addDocOrIdentBlock"/>
    <tiles:put name="styleClass" value="addDocOrIdentBlock"/>
    <tiles:put name="data">
    <div class="addDocOrIdentContainer">
        <div class="addDocOrIdentHeader">
            <div class="addDocOrIdentTitle">Добавить документ или идентификатор</div>
            <div class="addDocOrIdentDesc">Для добавления документа выберите его из списка и внесите свои данные.<br/>
            Созданные документы будут отображены на главной странице профиля</div>
        </div>
        <div class="documentContainer">
            <c:if test="${!hasSNILS}">
                <div class="personSNILS" onclick="addDocument('SNILS');">
                    <div class="documentIcon"></div>
                    <div class="documentTitle">СТРАХОВОЕ СВИДЕТЕЛЬСТВО</div>
                </div>
            </c:if>
            <c:if test="${needAddDL}">
                <div class="personDL" onclick="addDocument('DL');">
                    <div class="documentIcon"></div>
                    <div class="documentTitle"><c:out value="${form.ident['DL'].name}"/></div>
                </div>
            </c:if>
            <c:if test="${needAddRC}">
                <div class="personRC" onclick="addDocument('RC');">
                    <div class="documentIcon"></div>
                    <div class="documentTitle"><c:out value="${form.ident['RC'].name}"/></div>
                </div>
            </c:if>
            <c:if test="${needAddINN}">
                <div class="personINN empty" onclick="addDocument('INN');">
                    <div class="documentTitle"><c:out value="${form.ident['INN'].name}"/></div>
                </div>
            </c:if>
        </div>
    </div>
    </tiles:put>
</tiles:insert>
