package Client

// Simple client
import java.io._
import java.net._

import scala.io._

object Client extends App {

  val s = new Socket(InetAddress.getByName("127.0.0.1"), 8080)

  var conect = true

  while(conect) {
    lazy val in = new BufferedSource(s.getInputStream()).getLines()
    val out = new PrintStream(s.getOutputStream())
    println("enviar mensaje: ")
    val mensaje = scala.io.StdIn.readLine()
    out.println(mensaje)
    out.flush()
    if(in != null) println("Received: " + in.next())
  }

  s.close()
}
