# My Personal Project

## Project Proposal
My project will be an app that serves as a collection of reviews. It will allow one to write reviews for any media, 
from books to movies to video games, including the ability to add ratings to each review alongside a written account in
text if one so wishes. One can create and delete as many reviews as one wishes. This app can be used by everyone and 
anyone who simply wishes to keep all their reviews together in one place and without the use of other applications. 
I personally very much enjoy keeping note of my thoughts after I finish a book or an episode of a show to look back on 
at a later time, which is where this idea originally stemmed from. This could possibly be useful to others, and would 
also be something that I would personally find to be convenient to have.

## User Stories
- As a user, I want to be able to save my review collection to file.
- As a user, I want to be able to be able to load my review collection from file.
- As a user, I want to be able to title my reviews.
- As a user, I want to be able to specify the name of the reviewed work and an arbitrary number of creator(s).
- As a user, I want to be able to be able to include a rating out of 10 in each review.
- As a user, I want to be able to add paragraphs of body text in each review an arbitrary number of times.
- As a user, I want to be able to *add a review to the collection an arbitrary number of times.*
- As a user, I want to be able to delete a review.
- As a user, I want to be able to view a list of all my reviews.
- As a user, I want to be able to edit my reviews.


# Instructions for Grader
- You can generate the first required event related to adding Xs to a Y (which is adding Reviews to a ReviewCollection)
by first clicking the "Create new review" button that can be found among the buttons at the top of the window. You then
fill in the text fields to give input for what you would like to be in your review. When you do this, you must enter a 
review title and must enter an integer between 0 and 10 for the rating in order for the app to allow you to create the 
review. The other text fields can be left blank or filled in as desired. When you are done filling out the information,
click the "Create" button to create the review and add it to the review collection. The list of added reviews can be 
viewed by clicking the "View all reviews" button that can be found among the buttons at the top of the window.
- You can generate the second required event related to adding Xs to a Y (which is removing Reviews that were previously
added to a ReviewCollection) by first clicking the "Delete a review" button that can be found among the 
buttons at the top of the window. You then find the review title of the review that you would like to delete from the 
review collection in the shown list (scrolling to find it you need to) and then click on that review title to select it.
The delete button should light up when a review title has been selected, and you then click it to delete the review.
- You can locate my visual component by seeing the image displayed in the middle of the app after it launches. Another
one of the visual components can be found by following the instructions that detail how to add a review to the
review collection (found in the first bullet point), and at the end of the process the app displays an image as 
confirmation that the review creation was successful.
- You can save the state of my application by clicking the "Save collection" button that can be found among the buttons
at the top of the window.
- You can reload the state of my application by clicking the "Load collection" button that can be found among the buttons
at the top of the window.


# Phase 4: Task 2

Wed Nov 30 11:26:46 PST 2022
Review 'The Great Gatsby left me wondering why I read it.' added to collection.

Wed Nov 30 11:26:46 PST 2022
Review 'The game I have wanted to play' added to collection.

Wed Nov 30 11:26:55 PST 2022
Review 'My favourite book' added to collection.

Wed Nov 30 11:27:01 PST 2022
Review 'The game I have wanted to play' removed from collection.

- (Note: When loading from file, the addition of each of the reviews being loaded in appears as a 'review added' entry 
in the event log.)