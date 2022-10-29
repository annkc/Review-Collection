package persistence;

import model.Review;
import model.ReviewCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonWriter

// (Referenced the JsonWriterTest class in the JsonSerializationDemo project)

public class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            ReviewCollection collection = new ReviewCollection();
            JsonWriter writer = new JsonWriter(".data/my\0illegal:fileName");
            writer.open();
            fail("Expected IOExpection was not thrown.");
        } catch (IOException e) {
            // test passes
        }
    }

    @Test
    public void testWriterEmptyReviewCollection() {
        try {
            ReviewCollection collection = new ReviewCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyReviewCollection.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyReviewCollection.json");
            collection = reader.read();
            assertEquals(0, collection.getReviewTitlesList().size());
        } catch (IOException e) {
            fail("An IOException was unexpectedly thrown.");
        }
    }

    @Test
    public void testWriterNotEmptyReviewCollection() {
        try {
            ReviewCollection collection = new ReviewCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterNotEmptyReviewCollection.json");
            Review review = new Review("Love Story", "I Once Knew This Song", 7);
            review.addWorkCreator("Taylor Swift");
            review.addParagraphToReviewText("but not anymore. I've forgotten most of the lyrics");
            review.addParagraphToReviewText("My memory isn't good enough for that");
            collection.addReview(review);
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNotEmptyReviewCollection.json");
            collection = reader.read();
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
