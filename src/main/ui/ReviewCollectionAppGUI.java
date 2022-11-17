package ui;

import model.Review;
import model.ReviewCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/*
 * Represents the review collection application with graphical user interface.
 */
public class ReviewCollectionAppGUI extends JFrame {

    private static final int HEIGHT = 650;
    private static final int WIDTH = 1000;
    private static final String JSON_FILE_PATHNAME = "./data/reviewcollection.json";
    private static final String[] TEXT_BOX_LABELS = {"Review Title:", "Work Title:",
            "Work Creators (separate with commas):", "Rating (integers 0-10):", "Rating (integers 0-10):",
            "Body Text:"};
    private static final Border MARGINS = BorderFactory.createEmptyBorder(20, 20, 20, 20);


    private ReviewCollection collection;

    private JButton newReviewOptionButton;
    private JButton viewReviewOptionButton;
    private JButton viewAllReviewsOptionButton;
    private JButton deleteReviewOptionButton;
    private JButton saveCollectionButton;
    private JButton loadCollectionButton;

    private JButton createButton;
    private JButton viewButton;
    private JButton deleteButton;
    private JButton currentChooseButton; // the button that involves choosing an existing review

    private ArrayList<JTextComponent> creationTextBoxes;
    private JList reviewList;
    private JComponent currentMiddle; // the current component displayed in the middle
    private JPanel reviewCreationForm;
    private JLabel middleMessage;
    private ImageIcon thonkImage; // starting image

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: constructs the app, initializing the fields and setting up and displaying the app's gui
    public ReviewCollectionAppGUI() {
        super("Review Collection App");
        collection = new ReviewCollection();
        jsonWriter = new JsonWriter(JSON_FILE_PATHNAME);
        jsonReader = new JsonReader(JSON_FILE_PATHNAME);

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        thonkImage = new ImageIcon(".\\data\\thonkEmoji.png");
        middleMessage = new JLabel(thonkImage);
        add(middleMessage);
        currentMiddle = middleMessage;
        setUpButtons();

        setUpButtonsSection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();

    }

    // EFFECTS: initializes the buttons and what will occur when they are clicked
    private void setUpButtons() {
        setUpCreateReviewOptionButton();
        setUpViewReviewOptionButton();
        setUpViewAllReviewsOptionButton();
        setUpDeleteReviewOptionButton();
        setUpSaveCollectionButton();
        setUpLoadCollectionButton();
        setUpCreateButton();
        setUpViewButton();
        setUpDeleteButton();
    }

    // EFFECTS: initializes the create review option button and sets that the user will be shown a review creation
    //          form when the button is clicked
    private void setUpCreateReviewOptionButton() {
        newReviewOptionButton = new JButton("Create new review");
        newReviewOptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setUpCreationForm();
                getContentPane().remove(currentMiddle);
                currentMiddle = reviewCreationForm;
                add(currentMiddle, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }

    // EFFECTS: initializes the view review option button and sets that the user can select a review from the review
    //          list and read the review when the button is clicked
    private void setUpViewReviewOptionButton() {
        viewReviewOptionButton = new JButton("View a review");
        viewReviewOptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentMiddle);
                currentChooseButton = viewButton;
                setUpReviewSelection();
                revalidate();
                repaint();
            }
        });
    }

    private void setUpViewAllReviewsOptionButton() {
        viewAllReviewsOptionButton = new JButton("View all reviews");
        viewAllReviewsOptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentMiddle);
                displayAllReviews();
                revalidate();
                repaint();
            }
        });
    }

    // EFFECTS: initializes the delete review option button and sets that the user can select a review from the review
    //          list and delete the review when the button is clicked
    private void setUpDeleteReviewOptionButton() {
        deleteReviewOptionButton = new JButton("Delete one of your reviews");
        deleteReviewOptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentMiddle);
                currentChooseButton = deleteButton;
                setUpReviewSelection();
                revalidate();
                repaint();
            }
        });
    }

    // EFFECTS: initializes the save collection button and sets the current review collection will be saved to file
    //          when the button is clicked
    private void setUpSaveCollectionButton() {
        saveCollectionButton = new JButton("Save collection");
        saveCollectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(currentMiddle);
                middleMessage = new JLabel(saveReviewCollection(), SwingConstants.CENTER);
                getContentPane().add(middleMessage, BorderLayout.CENTER);
                currentMiddle = middleMessage;
                revalidate();
                repaint();
            }
        });
    }

    // EFFECTS: initializes the load collection button and sets that the review collection on file will be loaded
    //          when the button is clicked
    private void setUpLoadCollectionButton() {
        loadCollectionButton = new JButton("Load collection");
        loadCollectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(currentMiddle);
                middleMessage = new JLabel(loadReviewCollection(), SwingConstants.CENTER);
                getContentPane().add(middleMessage, BorderLayout.CENTER);
                currentMiddle = middleMessage;
                revalidate();
                repaint();
            }
        });
    }

    // EFFECTS: initializes the create review button and sets that input provided by the user will be used to create
    //          a review and add it to the collection when the button is clicked
    private void setUpCreateButton() {
        createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ratingInput = creationTextBoxes.get(3).getText().trim();
                String reviewTitleInput = creationTextBoxes.get(0).getText();
                if (Pattern.matches("[0-9]+", ratingInput) && Integer.parseInt(ratingInput) <= Review.RATING_TOTAL
                        && reviewTitleInput.length() > 0) {
                    createReview();
                    getContentPane().remove(currentMiddle);
                    middleMessage = new JLabel("Review successfully created!", SwingConstants.CENTER);
                    getContentPane().add(middleMessage, BorderLayout.CENTER);
                    currentMiddle = middleMessage;
                    revalidate();
                    repaint();
                }
            }
        });
    }

    // EFFECTS: initializes the view review button and sets that the review that the user has selected from the list
    //          will be displayed for the user to view when the button is clicked
    private void setUpViewButton() {
        viewButton = new JButton("View");
        viewButton.setEnabled(false);
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(currentMiddle);
                int index = reviewList.getSelectedIndex();
                displayReview(collection.getReviewAt(index));
            }
        });
    }

    // EFFECTS: initializes the delete review button and sets that the review that the user has selected from the list
    //          will be deleted from the review collection when the button is clicked
    private void setUpDeleteButton() {
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(currentMiddle);
                int index = reviewList.getSelectedIndex();
                collection.removeReview(collection.getReviewAt(index));

                middleMessage = new JLabel("Review deleted!", SwingConstants.CENTER);
                getContentPane().add(middleMessage, BorderLayout.CENTER);
                currentMiddle = middleMessage;
                revalidate();
                repaint();
            }
        });
    }



    // EFFECTS: creates and adds the buttons that allow the user to make choices on what to do in the app
    //          so they are displayed in the app
    private void setUpButtonsSection() {
        JPanel reviewButtonsSection = new JPanel();
        reviewButtonsSection.setLayout(new GridLayout(1,4));
        reviewButtonsSection.add(newReviewOptionButton);
        reviewButtonsSection.add(viewReviewOptionButton);
        reviewButtonsSection.add(viewAllReviewsOptionButton);
        reviewButtonsSection.add(deleteReviewOptionButton);
        JPanel collectionButtonsSection = new JPanel();
        collectionButtonsSection.setLayout(new GridLayout(1,2));
        collectionButtonsSection.add(saveCollectionButton);
        collectionButtonsSection.add(loadCollectionButton);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,1));
        buttons.add(reviewButtonsSection);
        buttons.add(collectionButtonsSection);
        add(buttons, BorderLayout.NORTH);
    }

    // EFFECTS: sets up the form that users interact with and enter input into to create a new review
    private void setUpCreationForm() {
        creationTextBoxes = new ArrayList<>();
        reviewCreationForm = new JPanel();
        for (int i = 0; i < TEXT_BOX_LABELS.length - 2; i++) {
            JPanel row = new JPanel();
            JLabel label = new JLabel(TEXT_BOX_LABELS[i], JLabel.TRAILING);
            JTextField textBox = new JTextField(50);
            creationTextBoxes.add(textBox);
            label.setLabelFor(textBox);
            row.add(label);
            row.add(textBox);
            reviewCreationForm.add(row);
        }
        JPanel row = new JPanel();
        JTextArea textBox = new JTextArea(20,100);
        textBox.setLineWrap(true);

        creationTextBoxes.add(textBox);
        row.add(new JScrollPane(textBox));
        reviewCreationForm.add(row);
        reviewCreationForm.add(createButton);
    }

    // EFFECTS: displays the list of reviews in the collection in the app
    private void setUpReviewSelection() {
        ArrayList<String> reviewTitlesList = collection.getReviewTitlesList();
        String[] reviewTitlesArray = reviewTitlesList.toArray(new String[reviewTitlesList.size()]);
        reviewList = new JList(reviewTitlesArray);
        reviewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reviewList.setLayoutOrientation(JList.VERTICAL);
        reviewList.setVisibleRowCount(-1);
        JScrollPane reviewListPane = new JScrollPane(reviewList);
        currentMiddle = new JPanel(new GridLayout(2, 1));
        currentMiddle.add(reviewListPane);
        currentMiddle.add(currentChooseButton);
        add(currentMiddle, BorderLayout.CENTER);
        currentChooseButton.setEnabled(false);
        reviewList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (reviewList.getSelectedIndex() == -1) {
                        currentChooseButton.setEnabled(false);
                    } else {
                        currentChooseButton.setEnabled(true);
                    }
                }
            }
        });
    }



    // EFFECTS: uses user input to create a new review and add it to the review collection
    private void createReview() {
        String reviewTitleInput = creationTextBoxes.get(0).getText();
        String workTitleInput = creationTextBoxes.get(1).getText();
        String ratingInput = creationTextBoxes.get(3).getText().trim();
        Review newReview = new Review(workTitleInput, reviewTitleInput, Integer.parseInt(ratingInput));
        collection.addReview(newReview);
        String workCreatorsInput = creationTextBoxes.get(2).getText();
        String reviewTextInput = creationTextBoxes.get(4).getText();
        String[] workCreators = workCreatorsInput.split(",");
        String[] reviewText = reviewTextInput.split("\n");
        for (String creator : workCreators) {
            newReview.addWorkCreator(creator);
        }
        for (String paragraph : reviewText) {
            newReview.addParagraphToReviewText(paragraph);
        }
    }

    // EFFECTS: displays the review in the app for the user to see
    private void displayReview(Review review) {
        getContentPane().remove(currentMiddle);
        String middleMessageText = "<html>" + review.getReviewTitle() + "<br><br>" + "Work: " + review.getWorkTitle()
                + "<br>" + "Work Creators: " + review.getWorkCreators() + "<br>" + "Rating: " + review.getRating()
                + "/" + Review.RATING_TOTAL + "<br>";
        ArrayList<String> reviewTextList = review.getReviewText();
        for (String paragraph : reviewTextList) {
            middleMessageText += "<br>" + paragraph;
        }
        middleMessageText += "</html>";
        middleMessage = new JLabel(middleMessageText);

        JPanel reviewDisplay = new JPanel();
        reviewDisplay.setBorder(MARGINS);
        reviewDisplay.add(middleMessage);
        currentMiddle = new JScrollPane(middleMessage);
        currentMiddle.setBorder(MARGINS);
        getContentPane().add(currentMiddle, BorderLayout.CENTER);
        revalidate();
        repaint();

    }


    private void displayAllReviews() {
        getContentPane().remove(currentMiddle);
        String middleMessageText = "<html>";
        for (int i = 0; i < collection.getReviewTitlesList().size(); i++) {
            Review review = collection.getReviewAt(i);
            middleMessageText += "Review Title: " + review.getReviewTitle() + "<br>" + "Work Title: "
                    + review.getWorkTitle() + "<br>" + "Work Creators: " + review.getWorkCreators() + "<br>"
                    + "Rating: " + review.getRating() + "/" + Review.RATING_TOTAL + "<br>";
            ArrayList<String> reviewTextList = review.getReviewText();
            middleMessageText += "Body Text:";
            for (String paragraph : reviewTextList) {
                middleMessageText += "<br>" + paragraph;
            }
            middleMessageText += "<br><br><br><br>";
        }
        middleMessageText += "</html>";
        middleMessage = new JLabel(middleMessageText);
        JPanel reviewDisplay = new JPanel();
        reviewDisplay.add(middleMessage);
        currentMiddle = new JScrollPane(middleMessage);
        currentMiddle.setBorder(MARGINS);
        getContentPane().add(currentMiddle, BorderLayout.CENTER);
        revalidate();
        repaint();
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

}
