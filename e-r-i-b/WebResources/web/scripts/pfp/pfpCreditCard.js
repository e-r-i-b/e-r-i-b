/**
 * ���������� �� ��������� �����
 * @param programmImgSrc ������ ���������
 * @param description ������� ���������
 * @param recommendation ������������ �� ����������� �������
 * @param type - ��� ���������
 * @param cardDescription - �������� ��������
 * @param blockId ������������� ������� � ������� ����� ��������
 */
function pfpCreditCard(programmImgSrc, description, recommendation, type, cardDescription, blockId)
{
    this.programmImgSrc= programmImgSrc;
    this.description = description;
    this.recommendation = recommendation;
    this.cardDescription = cardDescription;
    this.blockId = blockId;
    this.pfpDiagram = null;

    this.createHtml = function(paramsColumn, chartData)
    {
        var block = $('#'+ this.blockId);

        if(type == 'empty')
        {
            block.find('.logo').empty();
            block.find('.cardRecommendation .recommendationTitle').empty();
            block.find('.cardRecommendation .recommendationDescription').empty();
            block.find('.information').empty();
        }

        if(paramsColumn)
        {
            block.find('.logo').html($('<img />').attr('src', this.programmImgSrc));
            block.find('.cardRecommendation .recommendationTitle').html(this.description);
            block.find('.cardRecommendation .recommendationDescription').html(this.cardDescription);
            if(this.recommendation)
                block.find('.information').html(this.recommendation);
            $('#creditCardsGraph').show();
            if(!this.pfpDiagram)
                this.pfpDiagram = new diagram(paramsColumn, 'creditCardsGraph', 'column', chartData);
            this.pfpDiagram.init(chartData);

        }
        else
        {
            $('#creditCardsGraph').hide();
        }
    };
}