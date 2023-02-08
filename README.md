### Run

You can run tests by
```sbt test```

(Please check `BooksOrganiserSpec.scala`)

### Notes
There are 2 implementations of the required task.

- Very basic one, doesn't take into account mixed case / extra spaces in author's name and book's title.
- A bit more "clever" - which probably would make sense in real scenarios where books come from user input. The implementation ignores case and extra white spaces of the author's name and book's title when grouping by author and removing
  duplicates. If authors with the same name but different cases are encountered, the name of the last occurrence
  is kept while removing extra spaces. We could capitalize the names of authors instead of leaving them as they were,
  however, this doesn't make sense for names such as Miguel [de] Cervantes? The same applies to book titles.
  On the huge dataset, probably mutable Map would make more sense (or can further think of other optimal solution
  without mutability).


### Task

Implement a function, which accepts a collection of books as an input and returns them grouped by the author.
The input collection can have duplicates (i.e. the same book multiple times), the resulting map should not have any duplicates anymore.

```
def organize(books: Seq[Book]): Map[Author, Seq[Book]] = {
...
}
```
Also, provide  your implementation of Book and Author.

a Book has:
```
author (Author)
title (String)
```

an Author has

```
first name (String)
last name (String
```
Use Scala 2.13.x. In case of any doubts, you can make your own assumptions - just let us know what it was.



