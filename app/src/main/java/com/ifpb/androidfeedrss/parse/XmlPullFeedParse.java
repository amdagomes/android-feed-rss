package com.ifpb.androidfeedrss.parse;

import android.util.Log;
import android.util.Xml;

import com.ifpb.androidfeedrss.model.Notice;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class XmlPullFeedParse extends BaseFeedParse {

    protected XmlPullFeedParse(String feedUrl) {
        super(feedUrl);
    }

    @Override
    public List<Notice> parse() {
        List<Notice> notices = null;
        XmlPullParser parser = Xml.newPullParser();

        try{
            parser.setInput(this.getInputStream(), null);
            int eventType = parser.getEventType();
            Notice currentNotice = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        notices = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ENTRY)){
                            currentNotice = new Notice();
                        } else if(currentNotice != null){
                            if(name.equalsIgnoreCase(TITLE)){
                                currentNotice.setTitle(parser.nextText());
                            } else if(name.equalsIgnoreCase(DESCRIPTION)){
                                currentNotice.setDescription(parser.nextText());
                            } else if (name.equalsIgnoreCase(DATE_UPDATE)){
                                currentNotice.setDateUpdate(parser.nextText());
                            } else if (name.equalsIgnoreCase(LINK)){
                                currentNotice.setLink(parser.getAttributeValue(null, "href"));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ENTRY) && currentNotice != null){
                            notices.add(currentNotice);
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            Log.e("Error: ", e.getMessage());
            throw new RuntimeException(e);
        }

        return notices;
    }
}
