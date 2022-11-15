package ui;

import model.ReviewCollection;

import javax.swing.*;
import java.awt.*;

/*
 * Represents the review collection application with graphical user interface.
 */
public class ReviewCollectionAppGUI extends JFrame {

    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1000;

    private ReviewCollection collection;
    private String[] mainMenuChoices = {"new", "existing"};
    private ImageIcon thonkImage;
    private JLabel eventLabel;
    private JButton newReview;
    private JButton viewReviews;
    private JButton deleteReview;
    private JButton saveCollection;
    private JButton loadCollection;
    private JLabel middleSection;

    public ReviewCollectionAppGUI() {
        super("Review Collection App");
        collection = new ReviewCollection();

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        thonkImage = new ImageIcon(".\\data\\thonkEmoji.png");
        setMiddleSection(Event.START);
        add(middleSection);
        buttonsSection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    // EFFECTS: creates and adds the buttons that allow the user to make choices on what to do in the app
    //          so they are displayed in the app
    private void buttonsSection() {
        newReview = new JButton("Create new review");
        viewReviews = new JButton("View your reviews");
        deleteReview = new JButton("Delete one of your reviews");
        saveCollection = new JButton("Save collection");
        loadCollection = new JButton("Load collection");

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

    // EFFECTS: sets the display in the middle to correspond to what event is occurring in the app
    private void setMiddleSection(Event event) {
        switch (event) {
            case START: default:
                middleSection = new JLabel(thonkImage);
                break;
            case CREATE:
                middleSection = new JLabel();
                //TODO: create helper method here to set up the creation process
                break;
            case VIEW:
                middleSection = new JLabel();
                //TODO: create helper method here to set up the list viewing and choosing process
                // and then create helper method to display review
                break;
            case DELETE:
                middleSection = new JLabel();
                //TODO: create helper method here to set up the list viewing and choosing process
                break;
            case SAVE:
                middleSection = new JLabel();
                break;
            case LOAD:
                middleSection = new JLabel();
                break;
        }
    }

    // Represents the events that can occur based on what the user chooses to do in the app
    private enum Event {
        START, CREATE, VIEW, DELETE, SAVE, LOAD
    }
}
