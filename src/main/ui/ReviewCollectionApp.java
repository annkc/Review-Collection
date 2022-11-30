package ui;

import model.Review;
import model.ReviewCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.*;
import java.util.Scanner;

/*
 * Represents the review collection application with command-line interface.
 *
 * (Referenced TellerApp class in https://github.students.cs.ubc.ca/CPSC210/TellerApp
 *  not including saveReviewCollection() and loadReviewCollection())
 * (Referenced the WorkRoomApp Class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *      saveReviewCollection() is modeled after saveWorkRoom()
 *      loadReviewCollection() is modeled after loadWorkRoom()
 * )
 */

public class ReviewCollectionApp {

    private static final String JSON_FILE_PATHNAME = "./data/reviewcollection.json";

    private Scanner input;
    private ReviewCollection collection;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /*
     * EFFECTS: has the review collection start running
     */
    public ReviewCollectionApp() {
        runReviewCollectionApp();
    }

    /*
     * MODIFIES: this
     * EFFECTS: setup and keeps the application running
     *          until the user has chosen to quit
     */
    private void runReviewCollectionApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_FILE_PATHNAME);
        jsonReader = new JsonReader(JSON_FILE_PATHNAME);
        collection = new ReviewCollection();

        boolean keepRunning = true;

        while (keepRunning) {
            printMainMenu();
            keepRunning = handleMainMenuChoice();
        }
        System.out.println("\nUntil next time!");
    }

    /*
     * EFFECTS: shows the user the choices they can make in the main menu
     */
    private void printMainMenu() {
        System.out.println("\nWelcome to your review collection. What would you like to do?");
        System.out.println("To create a new review, enter 'new'.");
        System.out.println("To edit an existing review, enter 'edit'.");
        System.out.println("To view your review, enter 'view'.");
        System.out.println("To delete an existing review, enter 'delete'.");
        System.out.println("To save your current collection, enter 'save'.");
        System.out.println("To load an existing collection, enter 'load'.");
        System.out.println("To quit and exit, enter 'quit'.");
    }

    /*
     * MODIFIES: this
     * EFFECTS: takes in the user's input and processes it for
     *          them to choose an option from the main menu, then enacts the choice
     *          returns false if the user chooses to quit,
     *          returns true otherwise
     */
    private boolean handleMainMenuChoice() {
        String userInput = input.nextLine().toLowerCase().trim();
        if (userInput.equals("new")) {
            newReview();
        } else if (userInput.equals("edit") || userInput.equals("view") || userInput.equals("delete")) {
            chooseReview(userInput);
        } else if (userInput.equals("save")) {
            saveReviewCollection();
        } else if (userInput.equals("load")) {
            loadReviewCollection();
        } else if (userInput.equals("quit")) {
            return false;
        } else {
            System.out.println("That is not a valid choice.\n");
        }
        return true;
    }

    /*
     * MODIFIES: this
     * EFFECTS: takes in the user's input and processes it for
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
     * REQUIRES: userInput.equals("edit") OR userInput.equals("view") OR userInput.equals("delete")
     * MODIFIES: this
     * EFFECTS: shows the list of existing reviews,
     *          takes in user input and processes it for user to choose an existing review
     *          and either edit, view, or delete it
     */
    private void chooseReview(String userInput) {
        if (collection.getReviewTitlesList().size() == 0) {
            System.out.println("There are no reviews to " + userInput + ".\n");
        } else {
            printReviewList();
            System.out.println("Please enter the number of the review that you would like to " + userInput + ":");
            String reviewNumber = input.nextLine().trim();
            if (Pattern.matches("[0-9]+", reviewNumber)) {
                Review chosenReview = collection.getReviewAt(Integer.parseInt(reviewNumber) - 1);
                if (chosenReview == null) {
                    System.out.println("There is no review numbered '" + reviewNumber + "' in the list.");
                } else {
                    handleExistingReviewChoice(userInput, chosenReview);
                }
            } else {
                System.out.println("'" + reviewNumber + "' is not a valid list number.\n");
            }
        }
    }

    /*
     * REQUIRES: userInput.equals("edit") OR userInput.equals("view") OR userInput.equals("delete")
     * MODIFIES: this AND review
     * EFFECTS: processes the given userInput and based on it,
     *          allows the user to edit, view, or delete chosenReview
     */
    private void handleExistingReviewChoice(String userInput, Review review) {
        if (userInput.equals("edit")) {
            editReview(review);
        } else if (userInput.equals("view")) {
            printReview(review);
        } else if (userInput.equals("delete")) {
            deleteReview(review);
        }
    }

    /*
     * MODIFIES: review
     * EFFECTS: gives the user choices in edits to make on an existing review
     *          then processes and carries out their choice
     */
    private void editReview(Review review) {
        System.out.println("What would you like to edit in '" + review.getReviewTitle() + "'?");
        printEditMenu();
        handleEditMenuChoice(review);
    }

    /*
     * EFFECTS: shows the user the editing choices they can make to an existing review
     */
    private void printEditMenu() {
        System.out.println("\nTo set a new review title, enter 'review title'.");
        System.out.println("To set a new work title, enter 'work title'.");
        System.out.println("To choose a new rating, enter 'rating'.");
        System.out.println("To add work creators, enter 'add creators'.");
        System.out.println("To completely redo the list of work creators, enter 'reassign creators'.");
        System.out.println("To add new paragraphs to the body text, enter 'add paragraphs'.");
        System.out.println("To completely rewrite the body text, enter 'rewrite paragraphs'.");
    }

    /*
     * MODIFIES: review
     * EFFECTS: takes in user input and processes it for
     *          them to choose an editing option to make on an existing review
     *          and then carry it out
     */
    private void handleEditMenuChoice(Review review) {
        String userInput = input.nextLine().toLowerCase().trim();
        if (userInput.equals("review title")) {
            System.out.println("Please enter the new review title:");
            review.setReviewTitle(input.nextLine().trim());
        } else if (userInput.equals("work title")) {
            System.out.println("Please enter the new work title:");
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
     * EFFECTS: takes in user input and checks it until it is
     *          a valid rating, then returns the rating
     */
    private int handleRatingInput() {
        String ratingInput = "";
        boolean canProceed = false;
        while (!canProceed) {
            System.out.println("Please enter a rating out of ten (whole numbers only):");
            ratingInput = input.nextLine().toLowerCase().trim();
            canProceed = Pattern.matches("[0-9]+", ratingInput) && Integer.parseInt(ratingInput) <= Review.RATING_TOTAL;
            if (!canProceed) {
                System.out.println("'" + ratingInput + "' is not a valid rating.");
            }
        }
        return Integer.parseInt(ratingInput);
    }

    /*
     * MODIFIES: review
     * EFFECTS: takes in user input for them to add work creators to review
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
     * MODIFIES: review
     * EFFECTS: clears out the list of work creators in review, then
     *          takes in user input for them to add new work creators to review
     */
    private void handleWorkCreatorsRedo(Review review) {
        review.clearWorkCreators();
        System.out.println("The existing list of creators has been deleted.");
        handleWorkCreatorsAddition(review);
    }

    /*
     * MODIFIES: review
     * EFFECTS: takes in user input for them to add to the body text of review
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
     * MODIFIES: review
     * EFFECTS: clears out the list of work creators in review, then
     *          takes in user input for them to add to the body text of review
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
            position++;
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
        System.out.println("Rating: " + review.getRating() + "/" + Review.RATING_TOTAL);
        for (String paragraph : review.getReviewText()) {
            System.out.println(paragraph);
        }
        System.out.println();

    }

    /*
     * REQUIRES: review must be a review in the collection
     * MODIFIES: this
     * EFFECTS: removes review from the collection
     */
    private void deleteReview(Review review) {
        collection.removeReview(review);
        System.out.println("'" + review.getReviewTitle() + "' has been deleted.\n");
    }

    /*
     * EFFECTS: saves the review collection to file
     */
    private void saveReviewCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("Your collection has been saved at: " + JSON_FILE_PATHNAME);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to file at: " + JSON_FILE_PATHNAME);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads the review collection from file
     */
    private void loadReviewCollection() {
        try {
            collection = jsonReader.read();
            System.out.println("Your collection has been loaded from: " + JSON_FILE_PATHNAME);
        } catch (IOException e) {
            System.out.println("Cannot read from file at: " + JSON_FILE_PATHNAME);
        }

    }
}
