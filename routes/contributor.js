var express = require('express');
var router = express.Router();
const db = require('../utils/db.config')
const uniqid = require('uniqid');
const { FieldValue } = require('firebase-admin/firestore')

router.get('/')
    .get('/products', async (req, res, next) => {
        let data = []
        const getProduct = db.collection('products')
        const snapshotProduct = await getProduct.get()
        snapshotProduct.forEach(doc => {
            data.push(doc.data())
        })
        res.statusCode = 200
        res.setHeader('Content-Type', 'application/json')
        res.json(data)
    })
    .get('/history/:contributorId', async (req, res) => {
        let currentData = []
        let role = ''
        const sellerRef = db.collection('contributor')
        const snapshot = await sellerRef.where('id_contributor', "==", req.params.contributorId).get()
        if (snapshot.empty) {
            res.statusCode = 404
            return res.json({ message: 'user not found!' })
        }
        snapshot.forEach(doc => {
            const data = doc.data()
            role = data.role
            if (role === 'donatur') {
                currentData.push(data.purchase_history)
            }
            else {
                currentData.push(data.claim_history)
            }
        })
        res.statusCode = 200
        res.setHeader('Content-Type', 'application/json')
        res.json(currentData)
    })
    .post('/signin', async (req, res) => {
        let user = {}
        const { email, password } = req.body
        const getContributor = db.collection('contributor')
        const validateUser = await getContributor.where('email', "==", email).where('password', "==", password).get()
        if (validateUser.empty) {
            res.statusCode = 404
            res.setHeader('Content-Type', 'application/json')
            res.json({ message: 'user not found' });
        }
        validateUser.forEach(doc => {
            const data = doc.data()
            user = {
                id_contributor: data.id_contributor,
                username: data.username,
                email: data.email,
                role: data.role
            }
        })
        res.statusCode = 200
        res.setHeader('Content-Type', 'application/json')
        res.json(user);
    })
    .post('/purchase/:contributorId', async (req, res, next) => {
        let documentId = ''
        let currentQty = 0
        let currentprize = 0
        let currentDesc = ''
        let currentName = ''
        let currentProductId = ''
        let currentSellerId = ''
        let contributorDocumentID = ''
        //! ATTRIBUT
        const { qty, prize, id_seller, id_product } = req.body;
        const contributorRef = db.collection('contributor')
        const checkContributorValidation = await contributorRef.where('id_contributor', '==', req.params.contributorId).
            where('role', '==', 'donatur').get()
        if (checkContributorValidation.empty) {
            res.statusCode = 404
            res.setHeader('Content-Type', 'application/json')
            return res.json({ message: 'contributor client not found' })
        }
        const productRef = db.collection('products')
        const checkPurchaseValidation = await productRef.where('id_seller', '==', id_seller).
            where('id_product', '==', id_product).get()
        if (checkPurchaseValidation.empty) {
            res.statusCode = 404
            res.setHeader('Content-Type', 'application/json')
            return res.json({ message: 'Purchased Failed' })
        }
        checkPurchaseValidation.forEach(doc => {
            documentId = doc.id
            const data = doc.data()
            currentQty = data.qty
            currentprize = data.prize
            currentName = data.name
            currentDesc = data.desc
            currentProductId = data.id_product
            currentSellerId = data.id_seller
        })
        await db.collection('products').doc(documentId).update(
            { qty: (parseInt(currentQty) - parseInt(qty)), prize: (parseInt(currentprize) - parseInt(prize)) }
        )
        const leftOfferRef = db.collection('leftoffers')
        await leftOfferRef.doc(uniqid()).set({
            desc: currentDesc, name: currentName,
            qty: qty, prize: prize,
            id_product: currentProductId, id_seller: currentSellerId
        })

        checkContributorValidation.forEach(doc => {
            contributorDocumentID = doc.id
        })

        const dataArr = {
            id_transaction: uniqid(),
            price: prize, qty: qty, desc: currentDesc,
            id_product: currentProductId, id_seller: currentSellerId,
            timestamp: new Date().toISOString()
        }
        await db.collection('contributor').doc(contributorDocumentID).update({
            purchase_history: FieldValue.arrayUnion(
                dataArr
            )
        })
        let sellerDocId = ''
        const seller = db.collection('seller')
        const sellerValidation = await seller.where("id_seller", "==", id_seller).get()
        sellerValidation.forEach((doc) => {
            sellerDocId = doc.id
        })

        await db.collection('seller').doc(sellerDocId).update({
            transaction_history: FieldValue.arrayUnion(
                dataArr
            )
        })
        res.statusCode = 200
        res.setHeader('Content-Type', 'application/json')
        return res.json({ message: 'Purchased Success' })
    })

module.exports = router;
