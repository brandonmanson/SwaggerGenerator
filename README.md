# Swaggy


[![](https://platform.slack-edge.com/img/add_to_slack.png)](https://slack.com/oauth/authorize?scope=incoming-webhook,commands,bot,files%3Awrite%3Auser&client_id=4460641922.158836768032&redirect_uri=https://swagger-generator.herokuapp.com/authenticate/redirect)

#### Generate boilerplate code for Swagger without leaving Slack!

Swaggy is a nifty tool that takes the pain out of bootstrapping a new Swagger definition. Need to define a new endpoint for that sweet app you're working on? No problem. You don't even have to leave Slack!

# How to use
Once you've added Swaggy to your team, you'll be able to generate new Swagger code like this:
`/swagger /path/to/endpoint METHOD (GET, POST, etc) NUMBER_OF_PARAMETERS`.

So `/swagger /api/doge POST 2` will produce this code snippet:

```javascript
{
  "swaggerVersion" : "1.2",
  "basePath" : "http://your_host_name/base_path",
  "apis" : [ {
    "path" : "/api/doge",
    "operations" : [ {
      "method" : "POST",
      "summary" : "Make this a nice summary",
      "type" : "string",
      "parameters" : [ {
        "param1" : "subject",
        "description" : "something descriptive",
        "required" : "true or false. change this to a bool.",
        "type" : "Up to you. I'm not a mind reader.",
        "paramType" : "Path, Query, Header, Body or Form"
      }, {
        "param2" : "subject",
        "description" : "something descriptive",
        "required" : "true or false. change this to a bool.",
        "type" : "Up to you. I'm not a mind reader.",
        "paramType" : "Path, Query, Header, Body or Form"
      } ]
    } ]
  } ]
}
```
Code snippets are editable so you can edit them right in Slack as well. Kinda cool right?
# New Features!
Swaggy is a bit of a one trick pony right now and it's very much a work in progress. If you have an idea of how to make Swaggy better, please open an issue or hit me up on Twitter @brandonmanson.
# Coming Soon
- More info on how you can contribute to this project. It's open sourced under the MIT license as well so you can fork to your heart's delight.
- Updates to make Swaggy more robust
- More tests and things of that nature
- 🍺, I hope.
