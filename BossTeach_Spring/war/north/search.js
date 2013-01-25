$('#ss').searchbox({   
    searcher:function(value,name){   
        alert(value + "," + name)   
    },   
    menu:'#mm',   
    prompt:'Please Input Value'  
});