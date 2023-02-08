import BooksOrganiser.{organiseClever, organiseSimple}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class BooksOrganiserSpec extends AnyFreeSpec {
  "organiseSimple" - {
    "should group books by author and remove duplicates" in {
      val books = Seq(
        Book("To Kill a Mockingbird", Author("Harper", "Lee")),
        Book("Pride and Prejudice", Author("Jane", "Austen")),
        Book("A Christmas Carol", Author("Charles", "Dickens")),
        Book("The Picture of Dorian Gray", Author("Oscar", "Wilde")),
        Book("To Kill a Mockingbird Part 2", Author("Harper", "Lee")),
        Book("Pride and Prejudice", Author("Jane", "Austen")),
        Book("A Christmas Carol", Author("Charles", "Dickens")),
        Book("To Kill a Mockingbird Part 2", Author("Harper", "Lee")),
      )

      testOrganisedBooks(
        organiseSimple(books),
        Map(
          Author("Harper", "Lee") -> Seq(
            Book("To Kill a Mockingbird", Author("Harper", "Lee")),
            Book("To Kill a Mockingbird Part 2", Author("Harper", "Lee"))
          ),
          Author("Jane", "Austen") -> Seq(Book("Pride and Prejudice", Author("Jane", "Austen"))),
          Author("Charles", "Dickens") -> Seq(Book("A Christmas Carol", Author("Charles", "Dickens"))),
          Author("Oscar", "Wilde") -> Seq(Book("The Picture of Dorian Gray", Author("Oscar", "Wilde"))),
        )
      )
    }
  }

  "organiseClever" - {
    "should group books by author and remove duplicates, taking into account author's name and book titles can be in mixed case, with keeping original names of last occurence" in {
      val books = Seq(
        Book("To Kill a Mockingbird", Author("Harper", "Lee")),
        Book("Pride and Prejudice", Author("Jane", "Austen")),
        Book("A Christmas Carol", Author("Charles", "Dickens")),
        Book("The Picture of Dorian Gray", Author("Oscar", "Wilde")),
        Book("TO Kill a Mockingbird  ", Author(" Harper", " Lee")),
        Book("TO Kill a MockingbirD part 2  ", Author(" HarpeR ", "LEE ")),
        Book("  PRIDE AND Prejudice", Author(" JANE", " Austen  ")),
        Book("A Christmas CaroL  ", Author(" Charles", " Dickens ")),
        Book("To Kill a Mockingbird Part 2", Author("  Harper", " Lee")),
      )

      testOrganisedBooks(
        organiseClever(books),
        Map(
          Author("Harper", "Lee") -> Seq(
            Book("TO Kill a Mockingbird", Author("Harper", "Lee")),
            Book("To Kill a Mockingbird Part 2", Author("Harper", "Lee"))
          ),
          Author("JANE", "Austen") -> Seq(Book("PRIDE AND Prejudice", Author("JANE", "Austen"))),
          Author("Oscar", "Wilde") -> Seq(Book("The Picture of Dorian Gray", Author("Oscar", "Wilde"))),
          Author("Charles", "Dickens") -> Seq(Book("A Christmas CaroL", Author("Charles", "Dickens"))),
        )
      )
    }
  }

  def testOrganisedBooks(actualBooks: Map[Author, Seq[Book]], expectedBooks: Map[Author, Seq[Book]]) = {
    (actualBooks.map { case (author, books) => books.map(book => (book, author)) }.flatten.toSeq should contain)
      .theSameElementsAs(
        expectedBooks.map { case (author, books) => books.map(book => (book, author)) }.flatten.toSeq
      )
  }
}
