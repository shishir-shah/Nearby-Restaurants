Hello,

It was an interesting project and I had fun working on it. In the paragraphs below I have explained in detail the libraries I have used and the features I have implemented.

Features:

The main activity displays the list of restaurants near your location. I have implemented the loading of the list asynchronously and it doesnt require any button clicks to get location or the items. Hence the list will be displayed as soon as you open the application. The items mentioned in the list include name, image, rating and address. It also includes an additional feature of distance from the current location, hence providing the user with more attributes to make the descision. 

Once the user clicks the item, it will be displayed in a neat cardview in a separate activity with information like name, image, address, phone number, rating and price level. I have implemented an additional feature that guides the user through google maps if the user clicks on the item. I have used recycler view along with card view, instead of just a regular view, to display the clicked item. Because of that, we can easily change the current implementation of click event to recent searched items.

Libraries:

Retrofit - API call
RxJava - Aysnchronous loading of list items
Picasso - Aysnchronous loading of images
ButterKnife - View Injection

Classes:

MainActivity - displays the list of nearby restaurants
Items - displays the selected item
GetLocation - performs location handling
Asyncload - contains static methods which return Observables for asyncronous 	    	loading of APIs
GetDataService - interface with retrofit calls 

