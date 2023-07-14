<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<tiles:importAttribute/>
<c:set var="editNotification" value="${phiz:availableNewProfile() && not phiz:isCommonAttributeUseInProductsAvailable()}"/>
<c:if test='${editNotification}'><c:set var="additionalClass" value="productNotification"/> </c:if>
<div class="tabContainer ${additionalClass} mb-tabs">
    <tiles:insert definition="paymentTabs" flush="false">
        <tiles:put name="count" value="${editNotification ? 4 : 3}"/>
        <tiles:put name="tabItems">
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="active" value="${selectedTab == 'registration'}"/>
                <tiles:put name="title" value="Настройка подключения"/>
                <tiles:put name="position" value="first"/>
                <c:if test="${selectedTab != 'registration'}">
                    <tiles:put name="action" value="/private/mobilebank/ermb/main"/>
                </c:if>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="active" value="${selectedTab == 'history'}"/>
                <tiles:put name="title" value="История SMS-запросов"/>
                <c:if test="${selectedTab != 'history'}">
                    <tiles:put name="action" value="/private/mobilebank/ermb/history"/>
                </c:if>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="last"/>
                <tiles:put name="active" value="${selectedTab == 'templates'}"/>
                <tiles:put name="title" value="SMS-запросы и шаблоны"/>
                <c:if test="${selectedTab != 'templates'}">
                    <tiles:put name="action" value="/private/mobilebank/ermb/templates"/>
                </c:if>
            </tiles:insert>
            <c:if test="${editNotification}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="${selectedTab == 'notification'}"/>
                    <tiles:put name="title" value="Установка SMS-оповещений"/>
                    <c:if test="${selectedTab != 'notification'}">
                        <tiles:put name="action" value="/private/userprofile/productNotification"/>
                    </c:if>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
    <div class="clear"></div>
</div>
<script type="text/javascript">

var availableinSYS = '<bean:message bundle="userprofileBundle" key="label.not_show_system"/>';
            var unavailableinSYS = '<bean:message bundle="userprofileBundle" key="label.not_show_system"/>';
            var availableinMobile = '<bean:message bundle="userprofileBundle" key="label.show_mobile"/>';
            var unavailableinMobile = '<bean:message bundle="userprofileBundle" key="label.not_show_mobile"/>';
            var availableinSocial = '<bean:message bundle="userprofileBundle" key="label.show_social"/>';
            var unavailableinSocial = '<bean:message bundle="userprofileBundle" key="label.not_show_social"/>';

var card = new Array();
var account = new Array();

function hideOrShowClosed(element, name, product)
{
    var closedTable = ensureElement(name);

    if (product == null)
    {
        return;
    }
    if (element == null)
    {
        closedTable.style.display = 'none';
    }
    else
    {
        if (element.checked)
        {
            closedTable.style.display = '';
        }
        else
        {
            closedTable.style.display = 'none';
            deselectBeforeHide(product);
        }
    }

    var elem = ensureElement('hidden' + product);
    if (elem != null)
    {
        elem.value = ensureElement(product).checked ? product : '';
    }
};

function deselectBeforeHide(product)
{
    var elems = getElementsByProduct(product);
    var array;

    var elemsES;
    var selectedIds;
    var text;
    switch (product)
            {
        case "CARD":
        {
            array = card;
            selectedIds = "selectedCardIds";
            /*text = unavailableinSYS;*/
            break;
        }
        case "CARD_MOB":
        {
            array = card;
            selectedIds = "selectedCardMobileIds";
            /*text = unavailableinMobile;*/

            break;
        }
        case "CARD_SOB":
        {
            array = card;
            selectedIds = "selectedCardSocialIds";
            /*text = unavailableinSocial;*/

            break;
        }
        case "ACCOUNT":
        {
            array = account;
            selectedIds = "selectedAccountIds";
            /*text = unavailableinSYS;*/

            break;
        }
        case "ACCOUNT_MOB":
        {
            array = account;
            selectedIds = "selectedAccountMobileIds";
            /*text = unavailableinMobile;*/
            break;
        }
        case "ACCOUNT_SOB":
        {
            array = account;
            selectedIds = "selectedAccountSocialIds";
            /*text = unavailableinSocial;*/
            break;
        }
    }

    if (elems == null || array.length == 0)
    {
        return;
    }
    deselect(elems, array, selectedIds/*, text*/);
};

function deselect(elems, array, selectedIds/*, text*/)
{
    for (var i = 0; i < elems.length; i++)
    {
        for (var j = 0; j < array.length; j++)
        {
            if (array[j] == elems[i].value)
            {
                elems[i].checked = false;
                /*$('.' + selectedIds + array[j]).html(text);*/
                break;
            }
        }
    }
};

function getElementsByProduct(product)
{
    switch (product)
            {
        case "CARD":
        {
            return document.getElementsByName('selectedCardIds');
        }
        case "CARD_MOB":
        {
            return document.getElementsByName('selectedCardMobileIds');
        }
        case "CARD_SOB":
        {
            return document.getElementsByName('selectedCardSocialIds');
        }
        case "ACCOUNT":
        {
            return document.getElementsByName('selectedAccountIds');
        }
        case "ACCOUNT_MOB":
        {
            return document.getElementsByName('selectedAccountMobileIds');
        }
        case "ACCOUNT_SOB":
        {
            return document.getElementsByName('selectedAccountSocialIds');
        }
        case "ACCOUNT_ES":
        {
            return document.getElementsByName('selectedAccountESIds');
        }
        default:
        {
            return null;
        }
    }
};

</script>
