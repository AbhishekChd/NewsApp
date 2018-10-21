package com.example.abhishek.newsapp.old;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.abhishek.newsapp.BuildConfig;
import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.old.adapters.NewsAdapter;
import com.example.abhishek.newsapp.old.contracts.BaseUrlContract;
import com.example.abhishek.newsapp.old.models.Box;
import com.example.abhishek.newsapp.old.models.NewsArticle;
import com.example.abhishek.newsapp.old.network.GsonRequest;
import com.example.abhishek.newsapp.old.network.NewsRequestQueue;
import com.example.abhishek.newsapp.old.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String SAMPLE_DATA = "{\"status\":\"ok\",\"totalResults\":20,\"articles\":[{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":null,\"title\":\"Low turnout in first vote for Iraqis since victory over IS\",\"description\":\"Low turnout and voting irregularities marked the initial hours of voting Saturday in Iraq’s first elections since the country declared victory over the Islamic State group.\",\"url\":\"https://www.washingtonpost.com/world/middle_east/iraqi-polls-open-in-first-elections-since-victory-over-is/2018/05/12/3f078118-559a-11e8-a6d4-ca1d035642ce_story.html\",\"urlToImage\":\"https://www.washingtonpost.com/resizer/2CjPNwqvXHPS_2RpuRTKY-p3eVo=/1484x0/www.washingtonpost.com/pb/resources/img/twp-social-share.png\",\"publishedAt\":\"2018-05-12T12:45:00Z\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"WIRED Staff\",\"title\":\"Russia-Linked Facebook Ads Targeted a Sketchy Chrome Extension at Teen Girls\",\"description\":\"Among the Russian ads released by House Democrats this week were links promoting malicious Chrome extensions.\",\"url\":\"https://www.wired.com/story/russia-facebook-ads-sketchy-chrome-extension/\",\"urlToImage\":\"https://media.wired.com/photos/5af614f7b6133764df488088/191:100/pass/russia_malware_facebook_chrome_extensions-FINAL.jpg\",\"publishedAt\":\"2018-05-12T12:42:04Z\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Zachary Cohen, CNN\",\"title\":\"Trump claims he saved almost $999800000 on US embassy in Jerusalem\",\"description\":\"President Donald Trump spent roughly 10 minutes of an hourlong speech Thursday night in Indiana telling the story of how he saved nearly $999,800,000 on controversial plans to move the US Embassy in Israel from Tel Aviv to Jerusalem -- a claim he has made bef…\",\"url\":\"https://www.cnn.com/2018/05/12/politics/trump-jerusalem-embassy-cost/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/180511062356-07-donald-trump-elkhart-in-05-10-2018-super-tease.jpg\",\"publishedAt\":\"2018-05-12T12:08:00Z\"},{\"source\":{\"id\":\"the-new-york-times\",\"name\":\"The New York Times\"},\"author\":\"http://www.nytimes.com/by/damien-cave\",\"title\":\"Mass Shooting in Australia Leaves a Tiny Community in Shock and Grief\",\"description\":\"The killing of a family of seven, apparently in a murder-suicide, was the worst incident of its kind in the country in more than 20 years.\",\"url\":\"https://www.nytimes.com/2018/05/12/world/australia/mass-shooting-osmington.html\",\"urlToImage\":\"https://static01.nyt.com/images/2018/05/13/world/australia/13oz-shootings1/13oz-shootings1-facebookJumbo.jpg\",\"publishedAt\":\"2018-05-12T11:03:18Z\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"https://www.facebook.com/aaronblakewp?fref=ts\",\"title\":\"The Trump White House crossed a new threshold for political debasement this week\",\"description\":\"The White House's message about Kelly Sadler's John McCain comment: Nothing is beyond the pale, and nothing is more important than politics.\",\"url\":\"https://www.washingtonpost.com/news/the-fix/wp/2018/05/12/the-trump-white-house-crossed-a-new-threshold-for-political-debasement-this-week/\",\"urlToImage\":\"https://www.washingtonpost.com/resizer/xXlw2Y1ZEuhBWa4NEvlbZHEEqA8=/1484x0/arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/6GRDOTX6YAYG5HI3YC7M2RHTGY.JPG\",\"publishedAt\":\"2018-05-12T10:35:53Z\"},{\"source\":{\"id\":\"the-new-york-times\",\"name\":\"The New York Times\"},\"author\":\"http://www.nytimes.com/by/yonette-joseph\",\"title\":\"NBC Revives 'Brooklyn Nine-Nine,' and Fans Rejoice\",\"description\":\"A day after Fox announced it was canceling the show, the peacock network swooped in and picked it up for 13 episodes.\",\"url\":\"https://www.nytimes.com/2018/05/12/arts/television/brooklyn-nine-nine-nbc.html\",\"urlToImage\":\"https://static01.nyt.com/images/2018/05/13/arts/13Brooklyn99/13Brooklyn99-facebookJumbo.jpg\",\"publishedAt\":\"2018-05-12T09:39:55Z\"},{\"source\":{\"id\":\"the-new-york-times\",\"name\":\"The New York Times\"},\"author\":\"Jugal K. Patel, Tim Wallace\",\"title\":\"'Shell-Shocked' in Hawaii: How Lava Overran a Neighborhood\",\"description\":\"The Kilauea volcano on the Big Island unleashed havoc when the earth split open, lava spewed into the air and molten rock swallowed streets whole.\",\"url\":\"https://www.nytimes.com/interactive/2018/05/12/us/kilauea-volcano-lava-leilani-estates-hawaii.html\",\"urlToImage\":\"https://static01.nyt.com/images/2018/05/11/us/kilauea-1526079544004/kilauea-1526079544004-facebookJumbo.jpg\",\"publishedAt\":\"2018-05-12T09:00:42Z\"},{\"source\":{\"id\":\"the-wall-street-journal\",\"name\":\"The Wall Street Journal\"},\"author\":\"James Hookway\",\"title\":\"Malaysia's Ex-Leader Najib Razak Quits Party After Election Loss\",\"description\":\"Malaysia’s former Prime Minister Najib Razak resigned as leader of his political party and promised to abide by a travel ban, as his successor Mahathir Mohamad struggled with factional infighting to assemble a new cabinet.\",\"url\":\"https://www.wsj.com/articles/malaysias-ex-leader-najib-razak-quits-party-after-election-loss-1526113943\",\"urlToImage\":\"https://images.wsj.net/im-10366/social\",\"publishedAt\":\"2018-05-12T08:39:47Z\"},{\"source\":{\"id\":null,\"name\":\"Latimes.com\"},\"author\":\"Brittny Mejia, Alene Tchekmedyian, James Queally\",\"title\":\"Suspected shooter at a Palmdale high school is a former student who recently transferred\",\"description\":\"One student was wounded when a 14-year-old former Highland High School student fired about 10 rounds on the campus, authorities said. The suspect was detained at a nearby parking lot, and authorities are investigating a motive.\",\"url\":\"http://www.latimes.com/local/lanow/la-me-ln-palmdale-high-school-shooting-20180511-story.html\",\"urlToImage\":\"http://www.latimes.com/resizer/2oMxkriWR0tZ4gRVmhN8j2HRuWE=/1200x0/www.trbimg.com/img-5af68193/turbine/la-me-ln-palmdale-high-school-shooting-20180511\",\"publishedAt\":\"2018-05-12T05:15:00Z\"},{\"source\":{\"id\":null,\"name\":\"Lohud.com\"},\"author\":\"Matt Spillane\",\"title\":\"Weather: What to know for Mother's Day weekend\",\"description\":\"Here's what to know for the Mother's Day weekend weather forecast in the Lower Hudson Valley.\",\"url\":\"https://www.lohud.com/story/weather/2018/05/11/weather-mothers-day-weekend/602088002/\",\"urlToImage\":\"https://www.gannett-cdn.com/-mm-/3acd617a478e6f83aae4571c24b59bf8529192de/c=1-0-589-332&r=x1683&c=3200x1680/local/-/media/2018/05/11/Westchester/Westchester/636616390229666419-Sunday-weather-forecast.jpg\",\"publishedAt\":\"2018-05-12T04:27:57Z\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Steve Almasy and Jamiel Lynch, CNN\",\"title\":\"Oklahoma governor signs adoption law that LGBT groups call discriminatory\",\"description\":\"Oklahoma Gov. Mary Fallin on Friday signed a law that says no child-placement agency will be required to put children up for adoption or in foster care in situations that \\\"violate the agency's written religious or moral convictions or policies.\\\"\",\"url\":\"https://www.cnn.com/2018/05/11/politics/oklahoma-adoption-law-lgbt/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/160506124513-04-mary-fallin-super-tease.jpg\",\"publishedAt\":\"2018-05-12T02:56:00Z\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Ryan Browne and Sophie Tatum, CNN\",\"title\":\"US stealth fighters intercept Russian bombers off the coast of Alaska\",\"description\":\"Two Russian TU-95 \\\"Bear\\\" bombers were intercepted by US F-22 stealth fighters in international airspace off the coast of Alaska on Friday, according to North American Aerospace Defense Command.\",\"url\":\"https://www.cnn.com/2018/05/11/politics/us-stealth-fighters-intercept-russian-bombers/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/180217124747-us-air-force-f22-raptors-syria-020218-super-tease.jpg\",\"publishedAt\":\"2018-05-12T02:08:56Z\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"https://www.facebook.com/brian.d.fung\",\"title\":\"AT&T CEO: Hiring Cohen as a consultant was a 'big mistake'\",\"description\":\"In a companywide email, Randall Stephenson also announced that the firm’s top lobbying executive in Washington would be leaving.\",\"url\":\"https://www.washingtonpost.com/business/economy/atandt-ceo-hiring-cohen-as-a-consultant-was-a-big-mistake/2018/05/11/243f57f2-554a-11e8-abd8-265bd07a9859_story.html\",\"urlToImage\":\"https://www.washingtonpost.com/resizer/88fMnUN8C3u74GcM5ZwKkjUtAQE=/1484x0/arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/PNNULWCVIQI6RJWUZIOQGVSCZY.jpg\",\"publishedAt\":\"2018-05-11T23:24:08Z\"},{\"source\":{\"id\":\"cbs-news\",\"name\":\"CBS News\"},\"author\":\"CBS News\",\"title\":\"\\\"Not the way I would deal with people,\\\" former Defense Secretary Gates says of Trump\",\"description\":\"\\\"Face the Nation\\\" moderator Margaret Brennan sat down with Robert Gates for an interview to air Sunday\",\"url\":\"https://www.cbsnews.com/news/not-the-way-i-would-deal-with-people-former-dod-secy-gates-says-of-trump/\",\"urlToImage\":\"https://cbsnews2.cbsistatic.com/hub/i/r/2018/05/11/e6d142d8-dce3-4c62-a0bf-7de37e350fff/thumbnail/1200x630/39dd6f8ae506645bd23c3318f1831d63/gates-intv-ftn-web.jpg\",\"publishedAt\":\"2018-05-11T23:07:00Z\"},{\"source\":{\"id\":null,\"name\":\"Wsbtv.com\"},\"author\":\"Mike Petchenik\",\"title\":\"Officer quits after controversial traffic stop involving grandmother\",\"description\":\"Alpharetta police originally suspended Officer James Legg and opened an internal investigation into what happened. Legg resigned Friday afternoon, according to his lawyer.\",\"url\":\"https://www.wsbtv.com/news/local/north-fulton-county/lawmakers-call-for-hate-crime-charges-against-officer-involved-in-womans-arrest/747704945\",\"urlToImage\":\"https://media-beta.wsbtv.com/photo/2018/05/11/Officer_resigns_after_controversial_traf_0_11639372_ver1.0_640_360.jpg\",\"publishedAt\":\"2018-05-11T22:52:30Z\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":null,\"title\":\"Retrial for US Border Patrol agent over fatal shooting\",\"description\":\"A roundup of news from throughout the country.\",\"url\":\"https://www.washingtonpost.com/national/retrial-for-us-border-patrol-agent-over-fatal-shooting/2018/05/11/6e06ff80-500d-11e8-84a0-458a1aa9ac0a_story.html\",\"urlToImage\":\"https://www.washingtonpost.com/resizer/2CjPNwqvXHPS_2RpuRTKY-p3eVo=/1484x0/www.washingtonpost.com/pb/resources/img/twp-social-share.png\",\"publishedAt\":\"2018-05-11T22:24:34Z\"},{\"source\":{\"id\":\"the-new-york-times\",\"name\":\"The New York Times\"},\"author\":\"https://www.nytimes.com/by/robert-pear, http://www.nytimes.com/by/peter-baker\",\"title\":\"Trump Outlines Plan to Lower Drug Prices\",\"description\":\"The president said he would promote more competition, but broke with promises that he made while campaigning, like calling for Medicare to negotiate lower prices.\",\"url\":\"https://www.nytimes.com/2018/05/11/us/politics/trump-prescription-drugs-plan.html\",\"urlToImage\":\"https://static01.nyt.com/images/2018/05/12/us/politics/12dc-health/12dc-health-facebookJumbo.jpg\",\"publishedAt\":\"2018-05-11T18:57:42Z\"},{\"source\":{\"id\":null,\"name\":\"Espn.com\"},\"author\":null,\"title\":\"Did the Raptors overachieve or disappoint under Dwane Casey?\",\"description\":\"Toronto compiled winning records under Dwane Casey, but that didn't translate to the playoffs. Was that the coach's fault?\",\"url\":\"http://www.espn.com/nba/story/_/id/23471830/did-toronto-raptors-overachieve-disappoint-dwane-casey-nba\",\"urlToImage\":\"http://a4.espncdn.com/combiner/i?img=%2Fphoto%2F2018%2F0414%2Fr356648_1296x729_16%2D9.jpg\",\"publishedAt\":\"2018-05-11T18:13:36Z\"},{\"source\":{\"id\":\"cbs-news\",\"name\":\"CBS News\"},\"author\":\"Crimesider Staff\",\"title\":\"1 infant dead, twin hospitalized after being left in SUV in Virginia\",\"description\":\"Neighbors say that the children's father may have forgotten they were in the car after he dropped his wife off at work\",\"url\":\"https://www.cbsnews.com/news/1-infant-dead-twin-hospitalized-after-being-left-in-suv-in-virginia/\",\"urlToImage\":\"https://cbsnews2.cbsistatic.com/hub/i/r/2018/05/11/f7e8bdf2-73e9-43c8-8a01-34e3449d9cee/thumbnail/1200x630/fa141883362b79a07ddc5c9b4bfa42ce/suv.png\",\"publishedAt\":\"2018-05-11T16:42:00Z\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Doug Criss, CNN\",\"title\":\"5 things for May 10: American detainees, Israel and Iran, and Hawaii eruptions\",\"description\":\"Here's what else you need to know to Get Up to Speed and Out the Door.\",\"url\":\"https://www.cnn.com/2018/05/10/us/five-things-may-10-trnd/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/180510104059-14-nk-detainees-return-0510-super-tease.jpg\",\"publishedAt\":\"2018-05-10T10:13:00Z\"}]}";

    private final String LOG_TAG = getClass().getSimpleName();
    private final String BUNDLE_JSON_KEY = "json";
    NewsAdapter mNewsAdapter;
    private Response.Listener<Box> mNewsResponseListener;
    private Response.ErrorListener mErrorListener;
    private List<NewsArticle> mNewsArticles = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        String jsonData = gson.toJson(mNewsArticles);
        outState.putString(BUNDLE_JSON_KEY, jsonData);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        if (mNewsResponseListener == null || mErrorListener == null)
            setupListeners();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_main);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fredericka_the_great.ttf");

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setupListeners();
        // Setup DrawerLayout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Setup Nav view for DrawerLayout
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Setting up LayoutManager and RecyclerView
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
        RecyclerView recyclerView = findViewById(R.id.rv_news_posts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        mNewsAdapter = new NewsAdapter(this);
        recyclerView.setAdapter(mNewsAdapter);


        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_JSON_KEY)) {
            String jsonData = savedInstanceState.getString(BUNDLE_JSON_KEY);
            Gson gson = new Gson();

            Type array = new TypeToken<List<NewsArticle>>() {
            }.getType();
            mNewsArticles = gson.fromJson(jsonData, array);
            mNewsAdapter.setNewsPosts(mNewsArticles);
        } else {
            fetchNewsData(NetworkUtils.getHeadlines(-1).toString());
//          fetchSampleData();
        }
    }

    @Override
    protected void onStart() {
        if (mNewsResponseListener == null || mErrorListener == null)
            setupListeners();
        super.onStart();
    }

    @Override
    protected void onPause() {
        mNewsResponseListener = null;
        mErrorListener = null;
        super.onPause();
    }

    /**
     * Sample data to avoid unnecessary calls while development
     */
    private void fetchSampleData() {
        Gson gson = new Gson();
        Box box = gson.fromJson(SAMPLE_DATA, Box.class);
        mNewsArticles = box.getArticles();
        mNewsAdapter.setNewsPosts(mNewsArticles);
    }

    /**
     * Fetch news data in form of {@link List<NewsArticle>}
     * Then pass it to {@link NewsAdapter} for display
     */
    private void fetchNewsData(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Api-Key", BuildConfig.NewsApiKey);
        GsonRequest<Box> gsonRequest = new GsonRequest<>(
                url,
                Box.class,
                headers,
                mNewsResponseListener,
                mErrorListener
        );
        NewsRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        NewsRequestQueue.getInstance(this).addToRequestQueue(gsonRequest);
        Log.v(LOG_TAG, "fetchNewsData()");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String url = null;
        switch (id) {
            case R.id.nav_headlines_top:
                url = NetworkUtils.getHeadlines(-1).toString();
                getSupportActionBar().setTitle(R.string.top_stories);
                break;
            case R.id.nav_headlines_business:
                url = NetworkUtils.getHeadlines(BaseUrlContract.HEADLINES_BUSINESS).toString();
                getSupportActionBar().setTitle(R.string.category_business);
                break;
            case R.id.nav_headlines_technology:
                url = NetworkUtils.getHeadlines(BaseUrlContract.HEADLINES_TECHNOLOGY).toString();
                getSupportActionBar().setTitle(R.string.category_technology);
                break;
            case R.id.nav_headlines_health:
                url = NetworkUtils.getHeadlines(BaseUrlContract.HEADLINES_HEALTH).toString();
                getSupportActionBar().setTitle(R.string.category_health);
                break;
            case R.id.nav_headlines_sports:
                url = NetworkUtils.getHeadlines(BaseUrlContract.HEADLINES_SPORTS).toString();
                getSupportActionBar().setTitle(R.string.category_sports);
                break;
            case R.id.nav_headlines_science:
                url = NetworkUtils.getHeadlines(BaseUrlContract.HEADLINES_SCIENCE).toString();
                getSupportActionBar().setTitle(R.string.category_science);
                break;
        }
        if (!TextUtils.isEmpty(url)) {
            fetchNewsData(url);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupListeners() {
        mNewsResponseListener = new Response.Listener<Box>() {
            @Override
            public void onResponse(Box response) {
                Log.v("MainActivity", response.toString());
                mNewsArticles = response.getArticles();
                mNewsAdapter.setNewsPosts(mNewsArticles);
            }
        };
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity", "Volley Error: " + error.getMessage());
            }
        };
    }
}
