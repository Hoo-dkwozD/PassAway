const express = require("express");
const cors = require("cors");
const fs = require("fs");
const path = require("path");
const morgan = require("morgan");
const errorLogger = require("./modules/errorLogger");

const app = express();

app.use(cors());

let accessLogStream = fs.createWriteStream('spa-server/logs/access.log', { flags: 'a' });

app.use(morgan("dev", { stream: accessLogStream }));

app.use(express.static(path.join(__dirname, "public")));

app.get('*', function(req, res, next) {
    fs.readFile('spa-server/public/index.html', 'utf-8', (err, content) => {
        if (err) {
            errorLogger.log('We cannot open "index.html" file. ');
        }

        res.writeHead(200, {
            'Content-Type': 'text/html; charset=utf-8'
        });

        res.end(content);
    });
});

module.exports = app;