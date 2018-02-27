var mysql = require('mysql');
var request = require('request');
var md5 = require('md5');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : '',
  database : 'SelfieLab'
});

connection.connect()
connection.query('SELECT 1 + 1 AS solution', function (err, rows, fields) {
  if (err) throw err
  console.log('mySQL Connected ', rows[0].solution)
})





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
  
  let image = req.body.image;
  let email = req.body.Email;
  


 let data = {
  api_key:fppAPI.api_key,
  api_secret:fppAPI.api_secret,
  image_base64:image,
};


request.post({url:faceapiURL+"detect", formData:data}, function optionalCallback(err, httpResponse, body) {
  if (err) {
    return console.error('upload failed:', err);
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end("No DETECT");
  }
  //console.log('Upload successful!  Server responded with:', body);
  var obj = JSON.parse(body);
  try{
   if (obj.faces.length==1){  
 

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

   // notification 
   var ip = req.headers['x-forwarded-for'] ||
   req.connection.remoteAddress;
   console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /facelab; selfie From : '+email+" " +return_data);
   //end notifiocation 
   
   

      res.writeHead(200, {'Content-Type': 'text/html'});
      res.end(return_data);
    });

  }else{
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end("NO_FACE_FOUND");
  }//more than one face detect
  }catch(err){
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end("NO_FACE_FOUND");
  }

  
});

}); 

app.post('/register', (req, res) => {
  
  let EMAIL = req.body.EMAIL;
  let password = req.body.PW;
  let name = req.body.name;
  let sts="";

    connection.query("SELECT * FROM USERS WHERE EMAIL = '"+EMAIL+"'", function (err, rows, fields) {
      if (err) throw err;
        if(rows.length==0){
          //not found create user 
          
            connection.query("INSERT INTO `USERS` (`ID`, `NICKNAME`, `EMAIL`, `PASSWORD`) VALUES (NULL, '"+name +"', '"+EMAIL+"', '"+md5(password)+"')", function (err, rows, fields) {
              if (err) throw err;
              sts="CREATED";
              res.writeHead(200, {'Content-Type': 'text/html'});
              res.end("CREATED_USER");
              // notification 
              var ip = req.headers['x-forwarded-for'] ||
              req.connection.remoteAddress;
              console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /Register; By EMAIL :'+EMAIL+", STATUS :"+sts );
              //end notifiocation 
            });
        }else{
        // found this email 
          sts="REJECT_EXIST";
          res.writeHead(404, {'Content-Type': 'text/html'});
          res.end("EXIST");
          // notification 
          var ip = req.headers['x-forwarded-for'] ||
          req.connection.remoteAddress;
          console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /Register; By EMAIL :'+EMAIL+", STATUS :"+sts );
          //end notifiocation 
        }
    });
  

   // notification 
 var ip = req.headers['x-forwarded-for'] ||
 req.connection.remoteAddress;
 console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /Register; By EMAIL :'+EMAIL+", STATUS :"+sts );
 //end notifiocation 
 
});

app.post('/peek', (req, res) => {
  let id = req.body.id;
  let name = req.body.name;
  let sts=""; //status code 

    connection.query("SELECT name,image FROM SAVE WHERE id ='"+id+"'" , function (err, rows, fields) {
      if (err||rows.length==0){
        res.writeHead(200, {'Content-Type': 'text/html'});
        res.end("ERROR");
      }       
      
              res.writeHead(200, {'Content-Type': 'text/html'});
              res.end(rows[0].image);
              // notification 
              var ip = req.headers['x-forwarded-for'] ||
              req.connection.remoteAddress;
              console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /peek USER :'+name+' Peek at '+rows[0].name+'. Picture id : '+id  );
              //end notifiocation 
   
    });
  
});

app.post('/list', (req, res) => {
   
  let sts=""; //status code 

    connection.query("SELECT id,name,score,age,ethnicity,gender FROM SAVE ORDER BY score DESC limit 30" , function (err, rows, fields) {
      if (err){
        res.writeHead(200, {'Content-Type': 'text/html'});
        res.end("ERROR");
      }       
      let data = "";
      for(i in rows){
        data += rows[i].id+","+rows[i].name+","+rows[i].score+","+rows[i].age+","+rows[i].ethnicity+","+rows[i].gender+"-";
      }

              res.writeHead(200, {'Content-Type': 'text/html'});
              res.end(data);
              // notification 
              var ip = req.headers['x-forwarded-for'] ||
              req.connection.remoteAddress;
              console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /List'  );
              //end notifiocation 
   
    });
  
});


app.post('/save', (req, res) => {
  
  let EMAIL = req.body.email;
  let name = req.body.name;
  let image = req.body.image;
  let score = req.body.score;
  let age  = req.body.age;
  let ethnicity  = req.body.eth;
  let gender = req.body.gender;

  let sts=""; //status code 
    let q = "INSERT INTO `SAVE`  (`id`, `email`,`name`, `score`, `age`, `ethnicity`, `gender`, `image`) VALUES (NULL,"+
    "'"+EMAIL+"',"+"'"+name+"'"+",'"+score+"',"+"'"+age+"',"+"'"+ethnicity+"',"+"'"+gender+"',"+"'"+image+"')";
    
    console.log(q);
    
    connection.query(q, function (err, rows, fields) {
      if (err){
        res.writeHead(200, {'Content-Type': 'text/html'});
        res.end("ERROR");
      } 
              res.writeHead(200, {'Content-Type': 'text/html'});
              res.end("SAVED");
              // notification 
              var ip = req.headers['x-forwarded-for'] ||
              req.connection.remoteAddress;
              console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /Save  By EMAIL :'+EMAIL );
              //end notifiocation 
   
    });
  
});



app.post('/login', (req, res) => {
  
  let EMAIL = req.body.EMAIL;
  let password = req.body.PW;
  let sts="";

    connection.query("SELECT NICKNAME FROM USERS WHERE EMAIL = '"+EMAIL+"' AND PASSWORD = '"+md5(password) +"'", function (err, rows, fields) {
      if (err) throw err;
        if(rows.length==1){
          //not found create user 
 
          sts=(rows[0].NICKNAME);
          res.writeHead(200, {'Content-Type': 'text/html'});
          res.end(sts);
          // notification 
          var ip = req.headers['x-forwarded-for'] ||
          req.connection.remoteAddress;
          console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /Login; By EMAIL :'+EMAIL+", STATUS : user "+sts+" is logged" );
          //end notifiocation 
        }else{
          sts="LOGIN_FAIL";
          res.writeHead(400, {'Content-Type': 'text/html'});
          res.end("LOGIN_FAIL");
          // notification 
          var ip = req.headers['x-forwarded-for'] ||
          req.connection.remoteAddress;
          console.log("["+ip.replace("::ffff:","")+ svrts()+' ~] "POST /Login; By EMAIL :'+EMAIL+", STATUS :"+sts );
          //end notifiocation 
        }
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