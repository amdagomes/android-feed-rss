package com.ifpb.androidfeedrss.parse;

import com.ifpb.androidfeedrss.model.Notice;

import java.util.List;

public interface FeedParser {
    List<Notice> parse();
}
