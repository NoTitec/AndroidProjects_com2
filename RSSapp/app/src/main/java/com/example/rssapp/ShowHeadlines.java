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
import androidx.recyclerview.widget.GridLayoutManager;
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

    //ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();
    private WordListAdapter wordListAdapter;//리사이클러뷰 어댑터
    private List<SingleItem> itemsList;//리사이클러 뷰홀더가 쓸 실제 데이터들
    RecyclerView recyclerView;//리사이클러뷰
    String urlAddress = "";
    String urlCaption = "";
    SingleItem selectedNewsItem;
    AlertDialog dialog;//경고 다이얼로그 변수

    private static final int LOADER_ID_USERACCOUNT = 10000;//로더 고유번호
    private LoaderManager loaderManager;//로드 매니저 변수

    // Use supplied URL to download web-feed. This process is inherently
    // slow and MUST be performed inside a thread or asynctask (as in here)
    ShowHeadlines callerContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main);
        recyclerView = findViewById(R.id.recycleView);//리사이클러뷰 가져옴

        // find out which intent is calling us
        Intent callingIntent = getIntent();//리사이클러뷰에서 선택시 인터넷 열어줄 intent

        // grab data bundle holding selected url & caption sent to us
        Bundle myBundle = callingIntent.getExtras();//다른 intent 보낸 번들있는지 조사후 가져옴
        urlAddress = myBundle.getString("urlAddress");//번들에서 RSS링크꺼냄
        urlCaption = myBundle.getString("urlCaption");//번들에서 카테고리 이름 꺼냄

        // update app's top 'TitleBar' (eg. 'Business Wed April 09, 2014')
        //이 액티비티 이름을 현재 카테고리이름+현재 날짜로 설정
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy ", Locale.US);
        this.setTitle(urlCaption + " \t" + sdf.format(new Date()));


        this.loaderManager = LoaderManager.getInstance(this);//로드매니저 인스턴스 1개 반환해서 저장
        PrepareProgressDialog();//백그라운드 진행동안 보여줄 프로그래스 뷰
        dialog.show();//프로그래스뷰 보여줌
        //로드매너저에 고유번호,null,로드매니저 콜백 implement 한 클래스전달해 초기화한뒤 forceLoad로 백그라운드 동작 실행
        loaderManager.initLoader(
                LOADER_ID_USERACCOUNT,
                null,
                (LoaderManager.LoaderCallbacks<ArrayList<SingleItem>>) this).forceLoad();


    }

    @NonNull
    @Override
    public Loader<ArrayList<SingleItem>> onCreateLoader(int id, Bundle args) {//내부에서 동작할 asnctask 상속한 클래스 생성하여 반환
        return new FetchData(getApplicationContext(), urlAddress); //this);//로드매니저가 FetchData클래스만들고 실행
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SingleItem>> loader, ArrayList<SingleItem> data) {
    //deliverdata메소드에서 넘겨받은 데이터와 onCreateLoader에서 넘겨받은 로드를이용해 리사이클러뷰 세팅
        itemsList = data;
        wordListAdapter = new WordListAdapter(this, itemsList);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator()); 이거 set되면 activity넘어갈때 오류 발생
        recyclerView.setAdapter(wordListAdapter);

        dialog.dismiss();//프로그래스뷰 끔
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
//------------------------------------------- 로드매니저가 oncreateloader로 생성하는 내부 동작 클래스
    private static class FetchData extends AsyncTaskLoader<ArrayList<SingleItem>> {
        String myUrl="";
        public FetchData(Context context, String url) {
            super(context);
            myUrl = url;

        }

        @Override
        public ArrayList<SingleItem> loadInBackground() {//백그라운드 동작메소드 반환값 ArrayList<SingleItem>

            ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();

            try {//json은 다끌고와서 파싱 xml은 연결상태에서 xml파싱하는 객체 만들어서 실시간 파싱

                // try to get connected to RSS source
                URL url = new URL(myUrl); //urlAddress);
                URLConnection connection;
                connection = url.openConnection();//url주소 연결

                HttpURLConnection httpConnection = (HttpURLConnection) connection;//httpurl연결
                int responseCode = httpConnection.getResponseCode();//http응답 코드 저장

                if (responseCode == HttpURLConnection.HTTP_OK) {//응답 코드가 ok인 경우
                    InputStream in = httpConnection.getInputStream();
                    // define a document builder to work on incoming stream
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//dom파서 하나 만듬
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    // make DOM-tree for incoming XML stream
                    Document dom = db.parse(in);//링크xml 읽은걸 dom에 전달해서 생성
                    // make available all access nodes in the parse tree
                    Element treeElements = dom.getDocumentElement();//전체 Element 트리 생성

                    // look for individual 'stories' (<items> in this case)
                    // add each found item to a NodeList collection (newsList)
                    newsList.clear();
                    NodeList itemNodes = treeElements.getElementsByTagName("item");//item 태그가진 노드들 리스트 생성
                    if ((itemNodes != null) && (itemNodes.getLength() > 0)) {//item태그의 내용물이 1개 이상인경우
                        for (int i = 0; i < itemNodes.getLength(); i++) {//item개수만큼 반복하며 아래쪽 dissectitemnode메소드로 원하는 태그정보 추출해 Singleitemlist에 저장
                            newsList.add(dissectItemNode(itemNodes, i));
                        }// for
                    }// if

                }// if
                // time to close. we don't need the connection anymore
                httpConnection.disconnect();

            } catch (Exception e) {
                Log.e("Error!!!!>> ", e.getMessage());
            }
            return newsList;  //to be consumed 완성된 기사목록 리턴
        }

        @Override//처리한 newsList를 로드매니저의 onloadfinished에게 넘겨줌 여기서 data변수엔 자동으로 loadinbackground반환값이 들어감
        public void deliverResult(ArrayList<SingleItem> data) {
            super.deliverResult(data);
        }

        SingleItem dissectItemNode(NodeList nodeList, int i) {//item태그가진 노드리스트와 번호 전달
            // disassemble i-th entry in NodeList collection
            // get the first child of elements: extract fields:
            // title, description, pubData, and link. Put those pieces
            // together into a POJO 'SingleItem' object, and return it

            try {
                Element entry = (Element) nodeList.item(i);//i번째 item태그 Element 생성
                //entry Element에서 원하는 키를 이용해 값을 추출
                Element title = (Element) entry.getElementsByTagName(
                        "title").item(0);//entry에서 title태그가진 노드리스트호출뒤 첫번째 노드 추출
                Element description = (Element) entry.getElementsByTagName(
                        "description").item(0);
                Element pubDate = (Element) entry.getElementsByTagName(
                        "pubDate").item(0);
                Element link = (Element) entry.getElementsByTagName(
                        "link").item(0);

                String titleValue = title.getFirstChild().getNodeValue();//title노드의 자식 textnode가져와 textnode가 가진 값 불러옴
                String descriptionValue = description.getFirstChild().getNodeValue();
                String dateValue = pubDate.getFirstChild().getNodeValue();
                String linkValue = link.getFirstChild().getNodeValue();

                SingleItem singleItem = new SingleItem(
                        dateValue, titleValue, descriptionValue, linkValue);

                return singleItem;//1개의 아이템 만들어 반환

            } catch (DOMException e) {
                return new SingleItem("", "Error!!!", e.getMessage(), null);
            }
        }//dissectNode
    } //Background

}
