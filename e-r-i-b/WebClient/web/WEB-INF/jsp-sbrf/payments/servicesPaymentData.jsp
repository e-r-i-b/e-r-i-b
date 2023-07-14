<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="listFormName" value="${form.metadata.name}"/>
<c:set var="currentPage" value="${form.searchPage}"/>
<c:set var="itemsPerPage" value="${form.itemsPerPage}"/>
<c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="createLongOffer" value="${param['createLongOffer']}"/>
<c:set var="phoneField" value="${param['phoneFieldParam']}"/>
<c:set var="isCopyOrCreatedDoc" value="${param[copying] or not empty form.id or not empty templateId}"/>
<c:set var="socialNetProviderId" value="${form.socialNetProviderId}"/>
<c:set var="socialNetUserId" value="${form.socialNetUserId}"/>
<c:set var="socialNetPaymentFieldName" value="${form.socialNetPaymentFieldName}"/>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/servicesProviderData.js"></script>
<script type="text/javascript">
    var pf;
    $(document).ready(function(){
        pf = new ServicesPaymentForm(${isUseProfileDocuments == 'true'}, '${socialNetUserId}');
    });

    function saveFavourite(event)
    {
        setEmptyAction();
        return confirm('<bean:message key="button.saveFavourite" bundle="favouriteBundle"/>?');
    }

    function initialize()
    {
        <c:if test="${not empty form.providers}">
            var firstID ="${form.recipient}";
            <c:forEach var="provider" items="${form.providers}">
                <c:choose>
                    <c:when test="${not empty provider.imageHelpId}">
                        <c:set var="imageData" value="${phiz:getImageById(provider.imageHelpId)}"/>
                        <c:set var="imageHelpForProvider" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="imageHelpForProvider" value="" />
                    </c:otherwise>
                </c:choose>
                <c:set var="accessProviderCurrentRegion">${provider.federal ||  phiz:accessProviderCurrentRegion(provider.id)}</c:set>
                pf.addProvider(new pf.Provider(${provider.id}, "${phiz:escapeForJS(provider.name, true)}",  "${provider.accountType}", ${provider.kind == 'B' && provider.autoPaymentSupported}, [], ${accessProviderCurrentRegion}, ${phiz:isIQWProviderBySynchKey(provider.synchKey)}, '${imageHelpForProvider}', ${phiz:isITunesProvider(provider.synchKey, provider.code)}, ${(not empty socialNetUserId) and (not empty socialNetProviderId) and provider.synchKey == socialNetProviderId}));
            </c:forEach>
            <%-- Обновляем поставщикой доп. полями --%>
            addFieldDescription();

            if (firstID == "")
                firstID = ${form.providers[0].id};

            <c:if test="${empty form.recipient}">
                setElement("recipient", firstID);
            </c:if>

            <%-- заполняем массив счетами и картам --%>
            <c:forEach items="${form.chargeOffResources}" var="resource" >
                <c:set var="rest" value="${resource.rest != null ? resource.rest.decimal : 'null'}"/>
                <c:choose>
                    <c:when test="${resource['class'].name == 'com.rssl.phizic.business.resources.external.CardLink'}">
                        pf.addFromResource(new pf.FromResource('card', "${resource.code}", "${phiz:getCutCardNumber(resource.number)} [${phiz:escapeForJS(resource.name, true)}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}", ${rest}));
                    </c:when>
                    <c:otherwise>
                        pf.addFromResource(new pf.FromResource('account',"${resource.code}","${resource.number} [${phiz:escapeForJS(resource.name, true)}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}", ${rest}));
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            pf.showProvider(firstID);

            <%-- Нужно ли заполнять значениями с формы --%>
            <c:set var="isSetExistValues" value="${!form.fromStart || isCopyOrCreatedDoc || (not empty param['autoId'])}"/>
            <c:forEach var="field" items="${form.metadata.form.fields}">
                <c:set var="fieldKey" value="field(${field.name})"/>
                <c:set var="fieldValue" value="${form.fields[field.name]}"/>

                <%-- Если нужно заполнять и есть чем заполнять ИЛИ это поле заполнится номером телефона --%>
                <c:set var="containsField" value="${phiz:containsKey(form.fields, field.name)}"/>
                <c:if test="${(containsField && isSetExistValues) || fieldKey == phoneField}">
                    if ($('*[name=${fieldKey}]').length > 0)
                    {
                        <c:choose>
                            <c:when test="${fieldKey == phoneField}">
                                setElement("${fieldKey}", "${phiz:getHiddenPhoneNumber()}");
                                $('*[name=${fieldKey}]').attr('class', 'masked-phone-number');
                            </c:when>
                            <c:otherwise>
                                <c:set var="escapeFieldValue" value="${phiz:escapeForJS(fieldValue, true)}"/>
                                var inputElement = getElement("field(${field.name})");
                                if(inputElement.type == 'checkbox')
                                    inputElement.checked = "${escapeFieldValue}" == inputElement.value;
                                else
                                {
                                    <c:if test="${form.maskedFields[field.name]}">
                                        $('*[name=${fieldKey}]').attr('class', 'masked-phone-number');
                                    </c:if>
                                    inputElement.value = "${escapeFieldValue}";
                                }
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${field.name == socialNetPaymentFieldName and escapeFieldValue != socialNetUserId}">
                            pf.otherUserSocialAccount = '${escapeFieldValue}';
                            pf.selectUserForSocial(false, '${field.name}');
                        </c:if>
                    }
                </c:if>
            </c:forEach>
            pf.postUpdateFieldValue();
        <c:if test="${form.id == null && empty templateId && !form.needSelectProvider && phiz:size(form.providers) > 1}">
             pf.showEmptyProvider();
        </c:if>
        </c:if>
    }

    function addFieldDescription()
    {
        var holderId;
        var provider;
        var arrayListValues;

        <c:set var="providersMap" value="${form.providersMap}"/>
        <c:forEach var="field" items="${form.fieldsDescription}">
            provider = pf.getProviderById(${field.holderId});
            arrayListValues = [];
            <logic:iterate id="value" name="field" property="listValues" indexId="i">
                <c:set var ="valueTrim" value="${fn:trim(value)}"/>
                arrayListValues[${i}]=("${phiz:escapeForJS(valueTrim, true)}");
            </logic:iterate>
            <%-- Массив полей --%>
            <%-- Временное решение, возникшее из-за метода com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation.getProviderAllServicesFields.
             В нем для автоплатежей полязаполняются для всех услуг, а не для тех, которые поддерживают автоплатеж.--%>
            if (provider)
            {
                var fields  = provider.getFields();
                <%-- добавляем поле в конец списка --%>
                <c:set var="currProvider" value="${providersMap[field.holderId]}"/>
                <c:set var="defaultProviderValue" value="${phiz:getDefaultValueProviderField(currProvider.synchKey, currProvider.code, field.externalId)}"/>

                <c:set var="isMaskValue" value="false"/>
                <c:set var="defaultValue" value="${defaultProviderValue != null ? defaultProviderValue : field.defaultValue}"/>
                <c:if test="${phiz:isRequireMaskingProvider(currProvider.synchKey, currProvider.code) && phiz:isMaskFieldDescriptionValue(field, defaultValue)}">
                    <c:set var="isMaskValue" value="true"/>
                    <c:set var="defaultValue" value="${phiz:getMaskFieldDescriptionValue(field, defaultValue)}"/>
                </c:if>
                provider.addField(new provider.Field(
                        "${field.id}",
                        "${phiz:escapeForJS(field.name, true)}",
                        "${phiz:escapeForJS(field.description, true)}",
                        "${field.type}",
                        "${phiz:escapeForJS(defaultValue, true)}",
                        "${field.externalId}",
                        "${field.maxLength}",
                        "${field.holderId}",
                        ${field.required},
                        "${phiz:escapeForJS(field.hint, true)}",
                        ${(!createLongOffer && field.visible) || (field.key && field.visible)},
                        ${field.editable},
                        arrayListValues,
                        ${fieldDisabled && !field.mainSum && (field.key || field.saveInTemplate)},
                        "${field.extendedDescId}",
                        ${isMaskValue},
                        "${field.businessSubType}",
                        ${field.externalId == socialNetPaymentFieldName}));
            }
        </c:forEach>
    }

    <%-- Функция пейджинга страницы --%>
    function changeSearchPage(page)
    {
        var searchPage = document.getElementById("searchPage");
        searchPage.value = page;
        new CommandButton('button.search').click();
    }

    $(document).ready(function()
    {
        <%-- регистрируем слушателя изменения региона в шапке --%>
        var region = getRegionSelector('regionsDiv');
        if (!$.isEmptyObject(region))
        {
            region.addListener(function(){
                checkProvidersRegion(providerIds, function(map){
                    pf.updateAccessProviderRegion(map);
                });
            });
        }
    });
</script>
<tiles:insert attribute="header"/>
<div id="paymentStripe${byCenter}">
    ${stripe}
    <div class="clear"></div>
</div>

<%-- ********************************************************************* --%>

<html:hidden name="form" property="service" />
<html:hidden name="form" property="back" />
<html:hidden name="form" property="id" />
<html:hidden name="form" property="orderId" />
<html:hidden name="form" property="personal"/>
<html:hidden name="form" property="phoneFieldParam" styleId="phoneFieldParam"/>
<html:hidden name="form" property="searchPage" styleId="searchPage"/>
<html:hidden name="form" property="markReminder"/>

<c:set var="imgCount" value="0"/>
<c:forEach var="provider" items="${form.providers}">
    <c:if test="${not empty provider.imageId}">
        <c:set var="imgCount" value="1"/>
    </c:if>
</c:forEach>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/providerImageHelp.js"></script>
<div id="imageHelpProviderHeaderRow" class="fullWidth form-row paymentImageHelp dashedBorder" style="display:none">
   <div class="paymentLabel">
     <span class="paymentTextLabel">
         <div class="imageHelpLabel">Образец квитанции:</div>
     </span>
   </div>
   <div class="paymentValue" id="imageHelpProviderHeader">
       <input type="hidden" id="imageHelpSrc" value=""/>
       <div class="imageHelpTitleContainer">
           <a class="imageHelpTitle imageHelpHeaderControl closed" onclick="paymentImageHelpHeaderAction(); return false;" href="#">показать</a>
       </div>
       <div class="imageHelp" style="display:none"></div>
   </div>
   <div class="clear"></div>
</div>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="providerImageHelp"/>
    <tiles:put name="data">
        <div id="imageHelpProvider" class="imageHelp"></div>
    </tiles:put>
</tiles:insert>

${paymentFieldsHtml}
<div id="saveTemplateRow">
    <div class="paymentLabel">
        <span class="paymentTextLabel">&nbsp;</span>
    </div>
    <div class="paymentValue">
        <div class="paymentInputDiv bold">
           ${template}
        </div>
    </div>
    <div class="clear"></div>
</div>

<%-- **************************** Кнопки ********************************* --%>

<div class="buttonsArea">
    ${cancelButton}
    ${removeButton}
    ${nextButton}
</div>
${backToServicesButton}

