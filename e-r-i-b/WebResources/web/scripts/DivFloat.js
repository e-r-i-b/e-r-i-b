// ����� ��� ����������� �������� ����������� ������ �� ������ �� ������� ��������
function DivFloat(divId, topLimit, topMargin)
{
    // ������� ������� �����
    this.topLimit = (topLimit == null)?63:topLimit;
    // ������ ������
    this.topMargin = (topMargin == null)?12:topMargin;
    // ������ ��� �������
    this.obj;
    // ������
    this.floatTimer;
    // ID div`a
    this.id = divId;

    // ������� ������������� ���������� ��������� ����� �������� DOM
    this.init = function (self)
    {
        if (self == null) self = this;
        self.obj = ensureElement(self.id);
        self.move();
    };
    // ����� �������������� ����� �������� ����������
    this.floatEffect = function  (from, to, self)
    {
        if (self == null) self = this;
        var hep = (to - from) / 8;
        //������ �� ������������
        if (hep < 5 && hep > 0 ) hep = 5;
        if (hep < 0 && hep > -5 ) hep = -5;

        var halfTo = from + hep;
        // ������ ����������� ��������
        if (halfTo > to && hep > 0 || halfTo < to && hep < 0)
            halfTo = to;
        self.obj.style.top = halfTo + "px";
        if (hep > 0 && halfTo < to || hep < 0 && halfTo > to)
            this.floatTimer = setTimeout(function ()
            {
                self.floatEffect(halfTo, to, self);
            }, 20);
    };
    // ����� ��� ����������� �������� ���� � ������������� �����
    this.move = function ()
        {
           if ( !(this.obj != undefined && this.obj != null)) return ;
           // ������� ������ �� ������ ���������
           var scroll= getScrollTop();
           // ������ ���� ��������
           var screen = screenSize();
           // ��������� �������� ��������� �����
           var fromPx = this.topLimit;
           if (this.obj.style.top != '')
           {
               var reg = /\d*/
               fromPx = parseInt( reg.exec(this.obj.style.top) );
           }
           // ���� ������ ������ ������ ������� �����, �� ��������� ��� �� �����
           var height = this.obj.offsetHeight;
            if (height + this.topMargin >= screen.h)
              scroll = fromPx-this.topMargin;
            // ���� ������ �� ������ ��������� ������ ������ �� ��������� ��� �� ������
            if (scroll < this.topLimit) scroll = this.topLimit - this.topMargin;

            var toPx = scroll+this.topMargin;
            //���� ��������� �� ��������� �� �� ��������� ����� ����������
            if( fromPx != toPx);
            {
            clearTimeout(this.floatTimer);
            this.floatEffect (fromPx, toPx, this);
            }

        };
    // ����� ��� �������� ������� � ������� ������
    this.doOnScroll = function ()
    {
        var scrollTimer;
        var scroll = window.onscroll;
        var self = this;

        window.onscroll = function () {
            if (scroll!=null) scroll();
            clearTimeout(scrollTimer)
            scrollTimer = setTimeout(function(){ self.move() }, 50); 
        };
    }
    // ������ ������������� ����� ���������� �������� ���������
    if (typeof(divId) != 'string')
        this.obj = divId;
    else
        doOnLoad(this.init, this);
}