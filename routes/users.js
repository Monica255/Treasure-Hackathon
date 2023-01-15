var express = require('express');
var router = express.Router();
const db = require('../utils/db.config')
const uniqid = require('uniqid');
const { FieldValue } = require('firebase-admin/firestore')

router.get('/')
  .get('/leftoffers', async (req, res, next) => {
    let data = []
    const leftoffers = db.collection('leftoffers')
    const snapshotProduct = await leftoffers.get()
    snapshotProduct.forEach(doc => {
      data.push(doc.data())
    })
    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    res.json(data)
  })
  .post('/claim/:constributorId', async (req, res, next) => {
    let currentDesc = ''
    let currentName = ''
    let currentQty = ''
    let contributorDocId = ''
    let leftOfferDocId = ''
    //! ATTRIBUTE
    const { id_seller, id_product } = req.body
    const contributorRef = db.collection('contributor')
    const checkContributorValidation = await contributorRef.where('id_contributor', '==', req.params.constributorId).get()
    if (checkContributorValidation.empty) {
      res.statusCode = 404
      res.setHeader('Content-Type', 'application/json')
      return res.json({ message: 'client not found' })
    }
    const leftOfferRef = db.collection('leftoffers')
    const checkClaimValidation = await leftOfferRef.where('id_seller', '==', id_seller).
      where('id_product', '==', id_product).get()
    if (checkClaimValidation.empty) {
      res.statusCode = 404
      res.setHeader('Content-Type', 'application/json')
      return res.json({ message: 'Claimed Failed' })
    }
    checkClaimValidation.forEach(doc => {
      const data = doc.data()
      leftOfferDocId = doc.id
      currentDesc = data.desc
      currentName = data.name
      currentQty = data.qty
    })
    checkContributorValidation.forEach(doc => {
      contributorDocId = doc.id
    })

    const dataArr = {
      id_claim: uniqid(),
      qty: currentQty, desc: currentDesc, name: currentName,
      id_product: id_product, id_seller: id_seller,
      timestamp: new Date().toISOString()
    }
    await db.collection('contributor').doc(contributorDocId).update({
      claim_history: FieldValue.arrayUnion(
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
    await db.collection('leftoffers').doc(leftOfferDocId).delete();
    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    return res.json({ message: 'Claimed Success' })
  })

module.exports = router;