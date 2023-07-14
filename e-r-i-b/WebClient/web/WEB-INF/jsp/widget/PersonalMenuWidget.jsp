<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<tiles:importAttribute/>
<c:set var="helpFavourite" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/favourite/list/null')}"/>
<c:set var="helpFavouriteReceivers" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/receivers/list')}"/>
<c:set var="faqLink" value="${phiz:calculateActionURL(pageContext, '/faq.do')}"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="showTemplates" value="${form.showTemplates}"/>
<c:set var="allTemplates" value="${form.allTemplates}"/>
<c:set var="showFavouriteLinks" value="${form.showFavouriteLinks}"/>
<c:set var="allFavouriteLinks" value="${form.allFavouriteLinks}"/>
<c:set var="widget" value="${form.widget}"/>
<!--������� �������� � ��������� ��������-->

    <tiles:insert definition="widget" flush="false">
        <tiles:put name="digitClassname" value="widget.PersonalMenuWidget"/>
        <tiles:put name="cssClassname" value="PersonalMenuWidget personalMenu"/>
        <tiles:put name="borderColor" value="greenTop"/>
        <tiles:put name="sizeable" value="false"/>
        <tiles:put name="editable" value="true"/>

        <c:if test="${phiz:impliesService('ClientProfile')}">
            <tiles:put name="linksControl">
                <c:set var="setUpUrl">${phiz:calculateActionURL(pageContext, '/private/favourite/list.do')}</c:set>
                <a href="${setUpUrl}" title="���������" onclick="return redirectResolved();">
                    ���������
                </a>
            </tiles:put>
        </c:if>

        <tiles:put name="viewPanel">
            <c:set var="bundleName" value="commonBundle"/>
            <%--������ � ������ ���� --%>
            <div id="favouriteLinks">
                <tiles:insert page="/WEB-INF/jsp-sbrf/personalMenuFavouriteLink.jsp" flush="false">
                    <tiles:put name="showFavouriteLinks" beanName="form" beanProperty="showFavouriteLinks"/>
                    <tiles:put name="isWidget" value="true"/>
                </tiles:insert>
            </div>
            <%--�����������--%>
            <c:if test="${phiz:impliesService('AutoPaymentsManagment')}">
                <c:set var="autoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/favourite/list/AutoPayments.do")}</c:set>
                <c:set var="addAutoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/autopayment/select-category-provider.do")}</c:set>
                <div class="personalMenuWithTitleItem">
                    <phiz:linksList num="0" styleClass="underlined"
                                    listSourceName="autoPaymentsLinkList${widget.codename}" title="����������" titleStyleClass="linksListTitle">
                        <phiz:linksListItem title="��� �����������" href="${autoPaymentsURL}" onClick="return redirectResolved();"/>
                        <phiz:linksListItem title="���������� ����������" href="${addAutoPaymentsURL}" onClick="return redirectResolved();"/>
                    </phiz:linksList>
                </div>
            </c:if>
            <%--������� � ������ ����--%>
            <c:if test="${phiz:impliesService('FavouriteManagment')}">
                <div class="personalMenuWithTitleItem">
                    <phiz:linksList num="0" styleClass="underlined"
                                    listSourceName="templatesLinkList${widget.codename}" title="�������" titleStyleClass="linksListTitle">
                        <c:choose>
                            <c:when test="${empty showTemplates}">
                                <c:set var="templatesNote">
                                    ��� ���� ����� ������ � ����� ��������� ��������, �������� ���� ������� �������� <a href="" class="green"
                                                               onclick="openHelp('${helpFavouriteReceivers}'); return false;">���������</a>.
                                </c:set>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${showTemplates}" var="template">
                                    <c:set var="title">
                                        <c:out value="${template.templateInfo.name}"/>
                                    </c:set>
                                    <c:set var="temp" value="${phiz:getTemplateLinkByTemplate(pageContext, template)}"/>
                                    <phiz:linksListItem title="${title}" href="${temp}" onClick="return personalMenuItemClick(event);"/>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </phiz:linksList>
                </div>
                <c:if test="${empty showTemplates}">
                    <div class="note">${templatesNote}</div>
                </c:if>
            </c:if>
            <%--�������� ��������� "������� �� ���������" � ������ ����--%>
            <c:if test="${phiz:impliesService('LoyaltyService')}">
                <div class="personalMenuWithTitleItem">
                    <phiz:linksList num="0" styleClass="underlined"
                                    listSourceName="loyaltyLinkList${widget.codename}" title="�������� ��������� ������� �� ���������" titleStyleClass="linksListTitle">
                        <phiz:linksListItem title="��� ������" href="#" onClick="loyaltyWindow(event);"/>
                    </phiz:linksList>
                </div>
            </c:if>
            <%--��������� ���� � ������ ����--%>
            <c:if test="${phiz:impliesService('MobileBank')}">
                <div class="personalMenuWithTitleItem">
                    <phiz:linksList num="0" styleClass="underlined"
                                    listSourceName="mobileBankLinkList${widget.codename}" title="��������� ����" titleStyleClass="linksListTitle">
                        <c:set var="url" value="/private/mobilebank/main.do"/>
                        <phiz:linksListItem title="�����������" href="${phiz:calculateActionURL(pageContext,url)}" onClick="return personalMenuItemClick(event);"/>
                        <c:set var="url" value="/private/mobilebank/payments/list/all.do"/>
                        <phiz:linksListItem title="SMS-������� � �������" href="${phiz:calculateActionURL(pageContext,url)}" onClick="return personalMenuItemClick(event);"/>
                    </phiz:linksList>
                </div>
            </c:if>

             <c:set var="abstractUrl" value="${phiz:calculateActionURL(pageContext,'/private/async/loyalty.do')}"/>

            <%--����� ����������� ������ ��� ������� �������� �� ������ ���� (��������: �������� ��������� "������� �� ���������", "����� ����������������")--%>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="externalWebSiteErrorWindow"/>
                <tiles:put name="loadAjaxUrl" value="${abstractUrl}"/>
                <tiles:put name="data">
                     <h2>��������!</h2>
                    ��������� ���������
                    <div id="externalWebSiteErrorMsg">������ ��� ������� ������� �� ������ ����.</div>
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
        </tiles:put>

        <tiles:put name="editPanel">
            <div class ="personal-menu-settings">
                <div class="interface-items">
                    <div class="favourites-operations">
                        <fieldset>
                            <table>
                                <%--������� ��������--%>
                                <c:if test="${phiz:impliesService('PaymentList')}">
                                    <tr>
                                        <td class="align-left">
                                            <input type="checkbox" disabled="true" checked/>
                                        </td>
                                        <td class="align-left">
                                           <div class="personal-menu-settings-item">
                                                <bean:message key="label.payments.history" bundle="favouriteBundle"/>
                                           </div>
                                        </td>
                                    </tr>
                                </c:if>
                                <%--������ ��������--%>
                                <c:if test="${phiz:impliesService('ViewFinance')}">
                                    <tr>
                                        <td class="align-left">
                                            <input type="checkbox" disabled="true" checked/>
                                        </td>
                                        <td class="align-left">
                                           <div class="personal-menu-settings-item">
                                                <bean:message key="label.finance" bundle="favouriteBundle"/>
                                           </div>
                                        </td>
                                    </tr>
                                </c:if>

                                <%--����������� ��������������� �� ����� ���--%>
                                <c:if test="${phiz:impliesService('BusinessmanRegistrationService')}">
                                    <tr>
                                        <td class="align-left">
                                            <input type="checkbox" disabled="true" checked/>
                                        </td>
                                        <td class="align-left">
                                           <div class="personal-menu-settings-item">
                                                <bean:message key="label.businessmanRegistration" bundle="favouriteBundle"/>
                                           </div>
                                        </td>
                                    </tr>
                                </c:if>

                                <%--������������� �����--%>
                                <c:if test="${phiz:impliesService('BlockingCardClaim')}">
                                    <tr>
                                        <td class="align-left">
                                            <input type="checkbox" disabled="true" checked/>
                                        </td>
                                        <td class="align-left">
                                           <div class="personal-menu-settings-item">
                                                <bean:message key="label.blockingCardClaim" bundle="favouriteBundle"/>
                                           </div>
                                        </td>
                                    </tr>
                                </c:if>

                                <%--������� �� ����� ����������--%>
                                <c:if test="${phiz:impliesService('LossPassbookApplication')}">
                                    <tr>
                                        <td class="align-left">
                                            <input type="checkbox" disabled="true" checked/>
                                        </td>
                                        <td class="align-left">
                                           <div class="personal-menu-settings-item">
                                                <bean:message key="label.lossPassbookApplication" bundle="favouriteBundle"/>
                                           </div>
                                        </td>
                                    </tr>
                                </c:if>

                                <%--�������� ������--%>
                                <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation','InternalPayment') || phiz:impliesOperation('CreateFormPaymentOperation','RurPayJurSB') || phiz:impliesOperation('CreateFormPaymentOperation','RurPayment') || phiz:impliesOperation('CreateFormPaymentOperation','TaxPayment')}">
                                     <tr>
                                        <td class="align-left">
                                            <input type="checkbox" disabled="true" checked/>
                                        </td>
                                        <td class="align-left">
                                           <div class="personal-menu-settings-item">
                                                <bean:message key="label.payment" bundle="favouriteBundle"/>
                                           </div>
                                        </td>
                                    </tr>
                                </c:if>

                                <%--��������� ����������--%>
                                <c:if test="${phiz:impliesService('ShowConnectedMobileDevicesService')}">
                                    <tr>
                                        <td class="align-left">
                                            <input type="checkbox" disabled="true" checked/>
                                        </td>
                                        <td class="align-left">
                                           <div class="personal-menu-settings-item">
                                                <bean:message key="label.mobileApplication" bundle="favouriteBundle"/>
                                           </div>
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                        </fieldset>
                    </div>

                    <c:if test="${phiz:impliesService('FavouriteManagment')}">
                        <div class="favourites-operations">
                           <c:choose>
                               <c:when test="${not empty allFavouriteLinks}">
                                   <c:set var="countLinks" value="${phiz:size(allFavouriteLinks)}"/>
                                   <fieldset>
                                       <table class="PersonalMenuFavouriteLinksList ${countLinks!=0 ? 'dashedBorder':''}">
                                           <c:forEach var="link" items="${allFavouriteLinks}" varStatus="line">
                                               <tr favouriteLinkId=${link.id}>
                                                   <td class="align-left">
                                                        <input type="checkbox" name="favouriteLinkShowInThisWidget" onclick="cancelBubbling(event);" value="${link.id}"/>
                                                   </td>
                                                   <td class="align-left">
                                                       <div class="personal-menu-settings-item">
                                                           <span>
                                                               <c:out value="${link.name}"/>
                                                           </span>
                                                       </div>
                                                   </td>
                                                   <td class="align-left">
                                                       <c:choose>
                                                           <c:when test="${line.count > 1}">
                                                               <div class="linkUp" style="display: block;" title="����������� �����">&#8593;</div>
                                                           </c:when>
                                                           <c:otherwise>
                                                               <div class="linkUp" style="display: none;" title="����������� �����">&#8593;</div>
                                                           </c:otherwise>
                                                       </c:choose>
                                                   </td>
                                                   <td class="align-left">
                                                       <c:choose>
                                                           <c:when test="${line.count < countLinks}">
                                                               <div class="linkDown" style="display: block;" title="����������� ����">&#8595;</div>
                                                           </c:when>
                                                           <c:otherwise>
                                                               <div class="linkDown" style="display: none;" title="����������� ����">&#8595;</div>
                                                           </c:otherwise>
                                                       </c:choose>
                                                   </td>
                                               </tr>
                                           </c:forEach>
                                       </table>
                                   </fieldset>
                               </c:when>
                               <c:otherwise>
                                   <p class="profile-empty-text">� ��� ��� �� ����� ��������� ��������. ����� �� ������� ��������� ����������, ������������ � ���������, � ����� ��������� �� ����������� � <b>������ ����</b>.<br/>
                                       <a href="" onclick="openHelp('${helpFavourite}'); return false;">���������&raquo;</a>
                                   </p>
                               </c:otherwise>
                           </c:choose>
                       </div>

                        <div class="payments-templates">
                            <c:choose>
                                <c:when test="${not empty allTemplates}">
                                    <c:set var="countTemplates" value="${phiz:size(allTemplates)}"/>
                                    <fieldset>
                                        <table class="PersonalMenuTemplatesList  ${countLinks==0 ? 'dashedBorder':''}">
                                                <c:forEach items="${allTemplates}" var="template" varStatus="line">
                                                     <tr templateId="${template.id}">
                                                        <td class="align-left">
                                                            <div>
                                                                <input type="checkbox" name="templateShowInThisWidget" onclick="cancelBubbling(event);" value="${template.id}"/>
                                                            </div>
                                                        </td>
                                                        <td class="align-left">
                                                            <div class="personal-menu-settings-item">
                                                                <c:set var="title">
                                                                    <c:out value="${template.templateInfo.name}"/>
                                                                </c:set>
                                                                <span>
                                                                    <c:out value="${title}"/>
                                                                </span>
                                                            </div>
                                                        </td>
                                                        <td class="align-left">
                                                            <c:choose>
                                                                <c:when test="${line.count > 1}">
                                                                    <div class="linkUp" style="display: block;" title="����������� �����">&#8593;</div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="linkUp" style="display: none;" title="����������� �����">&#8593;</div>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td class="align-left">
                                                            <c:choose>
                                                                <c:when test="${line.count < countTemplates}">
                                                                    <div class="linkDown" style="display: block;" title="����������� ����">&#8595;</div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="linkDown" style="display: none;" title="����������� ����">&#8595;</div>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                              </c:forEach>
                                        </table>
                                    </fieldset>
                                </c:when>
                                <c:otherwise>
                                    <p class="profile-empty-text">� ��� ��� �� ������ �������. ����� �� ������� ��������� ���������� ���������, � ����� ��������� �� ����������� � <b>������ ����</b>. <br/>
                                        <a href="" onclick="openHelp('${helpReceivers}'); return false;">���������&raquo;</a>
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                    </c:if>

                    <div class="clear"></div>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

<!-- ����� �������� �������� � ��������� �������� -->
<script type="text/javascript">
    <%--  �������� ������ ���� ��� ������������� �� ���� ��������� "����������",
          ���� ����������� ������������ ���� � ��� ��� url ��� �������� ������������� �� ������� --%>
    function loyaltyWindow(event)
    {
        event.cancelBubble = true;
        <%-- TODO ���������� ����� ��������� � utils. � ������ ������ phiz:linksListItem
               ��� �������� � ������� onClick ������� � ����������(���� Init(param)) �������������� �� ���������
               ���������� ����� ������������.--%>
        <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/async/loyalty.do")}</c:set>
        ajaxQuery(null, "${url}", function(data)
        {
            var url = trim(data);
            if (url == "error")
            {
                var error = document.getElementById("externalWebSiteErrorMsg");
                if (error != undefined)
                    error.innerText = "�� �� ������ ���������� ���� ��������� ����������. ����������, ��������� ������� �����.";
                var i = win.open('externalWebSiteErrorWindow');
            }
            else if(url == "")
            {
                window.location.reload();
            }
            else
            {
                window.open(
                        url,
                        'loyalty',
                        "resizable=1,menubar=0,toolbar=0,scrollbars=1"
                        );
            }
        });
    }

    <%--��� IE �� 7-�� ������������, ����� �� �������������� ������� onclick � ���������--%>
    function personalMenuItemClick(event)
    {
        event.cancelBubble = true;
        return redirectResolved();
    }
</script>

