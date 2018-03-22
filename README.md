# SelfieLab
![alt text](https://travis-ci.org/Telexine/SelfieLab.svg?branch=master) Selfie Processing Android Application using Face++ API api and Node js. Express + mysql With continuous integration

### Stack
 - Android
 - Node JS (Express)
 - Firebase
 - Face++ API
 - mysql
 
 ## Getting Started

 
 ### File Structure
 
Client Front-end (Android App)

```
./SelfieLab/Android
```

Server API,Backend (Node js Express)
```
./SelfieLab/Server
```

### Installing API

1. in (/Server)
```
 npm install
```
2. import Database in directory
```
/sqlscript/travis.sql 
```
3. Edit mysql config, Server path , Face++ API in
```
/Server/server.js
```
4. Run Node (in /Server)
```
- node server.js
 or 
- sudo forever server.js (for run it forever but need to install forever first with -  npm install forever -g)
```

