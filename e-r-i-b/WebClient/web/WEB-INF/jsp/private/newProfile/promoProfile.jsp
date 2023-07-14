<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="imagePath" value="${globalUrl}/commonSkin/images/profile"/>
<c:set var="uploadAvatarAccess" value="${phiz:impliesOperation('UploadAvatarOperation', '')}"/>
<c:set var="viewPaymentsBasketAccess" value="${phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment')}"/>
<c:set var="addressBookAccess" value="${phiz:impliesOperation('ViewAddressBookOperation', 'AddressBook')}"/>
<c:set var="promoCodeAccess" value="${phiz:impliesService('ClientPromoCode') or phiz:impliesService('CreatePromoAccountOpeningClaimService') or phiz:impliesService('ShowClientPromoCodeList')}"/>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/tutorial.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var tutorial = new Tutorial({
            showTrigger: '.showTutorial',
            tutorialElem: '#SbolTutorial',
            removeTrigger: '.closePromo'
            <c:if test="${phiz:isPromoState('SHOWING')}">
                , showOnInit: true
            </c:if>
        });
        callAjaxActionMethod('${url}', 'setPromoMinimized');
    });

    function showMinimizedPromo()
    {
        $('.promo-minimized').css('display', '');
    }

    function showAvatarLoadButton()
    {
        <c:if test="${uploadAvatarAccess}">
            <c:choose>
                <c:when test="${form.hasAvatar}">
                    $('.grayProfileButton').show();
                </c:when>
                <c:otherwise>
                    $('.whiteProfileButton').show();
                </c:otherwise>
            </c:choose>
        </c:if>
    }

    function hideAvatarLoadButton()
        {
            <c:if test="${uploadAvatarAccess}">
                <c:choose>
                    <c:when test="${form.hasAvatar}">
                        $('.grayProfileButton').css('display', '');
                    </c:when>
                    <c:otherwise>
                        $('.whiteProfileButton').css('display', '');
                    </c:otherwise>
                </c:choose>
            </c:if>
        }
</script>

<div id="SbolTutorial" class="b-tutorial" style="display: none;">
    <div class="tutorial_inner">
        <div class="tutorial_left-bg"></div>
        <div class="tutorial_right-bg"></div>
        <div class="tutorial_steps">

            <div class="tutorial_step tutorial_step-hello">
                <img class="tutorial_step-image" src="${imagePath}/step_hello.png"/>
                <div class="tutorial_bg"></div>
            </div>

            <div class="tutorial_step tutorial_step-1">
                <img class="tutorial_step-image" src="${imagePath}/step1.png"/>
                <div class="tutorial_step-content">
                    <h1>�������� ���� <br>������ �������</h1>
                    <p>� �������� ���� ������ ������� <br>
                        <c:choose>
                            <c:when test="${viewPaymentsBasketAccess or addressBookAccess}">
                                ��������� ����� �������:
                            </c:when>
                            <c:otherwise>
                                �������� ����� ������:
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <ul>
                        <li>� ������ ����������</li>
                        <c:if test="${addressBookAccess}">
                            <li>� �������� �����</li>
                        </c:if>
                        <c:if test="${viewPaymentsBasketAccess}">
                            <li>� ��������� ������ � ������</li>
                        </c:if>
                    </ul>
                </div>
                <div class="tutorial_bg"></div>
            </div>

            <c:if test="${uploadAvatarAccess}">
                <div class="tutorial_step tutorial_step-2">
                    <img class="tutorial_step-image" src="${imagePath}/step2.png"/>
                    <div class="tutorial_step-content">
                        <div class="tutorial_about">������ ����������</div>
                        <h1>��� ������ �������</h1>
                        <p>��������� ���� ����, �&nbsp;����� ���� <br>�������� ����� ������ ��� �&nbsp;����� <br>�������� ������.
                        </p>
                    </div>
                    <div class="tutorial_bg"></div>
                </div>
            </c:if>

            <div class="tutorial_step tutorial_step-3">
                <c:choose>
                    <c:when test="${not empty form.personDocumentList}">
                        <img class="tutorial_step-image" src="${imagePath}/step3.png"/>
                    </c:when>
                    <c:otherwise>
                        <img class="tutorial_step-image" src="${imagePath}/step3_1.png"/>
                    </c:otherwise>
                </c:choose>

                <div class="tutorial_step-content">
                    <div class="tutorial_about">������ ����������</div>
                    <h1>���� ��������� � �������������� </h1>
                    <p>��� ������ ��&nbsp;�������� ������ ��� ������ �������� ����� ��������, ���������� ���������� ��� ���&nbsp;������
                       ��������� ������������� � ��������� ������ ����� ������� ������� ���� ������ �&nbsp;������������ �&nbsp;������
                       ������. ������ ��������������� ����� ������ ��� ��� ������� �&nbsp;�������.</p>
                    <h2>��������� �������� � �������, �� ������������ �� �����!</h2></div>
                <div class="tutorial_bg"></div>
            </div>

            <c:if test="${addressBookAccess}">
                <div class="tutorial_step tutorial_step-4">
                    <img class="tutorial_step-image" src="${imagePath}/step4.png">
                    <div class="tutorial_step-content">
                        <h1>�������� �����</h1>
                        <p>������ �&nbsp;��� ���� ������� ������ <br>��&nbsp;���� ����� ��������� � <br>���������� ������ ������� <br>�&nbsp;��������� �������!</p>
                    </div>
                    <div class="tutorial_bg"></div>
                </div>
            </c:if>

            <c:if test="${viewPaymentsBasketAccess}">
                <div class="tutorial_step tutorial_step-5 <c:if test='${promoCodeAccess}'>havePromoCode</c:if>">  <%--��������� ����������� �����-�����--%>
                    <c:if test="${addressBookAccess}">
                        <div class="tutorial_bg_top_filler"></div>
                    </c:if>
                    <img class="tutorial_step-image" src="${imagePath}/step5.png"/>
                    <c:set var="textClassName" value="tutorial_step-content"/>
                    <c:if test="${addressBookAccess}">
                        <c:set var="textClassName" value="tutorial_step-content tutorial_step-5_textIndent"/>
                    </c:if>
                    <div class="${textClassName}">
                        <h1>����� ������������ ������</h1>
                        <p>��������� ������ �������� �������� ������������ ����� �&nbsp;�������� ���������� ��&nbsp;����. �������
                           �� ������ ��&nbsp;�������� ������� �������� ������������ ������, ������ ��� ����� ��&nbsp;�������� �&nbsp;������.
                           ����������� ��������� ��&nbsp;��� ��������.</p>
                        <h2>���������� ������� ��������� �&nbsp;�������������!</h2>
                        <a class="tutorial_link-btn" href="#" onclick="showMinimizedPromo()"><span>������� � ������ ����������</span><i></i></a>
                    </div>
                    <div class="tutorial_bg"></div>
                </div>
            </c:if>

        </div>
    </div>
    <div class="tutorial_cursor">
        <div class="tutorial_cursor-inner">������</div>
    </div>
    <a class="tutorial_exit" title="�������" onclick="showMinimizedPromo()"></a>
</div><!-- // b-tutorial -->
