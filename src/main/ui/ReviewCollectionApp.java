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
            } else if (userInput.equals("edit") || userInput.equals("view") || userInput.equals("delete")) {
                chooseReview(userInput);
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
        System.out.println("Please enter the title of the work you would like to review:");
        String workTitle = input.nextLine().trim();

        System.out.println("Please enter the title of your review:");
        String reviewTitle = input.nextLine().trim();

        int rating = handleRatingInput();

        Review toAdd = new Review(workTitle, reviewTitle, rating);

        handleWorkCreatorsAddition(toAdd);

        handleReviewTextAddition(toAdd);

        collection.addReview(toAdd);

        System.out.println("Review created and added to your collection!\n");
    }

    /*
     * REQUIRES: userInput.equals("edit") OR userInput.equals("view") OR
     * EFFECTS: allows the user to choose a review to act upon
     *          by showing the choices and processing user input
     */
    private void chooseReview(String userInput) {
        if (collection.getReviewTitlesList().size() == 0) {
            System.out.println("There are no reviews to " + userInput + ".\n");
        } else {
            printReviewList();
            System.out.println("Please enter the number of the review that you would like to " + userInput + ":");
            String reviewNumber = input.nextLine().trim();
            if (Pattern.matches("[0-9]*[0-9]", reviewNumber)) {
                Review chosenReview = collection.getReviewAt(Integer.parseInt(reviewNumber) - 1);
                if (chosenReview.equals(null)) {
                    System.out.println("There is no review numbered '" + reviewNumber + "' in the list.");
                } else {
                    if (userInput.equals("edit")) {
                        editReview(chosenReview);
                    } else if (userInput.equals("view")) {
                        printReview(chosenReview);
                    } else if (userInput.equals("delete")) {
                        deleteReview(chosenReview);
                    }
                }
            } else {
                System.out.println("'" + reviewNumber + "' is not a valid number in the list.\n");
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it to allow
     *          the user to make edits to an existing review in the collection
     */
    private void editReview(Review review) {
        System.out.println("What would you like to edit in '" + review.getReviewTitle() + "'?");
        printEditMenu();
        handleEditChoice(review);
    }

    /*
     * EFFECTS: shows the user the editing choices they can make in the editing menu
     */
    private void printEditMenu() {
        System.out.println("To set a new review title, enter 'review title'.");
        System.out.println("To set a new work title, enter 'work title'.");
        System.out.println("To choose a new rating, enter 'rating'.");
        System.out.println("To add work creators, enter 'add creators'.");
        System.out.println("To completely redo the list of work creators, enter 'reassign creators'.");
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
        } else if (userInput.equals("work title")) {
            review.setWorkTitle(input.nextLine().trim());
        } else if (userInput.equals("rating")) {
            review.setRating(handleRatingInput());
        } else if (userInput.equals("add creators")) {
            handleWorkCreatorsAddition(review);
        } else if (userInput.equals("reassign creators")) {
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
        System.out.println("Please type and press enter after each work creator.");
        System.out.println("When finished, press enter without typing anything in that line.");
        boolean keepAdding = true;
        String creator;
        while (keepAdding) {
            creator = input.nextLine().trim();
            if (creator.equals("")) {
                keepAdding = false;
            } else {
                review.addWorkCreator(creator);
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them to redo the assignment of work creators to review
     */
    private void handleWorkCreatorsRedo(Review review) {
        review.clearWorkCreators();
        System.out.println("The existing list of creators has been deleted.");
        handleWorkCreatorsAddition(review);
    }

    /*
     * MODIFIES: this
     * EFFECTS: receives the user's input and processes it for
     *          them add to the body text of review
     */
    private void handleReviewTextAddition(Review review) {
        System.out.println("Please type and press enter after each paragraph of the review text.");
        System.out.println("When finished, press enter without typing anything in that line.");
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
        review.clearReviewText();
        System.out.println("The existing body text has been removed.");
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
     * EFFECTS: shows the user review in detail, including all of
     *          its elements
     */
    private void printReview(Review review) {
        System.out.println("Review Title: " + review.getReviewTitle());
        System.out.println("Work Title: " + review.getWorkTitle());
        System.out.println("Work Creators: " + review.getWorkCreators());
        System.out.println("Rating: " + review.getRating() + "/10");
        for (String paragraph : review.getReviewText()) {
            System.out.println(paragraph);
        }
        System.out.println();

    }

    /*
     * MODIFIES: this
     * EFFECTS: removes review from the collection
     */
    private void deleteReview(Review review) {
        collection.removeReview(review);
        System.out.println("'" + review.getReviewTitle() + "' has been deleted.\n");
    }
}
