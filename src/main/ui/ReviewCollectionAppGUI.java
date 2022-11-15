package ui;

import model.Review;
import model.ReviewCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Represents the review collection application with graphical user interface.
 */
public class ReviewCollectionAppGUI extends JFrame {

    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1000;
    private static final String JSON_FILE_PATHNAME = "./data/reviewcollection.json";


    private ReviewCollection collection;

    private JButton newReview;
    private JButton viewReviews;
    private JButton deleteReview;
    private JButton saveCollection;
    private JButton loadCollection;

    private JLabel middleSection;
    private ImageIcon thonkImage;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    public ReviewCollectionAppGUI() {
        super("Review Collection App");
        collection = new ReviewCollection();
        jsonWriter = new JsonWriter(JSON_FILE_PATHNAME);
        jsonReader = new JsonReader(JSON_FILE_PATHNAME);

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        thonkImage = new ImageIcon(".\\data\\thonkEmoji.png");
        middleSection = new JLabel(thonkImage);
        add(middleSection);
        setUpButtons();

        setUpButtonsSection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();

    }

    // EFFECTS: initializes the buttons and sets their behaviours when they are clicked
    private void setUpButtons() {
        setUpCreateReviewButton();
        setUpViewReviewButton();
        setUpDeleteReviewButton();
        setUpSaveCollectionButton();
        setUpLoadCollectionButton();
    }

    private void setUpCreateReviewButton() {
        newReview = new JButton("Create new review");
        newReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                collection.addReview(createReview());
            }
        });
    }

    private void setUpViewReviewButton() {
        viewReviews = new JButton("View your reviews");
        viewReviews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayReview(chooseReview());
                revalidate();
                repaint();
            }
        });
    }

    private void setUpDeleteReviewButton() {
        deleteReview = new JButton("Delete one of your reviews");
        deleteReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                collection.removeReview(chooseReview());
                revalidate();
            }
        });
    }

    private void setUpSaveCollectionButton() {
        saveCollection = new JButton("Save collection");
        saveCollection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(middleSection);
                middleSection = new JLabel(saveReviewCollection(), SwingConstants.CENTER);
                getContentPane().add(middleSection, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }

    private void setUpLoadCollectionButton() {
        loadCollection = new JButton("Load collection");
        loadCollection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(middleSection);
                middleSection = new JLabel(loadReviewCollection(), SwingConstants.CENTER);
                getContentPane().add(middleSection, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }

    // EFFECTS: creates and adds the buttons that allow the user to make choices on what to do in the app
    //          so they are displayed in the app
    private void setUpButtonsSection() {
        JPanel reviewButtonsSection = new JPanel();
        reviewButtonsSection.setLayout(new GridLayout(1,3));
        reviewButtonsSection.add(newReview);
        reviewButtonsSection.add(viewReviews);
        reviewButtonsSection.add(deleteReview);
        JPanel collectionButtonsSection = new JPanel();
        collectionButtonsSection.setLayout(new GridLayout(1,2));
        collectionButtonsSection.add(saveCollection);
        collectionButtonsSection.add(loadCollection);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,1));
        buttons.add(reviewButtonsSection);
        buttons.add(collectionButtonsSection);
        add(buttons, BorderLayout.NORTH);
    }

    // EFFECTS: takes in user input and returns review created using user input
    private Review createReview() {
        getContentPane().remove(middleSection);
        middleSection = new JLabel("Review successfully created!", SwingConstants.CENTER);
        getContentPane().add(middleSection, BorderLayout.CENTER);
        revalidate();
        repaint();
        return null;
    }


    // EFFECTS: displays the list of reviews in the collection in the app
    private void displayReviewList() {
        ArrayList<String> reviewTitlesList = collection.getReviewTitlesList();
        String[] reviewTitlesArray = reviewTitlesList.toArray(new String[reviewTitlesList.size()]);
        for (int i = 0; i < reviewTitlesArray.length; i++) {
            reviewTitlesArray[i] = i + ": " + reviewTitlesArray[i];
        }

    }

    // EFFECTS: takes in user input and returns the review the user selected
    private Review chooseReview() {
        displayReviewList();

        return null;
    }


    /*
     * EFFECTS: saves the review collection to file, returns message saying if it was successful
     */
    private String saveReviewCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            return "Your collection has been saved at: " + JSON_FILE_PATHNAME;
        } catch (FileNotFoundException e) {
            return "Cannot write to file at: " + JSON_FILE_PATHNAME;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads the review collection from file, returns message saying if it was successful
     */
    private String loadReviewCollection() {
        try {
            collection = jsonReader.read();
            return "Your collection has been loaded from: " + JSON_FILE_PATHNAME;
        } catch (IOException e) {
            return "Cannot read from file at: " + JSON_FILE_PATHNAME;
        }

    }

    // EFFECTS: displays the review in the app for the user to see
    private void displayReview(Review review) {
        collection.addReview(createReview());
        getContentPane().remove(middleSection);
        middleSection = new JLabel("<html>" + review.getReviewTitle() + "<br>" + "Work: " + review.getWorkTitle()
                + "<br>" + "Work Creators: " + review.getWorkCreators() + "<br>" + "Rating: " + review.getRating()
                + Review.RATING_TOTAL + "<br>" + "</html>");
        getContentPane().add(middleSection, BorderLayout.CENTER);
        revalidate();
        repaint();

    }

}
