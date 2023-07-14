<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest/promo"/>

<html:form action="${actionUrl}">
    <tiles:insert definition="${definitionName}">
        <c:choose>
            <c:when test="${pageType == 'More_SBOL_UDBO'}">
                <tiles:put name="headerGroup" value="true"/>
                <tiles:put name="mainmenu" value="moreSbol"/>
                <tiles:put name="enabledLink" value="false"/>
            </c:when>
            <c:when test="${pageType == 'More_SBOL_LOGIN'}">
                <tiles:put name="pageTitle" type="string" value="�������� ������"/>
                <tiles:put name="headerGroup" value="true"/>
                <tiles:put name="needHelp" value="true"/>
            </c:when>
            <c:when test="${pageType == 'guestPromo'}">
                <c:set var="guestEntryLinkOnRegistration" value="${phiz:getLinkOnSelfRegistrationInCSA()}"/>
                <tiles:put name="mainMenuType" value="guestMainMenu"/>
                <tiles:put name="mainmenu" value="Abilities"/>
            </c:when>
        </c:choose>

        <tiles:put name="data" type="string">
            <div class="b-promo-page marginTop20">

                <h1 class="b-page-title">�������� ������&nbsp;&mdash; ���� ������ ���������� ������������!</h1>
                <p class="b-page-descr">���������� ��������� ���������� ��� ��������� �&nbsp;��������� ����� ����� �������� ������ ��� ������ ��������� ����������, ���-��������, ���������� �&nbsp;���������� �����.</p>

                <div class="b-section b-benefits float" id="SectionBenefits">
                    <h2 class="section_title">�������� ������ ������ ��� �����</h2>
                    <a class="section_anc" href="#SectionRegistration" onclick="return doSmoothScroll(this);">��� ������������</a>
                    <i class="benefits_promo"></i>

                    <div class="section_inner">
                        <ul class="section_list">
                            <li class="section_item">
                                �&nbsp;����� <br/><a target="_blank" href="http://www.sberbank.ru/moscow/ru/person/dist_services/#sectionMobile">��������� ���&nbsp;��������</a>
                            </li>
                            <li class="section_item">
                                �&nbsp;��������� �&nbsp;����������� ������� �&nbsp;������� ������� <a target="_blank"                                                                                                                           href="http://www.sberbank.ru/moscow/ru/person/dist_services/#sectionBank">SMS-���������</a>
                            </li>
                            <li class="section_item">
                                �&nbsp;������� <br/><a target="_blank" href="http://www.sberbank.ru/moscow/ru/person/dist_services/#sectionTerminals">����������
                                �&nbsp;����������</a> <br/>��&nbsp;����� ������
                            </li>
                            <li class="section_item">
                                �&nbsp;����� ������� ���������� ����� �&nbsp;
                                <a target="_blank" href="http://ok.ru/bankdruzey">��������������</a> �&nbsp;
                                <a target="_blank" href="https://vk.com/bankdruzey">���������</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- // b-section -->
                <div class="b-section b-operations" id="SectionOperations">
                    <h3 class="section_title">���&nbsp;�� ��&nbsp;��&nbsp;����������, �������</h3>

                    <div class="b-group">
                        <h4 class="group_title"><span>��������� ���������� ��������</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic1.png" alt=""/>
                                <span class="group_text">�������� ����� ������ �������, �������, ��������</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic2.png" alt=""/>
                                <span class="group_text">���������� ����������� ���������</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic3.png" alt=""/>
                                <span class="group_text">������� ��&nbsp;�������� �&nbsp;��������� ������</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic4.png" alt=""/>
                                <span class="group_text">������� ��&nbsp;�������� �&nbsp;��������� ������</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                    <div class="b-group">
                        <h4 class="group_title"><span>������ ���� �&nbsp;����� ����� ��������</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g2-pic1.png" alt=""/>
                                <span class="group_text">���������� ������ �&nbsp;������� ��&nbsp;���� ������ �&nbsp;������� ��������</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g2-pic2.png" alt=""/>
                                <span class="group_text">���������� �&nbsp;������ �������� ����� &laquo;������� ��&nbsp;���������&raquo;</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                    <div class="b-group">
                        <h4 class="group_title"><span>��������� ���� �����</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g3-pic1.png" alt=""/>
                                <span class="group_text">������ ����� �&nbsp;���� ���� �&nbsp;������� ��������� ���� ��������</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g3-pic2.png" alt=""/>
                                <span class="group_text">�������������� ���������� ������� ��&nbsp;��������, ��������, ������� ����� �&nbsp;������ ������</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                    <div class="b-group">
                        <h4 class="group_title"><span>������������ �&nbsp;���������</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g4-pic1.png" alt=""/>
                                <span class="group_text">������ �&nbsp;���������� ���������� �������</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g4-pic2.png" alt=""/>
                                <span class="group_text">������������� �����</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                </div>
                <!-- // b-section -->

                <div class="b-section b-start" id="SectionRegistration">
                    <h3 class="section_title">������������ �&nbsp;��������� <br/>������ ���������</h3>

                    <div class="section_descr">����������������� �&nbsp;��� <br/>���� �&nbsp;�������� ������</div>

                    <c:choose>
                        <c:when test="${guestEntryLinkOnRegistration != null}">
                            <div class="b-btn">
                                <a class="btn_inner" target="_blank" href="${guestEntryLinkOnRegistration}">������������������</a>
                                <i class="btn_bg"></i>
                            </div>
                            <!-- // b-btn -->
                        </c:when>
                        <c:otherwise>
                            <div class="promoUDBO">
                                <div class="buttonAreaPromo">
                                    <div class="b-btn">
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.connect"/>
                                            <tiles:put name="commandTextKey" value="button.connect"/>
                                            <tiles:put name="commandHelpKey" value="button.connect.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="viewType" value="orangePromo"/>
                                        </tiles:insert>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.notNow"/>
                                            <tiles:put name="commandTextKey" value="button.notNow"/>
                                            <tiles:put name="commandHelpKey" value="button.notNow.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="viewType" value="lightGrayPromo"/>
                                        </tiles:insert>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="clear"></div>

                    <i class="start_promo"></i>
                </div>
                <!-- // b-section -->

                <div class="b-section b-alternate" id="SectionAlternate">
                    <h3 class="section_title">� ���� ��� ��������� ����� ��������� :�(</h3>

                    <div class="b-group">
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g5-pic1.png" alt=""/>
                                <span class="group_text">�������� ����� ��&nbsp;��������� ���� <a target="_blank" href="http://www.sberbank.ru/moscow/ru/person/bank_cards/debet/">��&nbsp;�����
                                    ���������</a></span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g5-pic2.png" alt=""/>
                                <span class="group_text">�������� <a target="_blank" href="http://www.sberbank.ru/moscow/ru/about/today/oib/">�&nbsp;��������� ��������� ���������</a> ���� ��&nbsp;��������� ��� ����� ���������� ������</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                </div>
                <!-- // b-section -->
                <script>
                    function doSmoothScroll(elem)
                    {
                        if (!window.$ || !(window.$ = window.jQuery)) return;
                        var $target = $(elem.href.replace(/.+(#.+)/g, '$1'));
                        $('html,body').animate({scrollTop: $target.offset().top}, 650, function ()
                        {
                            if ($target[0].id) location.hash = $target[0].id;
                        });
                        return false;
                    }
                </script>
            </div>
            <!-- // b-promo-page -->
            <!-- ����� ��������!!! -->
        </tiles:put>
    </tiles:insert>
</html:form>