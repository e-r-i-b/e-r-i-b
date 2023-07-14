<%--
  Created by IntelliJ IDEA.
  User: Nechaeva
  Date: 08.005.2010
  Time: 15:47:52
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<c:set var="helpFavourite" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/favourite/list/null')}"/>
<c:set var="helpFavouriteReceivers" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/receivers/list')}"/>
<c:set var="faqLink" value="${phiz:calculateActionURL(pageContext, '/faq.do')}"/>
<c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>
<c:set var="autoPaymentsUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/favourite/listAutoPaymentsForPersonalMenu')}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="autoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/favourite/list/AutoPayments.do")}</c:set>
<c:set var="addAutoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/autopayment/select-category-provider.do")}</c:set>
<c:set var="mobileItemsMovedInfoClosed" value="${phiz:isMobileItemsMovedClosed()}"/>
<!--Шаблоны платежей и избранные операции-->
<div class="personalMenu" codename="personalMenu">
    <c:set var="bundleName" value="commonBundle"/>
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="grayGradient css3"/>
        <tiles:put name="title" value="Личное меню"/>
        <tiles:put name="data">
            <%--Ссылки в личном меню --%>
            <div id="favouriteLinks">
                <%@ include file="personalMenuFavouriteLink.jsp"%>
            </div>
            <%--Мои финансы--%>
            <c:set var="imageList" value="<img src='${imagePath}/newGroup.png' class='newGroup'/>"/>
            <c:set var="imageItemList" value="<img src='${imagePath}/newItem.png' class='newItemList'/>"/>
            <c:set var="personalFinanceUsed" value="${phiz:isPersonalFinanceEnabled()}"/>
            <c:set var="operationsServiceAvailable" value="${phiz:impliesService('FinanceOperationsService')}"/>
            <c:set var="categoriesServiceAvailable" value="${phiz:impliesService('CategoriesCostsService')}"/>
            <c:set var="targetsServiceAvailable" value="${phiz:impliesService('TargetsService')}"/>
            <c:set var="addFinanceOperationServiceAvailable" value="${phiz:impliesService('AddFinanceOperationsService')}"/>
            <c:set var="clientPfpEditServiceAvailable" value="${phiz:hasAccessToPFP()}"/>
            <c:set var="financeCalendarServiceAvailable" value="${phiz:impliesService('FinanceCalendarService')}"/>
            <c:set var="someAlfUsed" value="${((operationsServiceAvailable || categoriesServiceAvailable) && (personalFinanceUsed || addFinanceOperationServiceAvailable)) || targetsServiceAvailable}"/>
            <c:if test="${ phiz:impliesService('ViewFinance') || someAlfUsed || clientPfpEditServiceAvailable || financeCalendarServiceAvailable}">
                <div class="personalMenuWithTitleItem">
                    <div>
                        <phiz:linksList num="0" styleClass="underlined" listSourceName="FinanceLinkList" title="Мои финансы" image="${imageList}" titleStyleClass="linksListTitle">
                            <c:if test="${categoriesServiceAvailable && (personalFinanceUsed || addFinanceOperationServiceAvailable)}">
                                <c:set var="titleTag" value="<span>Расходы</span>"/>
                                <c:choose>
                                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                                        <c:set var="url">${phiz:getWebAPIUrl("operation.categories")}</c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/finances/operationCategories")}</c:set>
                                    </c:otherwise>
                                </c:choose>
                                <phiz:linksListItem title="${titleTag}" href="${url}" image="${imageItemList}" onClick="return redirectResolved();"/>
                            </c:if>
                            <c:if test="${phiz:impliesService('ViewFinance')}">
                                <c:set var="titleTag" value="<span>Доступные средства</span>"/>
                                <c:choose>
                                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                                        <c:set var="url">${phiz:getWebAPIUrl("graphics.finance")}</c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/graphics/finance")}</c:set>
                                    </c:otherwise>
                                </c:choose>
                                <phiz:linksListItem title="${titleTag}" href="${url}" onClick="return redirectResolved();"/>
                            </c:if>
                            <c:if test="${targetsServiceAvailable}">
                                <c:set var="titleTag" value="<span>Цели</span>"/>
                                <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/finances/targets/targetsList")}</c:set>
                                <phiz:linksListItem title="${titleTag}" href="${url}" image="${imageItemList}" onClick="return redirectResolved();"/>
                            </c:if>
                            <c:if test="${financeCalendarServiceAvailable}">
                                <c:set var="titleTag" value="<span>Календарь</span>"/>
                                <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/finances/financeCalendar")}</c:set>
                                <phiz:linksListItem title="${titleTag}" href="${url}" image="${imageItemList}" onClick="return redirectResolved();"/>
                            </c:if>
                            <c:if test="${operationsServiceAvailable && (personalFinanceUsed || addFinanceOperationServiceAvailable)}">
                                <c:set var="titleTag" value="<span>Операции</span>"/>
                                <c:choose>
                                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                                        <c:set var="url">${phiz:getWebAPIUrl("operations")}</c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/finances/operations")}</c:set>
                                    </c:otherwise>
                                </c:choose>
                                <phiz:linksListItem title="${titleTag}" href="${url}" onClick="return redirectResolved();"/>
                            </c:if>
                            <c:if test="${clientPfpEditServiceAvailable}">
                                <c:set var="titleTag" value="<span>Финансовое планирование</span>"/>
                                <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/pfp/edit")}</c:set>
                                <phiz:linksListItem title="${titleTag}" href="${url}" onClick="return redirectResolved();"/>
                            </c:if>
                        </phiz:linksList>
                    </div>
                </div>
            </c:if>
            <%--Избранное в личном меню--%>
            <div class="personalMenuWithTitleItem">
                <c:set var="links" value="${phiz:getUserLinks()}"/>
                <c:set var="emptyLinks" value="${empty links}"/>
                <c:set var="urlForBusinessmanRegistration" value="${phiz:getBusinessmanRegistrationUrl()}"/>
                <div>
                    <phiz:linksList num="0" styleClass="underlined" listSourceName="favouriteLinkList" title="Избранное" titleStyleClass="linksListTitle">
                        <c:choose>
                            <c:when test="${emptyLinks}">
                                <c:set var="linksNote">
                                    <bean:message key="label.linksNote" bundle="favouriteBundle"/>.
                                </c:set>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${links}" var="link">
                                    <c:set var="title">
                                        <bean:write name='link' property="name"/>
                                    </c:set>
                                    <c:set var="titleTag" value="<span>${title}</span>"/>
                                    <c:set var="urlLink"><bean:write name='link' property="link"/></c:set>
                                    <phiz:linksListItem title="${titleTag}" href="${urlLink}" onClick="return redirectResolved();"/>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <c:set var="favouritesControl">${phiz:calculateActionURL(pageContext,"/private/favourite/list/favouriteLinks.do")}</c:set>
                        <c:set var="titleTag" value="<span>Управление избранным</span>"/>
                        <phiz:linksListItem title="${titleTag}" styleClass="favouriteControl" href="${favouritesControl}" onClick="return redirectResolved();"/>
                        <c:if test="${emptyLinks}">
                            <phiz:linksListCommonItem body="<li><span class=\"LinksListCommonText\">${linksNote}</span></li>"/>
                        </c:if>
                    </phiz:linksList>
                </div>
            </div>
            <%--Шаблоны в личном меню--%>
            <c:if test="${phiz:impliesService('FavouriteManagment')}">
                <div class="personalMenuWithTitleItem">
                    <div>
                        <phiz:linksList num="0" styleClass="underlined" listSourceName="templatesLinkList" title="Мои шаблоны" titleStyleClass="linksListTitle">
                            <c:set var="templates" value="${phiz:getTemplates()}"/>
                            <c:choose>
                                <c:when test="${empty templates}">
                                    <c:set var="templatesNote">
                                         Для того чтобы быстро и легко совершать операции, добавьте сюда шаблоны платежей. <a href="" class="green"
                                                                    onclick="openHelp('${helpFavouriteReceivers}'); return false;"><span>подробнее»</span></a>
                                     </c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${templates}" var="template">
                                        <c:if test="${template.activityInfo.availablePay}">
                                            <c:set var="title">
                                                <c:out value="${template.templateInfo.name}"/>
                                            </c:set>
                                            <c:set var="temp" value="${phiz:getTemplateLinkByTemplate(pageContext, template)}"/>
                                            <c:set var="titleTag" value="<span>${title}</span>"/>
                                            <phiz:linksListItem title="${titleTag}" href="${temp}" onClick="return personalMenuItemClick(event);"/>
                                        </c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            <c:set var="templateControl">${phiz:calculateActionURL(pageContext,"/private/favourite/list/PaymentsAndTemplates.do")}</c:set>
                            <c:set var="titleTag" value="<span>Управление шаблонами</span>"/>
                            <phiz:linksListItem title="${titleTag}" styleClass="templateAndPaymentControl" href="${templateControl}" onClick="return personalMenuItemClick(event);"/>
                            <c:if test="${empty templates}">
                                <phiz:linksListCommonItem body="<li><span class=\"LinksListCommonText\">${templatesNote}</span></li>"/>
                            </c:if>
                        </phiz:linksList>
                    </div>
                </div>
            </c:if>
            <%--Мобильный банк в личном меню--%>
            <c:if test="${phiz:impliesService('MobileBank') and not phiz:impliesServiceRigid('NewClientProfile')}">
                <div class="personalMenuWithTitleItem">
                    <div>
                        <phiz:linksList num="0" styleClass="underlined" listSourceName="mobileBankLinkList" title="Мобильный банк" titleStyleClass="linksListTitle">
                            <c:choose>
                                <c:when test="${isERMBConnectedPerson == true}">
                                    <c:set var="url" value="/private/mobilebank/ermb/main.do"/>
                                    <c:set var="titleTag" value="<span>Детали подключения</span>"/>
                                    <phiz:linksListItem title="${titleTag}" href="${phiz:calculateActionURL(pageContext,url)}" onClick="return personalMenuItemClick(event);"/>
                                    <c:set var="url" value="/private/mobilebank/ermb/history.do"/>
                                    <c:set var="titleTag" value="<span>История SMS-запросов</span>"/>
                                    <phiz:linksListItem title="${titleTag}" href="${phiz:calculateActionURL(pageContext,url)}" onClick="return personalMenuItemClick(event);"/>
                                    <c:set var="url" value="/private/mobilebank/ermb/templates.do"/>
                                    <c:set var="titleTag" value="<span>SMS-запросы и шаблоны</span>"/>
                                    <phiz:linksListItem title="${titleTag}" href="${phiz:calculateActionURL(pageContext,url)}" onClick="return personalMenuItemClick(event);"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="url" value="/private/mobilebank/main.do"/>
                                    <c:set var="titleTag" value="<span>Детали подключения</span>"/>
                                    <phiz:linksListItem title="${titleTag}" href="${phiz:calculateActionURL(pageContext,url)}" onClick="return personalMenuItemClick(event);"/>
                                    <c:set var="url" value="/private/mobilebank/payments/list/all.do"/>
                                    <c:set var="titleTag" value="<span>SMS-запросы и шаблоны</span>"/>
                                    <phiz:linksListItem title="${titleTag}" href="${phiz:calculateActionURL(pageContext,url)}" onClick="return personalMenuItemClick(event);"/>
                                </c:otherwise>
                            </c:choose>
                        </phiz:linksList>
                    </div>
                </div>
            </c:if>
            <%--Автоплатежи--%>
            <c:if test="${phiz:impliesService('AutoPaymentsManagment')}">
                <c:set var="stepAutoPayments">${phiz:getStepShowPersonalMenuLinkList('autoPaymentsLinkList')}</c:set>
                <div class="personalMenuWithTitleItem">
                    <div id="autoPayments" onclick='loadAutoPayments();'>
                        <c:if test="${phiz:isNeedToShowP2PNewMark()}">
                            <c:set var="autoPaymentsListImage" value="${imageList}"/>
                        </c:if>
                        <phiz:linksList num="0" styleClass="underlined" listSourceName="autoPaymentsLinkList" title="Мои автоплатежи" titleStyleClass="linksListTitle" image="${autoPaymentsListImage}">

                            <phiz:linksListItem title="<img src='${imagePath}/ajaxLoader.gif' alt='Loading...' title='Loading...' class='abstractLoader'>"/>
                            <c:set var="titleTag" value="<span>Подключить автоплатеж</span>"/>
                            <phiz:linksListItem title="${titleTag}" styleClass="templateAndPaymentControl" href="${addAutoPaymentsURL}" onClick="return redirectResolved();"/>
                            <c:set var="titleTag" value="<span>Управление автоплатежами</span>"/>
                            <phiz:linksListItem title="${titleTag}" styleClass="templateAndPaymentControl" href="${autoPaymentsURL}" onClick="return redirectResolved();"/>
                        </phiz:linksList>
                    </div>
                </div>
            </c:if>
        </tiles:put>
    </tiles:insert>
</div>

 <c:set var="abstractUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/loyalty.do')}"/>

<%--Форма отображения ошибки при попытке перехода на другой сайт (например: Бонусная программа "Спасибо от Сбербанка", "Стань предпринимателем")--%>
 <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="externalWebSiteErrorWindow"/>
        <%--<tiles:put name="loadAjaxUrl" value="${abstractUrl}"/>--%>
        <tiles:put name="data">
             <h2>Внимание!</h2>
            <%--дефолтное сообщение--%>
            <div id="externalWebSiteErrorMsg">Ошибка при попытке перейти на другой сайт.</div>
            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close"/>
                    <tiles:put name="bundle" value="pfrBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="win.close('externalWebSiteErrorWindow');"/>
                </tiles:insert>
            </div>
        </tiles:put>
 </tiles:insert>

<!-- Конец шаблонов платежей и избранных операций -->

<c:set var="mobileBankAccess" value="${phiz:impliesService('MobileBank')}"/>
<c:set var="mobileApplicationsAccess" value="${phiz:impliesService('ShowConnectedMobileDevicesService')}"/>

<c:if test="${phiz:impliesServiceRigid('NewClientProfile') and (mobileBankAccess or mobileApplicationsAccess) and not mobileItemsMovedInfoClosed}">
    <script type="text/javascript">
        function closeMobileItemsMovedInfo()
        {
            $('.b-popup-note').hide();
            callAjaxActionMethod('${phiz:calculateActionURL(pageContext,'/private/async/userprofile/profileNovelties')}', 'setMobileItemsMovedClosed');
        }
    </script>

    <div class="b-popup-note">
        <div class="popup-note_inner">
            <c:if test="${mobileBankAccess}">
                <a href="${phiz:calculateActionURL(pageContext,'/private/mobilebank/main')}">Мобильный банк</a>
            </c:if>
            <c:if test="${mobileBankAccess and mobileApplicationsAccess}">
                и
            </c:if>
            <c:if test="${mobileApplicationsAccess}">
                <a href="${phiz:calculateActionURL(pageContext,'/private/mobileApplications/view')}">Мобильные приложения</a>
            </c:if>
            <c:choose>
                <c:when test="${mobileBankAccess and not mobileApplicationsAccess}">
                    переехал на страницу вашего профиля
                </c:when>
                <c:otherwise>
                    переехали на страницу вашего профиля
                </c:otherwise>
            </c:choose>
        </div>
        <i class="popup-note_arr"></i>
        <a class="popup-note_close" title="Закрыть" onclick="closeMobileItemsMovedInfo()"></a>
    </div>
</c:if>

<script type="text/javascript">
    try
    {
        doOnLoad(function(){
            <!-- Если шаг раскрытия списка для автоплатежей > 0, то загружаем список автоплатежей -->
            <c:if test="${stepAutoPayments > 0}">
                loadAutoPayments();
            </c:if>
        });
    } catch (e) { }


    <%--Для IE до 7-ой включительно, чтобы не пробрасывалось событие onclick к родителям--%>
    function personalMenuItemClick(event)
    {
        event.cancelBubble = true;
        return redirectResolved();
    }

    function openBusinessmanRegistrationWindow(event)
    {
        // Для IE до 7-ой включительно, чтобы не пробрасывалось событие onclick к родителям
        event.cancelBubble = true;
        <c:if test="${not empty urlForBusinessmanRegistration}">
            var winparams = "resizable=1,menubar=0,toolbar=0,scrollbars=1";
            var pwin = openWindow(event, "${urlForBusinessmanRegistration}", "businessman", winparams);
            pwin.focus();
        </c:if>
    }

    function loadAutoPayments()
    {
        ajaxQuery(null, '${autoPaymentsUrl}', function(data){
            if (trim(data) != "")
            {
                var opened = $('[name=autoPaymentsLinkList]').attr('class').indexOf('active') != -1;
                $("#autoPayments").html(data);
                $("#autoPayments").removeAttr("onclick");
                if(opened)
                {
                    $('#autoPaymentsLinkList').slideDown('normal');
                    $('[name=autoPaymentsLinkList]').addClass('active');
                    $('[name=autoPaymentsLinkList]').parent().addClass('hide');
                }
            }
        }, null, false);
    }
</script>

