// Simple server
import java.io._
import java.net._
import java.text.SimpleDateFormat
import java.util.Date

import scala.io._


object Server extends  App {
  val server = new ServerSocket(9999)
  var x = 0
  val time = new SimpleDateFormat("mm:ss:SSS").format(new Date(System.currentTimeMillis()))
  println(x +" : "+time )


  val s = server.accept()
  while (true) {
    val in = new BufferedSource(s.getInputStream()).getLines()
    val out = new PrintStream(s.getOutputStream())


    out.println(in.next())
    out.flush()
    s.close()
    val time = new SimpleDateFormat("mm:ss:SSS").format(new Date(System.currentTimeMillis()))
    println(x +" : "+time )
    x = x+1
  }
}