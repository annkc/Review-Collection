package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    private Review review;

    @BeforeEach
    void runBefore() {
        review = new Review("The Great Gatsby", "The Great Gatsby left me wondering why I read it.", 5);
    }

    @Test
    void testAddingAndClearingWorkCreators() {
        review.addWorkCreator("F. Scott Fitzgerald");
        review.addWorkCreator("Maxwell Perkins");
        assertEquals("F. Scott Fitzgerald", review.getWorkCreators().get(0));
        assertEquals("Maxwell Perkins", review.getWorkCreators().get(1));

        review.clearWorkCreators();
        assertEquals(0, review.getWorkCreators().size());
    }

    @Test
    void testAddingAndClearingReviewText() {
        String paragraph1 = "I knew of the Great Gatsby before I had to read it.";
        String paragraph2 = "I can't say that I would have read it if not for school.";
        String paragraph3 = "I can't say I enjoyed i. It wasn't bad, but it wasn't good.";

        review.addParagraphToReviewText(paragraph1);
        review.addParagraphToReviewText(paragraph2);
        review.addParagraphToReviewText(paragraph3);
        assertEquals("I knew of the Great Gatsby before I had to read it.", review.getReviewText().get(0));
        assertEquals("I can't say that I would have read it if not for school.", review.getReviewText().get(1));
        assertEquals("I can't say I enjoyed i. It wasn't bad, but it wasn't good.", review.getReviewText().get(2));

        review.clearReviewText();
        assertEquals(0, review.getReviewText().size());

    }


}