<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%-- текстовки для кнопок --%>
<c:set var="rollBackButtonName"><bean:message bundle='imageFieldComponentBundle' key='button.rollBack'/></c:set>
<c:set var="rollBackButtonHelp"><bean:message bundle='imageFieldComponentBundle' key='button.rollBack.help'/></c:set>

<%--
    id                            - идентификатор компонента
    readonly                      - доступно ли для редактирования
    forJS                         - компонент встраивается javaScropt'ом
    defaultImageSource            - дефолтный источник ресурса
    discSourceSelectorVisible     - есть ли возможность выбирать ресурс с диска
    externalSourceSelectorVisible - есть ли возможность выбирать внешний ресурс
    maxSize                       - максимальный размер для поля
--%>
<%-- типы источников --%>
<c:set var="externalSourceKind" value="EXTERNAL"/>     <%-- с внешнего ресурса --%>
<c:set var="discSourceKind"     value="DISC"/>         <%-- с диска --%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%-- вычисляем названия необходимых полей --%>
<c:set var="imageSourceSelectorFieldName"         value="imageSourceKind${id}"/>
<c:set var="imageDiscSourceSelectorFieldName"     value="imageDiscSource${id}"/>
<c:set var="imageNameDiscSourceSelectorFieldName" value="imageNameDiscSource${id}"/>
<c:set var="imageExternalSourceSelectorFieldName" value="imageExternalSource${id}"/>

<%-- вытаскиваем значения необходимых полей --%>    
<c:set var="imageSourceSelectorFieldValue"         value="${form.fields[imageSourceSelectorFieldName]}"/>
<c:set var="imageNameDiscSourceSelectorFieldValue" value="${form.fields[imageNameDiscSourceSelectorFieldName]}"/>

<%-- определяем тип ресурса --%>
<c:set var="isDiscSource"     value="${imageSourceSelectorFieldValue eq discSourceKind}"/>
<c:set var="isExternalSource" value="${imageSourceSelectorFieldValue eq externalSourceKind}"/>

<c:set var="sourceKind">
    <c:choose>
        <c:when test="${isDiscSource}">${discSourceKind}</c:when>
        <c:when test="${isExternalSource}">${externalSourceKind}</c:when>
        <c:otherwise>${defaultImageSource}</c:otherwise>
    </c:choose>
</c:set>
<%-- радиобаттоны для управления типом источника --%>
<c:choose>
    <c:when test="${discSourceSelectorVisible and externalSourceSelectorVisible}">
        <div>
            <input
                    <c:if test="${discSourceKind eq sourceKind}">checked="checked"</c:if>
                    type="radio" name="field(${imageSourceSelectorFieldName})" id="${imageSourceSelectorFieldName}1" value="${discSourceKind}"
                    <c:if test="${readonly}">disabled="true"</c:if>
                    onclick="changeImageSource('${id}', '${discSourceKind}');"/>&nbsp;
            <label for="${imageSourceSelectorFieldName}1"><bean:message bundle='imageFieldComponentBundle' key='label.source.kind.selector.DISC'/></label>
            <input <c:if test="${externalSourceKind eq sourceKind}">checked="checked"</c:if>
                   type="radio" id="${imageSourceSelectorFieldName}2" name="field(${imageSourceSelectorFieldName})" value="${externalSourceKind}"
                   <c:if test="${readonly}">disabled="true"</c:if> 
                   onclick="changeImageSource('${id}', '${externalSourceKind}');"/>&nbsp;
            <label for="${imageSourceSelectorFieldName}2"><bean:message bundle='imageFieldComponentBundle' key='label.source.kind.selector.EXTERNAL'/></label>
        </div>
    </c:when>
    <c:otherwise>
        <html:hidden property="field(${imageSourceSelectorFieldName})" value="${sourceKind}"/>
    </c:otherwise>
</c:choose>
<%-- запоминаем менялась ли картинка --%>
<html:hidden property="field(setNewFile${id})" styleId="setNewFileField${id}"/>

<c:set var="isImageAttached" value="${not empty imageNameDiscSourceSelectorFieldValue}"/>

<div id="discImageSelectorArea${id}"
        <c:if test="${not discSourceSelectorVisible or isExternalSource or sourceKind ne discSourceKind}">
            style="display:none;"
        </c:if>
        >
    <div id="notAttachedDiscImageSelectorArea${id}" style="${isImageAttached ? 'display:none;' : ''}width:430px;">
            <html:file property="images(${imageDiscSourceSelectorFieldName})" size="${maxSize-12}" disabled="${readonly}" styleId="imageFileField${id}" onchange="onChangeFileInput('${id}');" styleClass="floatRight"/>
            <div id="cancelButton${id}" style="display: none;" class="floatRight">
                <div style="display:inline-block;" class="floatRight">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="imageFieldComponentBundle"/>
                        <tiles:put name="onclick" value="clearFileInput('${id}');"/>
                        <tiles:put name="viewType" value="buttonGrayNew"/>
                    </tiles:insert>
                </div>
            </div>


            <input id="rollBackButton${id}"
                   type="button"
                   class="buttWhite"
                   style="<c:if test="${not isImageAttached}">display:none;</c:if>"
                   onclick="rollBackFileInput('${id}');"
                   value="${rollBackButtonName}"
                   alt="${rollBackButtonHelp}"
                   <c:if test="${readonly}">
                       disabled="disabled"
                   </c:if>
                   />
    </div>
    <c:if test="${isImageAttached}">
        <div id="attachedDiscImageSelectorArea${id}" class="floatInput">
            <input type="text" readonly="true" name="field(${imageNameDiscSourceSelectorFieldName})" value="${imageNameDiscSourceSelectorFieldValue}" size="${maxSize}"/>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.newAttachFile"/>
                <tiles:put name="commandHelpKey" value="button.newAttachFile.help"/>
                <tiles:put name="bundle" value="imageFieldComponentBundle"/>
                <tiles:put name="onclick" value="newAttachFileInput('${id}');"/>
                <tiles:put name="viewType" value="buttonGrayNew"/>
            </tiles:insert>
        </div>
    </c:if>
</div>
<div id="externalImageSelectorArea${id}"
        <c:if test="${not externalSourceSelectorVisible or isDiscSource or sourceKind ne externalSourceKind}">
            style="display:none;"
        </c:if>
        >
    <html:text property="field(${imageExternalSourceSelectorFieldName})" disabled="${readonly}" styleId="imageUrlField${id}" size="${maxSize}" maxlength="250"/>
</div>
<c:if test="${not readonly && not forJS}">
    <script type="text/javascript">
        $(document).ready(function(){
            addImage(new jsImageObject('${id}', '${imageSourceSelectorFieldValue}', ${maxSize-12}));
        });
    </script>
</c:if>