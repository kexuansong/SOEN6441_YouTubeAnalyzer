package models;
/**
 * Sample Java code for youtube.comments.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import akka.http.impl.engine.ws.FrameHandler;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;


import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class Comments {

    private static final String DEVELOPER_KEY = "AIzaSyBVJ00Q0_3VQIe__67PJ82HwQmY75cE8JA";

    private static final long NUMBER_OF_COMMENTS_RETURNED = 100;
    private static final String APPLICATION_NAME = "API code samples";

    private static YouTube youtube;

    public String SearchComment(String VideoId) throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        try {
            List<CommentThread> searchCommentsList = null;
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            })
                    .setApplicationName(APPLICATION_NAME)
                    .build();


            YouTube.CommentThreads.List comments = youtube.commentThreads()
                    .list("snippet,replies");


            CommentThreadListResponse response = comments.setKey(DEVELOPER_KEY)
                    .setVideoId(VideoId)
                    .setMaxResults(NUMBER_OF_COMMENTS_RETURNED)
                    .execute();

            searchCommentsList = response.getItems();


            List<String> commentsList = new ArrayList<>();
            List<String> emojiList = new ArrayList<>();

            for (CommentThread c : searchCommentsList) {
                String comment = c.getSnippet().getTopLevelComment().getSnippet().getTextDisplay();
                commentsList.add(comment);

                if (EmojiManager.containsEmoji(comment) == true) {
                    List<String> emoji = EmojiParser.extractEmojis(comment);
                    List<String> emojiPro = emoji.stream().map(e -> {
                        e.replace("[", "");
                        e.replace("]", "");
                        e.split(",");
                        return e;
                    })
                            .distinct().collect(toList());
                    emojiList.add(emojiPro.toString());
                }

            }


            List<String> happyEmo = Arrays.asList("\uD83D\uDE00", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83E\uDD23", "\uD83D\uDE03"
                    , "\uD83D\uDE04", "\uD83D\uDE05", "\uD83D\uDE06", "\uD83D\uDE09", "\uD83D\uDE0A", "\uD83D\uDE0B", "\uD83D\uDE0E"
                    , "\uD83D\uDE0D", "\uD83D\uDE18", "\uD83E\uDD70", "\uD83D\uDE17", "\uD83D\uDE19", "\uD83D\uDE1A", "\u263A\uFE0F"
                    , "\uD83D\uDE42", "\uD83E\uDD17", "\uD83E\uDD29", "\uD83D\uDE3A", "\uD83D\uDE38", "\uD83D\uDE39", "\uD83D\uDE3B"
                    , "\uD83D\uDE3D", "\uD83D\uDC98", "\u2665\uFE0F", "\uD83D\uDC93", "\uD83D\uDC95", "\uD83D\uDC96", "\uD83D\uDC97"
                    , "\uD83D\uDD25", "\uD83C\uDF89", "\uD83E\uDD18", "\uD83D\uDCAF", "\uD83D\uDC4F", "\uD83D\uDC4C", "\uD83D\uDC4D"
                    , "\uD83D\uDCAA", "\u270C\uFE0F"
            );

            List<String> sadEmo = Arrays.asList(
                    "\u2639\uFE0F️", "\uD83D\uDE41", "\uD83D\uDE16", "\uD83D\uDE1E", "\uD83D\uDE1F", "\uD83D\uDE24", "\uD83D\uDE22", "\uD83D\uDE2D"
                    , "\uD83D\uDE26", "\uD83D\uDE27", "\uD83D\uDE28", "\uD83D\uDE29",
                    "\uD83E\uDD2F", "\uD83D\uDE2C", "\uD83D\uDE30", "\uD83D\uDE31", "\uD83E\uDD75"
                    , "\uD83E\uDD76", "\uD83D\uDE33", "\uD83D\uDE35", "\uD83D\uDE21", "\uD83D\uDE20", "\uD83E\uDD2C", "\uD83E\uDD2E"
                    , "\uD83D\uDE37", "\uD83E\uDD27", "\uD83D\uDC7F", "\uD83D\uDE3F", "\uD83D\uDE40", "\uD83D\uDC4E", "\uD83D\uDC47"
            );


            List<String> happy = emojiList.stream()
                    .map(e -> e.replace("[", "").replace("]", ""))
                    .filter(w1 -> happyEmo.stream().anyMatch(w2 -> w1.contains(w2)))
                    .collect(toList());

            List<String> sad = emojiList.stream()
                    .map(e -> e.replace("[", "").replace("]", ""))
                    .filter(w1 -> sadEmo.stream().anyMatch(w2 -> w1.contains(w2)))
                    .collect(toList());


            int criteria = (int) (emojiList.size() * 0.7);

            if (happy.size() >= criteria) {
                return ("\uD83D\uDC4D");
            } else if (sad.size() >= criteria) {
                return ("\uD83D\uDC4E");
            } else {
                return ("\uD83D\uDE10");
            }
        } catch (Exception e) {
            System.out.println(e);
            return("CommentDisabled");
        }
    }
}
