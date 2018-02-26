var mysql = require('mysql');
var request = require('request');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : '',
  database : 'SelfieLab'
});

connection.connect()
connection.query('SELECT 1 + 1 AS solution', function (err, rows, fields) {
  if (err) throw err

  console.log('Connected ', rows[0].solution)
})
connection.end()




/*#########################################################
##                           Express                    ###
#########################################################*/


var express = require('express');
var bodyParser = require('body-parser');
var fs = require('fs');
var app = express();
json = require('json-simple');
let port = "3030";
let faceapiURL= "https://api-us.faceplusplus.com/facepp/v3/"; 

let fppAPI={
  api_key:"mwxX59ERyaRPBeJ3iTZbuFPxAXvbBcKm",
  api_secret:"Evg2nMFSEEY9qCp4omChWKaD4HJFyeGY",
}
 



// Send  HTML page to client
app.get('/debug101', function(req, res){
    var ip = req.headers['x-forwarded-for'] ||
    req.connection.remoteAddress;
    console.log("["+ip.replace("::ffff:","")+svrts()+' ~] "GET /debug101.html:3000"')

    var html = fs.readFileSync('index.html');
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end("couponChain");
});

app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
 
app.use(express.static('public'));
 

 
app.post('/facelab', (req, res) => {
  
  //let image = req.body.data.image;
  //let name = req.body.data.name;

   // notification 
 var ip = req.headers['x-forwarded-for'] ||
 req.connection.remoteAddress;
 console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /facelab; By user-Token :' );
 //end notifiocation 
 


 let data = {
  api_key:fppAPI.api_key,
  api_secret:fppAPI.api_secret,
  image_base64:"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"
};
request.post({url:faceapiURL+"detect", formData:data}, function optionalCallback(err, httpResponse, body) {
  if (err) {
    return console.error('upload failed:', err);
    res.writeHead(404, {'Content-Type': 'text/html'});
    res.end("No DETECT");
  }
  //console.log('Upload successful!  Server responded with:', body);
  var obj = JSON.parse(body);
   if (obj.faces.length==1){  
    console.log(obj.faces[0].face_token);

    // get face Token then analize



    let data = {
      api_key: fppAPI.api_key,
      api_secret: fppAPI.api_secret,
      face_tokens:obj.faces[0].face_token,
      return_attributes:"gender,age,ethnicity,beauty"
    };
 
    request.post({url:faceapiURL+"face/analyze", formData:data}, function optionalCallback(err, httpResponse, body) {
      if (err) {
        return console.error('analize failed:', err);
      }
      var faceResult = JSON.parse(body);

      let return_data =  faceResult.faces[0].attributes.gender.value+","+
      faceResult.faces[0].attributes.age.value+","+
      faceResult.faces[0].attributes.beauty.female_score+","+
      faceResult.faces[0].attributes.beauty.male_score+","+
      faceResult.faces[0].attributes.ethnicity.value;

      res.writeHead(200, {'Content-Type': 'text/html'});
      res.end(return_data);
    });

  }else{
    res.writeHead(404, {'Content-Type': 'text/html'});
    res.end("ERROR");
  }//more than one face detect

});





 
     
 
   
 
 }); 
app.listen(port);
console.log('Listening at http://localhost:' + port)
 


/*#########################################################
##                           Misc FUNCTION              ###
########################################################### */
 
function addDays(date, days) {
  var result = new Date(date);
  result.setDate(result.getDate() + days);
  return result;
}

function svrts(){ // timestamp 

      var dt = new Date();
      date = (dt.getDate()<10? "0"+dt.getDate():dt.getDate());
      month = (dt.getMonth()+1<10? "0"+dt.getMonth()+1:dt.getMonth()+1);
      min = (dt.getMinutes()<10? "0"+dt.getMinutes():dt.getMinutes());
      sec = (dt.getSeconds()<10? "0"+dt.getSeconds():dt.getSeconds());
      hour = (dt.getHours()<10? "0"+dt.getHours():dt.getHours());
      ms = dt.getMilliseconds();
      if(ms<10){"00"+ms;}else if(ms<100){"0"+ms;}

      return " "+date+"-"+month+"-" + dt.getFullYear()+" "+hour+":"+min+":"+sec+";"+ms+"ms";


}