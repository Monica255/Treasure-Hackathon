var express = require('express');
var router = express.Router();
const db = require('../utils/db.config')
const { uuid } = require('uuidv4');
const uniqid = require('uniqid');

/* GET home page. */
router.get('')
  .get('', async (req, res, next) => {
    let data = []
    const getSeller = db.collection('seller')
    const snapshotSeller = await getSeller.get();
    snapshotSeller.forEach(doc => {
      data.push(doc.data())
    })
    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    res.json(data)
  })
  .get('/product/:sellerId', async (req, res) => {
    let data = []
    const productRef = db.collection('products')
    const snapshot = await productRef.where('id_seller', '==', req.params.sellerId).get();
    if (snapshot.empty) {
      res.statusCode = 404
      res.setHeader('Content-Type', 'application/json')
      res.json({ message: 'seller not found' })
    }
    snapshot.forEach(doc => {
      const docData = doc.data()
      data.push(docData)
    })
    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    res.json(data)
  })
  .get('/history/:sellerId', async (req, res) => {
    let currentData = ''
    const sellerRef = db.collection('seller')
    const snapshot = await sellerRef.where('id_seller', "==", req.params.sellerId).get()
    if (snapshot.empty) {
      res.statusCode = 404
      res.setHeader('Content-Type', 'application/json')
      return res.json({ message: "seller not found" })
    }
    snapshot.forEach(doc => {
      const data = doc.data()
      currentData = data.transaction_history
    })
    res.statusCode = 200
    res.json(currentData)
  })
  .get('/leftoffer/:sellerId', async (req, res) => {
    let arrData = []
    const sellerRef = db.collection('leftoffers')
    const snapshot = await sellerRef.where('id_seller', "==", req.params.sellerId).get()
    if (snapshot.empty) {
      res.statusCode = 404
      res.setHeader('Content-Type', 'application/json')
      return res.json({ message: "seller not found" })
    }
    snapshot.forEach(doc => {
      const data = doc.data()
      arrData.push(data)
    })
    res.statusCode = 200
    res.json(arrData)
  })
  .post('/product/:sellerId', async (req, res, next) => {
    let username = ''
    const productRef = db.collection('seller')
    const snapshot = await productRef.where('id_seller', '==', req.params.sellerId).get();
    if (snapshot.empty) {
      res.statusCode = 404
      res.setHeader('Content-Type', 'application/json')
      return res.json({ message: 'seller client not found' })
    }
    snapshot.forEach(doc => {
      username = doc.data().username
    })
    const id_product = uuid()
    //! ATTRIBUT
    const { name, qty, desc, prize } = req.body
    await db.collection('products').doc(uniqid()).set({
      username: username,
      name, qty, desc, prize,
      id_product: id_product,
      id_seller: req.params.sellerId
    })
    res.statusCode = 201
    res.setHeader('Content-Type', 'application/json')
    res.json({ message: 'successfully added product' })
  })
  .delete('/product/:sellerId/:productId', async (req, res) => {
    let productDocId = ''
    const productRef = db.collection('products')
    const snapshot = await productRef.where('id_product', "==", req.params.productId)
      .where("id_seller", "==", req.params.sellerId).get()
    snapshot.forEach((doc) => {
      productDocId = doc.id
    })
    await db.collection('products').doc(productDocId).delete()

    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    return res.json({ message: 'Delete Product Success' })
  })

module.exports = router;
