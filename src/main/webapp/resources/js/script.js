/**
 * 
 */

(function() {
    'use strict';
	
    $(document).ready(function(){
    	
    	// Option on change
    	$("input[name^=option_]").change(function(){
    		var id = this.id;
    		var split = id.split("_");
    		
    		var optionSelected = split[2];
    		var questionNo = split[1];
    		
    		console.log(optionSelected, questionNo);
    		
    		var url = '/exam/saveProgress/' + questionNo + '/' + optionSelected;
    		var statusTextId = 'p#saveStatus_' + questionNo; 
    		
    		// assuming app running in root context
    		$.get(url, function(data){
    			console.log("Success");
    			$(statusTextId).text("Saved " + optionMap(optionSelected));
    			$(statusTextId).addClass("success");
    		}).fail(function(){
    			$(statusTextId).text("Not Saved");
    			$(statusTextId).addClass("warning");
    		})
    		
    		// update summary
    		var summarySelector = "div[id=attemptSummary_" + questionNo + "] > strong";
    		$(summarySelector).text(optionMap(optionSelected));
    	})
    	
    	function optionMap(option){
    		
    		if(option === '1'){
    			return 'A';
    		} else if(option === '2'){
    			return 'B';
    		} else if(option === '3'){
    			return 'C';
    		} else if(option === '4'){
    			return 'D';
    		} else if(option === '5'){
    			return 'E';
    		}
    	}
    	
    	// Registration ---
    	updateEl($("#registerCandidate input[type=date]"));
    	updateEl($("#registerCandidate select"));
    	
    	$("#registerCandidate input[type=date]").change(function(){
    		updateEl($(this));
    	});
    	
    	$("#registerCandidate select").change(function(){
    		updateEl($(this));
    	});
    	
    	function updateEl(el){
    		if(el.val()){
    			el.addClass("blue");
    			el.removeClass("red");
    			el.removeClass("text-gray");
    		} else {
    			el.addClass("text-gray");
    			el.addClass("red");
    			el.removeClass("blue");
    		}
    	}
    });
})();