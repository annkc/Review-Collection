package ui;

import model.Review;
import model.ReviewCollection;

import java.util.regex.*;
import java.util.Scanner;

/*
 * Represents the review collection application.
 */

public class ReviewCollectionApp {

    private Scanner input;
    private ReviewCollection collection;

    /*
     * EFFECTS: the review collection to start running
     */
    public ReviewCollectionApp() {
        runReviewCollectionApp();
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them to choose an option from the main menu
     */
    private void runReviewCollectionApp() {
        input = new Scanner(System.in);
        boolean keepRunning = true;
        collection = new ReviewCollection();
        String userInput;

        while (keepRunning) {
            printMainMenu();
            userInput = input.nextLine().toLowerCase().trim();
            if (userInput.equals("new")) {
                newReview();
            } else if (userInput.equals("edit")) {
                editReview();
            } else if (userInput.equals("view")) {
                viewReview();
            } else if (userInput.equals("delete")) {
                deleteReview();
            } else if (userInput.equals("quit")) {
                keepRunning = false;
            } else {
                System.out.println("That is not a valid choice.\n");
            }
        }
        System.out.println("Until next time!");
    }

    /*
     * EFFECTS: shows the user the choices they can make in the main menu
     */
    private void printMainMenu() {
        System.out.println("Welcome to your review collection. What would you like to do?");
        System.out.println("To create a new review, enter 'new'.");
        System.out.println("To edit an existing review, enter 'edit'.");
        System.out.println("To view your review, enter 'view'.");
        System.out.println("To delete an existing review, enter 'delete'.");
        System.out.println("To quit and exit, enter 'quit'.");
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it to allow
     *          the user to create a new review and add it to the collection
     */
    private void newReview() {
        System.out.println("Please enter the title of piece of media you would like to review:");
        String mediaTitle = input.nextLine().toLowerCase().trim();

        System.out.println("Please enter the title of your review:");
        String reviewTitle = input.nextLine().toLowerCase().trim();

        int rating = handleRatingInput();

        Review toAdd = new Review(mediaTitle, reviewTitle, rating);

        handleWorkCreatorsAddition(toAdd);

        handleReviewTextAddition(toAdd);

        collection.addReview(toAdd);

        System.out.println("Review created and added to your collection!\n");
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it to allow
     *          the user to make edits to an existing review in the collection
     */
    private void editReview() {
        if (collection.getReviewTitlesList().size() == 0) {
            System.out.println("There are no reviews to view.\n");
        } else {
            printReviewList();
            System.out.println("Please enter the title of the review that you would like to edit:");
            String reviewTitle = input.nextLine().trim();
            int reviewIndex = collection.findReviewIndex(reviewTitle);

            if (reviewIndex >= 0) {
                Review reviewToEdit = collection.getReviewAt(reviewIndex);
                System.out.println("What would you like to edit in '" + reviewToEdit.getReviewTitle() + "'?");
                printEditMenu();
                handleEditChoice(reviewToEdit);
            } else {
                System.out.println("There is no review with the title " + reviewTitle + ".\n");
            }
        }
    }

    /*
     * EFFECTS: shows the user the editing choices they can make in the editing menu
     */
    private void printEditMenu() {
        System.out.println("To set a new review title, enter 'review title'.");
        System.out.println("To set a new media title, enter 'media title'.");
        System.out.println("To choose a new rating, enter 'rating'.");
        System.out.println("To add new media contributors, enter 'add contributors'.");
        System.out.println("To completely redo the adding of a media contributors , enter 'reassign contributors'.");
        System.out.println("To add new paragraphs to the body text, enter 'add paragraph'.");
        System.out.println("To completely rewrite the body text, enter 'rewrite paragraphs'.");
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them to choose an option from the editing menu
     */
    private void handleEditChoice(Review review) {
        String userInput = input.nextLine().toLowerCase().trim();
        if (userInput.equals("review title")) {
            review.setReviewTitle(input.nextLine().trim());
        } else if (userInput.equals("media title")) {
            review.setWorkTitle(input.nextLine().trim());
        } else if (userInput.equals("rating")) {
            review.setRating(handleRatingInput());
        } else if (userInput.equals("add contributors")) {
            handleWorkCreatorsAddition(review);
        } else if (userInput.equals("reassign contributors")) {
            handleWorkCreatorsRedo(review);
        } else if (userInput.equals("add paragraphs")) {
            handleReviewTextAddition(review);
        } else if (userInput.equals("rewrite paragraphs")) {
            handleReviewTextRedo(review);
        } else {
            System.out.println("That is not a valid choice.\n");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it to
     *          get a number for a review's rating
     */
    private int handleRatingInput() {
        String ratingInput = "";
        boolean canProceed = false;
        while (!canProceed) {
            System.out.println("Please enter a rating out of ten (whole numbers only):");
            ratingInput = input.nextLine().toLowerCase().trim();
            canProceed = Pattern.matches("(10)|([0-9])", ratingInput);
            if (!canProceed) {
                System.out.println("'" + ratingInput + "' is not a valid rating.");
            }
        }
        return Integer.parseInt(ratingInput);
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them to add work creators to review
     */
    private void handleWorkCreatorsAddition(Review review) {
        System.out.println("Please enter contributors to the media one by one and press enter when finished.");
        boolean keepAdding = true;
        String contributor;
        while (keepAdding) {
            contributor = input.nextLine().trim();
            if (contributor.equals("")) {
                keepAdding = false;
            } else {
                review.addWorkCreator(contributor);
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them to redo the assignment of work creators to review
     */
    private void handleWorkCreatorsRedo(Review review) {
        for (String contributor : review.getWorkCreators()) {
            review.removeWorkCreator(contributor);
        }
        System.out.println("The existing list of contributors has been deleted.");
        handleWorkCreatorsAddition(review);
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them add to the body text of review
     */
    private void handleReviewTextAddition(Review review) {
        System.out.println("Please enter paragraphs of the review text one by one and press enter when finished.");
        boolean keepAdding = true;
        String paragraph;
        while (keepAdding) {
            paragraph = input.nextLine();
            if (paragraph.equals("")) {
                keepAdding = false;
            } else {
                review.addParagraphToReviewText(paragraph);
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them to rewrite the body text of review
     */
    private void handleReviewTextRedo(Review review) {
        for (String paragraph : review.getReviewText()) {
            review.removeParagraphFromReviewText(paragraph);
        }
        System.out.println("The existing body text has been deleted.");
        handleReviewTextAddition(review);
    }

    /*
     * EFFECTS: shows the user a numbered list of the reviews in the collection,
     *          with each review represented as its review title
     */
    private void printReviewList() {
        System.out.println("Here is the list of your reviews:");
        int position = 1;
        for (String title : collection.getReviewTitlesList()) {
            System.out.println(position + ": " + title);
        }
        System.out.println();
    }

    /*
     * EFFECTS: receives the user's input and processes it for
     *          them to choose a review from the collection and view it
     */
    private void viewReview() {
        if (collection.getReviewTitlesList().size() == 0) {
            System.out.println("There are no reviews to view.\n");
        } else {
            printReviewList();
            boolean reviewFound = false;
            System.out.println("Please enter the title of the review that you would like to view:");
            String reviewTitle = input.nextLine().trim();
            int reviewIndex = collection.findReviewIndex(reviewTitle);

            if (reviewIndex >= 0) {
                printReview(collection.getReviewAt(reviewIndex));
            } else {
                System.out.println("There is no review with the title " + reviewTitle + ".\n");
            }
        }
    }

    /*
     * EFFECTS: shows the user review in detail, including all of
     *          its elements
     */
    private void printReview(Review review) {
        System.out.println("Review Title: " + review.getReviewTitle());
        System.out.println("Media Title: " + review.getWorkTitle());
        System.out.println("Media Contributors: " + review.getWorkCreators());
        System.out.println("Rating: " + review.getRating() + "/10");
        for (String paragraph : review.getReviewText()) {
            System.out.println(paragraph);
        }
        System.out.println();

    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them to choose a review from the collection and remove it
     *          from the collection
     */
    private void deleteReview() {
        printReviewList();
        System.out.println("Please enter the number of the review that you would like to remove:");
        String reviewIndex = input.nextLine().trim();
        int collectionSize = collection.getReviewTitlesList().size();
        if (Pattern.matches("[0-9]*[0-9]", reviewIndex) && Integer.parseInt(reviewIndex) <= collectionSize) {
            Review reviewToRemove = collection.getReviewAt(Integer.parseInt(reviewIndex) - 1);
            collection.removeReview(reviewToRemove);
            System.out.println(reviewToRemove.getReviewTitle() + " has been removed.\n");
        } else {
            System.out.println("'" + reviewIndex + "' is not a valid number in the list.\n");
        }

    }
}
