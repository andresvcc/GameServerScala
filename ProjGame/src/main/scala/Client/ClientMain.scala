package Client

import java.net.InetSocketAddress

import akka.actor.{ActorSystem, Props}

  case class SendMessage(message: String)



/**
  * Created by Niels Bokmans on 30-3-2016.
  */
object ClientMain extends App {

  val Port = 8080

  val system = ActorSystem("ClientMain")
  val clientConnection = system.actorOf(Props(new ClientActor(new InetSocketAddress("localhost", Port), system)))
  val bufferedReader = io.Source.stdin.bufferedReader()
  loop("")

  def loop(message: String): Boolean = message match {
    case "~quit" =>
      system.shutdown()
      false
    case _ =>
      val msg = bufferedReader.readLine()
      clientConnection ! SendMessage(msg)
      loop(msg)
  }
}


object ClientMain2 extends App {

  val Port = 8080

  val system = ActorSystem("ClientMain")
  val clientConnection = system.actorOf(Props(new ClientActor(new InetSocketAddress("localhost", Port), system)))
  val bufferedReader = io.Source.stdin.bufferedReader()
  loop("")

  def loop(message: String): Boolean = message match {
    case "~quit" =>
      system.shutdown()
      false
    case _ =>
      val msg = bufferedReader.readLine()
      clientConnection ! SendMessage(msg)
      loop(msg)
  }
}