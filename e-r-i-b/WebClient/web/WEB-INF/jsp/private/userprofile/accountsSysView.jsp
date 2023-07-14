<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:importAttribute/>
<html:form action="/private/userprofile/accountsSystemView" onsubmit="return setEmptyAction(event);">
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<c:if test="${not empty form.confirmableObject}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>
<tiles:insert definition="userProfile">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройки"/>
            <tiles:put name="action" value="/private/userprofile/userSettings.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройка безопасности"/>
            <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройка видимости продуктов"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <script type="text/javascript">
            var availableinSYS = '<bean:message bundle="userprofileBundle" key="label.show_system"/>';
            var unavailableinSYS = '<bean:message bundle="userprofileBundle" key="label.not_show_system"/>';
            var availableinMobile = '<bean:message bundle="userprofileBundle" key="label.show_mobile"/>';
            var unavailableinMobile = '<bean:message bundle="userprofileBundle" key="label.not_show_mobile"/>';
            var availableinSocial = '<bean:message bundle="userprofileBundle" key="label.show_social"/>';
            var unavailableinSocial = '<bean:message bundle="userprofileBundle" key="label.not_show_social"/>';
            var availableinES = '<bean:message bundle="userprofileBundle" key="label.show_es"/>';
            var unavailableinES = '<bean:message bundle="userprofileBundle" key="label.not_show_es"/>';
            var availableinSMS = '<bean:message bundle="userprofileBundle" key="label.show_sms"/>';
            var unavailableinSMS = '<bean:message bundle="userprofileBundle" key="label.not_show_sms"/>';




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
                if(elem != null && ensureElement(product))
                {
                    elem.value = ensureElement(product).checked ? product: '';
                }
            };

            var card = new Array();
            var loan = new Array();
            var depo = new Array();
            var account = new Array();
            var imaccount = new Array();

            function addClosed(product, value, element)
            {
                switch (product)
                {
                    case "CARD":
                    {
                        card[card.length] = value;
                        break;
                    }
                    case "CARD_MOB":
                    {
                        card[card.length] = value;
                        break;
                    }
                    case "CARD_SOB":
                    {
                        card[card.length] = value;
                        break;
                    }
                    case "CARD_ES":
                    {
                        card[card.length] = value;
                        break;
                    }
                    case "LOAN":
                    {
                        loan[loan.length] = value;
                        break;
                    }
                   case "LOAN_MOB":
                    {
                        loan[loan.length] = value;
                        break;
                    }
                   case "LOAN_SOB":
                    {
                        loan[loan.length] = value;
                        break;
                    }
                   case "LOAN_ES":
                    {
                        loan[loan.length] = value;
                        break;
                    }
                    case "DEPO_ACCOUNT":
                    {
                        depo[depo.length] = value;
                        break;
                    }
                    case "ACCOUNT":
                    {
                        account[account.length] = value;
                        break;
                    }
                     case "ACCOUNT_MOB":
                    {
                        account[account.length] = value;
                        break;
                    }
                      case "ACCOUNT_SOB":
                    {
                        account[account.length] = value;
                        break;
                    }
                     case "ACCOUNT_ES":
                    {
                        account[account.length] = value;
                        break;
                    }
                    case "IM_ACCOUNT":
                    {
                        imaccount[imaccount.length] = value;
                        break;
                    }
                    case "IM_ACCOUNT_ES":
                    {
                        imaccount[imaccount.length] = value;
                        break;
                    }
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
                        text = unavailableinSYS;
                        break;
                    }
                     case "CARD_MOB":
                    {
                        array = card;
                        selectedIds = "selectedCardMobileIds";
                        text = unavailableinMobile;

                        break;
                    }
                    case "CARD_SOB":
                    {
                        array = card;
                        selectedIds = "selectedCardSocialIds";
                        text = unavailableinSocial;

                        break;
                    }
                     case "CARD_ES":
                    {
                        array = card;
                        selectedIds = "selectedCardESIds";
                        text = unavailableinMobile;

                        break;
                    }
                      case "CARD_SMS":
                    {
                        array = card;
                        selectedIds = "selectedCardSmsIds";
                        text = unavailableinSMS;

                        break;
                    }
                    case "LOAN":
                    {
                        array = loan;
                        selectedIds = "selectedLoanIds";
                        text = unavailableinSYS;

                        break;
                    }
                     case "LOAN_MOB":
                    {
                        array = loan;
                        selectedIds = "selectedLoanMobileIds";
                        text = unavailableinMobile;

                        break;
                    }
                     case "LOAN_SOB":
                    {
                        array = loan;
                        selectedIds = "selectedLoanSocialIds";
                        text = unavailableinSocial;

                        break;
                    }
                     case "LOAN_ES":
                    {
                        array = loan;
                        selectedIds = "selectedLoanESIds";
                        text = unavailableinMobile;

                        break;
                    }
                    case "LOAN_SMS":
                    {
                        array = loan;
                        selectedIds = "selectedLoanSmsIds";
                        text = unavailableinSMS;

                        break;
                    }
                    case "DEPO_ACCOUNT":
                    {
                        array = depo;
                        selectedIds = "selectedDepoAccountIds";
                        text = unavailableinSYS;

                        break;
                    }
                    case "ACCOUNT":
                    {
                        array = account;
                        selectedIds = "selectedAccountIds";
                        text = unavailableinSYS;

                        break;
                    }
                    case "ACCOUNT_ES":
                    {
                        array = account;
                        selectedIds = "selectedAccountESIds";
                        text = unavailableinES;

                        break;
                    }
                    case "ACCOUNT_MOB":
                   {
                        array = account;
                        selectedIds = "selectedAccountMobileIds";
                        text = unavailableinMobile;
                        break;
                    }
                    case "ACCOUNT_SOB":
                   {
                        array = account;
                        selectedIds = "selectedAccountSocialIds";
                        text = unavailableinSocial;
                        break;
                    }
                    case "ACCOUNT_SMS":
                    {
                        array = account;
                        selectedIds = "selectedAccountSmsIds";
                        text = unavailableinSMS;
                        break;
                    }
                    case "IM_ACCOUNT":
                    {
                        array = imaccount;
                        selectedIds = "selectedIMAccountIds";
                        text = unavailableinSYS;
                        break;
                    }
                    case "IM_ACCOUNT_ES":
                    {
                        array = imaccount;
                        selectedIds = "selectedIMAccountESIds";
                        text = unavailableinSYS;
                        break;
                    }
                }

                if (elems == null || array.length == 0)
                {
                    return;
                }

                deselect(elems, array, selectedIds, text);
            };


            function deselect(elems, array, selectedIds, text)
            {
                for (var i = 0; i < elems.length; i++)
                {
                    for (var j = 0; j < array.length; j++)
                    {
                        if (array[j] == elems[i].value)
                        {
                            $('.'+selectedIds+array[j] + ' label').html(text);
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
                    case "CARD_ES":
                    {
                        return document.getElementsByName('selectedCardESIds');
                    }
                    case "CARD_SMS":
                    {
                        return document.getElementsByName('selectedCardSmsIds');
                    }
                    case "LOAN":
                    {
                        return document.getElementsByName('selectedLoanIds');
                    }
                    case "LOAN_MOB":
                    {
                        return document.getElementsByName('selectedLoanMobileIds');
                    }
                    case "LOAN_SOB":
                    {
                        return document.getElementsByName('selectedLoanSocialIds');
                    }
                    case "LOAN_ES":
                    {
                        return document.getElementsByName('selectedLoanESIds');
                    }
                    case "LOAN_SMS":
                    {
                        return document.getElementsByName('selectedLoanSmsIds');
                    }
                    case "DEPO_ACCOUNT":
                    {
                        return document.getElementsByName('selectedDepoAccountIds');
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
                    case "ACCOUNT_SMS":
                    {
                        return document.getElementsByName('selectedAccountSmsIds');
                    }
                    case "IM_ACCOUNT":
                    {
                        return document.getElementsByName('selectedIMAccountIds');
                    }
                    case "IM_ACCOUNT_ES":
                    {
                        return document.getElementsByName('selectedIMAccountESIds');
                    }
                    default:
                    {
                        return null;
                    }
                }
            };

            function redirectResolved()
            {
                if (isDataChanged("pageType") || ${not empty confirmRequest})
                {
                    win.open('redirectRefused');
                    return false;
                }
                return true;
            }

            $(document).ready(function(){
                initData();
                $('#inSystem').click(function(){
                    if (!redirectResolved())
                        return;

                    $('#pageType').val('system');
                    $('#inSystem').removeClass('transparent');
                    $('#inMobile').addClass('transparent');
                    $('#inSocial').addClass('transparent');
                    $('#inES').addClass('transparent');
                    $('#inSMS').addClass('transparent');
                    $('#inES-items').hide();
                    $('#inMobile-items').hide();
                    $('#inSocial-items').hide();
                    $('#inSystem-items').show();
                    $('#inSMS-items').hide();
                });
                $('#inMobile').click(function(){
                    if (!redirectResolved())
                        return;

                    $('#pageType').val('mobile');
                    $('#inMobile').removeClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inSocial').addClass('transparent');
                    $('#inES').addClass('transparent');
                    $('#inSMS').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inES-items').hide();
                    $('#inSMS-items').hide();
                    $('#inSocial-items').hide();
                    $('#inMobile-items').show();
                }) ;
                $('#inSocial').click(function(){
                    if (!redirectResolved())
                        return;

                    $('#pageType').val('social');
                    $('#inSocial').removeClass('transparent');
                    $('#inMobile').addClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inES').addClass('transparent');
                    $('#inSMS').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inES-items').hide();
                    $('#inSMS-items').hide();
                    $('#inMobile-items').hide();
                    $('#inSocial-items').show();
                }) ;
                $('#inSMS').click(function(){
                    if (!redirectResolved())
                        return;

                    $('#pageType').val('SMS');
                    $('#inSMS').removeClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inES').addClass('transparent');
                    $('#inMobile').addClass('transparent');
                    $('#inSocial').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inES-items').hide();
                    $('#inMobile-items').hide();
                    $('#inSocial-items').hide();
                    $('#inSMS-items').show();
                }) ;
                $('#inES').click(function(){
                    if (!redirectResolved())
                        return;

                    $('#pageType').val('ES');
                    $('#inES').removeClass('transparent');
                    $('#inMobile').addClass('transparent');
                    $('#inSocial').addClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inSMS').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inMobile-items').hide();
                    $('#inSocial-items').hide();
                    $('#inSMS-items').hide();
                    $('#inES-items').show();
                }) ;

                if ($("#pageType").val() == 'ES')
                {
                    $('#inES').removeClass('transparent');
                    $('#inMobile').addClass('transparent');
                    $('#inSocial').addClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inSMS').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inMobile-items').hide();
                    $('#inSocial-items').hide();
                    $('#inSMS-items').hide();
                    $('#inES-items').show();
                }
                if ($("#pageType").val() == 'mobile')
                {
                    $('#inMobile').removeClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inSocial').addClass('transparent');
                    $('#inES').addClass('transparent');
                    $('#inSMS').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inES-items').hide();
                    $('#inSMS-items').hide();
                    $('#inSocial-items').hide();
                    $('#inMobile-items').show();
                }
                if ($("#pageType").val() == 'social')
                {
                    $('#inSocial').removeClass('transparent');
                    $('#inMobile').addClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inES').addClass('transparent');
                    $('#inSMS').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inES-items').hide();
                    $('#inSMS-items').hide();
                    $('#inMobile-items').hide();
                    $('#inSocial-items').show();
                }
                if ($("#pageType").val() == 'SMS')
                {
                    $('#inSMS').removeClass('transparent');
                    $('#inMobile').addClass('transparent');
                    $('#inSocial').addClass('transparent');
                    $('#inSystem').addClass('transparent');
                    $('#inES').addClass('transparent');
                    $('#inSystem-items').hide();
                    $('#inMobile-items').hide();
                    $('#inSocial-items').hide();
                    $('#inES-items').hide();
                    $('#inSMS-items').show();
                }
            });
        </script>

        <html:hidden property="field(unsavedData)" name="form"/>
        <html:hidden property="field(pageType)" styleId="pageType" name="form"/>
        <div id="profile"  onkeypress="onEnterKey(event);">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Настройки"/>
                <tiles:put name="data">
                    <tiles:insert definition="userSettings" flush="false">
                    <tiles:put name="data">
                        <c:set var="selectedTab" value="securetySettings"/>
                        <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                        <div class="payments-tabs">
                            <c:set var="security" value="true"/>
                            <%@ include file="securityHeader.jsp"%>
                            <div class="greenContainer picInfoBlockSysView">
                                <div>
                                    Для того чтобы изменить настройки видимости Ваших продуктов в «Сбербанк Онлайн», банкоматах, терминалах  и мобильных устройствах, отметьте интересующие Вас продукты и нажмите на кнопку «Сохранить».
                                </div>
                            </div>
                            <div class="clear"></div>
                            <div class="filter triggerFilter" onkeypress="onEnterKey(event);">
                               Продукты доступны:
                                <c:if test="${phiz:impliesServiceRigid('ProductsRCSView')}">
                                    <div class="greenSelector"  id="inSystem" >
                                        <span id="spanInSystem">
                                            <span>
                                                Сбербанк Онлайн
                                            </span>
                                        </span>
                                    </div>
                                </c:if>
                                <c:if test="${phiz:impliesServiceRigid('ProductsSiriusView')}">
                                    <div class="greenSelector transparent"  id="inES">
                                           <span id="spanInES">
                                               <span>
                                                банкоматы, терминалы
                                               </span>
                                           </span>
                                    </div>
                                </c:if>
                                <c:if test="${phiz:impliesServiceRigid('HideProductMobileService')}">
                                    <div class="greenSelector transparent"  id="inMobile" >
                                        <span id="spanInMobile">
                                            <span>
                                                мобильные устройства
                                            </span>
                                        </span>
                                    </div>
                                </c:if>
                                <div class="greenSelector transparent"  id="inSocial" >
                                       <span id="spanInSocial">
                                           <span>
                                            Соц. приложения
                                           </span>
                                       </span>
                                </div>
                                <c:if test="${isERMBConnectedPerson}">
                                    <div class="greenSelector transparent"  id="inSMS">
                                           <span id="spanInSms">
                                               <span>
                                                   СМС
                                               </span>
                                           </span>
                                    </div>
                                </c:if>
                            </div>
                            <div class="filter-wrapper filter-area"></div>
                            <img src="${globalUrl}/commonSkin/images/filter-tooth.gif" class="filter-tooth"/>
                            <div class="clear"></div>

                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="oneTimePasswordWindow"/>
                            </tiles:insert>

                             <%@ include file="accountsViewInSys.jsp"%>
                             <%@ include file="accountsViewInES.jsp"%>
                             <%@ include file="accountsViewInMobile.jsp"%>
                             <%@ include file="accountsViewInSocial.jsp"%>
                             <c:if test="${isERMBConnectedPerson}">
                                <%@ include file="accountsViewInSMS.jsp"%>
                             </c:if>

                        </div>

                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="redirectRefused"/>
                            <tiles:put name="data">
                                 <h2>Внимание!</h2>
                                 Для перехода на другую страницу, пожалуйста, сохраните изменения. Если Вы не хотите сохранять изменения, нажмите на кнопку «Отменить».
                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.close"/>
                                        <tiles:put name="commandHelpKey" value="button.close"/>
                                        <tiles:put name="bundle" value="pfrBundle"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                        <tiles:put name="onclick" value="win.close('redirectRefused');"/>
                                    </tiles:insert>
                                </div>
                            </tiles:put>
                     </tiles:insert>
                   </tiles:put>
               </tiles:insert>
                </tiles:put>
           </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
<script type="text/javascript">
    function checkData()
    {
        var unsavedData = ensureElementByName("field(unsavedData)").value;
        if (unsavedData) return true;
        var changedFieldNames = getChangedFieldNames();
        for (var i = 0; i <changedFieldNames.length; i++)
        {
            var changedFieldName = changedFieldNames[i];
            if ($.inArray(changedFieldName, ['field(pageType)', 'selectedClosedCards', 'selectedClosedAccounts', 'selectedClosedLoans', 'selectedClosedIMAccounts','selectedClosedDepoAccounts']) == -1)
                return true;
        }
        addMessage("Вы не внесли никаких изменений в настройки видимости продуктов.");
        return false;
    }
</script>
</html:form>