# jpa-exercice
This is an exercice repository about SpringData JPA. It is backed on [this sof question](https://stackoverflow.com/questions/65968259/select-only-specific-columns-from-joined-tables-many-to-many-in-spring-data-jp).

There are few differences between the table's name (category = theme here, and boardgame = board_game here)

Here the model schema :

![alt text](https://github.com/xxjokerx/jpa-exercice/blob/main/doc/model%20-%20with%20needed%20fields.jpg?raw=true)

The test which fetch those datas is call findDtoById_ListShouldNotBeEmpty.

### Run

If you want to run the TryingStuffApplication directly, you have to configure a postgresql database (and the application.properties).
You could also edit pom.xml to use h2 at runtime.

Finally, tests should run smoothly.