require('dotenv').config();
const express = require('express');
var sellersRouter = require('./routes/sellers');
var usersRouter = require('./routes/users');
var contributorRouter = require('./routes/contributor')

const app = express();
app.use(express.json());

app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
    res.header('Access-Control-Allow-Credentials', true);
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, PATCH');
    next();
});

app.use('/sellers', sellersRouter);
app.use('/contributor', contributorRouter)
app.use('/users', usersRouter);

const PORT = process.env.PORT || 8008;

app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
})