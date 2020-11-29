package Actors;
import com.google.api.services.youtube.model.SearchResult;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class Messages {

    public static final class UserParentActorCreate {
        public final String id;

        public UserParentActorCreate(String id) {
            this.id = requireNonNull(id);
        }

        @Override
        public String toString() {
            return "UserParentActorCreate(" + id + ")";
        }
    }

    public static final class WatchSearchResults {
        public final String searchKey;

        public WatchSearchResults(String searchKey) {
            this.searchKey = requireNonNull(searchKey);
        }

        @Override
        public String toString() {
            return "WatchSearchResults (" + searchKey + ")";
        }
    }

    public static final class UnWatchSearchResults{
        public final String searchKey;

        public UnWatchSearchResults(String searchKey) {
            this.searchKey = requireNonNull(searchKey);
        }

        @Override
        public String toString() {
            return "UnWatchSearchResults (" + searchKey + ")";
        }
    }

    public static final class SearchingResults {
        public final Set<SearchResult> results;
        public final String searchKey;

        public SearchingResults(Set<SearchResult> results, String searchKey) {
            this.results = requireNonNull(results);
            this.searchKey = requireNonNull(searchKey);
        }

        @Override
        public String toString() {
            return "Searching results (" + searchKey +")";
        }
    }

    public static final class RegisterActor{
        @Override
        public String toString() {
            return "Register";
        }
    }

    @Override
    public String toString() {
        return "Message";
    }
}
