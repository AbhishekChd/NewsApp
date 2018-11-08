# NewsApp
### Android Developer Nanodegree Capstone Project.
A bit overview, it uses newsapi.org API to get news and get JSON data back. Then it is parsed using google's gson library and the requests are sent by Retrofit.

#### Note: To run the app you have to add [NewsAPI.org](https://newsapi.org/register) key. I have taken this step to due to limited access to daily request for News. Getting a key would take just few seconds :)
**Run the app**
1. Clone the app with `.git` url
2. Open `/home/.gradle/gradle.properties` and add the following line *(Create the file if not exists)*
```
NewsApi_ApiKey=<YOUR API KEY>
``` 
3. Run the app by <kbd>Shift + F10</kbd> or clicking <kbd>Run</kbd>


#### Demo
- ##### Home
<img src="app.gif" width="40%">

- ##### Widget
<img src="widget.gif" width="40%">

#### Libraries
- [Google gson](https://github.com/google/gson) For parsing JSON data
- [Retrofit](http://square.github.io/retrofit/) For sending network requests
- [Glide](https://github.com/bumptech/glide) Loading and caching images
- [Room, LiveData and ViewModel](https://developer.android.com/topic/libraries/architecture/) Latest Android Architecture components
- [Databinding](https://developer.android.com/topic/libraries/data-binding/) Binding data and reducing 100s of Lines of Code

#### Credits
- [Udacity](https://udacity.com/) For teaching me Android

#### License
GNU General Public License v3.0
