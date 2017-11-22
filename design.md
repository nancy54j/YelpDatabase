Yelp given json datatypes: 

Businesses
 - String ID
 - name (is this that important?)
 - lat/long 
 - stars
 - review count 
 - open/closed
 - attributes (takeout, valet, etc)
 - categories (mexican, burgers, etc)
 - hours open 
 - int totalhoursOpen() - hours open in a given week 
 - ratingTrend() - rate at which reviews (stars) are changing 
 - textclouds of reviews 
 - compareAround(int radius) : compare this restaurant to the restaurants around it 

Review  - maybe a subtype would be goodreview / bad review 
 - userID
 - businessID
 - stars
 - date
 - text
 - useful/funny/cool grouped together 
 - weight() - computes weight of review based on number of useful/funny/cool it gets 

User: subtypes include: newUser, eliteUser, popularUser 
 * top categories String[] 
 * ID string [] 
 * int numReviews 
 * int cumulative score (useful + funny + cool)
 * int numYearsElite
 * int joindate (as unix time?)
 * name	
 METHODS: 
  computeRatingTrend() : rate at which ratings idk
 computeUserBehavior(): predicts the degree of deviation from the average review based on the    cumulative score  
groupSimilarRestaurants(): returns a set of restaurants that are the most similar to a restaurant based on categories and previous reviews
 Proximity(restaurant) : distance between the two (based on lat/long) 


 - numReviews 
 - joindate
 - friends
 - "elite" (years user was elite.. not sure what that means rn)
 - average of all stars 

Other Datatypes: 

Category (of a restaurant)
Int Cumulative average rating 

Dictionary type - 
 Each word maps to a weight - negative for bad, positive for good 

