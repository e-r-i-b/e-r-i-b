<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<tiles:importAttribute/>

<html:form action="/private/favourite/list">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="userProfile">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="���������"/>
            <tiles:put name="action" value="/private/userprofile/userSettings.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="��������� ����������"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <script type="text/javascript">

            function resizeProductListDescription()
            {
                resizeProductDescription("sortableAccounts");
                resizeProductDescription("sortableCards");
                resizeProductDescription("sortableLoans");
                resizeProductDescription("sortableIMAccounts");
                resizeProductDescription("sortableDepoAccounts");
            }

            /**
            * �-� ��� �������� ����������� ���������� � �������� �� ������ ������
            * @param tableId
            */
            function resizeProductDescription(tableId)
            {
                var indent = 10; // ������
                $('#' + tableId).find('div.left').each(function ()
                {
                    var productNumberWith = $(this).find('span.productNumber')[0].offsetWidth;

                    var productDescription = $(this).children('div')[0];
                    var productNameWith = $(productDescription).find('span#productTitle')[0].offsetWidth;

                    var productDescriptionWith = this.offsetWidth;
                    if (($.browser.msie) && ($.browser.version == '6.0')) {
                        productDescriptionWith = 345;
                    }

                    // ������������ ������ ��� ����������� �������� ��������
                    var productNameMaxWith = productDescriptionWith - productNumberWith - indent;

                    // ���� �������� �� ��������� � ���������� ������, �� ��������� ����������, ������������� ����� ��� ������� � ��������� �����,
                    // ����� ��� ��������� ����� �� ������ ������
                    if (productNameWith > productNameMaxWith)
                    {
                        productDescription.style.width = productNameMaxWith + "px";  // ������ ���� � ��������� � ������ ������������
                        $(this).find('.lightness')[0].style.visibility = "visible";  // �������� ����, �������� ��������� ������ ����������
                    }
                    // ���� �������� ������, ��� ���������� �����, �� ����������� ������� ��� ��������, ������� ����������
                    // (����� �������� ����� ����� � ���������)
                    else
                    {
                        productDescription.style.width = productNameWith + "px";
                    }
                });
            }

            $(function(){
                sortableProducts("sortableAccounts");
                sortableProducts("sortableCards");
                sortableProducts("sortableLoans");
                sortableProducts("sortableIMAccounts");
                sortableProducts("sortableDepoAccounts");
            });

            function sortableProducts(tableId)
            {
                var body = $("#"+tableId);
                var rows = body.find('div#sortable');

                if (rows.length > 1)
                {
                    body.sortable({
                        items:rows,
                        tolerance: "pointer",
                        containment: "parent",
                        cursor: "move",
                        placeholder: "placeholder",
                        axis: "y"
                    });
                }

            }

        </script>
        <div id="profile">

            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="���������"/>
                <tiles:put name="data">
                    <c:set var="selectedTab" value="interfaceSettings"/>
                    <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                    <div class="clear"></div>
                    <div class="securityOptionsArea">
                        <div class="securityOptions">
                            <tiles:insert definition="userProfileSecurity" flush="false">
                                <tiles:put name="title" value="������� ����"/>
                                <tiles:put name="text">
                                    ����� �� ������ �������� ��������� ����������� ������� �������� ����, � ����� �������� ������� �� ������������ � ������� �������.
                                </tiles:put>
                                <tiles:put name="style" value="id2"/>
                                <tiles:put name="data">
                                    <html:hidden property="selectedMenuLinkId"/>
                                    <c:set var="menuLinks" value="${form.menuLinks}"/>
                                    <c:set var="menuLinksCount" value="${phiz:size(menuLinks)}"/>
                                    <div class="interface-items">
                                        <div class="payments-templates">
                                            <fieldset>
                                                <table>
                                                    <c:if test="${not empty menuLinks}">
                                                        <c:forEach var="linkInfo" items="${menuLinks}" varStatus="line">
                                                            <tr>
                                                                <td>
                                                                    <html:hidden property="sortMenuLinks" value="${linkInfo.link.id}"/>
                                                                </td>
                                                                <td class="align-left"><div class="personal-menu-settings-item" style="width:150px;"><label for="securityOptions${linkInfo.link.id}"><c:out value="${linkInfo.text}"/></label></div> </td>
                                                                <td class="align-right">
                                                                    <html:multibox property="selectedMenuLinks"
                                                                            styleId="securityOptions${linkInfo.link.id}">
                                                                    <bean:write name="linkInfo" property="link.id"/>
                                                                    </html:multibox>
                                                                </td>
                                                                <td class="align-left">
                                                                    <c:choose>
                                                                        <c:when test="${line.count > 1}">
                                                                            <div class="linkUp" style="display: block;" title="�����������">&#8593;</div>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <div class="linkUp" style="display: none;" title="�����������">&#8593;</div>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td class="align-left">
                                                                    <c:choose>
                                                                        <c:when test="${line.count < menuLinksCount}">
                                                                            <div class="linkDown" style="display: block;" title="�����������">&#8595;</div>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <div class="linkDown" style="display: none;" title="�����������">&#8595;</div>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                 </table>
                                            </fieldset>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="info"><a href="#" onclick="setDefaultValue();">������������ �������� �� ���������</a></div>

                                        <div class="buttonsArea">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                <tiles:put name="bundle" value="commonBundle"/>
                                                <tiles:put name="action" value="/private/accounts.do"/>
                                                <tiles:put name="viewType" value="buttonGrey"/>
                                            </tiles:insert>
                                            <tiles:insert definition="commandButton" flush="false">
                                                <tiles:put name="commandKey" value="button.saveMainMenuSettings"/>
                                                <tiles:put name="commandHelpKey" value="button.saveMainMenuSettings.help"/>
                                                <tiles:put name="bundle" value="favouriteBundle"/>
                                            </tiles:insert>
                                        </div>
                                    </div>

                                </tiles:put>
                            </tiles:insert>
                        </div>
                        <div class="securityOptions">
                            <tiles:insert definition="userProfileSecurity" flush="false">
                                <tiles:put name="title" value="��������� ������� ��������"/>
                                <tiles:put name="text">
                                    ����� ����� �������� ������������������ ����� ����, �������, �������� ��� ������ ��������� �� ������� ��������. ��� ����� ����������� ������� � ������ � ������� �����������.
                                </tiles:put>
                                <tiles:put name="style" value="id3"/>
                                <tiles:put name="data">
                                        <%--�����--%>
                                        <c:if test="${not empty form.cards}">
                                            <div class="profile-view-products">
                                                <fieldset>
                                                    <div class="sortableHeader">
                                                        <bean:message bundle="userprofileBundle" key="title.cards"/>
                                                    </div>
                                                    <div id="sortableCards">
                                                        <logic:iterate id="listItem" name="form" property="cards">
                                                            <c:set var="cardLink" value="${listItem}" scope="request"/>
                                                            <tiles:insert page="mainPageProductSettings/cards.jsp" flush="false"/>
                                                        </logic:iterate>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </c:if>
                                        <%--������--%>
                                        <c:if test="${not empty form.accounts}">
                                            <div class="profile-view-products">
                                                <fieldset>
                                                    <div class="sortableHeader">
                                                        <bean:message bundle="userprofileBundle" key="title.accounts"/>
                                                    </div>
                                                    <div id="sortableAccounts">
                                                        <logic:iterate id="listItem" name="form" property="accounts">
                                                            <c:set var="accountLink" value="${listItem}" scope="request"/>
                                                            <tiles:insert page="mainPageProductSettings/account.jsp" flush="false"/>
                                                        </logic:iterate>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </c:if>
                                        <%--�������--%>
                                        <c:if test="${not empty form.loans && phiz:impliesService('ReceiveLoansOnLogin')}">
                                            <div class="profile-view-products">
                                                <fieldset>
                                                    <div class="sortableHeader">
                                                        <bean:message bundle="userprofileBundle" key="title.loans"/>
                                                    </div>
                                                    <div id="sortableLoans">
                                                        <logic:iterate id="listItem" name="form" property="loans">
                                                            <c:set var="loanLink" value="${listItem}" scope="request"/>
                                                            <tiles:insert page="mainPageProductSettings/loans.jsp" flush="false"/>
                                                        </logic:iterate>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </c:if>
                                        <%--���--%>
                                        <c:if test="${not empty form.imAccounts}">
                                            <div class="profile-view-products">
                                                <fieldset>
                                                    <div class="sortableHeader">
                                                        <bean:message bundle="userprofileBundle" key="title.ima"/>
                                                    </div>
                                                    <div id="sortableIMAccounts">
                                                        <logic:iterate id="listItem" name="form" property="imAccounts">
                                                            <c:set var="imAccountLink" value="${listItem}" scope="request"/>
                                                            <tiles:insert page="mainPageProductSettings/imAccount.jsp" flush="false"/>
                                                        </logic:iterate>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </c:if>
                                        <%--����� ����--%>
                                        <c:set var="personInf" value="${phiz:getPersonInfo()}"/>
                                        <c:choose>
                                            <c:when test="${!personInf.isRegisteredInDepo && personInf.creationType == 'UDBO'}">
                                                <div class="profile-view-products">
                                                    <div class="sortableHeader"><bean:message bundle="userprofileBundle"
                                                                      key="title.depoAccounts"/></div>

                                                    <div class="profile-empty-text-depo"> � ��� �� ����������
                                                        ������ &laquo;�����������&raquo;. ����� �������� ������ �
                                                        ����������� ����� ������� &laquo;�������� ������&raquo;,
                                                        ��������� �� �������� <html:link
                                                                action="/private/depo/list.do">����� ����</html:link>.
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${not empty form.depoAccounts}">
                                                    <div class="profile-view-products">
                                                        <fieldset>
                                                            <div class="sortableHeader">
                                                                <bean:message bundle="userprofileBundle" key="title.depoAccounts"/>
                                                            </div>
                                                            <div id="sortableDepoAccounts">
                                                                <logic:iterate id="listItem" name="form" property="depoAccounts">
                                                                    <c:set var="depoAccountLink" value="${listItem}" scope="request"/>
                                                                    <tiles:insert page="mainPageProductSettings/depoAccount.jsp" flush="false"/>
                                                                </logic:iterate>
                                                            </div>
                                                        </fieldset>
                                                    </div>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>

                                        <%--����������� �����--%>
                                        <div class="profile-view-products">
                                            <fieldset>
                                                <div class="sortableHeader">
                                                    <bean:message bundle="userprofileBundle" key="title.bank.offer"/>
                                                </div>
                                                <div class="noSortable">
                                                    <div class="sortableRows">
                                                        <div class="tinyProductImg"></div>
                                                        <div class="left">
                                                            <bean:message bundle="userprofileBundle" key="label.bank.offer"/>
                                                        </div>
                                                        <div class="right"></div>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </div>
                                        <%--���--%>
                                        <c:if test="${not empty form.pfrLink}">
                                            <div class="profile-view-products">
                                                <fieldset>
                                                    <div class="sortableHeader">
                                                        <bean:message bundle="userprofileBundle" key="title.pfr"/>
                                                    </div>
                                                    <div class="noSortable">
                                                        <div class="sortableRows">
                                                            <div class="tinyProductImg">
                                                                <img src="${imagePath}/products/icon_pfr32.jpg" alt="">
                                                            </div>
                                                            <div class="left">
                                                                <bean:message bundle="userprofileBundle" key="label.pfr"/>
                                                            </div>
                                                            <div class="right"></div>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </c:if>
                                        <c:if test="${phiz:impliesService('LoyaltyProgramRegistrationClaim')}">
                                            <div class="profile-view-products">
                                                <fieldset>
                                                    <div class="sortableHeader">
                                                        <bean:message bundle="userprofileBundle" key="label.loyalty"/>
                                                    </div>
                                                    <div class="noSortable">
                                                        <div class="sortableRows">
                                                            <div class="tinyProductImg">
                                                                <img src="${imagePath}/products/loyaltyProgramIcon32.gif" alt="">
                                                            </div>
                                                            <div class="left">
                                                                <bean:message bundle="userprofileBundle" key="label.loyalty"/>
                                                            </div>
                                                            <div class="right"></div>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </c:if>
                                        <div class="clear"></div>
                                    <div class="buttonsArea">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="action" value="/private/accounts.do"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                        </tiles:insert>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.saveSettingsView"/>
                                            <tiles:put name="isDefault" value="true"/>
                                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                                            <tiles:put name="bundle" value="userprofileBundle"/>
                                        </tiles:insert>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                        <div class="securityOptions" id="changeSkin">
                        <tiles:insert definition="userProfileSecurity" flush="false">
                            <tiles:put name="title" value="��������� �����"/>
                            <tiles:put name="text">
                                ����� �� ������ �������� ����� ����������� ������� ��������� ������.
                            </tiles:put>

                            <tiles:put name="data">
                                <tiles:insert definition="barleyBreak" flush="false">
                                    <tiles:put name="styleClass" value="skinIcon"/>
                                    <tiles:putList name="data">

                                        <c:forEach items="${form.skins}" var="skin">
                                            <c:set var="skinOnClick">document.forms[0].previewSkin.value="${skin.id}"; document.forms[0].submit(); saveOffset(); return false;</c:set>
                                            <tiles:add>

                                                <c:set var="data">
                                                    <a href="#" onclick='${skinOnClick}'>
                                                        <img src="${phiz:updateSkinPath(skin.url)}/images/preview.jpg" alt="${skin.name}" onerror="onImgError(this)" border="0"/><br/>
                                                        ${skin.name}
                                                    </a>
                                                </c:set>

                                               <c:choose>
                                                    <c:when test="${skin == form.currentSkin}">
                                                        <tiles:insert definition="roundBorder" flush="false">
                                                            <tiles:put name="color" value="orange"/>
                                                            <tiles:put name="data">
                                                                ${data}
                                                            </tiles:put>
                                                        </tiles:insert>
                                                    </c:when>
                                                    <c:otherwise>${data}</c:otherwise>
                                                </c:choose>
                                              </tiles:add>
                                        </c:forEach>

                                    </tiles:putList>
                                </tiles:insert>
                                <input type="hidden" name="previewSkin" value="${form.currentSkin.id}"/>
                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                        <tiles:put name="bundle" value="commonBundle"/>
                                        <tiles:put name="action" value="/private/favourite/list.do"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                    </tiles:insert>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey" value="button.saveSkin"/>
                                        <tiles:put name="commandHelpKey" value="button.saveSkin.help"/>
                                        <tiles:put name="bundle" value="userprofileBundle"/>
                                    </tiles:insert>
                                </div>


                            </tiles:put>
                        </tiles:insert>
                    </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
<script type="text/javascript">
    function checkData()
    {
        if (!isDataChanged())
        {
            addMessage("�� �� ������ ������� ��������� � ������������ ����������.");
            return false;
        }
        return true;
    }

    function setDefaultValue()
    {
        new CommandButton('button.setDefaultValue').click();
    }
</script>
</html:form>