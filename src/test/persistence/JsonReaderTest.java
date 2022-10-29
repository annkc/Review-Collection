package persistence;

import model.Review;
import model.ReviewCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonReader

// (Referenced the JsonReaderTest class in the JsonSerializationDemo project)

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonexistentFile.json");
        try {
            ReviewCollection collection = reader.read();
            fail("IOException expected but wasn't thrown.");
        } catch (IOException e) {
            // test passes
        }
    }

    @Test
    public void testReaderEmptyReviewCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyReviewCollection.json");
        try {
            ReviewCollection collection = reader.read();
            assertEquals(0, collection.getReviewTitlesList().size());
        } catch (IOException e) {
            fail("IOException e thrown, couldn't read from file");
        }
    }

    @Test
    public void testReaderNotEmptyReviewCollection() {
        try {
            JsonReader reader = new JsonReader("./data/testWriterNotEmptyReviewCollection.json");
            ReviewCollection collection = reader.read();
            assertEquals(1, collection.getReviewTitlesList().size());
            Review readReview = collection.getReviewAt(0);
            assertEquals("Love Story", readReview.getWorkTitle());
            assertEquals("I Once Knew This Song", readReview.getReviewTitle());
            assertEquals(7, readReview.getRating());
            ArrayList<String> readWorkCreators = readReview.getWorkCreators();
            assertEquals(1, readWorkCreators.size());
            assertEquals("Taylor Swift", readWorkCreators.get(0));
            ArrayList<String> readReviewText = readReview.getReviewText();
            assertEquals(2, readReviewText.size());
            assertEquals("but not anymore. I've forgotten most of the lyrics", readReviewText.get(0));
            assertEquals("My memory isn't good enough for that", readReviewText.get(1));

        } catch (IOException e) {
            fail("An IOException was unexpectedly thrown.");
        }

    }
}
