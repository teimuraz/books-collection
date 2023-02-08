case class Book(title: String, author: Author)

case class Author(firstName: String, lastName: String)

object BooksOrganiser {

  /**
    * Simplest organiser.
    * Doesn't take into account mixed case / extra spaces in author's name and book's title.
    */
  def organiseSimple(books: Seq[Book]): Map[Author, Seq[Book]] = books.distinct.groupBy(_.author)

  /**
    * A bit more advanced organizer.
    * Ignores case and extra white spaces of the author's name and book's title when grouping by author and removing
    * duplicates. If authors with the same name but different cases are encountered, the name of the last occurrence
    * is kept while removing extra spaces. We could capitalize the names of authors instead of leaving them as they were,
    * however, this doesn't make sense for names such as Miguel [de] Cervantes? The same applies to book titles.
    */
  def organiseClever(books: Seq[Book]): Map[Author, Seq[Book]] = {
    books
      .map { book =>
        normaliseBook(book) -> book.copy(
          author = removeWhiteSpacesFromAuthor(book.author),
          title = removeWhiteSpaces(book.title)
        )
      }
      .toMap
      .values
      .toSeq
      .groupBy(_.author)
  }

  def normaliseBook(book: Book) = book.copy(title = normaliseStr(book.title), author = normaliseAuthor(book.author))

  def normaliseAuthor(author: Author): Author =
    removeWhiteSpacesFromAuthor(author)
      .copy(firstName = normaliseStr(author.firstName), lastName = normaliseStr(author.lastName))


  def removeWhiteSpacesFromAuthor(author: Author): Author =
    author.copy(firstName = removeWhiteSpaces(author.firstName), lastName = removeWhiteSpaces(author.lastName))


  def normaliseStr(str: String): String = removeWhiteSpaces(str.toLowerCase)

  def removeWhiteSpaces(str: String): String = str.replaceAll("\\s+", " ").trim
}
