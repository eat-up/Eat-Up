
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:

Parse.Cloud.define("getPaired", function(request, response) {
  Parse.Cloud.useMasterKey();
  var query = new Parse.Query("_User");
  var groupID = Math.floor((Math.random() * 1000) + 1);
  // query.equalTo("username",request.params.username);
//   query.equalTo("paired", request.params.paired);
//   query.find({
//     success: function(results) {
//       for (var i = 0; i < results.length; i++) { 
//         var object = results[i];
//         alert(object.id + ' - ' + object.get("paired"));
//       }
//       response.success("it changed the data");
//     },
//     error: function(){
//       response.error("it didnt change at all");
//     }
//   });  
 
   query.get(request.params.myusername, 
    {
      success: function(object)
      {
        object.set(
        {
          groupID: groupID,
          paired: "yes"
        });
        object.save();
        response.success();
      },
      error: function(object, error)
      {
        response.error();
      }
    });
 
  

  query.get(request.params.username, 
    {
      success: function(object)
      {
        object.set(
        {
          groupID: groupID,
          paired: "yes"
        });
        object.save();
        response.success();
      },
      error: function(object, error)
      {
        response.error();
      }
    });
   
});

// Parse.Cloud.beforeSave("_User", function(request, response) {
//   var comment = request.object.get("paired");
//   request.object.set("paired", "yes");
//   response.success();
// });

