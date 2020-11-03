package models;
/**
 * Sample Java code for youtube.comments.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class Comments {
    // You need to set this value for your code to compile.
    private static final String DEVELOPER_KEY = "AIzaSyCDSxqEwVEt6PiATRyGqYm3_dYPFhsHERg";

    private static final long NUMBER_OF_COMMENTS_RETURNED = 100;
    private static final String APPLICATION_NAME = "API code samples";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static YouTube youtube;

    public List<CommentThread> SearchComment(){
        List<CommentThread> searchCommentsList = null;
        try{
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer(){
                public void initialize(HttpRequest request) throws IOException {
                }
            })
                    .setApplicationName(APPLICATION_NAME)
                    .build();

//            YouTube youtubeService = getService();
            YouTube.CommentThreads.List comments = youtube.commentThreads()
                    .list("snippet,replies");


            CommentThreadListResponse response = comments.setKey(DEVELOPER_KEY)
                    .setId("_VB39Jo8mAQ")
                    .setMaxResults(NUMBER_OF_COMMENTS_RETURNED)
                    .setFields("items(snippet/topLevelComment/snippet/textDisplay)")
                    .execute();

            searchCommentsList = response.getItems();

            List<String> commentList = new ArrayList<>();

            if(searchCommentsList != null){
                for (CommentThread searchComments : searchCommentsList){
                    commentList.add(searchComments.toString());
                }
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return searchCommentsList;
    }

}
