# NewsApp
### A project from Udacity Android Basics course.
A bit overview, it uses newsapi.org API to get news and get JSON data back. Then it is parsed using google's gson library and the requests are sent by Volley.

#### Note: To run the app you have to add [NewsAPI.org](https://newsapi.org/register) key. I have taken this step to due to limited access to daily request for News. Getting a key would take just few seconds :)
**Run the app**
1. Clone the app with `.git` url
2. Open `/home/.gradle/gradle.properties` and add the following line *(Create the file if not exists)*
```
NewsApi_ApiKey=<YOUR API KEY>
``` 
3. Run the app by <kbd>Shift + F10</kbd> or clicking <kbd>Run</kbd>

[Working app](https://i.imgur.com/RMxIGe3.gifv)

#### Libraries
- [Google gson](https://github.com/google/gson) For parsing JSON data
- [Volley](https://github.com/google/volley) For sending network requests
- [Picasso](http://square.github.io/picasso/) Loading and caching images

#### Credits
- [Udacity](https://udacity.com/) For teaching me Android
- [Font Awesome](https://fontawesome.com/icons) For open-source icons

#### License
GNU General Public License v3.0
