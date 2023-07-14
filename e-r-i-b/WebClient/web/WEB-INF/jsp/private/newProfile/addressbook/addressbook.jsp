<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images/profile/"/>
<c:set var="dictionary" value="${not empty param['dictionary']}"/>
<c:set var="bundle" value="userprofileBundle"/>
<html:form action="/private/userprofile/addressbook">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="showLinkConfirmContactAndroid" value="${phiz:isShowLinkConfirmContactAndroid()}"/>
    <tiles:insert definition="${dictionary ? 'empty' : 'newUserProfile'}">
        <tiles:put name="data" type="string">

            <tiles:insert definition="${dictionary ? 'empty' : 'profileTemplate'}" flush="false">
                <c:if test="${not dictionary}">
                    <tiles:put name="activeItem">addressbook</tiles:put>
                    <tiles:put name="title">
                        <bean:message key="menu.addressbook" bundle="userprofileBundle"/>
                    </tiles:put>
                </c:if>
                <tiles:put name="data">
                    <c:if test="${dictionary}">
                        <div id="workspace">
                            <span class="contactMainTitle"><bean:message key="menu.addressbook" bundle="userprofileBundle"/></span>
                            <div id="loading" style="left:-3300px;">
                                <div id="loadingImg"><img src="${skinUrl}/images/ajax-loader64.gif" alt=""/></div>
                                <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
                            </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${empty form.contactList}">
                            <script type="text/javascript">
                               resizeWinTo();
                                function resizeWinTo() {
                                    this.resizeTo(800, 930);
                                    this.getElementById('emptyAddressBook').style.left = '0';
                                    this.moveTo(screen.width / 2 - 410, 0);
                                }
                            </script>
                            <c:if test="${not dictionary}">
                                <div class="nocontact">
                                    <bean:message key="text.addressbook.nocontact" bundle="userprofileBundle"/>
                                </div>
                            </c:if>
                            <div class="emptyAddressBook" id="emptyAddressBook">
                                <div class="t1"><bean:message key="text.addressbook.t1" bundle="userprofileBundle"/></div>
                                <div class="t2"><bean:message key="text.addressbook.t2" bundle="userprofileBundle"/></div>
                                <div class="t3"><bean:message key="text.addressbook.t3" bundle="userprofileBundle"/></div>
                                <div class="t4"><bean:message key="text.addressbook.t4" bundle="userprofileBundle"/></div>
                                <div class="t5"><bean:message key="text.addressbook.t5" bundle="userprofileBundle"/></div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/addressbook.js"></script>

                            <div class="contactManagement <c:if test="${dictionary}">filterContact</c:if>">
                                <div class="contactSearch float">
                                    <div class="CSleft">
                                        <div class="CSright">
                                            <div class="CSinput">
                                                <c:set var="lbname"><bean:message key="label.searchby.name" bundle="userprofileBundle"/></c:set>
                                                <c:set var="lbphone"><bean:message key="label.searchby.phone" bundle="userprofileBundle"/></c:set>

                                                <div class="float searchField">
                                                    <input type="text" name="searchByName" id="searchByName" class="searchByName hintForSearchField" onkeydown="hintForSearchByName.onChange('', event);" value="${lbname}"/>
                                                    <div class="cancelSearch" id="cs1" onclick="cancelSearch(false);"></div>
                                                </div>
                                                <div class="float searchField">
                                                    <input type="text" name="searchByPhone" id="searchByPhone" class="float searchByPhone hintForSearchField" onkeydown="hintForSearchByPhone.onChange('', event);" value="${lbphone}"/>
                                                    <div class="cancelSearch" id="cs2" onclick="cancelSearch(true);"></div>
                                                </div>
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.search"/>
                                                    <tiles:put name="commandHelpKey" value="button.search"/>
                                                    <tiles:put name="bundle" value="userprofileBundle"/>
                                                    <tiles:put name="onclick" value="startSearch();"/>
                                                    <tiles:put name="viewType" value="buttonGreen"/>
                                                </tiles:insert>
                                                <div class="clear"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <c:if test="${not dictionary}">

                                    <div class="floatRight sortChooser">
                                        <div id="sortChooserInactive" class="css3" onclick="openSortOrder();">
                                            <div class="sortChooserInactiveVal"></div>
                                            <div class="sortChooserRow"></div>
                                        </div>
                                        <div id="sortChooserActive" class="css3">
                                            <div class="selectedItem" id="byAlphabet" onclick="closeSortOrder(true);">
                                                <bean:message key="label.sortby.alphabet.full" bundle="userprofileBundle"/>
                                                <div class="sortChooserRow"></div>
                                            </div>
                                            <div class="selectedItem" id="byPopularity" onclick="closeSortOrder(false);">
                                                <bean:message key="label.sortby.popularity.full" bundle="userprofileBundle"/>
                                                <div class="sortChooserRow"></div>
                                            </div>
                                        </div>
                                    </div>


                                </c:if>
                                <div class="clear"></div>
                            </div>

                            <c:if test="${dictionary}"><div class="autoScrolContainer"><div class="autoScrollBox"> </c:if>
                                <div id="bookmarkCntct" class="contactGroup <c:if test="${dictionary}">contactGroupDictionary</c:if>">
                                    <div class="contactTitle">
                                        <div class="whiteBack">
                                            <bean:message key="label.addressbook.bookmark" bundle="userprofileBundle"/>
                                            <span class="bookmarkCntct"></span>
                                        </div>
                                    </div>
                                </div>
                                <div id="otherCntct" class="other-contacts contactGroup <c:if test="${dictionary}">contactGroupDictionary</c:if>">
                                    <div class="contactTitle">
                                        <div class="whiteBack">
                                            <bean:message key="label.addressbook.other" bundle="userprofileBundle"/>
                                        </div>
                                    </div>
                                </div>
                                <div id="deletedCntct" class="contactGroup canHide <c:if test="${dictionary}">contactGroupDictionary</c:if>">
                                    <div class="contactTitle" onclick="showHideDeleted();">
                                        <div class="whiteBack">
                                            <span class="deletedCnt"></span><bean:message key="label.addressbook.deleted" bundle="userprofileBundle"/>
                                        </div>
                                    </div>
                                    <div id="deletedCntctInner"></div>
                                </div>

                            <c:if test="${dictionary}"></div></div></c:if> <%--end .autoScrollBox--%>
                            <c:if test="${dictionary}"> <div class="hiddenBox"></div></c:if>
                            <script type="text/javascript">
                                var hintForSearchByName = new HintSetter(startSearch, "", "searchByName", "${lbname}", "hintForSearchField", function (show) {if (show) $("#cs1").show(); else $("#cs1").hide();});
                                var hintForSearchByPhone = new HintSetter(startSearch, "", "searchByPhone", "${lbphone}", "hintForSearchField", function (show) {if (show) $("#cs2").show(); else $("#cs2").hide();});

                                function startSearch()
                                {
                                    var nameEl = $("#searchByName").val();
                                    if (nameEl == hintForSearchByName.hintText)
                                    {
                                        nameEl = "";
                                    }

                                    var ids = contactListManager.search(nameEl);

                                    var phoneEl = $("#searchByPhone").val();
                                    if (phoneEl == hintForSearchByPhone.hintText || phoneEl == "")
                                    {
                                    }
                                    else
                                    {
                                        ajaxQuery("ids="+ids+"&phone="+phoneEl, "/PhizIC/private/userprofile/addressbook/async/searchContact.do", function(data) {
                                            if(!data.error)
                                            {
                                                contactListManager.showOnly(data.ids);
                                            }
                                            else
                                            {
                                                cancelSearch(true);
                                            }
                                        }, "json");
                                    }
                                }

                                function cancelSearch(byPhone)
                                {
                                    if (byPhone)
                                    {
                                        $("#searchByPhone").val("");
                                        hintForSearchByPhone.onChange('', {"keyCode":-500,"which":-500});
                                    }
                                    else
                                    {
                                        $("#searchByName").val("");
                                        hintForSearchByName.onChange('', {"keyCode":-500,"which":-500});
                                    }

                                    startSearch();
                                }

                                function openSortOrder()
                                {
                                    $("#sortChooserInactive").hide();
                                    $("#sortChooserActive").show();

                                    if (contactListManager.sortByAlphabet)
                                    {
                                        $("#byAlphabet").addClass("select");
                                        $("#byPopularity").removeClass("select");
                                    }
                                    else
                                    {
                                        $("#byAlphabet").removeClass("select");
                                        $("#byPopularity").addClass("select");
                                    }

                                    // поставить выбранный на 1-ое место
                                    var $selected = $('#sortChooserActive .selectedItem.select').clone();
                                    $('#sortChooserActive .selectedItem.select').remove();
                                    $('#sortChooserActive').prepend($selected);

                                }

                                function closeSortOrder(byAlphabet)
                                {
                                    contactListManager.sort(byAlphabet);
                                    $(".sortChooserInactiveVal").html(byAlphabet ? "<bean:message key="label.sortby.alphabet" bundle="userprofileBundle"/>" : "<bean:message key="label.sortby.popularity" bundle="userprofileBundle"/>");

                                    $("#sortChooserInactive").show();
                                    $("#sortChooserActive").hide();

                                }

                                var contact;
                                <c:forEach var="contact" items="${form.contactList}">
                                    contact = new ABContact();<%

                                    %>contact.id = ${contact.id};<%
                                    %>contact.phone = "<c:out value="${phiz:getCutPhoneForAddressBook(contact.phone)}"/>";<%
                                    %>contact.fullName = "<c:out value="${contact.fullName}"/>";<%
                                    %>contact.sberbankClient = ${contact.sberbankClient};<%
                                    %>contact.incognito = ${contact.incognito};<%
                                    %>contact.alias = "<c:out value="${contact.alias}"/>";<%
                                    %>contact.avatarPath = "${contact.avatarPath}";<%
                                    %>contact.isShowLinkConfirmContactAndroid = ${showLinkConfirmContactAndroid};<%
                                    %><c:set var="avatarPath">
                                        <div class="float fixAvatarBlock <c:if test="${contact.yandexContact}">yandexContact</c:if>">
                                            <div class="relative">
                                                <tiles:insert definition="userImage" flush="false">
                                                    <tiles:put name="selector" value="SMALL"/>
                                                    <tiles:put name="imagePath" value="${contact.avatarPath}"/>
                                                    <c:choose>
                                                        <c:when test="${contact.yandexContact}">
                                                            <tiles:put name="imgStyle" value="float avatarIcon"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tiles:put name="imgStyle" value="float avatarIcon css3"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <tiles:put name="yandexContact" value="${contact.yandexContact}"/>
                                                </tiles:insert>
                                                <c:choose>
                                                    <c:when test="${contact.sberbankClient and not empty contact.cardNumber}">
                                                        <div class="sberbankClientCard" id="clnSbbnk${contact.id}"></div>
                                                    </c:when>
                                                    <c:when test="${contact.sberbankClient}">
                                                        <div class="sberbankClient" id="clnSbbnk${contact.id}"></div>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                        				</div>
                                    </c:set><%
                                    %>contact.avatarBlock = "${phiz:escapeForJS(avatarPath, true)}";<%
                                    %>contact.cardNumber = "${phiz:getCutCardNumber(contact.cardNumber)}";<%
                                    %>contact.cardType = "${phiz:substring(contact.cardNumber, 0, 1)}";<%
                                    %>contact.category = "${contact.category}";<%
                                    %>contact.trusted = ${contact.trusted};<%
                                    %>contact.frequencyP2P = ${contact.frequencypP2P};<%
                                    %>contact.status = "${contact.status}";<%
                                    %><c:set var="detailInfo">
                                            <tiles:insert definition="roundBorder" flush="false">
                                                <tiles:put name="color" value="shadow"/>
                                                <tiles:put name="data">
                                                <div class="contactDetail">
                                                    <div class="fixAvatarBlock info <c:if test="${contact.yandexContact}">yandexContact</c:if>">
                                                        <div class="relative">
                                                            <tiles:insert definition="userImage" flush="false">
                                                                <tiles:put name="selector" value="SMALL"/>
                                                                <tiles:put name="imagePath" value="${contact.avatarPath}"/>
                                                                <c:choose>
                                                                    <c:when test="${contact.yandexContact}">
                                                                        <tiles:put name="imgStyle" value="float avatarSmall"/>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <tiles:put name="imgStyle" value="float avatarSmall css3"/>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <tiles:put name="yandexContact" value="${contact.yandexContact}"/>
                                                            </tiles:insert>
                                                            <c:if test="${contact.sberbankClient}">
                                                                <div class="sberbankClient" id="clnSbbnk${contact.id}"></div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                    %%---%%
                                                </div>
                                                </tiles:put>
                                            </tiles:insert>
                                        </c:set>
                                        contact.detailInfo = "${phiz:escapeForJS(detailInfo, true)}";<%
                                    %>contact.create(document, ${dictionary});<%
                                %></c:forEach>

                                doOnLoad(function()
                                {
                                    showHideDeleted();
                                    <c:choose>
                                        <c:when test="${not dictionary}">
                                            $("#pageContent")[0].setAttribute("onclick", "contactListManager.hideAllContacts();");
                                            $("#footer")[0].setAttribute("onclick", "contactListManager.hideAllContacts();");
                                        </c:when>
                                        <c:otherwise>
                                            $(document).find("body").css({"overflow-x":"hidden","overflow-y":"hidden"});
                                        </c:otherwise>
                                    </c:choose>
                                    $("#cs1").hide();
                                    $("#cs2").hide();
                                    closeSortOrder(true);
                                });
                            </script>
                        </c:otherwise>
                    </c:choose>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="oneTimePasswordWindow"/>
                        <tiles:put name="styleClass" value="confirmTrustWin"/>
                    </tiles:insert>
                    <c:if test="${dictionary}"></div></c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>