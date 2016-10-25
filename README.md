# TheDailyQuestion
App for discovering yourself



Self Reminder:
Change qNum to qID.
Put changing qID to an integer in PrefsAccessor

Note that the custom adapter sets a position number... Maybe this is just the ID number and the list can still be sorted/rearranged. Maybe not. ----> http://stackoverflow.com/questions/19676781/android-sort-listview-alphabetically
In case I want to sort the LV it seems pretty easy. Every time the question list is attached to the custom adapter, the position is changed if the question list is different. I should add an ID to my question object and set that tag in the custom adapter.

Yup that's the problem. Don't set position in the onClick for each list view item. Instead, use an attribute of the question object called ID that you can pass through the intent. That should work and allow the Listview to be sorted however.
