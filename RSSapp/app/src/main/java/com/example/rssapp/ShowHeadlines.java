package com.example.rssapp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ShowHeadlines extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SingleItem>> {//로드는 ArrayList<SingleItem>을 sync클래스한테 반환받음
    // a main category has already been selected by the user
    // such as: 'Top Stories', 'World News', 'Business', ...
    // ["urlCaption", "urlAddress"] comes in a bundle sent
    // by main thread, here we access RSS-feed and show the
    // corresponding headlines.

    ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();

    RecyclerView recyclerView;
    String urlAddress = "";
    String urlCaption = "";
    SingleItem selectedNewsItem;
    AlertDialog dialog;

    private static final int LOADER_ID_USERACCOUNT = 10000;
    private LoaderManager loaderManager;

    // Use supplied URL to download web-feed. This process is inherently
    // slow and MUST be performed inside a thread or asynctask (as in here)
    ShowHeadlines callerContext;


    private WordListAdapter wordListAdapter;
    private List<SingleItem> itemsList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main);
        recyclerView = findViewById(R.id.recycleView);

        // find out which intent is calling us
        Intent callingIntent = getIntent();

        // grab data bundle holding selected url & caption sent to us
        Bundle myBundle = callingIntent.getExtras();
        urlAddress = myBundle.getString("urlAddress");
        urlCaption = myBundle.getString("urlCaption");

        // update app's top 'TitleBar' (eg. 'Business Wed April 09, 2014')
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy ", Locale.US);
        this.setTitle(urlCaption + " \t" + sdf.format(new Date()));


        this.loaderManager = LoaderManager.getInstance(this);

        loaderManager.initLoader(
                LOADER_ID_USERACCOUNT,
                null,
                (LoaderManager.LoaderCallbacks<ArrayList<SingleItem>>) this).forceLoad();
        PrepareProgressDialog();
        dialog.show();

    }

    @NonNull
    @Override
    public Loader<ArrayList<SingleItem>> onCreateLoader(int id, Bundle args) {
        return new FetchData(getApplicationContext(), urlAddress); //this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SingleItem>> loader, ArrayList<SingleItem> data) {

        itemsList = data;
        wordListAdapter = new WordListAdapter(this, itemsList);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(wordListAdapter);

        dialog.dismiss();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<SingleItem>> loader) {//일반적으로 필요없음

    }


    public void PrepareProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish, 무조건 돌아가야된다고 설정
        builder.setView(R.layout.prog);
        dialog = builder.create();
    }

    private static class FetchData extends AsyncTaskLoader<ArrayList<SingleItem>> {
        String myUrl="";
        public FetchData(Context context, String url) {
            super(context);
            myUrl = url;

        }

        @Override
        public ArrayList<SingleItem> loadInBackground() {

            ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();

            try {//json은 다끌고와서 파싱 xml은 연결상태에서 xml파싱하는 객체 만들어서 실시간 파싱

                // try to get connected to RSS source
                URL url = new URL(myUrl); //urlAddress);
                URLConnection connection;
                connection = url.openConnection();

                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                int responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = httpConnection.getInputStream();
                    // define a document builder to work on incoming stream
                    DocumentBuilderFactory dbf = DocumentBuilderFactory
                            .newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    // make DOM-tree for incoming XML stream
                    Document dom = db.parse(in);
                    // make available all access nodes in the parse tree
                    Element treeElements = dom.getDocumentElement();

                    // look for individual 'stories' (<items> in this case)
                    // add each found item to a NodeList collection (newsList)
                    newsList.clear();
                    NodeList itemNodes = treeElements.getElementsByTagName("item");
                    if ((itemNodes != null) && (itemNodes.getLength() > 0)) {
                        for (int i = 0; i < itemNodes.getLength(); i++) {
                            newsList.add(dissectItemNode(itemNodes, i));
                        }// for
                    }// if

                }// if
                // time to close. we don't need the connection anymore
                httpConnection.disconnect();

            } catch (Exception e) {
                Log.e("Error!!!!>> ", e.getMessage());
            }
            return newsList;  //to be consumed
        }

        @Override//처리한 값을 onloadfinished에게 넘겨줌
        public void deliverResult(ArrayList<SingleItem> data) {
            super.deliverResult(data);
        }

        SingleItem dissectItemNode(NodeList nodeList, int i) {
            // disassemble i-th entry in NodeList collection
            // get the first child of elements: extract fields:
            // title, description, pubData, and link. Put those pieces
            // together into a POJO 'SingleItem' object, and return it

            try {
                Element entry = (Element) nodeList.item(i);
                Element title = (Element) entry.getElementsByTagName(
                        "title").item(0);
                Element description = (Element) entry.getElementsByTagName(
                        "description").item(0);
                Element pubDate = (Element) entry.getElementsByTagName(
                        "pubDate").item(0);
                Element link = (Element) entry.getElementsByTagName(
                        "link").item(0);

                String titleValue = title.getFirstChild().getNodeValue();
                String descriptionValue = description.getFirstChild().getNodeValue();
                String dateValue = pubDate.getFirstChild().getNodeValue();
                String linkValue = link.getFirstChild().getNodeValue();

                SingleItem singleItem = new SingleItem(
                        dateValue, titleValue, descriptionValue, linkValue);

                return singleItem;

            } catch (DOMException e) {
                return new SingleItem("", "Error!!!", e.getMessage(), null);
            }
        }//dissectNode
    } //Background

}
