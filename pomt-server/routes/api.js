// var mongoose = require('mongoose');
var express = require('express');
var router = express.Router();

var Ti = require('../models/ti');

/* GET ti infos. */
router.get('/ti', function(req, res) {
  Ti.find({ 'username': 'developer' }).exec(function(err, result) {
    if (!err) {
      res.send(result);
    }
  });
});

/* POST ti infos. */
router.post('/ti', function(req, res) {
  var ti = new Ti();

  ti.title = req.body.title;
  ti.description = req.body.description;
  ti.category = req.body.category;
  ti.date_begin = req.body.date_begin;
  ti.date_end = req.body.date_end;
  ti.username = req.body.username;

  // Saving it to the database.
  ti.save(function (err) {
    if (err) {
      var result = {'status': 'failed', 'err': err.stack}
    } else {
      var result = {'status': 'success'};
    }

    res.end(JSON.stringify(result));
  });

});

module.exports = router;
