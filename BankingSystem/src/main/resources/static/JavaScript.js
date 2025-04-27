function chooseLanguage(language){
    document.getElementById('convertLanguage').innerHTML=language;
}
function feedback(){
    alert("Feedback Recorded !.");
	window.location.href = "/index";
	return false;
}

$(document).ready(function () {
    $("#policycontent,#financecontent,#pmycontent,#impcontent,#sercontent").hide();
    $("#gencontent").toggle();
    $("#genhover").click(function(e){
        e.preventDefault();
        $("#policycontent").hide();
        $("#financecontent").hide();
        $("#pmycontent").hide();
        $("#impcontent").hide();
        $("#sercontent").hide();
        $("#gencontent").toggle();
    });
    $("#policyhover").click(function(e){
        e.preventDefault();
        $("#gencontent").hide();
        $("#financecontent").hide();
        $("#pmycontent").hide();
        $("#impcontent").hide();
        $("#sercontent").hide();
        $("#policycontent").toggle();
    });
    $("#financehover").click(function(e){
        e.preventDefault();
        $("#gencontent").hide();
        $("#pmycontent").hide();
        $("#impcontent").hide();
        $("#sercontent").hide();
        $("#policycontent").hide();
        $("#financecontent").toggle();
    });

    $("#pmyhover").click(function(e){
        e.preventDefault();
        $("#gencontent").hide();
        $("#impcontent").hide();
        $("#sercontent").hide();
        $("#policycontent").hide();
        $("#financecontent").hide();
        $("#pmycontent").toggle();
    });
    $("#imphover").click(function(e){
        e.preventDefault();
        $("#gencontent").hide();
        $("#sercontent").hide();
        $("#policycontent").hide();
        $("#financecontent").hide();
        $("#pmycontent").hide();
        $("#impcontent").toggle();
    });
    $("#serhover").click(function(e){
        e.preventDefault();
        $("#gencontent").hide();
        $("#policycontent").hide();
        $("#financecontent").hide();
        $("#pmycontent").hide();
        $("#impcontent").hide();
        $("#sercontent").toggle();
    });
    $(".signinhover").click(function(e){
        $(".signincontent").toggle();
    })
    
    $(".adminhover").click(function(){
        $(".admincontent").toggle();
    })
    $("#userloginclosebutton").click(function(){
        $(".userloginpage").hide();
    })
    $(".usingmobilelogin").click(function(){
        $(".userloginpage").hide();
        $(".mobileloginpage").toggle();
    })
	
   

   

    
  });