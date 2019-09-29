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
    return wordCount;

}

function wrapWithSpan(){
console.log("debug1111-----------------")
    var words = $("#div_content").text().split(" ");
    $("#div_content").empty();
    $.each(words, function(i, v) {
        $("#div_content").append($("<wordspan>").text(v +" "));
    });
    $("#div_content").html(    $("#div_content").html().split('-').join('&#8209;')  );
    $("#div_content").html(   $("#div_content").html().split('\n').join('</wordspan>\n<wordspan>')   );
    addLineClasses();
}

var line_count = 0;

function addLineClasses(){
console.log("debug2222-----------------")

    var current_top = 0;

    $( "wordspan" ).each(function( index ) {
        var current_wordspan = $( this );

        var top = $(current_wordspan).position().top;

        if(current_top != top){
            line_count++;
            current_top = top;
        }

        current_wordspan.addClass("wordspan_line_"+line_count)

    });

    seperateLines();
}

function seperateLines(){
console.log("debug3333-----------------")
    var fullString = "";

    for(var count = 0; count <= line_count; count++){
        var lineString = "";
        $('.wordspan_line_'+count).each(function(){
            lineString += $(this).html();
        })
        fullString += "<linespan class='line_"+count+"'>" + lineString + "</linespan><br class='br_"+count+"'>";
    }

    $("#div_content").html(fullString);
    JSInterface.hideProgressJs()
}

var speedInSec = 0;
var startIndex = 0;
var highlightLength = 0;
var indices = [];
var count = 0;
var windowsPerLine_js = 0;
var intervalFunction;

var running_line = 0;
var running_window_in_line = 1;
var overlappingNoOfChars = 2;
var totNoOfWindows = 0;
var totWindowCount = 0;
var currentWindowCount = 0;

function calcTotNoOfWindows( windowsPerLine ){
console.log("debug4444-----------------")
    var tempWindowsPerLine = parseInt(windowsPerLine);
    totWindowCount = 0;

    for(var count = 0; count <= line_count; count++){
        var lineText = $('.line_'+count).text();
        if(lineText.trim() != ""){
            var full_text_length = lineText.length;
            var window_text_length = Math.ceil( full_text_length / tempWindowsPerLine );
            var chars_per_half_row = getCharsSizeForHalfLine();
            if( (chars_per_half_row+5) > (full_text_length) ){
                totWindowCount++;
            }
            else{
                totWindowCount = totWindowCount + tempWindowsPerLine
            }
        }
    }
console.log("debug5555-----------------")
    JSInterface.getTotWindowsFromJs(totWindowCount,countWords())
}


function startHighlighting(windowsPerLine, singleWindowDuration, overlappingChars){

    windowsPerLine_js = windowsPerLine;
    speedInSec = singleWindowDuration;
    overlappingNoOfChars = 2;
    overlappingNoOfChars = parseInt(overlappingChars)/2;
    intervalFunction = setInterval(highlightWithTime, speedInSec);

}

function highlightWithTime(){

    if((currentWindowCount) == totWindowCount){
        JSInterface.finishHighlightingAndShowPanelJs();
        return 0;
    }

    var lineText = $('.line_'+running_line).text();

    if(lineText.trim() == ""){

        var lineTextEailer = $('.line_'+(+running_line + -1)).text();
        $('.line_'+(+running_line + -1)).html(lineTextEailer);

        running_line++;
        running_window_in_line = 1;

        highlightWithTime();
        return 0;
    }


    ScrollScreen(running_line);

    var full_text_length = lineText.length;
    var window_text_length = Math.ceil( full_text_length / windowsPerLine_js );

    var chars_per_half_row = getCharsSizeForHalfLine();

    if( (chars_per_half_row+5) > (full_text_length) ){

        var lineTextEailer = $('.line_'+(+running_line + -1)).text();
        $('.line_'+(+running_line + -1)).html(lineTextEailer);

        $('.line_'+running_line).html("<span class='notBlur'>" + lineText + "</span>");

        running_line++;
        running_window_in_line = 1;
        currentWindowCount++;
        JSInterface.updateWhenWindowMoveJs();
        return 0;
    }

    else if(running_window_in_line == 1){

        var lineTextEailer = $('.line_'+(+running_line + -1)).text();
        $('.line_'+(+running_line + -1)).html(lineTextEailer);

        var not_blur_window_string = lineText.substr(0, window_text_length + overlappingNoOfChars);
        var blur_window_string = lineText.substr(window_text_length + overlappingNoOfChars , full_text_length);
        $('.line_'+running_line).html("<span class='notBlur'>" + not_blur_window_string + "</span><span>" + blur_window_string + "</span>")

    }

    else if(running_window_in_line == windowsPerLine_js){

        var blur_window_string = lineText.substr( 0, (full_text_length - window_text_length - overlappingNoOfChars) );
        var not_blur_window_string = lineText.substr( (full_text_length - window_text_length - overlappingNoOfChars) , full_text_length );
        $('.line_'+running_line).html("<span>" + blur_window_string + "</span><span class='notBlur'>" + not_blur_window_string + "</span>")

    }

    else{

        var blur_left_window_string = lineText.substr(0, (full_text_length - (2*window_text_length) - overlappingNoOfChars));
        var x11 = (full_text_length - (2*window_text_length) - overlappingNoOfChars);
        var x22 = (full_text_length - (window_text_length) + overlappingNoOfChars)
        var not_blur_window_string = lineText.substr( x11, (x22 - x11)  );
        var blur_right_window_string = lineText.substr((full_text_length - (window_text_length) + overlappingNoOfChars) , full_text_length);
        $('.line_'+running_line).html("<span>" + blur_left_window_string + "</span><span class='notBlur'>" + not_blur_window_string + "</span><span>" + blur_right_window_string + "</span>")

    }

    if( parseInt(running_window_in_line)    ==    parseInt(windowsPerLine_js) ){
        running_line++;
        running_window_in_line = 1;
        currentWindowCount++;
    }

    else{
        running_window_in_line++;
        currentWindowCount++;
    }
    JSInterface.updateWhenWindowMoveJs();
}

function getCharsSizeForHalfLine() {
    var rows = $('#div_content br').length;
    var chars = $('#div_content').text().length;
    var chars_per_half_row = (chars / rows) / 2;
    return chars_per_half_row;
}

function ScrollScreen(running_line_no){

    var window_current_scroll = $(window).scrollTop();
    var height_of_parent = $('.page').height();
    var full_height_of_br = $('.br_'+running_line_no).offset().top;
    if( (height_of_parent-50 + window_current_scroll) <= full_height_of_br){
        var valll = window_current_scroll + (height_of_parent-100);
        $('html,body').animate({ scrollTop: valll },500);
    }

}

function stopHighlighting(){

    $('html,body').animate({ scrollTop: 0 },500);

    speedInSec = 0;
    startIndex = 0;
    highlightLength = 0;
    indices = [];
    count = 0;
    windowsPerLine_js = 0;
    totNoOfWindows = 0;
    currentWindowCount = 0;

    clearInterval(intervalFunction);

    intervalFunction;

    var lineTextEailer = $('.line_'+(+running_line + -1)).text();
    $('.line_'+(+running_line + -1)).html(lineTextEailer);

    var lineTextEailer = $('.line_'+(+running_line)).text();
    $('.line_'+(+running_line)).html(lineTextEailer);

    running_line = 0;
    running_window_in_line = 1;
    overlappingNoOfChars = 2;

}

function reFormatAll(){

    line_count = 0;
    speedInSec = 0;
    startIndex = 0;
    highlightLength = 0;
    indices = [];
    count = 0;
    windowsPerLine_js = 0;
    intervalFunction;
    running_line = 0;
    running_window_in_line = 1;
    overlappingNoOfChars = 2;
    wrapWithSpan();
    totNoOfWindows = 0;
    totWindowCount = 0;
    currentWindowCount = 0;

}

function addNewTextContent(textContent, fontSize, fontStyle){

console.log("textContent------   "+textContent);

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

    reFormatAll();

}
