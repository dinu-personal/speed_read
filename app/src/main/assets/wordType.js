onload = getText;

function getText(){
    JSInterface.getTextJs()
}

function countWords() {
    var value = $('#div_content').text();

    if (value.length == 0) {
        return 0;
    }

    var regex = /\s+/gi;
    var wordCount = value.trim().replace(regex, ' ').split(' ').length;

//     var wordCount = value.trim().split('and').length;

    return wordCount;
}


function addNewTextContent(textContent, fontSize, fontStyle){

    $('#div_content').text(textContent);

    fontSize = 1.5 + ((fontSize-5)*0.1)
    $('#div_content').css("font-size", fontSize + "em");
    if(fontSize >6){
        $('#div_content').css("text-shadow", '0 0 12px #808080');
    }
    else{
        $('#div_content').css("text-shadow", '0 0 8px #808080');
    }

    if(fontStyle == 1){
        $('#div_content').css("font-family", '"Arial Black", Gadget, sans-serif');
    }
    else if(fontStyle == 2){
        $('#div_content').css("font-family", '"Times New Roman", Times, serif');
    }
    else if(fontStyle == 3){
        $('#div_content').css("font-family", '"Comic Sans MS", "Comic Sans", cursive');
    }

    JSInterface.sendWordCountAndLineCountJs(countWords(),getNoOfLines_wordType());
}





//////////////////////////////////////////////////
//segmentarion word by word



var speedInSec__wordType = 0;
var startIndex__wordType = 0;
var highlightLength__wordType = 0;
var indices__wordType = [];
var count__wordType = -1;
var word_count_js__wordType = 0;
var intervalFunction__wordType;
var isLast_worType_highlighted = false;

function startHighlighting__wordType(wordsPerWindow, singleWindowduratrion){

    isLast_worType_highlighted = false;

    word_count_js__wordType = wordsPerWindow;
    speedInSec__wordType = singleWindowduratrion;

    var text = $('#div_content').text();
    for(var i=0; i<text.length;i++) {
        if (text[i] === " ") indices__wordType.push(i);
    }

    highlightWithTime__wordType();

    intervalFunction__wordType = setInterval(highlightWithTime__wordType, speedInSec__wordType);
}

function highlightWithTime__wordType(){
console.log("count__wordType 11 "+count__wordType)

    if(isLast_worType_highlighted){
        stopHighlighting__wordType();
        JSInterface.finishHighlightingAndShowPanelJs();
        return 0;
    }

    if(count__wordType == -1){
        highlightLength__wordType = indices__wordType[+word_count_js__wordType + -1];
        var text = $('#div_content').text();
        var leftPart = '<span id="readingSpan" class="notBlur">' + text.substr(0, highlightLength__wordType) + '</span>';
        var rightPart = text.substr(highlightLength__wordType, text.length);
        $('#div_content').html( leftPart + rightPart );
        count__wordType = +word_count_js__wordType + -1 ;
        console.log("count__wordType 22 "+count__wordType)
        ScrollScreen_wordtype();
        JSInterface.updateWhenWindowMoveJs();
        console.log("count__wordType 33 "+count__wordType)
    }
    else if(indices__wordType.length > (+count__wordType + +word_count_js__wordType)){

        startIndex__wordType =indices__wordType[count__wordType];
        highlightLength__wordType = indices__wordType[+count__wordType + +word_count_js__wordType] - indices__wordType[count__wordType];
        var text = $('#div_content').text();
        var leftPart = text.substr(0, startIndex__wordType);
        var middlePart = '<span id="readingSpan" class="notBlur">' + text.substr(startIndex__wordType, highlightLength__wordType) + '</span>';
        var rightPart = text.substr((+startIndex__wordType + highlightLength__wordType), text.length);
        $('#div_content').html( leftPart + middlePart + rightPart );
        count__wordType = +count__wordType + +word_count_js__wordType;
        ScrollScreen_wordtype();
        JSInterface.updateWhenWindowMoveJs();
    }
    else{

        startIndex__wordType =indices__wordType[count__wordType];
        var text = $('#div_content').text();
        var leftPart = text.substr(0, startIndex__wordType);
        var rightPart ='<span id="readingSpan" class="notBlur">' +  text.substr(startIndex__wordType, text.length)+ '</span>';
        $('#div_content').html( leftPart + rightPart );
        ScrollScreen_wordtype();
        JSInterface.updateWhenWindowMoveJs();
        console.log("eddddddddddddddddddddddddddddddd");
        console.log(text.substr(startIndex__wordType, text.length))
        isLast_worType_highlighted = true;

    }


}

function stopHighlighting__wordType(){
    clearInterval(intervalFunction__wordType);
    var text = $('#div_content').text();
    $('#div_content').html( text );
    speedInSec__wordType = 0;
    startIndex__wordType = 0;
    highlightLength__wordType = 0;
    indices__wordType = [];
    count__wordType = -1;
    word_count_js__wordType = 0;
    intervalFunction__wordType;

    $('html,body').animate({ scrollTop: 0 },500);
}

function ScrollScreen_wordtype(){

    var window_current_scroll = $(window).scrollTop();
    var height_of_parent = $('.page').height();
    var full_height_of_br = $('#readingSpan').offset().top + $('#readingSpan').height();
    if( (height_of_parent-50 + window_current_scroll) <= full_height_of_br){
        var valll = window_current_scroll + (height_of_parent-100) - $('#readingSpan').height();
        $('html,body').animate({ scrollTop: valll },500);
    }

}

function getNoOfLines_wordType(){
    var divHeight = document.getElementById('div_content').offsetHeight;
    var fontSize = $('#div_content').css('font-size');
    var lineHeight = Math.floor(parseInt(fontSize.replace('px','')) * 1.5);
    var lineCount = divHeight/lineHeight;
    return lineCount;
}
