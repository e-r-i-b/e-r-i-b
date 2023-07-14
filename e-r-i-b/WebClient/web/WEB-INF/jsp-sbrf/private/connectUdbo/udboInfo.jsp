<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:importAttribute/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<div class="connectUDBO">
    <div class="titleForm">���������� ��� ����������� <br/><span class="noWrap">�������� ������</span></div>
    <p class="titleDesc">��� ������ �������� ������, �������, ������������� ����� � ��� ��������� ������
        �������� ��������� � �������� ���������. �������������!</p>

    <div class="promoUDBO">
        <img width="669" height="279" src="${image}/connectUDBO.png" alt=""/>
        <div class="buttonAreaPromo">
            <div class="centerBtn">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"        value="button.connect"/>
                    <tiles:put name="commandTextKey"    value="button.connect"/>
                    <tiles:put name="commandHelpKey"    value="button.connect.help"/>
                    <tiles:put name="bundle"            value="commonBundle"/>
                    <tiles:put name="viewType"          value="orangePromo"/>
                </tiles:insert>
            </div>
            <div class="rightBtn">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"        value="button.notNow"/>
                    <tiles:put name="commandTextKey"    value="button.notNow"/>
                    <tiles:put name="commandHelpKey"    value="button.notNow.help"/>
                    <tiles:put name="bundle"            value="commonBundle"/>
                    <tiles:put name="viewType"          value="lightGrayPromo"/>
                </tiles:insert>
            </div>
        </div>
    </div>
    <div class="clear"></div>

    <div class="accomplishGoal">
        <p class="promoMotivation">�� ������� ����� � ������</p>

        <div class="promoGoals">
            <div id="goalAccounts" class="goalsType">
                <p class="goalTitle">������� �������������� <br />���� ��� ����� ������</p>
                <p class="goalDesc">��� ������ �������� <br />������-������ �� ����� <br />�������� ���������� �������</p>
            </div>
            <div id="goalTarget" class="goalsType">
                <p class="goalTitle">������� ������������ <br />����</p>
                <p class="goalDesc">�������� �� ���, ������ ��� ����������� ������ ������� <br />������� ����� � �������</p>
            </div>
            <div id="goalIma" class="goalsType">
                <p class="goalTitle">��������� ���������� <br />� ������������� ������</p>
                <p class="goalDesc">������������� ����� �������� ���������� �� �������� � ���� ����������� �������� ����� �� <br />���� ����� ��������� <br />����������� ��������.</p>
            </div>
            <div id="goalLoan" class="goalsType">
                <p class="goalTitle">����� ���������� <br />������ ������</p>
                <p class="goalDesc">�� ������� �������� ������ <br />��� ������ � ��������� <br />���������.</p>
            </div>
            <div id="goalPfr" class="goalsType">
                <p class="goalTitle">���������� ���������� <br />���������, �������� <br />����������</p>
                <p class="goalDesc">�������������� ��������<br /> ���������� �������� ����� �����<br /> ��������� ��������� �������.</p>
            </div>
        </div>
    </div>
</div>