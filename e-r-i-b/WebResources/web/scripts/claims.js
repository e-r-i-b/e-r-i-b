/**
 * �����: usachev
 * ���� ��������: 15.05.15
 * ��������: ����� ����������� ��� ������ �� ������� ������ �� ����� � �������.
 */
function Claims()
{
    var claims = this;

    claims.MESSAGES = {
        show_all_claims: "�������� ��� ������",
        rollUp: "��������",
        errorOnServer: "�������� �������� �� ��������. ����������, ��������� ������� �����."
    };

    claims.SERVICE_MARKERS = {
        class_show: "gp-show",
        class_hide: "gp-hide",
        class_lastClaims: "lastClaims",
        marker: "markerAccept"
    };

    //������� ����������� ����� � ��������
    claims.STATE = {
        hide: 0, //���� ������
        show: 1  //���� ��������
    };

    /**
     * ������������� ����� � ��������
     * @param url ����� �� ������� ����������� ������ ������
     * @param minSize ����������� ������ ������ ��� ����������� ������������
     */
    claims.init = function (url, minSize)
    {
        claims.urlForGetData = url;
        claims.minListClaimsSize = minSize;
        claims.buttonHideShow = $("#buttonHideShow");
        claims.testOfButton = $("#textOfButtonHideShow");
        claims.state = claims.STATE.hide;
        claims.buttonHideShow.bind("click", claims.action);
        claims.lock = false;
    };

    /**
     * ������������� ����� � �������� � ������, ���� ������ ������ ������ ��� �������� �� ��������
     * @param minSize ����������� ������ ������ ��� ����������� ������������
     */
    claims.initWithFullData = function (minSize)
    {
        claims.urlForGetData = null;
        claims.listClaims = $(".gp-claim");
        claims.minListClaimsSize = minSize;
        claims.buttonHideShow = $("#buttonHideShow");
        claims.testOfButton = $("#textOfButtonHideShow");
        claims.state = claims.STATE.hide;
        claims.buttonHideShow.bind("click", claims.action);
        claims.lock = false;

        $(".gp-claim:last").addClass(claims.SERVICE_MARKERS.class_lastClaims);
        claims.rollUp();
    };

    /**
     * ����� ���������. ���������� ��� ������, ���� ��� ������. � ��������, ���� ��� �������.
     */
    claims.action = function ()
    {
        if (!claims.lock)
        {
            claims.lock = true;
            if (claims.state != claims.STATE.show)
            {
                claims.showAllClaims();
            }
            else
            {
                claims.rollUp();
            }
            claims.lock = false;
        }

    };

    /**
     * �������� ��������� ����� ������
     * @param addClass ����� ��� ���������� � ������
     * @param removeClass ����� ��� �������� � ������
     * @param message ����� ��� ������
     * @param state ��������� ����� �����
     */
    claims.changeVisibleState = function (addClass, removeClass, message, state)
    {
        claims.testOfButton.html(message);
        claims.buttonHideShow.removeClass(removeClass);
        claims.buttonHideShow.addClass(addClass);
        claims.state = state;
    };

    /**
     * ��������� ���� � ��������� "�������"
     */
    claims.hideState = function(){
        claims.changeVisibleState(claims.SERVICE_MARKERS.class_hide, claims.SERVICE_MARKERS.class_show, claims.MESSAGES.show_all_claims, claims.STATE.hide);
    };

    /**
     * ��������� ���� � ��������� "���������"
     */
    claims.showState = function(){
        claims.changeVisibleState(claims.SERVICE_MARKERS.class_show, claims.SERVICE_MARKERS.class_hide, claims.MESSAGES.rollUp, claims.STATE.show);
    };

    /**
     * ���������� ������ � �������
     * @param res ����� �������
     */
    claims.callback = function (res)
    {
        var answer = $.trim(res);
        if (answer.indexOf(claims.SERVICE_MARKERS.marker) > -1)
        {
            $(".gp-claims").html(res);
            claims.listClaims = $(".gp-claim");
            $(".gp-claim:last").addClass(claims.SERVICE_MARKERS.class_lastClaims);
            claims.showState();
        }
        else
        {
            addError(claims.MESSAGES.errorOnServer);
        }
    };

    /**
     * �������� ��� ������. ��� ������ ��������� ����� ������ ������ �� ������ � �������� �����. �� ��� ����������� ������ ������ ������� �� ����
     */
    claims.showAllClaims = function ()
    {
        if (claims.listClaims == null)
        {
            removeError(claims.MESSAGES.errorOnServer);
            ajaxQuery("", claims.urlForGetData, claims.callback, null, true);
        }
        else
        {
            for (var i = 0; i < claims.listClaims.size(); i++)
            {
                if (i == claims.minListClaimsSize - 1)
                {
                    claims.listClaims.eq(i).removeClass(claims.SERVICE_MARKERS.class_lastClaims);
                }
                else if (i > claims.minListClaimsSize - 1)
                {
                    claims.listClaims.eq(i).show();
                }
            }
            claims.showState();
        }

    };

    /**
     * ������ ��� ������
     */
    claims.rollUp = function ()
    {
        for (var i = 0; i < claims.listClaims.size(); i++)
        {
            if (i == claims.minListClaimsSize - 1)
            {
                claims.listClaims.eq(i).addClass(claims.SERVICE_MARKERS.class_lastClaims);
            }
            else if (i > claims.minListClaimsSize - 1)
            {
                claims.listClaims.eq(i).hide();
            }
        }
        claims.hideState();
    };
}

var claims = new Claims();

