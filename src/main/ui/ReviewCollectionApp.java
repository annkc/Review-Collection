package ui;

import model.Review;
import model.ReviewCollection;

import java.util.ArrayList;
import java.util.regex.*;
import java.util.Scanner;

public class ReviewCollectionApp {

    private boolean keepRunning;
    private Scanner input;
    private ReviewCollection collection;


    public ReviewCollectionApp() {
        runReviewCollectionApp();
    }


    private void runReviewCollectionApp() {
        input = new Scanner(System.in);
        keepRunning = true;
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

    private void printMainMenu() {
        System.out.println("Welcome to your review collection. What would you like to do?");
        System.out.println("To create a new review, enter 'new'.");
        System.out.println("To edit an existing review, enter 'edit'.");
        System.out.println("To view your review, enter 'view'.");
        System.out.println("To delete an existing review, enter 'delete'.");
        System.out.println("To quit and exit, enter 'quit'.");
    }

    private void newReview() {
        System.out.println("Please enter the title of piece of media you would like to review:");
        String mediaTitle = input.nextLine().toLowerCase().trim();

        System.out.println("Please enter the title of your review:");
        String reviewTitle = input.nextLine().toLowerCase().trim();

        int rating = handleRatingInput();

        Review toAdd = new Review(mediaTitle, reviewTitle, rating);

        handleMediaContributorsAddition(toAdd);

        handleReviewTextAddition(toAdd);

        collection.addReview(toAdd);

        System.out.println("Review created and added to your collection!\n");
    }

    private void printEditMenu() {
        System.out.println("To set a new review title, enter 'review title'.");
        System.out.println("To set a new media title, enter 'media title'.");
        System.out.println("To choose a new rating, enter 'rating'.");
        System.out.println("To add new media contributors, enter 'add contributors'.");
        System.out.println("To completely redo the adding of a media contributors , enter 'reassign contributors'.");
        System.out.println("To add new paragraphs to the body text, enter 'add paragraph'.");
        System.out.println("To completely rewrite the body text, enter 'rewrite paragraphs'.");
    }

    private void handleEditChoice(Review review) {
        String userInput = input.nextLine().toLowerCase().trim();
        if (userInput.equals("review title")) {
            review.setReviewTitle(input.nextLine().trim());
        } else if (userInput.equals("media title")) {
            review.setMediaTitle(input.nextLine().trim());
        } else if (userInput.equals("rating")) {
            review.setRating(handleRatingInput());
        } else if (userInput.equals("add contributors")) {
            handleMediaContributorsAddition(review);
        } else if (userInput.equals("reassign contributors")) {
            handleMediaContributorsRedo(review);
        } else if (userInput.equals("add paragraphs")) {
            handleReviewTextAddition(review);
        } else if (userInput.equals("rewrite paragraphs")) {
            handleReviewTextRedo(review);
        } else {
            System.out.println("That is not a valid choice.\n");
        }
    }

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

    private void handleMediaContributorsAddition(Review r) {
        System.out.println("Please enter contributors to the media one by one and press enter when finished.");
        boolean keepAdding = true;
        String contributor;
        while (keepAdding) {
            contributor = input.nextLine().trim();
            if (contributor.equals("")) {
                keepAdding = false;
            } else {
                r.addMediaContributor(contributor);
            }
        }
    }

    private void handleMediaContributorsRedo(Review r) {
        for (String contributor : r.getMediaContributors()) {
            r.removeMediaContributor(contributor);
        }
        System.out.println("The existing list of contributors has been deleted.");
        handleMediaContributorsAddition(r);
    }

    private void handleReviewTextAddition(Review r) {
        System.out.println("Please enter paragraphs of the review text one by one and press enter when finished.");
        boolean keepAdding = true;
        String paragraph;
        while (keepAdding) {
            paragraph = input.nextLine();
            if (paragraph.equals("")) {
                keepAdding = false;
            } else {
                r.addParagraphToReviewText(paragraph);
            }
        }
    }

    private void handleReviewTextRedo(Review r) {
        for (String paragraph : r.getReviewText()) {
            r.removeParagraphFromReviewText(paragraph);
        }
        System.out.println("The existing body text has been deleted.");
        handleReviewTextAddition(r);
    }

    private void printReviewList() {
        System.out.println("Here is the list of your reviews:");
        int position = 1;
        for (String title : collection.getReviewTitlesList()) {
            System.out.println(position + ": " + title);
        }
        System.out.println();
    }

    private void editReview() {
        if (collection.getReviewTitlesList().size() == 0) {
            System.out.println("There are no reviews to view.\n");
        } else {
            printReviewList();
            System.out.println("Please enter the title of the review that you would like to edit:");
            String reviewTitle = input.nextLine().trim();
            int reviewIndex = collection.findReview(reviewTitle);

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

    private void viewReview() {
        if (collection.getReviewTitlesList().size() == 0) {
            System.out.println("There are no reviews to view.\n");
        } else {
            printReviewList();
            boolean reviewFound = false;
            System.out.println("Please enter the title of the review that you would like to view:");
            String reviewTitle = input.nextLine().trim();
            int reviewIndex = collection.findReview(reviewTitle);

            if (reviewIndex >= 0) {
                printReview(collection.getReviewAt(reviewIndex));
            } else {
                System.out.println("There is no review with the title " + reviewTitle + ".\n");
            }
        }
    }

    private void printReview(Review review) {
        System.out.println("Review Title: " + review.getReviewTitle());
        System.out.println("Media Title: " + review.getMediaTitle());
        System.out.println("Media Contributors: " + review.getMediaContributors());
        System.out.println("Rating: " + review.getRating() + "/10");
        for (String paragraph : review.getReviewText()) {
            System.out.println(paragraph);
        }
        System.out.println();

    }

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
