require('dotenv').config();
// Import the functions you need from the SDKs you need
const firebase = require('firebase-admin');

firebase.initializeApp({
    credential: firebase.credential.cert({
        project_id: process.env.PROJECT_ID,
        private_key: process.env.PRIVATE_KEY
            ? process.env.PRIVATE_KEY.replace(/\\n/gm, "\n")
            : undefined,
        client_email: process.env.CLIENT_EMAIL,
    })
})

const db = firebase.firestore();
module.exports = db;