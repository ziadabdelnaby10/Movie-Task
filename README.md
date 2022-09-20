# Movie Task App
This project is a practical assessment which is rendering some contents in a json file attached in the assets folder in an android app

## Description:
1. Show the movie’s different categories and on clicking on category details will show the available movies.
2. For each movie show its name, description.
3. User can add a new category.
4. User can add, edit, delete movies.

## The things that are done:
1. Parsed the contents of the json file with Gson Library
2. Create the database:
  2.1 create the Category Table
  2.2 create the Movie Table
  2.3 create the CategoryWithMovie Table which is a 1 - N Relation in Sqlite
  2.4 create the Data Access Object 
  2.5 create the singleton Database class
  2.6 create the MovieRepository to use the Room Operations
3. create the ViewModel For each Activity (Category - Movie)
4. used Coroutine to handle the threading in the viewmodel
5. add the parsed data from the json file into the database only one time using sharedprefrences then the user can add or delete what ever he wants (note : could've used RoomDatabase.callback)
6. construct the recyclerview for the two activities 
7. used kotlin higher level order funtion instead of interfaces to handle the click listeners in recyclerview

## Screenshots
![Category Activity](https://github.com/ziadabdelnaby10/Movie-Task/blob/main/1.png)
![Add Category](https://github.com/ziadabdelnaby10/Movie-Task/blob/main/2.png)
![Category Activity After Inserting](https://github.com/ziadabdelnaby10/Movie-Task/blob/main/3.png)
![Movie Activity](https://github.com/ziadabdelnaby10/Movie-Task/blob/main/4.png)
![Add Movie](https://github.com/ziadabdelnaby10/Movie-Task/blob/main/5.png)
![Movie Activity After Inserting](https://github.com/ziadabdelnaby10/Movie-Task/blob/main/6.png)
![Deleting Movie](https://github.com/ziadabdelnaby10/Movie-Task/blob/main/7.png)

### How To use the App?
The App will open on Category Activity it will shows the available categories (the parsed ones & the Updated Ones by the User)
The User Can click on specific category to see it's movies or click on the floating action button to add new category then an alertdialog will be available to enter the new category data
if the user clicked on a category the app will intent to Movie Activity 
A list of movies will be there
The User Can add a movie by clicking on the floating action button Or Edit a Movie by clicking on the movie he wants to edit Or Delete the Movie By LongClicking on the Movie

#### Topics used :
- ViewBinding
- MVVM
- RecyclerView
- Room Database (1 - N) Relation
- Coroutine
- LiveData
- Gson
