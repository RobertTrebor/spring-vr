document.addEventListener('DOMContentLoaded',function(){
  document.getElementById('try').onclick=function(){
    console.log('try');
    req=new XMLHttpRequest();
    req.withCredentials=true;
    req.open("GET",'http://localhost:8080/restcemeteries',true);
    req.send();
    req.onload=function(){
      json=JSON.parse(req.responseText);
      document.getElementById('textarea').innerHTML=JSON.stringify(json);
    };
  };
  document.getElementById('try2').onclick=function(){
    console.log('try2');
    req=new XMLHttpRequest();
    req.open("GET",'http://localhost:8080/restcemeteries',true);
    req.send();
    req.onload=function(){
      json=JSON.parse(req.responseText);
      var html = "";
      json.forEach(function (val) {
        var keys = Object.keys(val);
        html += "<div class = 'cat'>";
        keys.forEach(function(key){
          html += "<strong>" + key + "</strong>: " + val[key] + "<br>";
        })
      });
      html += "</div><br>";
      document.getElementById('textarea2').innerHTML=html;
    };
  };
});
