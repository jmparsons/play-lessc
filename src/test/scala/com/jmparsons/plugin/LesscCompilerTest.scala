import org.specs2.mutable._
import org.specs2.specification._
import com.jmparsons.plugin._
import java.io.File

class LesscSpec extends Specification {

  trait testData extends Scope {
    val validCssMin = """body{background:#ccc}#main{background:#ddd}"""
    val validDependencies = List("styles.less", "substyles.less")
  }

  "The Lessc compiler" should {

    "the minified output should be valid" in new testData {
      val (css, cssmin, dependencies) = LesscCompiler.compile(getFile("styles.less"), "lessc", Seq())
      cssmin == validCssMin
    }

    "the dependencies should be valid" in new testData {
      val (css, cssmin, dependencies) = LesscCompiler.compile(getFile("styles.less"), "lessc", Seq())
      dependencies.map { _.getName } == validDependencies
    }

  }
  def getFile(fileName: String):File = {
    new File(this.getClass.getClassLoader.getResource(fileName).toURI)
  }
}