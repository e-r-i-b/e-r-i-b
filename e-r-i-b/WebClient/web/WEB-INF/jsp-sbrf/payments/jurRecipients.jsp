<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>

<html:form action="/private/async/jurPayment/recipients">
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${not empty form.fields.messages}"><html:hidden styleId="messageList" property="field(messages)"/></c:if>
    <c:if test="${not empty form.fields.error}"> <html:hidden styleId="errorList" property="field(error)"/></c:if>
    <c:if test="${not empty form.fields.inactiveESMessages}"><html:hidden styleId="inactiveESMessageList" property="field(inactiveESMessages)"/></c:if>
    <c:if test="${not empty form.nextURL}"><input type="hidden" id="nextURL" value="${phiz:calculateActionURL(pageContext, form.nextURL)}" /> </c:if>
     <%--div с id нужен для опознания того что это та страница которую мы ждали, а не допустим страница входа в систему--%>
     <%--если мы долго ничего не нажимали--%>
    <div id="title-jur-recipients"></div>
    <c:choose>
        <c:when test="${not empty form.externalProviders}">
            <div class="title">Выбор получателя</div>
            <div class="provHeader">По введенным реквизитам найдено несколько получателей. Для продолжения перевода средств выберите
            необходимого получателя и нажмите кнопку &laquo;Продолжить&raquo; для продолжения процесса оплаты.
            </div>
            <c:forEach items="${form.externalProviders}" var="provider">
                <div>
                    <html:radio property="field(externalReceiverId)" value="${provider.synchKey}"><c:out value="${provider.name}"/></html:radio>
                </div>
            </c:forEach>
            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="win.close('providersList');"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.next"/>
                    <tiles:put name="commandHelpKey" value="button.next"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                </tiles:insert>
                <div class="clear"></div>
            </div>

        </c:when>
        <c:when test="${not empty form.serviceProviders}">
            <html:hidden property="field(receiverId)" styleId="receiverId"/>
            <div class="title">Обратите внимание</div>
            <div class="provHeader">
                По указанным реквизитам найдено несколько получателей. Для продолжения операции выберите нужного получателя перевода.
            </div>
            <div>
                <div class="filterInfo">
                    <span class="bold">Номер счета: </span><span class="filterInfoData">${form.fields.receiverAccount}</span>
                    <span class="bold">ИНН: </span><span class="filterInfoData">${form.fields.receiverINN}</span>
                    <span class="bold">БИК: </span><span class="filterInfoData">${form.fields.receiverBIC}</span></br>
                    <span class="bold">Счет списания: </span><span class="filterInfoData">${form.fields.fromRes}</span>
                    <a href="#" class="providerTitle" onclick="win.close('providersList');return false;">изменить реквизиты</a>
                </div>
                <div class="triangle bottomFilterInfo"></div>
            </div>
            <script type="text/javascript">
                function next(id)
                {
                    $("#receiverId").val(id);
                    var button = new CommandButton("button.next", "");
                    button.click();
                }

                var providerZIndex = 50;
                /* Проставляем z-index, чтобы корректно отображалось в ie6,7 */
                function setZIndex(id)
                {
                    var parent = document.getElementById('provider' + id);
                    var otherRegions = document.getElementById('otherRegions' + id);
                    $(parent).css('z-index', providerZIndex);
                    if (otherRegions != undefined)
                        $('#otherRegions'+id).css('z-index', providerZIndex);
                    providerZIndex--;
                }

                function pagination(current, next)
                {
                    $("#regions"+current).hide();
                    $("#regions"+next).show();
                    $("#page"+current).hide();
                    $("#page"+next).show();
                }
                function hideOrShowSearchResult(show){
                    if (show)
                    {
                        $(".otherRegionsAreaHide").show();
                        $(".otherRegionsAreaShow").hide();
                        $("#otherRegion").show();
                    }
                    else
                    {
                        $(".otherRegionsAreaHide").hide();
                        $(".otherRegionsAreaShow").show();
                        $("#otherRegion").hide();
                    }
                }
            </script>
            <c:set var="currRegion" value=""/>
            <c:set var="providerCountInBlock" value="0"/>
            <c:set var="providerCount" value="0"/>
            <c:set var="pageCount" value="1" />
            <c:set var="personRegion" value="${phiz:getPersonRegion()}"/>
            <c:set var="homeRegion" value="Все регионы"/>
            <c:if test="${personRegion != null}">
                 <c:set var="homeRegion" value="${personRegion.name}"/>
            </c:if>
            <c:set var="stringCount" value="0"/>
            <c:set var="searchType"     value=""/>
            <c:set var="regions"     value="${form.providerRegions}"/>
            <div class="regions" id="regions1">
               <c:forEach items="${form.serviceProviders}" var="item" varStatus="stat">
                   <c:set var="category"        value="${item[0]}"/>
                   <c:set var="category_name"   value="${item[1]}"/>
                   <c:set var="groupId"         value="${item[2]}"/>
                   <c:set var="groupName"       value="${item[3]}"/>
                   <c:set var="serviceId"       value="${item[4]}"/>
                   <c:set var="serviceName"     value="${item[5]}"/>

                   <c:set var="region" value="${item[10]}"/>
                   <c:if test="${region == null}">
                       <c:set var="region" value="${homeRegion}"/>
                   </c:if>
                   <%--про картинки--%>
                   <c:set var="imageId" value="${item[8]}"/>
                   <c:choose>
                       <c:when test="${not empty imageId}">
                           <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                           <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                       </c:when>
                       <c:otherwise>
                           <c:set var="image" value="${imagePath}/defaultProviderIcon.jpg" />
                       </c:otherwise>
                   </c:choose>
                   <c:set var="countStringInRegion" value="${stringCount+providerCountInBlock/2}"/>
                   <c:if test="${countStringInRegion == 4 || (currRegion != region && countStringInRegion ==3.5)}">
                               <div class="clear"></div>
                               <div class="buttonsArea" id="page${pageCount}" <c:if test="${pageCount != 1}">style="display: none;"</c:if> >
                               <c:choose>
                                   <c:when test="${pageCount == 1}">
                                       <div class="inactivePaginLeftArrow"></div>
                                   </c:when>
                                   <c:otherwise>
                                       <a class="active-arrow" href="#" onclick="pagination(${pageCount},${pageCount-1});"><div class="activePaginLeftArrow"></div></a>
                                   </c:otherwise>
                               </c:choose>
                               <a class="active-arrow" href="#" onclick="pagination(${pageCount},${pageCount+1});"><div class="activePaginRightArrow"></div></a>
                           </div>
                           </div>
                       </div>
                       <c:set var="pageCount" value="${pageCount+1}"/>
                       <c:set var="currRegion" value=""/>
                       <c:set var="stringCount" value="0"/>
                       <c:set var="providerCountInBlock" value="0"/>
                       <div class="regions" id="regions${pageCount}" style="display: none;">
                   </c:if>
                   <c:if test="${providerCountInBlock % 2 == 0}">
                       <div class="clear"></div>
                   </c:if>
                   <c:if test="${item[11] != searchType && item[11] == 'allRegion'}">
                       <c:set var="searchType"     value="${item[11]}"/>
                       <div class="clear"></div>
                       <div class="otherRegionsAreaShow" id="regionListOpen">
                           <div class="greenTitle" onclick="hideOrShowSearchResult(true);"> Показать результаты без учета региона</div>
                       </div>
                       <div class="otherRegionsAreaHide" style="display:none;">
                           <div class="greenTitle hide" onclick="hideOrShowSearchResult();">Скрыть результаты без учета региона</div>
                       </div>
                       <c:if test="${providerCountInBlock % 2 == 1}">
                           <c:set var="providerCountInBlock" value="${providerCountInBlock+1}"/>
                       </c:if>
                       <c:set var="stringCount" value="${stringCount + providerCountInBlock/2}"/>
                       <c:set var="providerCountInBlock" value="0"/>
                       <c:set var="currRegion" value=""/>
                       <div id="otherRegion" style="display: none;">
                   </c:if>

                   <c:if test="${region != currRegion}">
                       <c:set var="currRegion" value="${region}"/>
                       <div class="clear"></div>
                       <div class="titleRegion">
                           <span class="bold">${currRegion}</span>
                       </div>
                       <c:set var="providerCountInBlock" value="0"/>
                   </c:if>
                   <c:set var="providerCount" value="${providerCount + 1}"/>
                   <c:set var="providerCountInBlock" value="${providerCountInBlock + 1}"/>
                   <c:set var="otherRegions" value="${regions[item[6]]}"/>
                    <tiles:insert definition="providerTemplate" flush="false">
                        <tiles:put name="id" value="${item[6]}"/>
                        <tiles:put name="providerName" value="${item[7]}"/>
                        <tiles:put name="idForDiv" value="${item[6]}_${providerCount}"/>
                        <tiles:put name="KPP" value="${item[9]}"/>
                        <tiles:put name="image" value="${image}"/>
                        <tiles:put name="region" value="${empty otherRegions[0] ? 'Все регионы' : otherRegions[0]}"/>
                        <tiles:put name="otherRegions" value="${otherRegions[1]}"/>
                        <tiles:put name="service">
                            <c:if test="${not empty category_name}">
                                <span class="serviceGroup">
                                     ${category_name}&rarr;
                                </span>
                            </c:if>
                            <c:if test="${not empty groupName}">
                                <span class="serviceGroup">
                                    ${groupName}&rarr;
                                </span>
                            </c:if>
                            <c:if test="${not empty serviceName}">
                                <span class="serviceGroup">
                                    ${serviceName}
                                </span>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </c:forEach>

            <%--</div>--%>
            <div class="clear"></div>
            <div class="buttonsArea" id="page${pageCount}" style="display: none;">
                <a class="active-arrow" href="#" onclick="pagination(${pageCount},${pageCount-1});"><div class="activePaginLeftArrow"></div></a>
                <div class="inactivePaginRightArrow"></div>
            </div>
            </div>
            <c:set var="isCardResource" value="${fn:startsWith(form.fromResource, 'card')}"/>
            <c:if test="${isCardResource || phiz:impliesService('ExternalJurAccountPayment')}">

                Если в списке нет нужного получателя, введите реквизиты вручную, щелкнув по ссылке
                <c:set var="onClickAction">createCommandButton('button.payByRequisites','Оплатить по реквизитам').click('', false)</c:set>
                <a href="#" class="boldLink" onclick="${onClickAction}">
                    «оплатить по реквизитам».
                </a>
            </c:if>
        </c:when>
    </c:choose>
</html:form>
