package com.company.stockmonitor;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class RssfeedFragment extends Fragment {


    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rssfeed_layout, container, false);

        lvRss = (ListView) view.findViewById(R.id.listViewRss);
        titles = new ArrayList<>();
        links = new ArrayList<>();

        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Uri uri = Uri.parse(links.get(i));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        new processInBg().execute();

        return view;
    }

    public InputStream getInputstream (URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }

    public class processInBg extends AsyncTask<Integer, Integer, Exception> {

        Exception exception;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            try {
                URL url = new URL("https://news.google.com/news/rss/?ned=us&gl=US&hl=en");

                XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();

                xppf.setNamespaceAware(false);

                XmlPullParser xpp = xppf.newPullParser();

                xpp.setInput(getInputstream(url),"UTF_8");

                boolean insideItem = false;

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem=true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                titles.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                links.add(xpp.nextText());
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem=false;
                    }

                    eventType = xpp.next();

                }
            }
            catch (MalformedURLException e) {
                exception = e;
            }
            catch (XmlPullParserException e) {
                exception = e;
            }
            catch (IOException e) {
                exception = e;
            }


            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getView().getContext(), android.R.layout.simple_list_item_1, titles);

            lvRss.setAdapter(adapter);

            super.onPostExecute(s);
        }
    }
}