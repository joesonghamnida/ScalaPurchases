import java.io.{File, PrintWriter}
import java.nio.channels.Pipe.SourceChannel

import scala.collection.mutable
import scala.io.Source

/**
  * Created by joe on 10/18/16.
  */
object FileIO {

  def prompt(s: String) = {
    println(s);
    io.StdIn.readLine()
  }

  def choice = {
    val seq = Seq("Alcohol", "Furniture", "Shoes", "Toiletries", "Food", "Jewelry").mkString("\n")
    prompt(s"\nPlease enter a category:\n ${seq}")

  }

  def findCategory(s: String) = {
    var writeToFile = new PrintWriter(new File(s"${s}_filtered_purchases.txt"))
    purchases.filter(_._5 == s).map(x => {
      var purchase = s"Customer: ${x._1}, Date: ${x._2.substring(0, 10)}"
      println(purchase)
      writeToFile.write(purchase + "\n")
    }

    )
    writeToFile.close()
  }

  val purchases = mutable.MutableList[(String, String, String, String, String)]()

  def main(args: Array[String]): Unit = {

    Source.fromFile("purchases.csv").getLines().drop(1).foreach(line => {
      val purchase = {
        val Array(customerId, date, creditCard, cvv, category) = line.split(",").map(_.trim)
        (customerId, date, creditCard, cvv, category)
      }
      purchases += purchase
    })

    var response = ""
    while (response != "q") {
      response = choice match {
        case s => {
          if (s != "q") findCategory(s);
          ""
        }
      }
    }
  }
}
